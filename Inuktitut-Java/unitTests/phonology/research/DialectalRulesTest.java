/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 23-Dec-2003
 * par Benoit Farley
 * 
 */
package unitTests.phonology.research;

import java.util.Arrays;

import phonology.research.DialectalRules;

import utilities1.Util;

import junit.framework.TestCase;

public class DialectalRulesTest extends TestCase {

	/**
	 * Constructor for DialectalRulesTest.
	 * @param arg0
	 */
	public DialectalRulesTest(String arg0) {
		super(arg0);
	}

	public void testT2s_strongI() {
		char [] cs = DialectalRules.t2s_strongI("itigak".toCharArray());
		if (!new String(cs).equals("isigak")) {
			System.err.println("t2s_strongI(itigak) != isigak");
			fail();
		}
	}
	
	public void testSpellingNorthBaffin() {
		tstSpellNB("iglu",new String[]{"iglu"});
		tstSpellNB("taipsumani",new String[]{"taissumani"});
		tstSpellNB("tusarapta",new String[]{"tusaratta"});
		tstSpellNB("takugapku",new String[]{"takugakku"});
		tstSpellNB("apqut",new String[]{"aqqut"});
		tstSpellNB("tablu",new String[]{"tallu"});
		tstSpellNB("itigak",new String[]{"itigak","isigak"});
	}
	
	private void tstSpellNB(String m1o, String [] m1c) {
		String [] m2;
		m2 = DialectalRules.spellingNorthBaffin(m1o);
		if (!Arrays.equals(m2,m1c)) {
			System.err.println(m1o+" est devenu "+Util.array2string(m2)+" au lieu de "+Util.array2string(m1c));
			fail();
		}
	}

}
