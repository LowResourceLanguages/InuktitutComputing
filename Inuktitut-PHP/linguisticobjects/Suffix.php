<?php
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Suffix.php
//
// Type/File type:		code PHP
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: Classe Suffix
//
// -----------------------------------------------------------------------

require_once 'linguisticobjects/Affix.php';
require_once 'lib/log4php/Logger.php';
require_once 'linguisticobjects/Morpheme.php';

class Suffix extends Affix {
	//morpheme,key,type,function,position,transitivity,nature,plural,antipassive,
	//V-form,V-action1,V-action2,t-form,t-action1,t-action2,k-form,k-action1,k-action2,
	//q-form,q-action1,q-action2,engMean,freMean,condPrec,condPrecTrans,condOnNext,sources,
	//composition,dialect,mobility
	
	private $key;
	private $transitivity;
	private $antipassive;
	private $nature;
	private $function;
	private $constraintOnTransitivity = null;
	private $plural;
    private $mobility;
   	//
	
	public function __construct ($fieldsAndValues) {
 		$logger = Logger::getLogger('Suffix.__construct');
		$this->morpheme = $fieldsAndValues['morpheme'];
 		$logger->debug("morpheme : $this->morpheme");	
		$this->key = $fieldsAndValues['key'];
//		$num = $key;
		$this->transitivity = $fieldsAndValues['transitivity'];
		$this->nature = $fieldsAndValues['nature'];
		$this->antipassive = $fieldsAndValues['antipassive'];
		$this->type = $fieldsAndValues['type'];
		$this->function = $fieldsAndValues['function'];
		$this->position = $fieldsAndValues['position'];
		$this->constraintOnTransitivity = $fieldsAndValues['condPrecTrans'];
		$this->plural = $fieldsAndValues['plural'];
        $this->mobility = $fieldsAndValues['mobility'];
        $this->dialect = $fieldsAndValues['dialect'];

//		// Développement des diverses formesAffixes associées aux 4 contextes
//		// voyelle, t, k et q et à leurs actions.
        $this->defineBehaviours($fieldsAndValues);

		$this->engMeaning = $fieldsAndValues['engMean'];
		$this->frenchMeaning = $fieldsAndValues['freMean'];
		if ( array_key_exists('dbName',$fieldsAndValues) )
			$this->dbName = $fieldsAndValues['dbName'];
		if ( array_key_exists('tableName',$fieldsAndValues) )
			$this->tableName = $fieldsAndValues['tableName'];
		$cs = $fieldsAndValues['condPrec'];
//		if ($cs == null || $cs == '') {
//            /*
//             * Pour les NV, si on n'a pas spécifié une condition précédente, on
//             * ajoute une condition par défaut correspondant à l'énoncé suivant:
//             * 'Les NV doivent suivre des radicaux nominaux', i.e. qu'ils ne
//             * peuvent suivre une terminaison nominale, sauf dans des cas
//             * spéciaux où ce sera indiqué spécifiquement. On empêchera donc par
//             * défaut les terminaisons nominales. Et puisqu'il y a des noms
//             * duels et pluriels dans la base de données, on les empêchera
//             * aussi.
//             */
//            if ( $type=='sn' && $function=='nv' ) {
//                $condStr = '!type:tn,!(type:n,number:d),!(type:n,number:p)';
//                $preCondition = new Imacond($condStr).ParseCondition();
//			}
//		} else {
//            $preCondition = new Imacond($cs).ParseCondition();
//		}
//		$cs = $fieldsAndValues['condOnNext'];
//        if ($cs != null && $cs != '') {
//           $nextCondition = new Imacond($cs).ParseCondition();
//        }
		$srcs = $fieldsAndValues['sources'];
		if ($srcs != null) {
			$st2 = explode(' ',$srcs);
			$this->sources = array();
			$n = 0;
			foreach ($st2 as $s) {
				$this->sources[$n++] = $s;
			}
		}
		$comb = $fieldsAndValues['composition'];
		if ($comb != null && $comb != '') {
		    $combinedMorphemes = explode('+',$comb);
		    if (count($combinedMorphemes) < 2) {
		        $combinedMorphemes = null;
		    }
		}

		$this->morphemeID =self::make_id($fieldsAndValues);
	}
	
	//----------COMPULSORY ABSTRACT FUNCTIONS------------------------------------------------------------------------------------------------
	public function getTransitivityConstraint() {
	    return $this->constraintOnTransitivity;
	}

	//----------------------------------------------------------------------------------------------------------
	
	public function fct()
	{
		return $this->function;
	}
	
 	public function nature() {
 		return $this->nature;
 	}
	
 	public function position() {
 		return $this->position;
 	}
	
 	// Suffixes have transitivity values:
	// t: transitive: the resulting stem is transitive
	//    if antipassive!=null, the resulting stem may be used intransitively; it is
    //    then passive or reflexive.
	// i: intransitive: the resulting stem is intransitive
	//    if nature=t, the resulting stem may also be transitive
	// n: neutral: the resulting stem is the same as the preceding stem
	public function agreesWithTransitivity($trans) {
	    if ($trans==null)
	        return true;
	    elseif ($this->transitivity==null || $this->transitivity=="n")
	        return true;
	    elseif ($this->transitivity=="t" && $trans=="t")
	        return true;
	    elseif ( $this->transitivity=="i" &&
	             ( $trans=="i" || 
	               ( trans=="t" && $this->nature!=null && $this->nature=="t" )
	             ) )
	        return true;
	    elseif ($this->transitivity=="t"
	            && $trans=="i"
	            && $this->antipassive != null)
	        return true;
	    else
	        return false;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	// Comparaison entre cet affixe et celui donne en argument.
	public function same($aff) {
		if ($this->morpheme == $aff->morpheme && $this->type == $aff->type)
			if ($this->type == "q")
				return true;
			elseif ($this->function == $aff->function)
				return true;
			else
				return false;
		else
			return false;
	}
	
	public function needsAntipassive($apId) {
	    if ($this->antipassive != null) {
	        $antipassives = explode(' ',$this->antipassive);
	        foreach ($antipassives as $anAntipassive)
	            if ($anAntipassive == $apId)
	                return true;
	        return false;
	    }
	    else
	        return false;
	}
	
	public function isMobile() 
	{
		if ( $this->mobility == 'm' )
			return true;
		else
			return false;
	}
	
	public function isNotMobile() {
		if ($this->mobility != null && $this->mobility == 'nm')
			return true;
		else
			return false;
	}
	
	
	static public function make_id ($field_values) {
		$morpheme_id_object = new ID_Morpheme(
				$field_values['morpheme'],
				array ( 
						$field_values['key'] .
						( $field_values['type']=='q' ? $field_values['type'] : $field_values['function'] )
						)
		);
		return $morpheme_id_object;
	}
	
	
	
//    Vector getIdsOfCompositesWithThisRoot() {
//        return null;
//    }
    
	
	
	
	//-------------------
//	String showData() {
//        StringBuffer sb = new StringBuffer();
//        sb.append("[Suffix:\n");
//        sb.append("morpheme= " + morpheme + "\n");
//        sb.append("key: "+ key + "\n");
//        sb.append("type= " + type + "\n");
//        sb.append("function= " + function + "\n");
//        sb.append("position= " + position + "\n");
//        sb.append("antipassive= " + antipassive + "\n");
//        sb.append(super.showData());
//        if (preCondition != null) {
//            sb.append("precedingSpecificCondition= "+preCondition.toString()+"\n");
//        }
//        if (nextCondition != null) {
//            sb.append("followingSpecificConditions= "+nextCondition.toString()+"\n");
//        }
//        sb.append("engMeaning= " + engMeaning + "\n");
//        sb.append("tableName= " + tableName + "\n");
//        sb.append("sources= ");
//        if (sources == null)
//            sb.append(sources);
//        else
//            for (int n = 0; n < sources.length; n++)
//                sb.append(sources[n] + " ");
//        sb.append("]\n");
//        return sb.toString();
//    }

}
?>