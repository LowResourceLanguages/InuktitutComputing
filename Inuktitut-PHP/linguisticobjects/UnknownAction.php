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
* UNKNOWN
*/

class Unknown_Action extends Action {

	private static $instance;

	private function __construct() {
		$this->type = Action::UNKNOWN;
		$this->engExpression = "the unknown action";
		$this->freExpression = "l'action inconnue";
	}

	public static function singleton() {
		$logger = Logger::getLogger('Unknown_Action.singleton');
		if (!isset (self::$instance)) {
			$c = __CLASS__;
			self::$instance = new $c;
		}
		$logger->debug(self::$instance);
		return self::$instance;
	}

	public function surfaceForm($form) {
		return '?';
	}

	public function applyOnStem($stem, $suffixe) {
		return '?';
	}

	/*
	 * Neutral_Action en action1 a Nulle, Deletion_Action, Insertion_Action et Self_Decapitation_Action
	 * en action2.
	 */
	public function resultExpression($context, $form, $act2) {
		return '?';
	}
	//
	public function combine($form1, $form2, $act2) {
		return '?';	}

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
