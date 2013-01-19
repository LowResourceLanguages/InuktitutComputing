// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File: MorphInuk.java
//
// Type/File type: code Java / Java code
// 
// Auteur/Author: Benoit Farley
//
// Organisation/Organization: Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:
//
// Description: Fonctions pour la décomposition d'un terme inuktitut
//              en ses diverses parties: base de mot et suffixes.
//
// -----------------------------------------------------------------------

package morph;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import data.constraints.Condition;
import data.constraints.Conditions;
import data.constraints.Imacond;
import data.constraints.ParseException;
import script.Orthography;
import script.Roman;
import data.*;
import phonology.Dialect;

import utilities1.Util;

public class MorphInuk {


    //-------------------------------------------------
    // Cette version fonctionne avec le graphe d'états.
    //-------------------------------------------------

    // DÉCOMPOSITION DU MOT.
    // Si la première tentative de décomposition échoue, on essaie un
    // certain nombre de choses :
	// - ajouter '*' à la fin si le mot termine par une voyelle (pour une consonne manquante)
	// - remplacer un 'n' final par 't' (à cause d'une nasalisation de la finale - phénomène courant)
    // Les décompositions résultantes sont ordonnées selon certaines règles.

    public static Decomposition[] decomposeWord(String word) {
        ArrayList decsList = new ArrayList();
        Vector words = null;
        
        // Trouver les mots correspondants selon les règles phonologiques
        //        Vector words = Dialect.termesCorrespondants(word);
        words = new Vector();
        words.add(word);

        for (int i = 0; i < words.size(); i++) {
            // Décomposition du mot.
            // 2ème arg. : 'false': mot en caractères latins, non en syllabique;
            // 3ème arg. : 'false': on ne demande pas la décomposition d'une racine
            // complexe, mais celle d'un mot entier.
            Vector decomps = null;
            try {
                decomps = decompose((String) words.elementAt(i), false, false);
            } catch (OutOfMemoryError e) {
                decomps = new Vector();
                System.out.println("Out of memory in decomposeWord for word '"+words.elementAt(i)+"'");
            }

            // Si aucune décomposition n'a été trouvée,
            // on essaye les choses suivantes.

            if (true
//                    (decomps == null || decomps.size() == 0) && !outOfMemory
                    ) {
                if (decomps==null)
                    decomps = new Vector();
                Vector newDecomps;
                if (Roman.typeOfLetterLat(word.charAt(word.length() - 1)) == Roman.V) {
                    // Si le mot se termine par une voyelle, il est possible
                    // qu'il manque la consonne finale. On ajoute '*' à la fin
                    // du mot, qui tient lieu de n'importe quelle consonne.
                    newDecomps = decompose(word + "*", false, false);
                } else if (word.charAt(word.length() - 1) == 'n') {
                    // Si le mot se termine par la consonne 'n', il est possible
                    // qu'il s'agisse d'un 't'.
                    newDecomps = decompose(word.substring(0, word.length() - 1)
                            + "t", false, false);
                    if (newDecomps!=null)
                        for (int j=0; j<newDecomps.size(); j++) {
                            Decomposition dec = (Decomposition)newDecomps.elementAt(j);
                            Object max[] = dec.morphParts;
                            AffixPartOfComposition affixPart = null;
                            if (max.length != 0) {
                                affixPart = (AffixPartOfComposition)max[max.length-1];
                                affixPart.setTerme(affixPart.getTerm().substring(0,affixPart.getTerm().length()-1)+"n");
                            }
                        }
                } else
                    newDecomps = new Vector();
                if (newDecomps!=null)
                    decomps.addAll(newDecomps);
            }

            // A.
            // Éliminer les décompositions qui contiennent une suite de suffixes
            // pour laquelle il existe un suffixe composé, pour ne garder que
            // la décomposition dans laquelle se trouve le suffixe composé.
            Decomposition decsM[] = new Decomposition[] {};
            decsM = (Decomposition[]) decomps.toArray(decsM);
            Decomposition decsC[];
            decsC = Decomposition.removeCombinedSuffixes(decsM);
//            decsC = decsM;
            
            // B.
            // Mettre dans l'ordre selon les règles suivantes:
            //   1. racines les plus longues
            //   2. nombre minimum de suffixes/terminaisons
            // (Ces règles sont incluses dans la classe Decomposition)
            Decomposition[] decs;
            decs = Decomposition.removeMultiples(decsC);
//            decs = decsC;
            Arrays.sort(decs);
            decsList.addAll(Arrays.asList(decs));
        }
        Decomposition[] decs = (Decomposition[]) decsList
                .toArray(new Decomposition[] {});
        return decs;
    }

    // decompose/3 retourne un vecteur de Decomposition, ou null.
    //  
    // Les décompositions sont retournées dans l'ordre où elles ont été
    // trouvées. Elles ne sont pas organisées à ce stade-ci.
    // Une Decomposition est un objet composé d'un MorceauRacine
    // et d'une table (array) de MorceauAffixe.
    // 
    // Note: l'argument isSyllabic est à toute fin pratique obsolète,
    // puisque tout mot en syllabique est d'abord translittéré en caractères
    // latins avant d'être décomposé. Tout ce qui a rapport au syllabique
    // devra éventuellement être supprimé d'une grande partie du code. On
    // ne doit pas en tenir compte ici.
    //
    // L'argument decomposeBase, lorsqu'il a la valeur 'true', indique
    // qu'on veut faire la décomposition d'un mot incomplet.

    // Note: cette méthode est appelée par plusieurs autres méthodes actuellement,
    // c'est la raison pour laquelle elle est publique. Mais éventuellement, ces
    // méthodes devraient plutôt appeler decomposeWord, et alors decompose sera
    // faite privée.

    private static Vector decompose(String term, boolean isSyllabic, boolean decomposeBase) 
    {
        Vector morphPartsInit = new Vector();
        Graph.State state;
        Vector decomposition = null;
        String simplifiedTerm = null;
        Conditions preCond = null;

        arcsByMorpheme.clear();
        
        if (!isSyllabic)
            term = Util.enMinuscule(term);

        // Etat de départ dans le graphe d'états.
        if (decomposeBase)
            state = null;
        else
            state = Graph.initialState;

        try {
			/*
			 * Cet OutputStreamWriter (System.out, en UTF-8 parce qu'à
			 * l'origine, on traitait directement les mots en syllabiques), ne
			 * sert à toutes fins pratiques que pour les messages de débogage.
			 * Tout le système de débogage est à repenser.
			 */
			if (term != null) {
                // Simplification de l'orthographe du mot, pour faciliter
                // l'analyse. En caractères latins, ceci signifie que
                // nng devient NN et ng devient N.
                simplifiedTerm = Orthography.simplifiedOrthography(term, isSyllabic);
                String transitivity = null;
                // DÉCOMPOSITION du terme simplifié.
                decomposition = decompose_simplified_term(simplifiedTerm, simplifiedTerm,
                        simplifiedTerm, morphPartsInit, 
                        new Graph.State[]{state},
                        preCond,
                        transitivity, isSyllabic
                        );
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return decomposition;
    }

    //==========================DECOMPOSER====================================
    // Lieu véritable de la décomposition des mots.
    //========================================================================

    // Décomposition d'un terme inuktitut.
    // Retourne un Vector avec 0 ou + éléments.
    // TERME peut être le terme original à décomposer ou un radical normalisé
    //       provenant de la décomposition en cours de TERME.
    //       'normalisé' signifie que lorsqu'un affixe a été trouvé et validé,
    //       la décomposition se poursuit sur le radical restant dont la finale
    //       a été ramenée à sa forme normale (par exemple, juar > juaq).
    //       NOTE: l'orthographe du terme est simplifiée: ng > N ; nng > NN
    // TERMEORIG est le terme à décomposer ou le radical tel quel, non normalisé.
    //       Orthographe simplifiée.
    // MOT est le mot original à décomposer, dans sa forme simplifiée.
    // MORCEAUX est un vecteur contenant les morphèmes trouvés jusqu'à présent.
    // ETAT indique l'état du graphe d'état où l'analyse est rendue.
    // CONDSSPECS conditions sur le morphème précédent.
    // TRANSITIVITE indique la valeur de transitivité du prochain morphème à trouver.
    // ISSYLLABIC n'est plus utilisé (il est placé par défaut à FALSE).

    // Note: le traitement des conditions spécifiques est très embryonnaire, et
    // à toutes fins pratiques, il faut le repenser totalement.

    private static Vector decompose_simplified_term(String term, String termOrig,
            String word, Vector morphParts, 
            Graph.State states[],
            Conditions preCond,
            String transitivity, boolean isSyllabic
            ) {

        Vector completeAnalysis = new Vector();
        Vector onesFound;
        Vector othersFound;
        String termICI = Orthography.orthographyICI(term, isSyllabic);
        String termOrigICI = Orthography.orthographyICI(termOrig, isSyllabic);

        //-------------- RACINE -----------------
        /*
		 * Le terme à analyser peut être une racine, simple ou complexe, connue
		 * dans la base de données comme une racine nom, verbe, adverbe, etc. On
		 * vérifie cette possibilité, et le cas échéant, on ajoute les
		 * décompositions résultantes à l'analyse complète.
		 */
        Vector rootAnalyses = analyzeRoot(termICI,termOrigICI,term,
                isSyllabic,word,morphParts,states, preCond, transitivity
                );
        completeAnalysis.addAll(rootAnalyses);

        // Le terme à analyser peut aussi se décomposer en morphèmes.
        try {
            /*
             * =================================================================
             * À partir du dernier caractère du terme, reculer 1 caractère à la
             * fois jusqu'à ce qu'un affixe soit trouvé.
             * 
             * Lorsqu'un affixe est trouvé, on crée un point de branchement: sur
             * cette nouvelle branche, on poursuit la décomposition avec le
             * radical qui précède cet affixe.
             * 
             * Lorsque ce processus est terminé, on poursuit sur la branche
             * courante la décomposition courante comme si un affixe n'avait pas
             * été trouvé. Cela permet de trouver toutes les possibilités de
             * combinaisons des lettres en morphèmes de longueurs diverses. Ex.:
             * lauq sima vs lauqsima.
             * 
             * On arrête le compteur 'positionAffix' à 2 puisqu'il n'y a pas de
             * racine qui, amputée de sa consonne finale, n'aurait plus qu'un
             * seul caractère. (Il n'y a pas de racine de 2 caractères dont le
             * dernier caractère est une consonne [susceptible d'être supprimée
             * par un suffixe].)
             */

            int positionAffix = 0; // position dans le mot
            int positionAffixStart = term.length() - 1;
            //            if (term.charAt(term.length() - 1) == '*')
            //                positionAffixStart--;

            for (positionAffix = positionAffixStart; positionAffix > 1; positionAffix--) {
                /*
                 * À la position d'analyse courante dans le terme, on vérifie si
                 * la séquence de caractères de cette position à la fin du terme
                 * est un affixe.
                 */
                String affixCandidate;
                affixCandidate = term.substring(positionAffix);
                /*
                 * 'affixCandidate' est donc un candidat correspondant à la
                 * seconde partie de 'term', de la position d'analyse courante
                 * 'positionAffix' à la fin; le radical est la première partie
                 * de 'term', de 0 à positionAffix-1 incl.
                 */
                String stem = term.substring(0, positionAffix);
                
                /*
                 * RECHERCHE D'AFFIXES---------------------------------------
                 * Chercher un affixe correspondant au(x) caractère(s)
                 * final(aux) du terme à partir de la position d'analyse
                 * courante (note: il peut y avoir plus d'un affixesExp). On
                 * cherche en fait les affixesExp qui ont 'affixCandidate' comme
                 * forme. Cette recherche est effectuée dans la table de hachage
                 * 'surfaceFormsOfAffixes'. (L'orthographe du mot à décomposer a été
                 * simplifié; il faut donc la renormaliser pour faire la
                 * recherche lexicale, puisque les données linguistiques sont
                 * stockées avec l'orthographe standard.)
                 */
                onesFound = null;
                othersFound = null;                
                /*
                 * Certaines combinaisons de caractères à la frontière de deux
                 * morphèmes ne sont pas possibles (par exemple, un suffixe
                 * commençant par une voyelle ne peut suivre un radical se
                 * terminant par un 'm'). Dans ces cas-là, il n'est même pas
                 * nécessaire de chercher des suffixes. On évitera ainsi du
                 * temps de traitement inutile, puisque dans ces cas-là, ces
                 * candidats suffixes seront éventuellement rejetés.
                 */
                /*
                 * Après avoir été essayé, il s'est avéré que cela ne change pas
                 * grand-chose. On enlève donc ce test.
                 */
                //                String finalRadInitAff = new String(new char[]{
                //                        stem.charAt(stem.length()-1),
                //                        affixCandidate.charAt(0)});
                //                boolean test =
                // Donnees.finalRadInitAffHashSet.contains(finalRadInitAff);
                if (true) { //if (test) {
                    onesFound = Lexicon.lookForForms(affixCandidate, isSyllabic);
                    /*
                     * Il est possible qu'une différence de prononciation
                     * dialectale se produise dans un groupe de consonnes à la
                     * frontière de deux suffixes. Cela peut se produire à la
                     * fin du candidat et aussi du début du candidat. Pour la
                     * fin du candidat, sa consonne finale peut être le résultat
                     * d'une action de 'validateContextActions' pour retourner
                     * la consonne contextuelle lors de l'analyse du morphème
                     * précédent, action qui tient compte des dialectes; on ne
                     * fait donc pas cette vérification. Pour le début du
                     * candidat, on fait la même chose avec la fin du radical
                     * qui précéde le candidat et le début du candidat. Il est
                     * aussi possible qu'une différence dialectale se soit
                     * produite à l'intérieur du candidat. On y cherche aussi
                     * des équivalences. Toutes les possibilités sont retenues.
                     * La loi de Schneider est aussi prise en considération.
                     */
                    Vector newCandidates = null;
                    newCandidates = Dialect.newCandidates(stem, affixCandidate,
                            null);
                    if (newCandidates != null)
                        for (int k = 0; k < newCandidates.size(); k++) {
                            Vector tr = Lexicon
                            .lookForForms((String) newCandidates
                                    .elementAt(k), isSyllabic);
                            if (othersFound == null)
                                othersFound = new Vector();
                            if (tr != null)
                                othersFound.addAll(tr);
                        }
                }
                /*
                 * POINT DE BRANCHEMENT
                 * 
                 * On est au point de branchement. On commence une branche en
                 * poursuivant la décomposition de 'radical' avec les
                 * candidats-suffixes possibles.
                 */
                
                /*
                 * 1. Les candidats-suffixes à partir de la chaîne originale.
                 */
                // Enlever les formes qui ne sont pas acceptables à ce moment-ci
                // (cf. arcsSuivis)
                Vector onesFoundDim = eliminateByArcsFollowedEtc(onesFound,states,morphParts,
                		positionAffix,preCond,transitivity);
                if (onesFoundDim != null) {
                    Vector anas = decomposeByAffixes(onesFoundDim, stem,
                            affixCandidate, states, preCond, transitivity,
                            positionAffix, morphParts, word, true);
                    completeAnalysis.addAll(anas);
                }
                /*
                 * 2. Les candidats-suffixes à partir des chaînes transformées
                 * contenant des groupes de consonnes équivalents dans d'autres
                 * dialectes.
                 */
                Vector othersFoundDim = eliminateByArcsFollowedEtc(othersFound,states,morphParts,
                		positionAffix,preCond,transitivity);
                if (othersFoundDim != null) {
                    Vector anas = decomposeByAffixes(othersFoundDim,
                            stem, affixCandidate, states, preCond,
                            transitivity, positionAffix, morphParts, word, false);
                    completeAnalysis.addAll(anas);
                }
                
                /*
                 * Retour de la boucle. On poursuit la décomposition de 'terme',
                 * qu'on ait trouvé ou pas un affixe à la position actuelle.
                 */
            } // for
            //=================================================================================
            
            
            return completeAnalysis;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return completeAnalysis;
    }
    
    private static Vector eliminateByArcsFollowedEtc(Vector onesFound, Graph.State [] states,
    		Vector morphParts, int positionAffix, Conditions preCond,
    		String transitivity) {
    	if (onesFound==null)
    		return null;
    	Vector toBeRemoved = new Vector();
    	Vector  onesFoundDimin= new Vector();
        String keyStateIDs = "0";
        for (int i=0; i<states.length; i++)
        	keyStateIDs += "+"+states[i].id;
    	for (int i=0; i<onesFound.size(); i++) {
    		SurfaceFormOfAffix formOfAffix = (SurfaceFormOfAffix)onesFound.elementAt(i); 
    		Morpheme aff = formOfAffix.getAffix();
    		if (	toBeRemoved.contains(aff)
    				|| arcsSuivis(aff,states,keyStateIDs)==null
    				|| !sameAsNext(aff, morphParts)
    				|| !samePosition(positionAffix, morphParts)
    				|| !aff.meetsConditions(preCond, morphParts)
					|| !aff.meetsTransitivityCondition(transitivity)) 
    		{
    			toBeRemoved.add(aff);
    			continue;
    		}
    		onesFoundDimin.add(formOfAffix);
    	}
//    	System.out.println(onesFound.size()+" > "+onesFoundDiminue.size());
    	return onesFoundDimin;
    }

    //=========================================================================
    // DES SUFFIXES ONT ÉTÉ TROUVÉS.
    //=========================================================================

    /*
     * Validation des affixes possibles trouvés.
     * 
     * Pour chaque affixe trouvé valide, une nouvelle branche de décomposition
     * est créée, par un appel récursif à décomposer/8.
     */
    private static Vector decomposeByAffixes(Vector onesFound,
            String stem, String affixCandidateOrig, 
            Graph.State states[],
            Conditions preCond,
            String transitivity, int positionAffix, Vector morphParts,
            String word,
            boolean notResultingFromDialectalPhonologicalTransformation) {

        Vector completeAnalysis = new Vector();

        String keyStateIDs = "0";
        for (int i=0; i<states.length; i++)
        	keyStateIDs += "+"+states[i].id;
        
        //---------------------------------------
        // Pour chaque (forme de) suffixe trouvé:
        //---------------------------------------
        for (Enumeration e = onesFound.elements(); e.hasMoreElements();) {

            SurfaceFormOfAffix form = (SurfaceFormOfAffix) e.nextElement();
            // La forme renvoie à l'affixe dont elle est une forme.
            Affix affix1 = (Affix) form.getAffix();
            // Faire une copie de cet affixe.
            Affix affix = null;
            try {
                affix = (Affix) affix1.copyOf();
            } catch (CloneNotSupportedException e1) {
            }
            boolean accepted = true;


            // Il faut vérifier 6 choses pour accepter le candidat
            // affixe trouvé:
            //   1. il ne peut s'agir du même suffixe que le précédent;
            //   2. l'affixe ne peut démarrer au mème endroit que l'affixe
            //      suivant (ceci arrive à cause de la suppression par
            //      l'affixe suivant)
            //   3. l'affixe doit être d'un type permis à ce moment-ci;
            //   4. les conditions spécifiques imposées par le morphème
            //      trouvé précédemment sur le morphème qui le précède
            //      dans le mot (le candidat actuel) doivent être respectées;
            //   5. la transitivité du radical doit être respectée;
            //   6. la forme de l'affixe doit être possible dans le
            //      contexte actuel.
            Graph.Arc[] arcsFollowed = null;
            Graph.State nextStates[] = null;
            Object stemAffs[][] = null;
            if (
                    (arcsFollowed=arcsSuivis(affix, states, keyStateIDs)) != null &&
                    (stemAffs=agreeWithContextAndActions(affixCandidateOrig, affix, stem, 
                            positionAffix, form,
                            notResultingFromDialectalPhonologicalTransformation)) != null
                    ) {
                accepted = true;
                nextStates = new Graph.State[arcsFollowed.length];
                for (int i=0; i<arcsFollowed.length; i++) {
                    Graph.State dest =  ((Graph.Arc)arcsFollowed[i]).getDestinationState();
                    nextStates[i] = (Graph.State)dest.clone();
                }
            } else
                accepted = false;
                    
                    

           /*
            * Si toutes les étapes ont réussi, on accepte l'affixe comme élément
            * de la décomposition du mot.
            */
            if (accepted) {
                /*
                 * Certains suffixes sont contraints de suivre des morphèmes
                 * spécifiques ou des morphèmes avec des propriétés spécifiques.
                 * Elles deviennent donc les nouvelles conditions spécifiques
                 * pour la suite de la décomposition.
                 */
                Conditions newCond = affix.getPrecCond();
                /* Contrainte sur la transitivité du morphème précédent. */
                String newTransitivity = affix.getTransitivityConstraint();
                if (newTransitivity==null)
                    newTransitivity = transitivity;
                
                //---------------------
                // Pour chaque résultat possible, continuer la
                // décomposition du radical retourné, et ajouter le
                // MorceauAffixe au vecteur des morphParts déjà trouvés.
                //---------------------
                for (int iro = 0; iro < stemAffs.length; iro++) {
                    Vector newMorphparts = (Vector) morphParts.clone();
                    AffixPartOfComposition partIro = (AffixPartOfComposition) stemAffs[iro][2];
                    partIro.arcs = arcsFollowed;
                    newMorphparts.add(0, partIro); // morceau ajouté
//                    System.out.println(">NOUVEAU MORCEAU:"+affixe.id+"@"+positionAffix+" > "+(String) stemAffs[iro][0]);
                    Vector analyses = decompose_simplified_term((String) stemAffs[iro][0],
                            (String) stemAffs[iro][1], word,
                            newMorphparts,
                            nextStates, 
                            newCond,
                            newTransitivity, false
                            );
                    if (analyses != null && analyses.size() != 0)
                        completeAnalysis.addAll(analyses);
                }
            } // if <condition et contexte>
            
        } // for <chaque suffixe trouvé>
        //=========================================================================
        return completeAnalysis;
    }


    //----------------------------------------------------------------------

    private static Object[][] validateContextActions(String context,
            Action action1, Action action2, String stem, int posAffix,
            Affix affix, SurfaceFormOfAffix form, boolean isSyllabic,
            boolean checkPossibleDialectalChanges,
            String affixCandidate) {

        int action1Type = action1.getType();
        int action2Type = action2.getType();
        
        // Initialiser le résultat de la function.
        Vector res = new Vector();

        // Caractère final du radical.
        char stemEndChar = stem.charAt(stem.length() - 1);
        // Caractère pénultien du radical, s'il existe.
        char stemPenultEndChar = (char) -1;
        if (stem.length() > 1)
            stemPenultEndChar = stem.charAt(stem.length() - 2);

        // Caractère initial de la forme
//        char formFirstChar = form.form.charAt(0);
        char formFirstChar = affixCandidate.charAt(0);

        // Type de ces caractères: entier correspondant à C, V.
        int typeOfStemEndChar = Roman.typeOfLetterLat(stemEndChar);
        int typeOfStemPenultEndChar = Roman.typeOfLetterLat(stemPenultEndChar);
        int typeOfFormFirstChar = Roman.typeOfLetterLat(formFirstChar);

        // Morceau qui sera enregistré dans la décomposition.
        AffixPartOfComposition partOfComp = new AffixPartOfComposition(posAffix, form);

        /*
         * Si cet affixe est non-mobile et qu'il est accepté, il faudra lui ajouter une
         * contrainte sur le morphème précédent, pour passer cette contrainte à
         * l'extérieur de cette méthode. (Seuls les suffixes ont cette propriété).
         */    
        if (affix.isNonMobileSuffix()) {
            Condition avc = new Condition.NonMobilityOfInfix(affix.id);
            affix.addPrecConstraint(avc);
        }
            
        /*
         * ------- NEUTRAL
         *
         * Avec une première action neutre et une deuxième action nulle, le
         * contexte sera respecté si les caractères finaux du radical
         * correspondent au contexte spécifié, i.e. une voyelle pour le contexte
         * V, ou les lettres t, k, q pour les contextes t, k, q. Comme l'action
         * est neutre, le résultat est unique: le radical tel quel et le suffixe
         * tel quel.
         * 
         * Note: Il y a un cas où le contexte est nul: les terminaisons
         * démonstratives. On accepte tout simplement.
         */
        if (action1Type == Action.NEUTRAL
                && action2Type == Action.NULLACTION ) {
            if (context == null) {
                res.add(new Object[] { stem, stem, partOfComp });
            } else if (context.equals("V")) {
                if (typeOfStemEndChar == Roman.V)
                    res.add(new Object[] { stem, stem, partOfComp });
            } else {
                // Treat 'ita' case first.
                String stemOrig = new String(stem);
                if (stemEndChar == 'i' && form.form.length() > 1
                        && form.form.substring(0, 2).equals("ta")) {
                    stem = stem + 't';
                    stemEndChar = 't';
                    stemPenultEndChar = 'i';
                    typeOfStemEndChar = Roman.C;
                    typeOfStemPenultEndChar = Roman.V;
                }

                // 
                if (stemEndChar==context.charAt(0))
                    res.add(new Object[] { stem, stemOrig, partOfComp });
                
                /*
                 * What precedes makes for the final consonants q, t and k of
                 * the stem as unmodified consonants. It is also possible that
                 * the final consonant of the stem has been changed due to a
                 * dialectal phonological phenomenon. The resulting consonant
                 * could happen to be one of the context consonants (t,k,q), or
                 * another one (s,n,m,...). What follows is to take care of
                 * those possibilities.
                 */

                // DIALECTALLY EQUIVALENT CONSONANT CLUSTERS
                /*
                 * Are there consonant clusters equivalent to that formed by the
                 * final consonant of the stem and the initial consonant of the
                 * suffix?
                 */
                if (true
//                        checkPossibleDialectalChanges
                        && typeOfStemEndChar == Roman.C 
                        && typeOfFormFirstChar == Roman.C) {
                    // Both are consonants
                    // Find equivalent clusters
                    Vector grs = Dialect.equivalentGroups(stemEndChar,
                            formFirstChar);
                    /*
                     * For each equivalent cluster the first consonant of which
                     * is the context consonant and the second is the initial consonant of
                     * the form, add to the result vector a stem
                     * resulting from the current stem with its final consonant
                     * replaced with the context consonant.
                     *  
                     * Ex.: stem='inus' suffix='siut' returns, for context 'k':
                     * new stem='inuk' because of ks <> ss.
                     */
                    if (grs != null)
                        for (int i = 0; i < grs.size(); i++)
                            if (((String) grs.elementAt(i)).charAt(0) == context
                                    .charAt(0) &&
                                    ((String)grs.elementAt(i)).charAt(1) == formFirstChar) {
                                res.add(new Object[] {
                                        stem.substring(0, stem.length() - 1)
                                                + context, stemOrig, partOfComp });
                                break;
                            }
                }

                /*
                 * If the context is a consonant and if the stem ends with a
                 * vowel, it might be that the contextual consonant is missing
                 * because of Schneider's law. In that case, let's return the
                 * suffixe with the modified stems.
                 */
                if (typeOfStemEndChar==Roman.V && typeOfFormFirstChar==Roman.C) {
                    Object x[] = Dialect.schneiderStateAtEnd(stemOrig);
                    boolean doubleConsonants = ((Boolean) x[0]).booleanValue();
                    if (doubleConsonants) {
                        /*
                         * The stem ends with a vowel and there are 2 consonants
                         * before it. And the candidate suffix starts with a
                         * consonant. This is a place where Schneider's law
                         * might have worked. Let's add the possibly suppressed
                         * consonant to the stem.
                         */
                        res.add(new Object[] { stemOrig + context,
                                        stemOrig, partOfComp });
                    }
                }
            }
            /*
             * S'il y a une condition (cas de ji/1vv)
             */
            String cond = action1.getCondition();
            if (res.size() != 0 && cond != null && cond.startsWith("id:")) {
                // Il faut ajouter à 'affix' une condition spécifique
                // précédente, pour la passer à l'extérieur de cette
                // méthode.
                try {
                    Condition avc = new Imacond(
                            new ByteArrayInputStream(cond.getBytes())).ParseCondition();
                    affix.addPrecConstraint(avc);
                } catch (ParseException e) {
                    // Ne devrait pas arriver.
                }
            } else {
                // Aucun suffixe ne mêne ici.
            }
        }

        /*
         * ------- NEUTRAL + DELETION
         * 
         * L'action 2 est spécifiée pour les suffixes qui se trouvent à être
         * ajoutés à un radical finissant par deux voyelles (souvent après
         * suppression de la consonne finale) et qui commencent (souvent)eux-
         * mêmes par une voyelle, provoquant ainsi une situation par laquelle on
         * se retrouve avec une succession de trois voyelles, ce qui est
         * toujours évité en inuktitut. Dans le contexte V(oyelle): Puisque
         * l'action 1 est 'neutre', il n'y a donc pas de suppression de consonne
         * et conséquemment, cela signifie que la seconde des deux voyelles
         * finales est supprimée. Donc, le contexte sera respecté si le radical
         * se termine par une voyelle (précédée d'une consonne). Dans le
         * contexte consonnantique: L'action1 neutre suggère que normalement, il
         * n'y a pas de suppression de la consonne finale; mais l'action2
         * suggère que si la syllabe finale contient 2 voyelles, la consonne
         * finale sera supprimée.
         */
        else if (action1Type == Action.NEUTRAL
                && action2Type == Action.DELETION) {
            if (context.equals("V")) {
                if (typeOfStemEndChar == Roman.V
                        && (typeOfStemPenultEndChar == -1 || typeOfStemPenultEndChar == Roman.C)) {
                    // L'action2 suggère qu'il est possible qu'une voyelle ait
                    // été supprimée. Mais pas nécessairement. Le résultat
                    // doit donc contenir le radical tel quel, mais aussi le
                    // radical auquel on ajoute cette voyelle supprimée.
                    res.add(new Object[] { stem, stem, partOfComp }); // tel quel
                    res.add(new Object[] { stem + "a", stem, partOfComp });
                    res.add(new Object[] { stem + "i", stem, partOfComp });
                    res.add(new Object[] { stem + "u", stem, partOfComp });
                }
            } else {
                /*
                 * Dans le contexte consonnantique, avec une première action
                 * neutre: l'action 2 suggère que dans le cas où la dernière
                 * syllabe contient 2 voyelles suivies d'une consonne, cette
                 * consonne contextuelle sera supprimée. Dans ce cas, on a un
                 * radical qui se termine par deux voyelles.
                 */
                if (stem.length() > 1) {
                    char charBeforeStemEndChar = stem.charAt(stem.length() - 2);
                    int typeOfCharBeforeStemEndChar = Roman
                            .typeOfLetterLat(charBeforeStemEndChar);
                    if (typeOfStemEndChar == Roman.V
                            && typeOfCharBeforeStemEndChar == Roman.V)
                        res.add(new Object[] { stem + context, stem, partOfComp });
                }
            }
        }

        /*
         * ------- NEUTRAL + INSERTION
         * 
         * L'action 2 est spécifiée pour les suffixes qui commencent par une
         * voyelle. Ces suffixes suppriment normalement la consonne finale du
         * radical auquel ils s'attachent. Si le radical se termine par deux
         * voyelles, avec possiblement une consonne qui sera supprimée, on se
         * retrouve avec 3 voyelles consécutives, ce qui n'est pas permis. Ici,
         * la stratégie est d'insérer quelque chose entre les deux voyelles du
         * radical et la voyelle du suffixe.
         * 
         * Ici, l'action 1 est neutre, donc pas de suppression de consonne.
         * (Ceci n'arrive en fait que dans le contexte de voyelle.)
         * 
         * Dans ce cas, le contexte sera respecté uniquement si le radical se
         * termine par deux voyelles suivies du caractère à insérer, ou s'il se
         * termine par une voyelle. Dans le premier cas, le résultat est unique:
         * le radical sans le caractère à insérer et le suffixe avec le
         * caractère à insérer. Dans le second cas, le résultat est unique
         * aussi: le radical tel quel et le suffixe tel quel.
         */

        else if (action1Type == Action.NEUTRAL
                && action2Type == Action.INSERTION) {
            String insert = Orthography.simplifiedOrthography(action2
                    .getInsert(), isSyllabic);
            int linsert = insert.length();
            int lstem = stem.length();

            if (context.equals("V")) {
                // Dans le contexte d'un radical se terminant par une voyelle,
                // avec une première action neutre:

                // Si le radical se termine avec le caractère à insérer et
                // qu'il est précédé par deux voyelles, le contexte est
                // possible:
                // le radical possible pour la suite de l'analyse est donc le
                // radical actuel sans le caractère à insérer.

                // La seule autre possibilité est que le radical se termine par
                // une voyelle, car aucune autre consonne que la consonne à
                // insérer
                // ne peut s'y trouver.
                if (stem.endsWith(insert)
                        && lstem > linsert + 2
                        && Roman.typeOfLetterLat(stem.charAt(lstem - linsert - 1)) == Roman.V
                        && Roman.typeOfLetterLat(stem.charAt(lstem - linsert - 2)) == Roman.V) {
                    AffixPartOfComposition npartOfComp = new AffixPartOfComposition(posAffix - linsert,
                            form);
                    res.add(new Object[] { stem.substring(0, lstem - linsert),
                            stem.substring(0, lstem - linsert), npartOfComp });
                } else if (typeOfStemEndChar == Roman.V)
                    res.add(new Object[] { stem, stem, partOfComp });
            }
        }

        /*
         * ------- DELETION
         * 
         * La suppression comme action première, sans seconde action, est
         * toujours celle d'une consonne. On n'a donc pas à tenir compte du
         * contexte de voyelle.
         * 
         * Le contexte sera respecté selon les conditions suivantes:
         * 
         * Dans le contexte d'une consonne, le contexte sera respecté si le
         * radical se termine par une voyelle (étant donné qu'un radical ne peut
         * terminer par deux consonnes), et le résultat sera unique: le radical
         * augmenté de la consonne contextuelle, et le suffixe tel quel.
         */

        else if (action1Type == Action.DELETION
                && action2Type == Action.NULLACTION) {
            // on suppose un contexte de consonne spécifique, i.e. t, k, q
            if (context.equals("t") || context.equals("k")
                    || context.equals("q"))
                if (typeOfStemEndChar == Roman.V) {
                    res.add(new Object[] { stem + context, stem, partOfComp });
                    // Le dialecte d'Aivilik utilise "r" plutôt que "q":
                    // on ajoute cette possibilité.
                    // 		    if (context.equals("q"))
                    // 			res.add(new Object [] {stem+"r",stem,partOfComp});
                }
        }

        /*
         * //----- DELETION ET INSERTION
         * 
         * La suppression de la consonne finale du radical est suivie de
         * l'insertion de caractères. Le contexte sera donc respecté si le
         * radical se termine par les caractères d'insertion précédé d'une
         * voyelle. Puisque l'action2 est nulle, on ne s'occupe pas du cas où il
         * y a deux voyelles. Le résultat est unique: le radical sans les
         * caractères insérés et augmenté de la consonne contextuelle, et le
         * suffixe tel quel.
         */

        else if (action1Type == Action.DELETIONINSERTION
                && action2Type == Action.NULLACTION) {
            String carsInsere = Orthography.simplifiedOrthography(action1
                    .getInsert(), isSyllabic);
            int linsert = carsInsere.length();
            int lstem = stem.length();

            if ((stem.endsWith(carsInsere))) {

                AffixPartOfComposition npartOfComp = new AffixPartOfComposition(posAffix - linsert,
                        form);
                res.add(new Object[] {
                        stem.substring(0, lstem - linsert) + context,
                        stem.substring(0, lstem - linsert), npartOfComp });
            }

        }

        /*
         * ----- DELETION CONDITIONNELLE
         * 
         * La suppression de la consonne finale du radical est conditionnelle à
         * la présence dans le radical de la séquence définie par l'expression
         * régulière de la condition. Le contexte sera respecté si le radical se
         * termine par une voyelle (étant donné qu'un radical ne peut terminer
         * par deux consonnes), et si la condition sur le radical est respectée,
         * et le résultat sera unique: le radical augmenté de la consonne
         * contextuelle, et le suffixe tel quel.
         * 
         * On a aussi un cas où la suppression conditionnelle s'exerce dans un
         * contexte de voyelle: le suffixe antipassif -ji/1vv- après -uti/1vv- où le
         * 'i' de 'uti' est supprimé par contraction.
         */

        else if (action1Type == Action.CONDITIONALDELETION
                && action2Type == Action.NULLACTION) {
            // on suppose un contexte de consonne spécifique, i.e. t, k, q
            if ( ((context.equals("t") || context.equals("k") || context
                    .equals("q")) && typeOfStemEndChar==Roman.V) ||
                    (context.equals("V") && typeOfStemEndChar==Roman.C) ) {
                String cond = action1.getCondition();
                if (cond.startsWith("id:")) {
                    // Il faut ajouter à 'affix' une condition spécifique
                    // précédente, pour la passer à l'extérieur de cette
                    // méthode.
                    try {
                        Condition avc = new Imacond(
                                new ByteArrayInputStream(cond.getBytes())).ParseCondition();
                        affix.addPrecConstraint(avc);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (!context.equals("V"))
                        // Suppression de consonne
                        res.add(new Object[] { stem + context, stem, partOfComp });
                    else {
                        // Suppression de voyelle
                        String formInCond = cond.substring(3,cond.indexOf("/"));
                        // 'form' devrait finir avec une voyelle (ex.: uti)
                        res.add(new Object[]{stem+formInCond.substring(formInCond.length()-1),stem,partOfComp});
                        /*
                         * Comme il y a eu suppression de voyelle, la dernière lettre du 
                         * radical peut être une voyelle ou une consonne.  Si c'est une
                         * consonne, et que le suffixe commence par une consonne, il est
                         * possible qu'il y ait eu un changement phonologique dialectal 
                         * dans le groupe de consonnes.
                         */
                        if (typeOfStemEndChar==Roman.C && typeOfFormFirstChar==Roman.C &&
                                checkPossibleDialectalChanges){
                            Vector grs = Dialect.equivalentGroups(stemEndChar,
                                    formFirstChar);
                            if (grs != null)
                                for (int i = 0; i < grs.size(); i++)
                                    if (((String)grs.elementAt(i)).charAt(1) == formFirstChar) {
                                        res.add(new Object[] {
                                                stem.substring(0, stem.length() - 1)+
                                                ((String)grs.elementAt(i)).charAt(0)+
                                                formInCond.substring(formInCond.length()-1), stem, partOfComp }); // or 'form' ?
                                    }
                        }
                    }
                } else {
                    Pattern pattern = Pattern.compile(action1.getCondition());
                    Matcher matcher = pattern.matcher(stem);
                    if (matcher.find()) {
                        res.add(new Object[] { stem + context, stem, partOfComp });
                    }
                }
            }
        }

        /*
         * ----- VOICING
         * 
         * La sonorisation affecte toujours une consonne. Elle n'a de sens que
         * dans un contexte de consonne. Si le contexte spécifié est une
         * consonne définie, le radical doit se terminer par la version sonore
         * de cette consonne. Par exemple, 'g' remplace 'k'. Mais il est aussi
         * possible que ce 'g' soit assimilé à la consonne suivante par
         * gémination dialectale; par exemple, 'g' devant 'v' devient 'v'. C'est
         * possible par exemple dans le mot 'ikupivvilirijikkunnut' = ikupik vik
         * liri ji kkut nut Le résultat sera unique: le radical dont la consonne
         * finale est remplacée par sa version sourde, et le suffixe tel quel.
         * 
         */

        else if (action1Type == Action.VOICING
                && action2Type == Action.NULLACTION) {
            if (!context.equals("V")) {
                char voicedCorrespondingChar = Roman.voicedOfOcclusiveUnvoicedLat(context.charAt(0));
                if (stemEndChar == voicedCorrespondingChar) {
                    res.add(new Object[] {
                            stem.substring(0, stem.length() - 1) + context, stem,
                            partOfComp });
                } else if (typeOfStemEndChar == Roman.V) {
                    /*
                     * If the context is a consonant and if the stem ends with a
                     * vowel, it might be that the contextual consonant is
                     * missing because of Schneider's law. In that case, let's
                     * return the suffixe with the modified stems.
                     */
                    if (typeOfStemEndChar==Roman.V && typeOfFormFirstChar==Roman.C) {
                        Object x[] = Dialect.schneiderStateAtEnd(stem);
                        boolean doubleConsonants = ((Boolean) x[0]).booleanValue();
                        if (doubleConsonants) {
                            /*
                             * The stem ends with a vowel and there are 2
                             * consonants before it. And the candidate suffix
                             * starts with a consonant. This is a place where
                             * Schneider's law might have worked. Let's add the
                             * possibly suppressed consonant to the stem.
                             */
                            res.add(new Object[] { stem + context,
                                            stem, partOfComp });
                        }
                    }
                } else if (checkPossibleDialectalChanges){
                    Vector grs = Dialect.equivalentGroups(stemEndChar,
                            formFirstChar);
                    if (grs != null)
                        for (int i = 0; i < grs.size(); i++)
                            if (((String) grs.elementAt(i)).charAt(0) == voicedCorrespondingChar &&
                                    ((String)grs.elementAt(i)).charAt(1) == formFirstChar) {
                                res.add(new Object[] {
                                        stem.substring(0, stem.length() - 1)
                                                + context, stem, partOfComp });
                                break;
                            }
                }
            }
        }

        /*
         * ----- NASALIZATION
         * 
         * La nasalisation affecte toujours une consonne. Elle n'a de sens que
         * dans un contexte de consonne.
         * 
         * Si le contexte spécifié est une consonne définie, le radical doit se
         * terminer par la version nasale de cette consonne. Par exemple, 'N'
         * (ng) remplace 'k'. Mais il est possible que ce 'N' soit assimilé à la
         * consonne suivante par gémination dialectale; par exemple, 'N' devant
         * 'n' devient 'n'. C'est possible par exemple dans le mot
         * 'atangirasunniaqqauviit' ('Were you planning to finish all at once?')
         * = atangiq nasuk niaqqau viit, où le 'k' de 'nasuk', qui devrait être
         * nasalisé en 'N (ng)' est plutôt assimilé à 'n'. Le résultat sera
         * unique: le radical dont la consonne finale est remplacée par sa
         * version sourde, et le suffixe tel quel.
         */

        else if (action1Type == Action.NASALIZATION) {
            if (!context.equals("V")) {
                // Consonant context
                char nasalCorrespondingChar = Roman.nasalOfOcclusiveUnvoicedLat(context.charAt(0));
                if (stemEndChar == nasalCorrespondingChar) {
                    res.add(new Object[] {
                            stem.substring(0, stem.length() - 1) + context, stem,
                            partOfComp });
                } else if (typeOfStemEndChar == Roman.V) {
                    /*
                     * If the context is a consonant and if the stem ends with a
                     * vowel, it might be that the contextual consonant is
                     * missing because of Schneider's law. In that case, let's
                     * return the suffixe with the modified stems.
                     */
                    if (typeOfStemEndChar==Roman.V && typeOfFormFirstChar==Roman.C) {
                        Object x[] = Dialect.schneiderStateAtEnd(stem);
                        boolean doubleConsonants = ((Boolean) x[0]).booleanValue();
                        if (doubleConsonants) {
                            /*
                             * The stem ends with a vowel and there are 2
                             * consonants before it. And the candidate suffix
                             * starts with a consonant. This is a place where
                             * Schneider's law might have worked. Let's add the
                             * possibly suppressed consonant to the stem.
                             */
                            res.add(new Object[] { stem + context,
                                            stem, partOfComp });
                        }
                    }
                } else if (checkPossibleDialectalChanges) {
                    Vector grs = Dialect.equivalentGroups(stemEndChar,
                            formFirstChar);
                    if (grs != null)
                        for (int i = 0; i < grs.size(); i++)
                            if (((String) grs.elementAt(i)).charAt(0) == nasalCorrespondingChar &&
                                    ((String)grs.elementAt(i)).charAt(1) == formFirstChar) {
                                res.add(new Object[] {
                                        stem.substring(0, stem.length() - 1)
                                                + context, stem, partOfComp });
                                break;
                            }
                }
            }
        }

        /*
         * ------- NASALIZATION CONDITIONNELLE
         */
        else if (action1Type == Action.CONDITIONALNASALIZATION) {
            String cond = action1.getCondition();
                // Il faut ajouter à 'affix' une condition spécifique
                // précédente, pour la passer à l'extérieur de cette
                // méthode.
                char nasalCorrespondingChar = Roman.nasalOfOcclusiveUnvoicedLat(context.charAt(0));
                if (stemEndChar == nasalCorrespondingChar) {
                    try {
                        Condition avc = new Imacond(
                                new ByteArrayInputStream(cond.getBytes())).ParseCondition();
                        affix.addPrecConstraint(avc);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    res.add(new Object[] {
                            stem.substring(0, stem.length() - 1) + context, stem,
                            partOfComp });
                } else if (typeOfStemEndChar == Roman.V) {
                    /*
                     * If the context is a consonant and if the stem ends with a
                     * vowel, it might be that the contextual consonant is
                     * missing because of Schneider's law. In that case, let's
                     * return the suffixe with the modified stems.
                     */
                    if (typeOfStemEndChar==Roman.V && typeOfFormFirstChar==Roman.C) {
                        Object x[] = Dialect.schneiderStateAtEnd(stem);
                        boolean doubleConsonants = ((Boolean) x[0]).booleanValue();
                        if (doubleConsonants) {
                            try {
                                Condition avc = new Imacond(
                                        new ByteArrayInputStream(cond.getBytes())).ParseCondition();
                                affix.addPrecConstraint(avc);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            /*
                             * The stem ends with a vowel and there are 2
                             * consonants before it. And the candidate suffix
                             * starts with a consonant. This is a place where
                             * Schneider's law might have worked. Let's add the
                             * possibly suppressed consonant to the stem.
                             */
                            res.add(new Object[] { stem + context,
                                            stem, partOfComp });
                        }
                    }
                } else if (checkPossibleDialectalChanges) {
                    Vector grs = Dialect.equivalentGroups(stemEndChar,
                            formFirstChar);
                    if (grs != null)
                        for (int i = 0; i < grs.size(); i++)
                            if (((String) grs.elementAt(i)).charAt(0) == nasalCorrespondingChar &&
                                    ((String)grs.elementAt(i)).charAt(1) == formFirstChar) {
                                try {
                                    Condition avc = new Imacond(
                                            new ByteArrayInputStream(cond.getBytes())).ParseCondition();
                                    affix.addPrecConstraint(avc);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                res.add(new Object[] {
                                        stem.substring(0, stem.length() - 1)
                                                + context, stem, partOfComp });
                                break;
                            }
                }
        }
        /*
         * ------- INSERTION
         * 
         * Pour que le contexte soit respecté, il faut que le radical se termine
         * par le caractère correspondant au contexte spécifié. Le résultat est
         * unique: le radical sans les caractères insérés, et le suffixe tel
         * quel.  La forme du suffixe passée ici contient le caractère inséré, par
         * exemple vvik pour vik dans le contexte de voyelle.
         */
        else if (action1Type == Action.INSERTION
                && action2Type == Action.NULLACTION) {
            if ((context.equals("V") && 
                    (stem.endsWith("a")
                            || stem.endsWith("i") 
                            || stem.endsWith("u")))
                            || (stem.endsWith(context))) {
                res.add(new Object[] { stem,
                        stem, partOfComp });
            }
        }


        /*
         * ------- FUSION
         * 
         * La fusion est toujours spécifiée dans un contexte défini de consonne.
         * Elle implique que la consonne finale du radical, spécifiée par le
         * contexte, a été en fait supprimée pour être fondue dans l'initiale du
         * suffixe. Cela signifie que le contexte ne peut être respecté que si
         * le radical se termine par une voyelle. Le résultat sera unique: le
         * radical auquel on a ajouté la consonne spécifiée par le contexte, et
         * le suffixe tel quel.
         */
        else if (action1Type == Action.FUSION && action2Type == Action.NULLACTION) {
            if ((context.equals("t") || context.equals("k") || context
                    .equals("q"))
                    && typeOfStemEndChar == Roman.V)
                res.add(new Object[] { stem + context, stem, partOfComp });
        }

        /*
         * ------- ASSIMILATION
         * 
         * L'assimilation est toujours spécifiée dans un contexte défini de
         * consonne. Pour que le contexte soit respecté, il faut que la dernière
         * lettre du radical soit une consonne, identique à la consonne initiale
         * du suffixe. Le résultat sera unique: le radical dont la consonne
         * finale assimilée est remplacée par la consonne spécifiée par le
         * contexte, et le suffixe tel quel.
         */

        else if (action1Type == Action.ASSIMILATION
                && action2Type == Action.NULLACTION) {
            if (typeOfStemEndChar == Roman.C && stemEndChar == form.form.charAt(0))
                res
                        .add(new Object[] {
                                stem.substring(0, stem.length() - 1) + context,
                                stem, partOfComp });
            /*
             * If the context is a consonant and if the stem ends with a vowel,
             * it might be that the contextual consonant is missing because of
             * Schneider's law. In that case, let's return the suffixe with the
             * modified stems.
             */
            if (typeOfStemEndChar==Roman.V && typeOfFormFirstChar==Roman.C) {
                Object x[] = Dialect.schneiderStateAtEnd(stem);
                boolean doubleConsonants = ((Boolean) x[0]).booleanValue();
                if (doubleConsonants) {
                    /*
                     * The stem ends with a vowel and there are 2 consonants
                     * before it. And the candidate suffix starts with a
                     * consonant. This is a place where Schneider's law might
                     * have worked. Let's add the possibly suppressed consonant
                     * to the stem.
                     */
                    res.add(new Object[] { stem + context,
                                    stem, partOfComp });
                }
            }
        }

        /*
         * ------- SPECIFICASSIMILATION
         * 
         * L'assimilation spécifique d'une consonne dans un contexte donné
         * signifie qu'un radical verra sa consonne finale (contexte) assimilée
         * (changée) à la lettre spécifiée. Pour que le contexte soit respecté,
         * il faut que le radical se termine par la consonne désignée (ex.
         * assim(k) -> k). Le résultat est unique: le radical avec la consonne
         * finale remplacée par le contexte, et le suffixe tel quel.
         */

        else if (action1Type == Action.SPECIFICASSIMILATION
                && action2Type == Action.NULLACTION) {
            if (stemEndChar == action1.getAssimA().charAt(0))
                res
                        .add(new Object[] {
                                stem.substring(0, stem.length() - 1) + context,
                                stem, partOfComp });
        }

        /*
         * ------- DELETION + SPECIFICDELETION
         * 
         * Ces actions combinées ne se rencontrent que pour les suffixes -it- et
         * -ut-, dans le contexte d'un radical en -q. Le contexte sera
         * donc respecté si la dernière lettre du radical est une voyelle, parce
         * que l'action 2 suppose la présence de deux voyelles à la fin du
         * radical, une fois la consonne finale supprimée. Il y a 2
         * possibilités: 1) le radical n'a pas deux voyelles devant la consonne
         * contextuelle (q); il y a eu suppression de cette consonne: le
         * résultat est le radical auquel on ajoute la consonne. 2) le radical
         * se termine par deux voyelles, dont la seconde est définie par
         * l'action 2; il y a eu suppression de la consonne et de cette voyelle:
         * le résultat est le radical auquel on ajoute la voyelle puis la
         * consonne.  Dans ce dernier cas, s'il y a condition à la suppression,
         * elle doit être respectée par le radical.
         */

        else if (action1Type == Action.DELETION
                && action2Type == Action.SPECIFICDELETION) {
            if (typeOfStemEndChar == Roman.V) {
                res.add(new Object[] { stem + context, stem, partOfComp });
                if (action2.getCondition() != null) {
                    String cond = action2.getCondition();
                    Pattern p = Pattern.compile(cond);
                    Matcher m = p.matcher(stem);
                    if (m.find())
                        res.add(new Object[] { stem + action2.getSuppr() + context, stem,
                                partOfComp });
                } else
                    res.add(new Object[] { stem + action2.getSuppr() + context, stem,
                            partOfComp });
            }
        }
        

        /*
         * ------- DELETION + INSERTION
         * 
         * Cette combinaison d'action se rencontre dans les contexts de
         * consonne.
         *
         * Pour que le contexte soit respecté, il faut ce qui suit:
         * 
         * a) si le radical se termine par deux voyelles et les caractères à
         * insérer:
         *    1. si le radical se termine par une voyelle:
         *       (les caractères à insérer se terminent par une voyelle)
         *       il y a 2 résultats:
         *           i. le radical sans les caractères insérés + consonne de contexte,
         *              et le suffixe avec les caractères insérés;
         *          ii. le radical + consonne de contexte,
         *              et le suffixe tel quel sans les caractères insérés.
         *    2. si le radical ne se termine pas par une voyelle:
         *       (les caractères à insérer ne se terminent pas par une voyelle)
         *       il n'y a qu'un seul résultat:
         *           i. le radical sans les caractères insérés + consonne de contexte,
         *              et le suffixe avec les caractères insérés.
         * b) si le radical se termine par une seule voyelle:
         *    1. il y a un résultat unique:
         *       i. le radical + consonne de contexte,
         *          et le suffixe tel quel sans les caractères insérés.
         */

        else if (action1Type == Action.DELETION
                && action2Type == Action.INSERTION) {
            String insert = Orthography.simplifiedOrthography(action2
                    .getInsert(), isSyllabic);
            int lstem = stem.length();
            int linsert = insert.length();
            String cntx = null;

            // Note: Le contexte de la forme vérifiée est V, t, k ou q
            if (context.equals("V"))
                cntx = "";
            else
                cntx = context;

            if (stem.endsWith(insert)
                    && lstem - linsert > 2
                    && Roman.typeOfLetterLat(stem.charAt(lstem - linsert - 1)) == Roman.V
                    && Roman.typeOfLetterLat(stem.charAt(lstem - linsert - 2)) == Roman.V)
                if (Roman.typeOfLetterLat(stem.charAt(lstem - 1)) == Roman.V
                        && Roman.typeOfLetterLat(stem.charAt(lstem - 2)) == Roman.C) {

                    AffixPartOfComposition npartOfComp = new AffixPartOfComposition(posAffix - linsert,
                            form);
                    res.add(new Object[] {
                            stem.substring(0, lstem - linsert) + cntx,
                            stem.substring(0, lstem - linsert), npartOfComp });
                    res.add(new Object[] { stem + cntx, stem, partOfComp });
                } else {
                    AffixPartOfComposition npartOfComp = new AffixPartOfComposition(posAffix - linsert,
                            form);
                    res.add(new Object[] {
                            stem.substring(0, lstem - linsert) + cntx,
                            stem.substring(0, lstem - linsert), npartOfComp });
                }

            else if (lstem > 2
                    && Roman.typeOfLetterLat(stem.charAt(lstem - 1)) == Roman.V
                    && Roman.typeOfLetterLat(stem.charAt(lstem - 2)) == Roman.C)
                res.add(new Object[] { stem + cntx, stem, partOfComp });

            else
                ;

        }

        /*
         * ------- VOWELLENGTHENING + ANNULATION
         * 
         * L'allongement de la voyelle finale d'un nom par une terminaison
         * nominale est annulée si cette voyelle finale est elle-même précédée
         * d'une autre voyelle, i.e. si le radical se termine par 2 voyelles. Le
         * contexte sera respecté si:
         * 
         * a) le radical se termine par deux voyelles identiques.
         *    - Le résultat est le radical sans la dernière voyelle (résultat de
         *      l'allongement) et le suffixe tel quel.
         *    - Il y a aussi possibilité que le radical lui-même se termine par
         *      ces deux voyelles identiques, auquel cas il n'y a pas d'
         *      allongement; il y a donc un second résultat possible: le radical
         *      tel quel et le suffixe tel quel.
         * b) le radical se termine par deux voyelles différentes.
         *    - Le résultat est le radical tel quel (parce qu'alors,
         *      l'allongement de la voyelle finale est annulé) et le suffixe tel
         *      quel.
         * 
         */

        else if (action1Type == Action.VOWELLENGTHENING
                && action2Type == Action.CANCELLATION) {
            if (typeOfStemEndChar == Roman.V) {
                if (stem.length() > 3
                        && stem.charAt(stem.length() - 2) == stemEndChar) {

                    AffixPartOfComposition npartOfComp = new AffixPartOfComposition(posAffix - 1, form);
                    res.add(new Object[] { stem.substring(0, stem.length() - 1),
                            stem.substring(0, stem.length() - 1), npartOfComp });
                    res.add(new Object[] { stem, stem, partOfComp });
                } else if (stem.length() > 3
                        && Roman.typeOfLetterLat(stem.charAt(stem.length() - 2)) == Roman.V) {

                    res.add(new Object[] { stem, stem, partOfComp });
                }
            }
        }

        /*
         * ------- DELETIONVOWELLENGTHENING + ANNULATION
         * 
         * La suppression de la consonne finale du radical est suivie de l'
         * allongement de la voyelle précédant cette consonne. Si cette voyelle
         * est elle-même précédée d'une autre voyelle, l'allongement est annulé.
         * Le contexte est respecté dans les mêmes conditions que ci-dessus pour
         * l'allongement de voyelle + annulation. Il y a aussi les mêmes
         * résultats, sauf qu'à cause de la suppression, le radical se voit
         * ajouter la consonne du contexte. (voir suppression ci-dessus)
         */

        else if (action1Type == Action.DELETIONVOWELLENGTHENING
                && action2Type == Action.CANCELLATION) {
            if (typeOfStemEndChar == Roman.V) {
                if (stem.length() > 3
                        && stem.charAt(stem.length() - 2) == stemEndChar) {

                    AffixPartOfComposition npartOfComp = new AffixPartOfComposition(posAffix - 1, form);
                    res.add(new Object[] {
                            stem.substring(0, stem.length() - 1) + context,
                            stem.substring(0, stem.length() - 1), npartOfComp });
                    res.add(new Object[] { stem + context, stem, partOfComp });
                } else if (stem.length() > 3
                        && Roman.typeOfLetterLat(stem.charAt(stem.length() - 2)) == Roman.V) {

                    res.add(new Object[] { stem + context, stem, partOfComp });
                }
            }
        }

        /*
         * ------- INSERTIONVOWELLENGTHENING
         * 
         * Ceci n'arrive que dans le contexte d'une finale nominale en t. Un i
         * est inséré après le t final du radical, et allongé avant l'addition
         * du suffixe. (L'allongement est une marque du duel). Le contexte est
         * respecté si le radical se termine par la consonne de contexte et deux
         * occurrences de la voyelle insérée.
         */

        else if (action1Type == Action.INSERTIONVOWELLENGTHENING
                && action2Type == Action.NULLACTION) {
            if (stemEndChar == action1.getInsert().charAt(0) && stem.length() > 3
                    && stem.charAt(stem.length() - 2) == stemEndChar
                    && stem.charAt(stem.length() - 3) == context.charAt(0)) {
                res.add(new Object[] { stem.substring(0, stem.length() - 2),
                        stem.substring(0, stem.length() - 2), partOfComp });
            }
        }

        /*
         * ------- NEUTRAL + DECAPITATION
         * 
         * Si le radical termine par 1 voyelle, l'autodécapitation n'a pas lieu
         * et la forme du suffixe devrait être la forme intégrale. Dans ce cas,
         * le contexte est respecté. Si le radical termine par 2 voyelles, la
         * voyelle initiale du suffixe/ terminaison (puisque ceci n'arrive que
         * pour les suffixes/terminaisons commençant par une voyelle) est
         * supprimée, et la forme devrait être la forme autodécapitée. Le
         * contexte est respecté si le radical se termine par deux voyelles.
         */
        else if (action1Type == Action.NEUTRAL
                && action2Type == Action.SELFDECAPITATION) {
            if (typeOfStemEndChar == Roman.V) {
                if (stem.length() > 1)
                    if (Roman.typeOfLetterLat(stem.charAt(stem.length() - 2)) == Roman.V
                            && form.form.length() == form.getAffix().morpheme
                                    .length() - 1) {
                        res.add(new Object[] { stem, stem, partOfComp });
                    } else if (Roman
                            .typeOfLetterLat(stem.charAt(stem.length() - 2)) == Roman.C
                            && form.form.length() == form.getAffix().morpheme
                                    .length()) {
                        res.add(new Object[] { stem, stem, partOfComp });
                    }
            }
        }

        /*
         * ------- DELETION + DECAPITATION
         * 
         * La consonne finale du radical est supprimée. Si cette consonne finale
         * supprimée est précédée de deux voyelles, la voyelle initiale du
         * suffixe/ terminaison est supprimée. Mêmes conditions que ci-dessus
         * pour que le contexte soit respecté. Même résultat, sauf que la
         * consonne de contexte est ajoutée au radical.
         */
      
        else if (action1Type == Action.DELETION
                && action2Type == Action.SELFDECAPITATION) {
            if (typeOfStemEndChar == Roman.V) {
                if (stem.length() > 2)
                    if (Roman.typeOfLetterLat(stem.charAt(stem.length() - 2)) == Roman.V
                            && form.form.length() == form.getAffix().morpheme
                                    .length() - 1) {
                        res.add(new Object[] { stem + context, stem, partOfComp });
                    } else if (Roman
                            .typeOfLetterLat(stem.charAt(stem.length() - 2)) == Roman.C
                            && form.form.length() == form.getAffix().morpheme
                                    .length()) {
                        res.add(new Object[] { stem + context, stem, partOfComp });
                    }
            }
        }

        /*
         * ------- DELETION + DELETION
         * 
         * La consonne finale du radical est supprimée. Si cette consonne finale
         * supprimée est précédée de deux voyelles, la dernière voyelle du
         * radical est supprimée.
         */

        else if (action1Type == Action.DELETION
                && action2Type == Action.DELETION) {
            // Puisque ce suffixe supprime la consonne précédente, le radical
            // actuel
            // doit se terminer par une voyelle.
            if (typeOfStemEndChar == Roman.V) {
                // La deuxième action indique qu'un radical terminant avec deux
                // voyelles
                // voit la dernière supprimée. Le radical actuel ne peut donc
                // avoir
                // qu'une seule voyelle.
                res.add(new Object[] { stem + context, stem, partOfComp });
                res.add(new Object[] { stem + "a" + context, stem, partOfComp });
                res.add(new Object[] { stem + "i" + context, stem, partOfComp });
                res.add(new Object[] { stem + "u" + context, stem, partOfComp });
            }
        } else
            ;

        // Avant de retourner 'res', on vérifie certaines choses, entre autres:
        // a. le radical ne peut pas se terminer par 2 consonnes
        //    (Sauf pour les racines démonstratives!!! exemple: tavv-ani)

        if (!affix.type.equals("tad"))
            for (int i = 0; i < res.size(); i++) {
                String stemres = (String) ((Object[]) res.get(i))[0];
                if (stemres.length() > 2
                        && Roman.typeOfLetterLat(stemres
                                .charAt(stemres.length() - 1)) == Roman.C
                        && Roman.typeOfLetterLat(stemres
                                .charAt(stemres.length() - 2)) == Roman.C)
                    res.remove(i--);
            }
        if (res.size() == 0)
            return null;
        else
            return (Object[][]) res.toArray(new Object[][] {});
    }

    
    /*
     * -------------------------RACINE----------------------------------------
     * 
     * Analyse d'un terme comme racine.
     */
    private static Vector analyzeRoot(String termICI, String termOrigICI, 
            String term, boolean isSyllabic,
            String word, Vector morphParts, Graph.State states[],
            Conditions preCond,
            String transitivity) {
        
        /*
         * Enlever le '*' à la fin du terme, s'il s'y trouve à la suite d'une
         * tentative pour trouver des analyses au cas où la consonne finale du
         * mot à analyser aurait été omise.
         */
        if (termOrigICI.endsWith("*"))
            termOrigICI = termOrigICI.substring(0,termOrigICI.length()-1);
        
        /*
         * À ce point-ci, nous sommes au début du terme. À cause de la
         * récursivité au point de branchement, le terme en question sera ce qui
         * précède tout affixe trouvé. Cela ira donc du mot entier à la racine
         * réelle, en passant par plusieurs termes intermédiaires.
         * 
         * On vérifie si cette partie initiale du mot est une racine connue.
         * 
         * Chercher le TERME dans les racines.
         */
        Vector lexs = null;
        Vector newCandidates = null;
        lexs = Lexicon.lookForBase(termICI, isSyllabic);                
        /*
         * Il est possible qu'une différence de prononciation dialectale se
         * produise dans un groupe de consonnes à la frontière de deux suffixes.
         * Il faut vérifier si le suffixe trouvé précédemment commence par une
         * consonne et si le candidat racine finit par une consonne et si ce
         * groupe de deux consonnes correspond à un autre groupe de consonnes.
         * On cherche aussi des groupes de consonnes équivalents à l'intérieur
         * de la racine candidate. Toutes les possibilités sont retenues.
         */
        newCandidates = Dialect.newRootCandidates(termICI);
        if (newCandidates != null)
            for (int k = 0; k < newCandidates.size(); k++) {
                Vector tr = Lexicon.lookForBase((String) newCandidates
                        .elementAt(k), isSyllabic);
                if (tr != null)
                    if (lexs == null)
                        lexs = (Vector) tr.clone();
                    else
                        lexs.addAll(tr);
            }
        Vector analyses = checkRoots(lexs,word,termOrigICI,morphParts,states,
                preCond,transitivity);

        /*
         * Vérifier la possibilité que la règle suivante s'applique: pour les
         * racines verbales, le redoublement de la dernière consonne
         * intervocalique et l'addition possible de 'q' à la fin du radical
         * exprime généralement le fait qu'une action débute.
         * 
         * Dans ce cas, on ajoute aux morphParts un morceau spécial correspondant
         * à cette règle.  Sa position sera la même que le dernier morceau trouvé, puisqu'il
         * ne correspond pas à un suite de caractères à la suite de la racine.
         */
        Vector analyses2 = new Vector();;
        
        // 13 mars 2006: on a décidé de ne pas appliquer ce processus aux 
        // racines et de plutôt ajouter à la table des racines toute racine
        // résultant de ce processus trouvée dans les dictionnaires, comme
        // ikummaq- < ikuma-
//        Vector v = new Vector();
//        v.add(termICI);
//        v.addAll(newCandidates);
//        Vector lexs2 = checkForDoubleConsonantInVerbalRoots(v);
//        Vector mcx = (Vector)morphParts.clone();
//        if (lexs2.size() != 0) {
//            if (morphParts.size() != 0) {
//                MorceauAffixe dernierMorceau = (MorceauAffixe)morphParts.elementAt(0);
//                MorceauAffixe mspecial = new MorceauAffixe.Inchoative(dernierMorceau.position);
//                
//                mcx.add(0,mspecial);
//            } else {
//                mcx.add(new MorceauAffixe.Inchoative(termICI.length()));
//            }
//        }
//
//        analyses2 = checkRoots(lexs2,word,termOrigICI,mcx,states,
//                condsSpecs,preCond,transitivity);
        
        Vector allAnalyses = new Vector();
        allAnalyses.addAll(analyses);
        allAnalyses.addAll(analyses2);
        return allAnalyses;
    }
    
    
    private static Vector checkRoots(Vector lexs, String word, String termOrigICI,
            Vector morphParts, Graph.State states[], Conditions preCond,
            String transitivity) {
        
        String keyStateIDs = "0";
        for (int i=0; i<states.length; i++)
        	keyStateIDs += "+"+states[i].id;

        Vector rootAnalyses = new Vector();
        char typeBase = 0;

        if (lexs == null) {
            // RACINE UNKNOWN !!!
            // Pour le moment, on ne fait rien. On ne fait que
            // créer un vecteur vide.
            lexs = new Vector();
        } 

        //-------------------------------------------
        // Pour chaque base possible du vecteur lexs:----------------
        //-------------------------------------------
        
        for (int ib = 0; ib < lexs.size(); ib++) {
            // Chaque élément de lexs est un ensemble Object []
            // {Integer,Base}.
            
            Base root = (Base) lexs.elementAt(ib);
            typeBase = root.type.charAt(0);
            
            if (typeBase == '?') {
                /*
                 * Si la racine est inconnue, on ajoute simplement une nouvelle
                 * décomposition à la liste des décompositions. (Note: ceci
                 * n'est pas effectué puisqu'on a mis en commentaire plus haut
                 * le traitement des racines inconnues.)
                 */
                Decomposition res = new Decomposition(word, new RootPartOfComposition(
                        termOrigICI, root, transitivity, null), morphParts
                        .toArray());
                rootAnalyses.add(res);
            } else {
                /*
                 * Si la racine est connue: il faut vérifier si le type de la
                 * racine correspond à un arc à partir de l'état actuel, et cet
                 * arc doit conduire à l'état final (aucun arc partant de cet
                 * état final). En principe, il ne devrait y avoir qu'un seul
                 * arc accepté puisqu'on est rendu à la racine et qu'une racine
                 * ne peut prendre qu'un seul arc.
                 */
                
                /*
                 * Il faut aussi vérifier que la finale de la base est une
                 * lettre valide: une voyelle, un k, un q, un t.
                 */
                //                          // *** Mis en quarantaine pour le moment ***
                //                                String motbase = racine.morpheme;
                //                                char dernierChar = motbase.charAt(motbase
                //                                        .length() - 1);
                //                                						if (dernierChar == 'a'
                //                                							|| dernierChar == 'i'
                //                                							|| dernierChar == 'u'
                //                                							|| dernierChar == 't'
                //                                							|| dernierChar == 'k'
                //                               							|| dernierChar == 'q') {
                
                /*
                 * Il faut aussi que les conditions spécifiques soient
                 * rencontrées. Par exemple, si le suffixe trouvé précédemment
                 * exige de suivre immédiatement un nom au cas datif, le suffixe
                 * ou la terminaison actuelle doit rencontrer cette contrainte.
                 */
                /*
                 * Vérifier si la transitivité imposée par le morphème trouvé
                 * précédemment sur le radical, i.e. sur le morphème qui le
                 * précède dans le mot (le candidat actuel) est respectée. Cette
                 * valeur de transitivité imposée par le morphème trouvé est
                 * indiquée dans le champ 'condTrans'.  Elle ne s'applique qu'aux
                 * racines.
                 */
                boolean accepted = true;
                Graph.Arc arcFollowed = null;
                Graph.Arc[] arcsFollowed = null;
                if ( (arcsFollowed=arcsSuivis(root,states,keyStateIDs)) !=null 
                		&& (arcFollowed=arcToZero(arcsFollowed)) != null
                		&& root.meetsConditions(preCond, morphParts)
                		&& (!root.type.equals("v") 
                				|| root.meetsTransitivityCondition(transitivity)
                			)
                	) 
                {
                    accepted = true;
                }
                else
                    accepted = false;
                
                
                if (accepted) {
                    /*
                     * Toutes les conditions ont été respectées. Créer une
                     * nouvelle décomposition avec cette racine et les morphParts
                     * trouvés jusqu'ici.
                     */
                	Graph.Arc arc = arcFollowed.copy();
                    RootPartOfComposition mr = new RootPartOfComposition(
                            termOrigICI, root, transitivity, arc);
                    Decomposition res = new Decomposition(word, mr, morphParts
                            .toArray());
                    rootAnalyses.add(res);
                }
            }
        } //-------------------------- for (ib ...) -------------------------
        
        return rootAnalyses;
    }
    //-----------------------------------------------

    private static Graph.Arc arcToZero(Graph.Arc[] arcsFollowed) {
        for (int i=0; i<arcsFollowed.length; i++)
            if (arcsFollowed[i].getDestinationState() == Graph.finalState) {
                return arcsFollowed[i];
            }
        return null;
    }
    
    
    /*
     * 1. Vérifier si ce suffixe est le même que le dernier suffixe trouvé
     * précédemment. Cela permet d'éliminer certaines analyses, entre autres,
     * celles qui retournent le suffixe "a" d'action de groupe deux fois
     * lorsqu'on a un double "a" dans le mot.
     */
    private static boolean sameAsNext(Morpheme morpheme, Vector partsAlreadyAnalyzed) {
        boolean res = true;
        if (partsAlreadyAnalyzed.size() != 0) {
            Affix affPrec = ((AffixPartOfComposition) partsAlreadyAnalyzed.elementAt(0)).getAffix();
            if (morpheme.id.equals(affPrec.id))
                res = false;
        }
        return res;
    }
    
    /*
     * 2. Vérifier si ce suffixe est à la même position dans le mot que le
     * suffixe trouvé précédemment (celui qui le suit dans le mot dans l'analyse
     * courante). Cela permet d'éliminer certaines analyses, entre autres,
     * celles où le suffixe suivant, à cause de son action de suppression,
     * ajoute les caractères supprimés au radical, lesquels caractères sont
     * interprétés comme suffixe. Or un suffixe ne peut logiquement supprimer un
     * autre suffixe.
     */
    private static boolean samePosition(int positionAffixInWord, 
            Vector partsAlreadyAnalyzed) {
        boolean res = true;
        if (partsAlreadyAnalyzed.size() != 0) {
            AffixPartOfComposition nextMorphpart = (AffixPartOfComposition) partsAlreadyAnalyzed
                .elementAt(0);
            if (nextMorphpart.getPosition() == positionAffixInWord) 
                res = false;       
        }
        return res;
    }

    
    /*
     * 3. Vérifier si ce suffixe est permis en ce moment. Il doit
     * correspondre à un des arcs partant de l'état actuel.
     */
    
    private static Hashtable arcsByMorpheme = new Hashtable();
    
    private static Graph.Arc[] arcsSuivis(Morpheme morpheme, Graph.State states[],
			String keyStateIDs) {
		Graph.Arc arcsFollowed[] = null;
		String keyMorphemeStateIDs = morpheme.id + ":" + keyStateIDs;
		Graph.Arc[] arcsFollowedByHash = (Graph.Arc[]) arcsByMorpheme
				.get(keyMorphemeStateIDs);
		if (arcsFollowedByHash == null) {
			Vector arcs = null;
			Vector arcsFollowedV = new Vector();
			for (int j = 0; j < states.length; j++) {
				arcs = states[j].verify(morpheme);
				arcsFollowedV.addAll(arcs);
			}
			if (arcsFollowedV.size() != 0) {
				arcsFollowed = (Graph.Arc[]) arcsFollowedV
						.toArray(new Graph.Arc[] {});
				arcsByMorpheme.put(keyMorphemeStateIDs, arcsFollowed);
			} 
		} else {
			arcsFollowed = arcsFollowedByHash;
		}
		return arcsFollowed;
	}
    
    

    /*
     * 6. Vérifier si le contexte est respecté. La forme candidate
     * trouvée est associée à un contexte de radical et à des actions.
     * On vérifie si le radical et la forme de l'affixe correspondent à
     * ce contexte et à ces actions. Si c'est le cas, on retourne les
     * résultats possibles:
     * 
     * a. radical sans les changements morphologiques causés par
     * l'affixe; b. un objet de classe MorceauAffixe contenant: 1. la
     * position de l'affixe dans le mot (la valeur de i); 2. un objet de
     * classe SurfaceFormOfAffix décrivant totalement l'affixe.
     */
    private static Object[][] agreeWithContextAndActions(String affixCandidateOrig,
            Affix affix, String stem, 
            int positionAffixInWord, SurfaceFormOfAffix form,
            boolean notResultingFromDialectalPhonologicalTransformation) {
        Object[][] stemAffs = null;
        boolean checkStartOfConsonantsGroup = true;
        /*
         * Si la forme du candidat affixe est le résultat de changements
         * phonologiques, et si ces changements impliquent la consonne initiale,
         * on ne vérifiera pas la possibilité de changements phonologiques parce
         * qu'on ne veut pas que le groupe de consonne soit vérifié à nouveau.
         */
        if (!notResultingFromDialectalPhonologicalTransformation) {
            if (Roman.isConsonant(form.form.charAt(0)) &&
                    Roman.isConsonant(affixCandidateOrig.charAt(0)) &&
                    form.form.charAt(0) != affixCandidateOrig.charAt(0) )
                checkStartOfConsonantsGroup = false;
        }
        String context = (String) form.context;
        Action action1 = form.action1;
        Action action2 = form.action2;
        stemAffs = validateContextActions(context, action1, action2,
                stem, positionAffixInWord, affix, form, false,
                checkStartOfConsonantsGroup,affixCandidateOrig);
        return stemAffs;
    }
    
    /*
     * Vérifier si les candidats racines qui ont une dernière consonne
     * intervocalique double et soit une voyelle, soit un 'q' à la fin,
     * correspondent à des racines verbales où cette consonne est simple et le
     * 'q' est absent ou présent.
     */
    private static Vector checkForDoubleConsonantInVerbalRoots(Vector rootCandidates) {
        Vector newLexs = new Vector();
        Vector newRootCandidates = new Vector();
        for (int i=0; i<rootCandidates.size(); i++) {
            String rootCandidate = (String)rootCandidates.elementAt(i);
            for (int j=rootCandidate.length()-2; j>0; j--) {
                if (Roman.isConsonant(rootCandidate.charAt(j))) {
                    if (rootCandidate.charAt(j-1)==rootCandidate.charAt(j) &&
                        (Roman.isVowel(rootCandidate.charAt(rootCandidate.length()-1)) ||
                                rootCandidate.charAt(rootCandidate.length()-1)=='q')) {
                        String newRootCandidate = rootCandidate.substring(0,j-1)+
                        rootCandidate.substring(j);
                        if (newRootCandidate.charAt(newRootCandidate.length()-1)=='q')
                            newRootCandidates.add(newRootCandidate.substring(0,newRootCandidate.length()-1));
                        newRootCandidates.add(newRootCandidate);
                    }
                    break;
                }
            }
        }
        for (int i=0; i<newRootCandidates.size(); i++) {
            Vector tr = Lexicon.lookForBase((String) newRootCandidates
                    .elementAt(i), false);
            if (tr != null)
                for (int j=0; j<tr.size(); j++)
                    if (((Base)tr.elementAt(j)).type.equals("v"))
                        newLexs.add(tr.elementAt(j));
        }
        return newLexs;
    }
}