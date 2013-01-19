// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2007
//           (c) National Research Council of Canada, 2007
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File: NRC_HTMLDocument.java
//
// Type/File type: code Java / Java code
// 
// Auteur/Author: Benoit Farley
//
// Organisation/Organization: Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation: 9 avril 2002 / April 9, 2002
//
// Description: Description d'une extension de la classe HTMLDocument
//              où le lecteur est redéfini.
//
// -----------------------------------------------------------------------

package documents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;

//import javaswing.javax.swing.text.html.CSS;
//import javaswing.javax.swing.text.html.HTML;
//import javaswing.javax.swing.text.html.HTMLDocument;
//import javaswing.javax.swing.text.html.HTMLEditorKit;
//import javaswing.javax.swing.text.html.StyleSheet;

import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.CSS.Attribute;


import utilities.Inuktitut;
import utilities1.Util;

import script.Orthography;
import script.Roman;
import script.Syllabics;
import script.TransCoder;

import fonts.Font;
import html.BetweenTag;
import html.HTMLDocuElement;
import html.HtmlDocu;
import html.HtmlEntities;
import html.Tag;

public class NRC_HTMLDocument extends HTMLDocument implements NRC_Document {

	private static final long serialVersionUID = 1L;

	public String encoding = null;
	public Encoding encodingObject = null;
	public File copyOfFile = null;

	private String contentType = null;
	private String urlName;
	private String base = null;
	private SousHTMLEditorKit editorKit;
	private URL url = null;
	private Date date;
	private String httpHeaderContentType = null;
	private static File tmpDir  = new File(System.getenv("JAVA_INUKTITUT"), "tmp");
	private WebPageReader wpr = null;
	private Hashtable fonts = null;
	private boolean containsInuktitut = false;
	private long lengthOfInuktitutContent = 0;
	private long lengthOfTotalContent = 0;
	private String pageContent = null;
	private String inuktitutContent = null;
	
	static {
		if (!tmpDir.exists() || !tmpDir.isDirectory()) {
			try {
				tmpDir.createNewFile();
			} catch (IOException e) {
				System.out.println("unable to create "+tmpDir.getAbsolutePath());
			}
		}
	}

	/*
	 * This constructor should be called only when it is certain that the file
	 * is indeed an html document. If it is not an HTML document, an exception
	 * will be thrown.
	 */
	public NRC_HTMLDocument(String urlName) throws Exception {
		editorKit = new SousHTMLEditorKit();
		// Pour remplacer un élément du document HTML ou pour en insérer un,
		// il faut indiquer au document un parseur.
		HTMLEditorKit.Parser parser = editorKit.getParser();
		setParser(parser);
		this.urlName = urlName;
		wpr = new WebPageReader(urlName);
		httpHeaderContentType = wpr.contentType.toLowerCase();
		if (httpHeaderContentType != null)
			if (httpHeaderContentType.indexOf("text/html") < 0)
				throw new Exception("not html");
			else
				encoding = getDocEncoding(httpHeaderContentType);
		date = new Date(wpr.connection.getLastModified());
		url = wpr.url;
		contentType = wpr.contentType;
		putProperty(Document.StreamDescriptionProperty, url);
		putProperty("IgnoreCharsetDirective", Boolean.FALSE);
		// Copier le fichier dans la cache
		copyOfFile = copyInCache(url);
		String fileCopy = "file://localhost/" + copyOfFile.getAbsolutePath();
		// Vérifier s'il y a un BOM en début de fichier.
		BOM bom;
		BufferedReader br = null;
		/*
		 * Si on n'a pas de HTTP Header qui spécifie l'encodage, on cherche si
		 * le fichier commence avec un BOM.
		 */
		if (encoding == null) {
			if ((bom = BOM.checkForBOM(copyOfFile)).encoding != null) {
				encoding = bom.encoding;
				encodingObject = new Encoding(encoding, "bom");
			}
		} else {
			/*
			 * Ne pas interrompre le parsage du fichier lorsqu'un élément META
			 * est rencontré avec une directive charset=... si l'encodage a déjà
			 * été spécifié par un HTTP Header.
			 */
			putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			encodingObject = new Encoding(encoding, "http header");
		}
		// Ouvrir une connection avec le fichier en cache.
		wpr = new WebPageReader(fileCopy);
		br = wpr.getBufferedReader(encoding);
		try {
			// Parsage du document
			editorKit.read(br, this, 0);
		} catch (ChangedCharSetException e) {
			/*
			 * Le code suivant est ex�cut� lorsqu'une directive charset=... dans
			 * un �l�ment META a �t� d�tect�e. Seulement lorsque la propri�t�
			 * IgnoreCharsetDirective est � FALSE. On ne fait rien si une
			 * directive HTTP a d�j� sp�cifi� l'encodage.
			 */
			String encFromMeta = getDocEncoding(e.getCharSetSpec()
					.toLowerCase());
			if (encFromMeta == null)
				encoding = "ISO-8859-1";
			else
				encoding = encFromMeta.trim();
			encodingObject = new Encoding(encoding, "meta");
			br.close();
			wpr = new WebPageReader(fileCopy);
			br = wpr.getBufferedReader(encoding);
			putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			// Parsage du document
			editorKit.read(br, this, 0);
		} catch (IOException e) {
			throw new IOException(e.toString()+" from editorKit.read(br,this,0) in NRC_HTMLDocument"); 
		}
		br.close();
		if (encoding == null) {
			encoding = "ISO-8859-1";
			encodingObject = new Encoding(encoding, "default");
		}
	}

	String getDocEncoding(String connectionContentType) {
		Pattern p = Pattern.compile("charset=(.+)");
		Matcher mp = p.matcher(connectionContentType);
		if (mp.find()) {
			String charset = mp.group(1);
			String enc = getDocEncoding(charset);
			if (enc == null)
				return charset;
			else
				return enc;
		}
		else
			return null;
	}

	/*
	 * Copy the URL's byte contents into a cached file.
	 */
	private File copyInCache(URL url) throws IOException {
		/* Créer le fichier de copie. */
		File of = null;
		try {
			of = File.createTempFile("copyOfHTML", ".htm", tmpDir);
		}
		catch (IOException e) {
			if (tmpDir != null)
				throw new IOException(e.toString()+" from createTempFile(\"copyOfHTML\",\".htm\",tmpDir[="+tmpDir.getAbsolutePath()+"]) in copyInCache");
			else
				throw new IOException(e.toString()+" from createTempFile(\"copyOfHTML\",\".htm\",tmpDir[=null] in copyInCache");
		}
		InputStream is = null;
		try {
			is = url.openStream();
		}
		catch (IOException e1) {
			throw new IOException(e1.toString()+" from url[="+url.toExternalForm()+"].openStream()");
		}
		FileOutputStream os = new FileOutputStream(of);
		int b = 0;
		while (b  != -1) {
			try {
				b = is.read();
			}
			catch (IOException e2) {
				throw new IOException(e2.toString()+" from is.read() in copyInCache");
			}
			try {
				os.write(b);
			}
			catch (IOException e3) {
				throw new IOException(e3.toString()+" from os.write(b[="+b+"]) in copyInCache");
			}
			}
		os.close();
		is.close();
		return of;
	}

	class CacheFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			if (new File(dir, name).isDirectory()) {
				return false;
			}
			name = name.toLowerCase();
			Pattern p = Pattern.compile("copy\\[[0-9]+\\].htm");
			Matcher m = p.matcher(name);
			return m.matches();
		}
	}

	// Red�finition de la m�thode getReader et des m�thodes 'callback'
	// du parseur.
	public HTMLEditorKit.ParserCallback getReader(int pos) {
		Object desc = getProperty(Document.StreamDescriptionProperty);
		if (desc instanceof URL) {
			setBase((URL) desc);
		}
		return new HtmlReader(pos);
	}

	public HTMLEditorKit.ParserCallback getReader(int pos, int popDepth,
			int pushDepth, HTML.Tag insertTag) {
		Object desc = getProperty(Document.StreamDescriptionProperty);
		if (desc instanceof URL) {
			setBase((URL) desc);
		}
		return new HtmlReader(pos, popDepth, pushDepth, insertTag);
	}

	public HTMLEditorKit getEditorKit() {
		return editorKit;
	}

	public URL resolveUrlFromBase(String ref) {
		URL newURL = null;
		try {
			URL base = getBase();
			newURL = new URL(base, ref);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newURL;
	}

	public String getBaseName() {
		return base;
	}

	public Date getDate() {
		return date;
	}

	// for testing purposes only
	public void setDate(Date newDate) {
		date = newDate;
	}

	public String getTitle() {
		String title = (String) getProperty(Document.TitleProperty);
		if (title == null) {
			title = "";
		}
		return title;
	}

	public String getInuktitutContent() {
		if (inuktitutContent != null)
			return inuktitutContent;
		getPageContent();
		return inuktitutContent;
	}

	// make title and textContent instance variables
	public String getPageContent() {
		if (pageContent != null)
			return pageContent;
		ElementIterator iterator = new ElementIterator(this);
		String contenuPage = "";
		javax.swing.text.Element elem;
		String previousElementName = "";
		fonts = new Hashtable();
		while ((elem = iterator.next()) != null) {
			if (elem.getName().equals("content")) {
				int beg = elem.getStartOffset();
				int end = elem.getEndOffset();
				String text = null;
				try {
					text = this.getText(beg, end - beg);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				Element parent = elem.getParentElement();
				String parentName = parent.getName().toLowerCase();
				if (parentName.equals("script")
						|| parent.getName().toLowerCase().equals("style"))
					continue;
				else if (parentName.equals("p-implied")) {
					parent = parent.getParentElement();
					parentName = parent.getName().toLowerCase();
					if (parentName.equals("script")
							|| parent.getName().toLowerCase().equals("style"))
						continue;
				}
				// Record the names of the fonts
				String[] elementFonts = this.getFont(elem);
				if (elementFonts != null)
					for (int ifont = 0; ifont < elementFonts.length; ifont++) {
						String f = elementFonts[ifont].toLowerCase();
						// f is either a single name or a series of names
						// separated by a comma
						String fs[] = f.split(",");
						for (int ifs = 0; ifs < fs.length; ifs++) {
							String fsi = fs[ifs].trim();
							if (!fonts.containsKey(fs[ifs])) {
								fonts.put(fsi, new Integer(1));
							} else {
								fonts.put(fsi, new Integer(((Integer) fonts
										.get(fsi)).intValue() + 1));
							}
						}
					}
				String font = null;
				// System.out.print("(" + font + ")");
				if (text.replaceAll("\\s", "").equals("")) {
					;
				} else if (Syllabics.containsInuktitut(text)) {
					containsInuktitut = true;
					lengthOfInuktitutContent += text.length();
					lengthOfTotalContent += text.length();
				} else if ((font = Font.containsLegacyFont(elementFonts)) != null) {
					// System.out.print("***");
					containsInuktitut = true;
					text = TransCoder.legacyToUnicode(text, font);
					lengthOfInuktitutContent += text.length();
					lengthOfTotalContent += text.length();
				} else {
					lengthOfTotalContent += text.length();
				}
				// System.out.println(" /" + text + "/");
				if (Arrays.binarySearch(HTMLDocuElement.tagsThatDoNotAddSpace,
						previousElementName) < 0) {
					contenuPage = contenuPage.concat(" ");
				}
				contenuPage = contenuPage.concat(text);
			} else {
				previousElementName = elem.getName().toLowerCase();
			}
		}
		// System.out.println("pageContent in getPageContent():" + pageContent);
		contenuPage = contenuPage.replaceAll("\\s+", " ");
		pageContent = new String(contenuPage);
		return contenuPage;
	}

	public URL getURL() {
		return url;
	}

	public void write() throws IOException, BadLocationException {
		int doclength = getLength();
		Writer out = new OutputStreamWriter(System.out);
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(out, this, 0, doclength);
		myWriter2.write();
		out.flush();
	}

	public void write(PrintStream out) throws IOException, BadLocationException {
		int doclength = getLength();
		Writer out1 = new OutputStreamWriter(out);
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(out1, this, 0, doclength);
		myWriter2.write();
		out1.flush();
	}

	public void write(String nomFichier) throws IOException,
			BadLocationException {
		int doclength = getLength();
		Writer out = new BufferedWriter(new FileWriter(nomFichier));
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(out, this, 0, doclength);
		myWriter2.write();
		out.close();
	}

	public void write(String nomFichier, String enc) throws IOException,
			BadLocationException {
		int doclength = getLength();
		Writer out = new OutputStreamWriter(new FileOutputStream(nomFichier),
				enc);
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(out, this, 0, doclength);
		myWriter2.write();
		out.flush();
	}

	public void writeUTF8() throws IOException, BadLocationException {
		int doclength = getLength();
		OutputStreamWriter out = new OutputStreamWriter(System.out, "UTF-8");
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(out, this, 0, doclength);
		myWriter2.write();
		out.flush();
	}

	public void writeUTF8(PrintStream out) throws IOException,
			BadLocationException {
		int doclength = getLength();
		Writer out1 = new OutputStreamWriter(out, "UTF8");
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(out1, this, 0, doclength);
		myWriter2.write();
		out1.flush();
	}

	public void writeUTF8(String nomFichier) throws IOException,
			BadLocationException {
		int doclength = getLength();
		Writer outUTF8 = new OutputStreamWriter(
				new FileOutputStream(nomFichier), "UTF-8");
		NRC_HTMLWriter myWriter2 = new NRC_HTMLWriter(outUTF8, this, 0,
				doclength);
		myWriter2.write();
		outUTF8.flush();
	}

	/*
	 * Retourne l'élément qui contient le mot trouvé, ou null si le mot n'a pas
	 * été trouvé.
	 */
	public Object findWord(String word) {
		ElementIterator it = new ElementIterator(this);
		boolean trouve = false;
		javax.swing.text.Element element = null;
		javax.swing.text.Element elem = null;
		String texte;

		// Ensuite, chercher le mot s�lectionn� parmi les �l�ments
		// de texte.
		while (!trouve && (elem = it.next()) != null) {
			if (elem.getName().equals("content")) {
				int deb = elem.getStartOffset();
				int fin = elem.getEndOffset();
				try {
					texte = getText(deb, fin - deb);
				} catch (BadLocationException e) {
					texte = "";
				}
				if (texte.indexOf(word) != -1) {
					trouve = true;
					element = elem;
				}
			}
		}
		return element;
	}

	public Object trouverProprieteCSSHeritage(Attribute attr, Element element) {
		Object attrValue = trouverProprieteCSS(attr, element);
		if (attrValue == null) {
			// Si ce n'est pas le cas, vérifier le parent.
			Element parent = element.getParentElement();
			if (parent != null)
				attrValue = trouverProprieteCSSHeritage(attr, parent);
		}
		return attrValue;
	}

	public Object trouverProprieteCSS(Attribute attr, Element element) {
		AttributeSet as;
		Object attrValue = null;
		Object style;
		StyleSheet styles;
		Style rule;
		NRC_HTMLTag tag = null;

		if (element == null)
			return null;

		String elementName = element.getName();

		if (elementName.equals("p-implied")) {
			Element parent = element.getParentElement();
			return trouverProprieteCSS(attr,parent);
		}
		as = element.getAttributes();

		// Vérifier si un attribut STYLE est spécifié avec la propriété 'attr'
		if (as.isDefined(HTML.Attribute.STYLE)) {
			style = as.getAttribute(HTML.Attribute.STYLE);
			StyleSheet ss = new StyleSheet();
			AttributeSet ssrule = ss.getDeclaration(style.toString());
			if (ssrule.isDefined(attr))
				attrValue = ssrule.getAttribute(attr).toString();
		}

		if (attrValue != null) {
			return attrValue;
		}

		// Si ce n'est pas le cas, vérifier si un style a été défini pour
		// cet élément dans une feuille de style (soit par l'élément STYLE
		// au début du fichier, soit par une feuille de style externe).

		styles = getStyleSheet();
		tag = new NRC_HTMLTag(elementName);
		rule = styles.getRule(tag, element);

		if (rule != null) {
			attrValue = rule.getAttribute(attr);
		}
		if (attrValue != null) {
			return attrValue;
		}

		// Si ce n'est pas le cas, vérifier si un style a été défini pour cet
		// élément avec un sélecteur contenant un ':'
		Vector attrValuesVector = new Vector();
		Pattern p = Pattern.compile("^[^:]+:");
		Enumeration rules = styles.getStyleNames();
		while (rules.hasMoreElements()) {
			String name = (String) rules.nextElement();
			Matcher mp = p.matcher(name);
			rule = styles.getStyle(name);
			if (mp.lookingAt()) {
				attrValue = rule.getAttribute(attr);
				if (attrValue != null
						&& !attrValuesVector.contains(attrValue.toString()))
					attrValuesVector.add(attrValue.toString());
			}
		}
		attrValue = null;
		if (attrValuesVector.size() != 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < attrValuesVector.size(); i++)
				sb.append(attrValuesVector.elementAt(i).toString()).append(" ");
			attrValue = sb.toString().trim();
		}
		return attrValue;
	}

	private String decoupe;

	public String[] decouperTexteEnLignes() {
		Element root = getRootElements()[0];
		decoupe = new String();
		decouperTexteEnLignes_String(root);
		String[] lignes = decoupe.split("<<.+>>");
		return lignes;
	}

	private void decouperTexteEnLignes_String(Element element) {
		if (!element.isLeaf()) {
			Object display = trouverProprieteCSSHeritage(CSS.Attribute.DISPLAY,
					element);
			if (display != null)
				display = display.toString().toLowerCase();
			else {
				HTML.Tag tag = HTML.getTag(element.getName());
				if (tag == null)
					display = "";
				else if (tag.isBlock())
					display = "block";
				else
					display = "inline";
			}
			if (display.equals("block")) {
				decoupe += "<<" + element.getName() + ">>";
			}

			int elementCount = element.getElementCount();
			for (int i = 0; i < elementCount; i++) {
				Element elem = element.getElement(i);
				decouperTexteEnLignes_String(elem);
			}

			if (display.equals("block")) {
				decoupe += "<<" + element.getName() + ">>";
			}
		} else {
			int beg = element.getStartOffset();
			int end = element.getEndOffset();
			String text = null;
			try {
				text = getText(beg, end - beg);
				text = text.replaceAll("\\s+", " ");
				decoupe += text;
			} catch (BadLocationException e) {
			}
		}
	}

	/*
	 * Returns the font-family names for the element 'e'. Because it is possible
	 * with css styles to have pseudo-elements for a same element, they might
	 * have different font-families.
	 */
	public String[] getFont(Element e) {
		return trouverFontFamily(e);
	}

	// Recherche de la police utilis�e pour un �l�ment de texte du document.
	// (�ventuellement, cette fonction devrait �tre amalgam�e avec la
	// suivante).
	public String[] trouverFontFamily(Element element) {
		AttributeSet as;
		String family = null;
		as = element.getAttributes();

		// Vérifier si l'élément de texte contient directement un
		// attribut "font-family". NOTE: Normalement, il ne devrait pas
		// le contenir car notre lecteur HTML a été modifié par rapport
		// au HTMLDocument.HTMLReader original qui, lui, convertit certains
		// éléments HTML affectant le texte en attributs qu'il ajoute à
		// l'élément de texte (par exemple, <FONT face=verdana> devient
		// un attribut font-family=verdana ajouté au texte qui suit.)
		// if (as.isDefined(CSS.Attribute.FONT_FAMILY))

		// Essayer l'attribut css font-family
		Object familyObject = as.getAttribute(CSS.Attribute.FONT_FAMILY);
		if (familyObject == null)
			family = null;
		else
			family = familyObject.toString();

		// Sinon, essay l'attribut css font
		// if (family == null) {
		// String font = (String)as.getAttribute(CSS.Attribute.FONT);
		// family = parseFontForFamily(font);
		// }
		if (family == null) {
			Element parent = element.getParentElement();
			// fontFamily =
			// transInuk.trouverFontFamilyHerit(parent,doc);
			if (parent != null) {
				String[] families = trouverFontFamilyHerit(parent);
				return families;
			} else
				return null;
		} else {
			return (String[]) new String[] { family };
		}
	}

	// String parseFontForFamily(String font) {
	// // x y ... f
	// // x y ... f1 , f2 , ...
	// }

	// Trouver par h�ritage la police utilis�e pour un �l�ment donn�,
	// en commen�ant par l'�l�ment sp�cifi� en argument d'appel qui est
	// le parent imm�diat de l'�l�ment de texte dont on cherche la police.
	//
	// On commence par v�rifier un attribut FONT-FAMILY directement sp�cifi�.
	// On poursuit en v�rifiant si un attribut STYLE avec la propri�t�
	// FONT-FAMILY est sp�cifi� directement.
	// On v�rifie si un style a �t� d�fini qui s'applique � l'�l�ment dans le
	// contexte courant et qui sp�cifie la propri�t� FONT-FAMILY
	// Si rien n'a �t� trouv�, on cherche pour l'�l�ment parent
	//
	private String[] trouverFontFamilyHerit(Element element) {
		AttributeSet as;
		Object family = null;
		Element parent;

		if (element == null)
			return null;

		String elementName = element.getName();
		if (elementName.equals("p-implied")) {
			parent = element.getParentElement();
			return trouverFontFamilyHerit(parent);
		}
			
		as = element.getAttributes();

		// ATTENTION: getAttribute() va retrouver l'attribut dans le
		// premier �l�ment de m�me type dans la parent�. ON NE VEUT PAS �A.
		// Il faut v�rifier si l'attribut est sp�cifi� dans l'�l�ment
		// avec isDefined().

		// V�rifier si l'�l�ment contenant le texte contient directement un
		// attribut "font-family". Attention: pour l'�l�ment FONT, il faut
		// v�rifier l'attribut "face".
		if (elementName.equalsIgnoreCase("font")) {
			if (as.isDefined(HTML.Attribute.FACE))
				family = as.getAttribute(HTML.Attribute.FACE);
		} else {
			family = trouverProprieteCSS(CSS.Attribute.FONT_FAMILY, element);
			if (family != null)
				family = family.toString();
		}

		if (family != null) {
			return new String[] { (String) family };
		} else {
			// vérifier le parent.
			String[] families = null;
			parent = element.getParentElement();
			if (parent != null) {
				families = trouverFontFamilyHerit(parent);
			}
			return families;
		}
	}

	public String[] getWords() {
		Vector words = new Vector();
		ElementIterator iterator = new ElementIterator(this);
		javax.swing.text.Element elem;
		Pattern p = Pattern.compile("([a-z&]+)");
		while ((elem = iterator.next()) != null) {
			if (elem.getName().equals("content")) {
				NRC_HTMLDocument.TexteHTML texteHTML = texteEnInuktitut(elem);
				if (texteHTML != null) {
					texteHTML.transliterer();
					Vector morceaux = texteHTML.getMorphParts();
					String s = "";
					for (int i = 0; i < morceaux.size(); i++)
						s = s
								+ ((Inuktitut.MorceauTexte) morceaux.get(i))
										.getTexte();
					Matcher m = p.matcher(s);
					while (m.find()) {
						String word = m.group();
						if (!words.contains(word))
							words.add(word);
					}
				}
			}
		}
		String[] wordsArray = (String[]) words.toArray(new String[] {});
		Arrays.sort(wordsArray);
		return wordsArray;
	}

	public String[] getAllFontFamiliesInStyleSheets() {
		Vector families = new Vector();
		StyleSheet styles = getStyleSheet();
		Enumeration styleNames = styles.getStyleNames();
		while (styleNames.hasMoreElements()) {
			String name = (String) styleNames.nextElement();
			Style style = styles.getStyle(name);
			Enumeration attrNames = style.getAttributeNames();
			while (attrNames.hasMoreElements()) {
				Object n = attrNames.nextElement();
				if (n.toString().equals("resolver")) {
					Style nst = (Style) style.getAttribute(n);
					Enumeration nAttrNames = nst.getAttributeNames();
					while (nAttrNames.hasMoreElements()) {
						Object nAttrName = nAttrNames.nextElement();
						if (nAttrName.toString().toLowerCase().equals(
								"font-family")) {
							Object value = nst.getAttribute(nAttrName);
							String values[] = value.toString().toLowerCase()
									.split(",");
							for (int i = 0; i < values.length; i++) {
								String val = values[i].trim();
								if (!families.contains(val))
									families.add(val);
							}
						}
					}
				}
			}
		}
		String[] familiesArray = (String[]) families.toArray(new String[] {});
		Arrays.sort(familiesArray);
		return familiesArray;
	}

	public String getContentType() {
		return contentType;
	}

	public String[] getEncodingAndManner() {
		return new String[] { encodingObject.value, encodingObject.source };
	}

	public String getUrlName() {
		return urlName;
	}

	/*
	 * ----------------------------- Highlighting -----------------------------
	 */

	private static HtmlDocu docu;
	private static String endOfLine = "\n";
	private static File wordFile;
	private static boolean test = false;
	private static String testWords[] = null;
	private static Integer testFreqs[] = null;
	private static Vector elements;
	private static Hashtable transHash;
	private static Pattern pfw = Pattern.compile("^\\s*(\\S{0,20})");
	private static Vector fontsForHighlighting;

	/*
	 * Arguments: -url url: url de la page dans laquelle les mots sont � mettre
	 * en �vidence. -f f: nom du fichier sur le serveur dans lequel se trouvent
	 * les mots � mettre en �vidence; ce fichier doit se trouver dans le
	 * r�pertoire d�fini par la propri�t� de syst�me "user.dir". -d d: nom du
	 * r�pertoire o� placer et lire les fichiers (copie et mots) -s latsyl:
	 * "latin" ou "syllabics"
	 * 
	 * Retour: Un tableau de 2 objets: 0 - une cha�ne String contenant
	 * l'encodage (charset) de cet url 1 - un objet File d�signant le
	 * fichier-copie avec le surlignement
	 */

	public Object[] highlight(String[] args) throws Exception {
		transHash = new Hashtable();
		String fileCopy = null;
		fontsForHighlighting = new Vector();
		traiterDocPourHighlight();
		/*
		 * transHash est une liste des polices utilis�es pour chaque �l�ment de
		 * texte.
		 */
		fileCopy = "file://localhost/" + copyOfFile.getAbsolutePath();
		docu = new HtmlDocu(new URL(fileCopy));
		docu.insertBase(getBase());
		traiterDocPourHighlight2(docu, encoding);
		encoding = "utf-8";
		Object[] hl = highlightWords(args);
		// doc.copyOfFile.delete();
		return hl;
	}

	/*
	 * Cette premi�re passe lit l'URL comme un document HTML et dresse une table
	 * de hachage pour chaque �l�ment de texte, o� la cl� est les 20 premi�res
	 * lettres de la cha�ne de texte, sans les espaces au d�but (ou moins de 20
	 * si la cha�ne est plus petite), et la valeur, la police de caract�res qui
	 * lui est associ�e.
	 */
	private void traiterDocPourHighlight() {
		ElementIterator iterator = new ElementIterator(this);
		javax.swing.text.Element elem;
		String text;
		// ----------------------------------------------
		// It�ration sur chaque �l�ment du document HTML
		// ----------------------------------------------
		while ((elem = iterator.next()) != null) {
			// -----------------
			// �l�ment de TEXTE
			// -----------------
			if (elem.getName().equals("content")) {
				int beg = elem.getStartOffset();
				int end = elem.getEndOffset();
				try {
					text = getText(beg, end - beg);
				} catch (BadLocationException e) {
					continue;
				}
				/*
				 * ---------------------------------------------------------
				 * S'il s'agit du contenu d'un script, ne pas le consid�rer.
				 */
				Element parent = elem.getParentElement();
				String parentName = parent.getName().toLowerCase();
				if (parentName.equals("script")
						|| parent.getName().toLowerCase().equals("style"))
					continue;
				else if (parentName.equals("p-implied")) {
					parent = parent.getParentElement();
					parentName = parent.getName().toLowerCase();
					if (parentName.equals("script")
							|| parent.getName().toLowerCase().equals("style"))
						continue;
				}
				/* --------------------------------------------------------- */

				Matcher mfw = pfw.matcher(text);
				/*
				 * Trouver la police sp�cifi�e pour cet �l�ment de texte. Si
				 * c'est une police inuktitut archa�que telle que Nunacom ou
				 * Prosyl, enregistrer les premiers 20 caract�res de cet �l�ment
				 * avec le nom de la police. Sinon, enregistrer ces m�mes
				 * caract�res avec "".
				 */
				String[] fontFamilies = getFont(elem);
				String fontLegacy = Font.containsLegacyFont(fontFamilies);
				if (mfw.find()) {
					String key = mfw.group(1).replaceAll("\\s+", " ");
					if (fontLegacy != null) {
						transHash.put(key, fontLegacy);
						if (!fontsForHighlighting.contains(fontLegacy))
							fontsForHighlighting.add(fontLegacy);
					} else if (fontFamilies != null) {
						// String fontarray = Util.array2string(fontFamilies);
						// Pattern pfarray = Pattern.compile("\\[array:
						// (.*)\\]$");
						// Matcher mpfarray = pfarray.matcher(fontarray);
						// String font = fontarray;
						// if (mpfarray.find())
						// font = mpfarray.group(1);
						// transHash.put(key,font);
						transHash.put(key, "");
					} else {
						transHash.put(key, "null");
					}
				}
			}
		}
	} // fin de traiterdoc()

	/*
	 * Cette deuxi�me passe lit l'URL comme une liste d'objets <...> et textuels
	 * et ins�re un �l�ment "\u0002 \u0003" (espace entre marqueurs) s'il s'agit
	 * d'un tag comme <p> par exemple.
	 */
	static void traiterDocPourHighlight2(HtmlDocu doc2, String enc) {
		for (int i = 0; i < doc2.elements.size(); i++) {
			HTMLDocuElement element = (HTMLDocuElement) doc2.elements
					.elementAt(i);
			if (element.getType() == HTMLDocuElement.TAG) {
				String tag = ((Tag) element).tag;
				if (tag.charAt(0) != '/'
						&& Arrays.binarySearch(
								HTMLDocuElement.tagsThatDoNotAddSpace, tag) < 0) {
					doc2.elements.add(i + 1, new BetweenTag("\u0002 \u0003"));
					i++;
				}
			} else if (element.getType() == HTMLDocuElement.BETWEENTAG) {
				String txt = null;
				try {
					txt = new String(element.getBytes(), enc);
					txt = HtmlEntities.entityToChar(txt);
					element.setText(txt, "utf-8");
					Matcher mpfw = pfw.matcher(txt);
					if (mpfw.find()) {
						String key = mpfw.group(1).replaceAll("\\s+", " ");
						String font = (String) transHash.get(key);
						((BetweenTag) element).font = font;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Object[] highlightWords(String[] args) throws Exception {
		String httpHeader;
		// Parser le fichier HTML.
		String urlName = Util.getArgument(args, "url");
		URL url = new URL(urlName);
		String wordFileName = Util.getArgument(args, "f");
		String directoryName = Util.getArgument(args, "d");
		String latsyl = Util.getArgument(args, "s");
		if (latsyl == null)
			latsyl = Util.getArgument(args, "outputType"); // pour test
		if (latsyl.startsWith("syl"))
			latsyl = "syllabics"; // parce que Marta envoie 'syllabic'
		String wordsSeq = Util.getArgument(args, "m");
		String sessionID = wordFileName.substring(
				wordFileName.indexOf('_') + 1, wordFileName.lastIndexOf('_'));
		if (directoryName != null)
			tmpDir = new File(directoryName);
		// Lire les mots � rechercher dans le fichier HTML -> vecteur words.
		Vector words = null;
		if (wordFileName != null && !wordFileName.equals("null")) {
			wordFile = new File(tmpDir, wordFileName);
			words = getWordsToHighlight(wordFile, latsyl);
		} else if (wordsSeq != null && !wordsSeq.equals("null")) {
			words = getWordsToHighlight(wordsSeq, latsyl);
		}
		// Chercher dans les �l�ments BetweenTag les mots � rechercher ->
		// vecteur wordsToCheckInFile
		Hashtable wordsToCheckInFile = lookForWordsInFile(words, latsyl);
		httpHeader = "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset="
				+ encoding + "\">";
		File of = processFile(wordsToCheckInFile, url, latsyl, httpHeader,
				sessionID);
		return new Object[] { encoding, of };
	}

	File processFile(Hashtable wordsToCheckInFile, URL url, String latsyl,
			String httpHeader, String sessionID) throws IOException {
		elements = docu.elements;
		boolean scriptWritten = false;
		File of = File.createTempFile("ISEcopyForHighlight_" + sessionID + "_",
				".htm", tmpDir);
		OutputStream os = new FileOutputStream(of);
		// Insert <base ...>, and <style ...> for class .hl
		for (int i = 0; i < elements.size(); i++) {
			HTMLDocuElement elm = (HTMLDocuElement) elements.elementAt(i);
			if (elm.getType() == HTMLDocuElement.TAG) {
				// TAG: <...>
				String tag = ((Tag) elm).tag;
				if (tag.equals("html")) {
				} else if (tag.equals("head")) {
					Tag base = new Tag(makeBaseTag(url), null);
					Tag style = new Tag(makeHLClassStyleTag(), null);
					elements.add(i + 1, base);
					elements.add(i + 2, style);
					break;
				} else if (Character.isLetter(tag.charAt(0))) {
					// HEAD or BODY ELEMENT
					Tag base = new Tag(makeBaseTag(url), null);
					Tag style = new Tag(makeHLClassStyleTag(), null);
					elements.add(i, base);
					elements.add(i + 1, style);
					break;
				} else if (test && tag.equals("/body")) {
					Tag script = new Tag(makeScriptInBody(), null);
					elements.add(i, script);
					scriptWritten = true;
				} else if (test && tag.equals("/html")) {
					if (!scriptWritten) {
						Tag script = new Tag(makeScriptInBody(), null);
						elements.add(i, script);
						scriptWritten = true;
					}
				}
			}
		}
		if (test && !scriptWritten) {
			Tag script = new Tag(makeScriptInBody(), null);
			elements.add(script);
		}
		// HighlightHTML the words
		highlightWords(wordsToCheckInFile, latsyl);
		copyInFile(os);
		os.flush();
		os.close();
		return of;
	}

	static String makeBaseTag(URL url) {
		return "<base href=\"" + url.toExternalForm() + "\">";
	}

	static String makeHLClassStyleTag() {
		return "<style type=\"text/css\">" + endOfLine
				+ ".hl {background-color:#22C668; color:#FFFFFF;}" + endOfLine
				+ "</style>";
	}

	static String makeScriptInBody() throws IOException {
		String str = "<script type=\"text/javascript\">" + endOfLine + "<!-- "
				+ endOfLine;
		str += "document.write(\"<p style='font-family:Pigiarniq'>Les mots suivants devraient &ecirc;tre surlign&eacute;s en vert dans cette page:<br>\");"
				+ endOfLine;
		for (int i = 0; i < testWords.length; i++) {
			str += "document.write(\"<b>" + wordToUnicode(testWords[i]) + " - "
					+ testFreqs[i].toString() + " fois</b><br>\");" + endOfLine;
		}
		str += "document.write(\"</p>\");" + endOfLine;
		str += "-->" + endOfLine + "</script>" + endOfLine;
		return str;
	}

	/*
	 * Chaque mot se trouve sur une ligne s�par�e.
	 */
	public static Vector getWordsToHighlight(File wordFile, String latsyl)
			throws IOException {
		BufferedReader rdr = new BufferedReader(new InputStreamReader(
				new FileInputStream(wordFile), "UTF-8"));
		String word = null;
		String line = null;
		String bomUTF8 = new String(new byte[] { (byte) 0xEF, (byte) 0xBB,
				(byte) 0xBF }, "UTF-8");
		Pattern p = Pattern.compile("^(.+?)(\\x20//--\\x20.*)?$");
		Vector words = new Vector();
		while ((line = rdr.readLine()) != null) {
			Matcher m = p.matcher(line);
			if (m.matches()) {
				word = m.group(1);
				if (word==null) continue;
				if (word.startsWith(bomUTF8))
					word = word.substring(1);
				if (latsyl.equals("latin")) {
					words.add(word.toLowerCase());
					words.add(word.toUpperCase());
					words.add(Util.premiereMaj(word));
				} else {
					words.add(word);
				}
			}
		}
		rdr.close();
		return words;
	}

	public static Vector getWordsToHighlight(String wordSeq, String latsyl) {
		Vector words = new Vector();
		Pattern p = Pattern.compile("\\u0002(.+?)\\u0003");
		Matcher m = p.matcher(wordSeq);
		int pos = 0;
		while (m.find(pos)) {
			String word = m.group(1);
			if (word==null) continue;
			if (latsyl.equals("latin")) {
				words.add(word.toLowerCase());
				words.add(word.toUpperCase());
				words.add(Util.premiereMaj(word));
			} else
				words.add(word);
			pos = m.end();
		}
		return words;
	}

	/*
	 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN"
	 * "http://www.w3.org/TR/REC-html40/strict.dtd"> <HTML> optional <HEAD>
	 * optional <TITLE> ... </TITLE> required, but often missing <BASE> optional
	 * <SCRIPT> ... </SCRIPT> optional <STYLE> ... </STYLE> optional <META>
	 * optional <LINK> optional <OBJECT> ... </OBJECT> optional </HEAD> optional
	 * <BODY> optional ... </BODY> optional </HTML> optional
	 */

	/*
	 * Write the words returned by Lucene into a temporary file in the directory
	 * specified by 'directoryName'. If 'useAsIs' is true, the file has the name
	 * 'filename'. If false, it has a name that starts with 'filename' and is
	 * appended with a number generated by createTempFile.
	 */
	static public String writeLuceneWordsInTempFile(String ws[],
			String directoryName, String filename, boolean useAsIs)
			throws IOException {
		File wordFile = null;
		if (useAsIs)
			wordFile = new File(directoryName, filename);
		else
			wordFile = File.createTempFile(filename, ".txt", new File(
					directoryName));
		wlucene(ws, wordFile);
		// Return the 'name' part of the temp io file
		return wordFile.getName();
	}

	static void wlucene(String ws[], File wordFile)
			throws UnsupportedEncodingException, FileNotFoundException {
		// Create an output stream in UTF-8
		PrintWriter wr = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(wordFile), "UTF-8"));
		for (int i = 0; i < ws.length; i++) {
			if (ws[i].charAt(0) == '"'
					&& ws[i].charAt(ws[i].length() - 1) == '"')
				wr.println(ws[i].substring(1, ws[i].length() - 1));
			else
				wr.println(ws[i]);
		}
		wr.close();
	}

	public static String writeLuceneWordsInTempFileWithOSDependantLineDelimiter(
			String words[], String directoryName, String filename, String os)
			throws IOException {
		String lineDelimiter = "";
		if (os.equals("win")) {
			lineDelimiter = "\r\n";
		} else if (os.equals("mac")) {
			lineDelimiter = "\r";
		} else if (os.equals("x11")) {
			lineDelimiter = "\n";
		}
		File wordFile = File.createTempFile(filename, ".txt", new File(
				directoryName));
		PrintWriter wr = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(wordFile), "UTF-8"));
		wr.print('\uFEFF');
		for (int i = 0; i < words.length; i++) {
			if (words[i].charAt(0) == '"'
					&& words[i].charAt(words[i].length() - 1) == '"') {
				wr.print(words[i].substring(1, words[i].length() - 1));
			} else {
				wr.print(words[i]);
			}
			wr.print(lineDelimiter);
		}
		wr.close();
		return wordFile.getName();
	}

	static public String writeLuceneWordsInTempFileForTest(String ws,
			String directoryName, String filename, boolean useAsIs)
			throws IOException {
		File wordFile = null;
		if (useAsIs)
			wordFile = new File(directoryName, filename);
		else
			wordFile = File.createTempFile(filename, ".txt", new File(
					directoryName));
		int pos = 0;
		Vector words = new Vector();
		Pattern p = Pattern.compile("\\u0002(.+?)\\u0003");
		Matcher m = p.matcher(ws);
		while (m.find(pos)) {
			words.add(m.group(1));
			pos = m.end();
		}
		String wordsArray[] = (String[]) words.toArray(new String[] {});
		wlucene(wordsArray, wordFile);
		// Return the 'name' part of the temp io file
		return wordFile.getName();
	}

	protected static String wordToUnicode(String word) {
		String w = "";
		for (int j = 0; j < word.length(); j++) {
			String n = Integer.toHexString((int) word.charAt(j));
			String ns = String.valueOf(n);
			int nb0 = 4 - ns.length();
			String zeros = "";
			for (int k = 0; k < nb0; k++)
				zeros += "0";
			w = w + "\\u" + zeros + ns;
		}
		return w;
	}

	/*
	 * First pass: all the text elements are appended to one another to form a
	 * long text string in which the words are searched. Since the font of each
	 * text element is not determined, each word (words are in Unicode) is
	 * transcoded to several commonly used legacy fonts. Those transcoded words,
	 * in addition to the Unicode words, are the words that are searched in the
	 * text.
	 */
	static Hashtable lookForWordsInFile(Vector words, String latsyl) {
		Hashtable wordsFontsToHighlight = new Hashtable();
		String[] fonts;
		/*
		 * Transcode each (Unicode) word to the same word in a legacy font and
		 * prepare it as a regular expression.
		 */
		// fonts[] = new String[]{"nunacom","prosyl","aipainunavik"};
		// fonts = Police.polices;
		fonts = (String[]) fontsForHighlighting.toArray(new String[] {});
		Hashtable wordsFontsToCheck = new Hashtable();
		wordsFontsToCheck.put("unicode_ra", words);

		if (latsyl.equals("syllabics")) {
			for (int i = 0; i < fonts.length; i++) {
				Vector wfs = new Vector();
				String font = fonts[i];
				for (int j = 0; j < words.size(); j++) {
					String word = (String) words.elementAt(j);
					String w = TransCoder.unicodeToLegacy(word, font);
					String wp = prepareForRegexp(w);
					String wd = withDots(wp);
					if (!wfs.contains(wd)) {
						wfs.add(wd);
					}
				}
				if (wfs.size() != 0)
					wordsFontsToCheck.put(font, wfs);
			}
		}
		/*
		 * Form a long text string by appending all so-called BETWEENTAG
		 * elements to one another. Words to be checked will be first searched
		 * in that long string. The vector prepared in traiterdoc2 is used here.
		 */
		String textInFile = "";
		for (int i = 0; i < docu.elements.size(); i++) {
			HTMLDocuElement element = (HTMLDocuElement) docu.elements
					.elementAt(i);
			if (element.getType() == HTMLDocuElement.BETWEENTAG)
				textInFile += ((BetweenTag) docu.elements.elementAt(i)).text;
		}
		/*
		 * Look for each word in the long text string.
		 */
		for (Enumeration e = wordsFontsToCheck.keys(); e.hasMoreElements();) {
			Object font = e.nextElement();
			Vector wordPatterns = (Vector) wordsFontsToCheck.get(font);
			String ffont = (String) font;
			if (((String) font).equals("unicode_ra"))
				ffont = "";
			Vector wordsToHighlight = new Vector();
			for (int i = 0; i < wordPatterns.size(); i++) {
				String w = (String) wordPatterns.elementAt(i);
				String wp = w.replaceAll("\\s+", "\\\\s+");
				Pattern p = Pattern.compile(wp);
				Matcher m = p.matcher(textInFile);
				int pos = 0;
				while (pos < textInFile.length() && m.find(pos)) {
					if (m.start() == 0
							|| Orthography.isWordDelimiter(textInFile.charAt(m
									.start() - 1), ffont))
						if (m.end() == textInFile.length()
								|| Orthography.isWordDelimiter(textInFile
										.charAt(m.end()), ffont)) {
							String wordFound = textInFile.substring(m.start(),
									m.end());
							if (!wordsToHighlight.contains(wordFound)) {
								wordsToHighlight.add(wordFound);
							}
						}
					if (ffont.equals(""))
						pos = textInFile.length();
					else
						pos = m.end();
				}
			}
			if (wordsToHighlight.size() != 0)
				wordsFontsToHighlight.put(ffont, wordsToHighlight);
		}
		return wordsFontsToHighlight;
	}

	/*
	 * Quelques codes de caract�res syllabiques, comme '[' dans la police Prosyl
	 * qui repr�sente la consonne seule 'g', sont des caract�res utilis�s dans
	 * les expressions r�guli�res. Pour que ces caract�res soient interpr�t�s
	 * comme caract�res ordinaires dans les expressions r�guli�res, il faut les
	 * faire pr�c�der d'un '\'.
	 */
	static String prepareForRegexp(String x) {
		String y = prepareForRegexpNonAlphanum(x);

		return y;
	}

	static String prepareForRegexpNonAlphanum(String x) {
		if (x.length() == 0)
			return x;
		else {
			String x1 = x.substring(0, 1);
			String x1a = x1.replaceFirst("\\p{Punct}", "\\\\" + x1);
			return x1a + prepareForRegexp(x.substring(1));
		}
	}

	static String prepareForRegexpSpaces(String x) {
		String y = x.replaceAll("\\s+", "\\\\s+");
		return y;
	}

	/*
	 * Remplacer chaque caract�re de point par l'expression r�guli�re incluant
	 * tous les caract�res de point. Ex.: w6vNw\`\/i4 (iqqanaijaanik) devient
	 * w6vNw[<>`~\]+}]/i4
	 */
	static String withDots(String src) {
		String combinedDotCodesForRegexp = "[<>`~\\\\\\]+|}]";
		String regexp = "\\\\" + combinedDotCodesForRegexp;
		String newsrc = src.replaceAll(regexp, combinedDotCodesForRegexp);
		return newsrc;
	}

	static void copyInFile(OutputStream os) throws IOException {
		Vector elements = docu.elements;
		for (int i = 0; i < elements.size(); i++) {
			HTMLDocuElement el = (HTMLDocuElement) elements.elementAt(i);
			if (el.getType() == HTMLDocuElement.BETWEENTAG
					&& new String(((BetweenTag) el).text)
							.equals("\u0002 \u0003"))
				continue;
			os.write(el.getBytes());
		}
	}

	void highlightWords(Hashtable fonts, String latsyl)
			throws UnsupportedEncodingException {
		for (Enumeration e = fonts.keys(); e.hasMoreElements();) {
			Object font = e.nextElement();
			Vector words = (Vector) fonts.get(font);
			for (int i = 0; i < words.size(); i++) {
				String word = (String) words.elementAt(i);
				highlightExp(word, latsyl, (String) font);
			}
		}
	}

	/*
	 * HighlightJS the word 'word'. Find the word and surround it with a <span>
	 * element of class 'hl' such that it will be rendered highlighted.
	 */
	void highlightExp(String word, String latsyl, String font)
			throws UnsupportedEncodingException {
		// System.out.println("word: \""+word+"\"");
		boolean toBeConsidered = true;
		for (int iel = 0; iel < elements.size(); iel++) {
			HTMLDocuElement element = (HTMLDocuElement) elements.elementAt(iel);
			int elementType = element.getType();
			if (elementType == HTMLDocuElement.BETWEENTAG && toBeConsidered) {
				BetweenTag elm = (BetweenTag) element;
				String text = elm.text;
				/*
				 * Check if the font of the element is the same as the font of
				 * the word; if it is not the case, do not look for the word in
				 * this text element.
				 */
				if (elm.font != null && !elm.font.equals("null")
						&& !elm.font.equals(font))
					continue;
				int is = 0;
				do {
					// Does the word start in this text element?
					Integer start[] = startInTextNode(word, text, false, is,
							latsyl, iel, font);
					int posStart;
					int endStart;
					int posEnd = 0;
					if (start == null) {
						// No. Get the next text node.
						break;
					} else {
						// Yes, the word starts in this text element.
						posStart = start[0].intValue();
						endStart = start[1].intValue();
						// System.out.println("\""+word+"\" dans \""+text+"\" de
						// "+posStart+" � "+endStart);
						String wordRest = word.substring(endStart - posStart);

						// Is the whole word in this text element?
						if (wordRest.length() == 0) {
							// Yes.
							// Is it followed by a space?
							if ((endStart < text.length() && Orthography
									.isWordDelimiter(text.charAt(endStart),
											font))
									|| (endStart == text.length() && nextNodeStartsWithWordDelimiter(
											iel, font))) {
								// Yes: highlight the word.
								posEnd = posStart + word.length();
								iel = addHighlight(iel, posStart, text, iel,
										posEnd, text);
								break;
							} else
								is = posStart + 1;
						} else if (endStart == text.length()) {
							/*
							 * No. But we have reached the end of the text
							 * element. Look for the rest of the word in the
							 * following text element(s).
							 */
							Integer js[] = findEndInText(wordRest, iel, latsyl,
									font);
							if (js == null) {
								/*
								 * The rest of the word has not been found.
								 * Start anew looking for the word starting at
								 * the next position in the text element.
								 */
								is = posStart + 1;
							} else {
								/*
								 * The rest of the word has been found.
								 */
								int j = js[0].intValue();
								int endPos = js[1].intValue();
								HTMLDocuElement endNode = (HTMLDocuElement) docu.elements
										.elementAt(j);
								String endNodeText = ((BetweenTag) endNode).text;
								// Is it followed by a space?
								if ((endPos < endNodeText.length() && Orthography
										.isWordDelimiter(endNodeText
												.charAt(endPos), font))
										|| (endPos == endNodeText.length() && nextNodeStartsWithWordDelimiter(
												j, font))) {
									iel = addHighlight(iel, posStart, text, j,
											endPos, endNodeText);
									break;
								} else
									is = posStart + 1;
							}
						} else
							/*
							 * A part of the word has been found in the text
							 * element, but before the end of the text element
							 * was reached, a different character was met: it is
							 * not the word that started there. Start anew
							 * looking for the word starting at the next
							 * position in the text element.
							 */
							is = posStart + 1;
					}
				} while (true);
			} else if (elementType == HTMLDocuElement.TAG) {
				// Tag element
				// If 'title', 'script' or 'style', set a boolean to that effect
				Tag elm = (Tag) element;
				String tagName = elm.tag;
				if (tagName.equals("title") || tagName.equals("script")
						|| tagName.equals("style"))
					toBeConsidered = false;
				else
					toBeConsidered = true;
			}
		}
	}

	Integer[] startInTextNode(String target, String nodeText,
			boolean startAtBeginning, int is, String latsyl, int nodeIndex,
			String font) {
		int ltxt = 0;
		int ltgt = 0;
		ltxt = nodeText.length();
		ltgt = target.length();
		boolean sameChar;
		if (nodeText.length() != 0 && !nodeText.equals(" ")) {
			if (!startAtBeginning) {
				// 'target' peut commencer n'importe o� dans le texte
				for (int i = is; i < ltxt; i++) {
					if (latsyl.equals("syllabics"))
						sameChar = nodeText.charAt(i) == target.charAt(0);
					else
						sameChar = Character.toLowerCase(nodeText.charAt(i)) == Character
								.toLowerCase(target.charAt(0));
					if (sameChar) {
						int ic = i + 1;
						int j = 1;
						while (ic < ltxt && j < ltgt) {
							if (latsyl.equals("syllabics"))
								sameChar = nodeText.charAt(ic) == target
										.charAt(j);
							else
								sameChar = Character.toLowerCase(nodeText
										.charAt(ic)) == Character
										.toLowerCase(target.charAt(j));
							if (!sameChar)
								break;
							ic++;
							j++;
						}
						// V�rifier si d�limiteur de mot (espace, etc.) devant.
						if ((i == 0 && previousNodeEndsWithWordDelimiter(
								nodeIndex, font))
								|| (i > 0 && Orthography.isWordDelimiter(
										nodeText.charAt(i - 1), font)))
							if (j == ltgt)
								return new Integer[] { new Integer(i),
										new Integer(i + j) };
							else if (ic == ltxt)
								return new Integer[] { new Integer(i),
										new Integer(ic) };
					}
				}
			} else {
				/*
				 * 'target' doit commencer au d�but du texte, ce qui signifie
				 * qu'il s'agit de la continuation d'un mot dont on a trouv� le
				 * d�but dans l'�l�ment de texte pr�c�dent.
				 */
				int ic = 0;
				int j = 0;
				while (ic < ltxt && j < ltgt) {
					if (latsyl.equals("syllabics"))
						sameChar = nodeText.charAt(ic) == target.charAt(j);
					else
						sameChar = Character.toLowerCase(nodeText.charAt(ic)) == Character
								.toLowerCase(target.charAt(j));
					if (!sameChar)
						break;
					ic++;
					j++;
				}
				if (j == ltgt)
					return new Integer[] { new Integer(0), new Integer(j) };
				else if (ic == ltxt)
					return new Integer[] { new Integer(0), new Integer(ic) };
				else
					return null;
			}
		}
		return null;
	}

	Integer[] findEndInText(String target, int nodeIndex, String latsyl,
			String font) throws UnsupportedEncodingException {
		for (int i = nodeIndex + 1; i < elements.size(); i++) {
			HTMLDocuElement elm = null;
			elm = (HTMLDocuElement) elements.elementAt(i);
			if (elm.getType() == HTMLDocuElement.BETWEENTAG) {
				String text = ((BetweenTag) elm).text;
				Integer start[] = startInTextNode(target, text, true, 0,
						latsyl, i, font);
				if (start != null && start[0].intValue() == 0) {
					String targetRestant = target.substring(start[1].intValue()
							- start[0].intValue());
					if (targetRestant.length() == 0) {
						return new Integer[] { new Integer(i),
								new Integer(target.length()) };
					} else {
						target = targetRestant;
					}
				} else {
					return null;
				}
			}
		}
		return null;
	}

	int addHighlight(int startNode, int startPos, String startNodeText,
			int endNode, int endPos, String endNodeText)
			throws UnsupportedEncodingException {
		Tag spanNode = new Tag("<span class=\"hl\">", null);
		Tag endSpan = new Tag("</span>", null);
		startNodeText = ((BetweenTag) elements.elementAt(startNode)).text;
		endNodeText = ((BetweenTag) elements.elementAt(endNode)).text;
		/*
		 * The original text may be using HTML entities. The substring function
		 * will not work properly here. Let's use the HtmlEntities.substring
		 * function instead.
		 */
		if (endNode == startNode) {
			if (startPos == 0) {
				if (endPos == startNodeText.length()) {
					elements.add(startNode, spanNode);
					elements.add(startNode + 2, endSpan);
					return startNode + 2;
				} else {
					HTMLDocuElement elmTxt = (HTMLDocuElement) elements
							.elementAt(startNode);
					elmTxt.setText(startNodeText.substring(endPos), encoding);
					elements.setElementAt(elmTxt, startNode);
					// String highlighted = startNodeText.substring(0,endPos);
					String highlighted = startNodeText.substring(0, endPos);
					BetweenTag highlightedNode = new BetweenTag(highlighted,
							encoding);
					elements.add(startNode, endSpan);
					elements.add(startNode, highlightedNode);
					elements.add(startNode, spanNode);
					return startNode + 3;
				}
			} else {
				if (endPos == startNodeText.length()) {
					HTMLDocuElement elmTxt = (HTMLDocuElement) elements
							.elementAt(startNode);
					// elmTxt.setBytes(startNodeText.substring(0,startPos).getBytes(encoding));
					elmTxt.setText(startNodeText.substring(0, startPos),
							encoding);
					elements.setElementAt(elmTxt, startNode);
					// String highlighted =
					// startNodeText.substring(startPos,endPos);
					String highlighted = startNodeText.substring(startPos,
							endPos);
					BetweenTag highlightedNode = new BetweenTag(highlighted,
							encoding);
					elements.add(startNode + 1, spanNode);
					elements.add(startNode + 2, highlightedNode);
					elements.add(startNode + 3, endSpan);
					return startNode + 3;
				} else {
					HTMLDocuElement elmTxt = (HTMLDocuElement) elements
							.elementAt(startNode);
					// elmTxt.setBytes(startNodeText.substring(0,startPos).getBytes(encoding));
					elmTxt.setText(startNodeText.substring(0, startPos),
							encoding);
					elements.setElementAt(elmTxt, startNode);
					// String highlighted =
					// startNodeText.substring(startPos,endPos);
					String highlighted = startNodeText.substring(startPos,
							endPos);
					BetweenTag highlightedNode = new BetweenTag(highlighted,
							encoding);
					// BetweenTag lastText = new
					// BetweenTag(startNodeText.substring(endPos),encoding);
					BetweenTag lastText = new BetweenTag(startNodeText
							.substring(endPos), encoding);
					elements.add(startNode + 1, spanNode);
					elements.add(startNode + 2, highlightedNode);
					elements.add(startNode + 3, endSpan);
					elements.add(startNode + 4, lastText);
					return startNode + 3; // must continue with rest of text
				}
			}
		} else {
			int nextNode = startNode;
			if (startPos == 0) {
				elements.add(startNode, spanNode);
				elements.add(startNode + 2, endSpan);
				nextNode = startNode + 2;
				endNode += 2;
			} else {
				HTMLDocuElement elmTxt = (HTMLDocuElement) elements
						.elementAt(startNode);
				// elmTxt.setBytes(startNodeText.substring(0,startPos).getBytes(encoding));
				elmTxt.setText(startNodeText.substring(0, startPos), encoding);
				elements.setElementAt(elmTxt, startNode);
				// String highlighted = startNodeText.substring(startPos);
				String highlighted = startNodeText.substring(startPos);
				BetweenTag highlightedNode = new BetweenTag(highlighted,
						encoding);
				elements.add(startNode + 1, spanNode);
				elements.add(startNode + 2, highlightedNode);
				elements.add(startNode + 3, endSpan);
				nextNode = startNode + 3;
				endNode += 3;
			}

			while (nextNode != endNode) {
				elements.add(nextNode, spanNode);
				elements.add(nextNode + 2, endSpan);
				nextNode += 3;
				endNode += 2;
			}
			if (endPos == endNodeText.length()) {
				elements.add(endNode, spanNode);
				elements.add(endNode + 2, endSpan);
				return endNode + 2;
			} else {
				HTMLDocuElement elmTxt = (HTMLDocuElement) elements
						.elementAt(endNode);
				// elmTxt.setBytes(endNodeText.substring(endPos).getBytes(encoding));
				elmTxt.setText(endNodeText.substring(endPos), encoding);
				elements.setElementAt(elmTxt, endNode);
				// String highlighted = endNodeText.substring(0,endPos);
				String highlighted = endNodeText.substring(0, endPos);
				BetweenTag highlightedNode = new BetweenTag(highlighted,
						encoding);
				elements.add(endNode, endSpan);
				elements.add(endNode, highlightedNode);
				elements.add(endNode, spanNode);
				return endNode + 3;
			}
		}
	}

	/*
	 * args[0]: url args[1]: action c: contenu (appel de getPageContent()) vi:
	 * vérifier inuktitut
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out
					.println("2 arguments: <url> <action - c:contenu  vi:vérifier quantité d'inuktitut  htmli:contenu html inuktitut avec http headers   htmlr: contenu html latin avec http headers>");
			System.exit(1);
		}
		NRC_HTMLDocument doc = null;
		try {
			doc = new NRC_HTMLDocument(args[0]);
		} catch (Exception e1) {
			if (doc != null)
				doc.close();
			System.out.println("---erreur de classe '" + e1.getClass().getName() + "' : " + e1.getMessage());
			System.exit(1);
		}
		// Only html, pdf, and doc; otherwise, null.
		if (doc != null) {
			if (args[1].equals("c")) {
				try {
					String contenu = null;
					contenu = doc.getPageContent();
					System.out.println(URLEncoder.encode(contenu, "utf-8"));
				} catch (UnsupportedEncodingException e) {
				}
			} else if (args[1].equals("vi")) {
				float ip = doc.getInuktitutPercentage();
				System.out.print(Float.toString(ip));
			} else if (args[1].equals("htmli")) {
				try {
					doc.toUnicode(System.out);
				} catch (Exception e) {
					System.out.print("");
				}
			} else if (args[1].equals("htmlr")) {
				try {
					doc.toRoman(System.out);
				} catch (OutOfMemoryError e) {
					System.out.print("");
				} catch (Exception e) {
					System.out.print("");
				}
			} else {
				System.out.print("");
			}
			doc.close();
		}
	}

	private boolean previousNodeEndsWithWordDelimiter(int nodeIndex,
			String fontName) {
		if (nodeIndex == 0)
			return true;
		nodeIndex = nodeIndex - 1; // previous node
		HTMLDocuElement previousElement = (HTMLDocuElement) docu.elements
				.elementAt(nodeIndex);
		if (previousElement.getType() == HTMLDocuElement.BETWEENTAG) {
			// The previous node has to be a text element
			String text = null;
			try {
				text = new String(previousElement.getBytes(), encoding);
			} catch (UnsupportedEncodingException e) {
				text = new String(previousElement.getBytes());
			}
			// and it has to end with a space.
			if (text.equals("\u0002 \u0003"))
				return true;
			else if (Orthography.isWordDelimiter(
					text.charAt(text.length() - 1), fontName))
				return true;
			else
				return false;
		} else {
			// If the previous node is not a text element,
			// and if it is a tag element that does not add a space, check
			// the previous node.
			String tag = ((Tag) previousElement).tag;
			if (Arrays.binarySearch(HTMLDocuElement.tagsThatDoNotAddSpace, tag) >= 0) {
				return previousNodeEndsWithWordDelimiter(nodeIndex, fontName);
			} else
				return false;
		}
	}

	private static boolean nextNodeStartsWithWordDelimiter(int nodeIndex,
			String fontName) {
		HTMLDocuElement nextElement;

		// Skip end tag elements.
		while (nodeIndex < docu.elements.size() - 1
				&& new String((nextElement = (HTMLDocuElement) docu.elements
						.elementAt(++nodeIndex)).getBytes()).startsWith("</"))
			;

		// This is the last node of the file.
		if (nodeIndex == docu.elements.size() - 1)
			return true;

		nodeIndex = nodeIndex + 1; // next node
		nextElement = (HTMLDocuElement) docu.elements.elementAt(nodeIndex);

		if (nextElement.getType() == HTMLDocuElement.BETWEENTAG) {
			// The next node is a text element: it has to start with a space.
			String text = nextElement.toString();
			if (Orthography.isWordDelimiter(text.charAt(0), fontName))
				return true;
			else
				return false;

		} else if (nodeIndex == docu.elements.size() - 1)
			// The next node is a tag element, and it is the last element.
			return true;

		else {
			// The next node is a tag element.
			// If the node after it is a special space element, return true.
			nodeIndex = nodeIndex + 1;
			nextElement = (HTMLDocuElement) docu.elements.elementAt(nodeIndex);
			if (nextElement.getType() == HTMLDocuElement.BETWEENTAG) {
				String text = ((BetweenTag) nextElement).text;
				if (text.equals("\u0002 \u0003"))
					return true;
				else if (Orthography.isWordDelimiter(text.charAt(0), fontName))
					return true;
				else
					return false;
			} else
				return false;
		}
	}

	/*
	 * ----------------------------- Translittération -------------------------
	 */

	private static Pattern pfw2 = Pattern
			.compile("(\\S+)(\\s+(\\S+))?(\\s+(\\S+))?");

	public Object[] transliterate() throws OutOfMemoryError, Exception {
		transHash = new Hashtable();
		String fileCopy = null;
		traiterDocPourTranslit();
		fileCopy = "file://localhost/" + copyOfFile.getAbsolutePath();
		HtmlDocu doc2 = new HtmlDocu(new URL(fileCopy));
		doc2.insertBase(getBase());
		File fout2 = traiterDocPourTranslit2(doc2, encoding, encoding);
		// copyOfFile.delete();
		return new Object[] { encoding, fout2 };
	}

	public void toRoman(OutputStream out) throws Exception {
		File fout2 = null;
		Exception exc = null;
		try {
			transHash = new Hashtable();
			traiterDocPourTranslit();
			HtmlDocu doc2 = new HtmlDocu(copyOfFile, encoding);
			doc2.insertBase(getBase());
			fout2 = traiterDocPourTranslit3(doc2, encoding, "latin");
			out.write(("Content-Type: text/html; charset=" + encoding)
					.getBytes());
			out.write("\n\n".getBytes());
			out.write("<!--END OF HTTP HEADERS-->".getBytes());
			FileInputStream fr = new FileInputStream(fout2);
			int c;
			while ((c = fr.read()) != -1) {
				out.write(c);
			}
			fr.close();
			fout2.delete();
			out.flush();
		} catch (Exception e) {
			exc = e;
		} finally {
			if (fout2 != null)
				fout2.delete();
			if (exc != null)
				throw exc;
		}
	}

	public void toUnicode(OutputStream out) throws Exception {
		File fout2 = null;
		Exception exc = null;
		try {
			transHash = new Hashtable();
			traiterDocPourTranslit();
			HtmlDocu doc2 = new HtmlDocu(copyOfFile, encoding);
			doc2.insertBase(getBase());
			fout2 = traiterDocPourTranslit3(doc2, encoding, "utf-8");
			out.write(("Content-Type: text/html; charset=" + "utf-8")
					.getBytes());
			out.write("\n\n".getBytes());
			out.write("<!--END OF HTTP HEADERS-->".getBytes());
			FileInputStream fr = new FileInputStream(fout2);
			int c;
			while ((c = fr.read()) != -1) {
				out.write(c);
			}
			fr.close();
			fout2.delete();
			out.flush();
		} catch (Exception e) {
			exc = e;
		} finally {
			if (fout2 != null)
				fout2.delete();
			if (exc != null)
				throw exc;
		}
	}

	// -----------------------------------------------------------
	// Traitement du document HTML
	// - Recherche du texte en inuktitut et traitement de ce texte
	// ------------------------------------------------------------

	private void traiterDocPourTranslit() {
		ElementIterator iterator = new ElementIterator(this);
		javax.swing.text.Element elem;
		String text;
		// PrintWriter out = null;

		try {
			// ----------------------------------------------
			// It�ration sur chaque �l�ment du document HTML
			// ----------------------------------------------
			while ((elem = iterator.next()) != null) {
				AttributeSet as = elem.getAttributes();
				// --------------
				// �l�ment TITLE
				// --------------
				if (elem.getName().equals("title")) {
					if (as.getAttribute(HTML.Attribute.ENDTAG) != null) {
						String title = (String) getProperty(Document.TitleProperty);
						if (title != null) {
							title = TransCoder.unicodeToRoman(title);
							putProperty(Document.TitleProperty, title);
						}
					}
				}

				// -----------------
				// �l�ment de TEXTE
				// -----------------

				// On v�rifie si le texte est du texte en inuktitut;
				// si c'est le cas, il est trait� selon les besoins.
				else if (elem.getName().equals("content")) {
					int beg = elem.getStartOffset();
					int end = elem.getEndOffset();
					text = getText(beg, end - beg);

					/*
					 * Trouver la police sp�cifi�e pour cet �l�ment de texte. Si
					 * c'est une police inuktitut archa�que telle que Nunacom ou
					 * Prosyl, enregistrer le premier mot de cet �l�ment avec le
					 * nom de la police.
					 */
					String[] fontFamilies = getFont(elem);
					String font = Font.containsLegacyFont(fontFamilies);
					if (font != null) {
						// pfw2: Un ou plusieurs caract�res non-blancs
						Matcher mfw = pfw2.matcher(text);
						if (mfw.find()) {
							String firstWord = mfw.group(1)
									+ (mfw.group(2) != null ? mfw.group(2) : "")
									+ (mfw.group(4) != null ? mfw.group(4) : "");
							transHash.put(firstWord, font);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // fin de traiterdoc()

	static File traiterDocPourTranslit2(HtmlDocu doc2, String enc,
			String translitMode) throws IOException {
		String translitFont;
		if (translitMode.equals("utf-8"))
			translitFont = "pigiarniq";
		else
			translitFont = "arial";
		for (int i = 0; i < doc2.elements.size(); i++) {
			HTMLDocuElement element = (HTMLDocuElement) doc2.elements
					.elementAt(i);

			// For each text element:
			if (element.getType() == HTMLDocuElement.BETWEENTAG) {
				String txt = null;
				try {
					/*
					 * Text elements in HTMLDocu contain the raw bytes from the
					 * file. HTML entities have to be converted to characters
					 * before attempting any match.
					 */
					txt = new String(element.getBytes(), enc);

					String txttmp = HtmlEntities.entityToChar(txt.replaceAll(
							"\\s+", " "));
					// pfw2: One or more non-white-space characters
					// Get the first word: this has been recorded earlier as a
					// key to the font of every text element.
					Matcher mfw = pfw2.matcher(txttmp);
					String key = null;
					if (mfw.find())
						key = mfw.group(1)
								+ (mfw.group(2) != null ? mfw.group(2) : "")
								+ (mfw.group(4) != null ? mfw.group(4) : "");
					/*
					 * In the first pass where the URL was parsed with the HTML
					 * parser, for tag elements ending with '/>', somehow the
					 * element is recognized, but not '/>', such that '/' is
					 * considered as a text element, and '>' is added to the
					 * next text element. This makes that the key in transHash
					 * starts with '>'. We check here for that situation, and
					 * add '>' to the string we want to check in transHash.
					 */
					if (i != 0) {
						HTMLDocuElement precElement = (HTMLDocuElement) doc2.elements
								.elementAt(i - 1);
						if (precElement.getType() == HTMLDocuElement.TAG
								&& ((Tag) precElement).text.endsWith("/>"))
							key = ">" + key;
					}

					// The first word of the text element matches a recorded key
					if (key != null && transHash.containsKey(key)) {
						int pos = 0;
						String transText = "";
						// Get the font of the text element
						String font = (String) transHash.get(key);
						/*
						 * The text is transliterated from the legacy font to
						 * the roman alphabet. HTML entities are skipped over.
						 */
						Matcher mh = HtmlEntities.pHtmlEntity.matcher(txt);
						while (pos < txt.length() && mh.find(pos)) {
							String htmlEntity = mh.group();
							String transPart;
							if (translitMode.equals("utf-8")) {
								transPart = TransCoder.legacyToUnicode(txt
										.substring(pos, mh.start()), font);
							} else {
								transPart = TransCoder.legacyToRoman(txt
										.substring(pos, mh.start()), font);
							}
							transText += transPart;
							transText += htmlEntity;
							pos = mh.end();
							if (txt.charAt(pos) == ';') {
								transText += ';';
								pos++;
							}
						}
						String transPart;
						if (translitMode.equals("utf-8")) {
							transPart = TransCoder.legacyToUnicode(txt
									.substring(pos), font);
						} else {
							transPart = TransCoder.legacyToRoman(txt
									.substring(pos), font);
						}
						transText += transPart;
						String fontAndTxt = "<FONT face=" + translitFont + ">"
								+ transText + "</FONT>";
						byte bs[] = fontAndTxt.getBytes(translitMode
								.equals("utf-8") ? translitMode : enc);
						element.setBytes(bs);
					} else {
						String txtentity = HtmlEntities.toStringInuktitut(txt);
						byte bs[] = txtentity.getBytes(translitMode
								.equals("utf-8") ? translitMode : enc);
						element.setBytes(bs);
					}
					doc2.elements.setElementAt(element, i);
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		File fout = null;
		try {
			fout = File.createTempFile("translitOutput", ".htm", tmpDir);
			OutputStream os = new FileOutputStream(fout);
			doc2.write(os);
			os.close();
		} catch (IOException e1) {
			throw e1;
		}
		return fout;
	}

	static File traiterDocPourTranslit3(HtmlDocu doc2, String enc,
			String translitMode) throws IOException {
		String translitFont;
		if (translitMode.equals("utf-8"))
			translitFont = "pigiarniq";
		else
			translitFont = "arial";
		for (int i = 0; i < doc2.elements.size(); i++) {
			HTMLDocuElement element = (HTMLDocuElement) doc2.elements
					.elementAt(i);

			// For each text element:
			if (element.getType() == HTMLDocuElement.BETWEENTAG) {
				String txt = null;
				try {
					/*
					 * Text elements in HTMLDocu contain the raw bytes from the
					 * file. HTML entities have to be converted to characters
					 * before attempting any match.
					 */
					txt = new String(element.getBytes(), enc);

					String txttmp = HtmlEntities.entityToChar(txt);
					// pfw2: One or more non-white-space characters
					// Get the first word: this has been recorded earlier as a
					// key to the font of every text element.
					Matcher mfw = pfw2.matcher(txttmp);
					String key = null;
					if (mfw.find()) {
						key = mfw.group(1)
								+ (mfw.group(2) != null ? mfw.group(2) : "")
								+ (mfw.group(4) != null ? mfw.group(4) : "");
						key = key.replaceAll("\\s+", " ");
					}
					/*
					 * In the first pass where the URL was parsed with the HTML
					 * parser, for tag elements ending with '/>', somehow the
					 * element is recognized, but not '/>', such that '/' is
					 * considered as a text element, and '>' is added to the
					 * next text element. This makes that the key in transHash
					 * starts with '>'. We check here for that situation, and
					 * add '>' to the string we want to check in transHash.
					 */
					if (i != 0) {
						HTMLDocuElement precElement = (HTMLDocuElement) doc2.elements
								.elementAt(i - 1);
						if (precElement.getType() == HTMLDocuElement.TAG
								&& ((Tag) precElement).text.endsWith("/>"))
							key = ">" + key;
					}

					// The first word of the text element matches a recorded key
					if (key != null && transHash.containsKey(key)) {
						String transText = "";
						// Get the font of the text element
						String font = (String) transHash.get(key);
						/*
						 * The text is transliterated from the legacy font to
						 * the roman alphabet.
						 */
						String transPart;
						String script;
						if (translitMode.equals("utf-8")) {
							transPart = TransCoder
									.legacyToUnicode(txttmp, font);
							script = "s";
							// transPart = new
							// String(transPart.getBytes("utf-8"));
						} else {
							transPart = TransCoder.legacyToRoman(txttmp, font);
							script = "l";
						}
						transText += transPart;
						String fontAndTxt = "<FONT face=" + translitFont 
							+ " class=\"inuk-" + script + "\">"
							+ transText + "</FONT>";
						byte bs[] = fontAndTxt.getBytes(translitMode
								.equals("utf-8") ? translitMode : enc);
						element.setBytes(bs);
					} else {
						String txtentity = HtmlEntities.toStringInuktitut(txt);
						if (Syllabics.containsInuktitut(txttmp)) {
							String script;
							if (translitMode.equals("utf-8")) {
								script = "s";
							} else {
								txtentity = TransCoder.unicodeToRoman(txttmp);
								script = "l";
							}
							txtentity = "<FONT face=" + translitFont
								+ " class=\"inuk-" + script + "\">"
								+ txtentity + "</FONT>";
						}
						byte bs[] = txtentity.getBytes(translitMode
								.equals("utf-8") ? translitMode : enc);
						element.setBytes(bs);
					}
					doc2.elements.setElementAt(element, i);
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		File fout = null;
		try {
			fout = File.createTempFile("translitOutput", ".htm", tmpDir);
			OutputStream os = new FileOutputStream(fout);
			doc2.write(os);
			os.close();
		} catch (IOException e1) {
			throw e1;
		}
		return fout;
	}

	public WebPageReader getWpr() {
		return wpr;
	}

	public Object[] getAllFonts() {
		if (fonts == null)
			getPageContent();
		return (Object[]) fonts.keySet().toArray();
	}

	public String[] getAllFontsNames() {
		Object[] fnts = getAllFonts();
		String[] fontNames = new String[fnts.length];
		for (int i = 0; i < fnts.length; i++)
			fontNames[i] = (String) fnts[i];
		return fontNames;
	}

	public String[] getInuktitutFonts() {
		String[] allFonts = getAllFontsNames();
		Vector fs = new Vector();
		List inuktitutFonts = Arrays.asList(Font.fonts);
		for (int i = 0; i < allFonts.length; i++) {
			String fnt = allFonts[i];
			if (inuktitutFonts.contains(fnt))
				if (!fs.contains(fnt))
					fs.add(fnt);
		}
		return (String[]) fs.toArray(new String[] {});
	}

	static public class Encoding {

		public String value;
		public String source;

		public Encoding(String enc, String source) {
			value = enc;
			this.source = source;
		}

		public String toString() {
			return "Encoding{" + value + "," + source + "}";
		}

	}

	/*
	 * --------------------------------- READER -------------------------------
	 */

	class HtmlReader extends HTMLDocument.HTMLReader {

		private boolean inStyle = false;

		public HtmlReader(int offset) {
			this(offset, 0, 0, null);
		}

		public HtmlReader(int offset, int popDepth, int pushDepth,
				HTML.Tag insertTag) {
			super(offset, popDepth, pushDepth, insertTag);
			// L'enregistrement des actions n'est utile que si les
			// m�thodes 'super.handle..." sont ex�cut�es. C'est de l� que
			// les m�thodes '<action>.start' et '<action>.end' sont appel�es.
			// Ainsi, si on enregistre l'action BlockAction pour SPAN, il faut
			// que dans handleSimpleTag(HTML.Tag.SPAN,...), on appelle
			// super.handleStartTag et super.handleEndTag. Cependant,
			// super.handleStartTag convertit l'attribut STYLE, un effet
			// que l'on ne veut pas n�cessairement. Il vaut mieux alors
			// appeler simplement blockOpen et blockClose.
			registerTag(HTML.Tag.H1, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.H2, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.H3, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.H4, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.H5, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.H6, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.A, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.ADDRESS,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.B, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.BASEFONT,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.BIG, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.CITE,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.CODE,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.DFN, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.EM, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.FONT,
					new HTMLDocument.HTMLReader.BlockAction());
			// registerTag(HTML.Tag.FORM,
			// new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.I, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.KBD, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.SAMP,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.SMALL,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.STRIKE,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.S, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.STRONG,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.SUB, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.SUP, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.TT, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.U, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.VAR, new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.DT, new HTMLDocument.HTMLReader.BlockAction());
			// registerTag(new NRC_HTMLTag("TBODY"),new
			// HTMLDocument.HTMLReader.BlockAction());
			registerTag(NRC_HTMLTag.TBODY,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(NRC_HTMLTag.LABEL,
					new HTMLDocument.HTMLReader.BlockAction());
			// registerTag(NRC_HTMLTag.PHP, new PHPAction());
			registerTag(NRC_HTMLTag.NOINDEX,
					new HTMLDocument.HTMLReader.BlockAction());
			// registerTag(HTML.Tag.STYLE,
			// new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.SCRIPT,
					new HTMLDocument.HTMLReader.BlockAction());
			registerTag(HTML.Tag.SPAN,
					new HTMLDocument.HTMLReader.BlockAction());
		}

		// class PHPAction extends HTMLDocument.HTMLReader.TagAction {
		// public void start(HTML.Tag t, MutableAttributeSet a) {
		// try {
		// handleSimpleTag(t, a, 0);
		// } catch (ChangedCharSetException e) {
		// e.printStackTrace();
		// throw e;
		// }
		// }
		// }

		// Dans la classe originale HTMLDocument.HTMLReader, l'�l�ment STYLE
		// est enregistr� avec la classe d'action StyleAction qui n'est pas
		// publique et qui ne fait qu'ajouter un �l�ment � la variable
		// non-publique 'styles'. Aucun �l�ment n'est ajout� au document.
		// Si on enregistre l'�l�ment avec la classe d'action BlockAction,
		// le contenu de STYLE n'est pas ajout� � la variable 'styles'.
		// Comme la classe BlockAction ne fait qu'appeler les m�thodes
		// 'blockOpen' et 'blockClose', on n'a qu'� ajouter ces appels
		// � la fin de 'handleStartTag' et 'handleEndTag' pour qu'un �l�ment
		// bloc soit ajout� dans le document HTML. Les appels aux m�thodes
		// 'super' feront le travail pour ce qui est de la variable 'styles'.
		// 
		// La variable 'styles' est importante: lorsque le marqueur </HEAD>
		// est rencontr�, tous les �l�ments contenus dans le vecteur de la
		// variable 'styles' sont ajout�s � la feuille de style du document.
		//

		/*
		 * La m�thode super.handleStartTag ajoute les propri�t�s de l'attribut
		 * 'style' aux attributs de l'�l�ment cr�� et supprime l'attribut
		 * 'style' lui-m�me de l'�l�ment cr�� et de la liste des attributs 'a'
		 * pass�e en argument.
		 * 
		 * Dans les versions pr�c�dentes de java, SPAN �tait trait� par
		 * handleSimpleTag. Mais il est maintenant trait� par handleStartTag.
		 * Son action associ�e par d�faut est 'CharacterAction'. La m�thode
		 * 'start' de l'action est appel�e par super.handleStartTag. Pour
		 * 'CharacterAction', elle ajoute les attributs � la liste des attributs �
		 * �tre utilis�s pour le prochain �l�ment de texte.
		 */

		public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			Object styleAtt;
			// System.out.println("handleStartTag: " + t.toString());
			if (t == HTML.Tag.STYLE) {
				inStyle = true;
			} else if (t == HTML.Tag.TITLE) {
				// inTitle = true;
			}
			// else if (t == HTML.Tag.FORM)
			// inForm = true;
			if (a.isDefined(HTML.Attribute.STYLE))
				styleAtt = a.getAttribute(HTML.Attribute.STYLE);
			else
				styleAtt = null;
			super.handleStartTag(t, a, pos);

			/*
			 * handleStartTag de HTMLDocument convertit les propri�t�s
			 * sp�cifi�es par la valeur de l'attribut STYLE en attributs;
			 * l'attribut STYLE est enlev� et remplac� par ces attributs. Pour
			 * contrer cet effet, on inverse le processus en enlevant ces
			 * attributs et en rajoutant l'attribut STYLE.
			 * 
			 * Certaines propri�t�s cependant ne sont pas retourn�es par la
			 * m�thode getStyleSheet().getDeclaration(String), par exemple
			 * mso-cellspacing, mso-padding-alt.
			 */

			if (styleAtt != null) {
				// Attributs de la valeur de l'attribut STYLE
				AttributeSet styleAttributes = getStyleSheet().getDeclaration(
						(String) styleAtt);
				// Dernier �l�ment juste ajout� par super.handleStartTag,
				// correspondant au startTag actuellement trait�.
				ElementSpec es = (ElementSpec) parseBuffer.lastElement();
				// Ses attributs
				MutableAttributeSet attrEs = (MutableAttributeSet) es
						.getAttributes();
				// Enlever les attributs qui ont �t� ajout�s
				for (Enumeration e = styleAttributes.getAttributeNames(); e
						.hasMoreElements();)
					attrEs.removeAttribute(e.nextElement());
				// Remettre l'attribut STYLE qui a �t� enlev�
				attrEs.addAttribute(HTML.Attribute.STYLE, styleAtt);
			}
		}

		public void handleEndTag(HTML.Tag t, int pos) {
			// System.out.println("handleEndTag: " + t.toString());
			if (t == HTML.Tag.STYLE) {
				inStyle = false;
			} else if (t == HTML.Tag.TITLE) {
				// inTitle = false;
			}
			// else if (t == HTML.Tag.FORM) {
			// if (inForm) {
			// inForm = false;
			// super.handleEndTag(t, pos);
			// }
			// }
			super.handleEndTag(t, pos);

		}

		// Lorsque le texte � l'int�rieur de STYLE est d�tect�, il n'est
		// pas ajout� au contenu du document, mais plut�t ajout� au vecteur
		// 'styles'. Ici, on l'ajoute au document avec 'addContent'.

		public void handleText(char[] dat, int pos) {
			// System.out.println("handleText: " + new String(dat));
			super.handleText(dat, pos);
			if (inStyle) {
				if (dat.length >= 1) {
					// addContent(dat, 0, dat.length);
					String text = new String(dat);
					getStyleSheet().addRule(text.toLowerCase());
				}
			}
			// else if (inTitle) {
			// String title = new String(dat);
			// String titleProperty = Document.TitleProperty;
			// putProperty(titleProperty,title);
			// }
		}

		public void handleComment(char[] dat, int pos) {
			// System.out.println("handleComment: " + new String(dat));
			// String comment = new String(dat);
			super.handleComment(dat, pos);
			if (inStyle) {
				if (dat.length >= 1) {
					// addContent(dat, 0, dat.length);
					String text = new String(dat);
					getStyleSheet().addRule(text.toLowerCase());
				}
			}
		}

		public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			// System.out.println("handleSimpleTag: " + t.toString());
			String charsetContent = null;
			if (t == HTML.Tag.SPAN
					|| t.toString().toLowerCase().equals("tbody")
					|| t.toString().toLowerCase().equals("label"))
				if (a.isDefined(HTML.Attribute.ENDTAG)
						&& ((String) a.getAttribute(HTML.Attribute.ENDTAG))
								.equals("true"))
					// super.handleEndTag(t,pos);
					blockClose(t);
				else {
					// System.out.println(a);
					// super.handleStartTag(t,a,pos);
					blockOpen(t, a);
				}
			else {
				if (t == HTML.Tag.META) {
					if (a.isDefined(HTML.Attribute.HTTPEQUIV)) {
						String httpequiv = (String) a
								.getAttribute(HTML.Attribute.HTTPEQUIV);
						if (httpequiv.toLowerCase().equals("content-type")) {
							if (a.isDefined(HTML.Attribute.CONTENT)) {
								charsetContent = (String) a
										.getAttribute(HTML.Attribute.CONTENT);
								int posCharset = charsetContent
										.indexOf("charset");
								if (posCharset != -1) {
									int posEndCharset = charsetContent.indexOf(
											(int) ';', posCharset);
									if (posEndCharset == -1)
										posEndCharset = charsetContent.length();
									String encodage = charsetContent.substring(
											posCharset + "charset=".length(),
											posEndCharset);
									/*
									 * Set the encoding of the document only if
									 * not already mentioned by a http header.
									 */
									if (encoding == null)
										encoding = encodage;
									/*
									 * If on the first opening of the file,
									 * abort the parsing and return an exception
									 * with the detected encoding.
									 * 
									 * Otherwise replace the charset to UTF-8 in
									 * the META element.
									 */
									// String newContent =
									// charsetContent.replaceAll(
									// encodage, "UTF-8");
									// a.removeAttribute(HTML.Attribute.CONTENT);
									// a.addAttribute(HTML.Attribute.CONTENT,
									// newContent);
								}
							}
						} else if (httpequiv.toLowerCase().equals("refresh")) {
							if (a.isDefined(HTML.Attribute.CONTENT)) {
							}
						}
					}
				} else if (t == HTML.Tag.LINK) {
					Object rel = null;
					if (a.isDefined(HTML.Attribute.REL))
						rel = a.getAttribute(HTML.Attribute.REL);
					if (rel != null) {
						Object type = null;
						String href;
						URL linkUrl;
						if (rel.toString().equalsIgnoreCase("stylesheet")) {
							if (a.isDefined(HTML.Attribute.TYPE))
								type = a.getAttribute(HTML.Attribute.TYPE);
							if (type == null
									|| type.toString().equalsIgnoreCase(
											"text/css")) {
								if (a.isDefined(HTML.Attribute.TITLE)) {
								}
								// Note sur le titre: avec REL=stylesheet, il ne
								// peut y avoir
								// qu'une seule feuille persistente (sans TITLE)
								// et une seule
								// feuille pr�f�r�e (avec TITLE), toutes deux
								// appliqu�es. Alors,
								// en supposant que la page html a �t� bien
								// �crite, on n'a pas
								// � faire quoi que ce soit: on charge les
								// r�gles.
								// (Il faudra peut-�tre tout de m�me v�rifier si
								// la page a �t�
								// bien �crite, �ventuellement).
								href = a.getAttribute(HTML.Attribute.HREF)
										.toString();
								linkUrl = resolveUrlFromBase(href);
								if (linkUrl != null) {
									getStyleSheet().importStyleSheet(linkUrl);
									StyleSheet ss = getStyleSheet();
									Enumeration styleNames = ss.getStyleNames();
									while (styleNames.hasMoreElements()) {
										String name = (String) styleNames
												.nextElement();
										Style style = ss.getStyle(name);
										style.getName();
									}
								}
							}
						}
					}
				}
				super.handleSimpleTag(t, a, pos);
			}
		}

		public void handleError(String msg, int pos) {
			// System.out.println(">>>>>>>Error at " + pos + ": " + msg);
			// String mes = new String(msg);
			msg.toString();
		}

		void mythrow(Exception e) throws Exception {
			throw e;
		}

		// public void flush() {
		//            
		// }

	}

	/*
	 * --------------------------------------------------------------------------------------------------------------------
	 * Cette classe repr�sente un �l�ment de texte ("content" en HTML) qui
	 * contient du texte en inuktitut. Il peut s'agir soit: a. d'un �l�ment de
	 * texte tout en inuktitut affich� avec une police inuktitut � 7 ou 8 bits
	 * non unicode b. d'un �l�mentde texte dont une partie est cod�e en unicode
	 * (on assume que l'autre partie n'est pas de l'inuktitut) element:
	 * l'�l�ment HTML codage: UNICODE ou NONUNICODE, selon a) ou b) morceaux:
	 * diff�rentes parties du texte, alternant entre l'inuktitut et le
	 * non-inuktitut.
	 * 
	 * Un �l�ment selon a) ne contient qu'un seul morceau.
	 */

	public static class TexteHTML {
		javax.swing.text.Element element;

		int codage;

		Vector morceaux;

		HTMLDocument doc;

		String texte;

		public TexteHTML(String text) {
			texte = text;
		}

		public TexteHTML(javax.swing.text.Element elem, HTMLDocument docum) {
			doc = docum;
			element = elem;
			try {
				int beg = element.getStartOffset();
				int end = element.getEndOffset();
				texte = doc.getText(beg, end - beg);
			} catch (Exception e) {
			}
		}

		public int getCodage() {
			return codage;
		}

		public Vector getMorphParts() {
			return morceaux;
		}

		public void setCodage(int cod) {
			codage = cod;
		}

		// public void setPolice(String pol) {
		// police = pol;
		// }

		public void setMorphParts(Vector morcx) {
			morceaux = morcx;
		}

		public String toString() {
			String police = ((Inuktitut.MorceauTexte) morceaux.get(0))
					.getPolice();
			String s = codage == Roman.UNICODE ? "u" : ("f/" + police);
			s = s + "/" + texte + "/";
			return s;
		}

		// Cette m�thode remplace le texte en inuktitut par sa translit�ration
		// en caract�res latins.

		public void transliterer() {
			String text;
			String textSyllabes;

			try {
				text = texte;

				// Si le texte est vide, ne rien faire
				if (text.length() == 0)
					;

				// Pour une raison que j'ignore encore, lorsqu'on a
				// <FONT face=nunacom></FONT>, le "newline" qui est ins�r�,
				// s'il est trait� par PoliceNunacom.convAunicode (et par
				// convAsyllabes ?) cause des erreurs. Comme
				// il n'y a aucune raison de traiter le "newline", on ne le
				// fait pas, et �a r�gle le probl�me.
				else if (text.length() == 1 && text.charAt(0) == 10)
					;

				else {
					for (int i = 0; i < morceaux.size(); i++) {
						Inuktitut.MorceauTexte morceau = (Inuktitut.MorceauTexte) morceaux
								.get(i);
						if (morceau.isInuk()) {
							String txt1 = morceau.getTexte();
							String txt = morceau.getTexte().replace((char) 160,
									(char) 32);
							txt = txt1;
							textSyllabes = TransCoder.unicodeToRoman(txt);
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setTexte(textSyllabes);
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setSyllabic(false);
						} else {
							// Remplacer le code correspondant � &nbsp; par la
							// cha�ne "&nbsp;".
							String newText = new String();
							String txt1 = morceau.getTexte();
							newText = txt1.replaceAll(new String(
									new char[] { (char) 160 }), "&nbsp;");
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setTexte(newText);
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setSyllabic(false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	} // class TexteHTML

	public static class TitreHTML {
		javax.swing.text.Element element;

		int codage;

		Vector morceaux;

		HTMLDocument doc;

		String texte;

		public TitreHTML(javax.swing.text.Element elem, HTMLDocument docum) {
			doc = docum;
			element = elem;
			texte = (String) doc.getProperty(Document.TitleProperty);
		}

		public TitreHTML(String txt) {
			texte = txt;
		}

		public int getCodage() {
			return codage;
		}

		public Vector getMorphParts() {
			return morceaux;
		}

		public void setCodage(int cod) {
			codage = cod;
		}

		// public void setPolice(String pol) {
		// police = pol;
		// }

		public void setMorphParts(Vector morcx) {
			morceaux = morcx;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < morceaux.size(); i++) {
				Inuktitut.MorceauTexte m = (Inuktitut.MorceauTexte) morceaux
						.elementAt(i);
				sb.append(m.getTexte());
			}
			return sb.toString();
		}

		// Cette m�thode remplace le texte en inuktitut par sa translit�ration
		// en caract�res latins.

		public void transliterer() {
			String text;
			String textSyllabes;

			try {
				text = texte;

				// Si le texte est vide, ne rien faire
				if (text.length() == 0)
					;

				// Pour une raison que j'ignore encore, lorsqu'on a
				// <FONT face=nunacom></FONT>, le "newline" qui est ins�r�,
				// s'il est trait� par PoliceNunacom.convAunicode (et par
				// convAsyllabes ?) cause des erreurs. Comme
				// il n'y a aucune raison de traiter le "newline", on ne le
				// fait pas, et �a r�gle le probl�me.
				else if (text.length() == 1 && text.charAt(0) == 10)
					;

				else {
					for (int i = 0; i < morceaux.size(); i++) {
						Inuktitut.MorceauTexte morceau = (Inuktitut.MorceauTexte) morceaux
								.get(i);
						if (morceau.isInuk()) {
							String txt = morceau.getTexte().replace((char) 160,
									(char) 32);
							textSyllabes = TransCoder.unicodeToRoman(txt);
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setTexte(textSyllabes);
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setSyllabic(false);
						} else {
							((Inuktitut.MorceauTexte) morceaux.get(i))
									.setSyllabic(false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	} // class TitreHTML

	public String getPreferredFont() {
		return null;
	}

	public void close() {
		copyOfFile.delete();
	}

	public boolean containsInuktitut() {
		if (pageContent == null)
			getPageContent();
		return containsInuktitut;
	}

	public float getInuktitutPercentage() {
		if (containsInuktitut())
			return (float) lengthOfInuktitutContent
					/ (float) lengthOfTotalContent;
		else
			return 0;
	}

	/*
	 * Pour d�terminer si le texte est en inuktitut, on v�rifie les conditions
	 * suivantes: - le texte est encod� en UNICODE dans la plage 1400-167F
	 * hexad�cimal (5120-5759 d�cimal), ou - la police utilis�e pour afficher le
	 * texte est une police contenant des glyphes inuktitut avec son codage
	 * sp�cifique.
	 * 
	 * Si le codage UNICODE est utilis�, un �l�ment de texte HTML peut contenir
	 * du texte dans les plages unicode �lev�es et du texte dans les plages
	 * basses. Il est possible que les caract�res dont les codes se trouvent
	 * dans les plages �l�v�es soient des syllabes inuktitut. Dans l'�tat actuel
	 * des choses, il est pratiquement impossible qu'un �l�ment de texte
	 * contenant du unicode inuktitut contiennent aussi du non-unicode dans une
	 * police inuktitut. On consid�re donc que si un �l�ment de texte contient
	 * du unicode inuktitut, le reste du contenu non unicode n'est pas de
	 * l'inuktitut dans une police inuktitut.
	 */
	public TexteHTML texteEnInuktitut(javax.swing.text.Element element) {
		TexteHTML txtHTML = new TexteHTML(element, this);
		String[] fontFamilies;
		boolean enInuktitut = false;
		Vector morceaux = new Vector();

		try {
			int beg = element.getStartOffset();
			int end = element.getEndOffset();
			String text = getText(beg, end - beg);
			int longu = text.length();
			int i = 0;
			int deb = 0;
			boolean dansInuk = false;
			boolean dansNonInuk = false;
			Inuktitut.MorceauTexte morceau;

			// Si le texte n'est qu'un newline, �a n'a pas
			// d'importance; on retourne 'null'
			if (longu == 1 && text.charAt(0) == 10)
				return null;

			// Y a-t-il des caract�res dans ce
			// texte dans la plage du syllabaire aborig�ne canadien?
			//
			// On place alors dans le vecteur offsets des triplets
			// "inuktitut"/"noninuktitut",d�but,fin
			// qui d�crivent la suite de caract�res dans le texte.
			while (i < longu) {
				if (Syllabics.isInuktitutCharacter(text.charAt(i))) {
					// syllabaire
					enInuktitut = true;
					if (dansInuk) {
						;
					} else if (dansNonInuk) {
						// NOUVEAU MORCEAU DE TEXTE EN INUKTITUT UNICODE SUIVANT
						// UN MORCEAU DE TEXTE PAS EN INUKTITUT
						dansNonInuk = false;
						dansInuk = true;
						morceau = new Inuktitut.MorceauTexte(false, deb, i,
								text.substring(deb, i), null);
						morceaux.add(morceau);
						deb = i;
					} else {
						// PREMIER MORCEAU DE TEXTE - EN INUKTITUT UNICODE
						dansInuk = true;
						deb = i;
					}
				} else {
					if (dansNonInuk) {
						;
					} else if (dansInuk) {
						// NOUVEAU MORCEAU DE TEXTE PAS EN INUKTITUT SUIVANT
						// UN MORCEAU DE TEXTE EN INUKTITUT
						dansInuk = false;
						dansNonInuk = true;
						morceau = new Inuktitut.MorceauTexte(true, deb, i, text
								.substring(deb, i), null);
						morceaux.add(morceau);
						deb = i;
					} else {
						// PREMIER MORCEAU DE TEXTE - PAS EN INUKTITUT
						dansNonInuk = true;
						deb = i;
					}
				}
				i++;
			} /* while */

			// Si du texte en unicode a �t� d�tect�, on ajoute le dernier
			// morceau de texte.
			if (enInuktitut) {
				txtHTML.setCodage(Roman.UNICODE);
				morceau = new Inuktitut.MorceauTexte(dansInuk ? true : false,
						deb, i, text.substring(deb, i), null);
				morceaux.add(morceau);
			}

			// Si aucun code unicode pour l'inuktitut n'a �t� d�tect� dans
			// le texte, on v�rifie si le texte est affich� avec une police
			// pour l'inuktitut. Si c'est le cas, on cr�e un vecteur offsets
			// avec le marqueur "font-family". Il n'est pas besoin d'indiquer
			// le d�but et la fin du texte parce qu'on consid�re alors que tout
			// le texte est en inuktitut. On ajoute � la suite du marqueur le
			// nom de la police.
			else {
				txtHTML.setCodage(Roman.NONUNICODE);
				String font;
				// Trouver la police sp�cifi�e directement
				// pour cet �l�ment de texte
				// fontFamily = transInuk.trouverFontFamily(element);
				fontFamilies = getFont(element);
				font = Font.containsLegacyFont(fontFamilies);
				if (font != null) {
					Inuktitut.fontInuk = font;
					enInuktitut = true;
					// txtHTML.setPolice(fontInuk);
					morceau = new Inuktitut.MorceauTexte(true, beg, end, text,
							Inuktitut.fontInuk);
					String txtUnicode = TransCoder.legacyToUnicode(text,
							Inuktitut.fontInuk);
					morceau.setTexte(txtUnicode);
					morceaux = new Vector();
					morceaux.add(morceau);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		txtHTML.setMorphParts(morceaux);

		return (enInuktitut ? txtHTML : null);

	} // fin de texteEnInuktitut()

	public Object[][] getInuktitutFontsAndPercentages() {
		// TODO Auto-generated method stub
		return null;
	}

}

// ---------------------------- SousHTMLEditorKit ----------------------------

// La m�thode 'HTMLDocument.setParser(HTMLEditorKit.Parser parser)'
// doit �tre appel�e pour indiquer quel parseur doit �tre utilis� par
// la m�thode 'HTMLEditorKit.insertHTML(HTMLDocument doc, int offset,
// String html, int popDepth, int pushDepth, HTML.Tag insertTag)' *
// pour ins�rer du texte HTML dans le document HTMLDocument. Il faut
// d�terminer la valeur de l'argument 'parser' � transmettre.
//
// * Les m�thodes 'HTMLDocument.insertBeforeStart(...)',
// 'HTMLDocument.insertAfterStart(...)', 'HTMLDocument.insertBeforeEnd(...),
// 'HTMLDocument.insertAfterEnd(...)' ne semblent pas bien fonctionner.
// C'est la raison pour laquelle nous utilisons 'HTMLEditorKit.insertHTML(...)'.
//
// La m�thode 'HTMLEditorKit.getParser()' n'est pas publique.
// Elle est prot�g�e. Elle ne peut �tre appel�e qu'� l'int�rieur de
// m�thodes d'une sous-classe de HTMLEditorKit et de m�thodes d'autres
// classes dans le m�me paquetage que HTMLEditorKit. Il faut donc cr�er
// une sous-classe de HTMLEditorKit pour pouvoir appeler 'getParser()'.
//
// Comme on n'en pas besoin ailleurs, il n'est pas n�cessaire que la
// sous-classe soit d�clar�e publique ni qu'elle soit d�clar�e dans un
// fichier s�par�.

class SousHTMLEditorKit extends HTMLEditorKit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Parser myDefaultParser = null;

	SousHTMLEditorKit() {
		super();
	}

	protected HTMLEditorKit.Parser getParser() {
		// if (myDefaultParser == null) {
		// myDefaultParser = new MonParserDelegator();
		// }
		// return myDefaultParser;
		return super.getParser();
	}
}

class BOM {

	static String bom[][] = { { "\u00ef\u00bb\u00bf", "UTF-8" },
			{ "\u00fe\u00ff", "UTF-16BE" }, { "\u00ff\u00fe", "UTF-16LE" } };
	protected int nbChars;
	protected String marker;
	protected String encoding;

	public BOM(String bom, String encoding) {
		marker = bom;
		this.encoding = encoding;
		nbChars = bom.length();
	}

	public BOM() {
		marker = null;
		nbChars = 0;
		encoding = null;
	}

	static protected BOM checkForBOM(File f) {
		BufferedReader r = null;
		BOM bom = new BOM();
		try {
			r = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "ISO-8859-1"));
			bom = checkForBOM(r);
			r.close();
		} catch (FileNotFoundException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		return bom;
	}

	static protected BOM checkForBOM(BufferedReader r) {
		String l;
		try {
			l = r.readLine();
			if (l != null) // Some pages contain nothing!
				for (int i = 0; i < bom.length; i++)
					if (l.startsWith(bom[i][0])) {
						return new BOM(bom[i][0], bom[i][1]);
					}
		} catch (IOException e) {
		}
		return new BOM();
	}

}
