package unitTests.dataCSV;

import java.util.HashMap;

import dataCSV.LinguisticDataCSV;


import junit.framework.TestCase;

public class LinguisticDataCSVTest extends TestCase {

	public void testGetNextRow() {
		String [] champs = new String[]{
				"morpheme","variant","nb","type","transitivity","nature","number",
				"compositionRoot","plural","intransSuffix","transSuffix","antipassive",
				"engMean","freMean","source","dialect","cf"	
		};
		String ligne = "ilaaniq,,1,v,i,,,,,,gi/4vv,,to do s.t. purposefully or with intent,\"faire qqch. intentionnellement, dans un but précis\"";
		HashMap hm = LinguisticDataCSV.getNextRow(ligne,champs);
		assertTrue("mauvais morpheme",hm.get("morpheme").toString().equals("ilaaniq"));
		assertTrue("mauvaise variant",hm.get("variant")==null);
		assertTrue("mauvais nb",hm.get("nb").toString().equals("1"));
		assertTrue("mauvais type",hm.get("type").toString().equals("v"));
		assertTrue("mauvaise transitivity",hm.get("transitivity").toString().equals("i"));
		assertTrue("mauvaise nature",hm.get("nature")==null);
		assertTrue("mauvais number",hm.get("number")==null);
		assertTrue("mauvais compositionRoot",hm.get("compositionRoot")==null);
		assertTrue("mauvaise plural",hm.get("plural")==null);
		assertTrue("mauvais intransSuffix",hm.get("intransSuffix")==null);
		assertTrue("mauvais transSuffix",hm.get("transSuffix").toString().equals("gi/4vv"));
		assertTrue("mauvais antipassive",hm.get("antipassive")==null);
		assertTrue("mauvais engMean",hm.get("engMean").toString().equals("to do s.t. purposefully or with intent"));
		assertTrue("mauvais freMean",hm.get("freMean").toString().equals("faire qqch. intentionnellement, dans un but précis"));
		assertTrue("mauvaise source",hm.get("source")==null);
		assertTrue("mauvais dialect",hm.get("dialect")==null);
		assertTrue("mauvais cf",hm.get("cf")==null);
	
		champs = new String[]{
				"morpheme","variant","nb","type","transitivity","nature","number",
				"intransSuffix","transSuffix","antipassive",
				"engMean","freMean","combination","dialect","root",
				"rootType","rootNb"
		};

		ligne = "aksut,,,a,,,,,,,\"(1) exhortation to apply pressure: \"\"Apply pressure!\"\", \"\"Do it with force!\"\" (2) \"\"That's right!\"\"; \"\"I certainly agree!\"\"\",\"(1) exhortation à appliquer une pression: \"\"Force!\"\" (2) \"\"C'est vrai!\"\"; \"\"Je suis bien d'accord!\"\"\",,,aksuq,n,1";
		hm = LinguisticDataCSV.getNextRow(ligne,champs);
		assertTrue("mauvais morpheme",hm.get("morpheme").toString().equals("aksut"));
		assertTrue("mauvaise variant",hm.get("variant")==null);
		assertTrue("mauvais nb",hm.get("nb")==null);
		assertTrue("mauvais type",hm.get("type").toString().equals("a"));
		assertTrue("mauvaise transitivity",hm.get("transitivity")==null);
		assertTrue("mauvaise nature",hm.get("nature")==null);
		assertTrue("mauvais number",hm.get("number")==null);
		assertTrue("mauvais intransSuffix",hm.get("intransSuffix")==null);
		assertTrue("mauvais transSuffix",hm.get("transSuffix")==null);
		assertTrue("mauvais antipassif",hm.get("antipassive")==null);
		assertTrue("mauvais engMean",hm.get("engMean").toString().equals("(1) exhortation to apply pressure: \"Apply pressure!\", \"Do it with force!\" (2) \"That's right!\"; \"I certainly agree!\""));
		assertTrue("mauvais freMean",hm.get("freMean").toString().equals("(1) exhortation à appliquer une pression: \"Force!\" (2) \"C'est vrai!\"; \"Je suis bien d'accord!\""));
		assertTrue("mauvaise combination",hm.get("combination")==null);
		assertTrue("mauvais dialect",hm.get("dialect")==null);
		assertTrue("mauvais root",hm.get("root").toString().equals("aksuq"));
		assertTrue("mauvais rootType",hm.get("rootType").toString().equals("n"));
		assertTrue("mauvais rootNb",hm.get("rootNb").toString().equals("1"));
	}

}
