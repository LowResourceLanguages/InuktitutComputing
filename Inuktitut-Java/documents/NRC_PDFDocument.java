/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Jun 22, 2006
 * par / by Benoit Farley
 * 
 */
package documents;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.cos.COSName;
import org.pdfbox.cos.COSObject;
import org.pdfbox.cos.COSString;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.font.PDFont;
//import org.pdfbox.util.PDFFonttedTextStripper;

import documents.pdfboxAddition.NRC_PDFHighlighter;


import script.Orthography;
import script.Syllabics;
import script.TransCoder;
import documents.pdfboxAddition.NRC_PDFFonttedTextStripper;
import fonts.Font;

//import fonts.ngrammes.Ngramme;
import utilities1.Util;


public class NRC_PDFDocument implements NRC_Document {
    private static Logger LOG = Logger.getLogger(NRC_PDFDocument.class);

    public COSDocument document = null;

    private String urlName = null;
    private String contentType = null;
    private Date date = null;
    private String title = null;
    private String preferredFont = null;
    private Hashtable fonts = null;
    private String pageContent = null;
    private String inuktitutContent = null;
    private String totalContent = null;
    private Hashtable inuktitutLegacy = null;
    private NRC_PDFFonttedTextStripper stripper;
    private WebPageReader wpr = null;
    private File copyFile = null;
    private boolean containsInuktitut = false;
    private long lengthOfInuktitutContent = 0;
    private long lengthOfTotalContent = 0;
    /*
     * PDFont font, String texte
     * ou
     * String inuktitutLegacyFontName, String texte
     */
    private Object[][] textElements = null;
    
    /*
     * Modèle des noms de police retournés par getBaseFont(), dont voici des
     * exemples: ArialMT, Arial-BoldMT, AMHBNE+ProSylBold
     */
    private static Pattern patternFontName = Pattern.compile("(.{6}\\x2B)?([^,]+)(,.+)?$");

    public NRC_PDFDocument (String fileName, String dummy) throws IOException {
    	FileInputStream fis = new FileInputStream(fileName);
    	make_object(fis);
    }
    
    public NRC_PDFDocument( String urlName ) throws MalformedURLException, IOException {
        this.urlName = urlName;
        URL url = new URL(urlName);
        String protocol = url.getProtocol().toLowerCase();
        URLConnection connection = null;
        wpr = new WebPageReader(urlName);
        contentType = wpr.contentType;
        connection = wpr.connection;
        date = new Date(connection.getLastModified());
        FileInputStream fis = null;
        if (protocol.matches("^file.*")) {
        	String fileName = url.getPath();
        	fis = new FileInputStream(fileName);
        }
        else {
            copyPDF(connection,urlName);
        	fis = new FileInputStream(copyFile);
        }
        make_object(fis);
    }
    
    private void make_object( FileInputStream fis) throws IOException {
        PDFParser parser = new PDFParser(fis);
        parser.parse();
        fis.close();
        document = parser.getDocument();
        stripper = new NRC_PDFFonttedTextStripper();
        setTextElements();
        List objs = document.getObjects();
        for (int i=0; i<objs.size(); i++) {
            COSObject obj = (COSObject)objs.get(i);
            COSString tit = (COSString) obj.getItem(COSName.getPDFName( "Title" ));
            if (tit!=null) {
                title = tit.getString();
                break;
            }
        }
    }
    
    public void close()  {
        try {
			document.close();
		} catch (IOException e) {
		}
    }
    
    private void copyPDF(URLConnection connection, String urlName) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            copyFile = File.createTempFile("pdfcopy",".txt",tmpDir);
            bis = new BufferedInputStream(connection.getInputStream());
            bos = new BufferedOutputStream(
                    new FileOutputStream(copyFile));
            byte[] buff = new byte[2048];
            int bytesRead; 
            // Simple read/write loop.
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            copyFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            copyFile = null;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }
    
    public Object[][] getTextElements() {
        return textElements;
    }
    
    /*
     * Retourne les éléments de texte avec leur police.
     */
    
    PrintStream out = new PrintStream(System.out,true,"utf-8");
    
    private void setTextElements() {
         try {
            Object [][]textElements =stripper.getText(document);
            for (int i=0; i<textElements.length; i++) {
                String textElement = (String)textElements[i][0];
                PDFont font = (PDFont)textElements[i][1];
                String textElementFont = null;
                if (font != null)
                    textElementFont = font.getBaseFont();
                if (textElementFont == null)
                    textElementFont = "";
                Matcher mp = patternFontName.matcher(textElementFont);
                if (mp.find()) {
                    textElementFont = mp.group(2).toLowerCase();
                    if (textElement.indexOf('\uF8FF') >= 0 && textElementFont.startsWith("naamajut"))
                        textElements[i][0] = textElement.replaceAll("\uF8FF","\uFFFD");
                }
            }
            this.textElements = textElements;
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    public String getInuktitutContent() {
        if (inuktitutContent != null)
            return inuktitutContent;
        getPageContent();
        return inuktitutContent;
    }
    
    /*
     * Retourne le contenu textuel d'un fichier PDF. Le texte inuktitut est
     * converti en Unicode. Si une exception survient (IOException par
     * PDFFonttedTextStripper() et getText()), la chaîne vide est retournée.
     */
    public String getPageContent() {
        if (pageContent != null) {
            return pageContent;
        }
        /*
         * Si aucune police inuktitut archaïque n'a été reconnue dans le
         * fichier, on va relire les éléments de texte et tenter, par la
         * méthode des ngrammes, de déterminer si le texte dans une police
         * non-inuktitut est en inuktitut.
         */
//        if (containsNonLegacyInuktitutFonts()) {
//            pageContent = null;
//            fonts = null;
//            identifyFonts();
//        }
//        System.out.println("entering getPageContent");
        fonts = new Hashtable();
        inuktitutLegacy = new Hashtable();
        StringBuffer totalText = new StringBuffer("");
        StringBuffer inuktitutText = new StringBuffer("");
        StringBuffer content = new StringBuffer("");
        String textElementFont = null;
        
        // Pour chaque élément de texte:
        if (textElements != null)
        for (int i = 0; i < textElements.length; i++) {
//        	System.out.println(i+" de "+textElements.length);
            String f = null;
            String textElement = (String)textElements[i][0];
            textElementFont = null;
            String unicode = textElement;
            if (textElement.replaceAll("\\s", "").equals("")) {
               	content.append(textElement);
            } 
            // Vérifier si le texte contient du syllabique inuktitut.
            // Ajouter au contenu.
            else if (Syllabics.containsInuktitut(textElement)) {
                totalText.append(" ").append(textElement);
                inuktitutText.append(" ").append(textElement);
               	content.append(" ").append(textElement);
                containsInuktitut = true;
            } // Ajouter au contenu.
            else {
					Object font = textElements[i][1];
					if (font != null)
						if (font instanceof PDFont)
							textElementFont = ((PDFont) font).getBaseFont();
						else
							textElementFont = (String) font;
					if (textElementFont == null)
						textElementFont = "";
					Matcher mp = patternFontName.matcher(textElementFont);
					try {
						if (mp.find())
							textElementFont = mp.group(2).toLowerCase();
					} catch (Exception e) {
						textElementFont = "";
					}
		            // Vérifier s'il s'agit d'une police inuktitut archaïque connue.
		            // Transcoder à Unicode et ajouter au contenu.
					if ((f = NRC_PDFFonttedTextStripper.isContainedFont(
							textElementFont, Font.fonts)) != null) {
						unicode = TransCoder.legacyToUnicode(textElement, f);
						Object n = inuktitutLegacy.get(f);
						int ni = 0;
						if (n != null)
							ni = ((Integer)n).intValue();
						ni += unicode.replaceAll("\\s", "").length();
						inuktitutLegacy.put(f, new Integer(ni));
						totalText.append(" ").append(unicode);
						inuktitutText.append(" ").append(unicode);
						content.append(" ").append(unicode);
						containsInuktitut = true;
					}
					// Vérifier s'il s'agit d'une police inuktitut unicode
					// connue.
					// Ajouter au contenu.
//					else if ((f = NRC_PDFFonttedTextStripper.isContainedFont(
//							textElementFont, Police.policesUnicode)) != null) {
//						totalText += " " + textElement;
//						inuktitutText += " " + textElement;
//						content += " " + textElement;
//						containsInuktitut = true;
//					} 
					else {
						totalText.append(" ").append(textElement);
						content.append(" ").append(textElement);
					}
				}
            /*
			 * Très souvent, les nouvelles lignes et les espaces sont exprimées
			 * avec une police standard différente de la police utilisée pour le
			 * texte. On ne comptabilise pas ce texte pour la détermination des
			 * polices.
			 */
            String textElementWithoutBlanks = textElement.replaceAll("\\s","");
            if (textElementFont != null && !textElementWithoutBlanks.equals(""))
                if (!fonts.containsKey(textElementFont)) {
                    fonts.put(textElementFont,new Integer(textElement.length()));
                } else {
                    fonts.put(textElementFont,new Integer(((Integer)fonts.get(textElementFont)).intValue()+textElement.length()));
                }
        }
        // Enlever les traits-d'union qui coupent des mots en fin de ligne.
        String scontent = removeHyphens(content.toString());
        // Enlever les blancs multiples et les remplacer par un simple espace.
        scontent = scontent.replaceAll("\\s{2,}", " ");
        pageContent = scontent;
        inuktitutContent = inuktitutText.toString();
        totalContent = totalText.toString();
        lengthOfInuktitutContent = inuktitutContent.length();
        lengthOfTotalContent = totalText.length();
//        System.out.println("leaving getPageContent");
        return totalText.toString();
    }
    
    static String removeHyphens(String s) {
        Pattern p = Pattern.compile("(\\S)[-]+\r\n");
        Matcher mp = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        int pos = 0;
        while (pos < s.length() && mp.find(pos)) {
            sb.append(s.substring(pos,mp.start()));
            sb.append(mp.group(1));
            pos = mp.end();
        }
        sb.append(s.substring(pos));
        return sb.toString();
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
    
//    public Object[] getAllFonts() {
//        HashSet fonts = new HashSet();
//        List pages = (new PDDocument((COSDocument)document)).getDocumentCatalog().getAllPages();
//        Iterator iter = pages.iterator();
//        while (iter.hasNext()) {
//            PDPage page = (PDPage)iter.next();
//            Map fontsMap = null;
//            try {
//                PDResources res = page.findResources();
//                fontsMap = res.getFonts();
//            } catch (IOException e) {
//            }
//            if (fontsMap != null) {
//                Collection fontsCollection = fontsMap.values();
//                fonts.addAll(fontsCollection);
//            }
//        }
//        return (PDFont[])fonts.toArray(new PDFont[]{});
//    }

    public Object[] getAllFonts() {
        return stripper.getAllFonts();
    }
    
    
//    public String[] getAllFontsNames() {
//        Object [] fonts = getAllFonts();
//        if (fonts != null) {
//            Set fontsSet = new HashSet();
//            for (int i=0; i<fonts.length; i++) {
//                PDFont font = (PDFont)fonts[i];
//                String fontName;
//                fontName = font.getBaseFont();
//                if (fontName==null)
//                    fontName = font.getName();
//                String realFontName;
//                Matcher mp = patternFontName.matcher(fontName);
//                if (mp.find()) {
//                    realFontName = mp.group(2);
//                }
//                else {
//                    realFontName = fontName;
//                }
//                fontsSet.add(realFontName);
//            }
//            return (String[])fontsSet.toArray(new String[]{});
//        } else
//            return null;
//    }

    
public static String getFontName(PDFont font) {
    String fntName = font.getBaseFont();
    Matcher match = patternFontName.matcher(fntName);
    match.lookingAt();
    return match.group(2);
}


public String[] getAllFontsNames() {
    HashSet fntNames = new HashSet();
    Object [] fnts = getAllFonts();
    for (int i=0; i<fnts.length; i++) {
        PDFont fnt = (PDFont)fnts[i];
        fntNames.add(getFontName(fnt));
    }
    return (String[])fntNames.toArray(new String[]{});
}

public String[] getInuktitutFonts() {
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
    return (String[])fs.toArray(new String[]{});
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
    	float pcn = (float)ni / (float)totalContent.replaceAll("\\s","").length();
    	res[i][0] = p;
    	res[i][1] = new Float(pcn);
    }
    return res;
}

    public String getContents() {
        StringWriter writer = (StringWriter) stripper.getOutput();
        String str = writer.toString();
        return str;
    }
    
//    private boolean containsNonLegacyInuktitutFonts() {
//        String[] allFonts = getAllFontsNames();
//        for (int i=0; i<allFonts.length; i++) {
//            String fnt = allFonts[i];
//            for (int j=0; j<Police.polices.length; j++)
//                if (!fnt.toLowerCase().startsWith(Police.polices[j]))
//                    return true;
//        }
//        return false;
//    }
    
//    private String isInuktitutLegacyFontName(String fontName) {
//        for (int j=0; j<Police.polices.length; j++)
//            if (fontName.toLowerCase().startsWith(Police.polices[j]))
//                return Police.polices[j];
//        return null;
//    }
    
//    private void identifyFonts() {
//        Hashtable fontNameReplacements = new Hashtable();
//         Hashtable hf = new Hashtable();
//        /*
//         * Pour chaque élément de texte, ajouter ce texte au texte associé à une
//         * police du document.
//         */
//         if (textElements != null)
//        for (int i = 0; i < textElements.length; i++) {
//            String textElement = (String)textElements[i][0];
//            PDFont font = (PDFont)textElements[i][1];
//            String tf = null;
//            if (font != null) {
//                tf = (String)hf.get(font);
//                if (tf == null)
//                    tf = textElement;
//                else
//                    tf = tf + " " + textElement;
//                hf.put(font,tf);
//            }
//        }
//        /*
//         * Pour chaque police, déterminer si son texte correspond, par ngrammes,
//         * à une police archaïque inuktitut connue.
//         */
//        for (Enumeration e = hf.keys(); e.hasMoreElements();) {
//            Object fontKey = e.nextElement();
//            String fontText = (String)hf.get(fontKey);
//            String inuktitutFontName;
//            PDFont font = (PDFont)fontKey;
//            String baseFontName = font.getBaseFont();
//            if (baseFontName==null)
//                baseFontName = font.getName();
//            Matcher mp = patternFontName.matcher(baseFontName);
//            String newBaseFontName = baseFontName;
//            if (mp.find())
//                newBaseFontName = mp.group(2).toLowerCase();
//            if (isInuktitutLegacyFontName(baseFontName) == null) {
//                Ngramme fontsNgrams = Police.determineFontsByNgrams(fontText);
//                List sortedNgrams = fontsNgrams.sort();
//                Ngramme.FontNgramme max = (Ngramme.FontNgramme)sortedNgrams.get(0);
//                if (max.freq > 95.0) {
//                    fontNameReplacements.put(font,newBaseFontName);
//                    System.out.println(baseFontName+" remplacé par "+newBaseFontName);
//                }
//            }
//        }
//        /*
//         * Remplacer les polices dans textElements
//         */
//        if (textElements != null)
//        for (int i = 0; i < textElements.length; i++) {
//            PDFont font = (PDFont)textElements[i][1];
//            String newFontName;
//            if ((newFontName = (String)fontNameReplacements.get(font)) != null)
//                textElements[i][1] = newFontName;
//        }
//   }
    
    public OutputStreamWriter getHighlightPositions(String highlightWord, File filePath) {
        NRC_PDFHighlighter hl;
        OutputStreamWriter xmlOutput = null;
        try {
            hl = new NRC_PDFHighlighter();
            PDDocument pdDocument = new PDDocument(document);
            xmlOutput = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
            hl.generateXMLHighlight(pdDocument, highlightWord, xmlOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlOutput;
    }

    public OutputStreamWriter getHighlightPositions(String[] highlightWords, File filePath) {
       NRC_PDFHighlighter hl;
        OutputStreamWriter xmlOutput = null;
        try {
            hl = new NRC_PDFHighlighter();
            PDDocument pdDocument = new PDDocument(document);
            xmlOutput = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
            hl.generateXMLHighlight(pdDocument, highlightWords, xmlOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlOutput;
    }
    
    public String[] getWordsToHighlight(String[] highlightWords) {
        NRC_PDFHighlighter hl;
        CharArrayWriter xmlOutput = null;
        String[] wordsToHighlight = null;
        try {
            hl = new NRC_PDFHighlighter();
            PDDocument pdDocument = new PDDocument(document);
            xmlOutput = new CharArrayWriter();
            hl.generateXMLHighlight(pdDocument, highlightWords, xmlOutput);
            wordsToHighlight = hl.getWordsToHighlight();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsToHighlight;
        
    }
    
    /*
     * --------------------------- Highlighting -------------------------------
     */

    private static File wordFile;
    private static File tmpDir = new File(File.separator,"tmp");

    /*
     * Arguments:
     * -url url: url de la page dans laquelle les mots sont à mettre en évidence.
     * -f f:     nom du fichier sur le serveur dans lequel se trouvent
     *            les mots à mettre en évidence; ce fichier doit se trouver dans
     *            le répertoire défini par la propriété de système "user.dir".
     * -d d:     nom du répertoire où placer et lire les fichiers (copie et 
     *           mots)
     * -h h: nom du répertoire htdocs où placer le fichier xml
     * -s latsyl:   "latin" ou "syllabics"
     * 
     * Retour:
     * Un tableau de 2 objets:
     * 0 - une chaîne String contenant l'encodage (charset) de cet url
     * 1 - un objet File désignant le fichier-copie avec le surlignement
     */
    
    public Object[] highlight(String[] args) throws Exception {
        String nomUrl = Util.getArgument(args,"url");
        String wordFileName = Util.getArgument(args,"f");
        String latsyl = Util.getArgument(args,"s");
        if (latsyl==null)
            latsyl = Util.getArgument(args,"outputType"); // pour test
        String directoryName = Util.getArgument(args,"d");
        String htdocsName = Util.getArgument(args, "h");
        String wordsSeq = Util.getArgument(args,"m");
        if (directoryName != null)
            tmpDir = new File(directoryName);
        File htdocsDir = new File(htdocsName);
        // Lire les mots à rechercher dans le fichier HTML -> vecteur words.
        Vector words = null;
        if (wordFileName != null) {
            wordFile = new File(tmpDir,wordFileName);
            words = NRC_HTMLDocument.getWordsToHighlight(wordFile,latsyl);
        } else if (wordsSeq != null) {
            words = NRC_HTMLDocument.getWordsToHighlight(wordsSeq,latsyl);
        }
        // Trouver les polices archaïques inuktitut dans le document
        String[] fonts = getInuktitutFonts();
        /*
         * sWords va contenir tous les mots trouvés dans le texte du fichier PDF 
         * qui correspondent aux mots unicode dans 'words'
         */
        String sWords[] = lookForWordsInFile(words,fonts,latsyl);

        File xmlFile = File.createTempFile("pdfhl",".txt",htdocsDir);
        getHighlightPositions(sWords,xmlFile);
//        String[] wordsToHighlight = getWordsToHighlight(sWords);
        Object hl[] = new Object[2];
        hl[0] = nomUrl;
        hl[1] = xmlFile;
//        hl[1] = wordsToHighlight;
        return hl;
    }
    
    /*
     * Look for all the words in the PDF text that correspond to any form of the
     * unicode words.
     */
    String[] lookForWordsInFile(Vector words, String[] fonts, String latsyl) {
        /*
         * Transcode each (Unicode) word to the same word in a legacy font and
         * prepare it as a regular expression.
         */
        Hashtable wordsFontsToCheck = new Hashtable();
        wordsFontsToCheck.put("unicode_ra",words);
        
//        Hashtable unicodeToLegacy = new Hashtable();
//        if (latsyl.startsWith("syll")) {
//            for (int i=0; i<fonts.length; i++) {
//                Vector wfs = new Vector();
//                String font = fonts[i];
//                for (int j=0; j<words.size(); j++) {
//                    String word = (String)words.elementAt(j);
//                    String w = TransCoder.unicodeToLegacy(word,font);
//                    String wd = prepareForRegexp(w);
////                    String wd = withDots(wp);
//                    if (!wfs.contains(wd)) {
//                        wfs.add(wd);
//                    }
//                    Vector v;
//                    if (!unicodeToLegacy.containsKey(word)) {
//                        v = new Vector();
//                        v.add(wd);
//                    } else {
//                        v = (Vector)unicodeToLegacy.get(word);
//                        v.add(wd);
//                    }
//                    unicodeToLegacy.put(word, v);
//                }
//                if (wfs.size() != 0)
//                    wordsFontsToCheck.put(font,wfs);
//            }
//        }

//        String textInFile = getPageContent();

        /*
         * Look for each word in the long text string.  The words to be looked
         * for are in Unicode, and the text returned by getPageContent() also.
         */
        Vector wordsToHighlight = new Vector();
        for (Enumeration e = wordsFontsToCheck.keys(); e.hasMoreElements();) {
            Object font = e.nextElement();
            Vector wordPatterns = (Vector)wordsFontsToCheck.get(font);
//            String ffont;
//                ffont = "";
            for (int i=0; i<wordPatterns.size(); i++) {
                String w = (String)wordPatterns.elementAt(i);
                String wp = w.replaceAll("\\s+","\\\\s+");
//                Pattern p = Pattern.compile(wp);
//                Matcher m = p.matcher(textInFile);
//                int pos = 0;
//                while (pos < textInFile.length() && m.find(pos)) {
//                    // The word has been found.
//                    if (m.start()==0 || Orthography.isWordDelimiter(textInFile.charAt(m.start()-1),ffont))
//                        if (m.end()==textInFile.length() || Orthography.isWordDelimiter(textInFile.charAt(m.end()),ffont)) {
//                            // The word is delimited by word delimiters
//
//                            // This word has to be highlighted.
//                            String wordFound = textInFile.substring(m.start(),m.end());
//                            // Convert this word to the Inuktitut fonts found in the file
//                            Vector w2h = makeWordsToHighlight(wordFound,fonts);
//                            for (int j=0; j<w2h.size(); j++) {
//                                String wf = (String)w2h.elementAt(j);
//                                if (!wordsToHighlight.contains(wf))
//                                        wordsToHighlight.add(wf);
//                            }
//                        }
//                    if (ffont.equals(""))
//                        pos = textInFile.length();
//                    else
//                        pos = m.end();
//                }
                Vector w2h = makeWordsToHighlight(wp,fonts);
                for (int j=0; j<w2h.size(); j++) {
                    String wf = (String)w2h.elementAt(j);
                    if (!wordsToHighlight.contains(wf))
                        wordsToHighlight.add(wf);
                }
            }
        }
        return (String[])wordsToHighlight.toArray(new String[]{});
    }
    
    /*
     * 'word' is a word in Unicode found in the Unicode version of the PDF file's
     * text.  Corresponding words with different spellings have to be generated,
     * and for each one, the corresponding sequence has to be generated in each
     * of the Inuktitut fonts found in the document.
     */
    static Vector makeWordsToHighlight(String word, String [] fonts) {
        Vector v = new Vector();
        v.add(word);
        String w = new String(word);
        /*
         * Check the word for 'rr' and 'qq' which could also be written 'qr' and 'rq'
         * respectively.
         */
        w = w.replaceAll("rr", "qr");
        w = w.replaceAll("qq","rq");
        w = w.replaceAll("\u1550([\u1546-\u154C])", "\u1585$1");
        w = w.replaceAll("\u1585\u146B","\u1550\u1670");
        w = w.replaceAll("\u1585\u146D","\u1550\u157F");
        w = w.replaceAll("\u1585\u146E","\u1550\u1580");
        w = w.replaceAll("\u1585\u146F","\u1550\u1581");
        w = w.replaceAll("\u1585\u1470","\u1550\u1582");
        w = w.replaceAll("\u1585\u1472","\u1550\u1583");
        w = w.replaceAll("\u1585\u1473","\u1550\u1584");
        if (!w.equals(word))
            v.add(w);
        
        /*
         * For each (Unicode) element in 'v', prepare a version of it in each font
         * found in the PDF file that can be used as a regular expression to be
         * matched against the original contents of the file. 
         */
        int vl = v.size();
        for (int i=0; i<vl; i++) {
            String wo = (String)v.elementAt(i);
            for (int j=0; j<fonts.length; j++) {
                String wol = TransCoder.unicodeToLegacy(wo,fonts[j]);
                String wd = prepareForRegexp(wol);
                v.add(wd);
            }
        }
        return v;
    }

    
    /*
     * Quelques codes de caractères syllabiques,
     * comme '[' dans la police Prosyl qui représente la consonne seule 'g', sont
     * des caractères utilisés dans les expressions régulières.  Pour que ces
     * caractères soient interprétés comme caractères ordinaires dans les 
     * expressions régulières, il faut les faire précéder d'un '\'.
     */
    
    static String prepareForRegexp(String x) {
        if (x.length()==0)
            return x;
        
        String dotPattern = "[|}~+\\]`<>]";
        String dotPatternReplacement = "[|}~+\\]`<>]";

        StringBuffer sb = new StringBuffer();
        Pattern patDot = Pattern.compile(dotPattern);
        Pattern patPunct = Pattern.compile("\\p{Punct}");
        for (int i=0; i<x.length(); i++) {
            String input = x.substring(i, i+1);
            Matcher mDot = patDot.matcher(input);
            Matcher mPunct = patPunct.matcher(input);
            if (mDot.matches())
                sb.append(dotPatternReplacement);
            else if (mPunct.matches())
                sb.append("\\"+input);
            else
                sb.append(input);
        }
        return sb.toString();

    }
//    static String prepareForRegexp(String x) {
//        String y = prepareForRegexpNonAlphanum(x);
//        
//        return y;
//    }
    
    static String prepareForRegexpNonAlphanum(String x) {
        if (x.length()==0)
            return x;
        else {
            int pos = 0;
            Pattern pat = Pattern.compile("\\p{Punct}");
            Matcher mpat = pat.matcher(x);
            String ret = "";
            while (pos < x.length() && mpat.find(pos)) {
                ret += x.substring(pos,mpat.start());
                ret += "\\\\"+mpat.group();
                pos = mpat.end();
            }
            ret += x.substring(pos);
            return ret;
        }
    }
    
    static String prepareForRegexpSpaces(String x) {
        String y = x.replaceAll("\\s+","\\\\s+");
        return y;
    }

    /*
     * Remplacer chaque caractère de point par l'expression 
     * régulière incluant tous les caractères de point.
     * Ex.: w6vNw\`\/i4 (iqqanaijaanik) devient w6vNw[<>`~\]+}]/i4
     */
    static String withDots(String src) {
        String combinedDotCodesForRegexp = "[<>`~\\\\\\]+|}]";
        String regexp = "\\\\"+combinedDotCodesForRegexp;
        String newsrc = src.replaceAll(regexp,combinedDotCodesForRegexp);
        return newsrc;
      }
    
    /**
     * @param args
     */
    public static void main (String [] args) {
		NRC_PDFDocument doc = null;
		OutputStream out = System.out;
		if (args[0].equals("-f"))
			try {
				File f = new File(args[1]);
				doc = new NRC_PDFDocument(args[1],"file");
	            float percent =  doc.getInuktitutPercentage();
	    		out.write(("Content-Type: application/pdf"+"\n").getBytes());
	    		out.write(("Inuktitut-Percentage: "+String.valueOf(percent)).getBytes());
	    		out.write("\n\n".getBytes());
	    		FileInputStream fr = new FileInputStream(f);
	    		int c;
	    		while ((c=fr.read()) != -1) {
	    			out.write(c);
	    		}
	    		fr.close();
	    		out.flush();
			} catch (Exception e) {
				LOG.info("--NRC_PDFDocument--- Exception: "+e.getMessage());
			}
//		else if (args[0].equals("-u"))
//			try {
//				doc = new NRC_HTMLDocumentByCobra(args[0]);
//				dumpFile(doc.copyOfFile,System.out);			System.out.println("\n-----------------\n");
//				doc.toUnicode(System.out);
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.print("");
//				}


//    public static void main(String[] args) {
//        String[] argsForHighlight = new String[8];
//        String url = "http://206.191.37.17/pdf/publications/pauktuutit/TeenPregnancy_i.pdf";
//        argsForHighlight[0] = "-url";
//        argsForHighlight[1] = url;
//        argsForHighlight[2] = "-f";
//        argsForHighlight[3] = "ISEWordsFoundSyl.txt";
//        argsForHighlight[4] = "-d";
//        argsForHighlight[5] = "C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\webapps\\NRCInuktitutSearchEngine\\tmp";
//        argsForHighlight[6] = "-s";
//        argsForHighlight[7] = "syllabics";
//
//        try {
////            NRC_Document doc = NRC_Document.Exec.getDocument(url);
//            NRC_Document doc = NRC_Document.Exec.getDocument("/temp/b.txt", "application/pdf", "http://assembly.nu.ca/inuktitut/bills/4th_bill26_inuk.pdf");
////            doc.highlight(argsForHighlight);
//			float ip = doc.getInuktitutPercentage();
//			String [] fonts = doc.getInuktitutFonts();
//			String fonts_str = "";
//			for (int i=0; i<fonts.length; i++)
//				fonts_str += fonts[i]+" ";
//			System.out.print(Float.toString(ip)+" "+fonts_str);
//            doc.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public Object[] transliterate() throws OutOfMemoryError, Exception {
        return null;
    }

    public WebPageReader getWpr() {
        return wpr;
    }

    public boolean containsInuktitut() {
        if (pageContent==null)
            getPageContent();
        return containsInuktitut;
    }

    public float getInuktitutPercentage() {
    	if (containsInuktitut()) {
    		return (float)inuktitutContent.replaceAll("\\s", "").length() /
    		(float)totalContent.replaceAll("\\s","").length();
//    		return (float)lengthOfInuktitutContent / (float)lengthOfTotalContent;
    	}
    	else
    		return 0;
    }
    


}
