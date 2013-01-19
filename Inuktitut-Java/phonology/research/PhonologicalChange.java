/*
 * Conseil national de recherche Canada 2005/ National Research Council Canada
 * 2005
 * 
 * Cr�� le / Created on Apr 20, 2005 par / by Benoit Farley
 *  
 */
package phonology.research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import script.Orthography;


public class PhonologicalChange {

    static char alveolars[] = { 't', 's', '&', 'l', 'n' };

    static char labials[] = { 'p', 'b', 'v', 'm' };

    static char palatals[] = { 'j' };

    static char velars[] = { 'k', 'g', 'N' };

    static char uvulars[] = { 'q', 'r' };

    static String listOfDialects[] = { "aivilik", "northbaffin",
            "southeastbaffin", "southwestbaffin", "eastbaffin" };

    static Hashtable transfs = new Hashtable();

    static Hashtable dialects = new Hashtable();

    static {
        transfs.put("ts2tt", new Transformation[] { new Transformation("ts", "tt",
                "afp") });
        transfs.put("t&2&&", new Transformation[] { new Transformation("t&", "&&",
                "afp") });
        transfs.put("nr2rng", new Transformation[] { new Transformation("nr", "rN",
                "") });
        transfs.put("mr2rng", new Transformation[] { new Transformation("mr", "rN",
                "") });
        transfs.put("itV2isV", new Transformation[] {
                new Transformation("iti", "isi", "pal"),
                new Transformation("itu", "isu", "pal"),
                new Transformation("ita", "isa", "pal") });
        transfs.put("^sitV2tisV", new Transformation[] {
                new Transformation("\\Asiti", "\\Atisi", "pal"),
                new Transformation("\\Asitu", "\\Atisu", "pal"),
                new Transformation("\\Asita", "\\Atisa", "pal") });
        transfs.put("alvC2CC", new Transformation[] {
                new Transformation("tl", "ll", "apr"),
                new Transformation("tp", "pp", "apr"),
                new Transformation("lv", "vv", "apr"),
                new Transformation("nm", "mm", "apr"),
                new Transformation("tj", "jj", "apr"),
                new Transformation("tk", "kk", "apr"),
                new Transformation("lg", "gg", "apr"),
                new Transformation("tq", "qq", "apr"),
                new Transformation("lr", "rr", "apr"),
                new Transformation("jg", "gg", "apr") });
        transfs.put("ps2ss", new Transformation[] { new Transformation("ps", "ss",
                "apr+afr") });
        transfs.put("ps2ts", new Transformation[] { new Transformation("ps", "ts",
                "apr") });
        transfs.put("vg2gg", new Transformation[] { new Transformation("vg", "gg",
                "apr") });
        transfs.put("labC2CC", new Transformation[] {
                new Transformation("pq", "qq", "apr"),
                new Transformation("pt", "tt", "apr"),
                new Transformation("pl", "ll", "apr"),
                new Transformation("bl", "ll", "apr"),
                new Transformation("mn", "nn", "apr"),
                new Transformation("pv", "vv", "apr"),
                new Transformation("bv", "vv", "apr"),
                new Transformation("bj", "jj", "apr"),
                new Transformation("pj", "jj", "apr"),
                new Transformation("pk", "kk", "apr"),
                new Transformation("bg", "gg", "apr"),
                new Transformation("pg", "gg", "apr"),
                new Transformation("mN", "NN", "apr"),
                new Transformation("vg", "gg", "apr") });
        transfs.put("nasal$", new Transformation[] {
                new Transformation("t\\z", "n\\z", "nas"),
                new Transformation("p\\z", "m\\z", "nas"),
                new Transformation("k\\z", "N\\z", "nas"),
                new Transformation("q\\z", "r\\z", "nas") });
        transfs.put("^tVs2sVs", new Transformation[] {
                new Transformation("\\Atis", "\\Asis", "pal"),
                new Transformation("\\Atus", "\\Asus", "pal"),
                new Transformation("\\Atas", "\\Asas", "pal") });
        transfs.put("C&2Cs", new Transformation[] {
                new Transformation("p&", "ps", "af"),
                new Transformation("t&", "ts", "af"),
                new Transformation("k&", "ks", "af"),
                new Transformation("q&", "qs", "af"),
                new Transformation("&&", "ts", "af") });
        transfs.put("C&2Ct", new Transformation[] {
                new Transformation("p&", "pt", "af"),
                new Transformation("t&", "tt", "af"),
                new Transformation("k&", "kt", "af"),
                new Transformation("q&", "qt", "af"),
                new Transformation("&&", "tt", "af") });
        transfs.put("V&V2VsV", new Transformation[] {
                new Transformation("i&i", "isi", "af"),
                new Transformation("i&u", "isu", "af"),
                new Transformation("i&a", "isa", "af"),
                new Transformation("u&i", "usi", "af"),
                new Transformation("u&u", "usu", "af"),
                new Transformation("u&a", "usa", "af"),
                new Transformation("a&i", "asi", "af"),
                new Transformation("a&u", "asu", "af"),
                new Transformation("a&a", "asa", "af") });
        transfs.put("V&V2VlV", new Transformation[] {
                new Transformation("i&i", "ili", "am"),
                new Transformation("i&u", "ilu", "am"),
                new Transformation("i&a", "ila", "am"),
                new Transformation("u&i", "uli", "am"),
                new Transformation("u&u", "ulu", "am"),
                new Transformation("u&a", "ula", "am"),
                new Transformation("a&i", "ali", "am"),
                new Transformation("a&u", "alu", "am"),
                new Transformation("a&a", "ala", "am") });
        transfs.put("V&V2VslV", new Transformation[] {
                new Transformation("i&i", "isi", "am"),
                new Transformation("i&u", "isu", "am"),
                new Transformation("i&a", "isa", "am"),
                new Transformation("u&i", "usi", "am"),
                new Transformation("u&u", "usu", "am"),
                new Transformation("u&a", "usa", "am"),
                new Transformation("a&i", "asi", "am"),
                new Transformation("a&u", "asu", "am"),
                new Transformation("a&a", "asa", "am"),
                new Transformation("i&i", "ili", "am"),
                new Transformation("i&u", "ilu", "am"),
                new Transformation("i&a", "ila", "am"),
                new Transformation("u&i", "uli", "am"),
                new Transformation("u&u", "ulu", "am"),
                new Transformation("u&a", "ula", "am"),
                new Transformation("a&i", "ali", "am"),
                new Transformation("a&u", "alu", "am"),
                new Transformation("a&a", "ala", "am") });
        transfs.put("velC2CC", new Transformation[] {
                new Transformation("kt", "tt", "apr"),
                new Transformation("ks", "ss", "apr+afr"),
                new Transformation("gl", "ll", "apr"),
                new Transformation("Nn", "nn", "apr"),
                new Transformation("kp", "pp", "apr"),
                new Transformation("gv", "vv", "apr"),
                new Transformation("Nm", "mm", "apr"),
                new Transformation("gj", "jj", "apr") });
        transfs.put("ks2ts", new Transformation[] { new Transformation("ks", "ts",
                "apr") });

        // A 'true' as second argument to DialectalChange indicates that the
        // change may or may not take place in the dialect.
        dialects.put("aivilik", 
                new DialectalChangesSet("aivilik",
                        null,
                        new DialectalChange[] {
                        	new DialectalChange("ts2tt", true),
                        	new DialectalChange("t&2&&"), 
                        	new DialectalChange("nr2rng"),
                        	new DialectalChange("alvC2CC"),
                        	new DialectalChange("vg2gg") 
                }));
        dialects.put("northbaffin",
                new DialectalChangesSet("northbaffin",
                        new DialectalChange[] {
                        	// The two next changes are not truly optional. They are
                        	// identified here as optional because since it is not
                        	// possible to tell whether the 'i' of '^sit' and the 'i'
                        	// of 'itV' are "strong" i's or not, which is the condition
                        	// for these changes taking place, we have to allow for
                        	// both possibilities.
                        	new DialectalChange("^sitV2tisV", true),
                        	new DialectalChange("itV2isV", true),
                        	new DialectalChange("nasal$", true)}, 
                		new DialectalChange[] {
                        	new DialectalChange("ts2tt"), 
                        	new DialectalChange("t&2&&"),
                        	new DialectalChange("nr2rng"), 
                        	new DialectalChange("alvC2CC"),
                        	new DialectalChange("ps2ss"),
                        	new DialectalChange("mr2rng"), 
                        	new DialectalChange("labC2CC") 
                }));
        dialects.put("southeastbaffin",
                new DialectalChangesSet("southeastbaffin",
                        new DialectalChange[] {
                        	new DialectalChange("^tVs2sVs"),
                        	new DialectalChange("itV2isV", true),
                        	new DialectalChange("C&2Ct"), 
                        	new DialectalChange("V&V2VslV"),
                        	new DialectalChange("nasal$", true) },
                        new DialectalChange[] { 
                        	new DialectalChange("nr2rng"),
                        	new DialectalChange("alvC2CC"),
                        	new DialectalChange("ps2ts"),
                        	new DialectalChange("mr2rng"),
                        	new DialectalChange("labC2CC"),
                        	new DialectalChange("velC2CC") })); // *
        dialects.put("eastbaffin",
                new DialectalChangesSet("eastbaffin",
                        new DialectalChange[] {
                        	new DialectalChange("^sitV2tisV", true),
                        	new DialectalChange("itV2isV", true),
                        	new DialectalChange("C&2Ct"), 
                        	new DialectalChange("V&V2VsV"),
                        	new DialectalChange("V&V2VlV", true),
                        	new DialectalChange("nasal$", true) }, 
                        new DialectalChange[] {
                        	new DialectalChange("nr2rng"), 
                        	new DialectalChange("alvC2CC"), 
                        	new DialectalChange("ps2ts"),
                        	new DialectalChange("mr2rng"),
                        	new DialectalChange("labC2CC"),
                        	new DialectalChange("velC2CC") })); // *
        dialects.put("southwestbaffin",
                new DialectalChangesSet("southwestbaffin",
                        new DialectalChange[] {
                        	new DialectalChange("C&2Cs"),
                        	new DialectalChange("V&V2VsV") },
                        new DialectalChange[] {
                        	new DialectalChange("nr2rng"), 
                        	new DialectalChange("alvC2CC"), 
                        	new DialectalChange("ps2ts"), 
                        	new DialectalChange("mr2rng"),
                        	new DialectalChange("labC2CC"),
                        	new DialectalChange("ks2ts"), // ? Arctic Quebec
                        	new DialectalChange("velC2CC") }));  // *
    }

    
    
    // Cette m�thode retourne toutes les formes possibles du mot soumis
    // dans le dialecte indiqu�. Le mot doit �tre dans le dialecte le
    // plus "conservateur" possible.
    public static String[] alternatives(String word, String dialect) {
        Vector matches = new Vector();
        Hashtable hm = new Hashtable();
        String m = new String(Orthography.simplifiedOrthographyLat(word));
        DialectalChangesSet set = (DialectalChangesSet) dialects.get(dialect);
        Object dialectalChanges[] = (Object[]) dialects.get(dialect);
        for (int iDialChng = 0; iDialChng < dialectalChanges.length; iDialChng++) {
            boolean opt = false;
            DialectalChange dialectalChange = (DialectalChange) dialectalChanges[iDialChng];
            if (dialectalChange.optional) {
                opt = true;
            }
            Transformation[] transformations = (Transformation[]) transfs
                    .get(dialectalChange.name);
            for (int iTransf = 0; iTransf < transformations.length; iTransf++) {
                Transformation transformation = transformations[iTransf];
                String pat1str = transformation.from;
                String pat2str = transformation.to;
                // Reconnaissance de la forme 'from'
                Pattern pat = Pattern.compile(pat1str);
                Matcher mat = pat.matcher(m);
                int imatch = 0;
                while (imatch < m.length())
                    if (mat.find(imatch)) {
                        Integer pos = new Integer(mat.start());
                        Match mtch = new Match(pos, dialectalChange.name,
                                transformation, new Boolean(opt));
                        Vector v = null;
                        if (hm.containsKey(pos))
                            v = (Vector) hm.get(pos);
                        else
                            v = new Vector();
                        if (opt)
                            v.add(null);
                        v.add(mtch);
                        hm.put(pos, v);
                        imatch = mat.end();
                    } else
                        imatch = m.length();
            }
        }
        // Placer les 'matches' par ordre croissant de la position dans le mot.
        Integer pos[] = (Integer[]) hm.keySet().toArray(new Integer[] {});
        Arrays.sort(pos);
        Vector v = new Vector();
        for (int i = 0; i < pos.length; i++) {
            v.add((Vector) hm.get(pos[i]));
        }
        // Distribuer les 'matches'.
        List ls = distribuer(v);
        //
        for (int i = 0; i < ls.size(); i++) {
            List chgs = (List) ls.get(i);
            String mcopy = new String(m);
            int prevTo = -1;
            for (int ichg = 0; ichg < chgs.size(); ichg++) {
                Match chg = (Match) chgs.get(ichg);
                if (chg == null) {
                } else if (chg.position.intValue() < prevTo) {
                } else {
                    int posChg = chg.position.intValue();
                    String from = new String(((String) chg.changes.from));
                    String to = new String(((String) chg.changes.to));
                    if (from.startsWith("\\A")) {
                        from = from.substring(2);
                    } else if (from.charAt(from.length() - 2) == '\\') {
                        from = from.substring(0, from.length() - 2);
                    }
                    prevTo = posChg + from.length();
                    if (to.startsWith("\\A")) {
                        to = to.substring(2);
                    } else if (to.charAt(to.length() - 2) == '\\') {
                        to = to.substring(0, to.length() - 2);
                    }
                    mcopy = mcopy.substring(0, posChg) + to
                            + mcopy.substring(posChg + to.length());
                }
            }
            mcopy = Orthography.orthographyICILat(mcopy);
            //            if (!mcopy.equals(m) && !matches.contains(mcopy))
            if (!matches.contains(mcopy))
                matches.add(mcopy);
        }

        return (String[]) matches.toArray(new String[] {});
    }

    //

    static public String dialectOf(String word) {
        // Look for any part of the word that may be a change,
        // i.e. the "to" (2nd element) of a Transformation in
        // any dialect
        for (int iDialect = 0; iDialect < listOfDialects.length; iDialect++) {
            Object dialectalChanges[] = (Object[]) dialects
                    .get(listOfDialects[iDialect]);
            for (int iDialectalChange = 0; iDialectalChange < dialectalChanges.length; iDialectalChange++) {
                Transformation transforms[] = (Transformation[]) transfs.get(dialectalChanges[iDialectalChange]);
                for (int iTransf = 0; iTransf < transforms.length; iTransf++) {
                    String from = transforms[iTransf].from;
                    String to = transforms[iTransf].to;
                }
            }
        }
        return null;
    }

    static public String[] alternativesAll(String word) {
        Vector v = new Vector();
        for (int i = 0; i < listOfDialects.length; i++) {
            String alts[] = alternatives(word, listOfDialects[i]);
            for (int j = 0; j < alts.length; j++)
                if (!v.contains(alts[j]))
                    v.add(alts[j]);
        }
        return (String[]) v.toArray(new String[] {});
    }

    // [ [a1 a2] [b1 b2] [c1 c2] ]
    //   [a1 a2] [ [b1 b2] [c1 c2] ]
    //              [b1 b2] [ [c1 c2] ]
    //                         [c1 c2] [ ]
    //                       < [ [c1] [c2] ]
    static List distribuer(List v2) {
        List newList = new ArrayList();

        List v = (List) v2.get(0);
        List subv2 = (List) v2.subList(1, v2.size());
        List lst2 = null;
        if (subv2.size() != 0)
            lst2 = distribuer(subv2);
        for (int iv = 0; iv < v.size(); iv++) {
            if (lst2 == null) {
                List lscopy = new ArrayList();
                lscopy.add(v.get(iv));
                newList.add(lscopy);
            } else
                for (int il = 0; il < lst2.size(); il++) {
                    List lscopy = (ArrayList) ((ArrayList) lst2.get(il))
                            .clone();
                    lscopy.add(0, v.get(iv));
                    newList.add(lscopy);
                }
        }

        return newList;
    }


    static class Transformation {
        public String from;

        public String to;

        public String method;

        public Transformation(String from, String to, String method) {
            this.from = from;
            this.to = to;
            this.method = method;
        }
    }

    static class DialectalChange {
        public String name;

        public boolean optional;

        public String dialect;

        public DialectalChange(String name, boolean optional) {
            this.name = name;
            this.optional = optional;
        }

        public DialectalChange(String name) {
            this.name = name;
            optional = false;
        }
        
        public Transformation[] getTransformations() {
            return (Transformation[])transfs.get(name);
        }
    }

}


class DialectalChangesSet {
    
    public String dialect;
    public PhonologicalChange.DialectalChange firstTransformations[];
    public PhonologicalChange.DialectalChange secondTransformations[];
    
    public DialectalChangesSet(String dialect,
            PhonologicalChange.DialectalChange[] firstTransformations,
            PhonologicalChange.DialectalChange[] secondTransformations) {
        this.dialect = dialect;
        this.firstTransformations = firstTransformations;
        this.secondTransformations = secondTransformations;
    }
}


class Match implements Comparable {
    public Integer position;

    String operation;

    public PhonologicalChange.Transformation changes;

    public Boolean optional;

    /**
     * @param oper
     * @param integer
     * @param objects
     * @param boolean1
     */
    public Match(Integer pos, String oper, PhonologicalChange.Transformation chgs,
            Boolean opt) {
        position = pos;
        operation = oper;
        changes = chgs;
        optional = opt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object arg0) {
        Match obj = (Match) arg0;
        String x = "?????????? ????????? ???????";
        return position.compareTo(obj.position);
    }

}