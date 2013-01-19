<?php
/*
 * public static suite() method which returns an PHPUnit_Framework_Test object, 
 * for example an instance of the PHPUnit_Framework_TestSuite class.
 */
require_once 'tools/NunHanSearch/ProcessQueryTest.php';
require_once 'tools/NunHanSearch/AlignmentTest.php';
require_once 'tools/NunHanSearch/GrepperTest.php';
mb_internal_encoding('UTF-8');
 
require_once 'lib/log4php/Logger.php';
Logger::configure('../log4php.properties.xml');
   
 class NunHanSearch_AllTests {
 	
 	private static $liste_de_tests = array(
			'ProcessQueryTest',
			'GrepperTest',
// 			'TermDistributionTest',
 			'AlignmentTest'
 			);
 	private static $nom_de_la_suite = 'NunHanSearch';
 	
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

