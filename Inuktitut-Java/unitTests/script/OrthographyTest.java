/*
 * Conseil national de recherche Canada 2004/
 * National Research Council Canada 2004
 * 
 * Créé le / Created on Dec 9, 2004
 * par / by Benoit Farley
 * 
 */
package unitTests.script;

import script.Orthography;
import junit.framework.TestCase;

public class OrthographyTest extends TestCase {

    public void testSimplifiedOrthography() {
    }

    public void testSimplifiedOrthographyLat() {
    }

    public void testSimplifiedOrthographyLatB() {
    }

    public void testSimplifiedOrthographySyl() {
    }

    public void testOrthographyICI() {
    }

    /*
     * Class under test for String orthographyICILat(String)
     */
    public void testOrthographyICILatString() {
    }

    /*
     * Class under test for String orthographyICILat(char)
     */
    public void testOrthographyICILatchar() {
    }

    public void testOrthographyICISyl() {
        String s = new String(new char[]{'\u1403', '\u14AA', '\u140A', '\u1585'});
        String t = Orthography.orthographyICISyl(s);
        char c[] = t.toCharArray();
        assertTrue("",c.length==3 && c[0]=='\u1403' && c[1]=='\u14AB' && c[2]=='\u1585');
    }

}
