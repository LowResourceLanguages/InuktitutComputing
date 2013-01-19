/*
 * Conseil national de recherche Canada 2005/
 * National Research Council Canada 2005
 * 
 * Créé le / Created on Aug 18, 2005
 * par / by Benoit Farley
 * 
 */
package lib;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import utilities1.Util;

/**
 * cgi_lib.java<p>
 *
 * <p>
 *
 * Usage:  This library of java functions, which I have encapsulated inside
 *         a class called cgi_lib as class (static) member functions,
 *         attempts to duplicate the standard PERL CGI library (cgi-lib.pl).
 *
 *         You must invoke any Java program that uses this library from
 *         within a UNIX script, Windows batch file or equivalent.  As you
 *         will see in the following example, all of the CGI environment
 *         variables must be passed from the script into the Java application
 *         using the -D option of the Java interpreter.  This example
 *         UNIX script uses the "main" routine of this class as a
 *         CGI script:
 *
 * <pre>
 * (testcgi.sh)
 *
 * #!/bin/sh
 *
 * java \
 *  -Dcgi.content_type=$CONTENT_TYPE \
 *  -Dcgi.content_length=$CONTENT_LENGTH \
 *  -Dcgi.request_method=$REQUEST_METHOD \
 *  -Dcgi.query_string=$QUERY_STRING \
 *  -Dcgi.server_name=$SERVER_NAME \
 *  -Dcgi.server_port=$SERVER_PORT \
 *  -Dcgi.script_name=$SCRIPT_NAME \
 *  -Dcgi.path_info=$PATH_INFO \
 * cgi_lib
 *
 * </pre>
 *
 *         Question and comments can be sent to pldurante@tasc.com.<p>
 *
 * @version 1.0
 * @author Pat L. Durante
 *
 */
public class cgi_lib
{

  /**
   *
   * Parse the form data passed from the browser into
   * a Hashtable.  The names of the input fields on the HTML form will
   * be used as the keys to the Hashtable returned.  If you have a form
   * that contains an input field such as this,<p>
   *
   * <pre>
   *   &ltINPUT SIZE=40 TYPE="text" NAME="email" VALUE="pldurante@tasc.com"&gt
   * </pre>
   *
   * then after calling this method like this,<p>
   *
   * <pre>
   *    Hashtable form_data = cgi_lib.ReadParse(System.in);
   * </pre>
   *
   * you can access that email field as follows:<p>
   *
   * <pre>
   *    String email_addr = (String)form_data.get("email");
   * </pre>
   *
   * @param inStream The input stream from which the form data can be read.
   * (Only used if the form data was posted using the POST method. Usually,
   * you will want to simply pass in System.in for this parameter.)
   *
   * @return The form data is parsed and returned in a Hashtable
   * in which the keys represent the names of the input fields.
   *
   */
    
    /*
     * Modified by Benoit Farley to allow for command line arguments to the
     * Inuktitut functions.  The arguments for each application are sent in the
     * following given order by the javascript links on the link bar of the browser:
     * 
     * DefinitionDeMot, DefinitionMot
     * application ;url ;langue; mot
     * c=DefinitionDeMot&f=http://localhost/inuk-bin/inuktitut.pl?c=TranslitPage&f=http://www.ihti.ca/inuktitut/projects.html&l=e&ittarnisalirinirmut

     * DefinitionDeSuffixe, DefinitionSuffixe
     * application; langue; suffixe
     * DefinitionDeRacine
     * application; langue; racine
     * ListeDesSuffixes, ConsultationSuffixes
     * application; langue
     * ListeDesRacines
     * application; langue
     * TranslitPage
     * application; url
     * 
     * In this case, the cgi.* environment variables are not set, and
     * MethGet() and MethPost() will return 'false'; then the hashtable
     * that is returned will be empty.
     */
    
    static Hashtable patternTable = new Hashtable();
    static {
        patternTable.put("DefinitionDeMot","^(&f=.+)&l=(.)&(m=)?(.+)$"); 
        patternTable.put("DefinitionMot","^(&f=.+)&l=(.)&(m=)?(.+)$"); 
        patternTable.put("DefinitionDeSuffixe","^&(l)=(.)&(m=)?(.+)$"); 
        patternTable.put("DefinitionSuffixe","^&(l)=(.)&(m=)?(.+)$"); 
        patternTable.put("DefinitionDeRacine","^&(l)=(.)&(m=)?(.+)$"); 
        patternTable.put("DefinitionRacine","^&(l)=(.)&(m=)?(.+)$"); 
        patternTable.put("ListeDesSuffixes","^&(l)=(.)$"); 
        patternTable.put("ConsultationSuffixes","^&(l)=(.)$"); 
        patternTable.put("ListeDesRacines","^&(l)=(.)$"); 
        patternTable.put("ConsultationRacines","^&(l)=(.)$"); 
        patternTable.put("TranslitPage","^(&f=.+)$");
        patternTable.put("SurlignageHTML","^(.+)$");
    };
    static     Pattern pComm = Pattern.compile("^c=([a-zA-Z]+)(&.+)$");
    
  public static String [] ReadParse(InputStream inStream, String [] args)
  {
      Hashtable form_data = new Hashtable();

      String inBuffer = "";

      if (MethGet())
      {
          inBuffer = System.getProperty("cgi.query_string");
      }
      else if (MethPost())
      {
          //
          //  TODO:  I should probably use the cgi.content_length property when
          //         reading the input stream and read only that number of
          //         bytes.  The code below does not use the content length
          //         passed in through the CGI API.
          //
          DataInput d = new DataInputStream(inStream);
          String line;
          try
          {
              while((line = d.readLine()) != null)
              {
                  inBuffer = inBuffer + line;
              }
          }
          catch (IOException ignored) { }
      } else {
          inBuffer = args[0];
      }
      
      Matcher mComm = pComm.matcher(inBuffer);
      /*
       * Because the f argument's value can be a url with arguments in the case
       * of the application TranslitPage, we have to do this.
       */
      String repl = null;
      if (mComm.matches()) {
          String commande = mComm.group(1);
          String arguments = mComm.group(2);
          Pattern p = Pattern.compile(patternTable.get(commande).toString());
          Matcher m = p.matcher(arguments);
          
          if (m.matches() && m.group(1).startsWith("&f=")) {
              repl = m.group(1);
              inBuffer = inBuffer.replaceFirst(repl,"");
          }
      }
      //
      //  Split the name value pairs at the ampersand (&)
      //
      StringTokenizer pair_tokenizer = new StringTokenizer(inBuffer,"&");

      while (pair_tokenizer.hasMoreTokens())
      {
          String pair = pair_tokenizer.nextToken();
          //
          // Split into key and value
          //
          StringTokenizer keyval_tokenizer = new StringTokenizer(pair,"=");
          String key = new String();
          String value = new String();
          if (keyval_tokenizer.hasMoreTokens())
            key = keyval_tokenizer.nextToken();
          else ; // ERROR - shouldn't ever occur
          if (keyval_tokenizer.hasMoreTokens())
            value = urlDecode(keyval_tokenizer.nextToken());
          else { // ERROR - shouldn't ever occur
              // But because the script javascript for Inuktitut Word Definition
              // initially did not send the word as a key/value pair, one has
              // to take that into account here, for backward compatibility.
              value = urlDecode(key);
              key = "m";         
          }
          //
          // Add key and associated value into the form_data Hashtable
          //
          form_data.put(key,value);
      }
      
      if (repl != null) {
          form_data.put("f",urlDecode(repl.substring(3)));
      }
      
      // Add the input argument string to the hashtable
      // at key "#input_argument_string"
      form_data.put("#input_argument_string",inBuffer);
      return Util.hashToArgsArray(form_data);

  }

  /**
   *
   * URL decode a string.<p>
   *
   * Data passed through the CGI API is URL encoded by the browser.
   * All spaces are turned into plus characters (+) and all "special"
   * characters are hex escaped into a %dd format (where dd is the hex
   * ASCII value that represents the original character).  You probably
   * won't ever need to call this routine directly; it is used by the
   * ReadParse method to decode the form data.
   *
   * @param in The string you wish to decode.
   *
   * @return The decoded string.
   *
   */

  public static String urlDecode(String in) {
        StringBuffer outsb = new StringBuffer(in.length());
        int i = 0;
        int j = 0;

        while (i < in.length()) {
            char ch = in.charAt(i);
            i++;
            if (ch == '+')
                ch = ' ';
            else if (ch == '%') {
                ch = (char) Integer.parseInt(in.substring(i, i + 2), 16);
                i += 2;
            }
            outsb.append(ch);
            j++;
        }
        String out = outsb.toString();
        try {
            byte[] inputBytes = out.getBytes("8859_1");
            // UTF-8 is necessary because the Javascript
            // encodeURIComponent() transforms in UTF-8. (Benoit Farley)
            out = new String(inputBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // The system should always have 8859_1
        }
        return out;
    }

  /**
   *
   * Generate a standard HTTP HTML header.
   *
   * @return A String containing the standard HTTP HTML header.
   *
   */
  public static String Header()
  {
      return "Content-type: text/html\n\n";
  }

  /**
   *
   * Generate some vanilla HTML that you usually
   * want to include at the top of any HTML page you generate.
   *
   * @param Title The title you want to put on the page.
   *
   * @return A String containing the top portion of an HTML file.
   *
   */
  public static String HtmlTop(String Title)
  {
      String Top = new String();
      Top = "<html>\n";
      Top+= "<head>\n";
      Top+= "<title>\n";
      Top+= Title;
      Top+= "\n";
      Top+= "</title>\n";
      Top+= "</head>\n";
      Top+= "<body>\n";

      return Top;

  }

  /**
   *
   * Generate some vanilla HTML that you usually
   * want to include at the bottom of any HTML page you generate.
   *
   * @return A String containing the bottom portion of an HTML file.
   *
   */
  public static String HtmlBot()
  {
      return "</body>\n</html>\n";
  }

  /**
   *
   * Determine if the REQUEST_METHOD used to
   * send the data from the browser was the GET method.
   *
   * @return true, if the REQUEST_METHOD was GET.  false, otherwise.
   *
   */
  public static boolean MethGet()
  {
     String RequestMethod = System.getProperty("cgi.request_method");
     boolean returnVal = false;

     if (RequestMethod != null)
     {
         if (RequestMethod.equals("GET") ||
             RequestMethod.equals("get"))
         {
             returnVal=true;
         }
     }
     return returnVal;
  }

  /**
   *
   * Determine if the REQUEST_METHOD used to
   * send the data from the browser was the POST method.
   *
   * @return true, if the REQUEST_METHOD was POST.  false, otherwise.
   *
   */
  public static boolean MethPost()
  {
     String RequestMethod = System.getProperty("cgi.request_method");
     boolean returnVal = false;

     if (RequestMethod != null)
     {
         if (RequestMethod.equals("POST") ||
             RequestMethod.equals("post"))
         {
             returnVal=true;
         }
     }
     return returnVal;
  }

  /**
   *
   * Determine the Base URL of this script.
   * (Does not include the QUERY_STRING (if any) or PATH_INFO (if any).
   *
   * @return The Base URL of this script as a String.
   *
   */
  public static String MyBaseURL()
  {
      String returnString = new String();
      returnString = "http://" +
                     System.getProperty("cgi.server_name");
      if (!(System.getProperty("cgi.server_port").equals("80")))
          returnString += ":" + System.getProperty("cgi.server_port");
      returnString += System.getProperty("cgi.script_name");

      return returnString;
  }

  /**
   *
   * Determine the Full URL of this script.
   * (Includes the QUERY_STRING (if any) or PATH_INFO (if any).
   *
   * @return The Full URL of this script as a String.
   *
   */
  public static String MyFullURL()
  {
      String returnString;
      returnString = MyBaseURL();
      returnString += System.getProperty("cgi.path_info");
      String queryString = System.getProperty("cgi.query_string");
      if (queryString.length() > 0)
         returnString += "?" + queryString;
      return returnString;
  }

  /**
   *
   * Neatly format all of the CGI environment variables
   * and the associated values using HTML.
   *
   * @return A String containing an HTML representation of the CGI environment
   * variables and the associated values.
   *
   */
  public static String Environment()
  {
      String returnString;

      returnString = "<dl compact>\n";
      returnString += "<dt><b>CONTENT_TYPE</b> <dd>:<i>" +
                      System.getProperty("cgi.content_type") +
                      "</i>:<br>\n";
      returnString += "<dt><b>CONTENT_LENGTH</b> <dd>:<i>" +
                      System.getProperty("cgi.content_length") +
                      "</i>:<br>\n";
      returnString += "<dt><b>REQUEST_METHOD</b> <dd>:<i>" +
                      System.getProperty("cgi.request_method") +
                      "</i>:<br>\n";
      returnString += "<dt><b>QUERY_STRING</b> <dd>:<i>" +
                      System.getProperty("cgi.query_string") +
                      "</i>:<br>\n";
      returnString += "<dt><b>SERVER_NAME</b> <dd>:<i>" +
                      System.getProperty("cgi.server_name") +
                      "</i>:<br>\n";
      returnString += "<dt><b>SERVER_PORT</b> <dd>:<i>" +
                      System.getProperty("cgi.server_port") +
                      "</i>:<br>\n";
      returnString += "<dt><b>SCRIPT_NAME</b> <dd>:<i>" +
                      System.getProperty("cgi.script_name") +
                      "</i>:<br>\n";
      returnString += "<dt><b>PATH_INFO</b> <dd>:<i>" +
                      System.getProperty("cgi.path_info") +
                      "</i>:<br>\n";

      returnString += "</dl>\n";

      return returnString;
  }

  /**
   *
   * Neatly format all of the form data using HTML.
   *
   * @param form_data The Hashtable containing the form data which was
   * parsed using the ReadParse method.
   *
   * @return A String containing an HTML representation of all of the 
   * form variables and the associated values.
   *
   */
  public static String Variables(Hashtable form_data)
  {

      String returnString;

      returnString = "<dl compact>\n";

      for (Enumeration e = form_data.keys() ; e.hasMoreElements() ;)
      {
          String key = (String)e.nextElement();
          String value = (String)form_data.get(key);
          returnString += "<dt><b>" + key + "</b> <dd>:<i>" +
                          value +
                          "</i>:<br>\n";
      } 

      returnString += "</dl>\n";

      return returnString;

  }

  public static String Variables(String [] form_data)
  {

      String returnString;

      returnString = "<dl compact>\n";

      for (int i=0; i<form_data.length; i++)
      {
          String key = form_data[i].substring(1);
          String value = form_data[i+1];
          returnString += "<dt><b>" + key + "</b> <dd>:<i>" +
                          value +
                          "</i>:<br>\n";
          i += 2;
      } 

      returnString += "</dl>\n";

      return returnString;

  }

  /**
   *
   * The main routine is included here as a test CGI script to
   * demonstrate the use of all of the methods provided above.
   * You can use it to test your ability to execute a CGI script written
   * in Java.  See the sample UNIX script file included above to see
   * how you would invoke this routine.<p>
   *
   * Please note that this routine references the member functions directly
   * (since they are in the same class), but you would have to
   * reference the member functions using the class name prefix to
   * use them in your own CGI application:<p>
   * <pre>
   *     System.out.println(cgi_lib.HtmlTop());
   * </pre>
   *
   * @param args An array of Strings containing any command line
   * parameters supplied when this program in invoked.  Any
   * command line parameters supplied are ignored by this routine.
   *
   */
  public static void main( String args[] )
  {

      //
      // This main program is simply used to test the functions in the
      // cgi_lib class.  
      //
      // That said, you can use this main program as a test cgi script.  All
      // it does is echo back the form inputs and enviroment information to
      // the browser.  Use the testcgi UNIX script file to invoke it.  You'll
      // notice that the script you use to invoke any Java application that
      // uses the cgi_lib functions MUST pass all the CGI enviroment variables
      // into it using the -D parameter.  See testcgi for more details.
      //

      //
      // Print the required CGI header.
      //
      System.out.println(Header());

      //
      // Create the Top of the returned HTML
      // page (the parameter becomes the title).
      //
      System.out.println(HtmlTop("Hello World"));
      System.out.println("<hr>");

      //
      // Determine the request method used by the browser.
      //
      if (MethGet())
          System.out.println("REQUEST_METHOD=GET");
      if (MethPost())
          System.out.println("REQUEST_METHOD=POST");
      System.out.println("<hr>");

      //
      // Determine the Base URL of this script.
      //
      System.out.println("Base URL: " + MyBaseURL());
      System.out.println("<hr>");

      //
      // Determine the Full URL used to invoke this script.
      //
      System.out.println("Full URL: " + MyFullURL());
      System.out.println("<hr>");

      //
      //  Print all the CGI environment variables
      //  (usually only used while testing CGI scripts).
      //
      //
      System.out.println(Environment());
      System.out.println("<hr>");

      //
      //  Parse the form data into a Hashtable.
      //
      String[] form_data = ReadParse(System.in,null);

      //
      //  Print out each of the name/value pairs
      //  from sent from the browser.
      //
      System.out.println(Variables(form_data));
      System.out.println("<hr>");

      //
      //  Access a particular form value.
      //  (This assumes the form contains a "name" input field.)
      //
      String name = Util.getArgument(form_data,"-name");
      System.out.println("Name=" + name);
      System.out.println("<hr>");

      //
      // Create the Bottom of the returned HTML page - which closes it cleanly.
      //
      System.out.println(HtmlBot());

  }
}


