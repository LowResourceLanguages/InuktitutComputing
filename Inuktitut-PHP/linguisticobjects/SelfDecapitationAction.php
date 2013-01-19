<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Action.php';
 
 	/*
	 * SELFDECAPITATION
	 */
	class Self_Decapitation_Action extends Action  {

		private static $instance;
		
        private function __construct() {
        	$this->type = Action::SELFDECAPITATION;
        	$this->engExpression = "the self decapitation";
        	$this->freExpression = "l'autodécapitation";
        }

	public static function singleton() {
		$logger = Logger::getLogger('Self_Decapitation_Action.singleton');
		if (!isset (self::$instance)) {
			$c = __CLASS__;
			self::$instance = new $c;
		}
		$logger->debug(self::$instance);
		return self::$instance;
	}


        public function surfaceForm($form) {
            return substr($form,1);
        }
        
		public function applyOnStem($stem,$suffixe) {
			return $stem;
		}

        /*
         * Self_Decapitation_Action n'est d�finie qu'en action2, i.e. lorsqu'elle ne s'applique que<
         * lorsque le radical se termine par deux voyelles.
        */
        public function resultExpression($context, $form, $act2) {
            // Ex.: _a + uti > _ajjuti
            return "_" . $context . " + " . $form . " &rarr; " . "_" . $context
            	. substr($form,1);
        }
//
        public function combine($form1, $form2, $act2) {
            // Ex.: _a + uti > _ajjuti
            return $form1 . substr($form2,1);
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
