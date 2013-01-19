<?php
/*
 * Created on 2010-09-07
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */

require_once 'linguisticobjects/Morpheme.php';
 
 abstract class Root extends Morpheme {
 	
//    // originalMorpheme:
//    //  If a morpheme has various spellings, these are contained in
//    //  the field 'variant' of the database table.
    protected $variant = NULL;
    protected $originalMorpheme = NULL;
    protected $key = NULL;
    protected $type = NULL;
    protected $nature = NULL;
    protected $source = NULL;
    protected $dialect = NULL;
    protected $sources = NULL;
    protected $combinedMorphemes = NULL;
    protected $comb = NULL;
    protected $cf = NULL;
    protected $cfs = NULL;
    protected $known = true;

 	public function __construct($fieldsAndValues) {
		$this->morpheme = $fieldsAndValues['morpheme'];
		$this->engMeaning = $fieldsAndValues['engMean'];
		$this->frenchMeaning = $fieldsAndValues['freMean'];
		if ( isset($fieldsAndValues['dbName']) )
			$this->dbName = $fieldsAndValues['dbName'];
		if ( isset($fieldsAndValues['tableName']) )
			$this->tableName = $fieldsAndValues['tableName'];		
		$this->variant = $fieldsAndValues['variant'];
		if ( isset($fieldsAndValues['originalMorpheme']) )
			$this->originalMorpheme = $fieldsAndValues['originalMorpheme'];
		$this->key = $fieldsAndValues['key'];
		if ($this->key==NULL || $this->key=='')
			$this->key = '1';
		if ( isset($fieldsAndValues['composition']) )
        	$this->comb = $fieldsAndValues['composition'];
		if ($this->comb != NULL && $this->comb!='') {
			$this->combinedMorphemes = explode('+',$this->comb);
			if (count($this->combinedMorphemes) < 2) {
				$this->combinedMorphemes = NULL;
			} else {
				$rootId = $this->combinedMorphemes[0];
			}
		}
		$this->type = $fieldsAndValues['type'];
		$this->nature = $fieldsAndValues['nature'];
 	}
 	
 	abstract public function parseMeaning($string);
 	
 	static public function make_id ($field_values) {
 		if ( $field_values['key']=="" ) {
 			$field_values['key'] = '1';
 		}
 		$morpheme_id_object = new ID_Morpheme(
 				$field_values['morpheme'],
 				array ( $field_values['key'] . $field_values['type'] )
 		);
 		return $morpheme_id_object;
 	}
 	
 	public function getNumericalKey() {
 		return $this->key;
 	}
 	
 	public function id() {
 		return $this->morphemeID->toString();
 	}
 	
	public function signature() {
		return $this->morphemeID->signature();
	}
	
 	public function getVariant() {
		return $this->variant;
	}
		
    public function getVariants() {
        return explode(' ',$this->variant);
    }
    
    public function morpheme() {
    	return $this->morpheme;
    }
    
	public function getOriginalMorpheme() {
	    if ($this->originalMorpheme != NULL)
	        return $this->originalMorpheme;
	    else
	        return $this->morpheme;
	}

 	public function type() {
		return $this->type;
	}
	
 	public function nature() {
 		return $this->nature;
 	}
	
	public function getCombinedMorphemes() {
		return $this->combinedMorphemes;
	}
	
 	public function getRelatedMorpheme() {
		return $this->cf;
	}
	
	public function setCombiningParts( $comb ) {
	    $combinedMorphemes = explode('+',$comb);
	    if (count($combinedMorphemes) < 2) 
	        $this->combinedMorphemes = null;
	    else
	    	$this->combinedMorphemes = $combinedMorphemes;
	}
	
    public function getRelatedMorphemes() {
    	if ( $this->cf != null )
        	return explode(' ',$this->cf);
        else
        	return null;
    }
	
    public function getSources() {
        return $this->sources;
    }
	
	public function isKnown() {
    	return $this->known;
    }
    
	 public static function _parseForPattern ($string, $pattern)
	 {
		$offset = 0;
		$before_match = '';
		$parts = array();
		$part_id = '1';
		while(preg_match($pattern, $string, $matches, PREG_OFFSET_CAPTURE, $offset))
    		{
    			$before_match = substr($string,$offset,$matches[1][1]-$offset);
    			if ( $before_match != '' )
    				$parts[$part_id] = trim($before_match);
    			$part_id = $matches[2][0];
    			$offset = $matches[1][1] + strlen($matches[1][0]);
    		}
    	$parts[$part_id] = trim(substr($string,$offset));
    	return $parts;
	 }
	
 	
 }
?>
