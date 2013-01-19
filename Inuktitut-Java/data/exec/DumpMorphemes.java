package data.exec;

import java.lang.reflect.Array;
import java.util.Arrays;

import data.Base;
import data.Morpheme;
import data.Suffix;

import data.LinguisticDataAbstract;

public class DumpMorphemes {

	/**
	 * @param args
	 */
	static String lang = "en";

	public static void main(String[] args) {
		String dataSource = null;
		String typeMorphemes = "b";
		for (int j=0; j<args.length; j++) {
			String arg = args[j].toLowerCase();
			if ( arg.equals("-b") )
				typeMorphemes = "b";
			else if ( arg.equals("-s") )
				typeMorphemes = "s";
			else if ( arg.equals("-l") )
				if ( args.length > j+1 ) {
					if ( args[j+1].equals("en") || args[j+1].equals("fr") )
						lang = args[++j];
					else
						usage();
				}
				else
					usage();
			else if ( arg.equals("-d") )
				if ( args.length > j+1 )
					if ( args[j+1].equals("csv") )
						dataSource = "csv";
					else
						usage();
				else
					usage();
			else
				usage();
		}
        System.err.println("dataSource = '"+dataSource+"'");
        System.err.println("typeMorphemes = '"+typeMorphemes+"'");
        System.err.println("lang = '"+lang+"'");
        LinguisticDataAbstract.init(dataSource);
        if (typeMorphemes.equals("b"))
        	dumpBases();
        else if (typeMorphemes.equals("s"))
        	dumpSuffixes();
	}
	
	static void dumpBases() {
        String bases[] = LinguisticDataAbstract.getAllBasesIds();
        Arrays.sort(bases);
        int max = 0;
        for (int ib=0; ib<bases.length; ib++) {
        	int l = bases[ib].length();
        	if (l > max)
        		max = l;
        }
        int indent = max+4;
        for (int ib=0; ib<bases.length; ib++) {
        	Base b = LinguisticDataAbstract.getBase(bases[ib]);
        	String meaning = lang.equals("en")? b.englishMeaning : b.frenchMeaning;
        	int n = indent - bases[ib].length();
        	String spaces = "";
        	for (int is=0; is<n; is++)
        		spaces += " ";
        	System.out.println(bases[ib]+spaces+meaning);
        }
	}

	static void dumpSuffixes() {
        String suffixes[] = LinguisticDataAbstract.getAllSuffixesIds();
        Arrays.sort(suffixes);
        int max = 0;
        for (int ib=0; ib<suffixes.length; ib++) {
        	int l = suffixes[ib].length();
        	if (l > max)
        		max = l;
        }
        int indent = max+4;
        for (int ib=0; ib<suffixes.length; ib++) {
        	Suffix b = LinguisticDataAbstract.getSuffix(suffixes[ib]);
        	String meaning = lang.equals("en")? b.englishMeaning : b.frenchMeaning;
        	int n = indent - suffixes[ib].length();
        	String spaces = "";
        	for (int is=0; is<n; is++)
        		spaces += " ";
        	System.out.println(suffixes[ib]+spaces+meaning);
        }
	}
	
	static void usage() {
		System.err.println(
				"usage: dumpMorphemes [-s | -b] [-l [en | fr]] [-d csv]");
		System.exit(1);
	}

}
