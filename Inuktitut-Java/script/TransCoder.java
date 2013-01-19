/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Jan 25, 2006
 * par / by Benoit Farley
 * 
 */
package script;

import html.HtmlEntities;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

//import org.apache.log4j.Logger;

import fonts.Font;

public class TransCoder {
	/**
	#	 * Log4j logger
	#	 */
//    private static Logger LOG = Logger.getLogger("TransCoder");

    // Make a hash table the keys of which are the sequences of
    // legacy codes and the values, the unicodes, or inversely, depending
    // on the direction of the transcoding.
    
    int maxLatLength;
    Hashtable conversionHash;
    String dotCodes = null;
    
    /* 
     * conversionTable: { {<code unicode>,<code de la police>}, {}, ... }
     * direction: 1 = ToUnicode      -1 = ToLegacy
     */
    public TransCoder(String [][] conversionTable, int direction) {
        maxLatLength = 0;
        conversionHash = new Hashtable();
        for (int i = 0; i < conversionTable.length; i++) {
            String key = "";
            String val = null;
            switch (direction) {
            case 1:
                key = conversionTable[i][1];
                val = conversionTable[i][0];
                break;
            case -1:
                key = conversionTable[i][0];
                val = conversionTable[i][1];
            }
            /*
             * Si la table de conversion Unicode/Police contient plus d'une
             * entr�e pour une m�me cl�, on utilise toujours la premi�re.
             */
            if (!conversionHash.containsKey(key)) {
                conversionHash.put(key,val);
                maxLatLength = Math.max(maxLatLength, key.length());
            }
        }
    }
    
    static String prepareForPattern(String dotCodes) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<dotCodes.length(); i++) {
            sb.append("\\"+dotCodes.charAt(i));
        }
        return sb.toString();
    }
    
    /* 
     * conversionTable: { {<code unicode>,<code de la police>}, {}, ... }
     * direction: 1 = ToUnicode      -1 = ToLegacy
     */
    public TransCoder(String [][] conversionTable, String dotCodes, int direction) {
        maxLatLength = 0;
        this.dotCodes = dotCodes;
        conversionHash = new Hashtable();
        /*
         * Préparer une expression régulière reconnaisant les codes de point dans
         * une chaîne.
         */
        Pattern p = Pattern.compile(prepareForPattern(dotCodes));
        
        /*
         * Pour chaque �l�ment de la table de conversion Unicode/Police:
         */
        for (int i = 0; i < conversionTable.length; i++) {
            /*
             * D�terminer la cl� et la valeur de la table du transcodeur, selon la
             * direction du transcodage.
             */
            String key = "";
            String val = null;
            switch (direction) {
            case 1:
                key = conversionTable[i][1];
                val = conversionTable[i][0];
                break;
            case -1:
                key = conversionTable[i][0];
                val = conversionTable[i][1];
                break;
            }
            Matcher m = p.matcher(key);
            
            /*
             * S'il y a un
             * code de point, on ajoute � la table du transcodeur autant
             * d'entr�es qu'il y a de codes de point dans la police, chacune
             * avec un code de point diff�rent. Ceci est n�cessaire � cause de
             * la r�alit� qui d�montre que dans les documents, on a souvent une
             * combinaison code-de-point/code diff�rente de celle qui est
             * prescrite pour les caract�res point�s.
             */
            
            // Est-ce que la cl� contient un code de point?
            if (m.find()) {
                // La cl� contient un code de point.
                /* 
                 * Ajouter une entr�e additionnelle avec la m�me valeur et avec une
                 * cl� avec chacun des codes de point.
                 */
                for (int j=0; j<dotCodes.length(); j++) {
                    /*
                     * Remplacer le code de point original par un autre code de point
                     * pour former une nouvelle cl�.
                     */
                    String keyWithDot = m.replaceFirst(dotCodes.substring(j,j+1));
                    /*
                     * Si la table de conversion Unicode/Police contient plus d'une
                     * entr�e pour une m�me cl�, on utilise toujours la premi�re.
                     */
                    if (!conversionHash.containsKey(keyWithDot)) {
                        conversionHash.put(keyWithDot,val);
                        maxLatLength = Math.max(maxLatLength, keyWithDot.length());
                    }
                }
            } else {
                // La cl� ne contient pas un code de point
                /*
                 * Si la table de conversion Unicode/Police contient plus d'une
                 * entr�e pour une m�me cl�, on utilise toujours la premi�re.
                 */
                if (!conversionHash.containsKey(key)) {
                    conversionHash.put(key,val);
                    maxLatLength = Math.max(maxLatLength, key.length());
                }
            }
        }
    }
    
    public String transcode(String src) {
        if (src.length()==0 || src == null)
            return src;
        String output = "";
        int location = 0;
        
        
        while (location < src.length()) {
            int len = Math.min(maxLatLength, src.length() - location);
            String arr = null;
            String sub = "";
            while (len > 0) {
                sub = src.substring(location, location+len);
                arr = (String)conversionHash.get(sub);
                if (arr != null) 
                    break;
                else 
                    len--;
            }
            
            if (arr == null) {
                output += sub;
                location ++;
            }
            else {
                // case analysis
                output += arr;
                location += len;
            }
        }
        return output;
    }
    
//    public String transcodeSkipUnknown(String src) {
//        if (src.length()==0 || src == null)
//            return src;
//        String output = "";
//        int location = 0;
//        
//        while (location < src.length()) {
//            int len = Math.min(maxLatLength, src.length() - location);
//            String arr = null;
//            String sub = "";
//            while (len > 0) {
//                sub = src.substring(location, location+len);
//                arr = (String)conversionHash.get(sub);
//                if (arr != null) 
//                    break;
//                else 
//                    len--;
//            }
//            
//            if (arr == null) {
//                // If unknown character, don't add the original character.
////                output += sub;
//                location ++;
//            }
//            else {
//                // case analysis
//                output += arr;
//                location += len;
//            }
//        }
//        return output;
//    }
    
    /*
     * -----------------------------------------------------------------------
     * TRANSCODING FROM ONE FORMAT TO ANOTHER
     * -----------------------------------------------------------------------
     */
    
    public static String  unistringToUnicode(String s) {
        return unistringToUnicode(s,"2,4");
    }
    
    public static String unistringToUnicode(String s, String nbHex) {
        String str = "";
        Pattern p = Pattern.compile("\\\\u([a-fA-F0-9]{"+nbHex+"})");
        Matcher mp = p.matcher(s);
        int pos = 0;
        while (pos < s.length() && mp.find(pos)) {
            str = str + s.substring(pos,mp.start());
            String ns = mp.group(1);
            char i = (char)Integer.valueOf(ns,16).intValue();
            str = str + i;
            pos = mp.end();
        }
        str = str + s.substring(pos);
        return str;
    }

    public static String unicodeToUrlencoding(String word) {
    	String wordUtf8 = null;
    	try {
			wordUtf8 = new String(word.getBytes("utf-8"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			wordUtf8 = word;
		}
        String w = "";
        for (int j=0; j<wordUtf8.length(); j++) {
            String n = Integer.toHexString((int)wordUtf8.charAt(j));
            int nb0 = 2-n.length();
            String zeros = "";
            for (int k=0; k<nb0; k++)
                zeros += "0";
            w = w + "%" + n.toUpperCase() ;
        }
        return w;
    }
    
    public static String unicodeToHtmlEntity(String uni) {
        String text = HtmlEntities.toHtmlEntity(uni);
        String html = text.replaceAll("&", "&amp;");
        return html;
    }


    public static String unicodeToUnistring(String word) {
        String w = "";
        for (int j=0; j<word.length(); j++) {
            String n = Integer.toHexString((int)word.charAt(j));
            String ns = String.valueOf(n);
            int nb0 = 4-ns.length();
            String zeros = "";
            for (int k=0; k<nb0; k++)
                zeros += "0";
            w = w + "\\u" + zeros + ns;
        }
        return w;
    }

    /*
     * Transliterate from the roman alphabet to Unicode syllabics
     */
//    public static String romanToUnicode(String s) {
//        TransCoder transcoder = Syllabics.getTranscoder();
//        String syll = transcoder.transcode(s.toLowerCase());
//        return syll;
//    }

    public static String romanToUnicode(String s) {
        String syll = Roman.transcodeToUnicode(s,null);
        return syll;
    }

    public static String romanToUnicode(String s, boolean aipaitai) {
        String apt = aipaitai?"aipaitai":null;
        String syll = Roman.transcodeToUnicode(s,apt);
        return syll;
    }
    
    public static String romanToLegacy(String s, String fontName) {
        String syl = romanToUnicode(s);
        String leg = unicodeToLegacy(syl,fontName);
        return leg;
    }

    /*
     * Transliterate from Unicode syllabics to the roman alphabet
     */
//    public static String unicodeToRoman(String s) {
//        TransCoder transcoder = Roman.getTranscoder();
//        String roman = transcoder.transcode(s);
//        return roman;
//    }
    
    public static String unicodeToRoman(String s) {
        String roman = Syllabics.transcodeToRoman(s);
        return roman;
    }
    
    static char [] macRomanToCP1252 = {
    0xc4, 0xc5, 0xc7, 0xc9, 0xd1, 0xd6, 0xdc, 0xe1, 0xe0, 0xe2, 0xe4, 0xe3, 0xe5, 0xe7, 0xe9, 0xe8,
    0xea, 0xeb, 0xed, 0xec, 0xee, 0xef, 0xf1, 0xf3, 0xf2, 0xf4, 0xf6, 0xf5, 0xfa, 0xf9, 0xfb, 0xfc,
    0x86, 0xb0, 0xa2, 0xa3, 0xa7, 0x95, 0xb6, 0xdf, 0xae, 0xa9, 0x99, 0xb4, 0xa8, 0, 0xc6, 0xd8,
    0, 0xb1, 0, 0, 0xa5, 0xb5, 0, 0, 0, 0, 0, 0xaa, 0xba, 0, 0xe6, 0xf8,
    0xbf, 0xa1, 0xac, 0, 0x83, 0, 0, 0xab, 0xbb, 0x85, 0xa0, 0xc0, 0xc3, 0xd5, 0x8c, 0x9c,
    0x96, 0x97, 0x93, 0x94, 0x91, 0x92, 0xf7, 0, 0xff, 0x9f, 0, 0x80, 0x8b, 0x9b, 0, 0,
    0x87, 0xb7, 0x82, 0x84, 0x89, 0xc2, 0xca, 0xc1, 0xcb, 0xc8, 0xcd, 0xce, 0xcf, 0xcc, 0xd3, 0xd4,
    0, 0xd2, 0xda, 0xdb, 0xd9, 0, 0x88, 0x98, 0xaf, 0, 0, 0, 0xb8, 0, 0, 0
    };
    
    public static String macRoman2cp1252 (String s) {
    	StringBuffer sb = new StringBuffer();
    	for (int i=0; i<s.length(); i++) {
    		int c = s.codePointAt(i);
//    		LOG.debug("c= "+c);
    		if (c >= 0x80 && c <= 0xff)
    			sb.append(macRomanToCP1252[c-0x80]);
    		else
    			sb.append((char)c);
    	}
    	return sb.toString();
    }
    
    public static String windows1252Toiso88591 (String s) {
    	StringBuffer sb = new StringBuffer();
    	for (int i=0; i<s.length(); i++) {
    		char c;
    		switch (s.codePointAt(i)) {
    		case 128: c = '\u20AC'; break;
    		case 130: c = '\u201A'; break;
    		case 131: c = '\u0192'; break;
    		case 132: c = '\u201E'; break;
    		case 133: c = '\u2026'; break;
    		case 134: c = '\u2020'; break;
    		case 135: c = '\u2021'; break;
    		case 136: c = '\u02C6'; break;
    		case 137: c = '\u2030'; break;
    		case 138: c = '\u0160'; break;
    		case 139: c = '\u2039'; break;
    		case 140: c = '\u0152'; break;
    		case 142: c = '\u017D'; break;
    		case 145: c = '\u2018'; break;
    		case 146: c = '\u2019'; break;
    		case 147: c = '\u201C'; break;
    		case 148: c = '\u201D'; break;
    		case 149: c = '\u2022'; break;
    		case 150: c = '\u2013'; break;
    		case 151: c = '\u2014'; break;
    		case 152: c = '\u02DC'; break;
    		case 153: c = '\u2122'; break;
    		case 154: c = '\u0161'; break;
    		case 155: c = '\u203A'; break;
    		case 156: c = '\u0153'; break;
    		case 158: c = '\u017E'; break;
    		case 159: c = '\u0178'; break;
    		default: c = s.charAt(i); break;
    		}
    		sb.append(c);
    	}
    	return sb.toString();
    }
    
    /*
     * Transliterate from legacy font syllabics to the roman alphabet
     */
    public static String legacyToRoman(String s, String fontName) {
    	String sNormalized = windows1252Toiso88591(s);
        String syl = legacyToUnicode(sNormalized, fontName);
        String roman = unicodeToRoman(syl);
        return roman;
    }
    
    /*
     * Transcode from Unicode to a legacy font syllabics
     */
    public static String unicodeToLegacy(String s, String fontName) {
        String res = "";
        Method meth = Font.getTranscoder3(fontName,"ToFont");
        if (meth != null)
        try {
				res = (String) meth.invoke(null, new Object[] { s });
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
        return res;
    }
    
    /*
     * Transcode from a legacy font syllabics to Unicode syllabics
     */
    public static String legacyToUnicode(String s, String fontName) {
        String res = legacyToUnicode(s,fontName,false);
        return res;
    }

    public static String legacyToUnicode(String s, String fontName, boolean aipaitai) {
//        if (LOG.isDebugEnabled())
//        	LOG.debug("avant appel à windows1252Toiso88591 --- s = '"+s+"'");
    	String sNormalized = windows1252Toiso88591(s);
//        if (LOG.isDebugEnabled())
//        	LOG.debug("après appel à windows1252Toiso88591 --- sNormalized = '"+sNormalized+"'");
    	// dotCodes
    	String dotCodes = null;
    	try {
    		Class fontClass = Font.getFontClass(fontName);
    		if (fontClass != null) {
    			Field field = fontClass.getDeclaredField("dotCodes");
    			if (field != null)
    				dotCodes = (String) field.get(null);
    		}
		} catch (IllegalArgumentException e1) {
		} catch (SecurityException e1) {
		} catch (IllegalAccessException e1) {
		} catch (NoSuchFieldException e1) {
		}
//        if (LOG.isDebugEnabled())
//        	LOG.debug("dotCodes = '"+dotCodes+"'");
		
		if (dotCodes != null) {
			String dotCodesForPattern = prepareForPattern(dotCodes);
//	        if (LOG.isDebugEnabled())
//	        	LOG.debug("dotCodesForPattern = '"+dotCodesForPattern+"'");
			Pattern patDotCodes = Pattern.compile("([" + dotCodesForPattern
					+ "])\\s+");
			patDotCodes = Pattern.compile("(([" + dotCodesForPattern
					+ "])\\s+)");
			Matcher mpatDotCodes = patDotCodes.matcher(sNormalized);
			int pos = 0;
			while (mpatDotCodes.find(pos)) {
				String replacementPattern = prepareForPattern(mpatDotCodes.group(1));
				String replacementValue = mpatDotCodes.group(2);
				try {
				sNormalized = sNormalized.replaceFirst(replacementPattern,
						replacementValue);
				} catch (PatternSyntaxException e) {
//			        if (LOG.isDebugEnabled())
//			        	LOG.debug("PatternSyntaxException -- sNormalized = '"+sNormalized+"'"
//			        			+" ; replacementPattern = '"+replacementPattern+"' ; replacementValue = '"
//			        			+ replacementValue+"'");
			        e.printStackTrace(System.err);
				}
				pos = mpatDotCodes.end();
				mpatDotCodes = patDotCodes.matcher(sNormalized);
			}
		}
        String res = "";
        String apt = aipaitai? "aipaitai":null;
        Method meth = Font.getTranscoder3(fontName,"ToUnicode");
        if (meth != null)
			try {
				res = (String) meth.invoke(null, new Object[] { sNormalized, apt });
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
        return res;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String translit, str;
        if (args.length > 1 && Font.isLegacy(args[1])) {
            str = HtmlEntities.entityToChar(args[0]);
            str = unistringToUnicode(str,"4");
            translit = legacyToRoman(str,args[1]);
            String translitEncoded = URLEncoder.encode(translit,"utf-8");
            System.out.print(translitEncoded);
        }
        else {
        	str = URLDecoder.decode(args[0],"utf-8");
            str = HtmlEntities.entityToChar(str);
            str = unistringToUnicode(str,"4");
        	translit = unicodeToRoman(str);
            String translitEncoded = URLEncoder.encode(translit,"utf-8");
            System.out.print(translit);
        }
    }

}
