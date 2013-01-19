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
* NEUTRAL
*/

class Neutral_Action extends Action {

	private static $instance;

	private function __construct() {
		$this->type = Action::NEUTRAL;
		$this->engExpression = "the neutral action";
		$this->freExpression = "l'action neutre";
	}

	public static function singleton() {
		$logger = Logger::getLogger('Neutral_Action.singleton');
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

	public function __clone() {
	}

	public function applyOnStem($stem, $suffixe) {
		return $stem;
	}

	/*
	 * Neutral_Action en action1 a Nulle, Deletion_Action, Insertion_Action et Self_Decapitation_Action
	 * en action2.
	 */
	public function resultExpression($context, $form, $act2) {
		$addForme = "+" . $form;
		if ($act2 == null) {
			// Ex.:  _k + paaq > _kpaaq
			return "_" . $context . $addForme . " &rarr; " . "_" . $context . $form;
		} else {
			$act2Type = -1;
			$act2Type = $act2->type();
			if ($act2Type == Action::NULLACTION) {
				// Ex.:  _k + paaq > _kpaaq
				return "_" . $context . $addForme . " &rarr; " . "_" . $context . $form;
			}
			elseif ($act2Type == Action::DELETION) {
				// Ex.:  _VV + ilitaq > _Vilitaq
				return "_" . "VV" . $addForme . " &rarr; " . "_" . "V" . $form;
			}
			elseif ($act2Type == Action::SELFDECAPITATION) {
				// Ex.: _VV + uqqaq > _VVqqaq
				return "_" . "VV" . $addForme . " &rarr; " . "_" . "VV" . substr($form, 1);
			}
			elseif ($act2Type == Action::INSERTION) {
				// Ex.: _VV + ut > _VVjjut
				return "_" . "VV" . $addForme . " &rarr; " . "_" . "VV" . $act2->getInsert() . $form;
			}
			return "";
		}
	}
	//
	public function combine($form1, $form2, $act2) {
		if ($act2 == null) {
			// Ex.:  aaqqik + taq > aaqqiktaq
			return $form1 . $form2;
		} else {
			$act2Type = -1;
			$act2Type = $act2->type();
			if ($act2Type == Action::NULLACTION) {
				// Ex.:  aaqqik + taq > aaqqiktaq
				return $form1 . $form2;
			}
			elseif ($act2Type == Action::DELETION) {
				// Ex.:  _VV + ilitaq > _Vilitaq
				return substr($form1, 0, strlen($form1) - 1) . $form2;
			}
			elseif ($act2Type == Action::SELFDECAPITATION) {
				// Ex.: _VV + uqqaq > _VVqqaq
				return $form1 . substr($form2, 1);
			}
			elseif ($act2Type == Action::INSERTION) {
				// Ex.: _VV + ut > _VVjjut
				return $form1 . $act2->getInsert() . $form2;
			}
			return "";
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
