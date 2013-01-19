package unitTests.morph;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for morph");
		//$JUnit-BEGIN$
		suite.addTestSuite(MorphInukTest_csv_data.class);
		//$JUnit-END$
		return suite;
	}

}
