/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 24-Aug-2004
 * par Benoit Farley
 * 
 */
package unitTests.data;

import data.Base;
import data.Morpheme;
import data.Affix;
import data.LinguisticDataAbstract;

import junit.framework.TestCase;

public class LinguisticDataAbstractTest extends TestCase {

	public LinguisticDataAbstractTest(String sourceOfData) {
		LinguisticDataAbstract.init(sourceOfData);
	}

	/*
	 * Test for Affix getAffix(SurfaceFormOfAffix)
	 */


	/*
	 * Test pour Vector getBase(String term) et getBase(Morpheme.Id idObj)
	 */
	public void testGetBase() {
	    String terms[][] = {
	            {"aggak","(1) glove (2) saw-handle of hand-saw","aggak/2n"},
	            {"aamai","disclaimer: 'I don't know' ; 'I don't want to say' (N. Baffin, WCHB)","aamai/1e"},
	            {"tamajja","right here beside me","tamajja/ad-ml"}
	    };
	    for (int i = 0; i < terms.length; i++) {
            for (int j = 0; j < terms.length; j++) {
                Base base = (Base) LinguisticDataAbstract.getBase(terms[j][2]);
                assertTrue("'"+terms[j][2]+"' pas trouvé.",base != null && base.englishMeaning.equals(terms[j][1]));
            }
        }
    }


	/*
	 * Test pour Vector getAffix(Morpheme.Id idObj)
	 */
	public void testGetAffix() {
	    String terms[][] = {
	            {"juaq","large; big","juaq/1nn"},
	    };
	    for (int i = 0; i < terms.length; i++) {
            for (int j = 0; j < terms.length; j++) {
                Affix aff = (Affix) LinguisticDataAbstract.getAffix(terms[j][2]);
                assertTrue("'"+terms[j][2]+"' pas trouvé.",aff != null && aff.englishMeaning.equals(terms[j][1]));
            }
        }
	}
	
	
}

