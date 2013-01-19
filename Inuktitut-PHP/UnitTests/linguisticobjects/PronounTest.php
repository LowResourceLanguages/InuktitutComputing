<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/Pronoun.php';
require_once 'linguisticobjects/LinguisticObjects.php';

class PronounTest extends PHPUnit_Framework_TestCase
{
	
	/**
     * @var utf8a
     */
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }
	
	
    public function test_2nd_person_pronoun()
    {
    	$values = 'igvit;ipvit;1;pr;per;2;s;you;;;;';
        $object = self::faireObjetRacine($values);
        $type = $object->type();
        $expected_type = 'pr';
    	$this->assertEquals($type, $expected_type, "The created object does not have the right type.");
    }
    
    /* Utility functions for tests */
    
    static function faireObjetRacine ( $valeurs )
    {
    	$champs = 'morpheme;variant;no;type;nature;per;number;engMean;freMean;composition;condOnNext;END';
    	$champs = 'dbName;tableName;' . $champs;
    	$valeurs = 'Inuktitut;Racines;' . $valeurs;
    	return LinguisticObjects::makeLinguisticObject_from_FieldsAndValues($champs,$valeurs);
    }
    
 
}
?>
