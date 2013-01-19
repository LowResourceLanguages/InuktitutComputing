package unitTests.data;

import data.Morpheme;
import data.Suffix;
import data.LinguisticDataAbstract;

import junit.framework.TestCase;

public class SuffixTest extends TestCase {
	
	public SuffixTest(String typeOfData) {
		LinguisticDataAbstract.init(typeOfData);
	}
	
	public void testSuffix() {
		Morpheme m = LinguisticDataAbstract.getMorpheme("si/1vv");
		assertTrue("[1] No morpheme found for si/1vv", m != null);
		assertTrue("[1] Wrong morpheme", ((Suffix)m).morpheme.equals("si"));
	}

}