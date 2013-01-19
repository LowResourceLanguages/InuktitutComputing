<?php
/*
 * Created on 2010-09-03
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once "lib/log4php/Logger.php";

 $delimiter = ';';
 
 function parseCSV ($fields_line, $data_line) {
	global $delimiter;
	$logger = Logger::getLogger('parceCSV');
 	$data_line = trim($data_line);
 	$fields_line = trim($fields_line);
 	$fields = parse_CSV_line($fields_line);
 	$values = parse_CSV_line($data_line);
 	$logger->debug("data_line= '$data_line'");
 	$logger->debug('fields/values : '.count($fields).'/'.count($values));
 	$fieldValues = array();
 	for ($i=0; $i<count($fields); $i++) {
 		$fieldValues[$fields[$i]] = $values[$i];
 	}
 	return $fieldValues;
 }
 
 function parse_CSV_line ($line) {
 	global $delimiter;
 	$values = str_getcsv($line,$delimiter);
 	return $values;
 }

?>
