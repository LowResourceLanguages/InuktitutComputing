// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		TerminaisonVerbale.java
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
// Description: Classe TerminaisonVerbale
//
// -----------------------------------------------------------------------

package data;

import java.io.ByteArrayInputStream;
import java.util.*;

import data.constraints.Conditions;
import data.constraints.Imacond;
import data.constraints.ParseException;

import utilities.Debugging;

public class VerbEnding extends Affix {
	//
	String mode;
	String spec = null;
	String subjPers = null;
	String subjNumber = null;
	String objPers = null;
	String objNumber = null;
	String sameSubject = null;
	String posneg = null;
	String tense = null;
	
	static Class conditionClass = null;
	static public Hashtable hash = new Hashtable();

	static String[] modes = {"caus", "cond", "dec", "dub", "freq", "ger", "imp", "int", "part"};
	static String[] numbers = {"d", "p", "s"};
	static String[] booleans = {"false", "true"};
	//
	
	//---------------------------------------------------------------------------------------------------------
	public VerbEnding() {
	}
	
	public VerbEnding(HashMap v) {
		morpheme = (String) v.get("morpheme");
		Debugging.mess("VerbEnding/1", 1, "morpheme= " + morpheme);
		type = (String) v.get("type");
		mode = (String) v.get("mode");
//		spec = (String) v.get("spec");
		subjPers = (String) v.get("perSubject");
		subjNumber = (String) v.get("numbSubject");
//		if (spec.equals("sp")) {
			objPers = (String) v.get("perObject");
			objNumber = (String) v.get("numbObject");
//		}
        if (objPers != null)
            spec = "sp";
        else
            spec = "nsp";
		if (mode.equals("part")) {
			sameSubject = (String) v.get("sameSubject");
			posneg = (String) v.get("posneg");
			tense = (String) v.get("tense");
		}
		dbName = (String) v.get("dbName");
		tableName = (String) v.get("tableName");

		makeMeanings();

		// D�veloppement des diverses surfaceFormsOfAffixes associ�es aux 4 contextes
		// voyelle, t, k et q et � leurs actions.

		// Apr�s Voyelle
		String form = (String) v.get("V-form");
		String act1 = (String) v.get("V-action1");
		String act2 = (String) v.get("V-action2");
		makeFormsAndActions("V", morpheme, form, act1, act2);

		// Apr�s 't'
		form = (String) v.get("t-form");
		act1 = (String) v.get("t-action1");
		act2 = (String) v.get("t-action2");
		makeFormsAndActions("t", morpheme, form, act1, act2);

		// Apr�s 'k'
		form = (String) v.get("k-form");
		act1 = (String) v.get("k-action1");
		act2 = (String) v.get("k-action2");
		makeFormsAndActions("k", morpheme, form, act1, act2);

		// Apr�s 'q'
		form = (String) v.get("q-form");
		act1 = (String) v.get("q-action1");
		act2 = (String) v.get("q-action2");
		makeFormsAndActions("q", morpheme, form, act1, act2);

		String cs = (String) v.get("condPrecSpecific");
		if (cs != null) {
            try {
                preCondition = (Conditions) new Imacond(
                        new ByteArrayInputStream(cs.getBytes())).ParseCondition();
            } catch (ParseException e) {
            }
        }

		String srcs = (String) v.get("sources");
		if (srcs != null) {
			StringTokenizer st2 = new StringTokenizer(srcs);
			sources = new String[st2.countTokens()];
			int n = 0;
			while (st2.hasMoreTokens()) {
				sources[n++] = st2.nextToken();
			}
		}
		setAttrs();
	}

	//---------------------------------------------------------------------------------------------------------
	public void addToHash(String key, Object obj) {
	    hash.put(key,obj);
	}

	// Signature des terminaisons verbales:
	// <type>-<mode>-<subjPers><subjNumber>[-<objPers><objNumber>][-<tense>]
	public String getSignature() {
		StringBuffer sb = new StringBuffer();
		sb.append(type);
		sb.append("-");
		sb.append(mode);
		sb.append("-");
		sb.append(subjPers);
		sb.append(subjNumber);
		if (objPers != null) {
			sb.append("-");
			sb.append(objPers);
			sb.append(objNumber);
		}
		if (mode.equals("part")) {
			if (tense != null) {
			    sb.append("-");
			    sb.append(tense);
			}
		}
		return sb.toString();
	}

	public String getTransitivityConstraint() {
	    if (spec.equals("nsp"))
	        return "i";
	    else
	        return "t";
	}
	
	public String[] getCombiningParts() {
	    return null;
	}
	
	//---------------------------------------------------------------------------------------------------------
	boolean agreeWithTransitivity(String trans) {
	    return true;
	}
	
//	void rendreReflexif() {
//		objPers = subjPers;
//		objNumber = subjNumber;
//		spec = "sp";
//		if (subjPers.equals("1"))
//			if (subjNumber.equals("s")) {
//				frenchMeaning = frenchMeaning + " ... moi-même";
//				englishMeaning = englishMeaning + " ... myself";
//			} else {
//				frenchMeaning = frenchMeaning + " ... nous-mêmes";
//				englishMeaning = englishMeaning + " ... ourselves";
//			}
//		else if (subjPers.equals("2"))
//			if (subjNumber.equals("s")) {
//				frenchMeaning = frenchMeaning + " ... toi-même";
//				englishMeaning = englishMeaning + " ... yourself";
//			} else {
//				frenchMeaning = frenchMeaning + " ... vous-mêmes";
//				englishMeaning = englishMeaning + " ... yourselves";
//			}
//		else if (subjPers.equals("3"))
//			if (subjNumber.equals("s")) {
//				frenchMeaning = frenchMeaning + " ... lui/elle-même";
//				englishMeaning = englishMeaning + " ... him/her/itself";
//			} else {
//				frenchMeaning = frenchMeaning + " ... ils/elles-mêmes";
//				englishMeaning = englishMeaning + " ... themselves";
//			}
//	}
	
//    Vector getIdsOfCompositesWithThisRoot() {
//        return null;
//    }

	void setAttrs() {
		setAttributes();
		setId();
	}
	
	void setAttributes() {
		HashMap tvAttrs = new HashMap();
		tvAttrs.put("mode", mode);
		tvAttrs.put("spec", spec);
		tvAttrs.put("subjPers", subjPers);
		tvAttrs.put("subjNumber", subjNumber);
		tvAttrs.put("objPers", objPers);
		tvAttrs.put("objNumber", objNumber);
		tvAttrs.put("sameSubject", sameSubject);
		tvAttrs.put("posneg", posneg);
		tvAttrs.put("tense", tense);
		super.setAttributes(tvAttrs);
	}

	//---------------------------------------------------------------------------------------------------------
	// D�velopper les sens en fran�ais et en anglais des terminaisons
	// verbales � partir des donn�es suivantes: mode, sp�cificit�,
	// personne et nombres du sujet et de l'objet.

	private void makeMeanings() {
		StringBuffer frenchMeaning1 = new StringBuffer();
		StringBuffer englishMeaning1 = new StringBuffer();

		if (mode.equals("dec") || mode.equals("ger")) {
			frenchMeaning1.append("déclaration: ");
			englishMeaning1.append("declaration: ");
		} else if (mode.equals("int")) {
			if (subjPers.equals("3") || subjPers.equals("4")) {
				frenchMeaning1.append("question: est-ce qu'");
				if (subjNumber.equals("s"))
					englishMeaning1.append("question: does ");
				else
					englishMeaning1.append("question: do ");
			} else {
				frenchMeaning1.append("question: est-ce que ");
				englishMeaning1.append("question: do ");
			}
		} else if (mode.equals("imp")) {
			frenchMeaning1.append("ordre: ");
			englishMeaning1.append("order: ");
		} else if (mode.equals("part")) {
			if (tense == null) {
				frenchMeaning1.append("part: ");
				englishMeaning1.append("part: ");
			} else if (tense.equals("prespas")) {
				frenchMeaning1.append("part. présent/passé: ");
				englishMeaning1.append("part. present/past: ");
			} else if (tense.equals("fut")) {
				frenchMeaning1.append("part. futur: ");
				englishMeaning1.append("part. future: ");
			}

			if (subjPers.equals("3") || subjPers.equals("4"))
				frenchMeaning1.append("alors/pendant qu'");
			else
				frenchMeaning1.append("alors/pendant que ");
			englishMeaning1.append("while ");
		} else if (mode.equals("caus")) {
			if (subjPers.equals("3") || subjPers.equals("4"))
				frenchMeaning1.append("causal: parce qu'");
			else
				frenchMeaning1.append("causal: parce que ");
			englishMeaning1.append("becausative: because ");
		} else if (mode.equals("cond")) {
			if ((subjPers.equals("3") || subjPers.equals("4"))
				&& subjNumber.equals("s"))
				frenchMeaning1.append("conditionnel: s'");
			else
				frenchMeaning1.append("conditionnel: si ");
			englishMeaning1.append("conditional: if ");
		} else if (mode.equals("dub")) {
			if ((subjPers.equals("3") || subjPers.equals("4"))
				&& subjNumber.equals("s"))
				frenchMeaning1.append("dubitatif: s'");
			else
				frenchMeaning1.append("dubitatif: si ");
			englishMeaning1.append("dubitative: whether ");
		} else if (mode.equals("freq")) {
			if (subjPers.equals("3") || subjPers.equals("4"))
				frenchMeaning1.append("fréquentatif: chaque fois qu' / lorsqu'");
			else
				frenchMeaning1.append("fréquentatif: chaque fois que / lorsque ");
			englishMeaning1.append("frequentative: whenever ");
		}

		if (subjPers.equals("1")) {
			if (subjNumber.equals("s")) {
				frenchMeaning1.append("je ");
				englishMeaning1.append("I ");
			} else if (subjNumber.equals("d")) {
				frenchMeaning1.append("nous (deux) ");
				englishMeaning1.append("we (two) ");
			} else if (subjNumber.equals("p")) {
				frenchMeaning1.append("nous (plusieurs) ");
				englishMeaning1.append("we (many) ");
			}
		} else if (subjPers.equals("2")) {
			if (subjNumber.equals("s")) {
				frenchMeaning1.append("tu ");
				englishMeaning1.append("you ");
			} else if (subjNumber.equals("d")) {
				frenchMeaning1.append("vous (deux) ");
				englishMeaning1.append("you (two) ");
			} else if (subjNumber.equals("p")) {
				frenchMeaning1.append("vous (plusieurs) ");
				englishMeaning1.append("you (many) ");
			}
		} else if (subjPers.equals("3") || subjPers.equals("4")) {
			if (subjNumber.equals("s")) {
				frenchMeaning1.append("il/elle ");
				englishMeaning1.append("he/she/it ");
			} else if (subjNumber.equals("d")) {
				frenchMeaning1.append("ils/elles (deux) ");
				englishMeaning1.append("they (two) ");
			} else if (subjNumber.equals("p")) {
				frenchMeaning1.append("ils/elles (plusieurs) ");
				englishMeaning1.append("they (many) ");
			}
		}
		if (mode.equals("part") && posneg.equals("neg")) {
			frenchMeaning1.append("ne ");
			englishMeaning1.append("not ");
		}
		frenchMeaning1.append("...");
		englishMeaning1.append("...");
		if (mode.equals("part") && posneg.equals("neg"))
			frenchMeaning1.append(" pas ");

		if (spec.equals("sp")) {
			if (objPers.equals("1")) {
				if (objNumber.equals("s")) {
					frenchMeaning1.append("moi");
					englishMeaning1.append("me");
				} else if (objNumber.equals("d")) {
					frenchMeaning1.append("nous (deux)");
					englishMeaning1.append("us (two)");
				} else if (objNumber.equals("p")) {
					frenchMeaning1.append("nous (plusieurs)");
					englishMeaning1.append("us (many)");
				}
			} else if (objPers.equals("2")) {
				if (objNumber.equals("s")) {
					frenchMeaning1.append("toi");
					englishMeaning1.append("you");
				} else if (objNumber.equals("d")) {
					frenchMeaning1.append("vous (deux)");
					englishMeaning1.append("you (two)");
				} else if (objNumber.equals("p")) {
					frenchMeaning1.append("vous (plusieurs)");
					englishMeaning1.append("you (many)");
				}
			} else if (objPers.equals("3")) {
				if (objNumber.equals("s")) {
					frenchMeaning1.append("lui/elle");
					englishMeaning1.append("him/her/it");
				} else if (objNumber.equals("d")) {
					frenchMeaning1.append("eux/elles (deux) ");
					englishMeaning1.append("them (two) ");
				} else if (objNumber.equals("p")) {
					frenchMeaning1.append("eux/elles (plusieurs) ");
					englishMeaning1.append("them (many) ");
				}
			}
		}

		if (mode.equals("int")) {
			frenchMeaning1.append("?");
			englishMeaning1.append("?");
		}

		frenchMeaning = frenchMeaning1.toString();
		englishMeaning = englishMeaning1.toString();
	}

	//---------------------------------------------------------------------------------------------------------
	public String showData() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n[VerbEnding: morpheme= " + morpheme + "\n");
		sb.append("type= " + type + "\n");
		sb.append("mode= " + mode + "\n");
		if (mode.equals("part")) {
			sb.append("sameSubject= " + sameSubject + "\n");
			sb.append("posneg= " + posneg + "\n");
			sb.append("tense= " + tense + "\n");
		}
		sb.append("spec= " + spec + "\n");
		sb.append("subjPers= " + subjPers + "\n");
		sb.append("subjNumber= " + subjNumber + "\n");
		sb.append("objPers= " + objPers + "\n");
		sb.append("objNumber= " + objNumber + "\n");
		sb.append(super.showData());
//		sb.append(
//			"precedingSpecificConditions= "
//				+ Util.array2string(
//					precedingSpecificConditions)
//				+ "\n");
        if (preCondition != null) {
            sb.append("precedingSpecificCondition= "+preCondition.toString()+"\n");
        }
    	sb.append("dbName= "+dbName+"\n");
    	sb.append("tableName= "+tableName+"\n");
		sb.append("sources= ");
		if (sources == null)
			sb.append(sources);
		else
			for (int n = 0; n < sources.length; n++)
				sb.append(sources[n] + " ");
		sb.append("]\n");
		return sb.toString();
	}


}
