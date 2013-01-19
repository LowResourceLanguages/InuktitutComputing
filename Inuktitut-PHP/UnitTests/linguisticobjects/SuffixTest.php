<?php
require_once 'linguisticobjects/Suffix.php';
require_once 'linguisticobjects/Action.php';
require_once 'lib/parseCSV.php';
require_once 'linguisticobjects/LinguisticObjects.php';


class SuffixTest extends PHPUnit_Framework_TestCase
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
		$champs = 'morpheme;key;type;function;position;transitivity;nature;plural;antipassive;V-form;V-action1;V-action2;V-actions;t-form;t-action1;t-action2;t-actions;k-form;k-action1;k-action2;k-actions;q-form;q-action1;q-action2;q-actions;engMean;freMean;condPrec;condPrecTrans;condOnNext;sources;composition;dialect;mobility';
		$donnees = 'iq;2;sv;vv;m;n;;;;iq;n;i(ng);n-i(ng);iq;i(a);;i(a);iq;s;i(ng);s-i(ng);iq;s;i(ng);s-i(ng);no longer;ne plus;;;;H1;;;m';
		$champsValeurs = parseCSV($champs,$donnees);
		$suffixe = new Suffix($champsValeurs);
		$this->assertEquals('sv',$suffixe->type(),"");
		$this->assertEquals('2vv',$suffixe->signature(),"");
		$this->assertEquals('iq/2vv',$suffixe->id(),"");
		
		$context = 'V';
		$forms = $suffixe->surfaceForms($context);
		$this->assertEquals(1,count($forms),"");
		$action1s = $suffixe->actions1($context);
		$this->assertTrue($action1s != null,"Erreur : action1s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action1s),"");
		$action1 = $action1s[0];
		$this->assertEquals(Action::NEUTRAL,$action1->type(),"");
		$action2s = $suffixe->actions2($context);
		$this->assertTrue($action2s != null,"Erreur : action2s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action2s),"");
		$action2 = $action2s[0];
		$this->assertEquals(Action::INSERTION,$action2->type(),"");
		
		$context = 't';
		$action1s = $suffixe->actions1($context);
		$this->assertTrue($action1s != null,"Erreur : action1s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action1s),"");
		$action1 = $action1s[0];
		$this->assertEquals(Action::INSERTION,$action1->type(),"");
		$action2s = $suffixe->actions2($context);
		$this->assertTrue($action2s != null,"Erreur : action2s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action2s),"");
		$action2 = $action2s[0];
		$this->assertTrue($action2 == null,"Erreur : action2 dans le contexte '$context' n'est pas null");
		
		$context = 'k';
		$action1s = $suffixe->actions1($context);
		$this->assertTrue($action1s != null,"Erreur : action1s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action1s),"");
		$action1 = $action1s[0];
		$this->assertEquals(Action::DELETION,$action1->type(),"");
		$action2s = $suffixe->actions2($context);
		$this->assertTrue($action2s != null,"Erreur : action2s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action2s),"");
		$action2 = $action2s[0];
		$this->assertEquals(Action::INSERTION,$action2->type(),"");
		
		$context = 'q';
		$action1s = $suffixe->actions1($context);
		$this->assertTrue($action1s != null,"Erreur : action1s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action1s),"");
		$action1 = $action1s[0];
		$this->assertEquals(Action::DELETION,$action1->type(),"");
		$action2s = $suffixe->actions2($context);
		$this->assertTrue($action2s != null,"Erreur : action2s dans le contexte '$context' est nul.");
		$this->assertEquals(1,count($action2s),"");
		$action2 = $action2s[0];
		$this->assertEquals(Action::INSERTION,$action2->type(),"");
		
		$sources = $suffixe->sources();
		$this->assertEquals(1,count($sources),"");
		$this->assertEquals('H1',$sources[0],"");
		
		$this->assertEquals(1,count($suffixe->behaviours_for_context('V')),"");
		$behaviours = $suffixe->behaviours_for_context('V');
		$behaviour= $behaviours[0];
		$this->assertEquals(Action::NEUTRAL,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(Action::INSERTION,$behaviour->actionVV()->type(),"contexte V");
		
		$this->assertEquals(1,count($suffixe->behaviours_for_context('t')),"");
		$behaviours = $suffixe->behaviours_for_context('t');
		$behaviour= $behaviours[0];
		$this->assertEquals(Action::INSERTION,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(null,$behaviour->actionVV(),"contexte t");
		
		$this->assertEquals(1,count($suffixe->behaviours_for_context('k')),"");
		$behaviours = $suffixe->behaviours_for_context('k');
		$behaviour= $behaviours[0];
		$this->assertEquals(Action::DELETION,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(Action::INSERTION,$behaviour->actionVV()->type(),"contexte k");
		
		$this->assertEquals(1,count($suffixe->behaviours_for_context('q')),"");
		$behaviours = $suffixe->behaviours_for_context('q');
		$behaviour= $behaviours[0];
		$this->assertEquals(Action::DELETION,$behaviour->actionOnStem()->type(),"");
		$this->assertEquals(Action::INSERTION,$behaviour->actionVV()->type(),"contexte q");
	}
    
}
?>
