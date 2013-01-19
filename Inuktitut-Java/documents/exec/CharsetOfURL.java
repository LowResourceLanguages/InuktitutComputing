/*
 * Conseil national de recherche Canada 2004/
 * National Research Council Canada 2004
 * 
 * Cr�� le / Created on Dec 20, 2004
 * par / by Benoit Farley
 * 
 */
package documents.exec;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.net.www.protocol.http.HttpURLConnection;

public class CharsetOfURL {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err
					.println("Usage: java -classpath <...>/documentHandling.jar documents.CharsetOfURL <URL>");
			System.exit(1);
		}
		URL url;
		HttpURLConnection conn;
		Vector list = new Vector(Arrays.asList(args));

		for (int n = 0; n < list.size(); n++) {
			try {
				url = new URL(list.get(n).toString());
				conn = (HttpURLConnection) url.openConnection();
				Map requestProperties;
				Set keysSet;
				Object keys[];
				conn.setRequestProperty("Accept-Charset", "utf-8,*;q=0.8");
				requestProperties = conn.getRequestProperties();
				keysSet = requestProperties.keySet();
				keys = keysSet.toArray();
				char spaces[] = new char[list.get(n).toString().length()];
				Arrays.fill(spaces, '-');
				System.out.println(spaces);
				System.out.println(list.get(n).toString());
				System.out.println("Request properties:");
				for (int i = 0; i < keys.length; i++) {
					System.out.print("    " + keys[i].toString());
					System.out.println(" = "
							+ requestProperties.get(keys[i]).toString());
				}
				conn.connect();
				// GET http://langcom.nu.ca/iu_index.html HTTP/1.1
				// InputStream is =
				// HttpURLConnection.openConnectionCheckRedirects(conn);
				String contentType = conn.getContentType();
				String contentLanguage = conn
						.getHeaderField("content-language");
				System.out.println("content-language: " + contentLanguage);
				String contentLength = conn.getHeaderField("content-length");
				System.out.println("content-length: " + contentLength);
				System.out.println("content-type: " + contentType);
				String x = conn.getHeaderField("refresh");
				System.out.println("refresh: " + x);
				String transferEncoding = conn
						.getHeaderField("Client-Transfer-Encoding");
				System.out.println("client-transfer-encoding: "
						+ transferEncoding);
				Pattern ct = Pattern.compile("text/html;\\s*charset=(.+)");
				if (contentType != null) {
					Matcher ctM = ct.matcher(contentType);
					if (ctM.find()) {
						System.out
								.println("charset dans HTTP: " + ctM.group(1));
					}
				}
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[2048];
				int len = 0;
				int c;
				while ((c = is.read()) != -1 && len < 2047) {
					buf[len++] = (byte) c;
				}
				String str = new String(buf);
				// System.out.println(str);
				Pattern bom = Pattern.compile("^(\u00ef\u00bb\u00bf)<");
				Pattern xml = Pattern.compile("<\\?xml\\s+?(.+?)\\s*?\\?>");
				Pattern html = Pattern
						.compile("<[mM][eE][tT][aA]\\s+?(.+?)\\s*?/?>");
				Matcher bomM = bom.matcher(str);
				Matcher xmlM = xml.matcher(str);
				Matcher htmlM = html.matcher(str);
				// System.out.println("Looking for <?xml>");
				if (xmlM.find()) {
					// System.out.println("\t<?xml> found");
					// System.out.println("\tLooking for 'charset' in <?xml>");
					String match = xmlM.group(1);
					Pattern encoding = Pattern.compile("encoding=\"(.+?)\"");
					Matcher encodingM = encoding.matcher(match);
					if (encodingM.find()) {
						System.out.println("*** charset dans <?xml>: "
								+ encodingM.group(1));
					}
				}
				int pos = 0;
				boolean found = false;
				// System.out.println("Looking for <meta>");
				while (!found && pos < str.length() && htmlM.find(pos)) {
					// System.out.println("\t<meta> found");
					String match = htmlM.group(1);
					// System.out.println("\tLooking for 'charset' in <meta ...
					// " + match + ">");
					Pattern content = Pattern
							.compile("content=\"text/html;\\s*charset=(.+)\"");
					Matcher contentM = content.matcher(match);
					if (contentM.find()) {
						found = true;
						System.out.println("*** charset dans <meta>: "
								+ contentM.group(1));
					} else {
						pos = htmlM.end();
					}
				}
				// System.out.println("Looking for BOM");
				if (bomM.find()) {
					String match = bomM.group(1);
					System.out.println("*** charset par BOM: " + match);
				}

				conn.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("*** error: File Not Found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
