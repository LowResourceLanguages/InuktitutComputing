/*
 * Conseil national de recherche Canada 2004/ National Research Council Canada
 * 2004
 * 
 * Créé le / Created on Nov 22, 2004 par / by Benoit Farley
 *  
 */
package fonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import fonts.ngrams.Ngram;

import utilities1.Util;

import script.TransCoder;


public class Font {

    public static String[][] legacyFonts = { 
        { "nunacom", "nunacom" },
        { "prosyl", "prosyl" }, 
        { "aujaqsyl", "aujaqsyl" },
        { "aujaq2", "aujaq2" },
        { "aujaq", "aujaq" },
        { "tunngavik", "tunngavik" }, 
        { "ainunavik", "ainunavik" },
        { "aipainunavik", "aipainunavik" }, 
        { "aipainuna", "aipainunavik" },
        { "naamajut", "naamajut" },
        { "oldsyl", "oldsyl" },
        { "tariurmiut", "tariurmiut" },
        { "emiinuktitut", "emiinuktitut" },
    };
    static Hashtable fontsCorrespondancies = null;
    public static String[] fonts = null;
    static {
        fontsCorrespondancies = new Hashtable();
        fonts = new String[legacyFonts.length];
        for (int i=0; i<legacyFonts.length; i++) {
            fontsCorrespondancies.put(legacyFonts[i][0],legacyFonts[i][1]);
            fonts[i] = legacyFonts[i][0];
        }
    }

    
    // Polices inuktitut unicode.
    public static String[] fontsUnicode = {
            "pigiarniq", "aipainunavik", "uqammaq", "nunacomu", "ballymun ro",
            "aipainutaaq"
    };

    
    private static String packageName = new Font().getClass().getPackage()
            .getName();

    public String name;

    char[][] unicodesAcodes;


    public static Method getTranscoder3(String fontName, String direction) {
        Class fontClass = getFontClass(getFontName(fontName));
        if (fontClass == null)
            return null;
        Method mth = null;
        try {
            if (direction.equals("ToUnicode"))
                mth = fontClass.getDeclaredMethod("transcodeToUnicode", new Class[]{String.class,String.class});
            else if (direction.equals("ToFont"))
                mth = fontClass.getDeclaredMethod("transcodeFromUnicode", new Class[]{String.class});
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return mth;
    }
    
    public static TransCoder getTranscoder2(String fontName, String direction,
            String mode) {
        TransCoder transcdr = null;
        Class fontClass = getFontClass(getFontName(fontName));
        if (fontClass == null)
            return null;
        Field transcdrField = null;
        int dir = 0;
        try {
            if (direction.equals("ToUnicode")) {
                transcdrField = fontClass
                        .getDeclaredField(mode.equals("ici") ? "transcoderToUnicodeICI"
                                : "transcoderToUnicodeAIPAITAI");
                dir = 1;
            } else if (direction.equals("ToFont")) {
                transcdrField = fontClass
                        .getDeclaredField(mode.equals("ici") ? "transcoderToFontICI"
                                : "transcoderToFontAIPAITAI");
                dir = -1;
            }
            
            // Get the transcoder for the font.
            transcdr = (TransCoder) transcdrField.get(null);
            
            // If the transcoder does not yet exist, create it ans set it.
            if (transcdr == null) {
                // Start with syllables
                String[][] unicodesICItoCodes = (String[][]) fontClass
                        .getDeclaredField("unicodesICI2Codes").get(null);
                ArrayList lst = new ArrayList();
                List lst1 = Arrays.asList(unicodesICItoCodes);
                lst.addAll(lst1);
                // Add digits
                Field digitsField = null;
                try {
                    digitsField = fontClass.getDeclaredField("unicodesICI2CodesDigits");
                }
                catch (NoSuchFieldException e) {
                }
                if (digitsField != null) {
                    String[][] unicodesICItoCodesDigits = (String[][])digitsField.get(null);
                    List lst2 = Arrays.asList(unicodesICItoCodesDigits);
                    lst.addAll(lst2);
                }
                // Add other signs
                Field otherSignsField = null;
                try {
                    otherSignsField = fontClass.getDeclaredField("unicodesICI2CodesOthers");
                }
                catch (NoSuchFieldException e) {
                }
                if (otherSignsField != null) {
                    String[][] unicodesICItoCodesOthers = (String[][])otherSignsField.get(null);
                    List lst2 = Arrays.asList(unicodesICItoCodesOthers);
                    lst.addAll(lst2);
                }
                // If aipaitai, add those codes
                if (mode.equals("aipaitai")) {
                    Field aipaitaiField = null;
                    try {
                        aipaitaiField = fontClass.getDeclaredField("unicodesAIPAITAI2Codes");
                    }
                    catch (NoSuchFieldException e) {
                    }
                    if (aipaitaiField != null) {
                        String[][] unicodesAIPAITAItoCodes = (String[][])aipaitaiField.get(null);
                        List lst2 = Arrays.asList(unicodesAIPAITAItoCodes);
                        lst.addAll(lst2);
                    }                    
                }
                String[][] unicodesToCodes = (String[][]) lst.toArray(new String[][] {});

                // Get dot codes
                Field dotCodesField = null;
                try {
                    dotCodesField = fontClass.getDeclaredField("dotCodes");
                }
                catch (NoSuchFieldException e) {
                }
                String dotCodes = null;
                if (dotCodesField != null)
                    dotCodes = (String) dotCodesField.get(null);
                
                // Create the transcoder
                if (dotCodes != null)
                    transcdr = new TransCoder(unicodesToCodes, dotCodes, dir);
                else
                    transcdr = new TransCoder(unicodesToCodes, dir);
                
                // Set the transcoder for the font such that it can be retrieved
                // quickly when required at another time.
                transcdrField.set(null, transcdr);
            }
        } catch (IllegalArgumentException e) {
        } catch (SecurityException e) {
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        }
        return transcdr;
    }
    

    public static String getFontName(String fontNameFromSource) {
        String fontName = fontNameFromSource.toLowerCase();
        if (fontsCorrespondancies.get(fontName) != null)
            return fontName;
        else
            return null;
//        int i = 0;
//        while (i < fonts.length) {
//            if (fontNameFromSource.toLowerCase().indexOf(fonts[i]) == 0)
//                return fonts[i];
//            i++;
//        }
//        return null;
    }

    public static Class getFontClass(String fontName) {
        Class cl = null;
        if (fontName != null) {
            String correspFontName = (String)fontsCorrespondancies.get(fontName.toLowerCase());
            String fontNameMaj = correspFontName.substring(0, 1).toUpperCase()
                    + correspFontName.substring(1).toLowerCase();
            String className = packageName + ".Font" + fontNameMaj;
            try {
                cl = Class.forName(className);
            } catch (ClassNotFoundException e) {
            }
        }
        return cl;
    }

    

    // Cette fonction détermine si S est membre de POLICES, la liste
    // des polices reconnues représentant des caractères inuktitut
    // avec encodage à 7 ou à 8 bits (pas Unicode).
    //
    // S peut être un nom seul, ou un ensembe de noms séparés par des
    // virgules tel que défini dans un élément html:
    //    nunacom
    //    nunacom, sans-serif, ...
    //--------------------------------------------------------------
    static public String isInuktitutLegacyFont(String s) {
        List fontsInuk = Arrays.asList(Font.fonts);
        return isInuktitutFont(s,fontsInuk);
    }
    
    static public String isInuktitutUnicodeFont(String s) {
        List fontsInuk = Arrays.asList(Font.fontsUnicode);
        return isInuktitutFont(s,fontsInuk);
    }
    
    static String isInuktitutFont(String s, List fontsInuk) {
        if (s == null)
            return null;
        String font = s.toLowerCase();
        String subs;
        StringTokenizer st = new StringTokenizer(font, ",");
        while (st.hasMoreTokens()) {
            subs = st.nextToken().trim().toLowerCase();
            if (fontsInuk.contains(subs))
                return subs;
        }
        return null;
    }

    public Font() {
    }

    public static boolean isLegacy(String font) {
        if (isInuktitutLegacyFont(font) != null)
            return true;
        else
            return false;
    }
    
    
    public static boolean isUnicodeFont(String font) {
        if (isInuktitutUnicodeFont(font) != null)
            return true;
        else
            return false;
    }
    
    public static String getWordChars(String fontName) {
        String wordChars = null;
        Class fontClass = getFontClass(getFontName(fontName));
        if (fontClass != null) {
            Field wordCharsField = null;
            try {
                wordCharsField = fontClass.getDeclaredField("wordChars");
            } catch (SecurityException e) {
            } catch (NoSuchFieldException e) {
            }
            if (wordCharsField != null)
                try {
                    wordChars = (String) wordCharsField.get(null);
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }
        }
        return wordChars;
    }


    public static String containsLegacyFont(String [] fonts) {
        String font = null;
        if (fonts != null)
            for (int i=0; i<fonts.length; i++)
                if ((font=isInuktitutLegacyFont(fonts[i])) != null) {
                    return font;
                }
        return null;
    }
    
    public static Ngram determineFontsByNgrams(String text) {
        Hashtable ngrmsht = new Hashtable();
        loadNGrams(ngrmsht);
        Ngram value = new Ngram();
        for (Enumeration e = ngrmsht.keys(); e.hasMoreElements();) {
            Object fontKey = e.nextElement();
            String font = (String)fontKey;
            Hashtable fontNgrams = (Hashtable)ngrmsht.get(fontKey);
            Set fontChars = null;
            try {
                String packageName = Font.class.getPackage().getName();
                fontChars = (Set)Class.forName(packageName+".Font"+Util.premiereMaj(font)).getDeclaredField("fontChars").get(null);
            } catch (IllegalArgumentException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (NoSuchFieldException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            computeNGrams(text,font,fontNgrams,fontChars,value);
        }
//        Arrays.sort(fontsByNgrams,new FontsByNgramsComparator());
        return value;
    }
    
    
    private static void computeNGrams(String text, String font, Hashtable fontNgrams, Set fontChars, Ngram value) {
        text = text.replaceAll("\\s+"," ");
        Hashtable computed = new Hashtable();
        int ngrams = 0;
        int ngramsFound = 0;
        long totalFreqsNgrammes = ((Long)fontNgrams.get("nbFreqsTotal")).longValue();
        int totalFreqs = 0;
        int ngramsRetained = 0;
        for (int i=0; i<text.length()-3+1; i++) {
            ngrams++;
            String part = text.substring(i,i+3);
            boolean allChars = true;
//            for (int j=0; j<part.length(); j++)
//                if (!fontChars.contains(new Character(part.charAt(j)))) {
//                    allChars = false;
//                    break;
//                }
            if (allChars) {
                ngramsRetained++;
                Integer freq = (Integer)computed.get(part);
                if (freq==null)
                    computed.put(part,new Integer(1));
                else
                    computed.put(part,new Integer((freq.intValue())+1));
            }
        }
        /*
         * 'computed' contient tous les 3grammes de 'text'.
         */
        ngrams = computed.size();
        ngramsRetained = ngrams;
        Vector notFound = new Vector();
        for (Enumeration e = computed.keys(); e.hasMoreElements();) {
            Object part = e.nextElement();
            int n = ((Integer)computed.get(part)).intValue();
            Integer posFreq[] = ((Integer[])fontNgrams.get(part));
            int fr;
            if (posFreq!=null) {
                fr = posFreq[1].intValue();
//                ngramsFound += n;
                ngramsFound += 1;
//                totalFreqs += fr*n;
                totalFreqs += fr;
            } else {
                notFound.add(part.toString());
//                if (font.equals("naamajut"))
//                    System.out.println("font:"+font+"   3gramme:"+part.toString());
            }
        }
        String [] notFoundS = (String [])notFound.toArray(new String[]{});
//        float freqAverage = (float)totalFreqs / (float)ngrams / (float)totalFreqsNgrammes * 100;
        float freqAverage = (float)totalFreqs / (float)totalFreqsNgrammes * 100 / ngramsFound;
        value.add(ngramsFound,ngramsRetained,font,freqAverage,ngrams,notFoundS);
    }

    private static void loadNGrams(Hashtable ngrmsht) {
        Class cl = Font.class;
        Package pk = cl.getPackage();
        String packageName = pk.getName();
        File ngramsDirectory = new File(packageName);
        FilenameFilter3Grams ffng = new FilenameFilter3Grams();
        File files[] = ngramsDirectory.listFiles(ffng);
        for (int i=0; i<files.length; i++) {
            File file = files[i];
            String font = file.getName().substring(0,
                    file.getName().indexOf(ffng.id));
            Hashtable ngrms = null;
            ngrms = readNGrams(file);
            if (ngrms != null)
                ngrmsht.put(font,ngrms);
        }
    }

    private static Hashtable readNGrams(File file) {
        Hashtable ngrms = new Hashtable();
        try {
            BufferedReader in
            = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String line;
            long nbFreqsTotal = 0;
            int lineNb = 0;
            while ((line=in.readLine())!=null) {
                if (lineNb==0 && line.length()>0 && line.charAt(0)=='\ufeff')
                    line = line.substring(1);
                lineNb++;
                String posNgramFreq[] = line.split("\t");
                String ngram = posNgramFreq[1];
                Integer pos = new Integer(posNgramFreq[0]);
                Integer freq = new Integer(posNgramFreq[2]);
                ngrms.put(ngram,new Integer[]{pos,freq});
                nbFreqsTotal += freq.intValue();
            }
            ngrms.put("nbFreqsTotal",new Long(nbFreqsTotal));
            in.close();
            return ngrms;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    
    static class FilenameFilter3Grams implements FilenameFilter {
        public String id = "3gram.txt";
        public boolean accept(File dir, String name) {
            if (name.indexOf(id) >= 0)
                return true;
            else
                return false;
        }
        
    }
    
    static class FontsByNgramsComparator implements Comparator {

        public int compare(Object arg0, Object arg1) {
            float f0 = ((Float)((Object []) arg0)[1]).floatValue();
            float f1 = ((Float)((Object [])arg1)[1]).floatValue();
            if (f0 > f1)
                return -1;
            else if (f0 < f1)
                return 1;
            else
                return 0;
        }
        
    }
    
    static String[][] nameToCode = new String[][]{
        {"exclam","!"},
        {"quotedbl","\""},
        {"numbersign","#"},
        {"dollar","$"},
        {"percent","%"},
        {"at","@"},
        {"quoteright",""},
        {"parenleft","("},
        {"parenright",")"},
        {"asterisk","*"},
        {"plus","+"},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
        {"",""},
    };
    static Hashtable nameToCodeMap;
    static {
        nameToCodeMap = new Hashtable();
    }
    
    static void checkForCharacterNames(String fontName, Set fontChars) {
        Iterator iterator = fontChars.iterator();
        while (iterator.hasNext()) {
            Character ch = (Character)iterator.next();
            if (nameToCodeMap.get(ch)==null) {
                System.err.println("Le nom de caractère pour le code "+(int)ch.charValue()+" ("+ch.charValue()+")"+" n'a pas été défini pour la police "+fontName+".");
                System.exit(1);
            }
        }
        
    }

}