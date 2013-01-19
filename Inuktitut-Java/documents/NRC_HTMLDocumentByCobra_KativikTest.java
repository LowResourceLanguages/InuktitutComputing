package documents;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class NRC_HTMLDocumentByCobra_KativikTest extends TestCase {

	public void test_write() {
		String html = "<html><head><title>TEST</title>\n"
			+"<style type=\"text/css\">\n"
			+".aipai {\n"
			+"  font-family: AiPaiNunavik;\n"
			+"}\n"
			+"</style>\n"
			+"</head>\n"
			+"<body>\n<p>bonjour à tout le monde</p>\n"
			+"<p class=\"aipai\">"
//			+"wkw5 "
			+"bW‰5"
//			+"vNbu"
			+"</p>\n"
//			+"<p style=\"font-family:AiPaiNunavik;\">wkw5 bW‰5 <b>vNbu</b></p>\n"
//			+"<p><font face=\"AiPaiNunavik\">wkw5 bW‰5 <b>vNbu</b></font></p>\n"
			+"</body></html>";
		URL url = null;
		try {
			url = new URL("http://localhost/null.html");
		} catch (MalformedURLException e) {
		}
		NRC_HTMLDocumentByCobra_Kativik doc = null;
		try {
			doc = new NRC_HTMLDocumentByCobra_Kativik(html, url, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.toUnicode(false);
		doc.write();
		fail("Not yet implemented");
	}

	
	public void ___test_toUnicodeBoolean() {
		String html = "<html><head><title>TEST</title>\n</head><body>\n<p>bonjour le monde</p>\n"
			+"</body></html>";
		URL url = null;
		try {
			url = new URL("http://localhost/null.html");
		} catch (MalformedURLException e) {
		}
		NRC_HTMLDocumentByCobra_Kativik doc = null;
		try {
			doc = new NRC_HTMLDocumentByCobra_Kativik(html, url, "iso-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.toUnicode(false);
		fail("Not yet implemented");
	}

}
