/*
 * Conseil national de recherche Canada 2005/ National Research Council Canada
 * 2005
 * 
 * Cr�� le / Created on Apr 11, 2005 par / by Benoit Farley
 *  
 */
package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;



import script.Orthography;

public class ListMorphemes {

    public static void main(String[] args) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("ressources/morphemes.txt"));

            LinguisticDataAbstract.init("csv");

            String bases[] = (String[]) LinguisticDataAbstract.getAllBasesKeys();
            Arrays.sort(bases);
            bw.write("Roots (simple & composite), including demonstratives:");
            bw.newLine();
            for (int i = 0; i < bases.length; i++) {
                bw.write(Orthography.simplifiedOrthographyLat(bases[i]));
                bw.newLine();
            }
            bw.newLine();
            bw.flush();

            Vector suffixesV = new Vector();
            Vector endingsV = new Vector();
            
            String affixes[] = LinguisticDataAbstract.getAllAffixesSurfaceFormsKeys();
            Arrays.sort(affixes);
            
            for (int i=0; i<affixes.length; i++) {
                Vector vecForms = (Vector)LinguisticDataAbstract.getSurfaceForms(affixes[i]);
                for (int j=0; j<vecForms.size(); j++) {
                    SurfaceFormOfAffix fa = (SurfaceFormOfAffix)vecForms.elementAt(j);
                    if (fa.type.equals("sn") || fa.type.equals("sv") || fa.type.equals("q"))
                        if (!suffixesV.contains(affixes[i]))
                                suffixesV.add(affixes[i]);
                        else;
                    else if (fa.type.equals("tn") || fa.type.equals("tv") || fa.type.equals("td") ||
                            fa.type.equals("tpd") || fa.type.equals("tad"))
                        if (!endingsV.contains(affixes[i]))
                                endingsV.add(affixes[i]);
                        else;
                    else
                        System.out.println("??? "+affixes[i]+" ("+fa.type+")");
                }
            }
            String suffixes[] = (String[])suffixesV.toArray(new String[]{});
            String endings[] = (String[])endingsV.toArray(new String[]{});
            Arrays.sort(suffixes);
            Arrays.sort(endings);
            
            bw.write("Infixes:");
            bw.newLine();
            for (int i = 0; i < suffixes.length; i++) {
                bw.write(suffixes[i]);
                bw.newLine();
            }
            bw.newLine();
            bw.flush();

            bw.write("Endings:");
            bw.newLine();
            for (int i = 0; i < endings.length; i++) {
                bw.write(endings[i]);
                bw.newLine();
            }
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}