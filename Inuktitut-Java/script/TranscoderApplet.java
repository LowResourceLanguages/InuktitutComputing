/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Jul 14, 2006
 * par / by Benoit Farley
 * 
 */

/*
 * This applet is used by the Inuktitut Search Engine to have access to the
 * transcoding methods.
 */

package script;

import java.applet.Applet;


public class TranscoderApplet extends Applet {
        
    private static final long serialVersionUID = 1L;

    public String legacyToUnicode (String text, String font) {
        return TransCoder.legacyToUnicode(text, font);
    }
    
    public String unicodeToLegacy (String text, String font) {
        return TransCoder.unicodeToLegacy(text, font);
    }
    
    public String romanToUnicode (String text) {
        return TransCoder.romanToUnicode(text);
    }
    
    public String unicodeToRoman (String text) {
        return TransCoder.unicodeToRoman(text);
    }

}
