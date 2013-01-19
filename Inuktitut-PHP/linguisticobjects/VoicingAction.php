<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Action.php';
 require_once 'script/Roman.php';
 require_once 'lib/log4php/Logger.php';
 
 	/*
	 * VOICING
	 */
	class Voicing_Action extends Action  {

	private static $instance;
	
        private function __construct() {
        	$this->type = Action::VOICING;
        	$this->engExpression = "the voicing";
        	$this->freExpression = "la sonorisation";
        }

	public static function singleton() {
		$logger = Logger::getLogger('Voicing_Action.singleton');
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
        	return
        	substr($stem,0,strlen($stem)-1)
        	. Orthography::latinICIOrthography(
        		Roman::latinVoicedOfUnvoicedStopConsonant($stem[strlen($stem)-1]));
        }

         /*
         * Voicing_Action n'arrive qu'en action1, toujours avec une action2 nulle.
         */
        public function resultExpression($context, $form, $act2) {
            // Ex.: _k + miaq > _gmiaq
            return "_". $context . " + " . $form . " &rarr; " . "_" .
            	Roman::latinVoicedOfUnvoicedStopConsonant(substr($context,0,1)) . $form;
        }
//
        public function combine($form1, $form2, $act2) {
            // Ex.: _k + miaq > _gmiaq
            return substr($form1,0,strlen($form1)-1) .
            	Roman::latinVoicedOfUnvoicedStopConsonant($form1[strlen($form1)-1]) . $form2;
        }

        public function phonologicalChange($carDeContexte) {
            switch ($carDeContexte) {
            case 't': return 'd'; break;
            case 'k': return'g'; break;
            case 'q': return 'r'; break; 
            case 'p': return 'b'; break;
            default: return ''; break; 
            }
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
