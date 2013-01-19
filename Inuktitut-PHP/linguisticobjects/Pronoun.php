<?php 

//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Pronoun.php
//
// Type/File type:		code PHP
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: Classe Pronoun
//
// -----------------------------------------------------------------------
require_once 'linguisticobjects/Affix.php';
require_once 'lib/log4php/Logger.php';
require_once 'linguisticobjects/Morpheme.php';

// TODO-BF-Inuktitut: ili and uva do not compile into objects. See why.
// TODO-BF-Inuktitut: translate everything to English
class Pronoun extends Root {
	//morpheme;variant;key;type;nature;per;number;engMean;freMean;composition;condOnNext
	protected $person = NULL;
	protected $number = NULL;
	
 	public function __construct($fieldsAndValues) {
		parent::__construct($fieldsAndValues);
 		$this->morpheme = $fieldsAndValues['morpheme'];
		$this->engMeaning = $fieldsAndValues['engMean'];
		$this->frenchMeaning = $fieldsAndValues['freMean'];
		if ( isset($fieldsAndValues['dbName']) )
			$this->dbName = $fieldsAndValues['dbName'];
		if ( isset($fieldsAndValues['tableName']) )
			$this->tableName = $fieldsAndValues['tableName'];		
		$this->variant = $fieldsAndValues['variant'];
		$this->key = $fieldsAndValues['key'];
		if ($this->key==NULL || $this->key=='')
			$this->key = '1';
		if ( isset($fieldsAndValues['composition']) )
			$this->comb = $fieldsAndValues['composition'];
		if ($this->comb != NULL && $this->comb!='') {
			$this->combinedMorphemes = explode('+',$this->comb);
			if (count($this->combinedMorphemes) < 2) {
				$this->combinedMorphemes = NULL;
			} else {
				$rootId = $this->combinedMorphemes[0];
			}
		}
		
		$this->number = $fieldsAndValues['number'];
		if ($this->number==NULL || $this->number=='')
		    $this->number = 's';
		$this->person = $fieldsAndValues['per'];
		if ($this->person==NULL || $this->person=='')
		    $this->number = '3';
		
		$this->morphemeID =self::make_id($fieldsAndValues);
 	}
    
    //------------------------------------------------------------------------   

//  	static public function make_id ($field_values) {
//  		$morpheme_id_object = new ID_Morpheme(
//  				$field_values['morpheme'],
//  				array ( $field_values['key'], $field_values['type'] )
//  		);
//  		return $morpheme_id_object;
//  	}
 	
	public function isFirstPerson() {
		if ( $this->person == "1" )
			return true;
		else
			return false;
	}
	
	public function isSecondPerson() {
		if ( $this->person == "2")
			return true;
		else
			return false;
	}
	
	public function isThirdPerson() {
		if ( $this->person == "3" )
			return true;
		else
			return false;
	}

	public function parseMeaning($string) {
		return $string;
	}
}

?>
