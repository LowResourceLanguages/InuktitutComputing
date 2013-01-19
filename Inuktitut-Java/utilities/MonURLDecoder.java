//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		MonURLDecoder.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: Version particulière de URLDecoder.
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: MonURLDecoder.java,v 1.1 2009/06/19 19:38:33 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: MonURLDecoder.java,v $
// Revision 1.1  2009/06/19 19:38:33  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.3  2006/04/24 17:20:04  farleyb
// Élimination d'une méthode.
//
// Revision 1.2  2005/08/19 17:21:33  farleyb
// *** empty log message ***
//
// Revision 1.1  2003/10/10 06:01:09  desiletsa
// Première sauvegarde
//
// Revision 1.0  2003-06-25 13:19:54-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:34:46-05  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


/*
 * @(#)URLDecoder.java	1.9 00/02/02
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package utilities;

import java.io.*;

/**
 * The class contains a utility method for converting from
 * a MIME format called "<code>x-www-form-urlencoded</code>"
 * to a <code>String</code>
 * <p>
 * To convert to a <code>String</code>, each character is examined in turn:
 * <ul>
 * <li>The ASCII characters '<code>a</code>' through '<code>z</code>',
 * '<code>A</code>' through '<code>Z</code>', and '<code>0</code>'
 * through '<code>9</code>' remain the same.
 * <li>The plus sign '<code>+</code>'is converted into a
 * space character '<code>&nbsp;</code>'.
 * <li>The remaining characters are represented by 3-character
 * strings which begin with the percent sign,
 * "<code>%<i>xy</i></code>", where <i>xy</i> is the two-digit
 * hexadecimal representation of the lower 8-bits of the character.
 * </ul>
 *
 * @author  Mark Chamness
 * @author  Michael McCloskey
 * @version 1.9, 02/02/00
 * @since   1.2
 */

public class MonURLDecoder {

/**
 * Decodes a &quot;x-www-form-urlencoded&quot; 
 * to a <tt>String</tt>.
 * @param s the <code>String</code> to decode
 * @return the newly decoded <code>String</code>
 */
    public static String decode(String s) {
        if (s==null)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char)Integer.parseInt(
                                        s.substring(i+1,i+3),16));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        // Undo conversion to external encoding
        String result = sb.toString();
	try {
	    byte[] inputBytes = result.getBytes("8859_1");
	    // UTF-8 is necessary because the Javascript 
	    // encodeURIComponent() transforms in UTF-8. (Benoit Farley)
	    result = new String(inputBytes,"UTF-8");
	 } catch (UnsupportedEncodingException e) {
            // The system should always have 8859_1
	 }
        return result;
    }

    /*
     * If the form's method is "get" and the action is an HTTP URI, the user
     * agent takes the value of action, appends a `?' to it, then appends the
     * form data set, encoded using the "application/x-www-form-urlencoded"
     * content type.
     * 
     * Some codes like %86 (used in the font Aipainunavik for the syllabic 
     * character 'tii') do not correspond to any character, such that making a
     * character from the integer is wrong. We rather return a Unicode string:
     * %86 becomes \u0086.
     */
    public static String decodeEncodedSubmittedForm(String s) {
        if (s==null)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char)Integer.parseInt(
                                s.substring(i+1,i+3),16));
                    } catch (NumberFormatException e) {
                        System.err.println("s: \""+s+"\"");
                        System.err.println("position: "+i+" \""+s.substring(i,i+3)+"\"");
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        // Undo conversion to external encoding
        String result = sb.toString();
        return result;
    }
}


