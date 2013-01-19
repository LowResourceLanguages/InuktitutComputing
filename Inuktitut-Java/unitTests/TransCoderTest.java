/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Nov 1, 2006
 * par / by Benoit Farley
 * 
 */
package unitTests;

import java.io.UnsupportedEncodingException;

import script.TransCoder;
import junit.framework.TestCase;

public class TransCoderTest extends TestCase {

    /*
     * Test method for 'script.TransCoder.unistringToUnicode(String)'
     */
    public void testUnistringToUnicode() {

    }

    /*
     * Test method for 'script.TransCoder.unicodeToUnistring(String)'
     */
    public void testUnicodeToUnistring() {

    }

    /*
     * Test method for 'script.TransCoder.romanToUnicode(String)'
     */
    public void testRomanToUnicodeString() {
        String s = "inuktitut";
        String t = "\u1403\u14c4\u1483\u144e\u1450\u1466";
        String r = TransCoder.romanToUnicode(s);
        assertTrue("(1) Wrong",r.equals(t));
        s = "rai";
        t = "\u154B\u1403";
        r = TransCoder.romanToUnicode(s);
        assertTrue("(2) Wrong",r.equals(t));
    }

    /*
     * Test method for 'script.TransCoder.romanToUnicode(String, boolean)'
     */
    public void testRomanToUnicodeStringBoolean() {
        String s = "rai";
        String t = "\u154B\u1403";
        String r = TransCoder.romanToUnicode(s,false);
        assertTrue("(1) Wrong",r.equals(t));
        s = "rai";
        t = "\u1542";
        r = TransCoder.romanToUnicode(s,true);
        assertTrue("(2) Wrong",r.equals(t));
    }

    /*
     * Test method for 'script.TransCoder.unicodeToRoman(String)'
     */
    public void testUnicodeToRoman() {

    }

    /*
     * Test method for 'script.TransCoder.legacyToRoman(String, String)'
     */
    public void testLegacyToRoman() {

    }

    /*
     * Test method for 'script.TransCoder.unicodeToLegacy(String, String)'
     */
    public void testUnicodeToLegacy() {
        String s = "\u1403\u14c4\u1483\u144e\u1450\u1466";
        String t = "\u017d\u00b4\u0153\u00a9\u00a6\u201d";
        String r = TransCoder.unicodeToLegacy(s,"aujaq2");
        assertTrue("(1) Wrong",r.equals(t));
        s = "\u1542";
        t = "\u00EA";
        r = TransCoder.unicodeToLegacy(s,"aipainunavik");
        assertTrue("(2) Wrong: "+r.codePointAt(0)+" should have been "+t.codePointAt(0),r.equals(t));
        s = "\u154B\u1403";
        t = "\u00EA";
        r = TransCoder.unicodeToLegacy(s,"aipainunavik");
        assertTrue("(3) Wrong: "+r.codePointAt(0)+" should have been "+t.codePointAt(0),r.equals(t));
    }
    
    /*
     * Test method for 'script.TransCoder.unicodeToUrlencoding(String)'
     */
    public void ___testunicodeToUrlencodingString() {
    	String s = "\u1403\u14c4\u1403\u1466"; // inuit
    	String t = "";
    	String r = TransCoder.unicodeToUrlencoding(s);
    	assertTrue("",r.equals(t));
    }


    /*
     * Test method for 'script.TransCoder.legacyToUnicode(String, String, boolean)'
     */
    public void testLegacyToUnicodeStringStringBoolean() {
    	String s = "xg6bsJ4nq8k5 X3Nst4nq8i4.";
    	String t = "\u140a\u1450\u1585\u1455\u1405\u152a\u1483\u14f4\u158f\u14d0\u14c4\u1466\u0020\u1438\u1550\u14c7\u1405\u144e\u1483\u14f4\u158f\u14d0\u14c2\u1483\u002e";
    	String r = TransCoder.legacyToUnicode(s, "nunacom");
    	assertTrue("",r.equals(t));
    	s = "(, @))@u vtmt5tt9lQ5 wko]mi4 |b4fx kNq5b xg6bsJ4nq8k5 X3Nst4nq8i4.";
    	t = "\u0039\u002c\u0020\u0032\u0030\u0030\u0032\u14a5\u0020\u1472\u144e\u14aa\u144e\u1466\u144e\u144e\u14ea\u14d7\u148b\u1466\u0020\u1403\u14c4\u14d5\u14ab\u14c2\u1483\u0020\u1456\u1483\u146f\u140a\u0020\u14c4\u14c7\u158f\u1466\u1455\u0020\u140a\u1450\u1585\u1455\u1405\u152a\u1483\u14f4\u158f\u14d0\u14c4\u1466\u0020\u1438\u1550\u14c7\u1405\u144e\u1483\u14f4\u158f\u14d0\u14c2\u1483\u002e";
    	r = TransCoder.legacyToUnicode(s, "nunacom");
    	assertTrue("",r.equals(t));
    	s = "Cw"; // rai en Nunacom
    	t = "\u154b\u1403";
    	r = TransCoder.legacyToUnicode(s, "nunacom");
    	assertTrue("(3) Wrong: ",r.equals(t));
    	s = "Cw"; // rai en Nunacom
    	t = "\u1542";
    	r = TransCoder.legacyToUnicode(s, "nunacom", true);
    	assertTrue("(4) Wrong: ",r.equals(t));
    }
    
    public void test_macRoman2cp1252() {
    	String s = "…m by3ŸZb";
    	String t = "…m by3ŸZb";
    	String r = TransCoder.macRoman2cp1252(s);
    	assertTrue("'"+r+"' should have been '"+t+"'",r.equals(t));
    	s = "…ˆ5©mb";
    	t = "…ˆ5©mb";
    	r = TransCoder.macRoman2cp1252(s);
    	assertTrue("'"+r+"' should have been '"+t+"'",r.equals(t));
    }

}
