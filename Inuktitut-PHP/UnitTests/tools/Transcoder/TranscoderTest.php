<?php

require_once 'tools/Transcoder/Transcoder.php';
require_once 'lib/log4php/Logger.php';
Logger::configure("../log4php.properties.xml");

/**
 * Test class for Transcoder.
 * Generated by PHPUnit on 2010-05-17 at 15:52:40.
 */
class TranscoderTest extends PHPUnit_Framework_TestCase
{
    /**
     * @var Transcoder
     */
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
//         $this->object = new Transcoder;
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }

      public function test_function()
    {
    }
    
    
    function arrays_are_similar($a, $b) {
    	// if the indexes don't match, return immediately
    	if (count(array_diff_assoc($a, $b))) {
    		return false;
    	}
    	// we know that the indexes, but maybe not values, match.
    	// compare the values between the two arrays
    	foreach($a as $k => $v) {
    		if ($v !== $b[$k]) {
    			return false;
    		}
    	}
    	// we have identical indexes, and no unequal values
    	return true;
    }

}
?>