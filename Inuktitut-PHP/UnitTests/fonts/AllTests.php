<?php



/*
 * public static suite() method which returns an PHPUnit_Framework_Test object, 
 * for example an instance of the PHPUnit_Framework_TestSuite class.
 */
 require_once 'UnitTests/fonts/NunacomTest.php';
 require_once 'UnitTests/fonts/ProsylTest.php';
 require_once 'UnitTests/fonts/AipainunavikTest.php';
 mb_internal_encoding('UTF-8');
 
 require_once 'lib/log4php/Logger.php';
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');
   
 class fonts_AllTests {
 	
 	private static $liste_de_tests = array(
			'NunacomTest',
			'ProsylTest',
			'AipainunavikTest',
 			);
 	private static $nom_de_la_suite = 'fonts';
 	
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
 	 	
	
//sub new {
//    my ($class, @args) = @_;
//    my $self = $class->SUPER::new(@args);
//    return $self;
//}
//
//sub test_dummy {
//   my ($self) = @_;
//}
//
//package main;
//
//AllTestsPerlCorpusMiner::set_test_cases_to_run(@ARGV);
//
//my $runner = Test::Unit::TestRunner->new();
//no strict;
//$runner->start(AllTestsPerlCorpusMiner);
//use strict;

