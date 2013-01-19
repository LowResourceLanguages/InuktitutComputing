<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'lang/LinguaFRParticiple.php';
 
class LinguaFRParticipleTest extends PHPUnit_Framework_TestCase
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
        $this->object = null;
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }
	
	
    public function test_stem_ending()
    {
    	$inf = 'éclairer';
    	list($got_stem, $got_ending) = LinguaFRParticiple::stem_ending($inf);
    	$expected_stem = 'éclair';
    	$expected_ending = 'er';
        $this->assertEquals($expected_stem, $got_stem, "The stem for '$inf' is wrong.");
    	$this->assertEquals($expected_ending, $got_ending, "The ending for '$inf' is wrong.");
        
        $inf = 'comprendre';
    	list($got_stem, $got_ending) = LinguaFRParticiple::stem_ending($inf);
    	$expected_stem = NULL;
    	$expected_ending = NULL;
        $this->assertEquals($expected_stem, $got_stem, "The stem for '$inf' is wrong.");
    	$this->assertEquals($expected_ending, $got_ending, "The ending for '$inf' is wrong.");
    	        
        $inf = 'finir';
    	list($got_stem, $got_ending) = LinguaFRParticiple::stem_ending($inf);
    	$expected_stem = 'fin';
    	$expected_ending = 'ir';
        $this->assertEquals($expected_stem, $got_stem, "The stem for '$inf' is wrong.");
    	$this->assertEquals($expected_ending, $got_ending, "The ending for '$inf' is wrong.");
    	    }
 
    public function test_participle()
    {
    	$inf = 'éclairer';
    	$got_participle = LinguaFRParticiple::participle($inf);
    	$expected_participle = 'éclairé';
    	$this->assertEquals($expected_participle, $got_participle, "The participle for '$inf' is wrong.");

        $inf = 'comprendre';
    	$got_participle = LinguaFRParticiple::participle($inf);
    	$expected_participle = 'compris';
    	$this->assertEquals($expected_participle, $got_participle, "The participle for '$inf' is wrong.");

        $inf = 'finir';
    	$got_participle = LinguaFRParticiple::participle($inf);
    	$expected_participle = 'fini';
    	$this->assertEquals($expected_participle, $got_participle, "The participle for '$inf' is wrong.");
    }

}

?>
