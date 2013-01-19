package dataCompiled;

import data.Data;
import data.LinguisticDataAbstract;
import java.util.HashMap;
import java.util.Hashtable;

public final class LinguisticDataCompiled extends LinguisticDataAbstract {
	/*
	 * 'source' peut être null, 'r' ou 's'
	 */
public LinguisticDataCompiled() {
	_compile_bases();
	_compile_suffixes();
	_compile_sources();
}

public LinguisticDataCompiled(String type) {
	if (type==null || type.equals("r")) {
		_compile_bases();
	}
	if (type==null || type.equals("s")) {
		_compile_suffixes();
	}
	_compile_sources();
}

public void _compile_bases() {
		bases = new Hashtable();
		basesId = new Hashtable();
		words = new Hashtable();
		LinguisticDataCompiledBase.process();
		LinguisticDataCompiledDemonstrative.process();
		LinguisticDataCompiledPronoun.process();
		LinguisticDataCompiledVerbWord.process();
}

public void _compile_suffixes() {
		surfaceFormsOfAffixes = new Hashtable();
		affixesId = new Hashtable();
		LinguisticDataCompiledSuffix.process();
		LinguisticDataCompiledNounEnding.process();
		LinguisticDataCompiledVerbEnding.process();
		LinguisticDataCompiledDemonstrativeEnding.process();
}

public void _compile_sources() {
	sources = new Hashtable();
	LinguisticDataCompiledSource.process();
}

public static void processBase(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeBase(hm);
	}

public static void processSuffix(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeSuffix(hm);
	}

public static void processPronoun(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makePronoun(hm);
	}

public static void processDemonstrative(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeDemonstrative(hm);
	}

public static void processNounEnding(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeNounEnding(hm);
	}

public static void processVerbEnding(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeVerbEnding(hm);
	}

public static void processDemonstrativeEnding(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeDemonstrativeEnding(hm);
	}

public static void processVerbWord(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeVerbWord(hm);
	}

public static void processSource(String [] data) {
	HashMap hm = new HashMap();
	for (int j=0; j<data.length; j=j+2) {
		hm.put(data[j],data[j+1]);
		}
	Data.makeSource(hm);
	}

}
