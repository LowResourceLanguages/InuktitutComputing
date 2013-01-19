/*
 * Conseil national de recherche Canada 2004/
 * National Research Council Canada 2004
 * 
 * Créé le / Created on 14-Sep-2004
 * par / by Benoit Farley
 * 
 */
package utilities;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class MyCharsetDecoder extends CharsetDecoder {

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    protected MyCharsetDecoder(Charset arg0, float arg1, float arg2) {
        super(arg0, arg1, arg2);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see java.nio.charset.CharsetDecoder#decodeLoop(java.nio.ByteBuffer, java.nio.CharBuffer)
     */
    protected CoderResult decodeLoop(ByteBuffer arg0, CharBuffer arg1) {
        // TODO Auto-generated method stub
        return null;
    }

}
