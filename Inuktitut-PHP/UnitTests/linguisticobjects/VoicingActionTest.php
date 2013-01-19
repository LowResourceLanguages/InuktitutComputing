<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/VoicingAction.php';
 
class VoicingActionTest extends PHPUnit_Framework_TestCase
{
	
	public function testNew() {
		$action = Voicing_Action::singleton();
		$this->assertEquals('the voicing', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Voicing_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'la sonorisation','Erreur');
    }
    
    public function testType() {
    	$action = Voicing_Action::singleton();
    	$this->assertEquals($action->type(), 4, "Erreur");
    }
    
    public function testCombine() {
    	$action = Voicing_Action::singleton();
    	$res = $action->combine('sinik','miaq',null);
    	$att = 'sinigmiaq';
    	$this->assertEquals($att,$res,"");
    }
    
    public function testExpressionResultat () {
    	$action = Voicing_Action::singleton();
    	$res = $action->resultExpression('k', 'miaq', null);
    	$att = '_k + miaq &rarr; _gmiaq';
    	$this->assertEquals($att,$res,"");
    }
 
}
?>
