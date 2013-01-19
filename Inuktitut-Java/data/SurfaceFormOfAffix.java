//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		SurfaceFormOfAffix.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation:	
//
// Description: SurfaceFormOfAffix est la classe des objets qui sont enregistr�s
//              dans les MorceauAffixe d'une Decomposition.
//
// -----------------------------------------------------------------------

//                                  ***

// -------------------//Information RCS Information\\---------------------
// $Id: SurfaceFormOfAffix.java,v 1.1 2009/06/19 19:38:11 farleyb Exp $
//
// Commentaires RCS---------------------------------------RCS Log Messages
//
// $Log: SurfaceFormOfAffix.java,v $
// Revision 1.1  2009/06/19 19:38:11  farleyb
// Nouvelle version de Inuktitut Juin 2009
//
// Revision 1.7  2009/02/10 15:22:46  farleyb
// *** empty log message ***
//
// Revision 1.6  2006/12/11 22:05:57  farleyb
// *** empty log message ***
//
// Revision 1.5  2005/01/28 21:44:11  farleyb
// *** empty log message ***
//
// Revision 1.4  2004/04/27 13:07:47  farleyb
// Avant de tenter de corriger le probl�me du r�pertoire
// applications
//
// Revision 1.3  2003/12/09 22:34:35  farleyb
// 10 d�cembre 2003 - 71% succ�s sur Hansard
//
// Revision 1.2  2003/12/04 22:05:30  farleyb
// 5-dec-2003
// ajout des adverbes d�monstratifs et de leurs terminaisons
// ajout du code pour les analyser
//
// Revision 1.1  2003/10/10 06:01:10  desiletsa
// Premi�re sauvegarde
//
// Revision 1.0  2003-06-25 13:19:51-04  farleyb
// Initial revision
//
// Revision 1.0  2003-05-12 11:00:06-04  farleyb
// Initial revision
//
//
// -------------------\\Information RCS Information//---------------------
//
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


package data;

import data.LinguisticDataAbstract;


public class SurfaceFormOfAffix {
    public String form;
    public String key;
//    public Integer index;
    public String uniqueId;
    public String type;
    public String context;
    public Action action1;
    public Action action2;

    public SurfaceFormOfAffix (String formeAffixe, String keyOfAffix, 
//			  Integer indexOfAffix,
			  String id,
			  String typeOfAffixe, String context,
			  Action a1, Action a2) {
	form = formeAffixe;
	key = keyOfAffix;
//	index = indexAffixe;
	uniqueId = id;
	type = typeOfAffixe;
	this.context = context;
	action1 = a1;
	action2 = a2;
    }

private SurfaceFormOfAffix() {
}

    public Affix getAffix() {
        return LinguisticDataAbstract.getAffix(uniqueId);
        }


    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("[SurfaceFormOfAffix:");
	sb.append("\nform: "+form);
	sb.append("\ntype: "+type);
	sb.append("\nkey: "+key);
//	sb.append("\nindex: "+index.toString());
	sb.append("\ncontext: "+context);
	sb.append("\naction1: "+action1.toString());
	sb.append("\naction2: "+action2);
	sb.append("]");
	return sb.toString();
    }
    

}
