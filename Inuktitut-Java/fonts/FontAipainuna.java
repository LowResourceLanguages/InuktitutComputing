//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		PoliceAipainunavik.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation:	
//
// Description: 
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: PoliceAipainuna.java,v 1.1 2009/06/19 19:38:37 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceAipainuna.java,v $
// Revision 1.1  2009/06/19 19:38:37  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.4  2009/02/27 20:06:47  farleyb
// *** empty log message ***
//
// Revision 1.3  2006/11/01 15:29:57  farleyb
// *** empty log message ***
//
// Revision 1.2  2006/10/20 17:01:27  farleyb
// *** empty log message ***
//
// Revision 1.1  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.9  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.8  2006/06/19 17:46:27  farleyb
// *** empty log message ***
//
// Revision 1.7  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, n�cessaire entre autres pour le surlignage, o� il faut d�terminer la d�limitation des mots.
//
// Revision 1.6  2006/04/24 17:16:52  farleyb
// Modifications majeures pour simplifier le transcodage et la translitt�ration. Maintenant, les fichiers PoliceXXX.java ne contiennent que des tables.  Les m�thodes sont dans Police.java
//
// Revision 1.5  2006/03/09 18:01:53  farleyb
// Diverses modifications re tables de conversion � unicode
//
// Revision 1.4  2005/12/14 17:30:03  farleyb
// *** empty log message ***
//
// Revision 1.3  2005/02/14 17:22:21  stojanovim
// Small changes to Police*
//
// Revision 1.2  2004/12/07 20:49:49  farleyb
// *** empty log message ***
//
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Premi�re sauvegarde
//
// Revision 1.0  2003-06-25 13:19:57-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:38:25-05  farleyb
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

// Remplacement de codes d'une police donn�e 
// en codes unicode �quivalents repr�sentant
// les m�mes caract�res

public class FontAipainuna {

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
    { "\u1671", "\u2021"  }, // Xi
    { "\u1671", "\u0087"  }, // Xi
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
    { "\u1590", "\u0153" }, // Nii
    { "\u1590", "\u009C" }, // Nii
    { "\u1672", "\u00B7" }, // Xii
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
    { "\u1673", "\u201A"  }, // Xu
    { "\u1673", "\u0082"  }, // Xu
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
    { "\u1592", "\u00E5" }, // Nuu
    { "\u1674", "\u00C2" }, // Xuu
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
    { "\u1559", "?" }, // va
    { "\u154B", "C" }, // ra
    { "\u1583", "c" }, // qa
    { "\u1593", "z" }, // Na
    { "\u1675", "\u00CA" }, // Xa
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
    { "\u1594", "\u00AF" }, // Naa
    { "\u1676", "\u00C1" }, // Xaa
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
    { "\u1596", "\u00D2" }, // X
    { "\u15A6", "\u00D5" }, // &
    { "\u157C", "\u00FF" }, // H

    // groupe des "h" du nunavik
    {"\u157B","\"" }, // h
    {"\u1575","L"}, // hi
    {"\u1576","\u00D8"}, // hii
    {"\u1577","O"}, // ho
    {"\u1578","\u201D"}, // hoo
    {"\u1578","\u0094"}, // hoo
    {"\u1579","P"}, // ha
    {"\u157a","\u00BB"}, // haa
    {"\u1574","\u00A8"}, // hai
 };
    
    public static String unicodesICI2CodesDigits[][] = {

    // chiffres
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
    
    public static String unicodesICI2CodesOthers[][] = {

    // autres signes
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
    {"\u00AB","\u2039"}, // guillemets fran�ais d"ouverture
    {"\u00AB","\u008B"}, // guillemets fran�ais d"ouverture
    {"\u201C","\u2018"}, // guillemets d"ouverture
    {"\u201C","\u0091"}, // guillemets d"ouverture
    {"\u201D","\u2019"}, // guillemets de fermeture
    {"\u201D","\u0092"}, // guillemets de fermeture
    {"*","\u2022"}, // *
    {"*","\u0095"}, // *
    {"\u00BB","\u203A"}, // guillemets fran�ais de fermeture
    {"\u00BB","\u009B"}, // guillemets fran�ais de fermeture
	{"!","\u00A1"}, // !
	{"#","\u00A3"}, // #
	{"\u2022","\u00AB"}, // point gras
	{"&","\u00B4"}, // &
	{"%","\u00B6"}, // %
    {"\u141E", "\u00B8"}, // arr�t de la glotte
	{"@","\u00C4"}, // @
	{"\u2019","\u00C6"}, // apostrophe d"ouverture
	{"\u2018","\u00E6"}, // apostrophe de fermeture
	{"F","\u00F3"}, // F
	{"\u00B6","\u00F5"}, // pied de mouche
	{"\u00A7","\u00F6"}, // paragraphe
	{"\u00A9","\u00F9"}, // signe de droits r�serv�s
	{"\u2122","\u00FA"}, // signe de marque de commerce
	{"\u00AE","\u00FB"}, // signe de marque enregistr�e

    // a+i
    {"\u140A\u1403","\u00C9"}, // ai
    {"\u1438\u1403","\u00D1"}, // pai
    {"\u1455\u1403","\u00D6"}, // tai
    {"\u1472\u1403","\u00DC"}, // kai
    {"\u1490\u1403","\u00E1"}, // gai
    {"\u14AA\u1403","\u00E0"}, // mai
    {"\u14c7\u1403","\u00E2"}, // nai
    {"\u14F4\u1403","\u00E3"}, // sai
    {"\u14DA\u1403","\u00E4"}, // lai
    {"\u152D\u1403","\u00E8"}, // jai
    {"\u1559\u1403","\u00EB"}, // vai
    {"\u154B\u1403","\u00EA"}, // rai
    {"\u1583\u1403","\u00F2"}, // qai
    {"\u1593\u1403","\u00F4"},  // ngai
   
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
        {"\u1543","\u00EA"}, // rai
        {"\u166f","\u00F2"}, // qai
        {"\u1670","\u00F4"},  // ngai
    };

    public static TransCoder transcoderToUnicodeICI;
    public static TransCoder transcoderToUnicodeAIPAITAI;
    public static TransCoder transcoderToFontICI;
    public static TransCoder transcoderToFontAIPAITAI;

    public static String wordChars;
    public static Set fontChars = new HashSet();
    static {
        wordChars =
            "[!#-&(-*@\"+/0-9=?AC-EJ-QSWXZ\\[\\^a-z{\\u2022\\u0095\\u20ac\\u0080\\u201a\\u0082\\u0192\\u0083\\u201e";
        wordChars += "\\u0084\\u2020\\u0086\\u2021\\u0087\\u02c6\\u0088\\u2030\\u0089";
        wordChars += "\\u0152\\u008c\\u201c\\u0093\\u201d\\u0094\\u02dc\\u0098\\u2122";
        wordChars += "\\u0099\\u0153\\u009c\\u0178\\u009f\\u00a5\\u00a7-\\u00aa\\u00ac";
        wordChars += "\\u00ae\\u00af\\u00ba\\u00bb\\u00bf\\u00c0-\\u00c3\\u00c5\\u00c7";
        wordChars += "\\u00c9-\\u00cf\\u00d1-\\u00d6\\u00d8-\\u00dc\\u00df-\\u00ef";
        wordChars += "\\u00f1\\u00f2\\u00f4\\u00f7\\u00f8\\u00fc\\u00ff\\u1400-\\u167f";
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
    
    static public String transcodeToUnicode(String s) {
        return transcodeToUnicode(s,null); // null = no aipaitai
    }
    
    /*
     * aipaitaiMode: String -  "aipaitai" : convertir a+i au caract�re unicode AI
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
            case '\u0077': d = '\u1403'; break; // w -> i
            case '\u0057': d = '\u1431'; break; // W -> pi
            case '\u0074': d = '\u144E'; break; // t -> ti
            case '\u0072': // r -> ki
                d = '\u146D'; 
//                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
//                    sb.deleteCharAt(sbl-1);
//                    sb.deleteCharAt(sbl-2);
//                    sb.append('\u1585'); // r+r+ki = q+ki (qqi)
//                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
//                    sb.deleteCharAt(sbl-1); // q+r+ki = q+ki (qqi)
//                } else if (sbl > 1 && sb.charAt(sbl-2)=='\u1550' && sb.charAt(sbl-1)=='\u1585') {
//                    sb.deleteCharAt(sbl-2); // r+q+ki = q+ki (qqi)
//                } else 
                	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                		sb.deleteCharAt(sbl-1);
                		d = '\u157f'; // r+ki = qi
                	}
                break;
            case '\u0051': // Q -> gi
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
            case '\u0075': d = '\u14A5'; break; // u -> mi
            case '\u0069':  d = '\u14C2'; break; // i -> ni
            case '\u0079': d = '\u14EF'; break; // y -> si
            case '\u006F': d = '\u14D5'; break; // o -> li
            case '\u0070': d = '\u1528'; break; // p -> ji
            case '\u005B': d = '\u1555'; break; // [ -> vi
            case '\u0045': 
                if (i != 0 && sb.charAt(i-1)=='\u1585') { // q+ri = r+ri (rri)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1546'; // E -> ri
                break; 
            case '\u0065': // e -> qi
                d = '\u157F';
                if (sbl > 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146d';
                }
                break; 
            case '\u0071':
                d = '\u158F'; // q -> ngi
                if (sbl > 0) {
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
            case '\u2021':
            case '\u0087': d = '\u1671'; break; // nngi
            case '\u00C3': d = '\u15A0'; break; // &i
            case '\u2122':
            case '\u0099': d = '\u1404'; break; // ii
            case '\u201E': 
            case '\u0084': d = '\u1432'; break; // pii
            case '\u2020': 
            case '\u0086': d = '\u144F'; break; // tii
            case '\u00AE': // kii
                d = '\u146E'; 
//                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
//                    sb.deleteCharAt(sbl-1);
//                    sb.deleteCharAt(sbl-2);
//                    sb.append('\u1585');
//                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
//                    sb.deleteCharAt(sbl-1);
//                } else 
                	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {  // r+kii = qii
                		sb.deleteCharAt(sbl-1);
                		d = '\u1580';
                	}
                break;
            case '\u0152': 
            case '\u008C': // gii
                d = '\u148C'; 
                if (sbl > 0) {
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
            case '\u0089':
                if (sbl > 0 && sb.charAt(sbl-1)=='\u1585') { // q+rii = r+rii (rrii)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1547'; 
                break; // rii
            case '\u00E9':
                d = '\u1580'; // qii
                if (sbl > 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qii= q+kii (qqii)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146e';
                }
                break; 
            case '\u0153':
            case '\u009C':
                d = '\u1590'; // ngii
                if (sbl > 0) {
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
            case '\u2219':
            case '\u00B7': d = '\u1672'; break; // nngii
            case '\u00ED': d = '\u15A1'; break; // &ii
            case '\u0073': d = '\u1405'; break; // s -> u
            case '\u0053': d = '\u1433'; break; // S -> pu
            case '\u0067': d = '\u1450'; break; // g -> tu
            case '\u0066': // f -> ku
                d = '\u146F'; 
//                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
//                    sb.deleteCharAt(sbl-1);
//                    sb.deleteCharAt(sbl-2);
//                    sb.append('\u1585');
//                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
//                    sb.deleteCharAt(sbl-1);
//                } else 
                	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') { // r+ku = qu
                		sb.deleteCharAt(sbl-1);
                		d = '\u1581';
                	}
                break;
            case '\u0041': // A -> gu
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
            case '\u006a': d = '\u14A7'; break; // j -> mu
            case '\u006b': d = '\u14C4'; break; // k -> nu
            case '\u0068': d = '\u14F1'; break; // h -> su
            case '\u006c': d = '\u14D7'; break; // l -> lu
            case '\u004a': d = '\u152A'; break; // J -> ju
            case '\u004b': d = '\u1557'; break; // K -> vu
            case '\u0044': 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ru = r+ru (rru)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                d = '\u1548'; break; // D -> ru
            case '\u0064':
                d = '\u1581'; // d -> qu
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u146f';
                }
                break; 
            case '\u0061': 
                d = '\u1591';  // a -> ngu
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
            case '\u201A':
            case '\u0082': d = '\u1673'; break; // nngu
            case '\u00C0': d = '\u15A2'; break; // &u
            case '\u00DF': d = '\u1406'; break; // uu
            case '\u00CD': d = '\u1434'; break; // puu
            case '\u00A9': d = '\u1451'; break; // tuu
            case '\u0192': 
            case '\u0083': // kuu
                d = '\u1470'; 
//                if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
//                    sb.deleteCharAt(sbl-1);
//                    sb.deleteCharAt(sbl-2);
//                    sb.append('\u1585');
//                } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
//                    sb.deleteCharAt(sbl-1);
//                } else 
                	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') { // r+kuu = quu
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
            case '\u00CE': 
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ruu= r+ruu (rruu)
                    sb.setCharAt(sbl-1,'\u1550');
                }
                d = '\u1549'; break; // ruu
            case '\u00DA':
                d = '\u1582'; // quu
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+quu = q+kuu (qquu)
                    sb.deleteCharAt(sbl-1);
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
                        sb.deleteCharAt(sbl-1);
                        d = '\u1674'; // nnguu
                        break;
                    }
                }
                break;
            case '\u00C2': d = '\u1674'; break; // nnguu
            case '\u00EC': d = '\u15A3'; break; // &uu
            case '\u0078': // x -> a
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u1401';
            		++i;
            	} else
                	d = '\u140a';
                break;
            case '\u0058': // X -> pa 
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u142f';
            		++i;
            	} else
                	d = '\u1438';
            	break;
            case '\u0062': // b -> ta
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u144c';
            		++i;
            	} else
                	d = '\u1455';
                break;
            case '\u0076': // v -> ka
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
                	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') { // r+kai = qai
                		sb.deleteCharAt(sbl-1);
                		d = '\u166f';
                	} else
                		d = '\u146b';
            		++i;
            	} else
                	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') { 
                		sb.deleteCharAt(sbl-1);
                		d = '\u1583';
                	} else
                		d = '\u1472';
                break;
            case '\u005a': // Z -> ga
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
                    if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1670'; // ng+ga+i > ngai
                    }  else 
                    	d = '\u1489'; // ga+i > gai
            		++i;
            	} else
                    if (sbl != 0 && sb.charAt(sbl-1)=='\u1595') {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1593'; // nga
                    } else  if (sbl != 0 && sb.charAt(sbl-1)=='\u1596') {
                        sb.deleteCharAt(sbl-1);
                        d = '\u1675'; // nnga
                    } else
                    	d = '\u1490'; // ga
                break;
            case '\u006d': // m -> ma
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u14a3';
            		++i;
            	} else
                	d = '\u14aa';
            	break;
            case '\u004e': // N -> na
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u14c0';
            		++i;
            	} else
                	d = '\u14c7';
            	break;
            case '\u006e': // n -> sa
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u14ed';
            		++i;
            	} else
                	d = '\u14f4';
            	break;
            case '\u004d': // M -> la
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u14d3';
            		++i;
            	} else
                	d = '\u14da';
            	break;
            case '\u002f': // / -> ja
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u1526';
            		++i;
            	} else
                	d = '\u152d';
            	break;
            case '\u003f': // ? -> va
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u1553';
            		++i;
            	} else
                	d = '\u1559';
            	break;
            case '\u0043': // C -> ra
            	if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
            		d = '\u1542';
         		   	if (sbl != 0) {
                       e = sb.charAt(sbl-1);
                       switch (e) {
                       case '\u1585': //q
                    	   sb.setCharAt(sbl-1,'\u1550');
                           break;
                       }
         		   	}
            		++i;
            	} else {
                	d = '\u154b'; // ra
                    if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+ra = r+ra (rra)
                        sb.setCharAt(sbl-1,'\u1550');
                    }
                }
            	break;
           case '\u0063': // c -> qa
        	   if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') { // qa + i
        		   d = '\u166f'; // qa+i > qai
        		   if (sbl != 0) {
                       e = sb.charAt(sbl-1);
                       switch (e) {
                       case '\u1550': //r
                       case '\u1585': //q
                    	   sb.setCharAt(sbl-1,'\u1585');
                           d = '\u146b'; // q + kai
                           break;
                       }
        		   }
        		   ++i;
        	   } else {
        		   d = '\u1583'; // qa
                   if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qa = q+ka (qqa)
                       sb.deleteCharAt(sbl-1);
                       sb.append('\u1585');
                       d = '\u1472';
                   }
        	   }
        	   break;
            case '\u007a': // z -> nga
         	   if (aipaitai==1 && i+1<l && s.charAt(i+1)=='\u0077') {
        		   d = '\u1670'; // nga+i > ngai
        		   if (sbl != 0) {
                       e = sb.charAt(sbl-1);
                       switch (e) {
                       case '\u14d0': //n
                       case '\u1595': //ng
                    	   sb.setCharAt(sbl-1,'\u1596');
                           d = '\u1489'; // nng + gai
                           break;
                       }
        		   }
        		   ++i;
        	   } else {
        		   d = '\u1593'; // nga
                   if (sbl != 0) {
                       e = sb.charAt(sbl-1);
                       switch (e) {
                       case '\u14d0': //n
                       case '\u1595': //ng
                           sb.deleteCharAt(sbl-1);
                           d = '\u1675'; // nnga
                           break;
                       }
                   }
        	   }
        	   break;
            case '\u00CA': d = '\u1675'; break; // nnga
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
                    sb.setCharAt(sbl-1,'\u1550');
                }
                d = '\u154C'; break; // raa
            case '\u00E7':
                d = '\u1584'; // qaa
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r+qaa = q+kaa (qqaa)
                    sb.deleteCharAt(sbl-1);
                    sb.append('\u1585');
                    d = '\u1473';
                }
                break; 
            case '\u00AF': 
                d = '\u1594'; // ngaa
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
            case '\u00C1': d = '\u1676'; break; // nngaa
            case '\u00EF': d = '\u15A5'; break; // &aa
            
            case '\u0032': d = '\u1449'; break; // 2 -> p
            case '\u0035': d = '\u1466'; break; // 5 -> t
            case '\u0034': d = '\u1483'; break; // 4 -> k
            case '\u003d': d = '\u14A1'; break; // = -> g
            case '\u0037': d = '\u14BB'; break; // 7 -> m
            case '\u0038': d = '\u14D0'; break; // 8 -> n
            case '\u002b': d = '\u1505'; break; // + -> s
            case '\u0039': d = '\u14EA'; break; // 9 -> l
            case '\u0030': d = '\u153E'; break; // 0 -> j
            case '\u007b': d = '\u155D'; break; // { -> v
            case '\u0033': d = '\u1550'; break; // 3 -> r
            case '\u0036': d = '\u1585'; break; // 6 -> q
            case '\u00D5': d = '\u15A6'; break; // &
            case '\u00FF': d = '\u157C'; break;// H
            /*
             * n+ng_ et ng+ng_ = nng_
             */
            case '\u0031': 
                d = '\u1595'; // 1 -> ng
//                if (sbl != 0) {
//                    e = sb.charAt(sbl-1);
//                    switch (e) {
//                    case '\u14d0': //n
//                    case '\u1595': //ng
//                        sb.deleteCharAt(sb.length()-1);
//                        d = '\u1596'; // nng
//                        break;
//                    }
//                }
                break;
            case '\u00D2' : d = '\u1596'; break; // nng

            case '\u0021': d = '1'; break; // ! -> 1
            case '\u0023': d = '3'; break; // # -> 3
            case '\u0024': d = '4'; break; // $ -> 4
            case '\u0025': d = '5'; break; // % -> 5
            case '\u0026': d = '7'; break; // & -> 7
            case '\u0028': d = '9'; break; // ( -> 9
            case '\u0029': d = '0'; break; // ) -> 0
            case '\u002a': d = '8'; break; // * -> 8
            case '\u005e': d = '6'; break; // ^ -> 6
            case '\u0040': d = '2'; break; // @ -> 2
            
            case '\u002c': d = '\u002c'; break; // ,
            case '\u002d': d = '\u002d'; break; // -
            case '\u002e': d = '\u002e'; break; // .
            case '\u003a': d = '\u003a'; break; // :
            case '\u003b': d = '\u003b'; break; // ;
            case '\u003c': d = '\''; break; // < -> '
            case '\u003e': d = '\"'; break; // > -> "
            case '\u0042': d = '\u00F7'; break; // B -> signe de division
            case '\u0046': d = '/'; break; // F -> /
            case '\u0047': d = '('; break; // G -> (
            case '\u0048': d = ')'; break; // H -> )
            case '\u0052': d = '$'; break; // R -> $
            case '\u0054': d = '+'; break; // T -> +
            case '\u0055': d = '='; break; // U -> =
            case '\u0056': d = '?'; break; // V -> ?
            case '\u0059': d = '\u00D7'; break; // Y -> signe de multiplication
            case 0x5c: d = 0x5c; break; // \ -> \
            case '\u005d': d = '['; break; // ] -> [
            case '\u005f': d = '\u005f'; break; // .
            case 0x7c: d = 0x7c; break; // |
            case '\u007d': d = ']'; break; // } -> ]
            case 0x7e: d = 0x7e; break; // ~
            case 0xa0: d = 0xa0; break; // espace insécable
            case '\u00A1': d = '!'; break; // !
            case 0xa2: d = 0xa2; break; // centime
            case '\u00A3': d = '#'; break; // #
            case '\u00AB': d = '\u2022'; break; // point gras
            case 0xb0: d = 0xb0; break; // degré
            case 0xb1: d = 0xb1; break; // plus ou moins
            case '\u00B4': d = '&'; break; // &
            case '\u00B6': d = '%'; break; // %
            case '\u00B8': d = '\u141E'; break; // arrêt de la glotte
            case '\u00C4': d = '@'; break; // @
            case '\u00C6': d = '\u2019'; break; // apostrophe d"ouverture
            case '\u00E6': d = '\u2018'; break; // apostrophe de fermeture
            case '\u00F3': d = '\u15b4'; break; // F OUÉ pied-noir
            case '\u00F5': d = '\u00B6'; break; // pied de mouche
            case '\u00F6': d = '\u00A7'; break; // paragraphe
            case '\u00F9': d = '\u00A9'; break; // signe de droits r�serv�s
            case '\u00FA': d = '\u2122'; break; // signe de marque de commerce
            case '\u00FB': d = '\u00AE'; break; // signe de marque enregistr�e
            case 0x2c7: d = 0x2d9; break; // point en chef
            case '\u2013': d = '\u2013'; break; // tiret demi-cadratin
            case '\u2014': d = '\u2014'; break; // tiret cadratin
            case '\u2018': 
            case '\u0091': d = '\u201c'; break; // guillemet-apostrophe double culbuté
            case '\u2019':
            case '\u0092': d = '\u201D'; break; // guillemet-apostrophe double
            case '\u2022':
            case '\u0095': d = '*'; break; // *
            case '\u2026':
            case '\u0085': d = '\u2026'; break; // points de suspension
            case '\u2039':
            case '\u008B': d = '\u00AB'; break; // guillemets fran�ais d"ouverture
            case '\u203A':
            case '\u009B': d = '\u00BB'; break; // guillemets fran�ais de fermeture
            /*
             * Si aipaitai est true, transcoder aux codes unicode sp�cifiques.
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
            case '\u00DC': // kai
                switch (aipaitai) {
                case 1 : 
                    d = '\u146B'; 
//                    if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
//                        sb.deleteCharAt(sbl-1);
//                        sb.deleteCharAt(sbl-2);
//                        sb.append('\u1585');
//                    } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
//                        sb.deleteCharAt(sbl-1);
//                    } else 
                    	if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
                    		sb.deleteCharAt(sbl-1);
                    		d = '\u166f';
                    	}
                    break;
                default: 
//                    if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1550') {
//                        sb.deleteCharAt(sbl-1);
//                        sb.deleteCharAt(sbl-2);
//                        sb.append('\u1585');
//                    } else if (sbl > 1 && sb.charAt(sbl-1)=='\u1550' && sb.charAt(sbl-2)=='\u1585') {
//                        sb.deleteCharAt(sbl-1);
//                        sb.append('\u1472'); 
//                    } else 
                    if (sbl > 0 && sb.charAt(sbl-1)=='\u1550') {
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
            case '\u00EA':  // rai
                if (sbl != 0 && sb.charAt(sbl-1)=='\u1585') { // q+rai = r+rai (rrai)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                switch (aipaitai) {
                case 1 : d = '\u1542'; break;
                default: sb.append('\u154B'); d = '\u1403'; break; // rai
                }
                break;
            case '\u00F2': // qai
                boolean precr = false;
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1550' || sb.charAt(sbl-1)=='\u1585')) { // r ou q + qai
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
            case '\u00F4': // ngai
                boolean precn = false;
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u1595' || sb.charAt(sbl-1)=='\u14d0')) { // ng ou n + ngai
                    precn = true;
                    sb.deleteCharAt(sbl-1);
                }
                switch (aipaitai) {
                case 1 : 
                    d = '\u1670'; // ngai
                    if (precn) {
                        sb.append('\u1596'); // n ou ng + ngai > nng + gai
                        d = '\u1489';
                    }
                    break;
                default:
                    if (precn)
                        sb.append('\u1675'); // nnga + i
                    else
                        sb.append('\u1593'); // nga + i
                    d = '\u1403'; //i
                    break;  // ngai
                }
                break;
                
            case '\u0022': d = '\u157b'; break; // " -> h
            case '\u004c': d = '\u1575'; break; // L -> hi
            case '\u00D8': d = '\u1576'; break; // hii
            case '\u004f': d = '\u1577'; break; // O -> ho
            case '\u201D': d = '\u1578'; break; // hoo
            case '\u0050': d = '\u1579'; break; // P -> ha
            case '\u00BB': d = '\u157a'; break; // haa
            case '\u00a8': d = '\u1574'; break; // hai
            
            default: 
            	if ( c > 0x20 )
            		d = 0x20; // espace autrement
            	else
            		d = c;
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
            case '\u158F': d = 'q'; break; // Ni
            case '\u1671': d = '\u2021'; break; // Xi
//            case '\u1671': d = '\u0087'; break; // Xi
            case '\u15A0': d = '\u00C3'; break; // &i
            case '\u1404': d = '\u2122'; break; // ii
//            case '\u1404': d = '\u0099'; break; // ii
            case '\u1432': d = '\u201E'; break; // pii
//            case '\u1432': d = '\u0084'; break; // pii
            case '\u144F': d = '\u2020'; break; // tii
//            case '\u144F': d = '\u0086'; break; // tii
            case '\u146E': d = '\u00AE'; break; // kii
            case '\u148C': d = '\u0152'; break; // gii
//            case '\u148C': d = '\u008C'; break; // gii
            case '\u14A6': d = '\u00FC'; break; // mii
            case '\u14C3': d = '\u00EE'; break; // nii
            case '\u14F0': d = '\u00A5'; break; // sii
            case '\u14D6': d = '\u00F8'; break; // lii
            case '\u1529': d = '\u00BA'; break; // jii
            case '\u1556': d = '\u201C'; break; // vii
//            case '\u1556': d = '\u0093'; break; // vii
            case '\u1547': d = '\u2030'; break; // rii
//            case '\u1547': d = '\u0089'; break; // rii
            case '\u1580': d = '\u00E9'; break; // qii
            case '\u1590': d = '\u0153'; break; // Nii
//            case '\u1590': d = '\u009C'; break; // Nii
            case '\u1672': d = '\u00B7'; break; // Xii
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
            case '\u1591': d = 'a'; break; // Nu
            case '\u1673': d = '\u201A'; break; // Xu
//            case '\u1673': d = '\u0082'; break; // Xu
            case '\u15A2': d = '\u00C0'; break; // &u
            case '\u1406': d = '\u00DF'; break; // uu
            case '\u1434': d = '\u00CD'; break; // puu
            case '\u1451': d = '\u00A9'; break; // tuu
            case '\u1470': d = '\u0192'; break; // kuu
//            case '\u1470': d = '\u0083'; break; // kuu
            case '\u148E': d = '\u00C5'; break; // guu
            case '\u14A8': d = '\u00CB'; break; // muu
            case '\u14C5': d = '\u00AA'; break; // nuu
            case '\u14F2': d = '\u00A7'; break; // suu
            case '\u14D8': d = '\u00AC'; break; // luu
            case '\u152B': d = '\u00D4'; break; // juu
            case '\u1558': d = '\u00D3'; break; // vuu
            case '\u1549': d = '\u00CE'; break; // ruu
            case '\u1582': d = '\u00DA'; break; // quu
            case '\u1592': d = '\u00E5'; break; // Nuu
            case '\u1674': d = '\u00C2'; break; // Xuu
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
                c = 'b';
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
                    case '\u1403' : d = '\u00E0'; break; // mai
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
            case '\u1675': d = '\u00CA'; break; // Xa
            case '\u15A4': d = '\u0178'; break; // &a
//            case '\u15A4': d = '\u009F'; break; // &a
            case '\u140B': d = '\u20AC'; break; // aa
//            case '\u140B': d = '\u0080'; break; // aa
            case '\u1439': d = '\u00D9'; break; // paa
            case '\u1456': d = '\u00CC'; break; // taa
            case '\u1473': d = '\u00CF'; break; // kaa
            case '\u1491': d = '\u00DB'; break; // gaa
            case '\u14AB': d = '\u00B5'; break; // maa
            case '\u14C8': d = '\u02C6'; break; // naa
//            case '\u14C8': d = '\u0088'; break; // naa
            case '\u14F5': d = '\u00F1'; break; // saa
            case '\u14DB': d = '\u02DC'; break; // laa
//            case '\u14DB': d = '\u0098'; break; // laa
            case '\u152E': d = '\u00F7'; break; // jaa
            case '\u155A': d = '\u00BF'; break; // vaa
            case '\u154C': d = '\u00C7'; break; // raa
            case '\u1584': d = '\u00E7'; break; // qaa
            case '\u1594': d = '\u00AF'; break; // Naa
            case '\u1676': d = '\u00C1'; break; // Xaa
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
            case '\u1595': d = '1'; break; // N
            case '\u1596': d = '\u00D2'; break; // X
            case '\u15A6': d = '\u00D5'; break; // &
            case '\u157C': d = '\u00FF'; break; // H
            case '\u157B': d = '\"'; break; // h
            case '\u1575': d = 'L'; break; // hi
            case '\u1576': d = '\u00D8'; break; // hii
            case '\u1577': d = 'O'; break; // ho
            case '\u1578': d = '\u201D'; break; // hoo
//            case '\u1578': d = '\u0094'; break; // hoo
            case '\u1579': d = 'P'; break; // ha
            case '\u157a': d = '\u00BB'; break; // haa
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
            case '\u00AB': d = '\u2039'; break; // guillemets fran�ais d"ouverture
//            case '\u00AB': d = '\u008B'; break; // guillemets fran�ais d"ouverture
            case '\u201C': d = '\u2018'; break; // guillemets d"ouverture
//            case '\u201C': d = '\u0091'; break; // guillemets d"ouverture
            case '\u201D': d = '\u2019'; break; // guillemets de fermeture
//            case '\u201D': d = '\u0092'; break; // guillemets de fermeture
            case '*': d = '\u2022'; break; // *
//            case '*': d = '\u0095'; break; // *
            case '\u00BB': d = '\u203A'; break; // guillemets fran�ais de fermeture
//            case '\u00BB': d = '\u009B'; break; // guillemets fran�ais de fermeture
            case '!': d = '\u00A1'; break; // !
            case '#': d = '\u00A3'; break; // #
            case '\u2022': d = '\u00AB'; break; // point gras
            case '&': d = '\u00B4'; break; // &
            case '%': d = '\u00B6'; break; // %
            case '\u141E': d = '\u00B8'; break; // arr�t de la glotte
            case '@': d = '\u00C4'; break; // @
            case '\u2019': d = '\u00C6'; break; // apostrophe d"ouverture
            case '\u2018': d = '\u00E6'; break; // apostrophe de fermeture
            case 'F': d = '\u00F3'; break; // F
            case '\u00B6': d = '\u00F5'; break; // pied de mouche
            case '\u00A7': d = '\u00F6'; break; // paragraphe
            case '\u00A9': d = '\u00F9'; break; // signe de droits r�serv�s
            case '\u2122': d = '\u00FA'; break; // signe de marque de commerce
            case '\u00AE': d = '\u00FB'; break; // signe de marque enregistr�e
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
            case '\u1543': d = '\u00EA'; break; // rai
            case '\u1542': d = '\u00EA'; break; // rai
            case '\u166f': d = '\u00F2'; break; // qai
            case '\u1670': d = '\u00F4'; break;  // ngai
            case '\u1574': d = '\u00A8'; break; // hai
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}
