<?php

//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		DemonstrativeEnding.php
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
// Description: Class of demonstrative endings
//
// -----------------------------------------------------------------------

require_once 'linguisticobjects/Affix.php';
require_once 'linguisticobjects/Morpheme.php';
require_once 'lib/log4php/Logger.php';

class DemonstrativeEnding extends Affix {
	
	private $nombre = null;
	private $cas;
	
	public function __construct( $field_values) {
 		$logger = Logger::getLogger('DemonstrativeEnding.__construct');  		
		$this->morpheme = $field_values['morpheme'];
		$this->type = $field_values['type'];
		$this->cas = $field_values['case'];
		$this->nombre = $field_values['number'];
		$this->engMeaning = $field_values['engMean'];
		$this->frenchMeaning = $field_values['freMean'];
		if ( array_key_exists('dbName',$field_values) )
			$this->dbName = $field_values['dbName'];
		if ( array_key_exists('tableName',$field_values) )
			$this->tableName = $field_values['tableName'];
		$this->morphemeID =self::make_id($field_values);
	}

	static public function make_id ($field_values) {
		$info_for_signature = array(
				$field_values['type'],
				$field_values['case'],
		);
		if ( $field_values['number'] != '' ) {
			array_push( $info_for_signature, $field_values['number'] );
		}
		$morpheme_id_object = new ID_Morpheme(
				$field_values['morpheme'],
				$info_for_signature
		);
		return $morpheme_id_object;
	}
	
	public function getTransitivityConstraint() {
		return null;
	}
	
	public function agreesWithTransitivity($trans) {
		return true;
	}
	
	
	
}

?>