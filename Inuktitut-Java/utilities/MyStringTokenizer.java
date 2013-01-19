package utilities;

import java.util.Vector;
import java.lang.String;

public class MyStringTokenizer {
	private Vector tokens;
	private int nTokens;
	private int compteur = 0;

	public MyStringTokenizer(String s, char del, char carChaine) {
		tokens = new Vector();
		int ls = s.length();
		int deb = 0;
		boolean dansChaine = false;
		for (int k = 0; k < ls; k++) {
			char c = s.charAt(k);
			if (c == del && !dansChaine) {
				if (s.charAt(deb) == carChaine && s.charAt(k - 1) == carChaine)
					tokens.add(s.substring(deb + 1, k - 1));
				else
					tokens.add(s.substring(deb, k));
				deb = k + 1;
			} else if (c == carChaine)
				if (dansChaine)
					dansChaine = false;
				else
					dansChaine = true;
		}
		if (s.charAt(deb) == carChaine && s.charAt(ls - 1) == carChaine)
			tokens.add(s.substring(deb + 1, ls - 1));
		else
			tokens.add(s.substring(deb, ls));
		nTokens = tokens.size();
	}

	public int countTokens() {
		return nTokens;
	}

	public boolean hasMoreTokens() {
		return (compteur == nTokens) ? false : true;
	}

	public Object nextToken() {
		return tokens.elementAt(compteur++);
	}

	public void reset() {
		compteur = 0;
	}

}
