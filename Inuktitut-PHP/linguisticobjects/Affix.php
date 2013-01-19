<?php

// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Affix.php
//
// Type/File type:		code php
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation:	
//
// Description: Classe Affix
//
// -----------------------------------------------------------------------


require_once 'lib/log4php/Logger.php';

require_once 'linguisticobjects/Action.php';
require_once 'linguisticobjects/ActionFactory.php';
require_once 'linguisticobjects/Behaviour.php';
require_once 'linguisticobjects/Morpheme.php';

abstract class Affix extends Morpheme {
	//
	protected $type = null;
	protected $id = null;
	protected $preCondition = null;
	protected $position = null;
	protected $vform = array();
	protected $vaction1 = array();
	protected $vaction2 = array();
	protected $tform = array();
	protected $taction1 = array();
	protected $taction2 = array();
	protected $kform = array();
	protected $kaction1 = array();
	protected $kaction2 = array();
	protected $qform = array();
	protected $qaction1 = array();
	protected $qaction2 = array();
	protected $behaviours = array('V' => null, 't' => null, 'k' => null, 'q' => null);
	protected $dialect = '';
	protected $sources = array();
	//
	
	//---------------------------------------------------------------------------------------------------------
	public abstract function getTransitivityConstraint(); //
//	public abstract function addToHash($key, $obj); //
	public abstract function agreesWithTransitivity($trans); //	
	//---------------------------------------------------------------------------------------------------------

	//---------------------------------------------------------------------------------------------------------
	public function getOriginalMorpheme() {
	    return $this->morpheme;
	}
	
	public function sources() {
		return $this->sources;
	}
	
	public function behaviours() {
		return $this->behaviours;
	}
	
	public function behaviours_for_context($context) {
		return $this->behaviours[$context];
	}
	
    public function addPrecConstraint($cond) {
        if ($this->preCondition==null) {
            $this->preCondition = $cond;
        }
        else {
            $this->preCondition = new Condition_And($cond);
        }
    }
    
	//---------------------------------------------------------------------------------------------------------
	
    function surfaceForms($context)
    {
    	$logger = Logger::getLogger('Affix.surfaceForms');
    	$forms = array();
    	$logger->debug("$context: ".print_r($this->behaviours[$context],true));
    	foreach ($this->behaviours[$context] as $a_behaviour)
    	{
    		array_push($forms, $a_behaviour->formOfAffix());
    	}
    	$logger->debug("\$forms: ".print_r($forms,true));
    	return $forms;
    }

//    function surfaceForms($context) {
//        if ($context=='V' || $context=='a' || $context=='i' || $context=='u')
//            return $this->vform;
//        elseif ($context=='t')
//            return $this->tform;
//        elseif ($context=='k')
//            return $this->kform;
//        elseif ($context=='q')
//            return $this->qform;
//        else
//            return null;
//    }
    
    function actions1($context)
    {
    	return $this->_actions($context, 1);
    }
    
//    function actions1($context) {
//    	if ($context=='V' || $context=='a' || $context=='i' || $context=='u')
//            return $this->vaction1;
//        elseif ($context=='t')
//            return $this->taction1;
//        elseif ($context=='k')
//            return $this->kaction1;
//        elseif ($context=='q')
//            return $this->qaction1;
//		else
//			return null;
//    }
    
    function actions2($context)
    {
    	return $this->_actions($context, 2);
    }
    
    function _actions($context, $type)
    {
    	$logger = Logger::getLogger('Affix.actions');
    	$actions = array();
    	$logger->debug("$context: ".print_r($this->behaviours[$context],true));
    	foreach ($this->behaviours[$context] as $a_behaviour)
    	{
    		array_push($actions, 
    			$type==1 ? $a_behaviour->actionOnStem() : $a_behaviour->actionVV());
    	}
    	$logger->debug("\$actions: ".print_r($actions,true));
    	return $actions;
    }

    //    function actions2($context) {
//        if ($context=='V' || $context=='a' || $context=='i' || $context=='u')
//            return $this->vaction2;
//        elseif ($context=='t')
//            return $this->taction2;
//        elseif ($context=='k')
//            return $this->kaction2;
//        elseif ($context=='q')
//            return $this->qaction2;
//		else
//			return null;
//    }
    
    public function defineBehaviours ($fieldsAndValues) {
    	// Développement des diverses formesAffixes associées aux 4 contextes
		// voyelle, t, k et q et à leurs actions.
		$logger = Logger::getLogger('Affix.defineBehaviours');
		// Après Voyelle
		$form = $fieldsAndValues['V-form'];
//		$act1 = $fieldsAndValues['V-action1'];
//		$act2 = $fieldsAndValues['V-action2'];
		$actions = $fieldsAndValues['V-actions'];
		$behaviours = $this->makeBehaviours('V',$actions,$form);
		$this->behaviours['V'] = $behaviours;
//		$this->faireFormesActions('V', $this->morpheme, $form, $act1, $act2); 
		$logger->debug("behaviours pour V : ".count($this->behaviours['V']));
		
		// Après 't'
		$form = $fieldsAndValues['t-form'];
//		$act1 = $fieldsAndValues['t-action1'];
//		$act2 = $fieldsAndValues['t-action2'];
		$actions = $fieldsAndValues['t-actions'];
		$behaviours = $this->makeBehaviours('t',$actions,$form);
		$this->behaviours['t'] = $behaviours;
//		$this->faireFormesActions('t', $this->morpheme, $form, $act1, $act2);

		// Après 'k'
		$form = $fieldsAndValues['k-form'];
//		$act1 = $fieldsAndValues['k-action1'];
//		$act2 = $fieldsAndValues['k-action2'];
		$actions = $fieldsAndValues['k-actions'];
		$behaviours = $this->makeBehaviours('k',$actions,$form);
		$this->behaviours['k'] = $behaviours;
//		$this->faireFormesActions('k', $this->morpheme, $form, $act1, $act2);

		// Après 'q'
		$form = $fieldsAndValues['q-form'];
//		$act1 = $fieldsAndValues['q-action1'];
//		$act2 = $fieldsAndValues['q-action2'];
		$actions = $fieldsAndValues['q-actions'];
		$behaviours = $this->makeBehaviours('q',$actions,$form);
		$this->behaviours['q'] = $behaviours;
//		$this->faireFormesActions('q', $this->morpheme, $form, $act1, $act2);
    }
    
    public function makeBehaviours ($context,$actions,$forms) {
    	return Behaviour::makeBehaviours($context,$actions,$forms);
    }
    
	// Les chaînes 'formesAlternatives', 'actions1' et 'action2'
	// contiennent le contenu des champs X-forme, X-action1 et X-action2
	// des enregistrements dans la base de données.  Ces champs/chaînes
	// peuvent identifier plus d'une forme+action. 
	//
	// En fait, les possibilités sont les suivantes:
	// nb. actions1 < nb. formesAffixes >>> (nb. actions1 = 1) l'action est appliquée à chaque forme
	// nb. actions1 = nb. formesAffixes >>> une action par forme
	// nb. actions1 > nb. formesAffixes >>> (nb. formesAffixes = 1) chaque action est appliquée à la forme
	//
	// nb. formesAffixes = 0 >>> la forme est la même que la forme de 'morpheme'
	//
    // Si forme = '-' et action1 = '-', cela signifie qu'il n'y a pas de forme dans ce contexte.
    //
	// Quant à action2, elle doit soit être nulle, soit correspondre en nombre à action1.
	// Dans ce cas,  la valeur '-' signifie une action nulle, i.e. aucune action.
	//
	// Une forme peut prendre les valeurs suivantes:
	//   identificateur
	//   "*" : même valeur que la forme du champ 'morpheme'.
    //   "-" : la forme n'existe pas dans ce contexte
	//
	// Cette fonction construit un objet de classe FormeDeAffixe pour
	// chaque groupe forme+action1+action2 identifié.

    // TODO: The following function is no longer used. To be deleted.
// 	public function faireFormesActions(
// 		$context,
// 		$morpheme,
// 		$forms_alternatives,
// 		$actions1,
// 		$actions2) {
// 	    $logger = Logger::getLogger('Affix.faireFormesActions');
// 	 	$logger->debug('entrée');
//         $forms = array();
//         $a1 = array();
//         $a2 = array();

// 	    $toutes_formes = null;
        
//         if ($actions1=='-') {
//                 // Si le champ 'X-action1' est vide,
//                 // cela signifie qu'il n'y a pas de forme dans le contexte X.
//                 $toutes_formes = "";
//                 $actions1 = "";
//                 $actions2 = "";
//         } elseif ($forms_alternatives==null) {
//                 // Si le champ 'X-forme' est vide,
//                 // cela signifie que la forme est celle du champ 'morpheme'.
//                 $toutes_formes = $morpheme;
//         } else {
//     	    // Transformer les * en la valeur de 'morpheme'
//     	    $toutes_formes = str_replace('*',$morpheme,$forms_alternatives);
//         }
        
// //        StringTokenizer stf = new StringTokenizer($toutes_formes);
//         $st_formes = explode(' ',$toutes_formes);
//         // nb. de formesAffixes
//         $nb_formes = count($st_formes);
		
// //		StringTokenizer st1 = new StringTokenizer(action1);
// 		$st_actions1 = explode(' ',$actions1);
// 		// nb. d'actions1
// 		$nba1 = count($st_actions1);
		
// 		if ($actions2==null)
// 		    $actions2 = '';
// //		StringTokenizer st2 = new StringTokenizer(action2);
// 		$st_actions2 = explode(' ',$actions2);
// 		$nba2 = count($st_actions2);
		
// 		if ($nba1 > $nb_formes && $nb_formes==1) {
//             /*
//              * S'il y a plus d'action1 que de formes, le nb. de formes devrait être égal � 1
//              * et on ajoute dans la liste de formes la valeur initiale autant de fois qu'il le
//              * faut pour égaliser le nombre d'actions.
//              */
//             $valeurInitiale = $toutes_formes;
// 			for ($j = $nb_formes; $j < $nba1; $j++)
// 				$toutes_formes .= " " . $valeurInitiale;
// 			$st_formes = explode(' ',$toutes_formes);
// 			$nb_formes = count($st_formes);
// 		} elseif ($nb_formes > $nba1 && $nba1==1) {
//             /*
//              * S'il y a plus de formes que d'action1, le nb. d'action1 devrait être égal �
//              * 1 et on ajoute dans la liste d'action1 la valeur initiale autant de fois qu'
//              * il le faut pour égaliser le nombre de formes.
//              */
// 		    $act1 = $actions1;
// 		    for ($j = $nba1; $j < $nb_formes; $j++)
// 		        $actions1 .= " " + $act1;
//             $st_actions1 = explode(' ',$actions1);
//             $nba1 = count($st_actions1);
//             /*
//              * Si le nombre d'action2 est aussi égal à 1 (il devrait être égal à 0 ou à 1),
//              * on multiplie aussi.
//              */
// 		    if ($nba2==1) {
// 			    $act2 = $actions2;
// 			    for ($j = $nba2; $j < $nb_formes; $j++)
// 			        $actions2 .=  " " . $act2;		        
// 			    $st_actions2 = explode(' ',$actions2);
// 			    $nba2 = count($st_actions2);
// 		    }
// 		}
		
// 		// En principe, ici, toutesformes, action1 et action2 devraient
// 		// être en phase et contenir un nombre égal d'éléments.

// 		$formsV = array();
// 		$a1V = array();
// 		$a2V = array();

// 		for ($i=0; $i<count($st_formes); $i++) {
// 			$f = $st_formes[$i];
// 			array_push($formsV, $f);
// 			// La première fois, st1.hasMoreTokens() est vrai, puisque
// 			// action1 doit absolument avoir une valeur dans la base de
// 			// données: act1 y trouve sa valeur...
// 			$act1 = $st_actions1[$i];
// 			// ...sinon, act1 conserve la valeur pr�c�dente, obtenue
// 			// au premier tour.
// //            Pattern pif1 = Pattern.compile("^if\\((.+),([a-z]+),([a-z]+)\\)$");
// //            Matcher mif1 = pif1.matcher(act1);
//             $matchF1 = preg_match("/^if\\((.+),([a-z]+),([a-z]+)\\)$/",$act1,$matchesF1);
// //            Pattern pif2 = Pattern.compile("^if\\((.+),([a-z]+)\\)$");
// //            Matcher mif2 = pif2.matcher(act1);
//             $matchF2 = preg_match("/^if\\((.+),([a-z]+)\\)$/",$act1,$matchesF2);
//             if ($matchF1) {
//                 $condition = str_replace('|',' ',$matchesF1[1]);
//                 $actPos = $matchesF1[2];
//                 $actNeg = $matchesF1[3];
//                 array_push($a1V, ActionFactory::make($actPos."si(".$condition.")"));
//                 if ($actNeg != null) {
//                     array_push($formsV,$f);
//                     array_push($a1V, ActionFactory::make($actNeg."si(!(".$condition."))"));
//                 }
//                 if ($actions2 != '') {
//                     $ac2 = $st_actions2[$i];
//                     array_push($a2V, ActionFactory::make($ac2));
//                     array_push($a2V, ActionFactory::make($ac2));
//                 } else {
//                     array_push($a2V, ActionFactory::make(null));
//                     array_push($a2V, ActionFactory::make(null));
//                 }
//             } elseif ($matchF2) {
//                 $condition = str_replace('|',' ',$matchesF1[2]);
//                 $actPos = $matchesF2[2];
//                 array_push($a1V, ActionFactory::make($actPos."si(".$condition.")"));
//                 if ($actions2 != '') {
//                     $ac2 = $st_actions2[$i];
//                     array_push($a2V, ActionFactory::make($ac2));
//                     array_push($a2V, ActionFactory::make($ac2));
//                 } else {
//                     array_push($a2V, ActionFactory::make(null));
//                     array_push($a2V, ActionFactory::make(null));
//                 }
//             } else {
// 			    array_push($a1V, ActionFactory::make($act1));
// 			    if ($actions2 != '')
// 			        array_push($a2V, ActionFactory::make($st_actions2[$i]));
// 			    else
// 			        array_push($a2V, ActionFactory::make(null));
// 			}
// 		}
		

// 		// Enregistrer dans l'object affixe
// 		if ($context=='V') {
// 			$this->vform = $formsV;
// 			$this->vaction1 = $a1V;
// 			$this->vaction2 = $a2V;
// 		} elseif ($context=='t') {
// 			$this->tform = $formsV;
// 			$this->taction1 = $a1V;
// 			$this->taction2 = $a2V;
// 		} elseif ($context=='k') {
// 			$this->kform = $formsV;
// 			$this->kaction1 = $a1V;
// 			$this->kaction2 = $a2V;
// 		} elseif ($context=='q') {
// 			$this->qform = $formsV;
// 			$this->qaction1 = $a1V;
// 			$this->qaction2 = $a2V;
// 		}
		
// 		/*
// 		 * Mis hors-compilation parce pas utile, mais conservé pour usage futur
// 		 */
// //		for ($j=0; $j<count($forms); $j++) {
// //		    fillFinalRadInitAffHashSet($context,$forms[$j],$a1[$j],$a2[$j]);
// //		}
// 	}
	
//	void fillFinalRadInitAffHashSet(String contexte, String forme, Action a1, Action a2) {
//	    String f = Orthography.orthographeSimplifieeLat(forme);
//	    String finalRadInitAff[] = a1.finalRadInitAff(contexte,f);
//	    for (int i=0; i<finalRadInitAff.length; i++)
//	        Donnees.finalRadInitAffHashSet.add(finalRadInitAff[i]);
//	}


	// ----------------------------
//	String showData() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("vforme= [");
//		int i;
//		for (i = 0; i < vforme.length - 1; i++)
//			sb.append(vforme[i] + ",");
//		sb.append(vforme[i] + "]\n");
//		sb.append("vaction1= [");
//		for (i = 0; i < vaction1.length - 1; i++)
//			sb.append(vaction1[i] + ",");
//		sb.append(vaction1[i] + "]\n");
//		sb.append("vaction2= [");
//		for (i = 0; i < vaction2.length - 1; i++)
//			sb.append(vaction2[i] + ",");
//		sb.append(vaction2[i] + "]\n");
//		sb.append("tforme= [");
//		for (i = 0; i < tforme.length - 1; i++)
//			sb.append(tforme[i] + ",");
//		sb.append(tforme[i] + "]\n");
//		sb.append("taction1= [");
//		for (i = 0; i < taction1.length - 1; i++)
//			sb.append(taction1[i] + ",");
//		sb.append(taction1[i] + "]\n");
//		sb.append("taction2= [");
//		for (i = 0; i < taction2.length - 1; i++)
//			sb.append(taction2[i] + ",");
//		sb.append(taction2[i] + "]\n");
//		sb.append("kforme= [");
//		for (i = 0; i < kforme.length - 1; i++)
//			sb.append(kforme[i] + ",");
//		sb.append(kforme[i] + "]\n");
//		sb.append("kaction1= [");
//		for (i = 0; i < kaction1.length - 1; i++)
//			sb.append(kaction1[i] + ",");
//		sb.append(kaction1[i] + "]\n");
//		sb.append("kaction2= [");
//		for (i = 0; i < kaction2.length - 1; i++)
//			sb.append(kaction2[i] + ",");
//		sb.append(kaction2[i] + "]\n");
//		sb.append("qforme= [");
//		for (i = 0; i < qforme.length - 1; i++)
//			sb.append(qforme[i] + ",");
//		sb.append(qforme[i] + "]\n");
//		sb.append("qaction1= [");
//		for (i = 0; i < qaction1.length - 1; i++)
//			sb.append(qaction1[i] + ",");
//		sb.append(qaction1[i] + "]\n");
//		sb.append("qaction2= [");
//		for (i = 0; i < qaction2.length - 1; i++)
//			sb.append(qaction2[i] + ",");
//		sb.append(qaction2[i] + "]\n");
//
//		return sb.toString();
//	}
	
	public function expressionOfComposition ($stem_context) {
		// Ex. : ___VV + innaq > ___VVnginnaq
		// Ex. : katimajiu + innaq > katimajiunginnaq 
		// innaq : dans le contexte 'V' : action1 = n, action2 = i(ng)
		// Appliquer l'action1 au radical : katimajiu
		// Appliquer l'action2 au résultat de l'action1 : katimajiunginnaq
 		$logger = Logger::getLogger('Affix.expressionOfComposition');
 		$logger->debug("\$stem_context= '$stem_context'");
 		$new_stem = '';
 		$expressions = array();
 		$final = $stem_context[strlen($stem_context)-1];
 		if ( Roman::isVowel($final) )
 			$final = 'V';
 		$logger->debug("\$final= '$final'");
 		$behaviours_for_context = $this->behaviours_for_context($final);
 		$logger->debug("behaviours pour '$final' : ".count($behaviours_for_context));
 		foreach ($behaviours_for_context as $a_behaviour) {
			$new_stem = $a_behaviour->actionOnStem()
				->applyOnStem($stem_context,$a_behaviour->formOfAffix());
			$logger->debug("\$new_stem= '$new_stem'");
			if ( strlen($new_stem) > 2
				&& ( $new_stem[strlen($new_stem)-1]=='V' || Roman::isVowel($new_stem[strlen($new_stem)-1]) ) 
				&& ( $new_stem[strlen($new_stem)-2]=='V' ) || Roman::isVowel($new_stem[strlen($new_stem)-2]) )
				$new_stem = $a_behaviour->actionVV()->applyOnStem($new_stem,$a_behaviour->formOfAffix());
			$logger->debug("\$new_stem (après actionVV)= '$new_stem'");			
			$new_stem .= $a_behaviour->formOfAffix();
			$expression = $stem_context . ' + ' . $a_behaviour->formOfAffix() . ' > ' . $new_stem;
 			array_push($expressions,$expression);
 		}
		return $expressions;
	}
	
	public function addToStem ($stem) {
		// Appliquer l'action1 et l'action2.
		// D'abord appliquer l'action1 :
		$logger = Logger::getLogger('Affix.addToStem');
 		$expressions = array();
 		$final = $stem[strlen($stem)-1];
 		if ( Roman::isVowel($final) )
 			$final = 'V';
 		$behaviours_for_context = $this->behaviours_for_context($final);
 		$logger->debug("behaviours pour '$final' : ".count($behaviours_for_context));
 		foreach ($behaviours_for_context as $a_behaviour) {
			$new_stem = $a_behaviour->applyOnStem($stem);
			$logger->debug("\$new_stem= '$new_stem'");
 			array_push($expressions,$new_stem);
 		}
		return $expressions;
	}

}
?>