<?php

class DataModel_CSV {
	
	public $data_directory_pathname;
	private $index_pathname;
	private $array_of_attr_values = array();
	private $default_data_directory_pathname = __DIR__;
	private $default_index_filename = 'main_index';
	
	public function __construct( $options=null ) {
		$this->default_data_directory_pathname = __DIR__;
		$logger = Logger::getLogger('DataModel_CSV.new');
		$logger->debug("\$options on entry = ".print_r($options,true));
		$this->data_directory_pathname = realpath($this->default_data_directory_pathname);
		$logger->debug("default data directory pathname= ".$this->data_directory_pathname);
		if ( $options != null && array_key_exists('directory', $options) ) {
			$this->data_directory_pathname = realpath($options['directory']);
		}
		$this->index_pathname = $this->data_directory_pathname . DIRECTORY_SEPARATOR . $this->default_index_filename;
		if ( $options != null && array_key_exists('index', $options) ) {
			$this->index_pathname = $this->data_directory_pathname . DIRECTORY_SEPARATOR . $options['index'];
		}
	}
	
	public function get_attributes_values_iterator() {
		$logger = Logger::getLogger('DataModel_CSV.get_attributes_values_iterator');
		$iterator = new Data_Iterator( $this );
		return $iterator;
	}
	
	public function _extract_tablename_from_filename($file_name) {
		$logger = Logger::getLogger('DataModel_CSV._extract_tablename_from_filename');
		$pattern = '\\' . DIRECTORY_SEPARATOR . '([^\\' . DIRECTORY_SEPARATOR . ']+)\.csv$';
		$logger->debug("\$pattern= $pattern");
		preg_match("/$pattern/",$file_name,$matches);
		return $matches[1];
	}
	
	public function _extract_filename_from_index_filename($index_filename) {
		$logger = Logger::getLogger('DataModel_CSV._extract_filename_from_index_filename');
		$pattern = '(^.+\\' . DIRECTORY_SEPARATOR . '[^\\' . DIRECTORY_SEPARATOR . ']+\.csv)\.index$';
		$logger->debug("\$pattern= $pattern");
		preg_match("/$pattern/",$index_filename,$matches);
		return $matches[1];
	}
	
	public function data_file_full_pathname ($file_name) {
		return $this->data_directory_pathname . DIRECTORY_SEPARATOR . $file_name;
	}
	
	public function get_default_data_directory_pathname() {
		return $this->default_data_directory_pathname;
	}
	
	public function get_data_directory_pathname() {
		return $this->data_directory_pathname;
	}
	
	public function get_default_index_filename() {
		return $this->default_index_filename;
	}
	
	public function get_default_index_pathname() {
		return $this->default_data_directory_pathname . DIRECTORY_SEPARATOR . $this->default_index_filename;
	}
	
	public function get_index_pathname() {
		return $this->index_pathname;
	}
	
}
	
	class Data_Iterator {
	
			private $data_model;
			private $datafiles_iterator;
			private $handle_of_open_datafile_index = null;
			private $handle_of_open_datafile = null;
			private $open_datafile_pathname = null;
			private $current_index_line;
			
		public function __construct ( $data_model ) {
			$this->data_model = $data_model;
			$this->datafiles_iterator = new Datafiles_Iterator($data_model);
			$this->rewind();
		}
	
		public function rewind() {
			if ( $this->handle_of_open_datafile_index ) {
				fclose($this->handle_of_open_datafile_index);
				$this->handle_of_open_datafile_index = null;
				fclose($this->handle_of_open_datafile);
				$this->handle_of_open_datafile = null;
				$this->open_datafile_pathname = null;
			}
			$this->datafiles_iterator->rewind();
			$this->open_datafile_pathname = $this->data_model->_extract_filename_from_index_filename($this->datafiles_iterator->current());
			$this->handle_of_open_datafile_index = fopen($this->datafiles_iterator->current(),'r');
			$this->handle_of_open_datafile = fopen($this->open_datafile_pathname,'r');
		}
		public function next() {
			$logger = Logger::getLogger('Data_Iterator.next');
			$this->current_index_line = fgets($this->handle_of_open_datafile_index);
			$logger->debug("\$this->current_index_line= '".$this->current_index_line."'");
			if ( $this->current_index_line != null ) {
				$this->current_index_line = trim($this->current_index_line);
				$logger->debug("exists - after trimming: \$this->current_index_line= '".$this->current_index_line."'");
			} else {
				$logger->debug("null");
				fclose($this->handle_of_open_datafile_index);
				fclose($this->handle_of_open_datafile);
				$this->datafiles_iterator->next();
				if ( $this->datafiles_iterator->valid() ) {
					$this->handle_of_open_datafile_index = fopen($this->datafiles_iterator->current(),'r');
					$this->current_index_line = trim(fgets($this->handle_of_open_datafile_index));
					$this->open_datafile_pathname = $this->data_model->_extract_filename_from_index_filename($this->datafiles_iterator->current());
					$this->handle_of_open_datafile = fopen($this->open_datafile_pathname,'r');
					$logger->debug("in next file, after trimming: \$this->current_index_line= '".$this->current_index_line."'");
				} else {
					$this->current_index_line = null;
					$logger->debug("no next file: \$this->current_index_line= '".$this->current_index_line."'");
				}
			}
			return $this->current_index_line;
		}
		public function current() {
			$logger = Logger::getLogger('Data_Iterator.current');
			$line_of_data =$this->current_index_line;
			$logger->debug("\$line_of_data= '$line_of_data'");
			list($id,$position) = explode(':', $line_of_data);
			$data_file_pathname = $this->open_datafile_pathname;
			$logger->debug("\$data_file_pathname= '$data_file_pathname'");
			$tableName = $this->data_model->_extract_tablename_from_filename($data_file_pathname);
			$fd = fopen($data_file_pathname, 'r');
			$line_of_field_names = fgets($fd);
			fseek($fd, $position);
			$line_of_field_values=fgets($fd);
			$attr_values = parseCSV($line_of_field_names,$line_of_field_values);
			$attr_values['dbName'] = 'Inuktitut';
			$attr_values['tableName'] = $tableName;
			fclose($fd);
			return $attr_values;
		}
	}
	
	class Datafiles_Iterator implements Iterator {
		
	    private $position = 0;
   	 	private $array = array();

    public function __construct( $data_model) {
        $this->position = 0;
        $files_in_directory = scandir($data_model->data_directory_pathname);
        foreach ($files_in_directory as $a_file) {
        	$full_pathname = $data_model->data_directory_pathname . DIRECTORY_SEPARATOR . $a_file;
        	if ( !is_dir($full_pathname) ) {
        		if ( preg_match('/\.index$/', $a_file) ) {
        			array_push($this->array, $full_pathname);
        		}
        	}
        }
    }

    function rewind() {
        $this->position = 0;
    }

    function current() {
        return $this->array[$this->position];
    }

    function key() {
        return $this->position;
    }

    function next() {
        ++$this->position;
    }

    function valid() {
        return isset($this->array[$this->position]);
    }
	}
	
?>
