//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Action.java
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
// Description: classe pour les actions des suffixes et terminaisons.
//
// -----------------------------------------------------------------------


package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import script.Orthography;
import script.Roman;

public abstract class Action {
	static public int NULLACTION = 0;
	static public int INSERTION = 1;
	static public int SPECIFICDELETION = 2;
	static public int DELETION = 3;
	static public int VOICING = 4;
	static public int NASALIZATION = 5;
	static public int NEUTRAL = 6;
	static public int FUSION = 7;
	static public int ASSIMILATION = 8;
	static public int SPECIFICASSIMILATION = 9;
	static public int VOWELLENGTHENING = 11;
	static public int CANCELLATION = 12;
	static public int SELFDECAPITATION = 13;
	static public int INSERTIONVOWELLENGTHENING = 14;
	static public int DELETIONVOWELLENGTHENING = 15;
	static public int CONDITIONALDELETION = 16;
	static public int DELETIONINSERTION = 17;
	static public int CONDITIONALDELETIONMORPHEME = 18;
	static public int CONDITIONALNASALIZATION = 19;
	static public int UNKNOWN = 1000;

	public int type;
	String strng;

	static public Action makeAction() {
	    return null;
	}
	
	static public Action makeAction(String strng) {
	    Action action = null;
        String inside = null;
        if (strng != null) {
            Pattern p = Pattern.compile("^[a-z]+\\((.+)\\)$");
            Matcher m = p.matcher(strng);
            if (m.matches())
                inside = m.group(1);
        }
		if (strng == null || strng.equals("-") || strng.equals("0")) //***
			action = new NullAction();
		else if (strng.equals("?")) //***
			action = new Unknow();
		else if (strng.startsWith("i(") || strng.startsWith("ins(")) //***
		    action = new Insertion(strng);
		else if (strng.startsWith("s(") || strng.startsWith("suppr(")) //***
		    action = new SpecificSuppression(inside);
		else if (strng.startsWith("ssi(") || strng.startsWith("supprsi(")) {//***
            String condSupp[] = inside.split(",");
            if (condSupp.length == 1)
                action = new ConditionalSuppression(strng);
            else
                action = new SpecificSuppression(condSupp[0],condSupp[1]);
        } else if (
			strng.startsWith("si(")
				|| strng.startsWith("sins(")
				|| strng.startsWith("suppri(")
				|| strng.startsWith("supprins(")) //***
		    action = new SuppressionAndInsertion(strng);
		 else if (strng.equals("s") || strng.equals("suppr")) {//***
             action = new Suppression();
        }
		else if (strng.equals("son") || strng.equals("sonor")) //***
			action = new Voicing();
		else if (strng.equals("nas") || strng.equals("nasal")) //***
            action = new Nasalization();
		else if (strng.startsWith("nassi(")) //***
            action = new ConditionalNasalization(inside); //***
        else if (strng.equals("fus") || strng.equals("fusion")) //***
            action = new Fusion();
		else if (strng.equals("n") || strng.equals("neutre")) //***
			action = new Neutral();
        else if (strng.startsWith("nsi(")) //***
            action = new Neutral(inside);
		else if (strng.equals("a") || strng.equals("assim")) //***
			action = new Assimilation();
		else if (strng.startsWith("a(") || strng.startsWith("assim(")) //***
			action = new SpecificAssimilation(strng);
		else if (strng.equals("allV")) //***
		    action = new VowelLengthening();
		else if (strng.equals("x") || strng.equals("annul")) //***
		    action = new Cancellation();
		else if (strng.equals("decap")) //***
		     action = new Selfdecapitation();
		else if (strng.startsWith("iallV(") || strng.startsWith("insallV(")) //***
		    action = new InsertionAndVowelLengthening(strng);
		else if (strng.equals("sallV") || strng.equals("supprallV")) //***
		    action = new SuppressionAndVowelLengthening();
		else
		    ;
		if (action != null) {
		    action.strng = strng;
	        action.type = action.getType();
        }
		return action;
	}
	
//	abstract String[] finalRadInitAff(String c, String f);
	
	abstract public String surfaceForm(String form);
    abstract public String expressionResult(String context, String form, Action act2);
    abstract public String combine(String form1, String form2, Action act2);
    
	public String getInsert() {
		return null;
	}

	public String getSuppr() {
		return null;
	}

	public String getAssimA() {
		return null;
	}

	public String getCondition() {
		return null;
	}

	public String toString() {
		return strng;
	}
    
    public int getType() {
        try {
            return getClass().getField("type").getInt(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public String toString(String lang) {
        try {
            String fieldName = null;
            if (lang.equals("en"))
                fieldName = "expressionAng";
            else if (lang.equals("fr"))
                fieldName = "expressionFran";
            if (fieldName != null)
                return (String)getClass().getField(fieldName).get(null);
            else
                return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;        
    }
    




	
	/*
	 * NEUTRAL
	 */
	static class Neutral extends Action implements Cloneable {

        static public final int type = NEUTRAL;
        static public final String expressionAng = "the neutral action";
        static public final String expressionFran = "l'action neutre";
        String condition = null;

        public Neutral() {
        }
        
        public Neutral(String cond) {
            condition = cond;
        }

        public String getCondition() {
            return condition;
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        public Object clone() {
            Neutral cl = new Neutral();
            cl.strng = new String(strng);
            cl.condition = new String(condition);
            return cl;
        }

        /*
         * Neutre en action1 a NullAction, Suppression, Insertion et Selfdecapitation
         * en action2.
         */
        public String expressionResult(String context, String form,
                Action act2) {
            String addForm = "+"+form;
           if (act2 == null) {
               // Ex.:  _k + paaq > _kpaaq
                return "_"+context+addForm+" &rarr; "+"_"+context+form;
            } else {
                int act2Type = -1;
                act2Type = act2.type;
                if (act2Type == NULLACTION) {
                    // Ex.:  _k + paaq > _kpaaq
                    return "_" + context + addForm + " &rarr; "+"_" + context + form;
                } else if (act2Type == DELETION) {
                    // Ex.:  _VV + ilitaq > _Vilitaq
                    return "_"+"VV"+addForm+" &rarr; "+"_"+"V"+form;
                } else if (act2Type == SELFDECAPITATION) {
                    // Ex.: _VV + uqqaq > _VVqqaq
                    return "_"+"VV"+addForm+" &rarr; "+"_"+"VV"+form.substring(1);
                } else if (act2Type == INSERTION) {
                    // Ex.: _VV + ut > _VVjjut
                    return "_"+"VV"+addForm+" &rarr; "+"_"+"VV"+act2.getInsert()+form;
                }
                    return "";
            }
        }

        public String combine(String form1, String form2, Action act2) {
           if (act2 == null) {
               // Ex.:  _k + paaq > _kpaaq
                return form1+form2;
            } else {
                int act2Type = -1;
                act2Type = act2.type;
                if (act2Type == NULLACTION) {
                    // Ex.:  _k + paaq > _kpaaq
                    return form1+form2;
                } else if (act2Type == DELETION) {
                    // Ex.:  _VV + ilitaq > _Vilitaq
                    return form1.substring(0,form1.length()-1)+form2;
                } else if (act2Type == SELFDECAPITATION) {
                    // Ex.: _VV + uqqaq > _VVqqaq
                    return form1+form2.substring(1);
                } else if (act2Type == INSERTION) {
                    // Ex.: _VV + ut > _VVjjut
                    return form1+act2.getInsert()+form2;
                }
                    return "";
            }
        }

        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            if (context.equals("V")) {
//                return new String[]{"a"+initAff, "i"+initAff, "u"+initAff};
//            } else if (context.equals("t")) {
//                return new String[]{"t"+initAff};
//            } else if (context.equals("k")) {
//                return new String[]{"k"+initAff};
//            } else if (context.equals("q")) {
//                return new String[]{"q"+initAff};
//            } else {
//                return new String[]{};
//            }
//        }	    
	}

    
    /*
     * DELETION
     */
    static class Suppression extends Action implements Cloneable {

        static public final int type = DELETION;
        static public final String expressionAng = "the deletion";
        static public final String expressionFran = "la suppression";

        public Suppression() {
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        public Object clone() {
            Suppression cl = new Suppression();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        /*
         * Si resAct1 n'est pas null, la m�thode est appel�e pour l'action2, resAct1
         * �tant le r�sultat de la m�thode pour l'action1.  Comme l'action2 concerne
         * la pr�sence de deux voyelles � la fin du radical, on modifie resAct1 en
         * cons�quence.  resAct1 a la form g�n�rale _X > _Y.  Comme il s'agit ici
         * d'une action apr�s 2 voyelles, Y devrait �tre vide et la form de resAct1
         * devrait �tre _.  _X est remplac� par _VVX et _ est remplac� par _VV.
         * On ajoute ensuite l'action2.  Suppression comme action2 arrive avec
         * Neutre et Suppression comme action1.
         */
        /*
         * Suppression en action1 a NullAction, Insertion, Selfdecapitation, Suppression
         * et SpecificSuppression en action2.
         */
        public String expressionResult(String context, String form, Action act2) {
            String addForm = "+"+form;
            if (act2 == null) {
                // Ex.:  _k + paaq > _paaq
                 return "_"+context+addForm+" &rarr; "+"_"+form;
             } else {
                 int act2Type = -1;
                 act2Type = act2.type;
                 if (act2Type == NULLACTION) {
                     // Ex.:  _k + paaq > _paaq
                     return "_"+context+addForm+" &rarr; "+"_"+form;
                 } else if (act2Type == DELETION) {
                     // Ex.:  _VVk + ilitaq > _Vilitaq
                     return "_"+"VV"+context+addForm+" &rarr; "+"_"+"V"+form;
                 } else if (act2Type == SELFDECAPITATION) {
                     // Ex.: _VVk + uqqaq > _VVqqaq
                     return "_"+"VV"+context+addForm+" &rarr; "+"_"+"VV"+form.substring(1);
                 } else if (act2Type == INSERTION) {
                     // Ex.: _VVq + u > _VVngu
                     return "_"+"VV"+context+addForm+" &rarr; "+"_"+"VV"+act2.getInsert()+form;
                 } else if (act2Type == SPECIFICDELETION) {
                     // Ex.: _Vaq + it > _Vit
                     return "_"+"V"+act2.getSuppr()+context+addForm+" &rarr; "+"_"+"V"+form;
                 } else
                     return "";
             }
        }

        public String combine(String form1, String form2, Action act2) {
            if (act2 == null) {
                // Ex.:  _k + paaq > _paaq
                 return form1.substring(0,form1.length()-1)+form2;
             } else {
                 int act2Type = -1;
                 act2Type = act2.type;
                 if (act2Type == NULLACTION) {
                     // Ex.:  _k + paaq > _paaq
                     return form1.substring(0,form1.length()-1)+form2;
                 } else if (act2Type == DELETION) {
                     // Ex.:  _VVk + ilitaq > _Vilitaq
                     return form1.substring(0,form1.length()-2)+form2;
                 } else if (act2Type == SELFDECAPITATION) {
                     // Ex.: _VVk + uqqaq > _VVqqaq
                     return form1.substring(0,form1.length()-1)+form2.substring(1);
                 } else if (act2Type == INSERTION) {
                     // Ex.: _VVq + u > _VVngu
                     return form1.substring(0,form1.length()-1)+act2.getInsert()+form2;
                 } else if (act2Type == SPECIFICDELETION) {
                     // Ex.: _Vaq + it > _Vit
                     return form1.substring(0,form1.length()-2)+form2;
                 } else
                     return "";
             }
        }

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{"a"+initAff,"i"+initAff,"u"+initAff};
//        }     
    }



	/*
	 * NULLACTION
	 */
	static class NullAction extends Action implements Cloneable {

        static public final int type = NULLACTION;

        public NullAction() {
        }

        public String surfaceForm(String form) {
            return null;
        }

        public Object clone() {
            NullAction cl = new NullAction();
            cl.strng = new String(this.strng);
            return cl;
        }

        public String expressionResult(String context, String form, Action act1) {
            String res = null;
            if (act1 != null)
                res = act1.expressionResult(context,form,null);
            return res;
        }

        public String combine(String form1, String form2, Action act2) {
            return null;
        }
        

//        public String[] finalRadInitAff(String context, String form) {
//            return new String[]{};
//        }	    
	}


	/*
	 * UNKNOWN
	 */
	static class Unknow extends Action implements Cloneable {

        static public final int type = UNKNOWN;
        
        public Unknow() {
        }

        public String surfaceForm(String form) {
            return null;
        }

        public Object clone() {
            Unknow cl = new Unknow();
            cl.strng = new String(this.strng);
            return cl;
        }

        public String expressionResult(String context, String form, Action act1) {
            return null;
        }

        public String combine(String form1, String form2, Action act2) {
            return null;
        }
        

//        public String[] finalRadInitAff(String context, String form) {
//            return new String[]{};
//        }	    
	}

	/*
	 * ASSIMILATION
	 */
    
    /*
     * Note: Cette action ne devrait pas en �tre une.  Cette action
     * provient d'une m�prise.  En effet, l'assimilation est en fait un
     * ph�nom�ne phonologique qui survient dans certains dialectes, le plus 
     * souvent le premier T d'un groupe de consonnes assimil� � la seconde
     * consonne.  On remarquera que l'assimilation est une action uniquement
     * dans le context T.  Par exemple, la form pagiq de vagiq dans le context
     * T donne une action 1 d'assimilation.  L'action devrait en fait �tre nulle,
     * comme dans les deux autres contexts K et Q, l'assimilation survenant dans
     * certains dialectes seulement: _tpagiq > _ppagiq
     */
	static class Assimilation extends Action implements Cloneable {

        static public final int type = ASSIMILATION;
        static public final String expressionAng = "the assimilation";
        static public final String expressionFran = "l'assimilation";

        public Assimilation() {
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        public Object clone() {
            Assimilation cl = new Assimilation();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        /*
         * Assimilation arrive toujours en action 1 et avec une action 2 nulle.
         */
        public String expressionResult(String context, String form, Action act1) {
            // Ex.: _t + pagiq > _ppagiq
            return "_"+context+"+"+form+" &rarr; "+"_"+form.charAt(0)+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _t + pagiq > _ppagiq
            return form1.substring(0,form1.length()-1)+form2.charAt(0)+form2;
        }

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{initAff+initAff};
//        }	    
	}

	
	/*
	 * ASSIMILATION SP�CIFIQUE
	 */
    
    /*
     * Note:
     * L'assimilation sp�cifique n'est d�finie que pour 3 terminaisons verbales,
     * toutes commen�ant par -pa: -pat, -patik, -pata, et toutes dans le context
     * T.  Et elle est une alternative � une autre action d'assimilation r�guli�re.  L'action
     * d'assimilation r�guli�re est � mettre sur le compte du ph�nom�ne
     * phonologique dialectal qui change kp en pp.  Le ph�nom�ne r�el ici est
     * que la finale T est remplac�e par K devant le P de pat.  Ensuite seulement
     * y a-t-il assimilation dans certains dialectes.  Il faudra �ventuellement 
     * corriger cela dans la base de donn�es.
     */
	static class SpecificAssimilation extends Action implements Cloneable {

        static public final int type = SPECIFICASSIMILATION;
        static public final String expressionAng = "the assimilation";
        static public final String expressionFran = "l'assimilation";
	    String assimileA = null;
	    
        public SpecificAssimilation(String str) {
        	assimileA =
        		str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }
        
        private SpecificAssimilation() {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{assimileA+initAff};
//        }	   
        
    	public String getAssimA() {
    		return assimileA;
    	}

    	public String surfaceForm(String form) {
    	    return form;
    	}

        public Object clone() {
            SpecificAssimilation cl = new SpecificAssimilation();
            cl.strng = new String(this.strng);
            cl.assimileA = new String(this.assimileA);
            return cl;
        }

        /*
         * Voir note plus haut.
         */
        public String expressionResult(String context, String form, Action act1) {
            return "_"+context+"+"+form+" &rarr; "+"_"+assimileA+form;
        }

        public String combine(String form1, String form2, Action act2) {
            return form1.substring(0,form1.length()-1)+assimileA+form2;
        }
        

	}
	
	
	/*
	 * FUSION
	 */
	static class Fusion extends Action implements Cloneable {

        static final public int type = FUSION;
        static public final String expressionAng = "the fusion";
        static public final String expressionFran = "la fusion";
        
        public Fusion() {
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        public Object clone() {
            Fusion cl = new Fusion();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        /*
         * Fusion arrive toujours en action1 et avec une action2 nulle.
         */
        public String expressionResult(String context, String form, Action act1) {
            // Ex.: _q + gajuk > _rajuk
            return "_"+context+"+"+form+" &rarr; "+"_"+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _q + gajuk > _rajuk
            return form1.substring(0,form1.length()-1)+form2;
        }

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{"a"+initAff, "i"+initAff, "u"+initAff};
//        }	    
	}

	/*
	 * VOICING
	 */
	static public class Voicing extends Action implements Cloneable {

        static public final int type = VOICING;
        static public final String expressionAng = "the voicing";
        static public final String expressionFran = "la sonorisation";

        public Voicing() {
        }

        public String surfaceForm(String form) {
            return form;
        }

        public Object clone() {
            Voicing cl = new Voicing();
            cl.strng = new String(this.strng);
            return cl;
        }

        /*
         * Voicing n'arrive qu'en action1, toujours avec une action2 nulle.
         */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: _k + miaq > _gmiaq
            return "_"+context+"+"+form+" &rarr; "+"_"+
            Roman.voicedOfOcclusiveUnvoicedLat(context.charAt(0))+form;
        }
        
        /*
         * 
         */
        public static String changementPhonologique(char context) {
            switch (context) {
            case 't': return "d";
            case 'k': return "g";
            case 'q': return "r";
            case 'p': return "b";
            default: return "";
            }
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _k + miaq > _gmiaq
            return form1.substring(0,form1.length()-1)+
            Roman.voicedOfOcclusiveUnvoicedLat(form1.charAt(form1.length()-1))+form2;
        }
        

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{Roman.voicedOfOcclusiveUnvoicedLat(context.charAt(0))+initAff};
//        }	    
	}


	/*
	 * NASALIZATION
	 */
	static public class Nasalization extends Action implements Cloneable {

        static public final int type = NASALIZATION;
        static public final String expressionAng = "the nasalization";
        static public final String expressionFran = "la nasalisation";

        public Nasalization() {
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        public Object clone() {
            Nasalization cl = new Nasalization();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        /*
         * Nasalization n'arrive qu'en action1, toujours avec une action2 nulle.
         */
        public String expressionResult(String context, String form, Action act1) {
            // Ex.: _k + miut > _ngmiut
            return "_"+context+"+"+form+" &rarr; "+"_"+
            Orthography.orthographyICILat(Roman.nasalOfOcclusiveUnvoicedLat(context.charAt(0)))+form;
        }

        public static String changementPhonologique(char context) {
            switch (context) {
            case 't': return "n";
            case 'k': return "ng";
            case 'q': return "r";
            case 'p': return "m";
            default: return "";
            }
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _k + miut > _ngmiut
            return form1.substring(0,form1.length()-1)+
            Orthography.orthographyICILat(Roman.nasalOfOcclusiveUnvoicedLat(form1.charAt(form1.length()-1)))+form2;
        }
        

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{Roman.nasalOfOcclusiveUnvoicedLat(context.charAt(0))+initAff};
//        }	    
	}

	/*
	 * CONDITIONAL NASALIZATION
	 */
	static class ConditionalNasalization extends Action implements Cloneable {

        static public final int type = CONDITIONALNASALIZATION;
        static public final String expressionAng = "the nasalization";
        static public final String expressionFran = "la nasalisation";
	    String condition = null; 
	    
        public ConditionalNasalization(String str) {
			condition = str;
        }
        
        private ConditionalNasalization() {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{Roman.nasalOfOcclusiveUnvoicedLat(context.charAt(0))+initAff};
//        }	
        
    	public String getCondition() {
    		return condition;
    	}

    	public String surfaceForm(String form) {
    	    return form;
    	}

        public Object clone() {
            ConditionalNasalization cl = new ConditionalNasalization();
            cl.strng = new String(this.strng);
            cl.condition = new String(this.condition);
            return cl;
        }

        /*
         * ConditionalNasalization n'arrive qu'en action1, toujours avec une action2 nulle.
         * Elle est d�finie par une action de la form if(), par exemple:
         * if(id:juq/1vn|id:ksaq/1nn|id:jaq/3vn,s,nas)
         */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: _q + mik > _rmik
            return "_"+context+"+"+form+" &rarr; "+"_"+
            Orthography.orthographyICILat(Roman.nasalOfOcclusiveUnvoicedLat(context.charAt(0)))+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _q + mik > _rmik
            return form1.substring(0,form1.length()-1)+
            Orthography.orthographyICILat(Roman.nasalOfOcclusiveUnvoicedLat(form1.charAt(form1.length()-1)))+form2;
        }
        

	}

	/*
	 * SPECIFIC DELETION
	 */
	static class SpecificSuppression extends Action implements Cloneable {

        static final public int type = SPECIFICDELETION;
        static public final String expressionAng = "the deletion";
        static public final String expressionFran = "la suppression";
        String suppressed = null;
        String condition = null;
	    
        public SpecificSuppression(String str) {
        	suppressed = str;
        }
        
        public SpecificSuppression(String cond, String str) {
        	suppressed = str;
            condition = cond;
        }
        
        private SpecificSuppression() {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{"a"+initAff,"i"+initAff,"u"+initAff};
//        }	
        
    	public String getSuppr() {
    		return suppressed;
    	}

        public String getCondition() {
            return condition;
        }

    	public String surfaceForm(String form) {
    	    return form;
    	}
        
        public Object clone() {
            SpecificSuppression cl = new SpecificSuppression();
            cl.strng = new String(this.strng);
            cl.suppressed = new String(this.suppressed);
            return cl;
        }

        public String expressionResult(String context, String form, Action act2) {
            return null;
        }

        public String combine(String form1, String form2, Action act2) {
            return null;
        }
        

	}

	/*
	 * CONDITIONAL DELETION
	 */
    
    /*
     * L'action conditionnelle est cr��e lors de la lecture d'actions de form if()
     * dans les champs d'actions des tables de la base de donn�es.  Pour les 
     * infixes, cette action ne survient que pour l'antipassif ji lorsqu'il est
     * pr�c�d� de uti, donc dans le context de voyelle: if(id:uti/1vv,s,n),
     * c'est-�-dire: si ji est pr�c�d� de uti, il supprime le i final de uti,
     * sinon il a une action neutre.  On retrouve aussi la suppression conditionnelle
     * chez quelques terminaisons nominales.
     */
	static class ConditionalSuppression extends Action implements Cloneable {

        static final public int type = CONDITIONALDELETION;
        static public final String expressionAng = "the deletion";
        static public final String expressionFran = "la suppression";
        String condition = null; 
	    
        public ConditionalSuppression(String str) {
			condition =
				str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }
        
        private ConditionalSuppression() {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{context+initAff};
//        }	
        
    	public String getCondition() {
    		return condition;
    	}

    	public String surfaceForm(String form) {
    	    return form;
    	}

        public Object clone() {
            ConditionalSuppression cl = new ConditionalSuppression();
            cl.strng = new String(this.strng);
            cl.condition = new String(this.condition);
            return cl;
        }

        /*
         *  Comme cette action n'arrive qu'en action1, et avec une action 2 nulle, on
         *  ne s'occupe pas de cette derni�re.
         */
        public String expressionResult(String context, String form, Action act1) {
            return "_"+context+"+"+form+" &rarr; "+"_"+form;
        }

        public String combine(String form1, String form2, Action act2) {
            return form1.substring(0,form1.length()-1)+form2;
        }
       

	}



	/*
	 * INSERTION
	 */
	static class Insertion extends Action implements Cloneable {

        static final public int type = INSERTION;
        static public final String expressionAng = "the insertion";
        static public final String expressionFran = "l'insertion";
	    String inserted = null;
	    
        public Insertion(String str) {
        	inserted =
				str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }
        
        private Insertion () {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            String insereSimp = Orthography.simplifiedOrthographyLat(insere); 
//            return new String[]{
//                    insereSimp.substring(insereSimp.length()-1)+initAff};
//        }	
        
    	public String getInsert() {
    	    return inserted;
    	}

    	public String surfaceForm(String form) {
    	    return inserted+form;
    	}

        public Object clone() {
            Insertion cl = new Insertion();
            cl.strng = new String(this.strng);
            cl.inserted = new String(this.inserted);
            return cl;
        }

        /*
         * Insertion en action1 arrive toujours avec une action2 nulle.
        */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: _a + uti > _ajjuti
            return "_"+context+"+"+form+" &rarr; "+"_"+context+inserted+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _a + uti > _ajjuti
            return form1+inserted+form2;
        }
        

	}



	/*
	 * DELETION AND INSERTION
	 */
    
    /*
     * Tr�s peu fr�quente.  Seulement en action1.
     */
	static class SuppressionAndInsertion extends Action implements Cloneable {

        static final public int type = DELETIONINSERTION;
        static public final String expressionAng = "the deletion";
        static public final String expressionFran = "la suppression";
	    String inserted = null;
	    
        public SuppressionAndInsertion(String str) {
        	inserted =
				str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }
        
        private SuppressionAndInsertion() {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            String insereSimp = Orthography.simplifiedOrthographyLat(insere);
//            return new String[]{
//                    insereSimp.substring(insereSimp.length()-1)+initAff};
//        }	
        
    	public String getInsert() {
    	    return inserted;
    	}

    	public String surfaceForm(String form) {
    	    return inserted+form;
    	}

        public Object clone() {
            SuppressionAndInsertion cl = new SuppressionAndInsertion();
            cl.strng = new String(this.strng);
            cl.inserted = new String(this.inserted);
            return cl;
        }

        /*
         * SuppressionInsertion n'arrive qu'en action1 et toujours avec NullAction en
         * action2.  Cette action2 est donc sans cons�quence et peut �tre ignor�e.
         */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: _t + usiq > _jjusiq
            return "_"+context+"+"+form+" &rarr; "+"_"+inserted+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _t + usiq > _jjusiq
            return form1.substring(0,form1.length()-1)+inserted+form2;
        }
        

	}

	/*
	 * VOWEL LENGHTENING
	 */
	static class VowelLengthening extends Action implements Cloneable {

        static public final int type = VOWELLENGTHENING;
        static public final String expressionAng = "the lengthening";
        static public final String expressionFran = "l'allongement";
        
        public VowelLengthening() {
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        public Object clone() {
            VowelLengthening cl = new VowelLengthening();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        /*
         * AllongementVoyelle n'arrive que comme action1 dans le context de
         * voyelle, et toujours avec une action2 nulle.  Pour les terminaisons
         * nominales seulement.
         */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: _i + k > _iik
            return "_"+context+"+"+form+" &rarr; "+"_"+context+context+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _i + k > _iik
            return form1+form1.charAt(form1.length()-1)+form2;
        }

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{"a"+initAff, "i"+initAff, "u"+initAff};
//        }	    
	}


	/*
	 * CANCELLATION
	 */
	static class Cancellation extends Action implements Cloneable {

        static public final int type = CANCELLATION;
        
        public Cancellation() {
        }

        public String surfaceForm(String form) {
            return null;
        }
        
        public Object clone() {
            Cancellation cl = new Cancellation();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        public String expressionResult(String context, String form, Action act1) {
            return null;
        }

        public String combine(String form1, String form2, Action act2) {
            return null;
        }

//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{};
//        }	    
	}


	/*
	 * SELF-DECAPITATION
	 */
	static class Selfdecapitation extends Action implements Cloneable {

        static public final int type = SELFDECAPITATION;
        static public final String expressionAng = "the self-decapitation ";
        static public final String expressionFran = "l'autod�capitation";

        public Selfdecapitation() {
        }

        public String surfaceForm(String form) {
            return form.substring(1);
        }
        
        public Object clone() {
            Selfdecapitation cl = new Selfdecapitation();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        public String expressionResult(String context, String form, Action act1) {
            return "_"+context+" &rarr; "+"_"+form.substring(1);
        }

        public String combine(String form1, String form2, Action act2) {
            return form1+form2.substring(1);
        }

//        public String[] finalRadInitAff(String context, String form) {
//            String secondAff = form.substring(1,2);
//            return new String[]{"a"+secondAff, "i"+secondAff, "u"+secondAff};
//        }	    
	}


	/*
	 * INSERTION AND VOWEL LENGTHNENING
	 */
	static class InsertionAndVowelLengthening extends Action implements Cloneable {

        static final public int type = INSERTIONVOWELLENGTHENING;
        static public final String expressionAng = "the insertion and lengthening";
        static public final String expressionFran = "l'insertion et l'allongement";
	    String inserted = null;
	    
        public InsertionAndVowelLengthening(String str) {
        	inserted =
				str.substring(str.indexOf('(') + 1, str.indexOf(')'));
        }
        
        private InsertionAndVowelLengthening() {
        }
        
//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            String insereSimp = Orthography.simplifiedOrthographyLat(insere);
//            return new String[]{
//                    insereSimp.substring(insereSimp.length()-1)+initAff};
//        }	
        
    	public String getInsert() {
    	    return inserted;
    	}

    	public String surfaceForm(String form) {
    	    return inserted+inserted+form;
    	}

        public Object clone() {
            InsertionAndVowelLengthening cl = new InsertionAndVowelLengthening();
            cl.strng = new String(this.strng);
            cl.inserted = new String(this.inserted);
            return cl;
        }

        /*
         * InsertionAllongementVoyelle arrive toujours en action1 avec une action2
         * nulle.  Cette action n'est en fait d�finie que pour les terminaisons 
         * nominales dans le context T.  La voyelle ins�r�e est 'i', le 'i' de 
         * remplissage associ� � la finale T.
         */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: 
            return "_"+context+"+"+form+" &rarr; "+"_"+context+inserted+inserted+form;
        }

        public String combine(String form1, String form2, Action act2) {
            return form1+inserted+inserted+form2;
        }
        

	}


	/*
	 * DELETION AND VOWEL LENGTHNENING
	 */
	static class SuppressionAndVowelLengthening extends Action implements Cloneable {

        static public final int type = DELETIONVOWELLENGTHENING;
        static public final String expressionAng = "the deletion and the lengthening";
        static public final String expressionFran = "la suppression et l'allongement";

        public SuppressionAndVowelLengthening() {
        }

        public String surfaceForm(String form) {
            return form;
        }
        
        
        public Object clone() {
            SuppressionAndVowelLengthening cl = new SuppressionAndVowelLengthening();
            cl.strng = new String(this.strng);
            return cl;
        }
        
        /*
         * SuppressionAllongementVoyelle n'arrive qu'en action1, toujours avec une
         * action2 nulle.
         */
        public String expressionResult(String context, String form, Action act2) {
            // Ex.: _aq + kkut > _aakkut
            return "_V"+context+"+"+form+" &rarr; "+"_VV"+form;
        }

        public String combine(String form1, String form2, Action act2) {
            // Ex.: _aq + kkut > _aakkut
            String sform = form1.substring(0,form1.length()-1); 
            return sform+sform.charAt(sform.length()-1)+form2;
        }


//        public String[] finalRadInitAff(String context, String form) {
//            String initAff = form.substring(0,1);
//            return new String[]{"a"+initAff, "i"+initAff, "u"+initAff};
//        }	
	}


}
