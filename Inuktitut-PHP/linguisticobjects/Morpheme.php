<?php
/*
 * To change the template for this generated file go to
 * Window - Preferences  - PHP - Code Style - Code Templates - Code - Simple php file
 *
 * Auteur/Author: Benoît Farley
 * Date: 2011-02-25
 *
 */
require_once "lib/log4php/Logger.php";

abstract class Morpheme
{
	public $morpheme = NULL;
	protected $tableName = '';
	protected $dbName = '';
	protected $morphemeID = NULL;
 	protected $frenchMeaning = NULL;
    protected $engMeaning = NULL;
    
    public function getMeaning ($lang)
    {
    	if ( $lang=='en')
    		return $this->engMeaning;
    	else 
    		return $this->frenchMeaning;
    }
    
    public function getFrenchMeaning() {
		return $this->frenchMeaning;
	}
 	
 	public function getEnglishMeaning() {
		return $this->engMeaning;
	}
	
    public function setFrenchMeaning($string) {
		$this->frenchMeaning = $string;
	}
 	
 	public function setEnglishMeaning($string) {
		$this->engMeaning = $string;
	}
	
	public function morpheme()
	{
		return $this->morpheme;
	}
	
	public function type() {
		return $this->type;
	}
	
	public function listMeanings($lang)
	{
		if ($lang=='en')
			return $this->parseMeaning($this->engMeaning);
		else
			return $this->parseMeaning($this->frenchMeaning);
	}
	 	
	public function id()
	{
		return $this->morphemeID->toString();
	}
	
	public function signature()
	{
		return $this->morphemeID->signature();
	}
	
	public function isVerbRoot()
	{
		if ( $this->type() == 'v' )
			return true;
		else
			return false;
	}
	
	static public function generates_multiple_objects() {
		return false;
	}
}

?>