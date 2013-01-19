package dataCSV; 

import data.Data;
import data.LinguisticDataAbstract;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public final class LinguisticDataCSV extends LinguisticDataAbstract {
	
	static public int OK = 0;
	static public int FILE_NOT_FOUND = 1;
	static public int IOEXCEPTION = 2;
	
	public static String[][] dataTables = {
		{"Base","Inuktitut","RootsSpalding"},
		{"Base","Inuktitut","RootsSchneider"},
		{"Base","Inuktitut","WordsRelatedToRoots"},
		{"Base","Inuktitut","UndecomposableCompositeWords"},
		{"Base","Inuktitut","CommonCompositeWords"},
		{"Base","Inuktitut","Locations"},
		{"Base","Inuktitut","LoanWords"},
		{"Suffix","Inuktitut","Suffixes"},
		{"NounEnding","Inuktitut","Endings_noun"},
		{"VerbEnding","Inuktitut","Endings_verb"},
		{"VerbEnding","Inuktitut","Endings_verb_participle"},
		{"Demonstrative","Inuktitut","Demonstratives"},
		{"DemonstrativeEnding","Inuktitut","Endings_demonstrative"},
		{"Pronoun","Inuktitut","Pronouns"},
		{"VerbWord","Inuktitut","Passives_French"},
		{"VerbWord","Inuktitut","Passives_English"},
		{"Source","Inuktitut","Sources"}
	};
	static Class thisClass;

	public LinguisticDataCSV() {
		createLinguisticDataCSV(null);
	}
	/*
	 * 'type' peut être 'r' ou 's' ou null
	 */
	public LinguisticDataCSV(String type) {
		createLinguisticDataCSV(type);
	}
	
	void createLinguisticDataCSV(String type) {
		if (type==null) {
			bases = new Hashtable();
			basesId = new Hashtable();
			words = new Hashtable();
			surfaceFormsOfAffixes = new Hashtable();
			affixesId = new Hashtable();		
		}
		else if (type.equals("r")) {
        	type = "Base";
			bases = new Hashtable();
			basesId = new Hashtable();
			words = new Hashtable();
        }
        else if (type.equals("s")) {
        	type = "Suffix";
			surfaceFormsOfAffixes = new Hashtable();
			affixesId = new Hashtable();
        }
		sources = new Hashtable();
		for (int i=0; i < dataTables.length; i++) {
			if (type==null || dataTables[i][0].equals(type) ||
					dataTables[i][0].equals("Source"))
				readLinguisticDataCSV(dataTables[i]);
		}
	}
	
	public static void initAffixes() {
		affixesId = new Hashtable();
		surfaceFormsOfAffixes = new Hashtable();
	}


    public static int readLinguisticDataCSV(String [] data) {
    	String type = data[0];
    	String dbName = data[1];
    	String tableName = data[2];    	
    	BufferedReader f;
        try {
        	String fileName = tableName+".csv";
            InputStream is = LinguisticDataCSV.class.getResourceAsStream(fileName);
            f =  new BufferedReader(new InputStreamReader(is));
            String firstLine = f.readLine();
            String [] fieldNames = firstLine.split(",");
        	String line;
        	while ( (line=f.readLine()) != null) {
                    HashMap nextRow = getNextRow(line,fieldNames);
                    // ajouter le nom de la base de données et le nom de la table
                    nextRow.put("dbName", dbName);
                    nextRow.put("tableName", tableName);
                    if (type.equals("Base")) {
                        Data.makeBase(nextRow);
                    } else if (type.equals("Suffix")) {
                        Data.makeSuffix(nextRow);
                    } else if (type.equals("NounEnding")) {
                        Data.makeNounEnding(nextRow);
                    } else if (type.equals("VerbEnding")) {
                        Data.makeVerbEnding(nextRow);
                    } else if (type.equals("Demonstrative")) {
                        Data.makeDemonstrative(nextRow);
                    } else if (type.equals("DemonstrativeEnding")) {
                        Data.makeDemonstrativeEnding(nextRow);
                    } else if (type.equals("Pronoun")) {
                        Data.makePronoun(nextRow);
                    } else if (type.equals("VerbWord")) {
                        Data.makeVerbWord(nextRow);
                    } else if (type.equals("Source")) {
                        Data.makeSource(nextRow);
                    }
                }
        } catch (FileNotFoundException e) {
//			e.printStackTrace();
        	return FILE_NOT_FOUND;
		} catch (IOException e) {
//			e.printStackTrace();
			return IOEXCEPTION;
		}
		return OK;
    }

    public static HashMap getNextRow(String line, String [] fieldNames) {
        HashMap currentRow = new HashMap();
        Vector values = new Vector();
        boolean inString = false;
        int pos = 0;
        char c = 0;
        for (int i=0; i<line.length(); i++) {
        	c=line.charAt(i);
        	if (c=='"')
        		if (inString)
        			if (i<line.length()-1)
        				if (line.charAt(i+1)!='"')
        					inString = false;
        				else
        					++i;
        			else
        				;
        		else
        			inString = true;
        	else if (c==',')
        		if (!inString) {
        			values.add(line.substring(pos, i));
        			pos = i+1;
        		}
        }
        if (c==',')
        	values.add("");
        else
        	values.add(line.substring(pos));
        String [] valuesStr = (String[])values.toArray(new String[0]);
        for (int i = 0; i < valuesStr.length; i++) {
        	try {
            currentRow.put(fieldNames[i], removeQuotesAndNull(valuesStr[i]));
        	} catch (ArrayIndexOutOfBoundsException e) {
        		System.err.println("line: "+line);
        	}
        }
        return currentRow;
    }

static String removeQuotesAndNull(String str) {
	if (str.equals(""))
		return null;
	else if (str.charAt(0)=='"') {
		String s = str.replaceFirst("^\"", "");
		String newStr = s.replaceFirst("\"$", "");
		newStr = newStr.replaceAll("\"\"", "\"");
		return newStr;		
	} else
		return str;
}

}
