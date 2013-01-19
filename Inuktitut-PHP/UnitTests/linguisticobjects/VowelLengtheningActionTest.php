<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/VowelLengtheningAction.php';
require_once 'linguisticobjects/Action.php';
 
class VowelLengtheningActionTest extends PHPUnit_Framework_TestCase
{
	
	public function test_singleton() {
		$action = Vowel_Lengthening_Action::singleton();
		$this->assertEquals('the lengthening', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Vowel_Lengthening_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'l\'allongement','Erreur : l\'expression retournée est fausse.');
    }
    
    public function testType() {
    	$action = Vowel_Lengthening_Action::singleton();
    	$this->assertEquals(Action::VOWELLENGTHENING, $action->type(), "Erreur sur le type");
    }
    
    public function testCombine() {
    	$action = Vowel_Lengthening_Action::singleton();
    	$res = $action->combine('inu','k',null);
    	$att = 'inuuk';
    	$this->assertEquals($att,$res,"Erreur");

    	$res = $action->combine('inuu','k',null);
    	$att = 'inuuk';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testExpressionResultat () {
    	$action = Vowel_Lengthening_Action::singleton();
    	$res = $action->resultExpression('V', 'k', null);
    	$att = '_V + k &rarr; _VVk';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testAppliquerSurRadical () {
    	$action = Vowel_Lengthening_Action::singleton();
    	$res = $action->applyOnStem('inu','k');
    	$att = 'inuu';
    	$this->assertEquals($att,$res,"Erreur");
    }
 
}
?>
