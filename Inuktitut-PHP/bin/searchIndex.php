<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once "lib/log4php/Logger.php";
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');

require_once 'linguisticobjects/LinguisticObjects.php';

if ($argc < 2) {
	usage();
	die();
}
$objectId = $argv[1];

$valeurs = LinguisticObjects::findLinguisticDataInIndex($objectId); // ex.: 'saqqik/1v'
print_r($valeurs);

function usage() {
	echo "\nusage: php -c . searchIndex.php <morphemeId>\n";
	echo "\n\t<morphemeId>\tmorpheme identifier, eg saqqik/1v\n";
}
?>
