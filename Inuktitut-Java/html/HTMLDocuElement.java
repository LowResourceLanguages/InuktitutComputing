/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Feb 4, 2006
 * par / by Benoit Farley
 * 
 */
package html;

import java.io.UnsupportedEncodingException;

public interface HTMLDocuElement {
        static int TAG = 1;
        static int BETWEENTAG = 2;
        int getType();
        byte[] getBytes();
        String getEncodingFromMetaTag();
        String getText();
        void setBytes(byte bs[]);
        void setText(String encoding) throws UnsupportedEncodingException;
        void setText(String text, String encoding);
        static String[] tagsThatDoNotAddSpace = 
            {"a", "abbr", "acronym", "b", "big", "cite", "code", "del", "dfn", 
            "em", "font", "i", "ins", "kbd",  
            "samp", "small", "span", "strike", "strong",   
            "sub", "sup", "tt", "u", "var"};
    }

