<?php 

class Alignment {

	public $source;
	public $inuktitut;
	public $english;
	public $language;
	public static $colour = 'red';
	
	function __construct($str, $str2, $str3, $lang) {
		$this->source    = $str;
		$this->inuktitut = $str2;
		$this->english   = $str3;
		$this->language  = $lang;
	}

	# Highlight the words in the text corresponding to the query.
	# The text is either a variant of the query (a word) in the table of variants,
	# or an inuktitut or english sentence where a variant of the query has been
	# found. This variant is to be highlighted.

	function highlight ( $text, $queryRE, $lang ) {
		$logger = Logger::getLogger('Alignment.highlight');
		$logger->debug("\$queryRE= $queryRE ; \$lang= $lang");
		$logger->debug("\$text= $text");
		# The query is in fact the regular expression corresponding to the query
		if ( $lang == "iu" )
			$regex_modifier = 'u';
		else
			$regex_modifier = 'i';
		$in = preg_replace("/\b($queryRE)\b/$regex_modifier",'<font color='.self::$colour."><b>$1</b></font>", $text);
		return $in;
	}

// 	function match ($matched, $queryParts ) {
// 		foreach ($queryParts as $a_part) {
// 			if ( $a_part != "['&\\w]*"
// 				&& $a_part != "['\\x{1400}-\\x{167f}]*"
// 				&& $a_part != "" )
// 			{
// 				$matched = preg_replace("/$part/","<b>$part<\/b>", $matched);
// 			}
// 		}
// 		return "<font color=".self::$colour.">" . $matched . "<\/font>";
// 	}


	function to_html_string ($query, $querySyllabic, $output_inuktitut_script, $i ) {
		$logger = Logger::getLogger('Alignment.to_html_string');
		$logger->debug("\$this->language=".$this->language);
		$in = $this->inuktitut;
		$en = $this->english;
		$in = str_replace('"','&quot;', $in);
		$in = preg_replace('/(\.{2,})([^\.]|$)/','...',$in);
		$en = str_replace('"','&quot;', $en);
		$en = preg_replace('/(\.{2,})([^\.]|$)/','...',$en);

		if ( $this->language == "iu" ) {
			$in = self::highlight( $in, ( $output_inuktitut_script == 'syl' ? $querySyllabic : $query ),
								$this->language );    
		}
		else {
			$en = self::highlight( $en, $query, $this->language );
		}

		$bg = ($i % 2 == 0) ? " style='background-color:#ffffaa'" : "";
		$td_class = $output_inuktitut_script == 'syl' ? " class='syl'" : "";
		$ret =
	    	"<tr class='nhs_alignment'>\n"
	    	. "<td class='no'>$i</td>\n"
	    	. "<td$bg$td_class>" . $in . "</td>\n"
	  		. "<td$bg>" . $en . "</td>\n"
	  		. "<td class='no'>" . $this->source . "</td>\n"
	  		. "</tr>\n";
		return $ret;
	}

} // End of class

?>

