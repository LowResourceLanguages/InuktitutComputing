<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/Analyzer_MeaningOfMorpheme_Statement.php';
 
class Analyzer_MeaningOfMorpheme_StatementTest extends PHPUnit_Framework_TestCase
{
	
	public function test_parse_all__modes ()
	{
		$texte = '[-R]/tirer sur qqch. (animal)';
		$reste_attendu = '';
		$analyse_attendue = array(
			array('mode' => '-R', 'propositions' => array('/tirer sur qqch. (animal)')));
		$analyse = Analyzer_MeaningOfMorpheme_Statement::parse_all($texte);
		$this->assertTrue($analyse != false,"");
   		$this->assertEquals($analyse_attendue, $analyse, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_all__sens ()
	{
		$texte = '(1) premier sens (2) deuxième sens';
		$reste_attendu = '';
		$analyse_attendue = 
			array(
				array('nb' => '1', 'propositions' => array('premier sens')),
				array('nb' => 2, 'propositions' => array('deuxième sens')) );
		$analyse = Analyzer_MeaningOfMorpheme_Statement::parse_all($texte);
		$this->assertTrue($analyse != false,"");
   		$this->assertEquals($analyse_attendue, $analyse, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_all__propositions ()
	{
		$texte = 'une proposition; une autre proposition';
		$reste_attendu = '';
		$analyse_attendue = 
			array('une proposition' , 'une autre proposition');
		$analyse = Analyzer_MeaningOfMorpheme_Statement::parse_all($texte);
		$this->assertTrue($analyse != false,"");
   		$this->assertEquals($analyse_attendue, $analyse, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_all__vide()
	{
		$texte = '';
		$reste_attendu = '';
		$analyse_attendue = '';
		$analyse = Analyzer_MeaningOfMorpheme_Statement::parse_all($texte);
   		$this->assertEquals($analyse_attendue, $analyse, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_mode_statements()
    {
    	$texte = '[T]to strike the flint against the firestone causing sparks [P]to be made to spark by being struck with flint or flintstone [R]to spark';
    	$reste_attendu = '';
    	$analyse = Analyzer_MeaningOfMorpheme_Statement::parse_mode_statements($texte);
    	$analyse_attendue = 
    	    array(
    			array(	'mode' => 'T',
    					'propositions' => array('to strike the flint against the firestone causing sparks') ),
   				array(	'mode' => 'P',
    					'propositions' => array('to be made to spark by being struck with flint or flintstone') ),
    			array(	'mode' => 'R',
    					'propositions' => array('to spark') )
   				);
		$this->assertTrue($analyse != false,"");
   		$this->assertEquals($analyse_attendue, $analyse, "");
		$this->assertEquals($reste_attendu, $texte, "");
    }
	
    public function test_parse_one_mode_statement__1 ()
	{
		$texte = '[-R]/tirer sur qqch. (animal)';
		$reste_attendu = '';
		$attendu = array( 'mode' => '-R', 'propositions' => array('/tirer sur qqch. (animal)') );
		$enonce = Analyzer_MeaningOfMorpheme_Statement::parse_one_mode_statement($texte);
		$this->assertTrue($enonce != false,"");
		$this->assertEquals($attendu, $enonce, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
    public function test_parse_one_mode_statement__2 ()
	{
		$texte = 'to invoke the spirits';
		$reste_attendu = $texte;
		$enonce = Analyzer_MeaningOfMorpheme_Statement::parse_one_mode_statement($texte);
		$this->assertTrue($enonce == false,"");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
    public function test_parse_one_mode_statement__3 ()
	{
		$texte = '[-R](1) to /overtake and /kill s.t. (an animal or quarry) (2) to /catch up with s.o. or s.t.';
		$reste_attendu = '';
		$attendu = array( 
			'mode' => '-R', 
			'sens' => array(
					array ( 'nb' => '1',
							 'propositions' => array('to /overtake and /kill s.t. (an animal or quarry)') ),
					array ( 'nb' => '2',
							 'propositions' => array('to /catch up with s.o. or s.t.') )
					) );
		$enonce = Analyzer_MeaningOfMorpheme_Statement::parse_one_mode_statement($texte);
		$this->assertTrue($enonce != false,"");
		$this->assertEquals($attendu, $enonce, "");
		$this->assertEquals($reste_attendu, $texte, "");
			}

	public function test_parse_one_meaning ()
	{
		$texte = ' (1) premier sens (2) deuxième sens';
		$reste_attendu = '(2) deuxième sens';
		$sens_attendu = array( 
			'nb' => '1',
			'propositions' => array('premier sens'));
		$sens = Analyzer_MeaningOfMorpheme_Statement::parse_one_meaning($texte);
		$this->assertTrue($sens != false,"");
		$this->assertEquals($sens_attendu, $sens, "");
		$this->assertEquals($reste_attendu, $texte, "");
			
	}
	
	public function test_parse_meanings ()
	{
		$texte = ' (1) premier sens (2) deuxième sens';
		$reste_attendu = '';
		$sens_attendus = array(
			array( 
				'nb' => '1',
				'propositions' => array('premier sens')),
			array(
				'nb' => '2',
				'propositions' => array('deuxième sens'))
			);
		$sens = Analyzer_MeaningOfMorpheme_Statement::parse_meanings($texte);
		$this->assertTrue($sens != false,"");
		$this->assertEquals($sens_attendus, $sens, "");
		$this->assertEquals($reste_attendu, $texte, "");
			
	}
	
	public function test_meaning_index__true ()
	{
		$texte = '(3)';
		$res = Analyzer_MeaningOfMorpheme_Statement::meaning_index($texte);
		$this->assertTrue($res,"");
	}
	
	public function test_parse_meaning_index__false ()
	{
		$texte = ' (a) un sens';
		$no_attendu = '3';
		$reste_attendu = $texte;
		$no_sens = Analyzer_MeaningOfMorpheme_Statement::parse_meaning_index($texte);
		$this->assertTrue($no_sens == false,"Erreur : valeur retournée '$no_sens' n'est pas false");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_meaning_index__true ()
	{
		$texte = ' (3) un sens';
		$no_attendu = '3';
		$reste_attendu = ' un sens';
		$no_sens = Analyzer_MeaningOfMorpheme_Statement::parse_meaning_index($texte);
		$this->assertTrue($no_sens != false,"Erreur : valeur retournée= false");
		$this->assertEquals($no_attendu, $no_sens, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_semi_colon__true ()
	{
		$texte = '; reste du texte';
		$res = Analyzer_MeaningOfMorpheme_Statement::parse_semi_colon($texte);
		$res_attendu = ';';
		$texte_restant_attendu = ' reste du texte';
		$this->assertEquals($res_attendu,$res,"; en début de texte");
		$texte = '  ; reste du texte';
		$res = Analyzer_MeaningOfMorpheme_Statement::parse_semi_colon($texte);
		$res_attendu = ';';
		$texte_restant_attendu = ' reste du texte';
		$this->assertEquals($res_attendu,$res,"espaces devant ; en début de texte");
	}
	
	public function test_meaning_index__false ()
	{
		$texte = '(trans.: s.t.)';
		$res = Analyzer_MeaningOfMorpheme_Statement::meaning_index($texte);
		$this->assertFalse($res,"");
	}
	
	public function test_parse_propositions__false__1 ()
	{
		$texte = '[-R]abc';
		$texte_restant_attendu = $texte;
		$props = Analyzer_MeaningOfMorpheme_Statement::parse_propositions($texte);
		$this->assertFalse($props,"");
		$this->assertEquals($texte_restant_attendu, $texte, "");
	}
	
	public function test_parse_propositions__false__2 ()
	{
		$texte = '(1)abc';
		$texte_restant_attendu = $texte;
		$props = Analyzer_MeaningOfMorpheme_Statement::parse_propositions($texte);
		$this->assertFalse($props,"");
		$this->assertEquals($texte_restant_attendu, $texte, "");
	}
	
	public function test_parse_propositions__1prop__parant ()
	{
		$texte = '(abc) def';
		$texte_restant_attendu = '';
		$props_attendues = array($texte);
		$props = Analyzer_MeaningOfMorpheme_Statement::parse_propositions($texte);
		$this->assertEquals($props_attendues, $props, "");
	}
	
	public function test_parse_propositions__2props ()
	{
		$texte = 'abc; def';
		$texte_restant_attendu = '';
		$props_attendues = array('abc', 'def');
		$props = Analyzer_MeaningOfMorpheme_Statement::parse_propositions($texte);
		$this->assertEquals($props_attendues, $props, "");
	}
	
	public function test_parse_propositions__2props_suivi_de_sens ()
	{
		$texte = 'abc; def (2)';
		$texte_restant_attendu = '(2)';
		$props_attendues = array('abc', 'def');
		$props = Analyzer_MeaningOfMorpheme_Statement::parse_propositions($texte);
		$this->assertEquals($props_attendues, $props, "");
	}
	
	public function test_parse_propositions__2props_suivi_de_mode ()
	{
		$texte = 'abc; def [T]';
		$texte_restant_attendu = '[T]';
		$props_attendues = array('abc', 'def');
		$props = Analyzer_MeaningOfMorpheme_Statement::parse_propositions($texte);
		$this->assertEquals($props_attendues, $props, "");
	}
	
	public function test_parse_one_proposition__1 ()
	{
		$texte = 'une partie du sens';
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$attendu = 'une partie du sens';
		$reste_attendu = '';
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__2 ()
	{
		$texte = 'une partie du sens; une autre partie';
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$attendu = 'une partie du sens';
		$reste_attendu = '; une autre partie';
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__3 ()
	{
		$texte = 'une partie du sens (trans.: qqch.); une autre partie';
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$attendu = 'une partie du sens (trans.: qqch.)';
		$reste_attendu = '; une autre partie';
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__4 ()
	{
		$texte = 'une partie du sens (2) encore un sens (trans.: qqch.); une autre partie';
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$attendu = 'une partie du sens';
		$reste_attendu = '(2) encore un sens (trans.: qqch.); une autre partie';
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__5 ()
	{
		$texte = ' sens non réfléchi [R] sens réfléchi';
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$attendu = 'sens non réfléchi';
		$reste_attendu = '[R] sens réfléchi';
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__6 ()
	{
		$texte = '(souvent) abc';
		$attendu = $texte;
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$reste_attendu = '';
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__7 ()
	{
		$texte = 'to touch s.t., to put one\'s hand on s.t.';
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$attendu = 'to touch s.t., to put one\'s hand on s.t.';
		$reste_attendu = '';	
		$this->assertTrue($proposition != false,"");
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_one_proposition__vide ()
	{
		$texte = '';
		$attendu = $texte;
		$proposition = Analyzer_MeaningOfMorpheme_Statement::parse_one_proposition($texte);
		$reste_attendu = '';
		$this->assertEquals($attendu, $proposition, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_between_quotes__0 ()
	{
		$texte = '"les amis est un film';
		$texte_retourne_attendu = $texte;
		$texte_entre_guillemets = Analyzer_MeaningOfMorpheme_Statement::parse_between_quotes($texte,'"');
		$this->assertTrue($texte_entre_guillemets == false,"");
		$this->assertEquals($texte_retourne_attendu,$texte);
	}
	
	public function test_parse_between_quotes__1 ()
	{
		$texte = '"les amis" est un film...';
		$texte_entre_guillemets = Analyzer_MeaningOfMorpheme_Statement::parse_between_quotes($texte,'"');
		$attendu = '"les amis"';
		$reste_attendu = ' est un film...';	
		$this->assertTrue($texte_entre_guillemets != false,"");
		$this->assertEquals($attendu, $texte_entre_guillemets, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_between_quotes__2 ()
	{
		$texte = '"les ""grands"" amis" est un film...';
		$texte_entre_guillemets = Analyzer_MeaningOfMorpheme_Statement::parse_between_quotes($texte,'"');
		$attendu = '"les ""grands"" amis"';
		$reste_attendu = ' est un film...';	
		$this->assertTrue($texte_entre_guillemets != false,"");
		$this->assertEquals($attendu, $texte_entre_guillemets, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_between_quotes__3 ()
	{
		$texte = "'les 'grands' amis' est un film...";
		$texte_entre_guillemets = Analyzer_MeaningOfMorpheme_Statement::parse_between_quotes($texte,'"');
		$attendu = '';
		$reste_attendu = $texte;	
		$this->assertTrue($texte_entre_guillemets == false,"");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_between_parentheses__0 ()
	{
		$texte = '(trans.: s.t.; abc';
		$texte_retourne_attendu = $texte;
		$texte_entre_parantheses = Analyzer_MeaningOfMorpheme_Statement::parse_between_parentheses($texte);
		$this->assertTrue($texte_entre_parantheses == false,"");
		$this->assertEquals($texte_retourne_attendu,$texte);
	}
	
	public function test_parse_between_parentheses__1 ()
	{
		$texte = '(trans.: s.t.); abc';
		$texte_entre_parantheses = Analyzer_MeaningOfMorpheme_Statement::parse_between_parentheses($texte);
		$attendu = '(trans.: s.t.)';
		$reste_attendu = '; abc';
		$this->assertTrue($texte_entre_parantheses != false,"");
		$this->assertEquals($attendu, $texte_entre_parantheses, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	
	public function test_parse_between_parentheses__2 ()
	{
		$texte = '(trans.: s.t. (-mut (-mik))); abc';
		$texte_entre_parantheses = Analyzer_MeaningOfMorpheme_Statement::parse_between_parentheses($texte);
		$attendu = '(trans.: s.t. (-mut (-mik)))';
		$reste_attendu = '; abc';
		$this->assertTrue($texte_entre_parantheses != false,"");
		$this->assertEquals($attendu, $texte_entre_parantheses, "");
		$this->assertEquals($reste_attendu, $texte, "");
	}
	 
}
?>
