/*
 * Conseil national de recherche Canada 2007/
 * National Research Council Canada 2007
 * 
 * Créé le / Created on Jan 12, 2007
 * par / by Benoit Farley
 * 
 */
package phonology;

public class PhonologicalTransformation {

    public int position; // position dans la chaîne originale
    public String group1;
    public String group2;
    
    public PhonologicalTransformation (String gr1, String gr2, int pos) {
        position = pos;
        group1 = gr1;
        group2 = gr2;
    }
}
