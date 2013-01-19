<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/NasalizationAction.php';
 
class NasalizationActionTest extends PHPUnit_Framework_TestCase
{
	
	public function testNew() {
		$action = Nasalization_Action::singleton();
		$this->assertEquals('the nasalization', $action->expression('en'), "Erreur");
	}
	
    public function testExpression() {
    	$action = Nasalization_Action::singleton();
    	$expr = $action->expression('fr');
    	$this->assertEquals($expr,'la nasalisation','');
    }
    
    public function testType() {
    	$action = Nasalization_Action::singleton();
    	$this->assertEquals($action->type(), 5, "Erreur sur le type");
    }
    
    public function testCombine() {
    	$action = Nasalization_Action::singleton();
    	$res = $action->combine('sinik','miaq',null);
    	$att = 'siningmiaq';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testExpressionResultat () {
    	$action = Nasalization_Action::singleton();
    	$res = $action->resultExpression('k', 'miaq', null);
    	$att = '_k + miaq &rarr; _ngmiaq';
    	$this->assertEquals($att,$res,"Erreur");
    }
    
    public function testAppliquerSur () {
    	$action = Nasalization_Action::singleton();
    	$res = $action->applyOnStem('sinik',null);
    	$att = 'sining';
    	$this->assertEquals($att,$res,"Erreur");
    }
 
}
?>
