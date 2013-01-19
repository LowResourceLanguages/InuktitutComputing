<?php
/*
 * Created on 2011-01-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'linguisticobjects/Affix.php';
require_once 'linguisticobjects/LinguisticObjects.php';
 
class AffixTest extends PHPUnit_Framework_TestCase
{
	
    public function test_expressionOfComposition()
    {
    	$ligne_suffixe = 'innaq;2;sv;vv;m;n;;;;innaq;n-i(ng);n;i(ng);innaq;s-i(ng);s;i(ng);innaq;s-i(ng);s;i(ng);innaq;s-i(ng);s;i(ng);to do nothing but;;;;;;;;';
    	$ligne_champs = 'morpheme;key;type;function;position;transitivity;nature;plural;antipassive;V-form;V-actions;V-action1;V-action2;t-form;t-actions;t-action1;t-action2;k-form;k-actions;k-action1;k-action2;q-form;q-actions;q-action1;q-action2;engMean;freMean;condPrec;condPrecTrans;condOnNext;sources;composition;dialect;mobility';
    	$suffixe = LinguisticObjects::makeLinguisticObject_from_FieldsAndValues($ligne_champs,$ligne_suffixe);
    	$stem = '___VV';    	
    	$res = $suffixe->expressionOfComposition($stem);
    	$att = array('___VV + innaq > ___VVnginnaq');
        $this->assertEquals($att, $res, "Erreur");

    	$stem = '___k';
    	$res = $suffixe->expressionOfComposition($stem);
    	$att = array('___k + innaq > ___innaq');
        $this->assertEquals($att, $res, "Erreur");

    	$stem = '___VVk';
    	$res = $suffixe->expressionOfComposition($stem);
    	$att = array('___VVk + innaq > ___VVnginnaq');
        $this->assertEquals($att, $res, "Erreur");
    }
  
}
?>

