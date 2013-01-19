/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 10-Dec-2003 par Benoit Farley
 *  
 */
package data.exec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import dataCSV.LinguisticDataCSV;


public class CompileLinguisticData {
	//
    private static String pack = "dataCompiled";
    private static String baseFileName = pack + "\\LinguisticDataCompiled";
    private static StringBuffer toBeWritten = null;
	private static String[][] tablesCSV = LinguisticDataCSV.dataTables;
	//
	
	//-----------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        init();
    }
    
	public static void init() {
        System.out.println("Écriture du fichier principal:");
        writeMainFile();
        System.out.println("Écriture des fichiers secondaires:");
        writeSecondaryFiles();
        System.out.println("Terminé");
    }

	//-----------------------------------------------------------------------------------------------------------
    private static void writeMainFile() {
        String outName = baseFileName + ".java";
        File outFile = new File(outName);
        System.out.println("\t" + outFile.getAbsolutePath());
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("package " + pack + ";");
        out.println();
        out.println("import data.Data;");
        out.println("import data.LinguisticDataAbstract;");
        out.println("import java.util.HashMap;");
        out.println("import java.util.Hashtable;");
        out.println();
        out.println("public final class LinguisticDataCompiled"
                + " extends LinguisticDataAbstract {");
        out.println("\t/*");
        out.println("\t * 'source' peut être null, 'r' ou 's'");
        out.println("\t */");
        out.println("public LinguisticDataCompiled() {");
        out.println("\t_compile_bases();");
        out.println("\t_compile_suffixes();");
        out.println("\t_compile_sources();");
        out.println("}");
        out.println("");
        out.println("public LinguisticDataCompiled(String type) {");
        out.println("\tif (type==null || type.equals(\"r\")) {");
        out.println("\t\t_compile_bases();");
        out.println("\t}");
        out.println("\tif (type==null || type.equals(\"s\")) {");
        out.println("\t\t_compile_suffixes();");
        out.println("\t}");
        out.println("\t_compile_sources();");
        out.println("}");
        out.println("");
        out.println("public void _compile_bases() {");
        out.println("\t\tbases = new Hashtable();");
        out.println("\t\tbasesId = new Hashtable();");
        out.println("\t\twords = new Hashtable();");
        out.println("\t\tLinguisticDataCompiledBase.process();");
        out.println("\t\tLinguisticDataCompiledDemonstrative.process();");
        out.println("\t\tLinguisticDataCompiledPronoun.process();");
        out.println("\t\tLinguisticDataCompiledVerbWord.process();");
        out.println("}");
        out.println("");
        
        out.println("public void _compile_suffixes() {");
        out.println("\t\tsurfaceFormsOfAffixes = new Hashtable();");
        out.println("\t\taffixesId = new Hashtable();");
        out.println("\t\tLinguisticDataCompiledSuffix.process();");
        out
                .println("\t\tLinguisticDataCompiledNounEnding.process();");
        out.println("\t\tLinguisticDataCompiledVerbEnding.process();");
        out
                .println("\t\tLinguisticDataCompiledDemonstrativeEnding.process();");
        out.println("}");
        out.println("");
        
        out.println("public void _compile_sources() {");
        out.println("\tsources = new Hashtable();");
        out.println("\tLinguisticDataCompiledSource.process();");
        out.println("}");
        out.println("");
       
        out.println("public static void processBase(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeBase(hm);");
        out.println("\t}");
        out.println();
        out.println("public static void processSuffix(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeSuffix(hm);");
        out.println("\t}");
        out.println();
        out.println("public static void processPronoun(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makePronoun(hm);");
        out.println("\t}");
        out.println();
        out
                .println("public static void processDemonstrative(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeDemonstrative(hm);");
        out.println("\t}");
        out.println();
        out
                .println("public static void processNounEnding(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeNounEnding(hm);");
        out.println("\t}");
        out.println();
        out
                .println("public static void processVerbEnding(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeVerbEnding(hm);");
        out.println("\t}");
        out.println();
        out
                .println("public static void processDemonstrativeEnding(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeDemonstrativeEnding(hm);");
        out.println("\t}");
        out.println();
        out.println("public static void processVerbWord(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeVerbWord(hm);");
        out.println("\t}");
        out.println();
        out.println("public static void processSource(String [] data) {");
        out.println("\tHashMap hm = new HashMap();");
        out.println("\tfor (int j=0; j<data.length; j=j+2) {");
        out.println("\t\thm.put(data[j],data[j+1]);");
        out.println("\t\t}");
        out.println("\tData.makeSource(hm);");
        out.println("\t}");
        out.println();
        out.println("}"); // class
        out.close();
    }

    //-----------------------------------------------------------------
    private static void writeSecondaryFiles() {
    	Hashtable types2tables = new Hashtable();
    	for (int i=0; i<tablesCSV.length; i++) {
    		Vector tbs = (Vector)types2tables.get(tablesCSV[i][0]);
    		if (tbs==null) tbs = new Vector();
    		tbs.add(tablesCSV[i][2]);
    		types2tables.put(tablesCSV[i][0], tbs);
    	}

    	for (Enumeration e= types2tables.keys(); e.hasMoreElements();) {
            String type = (String) e.nextElement();
            String tables[] = (String[])((Vector)types2tables.get(type)).toArray(new String[]{});
            String outName = baseFileName+type+".java";
            File outFile = new File(outName);
            System.out.println("\t"+outFile.getAbsolutePath());
            PrintStream out = null;
            try {
                out = new PrintStream(new FileOutputStream(outFile),true,"utf-8");
                initSecondaryFile(type, out);
                writeSecondaryFile(type, tables, out);
                completeSecondaryFile(out);
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void writeSecondaryFile(String type, String tables[],
            PrintStream out) {
        toBeWritten = new StringBuffer();
		int n = 0;
        String nameOfMethod = "process0";
        out.println("private static void " + nameOfMethod + " () {");
        toBeWritten.append("\t" + nameOfMethod + "();"+System.getProperty("line.separator"));
        for (int i=0; i<tables.length; i++) {
            n = writeTableCSV(tables[i], type, n, out);	
        }
        out.println("}");
        out.println();
    }
    
    private static int writeTableCSV(String tableCSV, String type, int n, PrintStream out) {
		BufferedReader f = null;
		try {
			InputStream is = LinguisticDataCSV.class
					.getResourceAsStream(tableCSV + ".csv");
			f = new BufferedReader(new InputStreamReader(is));
			String firstLine;
			try {
				firstLine = f.readLine();
				String[] fields = firstLine.split(",");
				String line;
				while ((line = f.readLine()) != null) {
					HashMap valuesOfFields = LinguisticDataCSV.getNextRow(line, fields);
					addToData(valuesOfFields,type,out,firstLine,line);
					n++;
			        if (n >= 100 && n % 100 == 0) {
			            out.println("}");
			            out.println();
			            String nameOfMethod = "process" + new Integer(n).toString();
			            out.println("private static void " + nameOfMethod + " () {");
			            toBeWritten.append("\t" + nameOfMethod + "();\n");
			        }
				}
			} catch (IOException e) {
				e.printStackTrace();
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(1);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return n;
	}

    private static void addToData(HashMap valuesOfFields, String type, PrintStream out,
    		String lineOfFieldnames, String lineOfValues) {
        String value = null;
        out.print("LinguisticDataCompiled.process" + type + "(new String [] {");
        Iterator it = valuesOfFields.keySet().iterator();
        int i=0;
        while (it.hasNext()) {
            if (i++ > 0)
                out.print(",");
            String field = (String)it.next();
            out.print("\"" + field + "\",");
            try {
            value = (String)valuesOfFields.get(field);
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            	System.err.println("lineOfFieldnames: \""+lineOfFieldnames+"\"");
            	System.err.println("lineOfValues: \""+lineOfValues+"\"");
            	System.err.println("i= "+i);
            	System.err.println("field= "+field);
            	System.err.println("type: "+type);
            	System.exit(1);
            }
            if (value == null)
                out.print(value);
            else {
                value = replaceQuotes(value);
                out.print("\"" + value + "\"");
            }
        }
        out.print("});\n");
    }

    private static String replaceQuotes(String str) {
        char[] chars = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
            if (chars[i] == '"')
                sb.append("\\\"");
            else
                sb.append(chars[i]);
        return sb.toString();
    }

    private static void initSecondaryFile(String type, PrintStream out) {
        out.println("package " + pack + ";");
        out.println();
        out.println("public class LinguisticDataCompiled" + type + " {");
        out.println();
        /*
         * Ici, on ajoutera les méthodes processN, écrites au fur et à mesure, et
         * la méthode process, sauvegardée dans toBeWritten.
         */
    }

    private static void completeSecondaryFile(PrintStream out) {
        out.println("public static void process() {");
        out.println(toBeWritten.toString());
        out.println("}"); // process
        out.println("}"); // class
    }

    //--------------------------------------------------------------------------
    
}