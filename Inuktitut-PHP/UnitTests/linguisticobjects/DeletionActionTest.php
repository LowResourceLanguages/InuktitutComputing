<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/DeletionAction.php';
 
class DeletionActionTest extends PHPUnit_Framework_TestCase
{
	
	public function testNew() {
		$action = Deletion_Action::singleton();
		$this->assertEquals('the deletion', $action->expression('en'), "Erreur");
	}
	
    public function testClone()
    {
        $action = Deletion_Action::singleton();
        $nouvelle_action = clone $action;
        $this->assertEquals(Action::DELETION,$nouvelle_action->type(),"Erreur : pas le bon type");
    }
    
    public function testExpression() {
    	$action = Deletion_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'la suppression','Erreur.');
    }
    
    public function testType() {
    	$action = Deletion_Action::singleton();
    	$this->assertEquals(Action::DELETION,$action->type(), "Erreur");
    }
    
 
}
?>
