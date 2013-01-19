package fonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextCat {

	private static TextCat ref;

	private TextCat() {
	}

	public static TextCat getSingletonObject() {
		if (ref == null)
			// it's ok, we can call this constructor
			ref = new TextCat();
		return ref;
	}

	static String nonWordCharactersPattern = "[\\s]+";
	static String wordCharactersPattern = "[^\\s]+";
	static int opt_a = 10;
	static int opt_f = 0;
	static int opt_t = 400;
	static String opt_d = "LM";

	public static String classify(String input) {
		String [] languages = getLanguages();
		String [] unknown = create_lm(input);
		Hashtable results = new Hashtable();
		int maxp = opt_t;
		TextCat textcat = getSingletonObject();
		String packageName = textcat.getClass().getPackage().getName();
		File dir = new File(packageName, opt_d);
		for (int i=0; i<languages.length; i++) {
			String language = languages[i];
			// load the language model into hash
			Hashtable ngram = new Hashtable();
			int rang = 1;
			try {
				File file = new File(dir,language+".lm");
				FileInputStream fis = new FileInputStream(file);
//				InputStreamReader isr = new InputStreamReader(fis,"cp1252");
				InputStreamReader isr = new InputStreamReader(fis,"iso-8859-1");
				BufferedReader br = new BufferedReader(isr);
				String line;
				Pattern pat = Pattern.compile("^("+wordCharactersPattern+")\\s+[0-9]+");
				try {
					while ( (line=br.readLine()) != null) {
						Matcher mpat = pat.matcher(line);
						if (mpat.find())
						{
							ngram.put(mpat.group(1), new Integer(rang++));
						}
					}
					br.close();
					/* 
					 * Compare the language model with input ngrams list
					 */
					int p = 0;
					for (int j=0; j<unknown.length; j++) {
						Integer val = (Integer)ngram.get(unknown[j]);
						if (val != null) {
							p = p + Math.abs( val.intValue()-j  );
						} else {
							p = p + maxp;
						}
					}
					results.put(language,new Integer(p));
				} catch (IOException e) {
				}
			} catch (FileNotFoundException e) {
			} catch (UnsupportedEncodingException e) {
			}
		}
		String [] sorted = (String [])results.keySet().toArray(new String[0]);
		Arrays.sort(sorted, new TextCat_Comparator(results,true));
		return sorted[0];
	}

	//------------------------------------------------------------
	public static String [] create_lm(String input) {
		Hashtable ngram = new Hashtable();
		String [] words = getWords(input);
		for (int i=0; i<words.length; i++) {
			String word = "_"+words[i]+"_";
			int len = word.length();
			int flen = len;
			for (int j=0; j<flen; j++) {
				if (len > 4) {
					String key = word.substring(j, j+5);
					Integer n = (Integer)ngram.get(key);
					if (n==null)
						n = new Integer(0);
					int nv = n.intValue();
					n = new Integer(++nv);
					ngram.put(key, n);
				}
				if (len > 3) {
					String key = word.substring(j, j+4);
					Integer n = (Integer)ngram.get(key);
					if (n==null)
						n = new Integer(0);
					int nv = n.intValue();
					n = new Integer(++nv);
					ngram.put(key, n);
				}
				if (len > 2) {
					String key = word.substring(j, j+3);
					Integer n = (Integer)ngram.get(key);
					if (n==null)
						n = new Integer(0);
					int nv = n.intValue();
					n = new Integer(++nv);
					ngram.put(key, n);
				}
				if (len > 1) {
					String key = word.substring(j, j+2);
					Integer n = (Integer)ngram.get(key);
					if (n==null)
						n = new Integer(0);
					int nv = n.intValue();
					n = new Integer(++nv);
					ngram.put(key, n);
				}
				{
					String key = word.substring(j, j+1);
					Integer n = (Integer)ngram.get(key);
					if (n==null)
						n = new Integer(0);
					int nv = n.intValue();
					n = new Integer(++nv);
					ngram.put(key, n);
				}
				len--;
			}
		}
		/*
		 * As suggested by Karel P. de Vos, k.vos@elsevier.nl, we speed up
		 * sorting by removing singletons; but results for short inputs are
		 * very bad.
		 */
		for (Enumeration e = ngram.keys(); e.hasMoreElements(); ) {
			Object key = e.nextElement();
			if (((Integer)ngram.get(key)).intValue() <= opt_f)
				ngram.remove(key);
		}
		/*
		 * Sort the ngrams, and spit out the opt_t frequent ones. Adding
		 * "or $a cmp $b" in the sort block makes sorting five times slower,
		 * although it would be somewhat nicer (unique result)
		 */
		String [] sorted = (String [])ngram.keySet().toArray(new String[0]);
		Arrays.sort(sorted, new TextCat_Comparator(ngram,false));
		String [] sorted_t = sub_array(sorted,0,opt_t);
		return sorted_t;
	}

	//------------------------------------------------------------
	public static String[] getLanguages() {
		TextCat textcat = getSingletonObject();
		String packageName = textcat.getClass().getPackage().getName();
		File dir = new File(packageName, opt_d);
		String[] lmFilenames = dir.list(new TextCat_FilenameFilter(dir));
		for (int i = 0; i < lmFilenames.length; i++) {
			lmFilenames[i] = lmFilenames[i].replace(".lm", "");
		}
		return lmFilenames;
	}

	//------------------------------------------------------------
	public static void main(String[] args) {
		if (args[0].equals("-l")) {
			String[] languages = getLanguages();
			for (int i = 0; i < languages.length; i++) {
				System.out.println(languages[i]);
			}
		} else if (args[0].equals("-c")) {
			String input = args[1];
			String font = classify(input);
			System.out.println(font);
		}
	}

	//------------------------------------------------------------
	public static String[] getWords(String text) {
		String[] words = text.split(nonWordCharactersPattern);
		return words;
	}
	
	public static String [] sub_array (String [] array, int start, int length) {
		if (array.length <= length)
			return array;
		else {
			String [] newArray = new String[length];
			for (int i=0; i<length; i++)
				newArray[i] = array[i];
			return newArray;
		}
	}

	static class TextCat_FilenameFilter implements FilenameFilter {

		File dir;

		public TextCat_FilenameFilter(File dir) {
			this.dir = dir;
		}

		public boolean accept(File dir, String name) {
			if (dir == this.dir && name.matches(".+\\.lm$"))
				return true;
			else
				return false;
		}
	}
	
	public static class TextCat_Comparator implements Comparator {

		Hashtable hash;
		boolean increasingOrder;
		
		public TextCat_Comparator (Hashtable hash, boolean increasingOrder) {
			this.hash = hash;
			this.increasingOrder = increasingOrder;
		}
		
		public int compare(Object arg0, Object arg1) {
			if ( ((Integer)hash.get(arg0)).intValue() >  ((Integer)hash.get(arg1)).intValue() )
				if (increasingOrder)
					return 1;
				else
					return -1;
			else if ( ((Integer)hash.get(arg0)).intValue() <  ((Integer)hash.get(arg1)).intValue() )
				if (increasingOrder)
					return -1;
				else
					return 1;
			else
				return 0;
		}
		
	}
}
