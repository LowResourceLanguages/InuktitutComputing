/*
 * Conseil national de recherche Canada 2004/ National Research Council Canada
 * 2004
 * 
 * Cr�� le / Created on Oct 14, 2004 par / by Benoit Farley
 *  
 */
package data.exec;

import java.util.Vector;

import data.Affix;
import data.Lexicon;
import data.LinguisticDataAbstract;
import data.Morpheme;
import data.SurfaceFormOfAffix;


public class InfoMorpheme {
    
    public static void main(String[] args) {
        Vector morphVec = new Vector();
        boolean csv = false;
        String word = null;
        try {
        for (int ia=0; ia<args.length; ia++)
            if (args[ia].equals("-csv"))
                csv = true;
            else if (args[ia].startsWith("-"))
            	throw new Exception("L'option "+args[ia]+" n'est pas valide.");
            else
                word = args[ia];
        System.out.println("Recherche de '"+word+"' dans la base de données"+(csv?".":" dompée.")+"\n");
        LinguisticDataAbstract.init(csv?"csv":null);
        // Chercher dans les racines
        Vector bases = Lexicon.lookForBase(word, false);
        if (bases != null)
            morphVec.addAll(bases);
        Vector forms = LinguisticDataAbstract.getSurfaceForms(word);
        Vector affs = new Vector();
        if (forms != null) {
            for (int i=0; i<forms.size(); i++) {
                SurfaceFormOfAffix form = (SurfaceFormOfAffix)forms.elementAt(i);
                Affix aff = form.getAffix();
                if (!affs.contains(aff))
                    affs.add(aff);
            }
            morphVec.addAll(affs);
        }
        
        for (int j = 0; j < morphVec.size(); j++) {
            Morpheme morph = (Morpheme) morphVec.elementAt(j);
            System.out.println(morph.showData() + "\n");
        }
    } catch (Exception e) {
    	System.err.println(e.getMessage());
    }
    }
}