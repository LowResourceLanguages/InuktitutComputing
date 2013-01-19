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

import java.io.PrintStream;

import documents.NRC_Document;
import documents.NRC_HTMLDocument;
import documents.NRC_HTMLDocumentByCobra;
import utilities1.Util;

public class TranslitInuk {

    public static void main(String[] args) {
        try {
            transliteratePage(args, System.out);
        } catch (OutOfMemoryError e) {
        } catch (Exception e) {
        }
    }
    
    public static void transliteratePage(String[] args, PrintStream out)  {
        String urlName = Util.getArgument(args,"f");
        NRC_Document doc = null;
		try {
			doc = NRC_Document.Exec.getDocument(urlName);
			if (doc instanceof NRC_HTMLDocument)
				((NRC_HTMLDocument)doc).toRoman(out);
			else if (doc instanceof NRC_HTMLDocumentByCobra)
				((NRC_HTMLDocumentByCobra)doc).toRoman(out);
		} catch (Exception e) {
		} finally {
			if (doc != null) doc.close();
		}
    }



}