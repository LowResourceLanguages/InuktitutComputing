<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/SelfDecapitationAction.php';
require_once 'linguisticobjects/Action.php';
 
class SelfDecapitationActionTest extends PHPUnit_Framework_TestCase
{
	
	public function testNew() {
		$action = Self_Decapitation_Action::singleton();
		$this->assertEquals('the self decapitation', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Self_Decapitation_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'l\'autodécapitation','Erreur');
    }
    
    public function testType() {
    	$action = Self_Decapitation_Action::singleton();
    	$this->assertEquals($action->type(), Action::SELFDECAPITATION, "Erreur sur le type");
    }
    
    public function testCombine() {
    	$action = Self_Decapitation_Action::singleton();
    	$res = $action->combine('mamaa','it',null);
    	$att = 'mamaat';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testExpressionResultat () {
    	$action = Self_Decapitation_Action::singleton();
    	$res = $action->resultExpression('VV', 'it', null);
    	$att = '_VV + it &rarr; _VVt';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testAppliquerSurRadical () {
    	$action = Self_Decapitation_Action::singleton();
    	$res = $action->applyOnStem('mamaa','it');
    	$att = 'mamaa';
    	$this->assertEquals($att,$res,"");
    }
 
}
?>
