<?php
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		VerbEnding.php
//
// Type/File type:		code php / php code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: Classe VerbEnding
//
// -----------------------------------------------------------------------

require_once 'linguisticobjects/Affix.php';
require_once 'linguisticobjects/Morpheme.php';

require_once 'lib/log4php/Logger.php';

class VerbEnding extends Affix {
	//
	private $mode;
	private $spec;
	private $subjPers;
	private $subjNumber;
	private $objPers;
	private $objNumber;
	private $sameSubject;
	private $posneg;
	private $tense = '';

	//---------------------------------------------------------------------------------------------------------
	
	// morpheme;type;mode;specificity;perSubject;numbSubject;perObject;numbObject;V-form;V-actions;V-action1;V-action2;t-form;t-actions;t-action1;t-action2;k-form;k-actions;k-action1;k-action2;q-form;q-actions;q-action1;q-action2;SpecificCondOnPrecMorph;sources
	// morpheme;type;mode;sameSubject;posneg;tense;dialect;specificity;perSubject;numbSubject;perObject;numbObject;V-form;V-actions;V-action1;V-action2;t-form;t-actions;t-action1;t-action2;k-form;k-actions;k-action1;k-action2;q-form;q-actions;q-action1;q-action2;SpecificCondOnPrecMorph;sources
	public function __construct ($fieldsAndValues) {
 		$logger = Logger::getLogger('VerbEnding.__construct');  		
		$this->morpheme = $fieldsAndValues['morpheme'];
		$this->type = $fieldsAndValues['type'];
		$this->mode = $fieldsAndValues['mode'];
		$this->subjPers = $fieldsAndValues['perSubject'];
		$this->subjNumber = $fieldsAndValues['numbSubject'];
		$this->objPers = $fieldsAndValues['perObject'];
		$this->objNumber = $fieldsAndValues['numbObject'];
		$logger->debug("attributs d�finis");
        if ($this->objPers != '')
            $this->spec = 'sp';
        else
            $this->spec = 'nsp';
		if ( $this->mode == 'part' ) {
			$this->sameSubject = $fieldsAndValues['sameSubject'];
			$this->posneg = $fieldsAndValues['posneg'];
			$this->tense = $fieldsAndValues['tense'];
		}
		if ( array_key_exists('dbName',$fieldsAndValues) )
			$this->dbName = $fieldsAndValues['dbName'];
		if ( array_key_exists('tableName',$fieldsAndValues) )
			$this->tableName = $fieldsAndValues['tableName'];

		$this->_makeMeaning();

//		// Développement des diverses formesAffixes associées aux 4 contextes
//		// voyelle, t, k et q et à leurs actions.
		$this->defineBehaviours($fieldsAndValues);
//

		$cs = $fieldsAndValues['SpecificCondOnPrecMorph'];
//		if ($cs != null) {
//            try {
//                preCondition = (Conditions) new Imacond(
//                        new ByteArrayInputStream(cs.getBytes())).ParseCondition();
//            } catch (ParseException e) {
//            }
//        }

		$srcs = $fieldsAndValues['sources'];
		if ($srcs != '') {
			$st2 = explode(' ',$srcs);
			$this->sources = array();
			$n = 0;
			foreach ($st2 as $s) {
				$this->sources[$n++] = $s;
			}
		}
		$this->morphemeID =self::make_id($fieldsAndValues);
		$logger->debug("id= '$this->id'");
	}

	//---------------------------------------------------------------------------------------------------------
	// Signature des terminaisons verbales:
	// <type>-<mode>-<subjPers><subjNumber>[-<objPers><objNumber>][-<tense>]
// 	public function signature_components() {
//  		$logger = Logger::getLogger('VerbEnding.signature_components');  		
// 		$comps = array($this->type, $this->mode, $this->subjPers . $this->subjNumber);
// 		$logger->debug("\$this->objPers= '".$this->objPers."'");
// 		if ( $this->objPers != '')
// 			array_push($comps, $this->objPers . $this->objNumber);
// 		if ( $this->mode == 'part'  && $this->tense != '' )
// 			array_push($comps, $this->tense);
// 		return $comps;
// 	}

	//----------COMPULSORY ABSTRACT FUNCTIONS------------------------------------------------------------------------------------------------
	public function getTransitivityConstraint() {
	    if ( $this->spec == 'nsp' )
	        return 'i';
	    else
	        return 't';
	}
	public function agreesWithTransitivity($trans) {
	    return true;
	}
	
	static public function make_id ($field_values) {
		$info_for_signature = array(
			$field_values['type'],
			$field_values['mode'],
			$field_values['perSubject'] . $field_values['numbSubject']
		);
		if ( $field_values['perObject'] != '' ) {
			array_push( $info_for_signature, $field_values['perObject'] . $field_values['numbObject'] );
		}
		if ( $field_values['mode']=='part' && $field_values['tense'] != "" ) {
			array_push( $info_for_signature, $field_values['tense'] );
		}
		$morpheme_id_object = new ID_Morpheme(
				$field_values['morpheme'],
				$info_for_signature
		);
		return $morpheme_id_object;
	}
		
    //------------------------------------------------------------------------------------------------------------
	
//	void rendreReflexif() {
//		objPers = subjPers;
//		objNumber = subjNumber;
//		spec = "sp";
//		if (subjPers.equals("1"))
//			if (subjNumber.equals("s")) {
//				frenchMeaning = frenchMeaning + " ... moi-même";
//				engMeaning = engMeaning + " ... myself";
//			} else {
//				frenchMeaning = frenchMeaning + " ... nous-mêmes";
//				engMeaning = engMeaning + " ... ourselves";
//			}
//		else if (subjPers.equals("2"))
//			if (subjNumber.equals("s")) {
//				frenchMeaning = frenchMeaning + " ... toi-même";
//				engMeaning = engMeaning + " ... yourself";
//			} else {
//				frenchMeaning = frenchMeaning + " ... vous-mêmes";
//				engMeaning = engMeaning + " ... yourselves";
//			}
//		else if (subjPers.equals("3"))
//			if (subjNumber.equals("s")) {
//				frenchMeaning = frenchMeaning + " ... lui/elle-même";
//				engMeaning = engMeaning + " ... him/her/itself";
//			} else {
//				frenchMeaning = frenchMeaning + " ... ils/elles-mêmes";
//				engMeaning = engMeaning + " ... themselves";
//			}
//	}
	
//    Vector getIdsOfCompositesWithThisRoot() {
//        return null;
//    }


	//---------------------------------------------------------------------------------------------------------
	// Développer les sens en français et en anglais des terminaisons
	// verbales à partir des données suivantes : mode, spécificité,
	// personne et nombres du sujet et de l'objet.

	public function _makeMeaning() {
		$frenchMeaning1 = '';
		$engMeaning1 = '';

		if ($this->mode == 'dec' || $this->mode == 'ger') {
			$frenchMeaning1 .= 'd�claration: ';
			$engMeaning1 .= 'declaration: ';
		} elseif ($this->mode == 'int') {
			if ($this->subjPers == '3' || $this->subjPers == '4') {
				$frenchMeaning1 .= 'question: est-ce qu';
				if ($this->subjNumber == 's')
					$engMeaning1 .= 'question: does ';
				else
					$engMeaning1 .= 'question: do ';
			} else {
				$frenchMeaning1 .= 'question: est-ce que ';
				$engMeaning1 .= 'question: do ';
			}
		} elseif ($this->mode == 'imp') {
			$frenchMeaning1 .= 'ordre: ';
			$engMeaning1 .= 'order: ';
		} elseif ($this->mode == 'part') {
			if ($this->tense == '') {
				$frenchMeaning1 .= 'part: ';
				$engMeaning1 .= 'part: ';
			} elseif ($this->tense == 'prespas') {
				$frenchMeaning1 .= 'part. pr�sent/pass�: ';
				$engMeaning1 .= 'part. present/past: ';
			} elseif ($this->tense == 'fut') {
				$frenchMeaning1 .= 'part. futur: ';
				$engMeaning1 .= 'part. future: ';
			}
			if ($this->subjPers == '3' || $this->subjPers == '4')
				$frenchMeaning1 .= 'alors/pendant qu';
			else
				$frenchMeaning1 .= 'alors/pendant que ';
			$engMeaning1 .= 'while ';
		} elseif ($this->mode == 'caus') {
			if ($this->subjPers == '3' || $this->subjPers == '4')
				$frenchMeaning1 .= 'causal: parce qu';
			else
				$frenchMeaning1 .= 'causal: parce que ';
			$engMeaning1 .= 'becausative: because ';
		} elseif ($this->mode == 'cond') {
			if (($this->subjPers == '3' || $this->subjPers == '4')
				&& $this->subjNumber == 's')
				$frenchMeaning1 .= 'conditionnel: s';
			else
				$frenchMeaning1 .= 'conditionnel: si ';
			$engMeaning1 .= 'conditional: if ';
		} elseif ($this->mode == 'dub') {
			if (($this->subjPers == '3' || $this->subjPers == '4')
				&& $this->subjNumber == 's')
				$frenchMeaning1 .= 'dubitatif: s';
			else
				$frenchMeaning1 .= 'dubitatif: si ';
			$engMeaning1 .= 'dubitative: whether ';
		} elseif ($this->mode == 'freq') {
			if ($this->subjPers == '3' || $this->subjPers == '4')
				$frenchMeaning1 .= 'fr�quentatif: chaque fois qu\' / lorsqu\'';
			else
				$frenchMeaning1 .= 'fr�quentatif: chaque fois que / lorsque ';
			$engMeaning1 .= 'frequentative: whenever ';
		}

		if ($this->subjPers == '1') {
			if ($this->subjNumber == 's') {
				$frenchMeaning1 .= 'je ';
				$engMeaning1 .= 'I ';
			} elseif ($this->subjNumber == 'd') {
				$frenchMeaning1 .= 'nous (deux) ';
				$engMeaning1 .= 'we (two) ';
			} elseif ($this->subjNumber == 'p') {
				$frenchMeaning1 .= 'nous (plusieurs) ';
				$engMeaning1 .= 'we (many) ';
			}
		} elseif ($this->subjPers == '2') {
			if ($this->subjNumber == 's') {
				$frenchMeaning1 .= 'tu ';
				$engMeaning1 .= 'you ';
			} elseif ($this->subjNumber == 'd') {
				$frenchMeaning1 .= 'vous (deux) ';
				$engMeaning1 .= 'you (two) ';
			} elseif ($this->subjNumber == 'p') {
				$frenchMeaning1 .= 'vous (plusieurs) ';
				$engMeaning1 .= 'you (many) ';
			}
		} elseif ($this->subjPers == '3' || $this->subjPers == '4') {
			if ($this->subjNumber == 's') {
				$frenchMeaning1 .= 'il/elle ';
				$engMeaning1 .= 'he/she/it ';
			} elseif ($this->subjNumber == 'd') {
				$frenchMeaning1 .= 'ils/elles (deux) ';
				$engMeaning1 .= 'they (two) ';
			} elseif ($this->subjNumber == 'p') {
				$frenchMeaning1 .= 'ils/elles (plusieurs) ';
				$engMeaning1 .= 'they (many) ';
			}
		}
		
		if ($this->mode == 'part' && $this->posneg == 'neg') {
			$frenchMeaning1 .= 'ne ';
			$engMeaning1 .= 'do not ';
		}
		
		$frenchMeaning1 .= '...';
		$engMeaning1 .= '...';
		if ($this->mode == 'part' && $this->posneg == 'neg')
			$frenchMeaning1 .= ' pas ';

		if ($this->spec == 'sp') {
			if ($this->objPers == '1') {
				if ($this->objNumber == 's') {
					$frenchMeaning1 .= 'moi';
					$engMeaning1 .= 'me';
				} elseif ($this->objNumber == 'd') {
					$frenchMeaning1 .= 'nous (deux)';
					$engMeaning1 .= 'us (two)';
				} elseif ($this->objNumber == 'p') {
					$frenchMeaning1 .= 'nous (plusieurs)';
					$engMeaning1 .= 'us (many)';
				}
			} elseif ($this->objPers == '2') {
				if ($this->objNumber == 's') {
					$frenchMeaning1 .= 'toi';
					$engMeaning1 .= 'you';
				} elseif ($this->objNumber == 'd') {
					$frenchMeaning1 .= 'vous (deux)';
					$engMeaning1 .= 'you (two)';
				} elseif ($this->objNumber == 'p') {
					$frenchMeaning1 .= 'vous (plusieurs)';
					$engMeaning1 .= 'you (many)';
				}
			} elseif ($this->objPers == '3') {
				if ($this->objNumber == 's') {
					$frenchMeaning1 .= 'lui/elle';
					$engMeaning1 .= 'him/her/it';
				} elseif ($this->objNumber == 'd') {
					$frenchMeaning1 .= 'eux/elles (deux) ';
					$engMeaning1 .= 'them (two) ';
				} elseif ($this->objNumber == 'p') {
					$frenchMeaning1 .= 'eux/elles (plusieurs) ';
					$engMeaning1 .= 'them (many) ';
				}
			}
		}
		
		if ($this->mode == 'int') {
			$frenchMeaning1 .= '?';
			$engMeaning1 .= '?';
		}
		
		$this->frenchMeaning = $frenchMeaning1;
		$this->engMeaning = $engMeaning1;
	}
	
	//---------------------------------------------------------------------------------------------------------
//	String showData() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n[VerbEnding: morpheme= " + morpheme + "\n';
//		sb.append("type= " + type + "\n';
//		sb.append("mode= " + mode + "\n';
//		if (mode == 'part') {
//			sb.append("sameSubject= " + sameSubject + "\n';
//			sb.append("posneg= " + posneg + "\n';
//			sb.append("tense= " + tense + "\n';
//		}
//		sb.append("spec= " + spec + "\n';
//		sb.append("subjPers= " + subjPers + "\n';
//		sb.append("subjNumber= " + subjNumber + "\n';
//		sb.append("$this->objPers= " + $this->objPers + "\n';
//		sb.append("$this->objNumber= " + $this->objNumber + "\n';
//		sb.append(super.showData());
////		sb.append(
////			"precedingSpecificConditions= "
////				+ Util.array2string(
////					precedingSpecificConditions)
////				+ "\n';
//        if (preCondition != null) {
//            sb.append("precedingSpecificCondition= "+preCondition.toString()+"\n';
//        }
//    	sb.append("dbName= "+dbName+"\n';
//    	sb.append("tableName= "+tableName+"\n';
//		sb.append("sources= ';
//		if (sources == null)
//			sb.append(sources);
//		else
//			for (int n = 0; n < sources.length; n++)
//				sb.append(sources[n] + " ';
//		sb.append("]\n';
//		return sb.toString();
//	}


}
?>
