package unitTests;

import java.util.Arrays;
import java.util.Hashtable;

import fonts.TextCat;
import junit.framework.TestCase;

public class TextCatTest extends TestCase {

	public void ___testGetLanguages() {
		String [] languages = TextCat.getLanguages();
		Arrays.sort(languages);
		String [] targetLanguages = new String[]{"inuktitut_aujaq","inuktitut_prosyl"};
		assertTrue("Wrong number of languages: "+languages.length+" should have been "+targetLanguages.length,languages.length == targetLanguages.length);
		for (int i=0; i<targetLanguages.length; i++)
			assertTrue("Wrong language: \""+languages[i]+"\" should have been \""+targetLanguages[i]+"\"",languages[i].equals(targetLanguages[i]));
	}
	
	public void ___testGetWords () {
		String [] words = TextCat.getWords("my friends' friends are my friends too");
		String [] targetWords = new String[]{"my","friends'","friends","are","my","friends","too"};
		assertTrue("Wrong number of words: "+words.length+" should have been "+targetWords.length,words.length == targetWords.length);
		for (int i=0; i<targetWords.length; i++)
			assertTrue("Wrong word: \""+words[i]+"\" should have been \""+targetWords[i]+"\"",words[i].equals(targetWords[i]));
	}
	
	public void ___testTextCat_Comparator () {
		String [] n = new String[]{
				"julie","louise","martine","suzie"
		};
		Hashtable hash = new Hashtable();
		hash.put("julie", new Integer(52));
		hash.put("louise", new Integer(44));
		hash.put("martine", new Integer(83));
		hash.put("suzie", new Integer(35));
		String [] targetn = new String[]{
				"suzie","louise","julie","martine"
		};
		Arrays.sort(n, new TextCat.TextCat_Comparator(hash,true));
		for (int i=0; i<targetn.length; i++)
			assertTrue("Wrong order: n["+i+"]="+n[i]+" should be "+targetn[i],n[i].equals(targetn[i]));
	}
	
	public void ___testClassify_1 () {
		String input = "Î©Ã’Ã¯OEÃ’Ã©Ã®Ã©â‰¤Ã’ Ã„Ã’Ã¶âˆ‚Ã„Ã•Ã�â€ºÂ±Â´ Ã–Ã®Ã­âˆ‚Ã’ÃªÂ¿â€°â‰¤Ã�Â´Ãº Ã–Â±Ã˜ Ã¶Ã˜Ã®Ã©Ã–Ã�â‰¤Ã�Â´ÃºÃœÎ â‰¤Ã’Ï€Ã˜Ã€â‰¤Ãº Ã„Ã’Ã¶âˆ‚Ã„Ã•Ã’Ã©â‰¤Ãº";
		String  language = TextCat.classify(input);
		assertTrue("Wrong language: \""+language+"\" should have been \"inuktitut_aujaq\"",language.equals("inuktitut_aujaq"));
	}
	
	public void testClassify_2 () {
		String input = "wcNw/6t5tJ5 si4]vz5 ]x8i3i3u8i4";
		String language = TextCat.classify(input);
		assertTrue("Wrong language: \""+language+"\" should have been \"inuktitut_prosyl\"",language.equals("inuktitut_prosyl"));
	}

}
