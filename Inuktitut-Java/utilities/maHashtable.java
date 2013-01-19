//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		maHashtable.java
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
// Description: Version privée de Hashtable.
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: maHashtable.java,v 1.1 2009/06/19 19:38:32 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: maHashtable.java,v $
// Revision 1.1  2009/06/19 19:38:32  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Première sauvegarde
//
// Revision 1.0  2003-06-25 13:19:53-04  farleyb
// Initial revision
//
// Revision 1.0  2002-12-03 12:30:22-05  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


package utilities;

import java.util.*;

public class maHashtable extends Hashtable {

	public maHashtable() {
	    super();
	}

	public Object put(Object key, Object data) {
	    Vector d;
	    Object deja = get(key);
	    if (deja == null) {
		d = new Vector();
	    }
	    else {
		d = (Vector)deja;
	    }
	    d.add(data);
	    return super.put(key,d);
	}

    }
