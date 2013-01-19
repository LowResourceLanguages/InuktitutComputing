// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File: PoliceNunacom.java
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
// Description:
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: PoliceNunacom.java,v 1.1 2009/06/19 19:38:40 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceNunacom.java,v $
// Revision 1.1  2009/06/19 19:38:40  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.14  2006/11/01 15:29:56  farleyb
// *** empty log message ***
//
// Revision 1.13  2006/10/20 17:01:26  farleyb
// *** empty log message ***
//
// Revision 1.12  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.11  2006/07/20 18:54:54  farleyb
// *** empty log message ***
//
// Revision 1.10  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.9  2006/07/12 14:45:21  farleyb
// Additions dans la table de codes, dû à l'installation d'une nouvelle version de Nunacom contenant plus de codes que l'ancienne.
//
// Revision 1.8  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, nécessaire entre autres pour le surlignage, où il faut déterminer la délimitation des mots.
//
// Revision 1.7  2006/04/24 17:16:52  farleyb
// Modifications majeures pour simplifier le transcodage et la translittération. Maintenant, les fichiers PoliceXXX.java ne contiennent que des tables.  Les méthodes sont dans Police.java
//
// Revision 1.6  2006/03/09 18:01:53  farleyb
// Diverses modifications re tables de conversion à unicode
//
// Revision 1.5  2005/12/14 17:30:03  farleyb
// *** empty log message ***
//
// Revision 1.4  2005/07/06 20:09:23  farleyb
// *** empty log message ***
//
// Revision 1.3  2005/01/05 22:04:50  farleyb
// *** empty log message ***
//
// Revision 1.2  2004/12/07 20:49:49  farleyb
// *** empty log message ***
//
// Revision 1.1 2003/10/10 06:01:10 desiletsa
// Première sauvegarde
//
// Revision 1.0 2003-06-25 13:19:57-04 farleyb
// Initial revision
//
// Revision 1.0 2002-12-03 12:39:27-05 farleyb
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

public class FontNunacom {
    
    public static String unicodesICI2Codes[][] = {

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
            { "\u158F", "q"  }, // Ni
            { "\u1671", "T" }, // Xi
            { "\u15A0", "O"  }, // &i

            // ii
            { "\u1404", "|w" }, // ii
            { "\u1404", "\u2211" }, // ii
            { "\u1432", "|W" }, // pii
            { "\u1432", "\u201e" }, // pii
            { "\u1432", "\u0084" }, // pii
            { "\u144F", "|t" }, // tii
            { "\u144F", "\u2020" }, // tii
            { "\u144F", "\u0086" }, // tii
            { "\u146E", "}r" }, // kii
            { "\u146E", "\u00ae" }, // kii
            { "\u148C", "}Q" }, // gii
            { "\u148C", "\u0152" }, // gii
            { "\u148C", "\u008c" }, // gii
            { "\u14A6", "}u" }, // mii
            { "\u14A6", "\u00a8" }, // mii
            { "\u14C3", "`i" }, // nii
            { "\u14C3", "\u00ee" }, // nii
            { "\u14F0", "+y" }, // sii
            { "\u14F0", "\u00a5" }, // sii
            { "\u14D6", "`o" }, // lii
            { "\u14D6", "\u00f8" }, // lii
            { "\u1529", "]p" }, // jii
            { "\u1529", "\u03c0" }, // jii
            { "\u1556", "|F" }, // vii
            { "\u1556", "\u00cf" }, // vii
            { "\u1547", "`E" }, // rii
            { "\u1547", "\u00b4" }, // rii
            { "\u1580", "+e" }, // qii
            { "\u1580", "\u00e9" }, // qii
            { "\u1590", "1}Q" }, // Nii
            { "\u1590", "\u0153" }, // Nii
            { "\u1590", "\u009c" }, // Nii ngii
            { "\u1672", "R}Q" }, // Xii nngii
            { "\u1672", "\u02c7" }, // Xii
            { "\u15A1", "`O" }, // &ii
            { "\u15A1", "\u00d8" }, // &ii
            // On rencontre dans la réalité les codes pour "nguu"
            // et "nnguu" codés par un code de point et la syllabe
            // courte.
            {"\u1590", "+q" }, // ngii
            {"\u1672", "+T" }, // nngii
            
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
            { "\u1591", "a"  }, // Nu
            { "\u1673", "Y"  }, // Xu
            { "\u15A2", "L"  }, // &u

            // uu
            { "\u1406", "]s" }, // uu
            { "\u1406", "\u00df" }, // uu
            { "\u1434", "]S" }, // puu
            { "\u1434", "\u00cd" }, // puu
            { "\u1451", "}g" }, // tuu
            { "\u1451", "\u00a9" }, // tuu
            { "\u1470", "|f" }, // kuu
            { "\u1470", "\u0192" }, // kuu
            { "\u1470", "\u0083" }, // kuu
            { "\u148E", "|A" }, // guu
            { "\u148E", "\u00c5" }, // guu
            { "\u14A8", "+j" }, // muu
            { "\u14A8", "\u0394" }, // muu
            { "\u14C5", "~k" }, // nuu
            { "\u14C5", "\u02da" }, // nuu
            { "\u14F2", "+h" }, // suu
            { "\u14F2", "\u02d9" }, // suu
            { "\u14D8", "~l" }, // luu
            { "\u14D8", "\u00ac" }, // luu
            { "\u152B", "+J" }, // juu
            { "\u152B", "\u00d4" }, // juu
            { "\u1558", "}K" }, // vuu
            { "\u1558", "\uf000" }, // vuu
            { "\u1549", "}D" }, // ruu
            { "\u1549", "\u00ce" }, // ruu
            { "\u1582", "3+f" }, // quu
            { "\u1582", "\u2202" }, // quu
            { "\u1592", "1+A" }, // Nuu
            { "\u1592", "\u00e5" }, // Nuu
            { "\u1674", "R+A" }, // Xuu
            { "\u1674", "\u00c1" }, // Xuu
            { "\u15A3", "~L" }, // &uu
            { "\u15A3", "\u00d2" }, // &uu
            // On rencontre dans la réalité les codes pour "quu", "nguu"
            // et "nnguu" codés par un code de point et la syllabe
            // courte.
            { "\u1582", "+d" }, // quu
            { "\u1592", "+a" }, // nguu
            { "\u1674", "+Y" }, // nnguu

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
            { "\u1593", "z" }, // Na
            { "\u1675", "U" }, // Xa
            { "\u15A4", "I" }, // &a

            // aa
            { "\u140B", "+x" }, // aa
            { "\u1439", "+X" }, // paa
            { "\u1439", "\u02db" }, // paa
            { "\u1456", "|b" }, // taa
            { "\u1456", "\u222b" }, // taa
            { "\u1473", "]v" }, // kaa
            { "\u1473", "\u221a" }, // kaa
            { "\u1491", "]Z" }, // gaa
            { "\u1491", "\u00b8" }, // gaa
            { "\u14AB", "]m" }, // maa
            { "\u14AB", "\u03bc" }, // maa
            { "\u14C8", "~N" }, // naa
            { "\u14C8", "\u02dc" }, // naa
            { "\u14C8", "\u0098" }, // naa
            { "\u14F5", "]n" }, // saa
            { "\u14F5", "\u00f1" }, // saa
            { "\u14DB", "~M" }, // laa
            { "\u14DB", "\u00c2" }, // laa
            { "\u152E", "]/" }, // jaa
            { "\u152E", "\u00f7" }, // jaa
            { "\u155A", "+?" }, // vaa
            { "\u155A", "\u00bf" }, // vaa
            { "\u154C", "}C" }, // raa
            { "\u154C", "\u00c7" }, // raa
            { "\u1584", "|c" }, // qaa
            { "\u1584", "\u00e7" }, // qaa
            { "\u1594", "1]Z" }, // Naa
            { "\u1594", "\u03a9" }, // Naa
            { "\u1676", "R]Z" }, // Xaa
            { "\u1676", "\u00dc" }, // Xaa
            { "\u15A5", "~I" }, // &aa
            { "\u15A5", "\u02c6" }, // &aa
            { "\u15A5", "\u0088" }, // &aa
            // On rencontre dans la réalité les codes pour "ngaa"
            // et "nngaa" codés par un code de point et la syllabe
            // courte.
            { "\u1594", "+z" }, // ngaa
            { "\u1676", "+U" },  // nngaa
            
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
            { "\u1595", "1" }, // N
            { "\u1596", "R" }, // X
            { "\u15A6", "P" }, // &
            { "\u157C", "B" }, // H
    };
    
    public static String unicodesICI2CodesDigits[][] = {

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
            { "0", ")"  }, // 0
    };
    
    public static String unicodesICI2CodesOthers[][] = {
            
            // autres signes
            { "(", "G"  }, // (
            { ")", "H"  }, // )
            { "?", "V"  }, // ?
            { "/", "\\" }, // /
            { "!", "\u00a1" }, //
            { "$", "\u00a2" }, // 
            { "#", "\u00a3" }, // 
            { "\u00ae", "\u00a4" }, // marque déposée
            { "*", "\u00a7" }, // 
            { "[", "\u00aa" }, // 
            { "\u00a9", "\u00b0" }, // copyright
            { "&", "\u00b6" }, // 
            { "\u00f7", "\u00b7" }, // division 
            { "]", "\u00ba" }, // 
            { "}", "\u0131" }, // 
            { "\u00d7", "\u2013" }, // multiplication
            { "\u00d7", "\u0096" }, // multiplication
            { "+", "\u201a" },
            { "+", "\u0082" },
            { "\u2154", "\u2021" }, // 2/3
            { "\u2154", "\u0087" }, // 2/3
            { "\u00bc", "\u2039" }, // 1/4
            { "\u00bc", "\u008b" }, // 1/4
            { "\u00bd", "\u203a" }, // 1/2
            { "\u00bd", "\u009b" }, // 1/2
            { "\u00a2", "\u2044" }, // centime
            { "=", "\u2260" }, // 
            { "{", "\u25ca" }, // 
            { "\u00be", "\uf001" }, // 3/4
            { "\u2153", "\uf002" }, // 1/3
            { "\u2026", "\u0085" }, // points de suspension
            { "\u2018", "\u0091" }, // guillemet-apostrophe culbuté
            { "\u2019", "\u0092" }, // guillemet-apostrophe
            { "\u201c", "\u0093" }, // guillemet-apostrophe double culbuté
            { "\u201d", "\u0094" }, // guillemet-apostrophe double
            { "\u2022", "\u0095" }, // puce
            { "\u2014", "\u0097" }, // tiret cadratin
            { "\u2122", "\u0099" }, // marque de commerce
 };
    
    public static String[][] unicodesAIPAITAI2Codes = {
        // aipaitai
        {"\u1401","xw"}, // ai
        {"\u142f","Xw"}, // pai
        {"\u144c","bw"}, // tai
        {"\u146b","vw"}, // kai
        {"\u1489","Zw"}, // gai
        {"\u14a3","mw"}, // mai
        {"\u14c0","Nw"}, // nai
        {"\u14ed","nw"}, // sai
        {"\u14d3","Mw"}, // lai
        {"\u1526","/w"}, // jai
        {"\u1553","?w"}, // vai
        {"\u1543","Cw"}, // rai
        {"\u166f","cw"}, // qai
        {"\u1670","zw"},  // ngai  
    };

    // + ] ` | } ~
    public static String dotCodes = "+]`|}~";

    public static TransCoder transcoderToUnicodeICI;
    public static TransCoder transcoderToUnicodeAIPAITAI;
    public static TransCoder transcoderToFontICI;
    public static TransCoder transcoderToFontAIPAITAI;

//    public static String wordChars = "[a-zA-FI-UW-Z[{/0-9=?+]`|}~!#-&(-*@]";
    
    public static String wordChars;
    public static Set fontChars = new HashSet();
    static {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<unicodesICI2Codes.length; i++) {
            sb.append(unicodesICI2Codes[i][1]);
            for (int j=0; j<unicodesICI2Codes[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2Codes[i][1].charAt(j)));
        }
        for (int i=0; i<unicodesICI2CodesDigits.length; i++) {
            sb.append(unicodesICI2CodesDigits[i][1]);
            for (int j=0; j<unicodesICI2CodesDigits[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2CodesDigits[i][1].charAt(j)));
        }
        for (int i=0; i<unicodesICI2CodesOthers.length; i++) {
            sb.append(unicodesICI2CodesOthers[i][1]);
        }
        wordChars = Util.prepareForRegexp(sb.toString());
        wordChars = "["+wordChars+"]";
    }
    
    static public String transcodeToUnicode(String s) {
        return transcodeToUnicode(s,null); // null = no aipaitai
    }
    
    /*
     * aipaitaiMode: String -  "aipaitai" : convertir a+i au caractère unicode AI
     */
    static public String transcodeToUnicode(String s, String aipaitaiMode) {
        int aipaitai = aipaitaiMode==null? 0 : aipaitaiMode.equals("aipaitai")? 1 : 0;
        boolean dot = false;
        int i=0,j;
        int l=s.length();
        char c,d,e;
        int sbl;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            sbl = sb.length();
            c = s.charAt(i);
            switch (c) {
            case 'w': d = '\u1403'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // i
            case 'W': d = '\u1431'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // pi
            case 't': d = '\u144E'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // ti
            case 'r': // ki
                d = '\u146D'; 
                if (dot) {sb.deleteCharAt(--sbl);}
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
                if (dot) {dot=false; d++;}
                break;
            case 'Q': // gi
                d = '\u148B'; 
                if (dot) sb.deleteCharAt(--sbl);
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
                if (dot) {dot=false; d++;}
                break;
            case 'u': d = '\u14A5'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // mi
            case 'i': d = '\u14C2'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // ni
            case 'y': d = '\u14EF'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // si
            case 'o': d = '\u14D5'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // li
            case 'p': d = '\u1528'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // ji
            case 'F': d = '\u1555'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // vi
            case 'E': // ri
                d = '\u1546'; 
                if (dot) {dot=false;sb.deleteCharAt(--sbl); d++;} 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ri = r+ri (rri)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break;
            case 'e': // qi
                d = '\u157F';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146d';
                    }
                if (dot) {dot=false; d++;}
                break; 
            case 'q': // ngi
                d = '\u158F';
                if (dot) sb.deleteCharAt(--sbl);
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
                if (dot) {dot=false; d++;}
                break;
            case 'T': d = '\u1671'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // nngi
            case 'O': d = '\u15A0'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &i
            case '\u2211': d = '\u1404'; break; // ii
            case '\u201e':
            case '\u0084': d = '\u1432'; break; // pii
            case '\u2020':
            case '\u0086': d = '\u144F'; break; // tii
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
            case '\u0152':
            case '\u008c': // gii
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
                break; // gii
            case '\u00a8': d = '\u14A6'; break; // mii
            case '\u00ee': d = '\u14C3'; break; // nii
            case '\u00a5': d = '\u14F0'; break; // sii
            case '\u00f8': d = '\u14D6'; break; // lii
            case '\u03c0': d = '\u1529'; break; // jii
            case '\u00cf': d = '\u1556'; break; // vii
            case '\u00b4': // rii
                d = '\u1547'; 
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; // rii
            case '\u00e9': // qii
                d = '\u1580'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qii = q+kii (qqii)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146e';
                    }
                break;
            case '1': // ng
                d = '\u1595';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                    sb.deleteCharAt(sbl-1);
                    d='\u1596';
                }
                break;
            case '3':  d = '\u1550'; break; // r
            case 'R':   d = '\u1596';  break; // nng
            case '\u0153':
            case '\u009c': // ngii
                d = '\u1590'; 
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
            case '\u02c7': d = '\u1672'; break; // nngii
            case '\u00d8': d = '\u15A1'; break; // &ii
            case 's': d = '\u1405'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // u
            case 'S': d = '\u1433'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // pu
            case 'g': d = '\u1450'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // tu
            case 'f': // ku
                d = '\u146F'; 
                if (dot) sb.deleteCharAt(--sbl);
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
                if (dot) {dot=false; d++;}
                break;
            case 'A': // gu
                d = '\u148D'; 
                if (dot) sb.deleteCharAt(--sbl);
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
                if (dot) {dot=false; d++;}
                break;
            case 'j': d = '\u14A7'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // mu
            case 'k': d = '\u14C4'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // nu
            case 'h': d = '\u14F1'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // su
            case 'l': d = '\u14D7'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // lu
            case 'J': d = '\u152A'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // ju
            case 'K': d = '\u1557'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // vu
            case 'D': // ru
                d = '\u1548'; 
                if (dot) {dot=false; sb.deleteCharAt(--sbl); d++;}; // ruu
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+ru = r+ru (rru)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case 'd': // qu
                d = '\u1581';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146f';
                    }
                if (dot) {dot=false; d++;}
                break; 
            case 'a': // ngu
                d = '\u1591';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1673'; // nngu
                }
                if (dot) {dot=false; d++;}
                break;
            case 'Y': d = '\u1673'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // nngu
            case 'L': d = '\u15A2'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &u
            case '\u00df': d = '\u1406'; break; // uu
            case '\u00cd': d = '\u1434'; break; // puu
            case '\u00a9': d = '\u1451'; break; // tuu
            case '\u0192':
            case '\u0083': // kuu
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
                if (dot) sb.deleteCharAt(--sbl);
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
                if (dot) {dot=false; d++;}
                break; // guu
            case '\u0394': d = '\u14A8'; break; // muu
            case '\u02da': d = '\u14C5'; break; // nuu
            case '\u02d9': d = '\u14F2'; break; // suu
            case '\u00ac': d = '\u14D8'; break; // luu
            case '\u00d4': d = '\u152B'; break; // juu
            case '\uf000': d = '\u1558'; break; // vuu
            case '\u00ce': // ruu
                d = '\u1549'; 
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu = r+ruu (rruu)
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
            case '\u00e5': // nguu
                d = '\u1592'; 
                if (dot) sb.deleteCharAt(--sbl);
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
                if (dot) {dot=false; d++;}
                break;
            case '\u00c1': d = '\u1674'; break; // nnguu
            case '\u00d2': d = '\u15A3'; break; // &uu
            
            case 'x':  // a
                d = '\u140a';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1401'; break;
                        default: sb.append(d); d = '\u1403'; break; // ai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case 'X': // pa
                d = '\u1438';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u142F'; break;
                        default: sb.append(d); d = '\u1403'; break; // pai
                        }
                        i=j;
                        break;
                    }
                } 
                if (dot) {dot=false; d++;}
                break;
            case 'b': // ta
                d = '\u1455';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u144C'; break;
                        default: sb.append(d); d = '\u1403'; break; // tai
                        }
                        i=j;
                        break;
                    }
                } 
                if (dot) {dot=false; d++;}
                break;
            case 'v': // ka
                d = '\u1472';
                boolean k2q = false;
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    sb.deleteCharAt(sbl-2);
                    sb.append('\u1585');
                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
                    sb.deleteCharAt(sbl-1);
                } else if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1583';
                    k2q = true;
                }
               j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : 
                           d = k2q? '\u166f' : '\u146B'; break; // kai
                        default: sb.append(d); d = '\u1403'; break; // ka+i
                        }
                        i=j;
                        break;
                    }
                } 
                if (dot) {dot=false; d++;}
                break;
            case 'Z': // ga
                d = '\u1490';
                if (dot) sb.deleteCharAt(--sbl);
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
                            } else 
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
                    }
                } else if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                    sb.deleteCharAt(sbl-1);
                   d = '\u1593'; // nga
                } else  if (sbl != 0 && sb.charAt(sbl-1)=='\u1596') {
                    sb.deleteCharAt(sbl-1);
                    d = '\u1675'; // nnga
                }
                if (dot) {dot=false; d++;}
                break;
            case 'm': // ma
                d = '\u14AA';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14A3'; break;
                        default: sb.append(d); d = '\u1403'; break; // mai
                        }
                        i=j;
                        break;
                    }
                } 
                if (dot) {dot=false; d++;}
                break;
            case 'N': // na
                d = '\u14C7';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14C0'; break;
                        default: sb.append(d); d = '\u1403'; break; // nai
                        }
                        i=j;
                        break;
                    }
                } 
                if (dot) {dot=false; d++;}
                break;
            case 'n': // sa
                d = '\u14F4';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14ED'; break;
                        default: sb.append(d); d = '\u1403'; break; // sai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case 'M': // la
                d = '\u14DA';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u14D3'; break;
                        default: sb.append(d); d = '\u1403'; break; // lai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case '/': // ja
                d = '\u152d';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1526'; break;
                        default: sb.append(d); d = '\u1403'; break; // jai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case '?': // va
                d = '\u1559';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1553'; break;
                        default: sb.append(d); d = '\u1403'; break; // vai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case 'C': // ra
                d = '\u154b';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+r_ = r+r_ (rr_)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : d = '\u1542'; break;
                        default: sb.append(d); d = '\u1403'; break; // rai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
           case 'c': // qa
               boolean precr = false;
               d = '\u1583';
               if (dot) sb.deleteCharAt(--sbl);
               if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qa = q+ka (qqa)
                   sb.deleteCharAt(sbl-1);
                   sb.append('\u1585');
                   d = '\u1472';
                   precr = true;
                   }
               j=i+1;
               if (j < l) {
                   e = s.charAt(j);
                   switch (e) {
                   case 'w': 
                       switch (aipaitai) {
                       case 1 :
                           if (precr)
                               d = '\u146b'; // kai
                           else
                               d = '\u166f'; // qai
                           break;
                       default: 
                           sb.append(d); 
                           d = '\u1403'; 
                       break; 
                       }
                       i=j;
                       break;
                   }
               }
               if (dot) {dot=false; d++;}
               break;
            case 'z': // nga
                d = '\u1593';
                if (dot) sb.deleteCharAt(--sbl);
                boolean precnng = false;
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sbl-1);
                        d = '\u1675'; // nnga
                        precnng = true;
                        break;
                    }
                }
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 :
                            if (precnng) {
                                sb.append('\u1596');
                                d = '\u1489';
                            } else
                                d = '\u1670'; // ngai
                            break;
                        default: sb.append(d); d = '\u1403'; break; // ngai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case 'U': // nnga
                d = '\u1675';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case 'w': 
                        switch (aipaitai) {
                        case 1 : sb.append('\u1596'); d = '\u1489'; break;
                        default: sb.append('\u1675'); d = '\u1403'; break; // nngai
                        }
                        i=j;
                        break;
                    }
                }
                if (dot) {dot=false; d++;}
                break;
            case 'I': d = '\u15A4'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &a
            case '\u02db': d = '\u1439'; break; // paa
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
            case '\u00b8': d = '\u1491'; break; // gaa
            case '\u03bc': d = '\u14AB'; break; // maa
            case '\u02dc':
            case '\u0098': d = '\u14C8'; break; // naa
            case '\u00f1': d = '\u14F5'; break; // saa
            case '\u00c2': d = '\u14DB'; break; // laa
            case '\u00f7': d = '\u152E'; break; // jaa
            case '\u00bf': d = '\u155A'; break; // vaa
            case '\u00c7': // raa
                d = '\u154C'; 
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break;
            case '\u00e7': // qaa
                d = '\u1584'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qaa = q+kaa (qqaa)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u1473';
                    }
                break;
            case '\u03a9': // ngaa
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
                break; // ngaa
            case '\u00dc': d = '\u1676'; break; // nngaa
            case '\u02c6': 
            case '\u0088': d = '\u15A5'; break; // &aa
            case '2': d = '\u1449'; break;
            case '5': d = '\u1466'; break;
            case '4': d = '\u1483'; break;
            case '[': d = '\u14A1'; break;
            case '7': d = '\u14BB'; break;
            case '8': d = '\u14D0'; break;
            case '{': d = '\u1505'; break;
            case '9': d = '\u14EA'; break;
            case '0': d = '\u153E'; break;
            case '=': d = '\u155D'; break;
            case '6': d = '\u1585'; break;
            case 'P': d = '\u15A6'; break;
            case 'B': d = '\u157C'; break;
            case '!': d = '1'; break;
            case '@': d = '2'; break;
            case '#': d = '3'; break;
            case '$': d = '4'; break;
            case '%': d = '5'; break;
            case '^': d = '6'; break;
            case '&': d = '7'; break;
            case '*': d = '8'; break;
            case '(': d = '9'; break;
            case ')': d = '0'; break;
            case 'G': d = '('; break;
            case 'H': d = ')'; break;
            case 'V': d = '?'; break;
            case '\\': d = '/'; break;
            case '\u00a1': d = '!'; break;
            case '\u00a2': d = '$'; break;
            case '\u00a3': d = '#'; break;
            case '\u00a4': d = '\u00ae'; break;
            case '\u00a7': d = '*'; break;
            case '\u00aa': d = '['; break;
            case '\u00b0': d = '\u00a9'; break;
            case '\u00b6': d = '&'; break;
            case '\u00b7': d = '\u00f7'; break;
            case '\u00ba': d = ']'; break;
            case '\u0131': d = '}'; break;
            case '\u2013': d = '\u00d7'; break;
            case '\u0096': d = '\u00d7'; break;
            case '\u201a': d = '+'; break;
            case '\u0082': d = '+'; break;
            case '\u2021': d = '\u2154'; break;
            case '\u0087': d = '\u2154'; break;
            case '\u2039': d = '\u00bc'; break;
            case '\u008b': d = '\u00bc'; break;
            case '\u203a': d = '\u00bd'; break;
            case '\u009b': d = '\u00bd'; break;
            case '\u2044': d = '\u00a2'; break;
            case '\u2260': d = '='; break;
            case '\u25ca': d = '{'; break;
            case '\uf001': d = '\u00be'; break;
            case '\uf002': d = '\u2153'; break;
            case '\u0085': d = '\u2026'; break;
            case '\u0091': d = '\u2018'; break;
            case '\u0092': d = '\u2019'; break;
            case '\u0093': d = '\u201c'; break;
            case '\u0094': d = '\u201d'; break;
            case '\u0095': d = '\u2022'; break;
            case '\u0097': d = '\u2014'; break;
            case '\u0099': d = '\u2122'; break;
            case '|': 
            case '}':
            case '`':
            case '+':
            case ']':
            case '~':
                d=c; dot = true; break;
            default: if (dot) {
                dot=false;
            }
            d=c;
            break;
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
            case '\u158F': d = 'q'; break; // Ni
            case '\u1671': d = 'T'; break; // Xi
            case '\u15A0': d = 'O'; break; // &i
            case '\u1404': sb.append('|'); d = 'w'; break; // ii
//          case '\u1404': d = '\u2211'; break; // ii
            case '\u1432': sb.append('|'); d = 'W'; break; // pii
//          case '\u1432': d = '\u201e'; break; // pii
//          case '\u1432': d = '\u0084'; break; // pii
            case '\u144F': sb.append('|'); d = 't'; break; // tii
//          case '\u144F': d = '\u2020'; break; // tii
//          case '\u144F': d = '\u0086'; break; // tii
            case '\u146E': sb.append('}'); d = 'r'; break; // kii
//          case '\u146E': d = '\u00ae'; break; // kii
            case '\u148C': sb.append('}'); d = 'Q'; break; // gii
//          case '\u148C': d = '\u0152'; break; // gii
//          case '\u148C': d = '\u008c'; break; // gii
            case '\u14A6': sb.append('}'); d = 'u'; break; // mii
//          case '\u14A6': d = '\u00a8'; break; // mii
            case '\u14C3': sb.append('`'); d = 'i'; break; // nii
//          case '\u14C3': d = '\u00ee'; break; // nii
            case '\u14F0': sb.append('+'); d = 'y'; break; // sii
//          case '\u14F0': d = '\u00a5'; break; // sii
            case '\u14D6': sb.append('`'); d = 'o'; break; // lii
//          case '\u14D6': d = '\u00f8'; break; // lii
            case '\u1529': sb.append(']'); d = 'p'; break; // jii
//          case '\u1529': d = '\u03c0'; break; // jii
            case '\u1556': sb.append('|'); d = 'F'; break; // vii
//          case '\u1556': d = '\u00cf'; break; // vii
            case '\u1547': sb.append('`'); d = 'E'; break; // rii
//          case '\u1547': d = '\u00b4'; break; // rii
            case '\u1580': sb.append('+'); d = 'e'; break; // qii
//          case '\u1580': d = '\u00e9'; break; // qii
            case '\u1590': sb.append("1}"); d = 'Q'; break; // Nii
//          case '\u1590': sb.append('+'); d = 'q'; break; // ngii
//          case '\u1590': d = '\u0153'; break; // Nii
//          case '\u1590': d = '\u009c'; break; // Nii ngii
            case '\u1672': sb.append("R}" ); d = 'Q'; break; // Xii nngii
//          case '\u1672': sb.append('+'); d = 'T'; break; // nngii
//          case '\u1672': d = '\u02c7'; break; // Xii
            case '\u15A1': sb.append('`'); d = 'O'; break; // &ii
//          case '\u15A1': d = '\u00d8'; break; // &ii
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
            case '\u1591': d = 'a'; break; // Nu
            case '\u1673': d = 'Y'; break; // Xu
            case '\u15A2': d = 'L'; break; // &u
            case '\u1406': sb.append(']'); d = 's'; break; // uu
//          case '\u1406': d = '\u00df'; break; // uu
            case '\u1434': sb.append(']'); d = 'S'; break; // puu
//          case '\u1434': d = '\u00cd'; break; // puu
            case '\u1451': sb.append('}'); d = 'g'; break; // tuu
//          case '\u1451': d = '\u00a9'; break; // tuu
            case '\u1470': sb.append('|'); d = 'f'; break; // kuu
//          case '\u1470': d = '\u0192'; break; // kuu
//          case '\u1470': d = '\u0083'; break; // kuu
            case '\u148E': sb.append('|'); d = 'A'; break; // guu
//          case '\u148E': d = '\u00c5'; break; // guu
            case '\u14A8': sb.append('+'); d = 'j'; break; // muu
//          case '\u14A8': d = '\u0394'; break; // muu
            case '\u14C5': sb.append('~'); d = 'k'; break; // nuu
//          case '\u14C5': d = '\u02da'; break; // nuu
            case '\u14F2': sb.append('+'); d = 'h'; break; // suu
//          case '\u14F2': d = '\u02d9'; break; // suu
            case '\u14D8': sb.append('~'); d = 'l'; break; // luu
//          case '\u14D8': d = '\u00ac'; break; // luu
            case '\u152B': sb.append('+'); d = 'J'; break; // juu
//          case '\u152B': d = '\u00d4'; break; // juu
            case '\u1558': sb.append('}'); d = 'K'; break; // vuu
//          case '\u1558': d = '\uf000'; break; // vuu
            case '\u1549': sb.append('}'); d = 'D'; break; // ruu
//          case '\u1549': d = '\u00ce'; break; // ruu
            case '\u1582': sb.append("3+"); d = 'f'; break; // quu
//          case '\u1582': sb.append('+'); d = 'd'; break; // quu
//          case '\u1582': d = '\u2202'; break; // quu
            case '\u1592': sb.append("1+"); d = 'A'; break; // Nuu
//          case '\u1592': sb.append('+'); d = 'a'; break; // nguu
//          case '\u1592': d = '\u00e5'; break; // Nuu
            case '\u1674': sb.append("R+"); d = 'A'; break; // Xuu
//          case '\u1674': sb.append('+'); d = 'Y'; break; // nnguu
//          case '\u1674': d = '\u00c1'; break; // Xuu
            case '\u15A3': sb.append('~'); d = 'L'; break; // &uu
//          case '\u15A3': d = '\u00d2'; break; // &uu
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
            case '\u1593': d = 'z'; break; // Na
            case '\u1675': d = 'U'; break; // Xa
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
            case '\u1542': sb.append('C'); d = 'w'; break; // rai
            case '\u166f': sb.append('c'); d = 'w'; break; // qai
            case '\u1670': sb.append('z'); d = 'w'; break;  // ngai  
            case '\u140B': sb.append('+'); d = 'x'; break; // aa
            case '\u1439': sb.append('+'); d = 'X'; break; // paa
//          case '\u1439': d = '\u02db'; break; // paa
            case '\u1456': sb.append('|'); d = 'b'; break; // taa
//          case '\u1456': d = '\u222b'; break; // taa
            case '\u1473': sb.append(']'); d = 'v'; break; // kaa
//          case '\u1473': d = '\u221a'; break; // kaa
            case '\u1491': sb.append(']'); d = 'Z'; break; // gaa
//          case '\u1491': d = '\u00b8'; break; // gaa
            case '\u14AB': sb.append(']'); d = 'm'; break; // maa
//          case '\u14AB': d = '\u03bc'; break; // maa
            case '\u14C8': sb.append('~'); d = 'N'; break; // naa
//          case '\u14C8': d = '\u02dc'; break; // naa
//          case '\u14C8': d = '\u0098'; break; // naa
            case '\u14F5': sb.append(']'); d = 'n'; break; // saa
//          case '\u14F5': d = '\u00f1'; break; // saa
            case '\u14DB': sb.append('~'); d = 'M'; break; // laa
//          case '\u14DB': d = '\u00c2'; break; // laa
            case '\u152E': sb.append(']'); d = '/'; break; // jaa
//          case '\u152E': d = '\u00f7'; break; // jaa
            case '\u155A': sb.append('+'); d = '?'; break; // vaa
//          case '\u155A': d = '\u00bf'; break; // vaa
            case '\u154C': sb.append('}'); d = 'C'; break; // raa
//          case '\u154C': d = '\u00c7'; break; // raa
            case '\u1584': sb.append('|'); d = 'c'; break; // qaa
//          case '\u1584': d = '\u00e7'; break; // qaa
            case '\u1594': sb.append("1]"); d = 'Z'; break; // Naa
//          case '\u1594': sb.append('+'); d = 'z'; break; // ngaa
//          case '\u1594': d = '\u03a9'; break; // Naa
            case '\u1676': sb.append("R]"); d = 'Z'; break; // Xaa
//          case '\u1676': sb.append('+'); d = 'U'; break;  // nngaa
//          case '\u1676': d = '\u00dc'; break; // Xaa
            case '\u15A5': sb.append('~'); d = 'I'; break; // &aa
//          case '\u15A5': d = '\u02c6'; break; // &aa
//          case '\u15A5': d = '\u0088'; break; // &aa
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
            case '\u1595': d = '1'; break; // N
            case '\u1596': d = 'R'; break; // X
            case '\u15A6': d = 'P'; break; // &
            case '\u157C': d = 'B'; break; // H
            case '1': d = '!'; break; // 1
            case '2': d = '@'; break; // 2
            case '3': d = '#'; break; // 3
            case '4': d = '$'; break; // 4
            case '5': d = '%'; break; // 5
            case '6': d = '^'; break; // 6
            case '7': d = '&'; break; // 7
            case '8': d = '*'; break; // 8
            case '9': d = '('; break; // 9
            case '0': d = ')'; break; // 0
            case '(': d = 'G'; break; // (
            case ')': d = 'H'; break; // )
            case '?': d = 'V'; break; // ?
            case '/': d = '\\'; break; // /
            case '!': d = '\u00a1'; break; //
            case '$': d = '\u00a2'; break; // 
            case '#': d = '\u00a3'; break; // 
            case '\u00ae': d = '\u00a4'; break; // marque déposée
            case '*': d = '\u00a7'; break; // 
            case '[': d = '\u00aa'; break; // 
            case '\u00a9': d = '\u00b0'; break; // copyright
            case '&': d = '\u00b6'; break; // 
            case '\u00f7': d = '\u00b7'; break; // division 
            case ']': d = '\u00ba'; break; // 
            case '}': d = '\u0131'; break; // 
            case '\u00d7': d = '\u2013'; break; // multiplication
//          case '\u00d7': d = '\u0096'; break; // multiplication
            case '+': d = '\u201a'; break;
//          case '+': d = '\u0082'; break;
            case '\u2154': d = '\u2021'; break; // 2/3
//          case '\u2154': d = '\u0087'; break; // 2/3
            case '\u00bc': d = '\u2039'; break; // 1/4
//          case '\u00bc': d = '\u008b'; break; // 1/4
            case '\u00bd': d = '\u203a'; break; // 1/2
//          case '\u00bd': d = '\u009b'; break; // 1/2
            case '\u00a2': d = '\u2044'; break; // centime
            case '=': d = '\u2260'; break; // 
            case '{': d = '\u25ca'; break; // 
            case '\u00be': d = '\uf001'; break; // 3/4
            case '\u2153': d = '\uf002'; break; // 1/3
            case '\u2026': d = '\u0085'; break; // points de suspension
            case '\u2018': d = '\u0091'; break; // guillemet-apostrophe culbuté
            case '\u2019': d = '\u0092'; break; // guillemet-apostrophe
            case '\u201c': d = '\u0093'; break; // guillemet-apostrophe double culbuté
            case '\u201d': d = '\u0094'; break; // guillemet-apostrophe double
            case '\u2022': d = '\u0095'; break; // puce
            case '\u2014': d = '\u0097'; break; // tiret cadratin
            case '\u2122': d = '\u0099'; break; // marque de commerce
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}