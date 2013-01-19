/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Jun 26, 2006
 * par / by Benoit Farley
 * 
 */

/*
 * Parsing Microsoft Word documents.
 * Two tools can be used: Jakarta POI and the TextMining.org text extractors.
 * API.
 */
package documents;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;

import fonts.Font;

import script.Syllabics;
import script.TransCoder;


public class NRC_DOCDocument implements NRC_Document {

    public HWPFDocument document = null;

    private String urlName = null;
    private String contentType = null;
    private Date date = null;
    private String title = null;
    private String preferredFont = null;
    private Hashtable fonts = null;
    private String pageContent = null;
    private WebPageReader wpr = null;
    private boolean containsInuktitut = false;
    private long lengthOfInuktitutContent = 0;
    private long lengthOfTotalContent = 0;

    public NRC_DOCDocument (String urlName) throws IOException {
        this.urlName = urlName;
        wpr = new WebPageReader(urlName);
        contentType = wpr.contentType;
        URLConnection conn = wpr.connection;
        date = new Date(conn.getLastModified());
        InputStream fis = conn.getInputStream();
        document = new HWPFDocument(fis);
        title = document.getSummaryInformation().getTitle();
    }
    
    /*
     * Retourne le contenu d'un fichier DOC. Le texte inuktitut est converti �
     * Unicode. Si une exception survient, la cha�ne vide est retourn�e.
     */
    public String getPageContent() {
        if (pageContent != null)
            return pageContent;
        fonts = new Hashtable();
        String totalText = "";
        Range range = document.getRange();
        int nbRuns = range.numCharacterRuns();
        String font = "";
        String previousFont = "";
        String text = null;
        for (int i=0; i<nbRuns; i++) {
            CharacterRun cr = range.getCharacterRun(i);
            previousFont = font;
            font = cr.getFontName();
            String piece = null;
            try {
                piece = cr.text();
            } catch (Exception e) {
                // Sometimes, an ArrayOutOfBoundsException arises.
                piece = "";
            }
            if (font.equals(previousFont))
                /* The font for this piece of text is the same as for the
                 * preceeding piece of text. Just append this text 'text'.
                 */
                text += piece;
            else if (text==null)
                // Very first piece of text. Just initialize 'text' to it.
                text = piece;
            else {
                /*
                 * The font for this piece of text is different than for the
                 * preceeding piece of text. If that previous font is a known
                 * legacy or unicode inuktitut font, convert the text in unicode
                 * and add it to the total text. Otherwise, just add the text as
                 * is.
                 */
                if (text.replaceAll("\\s", "").equals("")) {
                   	;
                } else if (Font.isLegacy(previousFont)) {
                	String iutext = TransCoder.legacyToUnicode(text,previousFont);
                    totalText += " "+iutext;
                    containsInuktitut = true;
                    lengthOfInuktitutContent += iutext.length();
                    lengthOfTotalContent += iutext.length();
                }
                else if (Font.isUnicodeFont(previousFont)) {
                    totalText += " "+text;
                    containsInuktitut = true;
                    lengthOfInuktitutContent += text.length();
                    lengthOfTotalContent += text.length();
                }
                else if (Syllabics.containsInuktitut(text)) {
                    totalText += " "+text;
                    containsInuktitut = true;
                    lengthOfInuktitutContent += text.length();
                    lengthOfTotalContent += text.length();
                }
                else {
                    totalText += " "+text;
                    lengthOfTotalContent += text.length();
                }
                // Re-init 'text' to the contents of the current piece of text
                text = piece;
            }
            if (font != null)
                if (!fonts.containsKey(font)) {
                    fonts.put(font,new Integer(1));
                } else {
                    fonts.put(font,new Integer(((Integer)fonts.get(font)).intValue()+1));
                }
        }
        if (Font.isLegacy(font))
            totalText += " "+TransCoder.legacyToUnicode(text,font);
        else if (Font.isUnicodeFont(font))
            totalText += " "+text;
        else if (Syllabics.containsInuktitut(text))
            totalText += " "+text;
        else
            totalText += " "+text;
        pageContent = totalText;
        return totalText;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrlName() {
        return urlName;
    }

    public String getTitle() {
        if (title==null) {
            String pc = getPageContent();
            Pattern p = Pattern.compile("(\\S+)");
            Matcher mp = p.matcher(pc);
            int pos = 0;
            int nwords = 5;
            title = "";
            while (mp.find(pos) && nwords>0) {
                title += mp.group()+" ";
                nwords--;
                pos = mp.end();
            }
            title += "...";
        }
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getPreferredFont() {
        if (preferredFont==null) {
            if (fonts==null)
                getPageContent();
            int max = 0;
            for (Enumeration e = fonts.keys(); e.hasMoreElements();) {
                Object f = e.nextElement();
                int n = ((Integer)fonts.get(f)).intValue();
                if (n>max) {
                    preferredFont = (String)f;
                    max = n;
                }
            }
        }
        return preferredFont;
    }

    public Object[] getAllFonts() {
        if (fonts==null)
            getPageContent();
        return (Object[])fonts.keySet().toArray();
    }

    public String[] getAllFontsNames() {
        Object [] fnts = getAllFonts();
        String[] fontNames = new String[fnts.length];
        for (int i=0; i<fnts.length; i++)
            fontNames[i] = (String)fnts[i]; 
        return fontNames;
    }

    public String[] getInuktitutFonts() {
        String[] allFonts = getAllFontsNames();
        Vector fs = new Vector();
        List inuktitutFonts = Arrays.asList(Font.fonts);
        for (int i=0; i<allFonts.length; i++) {
            String fnt = allFonts[i];
            if (inuktitutFonts.contains(fnt))
            	if (!fs.contains(fnt))
            		fs.add(fnt);
        }
        return (String[])fs.toArray(new String[]{});
    }
    

    public Object[] highlight(String[] x) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Object[] transliterate() throws OutOfMemoryError, Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public WebPageReader getWpr() {
        return wpr;
    }

    public void close() {
    }

    public boolean containsInuktitut() {
        if (pageContent==null)
            getPageContent();
        return containsInuktitut;
    }

    public float getInuktitutPercentage() {
    	if (containsInuktitut())
    		return (float)lengthOfInuktitutContent / (float)lengthOfTotalContent;
    	else
    		return 0;
    }

	public Object[][] getInuktitutFontsAndPercentages() {
		// TODO Auto-generated method stub
		return null;
	}


}
