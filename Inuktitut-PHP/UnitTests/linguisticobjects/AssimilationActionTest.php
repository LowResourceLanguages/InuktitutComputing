<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/AssimilationAction.php'; 

class AssimilationActionTest extends PHPUnit_Framework_TestCase
{
		
	public function test_singleton() {
		$action = Assimilation_Action::singleton();
		$this->assertEquals('the assimilation', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Assimilation_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'l\'assimilation','Erreur : l\'expression retournée est fausse.');
    }
    
    public function testType() {
    	$action = Assimilation_Action::singleton();
    	$this->assertEquals(Action::ASSIMILATION,$action->type(), "Erreur sur le type.");
    }
    
 
}
?>
