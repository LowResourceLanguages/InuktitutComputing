<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Action.php';
 require_once 'script/Roman.php';
 
 	/*
	 * VOICING
	 */
class Vowel_Lengthening_Action extends Action  {

	private static $instance;
	
    private function __construct() {
        	$this->type = Action::VOWELLENGTHENING;
        	$this->engExpression = "the lengthening";
        	$this->freExpression = "l'allongement";
        }

	public static function singleton() {
		$logger = Logger::getLogger('Vowel_Lengthening_Action.singleton');
		if (!isset (self::$instance)) {
			$c = __CLASS__;
			self::$instance = new $c;
		}
		$logger->debug(self::$instance);
		return self::$instance;
	}

    public function surfaceForm($form) {
            return $form;
        }
        
        public function applyOnStem($stem,$suffixe) {
            if ( strlen($stem) < 2 )
            	return $stem . $stem[strlen($stem)-1];
            elseif ( ! Roman::isVowel($stem[strlen($stem)-2]) )
            	return $stem . $stem[strlen($stem)-1];
            else
            	return $stem;
        }

         /*
         * Allongement de voyelle n'arrive qu'en action1, toujours avec une action2 nulle,
         * et toujours sur un radical en voyelle.
         */
        public function resultExpression($context, $form, $act2) {
            // Ex.: _V + k > _VVk
            return "_". $context . " + " . $form . " &rarr; " 
            	. "_" . $context . $context . $form;
        }
//
        public function combine($form1, $form2, $act2) {
            // Ex.: _V + k > _VVk
            if ( strlen($form1) < 2 )
            	return $form1 . $form1[strlen($form1)-1] . $form2;
            elseif ( ! Roman::isVowel($form1[strlen($form1)-2]) )
            	return $form1 . $form1[strlen($form1)-1] . $form2;
            else
            	return $form1 . $form2;
        }


        
////        public function String[] finalRadInitAff(String context, String form) {
////            String initAff = form.substring(0,1);
////            return new String[]{"a"+initAff, "i"+initAff, "u"+initAff};
////        }	    
	
	}

 
?>
