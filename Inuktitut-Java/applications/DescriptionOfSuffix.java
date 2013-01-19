//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2003
//           (c) National Research Council of Canada, 2003
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		DefinitionDeSuffixe.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: La méthode statique 'getDef' retourne dans un format HTML
//              l'information sur un suffixe.
//
// -----------------------------------------------------------------------


package applications;

import data.LinguisticDataAbstract;
import java.io.*;
import utilities1.Util;


public class DescriptionOfSuffix {
	
    static public void main(String args[]) {
    	if (args.length==0) {
    		usage("Argument missing");
    	}
    	String source = Util.getArgument(args, "s");
    	boolean res = LinguisticDataAbstract.init(source);
    	if ( ! res ) {
    		usage("'csv' is the only one accepted value for the \"source\" option '-s'");
    	}
        try {
			data.Suffixes.getDef(args,new PrintStream(System.out,true,"utf-8"));
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
		}
    }
    
    static void usage(String mess) {
		System.err.println("args: [-s csv] -m <suffix id>");
		System.err.println(mess);
		System.exit(1);    		
    }

}
