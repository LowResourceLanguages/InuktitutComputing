/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Nov 2, 2006
 * par / by Benoit Farley
 * 
 */
package unitTests.script;

import script.Syllabics;
import junit.framework.TestCase;

public class SyllabicsTest extends TestCase {

    /*
     * Test method for 'script.Syllabics.isInuktitutCharacter(char)'
     */
    public void testIsInuktitutCharacterChar() {

    }

    /*
     * Test method for 'script.Syllabics.isInuktitutCharacter(Character)'
     */
    public void testIsInuktitutCharacterCharacter() {

    }

    /*
     * Test method for 'script.Syllabics.getSyl(Character)'
     */
    public void testGetSylCharacter() {

    }

    /*
     * Test method for 'script.Syllabics.getSyl(char)'
     */
    public void testGetSylChar() {

    }

    /*
     * Test method for 'script.Syllabics.getCharacter(String)'
     */
    public void testGetCharacter() {

    }

    /*
     * Test method for 'script.Syllabics.allInuktitut(String)'
     */
    public void testAllInuktitut() {

    }

    /*
     * Test method for 'script.Syllabics.containsInuktitut(String)'
     */
    public void testContainsInuktitut() {

    }

    /*
     * Test method for 'script.Syllabics.getTranscoder()'
     */
    public void testGetTranscoder() {

    }

    /*
     * Test method for 'script.Syllabics.getTranscoder(String)'
     */
    public void testGetTranscoderString() {

    }

    /*
     * Test method for 'script.Syllabics.simplify(String)'
     */
    public void testSimplify() {

    }

    /*
     * Test method for 'script.Syllabics.transcodeToRoman(String)'
     */
    public void testTranscodeToUnicodeStringString() {
        String [][] words = {
                // n+ngi: pinasualaursimanngituq
                {"pinasualaursimanngituq","\u1431\u14c7\u14f1\u140a\u14da\u1405\u1550\u14ef\u14aa\u1671\u1450\u1585"},
                // n+ngii: atanngiijjutauniaqpuq
                {"atanngiijjutauniaqpuq","\u140a\u1455\u1672\u153e\u152a\u1455\u1405\u14c2\u140a\u1585\u1433\u1585"},
                // n+ngu: nunannguatigut
                {"nunannguatigut","\u14c4\u14c7\u1673\u140a\u144e\u148d\u1466"},
                // n+nguu: kinnguumajanginnik
                {"kinnguumajanginnik","\u146d\u1674\u14aa\u152d\u158f\u14d0\u14c2\u1483"},
                // n+nga: tunngasugitsi
                {"tunngasugitsi","\u1450\u1675\u14f1\u148b\u1466\u14ef"},
                // n+ngaa: uqautivinngaa
                {"uqautivinngaa","\u1405\u1583\u1405\u144e\u1555\u1676"},
                // ngi: kangiq&iniup
                {"kangiq&iniup","\u1472\u158f\u1585\u15a0\u14c2\u1405\u1449"},
                // ngii: ajjingiinngittuniinniaqtutik
                {"ajjingiinngittuniinniaqtutik","\u140a\u153e\u1528\u1590\u1671\u1466\u1450\u14c3\u14d0\u14c2\u140a\u1585\u1450\u144e\u1483"},
                // ngu: angutiillu
                {"angutiillu","\u140a\u1591\u144f\u14ea\u14d7"},
                // nguu: arraanguulaaqtumi
                {"arraanguulaaqtumi","\u140a\u1550\u154c\u1592\u14db\u1585\u1450\u14a5"},
                // nga: gavamangannik
                {"gavamangannik","\u1490\u1559\u14aa\u1593\u14d0\u14c2\u1483"},
                // ngaa: maligaliurvingmiingaarnikuuvugut
                {"maligaliurvingmiingaarnikuuvugut","\u14aa\u14d5\u1490\u14d5\u1405\u1550\u1555\u1595\u14a6\u1594\u1550\u14c2\u1470\u1557\u148d\u1466"},
                
                // rqi: utaqqiniq
                {"utaqqiniq","\u1405\u1455\u1585\u146d\u14c2\u1585"},
                // rqii: utaqqiinnaqtuq
                {"utaqqiinnaqtuq","\u1405\u1455\u1585\u146e\u14d0\u14c7\u1585\u1450\u1585"},
                // qri: nirriku
                {"nirriku","\u14c2\u1550\u1546\u146f"},
                // qrii: tarriijaamit
                {"tarriijaamit","\u1455\u1550\u1547\u152e\u14a5\u1466"},
                // rqu: qimiqqujiujut
                {"qimiqqujiujut","\u157f\u14a5\u1585\u146f\u1528\u1405\u152a\u1466"},
                // rquu: ilitariqquupuq
                {"ilitariqquupuq","\u1403\u14d5\u1455\u1546\u1585\u1470\u1433\u1585"},
                // qru: qimirruagait
                {"qimirruagait","\u157f\u14a5\u1550\u1548\u140a\u1490\u1403\u1466"},
                // qruu: marruuk
                {"marruuk","\u14aa\u1550\u1549\u1483"},
                // rqa: iniuqqavik
                {"iniuqqavik","\u1403\u14c2\u1405\u1585\u1472\u1555\u1483"},
                // rqaa: itiqqaaqpunga
                {"itiqqaaqpunga","\u1403\u144e\u1585\u1473\u1585\u1433\u1593"},
                // qra: angirrapaa
                {"angirrapaa","\u140a\u158f\u1550\u154b\u1439"},
                // qraa: arraagu
                {"arraagu","\u140a\u1550\u154c\u148d"},

                // ru: ippigitsiaruk
                {"ippigitsiaruk","\u1403\u1449\u1431\u148b\u1466\u14ef\u140a\u1548\u1483"},
                // &u: taku&ugit
                {"taku&ugit","\u1455\u146f\u15a2\u148b\u1466"},
                // &a: ikak&ak
                {"ikak&ak","\u1403\u1472\u1483\u15a4\u1483"},
                // luu, nii, v: uvvaluunniit
                {"uvvaluunniit","\u1405\u155d\u1559\u14d8\u14d0\u14c3\u1466"},
                // ii, laa: iilaak
                {"iilaak","\u1404\u14db\u1483"},
                // H, aa, m: Haamlaujunit
                {"Haamlaujunit","\u157c\u140b\u14bb\u14da\u1405\u152a\u14c2\u1466"},
                // vaa, s, juu: angillivaalliarjussimajuumuppuq
                {"angillivaalliarjussimajuumuppuq",
                    "\u140a\u158f\u14ea\u14d5\u155a\u14ea\u14d5\u140a\u1550\u152a\u1505\u14ef\u14aa\u152b\u14a7\u1449\u1433\u1585"},
                // taa, &: taakkuangu&&aqpuq
                {"taakkuangu&&aqpuq","\u1456\u1483\u146f\u140a\u1591\u15a6\u15a4\u1585\u1433\u1585"},
                // uu, &aa, suu: uumajur&aasuut
                {"uumajur&aasuut","\u1406\u14aa\u152a\u1550\u15a5\u14f2\u1466"},
                // &uu, raa, ng: i&uuraarjungnik
                {"i&uuraarjungnik","\u1403\u15a3\u154c\u1550\u152a\u1595\u14c2\u1483"},
                // &ii: sivu&iinnaq
                {"sivu&iinnaq","\u14ef\u1557\u15a1\u14d0\u14c7\u1585"},
                // ruu, tuu: marruuliqtuunni
                {"marruuliqtuunni","\u14aa\u1550\u1549\u14d5\u1585\u1451\u14d0\u14c2"},
                // luu, naa, guu: iluunnaaguut
                {"iluunnaaguut","\u1403\u14d8\u14d0\u14c8\u148e\u1466"},
                // vuu, maa: nunavuulimaaq
                {"nunavuulimaaq","\u14c4\u14c7\u1558\u14d5\u14ab\u1585"},
                // puu: sapuulutait
                {"sapuulutait","\u14f4\u1434\u14d7\u1455\u1403\u1466"},
                // muu: tamatumuuna
                {"tamatumuuna","\u1455\u14aa\u1450\u14a8\u14c7"},
                // aa, gaa: aaggaa
                {"aaggaa","\u140b\u14a1\u1491"},
                // aa, jii, gii: aajiiqatigiinniq
                {"aajiiqatigiinniq","\u140b\u1529\u1583\u144e\u148c\u14d0\u14c2\u1585"},
                // jaa, vii: siarrijaarviit
                {"siarrijaarviit","\u14ef\u140a\u1550\u1546\u152e\u1550\u1556\u1466"},
                // kaa, pii: unikkaaliulauqpiit
                {"unikkaaliulauqpiit","\u1405\u14c2\u1483\u1473\u14d5\u1405\u14da\u1405\u1585\u1432\u1466"},
                // lii, sii: iliilauqpisii
                {"iliilauqpisii","\u1403\u14d6\u14da\u1405\u1585\u1431\u14f0"},
                // kii, rii: kiinaujallariit
                {"kiinaujallariit","\u146e\u14c7\u1405\u152d\u14ea\u14da\u1547\u1466"},
        };

        String wordsAI[][] = {
                // ai: aippiqtaulluni
                {"aippiqtaulluni",
                    "\u140a\u1403\u1449\u1431\u1585\u1455\u1405\u14ea\u14d7\u14c2",
                    "\u1401\u1449\u1431\u1585\u1455\u1405\u14ea\u14d7\u14c2"
                },
                // pai: paippaamullu
                {"paippaamullu",
                    "\u1438\u1403\u1449\u1439\u14a7\u14ea\u14d7",
                    "\u142f\u1449\u1439\u14a7\u14ea\u14d7"
                },
                // tai: taikkua
                {"taikkua",
                    "\u1455\u1403\u1483\u146f\u140a",
                    "\u144c\u1483\u146f\u140a"
                },
                // kai: kaivallalluaqsunga
                {"kaivallalluaqsunga",
                    "\u1472\u1403\u1559\u14ea\u14da\u14ea\u14d7\u140a\u1585\u14f1\u1593",
                    "\u146b\u1559\u14ea\u14da\u14ea\u14d7\u140a\u1585\u14f1\u1593"
                 },
                // gai: turaagait
                {"turaagait",
                     "\u1450\u154c\u1490\u1403\u1466",
                     "\u1450\u154c\u1489\u1466"
                },
                // mai: tamaita
                {"tamaita",
                    "\u1455\u14aa\u1403\u1455",
                    "\u1455\u14a3\u1455"
                },
                // nai: nalunairlugu
                {"nalunairlugu",
                    "\u14c7\u14d7\u14c7\u1403\u1550\u14d7\u148d",
                    "\u14c7\u14d7\u14c0\u1550\u14d7\u148d"
                },
                // sai: tungaliritsainartilugit
                {"tungaliritsainartilugit",
                    "\u1450\u1593\u14d5\u1546\u1466\u14f4\u1403\u14c7\u1550\u144e\u14d7\u148b\u1466",
                    "\u1450\u1593\u14d5\u1546\u1466\u14ed\u14c7\u1550\u144e\u14d7\u148b\u1466"
                 },
                // lai: allait
                {"allait",
                    "\u140a\u14ea\u14da\u1403\u1466",
                    "\u140a\u14ea\u14d3\u1466",
                 },
                // jai: nunalituqatsajait
                {"nunalituqatsajait",
                     "\u14c4\u14c7\u14d5\u1450\u1583\u1466\u14f4\u152d\u1403\u1466",
                     "\u14c4\u14c7\u14d5\u1450\u1583\u1466\u14f4\u1526\u1466"
                },
                // vai: sanirvaisimavugut
                {"sanirvaisimavugut",
                    "\u14f4\u14c2\u1550\u1559\u1403\u14ef\u14aa\u1557\u148d\u1466",
                    "\u14f4\u14c2\u1550\u1553\u14ef\u14aa\u1557\u148d\u1466"
                },
                // rai: nutarait
                {"nutarait",
                    "\u14c4\u1455\u154b\u1403\u1466",
                    "\u14c4\u1455\u1542\u1466"
                 },
                // qai: qaisimalaurtutuit
                {"qaisimalaurtutuit",
                     "\u1583\u1403\u14ef\u14aa\u14da\u1405\u1550\u1450\u1450\u1403\u1466",
                     "\u166f\u14ef\u14aa\u14da\u1405\u1550\u1450\u1450\u1403\u1466"
                },
                // ngai: tamakkuningaiglunit
                {"tamakkuningaiglunit",
                    "\u1455\u14aa\u1483\u146f\u14c2\u1593\u1403\u14a1\u14d7\u14c2\u1466",
                    "\u1455\u14aa\u1483\u146f\u14c2\u1670\u14a1\u14d7\u14c2\u1466"
                },
                // n+ngai: saanngainnaraktigu
                {"saanngainnaraktigu",
                    "\u14f5\u1675\u1403\u14d0\u14c7\u154b\u1483\u144e\u148d",
                    "\u14f5\u1596\u1489\u14d0\u14c7\u154b\u1483\u144e\u148d"
                },
                // rqai: tuqsunuuqqaivuq
                {"tuqsunuuqqaivuq",
                    "\u1450\u1585\u14f1\u14c5\u1585\u1472\u1403\u1557\u1585",
                    "\u1450\u1585\u14f1\u14c5\u1585\u146b\u1557\u1585"
                },
                // qrai: ikarrait
                {"ikarrait",
                    "\u1403\u1472\u1550\u154b\u1403\u1466",
                    "\u1403\u1472\u1550\u1542\u1466"
                },
    };
        String res;
        for (int i=0; i<words.length; i++) {
            String str = words[i][1];
            String targ = words[i][0];
            res = Syllabics.transcodeToRoman(str);
            assertEquals("1. "+res+" au lieu de "+targ,targ,res);
        }
        for (int i=0; i<wordsAI.length; i++) {
            String str = wordsAI[i][1];
            String targ = wordsAI[i][0];
            res = Syllabics.transcodeToRoman(str);
            assertEquals("2a. "+res+" au lieu de "+targ,targ,res);
            str = wordsAI[i][2];
            res = Syllabics.transcodeToRoman(str);
            assertEquals("2b. "+res+" au lieu de "+targ,targ,res);
        }

    }

    /*
     * Test method for 'script.Syllabics.unicodeAIPAITAItoICI(String)'
     */
    public void testUnicodeAIPAITAItoICI() {
        String [][] words = {
                {"\u140b\u14a3","\u140b\u14aa\u1403"}, // aamai
                {"\u1438\u1596\u1489\u1585\u1450\u1585","\u1438\u1675\u1403\u1585\u1450\u1585"} // panngaiqtuq
        };
        for (int i=0; i<words.length; i++) {
            String str = words[i][0];
            String targ = words[i][1];
            String res = Syllabics.unicodeAIPAITAItoICI(str);
            assertEquals("",targ,res);
        }
    }

    /*
     * Test method for 'script.Syllabics.unicodeICItoAIPAITAI(String)'
     */
    public void testUnicodeICItoAIPAITAI() {
        String [][] words = {
                {"\u140b\u14a3","\u140b\u14aa\u1403"}, // aamai     
                {"\u1438\u1596\u1489\u1585\u1450\u1585","\u1438\u1675\u1403\u1585\u1450\u1585"} // panngaiqtuq
        };
        for (int i=0; i<words.length; i++) {
            String str = words[i][1];
            String targ = words[i][0];
            String res = Syllabics.unicodeICItoAIPAITAI(str);
            assertEquals("",targ,res);
        }
    }

}
