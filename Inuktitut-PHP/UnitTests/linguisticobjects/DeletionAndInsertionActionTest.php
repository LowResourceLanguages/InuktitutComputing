<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/DeletionAndInsertionAction.php';
 
class DeletionAndInsertionActionTest extends PHPUnit_Framework_TestCase
{
	
	public function test_singleton() {
		$action = Deletion_and_Insertion_Action::singleton('jj');
		$this->assertEquals('the deletion', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Deletion_and_Insertion_Action::singleton('jj');
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'la suppression','Erreur');
    }
    
    public function testType() {
    	$action = Deletion_and_Insertion_Action::singleton('jj');
    	$this->assertEquals(Action::DELETIONINSERTION,$action->type(), "Erreur");
    }
    
    public function testInsertion() {
    	$action = Deletion_and_Insertion_Action::singleton('jj');
    	$this->assertEquals('jj',$action->insertedChars(),"Erreur");
    }
 
    public function testCombine() {
    	$action = Deletion_and_Insertion_Action::singleton('jj');
    	$res = $action->combine('akit','uti',null);
    	$att = 'akijjuti';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testExpressionResultat () {
    	$action = Deletion_and_Insertion_Action::singleton('jj');
    	$res = $action->resultExpression('t', 'uti', null);
    	$att = '_t + uti &rarr; _jjuti';
    	$this->assertEquals($att,$res,"Erreur");
    }
 
}
?>
