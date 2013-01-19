<?php

require_once "lib/log4php/Logger.php";
require_once "linguisticdata/DataModel_CSV.php";
require_once "lib/parseCSV.php";

/*
 * This class is a work in progress. It is not yet part of the Inuktitut Project.
 */

class InuktitutLinguisticModel {
	
	private static $instances = array();
	private static $the_instance;
	private $data_directory;
	private $data_model;
	private $objects = array();
	
	
	public function instance( $data_directory='null' ) {
		if ( ! array_key_exists($data_directory, self::$instances) ) {
			$instance = new InuktitutLinguisticModel( $data_directory );
			self::$instances[$data_directory] = $instance;
		}
		return self::$instances[$data_directory];
	}
	
	public function __construct ( $data_directory='null' ) {
		if ( $data_directory == 'null' ) {
			$this->data_directory =  __DIR__ . '/../linguisticdata';
		} else {
			$this->data_directory = $data_directory;
		}
		$this->data_model = new DataModel_CSV();
		
		if ( is_dir($this->data_directory) )
			$this->_init();
	}
	
	function _init () {
		$logger = Logger::getLogger('InuktitutLinguisticModel._init');
		$main_index_pathname = $this->data_model->index_pathname;
		$fd = fopen($main_index_pathname,'r');
		$line_of_field_names = fgets($fd);
		while ( ($line_of_field_values=fgets($fd)) ) {
			$attr_values = parseCSV($line_of_field_names,$line_of_field_values);
			$attr_values['dbName'] = 'Inuktitut';
			$attr_values['tableName'] = $tableName;
			$object = $this->_make_linguistic_object($attr_values);
			if ( $obj != NULL ) {
				$this->objects[$obj->id()] = $obj;
			}
		}
		fclose($fd);
	}
	
	public function _make_linguistic_object ($fieldsAndValues) {
		$logger = Logger::getLogger('LinguisticObjects.makeLinguisticObject');
		$logger->debug("\$fieldsAndValues= ".print_r($fieldsAndValues,TRUE));
		$type = $fieldsAndValues['type'];
		$logger->debug("type= $type");
		$obj = NULL;
		if ( $type=='n' || $type=='a' || $type=='e' || $type=='c' ) {
			$obj = new NounRoot($fieldsAndValues);
			array_push(self::$rootIDs,$obj->id());
		}
		elseif ( $type=='v' ) {
			$obj = new VerbRoot($fieldsAndValues);
			array_push(self::$rootIDs,$obj->id());
		}
		elseif ( $type=='s' ) {
			$obj = new Source($fieldsAndValues);
			array_push(self::$sourceIDs, $obj->id());
		}
		elseif ( $type=='sn' || $type=='sv' || $type=='q' ) {
			$obj = new Suffix($fieldsAndValues);
			array_push(self::$affixIDs, $obj->id());
		}
		elseif ( $type=='tn' ) {
			$obj = new NounEnding($fieldsAndValues);
			array_push(self::$affixIDs, $obj->id());
		}
		elseif ( $type=='tv' ) {
			$obj = new VerbEnding($fieldsAndValues);
			array_push(self::$affixIDs, $obj->id());
		}
		elseif ( $type=='vw' ) {
			$obj = new VerbWord($fieldsAndValues);
			self::$verbWords[$object->getVerb()] = $obj;
		}
		elseif ( $type=='pr' ) {
			$obj = new Pronoun($fieldsAndValues);
			array_push(self::$rootIDs,$obj->id());
		}
		elseif ( $type=='pd' || $type=='ad' ) {
			$obj = new Demonstrative($fieldsAndValues);
			array_push(self::$rootIDs,$obj->id());
			$roots = explode(' ',$obj->get_root());
			foreach ($roots as $a_root) {
				$newFieldsAndValues = $fieldsAndValues;
				$newFieldsAndValues['morpheme'] = $a_root;
				$newFieldsAndValues['root'] = $a_root;
				$new_obj = new Demonstrative($newFieldsAndValues);
				array_push(self::$rootIDs,$new_obj->id());
			}
		}
		else {
			$logger->debug("type '$type' not supported yet");
		}
			
		return $obj;
	}
	
}
?>