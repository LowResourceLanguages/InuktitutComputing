//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2003
//           (c) National Research Council of Canada, 2003
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		MorceauAffixe.java
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

import java.util.Vector;

import data.Affix;
import data.SurfaceFormOfAffix;
import data.Morpheme;
import data.VerbEnding;


public class AffixPartOfComposition extends PartOfComposition {

	SurfaceFormOfAffix form = null;
	boolean reflexive = false;

    VerbEnding tv = null;
	AffixPartOfComposition []  multipleMorphparts = null;

	public AffixPartOfComposition(int posAffix, SurfaceFormOfAffix f) {
		term = null;
		position = posAffix;
		form = f;
	}
	
	AffixPartOfComposition(int pos) { // Seulement pour la sous-classe Inchoative
	    position = pos;
	}


	//   public MorceauAffixe copyOf() throws CloneNotSupportedException {
	// 	return (MorceauAffixe)this.clone();
	//     }

	public SurfaceFormOfAffix getForm() {
		return form;
	}
	
	public boolean getReflexive() {
		return reflexive;
	}
	
	public Morpheme getMorpheme() {
	    return form.getAffix();
	}

	public Affix getAffix() {
		return form.getAffix();
	}
    
    public String getType() {
        return form.getAffix().type;
    }

	public VerbEnding getVerbEnding() {
		return tv;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[AffixPartOfComposition: ");
		sb.append("\nterm= ");
		sb.append(term);
		sb.append("\nposition= ");
		sb.append(position);
		sb.append("\nform= ");
		if (form == null)
			sb.append("null");
		else
			sb.append(form.toString());
		sb.append("\nreflexive= ");
		if (reflexive)
			sb.append("true");
		else
			sb.append("false");
		sb.append("\n]");
		return sb.toString();
	}
	
	
	public String toStr() {
		String trm;
		Affix aff = (Affix) form.getAffix();
		if (term != null && term.length() != 0 && term.charAt(term.length()-1)=='*')
			trm = term.substring(0,term.length()-1);
		else
			trm = term;
		return new Decomposition.DecompositionExpression.DecPart(trm,aff.id).str;
	}
    
    /*
     * L'analyse d'un mot r�sulte souvent en une s�rie de d�compositions dont la
     * seule diff�rence r�side dans le dernier morceau, correspondant � diff�rents
     * affixes de m�me type (typiquement terminaison nominale ou verbale) avec
     * une m�me forme de surface.  Par exemple: 'mik' est la forme de surface de
     * 5 terminaisons nominales correspondant � des cas diff�rents avec des
     * actions contextuelles diff�rentes.
     * 
     * On utilise 'multipleMorphparts' pour r�duire le nombre de tableaux
     * d'affichage des r�sultats lors de la d�finition (d�composition) d'un mot.
     */
    public void setMultipleMorphparts(Vector morphParts) {
        multipleMorphparts = (AffixPartOfComposition[])morphParts.toArray(new AffixPartOfComposition[]{});
    }
    
    public AffixPartOfComposition[] getMultipleMorphparts() {
        return multipleMorphparts;
    }
	

}
