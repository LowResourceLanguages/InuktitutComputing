/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Jun 22, 2006
 * par / by Benoit Farley
 * 
 */
package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.pdfbox.cos.COSBase;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.cos.COSName;
import org.pdfbox.cos.COSObject;
import org.pdfbox.cos.COSString;
import org.pdfbox.pdfparser.PDFParser;

import documents.NRC_PDFDocument;
import fonts.Font;

import script.TransCoder;


public class TestRead {

    public static void main(String[] args) {    
        String urlName;
        urlName = "http://www.cdncouncilarchives.ca/ExecResponsibilities_Inuk.pdf";
        
        try {
            NRC_PDFDocument pdfdoc = new NRC_PDFDocument(urlName);
            List objs = pdfdoc.document.getObjects();
            for (int i=0; i<objs.size(); i++) {
                COSObject obj = (COSObject)objs.get(i);
                System.out.println(obj.toString());
                COSBase b = obj.getItem(COSName.getPDFName( "Title" ));
                if (b!=null) {
                    String title = ((COSString)b).getString();
                    String font = pdfdoc.getPreferredFont();
                    if (Font.isLegacy(font)) 
                        System.out.println("\t"+TransCoder.legacyToRoman(title,font));
                    else
                        System.out.println("\t"+title);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
