<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/SpecificDeletionAction.php';
 
class SpecificDeletionActionTest extends PHPUnit_Framework_TestCase
{
	
	public function testNew() {
		$action = Specific_Deletion_Action::singleton('a');
		$this->assertEquals('the deletion', $action->expression('en'), "Erreur");
		$this->assertEquals('a',$action->deleted(),'Erreur');
	}
	
    public function testClone()
    {
        $action = Specific_Deletion_Action::singleton('a');
        $nouvelle_action = clone $action;
        $this->assertEquals(Action::SPECIFICDELETION,$nouvelle_action->type(),"Erreur");
    }
    
    public function testExpression() {
    	$action = Specific_Deletion_Action::singleton('ra');
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'la suppression','Erreur');
    }
    
    public function testType() {
    	$action = Specific_Deletion_Action::singleton('a');
    	$this->assertEquals(Action::SPECIFICDELETION,$action->type(), "Erreur");
    }
    
    public function testCombine() {
    	$action = Specific_Deletion_Action::singleton('a');
    	$res = $action->combine('imaa','aluk',null);
    	$this->assertTrue($res==null,"Erreur");
    }
    
    public function testExpressionResultat () {
    	$action = Specific_Deletion_Action::singleton('i');
    	$res = $action->resultExpression('t', 'aluk', null);
    	$this->assertTrue($res==null,"Erreur");
    }
 
}
?>
