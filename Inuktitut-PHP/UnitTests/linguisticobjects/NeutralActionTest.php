<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/NeutralAction.php'; 

class NeutralActionTest extends PHPUnit_Framework_TestCase
{
		
	public function test_singleton() {
		$action = Neutral_Action::singleton();
		$this->assertEquals('the neutral action', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Neutral_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'l\'action neutre','Erreur : l\'expression retournée est fausse.');
    }
    
    public function testType() {
    	$action = Neutral_Action::singleton();
    	$this->assertEquals(Action::NEUTRAL,$action->type(), "Erreur sur le type.");
    }
    
 
}
?>
