package data.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import data.LinguisticDataAbstract;
import data.Suffixes;

import applications.DescriptionOfSuffix;

public class DumpDescriptionsOfSuffixes {

	/**
	 * @param args
	 */
	
	static String nl = System.getProperty("line.separator");
	
	public static void main(String[] args) {
		boolean test = false;
		boolean plain = false;
		for (int i=0; i<args.length; i++)
			if (args[i].equals("test"))
				test = true;
			else if (args[i].equals("plain"))
				plain = true;
		LinguisticDataAbstract.init("csv", "s");
		String outFileName = "allSuffixes";
		String argsDef[];
		if (plain) {
			outFileName += ".txt";
			argsDef = new String[6];
		}
		else{
			outFileName += ".html";
			argsDef = new String[4];
		}
		File outFile = new File(outFileName);
		String listOfSuffixes[];
		PrintStream out;
		try {
			if (test) {
				listOfSuffixes = new String[] { "miniq/1nn", "nialauq/1vv" };
				out = new PrintStream(System.out, true, "utf-8");
			} else {
				listOfSuffixes = LinguisticDataAbstract.getAllAffixesIds();
				out = new PrintStream(new FileOutputStream(outFile), true, "utf-8");
			}
			Arrays.sort(listOfSuffixes);
			if (!plain) {
				out.println("<html><head><title>Inuktitut Suffixes</title>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
				out.println("<style type=\"text/css\">");
				out.println(readCss());
				out.println("</style>");
				out.println("</head><body>");
			}
			System.out.println("Nb. suffixes = " + listOfSuffixes.length);
			argsDef[0] = "-l";
			argsDef[1] = "en";
			argsDef[2] = "-m";
			if (plain) {
				argsDef[4] = "-plain";
				argsDef[5] = "1";
			}
			for (int i = 0; i < listOfSuffixes.length; i++) {
				System.out.println("suffixe : '" + listOfSuffixes[i] + "'");
				argsDef[3] = listOfSuffixes[i];
				Suffixes.getDef(argsDef, out);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static String readCss () {
		File f = new File("c:\\Program Files\\Apache Software Foundation\\Apache2.2\\htdocs\\ressources_globales\\inuktitut_global.css");
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(f));
			String line;
			while ( (line=bf.readLine()) != null)
				sb.append(line+nl);
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
