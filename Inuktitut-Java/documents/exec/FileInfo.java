/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Jul 20, 2006
 * par / by Benoit Farley
 * 
 */
package documents.exec;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import documents.NRC_Document;
import documents.NRC_DocumentException;

import utilities1.Util;

public class FileInfo {

    /**
     * @param args
     */
    public static void main(String[] args)  {
        String urlName = Util.getArgument(args,"url");
        URL url = null;
        try {
            url = new URL(urlName);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        URLConnection conn = null;
        InputStream is = null;
        try {
            conn = url.openConnection();
//            conn.setRequestProperty("Accept-Charset","utf-8,*;q=0.8");
            conn.setDoInput(true);


            Map requestProperties;
            Set keysSet;
            Object keys[];
            requestProperties = conn.getRequestProperties();
            keysSet = requestProperties.keySet();
            keys = keysSet.toArray();
            char spaces[] = new char[urlName.length()];
            Arrays.fill(spaces,'-');
            System.out.println(spaces);
            System.out.println(urlName);
            System.out.println("Request properties:");
            for (int i=0; i<keys.length; i++) {
                System.out.print("    "+keys[i].toString());
                System.out.println(" = "+requestProperties.get(keys[i]).toString());
            }

            is = conn.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        try {
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // GET http://langcom.nu.ca/iu_index.html HTTP/1.1
        String contentType = conn.getContentType();
        String contentLanguage = conn.getHeaderField("content-language");
        System.out.println("content-language: "+contentLanguage);
        String contentLength = conn.getHeaderField("content-length");
        System.out.println("content-length: "+contentLength);
        System.out.println("content-type: "+contentType);
        String x = conn.getHeaderField("refresh");
        System.out.println("refresh: "+x);
        Pattern ct = Pattern.compile("text/html;\\s*charset=(.+)");
        if (contentType != null) {
            Matcher ctM = ct.matcher(contentType);
            if (ctM.find()) {
                System.out.println("charset dans HTTP: "+ctM.group(1));
            }
        }

        byte[] buf = new byte[2048];
        int len=0;
        int c;
        try {
            while ((c=is.read())!=-1 && len<2047) {
                buf[len++]=(byte)c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(buf);
        Pattern xml = Pattern.compile("<\\?xml\\s+?(.+?)\\s*?\\?>");
        Pattern html = Pattern.compile("<[mM][eE][tT][aA]\\s+?(.+?)\\s*?/?>");
        Matcher xmlM = xml.matcher(str);
        Matcher htmlM = html.matcher(str);
        if (xmlM.find()) {
            String match = xmlM.group(1);
            Pattern encoding = Pattern.compile("encoding=\"(.+?)\"");
            Matcher encodingM = encoding.matcher(match);
            if (encodingM.find()) {
                System.out.println("charset dans <?xml>: "+encodingM.group(1));                        
            }
        } else if (htmlM.find()) {
            String match = htmlM.group(1);
            Pattern content = Pattern.compile("content=\"text/html;\\s*charset=(.+)\"");
            Matcher contentM = content.matcher(match);
            if (contentM.find()) {
                System.out.println("charset dans <meta>: "+contentM.group(1));
            }
        } else {
            
        }
        try {
            is.close();
            if (conn instanceof HttpURLConnection)
                ((HttpURLConnection)conn).disconnect();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        NRC_Document doc = null;
        try {
            doc = NRC_Document.Exec.getDocument(urlName);
            Date date = doc.getDate();
            System.out.println("Date: "+date.toString());
            String title = doc.getTitle();
            System.out.println("Title: "+title);
            String fonts[] = doc.getAllFontsNames();
            String fs = Util.array2string(fonts);
            System.out.println("Fonts: "+fs);
            System.out.println("Preferred font: "+doc.getPreferredFont());
        } catch (NRC_DocumentException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	doc.close();
        }
    }


}
