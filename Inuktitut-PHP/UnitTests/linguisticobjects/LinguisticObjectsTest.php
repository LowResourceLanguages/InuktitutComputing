<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/LinguisticObjects.php';

 
class LinguisticObjectsTest extends PHPUnit_Framework_TestCase
{
	
	/**
     * @var utf8a
     */
    protected $object;
    protected $env_var;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
        $this->object = null;
        $this->env_var = getenv('PHP_INUKTITUT');
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    	putenv('PHP_INUKTITUT='.$this->env_var);
    }
	
	
	
    public function test_findLinguisticDataInIndex()
    {
    	$id = 'aakaq/1n';
    	$linguistic_data = LinguisticObjects::findLinguisticDataInIndex($id);
    	$expected_morpheme = 'aakaq';
    	$this->assertEquals($expected_morpheme, $linguistic_data['morpheme'],"The field 'morpheme' of the object is not right.");
    }
    
    public function test_getObject() {
    	$id = 'aakaq/1n';
    	$object = LinguisticObjects::getObject($id);
    	$expected_type = 'n';
    	$this->assertEquals($expected_type,$object->type(),"The type of the object is not right.");
    }
    
    public function test_listOfRoots() {
    	$saved_env_var = getenv('PHP_INUKTITUT');
    	putenv('PHP_INUKTITUT='.getenv('PHP_INUKTITUT').'/UnitTests');
    	$liste = LinguisticObjects::listOfRoots();
    	$liste_attendue = array(
    		'pisuk/1v', 'arviq/1n', 'aakaq/1n', 'suqquiq/1v');
		$this->assertSame($liste_attendue, $liste, "The list of root ids is wrong.");
    }
 
    public function test_make_object_ids() {
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'n', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1n');
    	$this->assertEquals( $expected_ids, $ids, "The id of the noun root is incorrect.");

        $field_values = array( 'morpheme'=> 'abc',  'type' => 'a', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1a');
    	$this->assertEquals( $expected_ids, $ids, "The id of the adverb is incorrect.");

        $field_values = array( 'morpheme'=> 'abc',  'type' => 'e', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1e');
    	$this->assertEquals( $expected_ids, $ids, "The id of the expression is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'c', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1c');
    	$this->assertEquals( $expected_ids, $ids, "The id of the conjonction is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'v', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1v');
    	$this->assertEquals( $expected_ids, $ids, "The id of the verb root is incorrect.");

        $field_values = array( 'id'=> 'abc', 'type' => 's' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc');
    	$this->assertEquals( $expected_ids, $ids, "The id of the source is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc', 'type' => 'sn',  'function' => 'nv', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1nv');
    	$this->assertEquals( $expected_ids, $ids, "The id of the noun-to-verb noun suffix is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc', 'type' => 'q', 'key' => 1 );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1q');
    	$this->assertEquals( $expected_ids, $ids, "The id of the tail suffix is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'tn', 'case' => 'acc', 'number' => 's' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/tn-acc-s');
    	$this->assertEquals( $expected_ids, $ids, "The id of the noun ending without possessive is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'tn', 'case' => 'acc', 'number' => 's', 'perPoss' => '3', 'numbPoss' => 'p' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/tn-acc-s-3p');
    	$this->assertEquals( $expected_ids, $ids, "The id of the noun ending with possessive is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'tv', 'mode' => 'ind', 'perSubject' => '2', 'numbSubject' => 'p' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/tv-ind-2p');
    	$this->assertEquals( $expected_ids, $ids, "The id of the non-specific verb ending is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'tv', 'mode' => 'ind', 'perSubject' => '2', 'numbSubject' => 'p', 'perObject' => '3', 'numbObject' => 'd' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/tv-ind-2p-3d');
    	$this->assertEquals( $expected_ids, $ids, "The id of the specific verb ending is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'tv', 'mode' => 'part', 'perSubject' => '2', 'numbSubject' => 'p', 'tense' => 'fut' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/tv-part-2p-fut');
    	$this->assertEquals( $expected_ids, $ids, "The id of the non-specific future-tense participal verb ending is incorrect.");
    	
    	$field_values = array( 'morpheme'=> 'abc',  'type' => 'tv', 'mode' => 'part', 'perSubject' => '2', 'numbSubject' => 'p' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/tv-part-2p');
    	$this->assertEquals( $expected_ids, $ids, "The id of the non-specific participal verb ending is incorrect.");
    	
    	$field_values = array( 'verb'=> 'abc', 'type' => 'vw' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/vw');
    	$this->assertEquals( $expected_ids, $ids, "The id of the verb word is incorrect.");
    	
    	$field_values = array( 'morpheme' => 'abc', 'type' => 'pr', 'key' => '1' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1pr');
    	$this->assertEquals( $expected_ids, $ids, "The id of the pronoun is incorrect.");
    	
    	$field_values = array( 'morpheme' => 'abc', 'type' => 'rpr', 'key' => '1' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/1rpr');
    	$this->assertEquals( $expected_ids, $ids, "The id of the pronoun root is incorrect.");
    	
    	$field_values = array( 'morpheme' => 'abc', 'type' => 'pd', 'typeObject' => '?', 'number' => 's', 'root' => 'ab' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/pd-?-s', 'ab/rpd-?');
    	$this->assertEquals( $expected_ids, $ids, "The id of the demonstrative pronoun is incorrect.");
    	
    	$field_values = array( 'morpheme' => 'abc', 'type' => 'ad', 'typeObject' => 'sc' );
    	$id_objects = LinguisticObjects::make_object_ids($field_values);
    	$ids = array_map("self::to_string", $id_objects);
    	$expected_ids = array('abc/ad-sc');
    	$this->assertEquals( $expected_ids, $ids, "The id of the demonstrative adverb is incorrect.");
    	 
    }
    
    static function to_string($id_object) {
    	return $id_object->toString();
    }
}
?>
