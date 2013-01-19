package unitTests.phonology.research;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for phonology.research");
		//$JUnit-BEGIN$
		suite.addTestSuite(DialectalRulesTest.class);
		suite.addTestSuite(PhonologicalChangeTest.class);
		//$JUnit-END$
		return suite;
	}

}
