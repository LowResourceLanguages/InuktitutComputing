/**
 * Copyright (c) 2003-2004, www.pdfbox.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of pdfbox; nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * http://www.pdfbox.org
 *
 */
package documents.pdfboxAddition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.util.PDFTextStripper;
import org.pdfbox.util.TextPosition;
import org.pdfbox.util.TextPositionComparator;

import org.apache.log4j.Logger;

import documents.NRC_Document;
import documents.NRC_PDFDocument;
import fonts.Font;
import script.TransCoder;



/**
 * Wrap stripped text in simple XML, trying to form XML paragraphs.
 * Paragraphs broken by pages, columns, or figures are not mended.
 * 
 * 
 * @author jjb - http://www.johnjbarton.com
 * @version  $Revision: 1.2 $
 * 
 */
public class NRC_PDFText2XML extends NRC_PDFTextStripper 
{
    private static Logger log = Logger.getLogger(NRC_PDFText2XML.class);
    private static final int INITIAL_PDF_TO_HTML_BYTES = 8192;

    private TextPosition beginTitle;
    private TextPosition afterEndTitle;
    private String titleGuess;
    private boolean suppressParagraphs;
//    private boolean onFirstPage = true;
    
    private boolean sortByPosition = false;
    
    private String lineSeparator = System.getProperty("line.separator");
//    private String lineSeparator = "";
    private int pageNumber;
    private int indent;
    private int indentIncrement = 0;
    private String source;

    /**
     * Constructor.
     * 
     * @throws IOException If there is an error during initialization.
     */
    public NRC_PDFText2XML(String pdfUrlName) throws IOException 
    {
        titleGuess = "";
        beginTitle = null;
        afterEndTitle = null;
        suppressParagraphs = false;
        source = pdfUrlName;
    }

    /**
     * Write the header to the output document.
     * 
     * @throws IOException If there is a problem writing out the header to the document.
     */
    protected void writeHeader() throws IOException 
    {
        StringBuffer buf = new StringBuffer(INITIAL_PDF_TO_HTML_BYTES);
        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>"+lineSeparator);
        buf.append("<xmlstream>"+lineSeparator);
        buf.append("<metatags>");
        buf.append("<meta name=\"source\" value=\""+source+"\" />");
        buf.append("<meta name=\"title\" value=\""+getTitleGuess()+"\" />");
        buf.append("</metatags>");
        buf.append("<body>"+lineSeparator);
        getOutput().write(buf.toString());
    }
   
    /**
     * The guess to the document title.
     * 
     * @return A string that is the title of this document.
     */
    protected String getTitleGuess() 
    {
        return titleGuess;
    }
   
    /**
     * @see PDFTextStripper#flushText
     */
    protected void flushText() throws IOException 
    {
        Iterator textIter;
        PDFont  font = null;
        float xbegin = -1;
        float xend = -1;
       
        if( log.isDebugEnabled() ) log.debug( "flushText() start" );
        float currentY = -1;
        float lastBaselineFontSize = -1;
        if( log.isDebugEnabled() ) log.debug("<Starting text object list>");
        float endOfLastTextX = -1;
        float startOfNextWordX = -1;
        float lastWordSpacing = -1;
        TextPosition lastProcessedCharacter = null;
        float ybegin = -1;
        
        for( int i=0; i<charactersByArticle.size(); i++)
        {
        	// START A PARAGRAPH (COLUMN)
            startParagraph();  // output <column> if not suppressParagraphs()
			if (log.isDebugEnabled()) log.debug("<column> new paragraph (charactersByArticle) started");
            boolean newLine_firstElement = true;
            boolean inBlock = true;
            float fontSize = 0f;
            float spaceWidth = 0f;
            String text = "";
            output.write(outputIndent());
            output.write("<block>"+lineSeparator);
            indent += indentIncrement;
            List textList = (List)charactersByArticle.get( i );
            if( sortByPosition )
            {
                TextPositionComparator comparator = new TextPositionComparator( getCurrentPage() );
                Collections.sort( textList, comparator );
            }
            ArrayList chunks = new ArrayList();
            TextPosition position = null;
            textIter = textList.iterator();
            
            while( textIter.hasNext() )
            {
            	// PROCESS AN ELEMENT OF charactersByArticle
                position = (TextPosition)textIter.next();
                String characterValue = position.getCharacter();
				if (log.isDebugEnabled()) log.debug("NEW TEXT ELEMENT from charactersByArticle : '"+characterValue+"'");
                   
                if (newLine_firstElement) {
                    xbegin = position.getX();
                    ybegin = position.getY();
                    xend = xbegin + position.getWidth();
                    font = position.getFont();
                    fontSize = position.getFontSize() * position.getYScale();
                    spaceWidth = position.getWidthOfSpace();
                    newLine_firstElement = false;
                }
                
                //wordSpacing = position.getWordSpacing();
                float wordSpacing = 0;
                
                if( wordSpacing == 0 )
                {
                    //try to get width of a space character
                    wordSpacing = position.getWidthOfSpace();
                    //if still zero fall back to getting the width of the current character
                    if( wordSpacing == 0 )
                        wordSpacing = position.getWidth();
                }
                               
                // RDD - We add a conservative approximation for space determination.
                // Basically if there is a blank area between two characters that is
                // equal to some percentage of the word spacing then that will be the
                // start of the next word
                if( lastWordSpacing <= 0 )
                    startOfNextWordX = endOfLastTextX + (wordSpacing* 0.50f);
                else
                    startOfNextWordX = endOfLastTextX + (((wordSpacing+lastWordSpacing)/2f)* 0.50f);
                
                lastWordSpacing = wordSpacing;
    
                // RDD - We will suppress text that is very close to the current line
                // and which overwrites previously rendered text on this line.
                // This is done specifically to handle a reasonably common situation
                // where an application (MS Word, in the case of my examples) renders
                // text four times at small (1 point) offsets in order to accomplish
                // bold printing.  You would not want to do this step if you were
                // going to render the TextPosition objects graphically.
                //
                /*if ((endOfLastTextX != -1 && position.getX() < endOfLastTextX) &&
                    (currentY != -1 && Math.abs(position.getY() - currentY) < 1))
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("Suppressing text overwrite" +
                                  " x: " + position.getX() +
                                  " endOfLastTextX: " + endOfLastTextX +
                                  " string: " + position.getCharacter());
                    }
                    continue;
                }*/
    
                // RDD - Here we determine whether this text object is on the current
                // line.  We use the lastBaselineFontSize to handle the superscript
                // case, and the size of the current font to handle the subscript case.
                // Text must overlap with the last rendered baseline text by at least
                // a small amount in order to be considered as being on the same line.
                //
                int verticalScaling = 1;
                if( lastBaselineFontSize < 0 || position.getFontSize() < 0 )
                {
                    verticalScaling = -1;
                }
                if (currentY != -1
						&& ((position.getY() < (currentY - (lastBaselineFontSize * 0.9f * verticalScaling))) || 
							   (position.getY() > (currentY + (position.getFontSize() * 0.9f * verticalScaling))))) {
					// NEW LINE
                	// BF - determine if superscript. [I am afraid that RDD's above
                	// does not really take car of superscript. In practice, a superscript
                	// becomes a new line (!?)] Here we determine that it is a superscript
                	// if Y position is inside last character height and size is smaller.
                	boolean isSuperScript = false;
                	if (position.getY() < currentY
                			&& position.getY() > currentY-fontSize
                			&& position.getFontSize()*position.getYScale() < fontSize)
                	{
                		isSuperScript = true;
                	}
                	if (isSuperScript) {
                		// Superscript
                	} else {
						// True Newline
						if (log.isDebugEnabled()) {
							log.debug("NEWLINE DETECTED - currentY=" + currentY + ", y="
									+ position.getY() + " fs="
									+ position.getFontSize() + " lb fs="
									+ lastBaselineFontSize + ">");
						}
						
						// Write current line
						String fontParams[] = getFontName(font);
						chunks.add(new Object[] { text, fontParams,
								new Float(fontSize), new Float(spaceWidth) });
						outputLine(xbegin, ybegin, xend, chunks); // output <line...><text...>
						if (Math.floor(position.getY()) < Math.floor(ybegin)) {
							if (inBlock) {
					            output.write(outputIndent());
								output.write("</block>" + lineSeparator);
								indent -= indentIncrement;
							}
							output.write(outputIndent());
							output.write("<block>" + lineSeparator);
							indent += indentIncrement;
							inBlock = true;
						}
						
						// Initialize info for the new line
						font = position.getFont();
						fontSize = position.getFontSize() * position.getYScale();
						spaceWidth = position.getWidthOfSpace();
						text = "";
						chunks = new ArrayList();
						xbegin = position.getX();
						ybegin = position.getY();
						xend = xbegin + position.getWidth();
						// The following 4 lines indicate a new line
						endOfLastTextX = -1;
						startOfNextWordX = -1;
						currentY = -1;
						lastBaselineFontSize = -1;
					} // end of NEWLINE and NOT SUPERSCRIPT
                } // end of NEWLINE
                
				if (startOfNextWordX != -1
					&& startOfNextWordX < position.getX()
					&& lastProcessedCharacter != null
					// only bother adding a space if the last character was not a space
					&& lastProcessedCharacter.getCharacter() != null
					&& !lastProcessedCharacter.getCharacter().endsWith( " " ) )
                {
                    if (log.isDebugEnabled())
                        log.debug("<ADD A SPACE startOfNextWordX=" + startOfNextWordX + ", x=" + position.getX() + ">");
                    text +=  wordSeparator;
                }
    
                if (log.isDebugEnabled())
                    log.debug("flushText" +
                              " x=" + position.getX() +
                              " y=" + position.getY() +
                              " xScale=" + position.getXScale() +
                              " yScale=" + position.getYScale() +
                              " width=" + position.getWidth() +
                              " currentY=" + currentY +
                              " endOfLastTextX=" + endOfLastTextX +
                              " startOfNextWordX=" + startOfNextWordX +
                              " fontSize=" + position.getFontSize() +
                              " wordSpacing=" + wordSpacing +
                              " string=\"" + characterValue + "\"");
    
                if (currentY == -1)
                    currentY = position.getY();
    
                if (currentY == position.getY())
                    lastBaselineFontSize = position.getFontSize();
    
                // RDD - endX is what PDF considers to be the x coordinate of the
                // end position of the text.  We use it in computing our metrics below.
                //
                endOfLastTextX = position.getX() + position.getWidth();
                xend = endOfLastTextX;
    
                if (characterValue != null) {
                    if (position.getFont() == font) {
//                        text += toHTMLEntities(characterValue);
                        text += characterValue;
                    }
                    else {
                        String fontParams[] =getFontName(font); 
                        chunks.add(new Object[]{text,fontParams,new Float(fontSize),
                                new Float(spaceWidth)});
                        font = position.getFont();
                        fontSize = position.getFontSize() * position.getYScale();
                        spaceWidth = position.getWidthOfSpace();
//                        text = toHTMLEntities(characterValue);
                        text = characterValue;
                    }
                } 
                else
                {
                    log.debug( "Position.getString() is null so not writing anything" );
                }
                lastProcessedCharacter = position;
            }
            if (position != null) {
            	String fontParams[] =getFontName(position.getFont()); 
            	chunks.add(new Object[]{text,fontParams,new Float(fontSize),
                    new Float(spaceWidth)});
            }
			outputLine(xbegin, ybegin, xend, chunks); // output <line...><text...>
            chunks.clear();
            if (inBlock) {
            	output.write(outputIndent());
                output.write("</block>"+lineSeparator);
                indent -= indentIncrement;
            }
            endParagraph();
            // END OF PARAGRAPH
        } // end of for charactersByArticle
        

        // RDD - newline at end of flush - required for end of page (so that the top
        // of the next page starts on its own line.
        //
        if( log.isDebugEnabled() ) log.debug("<newline endOfFlush=\"true\">");
        output.flush();
    }
    
    /**
     * This method will attempt to guess the title of the document.
     * 
     * @param textIter The characters on the first page.
     * @return The text position that is guessed to be the title.
     */
    protected TextPosition guessTitle(Iterator textIter) 
    {
        float lastFontSize = -1.0f;
        int stringsInFont = 0;
        StringBuffer titleText = new StringBuffer();
        while (textIter.hasNext()) 
        {
            Iterator textByArticle = ((List)textIter.next()).iterator();
            while( textByArticle.hasNext() )
            {
                TextPosition position = (TextPosition) textByArticle.next();
                float currentFontSize = position.getFontSize();
                if (currentFontSize != lastFontSize) 
                {
                    if (beginTitle != null) 
                    { // font change in candidate title.
                        if (stringsInFont == 0) 
                        {
                            beginTitle = null; // false alarm
                            titleText.setLength(0);
                        } 
                        else 
                        {
                            // had a significant font with some words: call it a title
                            titleGuess = titleText.toString();
                            log.debug("Title candidate =" + titleGuess);
                            afterEndTitle = position;
                            return beginTitle;
                        }
                    } 
                    else 
                    { // font change and begin == null
                        if (currentFontSize > 13.0f) 
                        { // most body text is 12pt max I guess
                            beginTitle = position;
                        }
                    }
         
                    lastFontSize = currentFontSize;
                    stringsInFont = 0;
                } 
                stringsInFont++;
                if (beginTitle != null)
                {
                    titleText.append(position.getCharacter()+" ");
                }
            }
        }
        return beginTitle; // null
    }
    
    /**
     * Write out the paragraph separator.
     * 
     * @throws IOException If there is an error writing to the stream.
     */
    protected void startParagraph() throws IOException 
    {
        if (! suppressParagraphs) 
        {
        	output.write(outputIndent());
            getOutput().write("<column>"+lineSeparator);
            indent += indentIncrement;
        }
    }
    /**
     * Write out the paragraph separator.
     * 
     * @throws IOException If there is an error writing to the stream.
     */
    protected void endParagraph() throws IOException 
    {
        if (! suppressParagraphs) 
        {
        	output.write(outputIndent());
            getOutput().write("</column>"+lineSeparator);
            indent -= indentIncrement;
        }
    }
    
    /**
     * @see PDFTextStripper#writeCharacters( TextPosition )
     */
    protected void writeCharacters(TextPosition position ) throws IOException 
    {
        if (position == beginTitle) 
        {
            output.write("<H1>");
            suppressParagraphs = true;
        } 
        if (position == afterEndTitle) 
        {
            output.write("</H1>");  // end title and start first paragraph
            suppressParagraphs = false;
        }
      
        String chars = position.getCharacter();

        for (int i = 0; i < chars.length(); i++) 
        {
            char c = chars.charAt(i);
            if ((c < 32) || (c > 126)) 
            {
                int charAsInt = c;
                output.write("&#" + charAsInt + ";");
            } 
            else 
            {
                switch (c) 
                {
                    case 34:
                        output.write("&quot;");
                        break;
                    case 38:
                        output.write("&amp;");
                        break;
                    case 60:
                        output.write("&lt;");
                        break;
                    case 62:
                        output.write("&gt;");
                        break;
                    default:
                        output.write(c);
                }
            }
        }
    }
    
    /**
     * @return Returns the suppressParagraphs.
     */
    public boolean isSuppressParagraphs()
    {
        return suppressParagraphs;
    }
    /**
     * @param shouldSuppressParagraphs The suppressParagraphs to set.
     */
    public void setSuppressParagraphs(boolean shouldSuppressParagraphs)
    {
        this.suppressParagraphs = shouldSuppressParagraphs;
    }
    
    /*
     * Modèle des noms de police retournés par getBaseFont(), dont voici des
     * exemples: ArialMT, Arial-BoldMT, AMHBNE+ProSylBold
     */
    private Pattern patternFontName1 = Pattern.compile("(.{6}\\x2B)?([^,]+)([,].+)?$");
    private Pattern patternFontName2 = Pattern.compile("(.{6}\\x2B)?([^-]+)[-](.+)$");

    private String[] getFontName(PDFont font) {
        String[] res = new String[3];
        res[1] = "";
        res[2] = "";
        String name = font.getBaseFont();
        Matcher mp2 = patternFontName2.matcher(name);
        Matcher mp1 = patternFontName1.matcher(name);
        if (mp2.find()) {
            res[0] = mp2.group(2);
//            String type = mp2.group(3).toLowerCase();
//            if (type.equals("bold")) {
//                res[1] = "b";
//            } else if (type.equals("boldoblique") || type.equals("bolditalic")) {
//                res[1] = "b";
//                res[2] = "i";
//            } else if (type.equals("oblique") || type.equals("italic")) {
//                res[2] = "i";
//            } else if (type.equals("roman")) {
//            }
            Pattern patternBold = Pattern.compile("bo?l?d",Pattern.CASE_INSENSITIVE);
            Pattern patternItalic = Pattern.compile("(ita?l?i?c?)|(obl?i?q?u?e?)",Pattern.CASE_INSENSITIVE);
            Matcher mb = patternBold.matcher(mp2.group(3));
            Matcher mi = patternItalic.matcher(mp2.group(3));
            if (mb.find())
                res[1] = "b";
            if (mi.find())
                res[2] = "i";
        }
        else if (mp1.find()) {
            String type = mp1.group(3);
            res[0] = mp1.group(2);
            String fname = res[0].toLowerCase();
            for (int i=0; i<Font.fonts.length; i++)
                if (fname.startsWith(Font.fonts[i]))
                {
                    res[0] = Font.fonts[i];
                    if (type==null)
                        type = mp1.group(2).substring(res[0].length());
                    break;
                }
            if (type != null) {
                Pattern patternBold = Pattern.compile("bo?l?d",Pattern.CASE_INSENSITIVE);
                Pattern patternItalic = Pattern.compile("(ita?l?i?c?)|(obl?i?q?u?e?)",Pattern.CASE_INSENSITIVE);
                Matcher mb = patternBold.matcher(type);
                Matcher mi = patternItalic.matcher(type);
                if (mb.find())
                    res[1] = "b";
                if (mi.find())
                    res[2] = "i";
            }
        }
        return res;
        }
    
    
    /**
     * This method is available for subclasses of this class.  It will be called before processing
     * of the document start.
     * 
     * @param pdf The PDF document that is being processed.
     * @throws IOException If an IO error occurs.
     */
    protected void startDocument(PDDocument pdf) throws IOException 
    {
            Iterator textIter =  getCharactersByArticle().iterator();
            guessTitle(textIter);
            writeHeader();
            pageNumber = 0;
    }
    
    /**
     * @see PDFTextStripper#endDocument( PDDocument )
     */
    public void endDocument(PDDocument pdf) throws IOException 
    {
        output.write("</body>");
        output.write("</xmlstream>");
        output.flush();
    }


    /**
     * Start a new page.  Default implementation is to do nothing.  Subclasses
     * may provide additional information.
     * 
     * @param page The page we are about to process.
     * 
     * @throws IOException If there is any error writing to the stream.
     */
    protected void startPage( PDPage page ) throws IOException
    {
    	output.write(outputIndent());
        pageNumber++;
        output.write("<page no=\""+pageNumber+"\">"+lineSeparator);
        indent += indentIncrement;
    }
    
    /**
     * End a page.  Default implementation is to do nothing.  Subclasses
     * may provide additional information.
     * 
     * @param page The page we are about to process.
     * 
     * @throws IOException If there is any error writing to the stream.
     */
    protected void endPage( PDPage page ) throws IOException
    {
    	output.write(outputIndent());
        output.write("</page>"+lineSeparator);
        indent -= indentIncrement;
    }

    private char[] outputIndent() throws IOException
        {
        char [] c = new char[indent];
        for (int i=0; i<indent; i++) {
            c[i] = ' ';
        }
        return c;
    }
    
	private void outputLine(float xbegin, float ybegin, float xend, ArrayList chunks) throws IOException {
		startLine(xbegin, ybegin, xend);  // output <line ...>
		outputLine(chunks);
		endLine();
	}

    private void startLine(float xb, float yb, float xe) throws IOException {
    	output.write(outputIndent());
        float xi, yi,xei;
        xi = Math.round(xb*100f)/100f;
        yi = Math.round(yb*100f)/100f;
        xei = Math.round(xe*100f)/100f;
        output.write("<line x=\""+xi+"\" y=\""+yi+"\" xend=\""+xei+"\">"+lineSeparator);
        indent += indentIncrement;
    }
    
    private void outputLine(ArrayList chunks) throws IOException {
        for (int i=0; i<chunks.size(); i++) {
            Object[] chunk = (Object[])chunks.get(i);
            String chunkText = (String)chunk[0];
            String[] fontParams = (String[])chunk[1];
            String fontName = fontParams[0];
            if (Font.isLegacy(fontName))
            	chunkText = TransCoder.legacyToUnicode(chunkText, fontName);
            float fontSize = ((Float)chunk[2]).floatValue();
            float spaceWidth = ((Float)chunk[3]).floatValue();
            boolean bold = false;
            boolean  italic = false;
            if (fontParams[1].equals("b"))
                bold = true;
            if (fontParams[2].equals("i"))
                italic = true;
            output.write(outputIndent());
            output.write("<text face=\""+fontName+"\" size=\""+fontSize+"\"");
            output.write(" spaceWidth=\""+spaceWidth+"\"");
            if (bold)
                output.write(" bold=\"yes\"");
            if (italic)
                output.write(" italic=\"yes\"");
            output.write(">"+lineSeparator);
            indent += indentIncrement;
            output.write(outputIndent());
            // This is a hack. Joined with extrairePhrases.pl, this is to make sure
            // that a : at the end of a line is considered as the end of the sentence.
            if (i==chunks.size()-1 && chunkText.charAt(chunkText.length()-1)==':')
            	chunkText += ".";
            output.write(chunkText+lineSeparator);
            output.write(outputIndent());
            output.write("</text>"+lineSeparator);
            indent -= indentIncrement;
        }
    }
    
    private void endLine() throws IOException {
    	output.write(outputIndent());
        output.write("</line>"+lineSeparator);
        indent -= indentIncrement;
    }
    
//    private void writePar(String text, String fontName) throws IOException {
//        outputIndent();
//        output.write("<paragraph>"+lineSeparator);
//        indent += 4;
//        outputIndent();
//        output.write("<text font=\""+fontName+"\">"+lineSeparator);
//        indent += 4;
//        outputIndent();
//        output.write(text+lineSeparator);
//        outputIndent();
//        output.write("</text>"+lineSeparator);
//        indent -= 4;
//        outputIndent();
//        output.write("</paragraph>"+lineSeparator);
//        indent -= 4;
//    }
    
    public static void main(String[] args) {
//    	PropertyConfigurator.configure("log4j.properties");
        String pdfUrlName=null, xmlName;
        String name = args[0];
        String dirName = args[1];
        try {
                File f = new File(name);
                if (f.exists())
                    pdfUrlName = "file:///"+f.getAbsolutePath();
                else
                    throw new Exception("Le fichier \""+name+"\" n'existe pas.");
                File d = new File(dirName);
                if (!d.exists())
                    throw new Exception("Le répertoire \""+dirName+"\" n'existe pas.");
        }catch (Exception e2) {
            System.err.println(e2.getMessage());
            System.exit(1);
        }
        File pdfFile = new File(pdfUrlName);
        String fileName = pdfFile.getName();
        String fileNameWithoutExt = fileName.split("\\.")[0];
        xmlName = fileNameWithoutExt + ".xml";
        File xmlFile = new File(dirName,xmlName);
        Writer outputStream;
        NRC_Document doc = null;
        try {
            outputStream = new OutputStreamWriter(
                    new FileOutputStream(xmlFile),"utf-8");
            NRC_PDFText2XML pdf2txt = new NRC_PDFText2XML(pdfUrlName);
//            doc = NRC_Document.Exec.getDocument(pdfUrlName);
            doc = new NRC_PDFDocument(pdfUrlName);
			if (log.isDebugEnabled()) {
				log.debug("pdfUrlName="+pdfUrlName+"\ndoc="+doc);
			}
            pdf2txt.writeText(new PDDocument(((NRC_PDFDocument)doc).document), outputStream);
            System.out.println(xmlName);
//        } catch (NRC_DocumentException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	if (doc != null) doc.close();
        }
    }

}