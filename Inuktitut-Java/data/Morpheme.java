//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Morpheme.java
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
// Description: Classe Morpheme, englobant les sous-classes:
//                               Affix (Suffix et TerminaisonVerbale)
//                               Base
//
// -----------------------------------------------------------------------


package data;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import morph.AffixPartOfComposition;

import data.constraints.Conditions;

import data.LinguisticDataAbstract;

public abstract class Morpheme extends Object implements Cloneable {
	//
	public String id = null;
	public String type = null;
	public String morpheme;
	public String englishMeaning = null;
	public String frenchMeaning = null;
	String nb = null;
	String [] sources = null;
	Integer num = null;
	String dbName = null;
	String tableName = null;
	Morpheme.Id idObj = null;
	String dialect = null;
	String combinedMorphemes[] = null;
    
    String cf = null; // référence à d'autres morphèmes
    String[] cfs = null; // tableau de références à d'autres morphèmes

    Conditions preCondition = null;
    Conditions nextCondition = null;

    private HashMap attributes = null;
    //
    
    //------------------------------------------------------------------------------------------------------------
	abstract boolean agreeWithTransitivity(String trans); //
    public abstract String showData();
    abstract void setAttrs();

    //------------------------------------------------------------------------------------------------------------
	public abstract String getSignature(); //
	public abstract String getOriginalMorpheme(); //
	
    //------------------------------------------------------------------------------------------------------------
	public String[] getCombiningParts() {
	    return combinedMorphemes;
	}

    public Morpheme copyOf() throws CloneNotSupportedException  {
        return (Morpheme)this.clone();
    }
    
    public Conditions getPrecCond() {
        return preCondition;
    }
    
    public Conditions getNextCond() {
        return nextCondition;
    }
    
    public boolean meetsTransitivityCondition(String transitivity) {
        /*
         * Vérifier la valeur de transitivité du candidat actuel. Les
         * sufffixes-noms et les racines autres que verbales ont une
         * valeur de transitivité nulle. La valeur de transitivité des
         * suffixes-verbes et des racines verbales est indiquée dans le
         * champ 'transitivite'.
         */
        boolean res;
        if (agreeWithTransitivity(transitivity))
            res = true;
        else
            res = false;
        return res;
    }
  
    public boolean meetsConditions (Conditions conds, Vector followingMorphemes) {
        boolean res = true;
        /*
         * Il faut que les conditions spécifiques soient rencontrées. Par
         * exemple, si le morphème trouvé précédemment exige de suivre
         * immédiatement un nom au cas datif, le suffixe ou la terminaison
         * actuelle doit rencontrer cette contrainte.
         */
        if (conds != null) {
            res = conds.isMetBy(this);
        }
        /*
         * Le suffixe peut aussi avoir des contraintes sur ce qui doit le suivre
         * immédiatement. Vérifier ces contraintes.
         */
        if (res) {
            if (getNextCond() != null) {
                // Morphème suivant, i.e. le dernier morphème trouvé.
                if (followingMorphemes.size() != 0) {
                    Affix affPrec = ((AffixPartOfComposition) followingMorphemes
                            .elementAt(0)).getAffix();
                    res = getNextCond().isMetBy(affPrec);
                }
            }
        }
    return res;
	}

//    public abstract Vector getIdsOfCompositesWithThisRoot();
    
    public Morpheme getLastCombiningMorpheme() {
        String [] parts = getCombiningParts();
        String lastPart = null;
        Morpheme lastMorpheme = null;
        if (parts!=null) {
            lastPart = parts[parts.length-1];
            if (!lastPart.equals("?")) {
                lastMorpheme = getMorpheme(lastPart);
            }
        }
        return lastMorpheme;
    }
    
    public static Morpheme getMorpheme(String morphemeId) {
    	    // Look for the morpheme in the affixes
    	    Morpheme morph = (Morpheme)LinguisticDataAbstract.getAffix(morphemeId);
    	    // If not found, look for the morpheme in the roots
    	    if (morph == null)
    	        morph = (Morpheme)LinguisticDataAbstract.getBase(morphemeId);
            return morph;
        }
    
    public boolean attrEqualsValue (String attr, String val, boolean eq) {
        boolean res = false;
        String valAttr = getAttr(attr);
        // It is possible that the value of the attribute for the morpheme
        // is not unique.  For example, the value of 'intransinfix' (field
        // 'suffixe-intans') of the verbal root 'quviak' is 
        // "gusuk/1vv suk/1vv", that is, a dual value.  When the values
        // are compared, one has to take this into account.
        String valAspect = val;
        // This is the value that the morpheme's attribute should have
        // to meet the condition.  If that value is the name of an attribute
        // of the morpheme, find the value of that attribute.
        if (attributes.containsKey(valAspect))
            valAspect = getAttr(valAspect);
        // Check for the morpheme
        if (valAspect.equals("null"))
            if (eq)
                res = (valAttr==null);
            else
                res = (valAttr!=null);
        else if (valAttr != null) {
            String[] valAttrs;
            valAttrs = valAttr.split(" ");
            if (eq) {
                for (int iValAttrs=0; iValAttrs < valAttrs.length; iValAttrs++)
                    if (res = valAttrs[iValAttrs].equals(valAspect))
                        break;
            }
            else
                for (int iValAttrs=0; iValAttrs < valAttrs.length; iValAttrs++)
                    res = !valAttr.equals(valAspect);
        }
        else
            res = false;
        return res;
    }
    
    //------------------------------------------------------------------------------------------------------------
    String getNb() {
    	return nb;
    }
    
    void setId() {
        idObj = new Morpheme.Id(getOriginalMorpheme(),getSignature());
        id = idObj.id;
		attributes.put("id",id);
    }
    
    String getAttr(String attr) {
        return (String)this.attributes.get(attr);
    }
    
	void setAttributes(HashMap attrs) {
		attributes = new HashMap();
		attributes.putAll(attrs);
		attributes.put("type",type);
		attributes.put("nb", nb);
		attributes.put("morpheme",morpheme);
		attributes.put("englishMeaning",englishMeaning);
		attributes.put("frenchMeaning",frenchMeaning);
		attributes.put("sources",sources);
		attributes.put("num",num);
		attributes.put("dbName",dbName);
		attributes.put("tableName",tableName);
		attributes.put("idObj",idObj);
		attributes.put("dialect",dialect);
		attributes.put("cf",cf);
		attributes.put("cfs",cfs);
		attributes.put("preCondition",preCondition);
		attributes.put("nextCondition",nextCondition);		
		attributes.put("combinedMorphemes",combinedMorphemes);
	}
	
    public static String combine(String combination, boolean withAction2, String highlightedMorpheme) {
        String combinedForm;
        String[]morphemes = combination.split("\\x2b"); // +
        Morpheme morpheme = LinguisticDataAbstract.getMorpheme(morphemes[0]);
        combinedForm = morpheme.morpheme;
        char context = combinedForm.charAt(combinedForm.length()-1);
        for (int i=1; i<morphemes.length; i++) {
            String precForm = combinedForm;
            Affix aff = (Affix)LinguisticDataAbstract.getMorpheme(morphemes[i]);
            Action[] action1 = aff.getAction1(context);
            Action[] action2 = aff.getAction2(context);
            String formAff = aff.getForm(context)[0];
            Action a2 = null;
            if (withAction2)
                a2 = action2[0];
            combinedForm = action1[0].combine(precForm,formAff,a2);
            context = combinedForm.charAt(combinedForm.length()-1);
            if (highlightedMorpheme!=null && morphemes[i].equals(highlightedMorpheme))
                combinedForm = combinedForm.replaceFirst(formAff,"<span style=\"color:red\">"+formAff+"</span>");
        }
        return combinedForm;
    }
    
    //------------------------------------------------------------------------------------------
    // STATIC CLASS "ID"
    static public class Id {
    	//
	    static public String delimiter = "/";
	    public String morphemeName;
	    public String signature;
	    public String id;
	    //
	    
	    //-------------------------------------------------------------------------
	    public Id(String morphId) {
	        StringTokenizer st = new StringTokenizer(morphId,delimiter);
	        if (st.countTokens()==2) {
	            morphemeName = st.nextToken();
	            signature = st.nextToken();
	            id = morphId;
	        }
	    }
	    
	    public Id(String morphName, String sign) {
	        morphemeName = morphName;
	        signature = sign;
	        id = morphemeName + delimiter + signature;
	    }
	    
	    //-------------------------------------------------------------------------	    
	    public String toHTML() {
	        return morphemeName+"<sup>"+signature+"</sup>";
	    }
        
        static public String toHTML(String morphemeId) {
            String morphName = morphemeId.substring(0,morphemeId.indexOf(delimiter));
            String sign = morphemeId.substring(morphemeId.indexOf(delimiter)+1);
            return morphName+"<sub>"+sign+"</sub>";
        }
	}


}


