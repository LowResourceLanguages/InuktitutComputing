<?php

class Grepper {
	
	public $index;
	public $query;
	public $grepQuery;
	public $options;
	public $language;
	public $output_inuktitut_script;
	public $data_directory;
	
	function __construct ( $query, $querySyllabic, $query_language, $output_inuktitut_script ) {
		$this->data_directory  = dirname(__FILE__).'/Data/1999-2007';
// 		$grepOptions = "-E ";
		$grepOptions = "-P ";
		$grepQuery;
		if ( $query_language == "iu" ) {
			// inuktitut query
			if ( $output_inuktitut_script == 'syl' ) {
				$word_index_filename = $this->data_directory."/InuktitutWordsSyllabicIndex.txt";
				$grepQuery           = self::escapeChars($querySyllabic);
			}
			else {
				$word_index_filename = $this->data_directory."/InuktitutWordsIndex.txt";
				$grepQuery           = $query;
			}
		}
		else {
			// english query
			if ( $output_inuktitut_script == 'syl' ) {
				$word_index_filename = $this->data_directory."/EnglishWordsSyllabicIndex.txt";
			}
			else {
				$word_index_filename = $this->data_directory."/EnglishWordsIndex.txt";
			}
			$grepQuery = $query;
			$grepOptions .= "-i ";
		}
		$this->index = $word_index_filename;
		$this->query = $query;
		$this->grepQuery = $grepQuery;
		$this->options = $grepOptions;
		$this->language = $query_language;
		$this->output_inuktitut_script = $output_inuktitut_script;
	}
	
	// TODO-BF-Inuktitut: This 'grep' thing takes much time. See why.
	function run () {
		$grepCommand =
			( strtoupper(substr(PHP_OS, 0, 3)) === 'WIN' ? "grep.exe" : "grep" ) . " "
	   		. $this->options . "\"^" . $this->grepQuery . ":\" " . $this->index;
		$logger = Logger::getLogger('Grepper.run');
		$logger->debug("\$grepCommand= $grepCommand");
		$grepped = shell_exec($grepCommand);
		$grepped = trim($grepped);
		return $grepped;
	}
	
	function get_alignments_file () {
		$syllabic_extension = $this->output_inuktitut_script == 'syl' ?	'Syllabic' : '';
		return $this->data_directory."/SingleLineAligned${syllabic_extension}.txt";
	}
	
	static function escapeChars ($grepQuery) {
		$logger = Logger::getLogger('Grepper.escapeChars');
		$logger->debug("\$grepQuery= '$grepQuery'");
		$grepQueryParts = explode('\S*?',$grepQuery);
		$escaped_grepQueryParts = array();
		$logger->debug("\$grepQueryParts=".print_r($grepQueryParts,true));
		foreach ($grepQueryParts as $a_grepQueryPart) {
			$partChars = str_split($a_grepQueryPart);
			$xchars = '';
			if ($a_grepQueryPart!='') {
				foreach ($partChars as $a_char) {
					$xchars .= '\x{'.dechex(ord($a_char)).'}';
				}
			}
			array_push($escaped_grepQueryParts, $xchars);
		}
		$escaped_grepQuery = implode('\S*?',$escaped_grepQueryParts);
		return $escaped_grepQuery;
	}
	
}

?>
