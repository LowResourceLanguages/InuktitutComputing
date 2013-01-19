//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		PoliceNaamajut.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: 
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: PoliceNaamajut.java,v 1.1 2009/06/19 19:38:40 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceNaamajut.java,v $
// Revision 1.1  2009/06/19 19:38:40  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.14  2006/11/01 15:29:57  farleyb
// *** empty log message ***
//
// Revision 1.13  2006/10/20 17:01:26  farleyb
// *** empty log message ***
//
// Revision 1.12  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.11  2006/07/20 20:35:41  farleyb
// *** empty log message ***
//
// Revision 1.10  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.9  2006/06/21 20:27:11  farleyb
// *** empty log message ***
//
// Revision 1.8  2006/06/19 17:46:27  farleyb
// *** empty log message ***
//
// Revision 1.7  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, nécessaire entre autres pour le surlignage, où il faut déterminer la délimitation des mots.
//
// Revision 1.6  2006/03/30 18:40:24  farleyb
// Corrections mineures
//
// Revision 1.5  2006/03/09 18:01:53  farleyb
// Diverses modifications re tables de conversion à unicode
//
// Revision 1.4  2005/11/28 15:20:57  farleyb
// *** empty log message ***
//
// Revision 1.3  2005/02/14 17:22:21  stojanovim
// Small changes to Police*
//
// Revision 1.2  2004/12/07 20:49:49  farleyb
// *** empty log message ***
//
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Première sauvegarde
//
// Revision 1.0  2003-06-25 13:19:57-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:39:09-05  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


package fonts;

import java.util.HashSet;
import java.util.Set;

import utilities1.Util;
import script.TransCoder;


// Remplacement de codes d'une police donnée 
// en codes unicode équivalents représentant
// les mêmes caractères

public class FontNaamajut {

    static String fontName = "naamajut";
    static String unicodesICI2Codes[][] = {

        // i
        { "\u1403", "w"  }, // i
        { "\u1431", "W"  }, // pi
        { "\u144E", "t"  }, // ti
        { "\u146D", "r"  }, // ki
        { "\u148B", "Q"  }, // gi
        { "\u14A5", "u"  }, // mi
        { "\u14C2", "i"  }, // ni
        { "\u14EF", "y"  }, // si
        { "\u14D5", "o"  }, // li
        { "\u1528", "p"  }, // ji
        { "\u1555", "F"  }, // vi
        { "\u1546", "E"  }, // ri
        { "\u157F", "e"  }, // qi
        { "\u158F", "q"  }, // ngi
        { "\u158F", "R"  }, // ngi
        { "\u15A0", "O"  }, // &i

        // ii
        { "\u1404", "\u2211" }, // ii
        { "\u1432", "\u201e" }, // pii
        { "\u144F", "\u2020" }, // tii
        { "\u146E", "\u00ae" }, // kii
        { "\u148C", "\u0152" }, // gii
        { "\u14A6", "\u00a8" }, // mii
        { "\u14C3", "\u2022" }, // nii
        { "\u14F0", "\u00a5" }, // sii
        { "\u14D6", "\u00f8" }, // lii
        { "\u1529", "\u03c0" }, // jii
        { "\u1556", "\u00cf" }, // vii
        { "\u1547", "\u2030" }, // rii
        { "\u1580", "\u00b4" }, // qii
        { "\u1590", "\u0153" }, // ngii
        { "\u1590", "\u00c2" }, // ngii
        { "\u15A1", "\u00d8" }, // &ii

        // u
        { "\u1405", "s"  }, // u
        { "\u1433", "S" }, // pu
        { "\u1450", "g"  }, // tu
        { "\u146F", "f"  }, // ku
        { "\u148D", "A"  }, // gu
        { "\u14A7", "j"  }, // mu
        { "\u14C4", "k"  }, // nu
        { "\u14F1", "h"  }, // su
        { "\u14D7", "l" }, // lu
        { "\u152A", "J"  }, // ju
        { "\u1557", "K"  }, // vu
        { "\u1548", "D"  }, // ru
        { "\u1581", "d"  }, // qu
        { "\u1591", "a"  }, // ngu
        { "\u1591", "T"  }, // ngu
        { "\u15A2", "L"  }, // &u

        // uu
        { "\u1406", "\u00df" }, // uu
        { "\u1434", "\u00cd" }, // puu
        { "\u1451", "\u00a9" }, // tuu
        { "\u1470", "\u0192" }, // kuu
        { "\u148E", "\u00c5" }, // guu
        { "\u14A8", "\u2206" }, // muu
        { "\u14A8", "\u0394" }, // muu
        { "\u14C5", "\u02da" }, // nuu
        { "\u14F2", "\u02d9" }, // suu
        { "\u14D8", "\u00ac" }, // luu
        { "\u152B", "\u00d4" }, // juu
        { "\u1558", "\ufffd" }, // vuu
        { "\u1549", "\u00ce" }, // ruu
        { "\u1582", "\u2202" }, // quu
        { "\u1592", "\u00e5" }, // nguu
        { "\u1592", "\u00ca" }, // nguu
        { "\u15A3", "\u00d2" }, // &uu

        // a
        { "\u140A", "x" }, // a
        { "\u1438", "X" }, // pa
        { "\u1455", "b" }, // ta
        { "\u1472", "v" }, // ka
        { "\u1490", "Z" }, // ga
        { "\u14AA", "m" }, // ma
        { "\u14C7", "N" }, // na
        { "\u14F4", "n" }, // sa
        { "\u14DA", "M" }, // la
        { "\u152D", "/" }, // ja
        { "\u1559", "?" }, // va
        { "\u154B", "C" }, // ra
        { "\u1583", "c" }, // qa
        { "\u1593", "z" }, // nga
        { "\u1593", "Y" }, // nga
        { "\u15A4", "I" }, // &a

        // aa
        { "\u140B", "\u2248" }, // aa
        { "\u1439", "\u00d9" }, // paa
        { "\u1456", "\u222b" }, // taa
        { "\u1473", "\u221a" }, // kaa
        { "\u1491", "\u00db" }, // gaa
        { "\u14AB", "\u00b5" }, // maa
        { "\u14AB", "\u03bc" }, // maa
        { "\u14C8", "\u02c6" }, // naa
        { "\u14F5", "\u007e" }, // saa
        { "\u14DB", "\u02dc" }, // laa
        { "\u152E", "\u00f7" }, // jaa
        { "\u155A", "\u00bf" }, // vaa
        { "\u154C", "\u00c7" }, // raa
        { "\u1584", "\u00e7" }, // qaa
        { "\u1594", "\u2126" }, // Naa
        { "\u1594", "\u03a9" }, // ngaa
        { "\u1594", "\u00c1" }, // ngaa
        { "\u15A5", "\u00c8" }, // &aa

        // consonnes seules
        { "\u1449", "2" }, // p
        { "\u1466", "5" }, // t
        { "\u1483", "4" }, // k
        { "\u14A1", "[" }, // g
        { "\u14BB", "7" }, // m
        { "\u14D0", "8" }, // n
        { "\u1505", "{" }, // s
        { "\u14EA", "9" }, // l
        { "\u153E", "0" }, // j
        { "\u155D", "=" }, // v
        { "\u1550", "3" }, // r
        { "\u1585", "6" }, // q
        { "\u1595", "1" }, // ng
        { "\u1595", "U" }, // ng
        { "\u1596", "`" }, // nng
        { "\u15A6", "P" }, // &
        { "\u157C", "B" } // H
    };
    
    static String unicodesICI2CodesDigits[][] = {

        // chiffres
        { "1", "!"  }, // 1
        { "2", "@"  }, // 2
        { "3", "#"  }, // 3
        { "4", "$"  }, // 4
        { "5", "%"  }, // 5
        { "6", "^"  }, // 6
        { "7", "&"  }, // 7
        { "8", "*"  }, // 8
        { "9", "("  }, // 9
        { "0", ")"  } // 0
    };
    
    static String unicodesICI2CodesOthers[][] = {

        // autres signes
        {",","<"},
        {".",">"},
        {"(","G"},
        {")","H"},
        {"/","\\"},
        {"?","V"},
        {"$","\u00a2"},
        {"#","\u00a3"},
        {"!","\u00a1"},
        {"*","\u00a7"},
        {"[","\u00aa"},
        {"+","\u00b0"},
        {"&","\u00b6"},
        {"\u00f7","\u00b7"}, // divise
        {"]","\u00ba"},
        {"\u00a9","\u00cc"}, // copyright
        {"}","\u0131"},
        {"\u00b7","\u02c7"}, // point médian
        {"\u02d9","\u02db"}, // point en chef
        {"\u166d","\u2013"}, // khi (??)
        {"=","\u201a"},
        {"\u2154","\u2021"}, // 2/3
        {"\u00bc","\u2039"}, // 1/4
        {"\u00bd","\u203A"}, // 1/2
        {"\u00a3","\u20ac"}, // enregistré
        {"\u00f7","\u2219"}, // divise
        {"%","\u221e"},
        {"\u2022","\u2260"}, // puce
        {"{","\u25ca"},
        {"\u00be","\ufb01"}, // 3/4
        {"\u2153","\ufb02"} // 1/3
        };

    public static TransCoder transcoderToUnicodeICI;
    public static TransCoder transcoderToUnicodeAIPAITAI;
    public static TransCoder transcoderToFontICI;
    public static TransCoder transcoderToFontAIPAITAI;

    public static String wordChars = "";
    public static Set fontChars = new HashSet();
    static {
        for (int i=0; i<unicodesICI2Codes.length; i++) {
            String chars = unicodesICI2Codes[i][1];
            /*
             * If there are more than 1 character, it is a long syllable made of
             * a dot code and a short syllable character, both of which will 
             * already be in the list. So we don't process this long-syllable
             * character.
             */
            if (chars.length()==1) {
                wordChars += unicodesICI2Codes[i][1];
                for (int j=0; j<unicodesICI2Codes[i][1].length(); j++)
                    fontChars.add(new Character(unicodesICI2Codes[i][1].charAt(j)));
}
        }
        for (int i=0; i<unicodesICI2CodesDigits.length; i++) {
            wordChars += unicodesICI2CodesDigits[i][1];
            for (int j=0; j<unicodesICI2CodesDigits[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2CodesDigits[i][1].charAt(j)));
        }
        for (int i=0; i<unicodesICI2CodesOthers.length; i++) {
            wordChars += unicodesICI2CodesOthers[i][1];
        }
        wordChars = Util.prepareForRegexp(wordChars);
        wordChars = "["+wordChars+"]";
        
//        Police.checkForCharacterNames(fontName,fontChars);
    }
    
    static public String transcodeToUnicode(String s) {
        return transcodeToUnicode(s,null); // null = no aipaitai
    }
    
    /*
     * aipaitaiMode: String -  "aipaitai" : convertir a+i au caractère unicode AI
     */
    static public String transcodeToUnicode(String s, String aipaitaiMode) {
        int aipaitai = aipaitaiMode==null? 0 : aipaitaiMode.equals("aipaitai")? 1 : 0;
        int i=0,j,sbl;
        int l=s.length();
        char c,d,e;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            sbl = sb.length();
            c = s.charAt(i);
            switch (c) {
            case 'w': d = '\u1403';  break; // i
            case 'W': d = '\u1431';  break; // pi
            case 't': d = '\u144E';  break; // ti
            case 'r': // ki
                d = '\u146D'; 
                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    sb.deleteCharAt(sbl-2);
                    sb.append('\u1585');
                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                    sb.deleteCharAt(sbl-1);
                } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u157f';
                }
                break;
            case 'Q': // gi
                d = '\u148B'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u1595': //ng+gi
                        sb.deleteCharAt(sbl-1);
                        d = '\u158f'; // ngi
                        break;
                    case '\u1596': //nng+gi
                        sb.deleteCharAt(sbl-1);
                        d = '\u1671'; // nngi
                        break;
                    }
                }
                break;
            case 'u': d = '\u14A5';  break; // mi
            case 'i': d = '\u14C2';  break; // ni
            case 'y': d = '\u14EF'; break; // si
            case 'o': d = '\u14D5';  break; // li
            case 'p': d = '\u1528';  break; // ji
            case 'F': d = '\u1555'; break; // vi
            case 'E': // ri
                d = '\u1546'; // ri
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ri = r+ri (rri)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case 'e': // qi
                d = '\u157F';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146d';
                    }
                break; 
            case 'R':
            case 'q': // ngi
                d = '\u158F';
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sbl-1);
                        d = '\u1671'; // nngi
                        break;
                    }
                }
                break;
            case 'O': d = '\u15A0'; break; // &i
            case '\u2211': d = '\u1404'; break; // ii
            case '\u201e': d = '\u1432'; break; // pii
            case '\u2020': d = '\u144F'; break; // tii
            case '\u00ae': // kii
                d = '\u146E'; 
                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    sb.deleteCharAt(sbl-2);
                    sb.append('\u1585');
                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                    sb.deleteCharAt(sbl-1);
                } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1580';
                }
                break;
            case '\u0152': // gii
                d = '\u148C'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u1595': //ng+gii
                        sb.deleteCharAt(sbl-1);
                        d = '\u1590'; // ngii
                        break;
                    case '\u1596': //nng+gii
                        sb.deleteCharAt(sbl-1);
                        d = '\u1672'; // nngii
                        break;
                    }
                }
                break;
            case '\u00a8': d = '\u14A6'; break; // mii
            case '\u2022': d = '\u14C3'; break; // nii
            case '\u00a5': d = '\u14F0'; break; // sii
            case '\u00f8': d = '\u14D6'; break; // lii
            case '\u03c0': d = '\u1529'; break; // jii
            case '\u00cf': d = '\u1556'; break; // vii
            case '\u2030': // rii
                d = '\u1547'; 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case '\u00b4':  // qii
                d = '\u1580'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146e';
                    }
                break; 
            case '\u0153':
            case '\u00c2': 
                d = '\u1590'; // ngii
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sbl-1);
                        d = '\u1672'; // nngii
                        break;
                    }
                }
                break;
            case '\u00d8': d = '\u15A1'; break; // &ii
            case 's': d = '\u1405'; break; // u
            case 'S': d = '\u1433'; break; // pu
            case 'g': d = '\u1450'; break; // tu
            case 'f': // ku
                d = '\u146F'; 
                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    sb.deleteCharAt(sbl-2);
                    sb.append('\u1585');
                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                    sb.deleteCharAt(sbl-1);
                } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1581';
                }
                break;
            case 'A': // gu
                d = '\u148D'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u1595': //ng+gu
                        sb.deleteCharAt(sbl-1);
                        d = '\u1591'; // ngu
                        break;
                    case '\u1596': //nng+gu
                        sb.deleteCharAt(sbl-1);
                        d = '\u1673'; // nngu
                        break;
                    }
                }
                break;
            case 'j': d = '\u14A7'; break; // mu
            case 'k': d = '\u14C4'; break; // nu
            case 'h': d = '\u14F1'; break; // su
            case 'l': d = '\u14D7'; break; // lu
            case 'J': d = '\u152A'; break; // ju
            case 'K': d = '\u1557'; break; // vu
            case 'D': // ru
                d = '\u1548'; 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ru = r+ru (rru)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case 'd': //qu 
                d = '\u1581'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146f';
                    }
                break; 
            case 'a':
            case 'T': // ngu
                d = '\u1591'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sbl-1);
                        d = '\u1673'; // nngu
                        break;
                    }
                }
                break;
            case 'L': d = '\u15A2'; break; // &u
            case '\u00df': d = '\u1406'; break; // uu
            case '\u00cd': d = '\u1434'; break; // puu
            case '\u00a9': d = '\u1451'; break; // tuu
            case '\u0192': // kuu
                d = '\u1470'; 
                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    sb.deleteCharAt(sbl-2);
                    sb.append('\u1585');
                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                    sb.deleteCharAt(sbl-1);
                } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1582';
                }
                break;
            case '\u00c5': // guu
                d = '\u148E'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u1595': //ng+guu
                        sb.deleteCharAt(sbl-1);
                        d = '\u1592'; // nguu
                        break;
                    case '\u1596': //nng+guu
                        sb.deleteCharAt(sbl-1);
                        d = '\u1674'; // nnguu
                        break;
                    }
                }
                break;
            case '\u0394': d = '\u14A8'; break; // muu
            case '\u2206': d = '\u14A8'; break; // muu
            case '\u02da': d = '\u14C5'; break; // nuu
            case '\u02d9': d = '\u14F2'; break; // suu
            case '\u00ac': d = '\u14D8'; break; // luu
            case '\u00d4': d = '\u152B'; break; // juu
            case '\ufffd': d = '\u1558'; break; // vuu
            case '\u00ce': // ruu
                d = '\u1549'; 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu = r+ruu (rruu)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case '\u2202': // quu
                d = '\u1582'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+quu = q+kuu (qquu)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u1470';
                    }
                break; 
            case '\u00e5':
            case '\u00ca': // nguu
                d = '\u1592'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sbl-1);
                        d = '\u1674'; // nnguu
                        break;
                    }
                }
                break;
            case '\u00d2': d = '\u15A3'; break; // &uu
            case 'x': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1401'; break;
                        default: sb.append('\u140a'); d = '\u1403'; break; // ai
                        }
                        break;
                    default: d = '\u140a'; i--; break; // a
                    }
                } else {
                    d = '\u140a'; i--;
                }
                break;
            case 'X': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u142F'; break;
                        default: sb.append('\u1438'); d = '\u1403'; break; // pai
                        }
                        break;
                    default: d = '\u1438'; i--; break; // pa
                    }
                } else {
                    d = '\u1438'; i--;
                }
                break;
            case 'b': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u144C'; break;
                        default: sb.append('\u1455'); d = '\u1403'; break; // tai
                        }
                        break;
                    default: d = '\u1455'; i--; break; // ta
                    }
                } else {
                    d = '\u1455'; i--;
                }
                break;
            case 'v': // ka
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : 
                            d = '\u146B'; 
                            if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                                sb.deleteCharAt(sbl-1);
                                sb.deleteCharAt(sbl-2);
                                sb.append('\u1585');
                            } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                                sb.deleteCharAt(sbl-1);
                            } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                                sb.deleteCharAt(sbl-1);
                                d = '\u166f';
                            }
                            break;
                        default: 
                            if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                                sb.deleteCharAt(sbl-1);
                                sb.deleteCharAt(sbl-2);
                                sb.append('\u1585');
                            } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                                sb.deleteCharAt(sbl-1);
                                sb.append('\u1472'); 
                            } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                                sb.deleteCharAt(sbl-1);
                                sb.append('\u1583');
                            } else
                                sb.append('\u1472'); 
                            d = '\u1403'; 
                            break; // kai
                        }
                        break;
                    default: 
                        d = '\u1472'; 
                    if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                        sb.deleteCharAt(sbl-1);
                        sb.deleteCharAt(sbl-2);
                        sb.append('\u1585');
                    } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                        sb.deleteCharAt(sbl-1);
                    } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1583';
                    }
                        i--; break; // ka
                    }
                } else {
                    d = '\u1472'; i--;
                    if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                        sb.deleteCharAt(sbl-1);
                        sb.deleteCharAt(sbl-2);
                        sb.append('\u1585');
                    } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                        sb.deleteCharAt(sbl-1);
                    } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1583';
                    }
                }
                break;
            case 'Z': // ga
                d = '\u1490';
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 :
                            if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                                sb.deleteCharAt(sbl-1);
                                d = '\u1670'; // ngai
                            }  else 
                                d = '\u1489'; // gai
                            break;
                        default:
                            if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                                sb.deleteCharAt(sbl-1);
                                sb.append('\u1593'); // nga
                            } else  if (sbl != 0 && sb.charAt(sbl-1)=='\u1596') {
                                sb.deleteCharAt(sbl-1);
                                sb.append('\u1675'); // nnga
                            } else
                                sb.append(d); // ga
                        d = '\u1403'; break; // +i
                        }
                        i=j;
                        break;
                    default: 
                        if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                            sb.deleteCharAt(sbl-1);
                            d = '\u1593'; // nga
                        } else  if (sbl != 0 && sb.charAt(sbl-1)=='\u1596') {
                            sb.deleteCharAt(sbl-1);
                            d = '\u1675'; // nnga
                        }
                    break;
                    }
                } else if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1593'; // nga
                } else  if (sbl != 0 && sb.charAt(sbl-1)=='\u1596') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1675'; // nnga
                }
                break;
            case 'm': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14A3'; break;
                        default: sb.append('\u14AA'); d = '\u1403'; break; // mai
                        }
                        break;
                    default: d = '\u14AA'; i--; break; // ma
                    }
                } else {
                    d = '\u14AA'; i--;
                }
                break;
            case 'N': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14C0'; break;
                        default: sb.append('\u14C7'); d = '\u1403'; break; // nai
                        }
                        break;
                    default: d = '\u14C7'; i--; break; // na
                    }
                } else {
                    d = '\u14C7'; i--;
                }
                break;
            case 'n': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14ED'; break;
                        default: sb.append('\u14F4'); d = '\u1403'; break; // sai
                        }
                        break;
                    default: d = '\u14F4'; i--; break; // sa
                    }
                } else {
                    d = '\u14F4'; i--;
                }
                break;
            case 'M': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14D3'; break;
                        default: sb.append('\u14DA'); d = '\u1403'; break; // lai
                        }
                        break;
                    default: d = '\u14DA'; i--; break; // la
                    }
                } else {
                    d = '\u14DA'; i--;
                }
                break;
            case '/': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1526'; break;
                        default: sb.append('\u152d'); d = '\u1403'; break; // jai
                        }
                        break;
                    default: d = '\u152d'; i--; break; // ja
                    }
                } else {
                    d = '\u152d'; i--;
                }
                break;
            case '?': 
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1553'; break;
                        default: sb.append('\u1559'); d = '\u1403'; break; // vai
                        }
                        break;
                    default: d = '\u1559'; i--; break; // va
                    }
                } else {
                    d = '\u1559'; i--;
                }
                break;
            case 'C': // ra
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+ra = r+ra (rra)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1542'; break;
                        default: sb.append('\u154b'); d = '\u1403'; break; // rai
                        }
                        break;
                    default: d = '\u154b'; i--; break; // ra
                    }
                } else {
                    d = '\u154b'; i--;
                }
                break;
           case 'c': // qa
               j=i+1;
               if (j < l) {
                   e = s.charAt(j);
                   switch (e) {
                   case 'w': 
                       switch (aipaitai) {
                       case 1 : 
                           d = '\u166f'; 
                           if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) { // r+qa+i = q+kai (qqai)
                               sb.deleteCharAt(sb.length()-1);
                               sb.append('\u1585');
                               d = '\u146b';
                           }
                           break;
                       default: 
                           if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) { // r+qa+i = q+ka+i (qqai)
                               sb.deleteCharAt(sb.length()-1);
                               sb.append('\u1585');
                               sb.append('\u1472');
                               d = '\u1403';
                           } else {
                               sb.append('\u1583'); 
                               d = '\u1403';
                           }
                           break; // qai
                       }
                       i=j;
                       break;
                   default: 
                       d = '\u1583';; 
                       if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) { // r+qa = q+ka (qqa)
                           sb.deleteCharAt(sb.length()-1);
                           sb.append('\u1585');
                           d='\u1472';
                       }
                       break; // qa
                   }
               } else {
                   d = '\u1583';;
                   if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) { // r+qa = q+ka (qqa)
                       sb.deleteCharAt(sb.length()-1);
                       sb.append('\u1585');
                       d='\u1472';
                   }
               }
               break;
            case 'z': 
            case 'Y': // nga
                d = '\u1593'; // nga
                boolean precng = false;
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                    precng = true;
                    sb.deleteCharAt(--sbl);
                }
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : 
                            if (precng) {
                                sb.append('\u1596'); d = '\u1489'; // n+nga+i = nng + gai
                            } else
                                d = '\u1670'; // nga+i = ngai
                            break;
                        default: 
                            if (precng) {
                                sb.append('\u1675'); // n+nga+i = nnga + i
                                d = '\u1403'; 
                            }
                            else {
                                sb.append(d); // nga+i = nga + i
                                d = '\u1403'; 
                            }
                            break; 
                        }
                        i=j;
                        break;
                    default: 
                        if (precng) d = '\u1675';
                        break;
                    }
                } else {
                    if (precng) d = '\u1675';
                }
                break;
            case 'I': d = '\u15A4'; break; // &a
            case '\u2248': d = '\u140B'; break; // aa
            case '\u00d9': d = '\u1439'; break; // paa
            case '\u222b': d = '\u1456'; break; // taa
            case '\u221a': // kaa
                d = '\u1473'; 
                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    sb.deleteCharAt(sbl-2);
                    sb.append('\u1585');
                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                    sb.deleteCharAt(sbl-1);
                } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1584';
                }
                break;
            case '\u00db': // gaa
                d = '\u1491'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u1595': //ng+gaa
                        sb.deleteCharAt(sbl-1);
                        d = '\u1594'; // ngaa
                        break;
                    case '\u1596': //nng+gaa
                        sb.deleteCharAt(sbl-1);
                        d = '\u1676'; // nngaa
                        break;
                    }
                }
                break;
            case '\u00b5': d = '\u14AB'; break; // maa
            case '\u03bc': d = '\u14AB'; break; // maa
            case '\u02c6': d = '\u14C8'; break; // naa
            case '\u007e': d = '\u14F5'; break; // saa
            case '\u02dc': d = '\u14DB'; break; // laa
            case '\u00f7': d = '\u152E'; break; // jaa
            case '\u00bf': d = '\u155A'; break; // vaa
            case '\u00c7': // raa
                d = '\u154C'; 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu = r+ruu (rruu)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case '\u00e7': 
                d = '\u1584'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+quu = q+kuu (qquu)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u1473';
                    }
                break; 
            case '\u03a9':
            case '\u2126':
            case '\u00c1': // ngaa
                d = '\u1594'; 
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sbl-1);
                        d = '\u1676'; // nngaa
                        break;
                    }
                }
                break;
            case '\u00c8': d = '\u15A5'; break; // &aa
            case '2': d = '\u1449'; break; // p
            case '5': d = '\u1466'; break; // t
            case '4': d = '\u1483'; break; // k
            case '[': d = '\u14A1'; break; // g
            case '7': d = '\u14BB'; break; // m
            case '8': d = '\u14D0'; break; // n
            case '{': d = '\u1505'; break; // s
            case '9': d = '\u14EA'; break; // l
            case '0': d = '\u153E'; break; // j
            case '=': d = '\u155D'; break; // v
            case '3': d = '\u1550'; break; // r
            case '6': d = '\u1585'; break; // q
            case '1':
            case 'U': 
                d = '\u1595';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                    sb.deleteCharAt(sbl-1);
                    d='\u1596';
                }
                break;
            case '`': d = '\u1596'; break; // nng
            case 'P': d = '\u15A6'; break; // &
            case 'B': d = '\u157C'; break;// H
            case '!': d = '1'; break; // 1
            case '@': d = '2'; break; // 2
            case '#': d = '3'; break; // 3
            case '$': d = '4'; break; // 4
            case '%': d = '5'; break; // 5
            case '^': d = '6'; break; // 6
            case '&': d = '7'; break; // 7
            case '*': d = '8'; break; // 8
            case '(': d = '9'; break; // 9
            case ')': d = '0'; break;// 0
            case '<': d = ','; break;
            case '>': d = '.'; break;
            case 'G': d = '('; break;
            case 'H': d = ')'; break;
            case '\\': d = '/'; break;
            case 'V': d = '?'; break;
            case '\u00a2': d = '$'; break;
            case '\u00a3': d = '#'; break;
            case '\u00a1': d = '!'; break;
            case '\u00a7': d = '*'; break;
            case '\u00aa': d = '['; break;
            case '\u00b0': d = '+'; break;
            case '\u00b6': d = '&'; break;
            case '\u00b7': d = '\u00f7'; break; // divise
            case '\u00ba': d = ']'; break;
            case '\u00cc': d = '\u00a9'; break; // copyright
            case '\u0131': d = '}'; break;
            case '\u02c7': d = '\u00b7'; break; // point médian
            case '\u02db': d = '\u02d9'; break; // point en chef
            case '\u2013': d = '\u166d'; break; // khi (??)
            case '\u201a': d = '='; break;
            case '\u2021': d = '\u2154'; break; // 2/3
            case '\u2039': d = '\u00bc'; break; // 1/4
            case '\u203A': d = '\u00bd'; break; // 1/2
            case '\u20ac': d = '\u00a3'; break; // enregistré
            case '\u2219': d = '\u00f7'; break; // divise
            case '\u221e': d = '%'; break;
            case '\u2260': d = '\u2022'; break; // puce
            case '\u25ca': d = '{'; break;
            case '\ufb01': d = '\u00be'; break; // 3/4
            case '\ufb02': d = '\u2153'; break;// 1/3
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

    
    static public String transcodeFromUnicode(String s) {
        int i=0;
        int l=s.length();
        char c,d;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            c = s.charAt(i);
            switch (c) {
            case '\u1403': d = 'w'; break; // i
            case '\u1431': d = 'W'; break; // pi
            case '\u144E': d = 't'; break; // ti
            case '\u146D': d = 'r'; break; // ki
            case '\u148B': d = 'Q'; break; // gi
            case '\u14A5': d = 'u'; break; // mi
            case '\u14C2': d = 'i'; break; // ni
            case '\u14EF': d = 'y'; break; // si
            case '\u14D5': d = 'o'; break; // li
            case '\u1528': d = 'p'; break; // ji
            case '\u1555': d = 'F'; break; // vi
            case '\u1546': d = 'E'; break; // ri
            case '\u157F': d = 'e'; break; // qi
            case '\u158F': d = 'q'; break; // ngi
//            case '\u1671': d = 'R'; break; // NNi
            case '\u15A0': d = 'O'; break; // &i
            case '\u1404': d = '\u2211'; break; // ii
            case '\u1432': d = '\u201e'; break; // pii
            case '\u144F': d = '\u2020'; break; // tii
            case '\u146E': d = '\u00ae'; break; // kii
            case '\u148C': d = '\u0152'; break; // gii
            case '\u14A6': d = '\u00a8'; break; // mii
            case '\u14C3': d = '\u2022'; break; // nii
            case '\u14F0': d = '\u00a5'; break; // sii
            case '\u14D6': d = '\u00f8'; break; // lii
            case '\u1529': d = '\u03c0'; break; // jii
            case '\u1556': d = '\u00cf'; break; // vii
            case '\u1547': d = '\u2030'; break; // rii
            case '\u1580': d = '\u00b4'; break; // qii
            case '\u1590': d = '\u0153'; break; // ngii
//            case '\u1672': d = '\u00c2'; break; // NNii
            case '\u15A1': d = '\u00d8'; break; // &ii
            case '\u1405': d = 's'; break; // u
            case '\u1433': d = 'S'; break; // pu
            case '\u1450': d = 'g'; break; // tu
            case '\u146F': d = 'f'; break; // ku
            case '\u148D': d = 'A'; break; // gu
            case '\u14A7': d = 'j'; break; // mu
            case '\u14C4': d = 'k'; break; // nu
            case '\u14F1': d = 'h'; break; // su
            case '\u14D7': d = 'l'; break; // lu
            case '\u152A': d = 'J'; break; // ju
            case '\u1557': d = 'K'; break; // vu
            case '\u1548': d = 'D'; break; // ru
            case '\u1581': d = 'd'; break; // qu
            case '\u1591': d = 'a'; break; // ngu
//            case '\u1673': d = 'T'; break; // NNu
            case '\u15A2': d = 'L'; break; // &u
            case '\u1406': d = '\u00df'; break; // uu
            case '\u1434': d = '\u00cd'; break; // puu
            case '\u1451': d = '\u00a9'; break; // tuu
            case '\u1470': d = '\u0192'; break; // kuu
            case '\u148E': d = '\u00c5'; break; // guu
            case '\u14A8': d = '\u2206'; break; // muu
//          case '\u14A8': d = '\u0394'; break; // muu
            case '\u14C5': d = '\u02da'; break; // nuu
            case '\u14F2': d = '\u02d9'; break; // suu
            case '\u14D8': d = '\u00ac'; break; // luu
            case '\u152B': d = '\u00d4'; break; // juu
            case '\u1558': d = '\ufffd'; break; // vuu
            case '\u1549': d = '\u00ce'; break; // ruu
            case '\u1582': d = '\u2202'; break; // quu
            case '\u1592': d = '\u00e5'; break; // nguu
//            case '\u1674': d = '\u00ca'; break; // NNuu
            case '\u15A3': d = '\u00d2'; break; // &uu
            case '\u140A': d = 'x'; break; // a
            case '\u1438': d = 'X'; break; // pa
            case '\u1455': d = 'b'; break; // ta
            case '\u1472': d = 'v'; break; // ka
            case '\u1490': d = 'Z'; break; // ga
            case '\u14AA': d = 'm'; break; // ma
            case '\u14C7': d = 'N'; break; // na
            case '\u14F4': d = 'n'; break; // sa
            case '\u14DA': d = 'M'; break; // la
            case '\u152D': d = '/'; break; // ja
            case '\u1559': d = '?'; break; // va
            case '\u154B': d = 'C'; break; // ra
            case '\u1583': d = 'c'; break; // qa
            case '\u1593': d = 'z'; break; // nga
//            case '\u1675': d = 'Y'; break; // NNa
            case '\u15A4': d = 'I'; break; // &a
            case '\u1401': sb.append('x'); d = 'w'; break; // ai
            case '\u142f': sb.append('X'); d = 'w'; break; // pai
            case '\u144c': sb.append('b'); d = 'w'; break; // tai
            case '\u146b': sb.append('v'); d = 'w'; break; // kai
            case '\u1489': sb.append('Z'); d = 'w'; break; // gai
            case '\u14a3': sb.append('m'); d = 'w'; break; // mai
            case '\u14c0': sb.append('N'); d = 'w'; break; // nai
            case '\u14ed': sb.append('n'); d = 'w'; break; // sai
            case '\u14d3': sb.append('M'); d = 'w'; break; // lai
            case '\u1526': sb.append('/'); d = 'w'; break; // jai
            case '\u1553': sb.append('?'); d = 'w'; break; // vai
            case '\u1543': sb.append('C'); d = 'w'; break; // rai
            case '\u166f': sb.append('c'); d = 'w'; break; // qai
            case '\u1670': sb.append('z'); d = 'w'; break;  // ngai  
            case '\u140B': d = '\u2248'; break; // aa
            case '\u1439': d = '\u00d9'; break; // paa
            case '\u1456': d = '\u222b'; break; // taa
            case '\u1473': d = '\u221a'; break; // kaa
            case '\u1491': d = '\u00db'; break; // gaa
            case '\u14AB': d = '\u00b5'; break; // maa
//          case '\u14AB': d = '\u03bc'; break; // maa
            case '\u14C8': d = '\u02c6'; break; // naa
            case '\u14F5': d = '\u007e'; break; // saa
            case '\u14DB': d = '\u02dc'; break; // laa
            case '\u152E': d = '\u00f7'; break; // jaa
            case '\u155A': d = '\u00bf'; break; // vaa
            case '\u154C': d = '\u00c7'; break; // raa
            case '\u1584': d = '\u00e7'; break; // qaa
            case '\u1594': d = '\u03a9'; break; // ngaa
//            case '\u1594': d = '\u2126'; break; // ngaa
//            case '\u1676': d = '\u00c1'; break; // NNaa
            case '\u15A5': d = '\u00c8'; break; // &aa
            case '\u1449': d = '2'; break; // p
            case '\u1466': d = '5'; break; // t
            case '\u1483': d = '4'; break; // k
            case '\u14A1': d = '['; break; // g
            case '\u14BB': d = '7'; break; // m
            case '\u14D0': d = '8'; break; // n
            case '\u1505': d = '{'; break; // s
            case '\u14EA': d = '9'; break; // l
            case '\u153E': d = '0'; break; // j
            case '\u155D': d = '='; break; // v
            case '\u1550': d = '3'; break; // r
            case '\u1585': d = '6'; break; // q
            case '\u1595': d = '1'; break; // ng
//           case '\u1596': d = 'U'; break; // NN
//           case '\u1596': d = '`'; break; // NN
            case '\u15A6': d = 'P'; break; // &
            case '\u157C': d = 'B'; break;// H
            case '1': d = '!'; break; // 1
            case '2': d = '@'; break; // 2
            case '3': d = '#'; break; // 3
            case '4': d = '$'; break; // 4
            case '5': d = '%'; break; // 5
            case '6': d = '^'; break; // 6
            case '7': d = '&'; break; // 7
            case '8': d = '*'; break; // 8
            case '9': d = '('; break; // 9
            case '0': d = ')'; break;// 0
            case ',': d = '<'; break;
            case '.': d = '>'; break;
            case '(': d = 'G'; break;
            case ')': d = 'H'; break;
            case '/': d = '\\'; break;
            case '?': d = 'V'; break;
            case '$': d = '\u00a2'; break;
            case '#': d = '\u00a3'; break;
            case '!': d = '\u00a1'; break;
            case '*': d = '\u00a7'; break;
            case '[': d = '\u00aa'; break;
            case '+': d = '\u00b0'; break;
            case '&': d = '\u00b6'; break;
            case '\u00f7': d = '\u00b7'; break; // divise
//          case '\u00f7': d = '\u2219'; break; // divise
            case ']': d = '\u00ba'; break;
            case '\u00a9': d = '\u00cc'; break; // copyright
            case '}': d = '\u0131'; break;
            case '\u00b7': d = '\u02c7'; break; // point médian
            case '\u02d9': d = '\u02db'; break; // point en chef
            case '\u166d': d = '\u2013'; break; // khi (??)
            case '=': d = '\u201a'; break;
            case '\u2154': d = '\u2021'; break; // 2/3
            case '\u00bc': d = '\u2039'; break; // 1/4
            case '\u00bd': d = '\u203A'; break; // 1/2
            case '\u00a3': d = '\u20ac'; break; // enregistré
            case '%': d = '\u221e'; break;
            case '\u2022': d = '\u2260'; break; // puce
            case '{': d = '\u25ca'; break;
            case '\u00be': d = '\ufb01'; break; // 3/4
            case '\u2153': d = '\ufb02'; break;// 1/3
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}