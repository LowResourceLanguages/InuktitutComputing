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
// Date de création/Date of creation:	
//
// Description: 
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: PoliceAujaq2.java,v 1.1 2009/06/19 19:38:39 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceAujaq2.java,v $
// Revision 1.1  2009/06/19 19:38:39  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.6  2006/11/01 15:29:57  farleyb
// *** empty log message ***
//
// Revision 1.5  2006/10/20 17:01:26  farleyb
// *** empty log message ***
//
// Revision 1.4  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.3  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.2  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, nécessaire entre autres pour le surlignage, où il faut déterminer la délimitation des mots.
//
// Revision 1.1  2006/03/09 18:01:53  farleyb
// Diverses modifications re tables de conversion à unicode
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
// Première sauvegarde
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

import utilities1.Util;
import script.TransCoder;

// Remplacement de codes d'une police donnée 
// en codes unicode équivalents représentant
// les mêmes caractères

public class FontAujaq2  {


    public static String unicodesICI2Codes[][] = {

	// i
    { "\u1403", "\u017d"  }, // i
    { "\u1403", "\u008E"  }, // i
    { "\u1431", "\u2021"  }, // pi
    { "\u1431", "\u0087"  }, // pi
    { "\u144E", "\u00A9"  }, // ti
    { "\u146D", "\u2022"  }, // ki
    { "\u146D", "\u0095"  }, // ki
    { "\u148B", "\u20ac"  }, // gi
    { "\u148B", "\u0080"  }, // gi
    { "\u14A5", "\u00ab"  }, // mi
    { "\u14C2", "\u00b2"  }, // ni
    { "\u14EF", "\u00b9"  }, // si
    { "\u14D5", "\u00c0"  }, // li
    { "\u1528", "\u00c7"  }, // ji
    { "\u1555", "\u00dd"  }, // vi
    { "\u1546", "\u00e4"  }, // ri
    { "\u157F", "\u00eb"  }, // qi
    { "\u158F", "\u00f2"  }, // Ni
    { "\u15A0", "\u00f9"  }, // &i


	// ii
    { "\u1404", "\u017e" }, // ii
    { "\u1404", "\u009e" }, // ii
    { "\u1432", "\u02c6" }, // pii
    { "\u1432", "\u0088" }, // pii
    { "\u144F", "\u00aa" }, // tii
    { "\u146E", "\u2014" }, // kii
    { "\u146E", "\u0097" }, // kii
    { "\u148C", "\u00ca" }, // gii
    { "\u14A6", "\u00ac" }, // mii
    { "\u14C3", "\u00b3" }, // nii
    { "\u14F0", "\u00ba" }, // sii
    { "\u14D6", "\u00c1" }, // lii
    { "\u1529", "\u00c8" }, // jii
    { "\u1556", "\u00de" }, // vii
    { "\u1547", "\u00e5" }, // rii
    { "\u1580", "\u00ec" }, // qii
    { "\u1590", "\u00f3" }, // Nii
    { "\u15A1", "\u00fa" }, // &ii

	// u
    { "\u1405", "\u201a"  }, // u
    { "\u1405", "\u0082"  }, // u
    { "\u1433", "\u2030" }, // pu
    { "\u1433", "\u0089" }, // pu
    { "\u1450", "\u00a6"  }, // tu
    { "\u146F", "\u02dc"  }, // ku
    { "\u148D", "\u0178"  }, // gu
    { "\u148D", "\u009f"  }, // gu
    { "\u14A7", "\u00ad"  }, // mu
    { "\u14C4", "\u00b4"  }, // nu
    { "\u14F1", "\u00bb"  }, // su
    { "\u14D7", "\u00c2" }, // lu
    { "\u152A", "\u00cb"  }, // ju
    { "\u1557", "\u00df"  }, // vu
    { "\u1548", "\u00e6"  }, // ru
    { "\u1581", "\u00ed"  }, // qu
    { "\u1591", "\u00f4"  }, // Nu
    { "\u15A2", "\u00fb"  }, // &u

	// uu
    { "\u1406", "\u0192" }, // uu
    { "\u1406", "\u0083" }, // uu
    { "\u1434", "\u0160" }, // puu
    { "\u1434", "\u008a" }, // puu
    { "\u1451", "\u2018" }, // tuu
    { "\u1451", "\u0091" }, // tuu
    { "\u1470", "\u2122" }, // kuu
    { "\u1470", "\u0099" }, // kuu
    { "\u148E", "\u00a0" }, // guu
    { "\u14A8", "\u00ae" }, // muu
    { "\u14C5", "\u00b5" }, // nuu
    { "\u14F2", "\u00bc" }, // suu
    { "\u14D8", "\u00c3" }, // luu
    { "\u152B", "\u00cc" }, // juu
    { "\u1558", "\u00e0" }, // vuu
    { "\u1549", "\u00e7" }, // ruu
    { "\u1582", "\u00ee" }, // quu
    { "\u1592", "\u00f5" }, // Nuu
    { "\u15A3", "\u00fc" }, // &uu

	// a
    { "\u140A", "\u2026" }, // a
    { "\u140A", "\u0085" }, // a
    { "\u1438", "\u2039" }, // pa
    { "\u1438", "\u008b" }, // pa
    { "\u1455", "\u2019" }, // ta
    { "\u1455", "\u0092" }, // ta
    { "\u1472", "\u0161" }, // ka
    { "\u1472", "\u009a" }, // ka
    { "\u1490", "\u00a1" }, // ga
    { "\u14AA", "\u00af" }, // ma
    { "\u14C7", "\u00b6" }, // na
    { "\u14F4", "\u00bd" }, // sa
    { "\u14DA", "\u00c4" }, // la
    { "\u152D", "\u00cd" }, // ja
    { "\u1559", "\u00e1" }, // va
    { "\u154B", "\u00e8" }, // ra
    { "\u1583", "\u00ef" }, // qa
    { "\u1593", "\u00f6" }, // Na
    { "\u15A4", "\u00fd" }, // &a

	// aa
    { "\u140B", "\u2020" }, // aa
    { "\u140B", "\u0086" }, // aa
    { "\u1439", "\u0152" }, // paa
    { "\u1439", "\u008c" }, // paa
    { "\u1456", "\u201c" }, // taa
    { "\u1456", "\u0093" }, // taa
    { "\u1473", "\u203a" }, // kaa
    { "\u1473", "\u009b" }, // kaa
    { "\u1491", "\u00a2" }, // gaa
    { "\u14AB", "\u00b0" }, // maa
    { "\u14C8", "\u00b7" }, // naa
    { "\u14F5", "\u00be" }, // saa
    { "\u14DB", "\u00c5" }, // laa
    { "\u152E", "\u00ce" }, // jaa
    { "\u155A", "\u00e2" }, // vaa
    { "\u154C", "\u00e9" }, // raa
    { "\u1584", "\u00f0" }, // qaa
    { "\u1594", "\u00f7" }, // Naa
    { "\u15A5", "\u00fe" }, // &aa

    // consonnes seules
    { "\u1449", "\u00a8" }, // p
    { "\u1466", "\u201d" }, // t
    { "\u1466", "\u0094" }, // t
    { "\u1483", "\u0153" }, // k
    { "\u1483", "\u009c" }, // k
    { "\u14A1", "\u00a3" }, // g
    { "\u14BB", "\u00b1" }, // m
    { "\u14D0", "\u00b8" }, // n
    { "\u1505", "\u00bf" }, // s
    { "\u14EA", "\u00c6" }, // l
    { "\u153E", "\u00cf" }, // j
    { "\u155D", "\u00e3" }, // v
    { "\u1550", "\u00ea" }, // r
    { "\u1585", "\u00f1" }, // q
    { "\u1595", "\u00f8" }, // N
    { "\u1596", "\u00a7" }, // NN
    { "\u15A6", "\u00ff" } // &
    };
    
    
    public static String unicodesICI2CodesOthers[][] = {

	// autres signes
    {"\u0141","\u005e"}, // L avec une barre (ou tilde)
    {"\u2022","\u00a5"}, // puce
    {"\u2026","\u00c9"}, // points de suspension
    {"\u2013","\u00d0"}, // tiret demi-quadratin
    {"\u2014","\u00d1"}, // tiret quadratin
    {"\u201c","\u00d2"}, // guillements doubles inversés
    {"\u201d","\u00d3"}, // guillements doubles
    {"\u2018","\u00d4"}, // guillement simple inversé
    {"\u2019","\u00d5"}, // guillement simple
    {"\u00f7","\u00d6"}, // division
    {"\u25cb","\u00d7"}, // cercle blanc
    {"\u00b0","\u00d8"}, // petit cercle en haut
    {"\u25c0","\u00d9"}, // flèche noire vers la gauche
    {"\u25b6","\u00da"}, // flèche noire vers la droite
    {"\u25a0","\u2013"}, // carré noir
//    {"\ufffd","\u201e"}, // inuksuk
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


    public static TransCoder transcoderToUnicodeICI;
    public static TransCoder transcoderToUnicodeAIPAITAI;
    public static TransCoder transcoderToFontICI;
    public static TransCoder transcoderToFontAIPAITAI;
//    public static String wordChars = "[/0-9=?A-EI-QSWXZ[a-z{}<>]`~YV!#-&(-*@]";

    public static String wordChars;
    public static Set fontChars = new HashSet();
    static {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<unicodesICI2Codes.length; i++) {
            sb.append(unicodesICI2Codes[i][1]);
            for (int j=0; j<unicodesICI2Codes[i][1].length(); j++)
                fontChars.add(new Character(unicodesICI2Codes[i][1].charAt(j)));
        }
        for (int i=0; i<unicodesICI2CodesOthers.length; i++)
            sb.append(unicodesICI2CodesOthers[i][1]);
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
        char r='\u00ea', q='\u00f1', n='\u00b8', ng1='\u00a7', ng2='\u00f8';
        int sbl;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            sbl = sb.length();
            c = s.charAt(i);
            switch (c) {
            case '\u017d':
            case '\u0080': d = '\u1403'; break; // i
            case '\u2021':
            case '\u0087': d = '\u1431'; break; // pi
            case '\u008e':
            case '\u00A9': d = '\u144E'; break; // ti
            case '\u2022':
            case '\u0095': // ki
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
            case '\u20ac':
            case '\u009d': // gi
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
            case '\u00ab': d = '\u14A5'; break; // mi
            case '\u00b2': d = '\u14C2'; break; // ni
            case '\u00b9': d = '\u14EF'; break; // si
            case '\u00c0': d = '\u14D5'; break; // li
            case '\u00c7': d = '\u1528'; break; // ji
            case '\u00dd': d = '\u1555'; break; // vi
            case '\u00e4': // ri
                if (i != 0 && s.charAt(i-1)==q) { // q+ri = r+ri (rri)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1546';
                break; 
            case '\u00eb': // qi
                d = '\u157F';
                if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qi = q+ki (qqi)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146d';
                }
                break; 
            case '\u00f2': // ngi
                d = '\u158F';
                if (i != 0) {
                    e = s.charAt(i-1);
                    if (e==n || e==ng1 || e==ng2) {
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1671'; // nngi
                    }
                }
                break;
            case '\u00f9': d = '\u15A0'; break; // &i
            case '\u017e':
            case '\u009e': d = '\u1404'; break; // ii
            case '\u02c6':
            case '\u0088': d = '\u1432'; break; // pii
            case '\u00aa': d = '\u144F'; break; // tii
            case '\u2014':
            case '\u0097': // kii
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
            case '\u00ca': // gii
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
            case '\u00ac': d = '\u14A6'; break; // mii
            case '\u00b3': d = '\u14C3'; break; // nii
            case '\u00ba': d = '\u14F0'; break; // sii
            case '\u00c1': d = '\u14D6'; break; // lii
            case '\u00c8': d = '\u1529'; break; // jii
            case '\u00de': d = '\u1556'; break; // vii
            case '\u00e5': // rii 
                if (i != 0 && s.charAt(i-1)==q) { // q+rii = r+rii (rrii)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1547'; 
                break;
            case '\u00ec': // qii
                d = '\u1580';
                if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qii= q+kii (qqii)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146e';
                }
                break; 
            case '\u00f3': // ngii
                d = '\u1590';
                if (i != 0) {
                    e = s.charAt(i-1);
                    if (e==n || e==ng1 || e==ng2) {
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1672'; // nngii
                    }
                }
            break;
            case '\u00fa': d = '\u15A1'; break; // &ii
            case '\u201a': 
            case '\u00dc':
            case '\u0082': d = '\u1405'; break; // u
            case '\u2030': 
            case '\u0089': d = '\u1433'; break; // pu
            case '\u00a6': d = '\u1450'; break; // tu
            case '\u02dc': // ku
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
            case '\u0178': 
            case '\u009f': // gu
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
            case '\u00ad': d = '\u14A7'; break; // mu
            case '\u00b4': d = '\u14C4'; break; // nu
            case '\u00bb': d = '\u14F1'; break; // su
            case '\u00c2': d = '\u14D7'; break; // lu
            case '\u00cb': d = '\u152A'; break; // ju
            case '\u00df': d = '\u1557'; break; // vu
            case '\u00e6': // ru
                if (i != 0 && s.charAt(i-1)==q) { // q+ru = r+ru (rru)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1548'; 
                break;
            case '\u00ed': // qu
                d = '\u1581'; 
                if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qu = q+ku (qqu)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u146f';
                }
                break; 
            case '\u00f4': // ngu
                d = '\u1591'; 
                if (i != 0) {
                    e = s.charAt(i-1);
                    if (e==n || e==ng1 || e==ng2) {
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1673'; // nngu
                    }
                }
                break;
            case '\u00fb': d = '\u15A2'; break; // &u
            case '\u0192': 
            case '\u0083': d = '\u1406'; break; // uu
            case '\u0160': 
            case '\u008a': d = '\u1434'; break; // puu
            case '\u2018': 
            case '\u0091': d = '\u1451'; break; // tuu
            case '\u2122': 
            case '\u0099': // kuu
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
            case '\u00a0': // guu
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
            case '\u00ae': d = '\u14A8'; break; // muu
            case '\u00b5': d = '\u14C5'; break; // nuu
            case '\u00bc': d = '\u14F2'; break; // suu
            case '\u00c3': d = '\u14D8'; break; // luu
            case '\u00cc': d = '\u152B'; break; // juu
            case '\u00e0': d = '\u1558'; break; // vuu
            case '\u00e7': //ruu
                if (i != 0 && s.charAt(i-1)==q) { // q+ruu= r+ruu (rruu)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u1549'; 
                break;
            case '\u00ee':// quu
                d = '\u1582'; 
                if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+quu = q+kuu (qquu)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1470';
                }
                break; 
            case '\u00f5': // nguu
                d = '\u1592'; 
                if (i != 0) {
                    e = s.charAt(i-1);
                    if (e==n || e==ng1 || e==ng2) {
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1674'; // nnguu
                    }
                }
                break;
            case '\u00fc': d = '\u15A3'; break; // &uu
            
            case '\u2026':
            case '\u0085':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E':
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
            case '\u2039':
            case '\u008b':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u2019':
            case '\u0092':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u0161':
            case '\u009a': // ka
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008e':
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
            case '\u00a1': // ga
                d = '\u1490';
                j=i+1;
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u017d':
                    case '\u008d':
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
            case '\u00af': // ma
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
                        switch (aipaitai) {
                        case 1 : d = '\u14a3'; break;
                        default: sb.append('\u14aa'); d = '\u1403'; break; // nai
                        }
                        break;
                    default: d = '\u14aa'; i--; break; // na
                    }
                } else {
                    d = '\u14aa'; i--;
                }
                break;
               case '\u00b6':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u00bd':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u00c4':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u00cd':
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u00e1':
               i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u00e8':
                if (i != 0 && s.charAt(i-1)==q) { // q+rai = r+rai (rrai)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                i=i+1;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case '\u017d':
                    case '\u008E':
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
            case '\u00ef':
                j = i+1;
                if (j < l) {
                e = s.charAt(j);
                switch (e) {
                case '\u017d':
                case '\u008E': 
                    switch (aipaitai) {
                    case 1 : 
                        d = '\u166f'; 
                        if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qa+i = q+kai (qqai)
                            sb.deleteCharAt(sb.length()-1);
                            sb.append('\u1585');
                            d = '\u146b';
                        }
                        break; 
                    default: 
                        if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qa+i = q+ka+i (qqai)
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
                    if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qa = q+ka (qqa)
                        sb.deleteCharAt(sb.length()-1);
                        sb.append('\u1585');
                        d = '\u1472';
                    }
                    break; 
                }
            } else {
                d = '\u1583';
                if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qa = q+ka (qqa)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1472';
                }
            }
            break;
            case '\u00f6':
                j=i+1;
                d = '\u1593'; // nga
                boolean precng = false;
                if (i != 0 && (s.charAt(i-1)==n || s.charAt(i-1)==ng1 || s.charAt(i-1)==ng2)) {
                    precng = true;
                    sb.deleteCharAt(sb.length()-1);
                }
                if (j < l) {
                    e = s.charAt(j);
                    switch (e) {
                    case '\u017d':
                    case '\u008E': 
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
            case '\u00fd': d = '\u15A4'; break; // &a
            case '\u2020': 
            case '\u0086': d = '\u140B'; break; // aa
            case '\u0152': 
            case '\u008c': d = '\u1439'; break; // paa
            case '\u201c': 
            case '\u0093': d = '\u1456'; break; // taa
            case '\u203a': 
            case '\u009b': // kaa
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
            case '\u00a2': // gaa
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
            case '\u00b0': d = '\u14AB'; break; // maa
            case '\u00b7': d = '\u14C8'; break; // naa
            case '\u00be': d = '\u14F5'; break; // saa
            case '\u00c5': d = '\u14DB'; break; // laa
            case '\u00ce': d = '\u152E'; break; // jaa
            case '\u00e2': d = '\u155A'; break; // vaa
            case '\u00e9': // raa
                if (i != 0 && s.charAt(i-1)==q) { // q+raa = r+raa (rraa)
                    sb.setCharAt(sb.length()-1,'\u1550');
                }
                d = '\u154C'; 
                break;
            case '\u00f0': // qaa
                d = '\u1584'; 
                if (i != 0 && (s.charAt(i-1)==r || s.charAt(i-1)==q)) { // r+qaa = q+kaa (qqaa)
                    sb.deleteCharAt(sb.length()-1);
                    sb.append('\u1585');
                    d = '\u1473';
                }
                break; 
            case '\u00f7': // ngaa
                d = '\u1594';
                if (i != 0) {
                    e = s.charAt(i-1);
                    if (e==n || e==ng1 || e==ng2) {
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1676'; // nngi
                    }
                }
                break;
            case '\u00fe': d = '\u15A5'; break; // &aa
            case '\u00a8': d = '\u1449'; break; // p
            case '\u201d': 
            case '\u0094': d = '\u1466'; break; // t
            case '\u0153': 
            case '\u009c': d = '\u1483'; break; // k
            case '\u00a3': d = '\u14A1'; break; // g
            case '\u00b1': d = '\u14BB'; break; // m
            case '\u00b8': d = '\u14D0'; break; // n
            case '\u00bf': d = '\u1505'; break; // s
            case '\u00c6': d = '\u14EA'; break; // l
            case '\u00cf': d = '\u153E'; break; // j
            case '\u00e3': d = '\u155D'; break; // v
            case '\u00ea': d = '\u1550'; break; // r
            case '\u00f1': d = '\u1585'; break; // q
            case '\u00a7': 
            case '\u00f8': // ng
                d = '\u1595';
                if (i != 0) {
                    e = s.charAt(i-1);
                    if (e==n || e==ng1 || e==ng2) {
                        sb.deleteCharAt(sb.length()-1);
                        d = '\u1596'; // nng
                    }
                }
                break;
            case '\u00ff': d = '\u15A6'; break;// &
            case 'H': d = '\u157c'; break; // H
            
            case '\u005e': d = '\u0141'; break; // L avec une barre (ou tilde)
            case '\u00a5': d = '\u2022'; break; // puce
            case '\u00c9': d = '\u2026'; break; // points de suspension
            case '\u00d0': d = '\u2013'; break; // tiret demi-quadratin
            case '\u00d1': d = '\u2014'; break; // tiret quadratin
            case '\u00d2': d = '\u201c'; break; // guillements doubles inversés
            case '\u00d3': d = '\u201d'; break; // guillements doubles
            case '\u00d4': d = '\u2018'; break; // guillement simple inversé
            case '\u00d5': d = '\u2019'; break; // guillement simple
            case '\u00d6': d = '\u00f7'; break; // division
            case '\u00d7': d = '\u25cb'; break; // cercle blanc
            case '\u00d8': d = '\u00b0'; break; // petit cercle en haut
            case '\u00d9': d = '\u25c0'; break; // flèche noire vers la gauche
            case '\u00da': d = '\u25b6'; break; // flèche noire vers la droite
            case '\u2013': d = '\u25a0'; break; // carré noir
            case '\u201e': d = '\ufffd'; break; // inuksuk - aucun unicode
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
            case '\u1403': d = '\u017d'; break; // i
//          case '\u1403': d = '\u008E'; break; // i
            case '\u1431': d = '\u2021'; break; // pi
//          case '\u1431': d = '\u0087'; break; // pi
            case '\u144E': d = '\u00A9'; break; // ti
            case '\u146D': d = '\u2022'; break; // ki
//          case '\u146D': d = '\u0095'; break; // ki
            case '\u148B': d = '\u20ac'; break; // gi
//          case '\u148B': d = '\u0080'; break; // gi
            case '\u14A5': d = '\u00ab'; break; // mi
            case '\u14C2': d = '\u00b2'; break; // ni
            case '\u14EF': d = '\u00b9'; break; // si
            case '\u14D5': d = '\u00c0'; break; // li
            case '\u1528': d = '\u00c7'; break; // ji
            case '\u1555': d = '\u00dd'; break; // vi
            case '\u1546': d = '\u00e4'; break; // ri
            case '\u157F': d = '\u00eb'; break; // qi
            case '\u158F': d = '\u00f2'; break; // Ni
            case '\u15A0': d = '\u00f9'; break; // &i
            case '\u1404': d = '\u017e'; break; // ii
//          case '\u1404': d = '\u009e'; break; // ii
            case '\u1432': d = '\u02c6'; break; // pii
//          case '\u1432': d = '\u0088'; break; // pii
            case '\u144F': d = '\u00aa'; break; // tii
            case '\u146E': d = '\u2014'; break; // kii
//          case '\u146E': d = '\u0097'; break; // kii
            case '\u148C': d = '\u00ca'; break; // gii
            case '\u14A6': d = '\u00ac'; break; // mii
            case '\u14C3': d = '\u00b3'; break; // nii
            case '\u14F0': d = '\u00ba'; break; // sii
            case '\u14D6': d = '\u00c1'; break; // lii
            case '\u1529': d = '\u00c8'; break; // jii
            case '\u1556': d = '\u00de'; break; // vii
            case '\u1547': d = '\u00e5'; break; // rii
            case '\u1580': d = '\u00ec'; break; // qii
            case '\u1590': d = '\u00f3'; break; // Nii
            case '\u15A1': d = '\u00fa'; break; // &ii
            case '\u1405': d = '\u201a'; break; // u
//          case '\u1405': d = '\u0082'; break; // u
            case '\u1433': d = '\u2030'; break; // pu
//          case '\u1433': d = '\u0089'; break; // pu
            case '\u1450': d = '\u00a6'; break; // tu
            case '\u146F': d = '\u02dc'; break; // ku
            case '\u148D': d = '\u0178'; break; // gu
//          case '\u148D': d = '\u009f'; break; // gu
            case '\u14A7': d = '\u00ad'; break; // mu
            case '\u14C4': d = '\u00b4'; break; // nu
            case '\u14F1': d = '\u00bb'; break; // su
            case '\u14D7': d = '\u00c2'; break; // lu
            case '\u152A': d = '\u00cb'; break; // ju
            case '\u1557': d = '\u00df'; break; // vu
            case '\u1548': d = '\u00e6'; break; // ru
            case '\u1581': d = '\u00ed'; break; // qu
            case '\u1591': d = '\u00f4'; break; // Nu
            case '\u15A2': d = '\u00fb'; break; // &u
            case '\u1406': d = '\u0192'; break; // uu
//          case '\u1406': d = '\u0083'; break; // uu
            case '\u1434': d = '\u0160'; break; // puu
//          case '\u1434': d = '\u008a'; break; // puu
            case '\u1451': d = '\u2018'; break; // tuu
//          case '\u1451': d = '\u0091'; break; // tuu
            case '\u1470': d = '\u2122'; break; // kuu
//          case '\u1470': d = '\u0099'; break; // kuu
            case '\u148E': d = '\u00a0'; break; // guu
            case '\u14A8': d = '\u00ae'; break; // muu
            case '\u14C5': d = '\u00b5'; break; // nuu
            case '\u14F2': d = '\u00bc'; break; // suu
            case '\u14D8': d = '\u00c3'; break; // luu
            case '\u152B': d = '\u00cc'; break; // juu
            case '\u1558': d = '\u00e0'; break; // vuu
            case '\u1549': d = '\u00e7'; break; // ruu
            case '\u1582': d = '\u00ee'; break; // quu
            case '\u1592': d = '\u00f5'; break; // Nuu
            case '\u15A3': d = '\u00fc'; break; // &uu
            case '\u140A': d = '\u2026'; break; // a
//          case '\u140A': d = '\u0085'; break; // a
            case '\u1438': d = '\u2039'; break; // pa
//          case '\u1438': d = '\u008b'; break; // pa
            case '\u1455': d = '\u2019'; break; // ta
//          case '\u1455': d = '\u0092'; break; // ta
            case '\u1472': d = '\u0161'; break; // ka
//          case '\u1472': d = '\u009a'; break; // ka
            case '\u1490': d = '\u00a1'; break; // ga
            case '\u14AA': d = '\u00af'; break; // ma
            case '\u14C7': d = '\u00b6'; break; // na
            case '\u14F4': d = '\u00bd'; break; // sa
            case '\u14DA': d = '\u00c4'; break; // la
            case '\u152D': d = '\u00cd'; break; // ja
            case '\u1559': d = '\u00e1'; break; // va
            case '\u154B': d = '\u00e8'; break; // ra
            case '\u1583': d = '\u00ef'; break; // qa
            case '\u1593': d = '\u00f6'; break; // Na
            case '\u15A4': d = '\u00fd'; break; // &a
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
            case '\u140B': d = '\u2020'; break; // aa
//          case '\u140B': d = '\u0086'; break; // aa
            case '\u1439': d = '\u0152'; break; // paa
//          case '\u1439': d = '\u008c'; break; // paa
            case '\u1456': d = '\u201c'; break; // taa
//          case '\u1456': d = '\u0093'; break; // taa
            case '\u1473': d = '\u203a'; break; // kaa
//          case '\u1473': d = '\u009b'; break; // kaa
            case '\u1491': d = '\u00a2'; break; // gaa
            case '\u14AB': d = '\u00b0'; break; // maa
            case '\u14C8': d = '\u00b7'; break; // naa
            case '\u14F5': d = '\u00be'; break; // saa
            case '\u14DB': d = '\u00c5'; break; // laa
            case '\u152E': d = '\u00ce'; break; // jaa
            case '\u155A': d = '\u00e2'; break; // vaa
            case '\u154C': d = '\u00e9'; break; // raa
            case '\u1584': d = '\u00f0'; break; // qaa
            case '\u1594': d = '\u00f7'; break; // Naa
            case '\u15A5': d = '\u00fe'; break; // &aa
            case '\u1449': d = '\u00a8'; break; // p
            case '\u1466': d = '\u201d'; break; // t
//          case '\u1466': d = '\u0094'; break; // t
            case '\u1483': d = '\u0153'; break; // k
//          case '\u1483': d = '\u009c'; break; // k
            case '\u14A1': d = '\u00a3'; break; // g
            case '\u14BB': d = '\u00b1'; break; // m
            case '\u14D0': d = '\u00b8'; break; // n
            case '\u1505': d = '\u00bf'; break; // s
            case '\u14EA': d = '\u00c6'; break; // l
            case '\u153E': d = '\u00cf'; break; // j
            case '\u155D': d = '\u00e3'; break; // v
            case '\u1550': d = '\u00ea'; break; // r
            case '\u1585': d = '\u00f1'; break; // q
            case '\u1595': d = '\u00f8'; break; // N
            case '\u1596': d = '\u00a7'; break; // NN
            case '\u15A6': d = '\u00ff'; break;// &
            case '\u0141': d = '\u005e'; break; // L avec une barre (ou tilde)
            case '\u2022': d = '\u00a5'; break; // puce
            case '\u2026': d = '\u00c9'; break; // points de suspension
            case '\u2013': d = '\u00d0'; break; // tiret demi-quadratin
            case '\u2014': d = '\u00d1'; break; // tiret quadratin
            case '\u201c': d = '\u00d2'; break; // guillements doubles inversés
            case '\u201d': d = '\u00d3'; break; // guillements doubles
            case '\u2018': d = '\u00d4'; break; // guillement simple inversé
            case '\u2019': d = '\u00d5'; break; // guillement simple
            case '\u00f7': d = '\u00d6'; break; // division
            case '\u25cb': d = '\u00d7'; break; // cercle blanc
            case '\u00b0': d = '\u00d8'; break; // petit cercle en haut
            case '\u25c0': d = '\u00d9'; break; // flèche noire vers la gauche
            case '\u25b6': d = '\u00da'; break; // flèche noire vers la droite
            case '\u25a0': d = '\u2013'; break; // carré noir
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}
