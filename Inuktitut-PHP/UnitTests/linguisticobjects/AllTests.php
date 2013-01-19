<?php

/*
 * public static suite() method which returns an PHPUnit_Framework_Test object, 
 * for example an instance of the PHPUnit_Framework_TestSuite class.
 */

 require_once 'UnitTests/linguisticobjects/NeutralActionTest.php';
 require_once 'UnitTests/linguisticobjects/NasalizationActionTest.php'; 
 require_once 'UnitTests/linguisticobjects/InsertionActionTest.php'; 
 require_once 'UnitTests/linguisticobjects/VoicingActionTest.php'; 
 require_once 'UnitTests/linguisticobjects/DeletionActionTest.php';  
 require_once 'UnitTests/linguisticobjects/DeletionAndInsertionActionTest.php';  
 require_once 'UnitTests/linguisticobjects/SpecificDeletionActionTest.php';  
 require_once 'UnitTests/linguisticobjects/VowelLengtheningActionTest.php';  
 require_once 'UnitTests/linguisticobjects/VowelLengtheningActionTest.php';  
 require_once 'UnitTests/linguisticobjects/SelfDecapitationActionTest.php';  
 require_once 'UnitTests/linguisticobjects/AssimilationActionTest.php';  
 require_once 'UnitTests/linguisticobjects/ActionFactoryTest.php';  
 require_once 'UnitTests/linguisticobjects/MeaningOfMorphemeTest.php';  
 require_once 'UnitTests/linguisticobjects/SuffixTest.php';  
 require_once 'UnitTests/linguisticobjects/AffixTest.php';  
 require_once 'UnitTests/linguisticobjects/NounEndingTest.php';  
 require_once 'UnitTests/linguisticobjects/VerbEndingTest.php';
 require_once 'UnitTests/linguisticobjects/BehaviourTest.php';  
 require_once 'UnitTests/linguisticobjects/LinguisticObjectsTest.php';  
 require_once 'UnitTests/linguisticobjects/WordTest.php';  
 require_once 'UnitTests/linguisticobjects/VerbRootTest.php';  
 require_once 'UnitTests/linguisticobjects/NounRootTest.php';  
 require_once 'UnitTests/linguisticobjects/PronounTest.php';
 
 require_once 'lib/log4php/Logger.php';
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');
  
 
 class linguisticobjects_AllTests {
 	
 	private static $liste_de_tests = array(
			'NeutralActionTest', 
 			'NasalizationActionTest', 
			'InsertionActionTest',
			'VoicingActionTest',
			'DeletionActionTest',
			'DeletionAndInsertionActionTest',
			'SpecificDeletionActionTest',
			'VowelLengtheningActionTest',
			'SelfDecapitationActionTest',
			'AssimilationActionTest',
			'ActionFactoryTest',
 			'MeaningOfMorphemeTest',
			'SuffixTest',
			'AffixTest',
			'NounEndingTest',
			'VerbEndingTest',
 			'BehaviourTest',
			'LinguisticObjectsTest',
 	
 			'VerbRootTest',
 			'NounRootTest',
 			'PronounTest',
 			
 			);
 	private static $nom_de_la_suite = '_linguisticobjects_';
 	
 	public static function suite() {
 		echo "Tests du module '".self::$nom_de_la_suite."'\n";
 		$suite = new PHPUnit_Framework_TestSuite(self::$nom_de_la_suite);
 		foreach (self::$liste_de_tests as $un_test) {
 			echo "\t- ajout des tests $un_test ... ";
 			$suite->addTestSuite($un_test);
 			echo " ok\n";
 		}
  		return $suite;
 	}
 	
 }
 	 	
?>
