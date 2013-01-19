<?php

require_once 'lib/Common.php';

class CommonTest extends PHPUnit_Framework_TestCase
{
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

//------------------------------------------------------------------------------------------

	public function testStr_endswith() {
		$text = 'abcdef';
		$this->assertFalse(str_endswith($text,'abc'),"La cha�ne '$text' ne se termine pas par 'abc'.");
		$this->assertTrue(str_endswith($text,'def'),"La cha�ne '$text' se termine par 'def'.");
		$this->assertTrue(str_endswith($text,'ef'),"La cha�ne '$text' se termine par 'ef'.");
		$this->assertFalse(str_endswith($text,'abcdefgef'),"La cha�ne '$text' ne se termine par 'abcdefgef'.");
	}
    
}
?>
