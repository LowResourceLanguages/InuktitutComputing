package unitTests.html;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for html");
		//$JUnit-BEGIN$
		suite.addTestSuite(HtmlDocuTest.class);
		suite.addTestSuite(HtmlEntitiesTest.class);
		//$JUnit-END$
		return suite;
	}

}
