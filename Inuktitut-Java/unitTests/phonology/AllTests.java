package unitTests.phonology;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for phonology");
		//$JUnit-BEGIN$
		suite.addTestSuite(DialectTest.class);
		//$JUnit-END$
		return suite;
	}

}
