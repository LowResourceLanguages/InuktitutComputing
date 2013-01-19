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
import java.util.Vector;

import utilities.Debugging;

public class DemonstrativeEnding extends Affix {
	//
	String grammCase;
	String number;
	
	static Class conditionClass = null;
	static public Hashtable hash = new Hashtable();

	static String[] cases = {"abl", "acc", "dat", "gen", "loc", "nom", "sim", "via"};
	static String[] types = {"tad", "tpd"};
	//
	
	//--------------------------------------------------------------------------------------------------------
	public DemonstrativeEnding() { 
	}
	
	public DemonstrativeEnding(HashMap v) {
		morpheme = (String) v.get("morpheme");
		Debugging.mess("DemonstrativeEnding/1", 1, "morpheme= " + morpheme);
		type = (String) v.get("type");
		grammCase = (String) v.get("case");
		number = (String) v.get("number");
		englishMeaning = (String) v.get("engMean");
		frenchMeaning = (String) v.get("freMean");
		dbName = (String) v.get("dbName");
		tableName = (String) v.get("tableName");
		setAttrs();
	}
	
	//--------------------------------------------------------------------------------------------------------
	public void addToHash(String key, Object obj) {
	    hash.put(key,obj);
	}

	public String getTransitivityConstraint() {
	    return null;
	}
	
	public String[] getCombiningParts() {
	    return null;
	}

	public String getSignature() {
		StringBuffer sb = new StringBuffer();
		sb.append(type);
		sb.append("-");
		sb.append(grammCase);
		if (number != null) {
		    sb.append("-");
		    sb.append(number);
		}
		return sb.toString();
	}

	//--------------------------------------------------------------------------------------------------------
	boolean agreeWithTransitivity(String trans) {
	    return true;
	}
	
//    Vector getIdsOfCompositesWithThisRoot() {
//         return null;
//    }
    
	void setAttrs() {
		setAttributes();
		setId();
	}

    void setAttributes() {
    	HashMap endingAttrs = new HashMap();
    	endingAttrs.put("case",grammCase);
    	endingAttrs.put("number",number);
    	super.setAttributes(endingAttrs);
    }

	//--------------------------------------------------------------------------------------------------------
	public String showData() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n[DemonstrativeEnding: morpheme= " + morpheme + "\n");
		sb.append("type= " + type + "\n");
		sb.append("case= " + grammCase + "\n");
		sb.append("number= " + number + "\n");
		sb.append("englishMeaning= " + englishMeaning + "\n");
		sb.append("frenchMeaning= " + frenchMeaning + "]\n");
		return sb.toString();
	}

}
