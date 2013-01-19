/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 18-Nov-2003 par Benoit Farley
 *  
 */
package applications;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.LinguisticDataAbstract;

import script.TransCoder;

import morph.Decomposition;
import morph.MorphInuk;
import utilities.MonURLDecoder;
import utilities.Text;

// Décomposition d'un mot Inuktitut.
// arguments:
// -s cvs utilisation des données des tables Excel,
//          au lieu des données dompées.
// <mot> un mot en caractères latins ou en syllabique UNICODE codé URI
//       (Perl: uri_escape; Javascript: URIEncodeComponent)

/*
 * Le caractère & doit être entré ainsi : %26.  Par exemple, le mot taku&ugu doit
 * être entré de la façon suivante : taku%26ugu
 */
public class Decompose {
    static String lang = "en";
    
    static void usage() {
    	usage(null);
    }
    static void usage(String mess) {
    	if (lang.equals("en")) {
    		System.err.println("\nusage: java -jar Uqailaut.jar [-h[elp]] [-a[ide]] [-t] [-csv] [-l [en|fr]] <word>");
    		System.err.println("\n-h[elp] : print this in English");
    		System.err.println("-a[ide] : print this in French");
    		System.err.println("-t : display initialization time and decomposition time");
    		System.err.println("-csv : use linguistic data in .csv files");
    		System.err.println("-l [en|fr] : user's language - en: english   fr: french (default: en)");
    		System.err.println("\n<word>: in latin alphabet or in URI-encoded syllabics");
    		System.err.println("        Note: the inuktitut '&' consonant in latin alphabet must be input as %26 (that is, as its URI encoding)");
    	} else {
    		System.err.println("\nusage: java -jar Uqailaut.jar [-h[elp]] [-a[ide]] [-t] [-csv] [-l [en|fr]] <mot>");
    		System.err.println("\n-h[elp] : imprimer ceci en anglais");
    		System.err.println("-a[ide] : imprimer ceci en français");
    		System.err.println("-t : imprimer le temps d'initialisation et le temps de décomposition");
    		System.err.println("-csv : utiliser les données linguistiques dans les fichiers .csv");
    		System.err.println("-l [en|fr] : langue de l'utilisateur - en: anglais   fr: français (défaut: en)");
    		System.err.println("\n<mot>: en alphabet latin ou en syllabique encodé à URI");
    		System.err.println("        Note: la consonne inuktitut '&' en alphabet latin doit être entrée comme %26 (c'est-à-dire encodée à URI)");
    	}
    	if (mess != null)
    		System.err.println(mess);
    	System.exit(1);
    }

    public static void main(String[] args) {
        String dataSource = null;
        String word = null;
        boolean displayTimes = false;
        boolean printHelp = false;
        boolean printUsage = false;
        long startInitTime, endInitTime, startTime, endTime;
        for (int i=0; i<args.length; i++)
        	if (args[i].equals("-csv"))
        		dataSource = "csv";  //Util.getArgument(cargs,"s");
        	else if (args[i].equals("-t"))
        		displayTimes = true;
        	else if (args[i].equals("-h") || args[i].equals("-help")) {
        		lang = "en";
        		printHelp = true;
        	}
        	else if (args[i].equals("-a") || args[i].equals("-aide")) {
        		lang = "fr";
        		printHelp = true;
        	}
        	else if (args[i].equals("-l"))
        		if (i+1 < args.length
        				&& (args[i+1].equals("en") || args[i+1].equals("fr")))
        			lang = args[++i];
        		else
        			printUsage = true;
        	else if (args[i].startsWith("-"))
        		printUsage = true;
        	else
        		word = args[i];
        if (printHelp)
        	usage();
        else if (printUsage)
        	usage();
        else if (word == null) {
        	String mess = lang.equals("en")?"Error: no input word.":"Erreur : le mot à décomposer n'a pas été défini.";
        	usage(mess);
        }
        	
        startInitTime = Calendar.getInstance().getTimeInMillis();
        LinguisticDataAbstract.init(dataSource);
        endInitTime = Calendar.getInstance().getTimeInMillis();
      
        startTime = Calendar.getInstance().getTimeInMillis();
        
        //**********************************************
        String decsS = decomposeToMultilineString(word);
        //**********************************************
        
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.print(decsS);
        if (displayTimes) {
        	System.out.println((lang.equals("en")?"init time: ":"temps d'initialisation : ")+(endInitTime-startInitTime));
        	System.out.println((lang.equals("en")?"init time: ":"temps de d'analyse : ")+(endTime-startTime));
        }
    }
    
    public static String decomposeToMultilineString(String word) {
    	String decExprs[] = decomposeToArrayOfStrings(word);
        // Préparation de l'affichage des résultats.
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < decExprs.length; i++) {
			sb.append(decExprs[i]).append(System.getProperty("line.separator"));
		}
        return (sb.toString());
    	
    }
    
    public static String[] decomposeToArrayOfStrings(String word) {
        // Décodage URL du mot, au cas où il a été codé avant d'être transmis
        // par l'application.
        word = MonURLDecoder.decode(word).trim();

        // Si le mot est en UNICODE inuktitut, le translittérer en caractères latins.
        Text txt = new Text(word);
        if (txt.containsUnicodeInuktitut()) {
            word = TransCoder.unicodeToRoman(word);
        }
        // Décomposition du mot.
        Decomposition[] decs = MorphInuk.decomposeWord(word);
        String[] decExprs = new String[decs.length];
        // Préparation de l'affichage des résultats.
		for (int i = 0; i < decs.length; i++) {
			decExprs[i] = decs[i].toStr2();
		}
        return decExprs;
    }
    
	static public String getMeaningsInString (String decstr, String lang, 
			boolean includeSurface, boolean includeId) {
		return Decomposition.getMeaningsInString (decstr, lang, includeSurface, includeId);
	}
	
	static public String[] getMeaningsInArrayOfStrings (String decstr, String lang, 
			boolean includeSurface, boolean includeId) {
		return Decomposition.getMeaningsInArrayOfStrings (decstr, lang, includeSurface, includeId);
	}
	
//	protected static boolean validInuktitutWord (String word) {
//        String w = MonURLDecoder.decode(word).trim();
//        Pattern platin = Pattern.compile("[aiubdghjklmnpqrstv&]+");
//        Matcher mlatin = platin.matcher(w);
//        if (mlatin.matches())
//        	return true;
//        Pattern psyll = Pattern.compile("[\u1400-\u166F]+");
//        Matcher msyll = psyll.matcher(w);
//        if (msyll.matches())
//        	return true;
//        return false;
//    }

}