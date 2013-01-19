/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 5-Dec-2003
 * par Benoit Farley
 * 
 */
package unitTests.data;

import java.util.Vector;

import data.Demonstrative;
import data.Lexicon;
import data.LinguisticDataAbstract;


import junit.framework.TestCase;

public class DemonstrativeTest extends TestCase {

	public DemonstrativeTest(String sourceOfData) {
		LinguisticDataAbstract.init(sourceOfData);
	}

	public void testDemonstrativeAdverb() {
		LinguisticDataAbstract.init("csv");
		dotestDemonstrativeAdverb1();
		dotestDemonstrativeAdverb2();
	}
	
	public void dotestDemonstrativeAdverb1() {
		Vector xs = Lexicon.lookForBase("pikka",false);
		if (xs==null) {
			System.out.println("pikka: Aucune base retrouv�e");
			fail();
		}
		Demonstrative ad = (Demonstrative)xs.elementAt(0);
		if (ad==null) {
			System.out.println("pikka: Objet nul");
			fail();
		}
		if (ad.morpheme==null) {
			System.out.println("pikka: morpheme nul");
			fail();
		}
		if (!ad.morpheme.equals("pikka")) {
			System.out.println("pikka: morpheme: "+ad.morpheme);
			fail();
		}
		if (ad.type==null) {
			System.out.println("pikka: type nul");
			fail();
		}
		if (!ad.type.equals("ad")) {
			System.out.println("pikka: type: " + ad.type);
			fail();
		}
		if (ad.getObjectType()==null) {
			System.out.println("pikka: objectType nul");
			fail();
		}
		if (!ad.getObjectType().equals("sc")) {
			System.out.println("pikka: objectType: " + ad.getObjectType());
			fail();
		}
		if (ad.getRoot()==null) {
			System.out.println("pikka: root nul");
			fail();
		}
		if (!ad.getRoot().equals("pik")) {
			System.out.println("pikka: root: " + ad.getRoot());
			fail();
		}
	}


	public void dotestDemonstrativeAdverb2() {
		Vector xs = Lexicon.lookForBase("pik",false);
		if (xs==null) {
			System.out.println("pik: Aucune base retrouvée");
			fail();
		}
		Demonstrative ad = (Demonstrative)xs.elementAt(0);
		if (ad==null) {
			System.out.println("pik: Objet nul");
			fail();
		}
		if (ad.morpheme==null) {
			System.out.println("pik: morpheme nul");
			fail();
		}
		if (!ad.morpheme.equals("pik")) {
			System.out.println("pik: morpheme: "+ad.morpheme);
			fail();
		}
		if (ad.type==null) {
			System.out.println("pik: type nul");
			fail();
		}
		if (!ad.type.equals("rad")) {
			System.out.println("pik: type: " + ad.type);
			fail();
		}
		if (ad.getObjectType()==null) {
			System.out.println("pik: objectType nul");
			fail();
		}
		if (!ad.getObjectType().equals("sc")) {
			System.out.println("pik: objectType: " + ad.getObjectType());
			fail();
		}
		if (ad.getRoot()==null) {
			System.out.println("pik: root nul");
			fail();
		}
		if (!ad.getRoot().equals("pik")) {
			System.out.println("pik: root: " + ad.getRoot());
			fail();
		}
	}


	
}
