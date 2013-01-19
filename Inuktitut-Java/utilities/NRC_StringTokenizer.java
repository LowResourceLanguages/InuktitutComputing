/*
 * Conseil national de recherche Canada 2005/
 * National Research Council Canada 2005
 * 
 * Créé le / Created on Feb 2, 2005
 * par / by Benoit Farley
 * 
 */
package utilities;

import java.util.StringTokenizer;
import java.util.Vector;

public class NRC_StringTokenizer extends StringTokenizer {
    
    String string;
    
    public NRC_StringTokenizer(String str) {
        super(str);
        string = str;
    }
    
    public NRC_StringTokenizer(String str, String delimiter) {
        super(str,delimiter);
        string = str;
    }
    
    public NRC_StringTokenizer(String str, String delimiter, boolean returnsDelim) {
        super(str,delimiter,returnsDelim);
        string = str;
    }
    
    public String[] toArray() {
        Vector v = new Vector();
        while (hasMoreTokens()) {
            v.add(nextToken());
        }
        return (String [])v.toArray(new String[]{});
    }

}
