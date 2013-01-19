package unitTests.documents;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.Style;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;

import documents.NRC_HTMLDocument;
import documents.NRC_HTMLTag;
import script.TransCoder;
import script.exec.TranscodingWebApp;
import junit.framework.TestCase;

public class NRC_HTMLDocumentTest extends TestCase {

	public void __testClose() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman.html");
			String pathOfCopy = doc.copyOfFile.getAbsolutePath();
			doc.close();
			assertTrue("The file "+pathOfCopy+" should not exist anymore.",!new File(pathOfCopy).exists());
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}

	}
	
	public void __testToUnicode() {
		NRC_HTMLDocument doc = null;
		String lineSeparator = System.getProperty("line.separator");
		String text = "        \"taitsumani pijumajauniqatsiasimanngimat akingalu akiliigutitsaqanngiluarsuni \n"
			+ "        kinnguumarijaulluanginninganut pitjutiqarsuni, kisianili maannaujukkut amisut qilanaalirtut. taimaimmat \n"
			+ "        ilagiisakkut atuinnaguisimavut sukattujautsuni qaritaujakkut pitutsimautigutimik pigutjinialirluni tilijaugutivinirminik \n"
			+ "        kuapait katimanimmariqartilugit kajusititsiluni.  \n";
		text = TransCoder.romanToUnicode(text);
		try {
			text = new String(text.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e1) {
		}
		String targetContent = 
			"Content-Type: text/html; charset=utf-8\n\n<!--END OF HTTP HEADERS-->"
			+ "<html>\n<head><base href="
			+ "\"file:/c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman.html\">\n"
			+ "<title>Fichier pour le test de toRoman</title>\n"
			+ "</head>\n"
			+ "<body>\n"
			+ "<p><font face=\"ainunavik,aipaiNunavik,nunacom\" size=\"3\"><FONT face=pigiarniq>\n"
			+ text
			+ "      </FONT></font>\n"
			+ "</p>\n"
			+ "</body>\n"
			+ "</html>";
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman.html");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.toUnicode(out);
			doc.close();
			String content = out.toString();
			out.close();
			content = content.replaceAll(lineSeparator, "\n");
			assertTrue("Wrong length: '"+content.length()+"' should have been '"+targetContent.length()+"'",content.length() == targetContent.length());
			assertTrue("Wrong content",content.equals(targetContent));
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}

	public void __testToRoman() {
		NRC_HTMLDocument doc = null;
		String lineSeparator = System.getProperty("line.separator");
		String targetEncoding = "iso-8859-1";
		String targetContent = 
			"Content-Type: text/html; charset=ISO-8859-1\n\n<!--END OF HTTP HEADERS-->"
			+ "<html>\n<head><base href="
			+ "\"file:/c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman.html\">\n"
			+ "<title>Fichier pour le test de toRoman</title>\n"
			+ "</head>\n"
			+ "<body>\n"
			+ "<p><font face=\"ainunavik,aipaiNunavik,nunacom\" size=\"3\"><FONT face=arial>\n"
			+ "        \"taitsumani pijumajauniqatsiasimanngimat akingalu akiliigutitsaqanngiluarsuni \n"
			+ "        kinnguumarijaulluanginninganut pitjutiqarsuni, kisianili maannaujukkut amisut qilanaalirtut. taimaimmat \n"
			+ "        ilagiisakkut atuinnaguisimavut sukattujautsuni qaritaujakkut pitutsimautigutimik pigutjinialirluni tilijaugutivinirminik \n"
			+ "        kuapait katimanimmariqartilugit kajusititsiluni.  \n"
			+ "      </FONT></font>\n"
			+ "</p>\n"
			+ "</body>\n"
			+ "</html>";
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman.html");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.toRoman(out);
			String encoding = doc.encoding;
			doc.close();
			String content = out.toString(encoding);
			out.close();
			String lowerCaseEncoding = encoding.toLowerCase();
			assertTrue("'"+lowerCaseEncoding+"' should be '"+targetEncoding+"'",lowerCaseEncoding.equals(targetEncoding));
			content = content.replaceAll(lineSeparator, "\n");
			assertTrue("Wrong length: '"+content.length()+"' should have been '"+targetContent.length()+"'",content.length() == targetContent.length());
			assertTrue("Wrong content",content.equals(targetContent));
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
	
	public void __testToRoman2() {
		NRC_HTMLDocument doc = null;
		String lineSeparator = System.getProperty("line.separator");
		String targetEncoding = "utf-8";
		String targetContent = 
			"Content-Type: text/html; charset=utf-8\n\n<!--END OF HTTP HEADERS-->"
			+ "<html>\n<head><base href="
			+ "\"file:/c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman2.html\">\n"
			+ "<title>Fichier pour le test de toRoman</title>\n"
			+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
			+ "</head>\n"
			+ "<body>\n"
			+ "<p>\u00c9</p>\n"
			+ "</body>\n"
			+ "</html>";
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testFileToRoman2.html");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.toRoman(out);
			String encoding = doc.encoding;
			doc.close();
			String content = out.toString(encoding);
			out.close();
			String lowerCaseEncoding = encoding.toLowerCase();
			assertTrue("'"+lowerCaseEncoding+"' should be '"+targetEncoding+"'",lowerCaseEncoding.equals(targetEncoding));
			content = content.replaceAll(lineSeparator, "\n");
			System.out.println(new String(content.getBytes(),"utf-8"));
			assertTrue("Wrong length: '"+content.length()+"' should have been '"+targetContent.length()+"'",content.length() == targetContent.length());
			assertTrue("Wrong content",content.equals(targetContent));
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
	
	public void __testToRoman3() {
		NRC_HTMLDocument doc = null;
		String lineSeparator = System.getProperty("line.separator");
		String targetEncoding = "utf-8";
		try {
			doc = new NRC_HTMLDocument("http://patrimoinecanadien.gc.ca/progs/ac-ca/progs/padie-bpidp/pubs/2004-2005/atp/1_f.cfm");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.toRoman(out);
			String encoding = doc.encoding;
			doc.close();
			String content = out.toString(encoding);
			out.close();
			String lowerCaseEncoding = encoding.toLowerCase();
			assertTrue("'"+lowerCaseEncoding+"' should be '"+targetEncoding+"'",lowerCaseEncoding.equals(targetEncoding));
			content = content.replaceAll(lineSeparator, "\n");
			Pattern pat = Pattern.compile("PADI\u00c9");
			Matcher mpat = pat.matcher(content);
			assertTrue("Wrong content",mpat.find());
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
	
	public void __testGetFont() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("http://www.tradition-orale.ca/inuktitut/inuit-recollections-the-military-presence-iqaluit-b31.html");
	        ElementIterator iterator = new ElementIterator(doc);
	        javax.swing.text.Element elem;
	        String value = null;
	        while ((elem = iterator.next()) != null) {
	        	if (elem.getName().equals("content")) {
	                int beg = elem.getStartOffset();
	                int end = elem.getEndOffset();
	                String text = null;
	                try {
	                    text = doc.getText(beg, end - beg);
	                } catch (BadLocationException e) {
	                }
	                text = text.replaceAll("\\s+", " ").trim();
	                if (text.startsWith("„b x5yb6")) {
	                	String [] fonts = doc.getFont(elem);
	                	if (fonts != null) {
	                		value = null;
	                		for (int i=0; i<fonts.length; i++) {
	                			System.out.println("---getFont--- fonts[]="+fonts[i]);
	                			if (value==null)
	                				value = fonts[i];
	                			else
	                				value += ", "+fonts[i];
	                		}
	                	}
	                	assertTrue("Wrong font-family: '"+value+"'",value != null && value.equals("nunacom, Verdana, Arial, Helvetica, sans-serif, "));	        			
	        		} 
	        	}
	        }
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}		
	}
	
	public void __testTrouverProprieteCSS_1() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testTranslit3.html");
	        ElementIterator iterator = new ElementIterator(doc);
	        javax.swing.text.Element elem;
	        Object value;
	        while ((elem = iterator.next()) != null) {
	        	if (elem.getName().equals("content")) {
	                int beg = elem.getStartOffset();
	                int end = elem.getEndOffset();
	                String text = null;
	                try {
	                    text = doc.getText(beg, end - beg);
	                } catch (BadLocationException e) {
	                }
	                text = text.replaceAll("\\s+", " ").trim();
    				value = doc.trouverProprieteCSSHeritage(CSS.Attribute.FONT_FAMILY, elem);
    				if (value != null) value = value.toString();
	                
	        		if (text.equals("wkw5 eiChAmJ5 w6vNw]/4nu4 xg6gx3F4 fxS`En4f8i vJq6h6bsK5 giyd/s9lt4 c9ostymJu4 tt6vu4 x7m Wo7m4ymi3j5 ttC6ymJdtu4.")) {
	        			assertTrue("Wrong font-family",value != null && 
	        					(value.equals("nunacom") || value.equals("nunacom, Arial, Helvetica, sans-serif")));
	        		}
	        		else if (text.equals("Wbcq5g6 w6vNw]/4ni4 xgw8NsJi4 bmgmi.")) {
        				assertTrue("Wrong font-family",value != null && value.equals("nunacom, Arial, Helvetica, sans-serif"));	        			
	        		} else {
	        			
	        		}
	        	}
	        }
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
		
	public void __testTrouverProprieteCSS_2() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("http://www.tradition-orale.ca/inuktitut/inuit-recollections-the-military-presence-iqaluit-b31.html");
	        ElementIterator iterator = new ElementIterator(doc);
	        javax.swing.text.Element elem;
	        Object value;
	        while ((elem = iterator.next()) != null) {
	        	if (elem.getName().equals("content")) {
	                int beg = elem.getStartOffset();
	                int end = elem.getEndOffset();
	                String text = null;
	                try {
	                    text = doc.getText(beg, end - beg);
	                } catch (BadLocationException e) {
	                }
	                text = text.replaceAll("\\s+", " ").trim();
	                if (text.startsWith("„b x5yb6")) {
	                	value = doc.trouverProprieteCSSHeritage(CSS.Attribute.FONT_FAMILY, elem);
	                	if (value != null) value = value.toString();
	                	assertTrue("Wrong font-family: '"+value+"'",value != null && value.equals("nunacom, Arial, Helvetica, sans-serif"));	        			
	        		} 
	        	}
	        }
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
		
	public void testGetRule() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testCSS_2.html");
	        StyleSheet styles = doc.getStyleSheet();
			ElementIterator iterator = new ElementIterator(doc);
	        javax.swing.text.Element elem;
	        Object value;
	        while ((elem = iterator.next()) != null) {
	        	if (elem.getName().equals("content")) {
	                int beg = elem.getStartOffset();
	                int end = elem.getEndOffset();
	                String text = null;
	                try {
	                    text = doc.getText(beg, end - beg);
	                } catch (BadLocationException e) {
	                }
	                text = text.replaceAll("\\s+", " ").trim();
	                if (text.startsWith("x5yb6")) {
	                	Element parent = elem.getParentElement();
	                	if (parent.getName().equals("p-implied"))
	                		parent = parent.getParentElement();
	                	AttributeSet attrs = parent.getAttributes();
	                	for (Enumeration e = attrs.getAttributeNames(); e.hasMoreElements();) {
	                		Object key = e.nextElement();
	                		System.out.println(key.toString()+": "+attrs.getAttribute(key));
	                	}
	                	Style rule = styles.getRule(HTML.getTag(parent.getName()),parent);
	                	rule = styles.getRule("html body div#container div#contenu div#contenu_droite div.soustitre div.texte");
	                	System.out.println("rule: "+rule);
	                	System.out.println("rule: "+rule.getName());
                		System.out.println("Nb. attributes: "+rule.getAttributeCount());
	                	for (Enumeration e = rule.getAttributeNames(); e.hasMoreElements();) {
	                		Object key = e.nextElement();
	                		System.out.println(key.toString()+": "+rule.getAttribute(key));
	                	}
	                	System.out.println("font-family>>>"+rule.getAttribute(CSS.Attribute.FONT_FAMILY));
	                	System.out.println("margin-top>>>"+rule.getAttribute(CSS.Attribute.MARGIN_TOP));
//	                	assertTrue("Wrong font-family: '"+value+"'",value != null && value.equals("nunacom, Arial, Helvetica, sans-serif"));	        			
	        		} 
	        	}
	        }
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
		
	public void __testTrouverProprieteCSS_3() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testCSS_2.html");
	        ElementIterator iterator = new ElementIterator(doc);
	        javax.swing.text.Element elem;
	        Object value;
	        while ((elem = iterator.next()) != null) {
	        	if (elem.getName().equals("content")) {
	                int beg = elem.getStartOffset();
	                int end = elem.getEndOffset();
	                String text = null;
	                try {
	                    text = doc.getText(beg, end - beg);
	                } catch (BadLocationException e) {
	                }
	                text = text.replaceAll("\\s+", " ").trim();
	                if (text.startsWith("x5yb6")) {
	                	value = doc.trouverProprieteCSSHeritage(CSS.Attribute.FONT_FAMILY, elem);
	                	if (value != null) value = value.toString();
	                	assertTrue("Wrong font-family: '"+value+"'",value != null && value.equals("nunacom, Arial, Helvetica, sans-serif"));	        			
	        		} 
	        	}
	        }
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
		
	public void __testDecouperTexteEnLignes_1() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testCSS.html");
			String lignes[] = doc.decouperTexteEnLignes();
			String expected[] = new String[]{
				"Essai de CSS","Un peu de texte","Avec liens ","abc","def"
			};
			doc.close();
			for (int i=0; i<lignes.length; i++)
				assertTrue(lignes[i]+" \\= "+expected[i],lignes[i].equals(expected[i]));
					
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}

	public void __testDecouperTexteEnLignes_2() {
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testTranslit.html");
			String lignes[] = doc.decouperTexteEnLignes();
			String expected[] = new String[]{
				"Texte en nunacom: ","w~kF4 wk4tg5 scs+y5 w[lCm",
				"Texte en naamajut: ","wËšF4 wk4tg5 scsÂ¥5 w[lCm",
				"Texte en Times New Roman: ","inuuvik inuktitut",
				"Texte en georgia:","inuuvik inuktitut",
				"Texte en prosyl: ","w~k=4 wk4tg5",
				"abc def ghi","abc","def",
				"ancrages en-ligne références 3 référence 4",
				"ancrages","référence 1","référence 2"
			};
			doc.close();
			for (int i=0; i<lignes.length; i++)
				assertTrue(lignes[i]+" \\= "+expected[i],lignes[i].equals(expected[i]));
					
		} catch (Exception e) {
			e.printStackTrace();
			if (doc != null) doc.close();
		}
	}
	
	


}
