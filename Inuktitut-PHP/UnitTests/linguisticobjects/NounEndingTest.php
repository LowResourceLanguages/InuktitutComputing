<?php
require_once 'linguisticobjects/NounEnding.php';
require_once 'linguisticobjects/Action.php';
require_once 'lib/parseCSV.php';
require_once 'linguisticobjects/LinguisticObjects.php';


class NounEndingTest extends PHPUnit_Framework_TestCase
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

		$champs = 'morpheme;type;case;number;perPoss;numbPoss;V-form;V-actions;V-action1;V-action2;t-form;t-actions;t-action1;t-action2;k-form;k-actions;k-action1;k-action2;q-form;q-actions;q-action1;q-action2;condPrec;sources';
		$donnees = 'k;tn;nom;d;;;k;n-n-allV;allV;x;k;i(ii);iallV(i);;k;s-n-allV;sallV;x;k;s-n-allV;sallV;x;;H2';
		$champsValeurs = parseCSV($champs,$donnees);
		$tn = new NounEnding($champsValeurs);
		$this->assertEquals('tn',$tn->type(),"");
		$this->assertEquals('tn-nom-d',$tn->signature(),"");
		$this->assertEquals('k/tn-nom-d',$tn->id(),"");
		
		$context = 'V';
		$forms = $tn->surfaceForms($context);
		$forms_attendues = array('k');
		$this->assertEquals(count($forms_attendues),count($forms),"");
		$behaviours = $tn->behaviours_for_context($context);
		$nb_expected_behaviours = 1;
		$this->assertTrue($behaviours != null,"Erreur");
		$this->assertEquals($nb_expected_behaviours,count($behaviours),"");
		$behaviour = $behaviours[0];
		$this->assertEquals(Action::NEUTRAL,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(Action::NEUTRAL,$behaviour->actionVV()->type(),"");
		$this->assertEquals(Action::VOWELLENGTHENING,$behaviour->actionNotVV()->type(),"");
		
		$context = 't';
		$behaviours = $tn->behaviours_for_context($context);
		$nb_expected_behaviours = 1;
		$this->assertTrue($behaviours != null,"Erreur");
		$this->assertEquals($nb_expected_behaviours,count($behaviours),"");
		$behaviour = $behaviours[0];
		$this->assertEquals(Action::INSERTION,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(null,$behaviour->actionVV(),"");
		$this->assertEquals(null,$behaviour->actionNotVV(),"");
		
		$context = 'k';
		$behaviours = $tn->behaviours_for_context($context);
		$nb_expected_behaviours = 1;
		$this->assertTrue($behaviours != null,"Erreur");
		$this->assertEquals($nb_expected_behaviours,count($behaviours),"");
		$behaviour = $behaviours[0];
		$this->assertEquals(Action::DELETION,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(Action::NEUTRAL,$behaviour->actionVV()->type(),"");
		$this->assertEquals(Action::VOWELLENGTHENING,$behaviour->actionNotVV()->type(),"");
	
		$context = 'q';
		$behaviours = $tn->behaviours_for_context($context);
		$nb_expected_behaviours = 1;
		$this->assertTrue($behaviours != null,"Erreur");
		$this->assertEquals($nb_expected_behaviours,count($behaviours),"");
		$behaviour = $behaviours[0];
		$this->assertEquals(Action::DELETION,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(Action::NEUTRAL,$behaviour->actionVV()->type(),"");
		$this->assertEquals(Action::VOWELLENGTHENING,$behaviour->actionNotVV()->type(),"");
		
		$sources = $tn->sources();
		$this->assertEquals(1,count($sources),"");
		$this->assertEquals('H2',$sources[0],"");
	}
    
}
?>
