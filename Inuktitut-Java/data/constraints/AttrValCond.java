/*
 * Conseil national de recherche Canada 2005/ National Research Council Canada
 * 2005
 * 
 * Cr�� le / Created on Mar 3, 2005 par / by Benoit Farley
 *  
 */
package data.constraints;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import data.Morpheme;

/*
 * Condition on some attribute of a morpheme. The condition is about one or
 * several aspects of the morpheme. A condition aspect is about an attribute and
 * its value. The attribute name is one of the attributes in the morpheme
 * classes.  The value is either the name of another attribute, which means the
 * real value is the value of that other attribute and the values of the
 * two attributes must be the same, or a real value otherwise.  
 *  
 */
public class AttrValCond extends Condition implements Conditions,Cloneable {

    Aspect aspect;
    
    /*
     * argument (String) attrValue: <attribute>:<value>
     * separated by commas
     */
    public AttrValCond(String attrValue) {
        parameters = attrValue;
        aspect = new Aspect(attrValue);
    }

    /*
     * Check whether this condition is met by the morpheme. Each aspect of the
     * condition must be met. 
     * argument: (Morpheme) morph
     */
    public boolean isMetBy(Morpheme morph) {
        boolean res = true;
        Morpheme lm = morph.getLastCombiningMorpheme();
        res = true;
        res = morph.attrEqualsValue(aspect.attribute, aspect.value, aspect.eq);
        /*
         * Several roots are described in the database as inchoative roots, that
         * is, roots that are the result of adding a 'q' at the end and doubling
         * the last internal consonant (eg. ikummaq- < ikuma-). In the database,
         * this is done in the column 'combination': the last morpheme is
         * #incho#/1vv. This morpheme, as last morpheme, is NOT to be checked.
         */
        if (!res && lm!=null && !lm.id.equals("#incho#/1vv"))
        	res = lm.attrEqualsValue(aspect.attribute, aspect.value, aspect.eq);
        return (truth == res); // XNOR
    }
    
    public boolean isMetByFullMorphem(Morpheme morph) {
        boolean res = true;
        res = morph.attrEqualsValue(aspect.attribute, aspect.value, aspect.eq);
        return (truth == res); // XNOR
    }
    

    public static boolean hasConditionPattern(String str) {
        Pattern p = Pattern.compile("[a-z]+:[^:]+");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 
    
    public String toString() {
        String str = parameters;
        if (!truth)
            str = "!"+str;
        return str;
    }
    
    public String toText(String lang) {
        String text = truth?"":
            (lang.equals("e")?"NOT ":"PAS ");
        text += getString(aspect.attribute,lang)+"==";
        text += getString(aspect.attribute.equals("id")?aspect.value:aspect.attribute+"_"+aspect.value,lang);
        return text;
    }
    
    public Condition expand() {
        Condition cond = null;
        try {
            cond = (AttrValCond)this.clone();
        } catch (CloneNotSupportedException e) {
        }
        return cond;
    }

    static Hashtable strings;
    static {
        strings = new Hashtable();
        strings.put("nature_a",new String[]{"adjectif","adjective"});
        strings.put("type_a",new String[]{"racine adverbe","adverb root"});
        strings.put("type_v",new String[]{"racine verbale","verb root"});
        strings.put("cas_abl",new String[]{"ablatif","ablative"});
        strings.put("antipassive",new String[]{"infixe antipassif","antipassive infix"});
        strings.put("mode_caus",new String[]{"causal","becausative"});
        strings.put("mode_cond",new String[]{"conditionnel","conditional"});
        strings.put("mode_dub",new String[]{"dubitatif","dubitative"});
        strings.put("number_d",new String[]{"duel","dual"});
        strings.put("cas_dat",new String[]{"datif","dative"});
        strings.put("mode_dec",new String[]{"d�claratif","declarative"});
        strings.put("mode_freq",new String[]{"fr�quentatif","frequentative"});
        strings.put("mode_ger",new String[]{"g�rondif","gerundive"});
        strings.put("id",new String[]{"morph�me","morpheme"});
        strings.put("mode_imp",new String[]{"imp�ratif","imperative"});
        strings.put("mode_int",new String[]{"interrogatif","interrogative"});
        strings.put("intransinfix",new String[]{"infixe pour usage intransitif","infix for intransitive usage"});
        strings.put("function",new String[]{"fonction","function"});
        strings.put("cas_loc",new String[]{"locatif","locative"});
        strings.put("mode",new String[]{"mode verbal","verb mode"});
        strings.put("nb",new String[]{"nombre","number"});
        strings.put("number",new String[]{"nombre","number"});
        strings.put("function_nn",new String[]{"infixe nom � nom","noun-to-noun infix"});
        strings.put("function_nv",new String[]{"infixe nom � verbe","noun-to-verb infix"});
        strings.put("mode_part",new String[]{"participe","participle"});
        strings.put("number_p",new String[]{"pluriel","plural"});
        strings.put("cas_dat",new String[]{"datif","dative"});
        strings.put("cas_sim",new String[]{"similaris","similaris"});
        strings.put("transinfix",new String[]{"infixe pour usage transitif","infix for transitive usage"});
        strings.put("cas_via",new String[]{"vialis","vialis"});
        strings.put("function_vn",new String[]{"infixe verbe � nom","verb-to-noun infix"});
        strings.put("function_vv",new String[]{"infixe verbe � verbe","verb-to-verb infix"});
    }
    
    static String getString(String x, String lang) {
        String v[] = (String[])strings.get(x);
        if (v!=null) {
            if (lang.equals("f"))
                return v[0];
            else if (lang.equals("e"))
                return v[1];
            else
                return x;
        } else
            return x;
    }
    
    //---------------------------------------------------------------------
    class Aspect {
        String attribute = null;
        String value = null;
        boolean eq = true;
        
        Aspect(String attrValue) {
            try {
                String sts[] = attrValue.split(":");
                attribute = new String(sts[0]);
                value = sts[1];
            } catch (Exception e) {
            }
        }
    }
    

}