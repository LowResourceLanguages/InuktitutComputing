/*
 * Conseil national de recherche Canada 2007/
 * National Research Council Canada 2007
 * 
 * Créé le / Created on Mar 1, 2007
 * par / by Benoit Farley
 * 
 */
package applications;

import script.TransCoder;

public class TranslitText {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String translit = TransCoder.legacyToRoman(args[0], args[1]);
        System.out.println(translit);
    }

}
