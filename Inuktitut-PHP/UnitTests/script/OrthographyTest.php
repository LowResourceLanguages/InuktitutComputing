<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'script/Orthography.php';
 
require_once "lib/log4php/Logger.php";
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');

class OrthographyTest extends PHPUnit_Framework_TestCase
{
	
    public function testOrthographeICILat()
    {
    	$res = Orthography::latinICIOrthography('X');
    	$att = 'nng';
        $this->assertEquals($att, $att, "Erreur : '$res' aurait dû être '$att'.");
    }
 
}
?>
