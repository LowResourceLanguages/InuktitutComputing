//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		PoliceProsyl.java
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
// $Id: PoliceTariurmiut.java,v 1.1 2009/06/19 19:38:35 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceTariurmiut.java,v $
// Revision 1.1  2009/06/19 19:38:35  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.8  2006/11/01 15:29:56  farleyb
// *** empty log message ***
//
// Revision 1.7  2006/10/20 17:01:26  farleyb
// *** empty log message ***
//
// Revision 1.6  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.5  2006/07/20 18:54:54  farleyb
// *** empty log message ***
//
// Revision 1.4  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.3  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, nécessaire entre autres pour le surlignage, où il faut déterminer la délimitation des mots.
//
// Revision 1.2  2006/04/24 17:16:52  farleyb
// Modifications majeures pour simplifier le transcodage et la translittération. Maintenant, les fichiers PoliceXXX.java ne contiennent que des tables.  Les méthodes sont dans Police.java
//
// Revision 1.1  2006/03/09 18:01:53  farleyb
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
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Première sauvegarde
//
// Revision 1.0  2003-06-25 13:19:58-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:40:19-05  farleyb
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

public class FontTariurmiut {

    public static String unicodesICI2Codes[][] = {
          // i
            { "\u1403", "\u00C4"  }, // i
            { "\u1431", "\u00E1"  }, // pi
            { "\u144E", "\u00E9"  }, // ti
            { "\u146D", "\u00EF"  }, // ki
            { "\u148B", "\u00F9"  }, // gi
            { "\u14A5", "\u00B4"  }, // mi
            { "\u14C2", "\u2264"  }, // ni
            { "\u14EF", "\u03C0"  }, // si
            { "\u14D5", "\u00BF"  }, // li
            { "\u1528", "\u00AB"  }, // ji
            { "\u1555", "\u203A"  }, // vi
            { "\u1555", "\u009B"  }, // vi
            { "\u1546", "\u2030"  }, // ri
            { "\u1546", "\u0089"  }, // ri
            { "\u157F", "\u00CE"  }, // qi
            { "\u158F", "\u00DA"  }, // Ni
            { "\u15A0", "\u02D8"  }, // &i

            // ii
            { "\u1404", "\u00C5" }, // ii
            { "\u1432", "\u00E0" }, // pii
            { "\u144F", "\u00E8" }, // tii
            { "\u146E", "\u00F3" }, // kii
            { "\u148C", "\u00FB" }, // gii
            { "\u14A6", "\u00A8" }, // mii
            { "\u14C3", "\u2265" }, // nii
            { "\u14F0", "\u222B" }, // sii
            { "\u14D6", "\u00A1" }, // lii
            { "\u1529", "\u00BB" }, // jii
            { "\u1556", "\uFB01" }, // vii
            { "\u1547", "\u00C2" }, // rii
            { "\u1580", "\u00CF" }, // qii
            { "\u1590", "\u00DB" }, // Nii
            { "\u15A1", "\u02D9" }, // &ii

            // u
            { "\u1405", "\u00C7"  }, // u
            { "\u1433", "\u00E2" }, // pu
            { "\u1450", "\u00EA"  }, // tu
            { "\u146F", "\u00F2"  }, // ku
            { "\u148D", "\u00FC"  }, // gu
            { "\u14A7", "\u2260"  }, // mu
            { "\u14C4", "\u00A5"  }, // nu
            { "\u14F1", "\u00AA"  }, // su
            { "\u14D7", "\u00AC" }, // lu
            { "\u152A", "\u00C0"  }, // ju
            { "\u1557", "\uFB02"  }, // vu
            { "\u1548", "\u00CA"  }, // ru
            { "\u1581", "\u00CC"  }, // qu
            { "\u1591", "\u00D9"  }, // Nu
            { "\u15A2", "\u02DA"  }, // &u

            // uu
            { "\u1406", "\u00C9" }, // uu
            { "\u1434", "\u00E4" }, // puu
            { "\u1451", "\u00EB" }, // tuu
            { "\u1470", "\u00F4" }, // kuu
            { "\u148E", "\u2020" }, // guu
            { "\u148E", "\u0086" }, // guu
            { "\u14A8", "\u00C6" }, // muu
            { "\u14C5", "\u00B5" }, // nuu
            { "\u14F2", "\u00BA" }, // suu
            { "\u14D8", "\u221A" }, // luu
            { "\u152B", "\u00C3" }, // juu
            { "\u1558", "\u2021" }, // vuu
//            { "\u1558", "\u0087" }, // vuu
            { "\u1549", "\u00C1" }, // ruu
            { "\u1582", "\u00D3" }, // quu
            { "\u1592", "\u0131" }, // Nuu
            { "\u15A3", "\u00B8" }, // &uu

            // a
            { "\u140A", "\u00D6" }, // a
            { "\u1438", "\u00E3" }, // pa
            { "\u1455", "\u00ED" }, // ta
            { "\u1472", "\u00F6" }, // ka
            { "\u1490", "\u00B0" }, // ga
            { "\u14AA", "\u00D8" }, // ma
            { "\u14C7", "\u2202" }, // na
            { "\u14F4", "\u2126" }, // sa
            { "\u14F4", "\u03A9" }, // sa
            { "\u14DA", "\u0192" }, // la
            { "\u14DA", "\u0083" }, // la
            { "\u152D", "\u00D5" }, // ja
            { "\u1559", "\u00B7" }, // va
            { "\u154B", "\u00CB" }, // ra
            { "\u1583", "\u00D4" }, // qa
            { "\u1593", "\u02C6" }, // Na
            { "\u1593", "\u0088" }, // Na
            { "\u15A4", "\u02DD" }, // &a

            // aa
            { "\u140B", "\u00DC" }, // aa
            { "\u1439", "\u00E5" }, // paa
            { "\u1456", "\u00EC" }, // taa
            { "\u1473", "\u00F5" }, // kaa
            { "\u1491", "\u00A2" }, // gaa
            { "\u14AB", "\u221E" }, // maa
            { "\u14C8", "\u2211" }, // naa
            { "\u14F5", "\u00E6" }, // saa
            { "\u14DB", "\u2248" }, // laa
            { "\u152E", "\u0152" }, // jaa
            { "\u152E", "\u008C" }, // jaa
            { "\u155A", "\u201A" }, // vaa
            { "\u155A", "\u0082" }, // vaa
            { "\u154C", "\u00C8" }, // raa
            { "\u1584", "\uF8FF" }, // qaa
            { "\u1594", "\u02DC" }, // Naa
            { "\u1594", "\u0098" }, // Naa
            { "\u15A5", "\u02DB" }, // &aa

            // consonnes seules
            { "\u1449", "\u00E7" }, // p
            { "\u1466", "\u00EE" }, // t
            { "\u1483", "\u00FA" }, // k
            { "\u14A1", "\u00A3" }, // g
            { "\u14BB", "\u00B1" }, // m
            { "\u14D0", "\u220F" }, // n
            { "\u1505", "\u00F8" }, // s
            { "\u14EA", "\u2206" }, // l
            { "\u153E", "\u0153" }, // j
            { "\u153E", "\u009C" }, // j
            { "\u155D", "\u201E" }, // v
            { "\u155D", "\u0084" }, // v
            { "\u1550", "\u00CD" }, // r
            { "\u1585", "\u00D2" }, // q
            { "\u1595", "\u02C9" }, // N
            { "\u1595", "\u00AF" }, // N
            { "\u15A6", "\u02C7" }, // &
            
            { "\u1596", "\u220F\u02C9" }, // nng
            { "\u1596", "\u220F\u00AF" }, // nng
            { "\u1671", "\u220F\u00DA" }, // nngi    
            { "\u1673", "\u220F\u00D9" }, // nngu
            { "\u1675", "\u220F\u02C6" }, // nnga
            { "\u1675", "\u220F\u0088" }, // nnga
            { "\u1672", "\u220F\u00DB" }, // nngii
            { "\u1674", "\u220F\u0131" }, // nnguu
            { "\u1676", "\u220F\u02DC" }, // nngaa
            { "\u1676", "\u220F\u0098" }, // nngaa

    };
    
    public static String unicodesICI2CodesOthers[][] = {

            // autres signes
            { "\u00A7", "\u00B2" }, //paragraphe
            { "\u2022", "\u00B3" }, //puce
            { "\u00AE", "\u00BC" }, //marque déposée
        	{ "\u00B6", "\u00BD" }, //pied de mouche
            { "\u00A9", "\u00BE"  }, //copyright
        	{ "\u201D", "\u0106" }, //guillemet-apostrophe double
            { "\u2018", "\u0107" }, //guillemet-apostrophe culbuté
            { "\u2019", "\u010C" }, //guillemet-apostrophe
            { "\u2026", "\u011E" }, //points de suspension
        	{ "\u2013", "\u0130" }, //tiret demi-cadratin
        	{ "\u2014", "\u015E" }, //tiret cadratin
        	{ "\u201C", "\u015F" }, //guillemet-apostrophe double culbuté
        	{ "\u15AC", "\u20A3" }, //marque de commerce (anglais)
            
    };
       
    public static String[][] unicodesAIPAITAI2Codes = {
        // aipaitai
        {"\u1401","\u00D6\u00C4"}, // ai
        {"\u142f","\u00E3\u00C4"}, // pai
        {"\u144c","\u00ED\u00C4"}, // tai
        {"\u146b","\u00F6\u00C4"}, // kai
        {"\u1489","\u00B0\u00C4"}, // gai
        {"\u14a3","\u00D8\u00C4"}, // mai
        {"\u14c0","\u2202\u00C4"}, // nai
        {"\u14ed","\u2126\u00C4"}, // sai
        {"\u14ed","\u03A9\u00C4"}, // sai
        {"\u14d3","\u0192\u00C4"}, // lai
        {"\u14d3","\u0083\u00C4"}, // lai
        {"\u1526","\u00D5\u00C4"}, // jai
        {"\u1553","\u00B7\u00C4"}, // vai
        {"\u1543","\u00CB\u00C4"}, // rai
        {"\u166f","\u00D4\u00C4"}, // qai
        {"\u1670","\u02C6\u00C4"},  // ngai  
        {"\u1670","\u0088\u00C4"}  // ngai  
    };

    public static TransCoder transcoderToUnicodeICI;
    public static TransCoder transcoderToUnicodeAIPAITAI;
    public static TransCoder transcoderToFontICI;
    public static TransCoder transcoderToFontAIPAITAI;

    public static String wordChars;
    public static Set fontChars = new HashSet();
    static {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<unicodesICI2Codes.length; i++) {
            sb.append(unicodesICI2Codes[i][1]);
            for (int j=0; j<unicodesICI2Codes[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2Codes[i][1].charAt(j)));
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
        int i=0,j;
        int l=s.length();
        char c,d,e;
        StringBuffer sb = new StringBuffer();
        int sbl;
        while (i < l) {
            sbl = sb.length();
            c = s.charAt(i);
            switch (c) {
            case '\u00C4': d = '\u1403'; break; // i
            case '\u00E1': d = '\u1431'; break; // pi
            case '\u00E9': d = '\u144E'; break; // ti
            case '\u00EF': // ki
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
                break; // ki
            case '\u00F9': // gi
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
            case '\u00B4': d = '\u14A5'; break; // mi
            case '\u2264': d = '\u14C2'; break; // ni
            case '\u03C0': d = '\u14EF'; break; // si
            case '\u00BF': d = '\u14D5'; break; // li
            case '\u00AB': d = '\u1528'; break; // ji
            case '\u009B':
            case '\u203A': d = '\u1555'; break; // vi
            case '\u0089':
            case '\u2030': // ri
                d = '\u1546';
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ri = r+ri (rri)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                break; 
            case '\u00CE': // qi
                d = '\u157F';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146d';
                }
                break; 
            case '\u00DA': // ngi
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
            case '\u02D8': d = '\u15A0'; break; // &i
            case '\u00C5': d = '\u1404'; break; // ii
            case '\u00E0': d = '\u1432'; break; // pii
            case '\u00E8': d = '\u144F'; break; // tii
            case '\u00F3': // kii
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
            case '\u00FB': // gii
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
            case '\u00A8': d = '\u14A6'; break; // mii
            case '\u2265': d = '\u14C3'; break; // nii
            case '\u222B': d = '\u14F0'; break; // sii
            case '\u00A1': d = '\u14D6'; break; // lii
            case '\u00BB': d = '\u1529'; break; // jii
            case '\uFB01': d = '\u1556'; break; // vii
            case '\u00C2': // rii
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1547'; 
                break;
            case '\u00CF': // qii
                d = '\u1580'; // qii
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qii= q+kii (qqii)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146e';
                }
                break; 
            case '\u00DB': // ngii
                d = '\u1590';
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1672'; // nngii
                        break;
                    }
                }
                break;
            case '\u02D9': d = '\u15A1'; break; // &ii
            case '\u00C7': d = '\u1405'; break; // u
            case '\u00E2': d = '\u1433'; break; // pu
            case '\u00EA': d = '\u1450'; break; // tu
            case '\u00F2': // ku
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
            case '\u00FC': // gu
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
            case '\u2260': d = '\u14A7'; break; // mu
            case '\u00A5': d = '\u14C4'; break; // nu
            case '\u00AA': d = '\u14F1'; break; // su
            case '\u00AC': d = '\u14D7'; break; // lu
            case '\u00C0': d = '\u152A'; break; // ju
            case '\uFB02': d = '\u1557'; break; // vu
            case '\u00CA': // ru
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ru = r+ru (rru)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1548'; break; 
            case '\u00CC': // qu
                d = '\u1581';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146f';
                }
                break; 
            case '\u00D9': // ngu
                d = '\u1591';
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1673'; // nngu
                        break;
                    }
                }
                break;
            case '\u02DA': d = '\u15A2'; break; // &u
            case '\u00C9': d = '\u1406'; break; // uu
            case '\u00E4': d = '\u1434'; break; // puu
            case '\u00EB': d = '\u1451'; break; // tuu
            case '\u00F4': // kuu
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
            case '\u2020':
            case '\u0086': // guu
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
            case '\u00C6': d = '\u14A8'; break; // muu
            case '\u00B5': d = '\u14C5'; break; // nuu
            case '\u00BA': d = '\u14F2'; break; // suu
            case '\u221A': d = '\u14D8'; break; // luu
            case '\u00C3': d = '\u152B'; break; // juu
            case '\u0087': d = '\u1558'; break; // vuu
            case '\u2021': d = '\u1558'; break; // vuu
            case '\u00C1': // ruu
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu= r+ruu (rruu)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1549'; break;
            case '\u00D3': // quu
                d = '\u1582'; // quu
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+quu = q+kuu (qquu)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1470';
                }
                break; 
            case '\u0131': // nguu
                d = '\u1592'; // nguu
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1674'; // nnguu
                        break;
                    }
                }
                break;
            case '\u00B8': d = '\u15A3'; break; // &uu     
            
            case '\u00D6':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00E3':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00ED':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00F6': // ka
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00B0': // ga
                d = '\u1490';
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00D8':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u2202':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u03A9':
            case '\u2126':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u0083':
            case '\u0192':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00D5':
               i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u2219':
            case '\u00B7':
                 i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00CB': // ra
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+rai = r+rai (rrai)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u00c4': 
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
            case '\u00D4': // qa
                j = i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u00c4': 
                        switch (aipaitai) {
                        case 1 : 
                            d = '\u166f'; 
                            if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qa+i = q+kai (qqai)
                                sb.deleteCharAt(sb.length()-1);
                                sb.append('\u1585');
                                d = '\u146b';
                            }
                            break; 
                        default: 
                            if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qa+i = q+ka+i (qqai)
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
                        d = '\u1583'; // qa
                        if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qa = q+ka (qqa)
                            sb.deleteCharAt(sb.length()-1);
                            sb.append('\u1585');
                            d = '\u1472';
                        }
                        break; 
                    }
                } else {
                    d = '\u1583';
                    if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qa = q+ka (qqa)
                        sb.deleteCharAt(sb.length()-1);
                        sb.append('\u1585');
                        d = '\u1472';
                    }
                }
                break;
            case '\u0088':
            case '\u02C6': // nga
                j=i+1;
                d = '\u1593'; // nga
                boolean precng = false;
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                    precng = true;
                    sb.deleteCharAt(sb.length()-1);
                }
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u00c4': 
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
                        if (precng)
                            d = '\u1675';
                        break;
                    }
                }
                break;
            case '\u02DD': d = '\u15A4'; break; // &a
            case '\u00DC': d = '\u140B'; break; // aa
            case '\u00E5': d = '\u1439'; break; // paa
            case '\u00EC': d = '\u1456'; break; // taa
            case '\u00F5': // kaa
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
            case '\u00A2': // gaa
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
            case '\u221E': d = '\u14AB'; break; // maa
            case '\u2211': d = '\u14C8'; break; // naa
            case '\u00E6': d = '\u14F5'; break; // saa
            case '\u2248': d = '\u14DB'; break; // laa
            case '\u008C':
            case '\u0152': d = '\u152E'; break; // jaa
            case '\u0082':
            case '\u201A': d = '\u155A'; break; // vaa
            case '\u00C8': // raa
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+raa = r+raa (rraa)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u154C'; break;
            case '\uF8FF':// qaa
                d = '\u1584'; // qaa
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550'|| sb.charAt(sbl-1)=='\u1585')) { // r+qaa = q+kaa (qqaa)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1473';
                }
                break; 
            case '\u0098':
            case '\u02DC': // ngaa
                d = '\u1594'; // ngaa
                if (sbl != 0) {
                    e = sb.charAt(sbl-1);
                    switch (e) {
                    case '\u14d0': //n
                    case '\u1595': //ng
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1676'; // nngaa
                        break;
                    }
                }
                break;
            case '\u02DB': d = '\u15A5'; break; // &aa
            case '\u00E7': d = '\u1449'; break; // p
            case '\u00EE': d = '\u1466'; break; // t
            case '\u00FA': d = '\u1483'; break; // k
            case '\u00A3': d = '\u14A1'; break; // g
            case '\u00B1': d = '\u14BB'; break; // m
            case '\u220F': d = '\u14D0'; break; // n
            case '\u00F8': d = '\u1505'; break; // s
            case '\u0394':
            case '\u2206': d = '\u14EA'; break; // l
            case '\u009C': 
            case '\u0153': d = '\u153E'; break; // j
            case '\u0084': 
            case '\u201E': d = '\u155D'; break; // v
            case '\u00CD': d = '\u1550'; break; // r
            case '\u00D2': d = '\u1585'; break; // q
            case 'H': d = '\u157c'; break; // Nunavut H
            case '\u00AF': 
            case '\u02C9': d = '\u1595'; // ng
            d = '\u1595'; // ng
            if (sbl != 0) {
                e = sb.charAt(sbl-1);
                switch (e) {
                case '\u14d0': //n
                case '\u1595': //ng
                    sb.deleteCharAt(sb.length()-1);
                    d = '\u1596'; // nng
                    break;
                }
            }
            break;
            case '\u02C7': d = '\u15A6'; break; // &
            case '\u00B2': d = '\u00A7'; break; //paragraphe
            case '\u00B3': d = '\u2022'; break; //puce
            case '\u00BC': d = '\u00AE'; break; //marque déposée
            case '\u00BD': d = '\u00B6'; break; //pied de mouche
            case '\u00BE': d = '\u00A9'; break; //copyright
            case '\u0106': d = '\u201D'; break; //guillemet-apostrophe double
            case '\u0107': d = '\u2018'; break; //guillemet-apostrophe culbuté
            case '\u010C': d = '\u2019'; break; //guillemet-apostrophe
            case '\u011E': d = '\u2026'; break; //points de suspension
            case '\u0130': d = '\u2013'; break; //tiret demi-cadratin
            case '\u015E': d = '\u2014'; break; //tiret cadratin
            case '\u015F': d = '\u201C'; break; //guillemet-apostrophe double culbuté
            case '\u20A3': d = '\u15AC'; break; //marque de commerce (anglais)
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
            case '\u1403': d = '\u00C4'; break; // i
            case '\u1431': d = '\u00E1'; break; // pi
            case '\u144E': d = '\u00E9'; break; // ti
            case '\u146D': d = '\u00EF'; break; // ki
            case '\u148B': d = '\u00F9'; break; // gi
            case '\u14A5': d = '\u00B4'; break; // mi
            case '\u14C2': d = '\u2264'; break; // ni
            case '\u14EF': d = '\u03C0'; break; // si
            case '\u14D5': d = '\u00BF'; break; // li
            case '\u1528': d = '\u00AB'; break; // ji
            case '\u1555': d = '\u203A'; break; // vi
//          case '\u1555': d = '\u009B'; break; // vi
            case '\u1546': d = '\u2030'; break; // ri
//          case '\u1546': d = '\u0089'; break; // ri
            case '\u157F': d = '\u00CE'; break; // qi
            case '\u158F': d = '\u00DA'; break; // Ni
            case '\u15A0': d = '\u02D8'; break; // &i
            case '\u1404': d = '\u00C5'; break; // ii
            case '\u1432': d = '\u00E0'; break; // pii
            case '\u144F': d = '\u00E8'; break; // tii
            case '\u146E': d = '\u00F3'; break; // kii
            case '\u148C': d = '\u00FB'; break; // gii
            case '\u14A6': d = '\u00A8'; break; // mii
            case '\u14C3': d = '\u2265'; break; // nii
            case '\u14F0': d = '\u222B'; break; // sii
            case '\u14D6': d = '\u00A1'; break; // lii
            case '\u1529': d = '\u00BB'; break; // jii
            case '\u1556': d = '\uFB01'; break; // vii
            case '\u1547': d = '\u00C2'; break; // rii
            case '\u1580': d = '\u00CF'; break; // qii
            case '\u1590': d = '\u00DB'; break; // Nii
            case '\u15A1': d = '\u02D9'; break; // &ii
            case '\u1405': d = '\u00C7'; break; // u
            case '\u1433': d = '\u00E2'; break; // pu
            case '\u1450': d = '\u00EA'; break; // tu
            case '\u146F': d = '\u00F2'; break; // ku
            case '\u148D': d = '\u00FC'; break; // gu
            case '\u14A7': d = '\u2260'; break; // mu
            case '\u14C4': d = '\u00A5'; break; // nu
            case '\u14F1': d = '\u00AA'; break; // su
            case '\u14D7': d = '\u00AC'; break; // lu
            case '\u152A': d = '\u00C0'; break; // ju
            case '\u1557': d = '\uFB02'; break; // vu
            case '\u1548': d = '\u00CA'; break; // ru
            case '\u1581': d = '\u00CC'; break; // qu
            case '\u1591': d = '\u00D9'; break; // Nu
            case '\u15A2': d = '\u02DA'; break; // &u
            case '\u1406': d = '\u00C9'; break; // uu
            case '\u1434': d = '\u00E4'; break; // puu
            case '\u1451': d = '\u00EB'; break; // tuu
            case '\u1470': d = '\u00F4'; break; // kuu
            case '\u148E': d = '\u2020'; break; // guu
//          case '\u148E': d = '\u0086'; break; // guu
            case '\u14A8': d = '\u00C6'; break; // muu
            case '\u14C5': d = '\u00B5'; break; // nuu
            case '\u14F2': d = '\u00BA'; break; // suu
            case '\u14D8': d = '\u221A'; break; // luu
            case '\u152B': d = '\u00C3'; break; // juu
            case '\u1558': d = '\u2021'; break; // vuu
//          case '\u1558': d = '\u0087'; break; // vuu
            case '\u1549': d = '\u00C1'; break; // ruu
            case '\u1582': d = '\u00D3'; break; // quu
            case '\u1592': d = '\u0131'; break; // Nuu
            case '\u15A3': d = '\u00B8'; break; // &uu
            case '\u140A': d = '\u00D6'; break; // a
            case '\u1438': d = '\u00E3'; break; // pa
            case '\u1455': d = '\u00ED'; break; // ta
            case '\u1472': d = '\u00F6'; break; // ka
            case '\u1490': d = '\u00B0'; break; // ga
            case '\u14AA': d = '\u00D8'; break; // ma
            case '\u14C7': d = '\u2202'; break; // na
            case '\u14F4': d = '\u2126'; break; // sa
//          case '\u14F4': d = '\u03A9'; break; // sa
            case '\u14DA': d = '\u0192'; break; // la
//          case '\u14DA': d = '\u0083'; break; // la
            case '\u152D': d = '\u00D5'; break; // ja
            case '\u1559': d = '\u00B7'; break; // va
            case '\u154B': d = '\u00CB'; break; // ra
            case '\u1583': d = '\u00D4'; break; // qa
            case '\u1593': d = '\u02C6'; break; // Na
//          case '\u1593': d = '\u0088'; break; // Na
            case '\u15A4': d = '\u02DD'; break; // &a
            case '\u140B': d = '\u00DC'; break; // aa
            case '\u1439': d = '\u00E5'; break; // paa
            case '\u1456': d = '\u00EC'; break; // taa
            case '\u1473': d = '\u00F5'; break; // kaa
            case '\u1491': d = '\u00A2'; break; // gaa
            case '\u14AB': d = '\u221E'; break; // maa
            case '\u14C8': d = '\u2211'; break; // naa
            case '\u14F5': d = '\u00E6'; break; // saa
            case '\u14DB': d = '\u2248'; break; // laa
            case '\u152E': d = '\u0152'; break; // jaa
//          case '\u152E': d = '\u008C'; break; // jaa
            case '\u155A': d = '\u201A'; break; // vaa
//          case '\u155A': d = '\u0082'; break; // vaa
            case '\u154C': d = '\u00C8'; break; // raa
            case '\u1584': d = '\uF8FF'; break; // qaa
            case '\u1594': d = '\u02DC'; break; // Naa
//          case '\u1594': d = '\u0098'; break; // Naa
            case '\u15A5': d = '\u02DB'; break; // &aa
            case '\u1449': d = '\u00E7'; break; // p
            case '\u1466': d = '\u00EE'; break; // t
            case '\u1483': d = '\u00FA'; break; // k
            case '\u14A1': d = '\u00A3'; break; // g
            case '\u14BB': d = '\u00B1'; break; // m
            case '\u14D0': d = '\u220F'; break; // n
            case '\u1505': d = '\u00F8'; break; // s
            case '\u14EA': d = '\u2206'; break; // l
            case '\u153E': d = '\u0153'; break; // j
//          case '\u153E': d = '\u009C'; break; // j
            case '\u155D': d = '\u201E'; break; // v
//          case '\u155D': d = '\u0084'; break; // v
            case '\u1550': d = '\u00CD'; break; // r
            case '\u1585': d = '\u00D2'; break; // q
            case '\u1595': d = '\u02C9'; break; // N
//          case '\u1595': d = '\u00AF'; break; // N
            case '\u15A6': d = '\u02C7'; break; // &
            case '\u1596': sb.append('\u220F'); d = '\u02C9'; break; // nng
//          case '\u1596': sb.append('\u220F'); d = '\u00AF'; break; // nng
            case '\u1671': sb.append('\u220F'); d = '\u00DA'; break; // nngi    
            case '\u1673': sb.append('\u220F'); d = '\u00D9'; break; // nngu
            case '\u1675': sb.append('\u220F'); d = '\u02C6'; break; // nnga
//          case '\u1675': sb.append('\u220F'); d = '\u0088'; break; // nnga
            case '\u1672': sb.append('\u220F'); d = '\u00DB'; break; // nngii
            case '\u1674': sb.append('\u220F'); d = '\u0131'; break; // nnguu
            case '\u1676': sb.append('\u220F'); d = '\u02DC'; break; // nngaa
//          case '\u1676': sb.append('\u220F'); d = '\u0098'; break; // nngaa
            case '\u00A7': d = '\u00B2'; break; //paragraphe
            case '\u2022': d = '\u00B3'; break; //puce
            case '\u00AE': d = '\u00BC'; break; //marque déposée
            case '\u00B6': d = '\u00BD'; break; //pied de mouche
            case '\u00A9': d = '\u00BE'; break; //copyright
            case '\u201D': d = '\u0106'; break; //guillemet-apostrophe double
            case '\u2018': d = '\u0107'; break; //guillemet-apostrophe culbuté
            case '\u2019': d = '\u010C'; break; //guillemet-apostrophe
            case '\u2026': d = '\u011E'; break; //points de suspension
            case '\u2013': d = '\u0130'; break; //tiret demi-cadratin
            case '\u2014': d = '\u015E'; break; //tiret cadratin
            case '\u201C': d = '\u015F'; break; //guillemet-apostrophe double culbuté
            case '\u15AC': d = '\u20A3'; break; //marque de commerce (anglais)
            case '\u1401': sb.append('\u00D6'); d = '\u00C4'; break; // ai
            case '\u142f': sb.append('\u00E3'); d = '\u00C4'; break; // pai
            case '\u144c': sb.append('\u00ED'); d = '\u00C4'; break; // tai
            case '\u146b': sb.append('\u00F6'); d = '\u00C4'; break; // kai
            case '\u1489': sb.append('\u00B0'); d = '\u00C4'; break; // gai
            case '\u14a3': sb.append('\u00D8'); d = '\u00C4'; break; // mai
            case '\u14c0': sb.append('\u2202'); d = '\u00C4'; break; // nai
            case '\u14ed': sb.append('\u2126'); d = '\u00C4'; break; // sai
//          case '\u14ed': sb.append('\u03A9'); d = '\u00C4'; break; // sai
            case '\u14d3': sb.append('\u0192'); d = '\u00C4'; break; // lai
//          case '\u14d3': sb.append('\u0083'); d = '\u00C4'; break; // lai
            case '\u1526': sb.append('\u00D5'); d = '\u00C4'; break; // jai
            case '\u1553': sb.append('\u00B7'); d = '\u00C4'; break; // vai
            case '\u1543': sb.append('\u00CB'); d = '\u00C4'; break; // rai
            case '\u166f': sb.append('\u00D4'); d = '\u00C4'; break; // qai
            case '\u1670': sb.append('\u02C6'); d = '\u00C4'; break;// ngai  
//          case '\u1670': sb.append('\u0088'); d = '\u00C4'; break;  // ngai  
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}
