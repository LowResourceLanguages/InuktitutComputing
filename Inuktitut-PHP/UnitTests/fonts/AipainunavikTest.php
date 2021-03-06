<?php

require_once 'fonts/Aipainunavik.php';

/**
 * Test class for Aipainunavik.
 * Generated by PHPUnit on 2010-05-17 at 15:52:40.
 */
class AipainunavikTest extends PHPUnit_Framework_TestCase
{
    /**
     * @var Aipainunavik
     */
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
        $this->object = new Aipainunavik;
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }

    /**
     * @todo Implement testUnicode_a_legacy().
     */
    public function testUnicode_a_legacy_1()
    {
    	$arru = array(0xE1,0x90,0x83,0xE1,0x93,0x84,0xE1,0x90,0x83,0xE1,0x91,0xA6);
    	$u = implode('',array_map('chr',$arru));
    	$au = 'wkw5';
    	$ru = Aipainunavik::unicodeToLegacy($u);
    	$this->assertEquals($au,$ru);
    }

    public function testUnicode_a_legacy_2()
    {
    	$arru = array(0xE1,0x92,0xA3);
    	$u = implode('',array_map('chr',$arru));
    	$au = utf8_encode(chr(0xE0));
    	$ru = Aipainunavik::unicodeToLegacy($u);
    	$this->assertEquals($au,$ru);
    }

    public function testLegacy_a_unicode_1()
    {
    	$lu = 'wkw5';
    	$arru = array(0xE1,0x90,0x83,0xE1,0x93,0x84,0xE1,0x90,0x83,0xE1,0x91,0xA6);
    	$au = implode('',array_map('chr',$arru));
    	$ru = Aipainunavik::legacyToUnicode($lu, TRUE);
    	$this->assertEquals($au,$ru);
    }

    public function testLegacy_a_unicode_2()
    {
    	$lu = 'mw';
    	$arru = array(0xE1,0x92,0xA3);
    	$au = implode('',array_map('chr',$arru));
    	$ru = Aipainunavik::legacyToUnicode($lu, TRUE);
    	$this->assertEquals($au,$ru);
    }
    
    public function testHighlight_aipaitai_chars() {
    	$text = 'wkw5 �m'; // inuit taima
    	$target = 'wkw5 <<<�>>>m';
    	$highlighted_text = Aipainunavik::highlight_aipaitai_chars($text);
    	$this->assertEquals($target,$highlighted_text);  	
    }

}
?>
