//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		PoliceAinunavik.java
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
// $Id: PoliceAinunavik.java,v 1.1 2009/06/19 19:38:36 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceAinunavik.java,v $
// Revision 1.1  2009/06/19 19:38:36  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.10  2006/11/01 15:29:56  farleyb
// *** empty log message ***
//
// Revision 1.9  2006/10/20 17:01:26  farleyb
// *** empty log message ***
//
// Revision 1.8  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.7  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.6  2006/06/19 17:46:27  farleyb
// *** empty log message ***
//
// Revision 1.5  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, nécessaire entre autres pour le surlignage, où il faut déterminer la délimitation des mots.
//
// Revision 1.4  2006/03/09 18:01:53  farleyb
// Diverses modifications re tables de conversion à unicode
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
// Revision 1.0  2003-06-25 13:19:56-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:38:03-05  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


package fonts;

import java.util.HashSet;
import java.util.Set;

import script.TransCoder;

// Remplacement de codes d'une police donnée 
// en codes unicode équivalents représentant
// les mêmes caractères

public class FontAinunavik {
        
    static char UNI_A = '\u140A';
    static char UNI_I = '\u1403';
    static char UNI_AI = '\u1401';
    
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
    { "\u1555", "["  }, // vi
    { "\u1546", "E"  }, // ri
    { "\u157F", "e"  }, // qi
    { "\u158F", "q"  }, // Ni
    { "\u15A0", "\u00C3"  }, // &i

    // ii
    { "\u1404", "\u2122" }, // ii
    { "\u1404", "\u0099" }, // ii
    { "\u1432", "\u201E" }, // pii
    { "\u1432", "\u0084" }, // pii
    { "\u144F", "\u2020" }, // tii
    { "\u144F", "\u0086" }, // tii
    { "\u146E", "\u00AE" }, // kii
    { "\u148C", "\u0152" }, // gii
    { "\u148C", "\u008C" }, // gii
    { "\u14A6", "\u00FC" }, // mii
    { "\u14C3", "\u00EE" }, // nii
    { "\u14F0", "\u00A5" }, // sii
    { "\u14D6", "\u00F8" }, // lii
    { "\u1529", "\u00BA" }, // jii
    { "\u1556", "\u201C" }, // vii
    { "\u1556", "\u0093" }, // vii
    { "\u1547", "\u2030" }, // rii
    { "\u1547", "\u0089" }, // rii
    { "\u1580", "\u00E9" }, // qii
    { "\u1590", "\u0153" }, // ngii
    { "\u1590", "\u009C" }, // ngii
    { "\u15A1", "\u00ED" }, // &ii

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
    { "\u15A2", "\u00C0"  }, // &u

    // uu
    { "\u1406", "\u00DF" }, // uu
    { "\u1434", "\u00CD" }, // puu
    { "\u1451", "\u00A9" }, // tuu
    { "\u1470", "\u0192" }, // kuu
    { "\u1470", "\u0083" }, // kuu
    { "\u148E", "\u00C5" }, // guu
    { "\u14A8", "\u00CB" }, // muu
    { "\u14C5", "\u00AA" }, // nuu
    { "\u14F2", "\u00A7" }, // suu
    { "\u14D8", "\u00AC" }, // luu
    { "\u152B", "\u00D4" }, // juu
    { "\u1558", "\u00D3" }, // vuu
    { "\u1549", "\u00CE" }, // ruu
    { "\u1582", "\u00DA" }, // quu
    { "\u1592", "\u00E5" }, // nguu
    { "\u15A3", "\u00EC" }, // &uu

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
    { "\u152D", "I" }, // ja
    { "\u1559", "?" }, // va
    { "\u154B", "C" }, // ra
    { "\u1583", "c" }, // qa
    { "\u1593", "z" }, // nga
    { "\u15A4", "\u0178" }, // &a
    { "\u15A4", "\u009F" }, // &a

    // aa
    { "\u140B", "\u20AC" }, // aa
    { "\u140B", "\u0080" }, // aa
    { "\u1439", "\u00D9" }, // paa
    { "\u1456", "\u00CC" }, // taa
    { "\u1473", "\u00CF" }, // kaa
    { "\u1491", "\u00DB" }, // gaa
    { "\u14AB", "\u00B5" }, // maa
    { "\u14C8", "\u02C6" }, // naa
    { "\u14C8", "\u0088" }, // naa
    { "\u14F5", "\u00F1" }, // saa
    { "\u14DB", "\u02DC" }, // laa
    { "\u14DB", "\u0098" }, // laa
    { "\u152E", "\u00F7" }, // jaa
    { "\u155A", "\u00BF" }, // vaa
    { "\u154C", "\u00C7" }, // raa
    { "\u1584", "\u00E7" }, // qaa
    { "\u1594", "\u00AF" }, // ngaa
    { "\u15A5", "\u00EF" }, // &aa

    // consonnes seules
    { "\u1449", "2" }, // p
    { "\u1466", "5" }, // t
    { "\u1483", "4" }, // k
    { "\u14A1", "=" }, // g
    { "\u14BB", "7" }, // m
    { "\u14D0", "8" }, // n
    { "\u1505", "+" }, // s
    { "\u14EA", "9" }, // l
    { "\u153E", "0" }, // j
    { "\u155D", "{" }, // v
    { "\u1550", "3" }, // r
    { "\u1585", "6" }, // q
    { "\u1595", "1" }, // N
    { "\u15A6", "\u00D5" }, // &
    { "\u157C", "\u00FF" } // H
        };
        
        // chiffres   
        public static String unicodesICI2CodesDigits[][] = {

    {"1","!"}, // 1
    {"3","#"}, // 3
    {"4","$"}, // 4
    {"5","%"}, // 5
    {"7","&"}, // 7
    {"9","("}, // 9
    {"0",")"}, // 0
    {"8","*"}, // 8
    {"2","@"}, // 2
    {"6","^"}, // 6
        };

        // autres signes        
        public static String unicodesICI2CodesOthers[][] = {

    {"'","<"}, // '
    {"\"",">"}, // "
    {"\u00F7","B"}, // signe de division
    {"/","F"}, // /
    {"(","G"}, // (
    {")","H"}, // )
    {"$","R"}, // $
    {"+","T"}, // +
    {"=","U"}, // =
    {"?","V"}, // ?
    {"\u00D7","Y"}, // signe de multiplication
    {"[","]"}, // [
    {"]","}"}, // ]
    {"\u2026","\u0085"}, // points de suspension
    {"\u201C","\u2018"}, // guillemets d"ouverture
    {"\u201C","\u0091"}, // guillemets d"ouverture
    {"\u201D","\u2019"}, // guillemets de fermeture
    {"\u201D","\u0092"}, // guillemets de fermeture
    {"*","\u2022"}, // *
    {"*","\u0095"}, // *
    {"!","\u00A1"}, // !
    {"#","\u00A3"}, // #
    {"\u2022","\u00AB"}, // point gras
    {"%","\u00B6"}, // %
    {"\u2019","\u00C6"}, // apostrophe d'ouverture
    {"\u02DA","\u00C8"}, // petit cercle en haut (rond en chef)
    {"\u2018","\u00E6"}, // apostrophe de fermeture
    };

    public static String[][] unicodesAIPAITAI2Codes = {
        {"\u1401","\u00C9"}, // ai
        {"\u142f","\u00D1"}, // pai
        {"\u144c","\u00D6"}, // tai
        {"\u146b","\u00DC"}, // kai
        {"\u1489","\u00E1"}, // gai
        {"\u14a3","\u00E0"}, // mai
        {"\u14c0","\u00E2"}, // nai
        {"\u14ed","\u00E3"}, // sai
        {"\u14d3","\u00E4"}, // lai
        {"\u1526","\u00E8"}, // jai
        {"\u1553","\u00EB"}, // vai
        {"\u1542","\u00EA"}, // rai
        {"\u1543","\u00EA"}, // rai
        {"\u166f","\u00F2"}, // qai
        {"\u1670","\u00F4"},  // ngai
    };

    public static String wordChars;
    public static Set fontChars = new HashSet();
    static {
        wordChars =
            "[!#-&(-*@\"+/0-9=?AC-EI-KMNQSWXZ\\[\\^a-z{\\u20ac\\u0080";
        wordChars += "\\u0192\\u0083\\u201e\\u0084";
        wordChars += "\\u2020\\u0086\\u02c6\\u0088\\u2030\\u0089\\u0152\\u008c";
        wordChars += "\\u201c\\u0093\\u2022\\u0095\\u201d\\u0094\\u02dc\\u0098\\u2122";
        wordChars += "\\u0099\\u0153\\u009c\\u0178\\u009f\\u00a5\\u00a7\\u00a9\\u00aa\\u00ac";
        wordChars += "\\u00ae\\u00af\\u00b5\\u00ba\\u00bf\\u00c0\\u00c3\\u00c5\\u00c7";
        wordChars += "\\u00c9\\u00cb-\\u00cf\\u00d1\\u00d3-\\u00d6\\u00d9-\\u00dc";
        wordChars += "\\u00df-\\u00e5\\u00e7-\\u00ef";
        wordChars += "\\u00f1\\u00f2\\u00f4\\u00f7\\u00f8\\u00fc\\u00ff";
        wordChars += "]";
        for (int i=0; i<unicodesICI2Codes.length; i++)
            for (int j=0; j<unicodesICI2Codes[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2Codes[i][1].charAt(j)));
        for (int i=0; i<unicodesICI2CodesDigits.length; i++)
            for (int j=0; j<unicodesICI2CodesDigits[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2CodesDigits[i][1].charAt(j)));
        for (int i=0; i<unicodesAIPAITAI2Codes.length; i++)
            for (int j=0; j<unicodesAIPAITAI2Codes[i][1].length(); j++)
                fontChars.add(new Character(unicodesAIPAITAI2Codes[i][1].charAt(j)));


    }
    public static TransCoder transcoderToUnicodeICI;
    public static TransCoder transcoderToUnicodeAIPAITAI;
    public static TransCoder transcoderToFontICI;
    public static TransCoder transcoderToFontAIPAITAI;

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
        int sbl;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            sbl = sb.length();
            c = s.charAt(i);
            switch (c) {
            case 'w': d = '\u1403'; break; // i
            case 'W': d = '\u1431'; break; // pi
            case 't': d = '\u144E'; break; // ti
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
                break; // ki
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
            case 'u': d = '\u14A5'; break; // mi
            case 'i':  d = '\u14C2'; break; // ni
            case 'y': d = '\u14EF'; break; // si
            case 'o': d = '\u14D5'; break; // li
            case 'p': d = '\u1528'; break; // ji
            case '[': d = '\u1555'; break; // vi
            case 'E': 
                d = '\u1546'; // ri
                if (i != 0 && s.charAt(i-1)=='6') { // q+ri = r+ri (rri)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                break; 
            case 'e': // qi
                d = '\u157F';
                if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146d';
                }
                break; 
            case 'q':
                d = '\u158F'; // ngi
                if (i != 0) {
                    e = s.charAt(i-1);
                    switch (e) {
                    case '8': //n
                    case '1': //ng
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1671'; // nngi
                        break;
                    }
                }
                break;
            case '\u00C3': d = '\u15A0'; break; // &i
            case '\u2122':
            case '\u0099': d = '\u1404'; break; // ii
            case '\u201E':
            case '\u0084': d = '\u1432'; break; // pii
            case '\u2020':
            case '\u0086': d = '\u144F'; break; // tii
            case '\u00AE': // kii
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
            case '\u008C': // gii
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
            case '\u00FC': d = '\u14A6'; break; // mii
            case '\u00EE': d = '\u14C3'; break; // nii
            case '\u00A5': d = '\u14F0'; break; // sii
            case '\u00F8': d = '\u14D6'; break; // lii
            case '\u00BA': d = '\u1529'; break; // jii
            case '\u201C':
            case '\u0093': d = '\u1556'; break; // vii
            case '\u2030':
            case '\u0089': // rii
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1547'; 
                break;
            case '\u00E9':
                d = '\u1580'; // qii
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qii= q+kii (qqii)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146e';
                }
                break; 
            case '\u0153':
            case '\u009C':
                d = '\u1590'; // ngii
                if (i != 0) {
                    e = s.charAt(i-1);
                    switch (e) {
                    case '8': //n
                    case '1': //ng
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1672'; // nngii
                        break;
                    }
                }
            break;
            case '\u00ED': d = '\u15A1'; break; // &ii
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
                if (i != 0 && s.charAt(i-1)=='6') { // q+ru = r+ru (rru)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1548'; break; 
            case 'd': // qu
                d = '\u1581';
                if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146f';
                }
                break; 
            case 'a': 
                d = '\u1591';  // ngu
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
            case '\u00C0': d = '\u15A2'; break; // &u
            case '\u00DF': d = '\u1406'; break; // uu
            case '\u00CD': d = '\u1434'; break; // puu
            case '\u00A9': d = '\u1451'; break; // tuu
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
            case '\u00C5': // guu
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
            case '\u00CB': d = '\u14A8'; break; // muu
            case '\u00AA': d = '\u14C5'; break; // nuu
            case '\u00A7': d = '\u14F2'; break; // suu
            case '\u00AC': d = '\u14D8'; break; // luu
            case '\u00D4': d = '\u152B'; break; // juu
            case '\u00D3': d = '\u1558'; break; // vuu
            case '\u00CE': // ruu
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu= r+ruu (rruu)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1549'; break;
            case '\u00DA':
                d = '\u1582'; // quu
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+quu = q+kuu (qquu)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1470';
                }
                break; 
            case '\u00E5': 
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
            case '\u00EC': d = '\u15A3'; break; // &uu
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
            case 'v': 
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
            case 'I': 
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
            case 'C': 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+rai = r+rai (rrai)
                    sb.setCharAt(sb.length()-1,'\u1550');
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
               j = i+1;
               if (j < l) {
                   e = s.charAt(j);
                   switch (e) {
                   case 'w': 
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
            case 'z': 
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
                        if (precng)
                            d = '\u1675';
                        break;
                    }
                }
                break;
            case '\u0178':
            case '\u009F': d = '\u15A4'; break; // &a
            case '\u20AC':
            case '\u0080': d = '\u140B'; break; // aa
            case '\u00D9': d = '\u1439'; break; // paa
            case '\u00CC': d = '\u1456'; break; // taa
            case '\u00CF': // kaa
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
            case '\u00DB': // gaa
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
            case '\u00B5': d = '\u14AB'; break; // maa
            case '\u02C6':
            case '\u0088': d = '\u14C8'; break; // naa
            case '\u00F1': d = '\u14F5'; break; // saa
            case '\u02DC':
            case '\u0098': d = '\u14DB'; break; // laa
            case '\u00F7': d = '\u152E'; break; // jaa
            case '\u00BF': d = '\u155A'; break; // vaa
            case '\u00C7': 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+raa = r+raa (rraa)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u154C'; break; // raa
            case '\u00E7':
                d = '\u1584'; // qaa
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550'|| sb.charAt(sbl-1)=='\u1585')) { // r+qaa = q+kaa (qqaa)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1473';
                }
                break; 
            case '\u00AF': 
            case '\u02c9':
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
            case '\u00EF': d = '\u15A5'; break; // &aa
            case '2': d = '\u1449'; break; // p
            case '5': d = '\u1466'; break; // t
            case '4': d = '\u1483'; break; // k
            case '=': d = '\u14A1'; break; // g
            case '7': d = '\u14BB'; break; // m
            /*
             * n+ng_ et ng+ng_ = nng_
             */
            case '8': d = '\u14D0'; break; // n
            case '1': 
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
            case '+': d = '\u1505'; break; // s
            case '9': d = '\u14EA'; break; // l
            case '0': d = '\u153E'; break; // j
            case '{': d = '\u155D'; break; // v
            case '3': d = '\u1550'; break; // r
            case '6': d = '\u1585'; break; // q
            case '\u00D5': d = '\u15A6'; break; // &
            case '\u00FF': d = '\u157C'; break;// H
            case '!': d = '1'; break; // 1
            case '#': d = '3'; break; // 3
            case '$': d = '4'; break; // 4
            case '%': d = '5'; break; // 5
            case '&': d = '7'; break; // 7
            case '(': d = '9'; break; // 9
            case ')': d = '0'; break; // 0
            case '*': d = '8'; break; // 8
            case '@': d = '2'; break; // 2
            case '^': d = '6'; break; // 6
            case '<': d = '\''; break; // '
            case '>': d = '\"'; break; // "
            case 'B': d = '\u00F7'; break; // signe de division
            case 'F': d = '/'; break; // /
            case 'G': d = '('; break; // (
            case 'H': d = ')'; break; // )
            case 'R': d = '$'; break; // $
            case 'T': d = '+'; break; // +
            case 'U': d = '='; break; // =
            case 'V': d = '?'; break; // ?
            case 'Y': d = '\u00D7'; break; // signe de multiplication
            case ']': d = '['; break; // [
            case '}': d = ']'; break; // ]
            case '\u0085': d = '\u2026'; break; // points de suspension
            case '\u2018':
            case '\u0091': d = '\u201C'; break; // guillemets d"ouverture
            case '\u2019':
            case '\u0092': d = '\u201D'; break; // guillemets de fermeture
            case '\u2022':
            case '\u0095': d = '*'; break; // *
            case '\u00A1': d = '!'; break; // !
            case '\u00A3': d = '#'; break; // #
            case '\u00AB': d = '\u2022'; break; // point gras
            case '\u00B6': d = '%'; break; // %
            case '\u00C6': d = '\u2019'; break; // apostrophe d'ouverture
            case '\u00C8': d = '\u02DA'; break; // petit cercle en haut (rond en chef)
            case '\u00E6': d = '\u2018'; break; // apostrophe de fermeture
            /*
             * Si aipaitai est true, transcoder aux codes unicode spécifiques.
             */
            case '\u00C9': 
                switch (aipaitai) {
                case 1 : d = '\u1401'; break;
                default: sb.append('\u140A'); d = '\u1403'; break; // ai
                }
                break;
            case '\u00D1': 
                switch (aipaitai) {
                case 1 : d = '\u142F'; break;
                default: sb.append('\u1438'); d = '\u1403'; break; // pai
                }
                break;
            case '\u00D6': 
                switch (aipaitai) {
                case 1 : d = '\u144c'; break;
                default: sb.append('\u1455'); d = '\u1403'; break; // tai
                }
                break;
            case '\u00DC': 
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
                        d = '\u157f';
                    } else
                        sb.append('\u1472'); 
                    d = '\u1403'; 
                    break; // kai
                }
                break;
            case '\u00E1': // gai
                switch (aipaitai) {
                case 1 : 
                    d = '\u1489'; 
                    if (sbl != 0) {
                        e = sb.charAt(sbl-1);
                        switch (e) {
                        case '\u1595': //ng+gai
                            sb.deleteCharAt(sbl-1);
                            d = '\u1670'; // ngai
                            break;
                        }
                    }
                    break;
                default: 
                    if (sbl != 0) {
                        e = sb.charAt(sbl-1);
                        switch (e) {
                        case '\u1595': //ng+ga
                            sb.deleteCharAt(sbl-1);
                            sb.append('\u1593'); // nga+i
                            break;
                        case '\u1596': //nng+ga
                            sb.deleteCharAt(sbl-1);
                            sb.append('\u1675'); // nnga+i
                            break;
                        default:
                            sb.append('\u1490'); // ga+i
                        break;
                        }
                    }
                d = '\u1403'; 
                break;
                }
                break;
            case '\u00E0': 
                switch (aipaitai) {
                case 1 : d = '\u14A3'; break;
                default: sb.append('\u14AA'); d = '\u1403'; break; // mai
                }
                break;
            case '\u00E2': 
                switch (aipaitai) {
                case 1 : d = '\u14C0'; break;
                default: sb.append('\u14c7'); d = '\u1403'; break; // nai
                }
                break;
            case '\u00E3': 
                switch (aipaitai) {
                case 1 : d = '\u14ED'; break;
                default: sb.append('\u14F4'); d = '\u1403'; break; // sai
                }
                break;
            case '\u00E4': 
                switch (aipaitai) {
                case 1 : d = '\u14D3'; break;
                default: sb.append('\u14DA'); d = '\u1403'; break; // lai
                }
                break;
            case '\u00E8': 
                switch (aipaitai) {
                case 1 : d = '\u1526'; break;
                default: sb.append('\u152D'); d = '\u1403'; break; // jai
                }
                break;
            case '\u00EB': 
                switch (aipaitai) {
                case 1 : d = '\u1553'; break;
                default: sb.append('\u1559'); d = '\u1403'; break; // vai
                }
                break;
            case '\u00EA': 
                if (i != 0 && s.charAt(i-1)=='6') { // q+rai = r+rai (rrai)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                switch (aipaitai) {
                case 1 : d = '\u1542'; break;
                default: sb.append('\u154B'); d = '\u1403'; break; // rai
                }
                break;
            case '\u00F2':
                boolean precr = false;
                if (i != 0 && (s.charAt(i-1)=='3' || s.charAt(i-1)=='6')) {
                    precr = true;
                    sb.deleteCharAt(sb.length()-1);
                }
                switch (aipaitai) {
                case 1 :
                    d = '\u166F'; // qai
                    if (precr) {
                        sb.append('\u1585'); // q
                        d = '\u146b'; // kai
                    }
                    break;
                default:
                    if (precr) {
                        sb.append('\u1585'); // q
                        sb.append('\u1472'); // ka
                    } else
                        sb.append('\u1583'); // qa
                    d = '\u1403'; //i
                    break;
                }
                break;
            case '\u00F4':
                boolean precn = false;
                if (i != 0 && (s.charAt(i-1)=='8' || s.charAt(i-1)=='1')) {
                    precn = true;
                    sb.deleteCharAt(sb.length()-1);
                }
                switch (aipaitai) {
                case 1 : 
                    d = '\u1670';
                    if (precn) {
                        sb.append('\u1596'); // nng
                        d = '\u1489';
                    }
                    break;
                default:
                    if (precn)
                        sb.append('\u1675'); // nnga
                    else
                        sb.append('\u1593'); // nga
                    d = '\u1403'; //i
                    break;  // ngai
                }
                break;
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }
    
    static public String transcodeFromUnicode(String s) {
        int i=0;
        int j;
        int l=s.length();
        char c,d,e;
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
            case '\u1555': d = '['; break; // vi
            case '\u1546': d = 'E'; break; // ri
            case '\u157F': d = 'e'; break; // qi
            case '\u158F': d = 'q'; break; // ngi
            case '\u1671': sb.append('8'); d = 'q'; break; // nngi = n + ngi
            case '\u15A0': d = '\u00C3'; break; // &i
            case '\u1404': d = '\u2122'; break; // ii
//          case '\u1404': d = '\u0099'; break; // ii
            case '\u1432': d = '\u201E'; break; // pii
//          case '\u1432': d = '\u0084'; break; // pii
            case '\u144F': d = '\u2020'; break; // tii
//          case '\u144F': d = '\u0086'; break; // tii
            case '\u146E': d = '\u00AE'; break; // kii
            case '\u148C': d = '\u0152'; break; // gii
//          case '\u148C': d = '\u008C'; break; // gii
            case '\u14A6': d = '\u00FC'; break; // mii
            case '\u14C3': d = '\u00EE'; break; // nii
            case '\u14F0': d = '\u00A5'; break; // sii
            case '\u14D6': d = '\u00F8'; break; // lii
            case '\u1529': d = '\u00BA'; break; // jii
            case '\u1556': d = '\u201C'; break; // vii
//          case '\u1556': d = '\u0093'; break; // vii
            case '\u1547': d = '\u2030'; break; // rii
//          case '\u1547': d = '\u0089'; break; // rii
            case '\u1580': d = '\u00E9'; break; // qii
            case '\u1590': d = '\u0153'; break; // ngii
//          case '\u1590': d = '\u009C'; break; // ngii
            case '\u1672': sb.append('8'); d = '\u0153'; break; // nngii = n + ngii
            case '\u15A1': d = '\u00ED'; break; // &ii
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
            case '\u1673': sb.append('8'); d = 'a'; break; // nngu = n + ngu
            case '\u15A2': d = '\u00C0'; break; // &u
            case '\u1406': d = '\u00DF'; break; // uu
            case '\u1434': d = '\u00CD'; break; // puu
            case '\u1451': d = '\u00A9'; break; // tuu
            case '\u1470': d = '\u0192'; break; // kuu
//          case '\u1470': d = '\u0083'; break; // kuu
            case '\u148E': d = '\u00C5'; break; // guu
            case '\u14A8': d = '\u00CB'; break; // muu
            case '\u14C5': d = '\u00AA'; break; // nuu
            case '\u14F2': d = '\u00A7'; break; // suu
            case '\u14D8': d = '\u00AC'; break; // luu
            case '\u152B': d = '\u00D4'; break; // juu
            case '\u1558': d = '\u00D3'; break; // vuu
            case '\u1549': d = '\u00CE'; break; // ruu
            case '\u1582': d = '\u00DA'; break; // quu
            case '\u1592': d = '\u00E5'; break; // nguu
            case '\u1674': sb.append('8'); d = '\u00e5'; break; // nnguu = n + nguu
            case '\u15A3': d = '\u00EC'; break; // &uu
            case '\u140A': 
                c = 'x'; // a
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00C9'; break; // ai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1438': 
                c = 'X'; // pa
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00D1'; break; // pai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1455': 
                c = 'b'; // ta
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00D6'; break; // tai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1472': 
                c = 'v'; // ka
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00DC'; break; // kai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1490': 
                c = 'Z'; // ga
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00E1'; break; // gai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u14AA': 
                c = 'm'; // ma
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00D0'; break; // mai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u14C7': 
                c = 'N'; // na
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00E2'; break; // nai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u14F4': 
                c = 'n'; // sa
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00E3'; break; // sai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u14DA': 
                c = 'M'; // la
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00E4'; break; // lai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u152D': 
                c = '/'; // ja
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00E8'; break; // jai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
//          case '\u152D': d = 'I'; break; // ja
            case '\u1559': 
                c = '?'; // va
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00EB'; break; // vai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u154B': 
                c = 'C'; // ra
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00EA'; break; // rai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1583': 
                c = 'c'; // qa
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00F2'; break; // qai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1593': 
                c = 'z'; // nga
                d = c;
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u1403' : d = '\u00F4'; break; // ngai
                    default: i=i-1; break;
                    }
                } else {
                    i=i-1;
                }
                break;
            case '\u1675': // nnga 
                j = i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u1403' : sb.append('8'); d = '\u00F4'; i=j; break; // nngai
                    default: sb.append('8'); d = 'z'; break; // nnga = n + nga;
                    }
                } else {
                    sb.append('8'); d = 'z'; // nnga = n + nga
                }
                break;
            case '\u15A4': d = '\u0178'; break; // &a
//          case '\u15A4': d = '\u009F'; break; // &a
            case '\u140B': d = '\u20AC'; break; // aa
//          case '\u140B': d = '\u0080'; break; // aa
            case '\u1439': d = '\u00D9'; break; // paa
            case '\u1456': d = '\u00CC'; break; // taa
            case '\u1473': d = '\u00CF'; break; // kaa
            case '\u1491': d = '\u00DB'; break; // gaa
            case '\u14AB': d = '\u00B5'; break; // maa
            case '\u14C8': d = '\u02C6'; break; // naa
//          case '\u14C8': d = '\u0088'; break; // naa
            case '\u14F5': d = '\u00F1'; break; // saa
            case '\u14DB': d = '\u02DC'; break; // laa
//          case '\u14DB': d = '\u0098'; break; // laa
            case '\u152E': d = '\u00F7'; break; // jaa
            case '\u155A': d = '\u00BF'; break; // vaa
            case '\u154C': d = '\u00C7'; break; // raa
            case '\u1584': d = '\u00E7'; break; // qaa
            case '\u1594': d = '\u00AF'; break; // ngaa
            case '\u1676': sb.append('8'); d = '\u00af'; break; // nngaa = n + ngaa
            case '\u15A5': d = '\u00EF'; break; // &aa
            case '\u1449': d = '2'; break; // p
            case '\u1466': d = '5'; break; // t
            case '\u1483': d = '4'; break; // k
            case '\u14A1': d = '='; break; // g
            case '\u14BB': d = '7'; break; // m
            case '\u14D0': d = '8'; break; // n
            case '\u1505': d = '+'; break; // s
            case '\u14EA': d = '9'; break; // l
            case '\u153E': d = '0'; break; // j
            case '\u155D': d = '{'; break; // v
            case '\u1550': d = '3'; break; // r
            case '\u1585': d = '6'; break; // q
            case '\u1595': d = '1'; break; // ng
            case '\u1596': sb.append('8'); d = '1'; break; // nng = n + ng
            case '\u15A6': d = '\u00D5'; break; // &
            case '\u157C': d = '\u00FF'; break;// H
            case '1': d = '!'; break; // 1
            case '3': d = '#'; break; // 3
            case '4': d = '$'; break; // 4
            case '5': d = '%'; break; // 5
            case '7': d = '&'; break; // 7
            case '9': d = '('; break; // 9
            case '0': d = ')'; break; // 0
            case '8': d = '*'; break; // 8
            case '2': d = '@'; break; // 2
            case '6': d = '^'; break; // 6
            case '\'': d = '<'; break; // '
            case '\"': d = '>'; break; // "
            case '\u00F7': d = 'B'; break; // signe de division
            case '/': d = 'F'; break; // /
            case '(': d = 'G'; break; // (
            case ')': d = 'H'; break; // )
            case '$': d = 'R'; break; // $
            case '+': d = 'T'; break; // +
            case '=': d = 'U'; break; // =
            case '?': d = 'V'; break; // ?
            case '\u00D7': d = 'Y'; break; // signe de multiplication
            case '[': d = ']'; break; // [
            case ']': d = '}'; break; // ]
            case '\u2026': d = '\u0085'; break; // points de suspension
            case '\u201C': d = '\u2018'; break; // guillemets d"ouverture
//          case '\u201C': d = '\u0091'; break; // guillemets d"ouverture
            case '\u201D': d = '\u2019'; break; // guillemets de fermeture
//          case '\u201D': d = '\u0092'; break; // guillemets de fermeture
            case '*': d = '\u2022'; break; // *
//          case '*': d = '\u0095'; break; // *
            case '!': d = '\u00A1'; break; // !
            case '#': d = '\u00A3'; break; // #
            case '\u2022': d = '\u00AB'; break; // point gras
            case '%': d = '\u00B6'; break; // %
            case '\u2019': d = '\u00C6'; break; // apostrophe d'ouverture
            case '\u02DA': d = '\u00C8'; break; // petit cercle en haut (rond en chef)
            case '\u2018': d = '\u00E6'; break; // apostrophe de fermeture
            case '\u1401': d = '\u00C9'; break; // ai
            case '\u142f': d = '\u00D1'; break; // pai
            case '\u144c': d = '\u00D6'; break; // tai
            case '\u146b': d = '\u00DC'; break; // kai
            case '\u1489': d = '\u00E1'; break; // gai
            case '\u14a3': d = '\u00E0'; break; // mai
            case '\u14c0': d = '\u00E2'; break; // nai
            case '\u14ed': d = '\u00E3'; break; // sai
            case '\u14d3': d = '\u00E4'; break; // lai
            case '\u1526': d = '\u00E8'; break; // jai
            case '\u1553': d = '\u00EB'; break; // vai
            case '\u1542': d = '\u00EA'; break; // rai
            case '\u1543': d = '\u00EA'; break; // rai
            case '\u166f': d = '\u00F2'; break; // qai
            case '\u1670': d = '\u00F4'; break;  // ngai
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }
    
}
