<?php
/*
 * Created on 2010-09-08
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 require_once 'lib/StringUtils.php';
 
 function make_html_a_reference ($key) {
 	return '<a href="#' . $key . '">' . $key . '</a>';
 }
 
 function displayAlphabeticalMenu ($alph_menu) {
 	$menu_array = array_map('make_html_a_reference',$alph_menu);
 	echo '<span class="alph_menu">[' . implode('&nbsp;&nbsp;&nbsp;',$menu_array) . "]</span>\n"; 
 }
 
 function splitArrayByInitialChar ($array) {
 	$arrays = array();
 	$elements = array_values($array);
 	sort($elements);
	$firstChars = array_values(array_unique(array_map('firstChar',$elements)));
	$firstCharsIndex = 0;
 	$elementsWithFirstChar = array();
 	foreach ( $elements as $element ) {
 		if ( firstChar($element)==$firstChars[$firstCharsIndex] )
 			array_push($elementsWithFirstChar,$element);
 		else {
 			$arrays[$firstChars[$firstCharsIndex]] = $elementsWithFirstChar;
 			$firstCharsIndex++;
 			$elementsWithFirstChar = array($element);
 		}
 	}
 	$arrays[$firstChars[$firstCharsIndex]] = $elementsWithFirstChar;
 	return $arrays;
 }
 
 
 function composeHTMLColoredText ( $txt, $colour ) {
 	return "<span style=\"color:" + $colour + "\">" + utf8_encode($txt) + "</span>";
 }
 
 function str_endswith ( $txt, $ending ) {
 	if ( strlen($txt) >= strlen($ending) )
 		if ( substr($txt, strlen($txt)-strlen($ending)) == $ending )
 			return true;
 	return false;
 }
 
 function fix_directory_separators($pathname) {
 	$fixed_pathname = preg_replace('/[\/\\]/',DIRECTORY_SEPARATOR,$pathname);
 	return $fixed_pathname;
 }
 
 function files_in_directory ( $directory_pathname, $pattern=null ) {
 	$list_of_files = array();
 	if ( $handle = opendir($directory_pathname) ) {
 		while (false !== ($entry = readdir($handle))) {
 			if ( $pattern && preg_match($pattern,$entry) ) {
 				array_push($list_of_files,$entry);
 			}
 		}
 		closedir($handle);
 	}
 	return $list_of_files;
 }
 
 function read_file_into_array ($filepath) {
 	$handle = fopen($filepath,'r');
 	$array_of_lines = array();
 	while ( ($line=fgets($handle)) != null ) {
 		array_push($array_of_lines, $line);
 	}
 	fclose($handle);
 	return $array_of_lines;
 }
 
?>
