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

import java.io.IOException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.util.PDFTextStripper;
import org.pdfbox.util.TextPosition;
import org.pdfbox.util.TextPositionComparator;

import org.apache.log4j.Logger;

import fonts.Font;


/**
 * Wrap stripped text in simple HTML, trying to form HTML paragraphs.
 * Paragraphs broken by pages, columns, or figures are not mended.
 * 
 * 
 * @author jjb - http://www.johnjbarton.com
 * @version  $Revision: 1.1 $
 * 
 */
public class NRC_PDFText2HTML extends NRC_PDFTextStripper 
{
    private static Logger log = Logger.getLogger(NRC_PDFText2HTML.class);
    private static final int INITIAL_PDF_TO_HTML_BYTES = 8192;

    private TextPosition beginTitle;
    private TextPosition afterEndTitle;
    private String titleGuess;
    private boolean suppressParagraphs;
    private boolean onFirstPage = true;
    
    private boolean sortByPosition = false;

    /**
     * Constructor.
     * 
     * @throws IOException If there is an error during initialization.
     */
    public NRC_PDFText2HTML() throws IOException 
    {
        titleGuess = "";
        beginTitle = null;
        afterEndTitle = null;
        suppressParagraphs = false;
        setLineSeparator("<br>");
        setPageSeparator("<br>");
    }

    /**
     * Write the header to the output document.
     * 
     * @throws IOException If there is a problem writing out the header to the document.
     */
    protected void writeHeader() throws IOException 
    {
        StringBuffer buf = new StringBuffer(INITIAL_PDF_TO_HTML_BYTES);
        buf.append("<html><head>");
        buf.append("<title>");
        buf.append(getTitleGuess());
        buf.append("</title>");
        buf.append("</head>");
        buf.append("<body>\n");
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
        Iterator textIter = getCharactersByArticle().iterator();
        PDFont  font = null;
        String fontName = "";
        boolean inFont = false;
        boolean inItalic = false;
        boolean inBold = false;
        
        if (onFirstPage) 
        {
            guessTitle(textIter);
            writeHeader();
            onFirstPage = false;
        }
        if( log.isDebugEnabled() )
        {
            log.debug( "flushText() start" );
        }
        float currentY = -1;
        float lastBaselineFontSize = -1;
        if( log.isDebugEnabled() )
        {
            log.debug("<Starting text object list>");
        }
        float endOfLastTextX = -1;
        float startOfNextWordX = -1;
        float lastWordSpacing = -1;
        TextPosition lastProcessedCharacter = null;
        float leading = 0;
        
        for( int i=0; i<charactersByArticle.size(); i++)
        {
            startParagraph();
            String text = "";
            List textList = (List)charactersByArticle.get( i );
            if( sortByPosition )
            {
                TextPositionComparator comparator = new TextPositionComparator( getCurrentPage() );
                Collections.sort( textList, comparator );
            }
            textIter = textList.iterator();
            while( textIter.hasNext() )
            {
                TextPosition position = (TextPosition)textIter.next();
                String characterValue = position.getCharacter();
                
                //wordSpacing = position.getWordSpacing();
                float wordSpacing = 0;
                
                if( wordSpacing == 0 )
                {
                    //try to get width of a space character
                    wordSpacing = position.getWidthOfSpace();
                    //if still zero fall back to getting the width of the current
                    //character
                    if( wordSpacing == 0 )
                    {
                        wordSpacing = position.getWidth();
                    }
                }
                               
                
                // RDD - We add a conservative approximation for space determination.
                // basically if there is a blank area between two characters that is
                //equal to some percentage of the word spacing then that will be the
                //start of the next word
                if( lastWordSpacing <= 0 )
                {
                    startOfNextWordX = endOfLastTextX + (wordSpacing* 0.50f);
                }
                else
                {
                    startOfNextWordX = endOfLastTextX + (((wordSpacing+lastWordSpacing)/2f)* 0.50f);
                }
                
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
                if (currentY != -1 &&
                    ((position.getY() < (currentY - (lastBaselineFontSize * 0.9f * verticalScaling))) ||
                     (position.getY() > (currentY + (position.getFontSize() * 0.9f * verticalScaling)))))
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("<newline currentY=" + currentY + ", y=" + position.getY() + 
                                  " fs=" + position.getFontSize()+ " lb fs=" + lastBaselineFontSize + ">");
                    }
                    float diffY =(float) ((int)(Math.abs(position.getY()-currentY)*100))/100;
                    if (lastBaselineFontSize != position.getFontSize()) {
                        if (inItalic)
                            text += "</i>";
                        if (inBold)
                            text += "</b>";
                        if (inFont)
                            text += "</font>";
                        text += "</p>\n<p>";
                        text += "<font face=\""+fontName+"\">";
                        inFont = true;
                        inItalic = false;
                        inBold = false;
                    }
                    else if (diffY >= ((NRC_TextPosition)position).getLeading() &&
                            ((NRC_TextPosition)position).getLeading() != leading) {
                        if (inItalic)
                            text += "</i>";
                        if (inBold)
                            text += "</b>";
                        if (inFont)
                            text += "</font>";
                        text += "</p>\n<p>";
                        text += "<font face=\""+fontName+"\">";
                        inFont = true;
                        inItalic = false;
                        inBold = false;
                    }
//                    else if (position.getX() > xbegin+position.getXScale()) {
//                        if (inItalic)
//                            text += "</i>";
//                        if (inBold)
//                            text += "</b>";
//                        if (inFont)
//                            text += "</font>";
//                        text += "</p>\n<p>";
//                        text += "<font face=\""+fontName+"\">";
//                        inFont = true;
//                        inItalic = false;
//                        inBold = false;                        
//                    }
                    else 
                        text += "\n"+lineSeparator;
                    
                    endOfLastTextX = -1;
                    startOfNextWordX = -1;
                    currentY = -1;
                    lastBaselineFontSize = -1;
               }
    
                if (startOfNextWordX != -1 && startOfNextWordX < position.getX() &&
                   lastProcessedCharacter != null &&
                   //only bother adding a space if the last character was not a space
                   lastProcessedCharacter.getCharacter() != null &&
                   !lastProcessedCharacter.getCharacter().endsWith( " " ) )
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("<space startOfNextWordX=" + startOfNextWordX + ", x=" + position.getX() + ">");
                    }
                    text +=  wordSeparator;
                }
    
    
                if (log.isDebugEnabled())
                {
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
                }
    
                if (currentY == -1)
                {
                    currentY = position.getY();
                }
    
                if (currentY == position.getY())
                {
                    lastBaselineFontSize = position.getFontSize();
                }
    
                // RDD - endX is what PDF considers to be the x coordinate of the
                // end position of the text.  We use it in computing our metrics below.
                //
                endOfLastTextX = position.getX() + position.getWidth();
    
    
                if (characterValue != null) {
                    String fontParams[] =getFontName(position.getFont()); 
                    if (position.getFont() == font)
                        text += toHTMLEntities(characterValue);
                    else {
                        font = position.getFont();
                        output.write(text);
                        if (inBold)
                            output.write("</b>");
                        if (inItalic)
                            output.write("</i>");
                        if (inFont)
                            output.write("</font>");
                        inItalic = false;
                        inBold = false;
                        fontName = fontParams[0];
                        output.write("<font face=\""+fontName+"\">");
                        inFont = true;
                        if (fontParams[1].equals("b")) {
                            output.write("<b>");
                            inBold = true;
                        }
                        if (fontParams[2].equals("i")) {
                            output.write("<i>");
                            inItalic = true;
                        }
                        text = toHTMLEntities(characterValue);
                    }
                } else
                {
                    log.debug( "Position.getString() is null so not writing anything" );
                }
                lastProcessedCharacter = position;
                leading = ((NRC_TextPosition)position).getLeading();
            }
            output.write(text);
            if (inFont)
                output.write("</font>");
            if (inItalic)
                output.write("</i>");
            if (inBold)
                output.write("</b");
            endParagraph();
        }
        

        // RDD - newline at end of flush - required for end of page (so that the top
        // of the next page starts on its own line.
        //
        if( log.isDebugEnabled() )
        {
            log.debug("<newline endOfFlush=\"true\">");
        }
        output.write(pageSeparator);

        output.flush();
    }
    
    /**
     * @see PDFTextStripper#endDocument( PDDocument )
     */
    public void endDocument(PDDocument pdf) throws IOException 
    {
        output.write("</body></html>");      
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
            getOutput().write("<p>");
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
            getOutput().write("</p>");
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
    private Pattern patternFontName = Pattern.compile("(.{6}\\x2B)?([^,]+)([,].+)?$");

    private String[] getFontName(PDFont font) {
        String[] res = new String[3];
        res[1] = "";
        res[2] = "";
        String name = font.getBaseFont();
        Matcher mp = patternFontName.matcher(name);
        if (mp.find()) {
            String fname = mp.group(2).toLowerCase();
            String fnameParts[] = fname.split("-");
            if (fnameParts.length==2) {
                res[0] = fnameParts[0];
                if (fnameParts[1].equals("bold")) {
                    res[1] = "b";
                } else if (fnameParts[1].equals("boldoblique") || fnameParts[1].equals("bolditalic")) {
                    res[1] = "b";
                    res[2] = "i";
                } else if (fnameParts[1].equals("oblique") || fnameParts[1].equals("italic")) {
                    res[2] = "i";
                } else if (fnameParts[1].equals("roman")) {
                }
            } else {
                fname = fnameParts[0];
                String inukFname = null;
                for (int i=0; i<Font.fonts.length; i++)
                    if (fname.startsWith(Font.fonts[i]))
                    {
                        inukFname = Font.fonts[i];
                        String type = fname.substring(inukFname.length());
                        Pattern bold = Pattern.compile("b.*?l?.*?d");
                        Matcher mbold = bold.matcher(type);
                        if (mbold.find())
                            res[1] = "b";
                        Pattern ital = Pattern.compile("(it)|(ob)");
                        Matcher mital = ital.matcher(type);
                        if (mital.find())
                            res[2] = "i";
                        res[0] = inukFname;
                        break;
                    }
            }
            return res;
        }
        else
            return null;
    }
    
    private String toHTMLEntities(String str) {
        String res = "";
        for (int i = 0; i < str.length(); i++) 
        {
            char c = str.charAt(i);
            if ((c < 32) || (c > 126)) 
            {
                int charAsInt = c;
               res += "&#" + charAsInt + ";";
            } 
            else 
            {
                switch (c) 
                {
                    case 34:
                        res += "&quot;";
                        break;
                    case 38:
                        res += "&amp;";
                        break;
                    case 60:
                       res += "&lt;";
                        break;
                    case 62:
                        res += "&gt;";
                        break;
                    default:
                        res += c;
                }
            }
        }
        return res;
    }
}