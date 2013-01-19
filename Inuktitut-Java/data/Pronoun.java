/*
 * Conseil national de recherche Canada 2005/
 * National Research Council Canada 2005
 * 
 * Cr�� le / Created on Jul 7, 2005
 * par / by Benoit Farley
 * 
 */
package data;

import java.util.HashMap;
import java.util.Hashtable;

public class Pronoun extends Base {
	//
	String person;
	static public Hashtable hash = new Hashtable();
	//
	
    //------------------------------------------------------------------------   
    public Pronoun() {
    }
    
    public Pronoun(HashMap v) {
		getAndSetBaseAttributes(v);
		type = (String) v.get("type");
		number = (String) v.get("number");
		variant = (String) v.get("variant");
		nb = (String)v.get("nb");
		if (nb==null || nb.equals(""))
			nb = "1";
		nature = (String)v.get("nature");
		person = (String)v.get("per");
		String comb = (String)v.get("combination");
		if (comb != null) {
			setCombiningParts(comb);
		}
		setAttrs();
    }
    
    //------------------------------------------------------------------------   
	public void addToHash(String key, Object obj) {
	    hash.put(key,obj);
	}

    //------------------------------------------------------------------------   
	void setAttrs() {
		setAttributes();
		setId();
	}

    void setAttributes() {
    	HashMap prAttrs = new HashMap();
    	prAttrs.put("person", person);
    	super.setAttributes(prAttrs);
    }

	
	boolean isFirstPerson() {
		if ( person.equals("1"))
			return true;
		else
			return false;
	}
	
	boolean isSecondPerson() {
		if ( person.equals("2"))
			return true;
		else
			return false;
	}
	
	boolean isThirdPerson() {
		if ( person.equals("3"))
			return true;
		else
			return false;
	}
	
    //------------------------------------------------------------------------   
	public String showData() {
		StringBuffer sb = new StringBuffer();
		sb.append("[Pronoun: morpheme= " + morpheme + "\n");
		sb.append("id= "+id+"\n");
		sb.append("variant= " + variant + "\n");
		sb.append("nb= " + nb + "\n");
		sb.append("type= " + type + "\n");
		sb.append("nature= " + nature + "\n");
		sb.append("per= " + person + "\n");
		sb.append("number= " + number + "\n");
		sb.append("englishMeaning= " + englishMeaning + "\n");
		sb.append("frenchMeaning= " + frenchMeaning + "\n");
    	sb.append("dbName= "+dbName+"\n");
    	sb.append("tableName= "+tableName+"\n");
    	sb.append("]");
 		return sb.toString();
	}



}
