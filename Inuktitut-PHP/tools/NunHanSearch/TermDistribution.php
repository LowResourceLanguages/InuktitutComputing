<?php 

include_once("tools/NunHanSearch/Alignment.php");

class TermDistribution {


# A TermDistribution object is a hash table representing the
# distribution of one term, with the following keys:
#     language
#     web
#     total_frequency
#     dist : hashtable(term's_variant => [frequency,distribution])
#     web_language

# %td is a hash table the keys of which are words, variants of the term
# (if the term contained wildcards; otherwise only one variant: the term itself).
# The value of each key is an (address of an) array of 2 elements:
# the frequency of the word, and a string of ':'-separated positions
# in the sentence-aligned file where the word is to be found.

	public $language;
	public $total_frequency;
	public $indices;
	public $dist;
	public $web_language;
	public $words;
	public $words_distributions;

	static public $web = "NunavutHansard/index.php?query";
	static public $max_variants = 21;
	static private $colour = 'red';
	
	function __construct ($language, $total_indices, $dist) {
	
		$this->language = $language;
		$this->total_frequency = count($total_indices);
		$this->indices         = $total_indices;
		$this->dist            = $dist;
		$this->web_language    = ( $language == "iu" ? "_inuktitut" : "_english" );
		$this->words_distributions		= array();
	}

	function to_string ($query_orig,   $query,
			$querySyllabic, $query_language,
			$output_inuktitut_script, $list_order, $show_list, $lang) {
		
		if ( $query_language == "iu" && $output_inuktitut_script == 'syl' )
			$queryRE = $querySyllabic;
		else
			$queryRE = $query;
		
		$sb = $this->print_summary_table($query_orig);
		if ( count($this->dist) > 1 && count($this->words) == 1) {
			$sb .= $this->print_variants_table($query_orig, $queryRE, $list_order, $show_list, $output_inuktitut_script, $query_language, $lang);
		}
		return $sb;
	}
	
	function print_summary_table ($query_orig) {
		$sb =
	   	 	"<center>\n"
	   	 	. "<table border=2>\n"
	   	 	. "  <tr>\n"
	   	 	. "    <td><b>"	. __QUERY__ . ":</b></td>\n"
	 		. "    <td>$query_orig</td>\n"
	 		. "  </tr>\n"
	  		. "  <tr>\n"
	  		. "    <td><b>"	. __NH_TOTAL_FREQ__	. ":</b></td>\n"
	  		. "    <td>" . count($this->indices) . "</td>\n"
	  		. "  </tr>" . "\n";
		
		// Variants
		$nbDistr = count( $this->dist );
		if ( $nbDistr > 1 && count($this->words) == 1) {
			$sb .=
		  		"  <tr>\n"
		  		. "    <td><b>"	. __NH_NB_VARIANTS__ . ":</b></td>\n"
		  		. "    <td>" . $nbDistr	. "</td>\n"
		  		. "  </tr>\n";
		}
		
		$sb .= "</table>\n"
			. "</center>\n";
		return $sb;
	}
	
	function print_variants_table ($query_orig, $queryRE, $list_order, $show_list, $output_inuktitut_script, $query_language, $lang) {

		$nbDistr = count($this->dist);
		
		if ( $show_list )
			$nb_variants_to_display = $nbDistr;
		elseif ( $nbDistr < self::$max_variants )
			$nb_variants_to_display = $nbDistr;
		else
			$nb_variants_to_display = self::$max_variants;
		
		$sb = "<center>\n";
				
		# Table of variants
		$sb .= "<br>";
		if ( $show_list )
			$sb .= "<table>\n";
		else
			$sb .= "<table border=2>\n";
		
		$sb .= $this->print_variants_table_caption($nbDistr, $nb_variants_to_display, $show_list, 
				$list_order, $output_inuktitut_script);
		
		$sb .= $this->print_variants_table_the_table_itself($queryRE, $nb_variants_to_display, $list_order,
			$show_list, $output_inuktitut_script, $lang);

		$sb .= "</table>\n";
		
		if ( !$show_list && $nbDistr != 1 && $nbDistr > $nb_variants_to_display && count($this->words) == 1) {
			$sb .= self::list_query_form( $output_inuktitut_script, $query_language, $query_orig, $lang );
		}
		$sb .= "</center>\n";
		
		return $sb;
	}
	
	function print_variants_table_caption ($nbDistr, $nb_variants_to_display, $show_list, $list_order, $output_inuktitut_script) {
		$sb = "<caption><i>";
		$sb .= 
			__NH_VARIANTS_FREQ__
			. $nb_variants_to_display
			. ($nbDistr <= $nb_variants_to_display
				? __NH_VARIANTS__
				: __NH_VARIANTS_MOST_FREQ__)
			. ".";
		if ( $show_list ) {
			$sb .= '<br>';
			if ( $list_order == "f" ) {
				$sb .= " ("
			  		. __NH_HIGHEST_FREQ_FIRST__
			  		. ")\n";
			}
			elseif ( $output_inuktitut_script == 'syl' ) {
				$sb .= " ("
			 	. __NH_SYLLABIC_ORDER__
			 	. ")\n";
			}
			else {
				$sb .= " ("
		 			. __NH_ALPHABETICAL_ORDER__
			 		. ")\n";
			}
		}
		else {
			$sb .= "\n";
		}
		$sb .= "</i></caption>\n";
		return $sb;
	}
	
	function highlight ( $text, $queryRE ) {
		# The query is in fact the regular expression corresponding to the query
		if ( $this->language == "iu" )
			$regex_modifier = 'u';
		else
			$regex_modifier = 'i';
		$in = preg_replace("/\b($queryRE)\b/$regex_modifier",'<font color='.self::$colour."><b>$1</b></font>", $text);
		return $in;
	}
	
	
	function print_variants_table_the_table_itself ($queryRE, $nb_variants_to_display, $list_order,
			$show_list, $output_inuktitut_script, $lang) {

		$across = $show_list ? 1 : 3;
		// Ordered list of distributions
		$ordered_list_of_distributions = $this->_make_ordered_list_of_distributions($list_order);

		$callShowLatin = 
			$output_inuktitut_script == 'rom' ? "&show_latin=on" : '';
		
		$sb = '';
		
		for ( $k = 0 ; $k < $nb_variants_to_display ; $k++ ) {
			$callWord      = $ordered_list_of_distributions[$k][0];
			$queryNoSlashB = $queryRE;
			$highlightedWord = $this->highlight( $callWord, $queryNoSlashB );
			$callWord = self::_escape_chars($callWord);
			if ( $k % $across == 0 ) 
				$sb .= "\t<tr>\n";
			$sb .=
		    	"\t  <td>\n"
		    	. "\t    <table width='100%'>\n"
		    	. "\t      <td align='left'><a href='"
		  		. self::$web
		  		. $this->web_language . "="
		  		. $callWord
		  		. $callShowLatin
		  		. "&lang="
		  		. $lang . "'>"
		  		. $highlightedWord
		  		. "</a></td>\n"
		  		. "\t      <td align='right'>"
		  		. $ordered_list_of_distributions[$k][1][0]
		  		. "</td></table>\n"
		  		. "\t  </td>\n";
			if ( $k % $across == $across - 1 )
				$sb .= "\t</tr>\n";
		}
		return $sb;
	}
	
	function _make_ordered_list_of_distributions ($list_order) {
		$unsortedlist = array();
		foreach ( array_keys($this->dist) as $key ) {
			array_push( $unsortedlist, array($key, $this->dist[$key]) );
		}
		$list = $unsortedlist;
		if ( $list_order == "f" ) {

			# Sort the list: highest $td{dist}{$key} first
			usort($list, function($a,$b) {
					if ( $a[1][0] < $b[1][0] )
						return 1;
					elseif ( $a[1][0] > $b[1][0] )
						return -1;
					else
						return 0;
					}
				);
		}
		else {

			# Sort the list alphabetically (or syllabically)
			usort($list, function($a,$b) {
					if ( $a[0] < $b[0] )
						return -1;
					elseif ( $a[0] > $b[0] )
						return 1;
					else
						return 0;
					}
				);
		}
		return $list;
	}

	function list_query_form ( $output_inuktitut_script, $query_language, $query, $lang ) {
		if ( $output_inuktitut_script == 'syl' ) {
			$show_latin = '';
			if ( $query_language == 'iu' )
				$query = self::_escape_chars($query);
		} else {
			$show_latin = 'on';
		}
		return "\n"
	  		. "<form action='NunavutHansard/index.php'>"
	  		. "<input type='submit' value='"
	  		. __NH_LIST_ALL_VARIANTS__
	  		. "' />"
	  		. "<input type='radio' name='list_order' value='a'"
	  		. " checked />"
	  		. (
		  		( $output_inuktitut_script == 'syl' )
					? __NH_SYLLABIC_ORDER__
					: __NH_ALPHABETICAL_ORDER__
	  			)
	  		. "<input type='radio' name='list_order' value='f' />"
	  		. __NH_HIGHEST_FREQ_FIRST__
	  		. "<input type='hidden' name='show_list' value='on' />"
	  		. "<input type='hidden' name='show_latin' value='$show_latin' />"
	  		. "<input type='hidden' name='lang' value='$lang' />"
	  		. "<input type='hidden' name='query_inuktitut' value='"
	  		. ($query_language == "iu" ? "$query" : "")
	  		. "'>"
	  		. "<input type='hidden' name='query_english' value='"
	  		. ($query_language == "en" ? "$query" : "")
	  		. "'>"
	  		. "</form>"
	  		. "\n";
	}

	function _escape_chars ($word) {
		$word = str_replace('&','%26',$word);
		$word = str_replace('#','%23',$word);
		$word = str_replace(';','%3B',$word);
		return $word;
	}



} // End of class
?>