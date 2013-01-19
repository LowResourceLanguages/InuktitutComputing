/*
 * Conseil national de recherche Canada 2004/ National Research Council Canada
 * 2004
 * 
 * Cr�� le / Created on 9-Sep-2004 par / by Benoit Farley
 *  
 */
package phonology;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.LinguisticDataAbstract;

import script.Orthography;
import script.Roman;

public class Dialect {

    /*
     * Il est � noter que les seuls cas o� la seconde consonne du groupe change
     * ont '&' comme seconde consonne; et il y a 'ts > tt'.
     * Arctic Quebec:  & > s
     * South Baffin: & > t dans les groupes de consonnes (South East)
     *                         & > s dans les groupes de consonnes (South West)
     *                         & > l ou s entre voyelles (South East)
     *                         & > s entre voyelles (South West)
     * t& > ts (AQ)
     * t& > tt (SE)
     * k& > ss : k& > ks (AQ) > ss (AQ)
     * k& > ts : k& > ks (SW) > ts (SW)
     * k& > kt (SE)
     * q& > qs
     * q& > qt
     * 
     * ts > tt
     */
    private static String[][] groups = new String[][] {
        // C: g, j, k, l, m, n, ng, p, q, r, s, t, v
        // labC > CC : p(b), v, m
        { "bl", "ll" }, { "bj", "jj" }, { "bg", "gg" }, { "bv", "vv" },
        { "pl", "ll" }, { "pk", "kk" }, { "pg", "gg" }, { "pv", "vv" }, { "pq", "qq" },
        { "ps", "ts"}, { "ps", "ss" }, 
        { "pt", "tt"}, // BF - il manquait ce groupe [20 fév. 2011]
        { "mN", "NN" }, {"mn", "nn"}, {"mp","pp"},
        // alvC > CC : t, s, &, l, n
        { "tp", "pp" }, { "tk", "kk" }, { "tj", "jj" }, { "ts", "ss" }, 
                { "ts", "tt" }, {"t&", "ts"}, {"t&", "tt"}, 
        { "&&", "tt" }, 
//                { "&&", "ts" },
        { "lv", "vv" },
        { "nN", "NN" }, {"nm","mm"},
        // velC > CC : k, g, ng
        { "kt", "tt" }, { "ks", "ss" }, { "kp", "pp" }, { "kv", "vv" }, { "ks", "ts" }, 
                { "k&", "ss" }, {"k&", "ts"}, {"k&", "kt"}, 
        { "gl", "ll" }, { "gv", "vv" }, 
        { "Nm", "mm" }, { "Nn", "nn" },
        // uvuC > CC : q, r
            { "qt", "tt" }, { "q&", "r&" }, {"qt", "rt"}, {"ql", "rl"}, {"qp", "rp"}, {"qs", "rs"},
                    { "q&","qs"}, {"q&", "qt"},
            { "rq", "qq" },
             };
    private static String[][] groups2 = new String[][] { { "it", "is" } };

    public static String [] getKeys() {
    	String keys[] = new String[groups.length * 2];
    	for (int i=0; i<groups.length; i++) {
    		int j = i*2;
    		keys[j] = groups[i][0];
    		keys[j+1] = groups[i][1];
    	}
    	return keys;
    }
  
    /**
     * @param l1
     * @param l2
     * @return
     */
    public static Vector equivalentGroups(char l1, char l2) {
        String group = new String(new char[] { l1, l2 });
        Vector terms = new Vector();
        for (int i = 0; i < groups.length; i++) {
            if (groups[i][0].equals(group)) {
                terms.add(groups[i][1]);
            }
            if (groups[i][1].equals(group)) {
                terms.add(groups[i][0]);
            }
        }
        if (terms.size() == 0)
            return null;
        else
            return terms;
    }
    
    /*
     * This method checks whether the initial of the candidate morpheme and the
     * final of the stem that precedes it form a cluster that has equivalent
     * clusters in other dialects. And it checks for internal equivalent
     * clusters.
     * 
     * It also checks for Schneider's Law of double consonants.
     * 
     * The results of each check are joined.
     */
    public static Vector newCandidates(String stem, String candidateMorpheme, String followingMorpheme) {
        Vector cands = new Vector(); // to hold the new candidates
        ArrayList candsAndChanges = new ArrayList();
        
        if (stem != null) {
            /*
             * Look after the initial consonant of the candidate morpheme. It might
             * form with the final consonant of the stem a consonant cluster that is
             * realized differently in other dialects, and new candidates with a
             * different initial consonant might occur.
             */ 
            char finalStem = stem.charAt(stem.length() - 1);
            char initialFollowingMorpheme = candidateMorpheme.charAt(0);
            if (Roman.typeOfLetterLat(initialFollowingMorpheme) == Roman.C) {
                String groupOrig = new String(new char[]{finalStem,initialFollowingMorpheme});
                Vector grps = Dialect.equivalentGroups(
                        groupOrig.charAt(0), groupOrig.charAt(1));
                if (grps != null) {
                    for (int j = 0; j < grps.size(); j++) {
                        String groupj = (String) grps.elementAt(j);
                        char c = groupj.charAt(1);
                        /*
                         * Replace the initial consonant of the candidate
                         * with the final consonant of the equivalent
                         * cluster, for each possible equivalent
                         * cluster.
                         */
                        String candStr = c + candidateMorpheme.substring(1);
                        if (!cands.contains(candStr)) {
                            cands.add(candStr);
                            ArrayList l = new ArrayList();
                            l.add(new PhonologicalTransformation(groupOrig,groupj,0));
                            candsAndChanges.add(new Object[]{candStr,l});
                        }
                    }
                }
            }
        }
        
        /* 
         * For each candidate found in the above part, check for internal equivalent 
         * clusters.  The initial morpheme is also checked.
         */
        Vector cands2 = new Vector();
        cands2.add(candidateMorpheme);
        cands2.addAll(cands);
        ArrayList candsAndChanges2 = new ArrayList();
        candsAndChanges2.add(new Object[]{candidateMorpheme,null});
        candsAndChanges2.addAll(candsAndChanges);
        for (int m = 0; m < cands2.size(); m++) {
            String candStr = (String) cands2.elementAt(m);
            Vector correspondingTerms = correspondingTerms(candStr);
            ArrayList correspTermsAndChanges =
                correspondingTermsEquivalentGroups(candStr,0);
            if (correspondingTerms != null) {
                /*
                 * For each term, add the change to the initial consonant, if any.
                 */
                ArrayList ltf = (ArrayList)((Object[])candsAndChanges2.get(m))[1];
                if (ltf != null) {
                    PhonologicalTransformation tf =
                        (PhonologicalTransformation) ltf.get(0);
                    for (int i=0; i<correspTermsAndChanges.size(); i++) {
                        ArrayList l = (ArrayList)((Object[])correspTermsAndChanges.get(i))[1];
                        l.add(tf);
                    }
                }
                for (int n = 0; n < correspondingTerms.size(); n++) {
                    String candN = (String) correspondingTerms.elementAt(n);
                    if (!cands.contains(candN)) {
                        cands.add(candN);
                        candsAndChanges.add(correspTermsAndChanges.get(n));
                    }
                }
            }
        }
        if (cands.size() == 0)
            return null;
        else {
            for (int i=0; i<cands.size(); i++)
                if (((String)cands.elementAt(i)).equals(candidateMorpheme)) {
                    cands.remove(i);
                    candsAndChanges.remove(i--);
                }
//            while (cands.removeElement(candidateMorpheme))
//                ;
        }

        // Schneider's Law
        Vector schCands = schneiderCandidates(stem, candidateMorpheme);
        for (int i=0; i<cands.size(); i++) {
            schCands.addAll(schneiderCandidates(stem,(String)cands.elementAt(i)));
            while(schCands.removeElement((String)cands.elementAt(i)));
        }
        cands.addAll(schCands);
        if (cands.size() == 0)
            return null;
        else
            while (cands.removeElement(candidateMorpheme))
                ;
        
        return cands;
    }
    
    public static Vector newRootCandidates(String rootICI) {
        Vector cands = new Vector(); // to hold the new candidates
        
        /*
         * Check for internal equivalent clusters in the root and add the 
         * corresponding terms to the candidates.
         */
            Vector correspondingTerms = correspondingTerms(rootICI);
            if (correspondingTerms != null)
                for (int n = 0; n < correspondingTerms.size(); n++) {
                    String candN = Orthography.orthographyICILat((String)correspondingTerms.elementAt(n));
                    if (!cands.contains(candN))
                        cands.add(candN);
                }
        if (cands.size() == 0)
            return null;
        else
            while (cands.removeElement(rootICI))
                ;

        // Schneider's Law
        Vector schCands = schneiderCandidates(null, rootICI);
        for (int i=0; i<cands.size(); i++) {
            schCands.addAll(schneiderCandidates(null,(String)cands.elementAt(i)));
            while(schCands.removeElement((String)cands.elementAt(i)));
        }
        cands.addAll(schCands); 
        if (cands.size()==0)
            return null;
        else
            while (cands.removeElement(rootICI))
                ;
        
        return cands;
    }
    
    static public Object[] schneiderStateAtEnd(String stem) {
        Vector cands = null;
        boolean doubleConsonants = false;
        int vcState;
        
        if (stem==null) {
            // For roots
            doubleConsonants = false;
            vcState = Roman.V;
            return new Object[]{new Boolean(doubleConsonants),
                    new Integer(vcState)};
        }
        
        // Check whether the stem's last group of consonants is single or double
        for (int i=stem.length()-1; i > 0; i--)
            if (Roman.isConsonant(stem.charAt(i))) {
                if (Roman.isConsonant(stem.charAt(i-1))) 
                    doubleConsonants = true;
                else
                    doubleConsonants = false;
                break;
            }
        // Set the vowel/consonant state at the end of the stem
        if (Roman.isConsonant(stem.charAt(stem.length()-1)))
            vcState = Roman.C;
        else
            vcState = Roman.V;
        return new Object[]{new Boolean(doubleConsonants),
                new Integer(vcState)};
    }


    // Not used yet.
//    public static void fillFinalRadInitAffHashSet() {
//        for (int i=0; i<groups.length; i++) {
//            Donnees.finalRadInitAffHashSet.add(groups[i][0]);
//            Donnees.finalRadInitAffHashSet.add(groups[i][1]);
//        }
//    }

    /*
     * 'terme' est un morph�me inuktitut en caract�res latins
     * dans l'orthographe simplifi�e.
     */
    private static Vector correspondingTerms(String term) {
        Vector termsGC = correspondingTermsEquivalentGroups(term);
        return termsGC;
    }

    public static Vector correspondingTermsEquivalentGroups(String term) {
        Vector terms = new Vector();
        int i;
        for (i = 0; i < term.length() - 1; i++) {
            Vector greqs = equivalentGroups(term.charAt(i), term
                    .charAt(i + 1));
            char l3;
            if (i==term.length()-2)
                l3 = (char)-1;
            else
                l3 = term.charAt(i+2);
            Vector greqs2 = equivalentGroups2(term.charAt(i),
                    term.charAt(i+1),l3);
            if (greqs2 != null)
                if (greqs != null)
                    greqs.addAll(greqs2);
                else
                    greqs = (Vector)greqs2.clone();
            
            if (greqs != null) {
                greqs.add(new String(new char[] { term.charAt(i),
                        term.charAt(i + 1) }));
                Vector remains = correspondingTermsEquivalentGroups(term
                        .substring(i + 2));
                for (int j = 0; j < greqs.size(); j++) {
                    String termTemp = term.substring(0, i) + (String)greqs.elementAt(j);
                    if (remains.size() > 0)
                        for (int k = 0; k < remains.size(); k++)
                            terms.add(termTemp
                                    + (String) remains.elementAt(k));
                    else
                        terms.add(termTemp);
                }
                break;
            }
        }
        if (i == term.length() - 1) {
            terms.add(term);
        }
        return terms;
    }


    public static ArrayList correspondingTermsEquivalentGroups(String term, int pos) {
        ArrayList termsAndAlterations = new ArrayList();
        int i;
        for (i = pos; i < term.length() - 1; i++) {
            String groupOfConsonants =
                new String(new char[]{term.charAt(i), term.charAt(i + 1)});
            Vector greqs = equivalentGroups(term.charAt(i), term
                    .charAt(i + 1));
            char l3;
            if (i==term.length()-2)
                l3 = (char)-1;
            else
                l3 = term.charAt(i+2);
            Vector greqs2 = equivalentGroups2(term.charAt(i),
                    term.charAt(i+1),l3);
            if (greqs2 != null)
                if (greqs != null)
                    greqs.addAll(greqs2);
                else
                    greqs = (Vector)greqs2.clone();
            
            if (greqs != null) {
                // Groupe de consonnes avec �quivalents trouv� � 'pos'
                greqs.add(groupOfConsonants);
                
                // Traiter le reste du mot pour chercher d'autres groupes.
                ArrayList remainsAndAlterations = 
                    correspondingTermsEquivalentGroups(term,i + 2);
                
                /*
                 * Pour chacun des groupes de consonnes �quivalents, former un mot
                 * avec chaque possibilit� retourn�e pour le reste du mot.
                 */
                for (int j = 0; j < greqs.size(); j++) {
                    String grp = (String)greqs.elementAt(j);
                    String termTemp = term.substring(pos, i) + grp;
                    PhonologicalTransformation tp =
                        new PhonologicalTransformation(groupOfConsonants,grp,i);
                    if (remainsAndAlterations.size() > 0)
                        for (int k = 0; k < remainsAndAlterations.size(); k++) {
                            String newTerm = termTemp + (String) ((Object[])remainsAndAlterations.get(k))[0];
                            ArrayList alterations = (ArrayList)((ArrayList) ((Object[])remainsAndAlterations.get(k))[1]).clone();
                            alterations.add(0,tp);
                            termsAndAlterations.add(new Object[]{newTerm,alterations});
                        }
                    else {
                        ArrayList alterations = new ArrayList();
                        alterations.add(tp);
                        termsAndAlterations.add(new Object[]{termTemp,alterations});
                    }
                }
                break;
            }
        }
        if (i == term.length() - 1) {
            termsAndAlterations.add(new Object[]{term.substring(pos),new ArrayList()});
        }
        return termsAndAlterations;
    }

 
    // Same thing as equivalentGroups, except that it has the
    // additional constraint that the group is followed by a vowel.
    // NOTE: this is a hack and it will have to be revised.
    private static Vector equivalentGroups2(char l1, char l2, char l3) {
        String group = new String(new char[] { l1, l2 });
        Vector terms = new Vector();
        if (l3 != (char) -1 && Roman.typeOfLetterLat(l3) == Roman.V) {
            for (int i = 0; i < groups2.length; i++) {
                if (groups2[i][0].equals(group)) {
                    terms.add(groups2[i][1]);
                }
                if (groups2[i][1].equals(group)) {
                    terms.add(groups2[i][0]);
                }
            }
        }
        if (terms.size() == 0)
            return null;
        else
            return terms;
    }
    
 
    
    /*
     * Schneider's law applies in Nunavik, where a word cannot have two
     * consecutive consonant clusters. The initial consonant of the second cluster
     * is deleted. This method returns a number of possible words corresponding
     * to the 'candidate' word assuming that Schneide's law has been applied to it.
     */
    public static Vector schneiderCandidates(String stem, String candidate) {
    	return schneiderCandidates(stem,candidate,'@');
    }
    
    private static Vector schneiderCandidates(String stem, String candidate, char mark) {
    	String markedCandidate = schneiderCandidatesToString(stem,candidate,mark);
        Vector cands = explode(markedCandidate);
//        for (int i=0; i<cands.size(); i++)
//            cands.setElementAt(
//                    Orthography.orthographyICILat((String)cands.elementAt(i)),i);
        return cands;
    }
    
    public static String schneiderCandidatesToString(String stem, String candidate, char mark) {
//      String candSimp = Orthography.simplifiedOrthographyLat(candidate);
        String candSimp = candidate;
        boolean doubleConsonants = false;
        int vcState;
        // Check whether the stem's last group of consonants is single or double
        Object x[] = schneiderStateAtEnd(stem);
        doubleConsonants = ((Boolean)x[0]).booleanValue();
        vcState = ((Integer)x[1]).intValue();
        /*
         * From the beginning of the candidate and forward, insert a mark
         * wherever a consonant is possibly missing according to Schneider's Law
         */
        String markedCandidate = markCandidate(candSimp, vcState, doubleConsonants, mark);
        return markedCandidate;
    }
    
    public static String markCandidate(String cand, int vcState, boolean doubleConsonants, char mark) {
    	String marked = "";
    	String str = cand;
    	if (doubleConsonants && vcState==0) str = "XXa"+str;
        Pattern p = Pattern.compile("(([^aiu][^aiu][aiu][aiu]?)([^aiu][aiu]))");
        Matcher m = p.matcher(str);
        int pos =0;
        while (m.find(pos)) {
        	marked += str.substring(pos,m.start(1));
        	marked += m.group(2);
        	marked += mark;
        	marked += m.group(3);
        	pos = m.end(1);
        }
        marked += str.substring(pos);
        marked = marked.replaceFirst("^XXa", "");
        return marked;
    }
        
    
//    static char cons[] = {'p', 't', 'k', 'g', 'm', 'n', 's', 'l', 'j', 'v',
//            'r', 'q', 'N', '&'};
    
    private static Vector explode(String s) {
        if (s.length()==0)
            return new Vector();
        else
            return explode2(s);
    }
    
    /*
     * Wherever there might be a deleted consonant, add a word with one of the
     * possible consonant at that place.
     */
    private static Vector explode2(String s) {
        Vector a = new Vector();
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i)=='@') {
                Vector grCons = (Vector)LinguisticDataAbstract.groupsOfConsonants.get(new Character(s.charAt(i+1)));
                for (int j=0; j<grCons.size(); j++)
                    a.addAll(explode(new String()+(String)grCons.elementAt(j)+s.substring(i+2)));
                break;
            }
            else
                sb.append(s.charAt(i));
        }
        if (a.size()==0)
            a.add("");
        String deb = sb.toString();
        for (int i=0; i<a.size(); i++)
            a.setElementAt(deb+(String)a.elementAt(i),i);
        return a;
    }
}