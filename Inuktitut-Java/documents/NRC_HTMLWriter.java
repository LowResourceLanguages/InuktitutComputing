//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		NRC_HTMLWriter.java
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
// Description: Version particulière de HTMLWriter pour éviter certaines
//              actions de la classe originale.
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: NRC_HTMLWriter.java,v 1.1 2009/06/19 19:38:51 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: NRC_HTMLWriter.java,v $
// Revision 1.1  2009/06/19 19:38:51  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.1  2006/07/18 21:11:12  farleyb
// *** empty log message ***
//
// Revision 1.3  2006/05/05 20:18:08  farleyb
// On n'utilise plus javaswing.javax.... On retourne à javax.swing....
//
// Revision 1.2  2005/11/28 15:20:56  farleyb
// *** empty log message ***
//
// Revision 1.1  2005/01/05 22:04:50  farleyb
// *** empty log message ***
//
// Revision 1.1  2004/12/07 20:49:49  farleyb
// *** empty log message ***
//
// Revision 1.3  2004/10/27 20:03:55  farleyb
// *** empty log message ***
//
// Revision 1.2  2004/04/27 13:07:47  farleyb
// Avant de tenter de corriger le problème du répertoire
// applications
//
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Première sauvegarde
//
// Revision 1.0  2003-06-25 13:19:54-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:33:25-05  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

package documents;

//import javaswing.javax.swing.text.html.*;
import javax.swing.text.html.*;
import java.io.*;
import javax.swing.text.*;
import java.util.Enumeration;

public class NRC_HTMLWriter extends HTMLWriter {

	private int maxLineLength = 70;
	private int lineLength = 0;
	private boolean inPre = false;
	private boolean wrap = false;
	private HTMLDocument doc;
	static private PrintWriter out = null;
	private Writer writer;
	
	static {
//        try {
//            out = new PrintWriter(new BufferedWriter(new FileWriter(
//                    "HTMLWriterOutput.txt")));
//        } catch (Exception e) {
//        }
	}

	public NRC_HTMLWriter(Writer w, HTMLDocument doc) {
		this(w, doc, 0, doc.getLength());
		this.doc = doc;
		writer = w;
	}

	public NRC_HTMLWriter(Writer w, HTMLDocument doc, int pos, int len) {
		super(w, doc, pos, len);
		this.doc = doc;
		writer = w;
	}

	// Write the contents of the document to the writer,
	// and as a side effect, if 'out' is defined, write the
	// contents to it too.  'out' is defined by decommenting
	// the code within the 'static' block above.
	public void write() throws IOException, BadLocationException {
        ElementIterator it = getElementIterator();
        Element next = null;

        setCanWrapLines(false);
        lineLength = 0;
//        write(doc.initialText);
        while ((next = it.next()) != null) {
            writeElement(next, it);
            writer.flush();
        }
        if (out != null) {
            out.flush();
            out.close();
        }
    }

	void writeElement(Element elem, ElementIterator it) throws IOException {
		AttributeSet as = elem.getAttributes();

		// Écrire des informations sur l'élément dans le fichier de sortie
		// HTMLWriterOutput.txt
		if (out != null) {
			out.println("-------------------------------");
			out.println("<" + elem.getName() + ">");
			for (Enumeration e = as.getAttributeNames();
				e.hasMoreElements();
				) {
				Object name = e.nextElement();
				out.println("  " + name + ": " + as.getAttribute(name));
			}
			if (elem.getName().equals("content")) {
				int beg = elem.getStartOffset();
				int end = elem.getEndOffset();
				try {
					String tx = doc.getText(beg, end - beg);
					out.println("  texte: /" + tx + "/");
				} catch (Exception e) {
				};
			}
		}
		
		// Écrire l'élément au Writer (voir constructeur)
		int i;
		AttributeSet attr = elem.getAttributes();
		Object nameTag =
			(attr != null)
				? attr.getAttribute(StyleConstants.NameAttribute)
				: null;
		Object endTag =
			(attr != null) ? attr.getAttribute(HTML.Attribute.ENDTAG) : null;
		// 	System.out.println(">>>"+nameTag+"<<<");
			
		if (!matchNameAttribute(attr,HTML.Tag.CONTENT))
		    write("\n");
		
		if (matchNameAttribute(attr, HTML.Tag.CONTENT)) {
			try {
				int pos = elem.getStartOffset();
				int length = elem.getEndOffset() - pos;
				String text = getDocument().getText(pos, length);
				// 		System.out.println("---\""+text+"\"");
				// remplacer le code 160 par la chaîne &nbsp;
				String tx = new String();
				for (int j=0; j<text.length(); j++)
				    if (text.charAt(j)=='\u00a0')
				        tx = tx + "&nbsp;";
				    else
				        tx = tx + text.charAt(j);
				write(tx);
				if (!inPre) {
					lineLength += length;
				}
			} catch (Exception e) {
			}
		} else if (matchNameAttribute(attr, HTML.Tag.COMMENT)) {
			write("<!--");
			write((String) attr.getAttribute(HTML.Attribute.COMMENT));
			write("-->");
//		} else if (matchNameAttribute(attr, NRC_HTMLTag.PHP)) {
//			write("<?PHP ");
//			write("");
//			write("-->");
		} else if (
			matchNameAttribute(attr, HTML.Tag.TITLE) && endTag == null) {
			write('<');
			write(nameTag.toString());
			write('>');
			lineLength = lineLength + 2 + nameTag.toString().length();
			if (wrap && lineLength > maxLineLength) {
				writeLineSeparator();
				lineLength = 0;
			}
			Document doc = elem.getDocument();
			String title = (String) doc.getProperty(Document.TitleProperty);
			if (title != null) {
				write(title);
				lineLength += title.length();
			}
		} else {
			if (matchNameAttribute(attr, HTML.Tag.PRE))
				inPre = true;
			// 	    if (!matchNameAttribute(attr,HTML.Tag.IMPLIED)) {
			boolean wroteSlash = false;
			write('<');
			if (endTag != null
				&& (endTag instanceof String)
				&& ((String) endTag).equals("true")) {
				write('/');
				wroteSlash = true;
			}
			write(nameTag.toString());
			lineLength =
				lineLength + (wroteSlash ? 2 : 1) + nameTag.toString().length();
			writeAttributes(attr);
			write('>');
			if (!inPre)
				lineLength++;
			//  	    }
			int nbChildren = elem.getElementCount();

			// Ajouter <base href...> après <head>
			if (matchNameAttribute(attr, HTML.Tag.HEAD)) {
				String base0 = "<base href=\"";
				String base =
					((HTMLDocument) getDocument()).getBase().toString();
				write(base0);
				write(base);
				write("\">");
				lineLength = lineLength + base0.length() + base.length() + 2;
				if (wrap && lineLength > maxLineLength) {
					writeLineSeparator();
					lineLength = 0;
				}
			}

			// Le dernier enfant CONTENT contenant seulement un NEWLINE,
			// si son parent est un P-IMPLIED à l'intérieur d'un PRE,
			// est conservé; sinon il est enlevé.

			for (i = 0; i < nbChildren; i++) {
				Element enfant = it.next();

				if (i == nbChildren - 1) {
					if (!matchNameAttribute(enfant.getAttributes(),
						HTML.Tag.CONTENT))
						writeElement(enfant, it);
					else {
						try {
							int pos = enfant.getStartOffset();
							int length = enfant.getEndOffset() - pos;
							String text = getDocument().getText(pos, length);
							if (length == 1 && text.charAt(0) == '\n') {
								if (matchNameAttribute(attr, HTML.Tag.IMPLIED)
									&& inPre)
									writeElement(enfant, it);
							} else
								writeElement(enfant, it);
						} catch (Exception e) {
						}
					}
				} else
					writeElement(enfant, it);
			}

			if (nbChildren
				!= 0 // 		&& !matchNameAttribute(attr,HTML.Tag.IMPLIED)
			) {
				write('<');
				write('/');
				write(nameTag.toString());
				write('>');
				if (matchNameAttribute(attr, HTML.Tag.PRE) && inPre)
					inPre = false;
				if (!inPre) {
					lineLength = lineLength + 3 + nameTag.toString().length();
					if (wrap && lineLength > maxLineLength) {
						writeLineSeparator();
						lineLength = 0;
					}
				}
			}
		}
	}

	protected void writeAttributes(AttributeSet attr) throws IOException {
		Enumeration names = attr.getAttributeNames();
		String satt;
		while (names.hasMoreElements()) {
			Object name = names.nextElement();
			if ((name instanceof HTML.Tag && name != HTML.Tag.STYLE)
				|| name instanceof StyleConstants
				|| name == HTML.Attribute.ENDTAG) {
				continue;
			}
			satt = " " + name + "=\"" + attr.getAttribute(name) + "\"";
			if (wrap
				&& !inPre
				&& (lineLength + satt.length()) > maxLineLength) {
				satt = name + "=\"" + attr.getAttribute(name) + "\"";
				writeLineSeparator();
				lineLength = 0;
			}
			write(satt);
			if (!inPre)
				lineLength = lineLength + satt.length();
		}
	}

}
