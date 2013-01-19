<?php
/*
 * Created on 2011-02-08
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'lib/log4php/Logger.php';
 require_once 'linguisticobjects/LinguisticObjects.php';
 
 class Word {
 	
 	private $morphemes = array();
 	
 	public function __construct ( $list_of_morpheme_ids ) {
 		$logger = Logger::getLogger('Word.__construct');
 		$list_of_morphemes = array();
 		foreach ($list_of_morpheme_ids as $a_morpheme_id) {
 			$logger->debug("\$a_morpheme_id= '$a_morpheme_id'");
 			$object = LinguisticObjects::getObject($a_morpheme_id);
 			$logger->debug("id de l'objet : '" . $object->id() . "'");
 			array_push($list_of_morphemes, $object);
 		}
 		$this->morphemes = $list_of_morphemes;
 	}
 	
 	public function morphemes() {
 		return $this->morphemes;
  	}
  	
  	public function idsOfMorphemes() {
  		$ids = array();
 		$logger = Logger::getLogger('Word.idsOfMorphemes');
 		$logger->debug("#morphemes= ".count($this->morphemes));
  		foreach ($this->morphemes as $a_morpheme)
  			array_push($ids, $a_morpheme->id()); 
  		$logger->debug("#ids= ".count($ids));
  		return $ids;
  	}
 	
 	public function surfaceForm() {
 		$logger = Logger::getLogger('Word.surfaceForm');
 		$morphemes = $this->morphemes;
 		$first_morpheme = array_shift($morphemes);
 		$stem = $first_morpheme->morpheme;
 		$forms = $this->_surfaceFormOnStems(array($stem), $morphemes);
 		return $forms;
 	}
// 	
 	public function _surfaceFormOnStems( $stems, $next_morphemes ) {
 		// appliquer les morphemes suivants à chaque radical par récursion
 		// chaque morphème peut avoir plus d'un behaviour pour un contexte donnée,
 		// d'où plusieurs radicaux formés pour chaque morphème.
 		$logger = Logger::getLogger('Word._surfaceFormOnStems');
 		$forms = array();
 		foreach ($stems as $a_stem) {
 			$new_stems_for_a_stem = $this->_surfaceFormOnOneStem($a_stem, $next_morphemes);
 			$forms = array_merge($forms, $new_stems_for_a_stem);
 		}
 		return $forms;
 	} 
// 	
 	public function _surfaceFormOnOneStem ($stem, $next_morphemes) {
 		$logger = Logger::getLogger('Word._surfaceFormOnOneStem');
 		$logger->debug("# morphemes avant shift= ".count($next_morphemes));
 		if ( count($next_morphemes) == 0 ) {
 			return array($stem);
 		}
 		else {
 			$a_next_morpheme = array_shift($next_morphemes);
 			$logger->debug("# morphemes apres shift= ".count($next_morphemes));
 			$stems_for_next_morpheme = $a_next_morpheme->addToStem($stem);
 			$logger->debug("radicaux pour le morpheme= ".implode('; ',$stems_for_next_morpheme));
 			$other_stems = $this->_surfaceFormOnStems($stems_for_next_morpheme, $next_morphemes);
 			return $other_stems;
 		}
 	}
 	
 }
?>
