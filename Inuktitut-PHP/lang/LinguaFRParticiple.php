<?php
/*
 * To change the template for this generated file go to
 * Window - Preferences  - PHP - Code Style - Code Templates - Code - Simple php file
 *
 * Auteur/Author: BenoÃ®t Farley
 * Date: 2011-04-27
 *
 */

require_once 'lib/Common.php';

class LinguaFRParticiple
{

	static $irreg = array(
'atteindre' => 'atteint',
'battre' => 'battu',
'comprendre' => 'compris',
'couvrir' => 'couvert',
'cuire' => 'cuit',
'défaire' => 'défait',
'descendre' => 'descendu',
'élire' => 'élu',
'éteindre' => 'éteint',
'faire' => 'fait',
'fendre' => 'fendu',
'frire' => 'frit',
'instruire' => 'instruit',
'mettre' => 'mis',
'mordre' => 'mordu',
'moudre' => 'moulu',
'offrir' => 'offert',
'peindre' => 'peint',
'pendre' => 'pendu',
'prendre' => 'pris',
'rejoindre' => 'rejoint',
'rendre' => 'rendu',
'répandre' => 'répandu',
'reprendre' => 'repris',
'saisir' => 'saisi',
'secourir' => 'secouru',
'tenir' => 'tenu',
'tondre' => 'tondu',
'vouloir' => 'voulu',
'coudre' => 'cousu',
'surprendre' => 'surpris'
);


public static function participle ($inf)
	{
		if ( array_key_exists($inf, self::$irreg) )
		{
			return self::$irreg[$inf];
		}
		
		unset($part);
		list($stem,$ending) = self::stem_ending($inf);
		if ( $ending=='er' )
		{
			$part = $stem . 'é';
		}
		elseif ( $ending=='ir' )
		{
			$part = $stem . 'i';
		}
		
		return $part;
	} 
	
	static function stem_ending ($inf)
	{
		$stem = NULL;
		$ending = NULL;
		if ( str_endswith($inf, 'er') )
		{
			$stem = substr($inf,0,strlen($inf)-2);
			$ending = 'er';
		}
		elseif ( str_endswith($inf,'ir') )
		{
			$stem = substr($inf,0,strlen($inf)-2);
			$ending = 'ir';
		}
		
		return array($stem,$ending);
	}
	
	
	
}

?>