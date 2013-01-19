<?php
/*
 * Created on 2011-02-09
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 set_include_path('..');
 require 'lib/parseCSV.php';
 require_once "lib/log4php/Logger.php";
 Logger::configure('log4php.properties');
 
 
 $fichier = '../linguisticdata/TV2.csv';
 $fichier_modifie = '../linguisticdata/TV2mod.csv';
 $fi = fopen($fichier,'r');
// $fo = fopen("php://stdout",'w');
 $fo = fopen($fichier_modifie,'w');
 $ligne_champs = fgets($fi);
 $champs = explode(';',$ligne_champs);
 $posV = array_search('V-form',$champs);
 $posT = array_search('t-form',$champs);
 $posK= array_search('k-form',$champs);
 $posQ = array_search('q-form',$champs);
 $ligne_champs = preg_replace('/V-form;/','V-form;V-actions;',$ligne_champs);
 $ligne_champs = preg_replace('/t-form;/','t-form;t-actions;',$ligne_champs);
 $ligne_champs = preg_replace('/k-form;/','k-form;k-actions;',$ligne_champs);
 $ligne_champs = preg_replace('/q-form;/','q-form;q-actions;',$ligne_champs);
// echo $ligne_champs."\n";
 fputs($fo,$ligne_champs);
  $eof = 0;
 while ( ($ligne_donnees=fgets($fi)) ) {
// 	echo 'D---'.$ligne_donnees."\n";
 	$donnees = explode(';',$ligne_donnees);
 	$donnees = array_insert($donnees,$posV+1,'');
 	$donnees = array_insert($donnees,$posT+2,'');
 	$donnees = array_insert($donnees,$posK+3,'');
 	$donnees = array_insert($donnees,$posQ+4,'');
 	$nouvelle_ligne_donnees = implode(';',$donnees);
 	$champsValeurs = parseCSV($ligne_champs,$nouvelle_ligne_donnees);
 	$champsValeurs['V-actions'] = $champsValeurs['V-action1'];
 	$champsValeurs['t-actions'] = $champsValeurs['t-action1'];
 	$champsValeurs['k-actions'] = $champsValeurs['k-action1'];
 	$champsValeurs['q-actions'] = $champsValeurs['q-action1'];
// 	echo 'A---'.implode(';',$champsValeurs)."\n";
 	$morph_def = $champsValeurs['morpheme'];
 	$champsValeurs['V-form'] = trim($champsValeurs['morpheme'] . ' ' . $champsValeurs['V-form']);
 	$champsValeurs['t-form'] = preg_replace('/\*/',$morph_def,$champsValeurs['t-form']);
 	if ( $champsValeurs['t-form'] == '' )
 		$champsValeurs['t-form'] = $morph_def;
 	$champsValeurs['k-form'] = preg_replace('/\*/',$morph_def,$champsValeurs['k-form']);
 	if ( $champsValeurs['k-form'] == '' )
 		$champsValeurs['k-form'] = $morph_def;
 	$champsValeurs['q-form'] = preg_replace('/\*/',$morph_def,$champsValeurs['q-form']); 
 	if ( $champsValeurs['q-form'] == '' )
 		$champsValeurs['q-form'] = $morph_def;
// 	echo 'B---'.implode(';',$champsValeurs)."\n";
 	$valeurs = array_values($champsValeurs);
// 	echo 'C---'.implode(';',$valeurs)."\n";
 	fputcsv($fo,$valeurs,';');
//	exit;
 }
 fclose($fi);
 fclose($fo);

function array_insert($array,$pos,$val)
{
    $array2 = array_splice($array,$pos);
    $array[] = $val;
    $array = array_merge($array,$array2);
  
    return $array;
}

?>
