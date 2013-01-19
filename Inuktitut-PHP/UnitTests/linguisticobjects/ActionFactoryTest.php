<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/ActionFactory.php';
require_once 'linguisticobjects/Action.php';
 
class ActionFactoryTest extends PHPUnit_Framework_TestCase
{
	
    public function testMakeAction_neutre()
    {
    	$action = ActionFactory::make('n');
    	if ( $action == null )
    		$this->fail();
        $this->assertEquals(Action::NEUTRAL,$action->type(),"Erreur sur le type");
     }
 
    public function testMakeAction_insertion()
    {
        $action = ActionFactory::make('i(ra)');
        $this->assertTrue($action != null, "Erreur : l'action retournée est nulle.");
        $this->assertEquals(1,$action->type(),"Erreur sur le type");
        $this->assertEquals('ra',$action->insertedChars(),"Erreur sur les caractères insérés");
        $action = ActionFactory::make('i(ng)');
        $this->assertTrue($action != null, "Erreur : l'action retournée est nulle.");
        $this->assertEquals(1,$action->type(),"Erreur sur le type.");
        $this->assertEquals('ng',$action->insertedChars(),"Erreur sur les caractères insérés");
    }
 
}
?>
