//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		NRC_HTMLTag.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	9 avril 2002 / April 9, 2002
//
// Description: Définition d'une extension de la classe HTML.Tag pour
//              permettre la création d'un HTML.Tag à partir d'une
//              chaîne par la méthode de construction HTML.Tag(String s)
//              qui est protégée.  Ce stratagème permet de contourner
//              cette protection.
//
// -----------------------------------------------------------------------


package documents;

//import javaswing.javax.swing.text.html.*;
import javax.swing.text.html.*;

public class NRC_HTMLTag extends HTML.Tag {

    public NRC_HTMLTag(String tagName) {
	super(tagName);
    }

    public static final HTML.Tag TBODY = new NRC_HTMLTag("tbody");
    public static final HTML.Tag LABEL = new NRC_HTMLTag("label");
//  public static final HTML.Tag PHP = new NRC_HTMLTag("?php");
    public static final HTML.Tag PHP = new HTML.UnknownTag("?php");
    public static final HTML.Tag NOINDEX = new NRC_HTMLTag("noindex");

}
