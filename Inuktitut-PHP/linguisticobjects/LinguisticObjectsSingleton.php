<?php

class LinguisticObjectsSingleton {
	
	/*
	 * This class is a work in progress. It is not part yet of the Inuktitut Project.
	 */
	static private $instances = array();
	static private $default_data_directory = null;
	public function instance( $options ) {
		$key = self::_make_unique_key($options);
		if ( array_key_exists($key, self::$instances) ) {
			$the_instance = self::$instances[$key];
		} else {
			$the_instance = new LinguisticObjects( $options );
			self::$instances[$key] = $the_instance;
		}
		return $the_instance;
	}
	
	public function __construct ($options) {
		if ( array_key_exists('directory',$options) ) {
			$this->data_directory = $options['directory'];
		} else {
			$this->data_directory = _self::$default_data_directory;
		}
	}
	
	
	
	public function _make_unique_key($options) {
		$default_unique_key = 'default';
		$unique_key = '';
		foreach ( array_keys($options) as $a_key ) {
			$unique_key .= $a_key . $options[$a_key];
		}
		if ( $unique_key == '' ) 
			$unique_key = $default_unique_key;
		return $unique_key;	
	}
	
	
}

?>