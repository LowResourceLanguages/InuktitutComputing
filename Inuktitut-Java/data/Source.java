/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Nov 22, 2006
 * par / by Benoit Farley
 * 
 */
package data;

import java.util.HashMap;
import java.util.Hashtable;

public class Source {

   public String id;
   public String authorSurName;
   public String authorMidName;
   public String authorFirstName;
    public String title;
    public String subtitle;
    public String publisher;
    public String publisherMisc;
    public String location;
    public String year;

    static public Hashtable hash = new Hashtable();

    HashMap record;

    public Source(HashMap v) {
        record = v;
        id = (String) v.get("id");
        authorSurName = (String) v.get("authorSurName");
        authorMidName = (String) v.get("authorMidName");
        authorFirstName = (String) v.get("authorFirstName");
        title = (String) v.get("title");
        subtitle = (String) v.get("subtitle");
        publisher = (String) v.get("publisher");
        publisherMisc = (String) v.get("publisherMisc");
        location = (String) v.get("city/country");
        year = (String) v.get("year");
    }
    
    static public void addToHash(String key, Object obj) {
        hash.put(key,obj);
    }
        
}
