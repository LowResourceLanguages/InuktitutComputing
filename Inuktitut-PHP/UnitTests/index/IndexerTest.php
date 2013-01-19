<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'index/Indexer.php';
require_once 'lib/Common.php';
 
require_once "lib/log4php/Logger.php";
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');

class IndexerTest extends PHPUnit_Framework_TestCase {

	private $indexer;
	
	public function setUp() {
		$this->indexer = new Indexer();
		$this->indexer->set_data_root(realpath(dirname(__FILE__)));
		$this->indexer->set_no_info(true);
	}
	
	public function tearDown() {
	}
	
    public function test_This_is_how_you_index_one_file() {
    	$csv_filename = 'somedatafile.csv';
    	$index_file_name = $this->indexer->index_one_file($csv_filename);
    }
    
    public function test_This_is_how_you_index_all_files() {
    	$index_file_name = $this->indexer->index_all_files();
    }
    
    public function test_index_all_files() {
    	$this->indexer->force_indexation();
    	$this->indexer->index_all_files();
    	$index_pathname = $this->indexer->get_main_index_pathname();
    	$contents_of_index = read_file_into_array($index_pathname);
    	$expected_contents_of_index = array(
    			"qugjuq/1n:153:oneRoot.csv\n",
    			"aluk/1nn:312:oneSuffix.csv\n"
    			);
    	$this->assertEquals($expected_contents_of_index, $contents_of_index, "The main index is not correct.");
    	$this->assertEquals(2,$this->indexer->get_nb_indexed_entries(), "The total number of indexed morphemes should have been 2.");
    	$this->assertEquals(2,$this->indexer->get_nb_indexed_files(), "The total number of indexed files should have been 2.");
    }
    
    public function test__index_one_indivudual_file__Case_index_does_not_exist() {
    	$csv_filename = 'oneRoot.csv';
    	$expected_individual_index_file_name = $this->indexer->get_data_root() . DIRECTORY_SEPARATOR . $csv_filename . '.index';
        if ( file_exists($this->indexer->get_main_index_pathname()) ) {
    		unlink($this->indexer->get_main_index_pathname());
    	} 
        if ( file_exists($expected_index_file_name) ) {
    		unlink($expected_individual_index_file_name);
    	} 
    	$index_file_name = $this->indexer->_index_one_indivudual_file($csv_filename);
    	$this->assertEquals($expected_individual_index_file_name, $index_file_name, 
    			"The name of the index file: '$index_file_name'  is wrong; it should have been '$expected_individual_index_file_name'");
    	$existence = file_exists($expected_individual_index_file_name);
    	$this->assertTrue($existence,"The file $expected_individual_index_file_name should have been created.");
    	$expected_content = "qugjuq/1n:153\n";
    	$content = file_get_contents($index_file_name);
    	unlink($expected_individual_index_file_name);
    	$this->assertEquals($expected_content, $content, "The contents of the index file is wrong.");
    	$this->assertEquals(1,$this->indexer->get_nb_indexed_entries(), "The number of indexed morphemes should have been 1.");
    	$this->assertEquals(1,$this->indexer->get_nb_indexed_files(), "The total number of indexed files should have been 1.");
    }
 
    public function test__index_one_indivudual_file__Case_index_is_not_older_than_data() {
    	$csv_filename = 'oneRoot.csv';
    	$expected_index_file_name = $this->indexer->get_data_root() . DIRECTORY_SEPARATOR . $csv_filename . '.index';
        if ( file_exists($expected_index_file_name) ) {
    		unlink($expected_index_file_name);
    	}
    	$this->indexer->index_one_file($csv_filename);
    	sleep(1);
    	$this->setUp(); // reset $this->indexer
    	$index_file_name = $this->indexer->_index_one_indivudual_file($csv_filename);
    	$this->assertTrue($index_file_name==null,"The index file should not have been created again. The method should have returned NULL instead of '$index_file_name'");
    	$this->assertEquals(0,$this->indexer->get_nb_indexed_entries(), "The number of indexed morphemes should have been 0.");
    	$this->assertEquals(0,$this->indexer->get_nb_indexed_files(), "The total number of indexed files should have been 0.");
    }
    
    public function test__index_one_indivudual_file__Case_force_indexation() {
    	$csv_filename = 'oneRoot.csv';
    	$expected_index_file_name = $this->indexer->get_data_root() . DIRECTORY_SEPARATOR . $csv_filename . '.index';
    	if ( file_exists($expected_index_file_name) ) {
    		unlink($expected_index_file_name);
    	}
    	$this->indexer->force_indexation();
    	$index_file_name = $this->indexer->_index_one_indivudual_file($csv_filename);
    	$this->assertEquals($expected_index_file_name, $index_file_name, 
    			"The name of the index file: '$index_file_name'  is wrong; it should have been '$expected_index_file_name'");
    	$existence = file_exists($expected_index_file_name);
    	$this->assertTrue($existence,"The file $expected_index_file_name should have been created.");
    	$expected_content = "qugjuq/1n:153\n";
    	$content = file_get_contents($index_file_name);
    	unlink($expected_index_file_name);
    	$this->assertEquals($expected_content, $content, "The contents of the index file is wrong.");
    	$this->assertEquals(1,$this->indexer->get_nb_indexed_entries(), "The number of indexed morphemes should have been 1.");
    	$this->assertEquals(1,$this->indexer->get_nb_indexed_files(), "The total number of indexed files should have been 1.");
    }

}
?>
