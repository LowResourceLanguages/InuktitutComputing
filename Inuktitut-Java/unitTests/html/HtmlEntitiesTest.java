/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Feb 20, 2006
 * par / by Benoit Farley
 * 
 */
package unitTests.html;

import html.HtmlEntities;
import junit.framework.TestCase;

public class HtmlEntitiesTest extends TestCase {

    /*
     * Test method for html.HtmlEntities.toString(String)
     */
    public void testToString() {
        String in = "Texte avec entit&eacute;s html &#161; &#xA1; &iexcl;";
        String out = "Texte avec entités html \u00a1 \u00a1 \u00a1";
        String res = HtmlEntities.entityToChar(in);
        assertTrue(res,res.equals(out));
    }
    
    /*
     * Test method for html.HtmlEntities.toStringInuktitut(String)
     */
    public void testToStringInuktitut() {
        String in = "Texte avec entit&eacute;s html &#x1401; &#xA1; &iexcl;";
        String out = "Texte avec entit&eacute;s html \u1401 &#xA1; &iexcl;";
        String res = HtmlEntities.toStringInuktitut(in);
        assertTrue(res,res.equals(out));
    }
    
    /*
     * Test method for html.HtmlEntities.substring(String...)
     */
    public void testSubstring() {
        String in = "Texte avec entit&eacute;s html &#x1401; &#xA1; &iexcl;";
        String out = "entit&eacute;";
        String res = HtmlEntities.substring(in,11,17);
        assertTrue(res,res.equals(out));
    }
    
    /*
     * Test method for 'html.HtmlEntities.substring(String, int, int)'
     */
    public void testSubstringStringIntInt() {
        String str = "ab&eacute;c&#5432;def";
        String sub = HtmlEntities.substring(str,0,3);
        assertTrue("",sub.equals("ab&eacute;"));
        sub = HtmlEntities.substring(str,2,6);
        assertTrue("",sub.equals("&eacute;c&#5432;d"));
    }

}
