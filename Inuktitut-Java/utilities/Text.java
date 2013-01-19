//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Texte.java
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
// Description: Classe pour un élément de txt d'une page HTML.
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: Texte.java,v 1.1 2009/06/19 19:38:34 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: Texte.java,v $
// Revision 1.1  2009/06/19 19:38:34  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.3  2006/07/18 21:15:28  farleyb
// *** empty log message ***
//
// Revision 1.2  2004/12/10 19:49:10  farleyb
// *** empty log message ***
//
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Première sauvegarde
//
// Revision 1.0  2003-06-25 13:20:00-04  farleyb
// Initial revision
//
// Revision 1.2  2003-04-15 13:32:29-04  farleyb
// <>
//
// Revision 1.1  2003-02-03 14:41:48-05  farleyb
// <>
//
// Revision 1.0  2002-12-03 12:42:00-05  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

package utilities;

import script.Roman;

public class Text {

	String txt;

	public Text(String txt) {
		this.txt = txt;
	}

	public boolean containsUnicodeInuktitut() {
		if (txt == null)
			return false;
		for (int i = 0; i < txt.length(); i++) {
			if (Roman.isInuktitutCharacter(txt.charAt(i)))
				return true;
		}
		return false;
	}

	public boolean containsUnicode() {
		if (txt == null)
			return false;
		for (int i = 0; i < txt.length(); i++) {
			int val = (int) txt.charAt(i);
			if (val > 255)
				return true;
		}
		return false;
	}

	public boolean isNull() {
		if (txt == null)
			return true;
		else
			return false;
	}

}
