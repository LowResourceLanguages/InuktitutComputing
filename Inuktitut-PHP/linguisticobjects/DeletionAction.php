<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Action.php';
 
 	/*
	 * DELETION
	 */
	class Deletion_Action extends Action  {

	private static $instance;
	
        private function __construct() {
        	$this->type = Action::DELETION;
        	$this->engExpression = "the deletion";
        	$this->freExpression = "la suppression";
        }

	public static function singleton() {
		$logger = Logger::getLogger('Deletion_Action.singleton');
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
        
        /*
         * Si resAct1 n'est pas null, la m�thode est appel�e pour l'action2, resAct1
         * �tant le r�sultat de la m�thode pour l'action1.  Comme l'action2 concerne
         * la pr�sence de deux voyelles � la fin du radical, on modifie resAct1 en
         * cons�quence.  resAct1 a la forme g�n�rale _X > _Y.  Comme il s'agit ici
         * d'une action apr�s 2 voyelles, Y devrait �tre vide et la forme de resAct1
         * devrait �tre _.  _X est remplac� par _VVX et _ est remplac� par _VV.
         * On ajoute ensuite l'action2.  Deletion_Action comme action2 arrive avec
         * Neutral_Action et Deletion_Action comme action1.
         */
        /*
         * Deletion_Action en action1 a Nulle, Insertion, Self_Decapitation_Action, Deletion_Action
         * et Specific_Deletion_Action en action2.
         */
        public function resultExpression($context, $form, $act2) {
            $addForme = "+" . $form;
           if ($act2 == null) {
               // Ex.:  _k + paaq > _kpaaq
                return "_" . $context . $addForme . " &rarr; " . "_" . $form;
            } else {
                $act2Type = -1;
                $act2Type = $act2->type();
                 if ($act2Type == Action::NULLACTION) {
                     // Ex.:  _k + paaq > _paaq
                     return "_" . $context . $addForme . " &rarr; " . "_" . $form;
                 } else if ($act2Type == Action::DELETION) {
                     // Ex.:  _VVk + ilitaq > _Vilitaq
                     return "_" . "VV" . $context . $addForme . " &rarr; " . "_" . "V" . $form;
                 } else if ($act2Type == Action::SELFDECAPITATION) {
                     // Ex.: _VVk + uqqaq > _VVqqaq
                     return "_" . "VV" . $context . $addForme . " &rarr; " . "_" . "VV" . substring($form,1);
                 } else if ($act2Type == Action::INSERTION) {
                     // Ex.: _VVq + u > _VVngu
                     return "_" . "VV" . $context . $addForme . " &rarr; " . "_" . "VV" . $act2->getInsert() . $form;
                 } else if ($act2Type == Action::SPECIFICDELETION) {
                     // Ex.: _Vaq + it > _Vit
                     return "_" . "V" . $act2->getDeleted() . $context . $addForme . " &rarr; " . "_" . "V" . $form;
                 } else
                     return "";
            }
        }
//
        public function combine($form1, $form2, $act2) {
            if ($act2 == null) {
                // Ex.:  _k + paaq > _paaq
                 return substring($form1,0,strlen($form1)-1) . $form2;
             } else {
                 $act2Type = -1;
                 $act2Type = $act2->type();
                 if ($act2Type == Action::NULLACTION) {
                     // Ex.:  _k + paaq > _paaq
                     return substr($form1,0,strlen($form1)-1) . $form2;
                 } elseif ($act2Type == Action::DELETION) {
                     // Ex.:  _VVk + ilitaq > _Vilitaq
                     return substr($form1,0,strlen($form1)-2) . $form2;
                 } elseif ($act2Type == Action::SELFDECAPITATION) {
                     // Ex.: _VVk + uqqaq > _VVqqaq
                     return substr($form1,0,strlen($form1)-1) . substr($form2,1);
                 } elseif ($act2Type == Action::INSERTION) {
                     // Ex.: _VVq + u > _VVngu
                     return substr($form1,0,strlen($form1)-1) . $act2->getInsert() . $form2;
                 } elseif ($act2Type == Action::SPECIFICDELETION) {
                     // Ex.: _Vaq + it > _Vit
                     return substr($form1,0,strlen($form1)-2) . $form2;
                 } else
                     return "";
             }
        }
        
        public function applyOnStem($stem,$suffixe) {
        	return substr($stem,0,strlen($stem)-1);
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
