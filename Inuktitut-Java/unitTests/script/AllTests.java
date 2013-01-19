package unitTests.script;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for script");
		//$JUnit-BEGIN$
		suite.addTestSuite(OrthographyTest.class);
		suite.addTestSuite(RomanTest.class);
		suite.addTestSuite(SyllabicsTest.class);
		//$JUnit-END$
		return suite;
	}

}
