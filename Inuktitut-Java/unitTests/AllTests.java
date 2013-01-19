package unitTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Inuktitut - Tests for all packages");
		suite.addTest(unitTests.data.AllTests.suite());
		suite.addTest(unitTests.data.constraints.AllTests.suite());
		suite.addTest(unitTests.dataCSV.AllTests.suite());
		suite.addTest(unitTests.documents.AllTests.suite());
		suite.addTest(unitTests.fonts.AllTests.suite());
		suite.addTest(unitTests.html.AllTests.suite());
		suite.addTest(unitTests.phonology.AllTests.suite());
		suite.addTest(unitTests.script.AllTests.suite());
		suite.addTest(unitTests.morph.AllTests.suite());
		return suite;
	}
}
