<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/CSV_DB_Reader.php';
 
class CSV_DB_ReaderTest extends PHPUnit_Framework_TestCase
{
	
	/**
     * @var utf8a
     */
    protected $lecteur;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
    	$this->lecteur = new CSV_DB_Reader('linguisticdata');
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }
	
	
    public function test_Voici_comment_on_cree_un_CSV_DB_Reader()
    {
    	$directory = 'blablabla';
    	$lecteur = new CSV_DB_Reader($directory);
    }
    
    public function test_Voici_comment_on_lit_un_fichier_csv()
    {
    	$fichier = null;
    	$status = $this->lecteur->readCSVFile($fichier);
    	$attendu = false;
    	$this->assertEquals($attendu,$status,"");
    	
    	$fichier = 'blabla.txt'; // fichier inexistant
    	$status = $this->lecteur->readCSVFile($fichier);
    	$attendu = false;
    	$this->assertEquals($attendu,$status,"");
    	
    	$fichier = 'linguisticdata/RootsSpalding.csv'; // fichier existant
    	$status = $this->lecteur->readCSVFile($fichier);
    	$attendu = true;
    	$this->assertEquals($attendu,$status,"");
    }
    
    public function test_Voici_comment_on_lit_les_donnees_d_un_fichier_index()
    {
    	$id = 'blabla';
    	$donnees_index = $this->lecteur->readIndexForElement($id);
    	$attendu = null;
    	$description_cas = 'Cas 1 : \$id inexistant';
    	$this->assertEquals($attendu, $donnees_index, $description_cas);
    	
    	$id = 'ailaq/1v';
    	$attendu = array(1772,'RootsSpalding.csv');
    	$donnees_index = $this->lecteur->readIndexForElement($id);
    	$description_cas = 'Cas 2 : \$id existant';
    	$this->assertEquals($attendu, $donnees_index, $description_cas);
    }
    
    public function test_Voici_comment_on_lit_les_donnees_d_un_element_linguistique_identifie_par_son_id()
    {
    	$id = 'blabla';
    	$donnees = $this->lecteur->readCSVDataForElement($id);
    	$attendu = null;
    	$this->assertEquals($attendu, $donnees, "");
    	
    	$id = 'ailaq/1v';
    	$donnees = $this->lecteur->readCSVDataForElement($id);
    	$attendu = array(
    		'morpheme' => 'ailaq',
    		'variant' => '',
    		'key' => '1',
    		'type' => 'v',
    		'transitivity' => 'i',
    		'nature' => 'a',
    		'number' => '',
    		'compositionRoot' => '',
    		'plural' => '',
    		'intransSuffix' => '',
    		'transSuffix' => '',
    		'antipassive' => '',
    		'engMean' => 'to be damp with moisture, wet with dew, etc.',
    		'freMean' => "\xEAtre mouill\xE9 par l'humidit\xE9, la ros\xE9e, etc.",
    		'source' => '',
    		'dialect' => '',
    		'cf' => '',
    		'END' => '.'
    	);
    	$this->assertEquals($attendu, $donnees, "");
    }
    
    public function test_listOfElementsInCSVFile()
    {
    	$fichier = 'RootsSpalding.csv';
    	$liste = $this->lecteur->listOfElementsInCSVFile($fichier);
    	$attendu = array(
    		'aa/1v', 'a-aaq/1v', 'aajuq/1v', 'aaktuq/1v', 'aanga/1v', 'aanniq/1v',
    		'aar&uq/1v', 'aatajaq/1v', 'aattaq/1v', 'ablaaq/1v');
    	$this->assertEquals($attendu, array_slice($liste,0,10), "");
    }
 
}
?>
