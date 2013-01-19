//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Decomposition.java
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
// Description: Classe d�crivant un terme d�compos� en ses diverses
//              parties: base de mot et suffixes.
//
// -----------------------------------------------------------------------

package morph;

import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.Affix;
import data.Base;
import data.Morpheme;

import script.Orthography;

import utilities.Debugging;

// Decomposition:
//    String word
//    MorceauRacine stem:
//        Base racine:
//           ...
//        String terme
//        int position
//        String niveau
//    Object [] morphParts:
//        MorceauAffixe morceau:
//            SurfaceFormOfAffix form
//            

public class Decomposition extends Object implements Comparable {

	String word;
	RootPartOfComposition stem;
	Object[] morphParts;

	// Au moment de la cr�ation d'un objet Decomposition, on d�termine
	// la cha�ne de caract�res � l'int�rieur du mot pour chaque morceau,
	// � partir de la position sauvegard�e dans l'objet MorceauAffixe
	// lors de la d�composition.

	public Decomposition(String word, RootPartOfComposition r, Object[] parts) {
		this.word = word;
		stem = r;
		String origState = stem.arc.startState.id;
		int nextPos = word.length();
		for (int i = parts.length - 1; i >= 0; i--) {
			AffixPartOfComposition m = (AffixPartOfComposition) parts[i];
			int pos = m.getPosition();
			m.setTerme(
				Orthography.orthographyICI(word.substring(pos, nextPos), false) );
			nextPos = pos;
		}
		for (int i=0; i<parts.length; i++) {
			// 'arc' de chaque morceau
			AffixPartOfComposition m = (AffixPartOfComposition) parts[i];
			for (int j=0; j<m.arcs.length; j++) {
			    if (m.arcs[j].destState.id.equals(origState)) {
			        m.arc = m.arcs[j];
			        origState = m.arc.startState.id;
			        break;
			    }
			}
		}
		morphParts = parts;
		//stem.terme = word.substring(0,nextPos);
	}

	public RootPartOfComposition getRootMorphpart() {
		return stem;
	}

	public Object[] getMorphParts() {
		return morphParts;
	}
    
    public void setMorphParts(Object [] parts) {
        morphParts = parts;
    }
    
    public AffixPartOfComposition getLastMorphpart() {
    	if (morphParts.length==0)
    		return null;
    	else
    		return (AffixPartOfComposition)morphParts[morphParts.length-1];
    }

	public int getNbMorphparts() {
		return morphParts.length;
	}

	//	// - Les racines les plus longues en premier
	//	// - Le nombre mininum de morphParts
	//	// - Les racines connues en premier
	//	public int compareTo(Object a) {
	//		int valeurRetour = 0;
	//		Decomposition otherDec = (Decomposition) a;
	//		boolean known = ((Base) stem.getRoot()).known;
	//		boolean otherDecConnue = ((Base) otherDec.stem.getRoot()).known;
	//		if ((known && otherDecConnue) || (!known && !otherDecConnue))
	//			valeurRetour = 0;
	//		else if (known && !otherDecConnue)
	//			valeurRetour = -1;
	//		else if (!known && otherDecConnue)
	//			valeurRetour = 1;
	//		if (valeurRetour == 0) {
	//			Integer lengthOfRoot =
	//				new Integer(((Base) stem.getRoot()).morpheme.length());
	//			Integer lengthOfRootOfOtherDec =
	//				new Integer(
	//					((Base) otherDec.stem.getRoot()).morpheme.length());
	//			valeurRetour = lengthOfRoot.compareTo(lengthOfRootOfOtherDec);
	//			if (valeurRetour == 0) {
	//				Integer nbOfMorphparts = new Integer(morphParts.length);
	//				Integer nbOfMorphpartsOfOtherDec =
	//					new Integer(otherDec.morphParts.length);
	//				valeurRetour = nbOfMorphparts.compareTo(nbOfMorphpartsOfOtherDec);
	//			}
	//		}
	//		return valeurRetour;
	//	} 

//	 - Les racines connues en premier
	// - Les racines les plus longues
	// - Le nombre mininum de morphParts en premier
	public int compareTo(Object obj) {
		int returnValue = 0;
		Decomposition otherDec = (Decomposition) obj;
//		boolean known = ((Base) stem.getRoot()).known;
//		boolean otherDecConnue = ((Base) otherDec.stem.getRoot()).known;
//		if ((known && otherDecConnue) || (!known && !otherDecConnue))
//			returnValue = 0;
//		else if (known && !otherDecConnue)
//			returnValue = -1;
//		else if (!known && otherDecConnue)
//			returnValue = 1;
		if (returnValue == 0) {
			Integer lengthOfRoot =
				new Integer(((Base) stem.getRoot()).morpheme.length());
			Integer lengthOfRootOfOtherDec =
				new Integer(
					((Base) otherDec.stem.getRoot()).morpheme.length());
			returnValue = lengthOfRootOfOtherDec.compareTo(lengthOfRoot);
			if (returnValue == 0) {
				Integer nbOfMorphparts = new Integer(morphParts.length);
				Integer nbOfMorphpartsOfOtherDec = new Integer(otherDec.morphParts.length);
				returnValue = nbOfMorphparts.compareTo(nbOfMorphpartsOfOtherDec);
			}
		}
		return returnValue;
	}

	public boolean isEqualDecomposition(Decomposition dec) {
		if (this.toStr2().equals(dec.toStr2()))
			return true;
		else
			return false;
	}

	// Note: � faire avec des HashSet: plus rapide probablement.
	static Decomposition[] removeMultiples(Decomposition[] decs) {
		if (decs == null || decs.length == 0)
			return decs;
		Vector v = new Vector();
		Vector vc = new Vector();
		v.add(decs[0]);
		vc.add(decs[0].toStr2());
		for (int i = 1; i < decs.length; i++) {
			String c = decs[i].toStr2();
			if (!vc.contains(c)) {
				v.add(decs[i]);
				vc.add(c);
			}
		}
		return (Decomposition[]) v.toArray(new Decomposition[] {
		});
	}
	
	
    // �liminer les d�compositions qui contiennent une suite de suffixes
    // pour laquelle il existe un suffixe compos�, pour ne garder que
    // la d�compositions dans laquelle se trouve le suffixe compos�.
	static Decomposition[] removeCombinedSuffixes(Decomposition decs[]) {
        Object[][] objs = new Object[decs.length][2];
        for (int i = 0; i < decs.length; i++) {
            objs[i][0] = decs[i];
            objs[i][1] = new Boolean(true);
        }
        // Pour chaque d�composition qui contient un suffixe combin�:
        for (int i = 0; i < objs.length; i++) {
            if (((Boolean) objs[i][1]).booleanValue()) {
                // On ne consid�re que les d�compositions qui n'ont pas
                // �t� rejet�es.
                Decomposition dec = (Decomposition) objs[i][0];
                // Morph�mes de cette d�composition
                Vector vParts = new Vector(Arrays.asList(dec.morphParts));
                vParts.add(0,dec.stem);
                // Pour chaque morph�me combin�, trouver celui qui le pr�c�de,
                // celui qui le suit, et v�rifier dans les autres
                // d�compositions retenues si ces deux morph�mes limites
                // contiennent les �l�ments du morph�me combin�.  Si c'est
                // le cas, on rej�te ces d�compositions.
                for (int j = 0; j < vParts.size(); j++) {
                    PartOfComposition morphPart = (PartOfComposition) vParts.elementAt(j);
                    Morpheme morph = morphPart.getMorpheme();
                    String cs[] = null;
                    if (morph != null)
                        cs = morph.getCombiningParts();
                    // Seulement pour les morph�mes combin�s.
                    if (cs != null) {
                        // Trouver les d�compositions qui ont les �l�ments du
                        // suffixe combin� flanqu�s de part et d'autre par les
                        // m�mes morph�mes, et les enlever de la liste.
                        String prec, follow;
                        // Morph�me pr�c�dant le morph�me combin�.
                        if (j == 0)
                            prec = null;
                        else if (j == 1)
                            prec = dec.stem.root.id;
                        else
                            prec = ((PartOfComposition) vParts.elementAt(j - 1)).getMorpheme().id;
                        // Morph�me suivant le morph�me combin�.
                        if (j == vParts.size() - 1)
                            follow = null;
                        else
                            follow = ((PartOfComposition) vParts.elementAt(j + 1)).getMorpheme().id;
                        // V�rifier dans les d�compositions retenues.
                        int k = 0;
                        while (k < objs.length) {
                            // D�compositions retenues seulement.
                            if (((Boolean) objs[k][1]).booleanValue()) {
                                Decomposition deck = (Decomposition) objs[k][0];
                                Object morphPartsk[] = (Object[]) deck.morphParts;
                                Vector vPartsk = new Vector(Arrays.asList(deck.morphParts));
                                vPartsk.add(0,deck.stem);
                                int l = 0;
                                boolean cont = true;
                                boolean inCombined = false;
                                int iCombined = 0;
                                // Analyser chaque morph�me de cette
                                // d�composition pour v�rifier s'il
                                // correspond � un �l�ment du morph�me
                                // combin�.
                                while (l < vPartsk.size() && cont) {
//                                    MorceauAffixe mck = (MorceauAffixe) morphPartsk[l];
//                                    Affix affk = mck.getAffix();
                                    Morpheme morphk = ((PartOfComposition)vPartsk.elementAt(l)).getMorpheme();
                                    if (inCombined) {
                                        // On a d�j� d�termin� qu'un ou
                                        // plusieurs morph�mes corres-
                                        // pondent aux �l�ments du morph�me
                                        // combin�.  V�rifier celui-ci.
//                                        if (affk.id.equals(cs[iCombined])) {
                                        if (morphk.id.equals(cs[iCombined])) {
                                            // C'est aussi un �l�ment du
                                            // morph�me combin�.
                                            iCombined++;
                                            if (iCombined == cs.length) {
                                                // C'est le dernier �l�ment du
                                                // morph�me combin�.  V�rifer
                                                // si le morph�me qui le suit
                                                // est le m�me que le morph�me
                                                // suivant le morph�me combin�.
                                                // Si c'est le cas, on rej�te
                                                // cette d�composition.  De
                                                // toute fa�on, on arr�te cette
                                                // v�rification.
                                                String followk;
                                                if (l == vPartsk.size() - 1)
                                                    followk = null;
                                                else
                                                    followk = ((PartOfComposition) vPartsk.elementAt(l + 1))
                                                            .getMorpheme().id;
                                                if ( (follow==null && followk==null) ||
                                                        (follow!=null && followk!=null && followk.equals(follow)) )
                                                    // rejeter cette d�composition
                                                    objs[k][1] = new Boolean(
                                                            false);
                                                cont = false;
                                            }
                                        } else {
                                            // Ce n'est pas un �l�ment du
                                            // morph�me combin�.  On remet � 0.
                                            inCombined = false;
                                            iCombined = 0;
                                        }
                                    } else {
                                        // On n'a pas encore reconnu un
                                        // morph�me comme premier �l�ment du
                                        // morph�me combin�.  Est-ce que celui-
                                        // ci l'est?
                                        if (morphk.id.equals(cs[iCombined])) {
                                            // Premier �l�ment du morph�me
                                            // combin�.
                                            inCombined = true;
                                            iCombined++;
                                            String preck;
                                            // V�rifier si le morph�me qui le
                                            // pr�c�de est le m�me que le
                                            // morph�me qui pr�c�de le morph�me
                                            // combin�.  Si c'est le cas, on
                                            // continue la v�rification.  Sinon
                                            // on arr�te la v�rification de
                                            // cette d�composition.
                                            if (l == 0)
                                                preck = null;
                                            else if (l == 1)
                                                preck = deck.stem.root.id;
                                            else
                                                preck = ((PartOfComposition) vPartsk.elementAt(l - 1))
                                                        .getMorpheme().id;
                                            if ( (preck == null && prec != null) || 
                                                    (preck != null && prec == null) ||
                                                    (preck != null && prec != null && !preck.equals(prec)))
                                                cont = false;
                                        }
                                    }
                                    l++;
                                }
                            }
                            k++;
                        }
                    }
                }
            }
        }
        Vector v = new Vector();
        for (int i = 0; i < objs.length; i++)
            if (((Boolean) objs[i][1]).booleanValue())
                v.add(objs[i][0]);
        Decomposition ndecs[] = (Decomposition[]) v
                .toArray(new Decomposition[] {});
        return ndecs;
    }
	
	
	/*
	 * --------------------------------------------------------------------
	 * �criture d'une d�composition
	 * --------------------------------------------------------------------
	 */
	
	static String startDelimitor = "{";
	static String endDelimitor = "}";
	static String interDelimitor = ":";
	
	/*
     * {<forme de surface>:<signature du morph�me>}...
	 */
	public String toStr2() {
		StringBuffer sb = new StringBuffer();
		Object[] morphParts = getMorphParts();
		sb.append(stem.toStr());
		for (int j = 0; j < morphParts.length; j++) {
			AffixPartOfComposition ma = (AffixPartOfComposition) morphParts[j];
//			sb.append("|");
			sb.append(ma.toStr());
		}
		return sb.toString();
	}
	
	static public String[] getMeaningsInArrayOfStrings (String decstr, String lang, 
			boolean includeSurface, boolean includeId) {
		DecompositionExpression de = new DecompositionExpression(decstr);
		String mngs[] = de.getMeanings(lang);
		for (int i=0; i<mngs.length; i++) {
			if (includeSurface && includeId)
				mngs[i] = de.parts[i].str+ "---" + mngs[i];
			else if (includeSurface)
				mngs[i] = de.parts[i].surface+ "---" + mngs[i];
			else if (includeId)
				mngs[i] = de.parts[i].morphid+ "---" + mngs[i];
		}
		return mngs;
	}
	
	static public String getMeaningsInString (String decstr, String lang, 
			boolean includeSurface, boolean includeId) {
		DecompositionExpression de = new DecompositionExpression(decstr);
		StringBuffer sb = new StringBuffer();
		String mngs[] = de.getMeanings(lang);
		for (int i=0; i<mngs.length; i++)
			if (includeSurface && includeId)
				sb.append("{").append(de.parts[i].str).append("---").append(mngs[i]).append("}");
			else if (includeSurface)
				sb.append("{").append(de.parts[i].surface).append("---").append(mngs[i]).append("}");
			else if (includeId)
				sb.append("{").append(de.parts[i].morphid).append("---").append(mngs[i]).append("}");
			else
				sb.append("{").append(mngs[i]).append("}");
		return sb.toString();
	}
	
	//----------------------------------------------------------------------------------------------
	/*
     * {<forme de surface>:<signature du morphème>}{...}...
	 */

	static public class DecompositionExpression {
		//
		String decstr;
		String partsStr[];
		DecPart parts[];
		//
		
		public DecompositionExpression (String decstr) {
			this.decstr = decstr;
			partsStr = expr2parts();
			parts = new DecPart[partsStr.length];
			for (int i=0; i<parts.length; i++)
				parts[i] = new DecPart(partsStr[i]);
		}
		
		public String[] getMeanings(String lang) {
			String meanings[] = new String[parts.length];
			for (int i=0; i<parts.length; i++) {
				meanings[i] =
					lang.equals("en")?
							Morpheme.getMorpheme(parts[i].morphid).englishMeaning
							:
								Morpheme.getMorpheme(parts[i].morphid).frenchMeaning;
							meanings[i] = meanings[i].replaceAll(" /", " ");
							meanings[i] = meanings[i].replace("^/", "");
			}
			return meanings;
		}
		
		private String[] expr2parts() {
			Pattern p = Pattern.compile("\\{[^}]+?\\}");
			Matcher mp = p.matcher(decstr);
			Vector v = new Vector();
			int pos=0;
			while (mp.find(pos)) {
				v.add(mp.group());
				pos = mp.end();
			}
			return (String[])v.toArray(new String[]{});
		}
		
		static public class DecPart {
			//
			String str;
			String surface;
			String morphid;
			//
			
			public DecPart (String str) {
				this.str = str;
				Pattern p = Pattern.compile("\\"+startDelimitor+"(.+?)"+"\\"+endDelimitor);
				Matcher m = p.matcher(str);
				m.matches();
				String[] partParts = Pattern.compile(":").split(m.group(1));
				surface = partParts[0];
				morphid = partParts[1];
			}
			
			public DecPart (String terme, String id) {
				surface = terme;
				morphid = id;
				str = startDelimitor + surface + interDelimitor + id + endDelimitor;
			}
		}
		
	}

}
