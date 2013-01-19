// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File: Roman.java
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
// Description: Diverses méthodes associées aux caractères inuktitut
//
// -----------------------------------------------------------------------

package script;

import java.util.Arrays;

public abstract class Roman {

    public static int V = 0; // verbe; voyelle

    public static int C = 2; // consonne

//    public static int VV = 3; // voyelle double

    public static int NONUNICODE = 0;

    public static int UNICODE = 1;

    public static char[] apicals = { 't', 'l', 'j', '&', 's', 'n', 'd' };

    public static char[] bilabials = { 'p', 'v', 'm', 'b' };

    public static char[] velars = { 'k', 'g', 'N' };

    public static char[] uvulars = { 'q', 'r' };


    public static boolean apicalConsonant(char car) {
        if (Arrays.binarySearch(apicals, car) >= 0)
            return true;
        else
            return false;
    }

    public static boolean bilabialConsonant(char car) {
        if (Arrays.binarySearch(bilabials, car) >= 0)
            return true;
        else
            return false;
    }

    public static boolean velarConsonant(char car) {
        if (Arrays.binarySearch(velars, car) >= 0)
            return true;
        else
            return false;
    }

    public static boolean uvularConsonant(char car) {
        if (Arrays.binarySearch(uvulars, car) >= 0)
            return true;
        else
            return false;
    }

    // True if 'car' represents a long-vowel syllabic character.
    public static boolean longVowel(char car) {
        if (Syllabics.Long.unicodeXsyl.get(new Character(car)) != null)
            return true;
        else
            return false;
    }

    // True if 'car' represents a short-vowel syllabic character.
    public static boolean shortVowel(char car) {
        if (Syllabics.Short.unicodeXsyl.get(new Character(car)) != null)
            return true;
        else
            return false;
    }

    // True if 'car' represents the syllabic character for 'a'.
    public static boolean isSylCharacterA(char charac) {
        String c = (String) Syllabics.Short.unicodeXsyl.get(new Character(charac));
        if (c != null && c.equals("a"))
            return true;
        else
            return false;
    }

    // True if 'car' represents the syllabic character for 'i'.
    public static boolean isSylCharacterI(char charac) {
        String c = (String) Syllabics.Short.unicodeXsyl.get(new Character(charac));
        if (c != null && c.equals("i"))
            return true;
        else
            return false;
    }

    // True if 'car' represents the syllabic character for 'u'.
    public static boolean isSylCharacterU(char charac) {
        String c = (String) Syllabics.Short.unicodeXsyl.get(new Character(charac));
        if (c != null && c.equals("u"))
            return true;
        else
            return false;
    }

    // True if 'c1' is a syllabic with the same vowel as the syllabic vowel
    // 'c2',
    // for example, c1==='ra' and c2==='a'.
    public static boolean sylWithSameVowel(char c1, char c2) {
        String sc1 = (String) Syllabics.unicodeXsyl.get(new Character(c1));
        String sc2 = (String) Syllabics.unicodeXsyl.get(new Character(c2));
        if (sc1 != null && sc2 != null && sc1.length() > 1 && sc2.length() == 1
                && sc1.charAt(sc1.length() - 1) == sc2.charAt(0))
            return true;
        else
            return false;
    }

    // Returns the unicode char corresponding to the long-vowel version of the
    // short-vowel
    // unicode 'v'.
    public static char shortToLong(char v) {
        Character vc = new Character(v);
        String syl = (String) Syllabics.Short.unicodeXsyl.get(vc);
        if (syl != null) {
            String longsyl = syl + syl.charAt(syl.length() - 1);
            Character lvc = Syllabics.getCharacter(longsyl);
            if (lvc != null)
                return lvc.charValue();
            else
                return (char) -1;
        } else
            return (char) -1;
    }

    /*
     * Returns an array of unicode char's corresponding to the short-vowel
     * syllable and the vowel of the long-vowel syllable 'v'.
     */
    public static char[] longToDouble(char v) {
        String longsyl = (String) Syllabics.Long.unicodeXsyl.get(new Character(
                v));
        // Either VV or CVV
        if (longsyl.length() == 3) {
            char va[] = new char[2];
            String shortsyl = longsyl.substring(0, 2); // short-vowel character
            String vowel = longsyl.substring(2); // single-vowel character
            if (shortsyl != null && vowel != null) {
                va[0] = ((Character) Syllabics.getCharacter(shortsyl))
                        .charValue();
                va[1] = ((Character) Syllabics.getCharacter(vowel)).charValue();
                return va;
            } else
                return null;
        } else {
            char va[] = new char[1];
            va[0] = v;
            return va;
        }
    }

    
    // TYPE DE LETTRE
    public static int typeOfLetterLat(char letter) {
        char consonants[] = { 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r',
                's', 't', 'v', '&', 'N', 'X', 'H' };
        char vowels[] = { 'a', 'i', 'u' };
        Arrays.sort(consonants);
        Arrays.sort(vowels);
        if (Arrays.binarySearch(consonants, letter) >= 0)
            return C;
        else if (Arrays.binarySearch(vowels, letter) >= 0)
            return V;
        else
            return -1;
    }

    public static boolean isConsonant(char charac) {
        if (typeOfLetterLat(charac) == C)
            return true;
        else
            return false;
    }

    public static boolean isVowel(char charac) {
        if (typeOfLetterLat(charac) == V)
            return true;
        else
            return false;
    }

    public static boolean isInuktitutCharacter(char c) {
        return Syllabics.isInuktitutCharacter(c);
    }


    public static char nasalOfOcclusiveUnvoicedLat(char n) {
        if (n == 'p')
            return 'm';
        else if (n == 't')
            return 'n';
        else if (n == 'k')
            return 'N';
        else if (n == 'q')
            return 'r';
        else
            return (char) -1;
    }


    public static char voicedOfOcclusiveUnvoicedLat(char n) {
        if (n == 'p')
            return 'v';
        else if (n == 't')
            return 'l';
        else if (n == 'k')
            return 'g';
        else if (n == 'q')
            return 'r';
        else
            return (char) -1;
    }
    
    public static String orthographyOfVoiced(String voiced) {
        if (voiced.equals("b"))
            return "p";
        else if (voiced.equals("d"))
            return "t";
        else
            return voiced;
    }


    //	public static void faireHashSyllabes() {
    static {
        Arrays.sort(apicals);
        Arrays.sort(bilabials);
        Arrays.sort(velars);
        Arrays.sort(uvulars);
    }

    /*
     * transcoder et getTranscoder() ne sont plus nécessaires depuis qu'on a
     * recodé les fonctions de transcodage.
     */
//    static TransCoder transcoder;
//    
//    public static TransCoder getTranscoder() {
//        if (transcoder==null)
//            transcoder = new TransCoder(Syllabics.syllabicsToRoman,-1);
//        return transcoder;
//    }

    static String prepareString(String str) {
        String s = str.replaceAll("nng","X");
        s = s.replaceAll("ng","N");
        s = s.replaceAll("qq","Q");
        s = s.replaceAll("rq","Q");
        return s;
    }
    
    static public String transcodeToUnicode(String str, String aipaitaiMode) {
        String s = prepareString(str);
        int aipaitai = aipaitaiMode==null? 0 : aipaitaiMode.equals("aipaitai")? 1 : 0;
        int i=0;
        int l=s.length();
        char c,d,e;
        StringBuffer sb = new StringBuffer();
        while (i < l) {
            c = s.charAt(i);
            switch (c) {
            case 'i':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i': d = '\u1404'; break; // ii
                    default: d = '\u1403'; i--; break;
                    }
                } else {
                    d = '\u1403';
                    i--;
                }
                break;
            case 'u': 
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'u': d = '\u1406'; break; // uu
                    default: d = '\u1405'; i--; break;
                    }
                } else {
                    d = '\u1405';
                    i--;
                }
                break;
            case 'a': // a
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'a': d = '\u140b'; break; // aa
                    case 'i': 
                        switch (aipaitai) {
                        case 1: d = '\u1401'; break; // ai
                        default: d = '\u140A'; i--; break;
                        }
                        break;
                    default: d = '\u140A'; i--; break;
                    }
                } else {
                    d = '\u140A';
                    i--;
                }
                break;
            case 'b':
            case 'p':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1432'; break; // pii
                            default: d = '\u1431'; i--; break; // pi
                            }
                        } else {
                            d = '\u1431';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1434'; break; // puu
                            default: d = '\u1433'; i--; break; // pu
                            }
                        } else {
                            d = '\u1433';
                            i--;
                        }
                        break;
                    case 'a': // pa
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1439'; break; // paa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u142f'; break; // pai
                                default: d = '\u1438'; i--; break; // pa
                                }
                                break;
                            default: d = '\u1438'; i--; break; // pa
                            }
                        } else {
                            d = '\u1438'; // pa
                            i--;
                        }
                        break;
                    default: d = '\u1449'; i--; break; // p
                    }
                } else {
                    i--;
                    d = '\u1449'; // p
                }
                break;
            case 'd':
            case 't':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u144f'; break; // tii
                            default: d = '\u144e'; i--; break; // ti
                            }
                        } else {
                            d = '\u144e';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1451'; break; // tuu
                            default: d = '\u1450'; i--; break; // tu
                            }
                        } else {
                            d = '\u1450';
                            i--;
                        }
                        break;
                    case 'a': // ta
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1456'; break; // taa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u144c'; break; // tai
                                default: d = '\u1455'; i--; break; // ta
                                }
                                break;
                            default: d = '\u1455'; i--; break; // ta
                            }
                        } else {
                            d = '\u1455'; // ta
                            i--;
                        }
                        break;
                    default: d = '\u1466'; i--; break; // t
                    }
                } else {
                    i--;
                    d = '\u1466'; // t
                }
                break;
            case 'k':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u146e'; break; // kii
                            default: d = '\u146d'; i--; break; // ki
                            }
                        } else {
                            d = '\u146d';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1470'; break; // kuu
                            default: d = '\u146f'; i--; break; // ku
                            }
                        } else {
                            d = '\u146f';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1473'; break; // kaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u146b'; break; // kai
                                default: d = '\u1472'; i--; break; // ka
                                }
                                break;
                            default: d = '\u1472'; i--; break; // ka
                            }
                        } else {
                            d = '\u1472'; // ka
                            i--;
                        }
                        break;
                    default: d = '\u1483'; i--; break; // k
                    }
                } else {
                    i--;
                    d = '\u1483'; // k
                }
                break;
            case 'g': 
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u148c'; break; // gii
                            default: d = '\u148b'; i--; break; // gi
                            }
                        } else {
                            d = '\u148b';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u148e'; break; // guu
                            default: d = '\u148d'; i--; break; // gu
                            }
                        } else {
                            d = '\u148d';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1491'; break; // gaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u1489'; break; // gai
                                default: d = '\u1490'; i--; break; // ga
                                }
                                break;
                            default: d = '\u1490'; i--; break; // ga
                            }
                        } else {
                            d = '\u1490'; // ga
                            i--;
                        }
                        break;
                    default: d = '\u14a1'; i--; break; // g
                    }
                } else {
                    i--;
                    d = '\u14a1'; // g
                }
                break;
            case 'm':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u14a6'; break; // mii
                            default: d = '\u14a5'; i--; break; // mi
                            }
                        } else {
                            d = '\u14a5';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u14a8'; break; // muu
                            default: d = '\u14a7'; i--; break; // mu
                            }
                        } else {
                            d = '\u14a7';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u14ab'; break; //maa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u14a3'; break; // mai
                                default: d = '\u14aa'; i--; break; // ma
                                }
                                break;
                            default: d = '\u14aa'; i--; break; // ma
                            }
                        } else {
                            d = '\u14aa'; // ma
                            i--;
                        }
                        break;
                    default: d = '\u14bb'; i--; break; // m
                    }
                } else {
                    i--;
                    d = '\u14bb'; // m
                }
                break;
           case 'n':
               i++;
               if (i < l) {
                   e = s.charAt(i);
                   switch (e) {
                   case 'i':
                       i++;
                       if (i < l) {
                           e = s.charAt(i);
                           switch (e) {
                           case 'i': d = '\u14c3'; break; // nii
                           default: d = '\u14c2'; i--; break; // ni
                           }
                       } else {
                           d = '\u14c2';
                           i--;
                       }
                       break;
                   case 'u':
                       i++;
                       if (i < l) {
                           e = s.charAt(i);
                           switch (e) {
                           case 'u': d = '\u14c5'; break; // nuu
                           default: d = '\u14c4'; i--; break; // nu
                           }
                       } else {
                           d = '\u14c4';
                           i--;
                       }
                       break;
                   case 'a': // 
                       i++;
                       if (i < l) {
                           e = s.charAt(i);
                           switch (e) {
                           case 'a': d = '\u14c8'; break; //naa
                           case 'i': 
                               switch (aipaitai) {
                               case 1: d = '\u14c0'; break; // nai
                               default: d = '\u14c7'; i--; break; // na
                               }
                               break;
                           default: d = '\u14c7'; i--; break; // na
                           }
                       } else {
                           d = '\u14c7'; // na
                           i--;
                       }
                       break;
                   default: d = '\u14d0'; i--; break; // n
                   }
               } else {
                   i--;
                   d = '\u14d0'; // n
               }
               break;
           case 'h':
           case 's':
               i++;
               if (i < l) {
                   e = s.charAt(i);
                   switch (e) {
                   case 'i':
                       i++;
                       if (i < l) {
                           e = s.charAt(i);
                           switch (e) {
                           case 'i': d = '\u14f0'; break; // sii
                           default: d = '\u14ef'; i--; break; // si
                           }
                       } else {
                           d = '\u14ef';
                           i--;
                       }
                       break;
                   case 'u':
                       i++;
                       if (i < l) {
                           e = s.charAt(i);
                           switch (e) {
                           case 'u': d = '\u14f2'; break; // suu
                           default: d = '\u14f1'; i--; break; // su
                           }
                       } else {
                           d = '\u14f1';
                           i--;
                       }
                       break;
                   case 'a': // 
                       i++;
                       if (i < l) {
                           e = s.charAt(i);
                           switch (e) {
                           case 'a': d = '\u14f5'; break; //saa
                           case 'i': 
                               switch (aipaitai) {
                               case 1: d = '\u14ed'; break; // sai
                               default: d = '\u14f4'; i--; break; // sa
                               }
                               break;
                           default: d = '\u14f4'; i--; break; // sa
                           }
                       } else {
                           d = '\u14f4'; // sa
                           i--;
                       }
                       break;
                   default: d = '\u1505'; i--; break; // s
                   }
               } else {
                   i--;
                   d = '\u1505'; // s
               }
               break;
            case 'l':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u14d6'; break; // lii
                            default: d = '\u14d5'; i--; break; // li
                            }
                        } else {
                            d = '\u14d5';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u14d8'; break; // luu
                            default: d = '\u14d7'; i--; break; // lu
                            }
                        } else {
                            d = '\u14d7';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u14db'; break; //laa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u14d3'; break; // lai
                                default: d = '\u14da'; i--; break; // la
                                }
                                break;
                            default: d = '\u14da'; i--; break; // la
                            }
                        } else {
                            d = '\u14da'; // la
                            i--;
                        }
                        break;
                    default: d = '\u14ea'; i--; break; // l
                    }
                } else {
                    i--;
                    d = '\u14ea'; // l
                }
                break;
            case 'j':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1529'; break; // jii
                            default: d = '\u1528'; i--; break; // ji
                            }
                        } else {
                            d = '\u1528';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u152b'; break; // juu
                            default: d = '\u152a'; i--; break; // ju
                            }
                        } else {
                            d = '\u152a';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u152e'; break; //jaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u1526'; break; // jai
                                default: d = '\u152d'; i--; break; // ja
                                }
                                break;
                            default: d = '\u152d'; i--; break; // ja
                            }
                        } else {
                            d = '\u152d'; // ja
                            i--;
                        }
                        break;
                    default: d = '\u153e'; i--; break; // j
                    }
                } else {
                    i--;
                    d = '\u153e'; // j
                }
                break;
            case 'v':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1556'; break; // vii
                            default: d = '\u1555'; i--; break; // vi
                            }
                        } else {
                            d = '\u1555';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1558'; break; // vuu
                            default: d = '\u1557'; i--; break; // vu
                            }
                        } else {
                            d = '\u1557';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u155a'; break; //vaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u1553'; break; // vai
                                default: d = '\u1559'; i--; break; // va
                                }
                                break;
                            default: d = '\u1559'; i--; break; // va
                            }
                        } else {
                            d = '\u1559'; // va
                            i--;
                        }
                        break;
                    default: d = '\u155d'; i--; break; // v
                    }
                } else {
                    i--;
                    d = '\u155d'; // v
                }
                break;
            case 'r':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1547'; break; // rii
                            default: d = '\u1546'; i--; break; // ri
                            }
                        } else {
                            d = '\u1546';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1549'; break; // ruu
                            default: d = '\u1548'; i--; break; // ru
                            }
                        } else {
                            d = '\u1548';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u154c'; break; //raa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u1542'; break; // rai
                                default: d = '\u154b'; i--; break; // ra
                                }
                                break;
                            default: d = '\u154b'; i--; break; // ra
                            }
                        } else {
                            d = '\u154b'; // ra
                            i--;
                        }
                        break;
                    default: d = '\u1550'; i--; break; // r
                    }
                } else {
                    i--;
                    d = '\u1550'; // r
                }
                break;
            case 'q':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1580'; break; // qii
                            default: d = '\u157f'; i--; break; // qi
                            }
                        } else {
                            d = '\u157f';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1582'; break; // quu
                            default: d = '\u1581'; i--; break; // qu
                            }
                        } else {
                            d = '\u1581';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1584'; break; //qaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u166f'; break; // qai
                                default: d = '\u1583'; i--; break; // qa
                                }
                                break;
                            default: d = '\u1583'; i--; break; // qa
                            }
                        } else {
                            d = '\u1583'; // qa
                            i--;
                        }
                        break;
                    default: d = '\u1585'; i--; break; // q
                    }
                } else {
                    i--;
                    d = '\u1585'; // q
                }
                break;
            case '&':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u15a1'; break; // &ii
                            default: d = '\u15a0'; i--; break; // &i
                            }
                        } else {
                            d = '\u15a0';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u15a3'; break; // &uu
                            default: d = '\u15a2'; i--; break; // &u
                            }
                        } else {
                            d = '\u15a2';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u15a5'; break; //&aa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: sb.append('\u15a4'); d = '\u1403'; break; // &ai
                                default: d = '\u15a4'; i--; break; // &a
                                }
                                break;
                            default: d = '\u15a4'; i--; break; // &a
                            }
                        } else {
                            d = '\u15a4'; // &a
                            i--;
                        }
                        break;
                    default: d = '\u15a6'; i--; break; // &
                    }
                } else {
                    i--;
                    d = '\u15a6'; // &
                }
                break;
            case 'N':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1590'; break; // ngii
                            default: d = '\u158f'; i--; break; // ngi
                            }
                        } else {
                            d = '\u158f';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1592'; break; // nguu
                            default: d = '\u1591'; i--; break; // ngu
                            }
                        } else {
                            d = '\u1591';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1594'; break; //ngaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u1670'; break; // ngai
                                default: d = '\u1593'; i--; break; // nga
                                }
                                break;
                            default: d = '\u1593'; i--; break; // nga
                            }
                        } else {
                            d = '\u1593'; // nga
                            i--;
                        }
                        break;
                    default: d = '\u1595'; i--; break; // ng
                    }
                } else {
                    i--;
                    d = '\u1595'; // ng
                }
                break;
            case 'X':
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d = '\u1672'; break; // nngii
                            default: d = '\u1671'; i--; break; // nngi
                            }
                        } else {
                            d = '\u1671';
                            i--;
                        }
                        break;
                    case 'u':
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d = '\u1674'; break; // nnguu
                            default: d = '\u1673'; i--; break; // nngu
                            }
                        } else {
                            d = '\u1673';
                            i--;
                        }
                        break;
                    case 'a': // 
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d = '\u1676'; break; //nngaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: sb.append('\u1596'); d = '\u1489'; break; // nngai
                                default: d = '\u1675'; i--; break; // nnga
                                }
                                break;
                            default: d = '\u1675'; i--; break; // nnga
                            }
                        } else {
                            d = '\u1675'; // nnga
                            i--;
                        }
                        break;
                    default: d = '\u1596'; i--; break; // nng
                    }
                } else {
                    i--;
                    d = '\u1596'; // nng
                }
                break;
//            case 'h': d = '\u157C'; break; // Nunavut H
//            case 'b': d = '\u15AF'; break; // Aivilik B
                // Nunavik H
            case 'Q': //qq
                sb.append('\u1585');
                i++;
                if (i < l) {
                    e = s.charAt(i);
                    switch (e) {
                    case 'i':
                        d = '\u146d';
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'i': d++; break; // qqii = q+kii
                            default: i--; break; // qqi = q+ki
                            }
                        } else {
                            i--;
                        }
                        break;
                    case 'u':
                        d = '\u146f';
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'u': d++; break; // qquu = q+kuu
                            default: i--; break; // qqu = q+ku
                            }
                        } else {
                            i--;
                        }
                        break;
                    case 'a':
                        d = '\u1472';
                        i++;
                        if (i < l) {
                            e = s.charAt(i);
                            switch (e) {
                            case 'a': d++; break; // qqaa = q+kaa
                            case 'i': 
                                switch (aipaitai) {
                                case 1: d = '\u146b'; break; // qqai = q+kai
                                default: i--; break; // qqa = q+ka
                                }
                                break;
                            default: i--; break; // qqa = q+ka
                            }
                        } else {
                            // qqa = q+ka
                            i--;
                        }
                        break;
                    default: // shouldn't happen 
                        d = '\u1585'; i--; break; // qq
                    }
                } else { // shouldn't happen
                    i--;
                    d = '\u1585';
                    sb.deleteCharAt(sb.length()-1);
                }
                break;
            case 'H': d = '\u157c'; break;
            default: d = c; break;
            }
            i++;
            sb.append(d);
        }
        return sb.toString();
    }
}