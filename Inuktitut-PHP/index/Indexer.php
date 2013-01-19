<?php

require_once "lib/parseCSV.php";
require_once 'lib/Common.php';
require_once "linguisticobjects/LinguisticObjects.php";
require_once "lib/log4php/Logger.php";
Logger::configure('../log4php.properties.xml');

class Indexer {

	static $dataRootDir = '/linguisticdata';
	static $all_index_filename = 'main_index';
	static $dataFiles = array(
			"RootsSpalding.csv",
			"RootsSchneider.csv",
			// 	"RacinesAutresSources.csv",
			"UndecomposableCompositeWords.csv",
			"CommonCompositeWords.csv",
			"LoanWords.csv",
			//	"MotsReliesAuxRacines.csv",
			"Placenames.csv",
			"Pronouns.csv",
			"Suffixes.csv",
			"Sources.csv",
			"Demonstratives.csv",
			"NounEndings.csv",
			"VerbEndings.csv",
			"VerbEndings_Participle.csv",
			"DemonstrativeEndings.csv",
			"FrenchPassives.csv",
			"EnglishPassives.csv"
	);
	private $_data_root;
	private $_force_indexation = false;
	private $_temp_main_index_filename;
	private $_nb_indexed_entries = 0;
	private $_nb_indexed_entries_in_individual_file = 0;
	private $_nb_indexed_files = 0;
	private $_no_info = false;
	
	function __construct() {
		$this->_data_root = realpath(getenv('PHP_INUKTITUT').self::$dataRootDir);
	}

	public function getDataRootDirectory() {
		return $this->_data_root;
	}
	
	public function force_indexation() {
		$this->_force_indexation = true;
	}
	
	public function set_data_root( $directory ) {
		$this->_data_root = $directory;
	}
	
	public function get_data_root() {
		return $this->_data_root;
	}
	
	public function get_nb_indexed_entries() {
		return $this->_nb_indexed_entries;
	}
	
	public function get_nb_indexed_files() {
		return $this->_nb_indexed_files;
	}
	
	public function get_main_index_pathname() {
		return $this->_data_root . DIRECTORY_SEPARATOR . self::$all_index_filename;
	}
	
	public function get_data_pathname($csv_filename) {
		return $this->_data_root . DIRECTORY_SEPARATOR . $csv_filename;
	}
	
	public function set_no_info( $boolean ) {
		$this->_no_info = $boolean;
	}
	
	public function index_one_file ($csv_filename) {
		$this->_nb_indexed_entries = 0;
		$this->_nb_indexed_files = 0;
		$name_of_index_file = $this->_index_one_indivudual_file($csv_filename);
		$this->_merge_all_individual_index_files();
	}
	
	public function index_all_files () {
		$logger = Logger::getLogger('Indexer.index_all_files');
		$this->_nb_indexed_entries = 0;
		$this->_nb_indexed_files = 0;
		$this->_index_all_individual_files();
		$this->_merge_all_individual_index_files();
	}
	
	public function _merge_all_individual_index_files() {
		$logger = Logger::getLogger('Indexer._merge_all_individual_index_files');
		$pattern = '/^.+\.csv.index$/';
		$list_of_index_files = files_in_directory( $this->_data_root, $pattern );
		$this->_create_main_index_tempfile();
		foreach ($list_of_index_files as $an_index_filename) {
			$this->_append_contents_to_main_index_file($an_index_filename);
		}
		rename($this->_temp_main_index_filename, $this->get_main_index_pathname());
	}
	
	public function _index_all_individual_files() {
		$list_of_csv_datafiles = $this->list_data_files_in_data_directory();
		$logger = Logger::getLogger('Indexer._index_all_individual_files');
		$logger->debug("\$list_of_csv_datafiles= ".print_r($list_of_csv_datafiles,true));
		foreach ($list_of_csv_datafiles as $a_csv_filename) {
			$index_filename_of_one_csv = $this->_index_one_indivudual_file($a_csv_filename);
		}
	}
	
	public function _index_one_indivudual_file( $csv_filename ) {
		$dbg_logger = Logger::getLogger('Indexer._index_one_indivudual_file');
		$dbg_logger->debug("invoked with \$csv_filename= '$csv_filename'");
		$inf_logger = Logger::getLogger('Indexer.info._index_one_indivudual_file');
		if ( ! $this->_no_info ) $inf_logger->info("Indexing $csv_filename ...");
		$newly_indexed_file_name = null;
		$full_data_filename = realpath($this->_data_root . DIRECTORY_SEPARATOR . $csv_filename);
		if ( $full_data_filename ) {
			$index_filename = $full_data_filename.'.index';
			if ( $this->_force_indexation || $this->datafile_more_recent_than_main_index($full_data_filename) ) {
				$newly_indexed_file_name =$this->_do_index_this_file($csv_filename);
				if ( ! $this->_no_info ) $inf_logger->info("ok\t\t(".$this->_nb_indexed_entries_in_individual_file." entries)");
			} else {
				if ( ! $this->_no_info ) $inf_logger->info("skipped");
			}
		}
		return $newly_indexed_file_name;
	}
	
	function _do_index_this_file ( $csv_filename ) {
		$logger = Logger::getLogger('Indexer._do_index_this_file');
		$full_data_filename = realpath($this->_data_root . DIRECTORY_SEPARATOR . $csv_filename);
// 		$logger->debug("\$full_data_filename= '$full_data_filename");
		$this->_nb_indexed_files += 1;
		$fih = fopen("$full_data_filename",'r');
		$index_file_name = "$full_data_filename".'.index';
		$foh = fopen($index_file_name,'w');
		preg_match("/(^|\\\\|\\/)([^\\\\\\/]+)\.csv/",$full_data_filename,$matches);
		$tableName = $matches[2];
// 		$logger->debug("nom de la table = '$tableName'");
		$ligne_champs = fgets($fih);
// 		$logger->debug("\$ligne_champs = '$ligne_champs'");
		$position = ftell($fih);
		$this->_nb_indexed_entries_in_individual_file = 0;
		while ( ($ligne_valeurs=fgets($fih)) != NULL ) {
			$this->_nb_indexed_entries += 1;
			$this->_nb_indexed_entries_in_individual_file += 1;
			$champsValeurs = parseCSV($ligne_champs,$ligne_valeurs);
			$champsValeurs['dbName'] = 'Inuktitut';
			$champsValeurs['tableName'] = $tableName;
// 			$object = LinguisticObjects::makeLinguisticObject($champsValeurs);
			$object_ids = LinguisticObjects::make_object_ids($champsValeurs);
			// 			$logger->debug("\$object_ids= ".print_r($object_ids,TRUE));
			foreach ($object_ids as $an_id) {
				$entry = $an_id->toString() . ':' . $position . "\n";
				fputs($foh, $entry);
			}
			$position = ftell($fih);
		}
		fclose($fih);
		fclose($foh);
		return $index_file_name;
	}
	
	public function _create_main_index_tempfile() {
		$temp_main_index_full_pathname = $this->get_main_index_pathname() . 'tmp';
		$this->_temp_main_index_filename = $temp_main_index_full_pathname;
		$handle = fopen($temp_main_index_full_pathname,'w');
		fclose($handle);
	}
	
	public function _append_contents_to_main_index_file($index_filename) {
		preg_match('/^(.+)\.index$/',$index_filename,$matches);
		$csv_filename = $matches[1];
		$tmp_main_index_handle = fopen($this->_temp_main_index_filename,'a');
		$full_index_pathname = $this->_data_root . DIRECTORY_SEPARATOR . $index_filename;
		$individual_index_handle = fopen($full_index_pathname,'r');
		while ( ($line= fgets($individual_index_handle)) != null ) {
			$trimmed_line = trim($line);
			$augmented_line = $trimmed_line . ":" . $csv_filename . "\n";
			fputs($tmp_main_index_handle, $augmented_line);
		}
		fclose($individual_index_handle);
		fclose($tmp_main_index_handle);
	}
	

	function index_is_older_than_data ($indexFileName, $dataFile) {
		if ( !file_exists($indexFileName) || filemtime($indexFileName) < filemtime($dataFile) )
			return TRUE;
		else
			return FALSE;
	}
	
	function datafile_more_recent_than_main_index ( $dataFile ) {
		$main_index_filename = $this->get_main_index_pathname();
		if ( !file_exists($main_index_filename) || filemtime($main_index_filename) < filemtime($dataFile) )
			return TRUE;
		else
			return FALSE;
	}
	
	function list_data_files_in_data_directory () {
		$pattern = '/^.+\.csv$/';
		$list_of_csv_datafiles = files_in_directory( $this->_data_root, $pattern );
		return $list_of_csv_datafiles;
	}
		
}
?>
