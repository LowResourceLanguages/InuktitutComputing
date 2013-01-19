<?php
/*
 * public static suite() method which returns an PHPUnit_Framework_Test object, 
 * for example an instance of the PHPUnit_Framework_TestSuite class.
 */

 
 require_once 'lib/log4php/Logger.php';
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');

require_once 'UnitTests/index/IndexerTest.php';
 mb_internal_encoding('UTF-8');

 class index_AllTests {
 	
 	private static $liste_de_tests = array(
			'IndexerTest',
 			);
 	private static $nom_de_la_suite = 'index';
 	
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
