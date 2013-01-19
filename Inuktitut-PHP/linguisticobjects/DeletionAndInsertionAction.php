<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Action.php';
 require_once 'lib/log4php/Logger.php';
 
 	/*
	 * DELETION ET INSERTION
	 */
	class Deletion_and_Insertion_Action extends Action  {

		private $insertion;
		private static $instance = array();
		
        public function __construct($inser) {
        	$this->type = Action::DELETIONINSERTION;
        	$this->engExpression = "the deletion";
        	$this->freExpression = "la suppression";
        	$this->insertion = $inser;
        }

     public static function singleton($insertion) {
		$logger = Logger::getLogger('Deletion_and_Insertion_Action.singleton');
		if (!array_key_exists ($insertion,self::$instance)) {
			$c = __CLASS__;
			self::$instance[$insertion] = new $c($insertion);
		}
		$logger->debug(self::$instance[$insertion]);
		return self::$instance[$insertion];        	
        }

        public function surfaceForm($form) {
            return $form;
        }
        
        public function insertedChars() {
        	return $this->insertion;
        }

		public function applyOnStem($stem,$suffixe) {
			return
			substr($stem, 0, strlen($stem)-1)
			. $this->insertion;	
		}
		
        /*
         * Deletion_and_Insertion_Action n'arrive qu'en action1 et toujours avec Nulle en
         * action2.  Cette action2 est donc sans cons�quence et peut �tre ignor�e.
         */
        public function resultExpression($context, $form, $act2) {
            // Ex.: _t + usiq > _jjusiq
            return "_" . $context . " + " . $form . " &rarr; " . "_" . $this->insertion . $form;
        }

        public function combine($form1, $form2, $act2) {
            // Ex.: _t + usiq > _jjusiq
            return substr($form1,0,strlen($form1)-1) . $this->insertion . $form2;
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
