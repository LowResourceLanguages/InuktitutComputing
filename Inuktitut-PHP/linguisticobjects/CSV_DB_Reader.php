<?php
/*
 * To change the template for this generated file go to
 * Window - Preferences  - PHP - Code Style - Code Templates - Code - Simple php file
 *
 * Auteur/Author: Beno�t Farley
 * Date: 2011-03-09
 *
 */
require_once 'lib/log4php/Logger.php';
require_once "lib/parseCSV.php";

class CSV_DB_Reader
{
	
	private $directory;
	
	public function __construct($directory)
	{
		$this->directory = $directory;
	}
	
	public function readCSVFile($file)
	{
		if ( $fichier==null )
			return false;
		if ( ! @file($file) )
			return false;
		$fh = fopen($file,'r');
		fclose($fh);
		return true;
	}
	
	public function readIndexForElement($id)
	{
		$logger = Logger::getLogger('CSV_DB_Reader.readIndexForElement');
		$index_data = null;
		$index_file = $this->directory . '/main_index';
		$fh = fopen($index_file,'r');
		while ( ($line=fgets($fh)) )
		{
			$parts = explode(':',rtrim($line));
			if ( $parts[0]==$id )
			{
				$index_data = array($parts[1], $parts[2]);
				break;
			}
		}
		fclose($fh);
		$logger->debug("\$index_data=\n".print_r($index_data,true));
		return $index_data;
	}
	
	public function readCSVDataForElement($id)
	{
		$fieldsAndValues = null;
		$index_data = $this->readIndexForElement($id);
		if ( $index_data != null)
		{
			list($position,$file) = $index_data;
			$data_file = $this->directory . '/' . $file;
			$fh = fopen($data_file,'r');
			$fields_line = rtrim(fgets($fh));
			fseek($fh, $position);
			$data_line = rtrim(fgets($fh));
			fclose($fh);
			$fieldsAndValues = parseCSV($fields_line,$data_line);
		}
		return $fieldsAndValues;
	}
	
	public function listOfElementsInCSVFile($file)
	{
		$list = array();
		$full_filename = $this->directory . '/' . $file . '.index';
		$fh = fopen($full_filename, 'r');
		while ( ($line=fgets($fh)) )
		{
			$parts = explode(':',$line);
			array_push($list,$parts[0]);
		}
		fclose($fh);
		return $list;
	}
	
	
}
?>