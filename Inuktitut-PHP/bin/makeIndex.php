<?php
/*
 * include_path set in php.ini
 */
 
echo "\n";

require_once 'index/Indexer.php';
require_once "lib/log4php/Logger.php";
Logger::configure('../log4php.properties.xml');
$logger = Logger::getLogger('makeIndex');

$lang = 'e'; 
$csv_filename = null;
$force_indexation = false;
$directory = null;

for ($i=1; $i<count($argv); $i++) {
	if ( $argv[$i]=='-h' ) {
		usage();
		exit;
	} elseif ( $argv[$i]=='-f' ) {
		$force_indexation = true;
	} elseif ( $argv[$i]=='-d' ) {
		$i++;
		$directory = $argv[$i];
	} elseif ( startsWith($argv[$i],'-') ) {
		usage("$argv[$i]: Wrong option");
		exit;
	} else {
		$csv_filename = $argv[$i];
	}
}

$indexer = new Indexer();
if ( $force_indexation ) 
	$indexer->force_indexation();
if ( $directory != null ) {
	if ( is_dir($directory) ) {
		$indexer->set_data_root($directory);
	} else {
		die("ERROR: $directory is not a directory.\n");
	}
}

$dir_mes = "in data directory " . $indexer->get_data_root();

if ( $csv_filename==null ) {
	$mes = "Indexation of all linguistic data files";
}
else {
	$mes = "Indexation of the linguistic data file $csv_filename";
	$indexer->force_indexation();
}
$logger->info("$mes $dir_mes\n");

if ( $csv_filename != null ) {
	$indexer->index_one_file($csv_filename);
	$logger->info("Nb. of morphemes indexed in this pass: ".$indexer->get_nb_indexed_entries());
}
else {
	$indexer->index_all_files();
	$logger->info("Total nb. of data files:              ".count($indexer->list_data_files_in_data_directory()));
	$logger->info("Nb. files indexed in this pass: ".$indexer->get_nb_indexed_files());
	$logger->info("Total nb. of entries:              ".count(file($indexer->get_main_index_pathname())));
	$logger->info("Nb. entries indexed in this pass: ".$indexer->get_nb_indexed_entries());
}



function usage( $message=null ) {
	if ( $message != null ) echo "\n*** $message ***\n";
	echo "\nUsage: php makeindex.php [-options] [<csv filename>]";
	echo "\n\nwhere options include:";
	echo "\n\n";
	echo "-h\t\tprint this information\n";
	echo "-f\t\tforce indexation\n";
	$indexer = new Indexer();
	echo "-d <dir>\tindex data files in directory <dir> (defaults to " 
		. $indexer->get_data_root() . ")\n";
	echo "\n";
	echo "If <csv filename> is present, index only that file; otherwise index all csv files newer than main index.\n\n";
}

?>
