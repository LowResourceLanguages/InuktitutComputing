<?php

require_once "lib/log4php/Logger.php";

class ID_Morpheme
{
	private $morpheme;
	private $signature = null;
	static private $delimiter = '/';

	public function __construct ($morpheme, $others=null)
	{
		$logger = Logger::getLogger('ID_Morpheme.__construct');
		if ( strpos($morpheme,self::$delimiter) )
		{
			$parts = explode(self::$delimiter,$morpheme);
			$morpheme = $parts[0];
			$others = array($parts[1]);
		}		$logger->debug("\$others= '$others'");
		$this->morpheme = $morpheme;
		$this->signature = implode('-',$others);
		$logger->debug("signature= '".$this->signature."'");
	}

	public function toString()
	{
		return $this->morpheme . self::$delimiter . $this->signature;
	}

	public function signature()
	{
		return $this->signature;
	}

	public function morpheme()
	{
		return $this->morpheme;
	}
	
}

?>
