package utilities1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


public class Util {

	public static String getNomClasse(Object objet) {
		Class cla = objet.getClass();
		String claName = cla.getName();
		Package pack = cla.getPackage();
		String packName = pack.getName();
		return claName.substring(packName.length() + 1);
	}

	public static String premiereMaj(String chaine) {
		return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
	}

	public static BufferedReader ouvrirFichier(String fichier) {
		BufferedReader br = null;
		InputStream is = ClassLoader.getSystemResourceAsStream(fichier);
		if (is != null) {
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		}
		return br;
	}

	public static BufferedReader ouvrirFichier(String fichier, String encodage) {
		BufferedReader br = null;
		InputStream is = ClassLoader.getSystemResourceAsStream(fichier);
		if (is != null) {
			InputStreamReader isr;
			try {
				isr = new InputStreamReader(is, encodage);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				isr = new InputStreamReader(is);
			}
			br = new BufferedReader(isr);
		}
		return br;
	}

	public static String array2string(Object[] array) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(array[i].toString());
			sb.append(", ");
		}
        if (array.length > 0)
            sb.append(array[array.length - 1]);
		return sb.toString();
	}
	
	public static String array2string(int [] array) {
		StringBuffer sb = new StringBuffer("[array: ");
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(new Integer(array[i]).toString());
			sb.append("; ");
		}
		if (array.length > 0)
			sb.append(array[array.length - 1]);
		sb.append("]");
		return sb.toString();
	}
	
	
	public static String array2string(char [] array) {
		StringBuffer sb = new StringBuffer("[array: ");
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(new Integer(array[i]).toString());
			sb.append("; ");
		}
		if (array.length > 0)
			sb.append(array[array.length - 1]);
		sb.append("]");
		return sb.toString();
	}
	// En Inuktitut �crit en caract�res latins, le H est toujours
	// en majuscule.  Il nous faut donc une m�thode sp�ciale pour
	// cela.
	public static String enMinuscule(String chaine) {
		String s = chaine.toLowerCase();
		String s2 = s.replace('h','H');
		return s2;
	}
	
		
	public static String[] hashToArgsArray(Hashtable hash) {
	    String array[] = hashToArray(hash);
	    for (int i=0; i<array.length; i=i+2)
	        array[i] = "-" + array[i];
	    return array;
	}
	
	
	public static String[] hashToArray(Hashtable hash) {
	    String array[];
	    int size = hash.size();
	    array = new String[size*2];
	    int iArr = 0;
	    for (Enumeration e = hash.keys(); e.hasMoreElements();) {
	        Object key = e.nextElement();
	        String value = (String)hash.get(key);
	        array[iArr++] = (String)key;
	        array[iArr++] = value;
	    }
	    return array;
	}
	
    public static String getArgument(String[] args, String key) {
		String value = null;
		boolean found = false;
		for (int i=0; i<args.length; i++) {
			if (args[i].startsWith("-"))
				if (args[i].substring(1).equals(key))
					if (!args[i+1].startsWith("-")) {
						value = args[++i];
						break;
					}
			i++;
		}
		return value;
	}

    public static String[] setArgument(String [] args, String key, String value) {
        boolean found = false;
        int i = 0;
        int lim = args.length;
        while (!found && i < lim) {
            if (args[i].substring(1).equals(key)) {
                found = true;
                args[++i] = value;
            }
            else {
                i += 2;
            }
        }
        if (!found) {
            List l = Arrays.asList(args);
            ArrayList al = new ArrayList(l);
            al.add("-"+key);
            al.add(value);
            args = (String[])al.toArray(new String[]{});
        }
        return args;
    }

    public static String prepareForRegexp(String x) {
        if (x.length()==0)
            return x;
          else {
              StringBuffer sb = new StringBuffer();
              for (int i=0; i<x.length(); i++) {
                  char c = x.charAt(i);
                  if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))
                      sb.append(c);
                  else {
                      sb.append("\\").append(c);
                  }
              }
              return sb.toString();
          }
        }
    
    public static boolean isVowel(char c) {
        if ("aeiouAEIOUàâéèêëîïôùûü".indexOf(c) >= 0)
            return true;
        else
            return false;
    }

}
