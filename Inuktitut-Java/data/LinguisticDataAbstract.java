/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 25-Nov-2003 par Benoit Farley
 *  
 */
package data;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import phonology.Dialect;

import dataCSV.LinguisticDataCSV;
import dataCompiled.LinguisticDataCompiled;
import script.Orthography;
import script.Roman;

import utilities.MyStringTokenizer;

public abstract class LinguisticDataAbstract {

    static Hashtable affixesExp = new Hashtable(); // suffixes, terminaisons (expérimental)
    static Hashtable surfaceFormsOfAffixesExp = new Hashtable();  // (expérimental)

    static Hashtable textualRenderings;
    static Hashtable examples;
    static LinguisticDataAbstract database;

    protected static Hashtable surfaceFormsOfAffixes;
    protected static Hashtable bases;
    
    protected static Hashtable basesId;
//    protected Hashtable demonstrativesId;
    protected static Hashtable affixesId;

    protected static Hashtable words;
    protected static Hashtable sources;
    
    static public Hashtable groupsOfConsonants = new Hashtable();

    /*
     * source: "csv" - from the .csv files
     *         null - from the dumped database
     * type:   "r" - roots
     *         "s" - suffixes
     */
    public static boolean init() {
    	return init(null, null);
    }
    
    public static boolean init(String source) {
    	return init(source, null);
    }
    
    public static boolean init(String source, String type) {
    	if (source != null && !source.equals("csv") && !source.equals("compiled"))
    		return false;
    	if (type != null && !type.equals("r") && !type.equals("s"))
    		return false;
        makeHashOfTextualRenderings();
        makeHashOfExamples();
        if (source==null || source.equals("compiled"))
            database = new LinguisticDataCompiled(type);
        else if (source.equals("csv"))
        	database = new LinguisticDataCSV(type);
        if (type==null || type.equals("s")) {
            // Ajouter le suffixe d'inchoativité.
            // 13 mars 2006: pour le moment, on décide de ne pas l'utiliser, mais
            // de plutôt placer dans la table des racines celles qui résultent de
            // ce processus, comme ikummaq- < ikuma-
            Suffix spec = new Inchoative();
            affixesId.put(spec.id, spec);
            Data.addToForms(spec, spec.morpheme);
        }
        makeGroupsOfConsonants();
        return true;
    }
    
    private static void makeGroupsOfConsonants() {
    	if (LinguisticDataAbstract.bases != null) {
    		String [] keys = LinguisticDataAbstract.getAllBasesKeys();
    		for (int i=0; i<keys.length; i++) {
    			String key = Orthography.simplifiedOrthographyLat(keys[i]);
                groupsOfConsonants(key);
    		}
    	}
    	if (LinguisticDataAbstract.surfaceFormsOfAffixes != null) {
    		String [] forms = getAllAffixesSurfaceFormsKeys();
    		for (int i=0; i<forms.length; i++)
                groupsOfConsonants(forms[i]);
    	}
    	String keys[] = Dialect.getKeys();
    	for (int i=0; i<keys.length; i++)
    		groupsOfConsonants(keys[i]);
    }
    
    private static void groupsOfConsonants(String str) {
        char chars[] = str.toCharArray();
        for (int i=0; i<chars.length-1; i++) {
            if (Roman.isConsonant(chars[i]) &&
                    Roman.isConsonant(chars[i+1])) {
                Character charac = new Character(chars[i+1]);
                Vector grCons;
                if (groupsOfConsonants.containsKey(charac))
                    grCons = (Vector)groupsOfConsonants.get(charac);
                else
                    grCons = new Vector();
                String newGr = new String(new char[]{chars[i],chars[i+1]});
                if (!grCons.contains(newGr))
                    grCons.add(newGr);
                groupsOfConsonants.put(charac,grCons);
            }
        }
    }

    
    
    // The suffix table has a field where the components of
    // composite suffixes are defined.  This method adds an
    // entry in a hashtable for each such combination, with
    // the first component as the key of the entry.
//    public void addToCombinationTable() {
//        
//    }

    //--------------------------------------------------------------


    public static Morpheme getMorpheme(String morphId) {
        Morpheme morph;
        morph = getBase(morphId);
        if (morph==null)
            morph = getAffix(morphId);
        return morph;
    }
    
    public static Affix getAffix(String uniqueId) {
        Affix aff = (Affix)affixesId.get(uniqueId);
        return aff;
    }

    public static Suffix getSuffix(String uniqueId) {
        Suffix afs = (Suffix)affixesId.get(uniqueId);
        return afs;
    }
    
    protected static VerbWord getVerbWord(String term) {
        VerbWord mv = (VerbWord)words.get(term);
        return mv;
    }

    protected static Source getSource(String sourceId) {
        Source s = (Source)sources.get(sourceId);
        return s;
    }

    // Returns a Vector of Base and Demonstrative objects, or null.
    public static Vector getBases(String term) {
        Vector bs = null;
        Vector gets = (Vector) bases.get(term);
        if (gets != null)
            bs = (Vector)gets.clone();
        return bs;
    }
    
    protected static Vector getExample(String key) {
        Vector ex = (Vector)examples.get(key);
        return ex;
    }
    

    
    public static Base getBase(String morphId) {
    	Base b = null;
    	if (basesId != null)
    		b = (Base)basesId.get(morphId);
        return b;
    }
    
    protected static Base getBase(Morpheme.Id morphId) {
    	Base b = null;
    	if (basesId != null)
    		b = (Base)basesId.get(morphId.id);
        return b;
    }
    
    // The keys of the hashtable 'surfaceFormsOfAffixes' are in the
    // simplified spelling (ng > N).  To search for a form
    // in the ICI spelling, one calls this method.
    public static Vector getSurfaceForms(String form) {
        // Simplify the spelling
        String simplifiedForm = Orthography.simplifiedOrthographyLat(form);
        return (Vector)surfaceFormsOfAffixes.get(simplifiedForm);
    }
    
    public static SurfaceFormOfAffix getForm(String morph) {
    	return (SurfaceFormOfAffix)((Vector)LinguisticDataAbstract.getSurfaceForms(morph)).elementAt(0);
    }
    
    protected static void addForm(String str, Object form) {
        String simplifiedForm = Orthography.simplifiedOrthographyLat(str);
		Vector v = (Vector)surfaceFormsOfAffixes.get(simplifiedForm);
		if (v == null)
			v = new Vector();
		v.add(form);
		surfaceFormsOfAffixes.put(simplifiedForm, v);
    }
    
    protected static String[] getAllExamplesKeys() {
    	return (String[])examples.keySet().toArray(new String[0]);
    }
    
    public static String[] getAllAffixesSurfaceFormsKeys() {
    	return (String[]) surfaceFormsOfAffixes.keySet().toArray(new String[] {});
    }
    
    protected static Hashtable getAllSuffixes() {
        Hashtable hash = Suffix.hash;
        if (hash.size()==0) {
            for (Enumeration keys = affixesId.keys(); keys.hasMoreElements();) {
                Object key = keys.nextElement();
                Affix aff = (Affix)affixesId.get(key);
                aff.addToHash((String)key,aff);
            }
        }
        return Suffix.hash;
    }
    
    public static String[] getAllBasesIds() {
    	return (String[])basesId.keySet().toArray(new String[0]);
    }

    public static String[] getAllAffixesIds() {
    	return (String[])affixesId.keySet().toArray(new String[0]);
    }

    public static String[] getAllSuffixesIds() {
    	Hashtable suffixes = getAllSuffixes();
    	String [] suffixesIds = new String[suffixes.size()];
    	int i=0;
    	for (Enumeration keys = suffixes.keys(); keys.hasMoreElements();) {
    		Suffix suf = (Suffix)suffixes.get(keys.nextElement());
    		suffixesIds[i++] = suf.id;
    	}
    	return suffixesIds;
    }

    protected static String[] getAllVerbWords() {
    	return (String[])words.keySet().toArray(new String[0]);
    }

    protected static String[] getAllSources() {
    	return (String[])sources.keySet().toArray(new String[0]);
    }


    protected static Hashtable getAllRoots() {
        Base sample = new Base();
        Class clazz = sample.getClass();
        Hashtable hash = Base.hash;
        if (hash.size()==0) {
            for (Enumeration keys = basesId.keys(); keys.hasMoreElements();) {
                Object key = keys.nextElement();
                Object obj = basesId.get(key);
                if (obj.getClass()==clazz) {
                    Base root = (Base)basesId.get(key);
                    root.addToHash((String)key,root);
                }
            }
        }
        return Base.hash;
    }
    
    protected static String [] getAllBasesKeys() {
    	return (String[])bases.keySet().toArray(new String[0]); 	
    }
    
    
    protected static Base [] getGiVerbs() {
    	Hashtable giverbsHash = new Hashtable();
    	Hashtable bases = getAllRoots();
    	for (Enumeration keys = bases.keys(); keys.hasMoreElements();) {
    		Object key = keys.nextElement();
    		Base base = (Base)bases.get(key);
    		if (base.isGiVerb())
    			giverbsHash.put(key, base);
    	}
    	String [] giverbsKeysArray = (String[])giverbsHash.keySet().toArray(new String[]{});
    	Arrays.sort(giverbsKeysArray);
    	Base [] giverbs = new Base[giverbsKeysArray.length];
    	for (int i=0; i<giverbsKeysArray.length; i++)
    		giverbs[i] = (Base)giverbsHash.get(giverbsKeysArray[i]);
    	return giverbs;
    }

    
    protected static Hashtable getAllDemonstratives() {
        Demonstrative sample = new Demonstrative();
        Class clazz = sample.getClass();
        Hashtable hash = Demonstrative.hash;
        if (hash.size()==0) {
            for (Enumeration keys = basesId.keys(); keys.hasMoreElements();) {
                Object key = keys.nextElement();
                Object obj = basesId.get(key);
                if (obj.getClass()==clazz) {
                    Demonstrative dem = (Demonstrative)basesId.get(key);
                    dem.addToHash((String)key,dem);
                }
            }
        }
        return Demonstrative.hash;
    }

    
    protected static String getTextualRendering(String key, String lang) {
        return _getTextualRendering(key,lang);
    }

    
    protected static String getTextualRendering(String key, String lang, String additionalText,
            int position) {
        String res = _getTextualRendering(key,lang);
        if (additionalText != null) {
            if (position == 0)
                res = additionalText + " " + res;
            else
                res = res + " " + additionalText;
        }
        String keyPlus = key + "+";
        Object valPlus = textualRenderings.get(keyPlus);
        if (valPlus != null) {
            String[] textualRenderingsPlus = (String[]) valPlus;
            res = res + ".  "
                    + (lang.equals("en") ? textualRenderingsPlus[0] : textualRenderingsPlus[1]);
        }
        return res;
    }

    protected static String _getTextualRendering(String key, String lang) {
        Object val = textualRenderings.get(key);
        String res = null;
        if (val == null)
            res = "";
        else {
            String[] txt = (String[]) val;
            res = lang.equals("en") ? txt[0] : txt[1];
        }
        return res;
    }
    
    private static boolean makeHashOfTextualRenderings() {
        textualRenderings = new Hashtable();
        textualRenderings.put("dec", new String[] { "declarative", "déclaratif" });
        textualRenderings.put("int", new String[] { "interrogative", "interrogatif" });
        textualRenderings.put("imp", new String[] { "imperative", "impératif" });
        textualRenderings.put("part", new String[] { "participle", "participe" });
        textualRenderings.put("prespas", new String[] { "present and past",
                "présent et passé" });
        textualRenderings.put("fut", new String[] { "future", "futur" });
        textualRenderings.put("pos", new String[] { "positive", "positif" });
        textualRenderings.put("neg", new String[] { "negative", "négatif" });
        textualRenderings.put("caus", new String[] { "becausative", "causatif" });
        textualRenderings.put("freq", new String[] { "frequentative", "fréquentatif" });
        textualRenderings.put("cond", new String[] { "conditional", "conditionnel" });
        textualRenderings.put("dub", new String[] { "dubitative", "dubitatif" });
        textualRenderings.put("tv",
                new String[] { "verbal ending", "terminaison verbale" });
        textualRenderings.put("q", new String[] { "tail suffix", "suffixe de queue" });
        textualRenderings.put("tn",
                new String[] { "noun ending", "terminaison nominale" });
        textualRenderings.put("sv", new String[] { "verbal suffix", "suffixe verbal" });
        textualRenderings.put("sn", new String[] { "noun suffix", "suffixe nominal" });
        textualRenderings
                .put("function",
                        new String[] { "producing a", "produisant un" });
        textualRenderings.put("vv", new String[] { "verb-to-verb", "verbe-à-verbe" });
        textualRenderings.put("nv", new String[] { "noun-to-verb", "nom-à-verbe" });
        textualRenderings.put("vn", new String[] { "verb-to-noun", "verbe-à-nom" });
        textualRenderings.put("nn", new String[] { "noun-to-noun", "nom-à-nom" });
        textualRenderings.put("nsp", new String[] { "non-specific", "non-spécifique" });
        textualRenderings.put("sp", new String[] { "specific", "spécifique" });
        textualRenderings.put("s", new String[] { "singular", "singulier" });
        textualRenderings.put("d", new String[] { "dual", "duel" });
        textualRenderings.put("p", new String[] { "plural", "plural" });
        textualRenderings.put("n", new String[] { "noun", "nom" });
        textualRenderings.put("v", new String[] { "verb", "verbe" });
        textualRenderings.put("a", new String[] { "adverb", "adverbe" });
        textualRenderings.put("e", new String[] { "expression or exclamation",
                "expression ou exclamation" });
        textualRenderings.put("c", new String[] { "conjunction", "conjonction" });
        textualRenderings.put("pr", new String[] { "pronoun", "pronom" });
        textualRenderings.put("m", new String[] { "medial", "médiane" });
        textualRenderings.put("t", new String[] { "terminal", "terminale" });
        textualRenderings.put("f", new String[] { "final", "finale" });
        textualRenderings.put("V", new String[] { "vowel", "voyelle" });
        textualRenderings.put("C", new String[] { "consonant", "consonne" });
        textualRenderings.put("VV", new String[] { "vowels", "voyelles" });
        textualRenderings.put("VC", new String[] { "vowel or consonant",
                "voyelle ou consonne" });
        textualRenderings.put("1ordinal", new String[] { "1st", "1ère" });
        textualRenderings.put("2ordinal", new String[] { "2nd", "2ème" });
        textualRenderings.put("3ordinal", new String[] { "3rd", "3ème" });
        textualRenderings.put("4ordinal", new String[] { "4th", "4ème" });
        textualRenderings.put("personne", new String[] { "person", "personne" });

        textualRenderings.put("nom", new String[] { "nominative", "nominatif" });
        textualRenderings.put("gen", new String[] { "genitive", "génitif" });
        textualRenderings.put("acc", new String[] { "accusative", "accusatif" });
        textualRenderings.put("abl", new String[] { "ablative", "ablatif" });
        textualRenderings.put("dat", new String[] { "dative", "datif" });
        textualRenderings.put("loc", new String[] { "locative", "locatif" });
        textualRenderings.put("sim", new String[] { "similaris", "similaris" });
        textualRenderings.put("via", new String[] { "vialis", "vialis" });
        textualRenderings.put("possessif", new String[] { "possessive", "possessif" });
        textualRenderings.put("possesseur", new String[] { "Possessor", "Possesseur" });
        textualRenderings.put("vt", new String[] { "transtitive", "transitif" });
        textualRenderings.put("vt1", new String[] { "transtitive", "transitif" });
        textualRenderings.put("vt2", new String[] { "transtitive", "transitif" });
        textualRenderings.put("vi", new String[] { "intransitive", "transitive" });
        textualRenderings.put("va", new String[] { "adjectival", "adjectif" });
        textualRenderings.put("ve", new String[] { "emotion, feeling",
                "d'émotion,de sentiment" });
        textualRenderings.put("vres", new String[] { "result", "de résultat" });
        textualRenderings
                .put(
                        "vres+",
                        new String[] {
                                "When this kind of verb is used transitively, the thing upon which the action "
                                        + "is done is the object of the verb.  When it is used intransitively, that thing "
                                        + "is the subject of the verb.  Similar verbs in English: to boil, to shatter.",
                                "Quand ce type de verbe est utilisé transitivement, la chose sur laquelle porte l'action "
                                        + "est l'objet du verbe.  Quand il est utilisé intransitivement, cette chose "
                                        + "est le sujet du verbe." });
        textualRenderings
                .put(
                        "m!",
                        new String[] {
                                "must be followed by another suffix, i.e. it cannot occur in word-final position",
                                "doit être suivi d'un autre suffixe, i.e. il ne peut pas se trouver à la fin d'un mot" });
        textualRenderings
                .put(
                        "f!",
                        new String[] {
                                "occurs only in word-final position, i.e. it cannot be followed by another suffix",
                                "ne peut se trouver qu'à la fin d'un mot, i.e. il ne peut pas être suivi d'un autre suffixe" });
        textualRenderings
                .put(
                        "t!",
                        new String[] {
                                "may occur in word-final position, but may also be followed by additional suffixes",
                                "peut se trouver à la fin d'un mot, mais peut cependant être suivi d'autres suffixes" });
        textualRenderings.put("neutre",
                new String[] { "does not affect", "n'affecte pas" });
        textualRenderings.put("suppr", new String[] { "deletes", "supprime" });
        textualRenderings.put("suppr1", new String[] { "is deleted", "est supprimé" });
        textualRenderings.put("nasal", new String[] { "nasalizes", "nasalise" });
        textualRenderings.put("nasal1", new String[] { "is nasalized to",
                "est nasalisé en" });
        textualRenderings.put("sonor", new String[] { "vocalizes", "sonorise" });
        textualRenderings.put("assim", new String[] { "assimilates", "assimile" });
        textualRenderings.put("assim2", new String[] { "to", "précédent à" });
        textualRenderings.put("allonge", new String[] { "lengthens", "allonge" });
        textualRenderings.put("fusion", new String[] { "fusions", "fusionne" });
        textualRenderings.put("sonor", new String[] { "vocalizes", "sonorise" });
        textualRenderings.put("sur", new String[] { "on", "sur" });
        textualRenderings.put("en", new String[] { "into", "en" });
        textualRenderings.put("au", new String[] { "to the", "au" });
        textualRenderings.put("du", new String[] { "of the", "du" });
        textualRenderings.put("à", new String[] { "to", "à" });
        textualRenderings.put("avec", new String[] { "with", "avec" });
        textualRenderings.put("voyellefinale", new String[] { "end vowel",
                "voyelle finale" });
        textualRenderings.put("finale", new String[] { "final", "finale" });
        textualRenderings.put("le", new String[] { "the", "le" });
        textualRenderings.put("la", new String[] { "the", "la" });
        textualRenderings.put("duradical", new String[] { "of the stem", "du radical" });
        textualRenderings
                .put("dusuffixe",
                        new String[] { "of the suffix", "du suffixe" });
        textualRenderings.put("ins1", new String[] { "inserts", "insére" });
        textualRenderings.put("devantsuffixe", new String[] { "in front of the suffix",
                "devant le suffixe" });
        textualRenderings.put("derniereVoyelle", new String[] { "last vowel",
                "dernière voyelle" });
        textualRenderings.put("casVV", new String[] { "the stem ends with 2 vowels",
                "le radical se termine par 2 voyelles" });
        textualRenderings.put("supprv2", new String[] { "the second vowel is deleted",
                "la seconde voyelle est supprimée" });
        textualRenderings.put("après", new String[] { "After", "Après" });
        textualRenderings.put("une", new String[] { "a", "une" });
        textualRenderings.put("deux", new String[] { "two", "deux" });
        textualRenderings.put("il", new String[] { "it", "il" });
        textualRenderings.put("et", new String[] { "and", "et" });
        textualRenderings.put("si", new String[] { "if", "si" });
        textualRenderings.put("estSing", new String[] { "is", "est" });
        textualRenderings.put("l'", new String[] { "the", "l'" });
        textualRenderings.put("inconnue", new String[] { "unknown", "inconnue" });

        // sources
        textualRenderings
        .put(
                "A1",
                new String[] {
                        "Alex Spalding, \"Inuktitut - A Grammar of North Baffin Dialects\", Wuerz Publishing Ltd., Winnipeg, 1992",
                        "Alex Spalding, \"Inuktitut - A Grammar of North Baffin Dialects\", Wuerz Publishing Ltd., Winnipeg, 1992" });
        textualRenderings
                .put(
                        "A2",
                        new String[] {
                                "A. Spalding, \"Inuktitut - A Multi-dialectal Outline Dictionary (with an Aivilingmiutaq base)\", Nunavut Arctic College, 1998",
                                "A. Spalding, \"Inuktitut - A Multi-dialectal Outline Dictionary (with an Aivilingmiutaq base)\", Nunavut Arctic College, 1998" });
        textualRenderings
                .put(
                        "H1",
                        new String[] {
                                "Kenn Harper, \"Suffixes of the Eskimo dialects of Cumberland Peninsula and North Baffin Island\", National Museum of Man, Mercury Series, Canadian Ethnology Service, Paper no. 54, Ottawa, 1979",
                                "Kenn Harper, \"Suffixes of the Eskimo dialects of Cumberland Peninsula and North Baffin Island\", Musée national de l'Homme, Collecion Mercure, Service canadien d'ethnologie, Dossier no. 54, Ottawa, 1979" });
        textualRenderings
                .put(
                        "H2",
                        new String[] {
                                "Kenn Harper, \"Some aspects of the grammar of the Eskimo dialects of Cumberland Peninsula and North Baffin Island\", National Museum of Man, Mercury Series, Ethnology Division, Paper no. 15, Ottawa, 1974",
                                "Kenn Harper, \"Some aspects of the grammar of the Eskimo dialects of Cumberland Peninsula and North Baffin Island\", Musée national de l'Homme, Collection Mercure, Division d'ethnologie, Dossier no. 15, Ottawa, 1974" });
        textualRenderings
                .put(
                        "M1",
                        new String[] {
                                "M. Mallon, \"Introductory Inuktitut Reference Grammar version 2.1\", Nunavut Arctic College, Ittukuluuk Language Programs, Iqaluit & Victoria, 1995",
                                "M. Mallon, \"Introductory Inuktitut Reference Grammar version 2.1\", Nunavut Arctic College, Ittukuluuk Language Programs, Iqaluit & Victoria, 1995" });
        textualRenderings.put("Hnsrd", new String[] { "Hansards of Nunavut",
                "Hansards du Nunavut" });
        textualRenderings
                .put(
                        "S1",
                        new String[] {
                                "L. Schneider, \"Dictionnaire des infixes de la langue eskimaude\", Minist�re des Affaires culturelles, Direction générale du Patrimoine, Dossier 43, 1979",
                                "L. Schneider, \"Dictionnaire des infixes de la langue eskimaude\", Minist�re des Affaires culturelles, Direction générale du Patrimoine, Dossier 43, 1979" });
        textualRenderings
                .put(
                        "S2",
                        new String[] {
                                "L. Schneider, \"Ulirnaisigutiit - An Inuktitut-English Dictionary of Northern Quebec, Labrador and Eastern Arctic Dialects\", Les Presses de l'Université Laval, Québec, 1985",
                                "L. Schneider, \"Ulirnaisigutiit - An Inuktitut-English Dictionary of Northern Quebec, Labrador and Eastern Arctic Dialects\", Les Presses de l'Université Laval, Québec, 1985" });
        // 	textualRenderings.put("",new String [] {"",""});
        // 	textualRenderings.put("",new String [] {"",""});
        // 	textualRenderings.put("",new String [] {"",""});
        return true;
    }

    //--------------------------------------------------------------------------------------------------------------
    // EXAMPLES
    private static boolean makeHashOfExamples() {
        try {
            BufferedReader r = null;
            String line;
            boolean eof;

            // Le "UTF-8" assume que le fichier lexiqueSyl.dat contient des
            // caract�res cod�s de cette fa�on, et assure que les caract�res
            // lus seront en unicode.
            InputStream is = new Examples().getExampleStream();
            if (is != null) {
                InputStreamReader isr;
                try {
                    isr = new InputStreamReader(is, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    isr = new InputStreamReader(is);
                }
                r = new BufferedReader(isr);
            }
            examples = new Hashtable();
            eof = false;

            while (r != null && !eof) {
                // Lire une ligne du fichier.
                // Chaque ligne contient, s�par�s par un espace ou par un tab:
                // <term> <id> <ex. lat.> <ex. syll.> <trad. angl.> <trad.
                // fran.>
                // * * <ex. lat.> ...
                //
                // Un * * est un exemple pour le m�me terme.
                // <id> est la signature unique d'un suffixe = numero + function
                // ou numero + 'q' si le suffixe est un suffixe de queue.
                line = r.readLine();
                if (line == null)
                    eof = true;
                else {
                    MyStringTokenizer mst = new MyStringTokenizer(line, ' ',
                            '"');
                    Vector v = new Vector();
                    Vector current;
                    Example ex;
                    String term = null;
                    String id = null;
                    String key;
                    //					st.slashSlashComments(true); // reconnaissance de '//'
                    // pour ligne de commentaire
                    //					st.quoteChar((int) '"'); // cha�ne de caract�res entre
                    // deux "
                    //					st.wordChars(33, (int) '"' - 1);
                    //					st.wordChars((int) '"' + 1, (int) '/' - 1);
                    //					st.wordChars((int) '/' + 1, 5760);
                    //					typeToken = StreamTokenizer.TT_EOL; // initialisation �
                    // int -= TT_EOF
                    //					while (typeToken != StreamTokenizer.TT_EOF) {
                    //						st.nextToken();
                    //						if (st.ttype == StreamTokenizer.TT_EOF)
                    //							typeToken = st.ttype;
                    //						else if (st.ttype == StreamTokenizer.TT_NUMBER)
                    //							v.add(Integer.toString((int) st.nval));
                    //						else
                    //							v.add(st.sval);
                    //					}
                    while (mst.hasMoreTokens()) {
                        v.add(mst.nextToken());
                    }
                    if (v.size() != 0) {
                        if (((String) v.elementAt(0)).equals("*")) {
                            v.setElementAt(term, 0);
                            v.setElementAt(id, 1);
                        } else {
                            term = (String) v.elementAt(0);
                            id = (String) v.elementAt(1);
                        }
                        ex = new Example(v);
                        // Ajouter � la table de hachage.
                        // Chaque cl� contient un vecteur, parce qu'un
                        // mot peut avoir plus d'un exemple.
                        // On ajoute � ce vecteur.
                        key = term + id;
                        current = (Vector) examples.get(key);
                        if (current == null)
                            current = new Vector();
                        current.add(ex);
                        examples.put(key, current);
                    }
                } // else
            } // while (!eof)
            if (r!=null)
                r.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return true;
    } // makeHashOfExamples

}