package documents.pdfboxAddition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.util.PDFTextStripper;


/**
 * Highlighting of words in a PDF document with an XML file.
 * 
 * @author slagraulet (slagraulet@cardiweb.com)
 * @author Ben Litchfield (ben@csh.rit.edu)
 * @version $Revision: 1.1 $
 * 
 * @see <a href="http://partners.adobe.com/public/developer/en/pdf/HighlightFileFormat.pdf">
 *      Adobe Highlight File Format</a>
 */
public class NRC_PDFHighlighter extends NRC_PDFTextStripper 
{
    private static Logger log = Logger.getLogger(NRC_PDFHighlighter.class);

    private Writer highlighterOutput = null;
    //private Color highlightColor = Color.YELLOW;

    private String[] searchedWords;
    private ByteArrayOutputStream textOS = null;
    private Writer textWriter = null;
    // Added by Benoit Farley
    private Vector foundWords;

    /**
     * Default constructor.
     * 
     * @throws IOException If there is an error constructing this class.
     */
    public NRC_PDFHighlighter() throws IOException 
    {
        super();
        super.setLineSeparator( "" );
        super.setPageSeparator( "" );
        super.setWordSeparator( "" );
        super.setShouldSeparateByBeads( false );
        super.setSuppressDuplicateOverlappingText( false );
        if( log.isDebugEnabled() )
        {
            log.debug( "--------------------------------------------------");
        }
    }
    
    /**
     * Generate an XML highlight string based on the PDF.
     * 
     * @param pdDocument The PDF to find words in.
     * @param highlightWord The word to search for.
     * @param xmlOutput The resulting output xml file.
     * 
     * @throws IOException If there is an error reading from the PDF, or writing to the XML.
     */
    public void generateXMLHighlight(PDDocument pdDocument, String highlightWord, Writer xmlOutput ) throws IOException
    {
        generateXMLHighlight( pdDocument, new String[] { highlightWord }, xmlOutput );
    }

    /**
     * Generate an XML highlight string based on the PDF.
     * 
     * @param pdDocument The PDF to find words in.
     * @param sWords The words to search for.
     * @param xmlOutput The resulting output xml file.
     * 
     * @throws IOException If there is an error reading from the PDF, or writing to the XML.
     */
    public void generateXMLHighlight(PDDocument pdDocument, String[] sWords, Writer xmlOutput ) throws IOException
    {
        String ls = System.getProperty("line.separator");
        highlighterOutput = xmlOutput;
        searchedWords = sWords;
        foundWords = new Vector(); // initialization - vector filled  in endPage()
        highlighterOutput.write("<XML>"+ls+"<Body units=characters " + 
                                //color and mode are not implemented by the highlight spec
                                //so don't include them for now
                                //" color=#" + getHighlightColorAsString() + 
                                //" mode=active " + */ 
                                " version=2>"+ls+"<Highlight>");
        highlighterOutput.write(ls);
        textOS = new ByteArrayOutputStream();
        textWriter = new OutputStreamWriter( textOS, "UTF-16" );
        writeText(pdDocument, textWriter);
        highlighterOutput.write("</Highlight>"+ls+"</Body>"+ls+"</XML>");
        highlighterOutput.flush();
    }

    // Added by Benoit Farley
    /**
     * Start a new page.  Initialize the list textElementsAndBytes that will be
     * filled by showString in PDFStreamEngine.
     * 
     * @param page The page we are about to process.
     * 
     * @throws IOException If there is any error writing to the stream.
     */
    protected void startPage( PDPage page ) throws IOException
    {
        textElementsAndBytes = new ArrayList();
    }
    //------- End of addition
    

    /**
     * @see PDFTextStripper#endPage( PDPage )
     */
    protected void endPage( PDPage pdPage ) throws IOException 
    {
        textWriter.flush();

        String page = new String( textOS.toByteArray(), "UTF-16" );
        textOS.reset();
//        page = page.replaceAll( "\n", "" );
//        page = page.replaceAll( "\r", "" );
//        page = CCRStringUtil.stripChar(page, '\n');
//        page = CCRStringUtil.stripChar(page, '\r');

        /*
         * Je ne sais pas d'où cela provient, mais on ne peut pas remplacer 'a'
         * suivi d'1 à 3 chiffres par un point; ces caractères correspondent à
         * du texte en Inuktitut.  Je mets ce code en commentaires. (BF)
         */
//        // Traitement des listes à puces (caractères spéciaux)
//        if (page.indexOf("a") != -1) 
//        {
//            page = page.replaceAll("a[0-9]{1,3}", ".");
//        }

        String ls = System.getProperty("line.separator");
        
        /*
         * The words to be highlighted are searched in the text of the page by
         * matching pattern. searchedWords contains regular expressions
         * corresponding to the inuktitut words to highlight. Certain fonts use
         * a two-byte encoding for long-vowel syllabic characters, the first
         * byte of which is a non-advancing dot. There are several dot
         * characters depending on the position of the dot over the syllabic
         * character, but users often use a dot character different than the one
         * prescribed for a given long-vowel syllabic character. The regular
         * expression in searchedWords takes care of that. Also, certain codes
         * used for syllabic characters in legacy fonts are special characters
         * in regular expressions and they must be escaped. This is also taken
         * care of in the regular expressions in searchWords.
         */
        for (int i = 0; i < searchedWords.length; i++) 
        {
            /*
             * Inuktitut words in legacy fonts are case-sensitive.  Case-insensitivity
             * must not be used.
             */
//            Pattern pattern = Pattern.compile(searchedWords[i], Pattern.CASE_INSENSITIVE);
            Pattern pattern = Pattern.compile(searchedWords[i]);
            /*
             * 'page' contains the original text of the page, including duplicate
             * overlapping text.  
             */
            Matcher matcher = pattern.matcher(page);
            while( matcher.find() ) 
            {
                int begin = matcher.start();
                int end = matcher.end();
                // The actual word to be highlighted.  Add it to the list.
                String foundWord = page.substring(begin,end);
                if (!foundWords.contains(foundWord))
                    foundWords.add(foundWord);
                // The position depends on the nb. of bytes per character (encoding)
                int [] poslen = getPosLen(begin,foundWord);
                highlighterOutput.write("    <loc " +
                        "pg=" + (getCurrentPageNo()-1)
                        + " pos=" + poslen[0]
                        + " len="+ (poslen[1] - poslen[0])
                        + ">"+ls);
                if( log.isDebugEnabled() )
                {
                    log.debug( "Word found: \"" + foundWord + "\" at position " + poslen[0]);
                }
            }
        }
        textElementsAndBytes = null; // reset
    }
    
    /*
     * Added by Benoit Farley
     */
    private int[] getPosLen(int start, String word) {
        String str = new String();
        int len = 0;
        int nbytes = 0;
        int begin=0;
        int end=0;
        for (int i=0; i<textElementsAndBytes.size(); i++)  {
            NRC_TextPosition textPosition = (NRC_TextPosition)textElementsAndBytes.get(i);
            String s = textPosition.getCharacter();
            int codeLength = textPosition.getCodeLength();
            if (start < str.length()+s.length()) {
                begin = nbytes + (start-str.length())*codeLength;
                if (start+word.length() < str.length()+s.length()) {
                    end = begin + word.length()*codeLength;
                    break;
                }
            }
            nbytes += s.length()*codeLength;
            str += s;
        }
        return new int[]{begin,end};
    }
    
    
    public String[] getWordsToHighlight() {
        return (String[]) foundWords.toArray(new String[0]);
    }

    /**
     * Command line application.
     * 
     * @param args The command line arguments to the application.
     * 
     * @throws IOException If there is an error generating the highlight file.
     */
    public static void main(String[] args) throws IOException 
    {
        NRC_PDFHighlighter xmlExtractor = new NRC_PDFHighlighter();
        PDDocument doc = null;
        try
        {
            if( args.length < 2 )
            {
                usage();
            }
            String[] highlightStrings = new String[ args.length - 1];
            System.arraycopy( args, 1, highlightStrings, 0, highlightStrings.length );
            doc = PDDocument.load( args[0] );
            
            xmlExtractor.generateXMLHighlight( 
                doc, 
                highlightStrings, 
                new OutputStreamWriter( System.out ) );
        }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }
    }
    
    private static void usage()
    {
        System.err.println( "usage: java " + NRC_PDFHighlighter.class.getName() + " <pdf file> word1 word2 word3 ..." );
        System.exit( 1 );
    }
    
    
    /**
     * Get the color to highlight the strings with.  Default is Color.YELLOW.
     * 
     * @return The color to highlight strings with.
     */
    /*public Color getHighlightColor()
    {
        return highlightColor;
    }**/
    
    /**
     * Get the color to highlight the strings with.  Default is Color.YELLOW.
     * 
     * @param color The color to highlight strings with.
     */
    /*public void setHighlightColor(Color color)
    {
        this.highlightColor = color;
    }**/
    
    /**
     * Set the highlight color using HTML like rgb string.  The string must be 6 characters long.
     * 
     * @param color The color to use for highlighting.  Should be in the format of "FF0000".
     */
    /*public void setHighlightColor( String color )
    {
        highlightColor = Color.decode( color );
    }**/
    
    /**
     * Get the highlight color as an HTML like string.  This will return a string of six characters.
     * 
     * @return The current highlight color.  For example FF0000
     */
    /*public String getHighlightColorAsString()
    {
        //BJL: kudos to anyone that has a cleaner way of doing this!
        String red = Integer.toHexString( highlightColor.getRed() );
        String green = Integer.toHexString( highlightColor.getGreen() );
        String blue = Integer.toHexString( highlightColor.getBlue() );
        
        return (red.length() < 2 ? "0" + red : red) + 
               (green.length() < 2 ? "0" + green : green) + 
               (blue.length() < 2 ? "0" + blue : blue); 
    }**/
}