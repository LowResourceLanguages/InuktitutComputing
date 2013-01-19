 
/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 29-Mar-2004 par Benoit Farley
 *  
 */
package applications;

import data.LinguisticDataAbstract;
import java.io.*;
import utilities1.Util;

/*
 * Ce programme est appel� par le serveur serveurInuktitut. Dans ce cas, c'est
 * qu'une requ�te a �t� faite � partir d'une page HTML. Les arguments d'appel
 * sont alors le mot s�lectionn� dans cette page et l'URL de cette page. On a
 * normalement un troisi�me argument qui d�signe la langue dans laquelle la
 * d�finition doit �tre retourn�e.
 * 
 * -f : nom du fichier d'o� provient le mot � d�composer 
 * -p : codage des caract�res: police 
 * -l : langue de l'usager ("e" ou "f")
 * -m : mot � d�composer
 * 
 * On peut aussi, cependant, appeler ce programme directement en lui fournissant
 * les arguments n�cessaires. Le seul argument n�cessaire est le mot � d�finir.
 * 
 * L'URL du fichier est utilis� lorsque le mot s�lectionn� � d�finir n'est pas
 * reconnu comme du texte Inuktitut en Unicode. Il est alors possible que ce
 * soit un mot inuktitut affich� avec une police � 7 bits qui donne une
 * interpr�tation diff�rente aux codes. Il faut alors identifier la police avec
 * laquelle le mot a �t� affich� pour d�terminer s'il s'agit d'une police
 * inuktitut connue.
 * 
 * Le produit de ce programme est une cha�ne contenant du code HTML qui sera
 * affich� dans une fen�tre cr�� par le programme appelant.
 */

public class DecompositionOfWord {

    public static void main(String args[]) {
    	if (args.length==0) {
    		usage("Argument missing");
    	}
    	String source = Util.getArgument(args, "s");
    	boolean res = LinguisticDataAbstract.init(source);
    	if ( ! res ) {
    		usage("'csv' is the only one accepted value for the \"source\" option '-s'");
    	}
        boolean htmlOutput = true;
        if (args[0].equals("-plain"))
        	htmlOutput = false;
        try {
            data.Words.getDef(args, new PrintStream(System.out, false, "UTF-8"), htmlOutput);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    static void usage(String mess) {
		System.err.println("args: [-s csv] -m <word>");
		System.err.println(mess);
		System.exit(1);    		
    }

}