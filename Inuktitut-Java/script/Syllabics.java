/*
 * Conseil national de recherche Canada 2004/ National Research Council Canada
 * 2004
 * 
 * Créé le / Created on Dec 8, 2004 par / by Benoit Farley
 *  
 */
package script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.lang.Character;
import java.util.Enumeration;


public class Syllabics {

    // N = ng X = nng
    
    public static Hashtable sylXunicode = new Hashtable();
    public static Hashtable unicodeXsyl = new Hashtable();
    

    public static class Short {

        public static Hashtable unicodeXsyl = new Hashtable();

        static {
            unicodeXsyl.put(new Character('\u1403'), "i");
            unicodeXsyl.put(new Character('\u1431'), "pi");
            unicodeXsyl.put(new Character('\u144E'), "ti");
            unicodeXsyl.put(new Character('\u146D'), "ki");
            unicodeXsyl.put(new Character('\u148B'), "gi");
            unicodeXsyl.put(new Character('\u14A5'), "mi");
            unicodeXsyl.put(new Character('\u14C2'), "ni");
            unicodeXsyl.put(new Character('\u14EF'), "si");
            unicodeXsyl.put(new Character('\u14D5'), "li");
            unicodeXsyl.put(new Character('\u1528'), "ji");
            unicodeXsyl.put(new Character('\u1555'), "vi");
            unicodeXsyl.put(new Character('\u1546'), "ri");
            unicodeXsyl.put(new Character('\u157F'), "qi");
            unicodeXsyl.put(new Character('\u158F'), "Ni");
            unicodeXsyl.put(new Character('\u1671'), "Xi");
            unicodeXsyl.put(new Character('\u15A0'), "&i");
            unicodeXsyl.put(new Character('\u1405'), "u");
            unicodeXsyl.put(new Character('\u1433'), "pu");
            unicodeXsyl.put(new Character('\u1450'), "tu");
            unicodeXsyl.put(new Character('\u146F'), "ku");
            unicodeXsyl.put(new Character('\u148D'), "gu");
            unicodeXsyl.put(new Character('\u14A7'), "mu");
            unicodeXsyl.put(new Character('\u14C4'), "nu");
            unicodeXsyl.put(new Character('\u14F1'), "su");
            unicodeXsyl.put(new Character('\u14D7'), "lu");
            unicodeXsyl.put(new Character('\u152A'), "ju");
            unicodeXsyl.put(new Character('\u1557'), "vu");
            unicodeXsyl.put(new Character('\u1548'), "ru");
            unicodeXsyl.put(new Character('\u1581'), "qu");
            unicodeXsyl.put(new Character('\u1591'), "Nu");
            unicodeXsyl.put(new Character('\u1673'), "Xu");
            unicodeXsyl.put(new Character('\u15A2'), "&u");
            unicodeXsyl.put(new Character('\u140A'), "a");
            unicodeXsyl.put(new Character('\u1438'), "pa");
            unicodeXsyl.put(new Character('\u1455'), "ta");
            unicodeXsyl.put(new Character('\u1472'), "ka");
            unicodeXsyl.put(new Character('\u1490'), "ga");
            unicodeXsyl.put(new Character('\u14AA'), "ma");
            unicodeXsyl.put(new Character('\u14C7'), "na");
            unicodeXsyl.put(new Character('\u14F4'), "sa");
            unicodeXsyl.put(new Character('\u14DA'), "la");
            unicodeXsyl.put(new Character('\u152D'), "ja");
            unicodeXsyl.put(new Character('\u1559'), "va");
            unicodeXsyl.put(new Character('\u154B'), "ra");
            unicodeXsyl.put(new Character('\u1583'), "qa");
            unicodeXsyl.put(new Character('\u1593'), "Na");
            unicodeXsyl.put(new Character('\u1675'), "Xa");
            unicodeXsyl.put(new Character('\u15A4'), "&a");
        }
    }

    public static class Long {

        static public Hashtable unicodeXsyl = new Hashtable();

        static {
            unicodeXsyl.put(new Character('\u1404'), "ii");
            unicodeXsyl.put(new Character('\u1432'), "pii");
            unicodeXsyl.put(new Character('\u144F'), "tii");
            unicodeXsyl.put(new Character('\u146E'), "kii");
            unicodeXsyl.put(new Character('\u148C'), "gii");
            unicodeXsyl.put(new Character('\u14A6'), "mii");
            unicodeXsyl.put(new Character('\u14C3'), "nii");
            unicodeXsyl.put(new Character('\u14F0'), "sii");
            unicodeXsyl.put(new Character('\u14D6'), "lii");
            unicodeXsyl.put(new Character('\u1529'), "jii");
            unicodeXsyl.put(new Character('\u1556'), "vii");
            unicodeXsyl.put(new Character('\u1547'), "rii");
            unicodeXsyl.put(new Character('\u1580'), "qii");
            unicodeXsyl.put(new Character('\u1590'), "Nii");
            unicodeXsyl.put(new Character('\u1672'), "Xii");
            unicodeXsyl.put(new Character('\u15A1'), "&ii");
            unicodeXsyl.put(new Character('\u1406'), "uu");
            unicodeXsyl.put(new Character('\u1434'), "puu");
            unicodeXsyl.put(new Character('\u1451'), "tuu");
            unicodeXsyl.put(new Character('\u1470'), "kuu");
            unicodeXsyl.put(new Character('\u148E'), "guu");
            unicodeXsyl.put(new Character('\u14A8'), "muu");
            unicodeXsyl.put(new Character('\u14C5'), "nuu");
            unicodeXsyl.put(new Character('\u14F2'), "suu");
            unicodeXsyl.put(new Character('\u14D8'), "luu");
            unicodeXsyl.put(new Character('\u152B'), "juu");
            unicodeXsyl.put(new Character('\u1558'), "vuu");
            unicodeXsyl.put(new Character('\u1549'), "ruu");
            unicodeXsyl.put(new Character('\u1582'), "quu");
            unicodeXsyl.put(new Character('\u1592'), "Nuu");
            unicodeXsyl.put(new Character('\u1674'), "Xuu");
            unicodeXsyl.put(new Character('\u15A3'), "&uu");
            unicodeXsyl.put(new Character('\u140B'), "aa");
            unicodeXsyl.put(new Character('\u1439'), "paa");
            unicodeXsyl.put(new Character('\u1456'), "taa");
            unicodeXsyl.put(new Character('\u1473'), "kaa");
            unicodeXsyl.put(new Character('\u1491'), "gaa");
            unicodeXsyl.put(new Character('\u14AB'), "maa");
            unicodeXsyl.put(new Character('\u14C8'), "naa");
            unicodeXsyl.put(new Character('\u14F5'), "saa");
            unicodeXsyl.put(new Character('\u14DB'), "laa");
            unicodeXsyl.put(new Character('\u152E'), "jaa");
            unicodeXsyl.put(new Character('\u155A'), "vaa");
            unicodeXsyl.put(new Character('\u154C'), "raa");
            unicodeXsyl.put(new Character('\u1584'), "qaa");
            unicodeXsyl.put(new Character('\u1594'), "Naa");
            unicodeXsyl.put(new Character('\u1676'), "Xaa");
            unicodeXsyl.put(new Character('\u15A5'), "&aa");
        }
    }

    public static class Final {

        static public Hashtable unicodeXsyl = new Hashtable();

        static {
            unicodeXsyl.put(new Character('\u1449'), "p");
            unicodeXsyl.put(new Character('\u1466'), "t");
            unicodeXsyl.put(new Character('\u1483'), "k");
            unicodeXsyl.put(new Character('\u14A1'), "g");
            unicodeXsyl.put(new Character('\u14BB'), "m");
            unicodeXsyl.put(new Character('\u14D0'), "n");
            unicodeXsyl.put(new Character('\u1505'), "s");
            unicodeXsyl.put(new Character('\u14EA'), "l");
            unicodeXsyl.put(new Character('\u153E'), "j");
            unicodeXsyl.put(new Character('\u155D'), "v");
            unicodeXsyl.put(new Character('\u1550'), "r");
            unicodeXsyl.put(new Character('\u1585'), "q");
            unicodeXsyl.put(new Character('\u1595'), "N");
            unicodeXsyl.put(new Character('\u1596'), "X");
            unicodeXsyl.put(new Character('\u15A6'), "&");
            unicodeXsyl.put(new Character('\u157C'), "h");
            unicodeXsyl.put(new Character('\u15AF'), "b");
        }
    }
    
    private static void makeSylXunicode(Hashtable ht) {
        for (Enumeration e = ht.keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            String value = (String)ht.get(key);
            sylXunicode.put(value,(Character)key);
        }
    }


    private static void makeUnicodeXsyl(Hashtable ht) {
        for (Enumeration e = ht.keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            String value = (String)ht.get(key);
            unicodeXsyl.put((Character)key,value);
        }
    }
    
    public static boolean isInuktitutCharacter(char character) {
        return unicodeXsyl.containsKey(new Character(character));
    }
    
    public static boolean isInuktitutCharacter(Character character) {
        return unicodeXsyl.containsKey(character);
    }

    public static String getSyl(Character character) {
        return (String)unicodeXsyl.get(character);
    }
    
    public static String getSyl(char character) {
        return (String)unicodeXsyl.get(new Character(character));
    }

    public static Character getCharacter(String syl) {
        return (Character)sylXunicode.get(syl);
    }
    
    public static boolean allInuktitut(String word) {
        char chars[] = word.toCharArray();
        for (int i=0; i<chars.length; i++)
            if (!isInuktitutCharacter(chars[i]))
                return false;
        return true;
    }
    
    public static boolean containsInuktitut(String word) {
        char chars[] = word.toCharArray();
        for (int i=0; i<chars.length; i++)
            if (isInuktitutCharacter(chars[i]))
                return true;
        return false;
    }
    

    
    static {
        makeSylXunicode(Short.unicodeXsyl);
        makeSylXunicode(Long.unicodeXsyl);
        makeSylXunicode(Final.unicodeXsyl);
        makeUnicodeXsyl(Short.unicodeXsyl);
        makeUnicodeXsyl(Long.unicodeXsyl);
        makeUnicodeXsyl(Final.unicodeXsyl);
    }
    
    static String [][] syllabicsToRomanICI = {
        {"\u1403", "i"},
        {"\u1431", "pi"},
        {"\u144E", "ti"},
        {"\u146D", "ki"},
        {"\u148B", "gi"},
        {"\u14A5", "mi"},
        {"\u14C2", "ni"},
        {"\u14EF", "si"},
        {"\u14D5", "li"},
        {"\u1528", "ji"},
        {"\u1555", "vi"},
        {"\u1546", "ri"},
        {"\u157F", "qi"},
        {"\u1585\u146D", "qqi"},
        {"\u158F", "ngi"},
        {"\u1671", "nngi"},
        {"\u158F", "Ni"},
        {"\u1671", "Xi"},
        {"\u15A0", "&i"},
        {"\u1405", "u"},
        {"\u1433", "pu"},
        {"\u1450", "tu"},
        {"\u146F", "ku"},
        {"\u148D", "gu"},
        {"\u14A7", "mu"},
        {"\u14C4", "nu"},
        {"\u14F1", "su"},
        {"\u14D7", "lu"},
        {"\u152A", "ju"},
        {"\u1557", "vu"},
        {"\u1548", "ru"},
        {"\u1581", "qu"},
        {"\u1585\u146F", "qqu"},
        {"\u1591", "ngu"},
        {"\u1673", "nngu"},
        {"\u1591", "Nu"},
        {"\u1673", "Xu"},
        {"\u15A2", "&u"},
        {"\u140A", "a"},
        {"\u1438", "pa"},
        {"\u1455", "ta"},
        {"\u1472", "ka"},
        {"\u1490", "ga"},
        {"\u14AA", "ma"},
        {"\u14C7", "na"},
        {"\u14F4", "sa"},
        {"\u14DA", "la"},
        {"\u152D", "ja"},
        {"\u1559", "va"},
        {"\u154B", "ra"},
        {"\u1583", "qa"},
        {"\u1585\u1472", "qqa"},
        {"\u1593", "nga"},
        {"\u1675", "nnga"},
        {"\u1593", "Na"},
        {"\u1675", "Xa"},
        {"\u15A4", "&a"},
        {"\u1404", "ii"},
        {"\u1432", "pii"},
        {"\u144F", "tii"},
        {"\u146E", "kii"},
        {"\u148C", "gii"},
        {"\u14A6", "mii"},
        {"\u14C3", "nii"},
        {"\u14F0", "sii"},
        {"\u14D6", "lii"},
        {"\u1529", "jii"},
        {"\u1556", "vii"},
        {"\u1547", "rii"},
        {"\u1580", "qii"},
        {"\u1585\u146E", "qqii"},
        {"\u1590", "ngii"},
        {"\u1672", "nngii"},
        {"\u1590", "Nii"},
        {"\u1672", "Xii"},
        {"\u15A1", "&ii"},
        {"\u1406", "uu"},
        {"\u1434", "puu"},
        {"\u1451", "tuu"},
        {"\u1470", "kuu"},
        {"\u148E", "guu"},
        {"\u14A8", "muu"},
        {"\u14C5", "nuu"},
        {"\u14F2", "suu"},
        {"\u14D8", "luu"},
        {"\u152B", "juu"},
        {"\u1558", "vuu"},
        {"\u1549", "ruu"},
        {"\u1582", "quu"},
        {"\u1585\u1470", "qquu"},
        {"\u1592", "nguu"},
        {"\u1674", "nnguu"},
        {"\u1592", "Nuu"},
        {"\u1674", "Xuu"},
        {"\u15A3", "&uu"},
        {"\u140B", "aa"},
        {"\u1439", "paa"},
        {"\u1456", "taa"},
        {"\u1473", "kaa"},
        {"\u1491", "gaa"},
        {"\u14AB", "maa"},
        {"\u14C8", "naa"},
        {"\u14F5", "saa"},
        {"\u14DB", "laa"},
        {"\u152E", "jaa"},
        {"\u155A", "vaa"},
        {"\u154C", "raa"},
        {"\u1584", "qaa"},
        {"\u1585\u1473", "qqaa"},
        {"\u1594", "ngaa"},
        {"\u1676", "nngaa"},
        {"\u1594", "Naa"},
        {"\u1676", "Xaa"},
        {"\u15A5", "&aa"},
        {"\u1449", "p"},
        {"\u1449", "b"},
        {"\u1466", "t"},
        {"\u1466", "d"},
        {"\u1483", "k"},
        {"\u14A1", "g"},
        {"\u14BB", "m"},
        {"\u14D0", "n"},
        {"\u1505", "s"},
        {"\u14EA", "l"},
        {"\u153E", "j"},
        {"\u155D", "v"},
        {"\u1550", "r"},
        {"\u1585", "q"},
        {"\u1595", "ng"},
        {"\u1596", "nng"},
        {"\u1595", "N"},
        {"\u1596", "X"},
        {"\u15A6", "&"},
        {"\u157C", "h"},
        {"\u15AF", "b"}
    };
    
    static String [][] syllabicsToRomanAIPAITAI = {
        // groupe des "ai"
        {"\u1401","ai"}, // ai
        {"\u142f","pai"}, // pai
        {"\u144c","tai"}, // tai
        {"\u146b","kai"}, // kai
        {"\u1489","gai"}, // gai
        {"\u14a3","mai"}, // mai
        {"\u14c0","nai"}, // nai
        {"\u14ed","sai"}, // sai
        {"\u14d3","lai"}, // lai
        {"\u1526","jai"}, // jai
        {"\u1553","vai"}, // vai
        {"\u1543","rai"}, // rai
        {"\u166f","qai"}, // qai
        {"\u1670","ngai"},  // ngai
    };
    
    
    /*
     * transcoderX et getTranscoder() et simplify ne sont plus nécessaires
     * depuis qu'on a recodé les fonctions de transcodage.
     */

//    static TransCoder transcoderICI;
//    static TransCoder transcoderAIPAITAI;
//    static TransCoder transcoderSimp;
//    
//    public static TransCoder getTranscoder() {
//        return getTranscoder("ici");
//    }
//    
//    public static TransCoder getTranscoder(String mode) {
//        if (mode.toLowerCase().equals("ici")) {
//            if (transcoderICI==null)
//                transcoderICI = new TransCoder(syllabicsToRomanICI,1);
//            return transcoderICI;
//        } else if (mode.toLowerCase().equals("aipaitai")) {
//            if (transcoderAIPAITAI==null) {
//                transcoderAIPAITAI = new TransCoder(syllabicsToRoman,1);
//            }
//            return transcoderAIPAITAI;            
//        } else
//            return null;
//    }
    
//    static String [][] simplificationTable = {
//        {"\u158F", "\u1595\u148B"}, // ngi < ng+gi
//        {"\u1591", "\u1595\u148D"}, // ngu < ng+gu
//        {"\u1593", "\u1595\u1490"}, // nga < ng+ga
//        {"\u1590", "\u1595\u148C"}, // ngii < ng+gii
//        {"\u1592", "\u1595\u148E"}, // nguu < ng+guu
//        {"\u1594", "\u1595\u1491"}, // ngaa < ng+gaa
//        {"\u1671", "\u1596\u148B"}, // nngi < nng+gi
//        {"\u1673", "\u1596\u148D"}, // nngu < nng+gu
//        {"\u1675", "\u1596\u1490"}, // nnga < nng+ga
//        {"\u1672", "\u1596\u148C"}, // nngii < nng+gii
//        {"\u1674", "\u1596\u148E"}, // nnguu < nng+guu
//        {"\u1676", "\u1596\u1491"}, // nngaa < nng+gaa
//    };
    
    
    /*
     * Transform ng+gi, etc. to ngi, etc...
     */
//    public static String simplify(String str) {
//        if (transcoderSimp==null)
//            transcoderSimp = new TransCoder(simplificationTable,1);
//        String strSimp = transcoderSimp.transcode(str);
//        return strSimp;
//    }
    
    static public String transcodeToRoman(String s) {
            int i=0;
            int l=s.length();
            char c,e;
            String d;
            StringBuffer sb = new StringBuffer();
            while (i < l) {
                c = s.charAt(i);
                switch (c) {
                case '\u1403': d = "i"; break;
                case '\u1431': d = "pi"; break;
                case '\u144E': d = "ti"; break;
                case '\u146D': d = "ki"; break;
                case '\u148B': d = "gi"; break;
                case '\u14A5': d = "mi"; break;
                case '\u14C2': d = "ni"; break;
                case '\u14EF': d = "si"; break;
                case '\u14D5': d = "li"; break;
                case '\u1528': d = "ji"; break;
                case '\u1555': d = "vi"; break;
                case '\u1546': d = "ri"; break;
                case '\u157F': d = "qi"; break;
                case '\u158F': d = "ngi"; break;
                case '\u1671': d = "nngi"; break;
                case '\u15A0': d = "&i"; break;
                case '\u1405': d = "u"; break;
                case '\u1433': d = "pu"; break;
                case '\u1450': d = "tu"; break;
                case '\u146F': d = "ku"; break;
                case '\u148D': d = "gu"; break;
                case '\u14A7': d = "mu"; break;
                case '\u14C4': d = "nu"; break;
                case '\u14F1': d = "su"; break;
                case '\u14D7': d = "lu"; break;
                case '\u152A': d = "ju"; break;
                case '\u1557': d = "vu"; break;
                case '\u1548': d = "ru"; break;
                case '\u1581': d = "qu"; break;
                case '\u1591': d = "ngu"; break;
                case '\u1673': d = "nngu"; break;
                case '\u15A2': d = "&u"; break;
                case '\u140A': d = "a"; break;
                case '\u1438': d = "pa"; break;
                case '\u1455': d = "ta"; break;
                case '\u1472': d = "ka"; break;
                case '\u1490': d = "ga"; break;
                case '\u14AA': d = "ma"; break;
                case '\u14C7': d = "na"; break;
                case '\u14F4': d = "sa"; break;
                case '\u14DA': d = "la"; break;
                case '\u152D': d = "ja"; break;
                case '\u1559': d = "va"; break;
                case '\u154B': d = "ra"; break;
                case '\u1583': d = "qa"; break;
                case '\u1593': d = "nga"; break;
                case '\u1675': d = "nnga"; break;
                case '\u15A4': d = "&a"; break;
                case '\u1404': d = "ii"; break;
                case '\u1432': d = "pii"; break;
                case '\u144F': d = "tii"; break;
                case '\u146E': d = "kii"; break;
                case '\u148C': d = "gii"; break;
                case '\u14A6': d = "mii"; break;
                case '\u14C3': d = "nii"; break;
                case '\u14F0': d = "sii"; break;
                case '\u14D6': d = "lii"; break;
                case '\u1529': d = "jii"; break;
                case '\u1556': d = "vii"; break;
                case '\u1547': d = "rii"; break;
                case '\u1580': d = "qii"; break;
                case '\u1590': d = "ngii"; break;
                case '\u1672': d = "nngii"; break;
                case '\u15A1': d = "&ii"; break;
                case '\u1406': d = "uu"; break;
                case '\u1434': d = "puu"; break;
                case '\u1451': d = "tuu"; break;
                case '\u1470': d = "kuu"; break;
                case '\u148E': d = "guu"; break;
                case '\u14A8': d = "muu"; break;
                case '\u14C5': d = "nuu"; break;
                case '\u14F2': d = "suu"; break;
                case '\u14D8': d = "luu"; break;
                case '\u152B': d = "juu"; break;
                case '\u1558': d = "vuu"; break;
                case '\u1549': d = "ruu"; break;
                case '\u1582': d = "quu"; break;
                case '\u1592': d = "nguu"; break;
                case '\u1674': d = "nnguu"; break;
                case '\u15A3': d = "&uu"; break;
                case '\u140B': d = "aa"; break;
                case '\u1439': d = "paa"; break;
                case '\u1456': d = "taa"; break;
                case '\u1473': d = "kaa"; break;
                case '\u1491': d = "gaa"; break;
                case '\u14AB': d = "maa"; break;
                case '\u14C8': d = "naa"; break;
                case '\u14F5': d = "saa"; break;
                case '\u14DB': d = "laa"; break;
                case '\u152E': d = "jaa"; break;
                case '\u155A': d = "vaa"; break;
                case '\u154C': d = "raa"; break;
                case '\u1584': d = "qaa"; break;
                case '\u1594': d = "ngaa"; break;
                case '\u1676': d = "nngaa"; break;
                case '\u15A5': d = "&aa"; break;
                case '\u1449': d = "p"; break;
                case '\u1466': d = "t"; break;
                case '\u1483': d = "k"; break;
                case '\u14A1': d = "g"; break;
                case '\u14BB': d = "m"; break;
                case '\u14D0': d = "n"; break;
                case '\u1505': d = "s"; break;
                case '\u14EA': d = "l"; break;
                case '\u153E': d = "j"; break;
                case '\u155D': d = "v"; break;
                case '\u1550': // r
                    i++;
                    if (i < l) {
                        e = s.charAt(i);
                        switch (e) { // r+k. > q.
                        case '\u146D': d = "qi"; break;
                        case '\u146F': d = "qu"; break;
                        case '\u1472': d = "qa"; break;
                        case '\u146E': d = "qii"; break;
                        case '\u1470': d = "quu"; break;
                        case '\u1473': d = "qaa"; break;
                        case '\u146B': d = "qai"; break;
                        default: d = "r"; i--; break;
                        }
                    }else {
                        i--;
                        d = "r";
                    }
                    break;
                case '\u1585': // q
                    i++;
                    if (i < l) {
                        e = s.charAt(i);
                        switch (e) { // q+k. > qq.
                        case '\u146D': d = "qqi"; break;
                        case '\u146F': d = "qqu"; break;
                        case '\u1472': d = "qqa"; break;
                        case '\u146E': d = "qqii"; break;
                        case '\u1470': d = "qquu"; break;
                        case '\u1473': d = "qqaa"; break;
                        case '\u146B': d = "qqai"; break;
                        default: d = "q"; i--; break;
                        }
                    }else {
                        i--;
                        d = "q";
                    }
                    break;
                case '\u1595': // ng
                    i++;
                    if (i < l) {
                        e = s.charAt(i);
                        switch (e) { // ng+g. > ng.
                        case '\u148B': d = "ngi"; break;
                        case '\u148D': d = "ngu"; break;
                        case '\u1490': d = "nga"; break;
                        case '\u148C': d = "ngii"; break;
                        case '\u148E': d = "nguu"; break;
                        case '\u1491': d = "ngaa"; break;
                        case '\u1489': d = "ngai"; break;
                        default: d = "ng"; i--; break;
                        }
                    }else {
                        i--;
                        d = "ng";
                    }
                    break;
                case '\u1596': 
                    i++;
                    if (i < l) {
                        e = s.charAt(i);
                        switch (e) { // nng+g. > nng.
                        case '\u148B': d = "nngi"; break;
                        case '\u148D': d = "nngu"; break;
                        case '\u1490': d = "nnga"; break;
                        case '\u148C': d = "nngii"; break;
                        case '\u148E': d = "nnguu"; break;
                        case '\u1491': d = "nngaa"; break;
                        case '\u1489': d = "nngai"; break;
                        default: d = "nng"; i--; break;
                        }
                    }else {
                        i--;
                        d = "nng";
                    }
                    break;
                case '\u15A6': d = "&"; break;
                case '\u157C': d = "H"; break; // Nunavut H
                case '\u1574': d = "hai"; break; // Nunavik Hai
                case '\u1575': d = "hi"; break; // Nunavik Hi
                case '\u1576': d = "hii"; break; // Nunavik Hii
                case '\u1577': d = "hu"; break; // Nunavik Hu
                case '\u1578': d = "huu"; break; // Nunavik Huu
                case '\u1579': d = "ha"; break; // Nunavik Ha
                case '\u157A': d = "haa"; break; // Nunavik Haa
                case '\u15AF': d = "b"; break; // Aivilik B
                case '\u1401': d = "ai"; break; // ai
                case '\u142f': d = "pai"; break; // pai
                case '\u144c': d = "tai"; break; // tai
                case '\u146b': d = "kai"; break; // kai
                case '\u1489': d = "gai"; break; // gai
                case '\u14a3': d = "mai"; break; // mai
                case '\u14c0': d = "nai"; break; // nai
                case '\u14ed': d = "sai"; break; // sai
                case '\u14d3': d = "lai"; break; // lai
                case '\u1526': d = "jai"; break; // jai
                case '\u1553': d = "vai"; break; // vai
                case '\u1543': d = "rai"; break; // rai
                case '\u1542': d = "rai"; break; // rai
                case '\u166f': d = "qai"; break; // qai
                case '\u1670': d = "ngai"; break;  // ngai
                default: d = new String(new char[]{c}); break;
                }
                i++;
                sb.append(d);
            }
            return sb.toString();
    }


    /*
     * Remplacer les caractères AI par A+I
     */
    public static String unicodeAIPAITAItoICI(String s) {
        int i=0;
        int j;
        int l=s.length();
        char c,e;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            c = s.charAt(i);
            switch (c) {
            case '\u1401': sb.append("\u140a\u1403"); break;// ai
            case '\u142f': sb.append("\u1438\u1403"); break; // pai
            case '\u144c': sb.append("\u1455\u1403"); break; // tai
            case '\u146b': sb.append("\u1472\u1403"); break; // kai
            case '\u1489': sb.append("\u1490\u1403"); break; // gai
            case '\u14a3': sb.append("\u14aa\u1403"); break; // mai
            case '\u14c0': sb.append("\u14c7\u1403"); break; // nai
            case '\u14ed': sb.append("\u14f4\u1403"); break; // sai
            case '\u14d3': sb.append("\u14da\u1403"); break; // lai
            case '\u1526': sb.append("\u152d\u1403"); break; // jai
            case '\u1553': sb.append("\u1559\u1403"); break; // vai
            case '\u1543': sb.append("\u154b\u1403"); break; // rai
            case '\u166f': sb.append("\u1583\u1403"); break; // qai
            case '\u1670': sb.append("\u1593\u1403"); break;  // ngai
            // nng + gai --> nnga + i
            case '\u1596':
                j = i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1489': sb.append("\u1675\u1403"); i=j; break;
                    }
                }
                break;
            default: sb.append(c); break;
            }
            i++;
        }
        return sb.toString();
    }


    /*
     * Remplacer les caractères A+I par AI
     */
    public static String unicodeICItoAIPAITAI(String s) {
        int i=0;
        int l=s.length();
        char c,d,e;
        int j;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            c = s.charAt(i);
            d = c;
            j = i+1;
            switch (c) {
            case '\u140a': //a 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u1401'; i=j; break;// ai
                    }
                }
            case '\u1438': //pa 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u142f'; i=j; break;// pai
                    }
                }
                break;
            case '\u1455': //ta 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u144c'; i=j; break;// tai
                    }
                }
                break;
            case '\u1472': //ka 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u146b'; i=j; break;// kai
                    }
                }
                break;
            case '\u1490': //ga 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u1489'; i=j; break;// gai
                    }
                }
                break;
            case '\u14aa': //ma 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u14a3'; i=j; break;// mai
                    }
                }
                break;
            case '\u14c7': //na 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u14c0'; i=j; break;// nai
                    }
                }
                break;
            case '\u14f4': //sa 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u14ed'; i=j; break;// sai
                    }
                }
                break;
            case '\u14da': //la 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u14d3'; i=j; break;// lai
                    }
                }
                break;
            case '\u152d': //ja 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u1526'; i=j; break;// jai
                    }
                }
                break;
            case '\u1559': //va 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u1553'; i=j; break;// vai
                    }
                }
                break;
            case '\u154b': //ra 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u1542'; i=j; break;// rai
                    }
                }
                break;
            case '\u1583': //qa 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u166f'; i=j; break;// qai
                    }
                }
                break;
            case '\u1593': //nga 
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': d = '\u1670'; i=j; break;// ngai
                    }
                }
                break;
                // nnga + i --> nng + gai
            case '\u1675': //nnga
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403': sb.append('\u1596'); d = '\u1489'; i=j; break;// nngai
                    }
                }
                break;
            }
            sb.append(d);
            i++;
        }
        return sb.toString();
    }
    
    
}