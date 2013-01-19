<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'lang/LinguaENParticiple.php';
 
class LinguaENParticipleTest extends PHPUnit_Framework_TestCase
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
	
	
    public function test_stem()
    {
    	$inf = 'audit';
    	$got_stem = LinguaENParticiple::stem($inf);
    	$expected_stem = 'audit';
        $this->assertEquals($expected_stem, $got_stem, "The stem for '$inf' is wrong.");

        $inf = 'flip';
    	$got_stem = LinguaENParticiple::stem($inf);
    	$expected_stem = 'flipp';
        $this->assertEquals($expected_stem, $got_stem, "The stem for '$inf' is wrong.");

        $inf = 'collect';
    	$got_stem = LinguaENParticiple::stem($inf);
    	$expected_stem = 'collect';
        $this->assertEquals($expected_stem, $got_stem, "The stem for '$inf' is wrong.");
    }
 
    public function test_participle()
    {
    	$inf = 'audit';
    	$got_participle = LinguaENParticiple::participle($inf);
    	$expected_participle = 'audited';
    	$this->assertEquals($expected_participle, $got_participle, "The participle for '$inf' is wrong.");

        $inf = 'flip';
    	$got_participle = LinguaENParticiple::participle($inf);
    	$expected_participle = 'flipped';
    	$this->assertEquals($expected_participle, $got_participle, "The participle for '$inf' is wrong.");

        $inf = 'win';
    	$got_participle = LinguaENParticiple::participle($inf);
    	$expected_participle = 'won';
    	$this->assertEquals($expected_participle, $got_participle, "The participle for '$inf' is wrong.");
    }

}

?>
