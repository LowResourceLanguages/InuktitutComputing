/*
 * Conseil national de recherche Canada 2003
 * 
 * Créé le 10-May-2004 par Benoit Farley
 *  
 */
package utilities;

import fonts.Font;
import script.TransCoder;
import junit.framework.TestCase;

public class InuktitutTest extends TestCase {

    /**
     * Constructor for InuktitutTest.
     * 
     * @param arg0
     */
    public InuktitutTest(String arg0) {
        super(arg0);
    }

    public void testEstLegacy() {
        if (!Font.isLegacy("New Font,nunacom")) {
            System.out.println("pas une police inuktitut");
            fail();
        }
    }

    public void testToRoman() {
        String words[][] = {
                {"","nautsiqtuqtiit"},
                {"\u1403\u14AA\u1548\u1403\u1466\u1450\u1585", "imaruittuq"},
                {"\u1403\u1585\u146F\u1550\u14D5\u1455\u1585", "iqqurlitaq"},
                {"\u1403\u1585\u146E\u1455\u1585", "iqqiitaq"}
        };
        for (int i=0; i<words.length; i++) {
            String res = TransCoder.unicodeToRoman(words[i][0]);
            assertEquals(i+": "+res+" != "+words[i][1],words[i][1],res);
        }
    }

    public void testToUnicode_roman() {
        String words[][] = {
                {"\u1403\u14AA\u1548\u1403\u1466\u1450\u1585", "imaruittuq"},
                {"\u1403\u1585\u146F\u1550\u14D5\u1455\u1585", "iqqurlitaq"},
                {"\u1403\u1585\u146E\u1455\u1585", "iqqiitaq"},
                {"\u1403\u1585\u146E_\u1455\u1585", "iqqii_taq"},
                {"\u1403\u1585\u146E*\u1455\u1585", "iqqii*taq"},
                {"\u1403\u1585\u146E?\u1455\u1585", "iqqii?taq"},
        };
        for (int i=0; i<words.length; i++) {
            String res = TransCoder.romanToUnicode(words[i][1]);
            assertEquals(i+": "+res+" != "+words[i][0],words[i][0],res);
        }
    }
    
    public void testToUnicode_legacy() {
        String words[][] = {
                {"\u1403\u14AA\u1548\u1403\u1466\u1450\u1585", "wmDw5g6", "naamajut"},
                {"\u1403\u158F\u1585\u1438\u1466", "w1Q6X5", "naamajut"}, // i-ng-gi-q-pa-t
        };
        for (int i=0; i<words.length; i++) {
            String res = TransCoder.legacyToUnicode(words[i][1],words[i][2]);
            assertEquals(i+": "+res+" != "+words[i][0],words[i][0],res);
        }
    }
    

}