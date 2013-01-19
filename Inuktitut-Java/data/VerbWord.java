/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Nov 2, 2006
 * par / by Benoit Farley
 * 
 */
package data;

import java.util.HashMap;
import java.util.Hashtable;

public class VerbWord {

    public String verb;
    public String passive;
    public HashMap record;
    static public Hashtable hash = new Hashtable();

    public VerbWord(HashMap v) {
        record = v;
        verb = (String) v.get("verb");
        passive = (String) v.get("passive");
    }

    static public void addToHash(String key, Object obj) {
        hash.put(key,obj);
    }

}
