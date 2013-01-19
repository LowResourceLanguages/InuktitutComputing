<?php
/*
 * Auteur/Author: Benoît Farley
 * Date: 2011-09-06
 *
 * include_path set in php.ini
 */

require_once "lib/log4php/Logger.php";
Logger::configure('../log4php.properties.xml');

if ( !extension_loaded('mbstring') ) { 
	echo "Loading mbstring\n";
	dl('php_mbstring.dll');
}
mb_internal_encoding('UTF-8');

require_once 'tools/Transcoder/Transcoder.php';

$froms_and_tos = array(
		'ra' => 1, 'u' => 1, 'n' => 1, 'p' => 1, 'a' => 1, 'u&' => 1, 'uu' => 1, 'u%' => 1
		);

if ( $argc == 2 && $argv[1]=='-h' ) {
	usage();
	exit;
}

if ( $argc >= 4 && $argc <= 5 ) {
	if ( ! array_key_exists($argv[1], $froms_and_tos) ) {
		usage("$argv[1]: Wrong first argument");
		exit;
	} else {
		$from = $argv[1];
	}
	if ( ! array_key_exists($argv[2], $froms_and_tos) ) {
		usage("$argv[2]: Wrong second argument");
		exit;
	} else {
		$to = $argv[2];
	}
	if ( $argc == 5 ) {
		if ( $argv[3] != 0 && $argv[3] != 1 ) {
			usage("$argv[3]: Wrong third argument");
			exit;
		} else {
			$toaipaitai_flag = $argv[3];
			$text_to_transcode = $argv[4];
		}
	}
	else {
		$toaipaitai_flag = '0';
		$text_to_transcode = $argv[3];
	}
} else {
	usage("Wrong number of arguments");
	exit;
}

$transcoder = new Transcoder();
$transcoded_text = $transcoder->transcode($from, $to, $toaipaitai_flag, $text_to_transcode);
echo $transcoded_text;
exit;



function usage ( $message=null ) {
	if ( $message != null ) echo "\n*** $message ***\n";
	echo "\nUsage: php transcode.php <from> <to> [<toUnicodeAipaitai_flag>] <text_to_transcode>";
	echo "\n       php transcode.php -h";
	echo "\n";
	echo "\n-h\tprint this information\n";
	echo "\n<from>,<to>\tone of:\n";
	echo "\tra\troman alphabet\n";
	echo "\tu\tunicode\n";
	echo "\tn\tnunacom\n";
	echo "\tp\tprosyl\n";
	echo "\ta\taipainunavik\n";
	echo "\tu&\t&#xxxx; (unicode html entity)\n";
	echo "\tuu\t\\uxxxx (unicode in string constant)\n";
	echo "\tu%\t%xx (unicode in utf-8 url encoding)\n";
	echo "\n<toUnicodeAipaitai_flag> --- of interest only for <to> = unicode; may be omitted (defaults to 0); if present, either of:\n";
	echo "\t0\tnot aipaitai\n";
	echo "\t1\taipaitai\n";
	echo "\n\n";
}
?>