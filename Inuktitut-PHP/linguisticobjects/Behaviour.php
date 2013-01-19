<?php
/*
 * Created on 2011-02-03
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'lib/log4php/Logger.php';
 require_once 'linguisticobjects/ActionFactory.php';
 require_once 'script/Roman.php';

 class Behaviour {
 	
 	private $context;
 	private $actionOnStem = null;
 	private $actionVV = null;
 	private $actionNotVV = null;
 	private $formOfAffix;
 	private $conditions_of_application_of_actions = null;
 	private $action_if_conditions_dont_apply = null;
 	
 	static private $delimiter_of_actions = '-';
 	
 	public function __construct ($context, $string_of_actions, $form_of_the_affix,
 				$conditions=null, $string_of_actions_by_default=null) {
 		$logger = Logger::getLogger('Behaviour.__construct');  		
 		$logger->debug("\$context= '$context'");
 		$logger->debug("\$string_of_actions= '$string_of_actions'");
 		$strings_of_actions = explode(self::$delimiter_of_actions,$string_of_actions);
 		$actions = array_map('ActionFactory::make',$strings_of_actions);
 		$logger->debug("nb. actions= ".count($actions));
 		$this->actionOnStem = $actions[0];
 		if ( count($actions) > 1 ) 
 			$this->actionVV = $actions[1];
 		if ( count($actions) > 2 ) 
 			$this->actionNotVV = $actions[2];
 		$this->context = $context;
 		$this->formOfAffix = $form_of_the_affix;
 		$this->conditions_of_application_of_actions = $conditions;
 		$this->action_if_conditions_dont_apply = $string_of_actions_by_default;
 	}
 	
 	public function context() {
 		return $this->context;
 	}
 	
 	public function formOfAffix() {
 		return $this->formOfAffix;
 	}
 	
 	public function actionOnStem() {
 		return $this->actionOnStem;
 	}
 	
 	public function actionVV() {
 		return $this->actionVV;
 	}
 	
 	public function actionNotVV() {
 		return $this->actionNotVV;
 	}
 	
 	public function applyOnStem ($stem) {
		// Ex. : qaujisaqtau + innaq > qaujisaqtaunginnaq
		// innaq : actionOnStem = n, actionVV = i(ng)
		// Appliquer l'actionOnStem (de la forme2) � la forme1 : qaujisaqtau
		// Appliquer l'actionVV (de la forme2) au r�sultat de l'actionOnStem : qaujisaqtaunginnaq
 		$logger = Logger::getLogger('Action.combiner');  		
 		$logger->debug("\$stem= '$stem'");
 		$new_stem = '';
 		$suffix = $this->formOfAffix;
		$new_stem = $this->actionOnStem->applyOnStem($stem,$this->formOfAffix);
		if ( strlen($new_stem) > 2
			&& Roman::isVowel(substr($new_stem,strlen($new_stem)-1,1)) 
			&& Roman::isVowel(substr($new_stem,strlen($new_stem)-2,1)) ) {
			if ( $this->actionVV != null ) {
				$new_stem = $this->actionVV->applyOnStem($new_stem,$this->formOfAffix);
				$suffix = $this->actionVV->surfaceForm($suffix);	
			}
		}
		else {
			if ( $this->actionNotVV != null ) {
				$new_stem = $this->actionNotVV->applyOnStem($new_stem,$this->formOfAffix);
				$suffix = $this->actionNotVV->surfaceForm($suffix);					
			}
		}
		$new_stem .= $suffix;
		return $new_stem;
	}
	
	static public function makeBehaviours($context,$string_of_actions,$string_of_forms_of_the_affix) {
		$logger = Logger::getLogger('Behaviour.makeBehaviours');
		$string_of_actions = trim($string_of_actions);
		$string_of_forms_of_the_affix = trim($string_of_forms_of_the_affix);
		$string_of_actions = preg_replace('/\s+/',' ',$string_of_actions);
		$string_of_forms_of_the_affix = preg_replace('/\s+/',' ',$string_of_forms_of_the_affix);
		$logger->debug("formes= '$string_of_forms_of_the_affix");
		$logger->debug("formes= '$string_of_actions");
		$forms_of_the_affix = explode(' ',$string_of_forms_of_the_affix);
		$actions = explode(' ',$string_of_actions);
		if ( count($forms_of_the_affix) != count($actions) ) {
			$logger->error("Il manque une forme ou une action : \nformes= '$string_of_forms_of_the_affix'\nactions= '$string_of_actions'");
		}
		$behaviours = array();
		for ($i=0; $i<count($forms_of_the_affix); $i++) {
			$action = $actions[$i];
			$form = $forms_of_the_affix[$i];
			$behaviour = new Behaviour($context,$action,$form);	
			array_push($behaviours,$behaviour);
		}
		return $behaviours;
	}
 	
 }
?>
