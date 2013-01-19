<?php
require_once 'linguisticobjects/VerbEnding.php';
require_once 'linguisticobjects/Action.php';
require_once 'lib/parseCSV.php';



class VerbEndingTest extends PHPUnit_Framework_TestCase
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

	public function test__construct() {

		$champs = 'morpheme;type;mode;perSubject;numbSubject;perObject;numbObject;V-form;V-actions;V-action1;V-action2;t-form;t-actions;t-action1;t-action2;k-form;k-actions;k-action1;k-action2;q-form;q-actions;q-action1;q-action2;SpecificCondOnPrecMorph;sources';
		$donnees = 'junga;tv;ger;1;s;;;junga;n;n;;tunga;n;n;;tunga;n;n;;tunga;n;n;;;?';
		$champsValeurs = parseCSV($champs,$donnees);
		$tv = new VerbEnding($champsValeurs);
		$this->assertEquals('tv',$tv->type(),"");
		$this->assertEquals('tv-ger-1s',$tv->signature(),"");
		$this->assertEquals('junga/tv-ger-1s',$tv->id(),"ERREUR Cas 'junga'");

		$champs = 'morpheme;type;mode;sameSubject;posneg;tense;dialect;perSubject;numbSubject;perObject;numbObject;V-form;V-actions;V-action1;V-action2;t-form;t-actions;t-action1;t-action2;k-form;k-actions;k-action1;k-action2;q-form;q-actions;q-action1;q-action2;SpecificCondOnPrecMorph;sources';
		$donnees = 'nak;tv;part;oui;neg;;North Baffin, Cumberland Peninsula;2;s;;;nak natit;n n;n;;nak natit;nas nas;nas nas;;nak natit;nas nas;nas nas;;nak rak natit ratit;nas s nas s;nas s nas s nas s;;;M1 H2';
		$champsValeurs = parseCSV($champs,$donnees);
		$tv = new VerbEnding($champsValeurs);
		$this->assertEquals('tv',$tv->type(),"");
		$this->assertEquals('tv-part-2s',$tv->signature(),"");
		$this->assertEquals('nak/tv-part-2s',$tv->id(),"ERREUR Cas 'nak'");
	}
	
	
	
	
    
}
?>
