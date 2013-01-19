package unitTests.fonts;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for fonts");
		//$JUnit-BEGIN$
		suite.addTestSuite(FontAinunavikTest.class);
		suite.addTestSuite(FontAipainunaTest.class);
		suite.addTestSuite(FontAipainunavikTest.class);
		suite.addTestSuite(FontAujaq2Test.class);
		suite.addTestSuite(FontAujaqsylTest.class);
		suite.addTestSuite(FontNaamajutTest.class);
		suite.addTestSuite(FontNunacomTest.class);
		suite.addTestSuite(FontNunacomTest.class);
		suite.addTestSuite(FontOldsylTest.class);
		suite.addTestSuite(FontProsylTest.class);
		suite.addTestSuite(FontTunngavikTest.class);
		suite.addTestSuite(FontTest.class);
		//$JUnit-END$
		return suite;
	}

}
