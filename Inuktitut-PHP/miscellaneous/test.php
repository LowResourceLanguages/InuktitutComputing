<?php
/*
 * Created on 2010-11-09
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
echo "test.php\n";

$sep = preg_match ('/win/i', PHP_OS) ? ';' : ':';
$inuktitut_php = getenv('PHP_INUKTITUT');
$path = '.' . $sep . $inuktitut_php;
set_include_path($path);
echo "\$path= '$path'\n";

echo "\$inuktitut_php= '$inuktitut_php'\n";
require_once "$inuktitut_php/lib/log4php/Logger.php";
echo "got Logger.php\n";
echo "configured logger\n";

class  xyz {
	
	private $val = null;
//	private $logger = null;
	
	public function __construct ($x) {
		$this->val = $x;
		$this->logger = Logger::getLogger('xyz');
	}
	
	public function getVal() {
		$this->logger->debug("Valeur de l'objet: '".$this->val."'");
		return $this->val;
	}
	
}

Logger::configure("./log4php.properties");
//Logger::configure();
$x = new xyz('abc');
$y = $x->getVal();
echo "retour: $y\n";
?>
