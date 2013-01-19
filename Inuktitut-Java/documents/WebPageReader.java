package documents;

import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
//import org.mozilla.intl.chardet.*; // voir http://www.i18nfaq.com/chardet.html


public class WebPageReader {
	public URLConnection connection;
	public URL url;
    public String contentType;

    public WebPageReader(URL url) throws Exception {
        this.url = url;
        wpr(url);
    }
    
	public WebPageReader(String urlName) throws MalformedURLException,
            IOException {
		try {
        url = new URL(urlName);
        wpr(url);
        connection.connect();
		} 
		catch (MalformedURLException e1) {
			throw new MalformedURLException(e1.toString()+" ["+urlName+"]");
		}
		catch (IOException e2) {
			throw new IOException ("From WebPageReader: "+e2.toString()+" ["+urlName+"]");
		}
    }
    
    void wpr(URL url) throws IOException {
        connection = (URLConnection) url.openConnection();
        //a 2 min.
        connection.setReadTimeout(120000); 
        /*
         * V�rifier s'il y a re-direction de la page. Dans ce cas, il y aura
         * dans les Headers HTTP un code qui indique que la page de r�f�rence a
         * �t� d�plac�e, et l'adresse o� la trouver (Header "Location").
         */
        if (url.getProtocol().startsWith("http")) {
            int code = ((HttpURLConnection) connection).getResponseCode();
            if (mustRedirect(code))
                secureRedirect(connection.getHeaderField("Location"));
        }
        contentType = connection.getContentType();
//        determinerEncodage();
//        System.out.println("Encoding: " + encoding);
    }


	public BufferedReader getBufferedReader() throws IOException {
        return getBufferedReader("ISO-8859-1");
    }


	public BufferedReader getBufferedReader(String encodage) throws IOException {
        BufferedReader in = null;
        if (encodage==null)
            encodage = "ISO-8859-1";
        InputStream is = null;
        try {
        	is = connection.getInputStream();
        }
        catch (IOException e) {
        	throw new IOException(e.toString()+" from connection["+connection.toString()+"].getInputStream() in getBufferedReader");
        }
        in = new BufferedReader(new HTMLReaderPatch(new InputStreamReader(
                is, encodage)));
        return in;
    }



	public String getContents() {
		BufferedReader in = null;
		StringBuffer contents = new StringBuffer();
		// On lit l'input stream avec cet encodage, qui permet de r�cup�rer
		// les octets originaux.
		String encoding = "iso-8859-1";
		try {
//			if (encoding == null)
//				in =
//					new BufferedReader(
//					        new HTMLReaderPatch(
//						new InputStreamReader(connection.getInputStream())
//						));
//			else
				in =
					new BufferedReader(
					        new HTMLReaderPatch(
						new InputStreamReader(
							connection.getInputStream(),
							encoding)
							));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				contents.append("\n" + inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contents.toString();
	}

	//---------------REDIRECTION------------------
	// Code pour redirection de pages s�curis�es
	private static boolean mustRedirect(int code) {
		if (code == HttpURLConnection.HTTP_MOVED_PERM
			|| code == HttpURLConnection.HTTP_MOVED_TEMP) {
			return true;
		} else
			return false;
	}

	private void secureRedirect(String location) throws IOException {
		//        System.out.println(location);
		url = new URL(location);
		connection = (HttpsURLConnection) url.openConnection();
	}
	//---------------REDIRECTION------------------

	//-----------------ENCODAGE-------------------
	
	// La classe nsDectector fonctionne curieusement.  Par exemple, elle
	// retourne CHARSET=US-ASCII pour une page encod�e en UTF-8.
//	private void determinerEncodage() {
//	   String encoding = null;
//	    String contentType = connection.getContentType();
//	    StringTokenizer st1 = new StringTokenizer(contentType,";");
//	    st1.nextToken();
//	    if (st1.hasMoreTokens()) {
//	        String charsetValue = st1.nextToken();
//	        StringTokenizer st2 = new StringTokenizer(charsetValue,"=");
//	        st2.nextToken();
//	        encoding = st2.nextToken();
//	        return;
//	    }
//
//	    nsDetector det = new nsDetector(nsPSMDetector.ALL);
//		// Set an observer...
//		// The Notify() will be called when a matching charset is found.
//
//		det.Init(new nsICharsetDetectionObserver() {
//			public void Notify(String charset) {
//				HtmlCharsetDetector.found = true;
//				String encoding = charset;
//			}
//		});
//
//		BufferedInputStream imp = null;
//		boolean isAscii = true;
//		try {
//			imp = new BufferedInputStream(url.openStream());
//
//			byte[] buf = new byte[1024];
//			int len;
//			boolean done = false;
//
//			while ((len = imp.read(buf, 0, buf.length)) != -1) {
//
//				// Check if the stream is only ascii.
//				if (isAscii)
//					isAscii = det.isAscii(buf, len);
//
//				// DoIt if non-ascii and not done yet.
//				if (!isAscii && !done)
//					done = det.DoIt(buf, len, false);
//			}
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		det.DataEnd();
//
//		if (isAscii) {
//			encoding = "US-ASCII";
//			HtmlCharsetDetector.found = true;
//		}
//		
//		if (encoding==null)
//		    encoding = "ISO-8859-1";
//
//	}
	//-----------------ENCODAGE-------------------

}
