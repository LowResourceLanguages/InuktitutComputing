<?php

// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		NounRoot.php
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
// Description: Classe Nom
//
// -----------------------------------------------------------------------

require_once "linguisticobjects/Root.php";
require_once "lib/log4php/Logger.php";
require_once 'linguisticobjects/ID_Morpheme.php';


class NounRoot extends Root {
	//morpheme,variant,key,type,transitivity,nature,number,compositionRoot,
	//plural,intransSuffix,transSuffix,antipassive,engMean,freMean,source,dialect,cf,END
	
    private $compositionRoot = NULL;
    private $number = NULL;
    private $plural = NULL;
    private $idsOfCompositesWithThisRoot = NULL;
    //
	
	public function __construct ($fieldsAndValues) {
 		$logger = Logger::getLogger('NounRoot.__construct');  		
 		$logger->debug("new NounRoot");
		parent::__construct($fieldsAndValues);
 		$logger->debug("new NounRoot, apr�s parent::__construct");
		$this->number = $fieldsAndValues['number'];
		if ($this->number==NULL || $this->number=='')
		    $this->number = 's';
        $this->cf = $fieldsAndValues['cf'];
        if ($this->cf != NULL && $this->cf!='') $this->cfs = explode(' ',$this->cf);
        $this->dialect = $fieldsAndValues['dialect'];
        $this->source = $fieldsAndValues['source'];
		if ($this->source == NULL || $this->source == '')
			$this->source = 'A2';
		$this->sources = explode(' ',$this->source);
		// Racine de composition pour les racines duelles et plurielles
		$this->compositionRoot = $fieldsAndValues['compositionRoot'];
		$this->morphemeID =self::make_id($fieldsAndValues);
 		$logger->debug("new NounRoot, exit");
	}
	
	//-------------------------------------------------------------------------------------------------------
	public function parseMeaning ($string) {
		return self::_parseNounMeaning($string);
	}

	public function _parseNounMeaning ($string) {
		$pattern_of_meaning_nb = '/(\((\d)\))/';
		return self::_parseForPattern($string, $pattern_of_meaning_nb);
	}

	//-------------------------------------------------------------------------------------------------------
    
	public function getCompositionRoot() {
	    return $this->compositionRoot;
	}
	
	//---------------------------------------------------------------------------------------------------------

	public function isSingular() {
		if ( $this->number == 's' )
			return true;
		else
			return false;
	}
	
	public function isDual() {
		if ( $this->number == 'd' )
			return true;
		else
			return false;
	}
	
	public function isPlural() {
		if ( $this->number == 'p' )
			return true;
		else
			return false;
	}
	

}
?>