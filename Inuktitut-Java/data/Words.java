package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Element;
import javax.swing.text.ElementIterator;

import lib.html;
import morph.Decomposition;
import morph.AffixPartOfComposition;
import morph.RootPartOfComposition;
import morph.MorphInuk;
import fonts.Font;
import utilities.Debugging;
import utilities.Inuktitut;
import utilities.Text;
import utilities1.Util;
import documents.NRC_HTMLDocument;
import script.Roman;
import script.TransCoder;
import data.LinguisticDataAbstract;

public class Words {

    //static OutputStreamWriter out;
    static boolean syllabic = true;
    static String inuktitutDisplayFontByDefault = "Pigiarniq";
    static String inuktitutDisplayFont = null;
    static String fontRoman = "\"Times New Roman\"";
    static String fontOfQuery = null;
    static String fontOfQueryArg = null;
    static String lang = "en";
    static String lang_default = "en";
    static boolean HTML_OUTPUT = true;
    static String UTF8_BOM = "\uFEFF";

    public static void getDef(String[] args, PrintStream out) {
    	getDef(args, out, HTML_OUTPUT);
    }
    
    public static void getDef(String[] args, PrintStream out, boolean htmlOutput) {
    	// Pour fins de test
//    	out.print("Nb. arguments: "+args.length+"<br>");
//        for (int i=0; i<args.length; i++) {
//            out.print("args["+i+"]: "+args[i]+"<br>");
//            out.flush();
//        }
//    	if (true) return;
    	// Pour fins de test
    	
        StringBuffer outputHTML = null;
        //        try {
        String input = null;
        String file = null;
        String cacheName = null;

        // Arguments d'appel
        file = Util.getArgument(args,"f");
        lang = Util.getArgument(args,"l");
        if (lang==null)
            lang = Util.getArgument(args, "lang");
        if (lang==null)
        	lang = lang_default;
        /*
         * L'argument 'inputType' détermine la font d'affichage. Il est envoyé
         * par la requête faite à partir de la page de l'analyseur morphologique où
         * l'usager peut choisir la font. Si l'appel est faite à partir d'un lien, cet
         * argument n'est pas envoyé; l'affichage sera en Unicode.
         */
        outputHTML = new StringBuffer();
        if (!htmlOutput)
        	outputHTML.append(UTF8_BOM);

        inuktitutDisplayFont = inuktitutDisplayFontByDefault;
        fontOfQuery = Util.getArgument(args, "inputType");
        if (fontOfQuery != null && fontOfQuery.equals("other"))
            fontOfQuery = null;
        if (fontOfQuery != null) {
            inuktitutDisplayFont = fontOfQuery;
            fontOfQueryArg = "'"+fontOfQuery+"'";
        } else {
            fontOfQueryArg = null;
        }
        cacheName = Util.getArgument(args,"cache");
        input = Util.getArgument(args,"m");
        if (input==null)
            input = Util.getArgument(args, "query");
        if (input != null)
            input = input.trim();

        /* 
         * If the input argument line contains the word 'TranslitPage' it means
         * that the word to decompose comes from a transliterated page.  The
         * file argument is not necessary then, because it is assumed that the
         * word is in roman alphabet.  Set the file argument to null.
         */
        String iag = null;
        if ((iag=Util.getArgument(args, "#input_argument_string"))!=null && iag.contains("TranslitPage"))
            file = null;
        
        boolean procedeYesNo = false;
        String reason = null;
        String defWord = null;
        String defWordForDisplay = null;
        Text txt = new Text(input);

        // Écriture des scripts
//        outputHTML = writeScriptsJSP();

        //---------------------------------------------------------
        // Il faut d�terminer ici si le mot � d�finir est en caract�res
        // syllabiques ou pas.
        if (txt.isNull()) {
            procedeYesNo = false;
            reason = (lang.equals("en")) ? "No selected word."
                    : "Aucun mot sélectionné à définir.";

        } else if (txt.containsUnicodeInuktitut()) {
            // Le mot s�lectionn� est en inuktitut Unicode.
            procedeYesNo = true;
            syllabic = true;
            defWord = input;
            defWordForDisplay = defWord;

        } else if (txt.containsUnicode()) {
            // Le mot s�lectionn� est en Unicode, mais pas en inuktitut.
            procedeYesNo = false;
            reason = (lang.equals("en")) ? "This is not an Inuktitut word."
                    : "Ce n'est pas un mot inuktitut.";

        } else {
            // Le mot sélectionné n'est pas en Unicode. Il peut s'agir:
            //   a. de texte Inuktitut syllabique avec font 'legacy'
            //   b. de texte Inuktitut en caractères latins
            //   c. de texte non Inuktitut en caractères latins
            // Note: on suppose qu'il s'agit de texte Inuktitut

            // Si le mot a été sélectionné dans un fichier, on le recherche
            // dans le fichier pour déterminer si la font est une font
            // inuktitut. Si le mot n'a pas été sélectionné dans un fichier,
            // on présume qu'il est inuktitut en caractères latins.

            if ( file == null ) {
            	if (fontOfQuery == null) {
            		// On suppose le mot en inuktitut et en caractères latins.
            		procedeYesNo = true;
            		syllabic = false;
            		defWord = input;
            		defWordForDisplay = defWord;
            	} else if (Font.isLegacy(fontOfQuery)) {
            		// Le font a été spécifié dans les arguments, et c'est un font
            		// inuktitut archaïque: convertir le mot en unicode
            		defWord = TransCoder.legacyToUnicode(input, fontOfQuery);
            		/* Comme le mot à définir vient d'un lien, donc d'une page internet, on veut
            		 * l'afficher tel quel.
            		 */
            		defWordForDisplay = input;
            		syllabic = true;
            		procedeYesNo = true;
            	}
            } else {
                // On télécharge le fichier HTML pour le parser et déterminer
                // quel est le font utilisé pour le mot à définir.
                Object res[] = determineWord(file, input, cacheName);
                defWord = (String) res[0]; // retourné en Unicode par detMot
                defWordForDisplay = defWord;
                procedeYesNo = ((Boolean) res[1]).booleanValue();
                if (res[2] != null) {
                	syllabic = ((Boolean) res[2]).booleanValue();
                }
                reason = (String) res[3];
            }
        }

        // Affichage du mot au haut de la page.
        if (htmlOutput) {
        	outputHTML.append("<span style=\"font-family:"+inuktitutDisplayFont+";color:green;\">");
        	outputHTML.append("<span style=\"font-weight:bold;\">");
        	outputHTML.append(defWordForDisplay);
        	outputHTML.append("</span>");
        	outputHTML.append("</span>");
        	out.print(outputHTML.toString());
        }

        //---------------------------------------------------------

        if (procedeYesNo) {
            // ON PEUT PROC�DER � LA D�COMPOSITION DU MOT.
            String defWordLat;
            // On convertit les caract�res syllabiques en caract�res latins.
            if (syllabic) {
                defWordLat = TransCoder.unicodeToRoman(defWord);
            } else
                defWordLat = defWord;
            // D�composition du mot � d�finir.
            // Si le programme a �t� appel� pour la d�composition de la
            // base,
            // auquel cas la valeur de 'eliminerSubordata' sera 'false',
            // on doit appeler 'decompose/3' avec le troisi�me argument
            // 'boolean decomposeBase' plac� � la valeur 'true', et vice
            // versa.
            Decomposition decs[] = null;
            //				Vector decomposition =
            //					MorphInuk.decompose(
            //						defWordLat,
            //						false,
            //						eliminerSubordata ? false : true);
            decs = MorphInuk.decomposeWord(defWordLat);
            // Mettre dans l'ordre selon les r�gles suivantes:
            //   a. racines les plus courtes
            //   b. number minimum de suffixes/terminaisons
            //				decs[] = new Decomposition[] {
            //				};
            //				decs = (Decomposition[]) decomposition.toArray(decs);
            //				Arrays.sort(decs);

            Decomposition[]  decomps = null;
            if (decs.length != 0) {
                // Sélection des décompositions.
                decomps = selectDecompositions(decs,
                        //eliminerSubordata);
                        false);
                decomps = mergeNounEndings(decomps);
            }
            
            // Affichage des décompositions retenues.
            
            if (decomps == null) {
        		// Pas de décomposition
            	if (htmlOutput) {
            		if (decs.length != 0) {
            			if (lang.equals("en"))
            				out.print("<p>None of the " + decs.length
            						+ " decompositions has been retained.</p>");
            			else
            				out.print("<p>Aucune des " + decs.length
            						+ " décompositions n'a été retenue.</p>");
            		} else {
            			if (lang.equals("en"))
            				out.print("<p>No decomposition has been found.</p>");
            			else
            				out.print("<p>Aucune décomposition n'a été trouvée.</p>");
            		}
            	} else {
            	}
            } else
            	// Des décompositions ont été trouvées: les afficher.
            	for (int ndcs = 0; ndcs < decomps.length; ndcs++) {
            		String display = null;
            		if (decomps[ndcs] != null) {
            			if (htmlOutput)
            				display = composeDisplay(decomps[ndcs], syllabic);
            			else
            				display = decomps[ndcs].toStr2().concat("\n");
            			out.print(display);
            		}
            	}
        } else {
            // ON NE PEUT PAS PROC�DER � LA D�COMPOSITION DU MOT.
            String reasonIntro = (lang.equals("en")) ? "Impossible to proceed: "
                    : "Impossible de procéder: ";
            if (htmlOutput) {
            	outputHTML.append(reasonIntro);
            	outputHTML.append(reason);
            	out.print(outputHTML.toString());
            }
        }
        if (htmlOutput)
        	out.print("</p>");
        out.flush();

        //        } catch (Exception e) {
        //        }
    }


    // D�termination du mot (inuktitut? syllabique? etc.) � partir
    // de l'analyse du fichier.
    // Le fichier HTML devra probablement �tre pars�. Pour cela,
    // il faut qu'il soit charg�. On peut �viter le chargement et le
    // parsage du fichier chaque fois qu'une requ�te dans cette page
    // est faite en tenant une cache qui se rappelle le codage du
    // fichier.
    //
    // Pour l'instant, cette cache est un simple fichier contenant une
    // table d'url avec le codage.
    private static Object[] determineWord(String file, String input, String cacheName) {
        String defWord = null;
        boolean procedeYesNo = false;
        boolean syllabic = false;
        String reason = null;
        String text = null;
        boolean trouve = false;
        ElementIterator it;
        javax.swing.text.Element elem = null;
        File cache = null;

        // V�rifier la cache. Si cette cache contient le nom de l'url o�
        // se trouve le mot � d�finir, c'est que ce fichier est d�j� connu
        // pour contenir de l'inuktitut cod� avec une font Legacy.
        if (cacheName != null)
            cache = new File(cacheName);
        if (cache != null && cache.exists()) {
            BufferedReader in = null;
            boolean eof = false;
            try {
                in = new BufferedReader(new FileReader(cacheName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (!eof) {
                String line = null;
                try {
                    line = in.readLine();
                    if (line == null)
                        eof = true;
                    else {
                        StringTokenizer st = new StringTokenizer(line, "\t");
                        String fileName = st.nextToken();
                        String fontName = st.nextToken();
                        if (file.startsWith(fileName)) {
                            defWord = TransCoder.legacyToUnicode(input, fontName);
                            syllabic = true;
                            procedeYesNo = true;
                            in.close();
                            // RETURN
                            return new Object[] { defWord,
                                    new Boolean(procedeYesNo),
                                    new Boolean(syllabic), reason };
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // L'url n'est pas dans la cache.
        // Retrouver le mot s�lectionn� dans la page HTML.

        // D'abord, parser le fichier HTML et en faire un document.
        NRC_HTMLDocument doc = null;
        try {
            doc = new NRC_HTMLDocument(file);
        } catch (Exception e1) {
        	if (doc != null)
        		doc.close();
            return new Object[] {
                    null,
                    new Boolean(false),
                    null,
                    e1.getMessage().equals("not html") ? (lang.equals("en") ? "This URL is not an HTML page"
                            : "Cet URL n'est pas une page HTML.")
                            : e1.getMessage() };
        }
        // Ensuite, chercher le mot sélectionné parmi les éléments
        // de texte.
        Object elementWithInputWord = doc.findWord(input);

        if (elementWithInputWord != null) {
            // Mot trouv� parmi les �l�ments de texte.
            // Il peut s'agir de:
            //  a. texte Inuktitut syllabique avec font 'legacy'
            //  b. texte Inuktitut non-syllabique en caract�res latins.
            //  c. texte non Inuktitut en caract�res latins.
            NRC_HTMLDocument.TexteHTML textHTML;
            textHTML = doc.texteEnInuktitut((Element)elementWithInputWord);
            defWord = input;
            if (textHTML != null) {
                // L'�l�ment contient du syllabique inuktitut:
                // il faut d�terminer si le texte s�lectionn� est
                // syllabique legacy ou pas.
                Vector morphParts = textHTML.getMorphParts();

                if (textHTML.getCodage() == Roman.UNICODE) {
                    // Caract�res latins ou UNICODE.
                	syllabic = false;
                    for (int im = 0; im < morphParts.size(); im++) {
                        Inuktitut.MorceauTexte morphPart = (Inuktitut.MorceauTexte) morphParts
                                .get(im);
                        if (morphPart.getTexte().equals(input)) {
                        	syllabic = morphPart.isSyllabic();
                            break;
                        }
                    }
                } else {
                    // font legacy. Premier (et unique) morceau.
                    // On le convertit en Unicode.
                    // On place aussi l'information dans la cache.
                    String font = ((Inuktitut.MorceauTexte) (morphParts.get(0)))
                            .getPolice();
                    defWord = TransCoder.legacyToUnicode(input, font);
                    syllabic = true;
                    if (cacheName != null) {
                        try {
                            BufferedWriter out = new BufferedWriter(
                                    new FileWriter(cacheName, true));
                            out.newLine();
                            out.write(file + "\t" + font);
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                // L'�l�ment ne contient pas de syllabique inuktitut
            	syllabic = false;
                defWord = input;
            }
            procedeYesNo = true;
        } else {
            procedeYesNo = false;
            reason = (lang.equals("en")) ? "The word cannot be found in the file."
                    : "Le mot n'a pas été trouvé dans le fichier.";
        }
        doc.close();
        return new Object[] { defWord, new Boolean(procedeYesNo),
                new Boolean(syllabic), reason };
    }

    //---------------------------------------------------------------------------------------------
    // Parmi les d�compositions, on �limine celles dont la base est
    // une base inconnue.
    private static Decomposition[] selectDecompositions(
            Decomposition decompositions[], boolean eliminateSubordata) {
        Debugging.mess("selectDecompositions/1", 1, "> decompositions("
                + decompositions.length + ")");
        Vector decompsKnownBases;
        Vector decomps;
        // Elimination des d�compositions dont la base est inconnue.
        decompsKnownBases = new Vector();
        for (int i = 0; i < decompositions.length; i++) {
            Decomposition dec = decompositions[i];
            RootPartOfComposition base = dec.getRootMorphpart();
            Base root = (Base) base.getRoot();
            Debugging.mess("selectDecompositions/1", 1, "racine= " + root);
            if (root.isKnown()) {
                decompsKnownBases.add(dec);
            }
        }
        // Recherche des d�compositions dont la base est compos�e, et
        // �limination des d�compositions
        // subordonn�es.
        if (decompsKnownBases.size() != 0)
            if (eliminateSubordata)
                decomps = eliminateSubordata(decompsKnownBases);
            else
                decomps = decompsKnownBases;
        else
            decomps = null;
        Debugging.mess("selectDecompositions/1", 1, "<");
        if (decomps != null) {
            Decomposition decs[] = new Decomposition[] {};
            decs = (Decomposition[]) decomps.toArray(decs);
            return decs;
        } else
            return null;
    }

    private static Vector eliminateSubordata(Vector decompsKnownBases) {
        Vector decomps = (Vector) decompsKnownBases.clone();
        Debugging.mess("eliminateSubordata/1", 1, "decomps.size() = "
                + decomps.size());
        int i = 0;
        while (i < decomps.size()) {
            Decomposition di = (Decomposition) decomps.elementAt(i);
            Object[] suffsI = (Object[]) di.getMorphParts();
            int j = i + 1;
            while (j < decomps.size()) {
                Decomposition dj = (Decomposition) decomps.elementAt(j);
                Object[] suffsJ = (Object[]) dj.getMorphParts();
                Object[] suffsA;
                Object[] suffsB;
                if (suffsI.length != suffsJ.length) {
                    if (suffsI.length < suffsJ.length) {
                        suffsA = suffsI;
                        suffsB = suffsJ;
                    } else {
                        suffsA = suffsJ;
                        suffsB = suffsI;
                    }
                    // V�rifier si suffsA est un sous-ensemble final de suffsB.
                    int kA = suffsA.length - 1;
                    int kB = suffsB.length - 1;
                    boolean goOn = true;
                    while (goOn && kA > -1) {
                        SurfaceFormOfAffix fA = ((AffixPartOfComposition) suffsA[kA--]).getForm();
                        SurfaceFormOfAffix fB = ((AffixPartOfComposition) suffsB[kB--]).getForm();
                        Affix morphemeA = fA.getAffix();
                        Affix morphemeB = fB.getAffix();
                        Debugging.mess("eliminateSubordata/1", 1,
                                "  morphemeA=" + morphemeA + "\n  morphemeB="
                                        + morphemeB);
                        // V�rifier si morphemeA = morphemeB, i.e.
                        // m�me morpheme, no, type
                        if (!morphemeA.morpheme.equals(morphemeB.morpheme)
                                || !morphemeA.type.equals(morphemeB.type))
                        	goOn = false;
                    }
                    if (kA == -1) {
                        // suffsA est un sous-ensemble final de suffsB.
                        if (suffsI.length < suffsJ.length) {
                            // On enl�ve B, i.e. J. On n'incr�mente pas j.
                            decomps.remove(j);
                        } else {
                            // On enl�ve B, i.e. I. On place j de sorte qu'on
                            // recommence avec un nouveau i. Comme i sera
                            // incr�ment�
                            // de 1, on diminue i de 1 de sorte que la prochaine
                            // it�ration
                            // sur i ait la m�me valeur que maintenant.
                            decomps.remove(i);
                            j = decomps.size();
                            i -= 1;
                        }
                    } else {
                        // suffsA n'est pas un sous-ensemble final de suffsB.
                        // On continue avec le prochain J.
                        j += 1;
                    }
                } else {
                    // M�me number de suffixes. On continue avec le prochain j.
                    j += 1;
                }
            }
            // On continue avec le prochain i.
            i += 1;
        }
        if (decomps.size() == 0)
            return null;
        else
            return decomps;
    }

    //---------------------------------------------------------------------------------------------

    // Addition d'un bouton 'checkbox' � c�t� de chaque d�composition,
    // que l'usager peut placer pour indiquer la validation de la
    // d�composition.
    //	<form action="#" method="get">
    //	<input type = "checkbox" name = "decomp" value="???"
    //	onClick = "valider(???);"><br>
    //	</form>
    private static String composeDisplay(Decomposition decomp,
            boolean isSyllabic) {
        Debugging.mess("composeDisplay/2", 1, ">" + decomp);
        RootPartOfComposition morphpartBase = decomp.getRootMorphpart();
        Base stem = (Base) morphpartBase.getRoot();
        String type = stem.type;
        String funct = "";
        StringBuffer output = new StringBuffer();
        StringBuffer word = new StringBuffer();

        output.append("<br><br>");
        output.append("<table border=1><thead>");
        output.append("<tr style=\"background-color:#ffffcc\">");
        output.append("<td colspan=2>");
        output.append(
        //			"<span style=\"color:red\">"
                "<font color=red>");
        output.append(lang.equals("en") ? "Root" : "Racine");
        output.append(
        //			"</span>"
                "</font>");
        output.append(
        //			"<span style=\"color:black\">"
                "<font color=black>");
        output.append("&amp;");
        output.append(
        //			"</span>"
                "</font>");
        output.append(
        //			"<span style=\"color:green\">"
                "<font color=green>");
        output.append(lang.equals("en") ? "affixes" : "affixes");
        output.append(
        //			"</span>"
                "</font>");
        output.append("</td><td>");
        output.append(lang.equals("en") ? "Meaning" : "Signification");
        output.append("</td></tr></thead>");
        
        output.append("<tbody>");
        output.append("<tr><td>");
        // Premi�re cellule: forme de surface de la racine
        output.append("<font face="+inuktitutDisplayFont+" color=red>");
        output.append("<b>");
        /*
         * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
         * d'origine.
         */
        if (fontOfQuery==null)
            output.append(isSyllabic ? TransCoder.romanToUnicode(morphpartBase
                    .getTerm()) : morphpartBase.getTerm());
        else
            output.append(isSyllabic ? TransCoder.romanToLegacy(morphpartBase
                    .getTerm(),inuktitutDisplayFont) : morphpartBase.getTerm());
        output.append("</b>");
        output.append("</font>");
        output.append("</td>");
        // Deuxi�me cellule: morph�me de la racine
        output.append("<td>");
        output.append("<a href=\"javascript:appelerDescriptionRacine(");
        output.append("'"+stem.id+"',"+fontOfQueryArg);
        output.append(")\">");
        output.append("<font face="+inuktitutDisplayFont+" color=red>");
        output.append("<b>");
        /*
         * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
         * d'origine.
         */
        if (fontOfQuery==null)
            output.append(isSyllabic ? TransCoder.romanToUnicode(stem.morpheme) : stem.morpheme);
        else
            output.append(isSyllabic ? TransCoder.romanToLegacy(stem.morpheme,inuktitutDisplayFont) : stem.morpheme);
        output.append("</b>");
        output.append("</font>");
        output.append("</td>");
        // Troisi�me cellule: sens
        output.append("<td>");
        output.append("<font face="+fontRoman+" color=blue>");
        output.append("<b>");
        String meaning = lang.equals("en") ? stem.englishMeaning : stem.frenchMeaning;
        if (stem.isTransitiveVerb()) {
            meaning = stem.getTransitiveMeaning(lang);
            if (morphpartBase.getTransitivity() != null)
                if (morphpartBase.getTransitivity().equals("t"))
                    meaning = stem.getTransitiveMeaning(lang);
                else
                {
                    String pass = stem.getPassiveMeaning(lang);
                    String refl = stem.getReflexiveMeaning(lang);
                    meaning = "";
                    if (pass != null)
                        meaning = (lang.equals("en")?"(passive) ":"(passif) ")+pass;
                    if (refl != null)
                        meaning += " "+(lang.equals("en")?"(reflexive) ":"(réfléchi) ")+refl;
                }
            else
                ;
        }
        output.append(meaning);
        output.append("</b>");
        output.append("</font>");
        output.append("</td></tr>");

        /*
         * Partie 'racine' de la d�composition multicolore du mot pr�sent�e sous
         * le tableau de chaque d�composition.
         */
        word.append("<font face="+inuktitutDisplayFont+">");
//        word.append("<b>");
        word.append("<font color=red>"); // on commence en rouge
        /*
         * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
         * d'origine.
         */
        if (fontOfQuery==null)
            word.append(isSyllabic ? TransCoder.romanToUnicode(morphpartBase
                    .getTerm()) : morphpartBase.getTerm());
        else
            word.append(isSyllabic ? TransCoder.romanToLegacy(morphpartBase
                    .getTerm(),inuktitutDisplayFont) : morphpartBase.getTerm());
        word.append("</font>");

        boolean color = true; // pour le changement de couleur des affixes
        boolean reflexive = false;
        Object[] morphPartsInuk = decomp.getMorphParts();
        
        // CHAQUE MORCEAU AFFIXE.
        // Attention: le dernier morceau peut �tre une fusion de morph�mes avec
        // la m�me forme de surface.
        for (int i = 0; i < morphPartsInuk.length; i++) {
            AffixPartOfComposition mi = (AffixPartOfComposition) morphPartsInuk[i];
            if (mi.getReflexive())
                reflexive = mi.getReflexive();

            AffixPartOfComposition[] multipleMorphpart = mi.getMultipleMorphparts();

            // rang�e du tableau
            output.append("<tr>");
            
            // premi�re cellule: forme de surface
            if (multipleMorphpart != null) {
                output.append("<td rowspan="+multipleMorphpart.length+">");
            } else {
                output.append("<td>");
            }
            output.append("<font face="+inuktitutDisplayFont+" color=green>");
//            output.append("<b>");
            String term = mi.getTerm();
            if (term.endsWith("*"))
                term = term.substring(0,term.length()-1);
            /*
             * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
             * d'origine.
             */
            if (fontOfQuery==null)
                output.append(isSyllabic ? TransCoder.romanToUnicode(term) : term);
            else
                output.append(isSyllabic ? TransCoder.romanToLegacy(term,inuktitutDisplayFont) : term);
//            output.append("</b>");
            output.append("</font>");
            output.append("</td>");

            if (multipleMorphpart != null) {
                for (int j=0; j<multipleMorphpart.length; j++) {
                    if (j>0)
                        output.append("<tr>");
                    // deuxi�me cellule: morph�me                    
                    output.append("<td>");
                    AffixPartOfComposition dmi = (AffixPartOfComposition)multipleMorphpart[j];
                    SurfaceFormOfAffix fa = dmi.getForm();
                    Affix aff = (Affix) fa.getAffix();
                    type = fa.type;
                    funct = aff.function;
                    if (type.equals("sn") || type.equals("sv")) {
                        output.append("<a href=\"javascript:appelerDescriptionSuffixe(");
                        output.append("'"+fa.uniqueId+"',"+fontOfQueryArg);
                        output.append(")\">");
                    }
                    output.append("<font face="+inuktitutDisplayFont+" color=green>");
//                    output.append("<b>");
                    /*
                     * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
                     * d'origine.
                     */
                    if (fontOfQuery==null)
                        output.append(isSyllabic ? TransCoder.romanToUnicode(aff.morpheme) : aff.morpheme);
                    else
                        output.append(isSyllabic ? TransCoder.romanToLegacy(aff.morpheme,inuktitutDisplayFont) : aff.morpheme);
//                    output.append("</b>");
                    output.append("</font>");
                    output.append("</a>"); // fermeture du <a ...> de ahrefCallDescriptionOfSuffix
                    output.append("</td>");
                    // troisi�me cellule: sens
                    output.append("<td>");
                    output.append("<font face="+fontRoman+">");
                    output.append(lang.equals("en") ? aff.englishMeaning : aff.frenchMeaning);
                    output.append("</td>");
                    if (j>0)
                        output.append("</tr>");
                }
            } else {
                // deuxi�me cellule: morph�me
                output.append("<td>");                
                SurfaceFormOfAffix fa = mi.getForm();
                Affix aff = (Affix) fa.getAffix();
                type = fa.type;
                funct = aff.function;
                if (type.equals("sn") || type.equals("sv")) {
                    output.append("<a href=\"javascript:appelerDescriptionSuffixe(");
                    output.append("'"+fa.uniqueId+"',"+fontOfQueryArg);
                    output.append(")\">");
                }
                output.append("<font face="+inuktitutDisplayFont+" color=green>");
//                output.append("<b>");
                /*
                 * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
                 * d'origine.
                 */
                if (fontOfQuery==null)
                    output.append(isSyllabic ? TransCoder.romanToUnicode(aff.morpheme) : aff.morpheme);
                else
                    output.append(isSyllabic ? TransCoder.romanToLegacy(aff.morpheme,inuktitutDisplayFont) : aff.morpheme);
//                output.append("</b>");
                output.append("</font>");
                output.append("</a>"); // fermeture du <a ...> de ahrefCallDescriptionOfSuffix
                output.append("</td>");
                // troisi�me cellule: sens
                output.append("<td>");
                output.append("<font face=\"Times New Roman\">");
                output.append(lang.equals("en") ? aff.englishMeaning : aff.frenchMeaning);
                output.append("</td>");
            }

            
            output.append("</tr>");

            /*
             * Partie 'affixe' de la d�composition multicolore du mot pr�sent�e sous
             * le tableau de chaque d�composition.
             */
            word.append("<font color=");
            word.append(color ? "blue" : "green");
            word.append(">");
            /*
             * Si l'appel provient d'un lien, afficher en Unicoce; sinon, dans la font
             * d'origine.
             */
            if (fontOfQuery==null)
                word.append(isSyllabic ? TransCoder.romanToUnicode(term) : term);
            else
                word.append(isSyllabic ? TransCoder.romanToLegacy(term,inuktitutDisplayFont) : term);
            word.append("</font>");
            color = color ? false : true;
        }
        output.append("</tbody></table>");

//        word.append("</b>");
        word.append("</font>");
        word.append(" ");
        word.append("<font face="+fontRoman+"><i>");
        word.append("(");
        /*
         * D�terminer le type - verbale ou nominal - du mot, par le dernier
         * suffixe. Il n'est pas tenu compte pour le moment des suffixes de
         * queue.
          */
        if (type.equals("tv") || type.equals("v")
                || (type.equals("sv") && funct.equals("vv"))
                || (type.equals("sn") && funct.equals("nv")))
            word.append(lang.equals("en") ? "verb" : "verbe");
        else if (type.equals("tn") || type.equals("n")
                || (type.equals("sv") && funct.equals("vn"))
                || (type.equals("sn") && funct.equals("nn")))
            word.append(lang.equals("en") ? "noun" : "nom");
        else if (type.equals("a"))
            word.append(lang.equals("en") ? "adverb" : "adverbe");
        else if (type.equals("rad") || type.equals("tad") || type.equals("ad"))
            word.append(lang.equals("en") ? "demonstrative adverb" : "adverbe démonstratif");
        else if (type.equals("rpd") || type.equals("tpd") || type.equals("pd"))
            word.append(lang.equals("en") ? "demonstrative pronoun" : "pronom démonstratif");
        else if (type.equals("e"))
            word.append(lang.equals("en") ? "expression or exclamation" : "expression ou exclamation");
        else if (type.equals("c"))
            word.append(lang.equals("en") ? "conjunction" : "conjonction");
        else if (type.equals("p"))
            word.append(lang.equals("en") ? "pronoun" : "pronom");

        if (reflexive)
            word.append(", "
                    + ((lang.equals("en")) ? "reflexive action"
                            : "action réflexive"));
        word.append(")");
        word.append(
        //			"</span>"
                "</i></font>");
        output.append("");
        output.append(word);

        return output.toString();
    }

    private static StringBuffer writeScriptsJSP() {
        StringBuffer outputHTML = new StringBuffer();
        outputHTML.append(html.scriptDescRootJSP(lang,fontOfQuery));
        outputHTML.append(html.scriptDescSufJSP(lang,fontOfQuery));
        outputHTML.append(html.scriptDecWordJSP(lang,fontOfQuery));
        return outputHTML;
    }
    
    static Decomposition [] mergeNounEndings(Decomposition [] decomps) {
        Vector v = new Vector();
        Hashtable hv = new Hashtable();
        Pattern decPat = Pattern.compile("(\\x7B.+?\\x7D)+");
        Pattern signPat = Pattern.compile("\\x7B(.+?):(.+?)\\x7D");
        Hashtable formsHT = new Hashtable();
        // Vérifier chaque décomposition
        for (int i = 0; i < decomps.length; i++) {
			// Dernier morceau de la décomposition
			AffixPartOfComposition dern = decomps[i].getLastMorphpart();
			String form;
			String decSign = decomps[i].toStr2();
			String stemSign;
			if (dern == null) {
				form = "null";
				stemSign = decSign;
			} else {
				Matcher decSignMat = decPat.matcher(decSign);
				decSignMat.matches();
				int posStartLast = decSignMat.start(1);
				stemSign = decSign.substring(0, posStartLast);
				String lastSign = decSign.substring(posStartLast);
				Matcher lastSignMat = signPat.matcher(lastSign);
				lastSignMat.matches();
				form = lastSignMat.group(1);
			}
				Vector vs;
				if (formsHT.get(form) == null)
					vs = new Vector();
				else
					vs = (Vector) formsHT.get(form);
				vs.add(new Object[] { decomps[i], dern, stemSign,
						new Integer(i) });
				formsHT.put(form, vs);
		}
        /*
		 * Pour chaque forme du dernier morceau:
		 */
        for (Enumeration e = formsHT.keys(); e.hasMoreElements();) {
            Object k = e.nextElement();
            Vector val = (Vector)formsHT.get(k);
            if (val.size()==1) {
                v.add((Decomposition)((Object[])val.elementAt(0))[0]);
                hv.put((Integer)((Object[])val.elementAt(0))[3],
                        (Decomposition)((Object[])val.elementAt(0))[0]);
            }
            else {
                Hashtable ht = new Hashtable();
                for (int j=0; j<val.size(); j++) {
                    Object[] o = (Object[])val.elementAt(j);
                    Decomposition dec = (Decomposition)o[0];
                    AffixPartOfComposition lastMorphpart = (AffixPartOfComposition)o[1];
                    String stemSign = (String)o[2];
                    Integer decNb = (Integer)o[3];
                    if (ht.get(stemSign)!=null) {
                        Object[] oo = (Object[])ht.get(stemSign);
                        Vector vLast = (Vector)oo[1];
                        vLast.add(lastMorphpart);
                        oo[1]=vLast;
                        ht.put(stemSign,oo);
                    } else {
                        Vector vLast = new Vector();
                        vLast.add(lastMorphpart);
                        ht.put(stemSign,new Object[]{dec,vLast,decNb});
                    }
                }
                for (Enumeration f = ht.keys(); f.hasMoreElements();) {
                    Object kf = f.nextElement();
                    Object [] o = (Object [])ht.get(kf);
                    Decomposition dec = (Decomposition)o[0];
                    Vector vLast = (Vector)o[1];
                    Integer decNb = (Integer)o[2];
                    Object[] parts = dec.getMorphParts();
                    AffixPartOfComposition dmc = (AffixPartOfComposition)parts[parts.length-1];
                    dmc.setMultipleMorphparts(vLast);
                    parts[parts.length-1] = dmc;
                    dec.setMorphParts(parts);
                    v.add(dec);
                    hv.put((Integer)decNb,dec);
                }
            }
        }
        Set hvKeys = hv.keySet();
        Integer [] intHvKeys = (Integer[])hvKeys.toArray(new Integer[]{});
        Arrays.sort(intHvKeys);
        Decomposition [] decomps2 = new Decomposition[intHvKeys.length];
        for (int i=0; i<decomps2.length; i++)
            decomps2[i] = (Decomposition)hv.get(intHvKeys[i]);
        return decomps2;
//        return decomps;
    }


}
