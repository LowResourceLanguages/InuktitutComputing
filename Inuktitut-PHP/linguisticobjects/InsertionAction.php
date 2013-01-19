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
	 * INSERTION
	 */
	class Insertion_Action extends Action  {

        private $insert;
        private static $instance = array();

        private function __construct($insertion) {
        	$this->type = Action::INSERTION;
        	$this->engExpression = "the insertion";
        	$this->freExpression = "l'insertion";
            $this->insert = $insertion;
        }
        
        public static function singleton($insertion) {
		$logger = Logger::getLogger('Insertion_Action.singleton');
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
			return $this->insert;
		}
		
		public function applyOnStem($stem,$suffixe) {
			return $stem . $this->insert;
		}

        /*
         * Insertion_Action en action1 arrive toujours avec une action2 nulle.
        */
        public function resultExpression($context, $form, $act2) {
            // Ex.: _a + uti > _ajjuti
            return "_" . $context . " + " . $form . " &rarr; " . "_" . $context
            	. $this->insert . $form;
        }
//
        public function combine($form1, $form2, $act2) {
            // Ex.: _a + uti > _ajjuti
            return $form1 . $this->insert . $form2;
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
