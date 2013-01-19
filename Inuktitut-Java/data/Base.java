// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Base.java
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
// Description: Classe Base
//
// -----------------------------------------------------------------------

package data;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.html;

import org.apache.log4j.Logger;

import utilities.MonURLDecoder;
import utilities1.Util;

import data.constraints.Conditions;
import data.constraints.Imacond;
import data.constraints.ParseException;
import data.LinguisticDataAbstract;
import data.VerbWord;
import script.TransCoder;

public class Base extends Morpheme {
	//
    String variant = null;
    // originalMorpheme:
    //  If a morpheme has various spellings, these are contained in
    //  the field 'variante' of the database table.
    String originalMorpheme = null;
    String nature = null;
    Boolean known = new Boolean(true);
    String transitivity = null; // for verbs only
    String transinfix = null; // for verbs only
    String intransinfix = null; // for verbs only
    String antipassive = null; // for verbs only
    String number = null;
    String subtype = null;
    String source = null;
    String compositionRoot = null;
    
	private String transitiveMeaning_e = null;
	private String passiveMeaning_e = null;
	private String reflexiveMeaning_e = null;
	private String resultMeaning_e = null;
	private String transitiveMeaning_f = null;
	private String passiveMeaning_f = null;
	private String reflexiveMeaning_f = null;
	private String resultMeaning_f = null;
    
    private Vector idsOfCompositesWithThisRoot = null;
    
	static public Hashtable hash = new Hashtable();
	//
	
	public Base(HashMap v) {
		makeRoot(v);
	}
		
	public Base() {
	}

	//-----------------------------------------------------------------------------------------------
	private void makeRoot(HashMap v) {
		getAndSetBaseAttributes(v);
		variant = (String) v.get("variant");
		originalMorpheme = (String)v.get("originalMorpheme");
		nb = (String)v.get("nb");
		if (nb==null || nb.equals(""))
			nb = "1";
		num = new Integer(nb);
		type = (String) v.get("type");
		number = (String) v.get("number");
		if (number==null || number.equals(""))
		    number = "s";
		antipassive = (String) v.get("antipassive");
		transinfix = (String) v.get("transSuffix");
		intransinfix = (String) v.get("intransSuffix");
		transitivity = (String) v.get("transitivity");
        cf = (String)v.get("cf");
        if (cf != null && !cf.equals("")) cfs = cf.split(" ");
        dialect = (String)v.get("dialect");
		nature = (String)v.get("nature");
        source = (String) v.get("source");
		if (source != null && !source.equals(""))
			sources = source.split(" ");
		String cs = (String) v.get("condOnNext");
//		StringTokenizer st =
//			(cs == null) ? new StringTokenizer("") : new StringTokenizer(cs);
//		if (cs != null)
//		    nextConds = new Vector();
//		int k = 0;
//		while (st.hasMoreTokens()) {
//		    String nextToken = st.nextToken();
//		    MorphemeCondition condition = null;
//		        condition = new MorphemeCondition(nextToken);
//		    nextConds.add(condition);
//		}
        if (cs != null && !cs.equals(""))
            try {
                nextCondition = (Conditions) new Imacond(
                        new ByteArrayInputStream(cs.getBytes())).ParseCondition();
            } catch (ParseException e) {
            }

		// Racine de composition pour les racines duelles et plurielles
		compositionRoot = (String)v.get("compositionRoot");
		subtype = (String)v.get("subtype");

        String comb = (String) v.get("combination");
		if (comb != null && !comb.equals("")) {
			combinedMorphemes = comb.split("[+]");
			if (combinedMorphemes.length < 2) {
//				System.out.println("combinaison avec problème: '" + comb + "' [" + morpheme + "]");
				combinedMorphemes = null;
			} else {
				String rootId = combinedMorphemes[0];
				// Attention!!!
				// This root should already have been created. Let's get its
				// list of idsOfCompositeWithThisRoot.
				Base b = LinguisticDataAbstract.getBase(rootId);
				if (b != null) {
					Vector vids = b.idsOfCompositesWithThisRoot;
					if (vids == null)
						vids = new Vector();
					vids.add(comb);
					b.setIdsOfCompositesWithThisRoot(vids);
				}
			}
		}
		setAttrs();
	}

	//-------------------------------------------------------------------------------------------------------
	public void addToHash(String key, Object obj) {
	    hash.put(key,obj);
	}
    
	public String getSignature() {
	    if (originalMorpheme != null)
	        return new Morpheme.Id(originalMorpheme).signature;
	    else
	        return nb+type;
	}
	
	public String getOriginalMorpheme() {
	    if (originalMorpheme != null)
	        return new Morpheme.Id(originalMorpheme).morphemeName;
	    else
	        return morpheme;
	}
	
	//-------------------------------------------------------------------------------------------------------	
	boolean isGiVerb() {
		if ( type.equals("v") && 
    				transinfix != null && transinfix.startsWith("gi") )
			return true;
		else
			return false;
	}
	
	boolean isSingular() {
		if ( number != null && number.equals("s") )
			return true;
		else
			return false;
	}
	
	boolean isDual() {
		if ( number != null && number.equals("d") )
			return true;
		else
			return false;
	}
	
	boolean isPlural() {
		if ( number != null && number.equals("p") )
			return true;
		else
			return false;
	}
	
	boolean isTransitiveVerb() {
		if ( type.equals("v") && transitivity != null && transitivity.equals("t") )
			return true;
		else
			return false;
	}
	
	boolean isIntransitiveVerb() {
		if ( type.equals("v") && transitivity != null && transitivity.equals("i") )
			return true;
		else
			return false;
	}
	
	String getAntipassive() {
		return antipassive;
	}
	
	String getVariant() {
		return variant;
	}
	
	/*
     * Roots' transitivity is defined as follows: 
     * t: transitive 
     *   If the value of 'antipassive' is not null, the root may be used
     *   intransitively (reflexive or passive transitiveMeaning)
     * i: intransitive 
     *   If the value of 'transinfix' is "nil", the root may also be transitive
     */
	boolean agreeWithTransitivity(String trans) {
	    if (trans==null)
	        return true;
	    else if (transitivity==null)
	        return false;
	    else if (transitivity.equals("t") && trans.equals("t"))
	        return true;
        /*
         * Certains verbes intransitifs peuvent �tre utilis�s transitivement sans
         * infixe de transitivit�.  Ceux-ci ont suffixe-trans=nil.
         */
	    else if (transitivity.equals("i") &&
	            (trans.equals("i") || 
	                    (trans.equals("t") && transinfix!=null && transinfix.equals("nil"))))
	        return true;
        /*
         * Les verbes transitifs avec un antipassif non-nul peuvent �tre utilis�s
         * intransitivement; ils sont alors interpr�t�s passivement ou r�flexivement.
         */
	    else if (transitivity.equals("t") && antipassive!=null &&
	            trans.equals("i") )
	        return true;
	    else
	        return false;
	}
	
    String[] getVariants() {
        return variant.split(" ");
    }
    
	String getCompositionRoot() {
	    return compositionRoot;
	}
	
	void getAndSetBaseAttributes(HashMap v) {
		morpheme = (String) v.get("morpheme");
		englishMeaning = (String) v.get("engMean");
		frenchMeaning = (String) v.get("freMean");
		dbName = (String) v.get("dbName");
		tableName = (String) v.get("tableName");		
	}

	void setCombiningParts( String comb ) {
	    combinedMorphemes = comb.split("[+]");
	    if (combinedMorphemes.length < 2) {
	        combinedMorphemes = null;
	    }
	}
	
	boolean needsAntipassive(String apId) {
	    if (antipassive != null) {
	        StringTokenizer st = new StringTokenizer(antipassive);
	        while (st.hasMoreTokens())
	            if (st.nextToken().equals(apId))
	                return true;
	        return false;
	    }
	    else
	        return false;
	}

//    String getMeaning(String lang) {
//        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
//            String transitiveMeaning = lang.equals("en")?
//                    transitiveMeaning_e:transitiveMeaning_f;
//            if (transitiveMeaning==null)
//                makeVerbMeanings();
//            return transitiveMeaning;
//        } else
//            return lang.equals("en")?englishMeaning:frenchMeaning;
//
//    }
    
    /*
     * The next 4 methods should be called for transitive verbs only.
     */
    String getTransitiveMeaning(String lang) {
        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
            String transitiveMeaning = lang.equals("en")?
                    transitiveMeaning_e:transitiveMeaning_f;
            if (transitiveMeaning==null)
                makeVerbMeanings();
            return lang.equals("en")?
                    transitiveMeaning_e:transitiveMeaning_f;
        } else
            return "";
    }
    
    String getPassiveMeaning(String lang) {
        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
            String passiveMeaning = lang.equals("en")?
                    passiveMeaning_e:passiveMeaning_f;
            if (passiveMeaning==null)
                makeVerbMeanings();
            return lang.equals("en")?
                    passiveMeaning_e:passiveMeaning_f;
        } else
            return null;
    }
    
    String getResultMeaning(String lang) {
        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
            String resultMeaning = lang.equals("en")?
                    resultMeaning_e:resultMeaning_f;
            if (resultMeaning==null)
                makeVerbMeanings();
            return lang.equals("en")?
                    resultMeaning_e:resultMeaning_f;
        } else 
            return "";
    }
    
    String getReflexiveMeaning(String lang) {
        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
            String reflexiveMeaning = lang.equals("en")?
                    reflexiveMeaning_e:reflexiveMeaning_f;
            if (reflexiveMeaning==null)
                makeVerbMeanings();
            return lang.equals("en")?
                    reflexiveMeaning_e:reflexiveMeaning_f;
        } else
            return null;
    }

//    Vector getIdsOfCompositesWithThisRoot() {
//        return idsOfCompositesWithThisRoot;
//    }
    
    boolean isKnown() {
    	return known.booleanValue();
    }
    
	void setAttrs() {
		setAttributes();
		setId();
	}
	
    void setAttributes() {
    	setAttributes(new HashMap());
    }
    
    void setAttributes(HashMap attrs) {
    	HashMap baseAttrs = new HashMap();
    	baseAttrs.put("variant", variant);
    	baseAttrs.put("originalMorpheme", originalMorpheme);
    	baseAttrs.put("nature", nature);
    	baseAttrs.put("nb", nb);
    	baseAttrs.put("known", known);
    	baseAttrs.put("transitivity", transitivity);
    	baseAttrs.put("transinfix", transinfix);
    	baseAttrs.put("intransinfix", intransinfix);
    	baseAttrs.put("antipassive", antipassive);
    	baseAttrs.put("number", number);
    	baseAttrs.put("subtype", subtype);
    	baseAttrs.put("source", source);
    	baseAttrs.putAll(attrs);
    	super.setAttributes(baseAttrs);
    }

	String getNature() {
		return nature;
	}
	
	//---------------------------------------------------------------------------------------------------------
    private void setIdsOfCompositesWithThisRoot(Vector v) {
        idsOfCompositesWithThisRoot = v;
    }
    
    /*
     * Modèle général d'une chaîne de texte pour le sens d'un verbe transitif:
     * ('[' '-'? ('R' | 'T' | 'P' | 'A') ']' texte )+
     * texte
     */
    private void makeVerbMeanings() {
		String[] englishMeanings = makeVerbMeanings(englishMeaning, "en");
		if (englishMeanings != null) {
			transitiveMeaning_e = englishMeanings[0];
			passiveMeaning_e = englishMeanings[1];
			reflexiveMeaning_e = englishMeanings[2];
			resultMeaning_e = englishMeanings[3];
		}
		String[] frenchMeanings = makeVerbMeanings(frenchMeaning, "fr");
		if (frenchMeanings != null) {
			transitiveMeaning_f = frenchMeanings[0];
			passiveMeaning_f = frenchMeanings[1];
			reflexiveMeaning_f = frenchMeanings[2];
			resultMeaning_f = frenchMeanings[3];
		}
	}
    
    private static String[] makeVerbMeanings(String sense, String lang) {
		if (sense == null)
			return null;
		String transitiveMeaning = "";
		String passiveMeaning = "";
		String resultMeaning = "";
		String reflexiveMeaning = "";

		/*
		 * Un mot commençant par / est un verbe à transformer au passif; le
		 * s.t. ou s.o. (ou qqch. ou qqn en français) représente le
		 * complément d'objet direct du verbe qui, dans le sens au passif,
		 * de même que pour les verbes marqués 'res', est supprimé.
		 */
		Pattern pt = Pattern.compile("\\x5B(-)?([RTPAI])\\x5D([^\\x5B]+)");
		// [(-)R|T|P|A|I]texte
		// groupe 1 : le signe -
		// groupe 2 : R, T, P, A ou I
		// groupe 3 : le texte
		Matcher mpt = pt.matcher(sense);
		int pos = 0;
		// [A] par défaut
//		if (!mpt.find()) {
		if (!sense.startsWith("[")) {
			sense = "[A]" + sense;
			mpt = pt.matcher(sense);
		}

		// Pour chaque '[' '-'? ('R' | 'T' | 'P' | 'A' ) ']' texte
		// R : reflexive
		// T : transitive
		// P : passive
		while (mpt.find(pos)) {
			String texte = null;
			String mode;
			boolean modeBool = true;
			boolean trans = true;
			boolean pass = true;
			boolean refl = true;
			texte = mpt.group(3);
			mode = mpt.group(2);
			if (mpt.group(1) != null)
				modeBool = false;
			if (mode.equals("R")) {
				if (modeBool) {
					trans = false;
					pass = false;
				} else {
					refl = false;
				}
			} else if (mode.equals("T")) {
				if (modeBool) {
					refl = false;
					pass = false;
				} else {
					trans = false;
				}
			} else if (mode.equals("P")) {
				if (modeBool) {
					trans = false;
					refl = false;
				} else {
					pass = false;
				}
			}

			Pattern pm = Pattern.compile("(to )?/(([a-zA-Zàâéèêëîïôùûü]|-)+)");
			// texte = (to) /verbe
			Matcher mpm = pm.matcher(texte);
			int pos2 = 0;
			boolean toBool = lang.equals("en") ? false : true;

			while (mpm.find(pos2)) {
				String key;
				String to = "";
				if (mpm.group(1) != null) {
					to = mpm.group(1);
					toBool = true;
				} else if (lang.equals("en"))
					toBool = false;
				key = mpm.group(2);
				String verbWord = key;
				verbWord = verbWord.replaceFirst("-[^-]+$","");
				String verbPart = key.replace(verbWord, "");
				VerbWord verb = (VerbWord) LinguisticDataAbstract.words.get(verbWord);
				String partPassive = texte.substring(pos2, mpm.start());
				String partReflexive = texte.substring(pos2, mpm.start());
				if (lang.equals("en")) {
					partPassive = partPassive.replaceAll("on top of s.t.",
							"atop");
					partPassive = partPassive.replaceAll("s.t.'s", "its");
					partPassive = partPassive
							.replaceAll("s.o.'s", "his or her");
					partPassive = partPassive.replaceAll(
							" s[.]o[.] or s[.]t[.]", "");
					partPassive = partPassive.replaceAll(
							" s[.]t[.] or s[.]o[.]", "");
					partPassive = partPassive.replaceAll(" s[.]o[.]", "");
					partPassive = partPassive.replaceAll(" s[.]t[.]", "");
					partReflexive = partReflexive.replaceAll("s.t.'s",
							"its own");
					partReflexive = partReflexive.replaceAll("s.o.'s",
							"his or her own");
					partReflexive = partReflexive.replaceAll(
							"s[.]o[.] or s[.]t[.]", "oneself or itself");
					partReflexive = partReflexive.replaceAll(
							"s[.]t[.] or s[.]o[.]", "itself or oneself");
					partReflexive = partReflexive.replaceAll("s[.]o[.]",
							"oneself");
					partReflexive = partReflexive.replaceAll("s[.]t[.]",
							"itself");
				} else if (lang.equals("fr")) {
					partPassive = partPassive.replaceAll(
							" qqn ou qqch[.]", "");
					partPassive = partPassive.replaceAll(
							" qqch[.] ou qqn", "");
					partPassive = partPassive.replaceAll(" qqn", "");
					partPassive = partPassive.replaceAll(" qqch[.]", "");
					if (pass)
						passiveMeaning += partPassive;
					partReflexive = partReflexive.replaceAll(
							" qqn ou qqch[.]", "");
					partReflexive = partReflexive.replaceAll(
							" qqch[.] ou qqn", "");
					partReflexive = partReflexive.replaceAll(" qqn", "");
					partReflexive = partReflexive.replaceAll(" qqch[.]",
							"");
				}
				if (refl)
					reflexiveMeaning += partReflexive + to;
				if (refl)
					resultMeaning += partPassive + to;
				if (lang.equals("fr")) {
					if (refl)
						reflexiveMeaning += Util.isVowel(key.charAt(0)) ? "s'" : "se ";
					if (pass)
						passiveMeaning += toBool ? "être " : "";
					toBool = false;
				} else {
					if (pass)
						passiveMeaning += partPassive
								+ (toBool ? (to + "be ") : "");
				}
				if (pass) {
					String vpass = null;
					if (lang.equals("en")) {
						if (verb != null)
							vpass = verb.passive;
						else if (key.endsWith("e"))
							vpass = key + "d";
						else if (key.endsWith("y"))
							vpass = key.substring(0, key.length() - 1) + "ied";
						else
							vpass = key + "ed";
					}
					else if (lang.equals("fr")) {
						if (verb != null)
							vpass = verb.passive+verbPart;
						else if (verbWord.endsWith("er"))
							vpass = verbWord.replaceAll("er$", "é")+verbPart;
						else if (verbWord.endsWith("ir"))
							vpass = verbWord.replaceAll("ir$", "i")+verbPart;
						{
							if (verbWord.startsWith("se-"))
								verbWord = verbWord.replace("se-", "");
							if (key.endsWith("-à") || key.endsWith("-contre")
									|| key.endsWith("-de") || key.endsWith("-dans")) {
								vpass = "se faire "+verbWord;
							}
							else if (key.endsWith("-sur")) {
								vpass = "se faire "+verbWord+" dessus";
							}
						}
					}
					else
						vpass = key;
					passiveMeaning += "/" + vpass;
					passiveMeaning = passiveMeaning.replace("être /se faire", "/se faire");
				}
				if (refl)
					reflexiveMeaning += key.replaceFirst("-[^-]+$","");
				if (refl)
					resultMeaning += "/" + key;
				if (trans)
					transitiveMeaning += texte.substring(pos2, mpm.start())
							+ to + key;
				pos2 = mpm.end();
			}

			// Partie finale
			String partPassive = texte.substring(pos2);
			String partReflexive = texte.substring(pos2);
			if (trans)
				transitiveMeaning += texte.substring(pos2);
			if (trans)
			if (pos2 != 0) {
				if (lang.equals("en")) {
					partPassive = partPassive.replaceAll("on top of s.t.",
							"atop");
					partPassive = partPassive.replaceAll("s.t.'s", "its");
					partPassive = partPassive
							.replaceAll("s.o.'s", "his or her");
					partPassive = partPassive.replaceAll(
							" s[.]o[.] or s[.]t[.]", "");
					partPassive = partPassive.replaceAll(
							" s[.]t[.] or s[.]o[.]", "");
					partPassive = partPassive.replaceAll(" s[.]o[.]", "");
					partPassive = partPassive.replaceAll(" s[.]t[.]", "");
					partReflexive = partReflexive.replaceAll("s.t.'s",
							"its own");
					partReflexive = partReflexive.replaceAll("s.o.'s",
							"his or her own");
					partReflexive = partReflexive.replaceAll(
							"s[.]o[.] or s[.]t[.]", "oneself or itself");
					partReflexive = partReflexive.replaceAll(
							"s[.]t[.] or s[.]o[.]", "itself or oneself");
					partReflexive = partReflexive.replaceAll("s[.]o[.]",
							"oneself");
					partReflexive = partReflexive.replaceAll("s[.]t[.]",
							"itself");
				} else if (lang.equals("fr")) {
					partPassive = partPassive.replaceAll(
							" qqn ou qqch[.]", "");
					partPassive = partPassive.replaceAll(
							" qqch[.] ou qqn", "");
					partPassive = partPassive.replaceAll(" qqn", "");
					partPassive = partPassive.replaceAll(" qqch[.]", "");
					partReflexive = partReflexive.replaceAll(
							" qqn ou qqch[.]", "");
					partReflexive = partReflexive.replaceAll(
							" qqch[.] ou qqn", "");
					partReflexive = partReflexive.replaceAll(" qqn", "");
					partReflexive = partReflexive.replaceAll(" qqch[.]", "");
				}
			}
			if (trans)
				transitiveMeaning = transitiveMeaning.replace("se-", "se ");
			if (trans)
				transitiveMeaning = transitiveMeaning.replace("-à", " à");
			if (trans)
				transitiveMeaning = transitiveMeaning.replace("-contre", " contre");
			if (trans)
				transitiveMeaning = transitiveMeaning.replace("-de ", " de ");
			if (trans)
				transitiveMeaning = transitiveMeaning.replace("-dans ", " dans ");
			if (trans)
				transitiveMeaning = transitiveMeaning.replace("-sur ", " sur ");
			if (refl)
				reflexiveMeaning = reflexiveMeaning.replace("-à", "");
			if (pass)
				passiveMeaning += partPassive;
			if (pass)
				passiveMeaning = passiveMeaning.replaceAll("be /made ",
						"be made to ");
			if (pass)
				passiveMeaning = passiveMeaning.replaceAll("/", "");
			if (refl)
				reflexiveMeaning += partReflexive;
			if (refl)
				resultMeaning += partPassive;
			if (refl)
				resultMeaning = resultMeaning.replaceAll("to /make", "to");
			if (refl)
				resultMeaning = resultMeaning.replaceAll("/make", "to");
			if (refl)
				resultMeaning = resultMeaning.replaceAll("/", "");
			pos = mpt.end();
		}

		passiveMeaning = passiveMeaning.trim();
		if (reflexiveMeaning.equals("")) reflexiveMeaning = null;
		if (resultMeaning.equals("")) resultMeaning = null;
		return new String[] { transitiveMeaning, passiveMeaning,
				reflexiveMeaning, resultMeaning };
	}
    
	//---------------------------------------------------------------------------------------------------------
	public String showData() {
		StringBuffer sb = new StringBuffer();
		sb.append("[Base: morpheme= " + morpheme + "\n");
		sb.append("id= "+id+"\n");
		sb.append("variant= " + variant + "\n");
		sb.append("nb= " + nb + "\n");
		sb.append("type= " + type + "\n");
		sb.append("nature= " + nature + "\n");
		sb.append("number= " + number + "\n");
		sb.append("compositionRoot= "+compositionRoot+"\n");
		if (type.equals("v")) {
		    sb.append("antipassive= " + antipassive + "\n");
		}
        if (nextCondition!=null)
            sb.append("followingSpecificConditions= "+nextCondition.toString()+"\n");
        sb.append("englishMeaning= " + englishMeaning + "\n");
        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
            String trans = this.getTransitiveMeaning("en");
            String refl = this.getReflexiveMeaning("en");
            String pass = this.getPassiveMeaning("en");
            String res = this.getResultMeaning("en");
            sb.append("(trans) "+ trans + "\n" +
                    ((pass!=null)?"(pass) "+pass:"") + "\n" +
                    ((refl!=null)?"(reflex) "+refl:"") + "\n" +
                    ((res!=null)?"(res) "+res:"") + "\n");
        } 
        sb.append("frenchMeaning= " + frenchMeaning + "\n");
        if (type.equals("v") && transitivity != null && transitivity.equals("t")) {
            String trans = this.getTransitiveMeaning("fr");
            String refl = this.getReflexiveMeaning("fr");
            String pass = this.getPassiveMeaning("fr");
            String res = this.getResultMeaning("fr");
            sb.append("(trans) "+ trans + "\n" +
                    ((pass!=null)?"(pass) "+pass:"") + "\n" +
                    ((refl!=null)?"(reflex) "+refl:"") + "\n" +
                    ((res!=null)?"(res) "+res:"") + "\n");
        } 
        sb.append("dbName= "+dbName+"\n");
    	sb.append("tableName= "+tableName+"\n");
        sb.append("dialect= "+dialect+"\n");
        sb.append("cf= "+cf+"\n");
    	sb.append("]");
 		return sb.toString();
	}

}

	


