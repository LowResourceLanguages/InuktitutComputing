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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tag implements HTMLDocuElement {
    Pattern tagPattern = Pattern.compile("^<([^\\s>]+)");
    public String tag;
    public String text;
    int type;
    byte bytes[];
    String encoding = "ISO-8859-1";
    
    public Tag(DynamicArrayOfByte bs) {
        type = TAG;
        bytes = bs.getBytes();
        text = new String(bytes);
        Matcher m = tagPattern.matcher(text);
        if (m.find())
        	tag = m.group(1).toLowerCase();
        else
        	tag = "";
    }
        
    public Tag(String txt, String encoding) {
        type = TAG;
        try {
            if (encoding==null)
                bytes = txt.getBytes();
            else
                bytes = txt.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            bytes = txt.getBytes();
        }
        text = txt;
        Matcher m = tagPattern.matcher(text);
        m.find();
        tag = m.group(1).toLowerCase();
    }
        
        public String getEncodingFromMetaTag() {
            String encoding = null;
            if (tag.equals("meta")) {
                Pattern phe = Pattern.compile("http-equiv\\s*=\\s*[\"\']?content-type[\"\']?");
                Matcher mphe = phe.matcher(text.toLowerCase());
                if (mphe.find()) {
                    Pattern pcs = Pattern.compile("content\\s*=\\s*[\"\']?text/html\\s*;\\s*charset\\s*=\\s*[\"\']?([^\"\'>]+)[\"\']?");
                    Matcher mpcs = pcs.matcher(text.toLowerCase());
                    if (mpcs.find())
                        encoding = mpcs.group(1);
                }
            }
            return encoding;
        }

    public int getType() {
        return type;
    }
    public byte[] getBytes() {
        return bytes;
    }
    public void setBytes(byte bs[]) {
        bytes = bs;
    }

    public void setText(String encoding) throws UnsupportedEncodingException {
        text = new String(bytes,encoding);
        this.encoding = encoding;
    }

    public void setText(String text, String encoding) {
        // TODO Auto-generated method stub
        
    }
    
    public static boolean isTagThatAddsSpace(String tagName) {
        if (Arrays.binarySearch(tagsThatDoNotAddSpace,tagName) >= 0)
            return false;
        else
            return true;
    }

    public String getEncoding() {
    	return null;
    }
    
	public String getText() {
		return text;
	}
}

