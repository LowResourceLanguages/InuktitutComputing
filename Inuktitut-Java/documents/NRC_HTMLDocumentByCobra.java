/*
 * Conseil national de recherche Canada 2007/
 * National Research Council Canada 2007
 * 
 * Créé le / Created on May 29, 2007
 * par / by Benoit Farley
 * 
 */
package documents;

import fonts.Font;
import fonts.TextCat;
import html.BetweenTag;
import html.HTMLDocuElement;
import html.HtmlDocu;
import html.HtmlEntities;
import html.Tag;
import utilities1.Util;
import script.Syllabics;
import script.TransCoder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.text.html.HTMLDocument;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.html2.HTMLCollection;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.style.CSS2PropertiesImpl;
//import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleBrowserFrame;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;

import org.apache.log4j.Logger;

import com.steadystate.css.dom.CSSStyleSheetImpl;

public class NRC_HTMLDocumentByCobra extends HTMLDocument implements NRC_Document{

//	public static final Log LOG = LogFactory.getLog(NRC_HTMLDocumentByCobra.class);
	/**
	#	 * Log4j logger
	#	 */
    private static Logger LOG = Logger.getLogger(NRC_HTMLDocumentByCobra.class+".main");
    private static Logger LOG1 = Logger.getLogger(NRC_HTMLDocumentByCobra.class+".getTextElementsWithFont_TextNode");
    private static Logger LOG2 = Logger.getLogger(NRC_HTMLDocumentByCobra.class+".getTextElementsWithFont");
    private static Logger LOG3 = Logger.getLogger(NRC_HTMLDocumentByCobra.class+".getFontsA");
    
    public String encoding = null;

    private Date date;
    private String baseName = null;
    private String contentType;
    protected HTMLDocumentImpl document;
    protected HTMLDocumentImpl documentUnicode;
    private String urlName;
    private WebPageReader wpr;
    private boolean containsInuktitut = false;
    private String inuktitutContent = null;
    private long lengthOfInuktitutContent = -1;
    private long lengthOfTotalContent = -1;
    private Hashtable inuktitutLegacy = null;
    private String pageContent = null;
    private String httpHeaderContentType;
    private String title;
    private Hashtable fonts = null;
    private String previousElementName = "";
    private String[] encodingAndManner;
    
    public  static File tmpDir = new File(System.getenv("JAVA_INUKTITUT"),"tmp");
    public File copyOfFile = null;
    private boolean deleteCopy = false;
    public InputStream inputStream = null;
    
    private boolean scriptingEnabled = true;
    
    // ----------------Copied from Nutch and modified as indicated:
    // I used 1000 bytes at first, but  found that some documents have 
    // meta tag well past the first 1000 bytes. 
    // (e.g. http://cn.promo.yahoo.com/customcare/music.html)
    private static final int CHUNK_SIZE = 2000;
    private static String httpContentTypePatternString = "http-equiv=\"?content-type\"?";
    private static Pattern metaPattern =
      Pattern.compile("<meta\\s+([^>]*"+httpContentTypePatternString+"[^>]*)>",
                      Pattern.CASE_INSENSITIVE);
    private static String charsetPatternString = "(charset=\\s*)+([a-z][_\\-0-9a-z]*)";
    private static Pattern charsetPattern =
      Pattern.compile(charsetPatternString,
                      Pattern.CASE_INSENSITIVE);
    private static Pattern contentPattern =
    	Pattern.compile("content=\"?([^\"]+)\"?",
    				Pattern.CASE_INSENSITIVE);
    private static Pattern bomPattern = 
    	Pattern.compile("\u00ef\u00bb\u00bf"); // added by Benoît Farley
    
    /**
     * Given a <code>byte[]</code> representing an html file of an 
     * <em>unknown</em> encoding,  read out 'charset' parameter in the meta tag   
     * from the first <code>CHUNK_SIZE</code> bytes.
     * If there's no meta tag for Content-Type or no charset is specified,
     * <code>null</code> is returned.  <br />
     * FIXME: non-byte oriented character encodings (UTF-16, UTF-32)
     * can't be handled with this. 
     * We need to do something similar to what's done by mozilla
     * (http://lxr.mozilla.org/seamonkey/source/parser/htmlparser/src/nsParser.cpp#1993).
     * See also http://www.w3.org/TR/REC-xml/#sec-guessing
     * <br />
     *
     * @param content <code>byte[]</code> representation of an html file
     */

//    private static String sniffCharacterEncoding(byte[] content) {
    private static String[] sniffCharacterEncoding(byte[] content) {
      int length = content.length < CHUNK_SIZE ? 
                   content.length : CHUNK_SIZE;
      String source = null; // added by Benoît Farley
      
      // We don't care about non-ASCII parts so that it's sufficient
      // to just inflate each byte to a 16-bit value by padding. 
      // For instance, the sequence {0x41, 0x82, 0xb7} will be turned into 
      // {U+0041, U+0082, U+00B7}. 
      String str = new String(content, 0, 0, length); 

      Matcher metaMatcher = metaPattern.matcher(str);
      String encoding = null;
      if (metaMatcher.find()) {
        Matcher charsetMatcher = charsetPattern.matcher(metaMatcher.group(1));
        if (charsetMatcher.find()) {
          encoding = new String(charsetMatcher.group(1));
          source = "meta"; // added by Benoît Farley
          }
      } else { // added by Benoît Farley - look for byte order mark
          Matcher bomMatcher = bomPattern.matcher(str);
          if (bomMatcher.find()) {
        	  encoding = "utf-8";
        	  source = "bom";
          }
      }

//      return encoding;
      if (encoding != null)
    	  return new String[]{encoding,source};  // added by Benoît Farley
      else
    	  return null;
    }
    //----------------------------------------------------------
    public NRC_HTMLDocumentByCobra () {
    	
    }
    
    public NRC_HTMLDocumentByCobra( byte [] content, URL url, String enc) throws Exception {
    	ByteArrayInputStream is = new ByteArrayInputStream(content);
    	inputStream = is;
    	MySimpleUserAgentContext userAgentContext = new MySimpleUserAgentContext();
    	MySimpleHtmlRendererContext htmlRendererContext = new MySimpleHtmlRendererContext(new SimpleBrowserFrame(null),userAgentContext);
        DocumentBuilderImpl dbi = new DocumentBuilderImpl(userAgentContext,htmlRendererContext);
        if (enc != null && !enc.equals(""))
        	encodingAndManner = new String[]{enc,"httpheader"};
        else {
        	byte [] top = Arrays.copyOf(content,CHUNK_SIZE);
         	encodingAndManner  = sniffCharacterEncoding(top);
        	if (encodingAndManner != null)
        		LOG.info("---NRC_HTMLDocumentByCobra--- sniffed encoding: '"+encodingAndManner[0]+"' ("+
        				encodingAndManner[1]+")");
        	else {
        		LOG.info("---NRC_HTMLDocumentByCobra--- sniffed encoding: null");
                encodingAndManner = new String[]{"iso-8859-1","default"};
        	}
        }
        urlName = url.toExternalForm();
		LOG.info("---NRC_HTMLDocumentByCobra--- url: '"+urlName+"'");
        document = (HTMLDocumentImpl)dbi.parse(new InputSourceImpl(is,urlName,encodingAndManner[0]));
        checkForCSSPseudoRules();
		LOG.info("---NRC_HTMLDocumentByCobra--- base: '"+document.getBaseURI()+"'");
		LOG.info("---NRC_HTMLDocumentByCobra--- number of links: '"+document.getLinks().getLength()+"'");
		LOG.info("---NRC_HTMLDocumentByCobra--- text: '"+document.getTextContent()+"'");
        encoding = encodingAndManner[0];
        is.reset();
    }
    
    public NRC_HTMLDocumentByCobra( String content, URL url, String enc) throws Exception {
    	this(content.getBytes("utf-8"), url, "utf-8");
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------------------
    public NRC_HTMLDocumentByCobra( String file, String urlName, String enc) throws Exception {
        this.urlName = urlName;
        if (enc != null && !enc.equals("")) {
        	httpHeaderContentType = "text/html; charset="+enc;
        }
        copyOfFile = new File(file);
        HTMLDocumentByCobra();
    }

    public NRC_HTMLDocumentByCobra( String file, String urlName, String enc, boolean javascriptEnabled) throws Exception {
    	scriptingEnabled = javascriptEnabled;
        this.urlName = urlName;
        if (enc != null && !enc.equals("")) {
        	httpHeaderContentType = "text/html; charset="+enc;
        }
        copyOfFile = new File(file);
        HTMLDocumentByCobra();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------

    public NRC_HTMLDocumentByCobra(String urlName)  throws Exception {
        this.urlName = urlName;
        URL url = null;
        document = null;
        wpr = new WebPageReader(urlName);
        url = wpr.url;
        // Copier le fichier dans la cache
        copyOfFile = copyInCache(url);
        deleteCopy = true;
        httpHeaderContentType = wpr.contentType;
        HTMLDocumentByCobra();
    } 
    
    void HTMLDocumentByCobra() throws Exception {
    	FileInputStream fis = null;
    	try {
        if (httpHeaderContentType != null) {
        	httpHeaderContentType = httpHeaderContentType.toLowerCase();
        	Pattern httpPat = Pattern.compile("text/html\\s*(;\\s*charset\\s*=\\s*(.+))?",
        			Pattern.CASE_INSENSITIVE);
        	Matcher httpMat = httpPat.matcher(httpHeaderContentType);
            if (!httpMat.find())
                throw new Exception("not html");
            else if (httpMat.group(2) != null) {
        		encoding = httpMat.group(2);
        		encodingAndManner = new String[]{encoding,"httpheader"};
        	}
        } 
        if (encoding == null) {
        	// Vérifier <meta http-equiv="Content-Type" content="text/html; charset=.....">
        	fis = new FileInputStream(copyOfFile);
        	byte [] fisBytes = new byte[(int)copyOfFile.length()];
        	fis.read(fisBytes);
        	String fisContents = new String(fisBytes,"iso-8859-1");
        	fisContents = fisContents.replaceAll("\\s+", " ");
        	Pattern metaPat = 
        		Pattern.compile("<meta http-equiv ?= ?\"content-type\" content ?= ?\".+?charset ?= ?([^=\"]+)\"",
        				Pattern.CASE_INSENSITIVE);
        	Matcher metaMat =
        		metaPat.matcher(fisContents);
        	if (metaMat.find()) {
        		encodingAndManner = new String[]{metaMat.group(1),"meta"};
        	} else if (fisContents.contains("\u00ef\u00bb\u00bf")) {
        		encodingAndManner = new String[]{"utf-8","bom"};
        	} else {
        		encodingAndManner = new String[]{"iso-8859-1","default"};
        	}
            encoding = encodingAndManner[0];
        }
        LOG.debug("encoding = "+encoding);
//    	String newFilename = checkForBadComments(copyOfFile.getAbsolutePath(), encoding);
//    	copyOfFile = new File(newFilename);
        InputStream is = new FileInputStream(copyOfFile);
        MySimpleUserAgentContext userAgentContext = new MySimpleUserAgentContext(scriptingEnabled);
    	MySimpleHtmlRendererContext htmlRendererContext = new MySimpleHtmlRendererContext(new SimpleBrowserFrame(null),userAgentContext);
        DocumentBuilderImpl dbi = new DocumentBuilderImpl(userAgentContext,htmlRendererContext);
        document = (HTMLDocumentImpl)dbi.parse(new InputSourceImpl(is,urlName,encoding));
        checkForCSSPseudoRules();
    	} catch (org.w3c.css.sac.CSSException e) {
    	} catch (Exception e) {
    		e.printStackTrace(System.err);
    		throw new Exception ("From HTMLDocumentByCobra() --- "+e.getClass().getName()+": "+e.getMessage());
    	} finally {
    		if (fis != null) fis.close();
    	}
    }
    
    
    public static String checkForBadComments (String fileName, String encoding) {
    	File f = new File(fileName);
		String content = "";
		String newFileName = null;
    	try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),encoding));
			String line;
			while ((line=br.readLine()) != null) {
				content += line+System.getProperty("line.separator");
			}
			br.close();
			content = checkForBadComments(content);
			newFileName = fileName+".out";
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(newFileName)),encoding);
			osw.write(content,0,content.length());
			osw.close();
		} catch (UnsupportedEncodingException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return newFileName;
    }
    
    public static String checkForBadComments(String content) {
		String newContent = "";
		Pattern pat = Pattern.compile("(<!--|-->)");
		int pos = 0;
		int nopen=0;
		int nclose=0;
		String pattern = "";
		Matcher mpat = pat.matcher(content);
		while (mpat.find(pos)) {
			if (mpat.group().equals("<!--")) {
				nopen++;
				pattern += "1";
			}
			else {
				nclose++;
				pattern += "0";
			}
			pos = mpat.end();
		}
		if (nopen==nclose)
//			System.out.println("Pas de problème");
			newContent = content;
		else {
//			System.out.println("Oups!   "+pattern);
			pos = 0; 
			for (int i=0; i<pattern.length(); i++) {
				int newPos;
				if (pattern.charAt(i)=='0') {
					newPos = content.indexOf("-->", pos)+3;
					newContent += content.substring(pos,newPos);
					pos = newPos;
				} else 
					if (i+1==pattern.length()) {
						newContent += content.substring(pos);
						pos = content.length();
					}
					else if (pattern.charAt(i+1)=='0') {
						newPos = content.indexOf("-->", pos)+3;
						newContent += content.substring(pos, newPos);
						i++;
						pos = newPos;
					} else {
						newPos = content.indexOf("<!--",pos)+4;
						newPos = content.indexOf("<!--",newPos);
						newContent += content.substring(pos, newPos)+"--><!--";
						pos = newPos+4;
					}
			}
			if (pos != content.length())
				newContent += content.substring(pos);
		}
		return newContent;
    }
    
    private void checkForCSSPseudoRules() {
        Collection ss = document.getStyleSheets();
        java.util.Iterator itss = ss.iterator();
        Pattern patRule = Pattern.compile("^([^:]+?):(link|active|visited|hover)\\s+(\\{.+)$");
        HashSet newRules = new HashSet();
        while (itss.hasNext()) {
        	CSSStyleSheetImpl ss1 = (CSSStyleSheetImpl)itss.next();
        	CSSRuleList ruleList = ss1.getCssRules();
        	for (int i=0; i < ruleList.getLength(); i++) {
        		CSSRule rule = ruleList.item(i);
        		String ruleText = rule.getCssText();
//        		System.out.println("rule : "+ruleText);
        		Matcher mpatRule = patRule.matcher(ruleText);
        		if (mpatRule.find()) {
        			String newRuleText = mpatRule.group(1)+" "+mpatRule.group(3);
        			if (!newRules.contains(mpatRule.group(1))) {
        				newRules.add(mpatRule.group(1));
//        				System.out.println("***new rule : "+newRuleText);
        				ss1.insertRule(newRuleText, i++);
        			}
        		}
        	}
        }
    }
    
    public boolean hasContents() {
    	if (document.getChildrenArray()==null)
    		return false;
    	return true;
    }    
    
    public void displayNodes() {
    	NodeImpl [] nodes = document.getChildrenArray();
    	displayNodes(nodes,"");
    }
    
    private void displayNodes(NodeImpl[] nodes, String tabs) {
    	if (nodes != null) {
    		for (int i=0; i<nodes.length; i++) {
    			String text = "";
    			if (nodes[i].getNodeType()==NodeImpl.TEXT_NODE)
    				text = nodes[i].getNodeValue();
    			else if (nodes[i].getNodeType()==NodeImpl.COMMENT_NODE)
    				text = nodes[i].getNodeValue();
        		System.out.println(tabs+nodes[i].getNodeName()+"  ["+text+"]");
        		displayNodes(nodes[i].getChildrenArray(),tabs+"    ");
    		}
    	}
    }
    
    public void close() {
        document.close();
        if (copyOfFile != null && deleteCopy)
        	copyOfFile.delete();
    }

    public boolean containsInuktitut() {
    	if (lengthOfInuktitutContent == -1)
//        if (pageContent==null)
            getPageContent();
    	else
    		containsInuktitut = true;
        return containsInuktitut;
    }

    public float getInuktitutPercentage() {
    	if (containsInuktitut())
    		return (float)lengthOfInuktitutContent / (float)lengthOfTotalContent;
    	else
    		return 0;
    }

    public Object[] getAllFonts() {
        if (fonts==null)
            getPageContent();
        Set keySet = fonts.keySet();
        Object [] keys = keySet.toArray();
        return keys;
    }

    public String[] getAllFontsNames() {
        Object [] fnts = getAllFonts();
        String[] fontNames = new String[fnts.length];
        for (int i=0; i<fnts.length; i++)
            fontNames[i] = (String)fnts[i]; 
        return fontNames;
    }

    public String getContentType() {
        return contentType;
    }

    public String getBaseURI() {
    	if (baseName==null)
    		baseName = document.getBaseURI();
    	return baseName;
    }

    public Date getDate() {
        return date;
    }

    public String[] getInuktitutFonts() {
        Vector fs = new Vector();
        String[] allFonts = getAllFontsNames();
        for (int i=0; i<allFonts.length; i++) {
            if (Font.isLegacy(allFonts[i]))
                fs.add(allFonts[i]);
        }
        return (String[])fs.toArray(new String[0]);
    }

    public Object[][] getInuktitutFontsAndPercentages() {
        String[] allFonts = getAllFontsNames();
        allFonts = (String [])fonts.keySet().toArray(new String[0]);
        Vector fs = new Vector();
        for (int i=0; i<allFonts.length; i++) {
            String fnt = allFonts[i];
            for (int j=0; j<Font.fonts.length; j++)
                if (fnt.toLowerCase().startsWith(Font.fonts[j]))
                	if (!fs.contains(Font.fonts[j]))
                		fs.add(Font.fonts[j]);
        }
        Object res[][] = new Object[fs.size()][2];
        for (int i=0; i<fs.size(); i++) {
        	String p = (String)fs.get(i);
        	Integer n = (Integer)inuktitutLegacy.get(p);
        	int ni = n.intValue();
        	float pcn = (float)ni / (float)pageContent.replaceAll("\\s","").length();
        	res[i][0] = p;
        	res[i][1] = new Float(pcn);
        }
        return res;
    }


    /*
     * The inuktitut text is transcoded to Unicode.
     * Multiple spaces are replaced by a single space.
     * 
     * (non-Javadoc)
     * @see documents.NRC_DocumentHandler#getPageContent()
     */
    public String getPageContent() {
        if (pageContent != null)
            return pageContent;
        NodeImpl [] nodes = document.getChildrenArray();
        fonts = new Hashtable();
        inuktitutLegacy = new Hashtable();
        inuktitutContent = "";
        previousElementName = "";
        lengthOfTotalContent = 0;
        lengthOfInuktitutContent = 0;
        String contenuPage = getNodeTextContent(nodes).trim();
        contenuPage = contenuPage.replaceAll("\\s+"," ");
        pageContent = new String(contenuPage);
        return contenuPage;
    }
    

    public String getPreferredFont() {
        return null;
    }

    public String getTitle() {
        if (title == null) {
            NodeList titleNodes = document.getElementsByTagName("title");
            if (titleNodes != null) {
                Node titleNode = titleNodes.item(0);
                Node titleText = titleNode.getFirstChild();
                if (titleText != null) {
                    title =  titleText.getNodeValue();
                }
            }
        }
        return title;
    }

    public String getUrlName() {
        return urlName;
    }

    public WebPageReader getWpr() {
        return wpr;
    }

    public Object[] highlight(String[] x) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Hashtable transHash;

    public Object[] transliterate() throws OutOfMemoryError, Exception {
            // -------------------------------------------------------
            // Lecture du fichier HTML et création d'un document HTML
            // Traitement du document HTML (traitement du texte en inuktitut)
            // Ecriture du document HTML dans un nouveau fichier HTML
            // -------------------------------------------------------
            
            transHash = new Hashtable();
            traiterDocPourTranslit();
            HtmlDocu doc2 = new HtmlDocu(copyOfFile,encoding);
            // Insérer <base ...> si absent de la source
            if (document.getElementsByTagName("base").getLength()==0)
            	doc2.insertBase(getBaseURI());
            File fout2 = traiterDocPourTranslit2(doc2, encoding);
            return new Object[] { encoding, fout2 };
    }
    
    public void toRoman(OutputStream out) throws OutOfMemoryError, Exception {
        transHash = new Hashtable();
        traiterDocPourTranslit();
        HtmlDocu doc2 = new HtmlDocu(copyOfFile,encoding);
        // Insérer <base ...> si absent de la source
        if (document.getElementsByTagName("base").getLength()==0)
        	doc2.insertBase(getBaseURI());
        File fout2 = traiterDocPourTranslit3(doc2, encoding, "latin");
		FileInputStream fr = new FileInputStream(fout2);
		int c;
		while ((c=fr.read()) != -1) {
			out.write(c);
		}
		fr.close();
		fout2.delete();
		out.flush();
    }

    /*
     * Retourne une chaîne contenant le contenu intégral du document HTML
     * avec l'inuktitut converti en Unicode et présenté avec la police Pigiarniq.
     */
    public void toUnicode(OutputStream out) throws OutOfMemoryError, Exception {
        // -------------------------------------------------------
        // Lecture du fichier HTML et création d'un document HTML
        // Traitement du document HTML (traitement du texte en inuktitut)
        // Ecriture du document HTML dans un nouveau fichier HTML
        // -------------------------------------------------------
        
        transHash = new Hashtable();
        traiterDocPourTranslit();
        HtmlDocu doc2 = null;
        if (copyOfFile != null)
        	doc2 = new HtmlDocu(copyOfFile,encoding);
        else
        	doc2 = new HtmlDocu(inputStream,encoding);
        // Insérer <base ...> si absent de la source
        if (document.getElementsByTagName("base").getLength()==0)
        	doc2.insertBase(getBaseURI());
        File fout2 = traiterDocPourTranslit3(doc2, encoding, "utf-8");
        float percent =  getInuktitutPercentage();
		out.write(("Content-Type: text/html; charset="+"utf-8"+"\n").getBytes());
		out.write(("Inuktitut-Percentage: "+String.valueOf(percent)).getBytes());
		out.write("\n\n".getBytes());
		out.write("<!--END OF HTTP HEADERS-->".getBytes());
		FileInputStream fr = new FileInputStream(fout2);
		int c;
		while ((c=fr.read()) != -1) {
			out.write(c);
		}
		fr.close();
		fout2.delete();
		out.flush();
}

    public void toUnicode2(OutputStream out, boolean aipaitai, String fontName) throws OutOfMemoryError, Exception {
        // -------------------------------------------------------
        // Lecture du fichier HTML et création d'un document HTML
        // Traitement du document HTML (traitement du texte en inuktitut)
        // Ecriture du document HTML dans un nouveau fichier HTML
        // -------------------------------------------------------
        
        transHash = new Hashtable();
        traiterDocPourTranslit();
        HtmlDocu doc2 = null;
        if (copyOfFile != null)
        	doc2 = new HtmlDocu(copyOfFile,encoding);
        else
        	doc2 = new HtmlDocu(inputStream,encoding);
        // Insérer <base ...> si absent de la source
        if (document.getElementsByTagName("base").getLength()==0)
        	doc2.insertBase(getBaseURI());
        File fout2 = traiterDocPourTranslit3_2(doc2, encoding, "utf-8", aipaitai, fontName);
		FileInputStream fr = new FileInputStream(fout2);
		int c;
		while ((c=fr.read()) != -1) {
			out.write(c);
		}
		fr.close();
		fout2.delete();
		out.flush();
}

    public void toUnicode3(OutputStream out, boolean aipaitai) throws OutOfMemoryError, Exception {
        // -------------------------------------------------------
        // Lecture du fichier HTML et création d'un document HTML
        // Traitement du document HTML (traitement du texte en inuktitut)
        // Ecriture du document HTML dans un nouveau fichier HTML
        // -------------------------------------------------------
        
        transHash = new Hashtable();
        traiterDocPourTranslit();
        HtmlDocu doc2 = null;
        if (copyOfFile != null)
        	doc2 = new HtmlDocu(copyOfFile,encoding);
        else
        	doc2 = new HtmlDocu(inputStream,encoding);
        // Insérer <base ...> si absent de la source
        if (document.getElementsByTagName("base").getLength()==0)
        	doc2.insertBase(getBaseURI());
        doc2.insertLinkCSS("/tcihtml.css");
        File fout2 = traiterDocPourTranslit3_3(doc2, encoding, "utf-8", aipaitai);
		FileInputStream fr = new FileInputStream(fout2);
		int c;
		while ((c=fr.read()) != -1) {
			out.write(c);
		}
		fr.close();
		fout2.delete();
		out.flush();
}
    
    //---------------------------------------UNICODE---------------------------------------------------
    
    // ---------------------------------------------------------------------------------------------------------------

    public static void dumpFile (File file, OutputStream out) {
    	FileInputStream fr = null;
		try {
			fr = new FileInputStream(file);
			int c;
			while ((c=fr.read()) != -1) {
				out.write(c);
			}
			fr.close();
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getEncoding() {
        return encodingAndManner[0];
    }
    
    public String getEncodingManner() {
        return encodingAndManner[1];
    }
    
    public HTMLDocumentImpl getDocument() {
        return document;
    }
    

    /*
     * ----------- PRIVATE ----------------------------
     */
        
    String [] determineDocEncoding() {
        Pattern p = Pattern.compile("charset=(.+)");
        Matcher mp = p.matcher(httpHeaderContentType);
        if (mp.find())
            return new String[]{mp.group(1),"httpheader"};
        else if (document != null) {
            // No specification of charset in HTTP Header
            NodeList metaElements = document.getElementsByTagName("meta");
            for (int i=0; i<metaElements.getLength(); i++) {
                Node metaElement = metaElements.item(i);
                NamedNodeMap attributes = metaElement.getAttributes();
                Node httpEquiv = attributes.getNamedItem("http-equiv"); 
                if (httpEquiv != null && httpEquiv.getNodeValue().toLowerCase().equals("content-type")) {
                        String content = attributes.getNamedItem("content").getNodeValue().toLowerCase();
                        mp = p.matcher(content);
                        if (mp.find())
                            return new String[]{mp.group(1),"meta"};
                }
            }
            // No specification of charset in META
            NodeImpl firstNode = document.getChildrenArray()[0];
            if (firstNode.getNodeType()==NodeImpl.TEXT_NODE &&
                    firstNode.getTextContent().equals("\u00ef\u00bb\u00bf"))
                return new String[]{"utf-8","bom"};
        }
        return null;
    }
    
   String getNodeTextContent(NodeImpl [] nodes) {
       if (nodes == null)
           return "";
       String wholeText = "";
       for (int i=0; i<nodes.length; i++) {
           String textOfNode = getNodeTextContent(nodes[i]);
           wholeText = wholeText.concat(textOfNode);
       }
       return wholeText;
   }
   
   String getNodeTextContent(NodeImpl node) {
       String text = null;
       if (node.getNodeType()==NodeImpl.TEXT_NODE) {
           text = node.getTextContent();
           NodeImpl parentNode = (NodeImpl)node.getParentNode();
           String parentName = parentNode.getNodeName().toLowerCase();
           if (parentName.equals("script") || parentName.equals("style") || parentName.equals("#document") || parentName.equals("title"))
               return "";
           // Record the names of the fonts
           String[] elementFonts = getFonts(node);
           if (elementFonts != null) {
               for (int ifs=0; ifs<elementFonts.length; ifs++) {
                   if (!fonts.containsKey(elementFonts[ifs])) {
                       fonts.put(elementFonts[ifs],new Integer(1));
                   } else {
                       fonts.put(elementFonts[ifs],new Integer(((Integer)fonts.get(elementFonts[ifs])).intValue()+1));
                   }
               }
           }
           String font = null;
           if (text.replaceAll("\\s", "").equals("")) {
           	;
           } else if (Syllabics.containsInuktitut(text)) {
               containsInuktitut = true;
               lengthOfInuktitutContent += text.replaceAll("\\s", "").length();
        	   lengthOfTotalContent += text.replaceAll("\\s", "").length();
           } else if ((font=Font.containsLegacyFont(elementFonts)) != null) {
               containsInuktitut = true;
               text = TransCoder.legacyToUnicode(text, font);
               lengthOfInuktitutContent += text.replaceAll("\\s", "").length();
        	   lengthOfTotalContent += text.replaceAll("\\s", "").length();
				Object n = inuktitutLegacy.get(font);
				int ni = 0;
				if (n != null)
					ni = ((Integer)n).intValue();
				ni += text.replaceAll("\\s", "").length();
				inuktitutLegacy.put(font, new Integer(ni));
				inuktitutContent += text;
           } else {
        	   lengthOfTotalContent += text.replaceAll("\\s", "").length();
           }
           if (Tag.isTagThatAddsSpace(previousElementName)) {
               text = " ".concat(text);
           }
           return text;
       }  else {
           previousElementName = node.getNodeName().toLowerCase();
           return getNodeTextContent(node.getChildrenArray());
       }
}
   
   /*
    * Copy the URL's byte contents into a cached file.
    */
   private File copyInCache(URL url) throws IOException {
	   File of = null;
	   String prefix = null;
	   try {
		   /* Créer le fichier de copie. */
		   String pre = String.valueOf(Math.random());
		   prefix = "cobracopy_"+pre;
		   of = File.createTempFile(prefix,".html",tmpDir);
	   } catch (IOException ioe1) {
		   throw new IOException("From copyInCache, createTempFile: prefix='"+prefix+"'; tmpDir='"+tmpDir.getAbsolutePath()+"'");   
	   }
	   try {
       InputStream is = url.openStream();
       FileOutputStream os = new FileOutputStream(of);
       int b;
       while ((b = is.read()) != -1)
           os.write(b);
       os.close();
       is.close();
       return of;
	   } catch (IOException ioe1) {
		   throw new IOException("From copyInCache, openStream: url='"+url.toExternalForm()+"'");
	   }
   }
   
//   private File copyInCache(InputStream is, URL url) throws IOException {
//       /* Créer le fichier de copie. */
//       File urlFile = new File(url.getFile());
//       String prefix = "copy_"+urlFile.getName()+"_";
//       File of = File.createTempFile(prefix,".html",tmpDir);
//       FileOutputStream os = new FileOutputStream(of);
//       int b;
//       while ((b = is.read()) != -1)
//           os.write(b);
//       os.close();
//       return of;
//   }
   


    /*
     * Traitement du document HTML
     * 
     * On crée une table de hachage dont les clés sont les 3 premiers mots de
     * chaque élément HTML de texte et les valeurs le nom de la police archaïque
     * utilisée pour l'élément de texte.
     */

public void traiterDocPourTranslit() {
    NodeImpl [] nodes = document.getChildrenArray();
    traiterDocPourTranslitNodes(nodes);
}

private void traiterDocPourTranslitNodes(NodeImpl [] nodes) {
    for (int i=0; i<nodes.length; i++) {
        NodeImpl node = nodes[i];
        if (node.getNodeType()==NodeImpl.TEXT_NODE) {
            traiterDocPourTranslitTextNode(node);
        } else if (node.getChildrenArray() != null) {
            traiterDocPourTranslitNodes(node.getChildrenArray());
        }
    }
}

private static Pattern pfw2 = Pattern.compile("(\\S+)(\\s+(\\S+))?(\\s+(\\S+))?");

private void traiterDocPourTranslitTextNode(NodeImpl node) {
    String text;
    Node parentNode = node.getParentNode();
    String parentNodeName = parentNode.getNodeName().toLowerCase();
    //--------------
    // TEXTE
    //--------------
    if (!parentNodeName.equals("style") &&
            !parentNodeName.equals("script") &&
            !parentNodeName.equals("#comment")
    ) {
        text = node.getNodeValue();
        String txttmp = HtmlEntities.entityToChar(text);
//        text = text.replaceAll("&dagger;", "\u2020");
        /*
         * Trouver la police spécifiée pour cet élément de texte. Si c'est une
         * police inuktitut archaïque telle que Nunacom ou Prosyl, enregistrer
         * les 3 premiers mots de cet élément avec le nom de la police.
         */
        String[] fontFamilies = getFonts(node);
        String font = Font.containsLegacyFont(fontFamilies);
        LOG.debug("font = "+font);
		LOG.debug("text = "+text);
		LOG.debug("txttmp = "+txttmp);
        if (font != null) {
            // pfw2: Un ou plusieurs caractères non-blancs
            Matcher mfw = pfw2.matcher(txttmp);
            if (mfw.find()) {
                String first3Words =  mfw.group(1).
                    concat(mfw.group(2)!=null?mfw.group(2):"").
                    concat(mfw.group(4)!=null?mfw.group(4):"");
                first3Words = first3Words.replaceAll("\\s+", " ");
                transHash.put(first3Words,font);
        		LOG.debug("first3Words = "+first3Words);
        		for (int i=0; i<first3Words.length(); i++)
        			LOG.debug("transHash: key("+i+")="+first3Words.codePointAt(i));
            }
        }
    }
}

File traiterDocPourTranslit2(HtmlDocu doc2, String enc) {
    for (int i = 0; i < doc2.elements.size(); i++) {
        HTMLDocuElement element = (HTMLDocuElement) doc2.elements.elementAt(i);
        
        // For each text element:
        if (element.getType() == HTMLDocuElement.BETWEENTAG) {
            String txt = null;
            try {
                /*
                 * Text elements in HTMLDocu contain the raw bytes from the file.
                 * HTML entities have to be converted to characters before attempting
                 * any match.
                 */
                txt = new String(element.getBytes(), enc);

                String txttmp = HtmlEntities.entityToChar(txt.replaceAll("\\s+"," "));
                // pfw2: One or more non-white-space characters
                // Get the first word: this has been recorded earlier as a
                // key to the font of every text element. 
                Matcher mfw = pfw2.matcher(txttmp);
                String key = null;
                if (mfw.find()) {
                    key = mfw.group(1)+(mfw.group(2)!=null?mfw.group(2):"")+(mfw.group(4)!=null?mfw.group(4):"");
                	key = key.replaceAll("\\s+", " ");
                }
                /*
                 * In the first pass where the URL was parsed with the HTML
                 * parser, for tag elements ending with '/>', somehow the
                 * element is recognized, but not '/>', such that '/' is
                 * considered as a text element, and '>' is added to the
                 * next text element.  This makes that the key in transHash
                 * starts with '>'.  We check here for that situation, and
                 * add '>' to the string we want to check in transHash.
                 */
                if (i != 0) {
                    HTMLDocuElement precElement = (HTMLDocuElement) doc2.elements
                            .elementAt(i - 1);
                    if (precElement.getType() == HTMLDocuElement.TAG
                            && ((Tag) precElement).text.endsWith("/>"))
                        key = ">" + key;
                }
                
                // The first word of the text element matches a recorded key
                if (key != null && transHash.containsKey(key)) {
                    int pos = 0;
                    String transText = "";
                    // Get the font of the text element
                    String font = (String) transHash.get(key);
                    /*
                     * The text is transliterated from the legacy font to
                     * the roman alphabet. HTML entities  are skipped over.
                     */
                    Matcher mh = HtmlEntities.pHtmlEntity.matcher(txt);
                    while (pos < txt.length() && mh.find(pos)) {
                        String htmlEntity = mh.group();
//                        char c = (char)HtmlEntities.et.entityCode(htmlEntity);
//                        if (Orthography.isWordChar(c,font)) {
//                            transText += 
//                                TransCoder.legacyToRoman(txt.substring(pos,mh.start())+c,font);
//                            pos = mh.end();
//                            if (txt.charAt(pos)==';') {
//                                pos++;
//                            }
//                        } 
//                        else {
                            transText += 
                                TransCoder.legacyToRoman(txt.substring(pos,mh.start()),font);
                            transText += htmlEntity;
                            pos = mh.end();
                            if (txt.charAt(pos)==';') {
                                transText += ';';
                                pos++;
                            }
//                        }
                    }
                    transText = transText + 
                        TransCoder.legacyToRoman(txt.substring(pos),font);
                    String fonttxt = "<FONT face=arial>" + transText
                            + "</FONT>";
                    byte bs[] = fonttxt.getBytes(enc);
                    element.setBytes(bs);
                } else {
                    String txtentity = HtmlEntities.toStringInuktitut(txt);
                    String transText = TransCoder
                            .unicodeToRoman(txtentity);
                    byte bs[] = transText.getBytes(enc);
                    element.setBytes(bs);
                }
                doc2.elements.setElementAt(element, i);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
    File fout = null;
    try {
        fout = File.createTempFile("translitOutput",".htm",tmpDir);
    } catch (IOException e1) {
    }
    try {
        OutputStream os = new FileOutputStream(fout);
        doc2.write(os);
        os.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
    }
    return fout;
}

File traiterDocPourTranslit3(HtmlDocu doc2, String enc, String translitMode) {
	boolean existsMetaHttpEquiv = false;
	String translitFont;
	lengthOfTotalContent = 0;
	lengthOfInuktitutContent = 0;
	if (translitMode.equals("utf-8"))
		translitFont = "pigiarniq";
	else
		translitFont = "arial";
//	boolean metaCharsetFoundAndSet = false;
    boolean inTitle = false;
    for (int i = 0; i < doc2.elements.size(); i++) {
        HTMLDocuElement element = (HTMLDocuElement) doc2.elements.elementAt(i);
        // For each text element:
        if (element.getType() == HTMLDocuElement.BETWEENTAG) {
            String txt = null;
            String font;
            if (!inTitle)
            try {
                /*
                 * Text elements in HTMLDocu contain the raw bytes from the file.
                 * HTML entities have to be converted to characters before attempting
                 * any match.
                 */
                txt = new String(element.getBytes(), enc);
                String txttmp = HtmlEntities.entityToChar(txt);
                // pfw2: One or more non-white-space characters
                // Get the first word: this has been recorded earlier as a
                // key to the font of every text element. 
                Matcher mfw = pfw2.matcher(txttmp);
                String key = null;
                if (mfw.find()) {
                    key = mfw.group(1)
                    	+(mfw.group(2)!=null?mfw.group(2):"")
                    	+(mfw.group(4)!=null?mfw.group(4):"");
                    key = key.replaceAll("\\s+", " ");
                }
                // The first word of the text element matches a recorded key
                if (key != null && transHash.containsKey(key)) {
                    String transText = "";
                    // Get the font of the text element
                    font = (String) transHash.get(key);
                    /*
                     * The text is transliterated from the legacy font to
                     * the roman alphabet.
                     */
                    String transPart;
					if (translitMode.equals("utf-8")) {
						transPart = TransCoder.legacyToUnicode(txttmp, font);
					} else {
						transPart = TransCoder.legacyToRoman(txttmp, font);
					}
					lengthOfInuktitutContent += transPart.replaceAll("\\s+", "").length();
					lengthOfTotalContent += transPart.replaceAll("\\s+", "").length();
					transText += transPart;
                    String fontAndTxt = "<span style=\"font-family:"
                    	+ translitFont
                    	+ "\">" 
                    	+ transText
                            + "</span>";
                    byte bs[] = fontAndTxt.getBytes(translitMode.equals("utf-8")?translitMode:enc);
                    element.setBytes(bs);
                } else {
                    String txtentity = HtmlEntities.toStringInuktitut(txt);
                    String txtWithConvertedEntities = HtmlEntities.fromHTMLEntity(txttmp);
                    if (Syllabics.containsInuktitut(txtWithConvertedEntities)) {
                        String txtWithConvertedEntitiesAnsNoSpaces = txtWithConvertedEntities.replaceAll("\\s+", "");
    					lengthOfInuktitutContent += txtWithConvertedEntitiesAnsNoSpaces.length();
    					lengthOfTotalContent += txtWithConvertedEntitiesAnsNoSpaces.length();
    					if (!translitMode.equals("utf-8")) {
    						txtentity = TransCoder.unicodeToRoman(txtWithConvertedEntities);
    					}
                    } else {
    					lengthOfTotalContent += txtWithConvertedEntities.replaceAll("\\s+", "").length();                    	
                    }
                    byte bs[] = txtentity.getBytes(translitMode.equals("utf-8")?translitMode:enc);
                    element.setBytes(bs);
                }
                doc2.elements.setElementAt(element, i);
            } catch (UnsupportedEncodingException e) {
            } catch (PatternSyntaxException e) {
            	e.printStackTrace(System.err);
            }
            inTitle = false;
        } else if ( translitMode.equals("utf-8") && ((Tag)element).tag.toLowerCase().equals("meta") ) {
        	try {
        	// Look for the META element and change its charset to UTF-8
        	String t = element.getText();
        	Pattern pct = Pattern.compile(httpContentTypePatternString,Pattern.CASE_INSENSITIVE);
        	Matcher mct = pct.matcher(t);
        	if (mct.find()) {
					Matcher m = charsetPattern.matcher(t);
					String new_t = null;
					if (m.find()) {
						new_t = t.replace(m.group(), "charset=utf-8");
					}
					else {
						Matcher mc = contentPattern.matcher(t);
						new_t = t;
						if (mc.find()) {
							String group1 = mc.group(1);
							new_t = t.replace(group1, group1+"; charset=utf-8");
						}
					}
					element.setBytes(new_t.getBytes());
					try {
						element.setText("iso-8859-1");
					} catch (UnsupportedEncodingException e) {
					}
					existsMetaHttpEquiv = true;
        	}
        	} catch (PatternSyntaxException e) {
        		e.printStackTrace(System.err);
        	}
        } else {
        	String tagElementName = ((Tag)element).tag.toLowerCase();
        	if (tagElementName.equals("title"))
        		inTitle = true;
        }
    }
    if ( translitMode.equals("utf-8") && !existsMetaHttpEquiv )
    	doc2.insertMetaContentType("utf-8");
    File fout = null;
    try {
        fout = File.createTempFile("translitOutput",".htm",tmpDir);
    } catch (IOException e1) {
    }
    try {
        OutputStream os = new FileOutputStream(fout);
        doc2.write(os);
        os.close();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }
    return fout;
}
File traiterDocPourTranslit3_2(HtmlDocu doc2, String enc, String translitMode, boolean aipaitai,
		String fontName) {
	boolean existsMetaHttpEquiv = false;
	String translitFont;
	lengthOfTotalContent = 0;
	lengthOfInuktitutContent = 0;
	if (translitMode.equals("utf-8"))
		translitFont = fontName;
	else
		translitFont = "arial";
//	boolean metaCharsetFoundAndSet = false;
    boolean inTitle = false;
    for (int i = 0; i < doc2.elements.size(); i++) {
        HTMLDocuElement element = (HTMLDocuElement) doc2.elements.elementAt(i);
        // For each text element:
        if (element.getType() == HTMLDocuElement.BETWEENTAG) {
            String txt = null;
            String font;
            if (!inTitle)
            try {
                /*
                 * Text elements in HTMLDocu contain the raw bytes from the file.
                 * HTML entities have to be converted to characters before attempting
                 * any match.
                 */
                txt = new String(element.getBytes(), enc);
                String txttmp = HtmlEntities.entityToChar(txt);
                // pfw2: One or more non-white-space characters
                // Get the first word: this has been recorded earlier as a
                // key to the font of every text element. 
                Matcher mfw = pfw2.matcher(txttmp);
                String key = null;
                if (mfw.find()) {
                    key = mfw.group(1)
                    	+(mfw.group(2)!=null?mfw.group(2):"")
                    	+(mfw.group(4)!=null?mfw.group(4):"");
                    key = key.replaceAll("\\s+", " ");
                }
                // The first word of the text element matches a recorded key
                if (key != null && transHash.containsKey(key)) {
                    String transText = "";
                    // Get the font of the text element
                    font = (String) transHash.get(key);
                    /*
                     * The text is transliterated from the legacy font to
                     * the roman alphabet.
                     */
                    String transPart;
					if (translitMode.equals("utf-8")) {
						transPart = TransCoder.legacyToUnicode(txttmp, font, aipaitai);
					} else {
						transPart = TransCoder.legacyToRoman(txttmp, font);
					}
					lengthOfInuktitutContent += transPart.replaceAll("\\s+", "").length();
					lengthOfTotalContent += transPart.replaceAll("\\s+", "").length();
					transText += transPart;
                    String fontAndTxt = "<span style=\"font-family:"
                    	+ translitFont
                    	+ "\">" 
                    	+ transText
                            + "</span>";
                    byte bs[] = fontAndTxt.getBytes(translitMode.equals("utf-8")?translitMode:enc);
                    element.setBytes(bs);
                } else {
                    String txtentity = HtmlEntities.toStringInuktitut(txt);
                    String txtWithConvertedEntities = HtmlEntities.fromHTMLEntity(txttmp);
                    if (Syllabics.containsInuktitut(txtWithConvertedEntities)) {
                        String txtWithConvertedEntitiesAnsNoSpaces = txtWithConvertedEntities.replaceAll("\\s+", "");
    					lengthOfInuktitutContent += txtWithConvertedEntitiesAnsNoSpaces.length();
    					lengthOfTotalContent += txtWithConvertedEntitiesAnsNoSpaces.length();
    					if (!translitMode.equals("utf-8")) {
    						txtentity = TransCoder.unicodeToRoman(txtWithConvertedEntities);
    					}
                    } else {
    					lengthOfTotalContent += txtWithConvertedEntities.replaceAll("\\s+", "").length();                    	
                    }
                    byte bs[] = txtentity.getBytes(translitMode.equals("utf-8")?translitMode:enc);
                    element.setBytes(bs);
                }
                doc2.elements.setElementAt(element, i);
            } catch (UnsupportedEncodingException e) {
            } catch (PatternSyntaxException e) {
            	e.printStackTrace(System.err);
            }
            inTitle = false;
        } else if ( translitMode.equals("utf-8") && ((Tag)element).tag.toLowerCase().equals("meta") ) {
        	try {
        	// Look for the META element and change its charset to UTF-8
        	String t = element.getText();
        	Pattern pct = Pattern.compile(httpContentTypePatternString,Pattern.CASE_INSENSITIVE);
        	Matcher mct = pct.matcher(t);
        	if (mct.find()) {
					Matcher m = charsetPattern.matcher(t);
					String new_t = null;
					if (m.find()) {
						new_t = t.replace(m.group(), "charset=utf-8");
					}
					else {
						Matcher mc = contentPattern.matcher(t);
						new_t = t;
						if (mc.find()) {
							String group1 = mc.group(1);
							new_t = t.replace(group1, group1+"; charset=utf-8");
						}
					}
					element.setBytes(new_t.getBytes());
					try {
						element.setText("iso-8859-1");
					} catch (UnsupportedEncodingException e) {
					}
					existsMetaHttpEquiv = true;
        	}
        	} catch (PatternSyntaxException e) {
        		e.printStackTrace(System.err);
        	}
        } else {
        	String tagElementName = ((Tag)element).tag.toLowerCase();
        	if (tagElementName.equals("title"))
        		inTitle = true;
        }
    }
    if ( translitMode.equals("utf-8") && !existsMetaHttpEquiv )
    	doc2.insertMetaContentType("utf-8");
    File fout = null;
    try {
        fout = File.createTempFile("translitOutput",".htm",tmpDir);
    } catch (IOException e1) {
    }
    try {
        OutputStream os = new FileOutputStream(fout);
        doc2.write(os);
        os.close();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }
    return fout;
}

File traiterDocPourTranslit3_3(HtmlDocu doc2, String enc, String translitMode, boolean aipaitai) {
	boolean existsMetaHttpEquiv = false;
	lengthOfTotalContent = 0;
	lengthOfInuktitutContent = 0;
//	boolean metaCharsetFoundAndSet = false;
    boolean inTitle = false;
    for (int i = 0; i < doc2.elements.size(); i++) {
        HTMLDocuElement element = (HTMLDocuElement) doc2.elements.elementAt(i);
        // For each text element:
        if (element.getType() == HTMLDocuElement.BETWEENTAG) {
            String txt = null;
            String font;
            if (!inTitle)
            try {
                /*
                 * Text elements in HTMLDocu contain the raw bytes from the file.
                 * HTML entities have to be converted to characters before attempting
                 * any match.
                 */
                txt = new String(element.getBytes(), enc);
                LOG.debug("traiterDocPourTranslit3_3: txt="+txt);
                String txttmp = HtmlEntities.entityToChar(txt);
                LOG.debug("traiterDocPourTranslit3_3: txttmp="+txttmp);
                // pfw2: One or more non-white-space characters
                // Get the first word: this has been recorded earlier as a
                // key to the font of every text element. 
                Matcher mfw = pfw2.matcher(txttmp);
                String key = null;
                if (mfw.find()) {
                    key = mfw.group(1)
                    	+(mfw.group(2)!=null?mfw.group(2):"")
                    	+(mfw.group(4)!=null?mfw.group(4):"");
                    key = key.replaceAll("\\s+", " ");
                }
                LOG.debug("traiterDocPourTranslit3_3: key="+key);
                // The first word of the text element matches a recorded key
                if (key != null) {
                for (int ik=0; ik<key.length(); ik++)
                	LOG.debug("key("+ik+")="+key.codePointAt(ik));
                for (Enumeration eth=transHash.keys(); eth.hasMoreElements();) {
                	String th = (String)eth.nextElement();
                    for (int ith=0; ith<th.length(); ith++)
                    	LOG.debug("th("+ith+")="+th.codePointAt(ith));
                }
                }
                if (key != null && transHash.containsKey(key)) {
                    // Get the font of the text element
                    font = (String) transHash.get(key);
                    /*
                     * The text is transliterated from the legacy font to
                     * the roman alphabet.
                     */
                    String transPart;
					if (translitMode.equals("utf-8")) {
						LOG.debug("traiterDocPourTranslit3_3: txttmp="+txttmp+" ; font="+font);
						transPart = TransCoder.legacyToUnicode(txttmp, font, aipaitai);
					} else {
						transPart = TransCoder.legacyToRoman(txttmp, font);
					}
					LOG.debug("traiterDocPourTranslit3_3: transPart="+transPart);
					lengthOfInuktitutContent += transPart.replaceAll("\\s+", "").length();
					lengthOfTotalContent += transPart.replaceAll("\\s+", "").length();
                    String fontAndTxt = "<span class=\"tcihtml\">"
                    	+ transPart
                            + "</span>";
                    byte bs[] = fontAndTxt.getBytes(translitMode.equals("utf-8")?translitMode:enc);
                    element.setBytes(bs);
                } else {
                    String txtentity = HtmlEntities.toStringInuktitut(txt);
                    String txtWithConvertedEntities = HtmlEntities.fromHTMLEntity(txttmp);
                    if (Syllabics.containsInuktitut(txtWithConvertedEntities)) {
                        String txtWithConvertedEntitiesAnsNoSpaces = txtWithConvertedEntities.replaceAll("\\s+", "");
    					lengthOfInuktitutContent += txtWithConvertedEntitiesAnsNoSpaces.length();
    					lengthOfTotalContent += txtWithConvertedEntitiesAnsNoSpaces.length();
    					if (!translitMode.equals("utf-8")) {
    						txtentity = TransCoder.unicodeToRoman(txtWithConvertedEntities);
    					}
                    } else {
    					lengthOfTotalContent += txtWithConvertedEntities.replaceAll("\\s+", "").length();                    	
                    }
                    byte bs[] = txtentity.getBytes(translitMode.equals("utf-8")?translitMode:enc);
                    element.setBytes(bs);
                }
                doc2.elements.setElementAt(element, i);
            } catch (UnsupportedEncodingException e) {
            } catch (PatternSyntaxException e) {
            	e.printStackTrace(System.err);
            }
            inTitle = false;
        } else if ( translitMode.equals("utf-8") && ((Tag)element).tag.toLowerCase().equals("meta") ) {
        	try {
        	// Look for the META element and change its charset to UTF-8
        	String t = element.getText();
        	Pattern pct = Pattern.compile(httpContentTypePatternString,Pattern.CASE_INSENSITIVE);
        	Matcher mct = pct.matcher(t);
        	if (mct.find()) {
					Matcher m = charsetPattern.matcher(t);
					String new_t = null;
					if (m.find()) {
						new_t = t.replace(m.group(), "charset=utf-8");
					}
					else {
						Matcher mc = contentPattern.matcher(t);
						new_t = t;
						if (mc.find()) {
							String group1 = mc.group(1);
							new_t = t.replace(group1, group1+"; charset=utf-8");
						}
					}
					element.setBytes(new_t.getBytes());
					try {
						element.setText("iso-8859-1");
					} catch (UnsupportedEncodingException e) {
					}
					existsMetaHttpEquiv = true;
        	}
        	} catch (PatternSyntaxException e) {
        		e.printStackTrace(System.err);
        	}
        } else {
        	String tagElementName = ((Tag)element).tag.toLowerCase();
        	if (tagElementName.equals("title"))
        		inTitle = true;
        }
    }
    if ( translitMode.equals("utf-8") && !existsMetaHttpEquiv )
    	doc2.insertMetaContentType("utf-8");
    File fout = null;
    try {
        fout = File.createTempFile("translitOutput",".htm",tmpDir);
    } catch (IOException e1) {
    }
    try {
        OutputStream os = new FileOutputStream(fout);
        doc2.write(os);
        os.close();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }
    return fout;
}
	String traiterDocPourToUnicode(HtmlDocu doc2, String enc) {
		boolean metaCharsetFoundAndSet = false;
		for (int i = 0; i < doc2.elements.size(); i++) {
			HTMLDocuElement element = (HTMLDocuElement) doc2.elements
					.elementAt(i);

			// For each text element:
			if (element.getType() == HTMLDocuElement.BETWEENTAG) {
				String txt = null;
				try {
					/*
					 * Text elements in HTMLDocu contain the raw bytes from the
					 * file. HTML entities have to be converted to characters
					 * before attempting any match.
					 */
					txt = new String(element.getBytes(), enc);

					String txttmp = HtmlEntities.entityToChar(txt.replaceAll(
							"\\s+", " "));
					// pfw2: One or more non-white-space characters
					// Get the first word: this has been recorded earlier as a
					// key to the font of every text element.
					Matcher mfw = pfw2.matcher(txttmp);
					String key = null;
					if (mfw.find()) {
						key = mfw.group(1)
								+ (mfw.group(2) != null ? mfw.group(2) : "")
								+ (mfw.group(4) != null ? mfw.group(4) : "");
						key = key.replaceAll("\\s+", " ");
					}
					/*
					 * In the first pass where the URL was parsed with the HTML
					 * parser, for tag elements ending with '/>', somehow the
					 * element is recognized, but not '/>', such that '/' is
					 * considered as a text element, and '>' is added to the
					 * next text element. This makes that the key in transHash
					 * starts with '>'. We check here for that situation, and
					 * add '>' to the string we want to check in transHash.
					 */
					if (i != 0) {
						HTMLDocuElement precElement = (HTMLDocuElement) doc2.elements
								.elementAt(i - 1);
						if (precElement.getType() == HTMLDocuElement.TAG
								&& ((Tag) precElement).text.endsWith("/>"))
							key = ">" + key;
					}

					// The beginning of the text element matches a recorded key:
					// inuktitut in a legacy font
					if (key != null && transHash.containsKey(key)) {
						int pos = 0;
						String transText = "";
						// Get the font of the text element
						String font = (String) transHash.get(key);
						/*
						 * The text is transliterated from the legacy font to
						 * Unicode. HTML entities are skipped over.
						 */
						Matcher mh = HtmlEntities.pHtmlEntity.matcher(txt);
						while (pos < txt.length() && mh.find(pos)) {
							String htmlEntity = mh.group();
							transText += TransCoder.legacyToUnicode(txt
									.substring(pos, mh.start()), font);
							transText += htmlEntity;
							pos = mh.end();
							if (txt.charAt(pos) == ';') {
								transText += ';';
								pos++;
							}
							// }
						}
						transText = transText
								+ TransCoder.legacyToUnicode(
										txt.substring(pos), font);
						String fonttxt = "<span style=\"font-family:pigiarniq\">"
								+ transText + "</span>";
						element.setText(fonttxt, "utf-8");
						doc2.elements.setElementAt(element, i);
					} else {
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (PatternSyntaxException e) {
					e.printStackTrace(System.err);
				}
			} else if (((Tag) element).tag.equals("meta")) {
				try {
				String text = ((Tag) element).text;
				Pattern phe = Pattern
						.compile("http-equiv\\s*=\\s*[\"\']?content-type[\"\']?");
				Matcher mphe = phe.matcher(text.toLowerCase());
				if (mphe.find()) {
					metaCharsetFoundAndSet = true;
					Pattern pcs = Pattern
							.compile("content\\s*=\\s*[\"\']?text/html\\s*;\\s*charset\\s*=\\s*[\"\']?([^\"\'>]+)[\"\']?");
					Matcher mpcs = pcs.matcher(text.toLowerCase());
					if (mpcs.find()) {
						text = text.replaceFirst(
								"charset\\s*=\\s*[\"\']?([^\"\'>]+)",
								"charset=\"utf-8\"");
						((Tag) element).text = text;
					} else {
						((Tag) element).text = "<meta http-equiv=\"content-type\"; charset=\"utf-8\">";
					}
				}
				} catch (PatternSyntaxException e) {
					e.printStackTrace(System.err);
				}
			} else if (((Tag) element).tag.equals("/head")
					&& !metaCharsetFoundAndSet) {
				try {
					doc2.elements.add(i - 1, new BetweenTag("\n", null));
					doc2.elements
							.add(
									i,
									new Tag(
											"<meta http-equiv=\"content-type\"; charset=\"utf-8\">",
											null));
					i = i + 2;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return doc2.print();
	}


   /*
	 * ----------- PROTECTED ----------------------------
	 */
   public static String [] getFonts (NodeImpl node) {
	   String [] fonts = getFontsA(node);
//	   if (fonts == null)
//		   // try with ngrams
//		   fonts = getFontsB(node);
	   return fonts;
   }
   
   static String[] getFontsA(NodeImpl node) {
		if (node == null) {
			return null;
		}
		if (node.getNodeType() == NodeImpl.TEXT_NODE)
			return getFontsA((NodeImpl) node.getParentNode());
		String fontFamiliesFromProperties = null;
		if (node.getNodeName().toLowerCase().equals("font")) {
			Node faceNode = node.getAttributes().getNamedItem("face");
			if (faceNode != null)
				fontFamiliesFromProperties = faceNode.getNodeValue();
		}
		if (fontFamiliesFromProperties == null && node != null
				&& node instanceof HTMLElementImpl) {
			CSS2PropertiesImpl properties = ((HTMLElementImpl) node)
//			AbstractCSS2Properties properties = ((HTMLElementImpl) node)
					.getCurrentStyle();
			if (properties != null)
				fontFamiliesFromProperties = properties.getFontFamily();
		}
		LOG3.debug("CSS properties= '"+fontFamiliesFromProperties+"'");
		if (fontFamiliesFromProperties == null)
			return getFontsA((NodeImpl) node.getParentNode());
		String[] fontFamilies = fontFamiliesFromProperties.split(",\\s*");
		return fontFamilies;
	}
   
   static String[] getFontsB(NodeImpl node) {
	   String text = node.getTextContent();
	   String font = TextCat.classify(text);
	   if (font==null || font.equals(""))
		   return null;
	   else
		   return new String [] { font.replace("inuktitut_", "") };
   }
   
   

   public static void main (String [] args) {
//	   System.setProperty("java.util.logging.config.file", "lib\\java.logging.properties");
		NRC_HTMLDocumentByCobra doc = null;
		if (args[0].equals("-f")) {
			try {
				doc = new NRC_HTMLDocumentByCobra(args[1], args[2], args[3]);
				doc.toUnicode(System.out);
			} catch (Exception e) {
				System.err.println("Problem with URL '"+args[1]+"' ("+e.getClass().getName()+")");
				System.out.print("--- ERROR from NRC_HTMLDocumentByCobra: "+e.getMessage());
			}
		}
		else if (args[0].equals("-u")) {
			try {
				doc = new NRC_HTMLDocumentByCobra(args[1]);
				try {
					doc.toUnicode(System.out);
				} catch (Exception e) {
					System.err.println("Problem in toUnicode with URL '"+args[1]+"' ("+e.getClass().getName()+")");
					System.out.print("--- ERROR from toUnicode in NRC_HTMLDocumentByCobra: "+e.getMessage());
				}
			} catch (Exception e) {
				System.err.println("Problem with creating HTML document with URL '"+args[1]+"' ("+e.getClass().getName()+": "+e.getMessage()+")");
				System.out.print("--- ERROR from NRC_HTMLDocumentByCobra: "+e.getMessage());
			}
		}
		else if (args[0].equals("-content")) {
			try {
				doc = new NRC_HTMLDocumentByCobra(args[1]);
				PrintStream out = new PrintStream(System.out,true,"utf-8");
				for (int i=0; i<doc.document.getChildNodes().getLength(); i++) {
					Node node = doc.document.getChildNodes().item(i);
					out.println(node.getTextContent());
				}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.print("");
				}
		}
		else if (args[0].equals("-nodes")) {
			try {
				doc = new NRC_HTMLDocumentByCobra(args[1], args[2], args[3]);
				doc.displayNodes();
			} catch (Exception e) {
				LOG.info("--NRC_HTMLDocumentByCobra--- Exception: "+e.getMessage());
			}
		} else if (args[0].equals("-a")) {
			// Anchors
			try {
				doc = new NRC_HTMLDocumentByCobra(args[1]);
				HTMLCollection anchors = doc.document.getAnchors();
				for (int i=0; i<anchors.getLength(); i++) {
					Node anchor = anchors.item(i);
					NamedNodeMap attrs = anchor.getAttributes();
					Node href = attrs.getNamedItem("href");
					if (href != null)
						System.out.println(href.getNodeValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
   }
   
   public String[][] getTextElementsWithFont() {
	   LOG2.debug("entrée");
	    NodeImpl [] nodes = document.getChildrenArray();
	    Vector v = getTextElementsWithFont_Nodes(nodes, new Vector());
	    return (String[][])v.toArray(new String[][]{});
	}

	private Vector getTextElementsWithFont_Nodes(NodeImpl [] nodes, Vector textFontObjects) {
		Vector v = new Vector();
	    for (int i=0; i<nodes.length; i++) {
	        NodeImpl node = nodes[i];
	        if (node.getNodeType()==NodeImpl.TEXT_NODE) {
	        	String[] textFont = getTextElementsWithFont_TextNode(node);
	        	if (textFont != null)
	        		v.add(textFont);
	        } else if (node.getChildrenArray() != null) {
	        	v = getTextElementsWithFont_Nodes(node.getChildrenArray(), v);
	        }
	    }
	    v.addAll(textFontObjects);
	    return v;
	}

	private String[] getTextElementsWithFont_TextNode(NodeImpl node) {
		String[] textFont = null;
	    String text;
	    Node parentNode = node.getParentNode();
	    String parentNodeName = parentNode.getNodeName().toLowerCase();
	    //--------------
	    // TEXTE
	    //--------------
	    if (!parentNodeName.equals("style") &&
	            !parentNodeName.equals("script") &&
	            !parentNodeName.equals("#comment")
	    ) {
	        text = node.getNodeValue();
	        text = text.replaceAll("(\\s|\\u00a0)+", " ");
	        if (text.equals(" ")) text = "";
	        text = HtmlEntities.entityToChar(text);
	        /*
	         * Trouver la police spécifiée pour cet élément de texte.
	         */
	        String[] fontFamilies = getFonts(node);
	        if (fontFamilies != null) LOG1.debug("\n: text='"+text+"' ["+Util.array2string(fontFamilies)+"]");
	        String font = Font.containsLegacyFont(fontFamilies);
	        if (font != null && !text.equals("")) {
	        	textFont = new String[] { text, font };
		        LOG1.debug("\nKEEP: text='"+text+"' ["+font+"]");
	        } else if (font == null && !text.equals("")) {
	        	if ( Syllabics.containsInuktitut(text) ) {
	        		textFont = new String[] { text, null };
			        LOG1.debug("\nKEEP: text='"+text+"' []");
	        	}
	        }
	    }
	    return textFont;
	}

   
}
	

class MySimpleUserAgentContext extends SimpleUserAgentContext {
	
	boolean scriptingEnabled;
    private static Logger logger = Logger.getLogger(MySimpleUserAgentContext.class.getName());

	public MySimpleUserAgentContext() {
		scriptingEnabled = true;
	}
	
	public MySimpleUserAgentContext(boolean b) {
		logger.debug("scriptingEnabled set to='"+b+"'");
		scriptingEnabled = b;
	}

	public boolean isScriptingEnabled() {
		logger.debug("scriptingEnabled='"+scriptingEnabled+"'");
		return scriptingEnabled;
	}

}

class MySimpleHtmlRendererContext extends SimpleHtmlRendererContext {
	
	UserAgentContext userAgentContext;
	
	public MySimpleHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext uac) {
		super(contextComponent);
		userAgentContext = uac;
	}

	public boolean isMedia(String med) {
//		if (med.toLowerCase().equals("screen"))
//			return true;
		return true;
	}
	
	public UserAgentContext getUserAgentContext() {
		return userAgentContext;
	}
}
