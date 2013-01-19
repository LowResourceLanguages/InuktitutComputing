/*
 * Conseil national de recherche Canada 2004/
 * National Research Council Canada 2004
 * 
 * Créé le / Created on Nov 22, 2004
 * par / by Benoit Farley
 * 
 */
package script;

import java.util.Arrays; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fonts.Font;


public class Orthography {

    static int doubleVowelsSyl[] = { 5131, 5124, 5126, 5265, 5260, 5262,
            5422, 5417, 5419, 5235, 5230, 5232, 5339, 5334, 5336, 5291, 5286,
            5288, 5320, 5315, 5317, 5177, 5170, 5172, 5508, 5504, 5506, 5452,
            5447, 5449, 5365, 5360, 5362, 5206, 5199, 5201, 5466, 5462, 5464,
            5524, 5520, 5522, 5541, 5537, 5539, 5750, 5746, 5748, 5498, 5494,
            5496 };

    static int vowelsSyl[] = { 5130, 5131, 5123, 5124, 5125, 5126, 5264,
            5265, 5259, 5260, 5261, 5262, 5421, 5422, 5416, 5417, 5418, 5419,
            5234, 5235, 5229, 5230, 5231, 5232, 5338, 5339, 5333, 5334, 5335,
            5336, 5290, 5291, 5285, 5286, 5287, 5288, 5319, 5320, 5314, 5315,
            5316, 5317, 5176, 5177, 5169, 5170, 5171, 5172, 5507, 5508, 5503,
            5504, 5505, 5506, 5451, 5452, 5446, 5447, 5448, 5449, 5364, 5365,
            5359, 5360, 5361, 5362, 5205, 5206, 5198, 5199, 5200, 5201, 5465,
            5466, 5461, 5462, 5463, 5464, 5523, 5524, 5519, 5520, 5521, 5522,
            5540, 5541, 5536, 5537, 5538, 5539, 5749, 5750, 5745, 5746, 5747,
            5748, 5497, 5498, 5493, 5494, 5495, 5496 };

    //-------------------------------------------------------------------------------
    // Pour faciliter l'analyse des termes, on convertit certains caractères
    //-------------------------------------------------------------------------------
    
    
    //--------------------------------- simplifié -----------------------------------
    // Orthographe simplifié pour simplifier les diverses tâches d'analyse du texte
    // inuktitut
    public static String simplifiedOrthography(String term, boolean isSyllabic) {
        return (isSyllabic ? simplifiedOrthographySyl(term)
                : Orthography.simplifiedOrthographyLat(term));
    }

    // Remplacement de:
    //   nng par NN
    //   ng N
    public static String simplifiedOrthographyLat(String term) {
        StringBuffer sb = new StringBuffer();
        int lengthTerm = term.length();
        for (int i = 0; i < lengthTerm; i++) {
            if (term.charAt(i) == 'n') {
                if (i < lengthTerm - 1) {
                    if (term.charAt(i + 1) == 'n') {
                        if (i < lengthTerm - 2 && term.charAt(i + 2) == 'g') {
                            //sb.append("X");
                            sb.append("NN");
                            i = i + 2;
                        } else {
                            sb.append("nn");
                            i = i + 1;
                        }
                    } else if (term.charAt(i + 1) == 'g') {
                        sb.append("N");
                        i = i + 1;
                    } else {
                        sb.append(term.substring(i, i + 1));
                    }
                } else {
                    sb.append(term.substring(i, i + 1));
                }
            } else {
                sb.append(term.substring(i, i + 1));
            }
        }
        return sb.toString();
    }
 


    // Pré-traitement du mot à translittérer en syllabique.
    // (Utilisée par translittererLatSyl)
    // Remplacement de:
    //   nng par X
    //   ng N
    //   qq par qk
    public static String simplifiedOrthographyLatB(String term) {
        StringBuffer sb = new StringBuffer();
        int lengthTerm = term.length();
        for (int i = 0; i < lengthTerm; i++) {
            if (term.charAt(i) == 'n') {
                if (i < lengthTerm - 1) {
                    if (term.charAt(i + 1) == 'n') {
                        if (i < lengthTerm - 2 && term.charAt(i + 2) == 'g') {
                            sb.append("X");
                            //sb.append("NN");
                            i = i + 2;
                        } else {
                            sb.append("nn");
                            i = i + 1;
                        }
                    } else if (term.charAt(i + 1) == 'g') {
                        sb.append("N");
                        i = i + 1;
                    } else {
                        sb.append(term.substring(i, i + 1));
                    }
                } else {
                    sb.append(term.substring(i, i + 1));
                }
            } else if (term.charAt(i) == 'q' &&
                    i < lengthTerm -1 && term.charAt(i+1) == 'q') {
                	sb.append("qk");
                	i = i + 1;
            } else {
                sb.append(term.substring(i, i + 1));
            }
        }
        return sb.toString();
    }

    // Remplacement de:
    //    qkV par qqV
    //    Vpointée VV
    // 
    public static String simplifiedOrthographySyl(String term) {
        Arrays.sort(doubleVowelsSyl);
        StringBuffer sb = new StringBuffer();
        int lengthTerm = term.length();
        int indDV;
        try {
//            OutputStreamWriter out = new OutputStreamWriter(System.out, "UTF-8");
    
            for (int i = 0; i < lengthTerm; i++) {
                char charac = term.charAt(i);
                // Caractère avec voyelle pointée (double): indV >= 0
                indDV = Arrays.binarySearch(doubleVowelsSyl, (int) charac);
    
                if (charac == (char) 5509) { // q
                    if (i < lengthTerm - 1) {
                        char charac1 = term.charAt(i + 1);
                        if (charac1 == (char) 5234) { // q.ka > q.qa
                            sb.append(new char[] { 5509, 5507 });
                            i += 1;
                        } else if (charac1 == (char) 5229) { // q.ki > q.qi
                            sb.append(new char[] { 5509, 5503 });
                            i += 1;
                        } else if (charac1 == (char) 5231) { // q.ku > q.qu
                            sb.append(new char[] { 5509, 5505 });
                            i += 1;
                        } else if (charac1 == '\u1473') { // q.kaa > q.qa.a
                            sb.append(new char[] { 5509, 5507, '\u140A' });
                            i += 1;
                        } else if (charac1 == '\u146E') { // q.kii > q.qi.i
                            sb.append(new char[] { 5509, 5503, '\u1403' });
                            i += 1;
                        } else if (charac1 == '\u1470') { // q.kuu > q.qu.u
                            sb.append(new char[] { 5509, 5505, '\u1405' });
                            i += 1;
                       } else
                            sb.append(term.substring(i, i + 1));
                    } else
                        sb.append(term.substring(i, i + 1));
                } else if (indDV >= 0) {
                    sb.append(new String(Roman
                            .longToDouble(charac))); // CVV > CV.V
                } else {
                    sb.append(term.substring(i, i + 1));
                }
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }


    //---------------------------- ICI --------------------------------------
    // Orthographe selon le standard du "Inuit Cultural Institute"
    // à partir d'un texte à orthographe simplifié.
    public static String orthographyICI(String term, boolean isSyllabic) {
        return (isSyllabic ? Orthography.orthographyICISyl(term)
                : Orthography.orthographyICILat(term));
    }

    // Remplacement de:
    //   NN par nng
    //   N ng
    public static String orthographyICILat(String term) {
        int lengthTerm = term.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lengthTerm; i++) {
            if (term.charAt(i) == 'X')
                sb.append("nng");
            else if (term.charAt(i) == 'N')
                if (i < lengthTerm - 1 && term.charAt(i + 1) == 'N') {
                    sb.append("nng");
                    i = i + 1;
                } else
                    sb.append("ng");
            else
                sb.append(term.substring(i, i + 1));
        }
        return sb.toString();
    }

    public static String orthographyICILat(char charac) {
        String term = new String(new char[] { charac });
        int lengthTerm = term.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lengthTerm; i++) {
            if (term.charAt(i) == 'X')
                sb.append("nng");
            else if (term.charAt(i) == 'N')
                if (i < lengthTerm - 1 && term.charAt(i + 1) == 'N') {
                    sb.append("nng");
                    i = i + 1;
                } else
                    sb.append("ng");
            else
                sb.append(term.substring(i, i + 1));
        }
        return sb.toString();
    }

    // Remplacement de:
    //   Vdouble par Vpointée
    //   qqV kqV
    public static String orthographyICISyl(String term) {
        int lengthTerm = term.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lengthTerm; i++) {
            char charac = term.charAt(i);
            if (i == lengthTerm - 1)
                // End of the term: just append the end character.
                sb.append(term.substring(i));
            else if (Roman.sylWithSameVowel(charac, term
                    .charAt(i + 1))) {
                // CV + V: append CV'.
                sb.append(new String(new char[] { Roman
                        .shortToLong(charac) }));
                i += 1;
            } else
                // Otherwise: just append the current character.
                sb.append(term.substring(i, i + 1));
        }
        return sb.toString();
    }

    /*
     * Éventuellement, il faudra ajouter les caractères latins utilisés au
     * Nunatsiavut; par exemple, â, &, l barré (\u0142)
     */
    static String UnicodeWordChars = "[a-zA-Z\\u1400-\\u167f0-9\\-\\&â\\u0142]";

    public static boolean isWordDelimiter(char c, String fontName) {
        Pattern pwordChars = null;
        String cs = ""+c;
        if (Font.isLegacy(fontName)) {
            pwordChars = Pattern.compile(Font.getWordChars(fontName));
        } else {
            pwordChars = Pattern.compile(UnicodeWordChars);
        }
        Matcher m = pwordChars.matcher(cs);
    
        if (m.matches())
            return false;
        else
            return true;
    }


    public static boolean isWordChar(char c, String fontName) {
        Pattern pwordChars;
        String cs = ""+c;
        if (Font.isLegacy(fontName)) {
            pwordChars = Pattern.compile(Font.getWordChars(fontName));
        } else {
            pwordChars = Pattern.compile(UnicodeWordChars);
        }
        Matcher m = pwordChars.matcher(cs);
    
        if (m.matches())
            return true;
        else
            return false;
    }
}
