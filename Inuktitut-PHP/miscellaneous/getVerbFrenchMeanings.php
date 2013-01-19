<?php
/*
 * Created on 2010-11-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */

set_include_path('..');
 require_once "lib/parseCSV.php";
 require_once "linguisticobjects/LinguisticObjects.php";
 
 	echo "Get French Meanings of Verb Roots\n";
	$fd = fopen('c:/eclipse_workspace/InuktitutNew-PHP/linguisticdata/RootsSpalding.csv','r');
	$premiereLigne = fgets($fd);
	$champs = explode(',',trim($premiereLigne));
	while ( ($ligne=fgets($fd)) != null ) {
//		echo "\$ligne= '$ligne'";
		$champsValeurs = parseCSV(trim($ligne),$champs);
		$champsValeurs['dbName'] = 'Inuktitut';
		$champsValeurs['tableName'] = $tableName;
		$object = LinguisticObjects::makeLinguisticObject($champsValeurs);
		if ( $object != null ) {
			if ( $object->getType() == 'v' ) {
				echo $object->id().' : '.$object->getFrenchMeaning()."\n";
			}
		}
	}
	close($fd);
?>
