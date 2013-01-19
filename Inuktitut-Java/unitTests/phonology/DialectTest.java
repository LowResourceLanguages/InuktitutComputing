/*
 * Conseil national de recherche Canada 2004/
 * National Research Council Canada 2004
 * 
 * Cr�� le / Created on 9-Sep-2004
 * par / by Benoit Farley
 * 
 */
package unitTests.phonology;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import phonology.Dialect;
import phonology.PhonologicalTransformation;


import data.LinguisticDataAbstract;

import script.Orthography;



import junit.framework.TestCase;

public class DialectTest extends TestCase {

    public void test_correspondingTermsEquivalentGroups_String() {
        String terme = "ippassaq";
        String termeSi = Orthography.simplifiedOrthographyLat(terme);
        Vector termes = Dialect.correspondingTermsEquivalentGroups(termeSi);
        String termesArr[] = (String[])termes.toArray(new String[]{});
        Arrays.sort(termesArr);
        /*
         * pp > mp, tp, kp       ss > ps, ts, ks, k&
         */
        // On devrait retrouver les termes suivants:
        String targets[]={
                "ippassaq", 
                "ippapsaq", "ippatsaq","ippaksaq", "ippak&aq",
                "impassaq", "impapsaq", "impatsaq", "impaksaq", "impak&aq",
                "itpassaq",  "itpapsaq", "itpatsaq", "itpaksaq","itpak&aq",
                "ikpassaq", "ikpapsaq", "ikpatsaq", "ikpaksaq", "ikpak&aq"};
        assertTrue("Pas "+targets.length+" �l�ments",termes.size()==targets.length);
        for (int i=0; i<targets.length; i++)
            assertTrue(targets[i]+" pas trouv�",Arrays.binarySearch(termesArr,targets[i])>=0);
    }
    
    public void test__correspondingTermsEquivalentGroups_ArrayList1() {
        String terme = "ippassaq";
        String termeSi = Orthography.simplifiedOrthographyLat(terme);
        ArrayList termes = Dialect.correspondingTermsEquivalentGroups(termeSi,0);
        String[] termesArr = new String[termes.size()]; 
        for (int i=0; i<termes.size(); i++) {
            termesArr[i] = (String)((Object[])termes.get(i))[0];
        }
        Arrays.sort(termesArr);
        /*
         * pp > mp, tp, kp       ss > ps, ts, ks, k&
         */
        // On devrait retrouver les termes suivants:
        String targets[]={
                "ippassaq", "ippapsaq", "ippatsaq","ippaksaq", "ippak&aq",
                "impassaq", "impapsaq", "impatsaq", "impaksaq", "impak&aq",
                "itpassaq",  "itpapsaq", "itpatsaq", "itpaksaq","itpak&aq",
                "ikpassaq", "ikpapsaq", "ikpatsaq", "ikpaksaq", "ikpak&aq"};
        assertTrue(termes.size()+" �l�ments trouv�s au lieu de "+targets.length,termes.size()==targets.length);
//        for (int i=0; i<termesArr.length; i++)
//            System.out.println(termesArr[i]);
        for (int i=0; i<targets.length; i++) {
            assertTrue(targets[i]+" pas trouv�",Arrays.binarySearch(termesArr,targets[i])>=0);
        }
        for (int i=0; i<termes.size(); i++) {
            ArrayList val = (ArrayList)((Object[])termes.get(i))[1];
            assertTrue(val.size()+" transformations au lieu de 2",val.size()==2);
            for (int j=0; j<val.size(); j++)
            {
                PhonologicalTransformation tp = (PhonologicalTransformation)val.get(j);
                assertTrue(tp.position+" au lieu de 1 ou 4",tp.position==1 || tp.position==4);
            }
        }
    }
    
    public void test_correspondingTermsEquivalentGroups_ArrayList2() {
        String terme = "qilatsugu";
        String termeSi = Orthography.simplifiedOrthographyLat(terme);
        ArrayList termes = Dialect.correspondingTermsEquivalentGroups(termeSi,0);
        String[] termesArr = new String[termes.size()]; 
        for (int i=0; i<termes.size(); i++) {
            termesArr[i] = (String)((Object[])termes.get(i))[0];
        }
        Arrays.sort(termesArr);
        /*
         * ts > ps, ss, tt, t&, &&, ks, k&
         */
        // On devrait retrouver les termes suivants:
        String targets[]={"qilatsugu",
                "qilapsugu", "qilassugu", "qilattugu","qilat&ugu", //"qila&&ugu",
                "qilaksugu", "qilak&ugu"};
        assertTrue(termes.size()+" éléments trouvés au lieu de "+targets.length,termes.size()==targets.length);
//        for (int i=0; i<termesArr.length; i++)
//            System.out.println(termesArr[i]);
        for (int i=0; i<targets.length; i++) {
            assertTrue(targets[i]+" pas trouvé",Arrays.binarySearch(termesArr,targets[i])>=0);
        }
        for (int i=0; i<termes.size(); i++) {
            ArrayList val = (ArrayList)((Object[])termes.get(i))[1];
            for (int j=0; j<val.size(); j++)
            {
            	PhonologicalTransformation tp = (PhonologicalTransformation)val.get(j);
                assertTrue(tp.position+" au lieu 4",tp.position==4);
            }
        }
    }
    
    public void test_newCandidates() {
        LinguisticDataAbstract.init("csv");
        Object targets[][] = {
                { "maliga", "ssaq", null,
                        new String[] { "ksaq", "tsaq", "psaq", "k&aq" } },
                { "naamma", "saq", null,
                            new String[] {"psaq","tsaq","ksaq","gsaq","msaq","nsaq","ssaq",
                                "lsaq","jsaq","vsaq","rsaq","qsaq","Nsaq","&saq"}
                        },
                { "aulat", "ti", null, new String[] { "si" } },
                { "qai", "laursima", null, new String[] {"lauqsima","laursipma",
                        "laursitma","laursikma","laursigma","laursimma","laursinma"
                        ,"laursisma","laursilma","laursijma","laursivma","laursirma"
                        ,"laursiqma","laursiNma","laursi&ma",
                        "lauqsipma",
                        "lauqsitma","lauqsikma","lauqsigma","lauqsimma","lauqsinma"
                        ,"lauqsisma","lauqsilma","lauqsijma","lauqsivma","lauqsirma"
                        ,"lauqsiqma","lauqsiNma","lauqsi&ma"}} };
        for (int i=0; i<targets.length; i++) {
            String [] targs = (String[])targets[i][3];
            Vector newCands = Dialect.newCandidates(
                    (String)targets[i][0],
                    (String)targets[i][1],
                    (String)targets[i][2]);
//            String newCandsArr[] = (String [])newCands.toArray(new String[]{});
//            Arrays.sort(newCandsArr);
//            assertTrue((String)targets[i][0]+": "+"Pas "+targs.length+" �l�ments dans "+print(newCandsArr),newCandsArr.length==targs.length);
//            for (int j=0; j<targs.length; j++)
//                assertTrue(targs[j]+" pas trouv� dans "+print(newCandsArr),Arrays.binarySearch(newCandsArr,targs[j])>=0);
            System.out.println("---"+targets[i][0]+" "+targets[i][1]);
            for (int j=0; j<newCands.size(); j++)
                System.out.println(newCands.elementAt(j));
            System.out.println();
        }
    }
    
    public void test_markCandidate () {
    	String marked;
    	String target;
    	marked = Dialect.markCandidate("saq", 0, false,'@');    target="saq";
    	assertTrue("erreur: '"+marked+"' aurait dû être '"+target+"'", marked.equals(target));
    	marked = Dialect.markCandidate("saq", 0, true,'@');    target="@saq";
    	assertTrue("erreur: '"+marked+"' aurait dû être '"+target+"'", marked.equals(target));
    	marked = Dialect.markCandidate("saqqikpuq", 0, false,'@');    target="saqqikpuq";
    	assertTrue("erreur: '"+marked+"' aurait dû être '"+target+"'", marked.equals(target));
    	marked = Dialect.markCandidate("saqqitaq", 0, false,'@');    target="saqqi@taq";
    	assertTrue("erreur: '"+marked+"' aurait dû être '"+target+"'", marked.equals(target));
    }
    
    
    public void test_schneiderCandidates () {
        LinguisticDataAbstract.init("csv");
        String radical = "naamma";
        String candidat = "saq";
        String [] target_candidates = new String[] {
        		"psaq","tsaq","ksaq","nsaq","ssaq","rsaq","qsaq","lsaq","msaq"};
        List list = Arrays.asList(target_candidates);
        Vector candidats = Dialect.schneiderCandidates(radical,candidat);
        for (int i=0; i<candidats.size(); i++) 
        	System.out.println(candidats.get(i));
        assertTrue("Mauvais nombre de candidats: "+candidats.size()+" au lieu de "+target_candidates.length,
        		candidats.size() == target_candidates.length);
        for (int i=0; i<target_candidates.length; i++) 
        	assertTrue(target_candidates[i]+" pas contenu",candidats.contains(target_candidates[i]));
    }
    
    public void test_schneiderCandidatesToString() {
    	String cand;
    	String targ;
    	cand = Dialect.schneiderCandidatesToString(null, "uummiajuaqtiuNittuNa", '*');
    	targ = "uummia*juaqtiu*Nittu*Na";
    	assertTrue(cand+": Mauvais candidat Schneider; devrait être '"+targ+"'",cand.equals(targ));
    }
    
    //---------------------------------------
    
    private String print(String v[]) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (int i=0; i<v.length-1; i++) {
            sb.append(v[i]+" ");
        }
        if (v.length > 0)
            sb.append(v[v.length-1]);
        sb.append("}");
        return sb.toString();
    }

}
