/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Feb 7, 2006
 * par / by Benoit Farley
 * 
 */
package html;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.tidy.EntityTable;

public final class HtmlEntities {

    public static Pattern pEntity = Pattern.compile("(&[^;]+?);");
    static Pattern pEnt = Pattern.compile("&[^;]+?;");
    public static EntityTable et = EntityTable.getDefaultEntityTable();
    public static Pattern pHtmlEntity = Pattern.compile("&((#[xX]?[a-fA-F0-9]+)|([a-zA-Z0-9]+))");
    
    /*
     * Note: The functions in EntityTable.class do not take the final ';' into
     * consideration.
     */
   
    /*
     * Replace the HTML entities with their corresponding characters.
     */
    static public String entityToChar(String str) {
        Matcher mpEntity;
        int pos = 0;
        int limit = str.length();
        StringBuffer sb = new StringBuffer();
        mpEntity = pHtmlEntity.matcher(str);
        while (pos < limit) {
            if (mpEntity.find(pos)) {
                String ent = new String(mpEntity.group());
                sb.append(str.substring(pos,mpEntity.start()));
                short d = et.entityCode(ent);
                char c = (char)d;
                sb.append(c);
                pos = mpEntity.end();
                // skip ';'
                if (pos < limit && str.charAt(pos)==';')
                    pos++;
            }
            else {
                sb.append(str.charAt(pos++));      
            }
            }
        return sb.toString();
        }
    
    static public String entityToChar(DynamicArrayOfByte bs, String encoding) {
        byte[] bytes = bs.getBytes();
        String str = null;
        try {
            if (encoding==null)
                str = new String(bytes);
            else
                str = new String(bytes,encoding);
        } catch (UnsupportedEncodingException e) {
        }
        String str1 = entityToChar(str);
        return str1;
    }
    
    /*
     * Replace the HTML entities corresponding to Unicode syllabic characters
     * with those characters; all other HTML entities are left as is.
     */
    static public String toStringInuktitut(String str) {
        Matcher mpEntity;
        int pos = 0;
        int limit = str.length();
        StringBuffer sb = new StringBuffer();
        while (pos < limit) {
            mpEntity = pHtmlEntity.matcher(str);
            if (mpEntity.find(pos)) {
                String ent = new String(mpEntity.group());
                if (et.entityCode(ent) > 0x1400 && et.entityCode(ent) < 0x167F) {
                    sb.append(str.substring(pos,mpEntity.start()));
                    sb.append((char)et.entityCode(ent));
                    pos = mpEntity.end();
                    // skip ';'
                    if (pos < limit && str.charAt(pos)==';')
                        pos++;
                } else {
                    sb.append(str.substring(pos,mpEntity.start()));
                    sb.append(mpEntity.group());
                    pos = mpEntity.end();
                }
            }
            else
                sb.append(str.charAt(pos++));      
            }
        return sb.toString();
        }
    
    /*
     * Returns a substring of a string that contains html entities; this
     * function considers an html entity as one character/position.
     */
    static public String substring(String str, int startPos) {
        return substring(str,startPos,str.length());
    }
    
    static public String substring(String str, int startPos, int endPos) {
        Matcher m = pHtmlEntity.matcher(str);
        int pos = 0;
        int cntr = 0;
        String sub = "";
        int limit = str.length();
        while (pos < str.length()) {
            if (m.find(pos) && m.start()==pos) {
                if (cntr >= startPos && cntr < endPos) {
                    sub += str.substring(pos,m.end());
                    if (str.charAt(m.end())==';')
                        sub += ";";
                }
                cntr++;
                pos = m.end();
                // skip ';'
                if (pos < limit && str.charAt(pos)==';') {
                    pos++;
                }
            } else {
                if (cntr >= startPos && cntr < endPos)
                    sub += str.charAt(pos);
                cntr++;
                pos += 1;
            }
        }
        return sub;
    }

    /*
     * Convert from HTML entities to characters.
     */
    public static String fromHTMLEntity(String s) {
        String str = entityToChar(s);
        return str;
    }

    /*
     * Convert from characters to HTML entities.
     */
    public static String toHtmlEntity(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            sb.append("&#"+Integer.toString((int)s.charAt(i),10)+";");
        }
        return sb.toString();
    }
    
    }

