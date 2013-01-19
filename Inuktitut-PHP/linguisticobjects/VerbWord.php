<?php
/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Nov 2, 2006
 * par / by Benoit Farley
 * 
 */

require_once "lib/log4php/Logger.php";

require_once 'linguisticobjects/Morpheme.php';
require_once 'linguisticobjects/ID_Morpheme.php';

class VerbWord {

    private $verb;
    private $pastParticiple;
    public static $type = 'vw';
    private $field_values;

 	public function __construct($fieldsAndValues) {
 		$logger = Logger::getLogger('VerbWord.__construct');
 		$this->field_values = $fieldsAndValues;
        $this->verb = $fieldsAndValues['verb'];
        $this->pastParticiple = $fieldsAndValues['passive'];
    }
    
    public function getVerb () {
    	return $this->verb;
    }

    public function getPastParticiple () {
    	return $this->pastParticiple;
    }
    
    public function id() 
    {
 		$logger = Logger::getLogger('VerbWord.id');
    	$id = self::make_id($this->field_values);
    	$logger->debug("id: \n".print_r($id,true));
    	return $id->toString();
    }

    static public function generates_multiple_objects() {
    	return false;
    }
    
    static public function make_id ($field_values) {
    	$morpheme_id_object = new ID_Morpheme(
    			$field_values['verb'],
    			array(self::$type)
    	);
    	return $morpheme_id_object;
    }
    
}
?>