/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Feb 16, 2006
 * par / by Benoit Farley
 * 
 */

/*
 * Cette m�thode convertit une cha�ne de texte d'un mode � un autre: les modes
 * sont: unicode, unicode \\uxxxx, nunacom, prosyl, aipainunavik, roman alphabet
 * et lorsqu'on convertit � unicode, on peut sp�cifier si l'on doit utiliser les
 * caract�res syllabiques de la s�rie 'aipaitai'.
 */
package script.exec;

import org.apache.log4j.Logger;

import html.HtmlEntities;
import script.Syllabics;
import script.TransCoder;
import utilities1.Util;

public class TranscodingWebApp {

    private static Logger LOG = Logger.getLogger(TranscodingWebApp.class);

    public static String transcode(String args[]) {
        String text = "";
        String from = Util.getArgument(args, "From");
        String to = Util.getArgument(args, "To");
        String toaipaitai = Util.getArgument(args, "ToAipaitai");
        boolean aipaitai = false;
        if (toaipaitai != null)
        	aipaitai = true;
        String fromLC = from.toLowerCase();
        String toLC = to.toLowerCase();
        String source = Util.getArgument(args, "source");
        LOG.debug("source= "+source);
        /*
         * Les codes Unicode sont encod�s en entit�s HTML. Ex.: &#5123;
         */
//       source = HtmlEntities.fromHTMLEntity(source);
        if (fromLC.equals("unicode")) {
            // FROM UNICODE
            if (toLC.equals("roman alphabet")) {
                text = TransCoder.unicodeToRoman(source);
            } else if (toLC.equals("url encoding")) {
            	text = TransCoder.unicodeToUrlencoding(source);
           } else if (toLC.equals("unicode")) {
                if (toaipaitai != null)
                    text = Syllabics.unicodeICItoAIPAITAI(source);
                else
                    text = source;
            } else if (toLC.equals("unicode \\uxxxx")) {
                if (toaipaitai != null)
                    text = TransCoder.unicodeToUnistring(Syllabics
                            .unicodeICItoAIPAITAI(source));
                else
                    text = TransCoder.unicodeToUnistring(source);
            } else if (toLC.equals("html entity")) {
                if (toaipaitai != null)
                    text = Syllabics.unicodeICItoAIPAITAI(source);
                else
                    text = source;
                text = TransCoder.unicodeToHtmlEntity(text);
            } else
                text = TransCoder.unicodeToLegacy(source, toLC);
        } else if (fromLC.equals("unicode \\uxxxx")) {
            // FROM UNICODE \UXXXX
            String uni = TransCoder.unistringToUnicode(source);
            if (toLC.equals("roman alphabet")) {
                text = TransCoder.unicodeToRoman(uni);
        	} else if (toLC.equals("url encoding")) {
        		text = TransCoder.unicodeToUrlencoding(uni);
        	}
        	else if (toLC.equals("unicode"))
                if (toaipaitai != null)
                    text = Syllabics.unicodeICItoAIPAITAI(uni);
                else
                    text = Syllabics.unicodeAIPAITAItoICI(uni);
            else if (toLC.equals("unicode \\uxxxx"))
                if (toaipaitai != null)
                    text = TransCoder.unicodeToUnistring(Syllabics
                            .unicodeICItoAIPAITAI(uni));
                else
                    text = source;
            else
                text = TransCoder.unicodeToLegacy(uni, toLC);
        } else if (fromLC.equals("html entity")) {
                // FROM UNICODE HTML ENTITY
                String uni = HtmlEntities.fromHTMLEntity(source);
                if (toLC.equals("roman alphabet"))
                    text = TransCoder.unicodeToRoman(uni);
                else if (toLC.equals("html entity")) {
                	text = source;
                	text = text.replaceAll("&", "&amp;");
                }
                else if (toLC.equals("url encoding")) {
                	text = TransCoder.unicodeToUrlencoding(uni);
                }
                else if (toLC.equals("unicode"))
                    if (toaipaitai != null)
                        text = Syllabics.unicodeICItoAIPAITAI(uni);
                    else
                        text = Syllabics.unicodeAIPAITAItoICI(uni);
                else if (toLC.equals("unicode \\uxxxx"))
                    if (toaipaitai != null)
                        text = TransCoder.unicodeToUnistring(Syllabics
                                .unicodeICItoAIPAITAI(uni));
                    else
                        text = source;
                else
                    text = TransCoder.unicodeToLegacy(uni, toLC);
        } else if (fromLC.equals("roman alphabet")) {
            // FROM ROMAN ALPHABET
            if (toLC.equals("aipainunavik") || toLC.equals("aipainuna")
                    || toLC.equals("ainunavik")) {
                text = TransCoder.romanToUnicode(source, true);
                text = TransCoder.unicodeToLegacy(text, to);
            } else if (toLC.equals("roman alphabet")) {
                text = source;
            } else if (toLC.equals("url encoding")) {
            	text = TransCoder.romanToUnicode(source, true);
            	text = TransCoder.unicodeToUrlencoding(text);
            } else if (toLC.equals("unicode")) {
                if (toaipaitai != null)
                    text = TransCoder.romanToUnicode(source, true);
                else
                    text = TransCoder.romanToUnicode(source);
            } else if (toLC.equals("unicode \\uxxxx")) {
                if (toaipaitai != null)
                    text = TransCoder.romanToUnicode(source, true);
                else
                    text = TransCoder.romanToUnicode(source);
                text = TransCoder.unicodeToUnistring(text);
            } else if (toLC.equals("html entity")) {
                if (toaipaitai != null)
                    text = TransCoder.romanToUnicode(source, true);
                else
                    text = TransCoder.romanToUnicode(source);
                text = TransCoder.unicodeToHtmlEntity(text);
            } else {
                text = TransCoder.romanToUnicode(source);
                text = TransCoder.unicodeToLegacy(text, to);
            }
        }  else {
        // FROM LEGACY
        if (toLC.equals("aipainunavik") || toLC.equals("ainunavik")
                || toLC.equals("aipainuna")) {
            text = TransCoder.legacyToUnicode(source, from, true);
            text = TransCoder.unicodeToLegacy(text, to);
        } else if (toLC.equals("roman alphabet")) {
            text = TransCoder.legacyToRoman(source, from);
        } else if (toLC.equals("url encoding")) {
        	text = TransCoder.legacyToUnicode(source, from);
        	text = TransCoder.unicodeToUrlencoding(text);
        } else if (toLC.equals("unicode")) {
            text = TransCoder.legacyToUnicode(source, from, aipaitai);
        } else if (toLC.equals("unicode \\uxxxx")) {
            text = TransCoder.legacyToUnicode(source, from, aipaitai);
            text = TransCoder.unicodeToUnistring(text);
        } else if (toLC.equals("html entity")) {
            text = TransCoder.legacyToUnicode(source, from, aipaitai);
            text = TransCoder.unicodeToHtmlEntity(text);
        } else {
            text = TransCoder.legacyToUnicode(source, from);
            text = TransCoder.unicodeToLegacy(text, to);
        }
        }

        return text;
    }

}
