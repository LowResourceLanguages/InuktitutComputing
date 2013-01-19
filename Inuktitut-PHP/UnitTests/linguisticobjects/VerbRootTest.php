<?php
 require_once 'lib/log4php/Logger.php';
Logger::configure(getenv('PHP_INUKTITUT').'/log4php.properties.xml');

require_once 'linguisticobjects/VerbRoot.php';
require_once 'linguisticobjects/VerbWord.php';
require_once 'linguisticobjects/LinguisticObjects.php';

class VerbRootTest extends PHPUnit_Framework_TestCase
{
    protected $trans_object;
    protected $intrans_object;
    
    public function test_reminder ()
    {
    	$this->assertFalse(TRUE, '*** Remember to reactivate all tests in VerbRootTest');
    }
    
    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
    	$valeurs = 'akpaq;;1;v;t;;;;;;;i/1vv;to /bring s.t. down; to /lower s.t.;"/baisser; /abaisser qqch.";;;;.';
    	$this->trans_object = $this->faireObjetRacine($valeurs);

    	$valeurs = 'allak;;1;v;i;;;;;;nil;;to write or draw (trans.: s.t.);ï¿½crire ou dessiner (trans.: qqch.);;;;.';
    	$this->intrans_object = $this->faireObjetRacine($valeurs);    	
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }

//------------------------------------------------------------------------------------------

	public function IGNORE_test_replaceWithPersPron_FR__Case_Verb_Prep_Pronoun ()
	{
		$texte = 'regarder en haut vers qqch.';
		$remp = VerbRoot::replaceWithPersPron_FR($texte);
		$att = 'regarder en haut vers ça';
		$this->assertEquals($att, $remp, "");
	}
	
	public function IGNORE_test_replaceWithPersPron_FR__Case_Verb_Pronoun ()
	{
		$texte = 'regarder qqn,';
		$remp = VerbRoot::replaceWithPersPron_FR($texte);
		$att = 'le/la regarder,';
		$this->assertEquals($att, $remp, "");

		$texte = 'regarder qqch.';
		$remp = VerbRoot::replaceWithPersPron_FR($texte);
		$att = 'le/la regarder';
		$this->assertEquals($att, $remp, "");
		
		$texte = 'arrimer qqch.';
		$remp = VerbRoot::replaceWithPersPron_FR($texte);
		$att = 'l\'arrimer';
		$this->assertEquals($att, $remp, "");
	}
	
	public function IGNORE_test_replaceWithPersPron_FR__Case_2_props ()
	{
		$texte = 'pousser qqn, conseiller à qqn';
		$remp = VerbRoot::replaceWithPersPron_FR($texte);
		$att = 'le/la pousser, lui conseiller,';
		$this->assertEquals($att, $remp, "");
	}
	
	/*
	 * Intransitive verbs
	 */
	
	public function test_makeMeaningsForIntransitiveVerbsForSense__1__en ()
	{
		$string = 'to play traditional game of catch';
		$lang = 'en';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array( 
			'1' => array ( 'iv_intrans_no_object' => 'to play traditional game of catch' )
			);
		$this->assertEquals($sens_attendus, $sens, "");
	}

	public function test_makeMeaningsForIntransitiveVerbsForSense__2__en ()
	{
		$string = '(1) to melt or thaw (2) to bleed at the nose';
		$lang = 'en';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 'iv_intrans_no_object' => 'to melt or thaw' ),
				'2' => array ( 'iv_intrans_no_object' => 'to bleed at the nose' ),
				);
		$this->assertEquals($sens_attendus, $sens, "");
	}

	public function test_makeMeaningsForIntransitiveVerbsForSense__3__en ()
	{
		$string = 'to steer (intrans.: a boat; trans.: s.t. (a boat))';
		$lang = 'en';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 
					'iv_intrans_no_object' => 'to steer a boat',
					'iv_intrans_with_object' => 'to steer s.t. (a boat) (-mik)',
					'iv_trans' => 'to steer it (a boat)'
				 ) );
		$this->assertEquals($sens_attendus, $sens, "Wrong rendering of English meanings.");
	}
	
	public function test_makeMeaningsForIntransitiveVerbsForSense__4__en ()
	{
		$string = 'to look up (trans.: at s.t.)';
		$lang = 'en';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 
					'iv_intrans_no_object' => 'to look up',
					'iv_intrans_with_object' => 'to look up at s.t. (-mik)',
					'iv_trans' => 'to look up at it'
				 ) );
		$this->assertEquals($sens_attendus, $sens, "");
		
		// regarder en haut, lever les yeux (trans.: vers qqch.)
	}

	public function test_makeMeaningsForIntransitiveVerbsForSense__5__en ()
	{
		$string = 'to go up or climb s.t.';
		$lang = 'en';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 
					'iv_intrans_no_object' => 'to go up or climb something',
					'iv_intrans_with_object' => 'to go up or climb s.t. (-mik)',
					'iv_trans' => 'to go up or climb it'
				 	)
				 );
		$this->assertEquals($sens_attendus, $sens, "");
		
		// regarder en haut, lever les yeux (trans.: vers qqch.)
	}
	
	public function testtest_makeMeaningsForIntransitiveVerbsForSense__6__en ()
	{
		$string = '(1) to go up or climb s.t. (2) to go ashore from boat or from sea-ice';
		$lang = 'en';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 
					'iv_intrans_no_object' => 'to go up or climb something',
					'iv_intrans_with_object' => 'to go up or climb s.t. (-mik)',
					'iv_trans' => 'to go up or climb it'
				 	),
				 '2' => array (
				 	'iv_intrans_no_object' => 'to go ashore from boat or from sea-ice'
				 	) 
				 );
		$this->assertEquals($sens_attendus, $sens, "");
		
		// regarder en haut, lever les yeux (trans.: vers qqch.)
	}
	
	public function test_makeMeaningsForIntransitiveVerbsForSense__1__fr ()
	{
		$string = '/conduire (intrans.: un bateau; trans.: qqch. (un bateau))';
		$lang = 'fr';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 
					'iv_intrans_no_object' => 'conduire un bateau',
					'iv_intrans_with_object' => 'conduire qqch. (un bateau) (-mik)',
					'iv_trans' => 'le/la conduire (un bateau)'
				 ) );
		$this->assertEquals($sens_attendus, $sens, "Wrong rendering of French meanings.");
	}

	public function test_makeMeaningsForIntransitiveVerbsForSense__2__fr ()
	{
		$string = 'regarder vers le haut, /lever les yeux (trans.: vers qqch.)';
		$lang = 'fr';
		$sens = VerbRoot::makeMeaningsForIntransitiveVerbsForSense($string,$lang);
		$sens_attendus = array(
				'1' => array ( 
					'iv_intrans_no_object' => 'regarder vers le haut, lever les yeux',
					'iv_intrans_with_object' => 'regarder vers le haut, lever les yeux vers qqch. (-mik)',
					'iv_trans' => 'regarder vers le haut, lever les yeux vers ça'
				 ) );
		$this->assertEquals($sens_attendus, $sens, "");
		
		// regarder en haut, lever les yeux (trans.: vers qqch.)
	}
	
	public function ___test_getIntransitiveMeaningOfIntransitiveVerbNoObject__1 ()
	{
		$meanings = $this->intrans_object->getIntransitiveMeaningOfIntransitiveVerb('en');
		$att = array('to write or draw');
		$this->assertEquals($att, $meanings, "");
	}
	
	public function ___test_getIntransitiveMeaningOfIntransitiveVerbNoObject__2 ()
	{
    	$valeurs = 'majuq;;1;v;i;;;;;;;;(1) to go up or climb s.t. (2) to go ashore from boat or from sea-ice;(1) aller sur le sommet de qqch.;;;;';
    	$object = $this->faireObjetRacine($valeurs);
    	$meanings = $object->getIntransitiveMeaningOfIntransitiveVerb('en');
		$att = array('to go up or climb something', 'to go ashore from boat or from sea-ice');
		$this->assertEquals($att, $meanings, "");
	}
		
	public function ___test_getIntransitiveMeaningOfIntransitiveVerbWithObject__1 ()
	{
		$meanings = $this->intrans_object->getIntransitiveMeaningOfIntransitiveVerb('en', TRUE);
		$att = array('to write or draw s.t. (-mik)');
		$this->assertEquals($att, $meanings, "");
	}
	
	public function ___test_getIntransitiveMeaningOfIntransitiveVerbWithObject__2 ()
	{
    	$valeurs = 'majuq;;1;v;i;;;;;;;;(1) to go up or climb s.t. (2) to go ashore from boat or from sea-ice;(1) aller sur le sommet de qqch.;;;;';
    	$object = $this->faireObjetRacine($valeurs);
    	$meanings = $object->getIntransitiveMeaningOfIntransitiveVerb('en', TRUE);
		$att = array('to go up or climb s.t. (-mik)');
		$this->assertEquals($att, $meanings, "");
	}
		
	public function ___test_getTransitiveMeaning__1 ()
	{
		$meanings = $this->intrans_object->getTransitiveMeaning('en');
		$att = array('to write or draw it');
		$this->assertEquals($att, $meanings, "");
	}
	
	public function ___test_getTransitiveMeaning__2 ()
	{
    	$valeurs = 'majuq;;1;v;i;;;;;;;;(1) to go up or climb s.t. (2) to go ashore from boat or from sea-ice;(1) aller sur le sommet de qqch.;;;;';
    	$object = $this->faireObjetRacine($valeurs);
    	$meanings = $object->getTransitiveMeaning('en');
		$att = array('to go up or climb it');
		$this->assertEquals($att, $meanings, "");
	}
		
	public function ___test_getTransitiveMeaning__3 ()
	{
    	$valeurs = 'aggiq;;1;v;i;;;;;;;;to come home;;;;;';
    	$object = $this->faireObjetRacine($valeurs);
    	$meanings = $object->getTransitiveMeaning('en');
		$att = array();
		$this->assertEquals(0,count($meanings),"");
		$this->assertEquals($att, $meanings, "");
	}
		
	
	/*
	 * Transitive verbs ------------------------------------------------------------------------------------------
	 */

	public function ___testMakePassivePart() {
		$text = ' s.t. to someone (-mut)';
		$partPassive = VerbRoot::replaceObjectInPassivePart($text,'en');
		$partPassiveTarget = ' to someone (-mut)';
		$this->assertEquals($partPassive,$partPassiveTarget,"Passive part '$partPassive' should have been '$partPassiveTarget'");
	}
	
	public function ___testMakeReflexivePart() {
		$text = ' s.t. to someone (-mut)';
		$partReflexive = VerbRoot::replaceObjectInReflexivePart($text,'en');
		$partReflexiveTarget = ' itself to someone (-mut)';
		$this->assertEquals($partReflexive,$partReflexiveTarget,"Reflexive part '$partReflexive' should have been '$partReflexiveTarget'");
	}	
    
    public function ___testMakeVerbFormPassive_EN() {
    	$pass = VerbRoot::makeVerbFormPassive('bring','en');
    	$passTarget = 'brought';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");

    	$pass = VerbRoot::makeVerbFormPassive('adopt','en');
    	$passTarget = 'adopted';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");

    	$pass = VerbRoot::makeVerbFormPassive('page','en');
    	$passTarget = 'paged';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");

    	$pass = VerbRoot::makeVerbFormPassive('deny','en');
    	$passTarget = 'denied';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");
    }

    public function ___testMakeVerbFormPassive_FR() {
    	$pass = VerbRoot::makeVerbFormPassive('mettre','fr');
    	$passTarget = 'mis';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");

    	$pass = VerbRoot::makeVerbFormPassive('adopter','fr');
    	$passTarget = 'adopté';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");

    	$pass = VerbRoot::makeVerbFormPassive('finir','fr');
    	$passTarget = 'fini';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");
    	
    	$pass = VerbRoot::makeVerbFormPassive('se-cogner-contre','fr');
    	$passTarget = 'se faire cogner';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");
    	
    	$pass = VerbRoot::makeVerbFormPassive('marcher-sur','fr');
    	$passTarget = 'se faire marcher dessus';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");
    	
    	$pass = VerbRoot::makeVerbFormPassive('mordre-dans','fr');
    	$passTarget = 'se faire mordre';
    	$this->assertEquals($passTarget,$pass,"'$pass' should have been '$passTarget'");
    }
    
    public function testMakeTransitiveVerbMeaningsForSense_EN_1_verb_Case_A() {
    	$sense = 'to /cover s.o. with a blanket';
    	$rv = new VerbRoot(null);
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meaning = array_pop($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to cover him/her with a blanket';
    	$pmTg = 'to be covered with a blanket';
    	$imTg = 'to cover s.o. (-mik) with a blanket';
    	$refmTg = 'to cover oneself with a blanket';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function testMakeTransitiveVerbMeaningsForSense_EN_1_verb_Case_B() {
    	$sense = '[-R]to /offer s.t. to someone (-mut)';
    	$rv = new VerbRoot(null);
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meaning = array_pop($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to offer it to someone (-mut)';
    	$pmTg = 'to be offered to someone (-mut)';
    	$imTg = 'to offer s.t. (-mik) to someone (-mut)';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function testMakeTransitiveVerbMeaningsForSense_EN_2_verbs() {
    	$sense = '[-R]to /offer, /give s.t. to someone (-mut)';
    	$rv = new VerbRoot(null);
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meaning = array_pop($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to offer, give it to someone (-mut)';
    	$imTg = 'to offer, give s.t. (-mik) to someone (-mut)';
    	$pmTg = 'to be offered, given to someone (-mut)';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function testMakeTransitiveVerbMeaningsForSense_EN_3_verbs() {
    	$sense = '[-R]to /offer, /give, /bring s.t. to someone (-mut)';
    	$rv = new VerbRoot(null);
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meaning = array_pop($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to offer, give, bring it to someone (-mut)';
    	$imTg = 'to offer, give, bring s.t. (-mik) to someone (-mut)';
    	$pmTg = 'to be offered, given, brought to someone (-mut)';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function test_MakeTransitiveVerbMeaningsForSense_FR_3_verbs_Case_A () {
    	$sense = '[-R]/pousser qqn, /conseiller-à qqn, /recommander-à qqn de faire quelque chose';
    	$rv = new VerbRoot(null);
    	$rv->setFrenchMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('fr');
    	$meaning = array_pop($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'le/la pousser, lui conseiller, lui recommander de faire quelque chose';
    	$pmTg = 'être poussé, se faire conseiller, se faire recommander de faire quelque chose';
    	$imTg = 'pousser qqn (-mik), conseiller à qqn (-mik), recommander à qqn (-mik) de faire quelque chose';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function ___testMakeTransitiveVerbMeaningsForSense_EN_T_ () {
    	$rv = new VerbRoot(null);
    	$sense = '[T]to brush or rub lightly against s.t.; to touch s.t., to put one\'s hand on s.t.';
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meaning = array_pop($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to brush or rub lightly against it; to touch it, to put one\'s hand on it';
    	$imTg = 'to brush or rub lightly against s.t. (-mik); to touch s.t. (-mik), to put one\'s hand on s.t. (-mik)';
    	$pmTg = '';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function ___testMakeTransitiveVerbMeaningsForSense_EN_TP() {
    	$rv = new VerbRoot(null);
    	$sense = '[T]to convey a message for s.o.; to represent s.o. or s.t.[P]to have his message conveyed';
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meaning = array_shift($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to convey a message for him/her; to represent him/her or it';
    	$imTg = 'to convey a message for s.o. (-mik); to represent s.o. or s.t. (-mik)';
    	$pmTg = '';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
    	$meaning = array_shift($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = '';
    	$imTg = '';
    	$pmTg = 'to have his message conveyed';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
    	//    	echo "transitive: '$tm'\nintransitive: '$im'\npassive: '$pm'\nreflexive: '$refm'\nresultive: '$resm'\n";
    }
    
    public function ___testMakeTransitiveVerbMeaningsForSense_EN_R_2senses() {
    	$rv = new VerbRoot(null);
    	$sense = '[-R](1) to /offer, /give, /bring s.t. to someone (-mut) (2) to /take s.t. away forcibly from someone (-mit)';
    	$rv->setEnglishMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('en');
    	$meanings1 = $meanings[1];
    	$meaning = array_pop($meanings1);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to offer, give, bring it to someone (-mut)';
    	$imTg = 'to offer, give, bring s.t. (-mik) to someone (-mut)';
    	$pmTg = 'to be offered, given, brought to someone (-mut)';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'1.transitive meaning');
    	$this->assertEquals($imTg,$im,'1.intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'1.passive meaning');
    	$this->assertEquals($refmTg,$refm,'1.reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'1.resultative meaning');
    	$meanings2 = $meanings[2];
    	$meaning = array_pop($meanings2);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'to take it away forcibly from someone (-mit)';
    	$imTg = 'to take s.t. (-mik) away forcibly from someone (-mit)';
    	$pmTg = 'to be taken away forcibly from someone (-mit)';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'2.transitive meaning');
    	$this->assertEquals($imTg,$im,'2.intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'2.passive meaning');
    	$this->assertEquals($refmTg,$refm,'2.reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'2.resultative meaning');
    }
        
    public function ___testMakeTransitiveVerbMeaningsForSense_FR_T_faire_tourner () {
    	$rv = new VerbRoot(null);
    	$sense = '[T]faire tourner qqch.';
    	$rv->setFrenchMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('fr');
    	$meaning = array_shift($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'le/la faire tourner';
    	$imTg = 'faire tourner qqch. (-mik)';
    	$pmTg = '';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
    }
    
 
    public function IGNORE_testMakeTransitiveVerbMeaningsForSense_FR_T() {
    	$rv = new VerbRoot(null);
    	$sense = '[T]faire faire des ï¿½tincelles ï¿½ qqch. (une pierre ï¿½ feu)';
    	$rv->setFrenchMeaning($sense);
    	$meanings = $rv->makeMeaningsForTransitiveVerbsForSense('fr');
    	$meaning = array_shift($meanings[1]);
    	$tm = $meaning['trans'];
    	$im = $meaning['intrans'];
    	$pm = $meaning['passive'];
    	$refm = $meaning['reflexive'];
    	$resm = $meaning['resultive'];
    	$tmTg = 'lui faire faire des étincelles (une pierre à feu)';
    	$imTg = 'faire faire des étincelles à qqch. (-mik) (une pierre à feu)';
    	$pmTg = '';
    	$refmTg = '';
    	$resmTg = '';
    	$this->assertEquals($tmTg,$tm,'transitive meaning');
    	$this->assertEquals($imTg,$im,'intransitive meaning');
    	$this->assertEquals($pmTg,$pm,'passive meaning');
    	$this->assertEquals($refmTg,$refm,'reflexive meaning');
    	$this->assertEquals($resmTg,$resm,'resultative meaning');
    }
    
    public function ___testAddMik_EN() {
    	$text = 'to convey a message for s.o.; to represent s.o. or s.t.';
    	$mik = VerbRoot::addMik($text,'en');
    	$mikTg = 'to convey a message for s.o. (-mik); to represent s.o. or s.t. (-mik)';
    	$this->assertEquals($mikTg, $mik, '');
    }    

    public function ___testAddMik_FR() {
    	$text = 'porter un message à qqn; représenter qqn ou qqch.';
    	$mik = VerbRoot::addMik($text,'fr');
    	$mikTg = 'porter un message à qqn (-mik); représenter qqn ou qqch. (-mik)';
    	$this->assertEquals($mikTg, $mik, '');
    }    
    
	public function ___test_attribut() {
		$id = 'uumi/1v';
		$rv = LinguisticObjects::getObject($id);
		$type = $rv->attribute('type');
		$type_att = 'v';
		$this->assertEquals($type_att, $type, "");
	}   
	
	/*
	 * 
	 */
	
	public function ___test__parseVerbSens__1 ()
	{
		$string = '[-R] (1) sens 1 (-mut) (2) sens 2 (-mik) [R] juste un sens (-mut)';
		$liste = VerbRoot::_parseVerbSens($string);
		$liste_attendue = array (
			array( 'mode' => '-R',
					'sens' => array(
								array( 'nb' => 1, 'propositions' => array('sens 1 (-mut)') ),
								array( 'nb' => 2, 'propositions' => array('sens 2 (-mik)') ) ) ),
			array( 'mode' => 'R',
					'propositions' => array('juste un sens (-mut)') ) );
		$this->assertEquals($liste_attendue,$liste,"");
	} 
	
	public function ___test__parseVerbSens__2 ()
	{
		$string = 'un sens intransitif';
		$liste = VerbRoot::_parseVerbSens($string);
		$liste_attendue = array ( 'un sens intransitif' ) ;
		$this->assertEquals($liste_attendue,$liste,"");
	} 
	
	public function ___test__makeVerbMeanings_Cas_1 ()
	{
		$string = '[-R] (1) sens 1 (-mut) (2) sens 2 (-mik) [R] juste un sens (-mut)';
		$sens = VerbRoot::_makeVerbMeanings($string);
		$this->assertEquals(3, count($sens), "");
		$att = array(
			new MeaningOfMorpheme( array('sens 1 (-mut)'), 1, '-R' ),
			new MeaningOfMorpheme( array('sens 2 (-mik)'), 2, '-R' ),
			new MeaningOfMorpheme( array('juste un sens (-mut)'), 1, 'R') );
		$this->assertEquals($att, $sens, "");
	}
	
	public function ___test__makeVerbMeanings_Cas_2 ()
	{
		$string = '[T]to brush or rub lightly against s.t.; to touch s.t., to put one\'s hand on s.t.';
		$sens = VerbRoot::_makeVerbMeanings($string);
		$this->assertEquals(1, count($sens), "");
		$att = array(
			new MeaningOfMorpheme( 
				array('to brush or rub lightly against s.t.', 'to touch s.t., to put one\'s hand on s.t.'), 1, 'T' ) );
		$this->assertEquals($att, $sens, "");
	}
	
	
	
	    // ------------------------------------------------ Expérimental --------------------------------------------------------------------

	
   public function testPrepareSenseForTransformation_EN_1() {
   	$text = 'to /convey a message for s.o.; to /represent s.o. or s.t.';
   	$pt = VerbRoot::prepareSenseForTransformation_EN($text);
   	$ptTg = 'v(to /convey) a message p(for) c(s.o.); v(to /represent) c(s.o. or s.t.)';
   	$this->assertEquals($ptTg, $pt, '');
   }    

   public function testPrepareSenseForTransformation_EN_2() {
   	$text = 'to /convey, /send a message for or to s.o.; to /represent s.o. or s.t.';
   	$pt = VerbRoot::prepareSenseForTransformation_EN($text);
   	$ptTg = 'v(to /convey, /send) a message p(for or to) c(s.o.); v(to /represent) c(s.o. or s.t.)';
   	$this->assertEquals($ptTg, $pt, '');
   }   
   
   public function testPrepareSenseForTransformation_EN_3() {
   	$text = 'to /offer, /give, /bring s.t. to someone (-mut)';
   	$pt = VerbRoot::prepareSenseForTransformation_EN($text);
   	$ptTg = 'v(to /offer, /give, /bring) c(s.t.) p(to) someone (-mut)';
   	$this->assertEquals($ptTg, $pt, '');
   }
   
   public function testToTransitive_EN_1() {
   	$text = 'v(to /convey) a message p(for) c(s.o.); v(to /represent) c(s.o. or s.t.)';
   	$trTg = 'to convey a message for him/her; to represent him/her or it';
   	$tr = VerbRoot::toTransitive_EN($text);
   	$this->assertEquals($trTg, $tr, '');    	
   }
   
   public function testToTransitive_EN_2() {
   	$text = 'v(to /offer, /give, /bring) c(s.t.) p(to) someone (-mut)';
   	$trTg = 'to offer, give, bring it to someone (-mut)';
   	$tr = VerbRoot::toTransitive_EN($text);
   	$this->assertEquals($trTg, $tr, '');    	
   }
   
   public function testToTransitive_EN_3() {
   	$text = 'v(to /convey, /send) a message p(for or to) c(s.o.); v(to /represent) c(s.o. or s.t.)';
   	$trTg = 'to convey, send a message for or to him/her; to represent him/her or it';
   	$tr = VerbRoot::toTransitive_EN($text);
   	$this->assertEquals($trTg, $tr, '');    	
   }
   
   public function testPrepareSenseForTransformation_FR_1() {
   	$text = '/transmettre un message pour qqn; /représenter qqn ou qqch.';
   	$pt = VerbRoot::prepareSenseForTransformation_FR($text);
   	$ptTg = 'v(/transmettre) un message p(pour) c(qqn); v(/représenter) c(qqn ou qqch.)';
   	$this->assertEquals($ptTg, $pt, '');
   }    

   public function testPrepareSenseForTransformation_FR_2() {
   	$text = '/transmettre, /envoyer un message pour ou à qqn; /représenter qqn ou qqch.';
   	$pt = VerbRoot::prepareSenseForTransformation_FR($text);
   	$ptTg = 'v(/transmettre, /envoyer) un message p(pour ou à) c(qqn); v(/représenter) c(qqn ou qqch.)';
   	$this->assertEquals($ptTg, $pt, '');
   }    

   public function testPrepareSenseForTransformation_FR_3 () {
	$sense = "/pousser qqn, /conseiller-à qqn, /recommander-à qqn";
   	$pt = VerbRoot::prepareSenseForTransformation_FR($sense);
   	$ptTg = 'v(/pousser) c(qqn), v(/conseiller) p(à) c(qqn), v(/recommander) p(à) c(qqn)';
   	$this->assertEquals($ptTg, $pt, '');
   	
   }
   
    
   public function testToTransitive_FR_1() {
   	$text = 'v(/transmettre) un message p(pour) c(qqn); v(/représenter) c(qqn ou qqch.)';
   	$trTg = 'transmettre un message pour lui/elle; le/la représenter';
   	$tr = VerbRoot::toTransitive_FR($text);
   	$this->assertEquals($trTg, $tr, '');    	
   }
   
   public function testToTransitive_FR_2() {
   	$text = 'v(/offrir, /donner, /apporter) c(qqch.) p(à) quelqu\'un (-mut)';
   	$trTg = 'le/la offrir, donner, apporter à quelqu\'un (-mut)';
   	$tr = VerbRoot::toTransitive_FR($text);
   	$this->assertEquals($trTg, $tr, '');    	
   }
   
   public function testToTransitive_FR_3() {
   	$text = 'v(/pousser) c(qqn), v(/conseiller) p(à) c(qqn), v(/recommander) p(à) c(qqn)';
   	$trTg = 'le/la pousser, lui conseiller, lui recommander';
   	$tr = VerbRoot::toTransitive_FR($text);
   	$this->assertEquals($trTg, $tr, '');
   }
   
   public function testToInransitive_EN_1() {
   	$text = 'v(to /convey, /send) a message p(for or to) c(s.o.); v(to /represent) c(s.o. or s.t.)';
   	$trTg = 'to convey, send a message for or to s.o.; to represent s.o. or s.t.';
   	$tr = VerbRoot::toIntransitive($text);
   	$this->assertEquals($trTg, $tr, '');
   }
    
    
   public function testToIntransitive_FR_1() {
   	$text = 'v(/pousser) c(qqn), v(/conseiller) p(à) c(qqn), v(/recommander) p(à) c(qqn)';
   	$trTg = 'pousser qqn, conseiller à qqn, recommander à qqn';
   	$tr = VerbRoot::toIntransitive($text);
   	$this->assertEquals($trTg, $tr, '');
   }
   
   public function testToPassive_FR_1() {
   	$text = 'v(/pousser) c(qqn), v(/conseiller) p(à) c(qqn), v(/recommander) p(à) c(qqn)';
   	$trTg = 'être poussé, se faire conseiller, se faire recommander';
   	$tr = VerbRoot::toPassive($text,'fr');
   	$this->assertEquals($trTg, $tr, '');
   }
    
   public function testToPassive_EN_1() {
   	$text = 'v(to /convey, /send) a message p(for or to) c(s.o.); v(to /represent) c(s.o. or s.t.)';
   	$trTg = 'to be conveyed, sent for or to a message; to be represented';
   	$tr = VerbRoot::toPassive($text,'en');
   	$this->assertEquals($trTg, $tr, '');
   }
  // [-R](1) to /offer, /give, /bring s.t. to someone (-mut) (2) to /take s.t. away forcibly from someone (-mit)
   
   public function testToPassive_EN_2() {
   	$text = 'v(to /offer, /give, /bring) c(s.t.) to someone (-mut)';
   	$trTg = 'to be offered, given, brought to someone (-mut)';
   	$tr = VerbRoot::toPassive($text,'en');
   	$this->assertEquals($trTg, $tr, '');
   }
    
    
/*
 * Fonctions utilitaires
 */
	
	function faireObjetRacine ( $valeurs )
	{
    	$champs = 'morpheme;variant;key;type;transitivity;nature;number;compositionRoot;plural;intransSuffix;transSuffix;antipassive;engMean;freMean;source;dialect;cf;END';
    	$champs = 'dbName;tableName;' . $champs;
    	$valeurs = 'Inuktitut;Racines;' . $valeurs;
    	return LinguisticObjects::makeLinguisticObject_from_FieldsAndValues($champs,$valeurs);
	}
}
?>
