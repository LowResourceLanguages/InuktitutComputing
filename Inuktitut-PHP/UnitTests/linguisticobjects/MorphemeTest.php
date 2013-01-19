<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/Morpheme.php';
 
class MorphemeTest extends PHPUnit_Framework_TestCase
{
	
    public function test__construct__1()
    {
    	$morpheme = 'abc';
    	$autres = array('1v');
    	$id = new ID_Morpheme($morpheme, $autres);
    	$string_attendue = 'abc/1v';
    	$string = $id->toString();
        $this->assertEquals($string_attendue, $string);
    }
 
	    public function test__construct__2()
    {
    	$morpheme = 'abc/1v';
    	$id = new ID_Morpheme($morpheme);
    	$string_attendue = $morpheme;
    	$string = $id->toString();
        $this->assertEquals($string_attendue, $string);
    }
 
}
?>
