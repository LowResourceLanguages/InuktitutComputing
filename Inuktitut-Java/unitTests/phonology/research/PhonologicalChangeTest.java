/*
 * Conseil national de recherche Canada 2005/
 * National Research Council Canada 2005
 * 
 * Cr�� le / Created on Apr 20, 2005
 * par / by Benoit Farley
 * 
 */
package unitTests.phonology.research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import phonology.research.PhonologicalChange;

import junit.framework.TestCase;

public class PhonologicalChangeTest extends TestCase {

    public void testAlternatives() {
        String rets[] = null;
        String targs[] = null;
        String set[][] = null;
        String dialect = null;
        
        Object [][] sets = new Object[][]{
                {"northbaffin",new String[][]{
                        {"sitamat","tisamat","tisaman","sitaman","sisamat","sisaman","sitamat"}, // 10, 11
                }},
                {"aivilik",new String[][]{
                        {"natsiq","nattiq","natsiq"}, // 2
                        {"tikit&uni","tiki&&uni"}, // 3
                        {"tikitpat","tikippat"}, // 1
                        {"tatqiq","taqqiq"}, // 1
                        {"ilvit","ivvit"}, // 1
                        {"ajgak","aggak"}, // 1
                        {"iqilraq","iqirraq"}, // 1
                        {"upinraaq","upirngaaq"}, // 4
                        {"tikinmat","tikimmat"}, // 1
                        {"kivgaq","kiggaq"}, // 8
                }},
                {"northbaffin",new String[][]{
                        {"tusarapta","tusaratta"}, // 6
                        {"takugapku","takugakku"}, // 6
                        {"apqut","aqqut","aqqun"}, // 6, 11
                        {"qablunaq","qallunaq","qallunar"}, // 6, 11
                        {"ibjuq","ijjuq","ijjur"}, // 6, 11
                        {"takugamnuk","takugannuk","takugannung"}, // 6, 11 
                        {"imngiqtuq","inngiqtuq","inngiqtur"}, // 6, 11
                        {"paamruqtuq","paarnguqtuq","paarnguqtur"}, // 5, 11
                        {"taipsumani","taissumani"}, // 7
                        {"sitamat","tisamat","tisaman","sitaman","sisamat","sisaman","sitamat"}, // 10, 11
                        {"itigak","isigak", "isigang", "itigang", "itigak"}, // 9, 11
                        // de Aivilik
                        {"tikit&uni","tiki&&uni"}, // 3
                        {"tikitpat","tikippan","tikippat"}, // 1, 11
                        {"tatqiq","taqqir","taqqiq"}, // 1, 11
                        {"ilvit","ivvin","ivvit"}, // 1, 11
                        {"ajgak","aggang","aggak"}, // 1, 11
                        {"iqilraq","iqirrar","iqirraq"}, // 1, 11
                        {"upinraaq","upirngaar","upirngaaq"}, // 4, 11
                        {"tikinmat","tikimman","tikimmat"}, // 1, 11
                        {"kivgaq","kiggar","kiggaq"}, // 8, 11
                        {"natsiq","nattir","nattiq"} // 2, 11
               }},
                {"southeastbaffin",new String[][]{
                        {"iksivautaq","issivautar","issivautaq"}, // 15, 11
                        {"tikit&uni","tikittuni"}, // 12
                        {"tiki&&uni","tikittuni"}, // 12
                        {"ak&unak","aktunang","aktunak"}, // 12, 11
                        {"kangiq&uk","kangiqtung","kangiqtuk"}, // 12, 11
                        {"taipsumani","taitsumani"}, // 7
                        {"sitamat","sitaman","sisamat","sisaman","sitamat"}, // 9, 11
                        {"itigak","isigang","itigang", "isigak","itigak"}, // 9, 11
                        {"tasiq","sasir","sasiq"}, // 10, 11
                        {"i&uaqtuq", "isuaqtuq", "isuaqtur", "iluaqtuq", "iluaqtur"},
                        // North Baffin
                        {"tusarapta","susaratta"},
                        {"takugapku","takugakku"},
                        {"apqut","aqqun","aqqut"},
                        {"qablunaq","qallunar","qallunaq"},
                        {"ibjuq","ijjur","ijjuq"},
                        {"takugamnuk","takugannung","takugannuk"},
                        {"imngiqtuq","inngiqtur","inngiqtuq"},
                        {"paamruqtuq","paarnguqtur","paarnguqtuq"},
                         // Aivilik
                        {"tikitpat","tikippan","tikippat"},
                        {"tatqiq","taqqir","taqqiq"},
                        {"ilvit","ivvin","ivvit"},
                        {"ajgak","aggang","aggak"},
                        {"iqilraq","iqirrar","iqirraq"},
                        {"upinraaq","upirngaar","upirngaaq"},
                        {"tikinmat","tikimman","tikimmat"},
                        {"kivgaq","kiggar","kiggaq"},
                        {"natsiq","natsir","natsiq"}
                }}
        };
        for (int k = 0; k < sets.length; k++) {
            dialect = (String)sets[k][0];
            set = (String[][])sets[k][1];
            for (int i = 0; i < set.length; i++) {
                System.out.println(set[i][0]);
                rets = PhonologicalChange.alternatives(set[i][0], dialect);
                List lst = Arrays.asList(set[i]);
                List sblst = lst.subList(1, set[i].length);
                targs = (String[]) sblst.toArray(new String[] {});
                for (int j = 0; j < rets.length; j++)
                    System.out.println("   [" + dialect + "] "
                            + (String) rets[j]);
                assertTrue(falseExplanation(set[i][0],targs, rets, dialect), arraysSameContents(targs, rets));
            }
        }
        
        PhonologicalChange.alternativesAll("issivautaq");
     }
    
    private String falseExplanation(String word, String[] expectations, String[] results, String dialect) {
        StringBuffer sb = new StringBuffer();
        sb.append(word+"["+dialect+"]\n");
        sb.append("  expected:\n");
        for (int i=0; i<expectations.length; i++)
            sb.append("    "+expectations[i]+"\n");
        sb.append("  gotten:\n");
        for (int i=0; i<results.length; i++)
            sb.append("    "+results[i]+"\n");
        return sb.toString();
    }
    
    private boolean arraysSameContents(Object [] a1, Object [] a2) {
        List a1List = Arrays.asList(a1);
        List a2List = Arrays.asList(a2);
        if (a1.length == a2.length)
            for (int i=0; i<a1.length; i++)
                if (!a2List.contains(a1List.get(i)))
                        return false;
                else;
        else
            return false;
        return true;
    }

}
