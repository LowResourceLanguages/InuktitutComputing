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
// Date de cr�ation/Date of creation:	
//
// Description: 
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: PoliceProsyl.java,v 1.1 2009/06/19 19:38:38 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: PoliceProsyl.java,v $
// Revision 1.1  2009/06/19 19:38:38  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.14  2009/02/10 15:22:30  farleyb
// *** empty log message ***
//
// Revision 1.13  2006/11/01 15:29:57  farleyb
// *** empty log message ***
//
// Revision 1.12  2006/10/20 17:01:26  farleyb
// *** empty log message ***
//
// Revision 1.11  2006/10/19 13:33:18  farleyb
// *** empty log message ***
//
// Revision 1.10  2006/07/20 18:54:54  farleyb
// *** empty log message ***
//
// Revision 1.9  2006/07/18 21:11:43  farleyb
// *** empty log message ***
//
// Revision 1.8  2006/05/03 17:41:02  farleyb
// Ajout de wordChars aux polices, n�cessaire entre autres pour le surlignage, o� il faut d�terminer la d�limitation des mots.
//
// Revision 1.7  2006/04/24 17:16:52  farleyb
// Modifications majeures pour simplifier le transcodage et la translitt�ration. Maintenant, les fichiers PoliceXXX.java ne contiennent que des tables.  Les m�thodes sont dans Police.java
//
// Revision 1.6  2006/03/09 18:01:53  farleyb
// Diverses modifications re tables de conversion � unicode
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
// Premi�re sauvegarde
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

// Remplacement de codes d'une police donn�e 
// en codes unicode �quivalents repr�sentant
// les m�mes caract�res

public class FontProsyl {

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
            { "\u1555", "="  }, // vi
            { "\u1546", "E"  }, // ri
            { "\u157F", "e"  }, // qi
            { "\u158F", "q"  }, // Ni
            { "\u15A0", "O"  }, // &i

            // ii
            { "\u1404", "`w" }, // ii
            { "\u1432", "`W" }, // pii
            { "\u144F", "`t" }, // tii
            { "\u146E", "`r" }, // kii
            { "\u148C", "`Q" }, // gii
            { "\u14A6", "`u" }, // mii
            { "\u14C3", "~i" }, // nii
            { "\u14F0", "<y" }, // sii
            { "\u14D6", "~o" }, // lii
            { "\u1529", ">p" }, // jii
            { "\u1556", "`=" }, // vii
            { "\u1547", "~E" }, // rii
            { "\u1580", "`e" }, // qii
            { "\u1590", "<q" }, // Nii
            { "\u15A1", "~O" }, // &ii

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
            { "\u15A2", "L"  }, // &u

            // uu
            { "\u1406", ">s" }, // uu
            { "\u1434", ">S" }, // puu
            { "\u1451", ">g" }, // tuu
            { "\u1470", "]f" }, // kuu
            { "\u148E", "]A" }, // guu
            { "\u14A8", "<j" }, // muu
            { "\u14C5", "~k" }, // nuu
            { "\u14F2", "<h" }, // suu
            { "\u14D8", "~l" }, // luu
            { "\u152B", "<J" }, // juu
            { "\u1558", ">K" }, // vuu
            { "\u1549", ">D" }, // ruu
            { "\u1582", "<d" }, // quu
            { "\u1592", "<a" }, // Nuu
            { "\u15A3", "~L" }, // &uu

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
            { "\u15A4", "I" }, // &a

            // aa
            { "\u140B", "<x" }, // aa
            { "\u1439", "<X" }, // paa
            { "\u1456", "]b" }, // taa
            { "\u1473", ">v" }, // kaa
            { "\u1491", ">Z" }, // gaa
            { "\u14AB", ">m" }, // maa
            { "\u14C8", "~N" }, // naa
            { "\u14F5", ">n" }, // saa
            { "\u14DB", "~M" }, // laa
            { "\u152E", ">/" }, // jaa
            { "\u155A", "<?" }, // vaa
            { "\u154C", ">C" }, // raa
            { "\u1584", "`c" }, // qaa
            { "\u1594", "<z" }, // Naa
            { "\u15A5", "~I" }, // &aa

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
            { "\u155D", "}" }, // v
            { "\u1550", "3" }, // r
            { "\u1585", "6" }, // q
            { "\u1595", "1" }, // N
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
            
        	{"=","+"}, // =
        	{"%","-"}, // %
        	{"/","F"}, // /
            { "(","G"  }, // (
            { ")","H"  }, // )
        	{"$","R"}, // $
        	{"+","T"}, // +
            {"!","U"}, // !
            {"?","V"}, // ?
        	{"_","Y"}, // _
        	{"}","\\"}, // }
        	{"-","_"}, // -
        	{"{","|"} // {
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

    // <  >  ]  `  ~
    public static String dotCodes = "<>]`~";

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
     * aipaitaiMode: String -  "aipaitai" : convertir a+i au caract�re unicode AI
     */
    static public String transcodeToUnicode(String s, String aipaitaiMode) {
        int aipaitai = aipaitaiMode==null? 0 : aipaitaiMode.equals("aipaitai")? 1 : 0;
        boolean dot = false;
        int i=0,j;
        int l=s.length();
        char c,d,e;
        StringBuffer sb = new StringBuffer();
        int sbl;
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
           case '=': d = '\u1555'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break;  // vi
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
           case 'O': d = '\u15A0'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &i
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
            case 'L': d = '\u15A2'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &u
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
            case 'I': d = '\u15A4'; if (dot) {dot=false;sb.deleteCharAt(sbl-1); d++;} break; // &a
            case '2': d ='\u1449'; break; // p
            case '5': d ='\u1466'; break; // t
            case '4': d ='\u1483'; break; // k
            case '[': d ='\u14A1'; break; // g
            case '7': d ='\u14BB'; break; // m
            case '8': d ='\u14D0'; break; // n
            case '{': d ='\u1505'; break; // s
            case '9': d ='\u14EA'; break; // l
            case '0': d ='\u153E'; break; // j
            case '}': d ='\u155D'; break; // v
            case '3': d ='\u1550'; break; // r
            case '6': d ='\u1585'; break; // q
            case '1': // ng
                d = '\u1595';
                if (sbl != 0 && (sb.charAt(sbl-1)=='\u14d0' || sb.charAt(sbl-1)=='\u1595')) {
                    sb.deleteCharAt(sbl-1);
                    d='\u1596';
                }
                break;
            case 'P': d ='\u15A6'; break; // &
            case 'B': d ='\u157C'; break; // H
            case '!': d ='1'; break; // 1
            case '@': d ='2'; break; // 2
            case '#': d ='3'; break; // 3
            case '$': d ='4'; break; // 4
            case '%': d ='5'; break; // 5
            case '^': d ='6'; break; // 6
            case '&': d ='7'; break; // 7
            case '*': d ='8'; break; // 8
            case '(': d ='9'; break; // 9
            case ')': d ='0'; break; // 0
            case '+': d ='='; break; // =
            case '-': d ='%'; break; // %
            case 'F': d ='/'; break; // /
            case 'G': d ='('; break; // (
            case 'H': d =')'; break; // )
            case 'R': d ='$'; break; // $
            case 'T': d ='+'; break; // +
            case 'U': d ='!'; break; // !
            case 'V': d ='?'; break; // ?
            case 'Y': d ='_'; break; // _
            case '\\': d ='}'; break; // }
            case '_': d ='-'; break; // -
            case '|': d ='{'; break;// {
            case '`':
            case '~':
            case '<':
            case '>':
            case ']':
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
            case '\u1555': d = '='; break; // vi
            case '\u1546': d = 'E'; break; // ri
            case '\u157F': d = 'e'; break; // qi
            case '\u158F': d = 'q'; break; // Ni
            case '\u15A0': d = 'O'; break; // &i
            case '\u1404': sb.append('`'); d = 'w'; break; // ii
            case '\u1432': sb.append('`'); d = 'W'; break; // pii
            case '\u144F': sb.append('`'); d = 't'; break; // tii
            case '\u146E': sb.append('`'); d = 'r'; break; // kii
            case '\u148C': sb.append('`'); d = 'Q'; break; // gii
            case '\u14A6': sb.append('`'); d = 'u'; break; // mii
            case '\u14C3': sb.append('~'); d = 'i'; break; // nii
            case '\u14F0': sb.append('<'); d = 'y'; break; // sii
            case '\u14D6': sb.append('~'); d = 'o'; break; // lii
            case '\u1529': sb.append('>'); d = 'p'; break; // jii
            case '\u1556': sb.append('`'); d = '='; break; // vii
            case '\u1547': sb.append('~'); d = 'E'; break; // rii
            case '\u1580': sb.append('`'); d = 'e'; break; // qii
            case '\u1590': sb.append('<'); d = 'q'; break; // Nii
            case '\u15A1': sb.append('~'); d = 'O'; break; // &ii
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
            case '\u15A2': d = 'L'; break; // &u
            case '\u1406': sb.append('>'); d = 's'; break; // uu
            case '\u1434': sb.append('>'); d = 'S'; break; // puu
            case '\u1451': sb.append('>'); d = 'g'; break; // tuu
            case '\u1470': sb.append(']'); d = 'f'; break; // kuu
            case '\u148E': sb.append(']'); d = 'A'; break; // guu
            case '\u14A8': sb.append('<'); d = 'j'; break; // muu
            case '\u14C5': sb.append('~'); d = 'k'; break; // nuu
            case '\u14F2': sb.append('<'); d = 'h'; break; // suu
            case '\u14D8': sb.append('~'); d = 'l'; break; // luu
            case '\u152B': sb.append('<'); d = 'J'; break; // juu
            case '\u1558': sb.append('>'); d = 'K'; break; // vuu
            case '\u1549': sb.append('>'); d = 'D'; break; // ruu
            case '\u1582': sb.append('<'); d = 'd'; break; // quu
            case '\u1592': sb.append('<'); d = 'a'; break; // Nuu
            case '\u15A3': sb.append('~'); d = 'L'; break; // &uu
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
            case '\u140B': sb.append('<'); d = 'x'; break; // aa
            case '\u1439': sb.append('<'); d = 'X'; break; // paa
            case '\u1456': sb.append(']'); d = 'b'; break; // taa
            case '\u1473': sb.append('>'); d = 'v'; break; // kaa
            case '\u1491': sb.append('>'); d = 'Z'; break; // gaa
            case '\u14AB': sb.append('>'); d = 'm'; break; // maa
            case '\u14C8': sb.append('~'); d = 'N'; break; // naa
            case '\u14F5': sb.append('>'); d = 'n'; break; // saa
            case '\u14DB': sb.append('~'); d = 'M'; break; // laa
            case '\u152E': sb.append('>'); d = '/'; break; // jaa
            case '\u155A': sb.append('<'); d = '?'; break; // vaa
            case '\u154C': sb.append('>'); d = 'C'; break; // raa
            case '\u1584': sb.append('`'); d = 'c'; break; // qaa
            case '\u1594': sb.append('<'); d = 'z'; break; // Naa
            case '\u15A5': sb.append('~'); d = 'I'; break; // &aa
            case '\u1449': d = '2'; break; // p
            case '\u1466': d = '5'; break; // t
            case '\u1483': d = '4'; break; // k
            case '\u14A1': d = '['; break; // g
            case '\u14BB': d = '7'; break; // m
            case '\u14D0': d = '8'; break; // n
            case '\u1505': d = '{'; break; // s
            case '\u14EA': d = '9'; break; // l
            case '\u153E': d = '0'; break; // j
            case '\u155D': d = '}'; break; // v
            case '\u1550': d = '3'; break; // r
            case '\u1585': d = '6'; break; // q
            case '\u1595': d = '1'; break; // N
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
            case '=': d = '+'; break; // =
            case '%': d = '-'; break; // %
            case '/': d = 'F'; break; // /
            case '(': d = 'G'; break; // (
            case ')': d = 'H'; break; // )
            case '$': d = 'R'; break; // $
            case '+': d = 'T'; break; // +
            case '!': d = 'U'; break; // !
            case '?': d = 'V'; break; // ?
            case '_': d = 'Y'; break; // _
            case '}': d = '\\'; break; // }
            case '-': d = '_'; break; // -
            case '{': d = '|'; break;// {
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }

}
