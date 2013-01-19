package documents;

import fonts.Font;
import html.HtmlEntities;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.lobobrowser.html.domimpl.AttrImpl;
import org.lobobrowser.html.domimpl.ElementImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.lobobrowser.html.style.CSS2PropertiesImpl;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import script.TransCoder;


public class NRC_HTMLDocumentByCobra_Kativik extends NRC_HTMLDocumentByCobra {

	private static Logger LOG = Logger
			.getLogger(NRC_HTMLDocumentByCobra_Kativik.class);

	public NRC_HTMLDocumentByCobra_Kativik(String content, URL url, String enc)
			throws Exception {
		super(content, url, enc);
	}

	public void toUnicode(boolean aipaitai) {
		this.documentUnicode = (HTMLDocumentImpl) document.cloneNode(true);
		Vector parentsToUpdate = new Vector();
		toUnicodeDocument(documentUnicode, aipaitai, parentsToUpdate);
		LOG.debug("Nb. parents = "+parentsToUpdate.size());
	}

	public void toUnicodeDocument(HTMLDocumentImpl doc, boolean aipaitai, Vector parentsToUpdate) {
		NodeImpl[] nodes = doc.getChildrenArray();
		toUnicodeDocumentNodes(nodes, aipaitai, parentsToUpdate);
		updateParents(parentsToUpdate);
	}

	private void toUnicodeDocumentNodes(NodeImpl[] nodes, boolean aipaitai, Vector parentsToUpdate) {
		for (int i = 0; i < nodes.length; i++) {
			NodeImpl node = nodes[i];
			if (node.getNodeType() == NodeImpl.TEXT_NODE) {
				toUnicodeDocumentTextNode(node, aipaitai, parentsToUpdate);
			} else if (node.getChildrenArray() != null) {
				toUnicodeDocumentNodes(node.getChildrenArray(), aipaitai, parentsToUpdate);
			}
		}
	}

	private void toUnicodeDocumentTextNode(NodeImpl node, boolean aipaitai, Vector parentsToUpdate) {
		String text;
		Node parentNode = node.getParentNode();
		String parentNodeName = parentNode.getNodeName().toLowerCase();
		// --------------
		// TEXTE
		// --------------
		if (!parentNodeName.equals("style") && !parentNodeName.equals("script")
				&& !parentNodeName.equals("#comment")) {
			String unicodeTextUtf8 = null;
			text = node.getNodeValue();
			String txttmp = HtmlEntities.entityToChar(text);
			try {
				byte bytes[] = text.getBytes("utf-8");
				unicodeTextUtf8 = new String(bytes,"iso-8859-1");
			} catch (UnsupportedEncodingException e2) {
			}
			// text = text.replaceAll("&dagger;", "\u2020");
			/*
			 * Trouver la police spécifiée pour cet élément de texte. Si c'est
			 * une police inuktitut archaïque telle que Nunacom ou Prosyl,
			 * enregistrer les 3 premiers mots de cet élément avec le nom de la
			 * police.
			 */
			LOG.debug("node text = "+text);
//			for (int i=0; i<text.length(); i++)
//				System.out.println("node text["+i+"] = "+text.codePointAt(i));
			Object[] fontFamiliesAndParent = getFontsAndParent(node);
			if (fontFamiliesAndParent != null) {
				String[] fontFamilies = (String[]) fontFamiliesAndParent[0];
				Node parentWithAipai = (Node) fontFamiliesAndParent[1];
				String font = Font.containsLegacyFont(fontFamilies);
				LOG.debug("font = " + font);
				LOG.debug("text = " + text);
				LOG.debug("txttmp = " + txttmp);
				LOG.debug("parent = "+parentWithAipai.toString());
				if (font != null) {
					String unicodeText = TransCoder.legacyToUnicode(txttmp,
							font, aipaitai);
					LOG.debug("unicode = " + unicodeText);
					byte[] bs = null;
					try {
						bs = unicodeText.getBytes("utf-8");
					} catch (UnsupportedEncodingException e) {
					}
					try {
						unicodeTextUtf8 = new String(bs,"iso-8859-1");
					} catch (UnsupportedEncodingException e1) {
					}
					NodeImpl copyTextNode = (NodeImpl) node.cloneNode(false);
					copyTextNode.setNodeValue(unicodeTextUtf8);
					NamedNodeMap parentAttrs = parentWithAipai.getAttributes();
					Node classAttr = parentAttrs.getNamedItem("class");
					Node styleAttr = parentAttrs.getNamedItem("style");
					if (classAttr != null) {
						if (classAttr.getNodeValue().toLowerCase().equals("aipai")) {
							if ( !parentsToUpdate.contains(parentWithAipai) )
								parentsToUpdate.add(parentWithAipai);
							LOG.debug("class - "+unicodeTextUtf8);
							node.setNodeValue(unicodeTextUtf8);
							LOG.debug("node set: "+node.getNodeValue());
						}
					}
					else if (styleAttr != null) {
						CSS2PropertiesImpl properties = ((HTMLElementImpl) parentWithAipai)
							.getCurrentStyle();
						if (properties != null) {
							String ff = properties.getFontFamily();
							String[] ffs = ff.split(",\\s*");
							String fnt = Font.containsLegacyFont(ffs);
							if (fnt.toLowerCase().equals("aipainunavik") || fnt.toLowerCase().equals("aipainuna")) {
								if ( !parentsToUpdate.contains(parentWithAipai) )
									parentsToUpdate.add(parentWithAipai);
								node.setNodeValue(unicodeTextUtf8);
							}
						}
					}
					else
						{
//						ElementImpl embeddingNode = (ElementImpl) this.documentUnicode
//								.createElement("SPAN");
//						AttrImpl classAtt = new AttrImpl("class");
//						classAtt.setNodeValue("unicode_syllabic");
//						embeddingNode.setAttributeNode(classAtt);
//						embeddingNode.appendChild(copyTextNode);
//						LOG.debug("remplacement de "+node.toString());
//						node.getParentNode().replaceChild(embeddingNode, node);
						if ( !parentsToUpdate.contains(parentWithAipai) )
							parentsToUpdate.add(parentWithAipai);
						node.setNodeValue(unicodeTextUtf8);
					}
				}
				else {
					node.setNodeValue(unicodeTextUtf8);					
				}
			}
			else {
				node.setNodeValue(unicodeTextUtf8);		
			}
		}
	}

	public static Object[] getFontsAndParent(NodeImpl node) {
		Object[] fontsAndParent = getFontsAndParentA(node);
		return fontsAndParent;
	}

	static Object[] getFontsAndParentA(NodeImpl node) {
		if (node == null) {
			return null;
		}
		if (node.getNodeType() == NodeImpl.TEXT_NODE)
			return getFontsAndParentA((NodeImpl) node.getParentNode());
		String fontFamiliesFromProperties = null;
		if (node.getNodeName().toLowerCase().equals("font")) {
			Node faceNode = node.getAttributes().getNamedItem("face");
			if (faceNode != null)
				fontFamiliesFromProperties = faceNode.getNodeValue();
		}
		if (fontFamiliesFromProperties == null && node != null
				&& node instanceof HTMLElementImpl) {
			CSS2PropertiesImpl properties = ((HTMLElementImpl) node)
					.getCurrentStyle();
			if (properties != null)
				fontFamiliesFromProperties = properties.getFontFamily();
		}
		if (fontFamiliesFromProperties == null)
			return getFontsAndParentA((NodeImpl) node.getParentNode());
		String[] fontFamilies = fontFamiliesFromProperties.split(",\\s*");
		return new Object[] { fontFamilies, node };
	}

	public void write() {
		LOG.debug("");
		PrintStream out = null;
		try {
			out = new PrintStream(System.out, false, "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		Node n = this.documentUnicode.getFirstChild();
		String outer = ((HTMLElementImpl) n).getOuterHTML();
		out.print(outer);
	}
	
	public void write(ByteArrayOutputStream baos) {
		LOG.debug("");
		PrintStream out = null;
		try {
			out = new PrintStream(baos, false, "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		Node n = this.documentUnicode.getFirstChild();
		String outer = ((HTMLElementImpl) n).getOuterHTML();
		out.print(outer);
	}
	
	public void updateParents (Vector parentsToUpdate) {
		for (int i=0; i<parentsToUpdate.size(); i++) {
			Node parent = (Node)parentsToUpdate.get(i);
			NamedNodeMap parentAttrs = parent.getAttributes();
			Node classAttr = parentAttrs.getNamedItem("class");
			Node styleAttr = parentAttrs.getNamedItem("style");
			String parentName = parent.getNodeName();
			if (classAttr != null) {
				if (classAttr.getNodeValue().toLowerCase().equals("aipai")) {
					((ElementImpl)parent).setAttribute("class", "aipai_unicode");
				}
			}
			if (styleAttr != null) {
				CSS2PropertiesImpl properties = ((HTMLElementImpl) parent)
						.getCurrentStyle();
				if (properties != null) {
					String ff = properties.getFontFamily();
					String[] ffs = ff.split(",\\s*");
					String fnt = Font.containsLegacyFont(ffs);
					if (fnt.toLowerCase().equals("aipainunavik")
							|| fnt.toLowerCase().equals("aipainuna")) {
						// Enlever font-family du style
						String style = ((ElementImpl)parent).getAttribute("style");
						LOG.debug("(1) style = "+style);
						Pattern pat = Pattern.compile("(;\\s?)?font-family:\\s*\\S+",Pattern.CASE_INSENSITIVE);
						Matcher mpat = pat.matcher(style);
						if (mpat.find()) {
							style = style.substring(0,mpat.start())+style.substring(mpat.end());
							LOG.debug("(2) style = "+style);
							if (style.equals(""))
								((ElementImpl)parent).removeAttribute("style");
							else
								((ElementImpl)parent).setAttribute("style", style);
							((ElementImpl)parent).setAttribute("class", "aipai_unicode");
						}
					}
				}
			}
			if (parentName.toLowerCase().equals("font")) {
				((ElementImpl)parent).removeAttribute("face");
				((ElementImpl)parent).setAttribute("class", "aipai_unicode");
			}
		}
	}

}
