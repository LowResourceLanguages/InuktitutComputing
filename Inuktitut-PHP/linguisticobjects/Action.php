<?php 
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Action.php
//
// Type/File type:		code php
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: classe pour les actions des suffixes et terminaisons.
//
// -----------------------------------------------------------------------

require_once 'script/Roman.php';
require_once 'linguisticobjects/Behaviour.php';
require_once 'lib/log4php/Logger.php';

abstract class Action {
	const NULLACTION = 0;
	const INSERTION = 1;
	const SPECIFICDELETION = 2;
	const DELETION = 3;
	const VOICING = 4;
	const NASALIZATION = 5;
	const NEUTRAL = 6;
//	const FUSION = 7;
	const ASSIMILATION = 8;
	const SPECIFICASSIMILATION = 9;
	const VOWELLENGTHENING = 11;
//	const CANCELLATION = 12;
	const SELFDECAPITATION = 13;
	const INSERTIONVOWELLENGTHENING = 14;
	const DELETIONVOWELLENGTHENING = 15;
//	const CONDITIONALDELETION = 16;
	const DELETIONINSERTION = 17;
//	const MORPHEMECONDITIONALDELETION = 18;
//	const CONDITIONALNASALIZATION = 19;
//	const CONDITIONALSPECIFICDELETION = 20;
	const UNKNOWN = 1000;
	
	protected $string;
	protected $type;
	protected $engExpression;
	protected $freExpression;
	
	abstract public function surfaceForm($form);
    abstract public function resultExpression($context, $form, $act2);
    abstract public function combine($form1, $form2, $act2);
    abstract public function applyOnStem($stem,$suffix);

//	abstract String[] finalRadInitAff(String c, String f);
    
	public function type() {
		return $this->type;
	}
	
    public function expression($lang) {
    	if ( $lang=='en' )
    		return $this->engExpression;
    	else
    		return $this->freExpression;
   	}
    
	public function defString($string) {
		$this->string = $string;
	}
	
	public function toString() {
		return $this->string;
	}

	public function getInsert() {
		return null;
	}

	public function getDeleted() {
		return null;
	}

	public function getAssimA() {
		return null;
	}
	


}


//
///	/*
//	 * INSERTION ET ALLONGEMENT DE VOYELLE
//	 */
//	static class InsertionEtAllongementDeVoyelle extends Action implements Cloneable {
//
//        static final public int type = INSERTIONVOWELLENGTHENING;
//        static public final String engExpression = "the insertion and lengthening";
//        static public final String freExpression = "l'insertion et l'allongement";
//	    String insere = null;
//	    
//        public function InsertionEtAllongementDeVoyelle(String str) {
//			insere =
//				str.substring(str.indexOf('(') + 1, str.indexOf(')'));
//        }
//        
//        private InsertionEtAllongementDeVoyelle() {
//        }
//        
////        public function String[] finalRadInitAff(String context, String form) {
////            String initAff = form.substring(0,1);
////            String insereSimp = Orthography.orthographeSimplifieeLat(insere);
////            return new String[]{
////                    insereSimp.substring(insereSimp.length()-1)+initAff};
////        }	
//        
//    	public function String getInsert() {
//    	    return insere;
//    	}
//
//    	public function String surfaceForm(String forme) {
//    	    return insere+insere+forme;
//    	}
//
//        public function Object clone() {
//            InsertionEtAllongementDeVoyelle cl = new InsertionEtAllongementDeVoyelle();
//            cl.chaine = new String(this.chaine);
//            cl.insere = new String(this.insere);
//            return cl;
//        }
//
//        /*
//         * InsertionAllongementVoyelle arrive toujours en action1 avec une action2
//         * nulle.  Cette action n'est en fait d�finie que pour les terminaisons 
//         * nominales dans le contexte T.  La voyelle ins�r�e est 'i', le 'i' de 
//         * remplissage associ� � la finale T.
//         */
//        public function String resultExpression(String contexte, String forme, Action act2) {
//            // Ex.: 
//            return "_"+contexte+"+"+forme+" &rarr; "+"_"+contexte+insere+insere+forme;
//        }
//
//        public function String combine(String forme1, String forme2, Action act2) {
//            return forme1+insere+insere+forme2;
//        }
//        
//
//	}
//
//
//	/*
//	 * DELETION ET ALLONGEMENT DE VOYELLE
//	 */
//	static class SuppressionEtAllongementDeVoyelle extends Action implements Cloneable {
//
//        static public final int type = DELETIONVOWELLENGTHENING;
//        static public final String engExpression = "the deletion and the lengthening";
//        static public final String freExpression = "la suppression et l'allongement";
//
//        public function SuppressionEtAllongementDeVoyelle() {
//        }
//
//        public function String surfaceForm(String forme) {
//            return forme;
//        }
//        
//        
//        public function Object clone() {
//            SuppressionEtAllongementDeVoyelle cl = new SuppressionEtAllongementDeVoyelle();
//            cl.chaine = new String(this.chaine);
//            return cl;
//        }
//        
//        /*
//         * SuppressionAllongementVoyelle n'arrive qu'en action1, toujours avec une
//         * action2 nulle.
//         */
//        public function String resultExpression(String contexte, String forme, Action act2) {
//            // Ex.: _aq + kkut > _aakkut
//            return "_V"+contexte+"+"+forme+" &rarr; "+"_VV"+forme;
//        }
//
//        public function String combine(String forme1, String forme2, Action act2) {
//            // Ex.: _aq + kkut > _aakkut
//            String sforme = forme1.substring(0,forme1.length()-1); 
//            return sforme+sforme.charAt(sforme.length()-1)+forme2;
//        }
//
//
////        public function String[] finalRadInitAff(String context, String form) {
////            String initAff = form.substring(0,1);
////            return new String[]{"a"+initAff, "i"+initAff, "u"+initAff};
////        }	
//	}


?>