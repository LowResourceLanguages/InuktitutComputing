<?php
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2003
//           (c) National Research Council of Canada, 2003
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		NounEnding.php
//
// Type/File type:		code php / php code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de crÃ©ation/Date of creation:	
//
// Description: Classe NounEnding
//
// -----------------------------------------------------------------------

require_once 'linguisticobjects/Affix.php';
require_once 'linguisticobjects/Morpheme.php';

require_once 'lib/log4php/Logger.php';

class NounEnding extends Affix {
	
	private $case;
	private $number;
	private $possPers = null;
	private $possNumber = null;
	private $poss = null;
	
	
    //------------------------------------------------------------------------------------------------------------
	public function __construct ($fieldsAndValues) {
 		$logger = Logger::getLogger('NounEnding.__construct');  		
		$this->morpheme = $fieldsAndValues['morpheme'];
		$logger->debug("morpheme= '$this->morpheme'");
		$this->type = $fieldsAndValues['type'];
		$this->case = $fieldsAndValues['case'];
		$this->number = $fieldsAndValues['number'];
		$this->possPers = $fieldsAndValues['perPoss'];
		$this->possNumber = $fieldsAndValues['numbPoss'];
		if ($this->possPers != null)
		    $this->poss = "true";
		else
		    $this->poss = "false";
		if ( array_key_exists('dbName',$fieldsAndValues) )
			$this->dbName = $fieldsAndValues['dbName'];
		if ( array_key_exists('tableName',$fieldsAndValues) )
			$this->tableName = $fieldsAndValues['tableName'];

		$this->_makeMeaning();

		$this->defineBehaviours($fieldsAndValues);

		$cs = $fieldsAndValues['condPrec'];
//		if (cs != null) {
//            try {
//                preCondition = (Conditions) new Imacond(
//                        new ByteArrayInputStream(cs.getBytes())).ParseCondition();
//            } catch (ParseException e) {
//            }
//		}
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

    //------------------------------------------------------------------------------------------------------------
// 	public function signature_components() {
// 		$comps = array( $this->type, $this->case, $this->number );
// 		if ($this->possPers != null)
// 			array_push($comps, $this->possPers . $this->possNumber);
// 		return $comps;
// 	}
	
	//----------COMPULSORY ABSTRACT FUNCTIONS------------------------------------------------------------------------------------------------
	public function getTransitivityConstraint() {
	    return $this->constraintOnTransitivity;
	}

	public function agreesWithTransitivity($trans) {
	    return true;
	}
	
	static public function make_id ($field_values) {
		$info_for_signature = array(
			$field_values['type'],
			$field_values['case'],
			$field_values['number']
		);
		if ( $field_values['perPoss'] != "" ) {
			array_push( $info_for_signature, $field_values['perPoss'] . $field_values['numbPoss'] );
		}
		$morpheme_id_object = new ID_Morpheme(
				$field_values['morpheme'],
				$info_for_signature
		);
		return $morpheme_id_object;
	}
	
    //------------------------------------------------------------------------------------------------------------
	// DÃ©velopper les sens en franÃ§ais et en anglais des terminaisons
	// verbales Ã  partir des donnÃ©es suivantes: mode, spÃ©cificitÃ©,
	// personne et nombres du sujet et de l'objet.
	public function _makeMeaning() {
		$frenchMeaning1 = '';
		$engMeaning1 = '';

		if ( $this->case=='nom' ) {
			$frenchMeaning1 .= 'nominatif : ';
			$engMeaning1 .= 'nominative: ';
		} elseif ( $this->case=='gen' ) {
			$frenchMeaning1 .= 'génitif : de ';
			$engMeaning1 .= 'genitive: of ';
		} elseif ( $this->case=='acc' ) {
			$frenchMeaning1 .= 'accusatif : ';
			$engMeaning1 .= 'accusative: ';
		} elseif ( $this->case=='dat' ) {
			$frenchMeaning1 .= 'datif : à ; avec (instrument) ';
			$engMeaning1 .= 'dative: to; with (instrument) ';
		} elseif ( $this->case=='abl' ) {
			$frenchMeaning1 .= 'ablatif : de ; par (agent) ; que (comparaison) ';
			$engMeaning1 .= 'ablative: from; by (agent); than (comparaison) ';
		} elseif ( $this->case=='loc' ) {
			$frenchMeaning1 .= 'locatif : dans ; sur ';
			$engMeaning1 .= 'locative: in; on; upon ';
		} elseif ( $this->case=='sim' ) {
			$frenchMeaning1 .= 'similaris : comme ';
			$engMeaning1 .= 'similaris: like ';
		} elseif ( $this->case=='via' ) {
			$frenchMeaning1 .= 'vialis : au moyen de ; à travers ; par; pendant ';
			$engMeaning1 .= 'vialis: through; by; by means of; across; over; for (period of time) ';
		}

		if ( $this->possPers == null) {
			if ( $this->number=='s' ) {
				$frenchMeaning1 .= 'un ; une ; le ; la ';
				$engMeaning1 .= 'a; the (one)';
			} elseif ( $this->number=='d' ) {
				$frenchMeaning1 .= 'des ; les (deux) ';
				$engMeaning1 .= 'two; the (two)';
			} elseif ( $this->number=='p' ) {
				$frenchMeaning1 .= 'des ; les (plusieurs) ';
				$engMeaning1 .= 'many; the (many)';
			}
		} elseif ( $this->possPers=='1' ) {
			if ( $this->possNumber=='s' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'mon ; ma ';
					$engMeaning1 .= 'my (one thing) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'mes (deux choses)';
					$engMeaning1 .= 'my (two things) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'mes (plusieurs choses) ';
					$engMeaning1 .= 'my (many things) ';
				}
			} elseif ( $this->possNumber=='d' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'notre (à nous deux) ';
					$engMeaning1 .= 'our (one thing to us two) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'nos (deux choses à nous deux) ';
					$engMeaning1 .= 'our (two things to us two) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'nos (plusieurs choses à nous deux) ';
					$engMeaning1 .= 'our (many things to us two) ';
				}
			} elseif ( $this->possNumber=='p' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'notre (à nous plusieurs) ';
					$engMeaning1 .= 'our (one thing to us many) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'nos (deux choses à nous plusieurs) ';
					$engMeaning1 .= 'our (two things to us many) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'nos (plusieurs choses à nous plusieurs) ';
					$engMeaning1 .= 'our (many things to us many) ';
				}
			}
		} elseif ( $this->possPers=='2' ) {
			if ( $this->possNumber=='s' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'ton ; ta ';
					$engMeaning1 .= 'your (one thing to one person) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'tes (deux choses)';
					$engMeaning1 .= 'your (two things to one person) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'tes (plusieurs choses) ';
					$engMeaning1 .= 'your (many things to one person) ';
				}
			} elseif ( $this->possNumber=='d' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'votre (à vous deux) ';
					$engMeaning1 .= 'your (one thing to you two) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'vos (deux choses à vous deux) ';
					$engMeaning1 .= 'your (two things to you two) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'vos (plusieurs choses à vous deux) ';
					$engMeaning1 .= 'your (many things to you two) ';
				}
			} elseif ( $this->possNumber=='p' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'votre (à vous plusieurs) ';
					$engMeaning1 .= 'your (one thing to you many) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'vos (deux choses à vous plusieurs) ';
					$engMeaning1 .= 'your (two things to you many) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'vos (plusieurs choses à vous plusieurs) ';
					$engMeaning1 .= 'your (many things to you many) ';
				}
			}
		} elseif ( $this->possPers=='3' ) {
			if ( $this->possNumber=='s' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'son ; sa (même personne) ';
					$engMeaning1 .= 'his;her;its (one thing, same person) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'ses (deux choses, même personne)';
					$engMeaning1 .= 'his;her;its (two things, same person) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'ses (plusieurs choses, même personne) ';
					$engMeaning1 .= 'his;her;its (many things, same person) ';
				}
			} elseif ( $this->possNumber=='d' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'leur (à eux deux, mêmes personnes) ';
					$engMeaning1 .= 'their (one thing to them two, same persons) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'leurs (deux choses à eux deux, mêmes personnes) ';
					$engMeaning1 .= 'their (two things to them two, same persons) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'leurs (plusieurs choses à eux deux, mêmes personnes) ';
					$engMeaning1 .= 'their (many things to them two, same persons) ';
				}
			} elseif ( $this->possNumber=='p' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'leur (à eux plusieurs, mêmes personnes) ';
					$engMeaning1 .= 'their (one thing to them many, same persons) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'leurs (deux choses à eux plusieurs, mêmes personnes) ';
					$engMeaning1 .= 'their (two things to them many, same persons) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'leurs (plusieurs choses à eux plusieurs, mêmes personnes) ';
					$engMeaning1 .= 'their (many things to them many, same persons) ';
				}
			}
		}
		elseif ( $this->possPers=='4' ) {
			if ( $this->possNumber=='s' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'son ; sa (autre personne) ';
					$engMeaning1 .= 'his;her;its (one thing, different person) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'ses (deux choses, autre personne)';
					$engMeaning1 .= 'his;her;its (two things, different person) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'ses (plusieurs choses, autre personne) ';
					$engMeaning1 .= 'his;her;its (many things, different person) ';
				}
			} elseif ( $this->possNumber=='d' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'leur (à eux deux, autres personnes) ';
					$engMeaning1 .= 'their (one thing to them two, different persons) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'leurs (deux choses à eux deux, autres personnes) ';
					$engMeaning1 .= 'their (two things to them two, different persons) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'leurs (plusieurs choses à eux deux, autres personnes) ';
					$engMeaning1 .= 'their (many things to them two, different persons) ';
				}
			} elseif ( $this->possNumber=='p' ) {
				if ( $this->number=='s' ) {
					$frenchMeaning1 .= 'leur (à eux plusieurs, autres personnes) ';
					$engMeaning1 .= 'their (one thing to them many, different persons) ';
				} elseif ( $this->number=='d' ) {
					$frenchMeaning1 .= 'leurs (deux choses à eux plusieurs, autres personnes) ';
					$engMeaning1 .= 'their (two things to them many, different persons) ';
				} elseif ( $this->number=='p' ) {
					$frenchMeaning1 .= 'leurs (plusieurs choses à eux plusieurs, autres personnes) ';
					$engMeaning1 .= 'their (many things to them many, different persons) ';
				}
			}
		}
		$this->frenchMeaning = $frenchMeaning1;
		$this->engMeaning = $engMeaning1;
	}

//    Vector getIdsOfCompositesWithThisRoot() {
//        return null;
//    }

		
    //------------------------------------------------------------------------------------------------------------
//	String showData() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n[NounEnding: morpheme= " + morpheme + "\n';
//		sb.append("type= " + type + "\n';
//		sb.append("case= " + case + "\n';
//		sb.append("number=" + number + "\n';
//		sb.append("possPers= " + possPers + "\n';
//		sb.append("possNumber= " + possNumber + "\n';
//		sb.append(super.showData());
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