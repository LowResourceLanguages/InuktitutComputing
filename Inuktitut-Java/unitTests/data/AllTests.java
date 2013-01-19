package unitTests.data;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for data");
		//$JUnit-BEGIN$
		suite.addTestSuite(LinguisticDataAbstractTest_compiled_data.class);
		suite.addTestSuite(LinguisticDataAbstractTest_csv_data.class);
//		suite.addTestSuite(BaseTest.class);
		
		suite.addTestSuite(SuffixTest_csv_data.class);
		suite.addTestSuite(SuffixTest_compiled_data.class);
		suite.addTestSuite(DemonstrativeTest_csv_data.class);
		suite.addTestSuite(DemonstrativeTest_compiled_data.class);
		suite.addTestSuite(DemonstrativeEndingTest_csv_data.class);
		suite.addTestSuite(DemonstrativeEndingTest_compiled_data.class);
		suite.addTestSuite(MorphemeTest_csv_data.class);
		suite.addTestSuite(MorphemeTest_compiled_data.class);

		//$JUnit-END$
		return suite;
	}

}
