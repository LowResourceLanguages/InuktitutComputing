/*
 * Conseil national de recherche Canada 2007/
 * National Research Council Canada 2007
 * 
 * Créé le / Created on May 31, 2007
 * par / by Benoit Farley
 * 
 */
package unitTests.html;

import html.HtmlDocu;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

public class HtmlDocuTest extends TestCase {

    public void testHtmlDocuFile() {
       File f = new File(new File(File.separator,"tmp"),"copy_testbom.html_12380.html");
       try {
           HtmlDocu doc = new HtmlDocu(f,"utf-8");
           assertTrue("",doc.url.getPath().equals("/C:/tmp/copy_testbom.html_12380.html"));
       } catch (MalformedURLException e) {
       } catch (IOException e) {
       }
    }

}
