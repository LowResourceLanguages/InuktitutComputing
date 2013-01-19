package unitTests.documents;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import documents.NRC_PDFDocument;
import junit.framework.TestCase;

public class NRC_PDFDocumentTest extends TestCase {

	public void testGetContents() {
		String pdfURLName = "file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/A-03763i_90.pdf";
		try {
			NRC_PDFDocument doc = new NRC_PDFDocument(pdfURLName);
			String contents = doc.getContents();
			System.out.println(contents);
			String targetPattern = "Z\\?m4f5\\s+WJmJ5 g4yCsti4\\s+kNo8i Z\\?m4fi9l Wp5yC6t4f5,\\s+";
			Pattern p = Pattern.compile(targetPattern);
			Matcher mp = p.matcher(contents);
			assertTrue("Mauvais résultat",mp.find());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
