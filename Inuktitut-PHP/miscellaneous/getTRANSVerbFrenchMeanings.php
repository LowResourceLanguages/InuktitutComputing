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
 
	$fd = fopen('c:/eclipse_workspace/InuktitutNew-PHP/divers/verbes_sens_francais.txt','r');
	while ( ($ligne=fgets($fd)) != null ) {
		if ( preg_match('/^[a-z\/0-9\&]+ : (.+)$/', $ligne, $match) ) { 
			$sens = $match[1];
			$sensPrepare = VerbRoot::prepareSenseForTransformation_FR($sens);
			$trans = VerbRoot::toTransitive_FR($sensPrepare);
			echo "-------------------------------------------------------------------\n";
			echo "$sens\n";
			echo "------\n";
			echo "$sensPrepare\n";
			echo "------\n";
			echo $trans."\n";
		}
	}
	close($fd);
?>
