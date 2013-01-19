/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Mar 2, 2006
 * par / by Benoit Farley
 * 
 */
package data.constraints;

import data.Morpheme;

public interface Conditions {

    boolean isMetBy(Morpheme m);
    boolean isMetByFullMorphem(Morpheme m);
    String toText(String lang);
    Condition expand();
}
