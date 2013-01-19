<?php
/*
 * Created on 2011-02-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */

/*
 * [-R](1) to /offer, /give, /bring s.t. to someone (-mut) (2) to /take s.t. away forcibly from someone (-mit)
 * (1) to be unable to or incapable of doing s.t. (2) to not work, to be out of order
 * to file (intrans.: something; trans.: s.t.);  to use a file (intrans.: on something; trans.: on s.t.)
 * to skin (intrans.: an animal; trans.: s.t. (an animal))
 * 
 * niveau 1 - proposition
 *     ex. : to skin (intrans.: an animal; trans.: s.t. (an animal))
 * niveau 2 - sens
 *     ex. : (1) to be unable to or incapable of doing s.t. 
 * niveau 3 - mode
 *     ex. : [-R](1) to /offer, /give, /bring s.t. to someone (-mut) (2) to /take s.t. away forcibly from someone (-mit)
 * 
 * <énoncé> ::= <énoncés de mode> | <énoncés de sens> | <propositions>
 * <énoncés de mode> ::= <énoncé de mode> | <énoncé de mode> <énoncés de mode>
 * <énoncé de mode> ::= <indicateur de mode> ( <énoncés de sens> | <propositions> )
 * <énoncés de sens> ::= <énoncé de sens> <énoncés de sens 1ou+>
 * <énoncés de sens 1ou+> ::= <énoncé de sens> | <énoncé de sens> <énoncés de sens 1ou+>
 * <énoncé de sens> ::= <indicateur de sens> <propositions>
 * <propositions> ::= <proposition> | <proposition> ';' <propositions>
 * <proposition> ::= <élément> | <élément> <espace> <proposition>
 * <élément> ::= <mot> | <entre paranthèses> | <entre guillemets>
 * <entre paranthèses> ::= '(' <proposition> ')'
 * <entre > ::= '"' <proposition> '"'
 * <indicateur de mode> ::= '[' '-'? ('A' | 'R' | 'T' | 'P') ']'
 * <indicateur de sens> ::= '(' <chiffre> ')'
 */
 
 require_once 'lib/log4php/Logger.php';
 
 /*
  * Note importante : les énoncés de sens proviennent d'un champ dans un
  * fichier CSV où les champs sont séparés par des ';'. Si le contenu d'un
  * champ contient un ';' ou un '"', ce contenu est commencé et terminé par des
  * guillemets doubles (") ; dans ce cas, tout guillemet double à l'intérieur 
  * du champ est doublé ("").
  */
 class Analyzer_MeaningOfMorpheme_Statement
 {
 	
 	public function __construct ()
 	{
 	}
 	
 	public function parse($text)
 	{
 		$analyse = self::parse_all($text);
 		return $analyse;
 	}
 	
 	/*
 	 * Fonctions statiques ------------------------------------------------------------------------------
 	 */
 	
 	public static function parse_all (&$text)
 	{
 		$logger = Logger::getLogger('AnalyseurSensDeMorpheme.parse_all');
 		$original_text = $text;
 		$analysis = self::parse_mode_statements($text);
 		$logger->debug("\$analysis après parse_mode_statements : '$analysis'");
 		if ( ! $analysis )
 		{
 			$analysis = self::parse_meanings($text);
 			$logger->debug("\$analysis après parse_meanings : '$analysis'");
 			if ( ! $analysis )
 			{
 				$analysis = self::parse_propositions($text);
 				$logger->debug("\$analysis après parse_propositions : '$analysis'");
 			}
 		}
 		$logger->debug("\$text après analyse : '$text'");
 		if ( $text == '' )
 		{
 			return $analysis;
 		}
 		else
 		{
 			$text = $original_text;
 			return false;
 		}
 	}
 	
 	public static function meaning_index ($text)
 	{
 		$model = '/^\(\d\)$/';
 		if ( preg_match($model,$text) )
 			return true;
 		else
 			return false;
 	}
 	
 	public static function parse_mode_statements (&$text)
 	{
 		$original_text = $text;
 		$a_statement = self::parse_one_mode_statement($text);
 		if ( $a_statement )
 		{
 			$other_statements = self::parse_mode_statements($text);
 			if ( $other_statements )
 			{
 				$statements = array($a_statement);
 				$all_statements = array_merge($statements,$other_statements);
 				return $all_statements;
 			}
 			else
 			{
 				return array($a_statement);
 			}
 		}
 		else
 		{
 			$text = $original_text;
 			return false; 			
 		}
 	}
 	
 	public static function parse_mode_index (&$text)
 	{
 		if ( preg_match('/^\s*\[(-?[ARTP])\]/', $text, $matches) )
 		{
 			$mode = $matches[1];
 			$text = substr($text,strlen($matches[0]));
 			return $mode;
 		}
 		else
 		{
 			return false;
 		}
 	}
 	
 	public static function parse_one_mode_statement (&$text)
 	{
 		$logger = Logger::getLogger('MeaningOfMorpheme.parse_one_mode_statement');
 		$original_text = $text;
 		$mode = self::parse_mode_index($text);
 		$logger->debug("\$mode= '$mode");
 		if ( $mode )
 		{
 			$meanings = self::parse_meanings($text);
 			$logger->debug("\$meanings= '$meanings'");
 			if ( $meanings )
 			{
 				return array( 'mode' => $mode, 'sens' => $meanings );
 			}
 			$propositions = self::parse_propositions($text);
 			$logger->debug("\$propositions= '$propositions'");
 			if ( $propositions )
 			{
 				return array( 'mode' => $mode, 'propositions' => $propositions );
 			}
 			$text = $original_text;
 			return false;
 		}
 		else
 		{
 			$text = $original_text;
 			return false;
 		}
 	}
 	
 	public static function parse_meanings (&$text)
 	{
 		$original_text = $text;
 		$a_meaning = self::parse_one_meaning($text);
 		if ( $a_meaning )
 		{
 			$other_meanings = self::parse_meanings($text);
 			if ( $other_meanings )
 			{
 				$meanings = array($a_meaning);
 				$all_meanings = array_merge($meanings,$other_meanings);
 				return $all_meanings;
 			}
 			else
 			{
 				return array($a_meaning);
 			}
 		}
 		else
 		{
 			$text = $original_text;
 			return false; 			
 		}
 	}
 	
 	public static function parse_one_meaning (&$text)
 	{
 		$original_text = $text;
 		$meaning_index = self::parse_meaning_index($text);
 		if ( $meaning_index )
 		{
 			$propositions = self::parse_propositions($text);
 			if ( $propositions )
 				return array( 'nb' => $meaning_index, 'propositions' => $propositions );
 			else
 			{
 				$text = $original_text;
 				return false;
 			}
 		}
 		else
 		{
 			$text = $original_text;
 			return false;
 		}
 	}
 	
 	public static function parse_meaning_index (&$text)
 	{
 		$logger = Logger::getLogger('MeaningOfMorpheme.parse_meaning_index');
 		$in_paran = false;
 		$meaning_index = '';
 		for ($i=0; $i<strlen($text); $i++)
 		{
 			$logger->debug("\$text[$i]= '$text[$i]'");
 			if ( $in_paran )
 				if ( preg_match('/[0-9]/',$text[$i]) )
 				{
 					$meaning_index .= $text[$i];
 					$logger->debug("\$meaning_index= '$meaning_index'");
 				}
 				elseif ( $text[$i]==')' && $meaning_index != '' )
 				{
 					$text = substr($text,$i+1);
 					$logger->debug("retour: \$meaning_index= '$meaning_index'; \$text= '$text'");
 					return $meaning_index;	
 				}
 				else
 				{
  					$logger->debug("retour=false ; rencontré '$text[$i]' après paranthèse");
 					return false;
 				}
 			elseif ( $text[$i]==' ' )
 			{
 				$logger->debug("espace");
 				continue;
 			}
 			elseif ( $text[$i]=='(' )
 			{
 				$logger->debug("paranthèse rencontrée");
 				$in_paran = true;
 			}
 			else
 			{
 				$logger->debug("retour=false ; caractère invalide rencontré");
 				return false;
 			}
 		}
 		$logger->debug("retour=false ; sortie de la boucle");
 		return false;
 	}
 	
 	public static function parse_propositions (&$text)
 	{
 		$logger = Logger::getLogger('MeaningOfMorpheme.parse_propositions');
 		$original_text = $text;
 		$proposition = self::parse_one_proposition($text);
 		$logger->debug("\$proposition= '$proposition'; \$text= '$text'");
 		if ( $proposition )
 		{
 			$semi_colon = self::parse_semi_colon($text);
 			if ( $semi_colon )
 			{
 				$next_propositions = self::parse_propositions($text);
 				if ( $next_propositions )
 				{
 					$propositions = array($proposition);
 					$new_propositions = array_merge($propositions, $next_propositions);
 					return	$new_propositions;
 				}
 				else
 				{
 					return array($proposition);
 				}
 			}
 			else
 			{
 				return array($proposition);
 			}
 		}
 		else
 		{
 			$text = $original_text;
 			return false;
 		}
 	}
 	
 	public static function parse_semi_colon (&$text)
 	{
 		$original_text = $text;
 		for ($i=0; $i < strlen($text); $i++)
 		{
 			if ( $text[$i]==';' )
 			{
 				$text = substr($text,$i+1);
 				return ';';
 			}
 			elseif ( $text[$i] != ' ' )
 			{
 				return false;
 			}
 		}
 		return false;
 	}
 	
 	/*
 	 * <proposition> ::= <élément> | <élément> <espace> <proposition>
 	 * <élément> ::= <texte> | <entre paranthèses> | <entre guillemets>
 	 * <entre paranthèses> ::= '(' <proposition> ')'
 	 * <entre guillemets> ::= '"' <proposition> '"'
 	 */
 	public static function parse_one_proposition (&$text)
 	{
// 		if ( strlen(trim($text))==0 )
// 			return false;
 			
 		$original_text = $text;
 		$proposition = '';
 		for ($i=0; $i<strlen($text); $i++)
 		{
 			if ( $text[$i] == '(' )
 			{
 				$text = substr($text,$i);
 				$text_between_parentheses = self::parse_between_parentheses($text);
 				if ( $text_between_parentheses )
 					if ( self::meaning_index($text_between_parentheses) )
 					{
 						$text = $text_between_parentheses . $text;
 						return trim($proposition);
 					}
 					else
 					{
 						$i = -1;
 						$proposition .= $text_between_parentheses;
 					}
 				else
 				{
 					$text = $original_text;
 					return false;
 				}
 			}
 			elseif ( $text[$i] == '[' )
 			{
 				$text = substr($text,$i);
 				return trim($proposition);
 			}
 			elseif ( $text[$i] == '"' )
 			{
 				$quote = $text[$i];
 				$text = substr($text,$i);
 				$teg = self::parse_between_quotes($text, $quote);
 				if ( $teg )
 				{
 					$i = -1;
 					$proposition .= $teg;
 				} 
 				else
 				{
 					$text = $original_text;
 					return false;
 				}
 			}
 			elseif ( $text[$i] == ';' )
 			{
 				$text = substr($text, $i);
 				return trim($proposition);
 			}
 			else
 			{
 				$proposition .= $text[$i];
 			}
 		}
 		$text = '';
 		return trim($proposition);
 	}
 	
 	public static function parse_between_parentheses (&$text)
 	{
 		$logger = Logger::getLogger('MeaningOfMorpheme.parse_between_parentheses');
 		$logger->debug(">>> \$text= '$text'");
 		$original_text = $text;
 		$text_between_parentheses = $text[0];
 		$text = substr($text,1);
 		for ($j=0; $j<strlen($text); $j++)
 		{
 			$logger->debug("# j=$j ; \$text= $text");
 			if ( $text[$j]==')' )
 			{
 				$text_between_parentheses .= substr($text,0,$j+1);
 				$text = substr($text,$j+1);
 				$logger->debug("<<< \$text_between_parentheses= '$text_between_parentheses'; \$text= '$text'");
 				return $text_between_parentheses;
 			}
 			elseif ( $text[$j]=='(' )
 			{
 				$text_between_parentheses .= substr($text,0,$j);
 				$next_text = substr($text,$j);
 				$text_between_parentheses_in_next_text = self::parse_between_parentheses($next_text);
 				$logger->debug("<<< \$text_between_parentheses_in_next_text= '$text_between_parentheses_in_next_text'; \$next_text= '$next_text");
 				if ( $text_between_parentheses_in_next_text )
 				{
 					$text_between_parentheses .= $text_between_parentheses_in_next_text;
 					$text = $next_text;
 					$j= -1;
 				}
 				else
 				{
 					$text = $original_text;
 					return false;
 				}
 			}
 		}
 		$text = $original_text;
 		return false;
 	}
 	
 	public static function parse_between_quotes (&$text, $quote)
 	{
 		$logger = Logger::getLogger('MeaningOfMorpheme.parse_between_quotes');
 		$logger->debug(">>> \$text= '$text'");
 		$original_text = $text;
 		$text_between_quotes = $text[0];
 		$text = substr($text,1);
 		$logger->debug("\$text après premier guillemet= '$text'");
 		for ($j=0; $j<strlen($text); $j++)
 		{
 			$logger->debug("# j=$j ; \$text[\$j]= $text[$j]");
 			if ( $text[$j]==$quote )
 			{
 				$logger->debug("guillemet de fermeture rencontré ; \$text= '$text' ; \$j= $j");
 				if ( $j < strlen($text)-1 && $text[$j+1] != '"' )
 				{ // guillemet de fermeture
 					$text_between_quotes .= substr($text,0,$j+1);
 					$logger->debug("\$text_between_quotes= '$text_between_quotes'");
 					$text = substr($text,$j+1);
 					$logger->debug("<<< \$text_between_quotes= '$text_between_quotes'; \$text= '$text'");
 					return $text_between_quotes;
 				}
 				else
 					$j++;
 			}
 		}
 		$text = $original_text;
 		return false;
 	}
 	
 }
 
?>
