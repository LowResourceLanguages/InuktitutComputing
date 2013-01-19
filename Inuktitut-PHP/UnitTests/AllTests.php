<?php
 require_once 'UnitTests/linguisticobjects/AllTests.php'; 
 require_once 'UnitTests/script/AllTests.php'; 
 require_once 'UnitTests/lib/AllTests.php'; 
 require_once 'UnitTests/fonts/AllTests.php'; 
 require_once 'lang/AllTests.php'; 
 require_once 'tools/NunHanSearch/AllTests.php';
 require_once 'tools/Transcoder/AllTests.php';
 require_once 'index/AllTests.php';
 require_once 'linguisticdata/AllTests.php';
 
 class AllTests {
 	
 	
 	public static function suite() {
 		$suite = new PHPUnit_Framework_TestSuite('Inuktitut-PHP');
 		$suite->addTest(linguisticobjects_AllTests::suite());
 		$suite->addTest(script_AllTests::suite());
 		$suite->addTest(lib_AllTests::suite());
//  		$suite->addTest(fonts_AllTests::suite());
 		$suite->addTest(lang_AllTests::suite());
 		$suite->addTest(NunHanSearch_AllTests::suite());
 		$suite->addTest(Transcoder_AllTests::suite());
 		$suite->addTest(index_AllTests::suite());
 		$suite->addTest(linguisticdata_AllTests::suite());
 		return $suite;
 	}
 	
 }
 	 	
