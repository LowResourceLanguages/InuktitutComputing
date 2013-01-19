<?php
/*
 * Created on 2011-11-23
 *
 */
echo "testJar.php\n";

$sep = preg_match ('/win/i', PHP_OS) ? ';' : ':';
$inuktitut_php = getenv('PHP_INUKTITUT');
$path = '.' . $sep . $inuktitut_php;
set_include_path($path);
echo "\$path= '$path'\n";

require_once "$inuktitut_php/lib/log4php/Logger.php";

class  testJar {
	
	private $val = null;
//	private $logger = null;
	
	public function __construct () {
		$this->logger = Logger::getLogger('testJar');
	}
	
	public function analyze($word) {
		$analysis = '';
		$this->logger->debug("Mot à analyser : '".$word."'");
		$analysis = `java -jar ../linguisticobjects/Uqailaut.jar $word`;
		$analyses = explode('\n',$analysis);
		return $analysis;
	}
	
}

Logger::configure("./log4php.properties");
$x = new testJar();
$y = $x->analyze('saqqitaujuq');
echo "analyse : \n";
print_r($y);
?>
