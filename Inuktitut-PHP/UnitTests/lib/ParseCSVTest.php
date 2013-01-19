<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'lib/parseCSV.php';

class ParseCSVTest extends PHPUnit_Framework_TestCase
{
	
    public function testParseCSV()
    {
    	$champs = 'morph;key;type;nature;freMean;engMean;description;source;dialect';
    	$str = 'morph/1n;1;n;;"un morphème";a morpheme;desc;;';
    	$champsValeurs = parseCSV($champs,$str);
    	$att = array(
			'morph' => 'morph/1n',
			'key'  => 1,
			'type' => 'n',
			'nature' => '',
			'freMean' => 'un morphème',
			'engMean' => 'a morpheme',
			'description' => 'desc',
			'source' => '',
			'dialect' => '');
        $this->assertEquals($att, $champsValeurs, "");
    }
 
}
?>
