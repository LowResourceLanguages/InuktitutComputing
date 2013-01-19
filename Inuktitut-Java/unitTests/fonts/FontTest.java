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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fonts.ngrams.Ngram;
import utilities1.Util;
import script.Syllabics;
import script.TransCoder;
import junit.framework.TestCase;

public class FontTest extends TestCase {

//   public void testTranscoder2toUnicodeICI() {
//        TransCoder transcoder = Police.getTranscoder2("nunacom","ToUnicode","ici");
//        String str = "|wte"; // iitiqi
//        String targ = "\u1404\u144E\u157F";
//        String res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//        transcoder = Police.getTranscoder2("nunacom","ToUnicode","ici");
//        str = "xwg"; // aitu
//        targ = "\u140a\u1403\u1450";
//        res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//    }
    
    public void testTranscoder3toUnicodeICI() {
        String res = null;
        Method meth = Font.getTranscoder3("nunacom","ToUnicode");
        String [][] words = {
                {"|wte", "iitiqi", "\u1404\u144E\u157F"},
                {"xwg", "aitu","\u140a\u1403\u1450"},
                {"n6rt9lA","saqqitillugu","\u14f4\u1585\u146d\u144e\u14ea\u14d7\u148d"},
                {"xR+A|t5","annguutiit","\u140a\u1674\u144f\u1466"},
                {"w3+f3t5","iquurtit","\u1403\u1582\u1550\u144e\u1466"}
        };
        for (int i=0; i<words.length; i++) {
            String str = words[i][0];
            String targ = words[i][2];
            try {
                res = (String) meth.invoke(null,new Object[]{str,null});
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            assertEquals("",targ,res);
        }
        meth = Font.getTranscoder3("ainunavik","ToUnicode");
        String [][] words2 = {
//              pinasualaursimanngituq
                {"WNhxMs3ym1qg6","\u1431\u14c7\u14f1\u140a\u14da\u1405\u1550\u14ef\u14aa\u1671\u1450\u1585"}, 
    };
        for (int i=0; i<words2.length; i++) {
            String str = words2[i][0];
            String targ = words2[i][1];
            try {
                res = (String) meth.invoke(null,new Object[]{str,null});
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            assertEquals("",targ,res);
        }
        
    }
    
//    public void testTranscoder2toUnicodeAIPAITAI() {
//        TransCoder transcoder = Police.getTranscoder2("nunacom","ToUnicode","aipaitai");
//        String str = "|wte"; // iitiqi
//        String targ = "\u1404\u144E\u157F";
//        String res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//        str = "xwg"; // aitu
//        targ = "\u1401\u1450";
//        res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//    }
    
    public void testTranscoder3toUnicodeAIPAITAI() {
        String res = null;
        Method meth = Font.getTranscoder3("nunacom","ToUnicode");
        String [][] words = {
                {"|wte", "iitiqi", "\u1404\u144E\u157F"},
                {"xwg", "aitu","\u1401\u1450"},
                {"n6rt9lA","saqqitillugu","\u14f4\u1585\u146d\u144e\u14ea\u14d7\u148d"},
                {"xR+A|t5","annguutiit","\u140a\u1674\u144f\u1466"},
                {"w3+f3t5","iquurtit","\u1403\u1582\u1550\u144e\u1466"}
        };
        for (int i=0; i<words.length; i++) {
            String str = words[i][0];
            String targ = words[i][2];
            try {
                res = (String) meth.invoke(null,new Object[]{str,"aipaitai"});
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            assertEquals("",targ,res);
        }
    }
    
//    public void testTranscoder2toFontICI() {
//        TransCoder transcoder = Police.getTranscoder2("nunacom","ToFont","ici");
//        String targ = "|wte"; // iitiqi
//        String str = "\u1404\u144E\u157F";
//        String res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//        targ = "xwg"; // aitu
//        str = "\u140a\u1403\u1450";
//        res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//    }
    
//    public void testTranscoder2toFontAIPAITAI() {
//        TransCoder transcoder = Police.getTranscoder2("nunacom","ToFont","aipaitai");
//        String targ = "|wte"; // iitiqi
//        String str = "\u1404\u144E\u157F";
//        String res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//        targ = "xwg"; // aitu
//        str = "\u1401\u1450";
//        res = transcoder.transcode(str);
//        assertEquals("",targ,res);
//    }
    
        
        /*
         * ----------------------------------------------------------------------
         */
        public void ttestDetermineFontByNgrams() throws IOException {
            String text, font, expectedFont;
            Ngram fontsNgrams;
            File f;
            BufferedReader br;
            
            // source: contaminants-capacity-building-in.pdf
            // police: TTE1BDCBS8tt0 et sim.
//            text = "=KxE !%_u4 !&_j5, @))%_u, kNK5 g8z=4f5 vtmt5tMs3mb wo8ix6t5t9lt4 hD8N6goEi3u4 kNK7u. $) sz]b]i5g5 wkw5 vtMs6g5 wcl1i wMs/6g6ht4 Wzhi4 s9li4 vtmi3u4 g]CzJu4 xJq8i6]b6X9oxi3u4 wkw5 hD8N6goEi3u4 W0Jtc6gi4 whm]lbsJi4. wMQ/sMs6g5 w8Ngcw5 wkw5, xaNh4]t5 urQx3ix6]t9l vg0pct]Qq5, ]smJoE]p5, kNo1i ]x8ixc3Nq5goE]p5, wcNw/6]t5 wkw5 vg0pct]Qq8i4, Gx=4g6ymisJi wkw5 vg0pct]Qq5, kNK5 g8z=4f5, wkw5 bW]E5 vNbu x7ml wkw5 yM3Jx3u vtmp3Jxq5H, srs6b6gu hD3N6goEp4f5, x7ml i]e5 x?t5t8i vtmpC]M5 r[Z6gwpq5 x7ml cspn6]t5. w4y?sbE/sMs6]g6 ]gmy xoc6g6, xzJ]cz er6bi wkw5 vg0pct]Qq8i x7ml vtmt5t9li JxNy xfmo4, xsM5tpsJ6 Z?m4f5 x7ml wkw5 WoEct]Q1iq8k5, kNK5 g8z=4f8i.";
//            fontsNgrams = Police.determineFontsByNgrams(text);
//            for (int i=0; i<fontsNgrams.fontNgrammes.size(); i++) {
//                Ngramme.FontNgramme fng = (Ngramme.FontNgramme)fontsNgrams.fontNgrammes.get(i);
//                float val = (float)fng.ngrammesTrouves / (float)fng.ngrammes * 100;
//                System.out.println(fng.font+"..."+fng.ngrammesTrouves+" sur "+fng.ngrammes+" ("+val+") "+" moy: "+fng.freqAverage);
//            }
//            font = "";
//            expectedFont = "prosyl";
//            System.out.println("1a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("1a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: GP-Inuktitut.pdf
            // police: Naamajut
            text = "\u22484v, wkoEpgc4f5 uiybz5 C8 sF8 bm8N iDx6yisc5b6g6 eu3D/sd9lA g[oxi iDxo6Xb moZos6t4nu1i4 kNK5 moZos6tq8i4, x7ml bm8N wvJ6y6bs9li kNK5 Z?m4noEp4fq8i5, kNK5 g8zF4f5 tuz8k5 x7ml Xs4©t4f8k5. kNKu moZos6tsj5 xy0p3yJ8N3ix6g5 iDx6bsiE?4bq8i4 moZos6tsJ5 bm8N eu3D/sMs6t9lA, bm8Nl W9lA slExN6gc8qM6 \u22486r4bsNh4g6 bm8N s4gCD8N6XK5.";
            fontsNgrams = Font.determineFontsByNgrams(text);
            try {
                PrintWriter out;
                out = new PrintWriter(new OutputStreamWriter(System.out,"utf-8"),true);
                for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                    Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                    float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                    out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
                    out.println(Util.array2string(fng.notFound));
                } 
                font = "";
                expectedFont = "naamajut";
                out.println("2a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
                out.println();
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            assertTrue("2a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: i_Commission.pdf
            // police: NewBNunavikPlain et NewNNunavikPlain
            try {
                PrintWriter out;
                out = new PrintWriter(new OutputStreamWriter(System.out,"utf-8"),true);
                f = new File("c:\\inuktitut\\polices\\ngrammes","i_commission.txt");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
                text = br.readLine();
                fontsNgrams = Font.determineFontsByNgrams(text);
                for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                    Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                    float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                    out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
                    out.println(Util.array2string(fng.notFound));
                }
                font = "";
                expectedFont = "ainunavik";
                out.println("3a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
                out.println();
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            assertTrue("3a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: inuk.pdf
            // police: Prosyl
            text = "v7SM8f5 X3Nwix6g5 nN/4n6`bD8Nd2lQ5 W?9oxt5ti3j5 X3Nstu4, W?9ox4v8iD8Nd2lQ5 wkw5 nNJ8N6yiq5 x7ml `W/3lt4 gl6b3=s?4g5 nN/4n6`bCh1i3j5. r?9o6 wkw5 vg0pct`Q4f5 wvJ3ix6g5 W?9oxt5ti3j5 X3Nstu4 WoEctc3lt4 xyq8i4 tusJi4 NlNw/wi3j5 xJq8iE/sJi4 WJ8N3iE/sJi[l r?9o3u wkw5 nNJ8N3iq8k5 x7ml c2ysiq8k5 r?9o3u wkw5 nNJ8NC/6g5 w8k4yJ8NC/6g5 nN}=1i4. bm8N X3Nst s2luj5 `x6rQx6bsc5b3l x3CAb`m5.";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "prosyl";
            System.out.println("4a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("4a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: KangirsujuaqCDPinut.pdf
            // police: NewNNunavikPlain
            text = "kNos2 W?9oxizb X3Nbsiz vq3hJxu wkq5 cspmJ©7mb WAm/q8i4 kNoub x3ÇÅMzJi b9omsMzJi. X3Nwi3ui, wko?5 wh?¬tQ/ui4 scsycc5bMs3mb, gÇDm/ui4, X3NwDm/ui4, WsyEAm/ui9l wko?3tbsic3ht4 vq3hJxus5 x5pOE1qgk5 vtmps[o8k5. vtmpxWs[ø5 eu3DD8Nc5bMs3d5 wko?5 whmQ/q8i4, wMQx3hQ9¬î5 k?i4 whm?Eym/ui4, bwm x3ÇAk5 b9omkxzJu4 X3NwD8NyixoCu4 yK9oXs5/sQxosJi4. kNoub xzJ3cz8k5 bf/st5hQ5 kNu9l tAux3tsJ4f5 xzJ3çz8k5, bwm eu3Dixo3mî4 bm4fiz.";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "ainunavik";
            System.out.println("5a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("5a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: lus2_i.pdf
            // police: NewNNunavikPlain et NewBNunavikPlain
            text = "yK9ou, v?m5noE?5 wmwQxDt5nsJE5yK5 x5pOE1qgi4 kN[7u tu7mEsJi4 vtt3bsQxc3gE5yht4 Wsyc3lt4 yKjx5tyQx3insZI3gu4 XX5yicDbsQxc3izi4 wkw5 wo3dyzb. w?4 scDbsZhx3S6 x?b6 WsygcoE[4 tu7mE4 wMQIsQxc3m5 Wcbs7ut9lQ5 xyq5 Nso??ozJw5 tu7m‰5, wMQos5pIsli vtt3bsQx5yxymJj5 v?mj5. x?b4f5 scsy5ncDt[iq5 v?m5noEpk5 si4vsyc3S5 bw5hm tus2 Gx?bs2H kwbsi[izi4 !(*)-u >Iwu+ Xw2 xqctOE8izb raixWxi, iWr5gxW7u4 i5÷ÇW5ht4 wkw5 wkgcq5b cspñEZhxDtQxW5bzis4 W7mExlCb3i4 r1aic3gcExzi4 W5Jtst9lQ5 wo3dys2 scsyc3isl çq8ˆbsi[iQ4.> scsyEI[î5 bf5nst5yQK5 x3ÇAi WQx3Xoxi3ui x?bs2 WA8N[7uA5 WZhx3i[izi4 WsoxEIst5yZhx3hi xyst5ybwoZhx3hi WJut5yZhx3hil wkw5 scsyq8i4 wo3dyq8il, WAt5nc5yxq5gmEsZlx3hi. NlN1qyx3S6 WixC5nui4 vJyt5yi3ui kN[7u v?msZI3g6 r1åm5y˜3m5 x?bs2";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "ainunavik";
            System.out.println("6a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("6a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: NCPI_Inuttitut.pdf
            // police: Aipainunavik
            text = "wl8Nt4 Nf3uAm/K5 s?5ti4 wvJcbMs3g5 x7ml cspm/ui cspt5ycbht4 wvJDmi3ui4 xg3ht4, b4fx WMsqXb bm8N cspn3isJ6 vJyA8NMsqm5, Wlx3gu4 : w{ uh x7ml è7+ xo+n8g WymJ4 wMQn4fi ; m3yx9 mS5; bmp7mEz x7ml WNhtq5 ƒ5Jx2 is[3ix[zi, Sx5n8 Kx3i WymJ6 kNo8i w9loEp7mEq8i ƒ5Jxu; mwf fxb, yKo3t4 x7ml Ìu €bu ƒ5Jx2 v??Wzi; /8 C?8hx ts8 WymJ6 vNbu w9lw5 xro3gbsiq8i x7ml w9loEis2 fxSEnzi; /8Wx3 MSx5t N7ui6 w9lc3iu WNhx3bsq8i4 vmJ xbs5yf3typ4 Wym5hi vt[4 kNoo? v?mzi; x7ml wl8Nq8i4 wMQaJi wMscbMs3gi is[3t5 WsyEhq8i4 gryix3[scbMs3gi x7ml Ùn c9l6, wk5tgo3tycbMs3m5.";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "aipainunavik";
            System.out.println("7a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("7a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // NWHP_POSTER_Inuktitut.pdf
            // Nunacom
            // Note: cette page ne contient aucune syllabe longue.
            text = "cspn3=1u smJ5 hD3N3gcExq5 cspn3bscb3ymJ5 srs3b3g7u smJ5 cspn3bsMs3ymNt4. smJ5 cspn3=1u cspn3bsJ5 hD3N3g3gcExq5 Gbmfx smJ5 cspn3bsJ5 hD3N3gc3g8i4 Wtbs?4Lt4H srs3b3g7u smJ5 hD3N3gc3iq5b sz=bk5 hD3N3go3bs?4Lt4 cspn3=1u. srs3b3g7u smJ5 hD3N3gc3iq5 h9o csp/symMEqm5b xbsysqg7u4 hD3N3g7u4 Wbc3tlA h3l wrbwoJt5 wloq5 ckwoJbsN/Exq5 csp/symMEqm5b.";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "nunacom";
            System.out.println("8a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("8a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: page_6.pdf
            // Naamajut
            text = "N5y6 X3Nw6g6 isFx5- ncc5b˜Exu4 y5N g0/s7u4 rNgw8N3j5 kNox?j5, Wlx6gu4 w?h5gk5 xy?6tk5. yKi5nc- Dm7uJ6bs6 X6nw?5 isFc5b- C/d9lQ5, wvÔtQix3mA NlNw4fbc3FQlA xgEx3i4 st6- bCc5b?D8•3lt4 NJ‰6ym- /q8k5, x7ml g0/wA8N6ylt4. “wkw5 X6nwJ•5g5 wv- J6bsQxcw8N?a7u7mb5bs6,” bw8N scMs6g6. gxJlw5 u5~•5g5 g0/w†5 isFxaymo6g5, wMQ/s9lt4 b9om5 isFxaMs6g5 kNK5 gxF3N6goEi3j5 xsM5ypk5 x7ml ybm5 wfW=FoEp4f8k5. N5y6 scMs3uJ6 X6nw?5 xgc5b3iC6gQ5 ?i©Xu x7ml SEt{ vM7Wxu vJyic5yx6ymiCwym9lt4. “g0pw7u4 dFxQ- /ø5.”";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "naamajut";
            System.out.println("9a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("9a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: R2-233-2001-1S.pdf
            // NewNNunavikPlain et NewBNunavikPlain
            text = "kwtbsJ[is5hi k[7X !(((-u, raizA5 Wix3ioEi3j5 xqctOE8is2 xtos3bsJ[is2 kN[s2 r=Zg3tq8k5, fXw7j5 x7ml vNbj5, kN[s2 v?m5noEpz giI[isK6 toIsmstu4 kw5ydIs5ht4 wmwAbsA8NgEIui4 v?mcDysA8Ngu4 kN[7u. v?m5noE?5 gr?MsJK5 yK9oXs5pMz5ht4 wko?3tyi3i4 vtmi3i4 x7ml vtm5yi3i4 Nso??ozJoEJi4 Gxi3tEJo??ozJoEJi4H kNdtsJo??ozJoEJi9l w¬8Nq8i kN[s2 kNodtq8i. v?m5noE?5 whmysEMsJQK5 kNc3çymJw5 kNoq8i4 x7ml kNc3çymÔˆ3tgi4 yM3ctQIsJi4 Gni9oEIsJi4H kN[7j5. ra9oXuo, vtm5yv5bg7mEsc5bMsJK5 whmysEi3j5 cspn3icc5bht4 xuhw7mExl8i4 v?mw5 Wp5yC3tq8i4 ?4fN1z5 fXw7u5, vNbu5, kN?7u5 x7ml xfr5gu5. NlNwIwAtq5b W9MEsiq5 v?m5noE?5 wmwQxDt5nsJEIq8i sfxAK5:";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "ainunavik";
            System.out.println("10a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("10a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

            // source: Taimanit.pdf
            // police: Nunacom
            text = "b2fx4 w~k4 ttC3}g4 w~ky3ui4 ttC3bq5 WsJx~l4. ttC5tx3ymJx~l2lt4l. sco]m3bs/Exc3g5 wk1k5 bmw8k5, wo5t+?9o3Fsix3mb, ck6 w~k5tx3is2 u4]nk5. wkw9l i3Jt9l xF8i6 xJ3g5, +h3l |b2fx4 jxt +xl~l6 x7ml +X9 wnrx6 ttC3mt4, w~kis2 u4]nk5. kNK5 m8N kN5tx?7mExl4, wkw5 s?A5 `N7mQ/2t8i4, iei4l, WD3gi4l kNu Wbcs3m5. b[? bm4fNU5 w8N3i5 scs0J3bs?4SA5 s0p3g5txd/s2lb. x7ml, s0p3g5tx3iK5 s?2t8k5 wv+Jtc3ix6Li w~kct2t8k9l. b[? bwm8N w8Nw5 s?~l8`i5 w8Nm`E5, bwm8N `NM5txd/sc5b3S5 sc9Mo|Czb. scsyq5 m4f4gi5 wobs?9oxix3mb. x7ml w8N3i4 wvJc5bd/s2lb scs0/s?4SA5 xzJ6]v2t8k5, wvJc5bDF5 wexhc5bT4fF5 w~ky5txE`MCF5 x7ml w~k4+h/D]mCF5. b[? bm4fx w~kct}Q5tx3i3j5 g|C3g5.";
            fontsNgrams = Font.determineFontsByNgrams(text);
            for (int i=0; i<fontsNgrams.fontNGrams.size(); i++) {
                Ngram.FontNGram fng = (Ngram.FontNGram)fontsNgrams.fontNGrams.get(i);
                float val = (float)fng.ngramsFound / (float)fng.ngrams * 100;
                System.out.println(fng.font+"..."+fng.ngramsFound+" sur "+fng.ngrams+" ("+val+") "+" moy: "+fng.freqAverage);
            }
            font = "";
            expectedFont = "nunacom";
            System.out.println("11a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
            System.out.println();
//            assertTrue("11a.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));

//            text = "whm]lbsJi4"; //isumaluutaujunik
//            fontsNgrams = Police.determineFontsByNgrams(text);
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "prosyl";
//            System.out.println("1.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("1.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//
//            text = "wl8Nt4 Nf3uAm/K5 s?5ti4 wvJcbMs3g5 x7ml cspm/uicspt5ycbht4 wvJDmi3ui4 xg3ht4, b4fx WMsqXb bm8N cspn3isJ6vJyA8NMsqm5, Wlx3gu4 : w{ uh x7ml è7+ xo+n8g WymJ4 wMQn4fi ;m3yx9 mS5; bmp7mEz x7ml WNhtq5 ƒ5Jx2 is[3ix[zi, Sx5n8 Kx3iWymJ6 kNo8i w9loEp7mEq8i ƒ5Jxu; mwf fxb, yKo3t4 x7ml Ìu€bu ƒ5Jx2 v??Wzi; /8 C?8hx ts8 WymJ6 vNbu w9lw5xro3gbsiq8i x7ml w9loEis2 fxSEnzi; /8Wx3 MSx5t N7ui6w9lc3iu WNhx3bsq8i4 vmJ xbs5yf3typ4 Wym5hi vt[4 kNoo?v?mzi; x7ml wl8Nq8i4 wMQaJi wMscbMs3gi is[3t5 WsyEhq8i4gryix3[scbMs3gi x7ml Ùn c9l6, wk5tgo3tycbMs3m5.";
//            fontsNgrams = Police.determineFontsByNgrams(text);
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "aipainunavik";
//            System.out.println("2.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("2.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//
//            text = "xWE/sZu4 @))#_u gryix6bsJi wkw5 x?tzbl ckw8iq8i4, rsd/s9lt4]sjz ''Nr5 gryN3i6]Xi4 WJ8NChQ=5 W0Jbsiq8i ]smJw5 hD3N6g9lV\"\", bm3uvnw5wkw5 !&_aJi kNK7u kNo1i rsc5bMs6g5 w]m4, ]smJoEpi4, xaNh4t4f8i4,w8N3i4, x7ml kNo1i ]x8ixc3Nq4goEpi4. bwmw8izk5, bw4fx wkw5cwd/sMs6g5 vtmJk5 kNK5 g8z=4f5 vtmtbq8i grysm0Jt4nc3iq8k5,gnEx6g6ht[l whm]lbsJi4 kNo1i cspmpbE/sJk5. bwmo xgi wkw5]n]ucbsJ5 cspmic3iq8k5, vtmJ5 gz=c9MEMs6g5 mgwzt5tNh4gi4scct]QA8N3iq8i4 nebsd9lQ5 wMscbsJ5 cspmiq5 x7ml whmQ/q5hD8N6goEi3u4.";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "prosyl";
//            System.out.println("3.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("3.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//
//            text = "·7mExl4. moZos6tsJ5 iDx6bsc5bC/6S5 whm3hDbslt4, WJ8N3iq5 mo4lQ5. rNgw8N6 iDxc5boC/q5g6 WJm8qbui4. bm8Nl g4yCsbsJ6 mo4ym9li vNbu rN4fgw8Nk5 WJ8Nsbs9lt4 whm3hD8Nsbs9lt4l moZ3Jxzk5. kNKu Z?m4noEp4f5, g8zF4f9l Xs4©t4f9l x7m xat5 x3Nw9l kNKusbsJ5 s4WDh9ME4g5 bm8N g4yCsbsJ6 whm3hD8N3i3u4 WsyQx6t5yix3m5 - kNKusbsJ5 xvsi6nu4 x7m NojQ1i6nu4 Z?mcoC/3mb.";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "naamajut";
//            System.out.println("4.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("4.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//
//            text = "kNKu Z?m4noEp4f8k5 G*!(H (&(-$!(( kNKu g8zF4f5 tuz5 G*!(H (&(-#@#@";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "naamajut";
//            System.out.println("5.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("5.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//            
//            text = "b2fx4 w~k4 ttC3}g4 w~ky3ui4 ttC3bq5 WsJx~l4. ttC5tx3ymJx~l2lt4l. sco]m3bs/Exc3g5 wk1k5 bmw8k5, wo5t+?9o3Fsix3mb, ck6 w~k5tx3is2 u4]nk5.";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "nunacom";
//            System.out.println("6.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("6.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//            
//            text = "osNs5 N5y6 tAux6g6 Xi7ui4, ÔxM8u4, n9o3i nNFz•5gt4 nNFQMs6bz y5N cz5b6tbsymJ4f5 g0/w5, hN4ft yKi5ncDmJ6 w?o5yA8N6yZ/d9lA x7ml wvJD8Nd9lA X6nwpi4. isFx5nEtbq5 N7uidtztA5, wkw5 kˆi sx/c`Q5goEi3j5 vg0pctOE5. Gx0p`Ax3u4 giyJ6 osNs5 N5y6H";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "naamajut";
//            System.out.println("7.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("7.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//            
//            text = "WixC5nE/symJw5 vt[4 kNoom5 v?mzk5, vt[4 wo8ixioEi3j5, kN[7u wlyoEº5 vtmpq8k5 x7ml x?b3j5 vtt3bsZ/3g5 wMQos5/slt4 kN[s2 v?mzk5 WNh5tq9l xtts3cC/3ht4 x5pŒEqgk5 x[5gym5ht4 Wp5y[oxaˆ3tymJk5 v?mk5 v?ms2 wlxi. vgp5is2 tudtz, vt[s2 kNo8i w9loEp3Jxq5, b3Cus5 iWz5, gM5b[s2 wªo5y[s9l €8ix[Q5b fxS‰nQ4 wo/sZ/3gw5 Wd/tA5 WJ8Nic3[zk5 kN[s2 v?mzb. Wsoxq8NClx3lt4 tusiqb wob3NDtq8i. « kNo8îgi4 vtmpxWø5 vmJi4 wo8ixioEi3u4, wlyoEi3u4 x7ml w3cgwpoEi6. wo5p[sQx9MC/3hi grÌEc5bD8NEx9Mi3u4 krc3tbsAtu4.";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "aipainunavik";
//            System.out.println("8.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("8.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
//
//            text = "WixC5nEIsymJw5 vt[4 kNoom5 v?mzk5, vt[4 wo8ixioEi3j5, kN[7u wlyoE?5 vtmpq8k5 x7ml x?b3j5 vtt3bsZI3g5 wMQos5Islt4 kN[s2 v?mzk5 WNh5tq9l xtts3cCI3ht4 x5pOEqgk5 x[5gym5ht4 Wp5y[oxaˆ3tymJk5 v?mk5 v?ms2 wlxi. vgp5is2 tudtz, vt[s2 kNo8i w9loEp3Jxq5, b3Cus5 iWz5, gM5b[s2 w?o5y[s9l ?8ix[Q5b fxS‰nQ4 woIsZI3gw5 WdItA5 WJ8Nic3[zk5 kN[s2 v?mzb. Wsoxq8NClx3lt4 tusiqb wob3NDtq8i. « kNo8îgi4 vtmpxWø5 vmJi4 wo8ixioEi3u4, wlyoEi3u4 x7ml w3cgwpoEi6. wo5p[sQx9MCI3hi gr?Ec5bD8NEx9Mi3u4 krc3tbsAtu4.";
//            for (int i=0; i<fontsNgrams.length; i++)
//                System.out.println(fontsNgrams[i][0]+"..."+((Float)fontsNgrams[i][1]).toString());
//            font = (String)fontsNgrams[0][0];
//            expectedFont = "ainunavik";
//            System.out.println("9.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font);
//            System.out.println();
////            assertTrue("9.Résultat attendu: "+expectedFont+"; résultat obtenu: "+font,font.equals(expectedFont));
        }

}