<?php
/*
 * Created on 2010-09-09
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
// TODO-BF-Inuktitut: Translate Demonstrative.java, DemonstrativeEnding.java to PHP

 require_once "lib/parseCSV.php";
 require_once "linguisticobjects/VerbWord.php";
 require_once "linguisticobjects/NounRoot.php";
 require_once "linguisticobjects/VerbRoot.php";
 require_once "linguisticobjects/Suffix.php";
 require_once "linguisticobjects/NounEnding.php";
 require_once "linguisticobjects/VerbEnding.php";
 require_once "linguisticobjects/Source.php";
 require_once "linguisticobjects/VerbWord.php";
 require_once "linguisticobjects/CSV_DB_Reader.php";
 require_once "linguisticobjects/Pronoun.php";
 require_once "linguisticobjects/Demonstrative.php";
 require_once "linguisticobjects/DemonstrativeEnding.php";
 require_once "lib/log4php/Logger.php";
 require_once "linguisticdata/DataModel_CSV.php";

 abstract class LinguisticObjects {
 	
 	public static $objects = array();
 	public static $rootIDs = array();
 	public static $sourceIDs = array();
 	public static $affixIDs = array();
 	public static $verbWords = array();
 	
 	static $files_containing_roots = array(
 			'RootsSpalding',
 			'RootsSchneider'
 			);
 	static $files_containing_suffixes = array('Suffixes');
 	
 	
 	private static $class_names = array(
 			'n' => 'NounRoot',
 			'a' => 'NounRoot',
 	 		'e' => 'NounRoot',
 	 		'c' => 'NounRoot',
 			'v' => 'VerbRoot',
 	 		's' => 'Source',
 	 		'sn' => 'Suffix',
 	 		'sv' => 'Suffix',
 	 		'q' => 'Suffix',
 	 		'tn' => 'NounEnding',
 	 		'tv' => 'VerbEnding',
 	 		'vw' => 'VerbWord',
 	 		'pr' => 'Pronoun',
 			'rpr' => 'Pronoun',
 	 		'pd' => 'Demonstrative',
 	 		'ad' => 'Demonstrative',
 			'tad' => 'DemonstrativeEnding',
 			'tpd' =>  'DemonstrativeEnding'
 	);
 	
 	public static function make_object_ids($field_values) {
 		$ids = array();
 		$type = $field_values['type'];
 		$class_of_object = self::$class_names[$type];
 		$object_id = $class_of_object::make_id($field_values);
 		array_push($ids, $object_id);
 	 	if ( $class_of_object::generates_multiple_objects() ) {
 			$additional_object_ids = $class_of_object::make_multiple_ids($field_values);
 			$ids = array_merge($ids, $additional_object_ids);
 		}
 		return $ids;
 	}
 	
 	public static function listOfSuffixes() {
		$ref_to_list_of_files = &self::$files_containing_suffixes; 		
		return self::_listOfMorphemes($ref_to_list_of_files);
 	}
 	public static function listOfRoots() {
		$ref_to_list_of_files = &self::$files_containing_roots; 		
		return self::_listOfMorphemes($ref_to_list_of_files);
 	}
 	
 	static function _listOfMorphemes($ref_to_list_of_files) {
 		$logger = Logger::getLogger('LinguisticObjects.listOfMorphemes');
 		$logger->debug("ref fichiers= '$ref_to_list_of_files'");
		$indexer = new Indexer();
		$data_directory = $indexer->get_data_root();
 		$reader = new CSV_DB_Reader($data_directory);
 		$list_morphemes = array();
 		foreach ($ref_to_list_of_files as $a_file) {
 			$a_csv_file = $a_file . '.csv';
 			$list = $reader->listOfElementsInCSVFile($a_csv_file);
 			$list_morphemes = array_merge($list_morphemes, $list);
 		}
 		return $list_morphemes;
 	}
 	
	public static function init () {
		$logger = Logger::getLogger('LinguisticObjects.init');
		$data_model = new DataModel_CSV();
		// This iterator will return a hash table of attribute-value pairs, one pair for each entry of data.
		$iterator = $data_model->get_attributes_values_iterator();
		$avs = array();
		while ($iterator->next()) {
			$attributes_values = $iterator->current();
			$object = self::makeLinguisticObject($attributes_values);
		}
	}
 	
	// TODO-BF-Inuktitut: This function is no longer necessary. To be deleted.
	// This is done by init - object ids are in the main index; init reads every entry
	// in the main index and creates an object.
//  	public static function _initialize_verbWords () {
//  		$logger = Logger::getLogger('LinguisticObjects._initialize_verbWords');
//  		$indexer = new Indexer();
// 		$dataFiles = array(
// 			"Passifs.csv",
// 			"Passives.csv"
// 			);
// 		foreach ($dataFiles as $dataFile) {
//  			preg_match('/(.+)\.csv/',$dataFile,$matches);
//  			$tableName = $matches[1];
// 			$fullDataFile = $indexer->get_data_pathname($dataFile);
// 			$fd = fopen($fullDataFile,'r');
// 			$ligne_champs = fgets($fd);
// 			while ( ($ligne_donnees=fgets($fd)) != null ) {
// 				$fieldAndValues = parseCSV($ligne_champs,$ligne_donnees);
// 				$object = new VerbWord($fieldAndValues);
// 				self::$verbWords[$object->getVerb()] = $object;
// 			}
// 			fclose($fd);
// 		}
//  	}
 
 	public static function getObjects () {
 		return self::$objects;
 	}
 	
 	public static function getObject ( $id ) {
 		$object = null;
 		$logger = Logger::getLogger('LinguisticObjects.getObject');
 		$logger->debug("\$id= '$id'");
 		if ( ! array_key_exists($id,self::$objects) ) {
 			$logger->debug("makeObject avec $id");
 			$object = self::makeObject($id);
 		}
 		if ( array_key_exists($id,self::$objects) ) {
 			$logger->debug("maintenant, la clé existe dans objets");
 			$object = self::$objects[$id];
 			$logger->debug($object);
 		}
 		return $object;
 	}
 	
 	public static function getSource($sourceID) {
 		return self::getObject($sourceID);
 	} 
 	
// 	public static function getVerbWord($verbe) {
// 		if ( isset(self::$verbWords[$verbe]) )
// 			return self::$verbWords[$verbe];
// 		else
// 			return NULL;
// 	}
 	
 	public static function getVerbWord($verbe) {
 		$id = new ID_Morpheme($verbe, array(VerbWord::$type));
 		return self::getObject($id->toString());
 	}
 	
 	public static function makeObject ($id) {
 		$fieldValues = self::findLinguisticDataInIndex($id);
 		if ( $fieldValues !== null ) {
 			return self::makeLinguisticObject($fieldValues);
 		}
 		return null;
 	}
 	
  	public static function findLinguisticDataInIndex ( $id ) {
 		$logger = Logger::getLogger("LinguisticObjects.findLinguisticDataInIndex");
 		$attr_values = null;
		$indexer = new Indexer();
 		$index_file = $indexer->get_main_index_pathname();
 		$fh = fopen($index_file, 'r');
 		while ( ($ligne=fgets($fh)) != null ) {
 			list($an_id, $position, $csv_file_name) = explode(':',trim($ligne));
 			if ( $an_id == $id ) {
 				$file_name = $indexer->get_data_pathname($csv_file_name);
 				$logger->debug("\$file_name= $file_name; \$position= $position");
 				$fih = fopen($file_name, 'r');
 				fseek($fih,$position);
 				$data = fgets($fih);
 				$logger->debug("\$data= $data");
 				fseek($fih,0);
 				$fields_desc = fgets($fih);
 				$logger->debug("\$fields= $fields_desc");
 				fclose($fih);
 				$fieldsValues = parseCSV($fields_desc,$data);
 				$attr_values = $fieldsValues;
 				break;
 			}
 		}
 		fclose($fh);
 		return $attr_values;
 	}
 	
 	// This function is called only from the unit tests on objects.
  	public static function makeLinguisticObject_from_FieldsAndValues ($fieldsStr, $valuesStr) {
  		$logger = Logger::getLogger('LinguisticObjects.makeLinguisticObject_from_FieldsAndValues');
		$fieldAndValues = parseCSV($fieldsStr,$valuesStr);
		$logger->debug("\$fieldsStr= '$fieldsStr'");
		$logger->debug("\$valuesStr= '$valuesStr'");

		return self::makeLinguisticObject($fieldAndValues);
 	}
 
	
 
  	public static function makeLinguisticObject ($fieldAndValues) {
 		$logger = Logger::getLogger('LinguisticObjects.makeLinguisticObject');  
 		$logger->debug("\$fieldAndValues= ".print_r($fieldAndValues,TRUE));		
 		$type = $fieldAndValues['type'];
 		$logger->debug("type= $type");
 		$obj = NULL;
 		if ( $type=='n' || $type=='a' || $type=='e' || $type=='c' ) {
 			$obj = new NounRoot($fieldAndValues);
 			array_push(self::$rootIDs,$obj->id());
 		}
 		elseif ( $type=='v' ) {
  			$obj = new VerbRoot($fieldAndValues);
 			array_push(self::$rootIDs,$obj->id());
 		}
 		elseif ( $type=='s' ) {
  			$obj = new Source($fieldAndValues);
  			array_push(self::$sourceIDs, $obj->id());
 		} 
 		elseif ( $type=='sn' || $type=='sv' || $type=='q' ) {
 			$obj = new Suffix($fieldAndValues);
 			array_push(self::$affixIDs, $obj->id());
 		}
 		elseif ( $type=='tn' ) {
 			$obj = new NounEnding($fieldAndValues);
 			array_push(self::$affixIDs, $obj->id());
 		}
 		elseif ( $type=='tv' ) {
 			$obj = new VerbEnding($fieldAndValues);
 			array_push(self::$affixIDs, $obj->id());
 		}
 		elseif ( $type=='vw' ) {
 			$obj = new VerbWord($fieldAndValues);
			self::$verbWords[$object->getVerb()] = $obj;
 		}
  	  	 elseif ( $type=='pr' ) {
 			$obj = new Pronoun($fieldAndValues);
 			array_push(self::$rootIDs,$obj->id());
  	  	 }
  	  	 elseif ( $type=='pd' || $type=='ad' ) {
 			$obj = new Demonstrative($fieldAndValues);
 			array_push(self::$rootIDs,$obj->id());
 			$roots = explode(' ',$obj->get_root());
 			foreach ($roots as $a_root) {
 				$newFieldAndValues = $fieldAndValues;
 				$newFieldAndValues['morpheme'] = $a_root;
 				$newFieldAndValues['root'] = $a_root;
 				$new_obj = new Demonstrative($newFieldAndValues);
 				array_push(self::$rootIDs,$new_obj->id());
 			}
  	  	 }
 		else {
			$logger->debug("type '$type' not supported yet");
		}
  	 	
  	 	if ( $obj != NULL ) {
			self::$objects[$obj->id()] = $obj;
 		}
		
 		return $obj;
 	}
 	
 	
 
 
 }
 
 
 
?>
