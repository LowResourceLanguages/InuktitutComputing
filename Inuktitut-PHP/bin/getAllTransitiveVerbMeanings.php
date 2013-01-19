<?php

require_once "linguisticobjects/LinguisticObjects.php";
require_once "lib/log4php/Logger.php";
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');
$logger = Logger::getLogger('getAllTransitiveVerbs');

$roots = LinguisticObjects::listOfRoots();
foreach ($roots as $a_root_id) {
	$logger->debug("\$a_root_id= $a_root_id");
	$root_object = LinguisticObjects::getObject($a_root_id);
	if ( $root_object->type() === 'v' ) {
		if ( $root_object->isTransitiveVerb() ) {
			$en_meaning = $root_object->getEnglishMeaning();
			echo "$a_root_id --- $en_meaning\n";
		}
	}
}

?>
