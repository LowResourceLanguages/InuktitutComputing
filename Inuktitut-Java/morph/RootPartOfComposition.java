//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2003
//           (c) National Research Council of Canada, 2003
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		MorceauRacine.java
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
// Description: 
//
// -----------------------------------------------------------------------


package morph;

import data.Base;
import data.Morpheme;

public class RootPartOfComposition extends PartOfComposition {

    Base root;
    String transitivity;


    public RootPartOfComposition(String t, Base b, String trans, Graph.Arc arc) {
	term = t;
	root = b;
    transitivity = trans;
    this.arc = arc;
    }



//    public MorceauRacine copyOf() throws CloneNotSupportedException {
// 	return (MorceauRacine)this.clone();
//     }

    public Morpheme getMorpheme() {
        return root;
    }

    public Base getRoot() {
	return root;
    }
    
    public String getTransitivity() {
        return transitivity;
    }


    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("[RootPartOfComposition: ");
	sb.append(", term=");
	sb.append(term);
    sb.append(", root=");
    sb.append(root);
    sb.append(", transitivity=");
    sb.append(transitivity);
	sb.append("]");
	return sb.toString();
    }

    
	public String toStr() {
		return new Decomposition.DecompositionExpression.DecPart(term,root.id).str;
	}
}
