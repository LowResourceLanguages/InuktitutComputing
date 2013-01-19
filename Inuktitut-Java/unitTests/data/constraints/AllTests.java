package unitTests.data.constraints;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for data.constraints");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConditionTest.class);
		suite.addTestSuite(ImacondTest.class);
		//$JUnit-END$
		return suite;
	}

}
