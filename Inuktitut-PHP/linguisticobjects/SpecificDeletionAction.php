<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Action.php';
 
 	/*
	 * DELETION SPECIFIQUE
	 * 
	 * Cette action n'appara�t qu'en action2 seulement, i.e. lorsque le radical auquel est ajout�
	 * le suffixe finit avec 2 voyelles.
	 */
	class Specific_Deletion_Action extends Action  {
		
		private $deleted;
		private static $instance = array();

        public function __construct($deletedVowel) {
        	$this->type = Action::SPECIFICDELETION;
        	$this->engExpression = "the deletion";
        	$this->freExpression = "la suppression";
        	$this->deleted = $deletedVowel;
        }

        public static function singleton($deletedVowel) {
			$logger = Logger::getLogger('Specific_Deletion_Action.singleton');
			if (!array_key_exists ($deletedVowel,self::$instance)) {
				$c = __CLASS__;
				self::$instance[$deletedVowel] = new $c($deletedVowel);
			}
			$logger->debug(self::$instance[$deletedVowel]);
			return self::$instance[$deletedVowel];        	
        }


        public function surfaceForm($form) {
            return $form;
        }
        
        public function deleted() {
        	return $this->deleted;
        }
        
        public function applyOnStem($stem,$suffix) {
        	if ( $stem[strlen($stem)-1]==$this->deleted )
        		return substr($stem, 0, strlen($stem)-1);
        	else
        		return $stem;
        }

        /*
         * Specific_Deletion_Action n'arrive qu'en action2, i.e. lorsque le radical auquel le suffixe
         * est ajout� finit par 2 voyelles. Comme resultExpression n'est ex�cut�e que sur
         * des action1, et qu'en tant que fonction abstraite, elle doit �tre d�finie dans la classe,
         * on lui fait simplement retourner 'null'.
         */
        public function resultExpression($context, $form, $act2) {
			return null;
        }

		/*
		 * M�me commentaire que pour 'resultExpression' ci-dessus.
		 */
        public function combine($form1, $form2, $act2) {
			return null;
        }

        
//        public function String[] finalRadInitAff(String context, String form) {
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

 
?>
