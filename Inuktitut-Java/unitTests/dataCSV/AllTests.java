package unitTests.dataCSV;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for dataCSV");
		//$JUnit-BEGIN$
		suite.addTestSuite(LinguisticDataCSVTest.class);
		//$JUnit-END$
		return suite;
	}

}
