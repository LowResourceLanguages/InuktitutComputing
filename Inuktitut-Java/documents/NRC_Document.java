/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Jun 22, 2006
 * par / by Benoit Farley
 * 
 */
package documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import documents.pdfboxAddition.NRC_PDFText2XML;

public interface NRC_Document {
    
    public String getPageContent();
    public String getContentType();
    public String getUrlName();
    public String getTitle();
    public Date getDate();
    public float getInuktitutPercentage();
    public Object[][] getInuktitutFontsAndPercentages();
    public Object[] highlight(String [] x) throws Exception;
    public Object[] transliterate() throws OutOfMemoryError, Exception;
    
    public WebPageReader getWpr();
    public String[] getInuktitutFonts();
    public String[] getAllFontsNames();
    public String getPreferredFont();
    public void close();
    public Object[] getAllFonts();
    public boolean containsInuktitut();
    
    public class Exec {
    	
        public static NRC_Document getDocument(String urlName) throws Exception {
            WebPageReader wpr = null;
            NRC_Document doc = null;

                wpr = new WebPageReader(urlName);
                String contentType = wpr.contentType;
                wpr.connection.getInputStream();
                if (wpr.connection instanceof HttpURLConnection)
                    ((HttpURLConnection)wpr.connection).disconnect();
                if (contentType.equals("application/pdf"))
                    doc = new NRC_PDFDocument(urlName);
                else if (contentType.indexOf("text/html") >= 0)
                    doc = new NRC_HTMLDocumentByCobra(urlName);
//                	doc = new NRC_HTMLDocument(urlName);
                else if (contentType.indexOf("text/plain") >=0 || contentType.equals("application/msword"))
                    if (urlName.toLowerCase().endsWith(".doc"))
                        doc = new NRC_DOCDocument(urlName);
                    else
                        ;
                else
                    ;
            return doc;
        }
        
        public static NRC_Document getDocument (String fileName, String contentType, String urlName) throws Exception {
        	NRC_Document doc = null;
        	URL url = new URL(urlName);
        	File f = new File(fileName);
        	byte [] bs = new byte[(int) f.length()];
        	FileInputStream fis = new FileInputStream(fileName);
        	fis.read(bs, 0, (int) f.length());
        	fis.close();
        	if (contentType.matches("^text/html.*")) {
        		String encoding = null;
        		Pattern p = Pattern.compile("charset\\s*=\\s*(.+)");
        		Matcher mp = p.matcher(contentType);
        		if (mp.find())
        			encoding = mp.group(1);
        		doc = new NRC_HTMLDocumentByCobra(bs, url, encoding);
        	}
        	else if (contentType.equals("application/pdf"))
                doc = new NRC_PDFDocument("file://"+fileName);

        	return doc;
        }

        /*
         * args[0]: url
         * args[1]: action
         *    c: contenu (appel de getPageContent())
         *    vi: vérifier inuktitut
         */
        public static void main(String[] args) {
        	String option = null;
			if (args.length != 2 && args.length != 5) {
				System.out
						.println(" arguments: <url> <action - c:contenu  vi:vérifier quantité d'inuktitut  htmli:contenu html inuktitut avec http headers   htmlr: contenu html latin avec http headers>");
				System.out
						.println(" arguments: -f <file> <content type> <url> <action - c:contenu  vi:vérifier quantité d'inuktitut  htmli:contenu html inuktitut avec http headers   htmlr: contenu html latin avec http headers>");
				System.exit(1);
			}
			NRC_Document doc = null;
			try {
				if (args.length == 2) {
					doc = getDocument(args[0]);
					option = args[1];
				}
				else {
					doc = getDocument(args[1], args[2], args[3]);
					option = args[4];
				}
			} catch (FileNotFoundException e1) {
				System.out.println("--- 403 " + e1.getMessage());
				if (doc != null)
					doc.close();
				System.exit(1);
			} catch (Exception e1) {
//				e1.printStackTrace();
				System.out.println("---erreur de classe '" + e1.getClass().getName() + "' : " + e1.getMessage());
				if (doc != null)
					doc.close();
				System.exit(1);
			}
			// Only html, pdf, and doc; otherwise, null.
			if (doc != null) {
				if (option.equals("c")) {
					try {
						String contenu = null;
						contenu = doc.getPageContent();
						System.out.println(URLEncoder.encode(contenu, "utf-8"));
					} catch (UnsupportedEncodingException e) {
					}
				} else if (option.equals("vi")) {
					float ip = doc.getInuktitutPercentage();
					Object [][] fontsAndPC = doc.getInuktitutFontsAndPercentages();
					String fonts_str = "";
					for (int i=0; i<fontsAndPC.length; i++)
						fonts_str += fontsAndPC[i][0]+" ("+(Float)fontsAndPC[i][1]+")"+" ";
					System.out.print(Float.toString(ip)+" "+fonts_str);
				} else if (option.equals("htmli")
						&& doc instanceof NRC_HTMLDocumentByCobra) {
					try {
						((NRC_HTMLDocumentByCobra) doc).toUnicode(System.out);
					} catch (OutOfMemoryError e) {
						System.out.print("");
					} catch (Exception e) {
						System.out.print("");
					}
				} else if (option.equals("htmlr")
						&& doc instanceof NRC_HTMLDocumentByCobra) {
					try {
						((NRC_HTMLDocumentByCobra) doc).toRoman(System.out);
					} catch (OutOfMemoryError e) {
						System.out.print("");
					} catch (Exception e) {
						System.out.print("");
					}
				} else {
					System.out.print("");
				}
				doc.close();
			}
		}
	}
}
