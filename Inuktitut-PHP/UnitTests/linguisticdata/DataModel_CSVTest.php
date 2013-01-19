<?php
require_once 'linguisticdata/DataModel_CSV.php';

class DataModel_CSVTest extends PHPUnit_Framework_TestCase {
	protected $data_model;

	/**
	 * Sets up the fixture, for example, opens a network connection.
	 * This method is called before a test is executed.
	 */
	protected function setUp()
	{
		$options = array( 'directory' => __DIR__, 'index' => 'main_index_for_data_tests' );
		$this->data_model = new DataModel_CSV($options);
	}

	/**
	 * Tears down the fixture, for example, closes a network connection.
	 * This method is called after a test is executed.
	 */
	protected function tearDown()
	{
	}

	//------------------------------------------------------------------------------------------

	public function test_This_is_how_you_create_a_DataModel_CSV__Case_by_default()  {
		$data_model = new DataModel_CSV();
		$expected_index_pathname = $data_model->get_default_index_pathname();
		$this->assertEquals($expected_index_pathname, $data_model->get_index_pathname(),"The pathname of the index file has not been set to the default pathname.");
	}
	
	public function test_This_is_how_you_create_a_DataModel_CSV__Case_specified_through_options() {
		$options = array( 'directory' => __DIR__, 'index' => 'main_index_for_data_tests' );
		$data_model = new DataModel_CSV( $options );
		$expected_index_pathname = realpath(__DIR__ . DIRECTORY_SEPARATOR . 'main_index_for_data_tests');
		$this->assertEquals($expected_index_pathname, $data_model->get_index_pathname(),"The pathname of the index file has not been set to the specified pathname.");
			}
	
	public function test_get_attributes_values_iterator__Case_iterator ()
	{
		$iterator = $this->data_model->get_attributes_values_iterator();
		$avs = array();
		while ($iterator->next()) {
			array_push($avs, $iterator->current());
		}
		$expected_avs = array(
				array( 'morpheme' => 'aakaq', 'variant' => '', 'key' => '1', 'type' => 'n', 'transitivity' => '', 'nature' => 'zoo',
						'number' => '', 'compositionRoot' => '', 'plural' => '', 'intransSuffix' => '', 'transSuffix' => '',
						'antipassive' => '', 
						'engMean' => 'kind of rock fish',
						'freMean' => 'sorte de poisson de roches',
						'source' => 'S2', 'dialect' => '', 'cf' => '', 'END' => '.',
						'dbName' => 'Inuktitut', 'tableName' => 'RootsSchneider'
						),
				array( 'morpheme' => 'suqquiq', 'variant' => '', 'key' => '1', 'type' => 'v', 'transitivity' => 't', 'nature' => '',
						'number' => '', 'compositionRoot' => '', 'plural' => '', 'intransSuffix' => '', 'transSuffix' => '',
						'antipassive' => 'si/1vv', 
						'engMean' => '[T]to understand or recognize or realize what s.o. or s.t. is now [P]for something or someone to be understood or recognized for what it is now; to be revealed in his identity [R]to make oneself or itself seen for what he/she/it is; to reveal his identity',
						'freMean' => '[T]comprendre ou reconnaître ou réaliser maintenant ce que qqn ou qqch. est [P]être compris ou reconnu pour ce que c\'est ou ce qu\'il est; être révélé dans son identité [R]révéler son identité; se montrer pour ce que l\'on est',
						'source' => 'S2', 'dialect' => 'Ungava', 'cf' => 'sukuiq/1v', 'END' => '.',
						'dbName' => 'Inuktitut', 'tableName' => 'RootsSchneider'
				),
				array( 'morpheme' => 'pisuk', 'variant' => '', 'key' => '1', 'type' => 'v', 'transitivity' => 'i', 'nature' => '',
						'number' => '', 'compositionRoot' => '', 'plural' => '', 'intransSuffix' => '', 'transSuffix' => 'nil',
						'antipassive' => '', 
						'engMean' => 'to walk (trans.: on s.t.)',
						'freMean' => 'marcher (trans.: sur qqch.)',
						'source' => '', 'dialect' => '', 'cf' => '', 'END' => '.',
						'dbName' => 'Inuktitut', 'tableName' => 'RootsSpalding'
				),
				array( 'morpheme' => 'arviq', 'variant' => '', 'key' => '1', 'type' => 'n', 'transitivity' => '', 'nature' => 'zoo',
						'number' => '', 'compositionRoot' => '', 'plural' => '', 'intransSuffix' => '', 'transSuffix' => '',
						'antipassive' => '', 
						'engMean' => 'right or Greenland whale',
						'freMean' => 'baleine boréale',
						'source' => '', 'dialect' => '', 'cf' => '', 'END' => '.',
						'dbName' => 'Inuktitut', 'tableName' => 'RootsSpalding'
				),
		);
		$this->assertEquals($expected_avs, $avs, "The iterator did not return the right array of attributes-values.");
	}
	
}
	
	?>