/*
 * Conseil national de recherche Canada 2007/
 * National Research Council Canada 2007
 * 
 * Créé le / Created on Apr 27, 2007
 * par / by Benoit Farley
 * 
 */
package data;

import java.io.IOException;
import java.io.InputStream;

public class Examples {

    public String filename = "data/examples.txt";
    
    public Examples() {
        
    }
    
    public InputStream getExampleStream() {
        ClassLoader cl = this.getClass().getClassLoader();
        java.net.URL url = cl.getResource(filename);
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
        }
        return is;
    }
    
}
