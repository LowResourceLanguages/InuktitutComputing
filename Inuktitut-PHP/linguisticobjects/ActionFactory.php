<?php
/*
 * Created on 2011-02-01
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once 'lib/log4php/Logger.php';
require_once 'lib/StringUtils.php';
require_once 'linguisticobjects/Action.php';
require_once 'linguisticobjects/UnknownAction.php';
require_once 'linguisticobjects/NeutralAction.php';
require_once 'linguisticobjects/NasalizationAction.php';
require_once 'linguisticobjects/InsertionAction.php';
require_once 'linguisticobjects/DeletionAction.php';
require_once 'linguisticobjects/VoicingAction.php';
require_once 'linguisticobjects/DeletionAndInsertionAction.php';
require_once 'linguisticobjects/SpecificDeletionAction.php';
require_once 'linguisticobjects/AssimilationAction.php';
require_once 'linguisticobjects/SelfDecapitationAction.php';
require_once 'linguisticobjects/VowelLengtheningAction.php';
 
 class ActionFactory {
 	
 	static public function make($string) {
 		$logger = Logger::getLogger('ActionFactory.make');
 		$logger->debug("\$string= '$string'");
	    $action = null;
        $inside = null;
        if ($string != null) {
        	$match = preg_match("/^[a-z]+\\((.+)\\)$/",$string,$matches);
            if ( $match )
                $inside = $matches[1];
        }
		if ( $string == null || $string=='-' || $string=='0' ) { //***
			$action = null; //new Nulle();
		}
		elseif ( $string == '?' ) { //***
			$action = Unknown_Action::singleton();
 		}	
		elseif ( startsWith($string,'i(') || startsWith($string,'ins(') ) { //***
		    $action = Insertion_Action::singleton($inside);
		}
		elseif ( $string=='s(' || $string=='suppr(' ) { //***
		    $action = Specific_Deletion_Action::singleton($inside);
		}
		// Suppression conditionnelle : un seul endroit : it/2nv (être sans qqch.)
		// en action2 : ssi(i$,a) devrait être s(ia) signifiant : si le radical se termine en 'ia',
		// supprimer le 'ia'. Il s'agit en fait d'une suppression spécifique : 
		// je choisis de remplacer par SuppressionConditionnelle dans la BD et
		// d'éliminer cette action.
//		elseif ( $string=='ssi(' || $string=='supprsi(' ) { //***
//            $condSupp = explode(',',$inside);
//            $action = new SuppressionSpecifiqueConditionnelle($condSupp[0],$condSupp[1]);
//        } 
		elseif (  startsWith($string,'si(')
				|| startsWith($string,'sins(')
				|| startsWith($string,'suppri(')
				|| startsWith($string,'supprins(') ) {//***
		     $action = Deletion_and_Insertion_Action::singleton($inside);
		}
		elseif ( $string=='s' || $string=='suppr' ) { //***
             $action = Deletion_Action::singleton();
		}
		elseif ( $string=='son' || $string=='sonor' ) { //***
			$action = Voicing_Action::singleton();
		}
		elseif ( $string=='nas' || $string=='nasal' ) { //***
            $action = Nasalization_Action::singleton();
		}
//		elseif ($string=='nassi(') //*** Pas d'occurrence dans les fichiers .csv
//            $action = new NasalisationConditionnelle($inside); //***
		// La classe d'action Fusion n'est pas nécessaire, puisqu'il y a toujours une
		// forme spéciale du suffixe dans ce contexte-là, et qu'à toute fin pratique,
		// cette forme 'supprime' la consonne du radical. On a donc remplacé
		// toutes les occurrences de 'fus' par 's' dans la base de données.
//       elseif ( $string == 'fus') || $string == $fusion' ) //***
//            $action = new Fusion();
		elseif ( $string=='n' || $string=='neutre' ) { //***
			$action = Neutral_Action::singleton();
		}
		// Aucune occurrence de nsi() dans les .csv
//        elseif ( startsWith($string,"nsi(",0) ) { //***
//            $action = new Neutral_Action($inside);
//        }
        // L'action d'assimilation n'est en fait que le processus de gémination associatif
        // qui a lieu dans certains dialectes. Ce n'est pas une action � proprement parler.
        // Il conviendrait de remplacer dans la base de donn�es toutes les occurrences
        // de 'assimilation' par ce que ça devrait vraiment être, soit 'sonorisation', soit
        // 'nasalisation' (dépendant du suffixe) et de laisser l'analyseur morphologique ou
        // toute autre application v�rifier ensuite s'il y a assimilation.
        // Il est d'ailleurs � noter que dans la base de donn�es, cette assimilation n'arrive
        // que dans le contexte de radical en 't'. Par exemple, gi/3vv a la forme 'ni'
        // apr�s 't', et ma documentation dit qu'il y a assimilation du 't' par 'n'. En 
        // r�alit�, c'est probablement plut�t une nasalisation caus�e par 'ni' ; or 't'
        // est nasalis� en 'n', d'o� apparence d'assimilation.
        // POUR LE MOMENT, on conserve cette action. 
		elseif ( $string == 'a' || $string == 'assim' ) { //***
			$action = Assimilation_Action::singleton();
		}
		// L'assimilation spécifique n'est définie dans la base de données que pour 3
		// terminaisons verbales, dans le même contexte de radical en 't' : a(k), et
		// dans les 3 cas, en plus d'une assimilation régulière. Il s'agit probablement
		// d'une erreur d'interprétation de ma part. Je décide d'éliminer ces 3 cas
		// et l'action d'assimilation spécifique.
//		elseif ( startsWith($string,'(') || startsWith($string,'assim(') ) { //***
//			$action = new AssimilationSpecifique($string);
// 		}
		elseif ( $string=='allV' ) { //***
		    $action = Vowel_Lengthening_Action::singleton();
		}
		// L'annulation n'arrive que pour des actions1 impliquant l'allongement de la voyelle,
		// lorsque le radical terminait déjà par 2 voyelles. C'était mal fait. Je remplace
		// cela par une troisième action dans 'actions' : 
		// <action sur le radical>-<action suivante sur le radical s'il termine par 2 voyelles>-
		// <action suivante sur le radical s'il ne termine pas par 2 voyelles>
		// Il n'est pas nécessaire de définir cette troisième action ; elle sera nulle par défaut. 
//		else if (chaine.equals("x") || chaine.equals("annul")) //***
//		    action = new Annulation();
		elseif ( $string == 'decap' ) { //***
		     $action = Self_Decapitation_Action::singleton();
		}
//		else if (chaine.startsWith("iallV(") || chaine.startsWith("insallV(")) //***
//		    action = new InsertionEtAllongementDeVoyelle(chaine);
//		else if (chaine.equals("sallV") || chaine.equals("supprallV")) //***
//		    action = new SuppressionEtAllongementDeVoyelle();

		if ( $action != null ) {
		    $action->defString($string);
        }
		return $action;
	}
 	
 }
?>
