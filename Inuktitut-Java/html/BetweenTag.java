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


public class BetweenTag implements HTMLDocuElement {
        int type;
        byte bytes[];  
        public String text;
        String encoding = "ISO-8859-1";
        public String font = "null";
        
        public BetweenTag(DynamicArrayOfByte bs) {
            type = BETWEENTAG;
            bytes = bs.getBytes();
        }
        public BetweenTag(String txt, String encoding) throws UnsupportedEncodingException {
            type = BETWEENTAG;
            if (encoding == null)
            	bytes = txt.getBytes();
            else
            	bytes = txt.getBytes(encoding);
            text = txt;
        }
        public BetweenTag(String txt) {
            type = BETWEENTAG;
            bytes = txt.getBytes();
            text = txt;
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
            this.text = text;
            try {
                bytes = text.getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
            }
            this.encoding = encoding;
        }
		public String getEncodingFromMetaTag() {
			return encoding;
		}
		public String getText() {
			return text;
		}
    }

