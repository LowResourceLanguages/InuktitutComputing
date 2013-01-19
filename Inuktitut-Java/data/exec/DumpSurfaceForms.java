package data.exec;

import java.io.*;

import data.LinguisticDataAbstract;



public class DumpSurfaceForms {
    String classPath;
    String fileName;

    public DumpSurfaceForms() {
	classPath = System.getProperty("java.class.path",".");
	fileName = "Inuktitut_Surface_Forms.txt";
    }


    public String getClassPath() {
	return classPath;
    }


    public void dump() {
	try {
	PrintWriter out
	    = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
	String [] forms = LinguisticDataAbstract.getAllAffixesSurfaceFormsKeys();
	for (int i=0; i<forms.length; i++) {
		out.println(forms[i]);
	}
	out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
