<?php
/*
 * Created on 2010-05-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'script/Syllabics.php';
 require_once 'fonts/Nunacom.php';
 require_once 'fonts/Prosyl.php';
 require_once 'fonts/Aipainunavik.php';
 require_once 'lib/utf8.php';
 
class Transcoder {
	
	function __construct() {
	}
	
/*
 * aipaitai
 *   '1' or 1 : all a+i replaced with aipai
 *   '0' or 0 : all aipai replaced with a+i
 *   '-' : no replacement whatsoever
 */
 function transcode ($fromFormat, $toFormat, $aipaitai, $text) {
	$logger = Logger::getLogger('transcode');
 	$transcodedText = "";
 	$fromFormat = strtolower($fromFormat);
 	$toFormat = strtolower($toFormat);
 	if ($fromFormat=='ra') $fromFormat = 'roman alphabet';
 	if ($fromFormat=='u') $fromFormat = 'unicode';
 	if ($fromFormat=='n') $fromFormat = 'nunacom';
 	if ($fromFormat=='p') $fromFormat = 'prosyl';
 	if ($fromFormat=='a') $fromFormat = 'aipainunavik';
 	if ($fromFormat=='uu') $fromFormat = 'unicode \\uxxxx';
 	if ($fromFormat=='u&') $fromFormat = 'unicode &#xxxx;';
 	if ($fromFormat=='u%') $fromFormat = 'unicode url encoding %xx';
 	if ($toFormat=='ra') $toFormat = 'roman alphabet';
 	if ($toFormat=='u') $toFormat = 'unicode';
 	if ($toFormat=='n') $toFormat = 'nunacom';
 	if ($toFormat=='p') $toFormat = 'prosyl';
 	if ($toFormat=='a') $toFormat = 'aipainunavik';
 	if ($toFormat=='uu') $toFormat = 'unicode \\uxxxx';
 	if ($toFormat=='u&') $toFormat = 'unicode &#xxxx;';
 	if ($toFormat=='u%') $toFormat = 'unicode url encoding %xx';
 	$logger->debug("\$fromFormat= '$fromFormat'");
 	$logger->debug("\$toFormat= '$toFormat'");
 	switch ($fromFormat) {
 		case 'unicode' : 
 			$transcodedText = $this->transcodeFromUnicode($toFormat, $aipaitai, $text); break;
 		case 'roman alphabet' : 
 			$transcodedText = $this->transcodeFromLatinAlphabet($toFormat, $aipaitai, $text); break;
 		case 'nunacom' : 
 		case 'prosyl' :
 		case 'aipainunavik' : 
 			$transcodedText = $this->transcodeFromLegacy($fromFormat, $toFormat, $aipaitai, $text); break;
 		case 'unicode &#xxxx;' :
 			$text = str_replace('&amp;','&',$text);
			$transcodedText = $this->transcodeFromHTMLEntity($toFormat, $aipaitai, $text); break;
 		case 'unicode \\uxxxx' :
			$transcodedText = $this->transcodeFromSlashUUnicodeString($toFormat, $aipaitai, $text); break;
 		case 'unicode url encoding %xx' :
			$transcodedText = $this->transcodeFromURLEncoding($toFormat, $aipaitai, $text); break;
 	}
 	return $transcodedText;
 }
 
 function  transcodeFromUnicode ($toFormat, $aipaitai, $text) {
	$logger = Logger::getLogger('transcodeFromUnicode');
	$logger->debug("\$toFormat= '$toFormat'");
	$logger->debug("\$text= '$text'");
	$transcodedText = "";
 	switch ($toFormat) {
 		case 'roman alphabet' :
 			$transcodedText = Syllabics::unicodeToLatinAlphabet($text); break;
 		case 'unicode' :
 			switch ($aipaitai) {
 				case '1' :
 					$transcodedText = Syllabics::iciUnicodeToLatinAlphabet($text); break;
 				case '0' :
 					$transcodedText = Syllabics::iciUnicodeToNoAipaitai($text); break;
 				default :
 					$transcodedText = $text; break;
 			}
 			break;
 		case 'unicode &#xxxx;' :
 			switch ($aipaitai) {
 				case '1' :
 					$transcodedText = Syllabics::iciUnicodeToLatinAlphabet($text); break;
 				case '0' :
 					$transcodedText = Syllabics::iciUnicodeToNoAipaitai($text); break;
 				default :
 					$transcodedText = $text; break;
 			}
			$transcodedText = $this->unicodeToHTMLEntity($transcodedText); 
			break;
 		case 'unicode \uxxxx' :
 			switch ($aipaitai) {
 				case '1' :
 					$transcodedText = Syllabics::iciUnicodeToLatinAlphabet($text); break;
 				case '0' :
 					$transcodedText = Syllabics::iciUnicodeToNoAipaitai($text); break;
 				default :
 					$transcodedText = $text; break;
 			}
			$transcodedText = $this->unicodeToSlashUUnicodeString($transcodedText); 
			break;
 		case 'unicode url encoding %xx' :
 			switch ($aipaitai) {
 				case '1' :
 					$transcodedText = Syllabics::iciUnicodeToLatinAlphabet($text); break;
 				case '0' :
 					$transcodedText = Syllabics::iciUnicodeToNoAipaitai($text); break;
 				default :
 					$transcodedText = $text; break;
 			}
			$transcodedText = $this->unicodeToURLEncoding($transcodedText); 
			break;
 		case 'nunacom':
 			$transcodedText = Nunacom::unicodeToLegacy($text); break;
 		case 'prosyl':
 			$transcodedText = Prosyl::unicodeToLegacy($text); break;
 		case 'aipainunavik':
 			$transcodedText = Aipainunavik::unicodeToLegacy($text); break;
 	}
 	return $transcodedText;
 }
 
 function transcodeFromLatinAlphabet ($toFormat, $aipaitai, $text) {
 	if ($toFormat === 'aipainunavik') $aipaitai = '1';
 	$transcodedText = Syllabics::latinAlphabetToUnicode($text,$aipaitai);
 	$transcodedText = $this->transcodeFromUnicode($toFormat,$aipaitai,$transcodedText);
 	return $transcodedText;
 }
 
 function transcodeFromLegacy ($fromFormat, $toFormat, $aipaitai, $text) {
 	switch ($fromFormat) {
 		case 'nunacom' : 
 			$transcodedText = Nunacom::legacyToUnicode($text,FALSE); 
 			$transcodedText = $this->transcodeFromUnicode($toFormat,$aipaitai,$transcodedText);
 			break;
 		case 'prosyl' : 
 			$transcodedText = Prosyl::legacyToUnicode($text,FALSE); 
 			$transcodedText = $this->transcodeFromUnicode($toFormat,$aipaitai,$transcodedText);
 			break;
 		case 'aipainunavik' : 
 			$transcodedText = Aipainunavik::legacyToUnicode($text,'-'); 
  			$transcodedText = $this->transcodeFromUnicode($toFormat,'-',$transcodedText);
 			break;
 	}
 	return $transcodedText;
 }
 
 function transcodeFromHTMLEntity ($toFormat, $aipaitai, $text) {
 	if ($toFormat === 'aipainunavik') $aipaitai = '-';
 	$transcodedText = $this->transcodeFromUnicode($toFormat, '-', entite_html_a_unicode($text));
 	return $transcodedText; 	
 }
 
 function transcodeFromSlashUUnicodeString ($toFormat, $aipaitai, $text) {
 	if ($toFormat === 'aipainunavik') $aipaitai = '-';
 	$transcodedText = $this->transcodeFromUnicode($toFormat, '-', slashUUnicodeStringToUnicode($text));
 	return $transcodedText; 	
 }
 
 function transcodeFromURLEncoding ($toFormat, $aipaitai, $text) {
 	if ($toFormat === 'aipainunavik') $aipaitai = '-';
 	$transcodedText = $this->transcodeFromUnicode($toFormat, '-', urlEncodingToUnicode($text));
 	return $transcodedText; 	
 }
 
 //-----------------------------------------------------------------------------------------------------
 
 function unicodeToHTMLEntity ($text) {
 	$cnt = mb_strlen($text);
 	$i = 0;
 	$res = '';
 	while ($i < $cnt) {
 		$c = mb_substr($text,$i++,1);
 		$nc = utf8::utf8_to_numeric($c);
 		$res .= '&amp;#'.strval($nc).';';
 	}
 	return $res;
 }
 
 function entite_html_a_unicode ($text) {
 	$transcodedText = '';
 	preg_match_all("/\&\#(\d+)\;/",$text,$entites,PREG_SET_ORDER);
 	foreach ($entites as $entite) {
 		$val = $entite[1];
 		$utf = utf8::numeric_to_utf8($val);
 		$transcodedText .= $utf;
 	}
 	return $transcodedText;
 }

 function unicodeToSlashUUnicodeString ($text) {
 	$cnt = mb_strlen($text);
 	$i = 0;
 	$res = '';
 	while ($i < $cnt) {
 		$c = mb_substr($text,$i++,1);
 		$nc = utf8::utf8_to_numeric($c);
 		$res .= sprintf("\\u%04x",$nc);
 	}
 	return $res;
 }
 
 function slashUUnicodeStringToUnicode ($text) {
 	$transcodedText = '';
 	preg_match_all("/\\\u([0-9a-fA-F]+)/",$text,$strings,PREG_SET_ORDER);
	foreach ($strings as $string) {
 		$val = $string[1];
 		$utf = utf8::numeric_to_utf8(hexdec($val));
 		$transcodedText .= $utf;
 	}
 	return $transcodedText;
 }

 function unicodeToURLEncoding ($text) {
 	return urlencode($text);
 }
 
 function urlEncodingToUnicode ($text) {
 	return urldecode($text);
 }

 }
?>
