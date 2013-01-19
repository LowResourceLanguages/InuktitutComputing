<?php
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		VerbRoot.php
//
// Type/File type:		PHP
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de crÃ©ation/Date of creation:	
//
// Description: Classe Base
//
// -----------------------------------------------------------------------

require_once "linguisticobjects/Root.php";
require_once "linguisticobjects/VerbWord.php";
require_once "linguisticobjects/LinguisticObjects.php";
require_once "lib/Common.php";
require_once 'linguisticobjects/Analyzer_MeaningOfMorpheme_Statement.php';
require_once 'linguisticobjects/MeaningOfMorpheme.php';
require_once 'linguisticobjects/Morpheme.php';
require_once 'lang/LinguaENParticiple.php';
require_once 'lang/LinguaFRParticiple.php';

require_once "lib/log4php/Logger.php";

class VerbRoot extends Root {
	//morpheme,variant,key,type,transitivity,nature,number,compositionRoot,
	//plural,intransSuffix,transSuffix,antipassive,engMean,freMean,source,dialect,cf,END

    private $transitivity = null;
    private $transinfix = null;
    private $intransinfix = null;
    private $antipassive = null;
    private $subtype = null;
    private $compositionRoot = null;
    
	private $transitiveMeaning_e = null;
	private $passiveMeaning_e = null;
	private $reflexiveMeaning_e = null;
	private $resultMeaning_e = null;
	private $transitiveMeaning_f = null;
	private $passiveMeaning_f = null;
	private $reflexiveMeaning_f = null;
	private $resultMeaning_f = null;
	private $english_meanings = null;
	private $french_meanings = null;
    
    private $idsOfCompositesWithThisRoot = null;
    
    private static $pattern_for_verb_complement = 
    	array( 
    		'en' => '/(?:s\.t\. or s\.o\.)|(?:s\.o\. or s\.t\.)|(?:s\.t\.)|(?:s\.o\.)/',
    		'fr' => '/(?:qqch\. ou qqn)|(?:qqn ou qqch\.)|(?:qqch\.)|(?:qqn)/' );
    private static $translations_of_complements =
    	array(
    		's.t. or s.o.' => 'something or someone',
    		's.o. or s.t.' => 'someone or something',
    		's.t.' => 'something',
    		's.o.' => 'someone',
    		'qqch. ou qqn' => 'quelque chose ou quelqu\'un',
    		'qqn ou qqch.' => 'quelqu\'un ou quelque chose',
    		'qqn' => 'quelqu\'un',
    		'qqch.' => 'quelque chose');
    
	//-----------------------------------------------------------------------------------------------
	public function __construct ($fieldsAndValues) {
		$inuktitut_php = getenv('PHP_INUKTITUT');
		if ( $fieldsAndValues != null ) {
		parent::__construct($fieldsAndValues);
		$this->antipassive = $fieldsAndValues['antipassive'];
		$this->transinfix = $fieldsAndValues['transSuffix'];
		$this->intransinfix = $fieldsAndValues['intransSuffix'];
		$this->transitivity = $fieldsAndValues['transitivity'];
        $this->cf = $fieldsAndValues['cf'];
        if ($this->cf != NULL && $this->cf!='') $this->cfs = explode(' ',$this->cf);
        $this->dialect = $fieldsAndValues['dialect'];
        $this->source = $fieldsAndValues['source'];
		if ($this->source == NULL || $this->source == '')
			$this->source = 'A2';
		$this->sources = explode(' ',$this->source);
		$this->morphemeID =self::make_id($fieldsAndValues);
		}
	}

	public function attribute($attribute_name) {
		return $this->$attribute_name;
	}
	
	//-------------------------------------------------------------------------------------------------------

	public function getCompositionRoot() {
	    return $this->compositionRoot;
	}
	
	//-------------------------------------------------------------------------------------------------------	
	public function transinfix() {
		return $this->transinfix;
	}
	
	public function intransinfix() {
		return $this->intransinfix;
	}
	
	public function isGiVerb() {
    	if ( $this->transinfix != null && preg_match('/^gi/',$this->transinfix) )
			return true;
		else
			return false;
	}
	
	public function isTransitiveVerb() {
		if ( $this->transitivity == 't' )
			return true;
		else
			return false;
	}
	
	public function isIntransitiveVerb() {
		if ( $this->transitivity == 'i' )
			return true;
		else
			return false;
	}
	
	public function isResultiveVerb() 
	{
		if ( $this->nature() == 'res' )
			return true;
		else
			return false;
	}
	
	public function antipassive() {
		return $this->antipassive;
	}
	
	public function one_antipassive()
	{
		$antipassives = explode(' ',$this->antipassive);
		return $antipassives[0];
	}
	
	public function nb_antipassives()
	{
		$antipassives = explode(' ',$this->antipassive);
		return count($antipassives);
	}
	
	
	/*
     * Roots' transitivity is defined as follows: 
     * t: transitive 
     *   If the value of 'antipassive' is not null, the root may be used
     *   intransitively (reflexive or passive transitiveMeaning)
     * i: intransitive 
     *   If the value of 'transinfix' is "nil", the root may also be transitive
     */
	public function agreesWithTransitivity( $trans ) {
	    if ($trans==null)
	        return true;
	    else if ($this->transitivity==null)
	        return false;
	    else if ($this->transitivity == 't' && $trans == 't')
	        return true;
        /*
         * Certains verbes intransitifs peuvent Ãªtre utilisÃ©s transitivement sans
         * infixe de transitivitÃ©.  Ceux-ci ont transSuffix=nil.
         */
	    else if ($this->transitivity == 'i' &&
	              ($trans == 'i' ||  ($trans == 't' && $this->transinfix == 'nil') ))
	        return true;
        /*
         * Les verbes transitifs avec un antipassif non-nul peuvent Ãªtre utilisÃ©s
         * intransitivement; ils sont alors interprÃ©tÃ©s passivement ou rÃ©flexivement.
         */
	    else if ($this->transitivity == 't' && $this->antipassive != null &&
	            $trans == 'i' )
	        return true;
	    else
	        return false;
	}
	
	public function needsAntipassive($apId) {
	    if ( $this->antipassive != null) {
	    	$aps = explode(' ',$this->antipassive);
	    	foreach ($aps as $ap)
	            if ( $ap == $apId )
	                return true;
	        return false;
	    }
	    else
	        return false;
	}
	
	/*
	 * The next 2 methods should be called for intransitive verbs only.
	 */
	public function getIntransitiveMeaningOfIntransitiveVerb($lang, $with_object=FALSE)
	{
		if ( $this->transitivity == 'i' )
		{
			if ( ($lang=='en'? $this->english_meanings : $this->french_meanings) == null )
				$this->makeMeaningsForIntransitiveVerbs();
			$ivs = array();
			$which_meaning = 'iv_intrans_no_object';
			if ( $with_object )
				$which_meaning = 'iv_intrans_with_object';
			foreach ( ($lang=='en'? $this->english_meanings : $this->french_meanings) as $a_meaning )
			{
				if ( array_key_exists($which_meaning, $a_meaning) )
					array_push($ivs, $a_meaning[$which_meaning]);
			}
			return $ivs;
		}
		else
			return null;
	}
		
 	public function getTransitiveMeaning($lang)
	{
		$logger = Logger::getLogger('VerbRoot.getTransitiveMeaning');
		if ( $this->transitivity == 'i' )
		{
			$logger->debug("getting meanings for Intransitive verb root");
			if ( ($lang=='en'? $this->english_meanings : $this->french_meanings) == null )
				$this->makeMeaningsForIntransitiveVerbs();
			$ivs = array();
			foreach ( ($lang=='en'? $this->english_meanings : $this->french_meanings) as $a_meaning )
			{
				if ( array_key_exists('iv_trans', $a_meaning) )
					array_push($ivs, $a_meaning['iv_trans']);
			}
			return $ivs;
		}
		else
		{
			$logger->debug("getting meanings for Transitive verb root");
			if ( ($lang=='en'? $this->english_meanings : $this->french_meanings) == null )
				$this->makeMeaningsForTransitiveVerbs();
			$tvs = array();
			$ref = $lang=='en'? $this->english_meanings : $this->french_meanings;
			foreach ( array_keys($ref) as $key )
			{
				$meanings_for_this_number = $ref[$key];
				foreach ( $meanings_for_this_number as $a_meaning )
				{
					$logger->debug("\$a_meaning=\n".print_r($a_meaning,true));
					if ( $a_meaning['trans'] != null )
						array_push($tvs, $a_meaning['trans']);
				}
			}
			return $tvs;
		}
	}
	
    /*
     * The next 4 methods should be called for transitive verbs only.
     */

    public function getPastParticipleMeaning($lang)
    {
    	return $this->_get_meaning('passive', $lang);
	}
    
    public function getResultiveMeaning($lang) {
    	return $this->_get_meaning('resultive', $lang);
    }
    
    public function getReflexiveMeaning($lang) {
    	return $this->_get_meaning('reflexive', $lang);
    }
    
    public function getTransitiveMeaningWithIntransEndingAndAntipassive ($lang) {
    	return $this->_get_meaning('intrans', $lang);
    }

	public function _get_meaning($type, $lang)
	{
    	$logger = Logger::getLogger('VerbRoot._get_meaning');
    	$logger->debug("getting '$type' meanings for Transitive verb root");
		if ( ($lang=='en'? $this->english_meanings : $this->french_meanings) == null )
			$this->makeMeaningsForTransitiveVerbs();
		$vs = array();
		$ref = $lang=='en'? $this->english_meanings : $this->french_meanings;
		foreach ( array_keys($ref) as $key )
		{
			$meanings_for_this_number = $ref[$key];
			foreach ( $meanings_for_this_number as $a_meaning )
			{
				$logger->debug("\$a_meaning=\n".print_r($a_meaning,true));
				if ( $a_meaning[$type] != null )
					array_push($vs, $a_meaning[$type]);
			}
		}
		return $vs;
	}
	
    /*
     * Modèle général d'une chaîne de texte pour le sens d'un verbe intransitif :
     * ( ( '(' [1-9] ')' )? texte )+
     * où texte peut contenir (trans.: <préposition>? s.t.) ou (intrans.: ...; trans.: ...)
     */
    public function makeMeaningsForIntransitiveVerbs() {
		$this->english_meanings = self::makeMeaningsForIntransitiveVerbsForSense($this->engMeaning, 'en');
		$this->french_meanings = self::makeMeaningsForIntransitiveVerbsForSense($this->frenchMeaning, 'fr');
	}
	
	public static function makeMeaningsForIntransitiveVerbsForSense ($string, $lang)
	{
		$logger = Logger::getLogger('VerbRoot.makeMeaningsForIntransitiveVerbsForSense');
		$verb_senses = self::_makeVerbMeanings($string);

		$meanings = array();
		foreach ($verb_senses as $a_verb_sens)
		{
			$a_sense_number = $a_verb_sens->nb();
			$string_sens = $a_verb_sens->toString();
			$logger->debug("\$string_sens= '$string_sens'");
			$sense_with_verb_without_slash = preg_replace('/(^|\s)\/([a-zA-Zàâèéëêïîôùüûç\-]+)/',"$1$2",$string_sens);
			$logger->debug("\$sense_with_verb_without_slash= '$sense_with_verb_without_slash'");
			if ( preg_match( '/\(intrans\.:\s*(.+?);\s*trans\.:\s*(.+)\)/', $sense_with_verb_without_slash, $matches ) )
			{
				$intrans_no_object = trim(str_replace($matches[0],$matches[1],$sense_with_verb_without_slash));
				$intrans_with_object = trim(str_replace($matches[0], $matches[2].' (-mik)', $sense_with_verb_without_slash));
				$trans_with_object = trim(str_replace($matches[0], $matches[2], $string_sens));
				$prep_chaine_sens = self::prepareSenseForTransformation($trans_with_object,$lang);
				$logger->debug("\$prep_chaine_sens= '$prep_chaine_sens'");
				$trans_with_object = self::toTransitive($prep_chaine_sens, $lang);
// 				preg_match("/c\((.+?)\)/", $prep_chaine_sens, $cmatches);
// 				$object = self::replaceObjectWithPersPron($cmatches[1],'direct',$lang);
// 				$trans_with_object = str_replace($cmatches[1], $object, $trans_with_object);
				$logger->debug("\$trans_with_object= '$trans_with_object'");
				$meanings[$a_sense_number] =
					array(
						'iv_intrans_no_object' => $intrans_no_object,
						'iv_intrans_with_object' => $intrans_with_object,
						'iv_trans' => $trans_with_object);		
			}
			elseif ( preg_match( '/\(trans\.:\s*(.+)\)/', $string_sens, $matches ) )
			{
				$intrans_no_object = trim(str_replace($matches[0], '', $sense_with_verb_without_slash));
				$intrans_with_object = str_replace($matches[0], $matches[1].' (-mik)',$sense_with_verb_without_slash);
				$trans_with_object = trim(str_replace($matches[0], $matches[1], $string_sens));
				$prep_chaine_sens = self::prepareSenseForTransformation($trans_with_object,$lang);
				$logger->debug("\$prep_chaine_sens= '$prep_chaine_sens'");
				$trans_with_object = self::toTransitive($prep_chaine_sens, $lang);
// 				preg_match("/c\((.+?)\)/", $prep_chaine_sens, $cmatches);
// 				$object = self::replaceObjectWithPersPron($cmatches[1],'direct',$lang);
// 				$trans_with_object = str_replace($cmatches[1], $object, $trans_with_object);
				$logger->debug("\$trans_with_object= '$trans_with_object'");
				$meanings[$a_sense_number] =
					array(
						'iv_intrans_no_object' => $intrans_no_object,
						'iv_intrans_with_object' => $intrans_with_object,
						'iv_trans' => $trans_with_object);
			}
			elseif ( preg_match_all( self::$pattern_for_verb_complement[$lang], $string_sens, $matches, PREG_SET_ORDER | PREG_OFFSET_CAPTURE ) )
			{ // s.o. or s.t.  ---   s.t. or s.o.   ---   s.t.   ---   s.o.
				$offset = 0;
				$string_sens_intrans_no_obj = '';
				$string_sens_intrans_with_obj = '';
				$string_sens_trans_with_obj = '';
				foreach ( $matches as $a_match )
				{
					$matched = $a_match[0][0];
					$offset_of_matched = $a_match[0][1];
					$string_sens_intrans_no_obj .= substr($string_sens,$offset,($offset_of_matched-$offset));
					$string_sens_intrans_no_obj .= self::$translations_of_complements[$matched];
					$string_sens_intrans_with_obj .= substr($string_sens,$offset,($offset_of_matched-$offset));
					$string_sens_intrans_with_obj .= $matched . ' (-mik)';
					$string_sens_trans_with_obj .= substr($string_sens,$offset,($offset_of_matched-$offset));
					$string_sens_trans_with_obj .= self::replaceObjectWithPersPron($matched,'direct',$lang);
					$offset = $offset_of_matched + strlen($matched);
				}
				$string_sens_intrans_no_obj .= substr($string_sens,$offset);
				$string_sens_intrans_with_obj .= substr($string_sens,$offset);
				$string_sens_trans_with_obj .= substr($string_sens,$offset);
				$meanings[$a_sense_number] =
					array(
						'iv_intrans_no_object' => $string_sens_intrans_no_obj,
						'iv_intrans_with_object' => $string_sens_intrans_with_obj,
						'iv_trans' => $string_sens_trans_with_obj);
			}
			else
			{
				$meanings[$a_sense_number] =
					array( 
						'iv_intrans_no_object' => $string_sens,
//						'iv_intrans_with_object' => null,
//						'iv_trans' => null
					);
			}
		}
		return $meanings;
	}
	
	
    
    
	//---------------------------------------------------------------------------------------------------------
    /*
     * ModÃ¨le gÃ©nÃ©ral d'une chaÃ®ne de texte pour le sens d'un verbe transitif:
     * ('[' '-'? ('R' | 'T' | 'P' | 'A') ']' texte )+
     * texte
     */
    public function makeMeaningsForTransitiveVerbs() {
		$this->english_meanings = $this->makeMeaningsForTransitiveVerbsForSense('en');
		$this->french_meanings = $this->makeMeaningsForTransitiveVerbsForSense('fr');
    }
    
    public function makeMeaningsForTransitiveVerbsForSense($lang)
    {
		$logger = Logger::getLogger('VerbRoot.makeMeaningsForTransitiveVerbsForSense');
		$string = $lang=='en' ? $this->engMeaning : $this->frenchMeaning;
		$logger->debug("\$string= '$string'");
		$verb_senses = self::_makeVerbMeanings($string);
		$logger->debug("\$verb_senses=\n".print_r($verb_senses,true));
		$meanings = array();
		foreach ($verb_senses as $a_verb_sense)
		{
			$a_sense_number = $a_verb_sense->nb();
			$sense = $a_verb_sense->toString();
			$mode = $a_verb_sense->mode();
			list( $trans_mode, $pass_mode, $refl_mode ) = self::_set_trans_pass_refl($mode);
			$logger->debug("\$trans_mode= '$trans_mode' ; \$pass_mode= '$pass_mode' ; \$refl_mode= '$refl_mode'");
			
			$meanings_for_this_verb_sens = 
				$this->_makeTransPassRefl_for_one_verb_sense ($sense, $trans_mode, $pass_mode, $refl_mode, $lang);
			$logger->debug("\$meanings_for_this_verb_sense=\n".print_r($meanings_for_this_verb_sens,true));
			$logger->debug("trans: ".$meanings_for_this_verb_sens['trans']);
			$logger->debug("\$a_sense_number= '$a_sense_number'");
			
			if ( ! array_key_exists($a_sense_number, $meanings) || ! is_array($meanings[$a_sense_number]) )
				$meanings[$a_sense_number] = array();
			
			array_push($meanings[$a_sense_number], $meanings_for_this_verb_sens);
		}
		$logger->debug("\$meanings=\n".print_r($meanings,true));
		return $meanings;
    }
    
    public function _makeTransPassRefl_for_one_verb_sense ($sense, $trans_mode, $pass_mode, $refl_mode, $lang)
    {
    	$logger = Logger::getLogger('VerbRoot._makeTransPassRefl_for_one_verb_sense');
		$pattern_pm = '/(to )?(\/[a-zA-Zàâèéëêïîôùüûç\-]+)/';  // texte =(to) /verbe1, /verb2, ... blablabla
		$toBool = $lang=='en' ? false : true;
   	 	$offset = 0;
    	$match_count = 0;
    	$logger->debug("\$sense: '$sense");
    	$logger->debug("pattern: '$pattern_pm'");
    	
    	$transitiveMeaning = '';
    	$intransitiveMeaning = '';
    	$passiveMeaning = '';
    	$reflexiveMeaning = '';
    	$resultMeaning = '';
    	 
    	/*
    	 *   Experimental
    	 *   
    	 */
    	$prepared_sense = self::prepareSenseForTransformation($sense,$lang);
    	$logger->debug("\$prepared_sense: '$prepared_sense'");
    	if ( $trans_mode) {
    		$transitiveMeaning = self::toTransitive($prepared_sense,$lang);
    		$logger->debug("\$transitiveMeaning from experimental 'toTransitive': '$transitiveMeaning'");
    		$intransitiveMeaning = self::toIntransitive($prepared_sense);
    		$intransitiveMeaning = self::addMik($intransitiveMeaning, $lang);
    		$logger->debug("\$intransitiveMeaning from experimental 'toIntransitive': '$intransitiveMeaning'");
    	}
    	if ( $pass_mode) {
    		$passiveMeaning = self::toPassive($prepared_sense,$lang);
    		$logger->debug("\$passiveMeaning from experimental 'toPassive': '$passiveMeaning'");
    	}
    	if ( $refl_mode ) {
   			if ( $this->isResultiveVerb() ) {
   				$resultMeaning = self::toResultive($prepared_sense, $lang);
   				$logger->debug("\$resultMeaning from experimental 'toResultive': '$resultMeaning'");
   			}
   			else {
   				$reflexiveMeaning = self::toReflexive($prepared_sense, $lang);
   				$logger->debug("\$reflexiveMeaning from experimental 'toReflexive': '$reflexiveMeaning'");
   			}
    	}
    	/*
    	 *
    	 */
    	
    	/*
    	if ( preg_match($pattern_pm, $sense, $pmmatches, PREG_OFFSET_CAPTURE, $offset) ) 
    		$logger->debug("matches");
    	else
    		$logger->debug("no matches");
    	
    	// PREG_OFFSET_CAPTURE:     index 0: the match    index 1: the position of the match
    	while(preg_match($pattern_pm, $sense, $pmmatches, PREG_OFFSET_CAPTURE, $offset))
    	{
    		$matchgroup_for_infinitive_marked_verb = $pmmatches[0];
    		$matchgroup_for_to_word = $pmmatches[1];
    		$matchgroup_for_verb_word = $pmmatches[2];
        	$pos_of_infinitive_marked_verb = $matchgroup_for_infinitive_marked_verb[1]; 
        	$before_infinitive_marked_verb = substr($sense,$offset,$pos_of_infinitive_marked_verb-$offset);
			if ( $matchgroup_for_to_word[0] )
				$toBool = true;
			elseif ($lang=='en')
				$toBool = false;
			$verb_word = $matchgroup_for_verb_word[0];
			
			list($transitiveMeaning,$passiveMeaning,$reflexiveMeaning,$resultMeaning) = 
        		$this->processTransitiveVerbMeaning($verb_word, $before_infinitive_marked_verb, $toBool,
        				$pass_mode, $refl_mode, $trans_mode, $lang,
        				$transitiveMeaning,$passiveMeaning,$reflexiveMeaning,$resultMeaning);
        				
        	$offset = $matchgroup_for_infinitive_marked_verb[1] + strlen($matchgroup_for_infinitive_marked_verb[0]);
        	$logger->debug("transitive meaning OUT: '$transitiveMeaning");
    	}
    	
		// Partie finale
		$transitive_part = substr($sense,$offset);
    	$passive_part = substr($sense,$offset);
		$reflexive_part = substr($sense,$offset);
		
				
		$logger->debug("\$trans= '$trans_mode'");
		$logger->debug("\$offset= $offset ; longueur \$sense= ".strlen($sense));
    	$logger->debug("\$sense: '$sense");

    	if ( $trans_mode ) 
    	{
			$transitiveMeaning .= $transitive_part;
    		$logger->debug("1) \$transitiveMeaning= '$transitiveMeaning");
			$transitiveMeaning = self::replaceFrenchPrepositionsAndReflexivePronouns($transitiveMeaning);
			$intransitiveMeaning = self::addMik($transitiveMeaning, $lang);
			$transitiveMeaning = self::replaceWithPersPron($transitiveMeaning, $lang);
			$logger->debug("2) \$transitiveMeaning= '$transitiveMeaning");
		}
		
		if ( $pass_mode ) 
		{
			$passive_part = self::replaceObjectInPassivePart($passive_part,$lang);
			$passiveMeaning .= $passive_part;
			$passiveMeaning = str_replace('be /made ', 'be made to ', $passiveMeaning);
			$passiveMeaning = str_replace('/','',$passiveMeaning);
			$passiveMeaning = trim($passiveMeaning);
			$passiveMeaning = self::removeObject($passiveMeaning,$lang);
		}
		
		if ( $refl_mode ) {
			if ( $this->isResultiveVerb() ) {
				$resultMeaning .= $passive_part;
				$resultMeaning = str_replace('to /make', 'to', $resultMeaning);
				$resultMeaning = str_replace('/make', 'to', $resultMeaning);
				$resultMeaning = str_replace('/', '', $resultMeaning);
				$resultMeaning = trim($resultMeaning);
				if ( $resultMeaning=='' ) 
					$resultMeaning = null;
				else
					$resultMeaning = self::removeObject($resultMeaning,$lang);
			}
			else {
				$reflexiveMeaning = str_replace('-à', '', $reflexiveMeaning);
				$reflexive_part = self::replaceObjectInReflexivePart($reflexive_part,$lang);
				$reflexiveMeaning .= $reflexive_part;
				$reflexiveMeaning = trim($reflexiveMeaning);
				if ( $reflexiveMeaning=='' ) 
					$reflexiveMeaning = null;
				else
					$reflexiveMeaning = self::replaceObject($reflexiveMeaning,$lang);
			}
		}
		*/
    	
		return array(
			'trans' => $transitiveMeaning, 
			'intrans' => $intransitiveMeaning, 
			'passive' => $passiveMeaning, 
			'reflexive' => $reflexiveMeaning, 
			'resultive' => $resultMeaning
		);	
 	}
    
    static function _set_trans_pass_refl ($mode)
    {
		$trans = true;
		$pass = true;
		$refl = true;
    	$mode_plus_sign = true;
		if ( $mode[0]=='-' )
		{
			$mode_plus_sign = false;
			$mode = substr($mode,1);
		}
		if ( $mode == 'R') {
			if ( $mode_plus_sign ) {
				$trans = false;
				$pass = false;
			}
			else {
				$refl = false;
			}
		} elseif ( $mode == 'T' ) {
			if ( $mode_plus_sign ) {
				$refl = false;
				$pass = false;
			} else {
				$trans = false;
			}
		} elseif ( $mode == 'P' ) {
			if ($mode_plus_sign) {
				$trans = false;
				$refl = false;
			} else {
				$pass = false;
			}
		}
		return array( $trans, $pass, $refl );
    }
    
//     public static function replaceObjectInPassivePart ($text, $lang) {
//     	$logger = Logger::getLogger('VerbRoot.replaceObjectInPassivePart');
//     	$logger->debug("\$text= '$text'");
//     	if ( $lang=='en' ) {
// 			$passive_part = str_replace('on top of s.t.', 'atop', $text);
// 			$passive_part = str_replace('s.t.\'s', 'its', $text);
// 			$passive_part = str_replace('s.o.\'s', 'his or her', $text);
// 			$passive_part = str_replace(' s.o. or s.t.', '', $text);
// 			$passive_part = str_replace(' s.t. or s.o.', '', $text);
// 			$passive_part = str_replace(' s.o.', '', $text);
// 			$passive_part = str_replace(' s.t.', '', $text);
// 		} elseif ( $lang=='fr' ) {
// 			$passive_part = str_replace(' qqn ou qqch.', '', $text);
// 			$passive_part = str_replace(' qqch. ou qqn', '', $text);
// 			$passive_part = str_replace(' qqn', '', $text);
// 			$passive_part = str_replace(' qqch.', '', $text);
// 		}
// 		$logger->debug("\$passive_part= '$passive_part'");
// 		return $passive_part;
//     }
    
//     public static function replaceObjectInReflexivePart ($text, $lang) {
//     	$logger = Logger::getLogger('VerbRoot.replaceObjectInReflexivePart');
//     	$logger->debug("\$text in '$lang' on entry= '$text'");
//     	if ( $lang=='en' ) {
// 			$reflexive_part = str_replace('s.t.\'s', 'its own', $text);
// 			$reflexive_part = str_replace('s.o.\'s', 'his or her own', $text);
// 			$reflexive_part = str_replace(' s.o. or s.t.', ' oneself or itself', $text);
// 			$reflexive_part = str_replace(' s.t. or s.o.', ' itself or oneself', $text);
// 			$reflexive_part = str_replace(' s.o.', ' oneself', $text);
// 			$reflexive_part = str_replace(' s.t.', ' itself', $text);
// 		} elseif ( $lang=='fr' ) {
// 			$reflexive_part = str_replace(' qqn ou qqch.', '', $text);
// 			$reflexive_part = str_replace(' qqch. ou qqn', '', $text);
// 			$reflexive_part = str_replace(' qqn', '', $text);
// 			$reflexive_part = str_replace(' qqch.', '', $text);		
// 		}
// 		return $reflexive_part;
//     }
    
    public static function makeVerbFormPassive ( $verbKey, $lang) {
    	$logger = Logger::getLogger('VerbRoot.makeVerbFormPassive');
    	$logger->debug("\$verbKey in '$lang' on entry= '$verbKey'");
    	preg_match_all("/\/([a-zA-Zàâèéëêïîôùüûç\-]+)/",$verbKey,$matches);
    	$verb_words = $matches[1];
    	$logger->debug("\$verb_words= ".print_r($verb_words,1));
    	$passive_verbKey = $verbKey;
    	foreach ($verb_words as $a_verb_word) {
    		$logger->debug("\$a_verb_word= $a_verb_word");
    		if ( $lang == 'en' ) {
				$vpass = LinguaENParticiple::participle($a_verb_word);
    	    	$logger->debug("\$vpass= $vpass");
    		}
    		else {
				// enlever la partie 'préposition' de la clé (ex.:  '-à' dans 'donner-à') 
				// (à la fin de la clé)
				$verbKey_without_prep = self::removePrepositionFromEndOfVerbWord($a_verb_word);
				$verbPrep = preg_replace("/$verbKey_without_prep/","",$a_verb_word);
				$verbKey_without_prep = preg_replace('/^(?:se-|s\')/', '', $verbKey_without_prep);
				if ( $verbPrep=='à' || $verbPrep=='-contre' || $verbPrep=='-de' || $verbPrep=='-dans' )
					$vpass = 'se faire ' . $verbKey_without_prep;
				elseif ( $verbPrep=='-sur' ) 
					$vpass = 'se faire ' . $verbKey_without_prep . ' dessus';
				else
					$vpass = LinguaFRParticiple::participle($verbKey_without_prep);
    		}
    		$logger->debug("\$passive_verbKey = str_replace('$a_verb_word', '$vpass', '$passive_verbKey')");
    		$passive_verbKey = str_replace($a_verb_word, $vpass, $passive_verbKey);
    		$logger->debug("\$passive_verbKey= $passive_verbKey");
    	}
    	$passive_verbKey = str_replace('/','',$passive_verbKey);
    	$logger->debug("\$passive_verbKey on exit= '$passive_verbKey'");
    	return $passive_verbKey;
    }
	//---------------------------------------------------------------------------------------------------------

// 	public function processTransitiveVerbMeaning($verb_word, $before_infinitive_marked_verb, $toBool,
// 					$pass_mode, $refl_mode, $trans_mode, $lang,
//         			$transitiveMeaning,$passiveMeaning,$reflexiveMeaning,$resultMeaning) {
//         $logger = Logger::getLogger('VerbRoot.processTransitiveVerbMeaning');
//         $logger->debug("\$verb_word='$verb_word'");
//         $logger->debug("\$before_infinitive_marked_verb='$before_infinitive_marked_verb'");
//         $passive_part = $before_infinitive_marked_verb;
// 		$reflexive_part = $before_infinitive_marked_verb;		
// 		$to = "";
// 		if ( $toBool && $lang=='en' )
// 			$to = 'to ';

// 		if ( $pass_mode ) {
// 			$passive_part = self::replaceObjectInPassivePart($before_infinitive_marked_verb,$lang);	
// 			$passiveMeaning .= $passive_part;
// 			if ( $toBool )
// 				if ( $lang=='fr' )
// 					$passiveMeaning .= 'être ';
// 				else
// 					$passiveMeaning .= $to . 'be ';
// 			$vpass = self::makeVerbFormPassive($verb_word,$lang);
// 			$verb_word_without_initial_slash = substr($verb_word,1);
// 			$passiveMeaning .= '/' . $vpass;
// 			if ( $lang=='fr' )
// 				$passiveMeaning = str_replace('être /se faire','/se faire',$passiveMeaning);
// 		}
		
// 		if ( $refl_mode ) {
// 			$logger->debug("make a reflexive meaning");
// 			if ( $this->isResultiveVerb() )
// 			{
// 				$resultMeaning .= $passive_part;  // or $reflexive_part ?
// 				$resultMeaning .= $to . '/' . $verb_word_without_initial_slash;
// 			}
// 			else
// 			{
// 				$reflexive_part = self::replaceObjectInReflexivePart($before_infinitive_marked_verb,$lang);	
// 				$reflexiveMeaning .= $reflexive_part;
// 				if ( $lang=='fr' )
// 					if ( preg_match('/[aeioué]/',$verb_word_without_initial_slash{0}) )
// 						$reflexiveMeaning .= 's\'';
// 					else
// 						$reflexiveMeaning .= 'se';
// 				else
// 					$reflexiveMeaning .= $to;
// 				$reflexiveMeaning .= self::removePrepositionFromEndOfVerbWord($verb_word_without_initial_slash);
// 			}
// 		}
		
// 		if ( $trans_mode ) {
// 			$transitiveMeaning .= $before_infinitive_marked_verb . $to . $verb_word_without_initial_slash;
// 		}
//     	return array($transitiveMeaning,$passiveMeaning,$reflexiveMeaning,$resultMeaning);	
//     }
    
    
    
	public static function removePrepositionFromEndOfVerbWord ($verb_word)
	{
		return preg_replace('/-[^-]+$/', '', $verb_word, 1);
	}
    
	public static function addMik ($text, $lang) {
		if ( $lang == 'en' ) {
			$intransitiveMeaning = preg_replace('/((s.t. or s.o.)|(s.o. or s.t.)|s.o.|s.t.)/','${1} (-mik)',$text);
		} else {
			$intransitiveMeaning = preg_replace('/((qqch. ou qqn)|(qqn ou qqch.)|qqn|qqch.)/','${1} (-mik)',$text);
		}
		return $intransitiveMeaning;
	}
	
	
// 	public static function replaceFrenchPrepositionsAndReflexivePronouns ($transitiveMeaning)
// 	{
// 		$transitiveMeaning = str_replace('se-', 'se ', $transitiveMeaning);
// 		$transitiveMeaning = str_replace('-à', ' à', $transitiveMeaning);
// 		$transitiveMeaning = str_replace('-contre', ' contre ', $transitiveMeaning);
// 		$transitiveMeaning = str_replace('-de ', ' de ', $transitiveMeaning);
// 		$transitiveMeaning = str_replace('-dans ', ' dans ', $transitiveMeaning);
// 		$transitiveMeaning = str_replace('-sur ', ' sur ', $transitiveMeaning);
// 		return $transitiveMeaning;
// 	}
	
	
	public static function replaceWithPersPron($text, $lang) {
		if ($lang=='en') {
			return self::replaceWithPersPron_EN($text);
		} else {
			return self::replaceWithPersPron_FR($text);
		}
	}
	
	public static function replaceWithPersPron_EN ($text) {
		$transitiveMeaning = str_replace('s.t. or s.o.', 'it or him/her', $text);
		$transitiveMeaning = str_replace('s.o. or s.t.', 'him/her or it', $transitiveMeaning);
		$transitiveMeaning = str_replace('s.o.', 'him/her', $transitiveMeaning);
		$transitiveMeaning = str_replace('s.t.', 'it', $transitiveMeaning);
		$transitiveMeaning = str_replace('his', 'his/her/its', $transitiveMeaning);
		return $transitiveMeaning;	
	}
	
	public static function replaceWithPersPron_FR ($text) {
		$logger = Logger::getLogger('VerbRoot.replaceWithPersPron_FR');
		$logger->debug("\$text= '$text'");
		$transitiveMeaning = '';
		$modele_faire = '(faire)';
		$modele_verbe = '([a-zàâèéëêïîôùüûç\-]+)';
		$modele_pronom = '((?:qqch\. ou qqn)|(?:qqn ou qqch\.)|qqn|qqch\.)';
		$modele_preposition = '((?:à propos de)|pour|à|avec|contre|vers|sur)';
		$offset = 0;
		$no_more_match = false;
		while (  ! $no_more_match )
		{
			if ( preg_match("/${modele_verbe}\s+${modele_preposition}\s+${modele_pronom}/", $text, $matches, PREG_OFFSET_CAPTURE, $offset) ) 
			{
				$transitiveMeaning .= substr($text, $offset, $matches[0][1]-$offset);
				$cas = 'Vb Prep Pr :';
				$logger->debug("$cas \$matches[0][0]= '".$matches[0][0]."'");
				$logger->debug("$cas \$matches[1][0]= '".$matches[1][0]."'");
				$logger->debug("$cas \$matches[2][0]= '".$matches[2][0]."'");
				$logger->debug("$cas \$matches[3][0]= '".$matches[3][0]."'");
				$match = $matches[0][0];
				$match = str_replace('qqch. ou qqn', 'ça ou lui/elle', $match);
				$match = str_replace('qqn ou qqch.', 'lui/elle ou ça', $match);
				$match = str_replace('qqn', 'lui/elle', $match);
				$match = str_replace('qqch.', 'ça', $match);			
				$transitiveMeaning .= $match;
				$offset = $matches[0][1] + strlen($matches[0][0]);
			}
			elseif ( preg_match("/${modele_faire}\s+${modele_verbe}\s+${modele_pronom}/", $text, $matches, PREG_OFFSET_CAPTURE, $offset) )
			{
				$transitiveMeaning .= substr($text, $offset, $matches[0][1]-$offset);
				$cas = 'faire ... :';
				$logger->debug("$cas \$matches[0][0]= '".$matches[0][0]."'");
				$logger->debug("$cas \$matches[1][0]= '".$matches[1][0]."'");
				$logger->debug("$cas \$matches[2][0]= '".$matches[2][0]."'");
				$logger->debug("$cas \$matches[3][0]= '".$matches[3][0]."'");
				$match = 'le/la' . ' ' . $matches[1][0] . ' ' . $matches[2][0];
				$transitiveMeaning .= $match;
				$offset = $matches[0][1] + strlen($matches[0][0]);
			}
			elseif ( preg_match("/${modele_verbe}\s+${modele_pronom}/", $text, $matches, PREG_OFFSET_CAPTURE, $offset) )
			{
				$transitiveMeaning .= substr($text, $offset, $matches[0][1]-$offset);
				$cas = 'Vb Pr :';
				$logger->debug("$cas \$matches[0][0]= '".$matches[0][0]."'");
				$logger->debug("$cas \$matches[1][0]= '".$matches[1][0]."'");
				$logger->debug("$cas \$matches[2][0]= '".$matches[2][0]."'");
				$match = $matches[0];
				$match = ( preg_match('/^[aeiouyé]/', $matches[1][0]) ? 'l\'' : 'le/la ') . $matches[1][0];
				$transitiveMeaning .= $match;
				$offset = $matches[0][1] + strlen($matches[0][0]);
			}
			else {
				$no_more_match = true;
			}
		}
		$transitiveMeaning .= substr($text,$offset);
		return $transitiveMeaning;		
	}
	

	
	public static function replaceObjectWithPersPron($text, $dir_indir, $lang) {
		if ($lang=='en') {
			return self::replaceObjectWithPersPron_EN($text, $dir_indir);
		} else {
			return self::replaceObjectWithPersPron_FR($text, $dir_indir);
		}
	}
	
	public static function replaceObjectWithPersPron_EN($object, $dir_indir) {
		$logger = Logger::getLogger('VerbRoot.replaceObjectWithPersPron_EN');
		$logger->debug("\$object= '$object'");
		$new_object = '';
		if ( $object == 's.t. or s.o.' )
				$new_object = 'it or him/her';
		elseif ( $object == 's.o. or s.t.' )
				$new_object = 'him/her or it';
		elseif ( $object == 's.t.' )
				$new_object = 'it';
		elseif ( $object == 's.o.' )
				$new_object = 'him/her';
		else 
				$new_object = 'it';
		return $new_object;
	}
	
	public static function replaceObjectWithPersPron_FR ($object, $dir_indir) {
		$logger = Logger::getLogger('VerbRoot.replaceObjectWithPersPron_FR');
		$logger->debug("\$object= '$object'");
		$new_object = '';
		if ( $object == 'qqch. ou qqn' )
			if ($dir_indir == 'direct')
				$new_object = 'le/la';
			elseif ($dir_indir == 'indirect in front')
				$new_object = 'lui';
			else
				$new_object = 'ça ou lui/elle';
		elseif ( $object == 'qqn ou qqch.' )
			if ($dir_indir == 'direct')
				$new_object = 'le/la';
			elseif ($dir_indir == 'indirect in front')
				$new_object = 'lui';
			else
				$new_object = 'lui/elle ou ça';
		elseif ( $object == 'qqch.' )
			if ($dir_indir == 'direct')
				$new_object = 'le/la';
			elseif ($dir_indir == 'indirect in front')
				$new_object = 'lui';
			else
				$new_object = 'ça';
		elseif ( $object == 'qqn' )
			if ($dir_indir == 'direct')
				$new_object = 'le/la';
			elseif ($dir_indir == 'indirect in front')
				$new_object = 'lui';
			else
				$new_object = 'lui/elle';
		else
			$new_object = 'ça';
		return $new_object;
	}
	
	
	
	public static function removeObject($text, $lang) {
		if ($lang=='en') {
			return self::removeObject_EN($text);
		} else {
			return self::removeObject_FR($text);
		}
	}
	
	public static function removeObject_EN ($text) {
		$passiveMeaning = str_replace('s.t. or s.o.', '', $text);
		$passiveMeaning = str_replace('s.o. or s.t.', '', $passiveMeaning);
		$passiveMeaning = str_replace('s.o.', '', $passiveMeaning);
		$passiveMeaning = str_replace('s.t.', '', $passiveMeaning);
		$passiveMeaning = preg_replace('/\s+/',' ',$passiveMeaning);
		return $passiveMeaning;	
	}
	
	public static function removeObject_FR ($text) {
		$passiveMeaning = str_replace('qqch. ou qqn', '', $text);
		$passiveMeaning = str_replace('qqn ou qqch.', '', $passiveMeaning);
		$passiveMeaning = str_replace('qqn', '', $passiveMeaning);
		$passiveMeaning = str_replace('qqch.', '', $passiveMeaning);
		$passiveMeaning = preg_replace('/\s+/',' ',$passiveMeaning);
		return $passiveMeaning;	
	}
	
	public static function replaceObject($text, $lang) {
		if ($lang=='en') {
			return self::replaceObject_EN($text);
		} else {
			return self::replaceObject_FR($text);
		}
	}
	
	public static function replaceObject_EN ($text) {
		$reflexiveObject = str_replace('s.t. or s.o.', 'itself or oneself', $text);
		$reflexiveObject = str_replace('s.o. or s.t.', 'oneself or itself', $reflexiveObject);
		$reflexiveObject = str_replace('s.o.', 'oneself', $reflexiveObject);
		$reflexiveObject = str_replace('s.t.', 'itself', $reflexiveObject);
		$reflexiveObject = preg_replace('/\s+/',' ',$reflexiveObject);
		return $reflexiveObject;	
	}
	
	public static function replaceObject_FR ($text) {
		$reflexiveObject = str_replace('qqch. ou qqn', 'lui-même ou soi-même', $text);
		$reflexiveObject = str_replace('qqn ou qqch.', 'soi-même ou lui-même', $reflexiveObject);
		$reflexiveObject = str_replace('qqn', 'soi-même', $reflexiveObject);
		$reflexiveObject = str_replace('qqch.', 'lui-même', $reflexiveObject);
		$reflexiveObject = preg_replace('/\s+/',' ',$reflexiveObject);
		return $reflexiveObject;	
	}
	
	
	 // --------------------------------------------------------------------------------------------------------
	 
	 public static function _makeVerbMeanings ($string)
	 {
	 	$logger = Logger::getLogger('VerbRoot._makeVerbMeanings');
	 	$verb_senses = array();
	 	$analyses = self::_parseVerbSens($string);
	 	$logger->debug("\$analyses= ".print_r($analyses,true));
	 	$no = 0;
	 	
	 	if ( $analyses )
	 	foreach ($analyses as $an_analysis) {
	 		if ( is_array($an_analysis) ) {
	 			if ( array_key_exists('mode', $an_analysis) ) {
	 				// mode with sense or propositions
	 				$mode = $an_analysis['mode'];
	 				if ( array_key_exists('sens', $an_analysis) ) {
	 					// mode vith sense
	 					foreach ( $an_analysis['sens'] as $a_sense ) {
	 						$senseOfMorpheme = new MeaningOfMorpheme($a_sense['propositions'],$a_sense['nb'],$mode);
							array_push($verb_senses, $senseOfMorpheme);	
							$logger->debug("A : ".print_r($verb_senses,true));
	 					}
	 				}
	 				else {
	 					// mode with propositions (only one sense)
	 					$senseOfMorpheme = new MeaningOfMorpheme($an_analysis['propositions'],null,$mode);
						array_push($verb_senses, $senseOfMorpheme);	
						$logger->debug("B : ".print_r($verb_senses,true));
	 				}
	 			}
	 			else {
	 				// sense : nb, propositions
	 				$senseOfMorpheme = new MeaningOfMorpheme($an_analysis['propositions'],$an_analysis['nb']);
					array_push($verb_senses, $senseOfMorpheme);
					$logger->debug("C : ".print_r($verb_senses,true));
	 			}
	 		}
	 	 	else {
	 			// $analyses is a set of propositions ; $an_analysis is a proposition
	 			$senseOfMorpheme = new MeaningOfMorpheme($analyses);
				array_push($verb_senses, $senseOfMorpheme);
				$logger->debug("D : ".print_r($verb_senses,true));
				break;
	 		}
	 	}
	 	
	 	return $verb_senses;
	 }

	 public function parseMeaning ($string)
	 {
	 	$analyses = self::_parseVerbSens($string);
	 	
	 }
	 
	 public static function _parseVerbSens ($string)
	 {
	 	$parser = new Analyzer_MeaningOfMorpheme_Statement();
	 	$analyses = $parser->parse($string);
	 	return $analyses;
	 }
	 
	 
    //------------------------------------------------ Expérimental -----------------------------------------------------
    
   public static function prepareSenseForTransformation ($sense, $lang) {
   	if ($lang=='en') {
   		return self::prepareSenseForTransformation_EN($sense);
   	}
   	else {
   		return self::prepareSenseForTransformation_FR($sense);
   	}
   }
	
   public static function prepareSenseForTransformation_EN ($sense) {
   	$logger = Logger::getLogger('VerbRoot.prepareSenseForTransformation_EN');
   	$verbPattern = '((to )?(\/)([a-zA-Z\-]+))';
   	$compPattern = '((s.o. or s.t.)|(s.t. or s.o.)|s.o.|s.t.)';
   	$prepPattern = '(for|to|with|against|at)';
   	$tagged_sense = preg_replace("/($verbPattern(, $verbPattern)*)/","v($1)",$sense);
   	$logger->debug("\$tagged_sense= '$tagged_sense'");
   	$tagged_sense = preg_replace("/$compPattern/","c($1)",$tagged_sense);
   	$tagged_sense = preg_replace("/\s(${prepPattern}( or ${prepPattern})*)\s/"," p($1) ",$tagged_sense);
   	return $tagged_sense;
   }
   
   public static function prepareSenseForTransformation_FR ($sense) {
   	$logger = Logger::getLogger('VerbRoot.prepareSenseForTransformation_FR');
   	$verbPattern = '((\/)([a-zA-Zàâèéëêïîôùüûç\-]+))';
   	$compPattern = '((qqn ou qqch.)|(qqch. ou qqn)|qqn|qqch.)';
   	$prepPattern = '((à propos de)|pour|à|avec|contre|vers|sur)';
   	$logger->debug("\$sense before checking for - = '$sense'");
   	$sense = preg_replace("/(\/[a-zA-Zàâèéëêïîôùüûç]+)-([^ ,]+)/","$1 $2",$sense);
   	$logger->debug("\$sense after checking for - = '$sense'");
   	$tagged_sense = preg_replace("/($verbPattern(, $verbPattern)*)/","v($1)",$sense);
   	$tagged_sense = preg_replace("/$compPattern/","c($1)",$tagged_sense);
   	$tagged_sense = preg_replace("/\s(${prepPattern}( ou ${prepPattern})*)\s/"," p($1) ",$tagged_sense);
   	return $tagged_sense;
   }
   
   public static function toTransitive ($tagged_sense, $lang) {
   	if ($lang=='en') {
   		return self::toTransitive_EN($tagged_sense);
   	}
   	else {
   		return self::toTransitive_FR($tagged_sense);
   	}
   }
    
   public static function toTransitive_EN ($tagged_sense) {
   	$vP = 'v\((.+?)\)';
   	$cP = 'c\((.+?)\)';
   	$pP = 'p\((.+?)\)';
   	$tr = preg_replace("/${vP}/e","str_replace('/','','\\1')",$tagged_sense);
   	$tr = preg_replace("/${cP}/e","self::replaceWithPersPron_EN('\\1')",$tr);
   	$tr = preg_replace("/${pP}/","\\1",$tr);
   	return $tr;
   }
   
   public static function toTransitive_FR ($tagged_sense) {
   	$logger = Logger::getLogger('VerbRoot.toTransitive_FR');
   	$logger->debug("\$tagged_sense= '$tagged_sense'");
   	$vP = 'v\(([^\)]+)\)';
   	$cP = 'c\(([^\)]+)\)';
   	$pP = 'p\(([^\)]+)\)';
   	$offset = 0;
   	$tr = $tagged_sense;
   	$search = true;
   	while ( $search ) {
   		$search = false;
   		if ( preg_match("/${vP}\s+${cP}/",$tr,$match,PREG_OFFSET_CAPTURE) ) {
   			$logger->debug("vP cP: \$match=\n".print_r($match,TRUE));
   			$tr = substr($tr,0,$match[0][1]) 
   				. self::replaceObjectWithPersPron_FR($match[2][0],'direct') . ' '
   				. str_replace('/','',$match[1][0])
   				. substr($tr,$match[0][1]+strlen($match[0][0]));
   			$search = true;
   		}
   		if ( preg_match("/${vP}(.+?)${pP}\s+${cP}/",$tr,$match,PREG_OFFSET_CAPTURE) ) {
   			$logger->debug("vP pP cP: \$match=\n".print_r($match,TRUE));
   			if ( $match[3][0]==='à' ) {
   				$logger->debug("\$match[2][0]= '".$match[2][0]."'");
   				$tr = substr($tr,0,$match[0][1])
   				. self::replaceObjectWithPersPron_FR($match[4][0],'indirect in front')
   				. ' '
   				. str_replace('/','',$match[1][0])
   				. ($match[2][0] !== ' ' ? $match[2][0] : '')
   				. substr($tr,$match[0][1]+strlen($match[0][0]));
   			}
   			else
   				$tr = substr($tr,0,$match[0][1])
   					. str_replace('/','',$match[1][0])
   					. $match[2][0] . $match[3][0] . ' '
   					. self::replaceObjectWithPersPron_FR($match[4][0],'indirect')
   					. substr($tr,$match[0][1]+strlen($match[0][0]));
   			$search = true;
   		}
   	}
   	$tr = preg_replace("/${pP}/","\\1",$tr);
   	return $tr;
   }
   
   
    
   public static function toIntransitive ($tagged_sense) {
   	$vP = 'v\((.+?)\)';
   	$cP = 'c\((.+?)\)';
   	$pP = 'p\((.+?)\)';
   	$tr = preg_replace("/${vP}/e","str_replace('/','','\\1')",$tagged_sense);
   	$tr = preg_replace("/${cP}/","\\1",$tr);
   	$tr = preg_replace("/${pP}/","\\1",$tr);
   	return $tr;
   }
   
   
   
   public static function toPassive ($tagged_sense, $lang) {
   	$logger = Logger::getLogger('VerbRoot.toPassive');
   	$logger->debug("\$tagged_sense= '$tagged_sense'");
   	$vP = 'v\(([^\)]+)\)';
   	$cP = 'c\(([^\)]+)\)';
   	$pP = 'p\(([^\)]+)\)';
   	$offset = 0;
   	$tr = $tagged_sense;
   	$search = true;
   	while ( $search ) {
   		$search = false;
   		if ( preg_match("/${vP}\s+${cP}/",$tr,$match,PREG_OFFSET_CAPTURE) ) {
   			$logger->debug("vP cP: \$match=\n".print_r($match,TRUE));
   			$total_match = $match[0][0];
   			$pos_of_total_match = $match[0][1];
   			$v = $match[1][0];
   			$tr = substr($tr,0,$pos_of_total_match)
   			. ($lang==='en' ? 'to be' :  'être') . ' '
   			. self::makeVerbFormPassive(self::removeTO($v),$lang)
   			. substr($tr,$pos_of_total_match+strlen($total_match));
   			$search = true;
   		}
   		if ( preg_match("/${vP}(.+?)${pP}\s+${cP}/",$tr,$match,PREG_OFFSET_CAPTURE) ) {
   			$logger->debug("vP pP cP: \$match=\n".print_r($match,TRUE));
   			$total_match = $match[0][0];
   			$pos_of_total_match = $match[0][1];
   			$v = $match[1][0];
   			$p = $match[3][0];
   			$text_between_v_and_p = $match[2][0];
   			$text_between_v_and_p = preg_replace('/\s+$/','',$text_between_v_and_p);
   			if ( $p==='à' || $p==='to') {
   				$logger->debug("\$text_between_v_and_p= '".$text_between_v_and_p."'");
   				$tr = substr($tr,0,$pos_of_total_match)
   				. ($lang==='en' ? 'to be' : 'se faire')
   				. ' '
   				. ($lang==='en' ? 
   						self::makeVerbFormPassive(self::removeTO($v),$lang) 
   						: str_replace('/','',$v))
   				. ($text_between_v_and_p !== ' ' ? $text_between_v_and_p : '')
   				. substr($tr,$pos_of_total_match+strlen($total_match));
   			}
   			else {
   				$logger->debug("\$text_between_v_and_p= '".$text_between_v_and_p."'");
   				$tr = substr($tr,0,$pos_of_total_match)
   				. ($lang==='en' ? 'to be' : 'se faire')
   				. ' '
   				. ($lang==='en' ? 
   						self::makeVerbFormPassive(self::removeTO($v),$lang) 
   						: str_replace('/','',$v))
   				. ' '
   				. self::prepToAdverb($p,$lang)
   				. ($text_between_v_and_p !== ' ' ? $text_between_v_and_p : '')
   				. substr($tr,$pos_of_total_match+strlen($total_match));
   			}
   			$search = true;
   		}
   }
   $tr = preg_replace("/${pP}/","\\1",$tr);
   return $tr;
   }
   
   
   public static function toReflexive ($tagged_sense, $lang) {
      	if ($lang=='en') {
   			return self::toReflexive_EN($tagged_sense);
   		}
   		else {
   			return self::toReflexive_FR($tagged_sense);
   		}
   }
   
   public static function toReflexive_EN ($tagged_sense) {
   		$logger = Logger::getLogger('VerbRoot.toReflexive');
   		$logger->debug("\$tagged_sense= '$tagged_sense'");
  	 	$vP = 'v\(([^\)]+)\)';
   		$cP = 'c\(([^\)]+)\)';
   		$pP = 'p\(([^\)]+)\)';
   		$tr = preg_replace("/${vP}/e","str_replace('/','','\\1')",$tagged_sense);
   		$logger->debug("\$tr= '$tr'");
   		$tr = preg_replace("/${cP}/e","self::replaceObject('\\1','en')",$tr);
   		$logger->debug("\$tr= '$tr'");
   		$tr = preg_replace("/${pP}/","\\1",$tr);
   		return $tr;
	}


   public static function toReflexive_FR ($tagged_sense) {
   		$logger = Logger::getLogger('VerbRoot.toReflexive');
   		$logger->debug("\$tagged_sense= '$tagged_sense'");
  	 	$vP = 'v\(([^\)]+)\)';
   		$cP = 'c\(([^\)]+)\)';
   		$pP = 'p\(([^\)]+)\)';
   		$tr = '';
// 			$reflexiveMeaning = str_replace('-à', '', $reflexiveMeaning);
//    		$reflexive_part = self::replaceObjectInReflexivePart($reflexive_part,$lang);
//    		$reflexiveMeaning .= $reflexive_part;
//    		$reflexiveMeaning = trim($reflexiveMeaning);
//    		if ( $reflexiveMeaning=='' )
//    			$reflexiveMeaning = null;
//    		else
//    			$reflexiveMeaning = self::replaceObject($reflexiveMeaning,$lang);
   		return $tr;
   }
   
   
   public static function toResultive ($tagged_sense, $lang) {
		$tr = '';
// 				$resultMeaning .= $passive_part;
// 				$resultMeaning = str_replace('to /make', 'to', $resultMeaning);
// 				$resultMeaning = str_replace('/make', 'to', $resultMeaning);
// 				$resultMeaning = str_replace('/', '', $resultMeaning);
// 				$resultMeaning = trim($resultMeaning);
// 				if ( $resultMeaning=='' ) 
// 					$resultMeaning = null;
// 				else
// 					$resultMeaning = self::removeObject($resultMeaning,$lang);
   		return $tr;
   }
    
   static $prepAdv = array(
   		'sur' => 'dessus'
   		);
    public static function prepToAdverb($preposition, $lang) {
    	if ( array_key_exists($preposition, self::$prepAdv) )
    		return $prepAdv[$preposition];
    	elseif ($lang==='en')
    		return $preposition;
    	else
    		return '';
    }
    
    public static function removeTO ($tagged_sense_verb) {
   		$logger = Logger::getLogger('VerbRoot.removeTO');
   		$logger->debug("\$tagged_sense_verb= '$tagged_sense_verb'");
    	$ret = str_replace('to ','',$tagged_sense_verb);
   		$logger->debug("\$ret= '$ret'");
    	return $ret;
    }

}

?>