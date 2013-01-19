/*
 * Conseil national de recherche Canada 2004/
 * National Research Council Canada 2004
 * 
 * Créé le / Created on 21-Sep-2004
 * par / by Benoit Farley
 * 
 */
package documents;

import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

public class WebPageReaderTest extends TestCase {

    public void testWebPageReader() throws MalformedURLException, IOException {
//        WebPageReader wpr = new WebPageReader("http://www.ihti.ca/inuktitut/home.html");
        WebPageReader wpr = new WebPageReader("file:///C:/Inuktitut/testpond.htm");
        String contents = null;
        contents = wpr.getContents();
        System.out.println(contents);
    }

}
