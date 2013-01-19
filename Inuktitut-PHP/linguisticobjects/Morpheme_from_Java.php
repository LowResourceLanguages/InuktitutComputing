<?php
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Morpheme.php
//
// Type/File type:		code php / php code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation:	
//
// Description: Classe Morpheme, englobant les sous-classes:
//                               Affix (Suffix et VerbEnding)
//                               Base
//
// -----------------------------------------------------------------------

require_once 'linguisticobjects/LinguisticObjects.php';

abstract class Morpheme {
	//
	private $id = null;
	private $type = null;
	private $morpheme;
	private $engMeaning = null;
	private $frenchMeaning = null;
	private $no = null;
	private $sources = array();
	private $num = null;
	private $dbName = null;
	private $tableName = null;
	private $dialect = null;
	private $combinedMorphemes = array();
    
    private $cf = null; // référence à d'autres morphèmes
    private $cfs = array(); // tableau de références à d'autres morphèmes

    private $preCondition = null;
    private $nextCondition = null;
  
    //------------------------------------------------------------------------------------------------------------
	abstract public function agreesWithTransitivity($trans); //
    abstract public function showData();
    //------------------------------------------------------------------------------------------------------------
	abstract public function signature(); //
	abstract public function getOriginalMorpheme(); //	
    //------------------------------------------------------------------------------------------------------------

	public function getCombiningParts() {
	    return $this->combinedMorphemes;
	}

    public function copie()   {
        return clone $this;
    }
    
    public function getPrecCond() {
        return preCondition;
    }
    
    public function getNextCond() {
        return $this->nextCondition;
    }
    
    public function meetsTransitivityCondition($transitivity) {
        /*
         * Vérifier la valeur de transitivité du candidat actuel. Les
         * sufffixes-noms et les racines autres que verbales ont une
         * valeur de transitivité nulle. La valeur de transitivité des
         * suffixes-verbes et des racines verbales est indiquée dans le
         * champ 'transitivity'.
         */
        if ($this->agreesWithTransitivity($transitivity))
            $res = true;
        else
            $res = false;
        return $res;
    }
  
    public function meetsConditions ($conds, $followingMorphemes) {
        $res = true;
        /*
         * Il faut que les conditions spécifiques soient rencontrées. Par
         * exemple, si le morphème trouvé précédemment exige de suivre
         * immédiatement un nom au cas datif, le suffixe ou la terminaison
         * actuelle doit rencontrer cette contrainte.
         */
        if ( $conds != null ) {
            $res = $conds->isMetBy($this);
        }
        /*
         * Le suffixe peut aussi avoir des contraintes sur ce qui doit le suivre
         * immédiatement. Vérifier ces contraintes.
         */
        if ( $res ) {
            if ( $this->getNextCond() != null ) {
                // Morphème suivant, i.e. le dernier morphème trouvé.
                if ( count($followingMorphemes) != 0) {
                    $affPrec = $followingMorphemes[0]->getAffix();
                    $res = $this->getNextCond()->isMetBy($affPrec);
                }
            }
        }
    return $res;
	}

//    public abstract Vector getIdsOfCompositesWithThisRoot();
    
    public function getLastCombiningMorpheme() {
        $parts = $this->getCombiningParts();
        $lastPart = null;
        $lastMorpheme = null;
        if ( count($parts) != 0 ) {
            $lastPart = $parts[count($parts)-1];
            if ( $lastPart != '?' ) {
                $lastMorpheme = self::getMorpheme($lastPart);
            }
        }
        return lastMorpheme;
    }
    
    public static function getMorpheme($morphemeId) {
    	    // Look for the morpheme in the affixes
    	    $morph = LinguisticObjects::getObject($morphemeId);
    	    // If not found, look for the morpheme in the roots
    	    if (morph == null)
    	        morph = (Morpheme)DonneesLinguistiquesAbstract.getBase(morphemeId);
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
    String getNo() {
    	return no;
    }
    
    void setId() {
        idObj = new Morpheme.Id(getOriginalMorpheme(),signature());
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
		attributes.put("no", no);
		attributes.put("morpheme",morpheme);
		attributes.put("engMeaning",engMeaning);
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
	
    static String combine(String combination, boolean withAction2, String highlightedMorpheme) {
        String formeCombinee;
        String[]morphemes = combination.split("\\x2b"); // +
        Morpheme morpheme = DonneesLinguistiquesAbstract.getMorpheme(morphemes[0]);
        formeCombinee = morpheme.morpheme;
        char context = formeCombinee.charAt(formeCombinee.length()-1);
        for (int i=1; i<morphemes.length; i++) {
            String precForme = formeCombinee;
            Affix aff = (Affix)DonneesLinguistiquesAbstract.getMorpheme(morphemes[i]);
            Action[] action1 = aff.actions1(context);
            Action[] action2 = aff.actions2(context);
            String formeAff = aff.surfaceForm(context)[0];
            Action a2 = null;
            if (withAction2)
                a2 = action2[0];
            formeCombinee = action1[0].combine(precForme,formeAff,a2);
            context = formeCombinee.charAt(formeCombinee.length()-1);
            if (highlightedMorpheme!=null && morphemes[i].equals(highlightedMorpheme))
                formeCombinee = formeCombinee.replaceFirst(formeAff,"<span style=\"color:red\">"+formeAff+"</span>");
        }
        return formeCombinee;
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
?>
