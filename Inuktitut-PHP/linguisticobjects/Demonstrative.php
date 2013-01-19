<?php 

//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Pronoun.php
//
// Type/File type:		code PHP
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation:	
//
// Description: Classe Pronoun
//
// -----------------------------------------------------------------------
require_once 'lib/log4php/Logger.php';
require_once 'linguisticobjects/Root.php';

class Demonstrative extends Root {
	// morpheme;type;typeObject;root;number;engMean;freMean;END
	//
	private $root;
	private  $objectType;
	
	//----------------------------------------------------------------------------------------------------------

	public function __construct($fieldsAndValues) {
		$this->morpheme = $fieldsAndValues['morpheme'];
		$this->type = $fieldsAndValues['type'];
		$this->objectType = $fieldsAndValues['typeObject'];
		$this->root = $fieldsAndValues['root'];
		$this->number = $fieldsAndValues['number'];
		$this->engMeaning = $fieldsAndValues['engMean'];
		$this->frenchMeaning = $fieldsAndValues['freMean'];
		if ( isset($fieldsAndValues['dbName']) )
			$this->dbName = $fieldsAndValues['dbName'];
		if ( isset($fieldsAndValues['tableName']) )
			$this->tableName = $fieldsAndValues['tableName'];
		$this->morphemeID =self::make_id($fieldsAndValues);
	}
	
	public function parseMeaning ($string) {
		return null;
	}
	
	// An entry in the CSV file for a PD (demonstrative pronoun) has a value in the field 'root';
	// each space-separated item in that field will contribute a RPD (root of demonstrative pronoun)
	static public function generates_multiple_objects() {
		return true;
	}
	
	static public function make_multiple_ids($field_values) {
		$ids = array();
		if ( $field_values['root'] != '' ) {
			$roots = explode(' ',$field_values['root']);
			$type = 'r' . $field_values['type'];
			foreach ($roots as $a_root) {
				$field_values['morpheme'] = $a_root;
				$field_values['root'] = $a_root;
				$field_values['type'] = $type;
				$id = self::make_id($field_values);
				array_push($ids, $id);
			}
		}
		return $ids;
	}
	
	
	

	static public function make_id ($field_values) {
		$info_for_signature = array ( $field_values['type'], $field_values['typeObject'] );
		if ( $field_values['type']=='pd' ) {
			array_push($info_for_signature, $field_values['number']);
		}
		$morpheme_id_object = new ID_Morpheme(
				$field_values['morpheme'],
				$info_for_signature
		);
		return $morpheme_id_object;
	}
	
// 	public Demonstrative() {	    
// 	}
// 	public Demonstrative(HashMap v) {
// 		type = (String) v.get("type");
// 	    demonstrative(v);
// 	}

// 	public Demonstrative(HashMap v, String prefixType) {
// 		type = prefixType + (String) v.get("type");
// 	    demonstrative(v);
// 	}

// 	void demonstrative(HashMap v) {
// 		getAndSetBaseAttributes(v);
// 		number = (String) v.get("number");
// 		objectType = (String) v.get("objectType");
// 		root = (String) v.get("root");
// 		setAttrs();	    
// 	}
	
// 	//----------------------------------------------------------------------------------------------------------
// 	public void addToHash(String key, Object obj) {
// 	    hash.put(key,obj);
// 	}

// 	// Signature: <type>-<objectType> pour les adverbes démonstratifs
// 	// Signature: <type>-<objectType>[-<number>] pour les pronoms démonstratifs
// 	public String getSignature() {
// 		StringBuffer sb = new StringBuffer();
// 		sb.append(type);
// 		sb.append("-");
// 		sb.append(objectType);
// 		String nbr = null;
// 		if (isSingular())
// 			nbr = "s";
// 		else if (isDual())
// 			nbr = "d";
// 		else if (isPlural())
// 			nbr = "p";
// 		if (type.endsWith("pd") && nbr != null && !nbr.equals("")) {
// 			sb.append("-");
// 			sb.append(nbr);
// 		}
// 		return sb.toString();
// 	}
	
// 	public String getRoot() {
// 		return root;
// 	}
	
// 	public String getObjectType() {
// 		return objectType;
// 	}
	
// 	//----------------------------------------------------------------------------------------------------------
// 	void setAttrs() {
// 		setAttributes();
// 		setId();
// 	}
	
// 	void setAttributes() {
// 		HashMap demAttrs = new HashMap();
// 		demAttrs.put("root", root);
// 		demAttrs.put("objectType", objectType);
// 		super.setAttributes(demAttrs);
// 	}

// 	//----------------------------------------------------------------------------------------------------------
// 	public String showData() {
// 		StringBuffer sb = new StringBuffer();
// 		sb.append("[Demonstrative: ");
// 		sb.append("morpheme= " + morpheme + "\n");
// 		sb.append("type= " + type + "\n");
// 		sb.append("objectType= " + objectType + "\n");
// 		sb.append("root= " + root + "\n");
// 		sb.append("number= " + number + "\n");
// 		sb.append("englishMeaning= " + englishMeaning + "\n");
// 		sb.append("frenchMeaning= " + frenchMeaning + "\n");
//     	sb.append("dbName= "+dbName+"\n");
//     	sb.append("tableName= "+tableName+"\n");
// 		sb.append("]\n");
// 		return sb.toString();
// 	}


}

?>
