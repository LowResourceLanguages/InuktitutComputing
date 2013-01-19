// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File: TranslitInuk.java
//
// Type/File type: code Java / Java code
// 
// Auteur/Author: Benoit Farley
//
// Organisation/Organization: Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation: 9 avril 2002 / April 9, 2002
//
// Description: Entr�e d'un programme qui fait la translit�ration d'une page
//              HTML du syllabaire inuktitut en caract�res latins.
//
// -----------------------------------------------------------------------

package translit;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import documents.NRC_HTMLDocumentByCobra;

public class TCIHTML {

    private static Logger LOG = Logger.getLogger(TCIHTML.class);

    public static void main(String[] args) {
    	String font = "pigiarniq";
		String command = args[0];
    	boolean aipaitai = false;
    	if (args.length < 4) {
    		LOG.error("Missing argument(s)\n"+usage(command));
    		System.exit(1);
    	}
    	try {
			URL url = new URL(args[1]);
			String option = args[2].toLowerCase();
			if ( !option.equals("l") && !option.equals("u") ) {
				LOG.error("Wrong second argument\n"+usage(command));
	    		System.exit(1);
			}
			if ( !args[3].equals("0") && !args[3].equals("1") ) {
				LOG.error("Wrong third argument\n"+usage(command));
	    		System.exit(1);
			}
			if (args[3].equals("1"))
				aipaitai = true;
			if (args.length == 4)
				font = args[4];
		} catch (MalformedURLException e1) {
			LOG.error("The URL is malformed: "+e1.getMessage());
    		System.exit(1);
		}
        try {
        	LOG.debug("args[1] = "+args[1]);
        	LOG.debug("args[2] = "+args[2]);
        	LOG.debug("args[3] = "+args[3]);
            translittererPage(args[1], args[2], aipaitai, font, new PrintStream(System.out, true, "utf-8"));
        } catch (Exception e) {
        }
    }
    
    public static void translittererPage(String urlName, String direction, boolean aipaitai, 
    		String fontName, PrintStream out)  {
        NRC_HTMLDocumentByCobra doc = null;
		try {
			doc = new NRC_HTMLDocumentByCobra(urlName);
			if (direction.equals("l"))
				doc.toRoman(out);
			else
				doc.toUnicode2(out, aipaitai, fontName);
		} catch (Exception e) {
			LOG.error("ERROR from NRC_HTMLDocumentByCobra: "+e.getMessage());
		} finally {
			if (doc != null) doc.close();
		}
    }
    
    private static String usage(String com) {
    	return "Usage: "+com+" tcihtml <url> <conversion> <aipaitai> <unicode font>*\n\t"
		+ "<conversion> - l or u; l : transliterate to the latin alphabet ; u : transliterate to Unicode syllabics\n\t"
		+ "<aipaitai> - 0 or 1; 1 means that aipaitai characters will be used"
		+ "<unicode font> - any unicode font with inuktitut syllabics; pigiarniq by default";
    }


}