<?php
/*
 * To change the template for this generated file go to
 * Window - Preferences  - PHP - Code Style - Code Templates - Code - Simple php file
 *
 * Auteur/Author: Benoît Farley
 * Date: 2011-02-23
 *
 */

class MeaningOfMorpheme {
	
	private $nb;
	private $mode;
	private $propositions;
	
	public function __construct($propositions=null, $nb=null, $mode=null) {
		if ( $propositions==null )
			$propositions = array();
		if ( $nb==null )
			$nb = 1;
		$this->propositions = $propositions;
		$this->nb = $nb;
		$this->mode = $mode;
	}
	
	public function propositions($valeur=null)
	{
		if ( $valeur==null )
			return $this->propositions;
		else
			$this->propositions = $valeur;
	}
	
	public function nb()
	{
		return $this->nb;
	}
		
	public function mode()
	{
		return $this->mode;
	}
	
	public function toString()
	{
		return implode('; ',$this->propositions);
	}
}
?>