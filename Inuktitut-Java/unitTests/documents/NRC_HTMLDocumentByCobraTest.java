/*
 * Conseil national de recherche Canada 2007/
 * National Research Council Canada 2007
 * 
 * CrÃ©Ã© le / Created on May 29, 2007
 * par / by Benoit Farley
 * 
 */
package unitTests.documents;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.apache.commons.lang.StringUtils;

import documents.NRC_HTMLDocumentByCobra;

import junit.framework.TestCase;

public class NRC_HTMLDocumentByCobraTest extends TestCase {

    public void testNRC_HTMLDocumentByCobra() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            File dir = NRC_HTMLDocumentByCobra.tmpDir;
            File [] files = dir.listFiles(new MyFilenameFilter());
            for (int i=0; i<files.length; i++)
                files[i].delete();
            files = dir.listFiles(new MyFilenameFilter());
            assertTrue("Il y a des fichiers \"...cobracopy...\" dans tmpDir",files.length==0);
            doc = new NRC_HTMLDocumentByCobra("http://localinuktitut/unitTests/files_for_tests/main_fr.html");
            boolean copyFileCreated = false;
            files = dir.listFiles(new MyFilenameFilter());
            if (doc != null) {
            	copyFileCreated = doc.copyOfFile.equals(files[0]);
            	doc.close();
            	assertTrue("Pas de fichier de copie créé",copyFileCreated);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }
    }
    
    class MyFilenameFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            if (name.indexOf("cobracopy") >= 0)
                return true;
            else
                return false;
        }
    }

    public void ___testGetEncodingAndManner() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
        	String encoding;
        	String manner;
            doc = new NRC_HTMLDocumentByCobra("http://www.inuktitutcomputing.ca/main_fr.html");
            encoding = doc.getEncoding();
            manner = doc.getEncodingManner();
            doc.close();
            assertEquals("L'encodage retourné ne correspond pas à  l'encodage escompté","utf-8",encoding);
            assertEquals("La manière retournée de spécifier l'encodage ne correspond pas à  la manière escomptée","httpheader",manner);

            doc = new NRC_HTMLDocumentByCobra("http://www.kativik.qc.ca/html/aipai/index_Inut.html");
            encoding = doc.getEncoding();
            manner = doc.getEncodingManner();
            doc.close();
            assertEquals("L'encodage retourné ne correspond pas à  l'encodage escompté","iso-8859-1",encoding);
            assertEquals("La manière retournée de spécifier l'encodage ne correspond pas à  la manière escomptée","meta",manner);

//            doc = new NRC_HTMLDocumentByCobra("http://www.gov.nu.ca/Nunavut/Inuktitut/");
//            encoding = doc.getEncoding();
//            manner = doc.getEncodingManner();
//            doc.close();
//            assertEquals("L'encodage retourné ne correspond pas à  l'encodage escompté","utf-8",encoding);
//            assertEquals("La manière retournée de spécifier l'encodage ne correspond pas à  la manière escomptée","bom",manner);
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }
    }
    
    public void ___testGetTitle() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/fr/main.html");
            String title = doc.getTitle();
            doc.close();
            assertEquals("Le titre retourné ne correspond pas au titre escompté","Inuktitut Computing point C A",title);

            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/test1.html");
            title = doc.getTitle();
            doc.close();
            assertEquals("Le titre retourné ne correspond pas au titre escompté","\u1403\u14c4\u1483\u144e\u1450\u1466",title);
} catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }        
    }

    public void ___testGetDate() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/test1.html");
            Date date = doc.getDate();
            doc.close();
            String dateS = date.toString();
            assertEquals("La date retournée ne correspond pas à  la date escomptée","Thu Jul 05 13:55:16 EDT 2007",dateS);
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }        
    }

    public void ___testGetFonts() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts.html");
            HTMLDocumentImpl document = doc.getDocument();
            doc.close();
            NodeImpl el = (NodeImpl)document.getElementById("tnunacom");
            el = (NodeImpl)el.getFirstChild();
            String fs[] = NRC_HTMLDocumentByCobra.getFonts(el);
            String fsS = Arrays.toString(fs);
            assertEquals("Les polices retournées ne correspondent pas aux polices escomptées","[nunacom, Verdana, Arial, Helvetica, sans-serif]",fsS);

            el = (NodeImpl)document.getElementById("tnaamajut");
            el = (NodeImpl)el.getFirstChild();
            fs = NRC_HTMLDocumentByCobra.getFonts(el);
            fsS = Arrays.toString(fs);
            assertEquals("Les polices retournées ne correspondent pas aux polices escomptées","[Naamajut, Verdana, Arial, Helvetica, sans-serif]",fsS);

            el = (NodeImpl)document.getElementById("ttimes");
            el = (NodeImpl)el.getFirstChild();
            fs = NRC_HTMLDocumentByCobra.getFonts(el);
            fsS = Arrays.toString(fs);
            assertEquals("Les polices retournées ne correspondent pas aux polices escomptées","[Times New Roman, Helvetica]",fsS);

            el = (NodeImpl)document.getElementById("tgeorgia");
            el = (NodeImpl)el.getFirstChild();
            fs = NRC_HTMLDocumentByCobra.getFonts(el);
            fsS = Arrays.toString(fs);
            assertEquals("Les polices retournées ne correspondent pas aux polices escomptées","[georgia]",fsS);

            el = (NodeImpl)document.getElementById("tprosyl");
            el = (NodeImpl)el.getFirstChild();
            fs = NRC_HTMLDocumentByCobra.getFonts(el);
            fsS = Arrays.toString(fs);
            assertEquals("Les polices retournées ne correspondent pas aux polices escomptées","[PROSYL, Verdana, Arial, Helvetica, sans-serif]",fsS);

        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        } 
    }
    
    public void ___testGetBase() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testTranslit.html");
            String base = doc.getBaseURI();
            doc.close();
            assertEquals("Le nom de la base retourné ne correspond pas au nom escompté","http://localhost/tests/testTranslit.html",base);
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }        
    }

//    public void testCSS_1() {
//    	NRC_HTMLDocumentByCobra doc = null;
//        try {
//            doc = new NRC_HTMLDocumentByCobra("file:///c:/Documents and Settings/farleyb/workspace/Inuktitut/unitTests/files_for_tests/testCSS.html");
//            HTMLDocumentImpl document = doc.getDocument();
//            Element node = document.getElementById("header1");
//            NamedNodeMap attrs = node.getAttributes();
//            String id = node.getAttribute("id");
//            
//            doc.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (doc != null) doc.close();
//            fail();
//        }        
//    }

    public void ___testGetPageContent() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testcontent.html");
            String text = doc.getPageContent();
            doc.close();
            assertEquals("Le contenu retourné ne correspond pas au contenu escompté","Texte en nunacom: w~kF4 Texte en naamajut: w˚F4 Texte en Times New Roman: inuuvik Texte en georgia:inuuvik Texte en prosyl: w~k=4",text);
            
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testcontent2.html");
            text = doc.getPageContent();
            doc.close();
            assertEquals("Le contenu retourné ne correspond pas au contenu escompté","Les joies philosophiques sont les plus grandes en considérant que l'amoureuse qu'elle est voit la philosophie comme une menace à son autonomie.",text);
            } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }        
    }

    public void ___testContainsInuktitut_legacy() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testcontent.html");
            boolean containsInuktitut = doc.containsInuktitut();
            doc.close();
            assertFalse("Le contenu contient de l'inuktitut",containsInuktitut);

            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts.html");
            containsInuktitut = doc.containsInuktitut();
            doc.close();
            assertTrue("Le contenu retourné ne contient pas d'inuktitut",containsInuktitut);

        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }

    public void ___testContainsInuktitut_unicode_entitesHtml() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts2.html");
            boolean containsInuktitut = doc.containsInuktitut();
            doc.close();
            assertTrue("Le contenu retourné ne contient pas d'inuktitut",containsInuktitut);
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void ___testGetAllFonts() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts2.html");
            Object [] fonts = doc.getAllFonts();
            doc.close();
            assertTrue("Le nombre de polices retourné pour testfonts2.html n'est pas correct",fonts.length==0);

            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts.html");
            fonts = doc.getAllFonts();
            doc.close();
            assertTrue("Le nombre de polices retourné pour testfonts.html ("+fonts.length+") n'est pas correct",fonts.length==9);

        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }

    public void ___testGetAllFontsNames() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts.html");
            String [] fontNames = doc.getAllFontsNames();
            doc.close();
            Arrays.sort(fontNames);
            String [] namesExpected = new String[]{
                    "nunacom","Verdana","Arial","Helvetica","sans-serif","Naamajut","Times New Roman","georgia","PROSYL"
            };
            Arrays.sort(namesExpected);
            assertTrue("Les noms des polices retournés pour testfonts.html ne sont pas corrects",
                    Arrays.hashCode(fontNames)==Arrays.hashCode(namesExpected));
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }

    public void ___testGetInuktitutFonts() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("http://localhost/tests/testfonts.html");
            String [] fontNames = doc.getInuktitutFonts();
            doc.close();
            Arrays.sort(fontNames);
            String [] namesExpected = new String[]{
                    "nunacom","Naamajut","PROSYL"
            };
            Arrays.sort(namesExpected);
            assertTrue("Les noms des polices inuktitut retournés pour testfonts.html ne sont pas corrects",
                    Arrays.hashCode(fontNames)==Arrays.hashCode(namesExpected));
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void testClose() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit.html");
            String copyOfFile_name = doc.copyOfFile.getAbsolutePath();
            doc.close();
            boolean copyFileExists = new File(copyOfFile_name).exists();
            assertTrue("Le fichier copie '"+copyOfFile_name+"' n'a pas été supprimé",!copyFileExists);
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }

    /*
     * Tests pour la translittération
     */

    public void testTraiterDocPourTranslit_1() {
    	NRC_HTMLDocumentByCobra doc = null;
    	try {
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit.html");
            doc.transHash = new Hashtable();
            doc.traiterDocPourTranslit();
            doc.close();
            Hashtable transHash = (Hashtable)doc.transHash.clone();
            assertTrue("Le nombre d'entrées dans transHash ("+transHash.size()+") est incorrect",
                    transHash.size()==3);
            Hashtable target = new Hashtable();
            target.put("w~kF4 wk4tg5 scs+y5", "nunacom");
            target.put("w˚F4 wk4tg5 scs¥5", "naamajut");
            target.put( "w~k=4 wk4tg5", "prosyl");
            for (Enumeration e = transHash.keys();e.hasMoreElements();) {
                String key = (String)e.nextElement();
                assertTrue("La clé \""+key+"\" n'est pas correcte",target.containsKey(key));
                String val = (String)transHash.get(key);
                assertTrue("La valeur de \""+key+"\" n'est pas égale à"+(String)target.get(key),val.equals((String)target.get(key)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void testTraiterDocPourTranslit_2() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testFontTelling.html");
            doc.transHash = new Hashtable();
            doc.traiterDocPourTranslit();
            Hashtable transHash = (Hashtable)doc.transHash.clone();
            doc.close();
            assertTrue("Le nombre d'entrées dans transHash ("+transHash.size()+") est incorrect",
                    transHash.size()==3);
            Hashtable target = new Hashtable();
            target.put("et3us5 sx1Nzi3us5 ra9o3+X3u1i4", "nunacom");
            target.put("w4OAh1iv9M1u4 s/C4ncF[Jx3gu4 et3us5", "nunacom");
            target.put( "(, @))@u vtmt5tt9lQ5", "nunacom");
            for (Enumeration e = transHash.keys();e.hasMoreElements();) {
                String key = (String)e.nextElement();
                assertTrue("La clé \""+key+"\" n'est pas correcte",target.containsKey(key));
                String val = (String)transHash.get(key);
                assertTrue("La valeur de \""+key+"\" n'est pas égale à"+(String)target.get(key),val.equals((String)target.get(key)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void ___testTraiterDocPourTranslit_3() {
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit3.html");
            doc.transHash = new Hashtable();
            doc.traiterDocPourTranslit();
            doc.close();
            Hashtable transHash = (Hashtable)doc.transHash.clone();
            assertTrue("Le nombre d'entrées dans transHash ("+transHash.size()+") est incorrect",
                    transHash.size()==1);
            Hashtable target = new Hashtable();
            target.put("wkw5 eiChAmJ5 w6vNw]/4nu4", "nunacom");
            for (Enumeration e = transHash.keys();e.hasMoreElements();) {
                String key = (String)e.nextElement();
                assertTrue("La clé \""+key+"\" n'est pas correcte",target.containsKey(key));
                String val = (String)transHash.get(key);
                assertTrue("La valeur de \""+key+"\" n'est pas égale à"+(String)target.get(key),val.equals((String)target.get(key)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void testTransliterate() {
    	String target = "﻿<html><head><base href=\"file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit.html\"><title>Lecture des polices</title>\n"
    		.concat("<link href=\"styles.css\" rel=\"stylesheet\" type=\"text/css\">\n")
    		.concat("<style type=\"text/css\">\n")
    		.concat(".nunac { font-family:nunacom, Verdana, Arial, Helvetica, sans-serif; }\n")
    		.concat("</style>\n</head>\n<body>\n")
    		.concat("Texte en nunacom: <div id=\"tnunacom\" class=nunac><FONT face=arial>inuuvik inuktitut uqausiit iglurama</FONT></div>\n")
    		.concat("Texte en naamajut: <div id=\"tnaamajut\" class=\"texte naamajut\"><FONT face=arial>inuuvik inuktitut uqausiit iglurama</FONT></div>\n")
    		.concat("Texte en Times New Roman: <div id=\"ttimes\" style=\"font-family:Times New Roman, Helvetica;\">inuuvik inuktitut</div>\n")
    		.concat("Texte en georgia:<br><font id=\"tgeorgia\" face=\"georgia\">inuuvik inuktitut</font>\n")
    		.concat("<p>\nTexte en prosyl: <div class=\"texte prosyl\"><div id=\"tprosyl\"><FONT face=arial>inuuvik inuktitut</FONT></div></div></p>\n")
    		.concat("</body></html>");
    	target = target.replaceAll("\\s+", " ");
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit.html");
            Object [] ret = doc.transliterate();
            doc.close();
            String enc = (String)ret[0];
            File fout = (File)ret[1];
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fout),enc);
            StringBuffer sb = new StringBuffer();
            int c;
            while ((c = isr.read()) != -1) {
            	sb.append((char)c);
            }
            isr.close();
            fout.delete();
            String res = sb.toString();
        	res = res.replaceAll("\\s+", " ");
            if (!res.equals(target)) {
            	for (int i=0; i<res.length(); i++)
            		if (res.charAt(i) != target.charAt(i)) {
            			System.out.println("attendu: "+(int)target.charAt(i));
            			System.out.println("reçu     : "+(int)res.charAt(i));
                        assertEquals("Le contenu retourné ne correspond pas au contenu escompté; différence à la position '"+i+"'",target.charAt(i),res.charAt(i));
            		} else {
            			System.out.print(res.charAt(i));
            		}
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }

    
    public void testTransliterate_2() {
    	String target = "<html><head><base href=\"file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testCSS_2.html\"><title>test de css</title>\n"
    		.concat("<style type=\"text/css\">\n")
    		.concat(".nuna { font-family:nunacom; }\n")
    		.concat("#contenu #contenu_droite .soustitre .texte { margin:20px 0 20px 20px; }\n")
    		.concat(".nunacom { font-family:nunacom, Verdana, Arial, Helvetica, sans-serif; }\n")
    		.concat("</style>\n</head>\n<body>\n")
    		.concat("<div class=\"nuna\"><FONT face=arial>atsitaq</FONT></div>\n")
    		.concat("<div id=\"container\">\n")
    		.concat("<div id=\"contenu\">\n")
    		.concat("<div id=\"contenu_droite\">\n")
    		.concat("<div class=\"soustitre\">\n")
    		.concat("<div class=\"texte nunacom\"><FONT face=arial>atsitaq</FONT></div></div></div></div></div>\n")
    		.concat("</body></html>");
    	target = target.replaceAll("\\s+", " ");
    	NRC_HTMLDocumentByCobra doc = null;
        try {
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testCSS_2.html");
            Object [] ret = doc.transliterate();
            doc.close();
            String enc = (String)ret[0];
            File fout = (File)ret[1];
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fout),enc);
            StringBuffer sb = new StringBuffer();
            int c;
            while ((c = isr.read()) != -1) {
            	sb.append((char)c);
            }
            isr.close();
            fout.delete();
            String res = sb.toString();
        	res = res.replaceAll("\\s+", " ");
            if (!res.equals(target)) {
            	for (int i=0; i<res.length(); i++)
            		if (res.charAt(i) != target.charAt(i)) {
            			System.out.println("attendu: "+(int)target.charAt(i));
            			System.out.println("reçu     : "+(int)res.charAt(i));
                        assertEquals("Le contenu retourné ne correspond pas au contenu escompté; différence à la position '"+i+"'",target.charAt(i),res.charAt(i));
            		} else {
            			System.out.print(res.charAt(i));
            		}
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }

    
    public void ___testToUnicode_1() {
    	String t1 = null;
    	String t2 = null;
    	String t3 = null;
			t1 = new String("\u157f\u144e\u1550\u14a5\u1405\u1466\u0020\u1405\u140a\u1595\u14c7\u1593\u14c2\u1550\u14a5\u1405\u1466\u0020\n\u146d\u1591\u14ea\u14d5\u1550\u1439\u1550\u14a5\u1595\u14c2\u1483\u0020\u1438\u1550\u14c7\u1405\u144e\u14a7\u1466\u0020\u144e\u146d\u14d5\u1585\u1433\u1466");
			t2 = new String("\u1403\u1483\u15a0\u148d\u14f1\u1595\u14c2\u1472\u14ea\u14da\u1595\u14a5\u1483\u0020\u1405\u152d\u154b\u1483\u14f4\u1583\u1555\u14a1\u152a\u140a\u1550\u1450\u14a5\u1483\u0020\u157f\u144e\u1550\u14a5\u1405\u1466\u0020\n\u1405\u140a\u1595\u14c7\u1593\u14c2\u1550\u14a5\u1405\u158f\u14d0\u14c2\u0020\u14f4\u1585\u146d\u14da\u1405\u1585\u1433\u1585\u0020\u0031\u0030\u0030\u1472\u14f4\u1595\u14c2\u1483\u0020\u14aa\u1483\u1431\u1490\u1583\u1585\u1450\u14a5\u1483\u0020\u1405\u1583\u1405\u14ef\u1405\u14c2\u1470\u152a\u14c2\u1483\u0020\n\u14aa\u1403\u0020\u0037");
			t3 = new String("\u0039\u002c\u0020\u0032\u0030\u0030\u0032\u14a5\u0020\n\u1472\u144e\u14aa\u144e\u1466\u144e\u144e\u14ea\u14d7\u148b\u1466\u0020\u1403\u14c4\u14d5\u14ab\u14c2\u1483\u0020\u1456\u1483\u146f\u140a\u0020\u14c4\u14c7\u158f\u1466\u1455\u0020\u140a\u1450\u1585\u1455\u1405\u152a\u1483\u14f4\u158f\u14d0\u14c4\u1466\u0020\u1438\u1550\u14c7\u1405\u144e\u1483\u14f4\u158f\u14d0\u14c2\u1483\u002e");
    	String targetContent =
    		"Content-Type: text/html; charset=utf-8\n\n<!--END OF HTTP HEADERS-->"
    		.concat("<html><head><base href=\"file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testFontTelling.html\"><title>Test pour la détermination des polices</title></head>\n")
    		.concat("<body>\n")
    		.concat("<p><font face=\"nunacom\" size=\"3\"><a href=\"story3.html\"><span style=\"font-family:pigiarniq\">")
    		.concat(t1)
    		.concat("</span></a></font> <br>\n")
    		.concat("<font face=\"nunacom\" size=\"2\"><span style=\"font-family:pigiarniq\">")
    		.concat(t2)
    		.concat("</span><font face=\"Arial, Helvetica, sans-serif\">-</font><span style=\"font-family:pigiarniq\">")
    		.concat(t3)
    		.concat("</span></font><br>\n</p>\n")
    		.concat("</body></html>");
    	NRC_HTMLDocumentByCobra doc = null;
        try {
    		String lineSeparator = System.getProperty("line.separator");
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testFontTelling.html");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.toUnicode(out);
			doc.close();
			String content = out.toString("utf-8");
			out.close();
			content = content.replaceAll(lineSeparator, "\n");
			if (!content.equals(targetContent))
			for (int i=0; i<targetContent.length(); i++)
				if (content.charAt(i) == targetContent.charAt(i))
					System.out.println(i+"    '"+content.charAt(i)+"' ["+(int)content.charAt(i)+"]");
				else {
					System.out.println("'" + content.charAt(i) + "' [" + (int)content.charAt(i) + "] (should have been '" + targetContent.charAt(i) + "' [" + (int)targetContent.charAt(i) + "])");
					break;
				}
			assertTrue("Wrong length: '"+content.length()+"' should have been '"+targetContent.length()+"'\n"+StringUtils.difference(targetContent,content),content.length() == targetContent.length());
			assertTrue("Wrong content: "+StringUtils.difference(targetContent,content),content.equals(targetContent));
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void ___testToUnicode_2() {
    	String t1 = null;
    	String t2 = null;
    	String t3 = null;
    	try {
			t1 = new String("\u1403\u14c5\u1555\u1483\u0020\u1403\u14c4\u1483\u144e\u1450\u1466\u0020\u1405\u1583\u1405\u14f0\u1466\u0020\u1403\u14a1\u14d7\u154b\u14aa".getBytes("utf-8"));
			t2 = new String("\u1403\u14c5\u1555\u1483\u0020\u1403\u14c4\u1483\u144e\u1450\u1466\u0020\u1405\u1583\u1405\u14f0\u1466\u0020\u1403\u14a1\u14d7\u154b\u14aa".getBytes("utf-8"));
			t3 = new String("\u1403\u14c5\u1555\u1483\u0020\u1403\u14c4\u1483\u144e\u1450\u1466".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e1) {
		}
    	String targetContent =
    		"Content-Type: text/html; charset=utf-8\n\n<!--END OF HTTP HEADERS-->"
    		.concat("ï»¿<html><head><base href=\"file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit.html\"><title>Lecture des polices</title>\n")
    		.concat("<link href=\"styles.css\" rel=\"stylesheet\" type=\"text/css\">\n")
    		.concat("<style type=\"text/css\">\n")
    		.concat(".nunac { font-family:nunacom, Verdana, Arial, Helvetica, sans-serif; }\n")
    		.concat("</style>\n</head>\n<body>\n")
    		.concat("Texte en nunacom: <div id=\"tnunacom\" class=nunac><span style=\"font-family:pigiarniq\">")
    		.concat(t1)
    		.concat("</span></div>\n")
    		.concat("Texte en naamajut: <div id=\"tnaamajut\" class=\"texte naamajut\"><span style=\"font-family:pigiarniq\">")
    		.concat(t2)
    		.concat("</span></div>\n")
    		.concat("Texte en Times New Roman: <div id=\"ttimes\" style=\"font-family:Times New Roman, Helvetica;\">inuuvik inuktitut</div>\n")
    		.concat("Texte en georgia:<br><font id=\"tgeorgia\" face=\"georgia\">inuuvik inuktitut</font>\n")
    		.concat("<p>\n")
    		.concat("Texte en prosyl: <div class=\"texte prosyl\"><div id=\"tprosyl\"><span style=\"font-family:pigiarniq\">")
    		.concat(t3)
    		.concat("</span></div></div></p>\n")
    		.concat("</body></html>");
    	NRC_HTMLDocumentByCobra doc = null;
        try {
    		String lineSeparator = System.getProperty("line.separator");
            doc = new NRC_HTMLDocumentByCobra("file:///"+System.getenv("JAVA_INUKTITUT")+"/unitTests/files_for_tests/testTranslit.html");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.toUnicode(out);
			doc.close();
			String content = out.toString();
			out.close();
			content = content.replaceAll(lineSeparator, "\n");
			if (!content.equals(targetContent))
			for (int i=0; i<targetContent.length(); i++)
				if (content.charAt(i) == targetContent.charAt(i))
					System.out.print(content.charAt(i));
				else {
					System.out.println("'" + content.charAt(i) + "' [" + (int)content.charAt(i) + "] (should have been '" + targetContent.charAt(i) + "' [" + (int)targetContent.charAt(i) + "])");
					break;
				}
			assertTrue("Wrong length: '"+content.length()+"' should have been '"+targetContent.length()+"'\n"+StringUtils.difference(targetContent,content),content.length() == targetContent.length());
			assertTrue("Wrong content: "+StringUtils.difference(targetContent,content),content.equals(targetContent));
        } catch (Exception e) {
            e.printStackTrace();
            if (doc != null) doc.close();
            fail();
        }                
    }
    
    public void testCheckForBadComments() {
    	String s = "abc <!-- comment <!-- def --> <!-- ghi --> jkl";
    	String t = "abc <!-- comment --><!-- def --> <!-- ghi --> jkl";
    	String ns = NRC_HTMLDocumentByCobra.checkForBadComments(s);
    	assertTrue("Wrong: "+StringUtils.difference(t,ns), ns.equals(t));
    	
    	s = "abc <!-- comment --><!-- def --> <!-- ghi --> jkl";
    	t = "abc <!-- comment --><!-- def --> <!-- ghi --> jkl";
    	ns = NRC_HTMLDocumentByCobra.checkForBadComments(s);

    	s = "abc <!-- comment --><!-- def --> <!-- ghi jkl";
    	t = "abc <!-- comment --><!-- def --> <!-- ghi jkl-->";
    	ns = NRC_HTMLDocumentByCobra.checkForBadComments(s);

    	s = "abc <!-- comment --><!-- def <!-- ghi --> jkl";
    	t = "abc <!-- comment --><!-- def --><!-- ghi --> jkl";
    	ns = NRC_HTMLDocumentByCobra.checkForBadComments(s);

    }

}
