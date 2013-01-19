package unitTests.data;

import data.LinguisticDataAbstract;
import data.Morpheme;
import junit.framework.TestCase;

public class MorphemeTest extends TestCase {
	
	public MorphemeTest(String sourceOfData) {
		LinguisticDataAbstract.init(sourceOfData);
	}

	public void testCombine() {
		String combination;
		String combined;
		String expected;
		combination = "pujjuk/1v+i/1vv";
		expected = "pujjui";
		combined = Morpheme.combine(combination, true, null);
		assertTrue("Wrong result: '"+combined+"'; should have been '"+expected+"'",combined.equals(expected));
	}

}
