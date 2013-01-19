/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 5-Dec-2003
 * par Benoit Farley
 * 
 */
package data;

import java.util.HashMap;
import java.util.Hashtable;

public class Demonstrative extends Base {
	//
	String root;
	String objectType;
	static public Hashtable hash = new Hashtable();
	//
	
	//----------------------------------------------------------------------------------------------------------
	public Demonstrative() {	    
	}
	public Demonstrative(HashMap v) {
		type = (String) v.get("type");
	    demonstrative(v);
	}

	public Demonstrative(HashMap v, String prefixType) {
		type = prefixType + (String) v.get("type");
	    demonstrative(v);
	}

	void demonstrative(HashMap v) {
		getAndSetBaseAttributes(v);
		number = (String) v.get("number");
		objectType = (String) v.get("objectType");
		root = (String) v.get("root");
		setAttrs();	    
	}
	
	//----------------------------------------------------------------------------------------------------------
	public void addToHash(String key, Object obj) {
	    hash.put(key,obj);
	}

	// Signature: <type>-<objectType> pour les adverbes démonstratifs
	// Signature: <type>-<objectType>[-<number>] pour les pronoms démonstratifs
	public String getSignature() {
		StringBuffer sb = new StringBuffer();
		sb.append(type);
		sb.append("-");
		sb.append(objectType);
		String nbr = null;
		if (isSingular())
			nbr = "s";
		else if (isDual())
			nbr = "d";
		else if (isPlural())
			nbr = "p";
		if (type.endsWith("pd") && nbr != null && !nbr.equals("")) {
			sb.append("-");
			sb.append(nbr);
		}
		return sb.toString();
	}
	
	public String getRoot() {
		return root;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	//----------------------------------------------------------------------------------------------------------
	void setAttrs() {
		setAttributes();
		setId();
	}
	
	void setAttributes() {
		HashMap demAttrs = new HashMap();
		demAttrs.put("root", root);
		demAttrs.put("objectType", objectType);
		super.setAttributes(demAttrs);
	}

	//----------------------------------------------------------------------------------------------------------
	public String showData() {
		StringBuffer sb = new StringBuffer();
		sb.append("[Demonstrative: ");
		sb.append("morpheme= " + morpheme + "\n");
		sb.append("type= " + type + "\n");
		sb.append("objectType= " + objectType + "\n");
		sb.append("root= " + root + "\n");
		sb.append("number= " + number + "\n");
		sb.append("englishMeaning= " + englishMeaning + "\n");
		sb.append("frenchMeaning= " + frenchMeaning + "\n");
    	sb.append("dbName= "+dbName+"\n");
    	sb.append("tableName= "+tableName+"\n");
		sb.append("]\n");
		return sb.toString();
	}


}
