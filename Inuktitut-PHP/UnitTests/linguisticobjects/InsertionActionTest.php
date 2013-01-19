<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/InsertionAction.php';
 
class InsertionActionTest extends PHPUnit_Framework_TestCase
{
	
	public function testNew() {
		$action = Insertion_Action::singleton('ra');
		$this->assertEquals('the insertion', $action->expression('en'), "Erreur");
		$this->assertEquals('ra',$action->insertedChars(),'Erreur sur les caractères insérés');
	}
	
    public function testExpression() {
    	$action = Insertion_Action::singleton('ra');
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'l\'insertion','Erreur : l\'expression retournée est fausse.');
    }
    
    public function testType() {
    	$action = Insertion_Action::singleton('ra');
    	$this->assertEquals(Action::INSERTION,$action->type(), "Erreur sur le type");
    }
    
    public function testCombine() {
    	$action = Insertion_Action::singleton('ra');
    	$res = $action->combine('imaa','aluk',null);
    	$att = 'imaaraaluk';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testExpressionResultat () {
    	$action = Insertion_Action::singleton('i');
    	$res = $action->resultExpression('t', 'aluk', null);
    	$att = '_t + aluk &rarr; _tialuk';
    	$this->assertEquals($att,$res,"Erreur");
    }
 
}
?>
