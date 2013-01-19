<?php

require_once 'linguisticobjects/MeaningOfMorpheme.php';

class MeaningOfMorphemeTest extends PHPUnit_Framework_TestCase
{

public function set_up () {
}

////////////////////////////////////////////////////////////////
//// Documentation Tests
//// These tests illustrate how to use the class MeaningOfMorpheme
////////////////////////////////////////////////////////////////

public function ___test_reminder () {
	$this->fail("Il faut réactiver tous les tests dans MeaningOfMorpheme");
}

public function test_Voici_comment_on_cree_un_MeaningOfMorpheme ()
{
	$string = '/lancer qqch.';
	$sens1 = new MeaningOfMorpheme();
	$sens2 = new MeaningOfMorpheme(array('une proposition','une autre propositions'));
	$numero_de_sens = 2;
	$sens3 = new MeaningOfMorpheme(array('une proposition','une autre propositions'),
						$numero_de_sens);
	$mode = 'R';
	$sens4 = new MeaningOfMorpheme(array('une proposition','une autre propositions'),
						$numero_de_sens, $mode);
}

public function test_Voici_comment_on_recupere_les_propositions_d_un_sens ()
{
	$string = '/lancer qqch.';
	$sdm = new MeaningOfMorpheme(array($string));
	$propositions_sdm = $sdm->propositions();
}

public function test_Voici_comment_on_definit_les_propositions_d_un_sens ()
{
	$string = '/lancer qqch.';
	$sdm = new MeaningOfMorpheme(null);
	$sdm->propositions(array($string));
}

public function test_Voici_comment_on_recupere_le_numero_d_un_sens ()
{
	$string = '/lancer qqch.';
	$sdm = new MeaningOfMorpheme(array($string));
	$no_sdm = $sdm->nb();
}

public function test_Voici_comment_on_recupere_le_mode_d_un_sens ()
{
	$string = '/lancer qqch.';
	$sdm = new MeaningOfMorpheme(array($string));
	$mode_sdm = $sdm->mode();
}

//
// Note: in the reminder of this file, $self->{morpheme}
// is assumed to be an instance of Morpheme, build
// as above.
//


////////////////////////////////////////////////////////////////
//// Internal Unit Tests
//// These tests check the internal workings of the class
////////////////////////////////////////////////////////////////

public function test__construct ()
{
	$sdm = new MeaningOfMorpheme();
	$att = array();
	$this->assertEquals($att, $sdm->propositions(),"Erreur - sens créé sans argument");
	$sdm = new MeaningOfMorpheme(null);
	$this->assertEquals($att, $sdm->propositions(),"Erreur - sens créé avec 'null'");
}

public function test_numero ()
{
	$string = '/lancer qqch.';
	$sdm = new MeaningOfMorpheme(array($string));
	$no_sdm = $sdm->nb();
	$this->assertEquals(1, $no_sdm, "");
}

public function test_mode ()
{
	$string = '/lancer qqch.';
	$sdm = new MeaningOfMorpheme(array($string));
	$mode_sdm = $sdm->mode();
	$this->assertEquals(null, $mode_sdm, "");
}

public function test_propositions ()
{
	$sdm = new MeaningOfMorpheme();
	$props = array('abc','def');
	$sdm->propositions($props);
	$this->assertEquals(count($props), count($sdm->propositions()), "");
}

public function test_chaine ()
{
	$props = array('abc','def');
	$sdm = new MeaningOfMorpheme($props);
	$string = $sdm->toString();
	$att = 'abc; def';
	$this->assertEquals($att, $string, "");
}


////////////////////////////////////////////////////////////////
//// Helper methods
//// Assertions, etc...
////////////////////////////////////////////////////////////////

}
?>