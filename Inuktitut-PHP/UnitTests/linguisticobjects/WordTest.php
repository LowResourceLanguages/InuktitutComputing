<?php
/*
 * Created on 2011-02-08
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'linguisticobjects/Word.php';
 require_once 'linguisticobjects/LinguisticObjects.php';

 require_once 'lib/log4php/Logger.php';

 class WordTest extends PHPUnit_Framework_TestCase
 {
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
    	$morphemes = array( 'aariaq/1n', 'it/tn-nom-p' );
		$this->object = new Word($morphemes);	
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }

//------------------------------------------------------------------------------------------

	public function test__construct() {
		$morphemes = array( 'aariaq/1n', 'it/tn-nom-p' );
		$mot = new Word($morphemes);
	}
	
	public function test__surfaceFormOnOneStem_1_morpheme () {
		$stem = 'aariaq';
		$morpheme = LinguisticObjects::getObject('it/tn-nom-p');
		$radicaux = $this->object->_surfaceFormOnOneStem($stem,array($morpheme));
		$att = array('aariat');
		$this->assertEquals($att,$radicaux,"");
	}
	
	public function test__surfaceFormOnOneStem_2_morphemes () {
		$stem = 'aariaq';
		$morpheme1 = LinguisticObjects::getObject('galaq/1nn');
		$morpheme2 = LinguisticObjects::getObject('it/tn-nom-p');
		$radicaux = $this->object->_surfaceFormOnOneStem($stem,array($morpheme1,$morpheme2));
		$att = array('aariagalait');
		$this->assertEquals($att,$radicaux,"");
	}
	
	public function test_surfaceForm() {
		$forms = $this->object->surfaceForm();
		$forms_attendues = array('aariat');
		$this->assertEquals($forms_attendues, $forms, "");		
	}
	
	public function test_surfaceForm_3morphemes () {
		$mot = new Word(array('aariaq/1n','galaq/1nn','it/tn-nom-p'));
		$forms = $mot->surfaceForm();
		$forms_attendues = array('aariagalait');
		$this->assertEquals($forms_attendues, $forms, "");		
	}
	
	public function test_idsOfMorphemes() {
		$ids = $this->object->idsOfMorphemes();
		$ids_attendus = array('aariaq/1n','it/tn-nom-p');
		$this->assertEquals($ids_attendus, $ids,"");
	}
	
 }
 
?>
