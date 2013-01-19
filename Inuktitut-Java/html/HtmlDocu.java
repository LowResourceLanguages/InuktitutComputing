/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Feb 4, 2006
 * par / by Benoit Farley
 * 
 */
package html;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import documents.WebPageReader;


public class HtmlDocu {
    public String encoding = null;
    public String encodingSource = null;
    public Vector elements = null;
    public URL url = null;
    static Object[][] bom = {
        { new Byte[]{new Byte((byte) 0xEF), new Byte((byte) 0xBB), new Byte((byte) 0xBF)}, "UTF-8" },
        { new Byte[]{new Byte((byte) 0xFE), new Byte((byte) 0xFF)}, "UTF-16BE" },
        { new Byte[]{new Byte((byte) 0xFF), new Byte((byte) 0xFE)}, "UTF-16LE" }
    };
    
    public HtmlDocu(URL url) throws MalformedURLException, IOException {
        WebPageReader wpr = new WebPageReader(url.toExternalForm());
        this.url = wpr.url;
//      String wordFileName = Util.getArgument(args,"f");
//      String directoryName = Util.getArgument(args,"d");
//      File directory = new File(directoryName);
//      wordFile = new File(directory,wordFileName);
        String contentType = wpr.connection.getContentType().toLowerCase();
        if (contentType != null) {
        	try {
            Pattern p = Pattern.compile("text/html;\\s?charset=(.+)");
            Matcher mp = p.matcher(contentType);
            if (mp.find()) {
                encoding = mp.group(1);
                encodingSource = "http header";
            }
        	} catch (PatternSyntaxException e) {
        		e.printStackTrace(System.err);
        	}
        }
        InputStream stream = this.url.openStream();
        parse(stream);
        /*
         * Encoding may not have been specified, either from the HTTP header
         * or from the META element.  In that case, check if there is any
         * BOM character at the beginning of the file.  If nothing, consider
         * ISO-8859-1 as prescribed.
         */
//        if (encoding==null) {
        if (true) {
            HTMLDocuElement first = (HTMLDocuElement)elements.elementAt(0);
            if (first.getType()==HTMLDocuElement.BETWEENTAG) {
                byte bs[] = first.getBytes();
                for (int i=0; i<bom.length; i++) {
                    Byte bbom[] = (Byte[])bom[i][0];
                    if (bbom.length == bs.length) {
                        int j=0;
                        for (j=0; j<bbom.length; j++) {
                            if (bbom[j].byteValue() != bs[j])
                                break;
                        }
                        if (j==bbom.length) {
                            if (encoding==null) {
                                encoding = (String)bom[i][1];
                                encodingSource = "bom";
                            }
                            elements.remove(0);
                            break;
                        }
                    }
                }
            }
            if (encoding==null)
                encoding = "ISO-8859-1";
            // Set 'text' in each 'betweentag' element.
            for (int i=0; i<elements.size(); i++) {
                HTMLDocuElement elm = (HTMLDocuElement)elements.elementAt(i);
                if (elm.getType()==HTMLDocuElement.BETWEENTAG)
                    elm.setText(encoding);
            }
        }
        stream.close();
    }
    
    public HtmlDocu(File file, String encoding) throws IOException  {
        InputStream is = new FileInputStream(file);
        parse(is);
        // Set 'text' in each 'betweentag' element.
        for (int i=0; i<elements.size(); i++) {
            HTMLDocuElement elm = (HTMLDocuElement)elements.elementAt(i);
            if (elm.getType()==HTMLDocuElement.BETWEENTAG 
            		|| elm.getType()==HTMLDocuElement.TAG
            		)
                elm.setText(encoding);
        }
        this.encoding = encoding;
        is.close();
    }
    
    public HtmlDocu(InputStream is, String encoding) throws IOException {
        parse(is);
        // Set 'text' in each 'betweentag' element.
        for (int i=0; i<elements.size(); i++) {
            HTMLDocuElement elm = (HTMLDocuElement)elements.elementAt(i);
            if (elm.getType()==HTMLDocuElement.BETWEENTAG 
            		|| elm.getType()==HTMLDocuElement.TAG
            		)
                elm.setText(encoding);
        }
        this.encoding = encoding;
        is.close();
    }

    
    static boolean isHtmlSpace(int c) {
        switch (c) {
            case 0x0020:
            case 0x0009:
            case 0x000C:
            case 0x200B:
            case 0x000D:
            case 0x000A:
                return true;
            default:
                return false;
        }
    }
    
    /*
     * White space in HTML: ASCII space (&#x0020;) ASCII tab (&#x0009;) ASCII
     * form feed (&#x000C;) Zero-width space (&#x200B;) Line breaks: carriage
     * return (&#x000D;) line feed (&#x000A;) carriage return/line feed pair
     */
    void parse(InputStream is)  throws IOException {
        Pattern tagPattern = Pattern.compile("^<([^\\s>]+)");
        int c;
        boolean readingTag = false;
        boolean tagIsRead = false;
        boolean readingHtmlComment = false;
        DynamicArrayOfByte tagBs = new DynamicArrayOfByte(100);
        DynamicArrayOfByte txtBs = new DynamicArrayOfByte(100);
        elements = new Vector();
        while ((c=is.read()) != -1) {
              if ((byte)c=='>') {
                if (readingTag) {
                    if ((readingHtmlComment && tagBs.get(-1)=='-' && tagBs.get(-2)=='-') ||
                            !readingHtmlComment) {
                        // Fin d'un tag
                        tagBs.add((byte)c);
                        readingTag = false;
                        Tag tag = new Tag(tagBs);
                       if (encoding==null && tag.tag.equals("meta")) {
                            String enc = tag.getEncodingFromMetaTag();
                            if (enc != null)
                                encoding = enc;
                        }
                        tagBs = new DynamicArrayOfByte(100);
                        tagIsRead = false;
                        readingHtmlComment = false;
                        elements.add(tag);
                    } else {
                        tagBs.add((byte)c);
                    }
                } else {
                    txtBs.add((byte)c);
                }
            } else if (readingTag) {
                // Lire le nom du tag (jusqu'� l'espace)
                if (!tagIsRead && isHtmlSpace(c)) {
                	try {
                	String tagCandidate = new String(tagBs.getBytes());
                    Matcher mtag = tagPattern.matcher(tagCandidate);
                    mtag.find();
                    String tag = mtag.group(1);
                    if (tag.equals("!--"))
                        readingHtmlComment = true;
                    tagIsRead = true;
                	} catch (PatternSyntaxException e) {
                		e.printStackTrace(System.err);
                	}
                }
                tagBs.add((byte)c);
            } else if ((byte)c=='<' && !readingTag) {
            	int c1 = is.read();
            	if (c1 == -1)
            		break;
            	if (isHtmlSpace(c1))
            		txtBs.add((byte)c1);
            	else {
					// Début d'un tag
					readingTag = true;
					tagBs.add((byte) c);
					tagBs.add((byte) c1);
					if (txtBs.size() != 0) {
						elements.add(new BetweenTag(txtBs));
					}
					txtBs = new DynamicArrayOfByte(100);
				}
            } else
                txtBs.add((byte)c);
        }
    }
    
    public void write(OutputStream os) throws IOException {
        for (int i=0; i<elements.size(); i++) {
            HTMLDocuElement elm = (HTMLDocuElement)elements.elementAt(i);
            os.write(elm.getBytes());
        }
        os.flush();
    }
    
    public void write(OutputStream os, String encoding) throws IOException {
    	OutputStreamWriter osw = new OutputStreamWriter(os,encoding);
        for (int i=0; i<elements.size(); i++) {
            HTMLDocuElement elm = (HTMLDocuElement)elements.elementAt(i);
            String str = elm.getText();
            for (int j=0; j<str.length(); j++)
            	osw.write(str.charAt(j));
        }
        osw.flush();
    }
    
    public String print() {
    	StringBuffer res =new StringBuffer("");
        for (int i=0; i<elements.size(); i++) {
            HTMLDocuElement elm = (HTMLDocuElement)elements.elementAt(i);
            res.append(elm.getText());
        }
        return res.toString();
    }
    

    // Insert <base ...>
    public void insertBase(String urlName) {
        Tag base = new Tag(makeBaseTag(urlName), null);
        insertTagInHead(base);
    }
    
    public void insertBase(URL url) {
        Tag base = new Tag(makeBaseTag(url), null);
        insertTagInHead(base);
    }
    
    public void insertLinkCSS(String url) {
    	Tag link = new Tag("<link href=\""+url+"\" rel=\"stylesheet\" type=\"text/css\"","iso-8859-1");
    	insertTagInHead(link);
    }
    
    private void insertTagInHead(Tag newtag) {
        for (int i = 0; i < elements.size(); i++) {
            HTMLDocuElement elm = (HTMLDocuElement) elements.elementAt(i);
            if (elm.getType() == HTMLDocuElement.TAG) {
                // TAG: <...>
                String tag = ((Tag) elm).tag;
                if (tag.equals("html")) {
                } else if (tag.equals("head")) {
                    elements.add(i + 1, newtag);
                    break;
                } else if (Character.isLetter(tag.charAt(0))) {
                    // HEAD or BODY ELEMENT
                    elements.add(i, newtag);
                    break;
                }
            }
        }
    }
    
    static String makeBaseTag(URL url) {
        return "<base href=\""+url.toExternalForm()+"\">";
    }
    
    static String makeBaseTag(String urlName) {
        return "<base href=\""+urlName+"\">";
    }
    
    // Insert <meta ...>
    public void insertMetaContentType(String charset) {
        Tag meta = new Tag(makeMetaContentTypeTag(charset), null);
        insertTagInHead(meta);
    }
    
    
    static String makeMetaContentTypeTag(String charset) {
        return "<meta http-equiv=\"Content-Type\" content=\"text/html"
        + (charset==null? "\">" : "; charset="+charset+"\">");
    }
    

    
}




