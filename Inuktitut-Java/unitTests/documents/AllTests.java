package unitTests.documents;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for documents");
		//$JUnit-BEGIN$
		suite.addTestSuite(NRC_HTMLDocumentTest.class);
		suite.addTestSuite(NRC_PDFDocumentTest.class);
		suite.addTestSuite(NRC_HTMLDocumentByCobraTest.class);
		//$JUnit-END$
		return suite;
	}

}
