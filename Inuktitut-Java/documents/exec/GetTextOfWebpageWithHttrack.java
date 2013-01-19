//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2009
//           (c) National Research Council of Canada, 2009
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		TexteDePageWebParHttrack.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	7 octobre 2009
//
// Description: Entr�e d'un programme qui fait la translit�ration d'une page 
//              HTML du syllabaire inuktitut en caract�res latins.
//
// -----------------------------------------------------------------------

package documents.exec;

import org.apache.log4j.Logger;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.util.PDFFonttedTextStripper;

import documents.NRC_HTMLDocumentByCobra;

import fonts.Font;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
 * Écriture du texte inuktitut syllabique de fichiers inuktitut dans des
 * fichiers de texte spécifiques à chaque police legacy inuktitut, dans le but
 * de collecter le plus de texte possible pour chacune de ces polices et en
 * tirer une signature par N-grammes pour la reconnaissance automatique.
 */
public class GetTextOfWebpageWithHttrack {

    private static Logger LOG_3 = Logger.getLogger(GetTextOfWebpageWithHttrack.class.getName()+".process");
    private static Logger LOG_1 = Logger.getLogger(GetTextOfWebpageWithHttrack.class.getName()+".readNewDotTxt");
    private static Logger LOG_2 = Logger.getLogger(GetTextOfWebpageWithHttrack.class.getName()+".processFile2");
    private static Logger LOG_4 = Logger.getLogger(GetTextOfWebpageWithHttrack.class.getName()+".ecrireFontFiles");
    private static Logger LOG_5 = Logger.getLogger(GetTextOfWebpageWithHttrack.class.getName()+".ajouterAPolices");
    private static Logger LOG_6 = Logger.getLogger(GetTextOfWebpageWithHttrack.class.getName()+".ajouterAstream");

    static File siteRepositoryDirectory;
    static File outputDirectory;

    static int ng = 3;
    static Hashtable streamsht = null;
    static Hashtable file2url;
    static int procNumber = 0; // 0: extraire liste d'urls/fichiers  1: extraire polices   2: extraire texte
    static Hashtable font2file = new Hashtable();
//    static String [] polices = {"nunacom","prosyl","naamajut","aipainunavik"};
    
    public static void main(String[] args) {
    	if (args.length < 3)
    		usage();
    	if (args[0].equals("0") || args[0].equals("1") || args[0].equals("2") || args[0].equals("3"))
    		procNumber = new Integer(args[0]).intValue();
    	else
    		usage();
    	String siteRepositoryPathname = args[1];
    	String outputDirectoryPathname = args[2];
    	siteRepositoryDirectory = new File(siteRepositoryPathname);
    	outputDirectory = new File(outputDirectoryPathname);
    	if ( !siteRepositoryDirectory.isDirectory() )
    		usage("'"+siteRepositoryPathname+"' n'est pas un répertoire");
    	if ( !outputDirectory.isDirectory() )
    		usage("'"+outputDirectoryPathname+"' n'est pas un répertoire");
    	file2url = readNewDotTxt(siteRepositoryDirectory);
    	if ( procNumber==0 ) {
    		ecrireFiles(outputDirectory);
    		System.out.println("Terminé");
    		System.exit(0);
    	}
    	else {
    		if (args.length != 4)
    			usage();
    		File lookInDirectory = new File(args[3]);
        	if ( !lookInDirectory.isDirectory() )
        		usage("'"+args[3]+"' n'est pas un répertoire");
    		process( lookInDirectory );
    		if ( procNumber==1 )
    			ecrireFontFiles();
    	}
		System.out.println("Terminé");
    }
    
    public static void usage(String mess) {
    	System.err.println(mess);
    	System.err.println();
    	usage();
    }
    public static void usage() {
    	System.err.println("usage: TexteDePageWebParHttrack <no. application> <répertoire dépositaire httrack d'un site> <répertoire d'écriture> [ (fichier | répertoire) ]");
    	System.err.println();
    	System.err.println("no. application :");
    	System.err.println("\t0: liste des URLs et des noms de fichiers correspondants");
    	System.err.println("\t1: liste des polices Legacy et des noms de fichiers correspondants");
    	System.exit(1);
    }
    

    public static void process (File f) {
    	if ( f.isDirectory() && !f.getName().equals("hts-cache")) {
    		File[] files = f.listFiles();
            for (int i=0; i<files.length; i++)
            	process(files[i]);
            if ( f.getParentFile().equals(siteRepositoryDirectory) ) {
            	LOG_3.info("Écriture des fichiers de polices pour '"+f.getName()+"'");
            	ecrireFontFiles();
            }
    	} else {
    		processFile(f);
    	}
    }
    
    public static Hashtable readNewDotTxt (File siteRep) {
		LOG_1.debug("siteRep= '"+siteRep.getAbsolutePath()+"'");
    	Pattern pat = Pattern.compile("^.+[/\\\\]([^/\\\\]+)$");
    	Matcher mpat = pat.matcher(siteRep.getAbsolutePath());
    	mpat.matches();
    	String siteName = mpat.group(1);
    	LOG_1.debug("siteName= '"+siteName+"'");
    	pat = Pattern.compile("^.+/"+siteName+"(/.*)$");
    	Hashtable ht = new Hashtable();
    	File newDotText = new File( new File(siteRep, "hts-cache"), "new.txt" );
    	try {
			BufferedReader bf = new BufferedReader(new FileReader(newDotText));
			String line;
			try {
				bf.readLine();
				while( (line=bf.readLine()) != null ) {
					LOG_1.debug("line= '"+line+"'");
					String[] parts = line.split("\t");
					if ( !parts[3].equals("200") )
						continue;
					String mime = parts[5];
					String url = parts[7];
					String file = parts[8];
					mpat = pat.matcher(file);
					if ( mpat.matches() ) {
						file = siteRep.getAbsolutePath()+mpat.group(1);
						if ( procNumber != 0 ) {
							file = file.replaceAll("\\\\", "/");
							file = file.toLowerCase();
						}
						LOG_1.debug("\nfile='"+file+"'\nurl='"+url+"'\nmime='"+mime+"'");
						ht.put(file, new String[]{url,mime});
					}
				}
				return ht;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static void processFile (File f) {
    	System.out.println(f.getAbsolutePath()+"...");
		System.out.flush();
    	processFile2(f);
    }
    
    public static void processFile2(File f) {
		try {
			String source = f.getAbsolutePath();
			source = source.replaceAll("\\\\", "/");
			String sourceLC = source.toLowerCase();
			try {
				String[] urlMime = (String[])file2url.get(sourceLC);
				if (urlMime==null) {
					LOG_2.debug("PAS DANS file2url: '"+sourceLC+"'");
					return;
				}
				String mime = urlMime[1];
				String url = urlMime[0];
				if (mime.matches(".*text/html.*")) {
					NRC_HTMLDocumentByCobra doc = new NRC_HTMLDocumentByCobra(source,url,null,false);
					LOG_2.debug("source HTML= '"+source+"'");
					LOG_2.debug("doc= '"+doc+"'");
					if (doc != null) {
						processDoc(doc);
						if ( procNumber==2) 
							ecrireStream();
						doc.close();
					}
				}
				else if (mime.matches(".*application/pdf.*")) {
//					NRC_PDFDocument doc = new NRC_PDFDocument(source,"");
//					ajouterAstream((NRC_PDFDocument) doc);
				}
				else {
				}
			} catch (Exception e) {
				System.out.print("Exception pendant " + source + " ...");
				e.printStackTrace(System.out);
				System.out.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.flush();
		}
	}
    
    // -----------------------------------------------------------
    // Traitement du document HTML
    // - Recherche du texte en inuktitut et traitement de ce texte
    // ------------------------------------------------------------

//    private static void ajouterAstream(NRC_PDFDocument doc) throws Exception {
//    	streamsht = new Hashtable();
//        Object [][] textElements = doc.getTextElements();
//        Pattern p = Pattern.compile("(.+\\+)?(.+)");
//        int nelms = textElements.length;
////        nelms = 20;
//        try {
//        // Pour chaque �l�ment de texte:
//        for (int i = 0; i < nelms; i++) {
//            String f = null;
//            String textElement = (String)textElements[i][0];
//            textElement = textElement.trim();
//            PDFont font = (PDFont)textElements[i][1];
//            String textElementFont = null;
//            if (font != null)
//                textElementFont = font.getBaseFont();
//            if (textElementFont == null)
//                textElementFont = "";
//            Matcher mp = p.matcher(textElementFont);
//            try {
//                if (mp.find())
//                    textElementFont = mp.group(2).toLowerCase();
//            } catch (Exception e) {
//                textElementFont = "";
//            }
//            // V�rifier s'il s'agit d'une police inuktitut archa�que connue.
//            if (!textElement.equals("") && (f = PDFFonttedTextStripper.isContainedFont(
//                    textElementFont, Police.polices)) != null) {
//                ajouterAstream(textElement,f);
//            }
//        }
//        } catch (Exception e) {
//            throw e;
//        }
//    }

	//-----------------------------------------------------------
	// Traitement du document HTML
	// - Recherche du texte en inuktitut et traitement de ce texte
	//------------------------------------------------------------

	private static void processDoc(NRC_HTMLDocumentByCobra doc) {
		streamsht = new Hashtable();
		// ----------------------------------------------
		// Itération sur chaque élément du document HTML
		// ----------------------------------------------
		if (doc.getInuktitutPercentage() > 0.1) {
			String[][] textFontObjects = doc.getTextElementsWithFont();
			LOG_6.debug("textFontObjects: size='" + textFontObjects.length
					+ "'");
			for (int i = 0; i < textFontObjects.length; i++) {
				String text = textFontObjects[i][0];
				String font = textFontObjects[i][1];
				if (procNumber == 2)
					ajouterAstream(text, font);
				else if (procNumber == 1)
					ajouterAPolices(font, doc.copyOfFile.getAbsolutePath());
			}
		}
	}
	
	
	private static void ajouterAPolices(String police, String fileName) {
		if ( police==null ) police = "unicode";
		LOG_5.debug("\npolice='"+police+"'\nfileName='"+fileName+"'");
		Vector v = (Vector)font2file.get(police);
		if (v==null)
			v = new Vector();
		if ( !v.contains(fileName))
			v.add(fileName);
		font2file.put(police, v);
	}

    
    private static void ajouterAstream(String txt, String police)  {
//    	System.out.println("texte= '"+txt+"' ["+police+"]");
        String fos = (String)streamsht.get(police);
        if (fos == null)
            fos = txt;
        else
            fos += " " + txt;
        streamsht.put(police,fos);
    }
    
//    private static void ouvrirStreams() {
//        File files[] = directory.listFiles(new FilenameFilterStream());
//        for (int i=0; i<files.length; i++) {
//            File file = files[i];
//            String police = file.getName().substring(0,
//                    file.getName().indexOf("Stream.txt"));
//            PrintWriter fos;
//            try {
//                fos = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8")));
//                streamsht.put(police,fos);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }

    
    private static void ecrireStream() {
            for (Enumeration keys = streamsht.keys(); keys.hasMoreElements();) {
                Object police = keys.nextElement();
                PrintWriter pw;
                try {
                	File f = new File(outputDirectory,(String)police+"Stream.txt");
                	FileOutputStream fos;
                	if ( !f.exists() )
                		fos = new FileOutputStream(f);
                	else
                		fos = new FileOutputStream(f,true);
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos,"utf-8")));
                    pw.println((String)streamsht.get(police));
                    pw.flush();
                    pw.close();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
           }
    }
    
    public static void ecrireFiles(File siteRep) {
    	PrintWriter pw;
    	try {
    		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
    				new FileOutputStream(new File(siteRep,"files2urls.txt")))));
        	for (Enumeration e=file2url.keys(); e.hasMoreElements();) {
        		String file = (String)e.nextElement();
        		String[] urlMime = (String[])file2url.get(file);
        		pw.println(file+"\t"+urlMime[0]+"\t"+urlMime[1]);
        	}
        	pw.close();
   	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
   
    
    private static void ecrireFontFiles() {
    	LOG_4.debug("Taille de font2file= "+font2file.size());
    		for (Enumeration keys = font2file.keys(); keys.hasMoreElements();) {
    			Object police = keys.nextElement();
    			Vector v = (Vector)font2file.get(police);
    			PrintWriter pw;
    			try {
    				File f = new File(outputDirectory,(String)police+"Files.txt");
    				FileOutputStream fos;
//                	if ( !f.exists() )
//                		fos = new FileOutputStream(f);
//                	else
                		fos = new FileOutputStream(f,true);
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
                    for (int i=0; i<v.size(); i++)
                    	pw.println((String)v.elementAt(i));
                    pw.close();
    			} catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
    			}
    		}
    }
    
    static class FilenameFilterStream implements FilenameFilter {

        public boolean accept(File dir, String name) {
            if (name.indexOf("Stream.txt") >= 0)
                return true;
            else
                return false;
        }
        
    }
    
    static class CompObject implements Comparable {

        String key;
        Integer val;
        
        CompObject(String x, Integer y) {
            key = x;
            val = y;
        }
        public int compareTo(Object arg0) {
            return val.compareTo(((CompObject)arg0).val) * -1;
        }
        
    }

    
}


