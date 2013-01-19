<?php
require_once 'linguisticobjects/NounRoot.php';
require_once 'linguisticobjects/VerbWord.php';
require_once 'linguisticobjects/LinguisticObjects.php';
require_once 'lib/parseCSV.php';

class NounRootTest extends PHPUnit_Framework_TestCase
{
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }

//------------------------------------------------------------------------------------------

	public function test__parseNousSens ()
	{
		$string = '(1) hook (2) harness snap [Padl.]';
		$liste = NounRoot::_parseNounMeaning($string);
		$liste_attendue = array ( '1' => 'hook', '2' => 'harness snap [Padl.]' );
		$this->assertEquals($liste_attendue,$liste,"");
	} 
	
	public function test_new_NounRoot() {
		$field_names = 'morpheme;variant;key;type;transitivity;nature;number;compositionRoot;intransSuffix;transSuffix;antipassive;OBSOLETE;OBSOLETE;engMean;freMean;composition;dialect;OBSOLETE;OBSOLETE;OBSOLETE';
		$field_values = 'taliaq;;;n;;;;;;;;;;wrist-watch;;;;;;';
		$attr_values = parseCSV($field_names, $field_values);
		$noun_root = new NounRoot($attr_values);
		$this->assertEquals(1,$noun_root->getNumericalKey(), "The attribute 'key' of the noun root should be 1");
	}
	
}
?>
