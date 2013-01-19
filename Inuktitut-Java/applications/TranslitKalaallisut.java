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

package applications;

import html.HTMLDocuElement;
import html.Tag;
import html.HtmlDocu;
import html.HtmlEntities;
//import javaswing.javax.swing.text.html.*;
import javax.swing.text.html.*;
import javax.swing.text.*;

import fonts.Font;

import documents.NRC_HTMLDocument;

import script.Roman;
import script.TransCoder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import utilities.*;
import utilities1.Util;

public class TranslitKalaallisut {

    private static File tmpDir = new File(File.separator,"tmp");
    private static Hashtable transHash;
    private static Pattern pfw = Pattern.compile("(\\S+)");

    public static void main(String[] args) {
        try {
            Object f[] = transliteratePage(args,null);
            String fileName = ((File)f[1]).getName();
            System.out.println("Page transliterated into "+fileName);
        } catch (OutOfMemoryError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static Object[] transliteratePage(String[] args, PrintStream output) 
        throws OutOfMemoryError, Exception {

        //-------------------------------------------------------
        // Lecture du fichier HTML et cr�ation d'un document HTML
        // Traitement du document HTML (traitement du texte en inuktitut)
        // Ecriture du document HTML dans un nouveau fichier HTML
        //-------------------------------------------------------

        //		Debogage.init();

        String urlName = null;
        transHash = new Hashtable();
        String fileCopy = null;
            urlName = Util.getArgument(args,"f");
            NRC_HTMLDocument doc = new NRC_HTMLDocument(urlName);
            fileCopy = "file://"+doc.copyOfFile.getAbsolutePath();
            HtmlDocu doc2 = new HtmlDocu(new URL(fileCopy));
            doc2.insertBase(doc.getBase());
            File fout2 = processDoc_v2(doc2,doc.encoding);
            doc.copyOfFile.delete();
            return new Object[]{"utf-8",fout2};
    }

    static File processDoc_v2(HtmlDocu doc2, String enc) {
        for (int i = 0; i < doc2.elements.size(); i++) {
            HTMLDocuElement element = (HTMLDocuElement) doc2.elements.elementAt(i);
            if (element.getType() == HTMLDocuElement.BETWEENTAG) {
                String txt = null;
                try {
                    txt = new String(element.getBytes(), enc);
                    String preprocessedText = preprocess(txt);
                    String transliteratedText = TransCoder.romanToUnicode(preprocessedText);
                    byte bs[] = transliteratedText.getBytes("utf-8");
                    element.setBytes(bs);
                    doc2.elements.setElementAt(element, i);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        File fout = null;
        try {
            fout = File.createTempFile("translitKalOutput",".htm",tmpDir);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            OutputStream os = new FileOutputStream(fout);
            doc2.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fout;
    }
    
    private static String preprocess(String str) {
        String x = new String(str).toLowerCase();
        x = HtmlEntities.fromHTMLEntity(x);
        x = x.replaceAll("e","i");
        x = x.replaceAll("o","u");
        x = x.replaceAll("f","v");
        x = x.replaceAll("ll","&");
        return x;
    }

}