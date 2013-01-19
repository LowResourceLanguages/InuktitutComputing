<?php

require_once 'exceptions/NotSupportedOnServer.php';

class MorphologicalAnalyzer {
	
	function __construct() {
	}
	
	public function is_supported_on_server() {
		$is_supported = true;
		if ( gethostname() != "iitm50nb1533a" ) {
			$is_supported = false;
		}
		return $is_supported;
	}
	
	public function run($word) {
		if ( ! $this->is_supported_on_server() ) {
			throw new NotSupportedOnServerException();
		} else {
			$analyses = null;
			$latin_word = 
				Syllabics::is_syllabic_word($word) ?
					Syllabics::unicodeToLatinAlphabet($word) :
					$word;
			$jar_path = realpath( dirname(__FILE__)."/Uqailaut.jar" );
			$command = "java -jar \"$jar_path\" $latin_word";
			$res = exec($command,$analyses);
			return $analyses;
		}
	}
	
}