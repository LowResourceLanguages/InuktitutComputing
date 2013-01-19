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
// $Id: PoliceEmiinuktitut.java,v 1.1 2009/06/19 19:38:36 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceEmiinuktitut.java,v $
// Revision 1.1  2009/06/19 19:38:36  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.1  2006/12/21 20:58:32  farleyb
// *** empty log message ***
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

public class FontEmiinuktitut {
    
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
            case '\u0077': d = '\u1403'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // i
            case '\u0057': d = '\u1431'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // pi
            case '\u0074': d = '\u144E'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // ti
            case '\u0072': // ki
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
            case '\u0051': // gi
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
            case '\u0075': d = '\u14A5'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // mi
            case '\u0069': d = '\u14C2'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // ni
            case '\u0079': d = '\u14EF'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // si
            case '\u006f': d = '\u14D5'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // li
            case '\u0070': d = '\u1528'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // ji
            case '\u005b': d = '\u1555'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // vi
            case '\u0045': // ri
                d = '\u1546'; 
                if (dot) {dot=false;sb.deleteCharAt(--sbl); d++;} 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ri = r+ri (rri)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break;
            case '\u0065': // qi
                d = '\u157F';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146d';
                    }
                if (dot) {dot=false; d++;}
                break; 
            case '\u0071': // ngi
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
            case '\u004f': d = '\u15A0'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &i
            case '\u00b3': d = '\u1404'; break; // ii
            case '\u0084': d = '\u1432'; break; // pii
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
            case '\u00d0': d = '\u14A6'; break; // mii
            case '\u00cb': d = '\u14C3'; break; // nii
            case '\u00a5': d = '\u14F0'; break; // sii
            case '\u00f8': d = '\u14D6'; break; // lii
            case '\u00db': d = '\u1529'; break; // jii
            case '\u0093': d = '\u1556'; break; // vii
            case '\u00ec': // rii
                d = '\u1547'; 
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break;
            case '\u00d6': // qii
                d = '\u1580'; 
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qii = q+kii (qqii)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146e';
                    }
                break;
            case '\u0031': // ng
                d = '\u1595';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                    sb.deleteCharAt(sbl-1);
                    d='\u1596';
                }
                break;
            case '\u0033':  d = '\u1550'; break; // r
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
            case '\u00d8': d = '\u15A1'; break; // &ii
            case '\u0073': d = '\u1405'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // u
            case '\u0053': d = '\u1433'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // pu
            case '\u0067': d = '\u1450'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // tu
            case '\u0066': // ku
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
            case '\u0041': // gu
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
            case '\u006a': d = '\u14A7'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // mu
            case '\u006b': d = '\u14C4'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // nu
            case '\u0068': d = '\u14F1'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // su
            case '\u006c': d = '\u14D7'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // lu
            case '\u004a': d = '\u152A'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // ju
            case '\u004b': d = '\u1557'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // vu
            case '\u0044': // ru
                d = '\u1548'; 
                if (dot) {dot=false; sb.deleteCharAt(--sbl); d++;}; // ruu
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+ru = r+ru (rru)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break; 
            case '\u0064': // qu
                d = '\u1581';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146f';
                    }
                if (dot) {dot=false; d++;}
                break; 
            case '\u0061': // ngu
                d = '\u1591';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1673'; // nngu
                }
                if (dot) {dot=false; d++;}
                break;
            case '\u004c': d = '\u15A2'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &u
            case '\u00df': d = '\u1406'; break; // uu
            case '\u00cd': d = '\u1434'; break; // puu
            case '\u00a9': d = '\u1451'; break; // tuu
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
            case '\u00bd': d = '\u14A8'; break; // muu
            case '\u00be': d = '\u14C5'; break; // nuu
            case '\u00bc': d = '\u14F2'; break; // suu
            case '\u00d1': d = '\u14D8'; break; // luu
            case '\u00d4': d = '\u152B'; break; // juu
            case '\u0090': d = '\u1558'; break; // vuu
            case '\u00ce': // ruu
                d = '\u1549'; 
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu = r+ruu (rruu)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                break;
            case '\u00f0': // quu
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
            case '\u00d2': d = '\u15A3'; break; // &uu
            
            case '\u0078':  // a
                d = '\u140a';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u0058': // pa
                d = '\u1438';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u0062': // ta
                d = '\u1455';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u0076': // ka
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
                    case '\u0077': 
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
            case '\u005a': // ga
                d = '\u1490';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u006d': // ma
                d = '\u14AA';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u004e': // na
                d = '\u14C7';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u006e': // sa
                d = '\u14F4';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u004d': // la
                d = '\u14DA';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u002f': // ja
                d = '\u152d';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u003f': // va
                d = '\u1559';
                if (dot) sb.deleteCharAt(--sbl);
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
            case '\u0043': // ra
                d = '\u154b';
                if (dot) sb.deleteCharAt(--sbl);
                if (sbl!= 0 && sb.charAt(sbl-1)=='\u1585') { // q+r_ = r+r_ (rr_)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u0077': 
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
           case '\u0063': // qa
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
                   case '\u0077': 
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
            case '\u007a': // nga
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
                    case '\u0077': 
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
            case '\u0049': d = '\u15A4'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &a
            case '\u0096': d = '\u1439'; break; // paa
            case '\u00c8': d = '\u1456'; break; // taa
            case '\u00c4': // kaa
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
            case '\u00d7': d = '\u1491'; break; // gaa
            case '\u00b5': d = '\u14AB'; break; // maa
            case '\u00cc': d = '\u14C8'; break; // naa
            case '\u00e4': d = '\u14F5'; break; // saa
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
            case '\u00c0': // ngaa
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
            case '\u00ee': d = '\u15A5'; break; // &aa
            
            case '\u0032': d = '\u1449'; break; //p
            case '\u0035': d = '\u1466'; break; //t
            case '\u0034': d = '\u1483'; break; //k
            case '\u003d': d = '\u14A1'; break; //g
            case '\u0037': d = '\u14BB'; break; //m
            case '\u0038': d = '\u14D0'; break; //n
            case '\u002b': d = '\u1505'; break; //s
            case '\u0039': d = '\u14EA'; break; //l
            case '\u0030': d = '\u153E'; break; //j
            case '\u007b': d = '\u155D'; break; //v
            case '\u0036': d = '\u1585'; break; //q
            case '\u0050': d = '\u15A6'; break; //&
            case '\u0042': d = '\u157C'; break; //H
            case '\u00eb': d = '\u1596'; break; //nng
            case '\u0021': d = '1'; break;
            case '\u0040': d = '2'; break;
            case '\u0023': d = '3'; break;
            case '\u0024': d = '4'; break;
            case '\u0025': d = '5'; break;
            case '\u005e': d = '6'; break;
            case '\u0026': d = '7'; break;
            case '\u002a': d = '8'; break;
            case '\u0028': d = '9'; break;
            case '\u0029': d = '0'; break;
            case '\u0047': d = '('; break;
            case '\u0048': d = ')'; break;
            case '\u0056': d = '?'; break;
            case '\u0046': d = '/'; break;
            case '\u00a3': d = '#'; break;
            case '\u0095': d = '*'; break;
            case '\u005d': d = '['; break;
            case '\u007d': d = ']'; break;
            case '\u003c': d = '\''; break;
            case '\u003e': d = '\"'; break;
            case '\u0054': d = '+'; break;
            case '\u0055': d = '='; break;
            case '\u00a1': d = '!'; break;
            case '\u0052': d = '$'; break;
            case '\u00a6': d = '\u0025'; break;
            case '\u007e': d = '\u00b0'; break; //degre
            case '\u0097': d = '\u2014'; break; //tiret cadratin
            case '\u00a7': d = '\u2022'; break; //puce
            case '\u00ad': d = '\u2212'; break; //signe moins
            case '\u00b2': d = '\u00f7'; break; //division
            case '\u0022': d = '\u201d'; break; //guillemet-apostrophe double
            case '\u00c6': d = '\u201c'; break; //guillemet-apostrophe double culbuté
            case '\'': d = '\u2019'; break; //guillemet-apostrophe
            case '\u00e6': d = '\u2018'; break; //guillemet-apostrophe culbuté
            case '\u0060':
            case '\u0091':
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
            case '\u1403': d = '\u0077'; break; // i
            case '\u1431': d = '\u0057'; break; // pi
            case '\u144E': d = '\u0074'; break; // ti
            case '\u146D': d = '\u0072'; break; // ki
            case '\u148B': d = '\u0051'; break; // gi
            case '\u14A5': d = '\u0075'; break; // mi
            case '\u14C2': d = '\u0069'; break; // ni
            case '\u14EF': d = '\u0079'; break; // si
            case '\u14D5': d = '\u006f'; break; // li
            case '\u1528': d = '\u0070'; break; // ji
            case '\u1555': d = '\u005b'; break; // vi
            case '\u1546': d = '\u0045'; break; // ri
            case '\u157F': d = '\u0065'; break; // qi
            case '\u158F': d = '\u0071'; break; // ngi
            case '\u1671': sb.append('\u0038'); d = '\u0071'; break; // nngi
            case '\u15A0': d = '\u004f'; break; // &i
//            case '\u1404': sb.append('|'); d = 'w'; break; // ii
          case '\u1404': d = '\u00b3'; break; // ii
//            case '\u1432': sb.append('|'); d = 'W'; break; // pii
//          case '\u1432': d = '\u201e'; break; // pii
          case '\u1432': d = '\u0084'; break; // pii
//            case '\u144F': sb.append('|'); d = 't'; break; // tii
//          case '\u144F': d = '\u2020'; break; // tii
          case '\u144F': d = '\u0086'; break; // tii
//            case '\u146E': sb.append('}'); d = 'r'; break; // kii
          case '\u146E': d = '\u00ae'; break; // kii
//            case '\u148C': sb.append('}'); d = 'Q'; break; // gii
//          case '\u148C': d = '\u0152'; break; // gii
          case '\u148C': d = '\u008c'; break; // gii
//            case '\u14A6': sb.append('}'); d = 'u'; break; // mii
          case '\u14A6': d = '\u00d0'; break; // mii
//            case '\u14C3': sb.append('`'); d = 'i'; break; // nii
          case '\u14C3': d = '\u00cb'; break; // nii
//            case '\u14F0': sb.append('+'); d = 'y'; break; // sii
          case '\u14F0': d = '\u00a5'; break; // sii
//            case '\u14D6': sb.append('`'); d = 'o'; break; // lii
          case '\u14D6': d = '\u00f8'; break; // lii
//            case '\u1529': sb.append(']'); d = 'p'; break; // jii
          case '\u1529': d = '\u00db'; break; // jii
//            case '\u1556': sb.append('|'); d = 'F'; break; // vii
          case '\u1556': d = '\u0093'; break; // vii
//            case '\u1547': sb.append('`'); d = 'E'; break; // rii
          case '\u1547': d = '\u00ec'; break; // rii
//            case '\u1580': sb.append('+'); d = 'e'; break; // qii
          case '\u1580': d = '\u00d6'; break; // qii
//            case '\u1590': sb.append("1}"); d = 'Q'; break; // ngii
//          case '\u1590': sb.append('+'); d = 'q'; break; // ngii
//          case '\u1590': d = '\u0153'; break; // Nii
          case '\u1590': d = '\u009c'; break; // Nii ngii
//            case '\u1672': sb.append("R}" ); d = 'Q'; break; // nngii
//          case '\u1672': sb.append('+'); d = 'T'; break; // nngii
          case '\u1672': sb.append('\u0038'); d = '\u009c'; break; // nngii
//            case '\u15A1': sb.append('`'); d = 'O'; break; // &ii
          case '\u15A1': d = '\u00d8'; break; // &ii
            case '\u1405': d = '\u0073'; break; // u
            case '\u1433': d = '\u0053'; break; // pu
            case '\u1450': d = '\u0067'; break; // tu
            case '\u146F': d = '\u0066'; break; // ku
            case '\u148D': d = '\u0041'; break; // gu
            case '\u14A7': d = '\u006a'; break; // mu
            case '\u14C4': d = '\u006b'; break; // nu
            case '\u14F1': d = '\u0068'; break; // su
            case '\u14D7': d = '\u006c'; break; // lu
            case '\u152A': d = '\u004a'; break; // ju
            case '\u1557': d = '\u004b'; break; // vu
            case '\u1548': d = '\u0044'; break; // ru
            case '\u1581': d = '\u0064'; break; // qu
            case '\u1591': d = '\u0061'; break; // ngu
            case '\u1673': sb.append('\u0038'); d = '\u0061'; break; // nngu
            case '\u15A2': d = '\u004c'; break; // &u
//            case '\u1406': sb.append(']'); d = 's'; break; // uu
          case '\u1406': d = '\u00df'; break; // uu
//            case '\u1434': sb.append(']'); d = 'S'; break; // puu
          case '\u1434': d = '\u00cd'; break; // puu
//            case '\u1451': sb.append('}'); d = 'g'; break; // tuu
          case '\u1451': d = '\u00a9'; break; // tuu
//            case '\u1470': sb.append('|'); d = 'f'; break; // kuu
//          case '\u1470': d = '\u0192'; break; // kuu
          case '\u1470': d = '\u0083'; break; // kuu
//            case '\u148E': sb.append('|'); d = 'A'; break; // guu
          case '\u148E': d = '\u00c5'; break; // guu
//            case '\u14A8': sb.append('+'); d = 'j'; break; // muu
          case '\u14A8': d = '\u00bd'; break; // muu
//            case '\u14C5': sb.append('~'); d = 'k'; break; // nuu
          case '\u14C5': d = '\u00be'; break; // nuu
//            case '\u14F2': sb.append('+'); d = 'h'; break; // suu
          case '\u14F2': d = '\u00bc'; break; // suu
//            case '\u14D8': sb.append('~'); d = 'l'; break; // luu
          case '\u14D8': d = '\u00d1'; break; // luu
//            case '\u152B': sb.append('+'); d = 'J'; break; // juu
          case '\u152B': d = '\u00d4'; break; // juu
//            case '\u1558': sb.append('}'); d = 'K'; break; // vuu
          case '\u1558': d = '\u0090'; break; // vuu
//            case '\u1549': sb.append('}'); d = 'D'; break; // ruu
          case '\u1549': d = '\u00ce'; break; // ruu
//            case '\u1582': sb.append("3+"); d = 'f'; break; // quu
//          case '\u1582': sb.append('+'); d = 'd'; break; // quu
          case '\u1582': d = '\u00f0'; break; // quu
//            case '\u1592': sb.append("1+"); d = 'A'; break; // Nuu
//          case '\u1592': sb.append('+'); d = 'a'; break; // nguu
          case '\u1592': d = '\u00e5'; break; // nguu
//            case '\u1674': sb.append("R+"); d = 'A'; break; // Xuu
//          case '\u1674': sb.append('+'); d = 'Y'; break; // nnguu
          case '\u1674': sb.append('\u0038'); d = '\u00e5'; break; // nnguu
//            case '\u15A3': sb.append('~'); d = 'L'; break; // &uu
          case '\u15A3': d = '\u00d2'; break; // &uu
            case '\u140A': d = '\u0078'; break; // a
            case '\u1438': d = '\u0058'; break; // pa
            case '\u1455': d = '\u0062'; break; // ta
            case '\u1472': d = '\u0076'; break; // ka
            case '\u1490': d = '\u005a'; break; // ga
            case '\u14AA': d = '\u006d'; break; // ma
            case '\u14C7': d = '\u004e'; break; // na
            case '\u14F4': d = '\u006e'; break; // sa
            case '\u14DA': d = '\u004d'; break; // la
            case '\u152D': d = '\u002f'; break; // ja
            case '\u1559': d = '\u003f'; break; // va
            case '\u154B': d = '\u0043'; break; // ra
            case '\u1583': d = '\u0063'; break; // qa
            case '\u1593': d = '\u007a'; break; // Na
            case '\u1675': sb.append('\u0038'); d = '\u007a'; break; // nnga
            case '\u15A4': d = '\u0049'; break; // &a
            case '\u1401': sb.append('\u0078'); d = '\u0077'; break; // ai
            case '\u142f': sb.append('\u0058'); d = '\u0077'; break; // pai
            case '\u144c': sb.append('\u0062'); d = '\u0077'; break; // tai
            case '\u146b': sb.append('\u0076'); d = '\u0077'; break; // kai
            case '\u1489': sb.append('\u005a'); d = '\u0077'; break; // gai
            case '\u14a3': sb.append('\u006d'); d = '\u0077'; break; // mai
            case '\u14c0': sb.append('\u004e'); d = '\u0077'; break; // nai
            case '\u14ed': sb.append('\u006e'); d = '\u0077'; break; // sai
            case '\u14d3': sb.append('\u004d'); d = '\u0077'; break; // lai
            case '\u1526': sb.append('\u002f'); d = '\u0077'; break; // jai
            case '\u1553': sb.append('\u003f'); d = '\u0077'; break; // vai
            case '\u1543': sb.append('\u0043'); d = '\u0077'; break; // rai
            case '\u1542': sb.append('\u0043'); d = '\u0077'; break; // rai
            case '\u166f': sb.append('\u0063'); d = '\u0077'; break; // qai
            case '\u1670': sb.append('\u007a'); d = '\u0077'; break;  // ngai  
//            case '\u140B': sb.append('+'); d = 'x'; break; // aa
            case '\u140B': d = '\u00c3'; break; // aa
//            case '\u1439': sb.append('+'); d = 'X'; break; // paa
          case '\u1439': d = '\u0096'; break; // paa
//            case '\u1456': sb.append('|'); d = 'b'; break; // taa
          case '\u1456': d = '\u00c8'; break; // taa
//            case '\u1473': sb.append(']'); d = 'v'; break; // kaa
          case '\u1473': d = '\u00c4'; break; // kaa
//            case '\u1491': sb.append(']'); d = 'Z'; break; // gaa
          case '\u1491': d = '\u00d7'; break; // gaa
//            case '\u14AB': sb.append(']'); d = 'm'; break; // maa
          case '\u14AB': d = '\u00b5'; break; // maa
//            case '\u14C8': sb.append('~'); d = 'N'; break; // naa
//          case '\u14C8': d = '\u02dc'; break; // naa
          case '\u14C8': d = '\u00cc'; break; // naa
//            case '\u14F5': sb.append(']'); d = 'n'; break; // saa
          case '\u14F5': d = '\u00e4'; break; // saa
//            case '\u14DB': sb.append('~'); d = 'M'; break; // laa
          case '\u14DB': d = '\u00c2'; break; // laa
//            case '\u152E': sb.append(']'); d = '/'; break; // jaa
          case '\u152E': d = '\u00f7'; break; // jaa
//            case '\u155A': sb.append('+'); d = '?'; break; // vaa
          case '\u155A': d = '\u00bf'; break; // vaa
//            case '\u154C': sb.append('}'); d = 'C'; break; // raa
          case '\u154C': d = '\u00c7'; break; // raa
//            case '\u1584': sb.append('|'); d = 'c'; break; // qaa
          case '\u1584': d = '\u00e7'; break; // qaa
//            case '\u1594': sb.append("1]"); d = 'Z'; break; // Naa
//          case '\u1594': sb.append('+'); d = 'z'; break; // ngaa
          case '\u1594': d = '\u00c0'; break; // ngaa
//            case '\u1676': sb.append("R]"); d = 'Z'; break; // nngaa
//          case '\u1676': sb.append('+'); d = 'U'; break;  // nngaa
          case '\u1676': sb.append('\u0038'); d = '\u00c0'; break; // nngaa
//            case '\u15A5': sb.append('~'); d = 'I'; break; // &aa
//          case '\u15A5': d = '\u02c6'; break; // &aa
          case '\u15A5': d = '\u00ee'; break; // &aa
            case '\u1449': d = '\u0032'; break; // p
            case '\u1466': d = '\u0035'; break; // t
            case '\u1483': d = '\u0034'; break; // k
            case '\u14A1': d = '\u003d'; break; // g
            case '\u14BB': d = '\u0037'; break; // m
            case '\u14D0': d = '\u0038'; break; // n
            case '\u1505': d = '\u002b'; break; // s
            case '\u14EA': d = '\u0039'; break; // l
            case '\u153E': d = '\u0030'; break; // j
            case '\u155D': d = '\u007b'; break; // v
            case '\u1550': d = '\u0033'; break; // r
            case '\u1585': d = '\u0036'; break; // q
            case '\u1595': d = '\u0031'; break; // ng
            case '\u1596': d = '\u00eb'; break; // nng
            case '\u15A6': d = '\u0050'; break; // &
            case '\u157C': d = '\u0042'; break; // H
            case '1': d = '\u0021'; break; // 1
            case '2': d = '\u0040'; break; // 2
            case '3': d = '\u0023'; break; // 3
            case '4': d = '\u0024'; break; // 4
            case '5': d = '\u0025'; break; // 5
            case '6': d = '\u005e'; break; // 6
            case '7': d = '\u0026'; break; // 7
            case '8': d = '\u002a'; break; // 8
            case '9': d = '\u0028'; break; // 9
            case '0': d = '\u0029'; break; // 0
            case '(': d = '\u0047'; break; // (
            case ')': d = '\u0048'; break; // )
            case '?': d = '\u0056'; break; // ?
            case '/': d = '\u0046'; break; // /
            case '!': d = '\u00a1'; break; //
            case '#': d = '\u00a3'; break; // 
            case '*': d = '\u0095'; break; // 
            case '[': d = '\u005d'; break; // 
            case ']': d = '\u007d'; break; // 
            case '$': d = '\u0052'; break;
            case '\u00b0': d = '\u007e'; break; // degre
            case '\u00f7': d = '\u00b2'; break; // division 
            case '\u2212': d = '\u00ad'; break; // moins
           case '+': d = '\u0054'; break;
            case '=': d = '\u0055'; break; // 
            case '\u2018': d = '\u00e6'; break; // guillemet-apostrophe culbuté
            case '\u2019': d = '\''; break; // guillemet-apostrophe
            case '\u201c': d = '\u00c6'; break; // guillemet-apostrophe double culbuté
            case '\u201d': d = '\u0022'; break; // guillemet-apostrophe double
            case '\u2022': d = '\u00a7'; break; // puce
            case '\u2014': d = '\u0097'; break; // tiret cadratin
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}