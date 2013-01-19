<?php 
include_once("script/Syllabics.php");
include_once("tools/NunHanSearch/TermDistribution.php");
include_once("tools/NunHanSearch/Alignment.php");
include_once("tools/NunHanSearch/Grepper.php");
require_once 'lib/log4php/Logger.php';

class ProcessQuery {
	
	private static $cons = array( "g", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "&", "N", "X" );
	private $data_directory;
	private $max_variants;
	private $max_parts;
	private $query_orig;
	private $query_regexp;
	private $query_regexpSyllabic;
	private $query_language;
	private $output_inuktitut_script;
	private $show_list_flag;
	private $list_order;
	private $alignments = array();
	private $term_distribution;
	private $elapsed_time;
	private $grepper;
	
	function __construct() {
		$this->data_directory  = dirname(__FILE__).'/Data/1999-2007';
	}

function run ($query_orig, $query_language, $output_inuktitut_script,  
							$max_variants, $max_parts, $show_list_flag, $list_order, $lang )  {
	$logger = Logger::getLogger('ProcessQuery.run');
	$logger->debug("\$query_orig= $query_orig");
	$this->max_variants = $max_variants;
	$this->max_parts = $max_parts;
	$this->query_orig = $query_orig;
	$this->query_language = $query_language;
	$this->output_inuktitut_script = $output_inuktitut_script;
	$this->show_list_flag = $show_list_flag;
	$this->list_order = $list_order;
	$this->lang = $lang;
	// Make the query a regular expression: * means '0 or more characters'
	$this->query_regexp = preg_replace('/\*/','\S*?',$this->query_orig);
	$this->query_regexpSyllabic = '';
	
	if ( $this->query_language=='iu' ) {
		if ( preg_match("/\p{Canadian_Aboriginal}/u", $this->query_regexp) ) {
			$this->query_regexpSyllabic = $this->query_regexp;
// 			$this->query_regexp = transcode('unicode','roman alphabet','0',$this->query_regexp);
			$this->query_regexp = Syllabics::unicodeToLatinAlphabet($this->query_regexp);
		}
		elseif ( $this->output_inuktitut_script == 'syl' )
// 			$this->query_regexpSyllabic = transcode('roman alphabet','unicode','0',$this->query_regexp);
			$this->query_regexpSyllabic = Syllabics::latinAlphabetToUnicode($this->query_regexp, '0');
	}
	
	$logger->debug("\$this->query_regexp= ".$this->query_regexp);
	$logger->debug("\$this->query_regexpSyllabic= ".$this->query_regexpSyllabic);
	
	# Starting time
	$startTime = time();

	# Prepare an object that tells the format of the query and which files to look into for that query.
	$this->grepper =
		new Grepper( $this->query_regexp, $this->query_regexpSyllabic, $this->query_language, $this->output_inuktitut_script );
	
	# Report the number of matching terms, total frequency, and distribution.
	# This is looked for in the files InuktitutWordsIndex.txt,
	# InuktitutWordsSyllabicIndex.txt and EnglisWordsIndex.txt.
	#
	# %td contains the following keys:
	# words : a reference to an array containing the words of the query
	# total_frequency : number of sentences containing the query
	# indices : positions of those sentences in the file SingleLineAlignment*.txt
	# $...$ : the distribution for each word of the query
	$this->getDistribution();

	if ( count($this->term_distribution->indices) > 0 ) {
		$this->get_all_matching_alignments_from_td();
		$this->term_distribution->total_frequency = count($this->alignments);
	}

	# Elapsed time
	$this->elapsed_time = time() - $startTime;
}


# Returns a hash table:
# 
function getDistribution () {
	$logger = Logger::getLogger('ProcessQuery.getDistribution');
	$grepQuery = $this->grepper->grepQuery;
	$language = $this->grepper->language;
	$dist = array();
	$distribution = new TermDistribution($language, array(), array());
	$words = preg_split( '/\s+/', $grepQuery );
	$logger->debug("nb. words= ".count($words));
	$intersection = array();
	foreach ($words as $word) {
		$this->grepper->grepQuery = $word;
		$td_of_word = $this->getDistributionOfOneWord();
		$logger->debug("\$td_of_word= ".print_r($td_of_word,true));
		$dist_for_word = $td_of_word->dist;
		foreach ($dist_for_word as $variant => $variantValue) {
			$dist[$variant] = $variantValue;
		}
		$distribution->words_distributions['$'.$word.'$'] = $td_of_word;
		$indices_for_word = $td_of_word->indices;
		if ( count($intersection) != 0 ) {
			$intersection = self::_intersect( $indices_for_word, $intersection );
		}
		else {
			$intersection = $indices_for_word;
		}
	}
	$distribution->indices = $intersection;
	$distribution->words = $words;
	# put all variants of all words of the query in one hash
	$distribution->dist = $dist;	
	$this->term_distribution = $distribution;
	$logger->debug("\$distribution= ".print_r($distribution,true));
}

function _intersect ( $a, $b ) {
	$union = array();
	$isect = array();
	foreach ($a as $e) {
		$union[$e] = TRUE;
	}

	foreach ($b as $e) {
		if ( $union[$e] ) {
			$isect[$e] = TRUE;
		}
	}
	$isect = array_keys($isect);
	return $isect;
}

// function _union ( $a, $b ) {
// 	$union = array();
// 	foreach ($a as $e) {
// 		$union[$e] = TRUE;
// 	}

// 	foreach ($b as $e) {
// 		$union[$e] = TRUE;
// 	}
// 	$union = array_keys($union);
// 	return $union;
// }

# Returns a hash table:
# 
function getDistributionOfOneWord () {
	$language          = $this->grepper->language;
	$indices_of_all_variants = array();
	$all_indices = '';
	$dist = array();
	# get one or several words (several if wildcards used)
	$grepped = self::_grep();
	$matching_lines = preg_split( '/\n/', $grepped );
	$logger = Logger::getLogger('ProcessQuery.getDistributionOfOneWord');
	$logger->debug("\$grepped= $grepped");
	foreach ($matching_lines as $line) {
		$matches = array();
		if ( preg_match( '/^([^:]+):([0-9]+):(.*)$/', $line, $matches ) ) {
			$variant    = $matches[1];
			$variant_frequency    = $matches[2];
			$variant_joined_indices = $matches[3];
			$variant_indices = explode( ':', $variant_joined_indices );
			$variant_indices = self::_remove_duplicates($variant_indices);
			$indices_of_all_variants = array_merge($indices_of_all_variants,$variant_indices);
			$dist[$variant] = array(count($variant_indices),$variant_indices);
		}
	}
	$indices_of_all_variants = self::_remove_duplicates($indices_of_all_variants);
	sort($indices_of_all_variants);
	$td = new TermDistribution( $language, $indices_of_all_variants, $dist );
	return $td;
}

function _grep () {
	$t1      = time();
	$grepped =$this->grepper->run();
	$greptime = time() - $t1;
	return $grepped;
}


function _remove_duplicates ($array) {
	$hash = array();
	foreach ($array as $e) { $hash[$e] = TRUE; };
	$new_array = array_keys($hash);
	return $new_array;
}

function val ($x, $y) {
	$res = "(" . $x . "|" . $x . $x . "|";
	foreach ($this->cons as $c) {
		$res .= $c . $x . "|" . $c . $x . $x . "|";
	}
	$res = chop($res);
	return "\.\*" . $res . ")" . $y;
}

function get_all_matching_alignments_from_td () {
	$file_name = $this->grepper->get_alignments_file();
	$logger = Logger::getLogger('ProcessQuery.get_all_matching_alignments_from_td');
	$logger->debug("\$file_name= $file_name");
	$alignments_file = 
		fopen( $file_name, 'r' , 1); // look in include_path
	$inds = array();
	$dist = $this->term_distribution->dist;

	$query = $this->grepper->grepQuery;
	# $query might be in syllabics, which means in utf-8 since php doesn't handle unicode directly.
	# So, in order to order to find the query in the lines from the data files (all in utf-8),
	# the matching must be done with a multibyte function.
	
	$unsortedlist = array();
	foreach ( array_keys( $dist ) as $a_key) {
		array_push( $unsortedlist, array($a_key, $dist[$a_key]) );
	}

	# Sort the list: highest frequency $td{dist}{$key}[0] first
	$list = $unsortedlist;
	usort($list, function($a,$b) {
			if ( $a[1][0] < $b[1][0] )
				return 1;
			elseif ( $a[1][0] > $b[1][0] )
				return -1;
			else
				return 0;
		}
			);

	$indices_ref = $this->term_distribution->indices;
	$indices = $indices_ref;
	
	$alignments = array();
	foreach ($indices as $an_ind) {
		if ( ! array_key_exists($an_ind, $inds) ) {
			$inds[$an_ind] = 1;
			fseek( $alignments_file, $an_ind );
			$line = fgets($alignments_file);
			if ( ! self::query_matches_line($query, $line) )
				continue;
			$parts = preg_split( '/::|\@----\@/', $line );
			$alignment = array();
			if ( count($parts) > 1 ) {
				$alignment =
				  new Alignment( $parts[0], $parts[1], $parts[2], $this->grepper->language );
			}
			else {
				$alignment =
				  new Alignment( $parts[0], $parts[1], "", $this->grepper->language );
			}
			$logger->debug("\$alignment->language= ".$alignment->language);
			array_push( $alignments, $alignment );
		}
	}
	fclose($alignments_file);
	$this->alignments = $alignments;
}

function html_elapsed_time ($elapsedTime) {
	return "<center><p>"
	. __NH_ELAPSED_TIME__.": "
	. $elapsedTime
	. "s. <br />" . "\n";
}

function query_matches_line ($query, $line) {
	$regexp = preg_replace('/\.\*/', '\S*', $query);
	$regexp = preg_replace('/\s+/', '\s+', $regexp);
	$regexp = '(^|\W)'.$regexp;
	return preg_match("/$regexp/i",$line);
} 

function print_results () {
	$logger = Logger::getLogger("ProcessQuery.print_results");
	$output = "<p>\n";
	if ( $this->show_list_flag ) {
		$output .= $this->term_distribution->to_string($this->query_orig, $this->query_regexp,
			$this->query_regexpSyllabic, $this->query_language,
			$this->output_inuktitut_script, $this->list_order, $this->show_list_flag, $this->lang)
		  . "\n";
	}
	else {
		$output .= $this->term_distribution->to_string($this->query_orig,   $this->query_regexp,
			$this->query_regexpSyllabic, $this->query_language,
			$this->output_inuktitut_script, "f", $this->show_list_flag, $this->lang)
		  . "\015\012";

		if ( count($this->term_distribution->indices) > 0 ) {
			$output .= "\n<br />";
			$output .= "<table width=100% border=2>\n";
			$output .= "<caption><i>";
			if ( count($this->alignments) == 1 ) {
				$output .= 
					count($this->alignments)
					. __NH_MATCH__ . ".";
			}
			elseif ( $this->max_parts == 0 || count($this->alignments) < $this->max_parts ) {
				$output .= 
					__NH_LISTING_ALL__
					. count($this->alignments)
					. __NH_MATCHES__ . ".";
			}
			else {
				$output .= 
					__NH_ONLY_LISTING__
					. $this->max_parts
					. __NH_MATCHES__ . ".";
			}
			$output .= "</i></caption>\n";
			$output .= "\n<colgroup><col width=4%><col width=45%><col width=45%><col width=6%></colgroup>";
			$output .= "\n<tr><td></td><td><b>Inuktitut</b></td><td><b>"
			  . __ENGLISH__
			  . "</b></td><td><b>".__NH_SOURCE__."</b></td></tr>\n";

			$ia = 1;

			# Sort the alignments from earliest source
			# Use value of $alignment->{source}

			#binmode STDOUT,"utf8";
			foreach ($this->alignments as $an_alignment) {
				$logger->debug("\$an_alignment->language= ".$an_alignment->language);
				$output .= $an_alignment->to_html_string($this->query_regexp, $this->query_regexpSyllabic,
					$this->output_inuktitut_script, $ia++ )."\n";
			}

			$output .= "</table></center>\n";
		}

	}
	$output .= self::html_elapsed_time($this->elapsed_time );
	return $output;
}

function set_grepper($grepper) {
	$this->grepper = $grepper;
}


} // End of class

?>

