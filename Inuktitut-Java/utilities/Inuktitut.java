// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File: Inuktitut.java
//
// Type/File type: code Java / Java code
// 
// Auteur/Author: Benoit Farley
//
// Organisation/Organization: Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation: 22 avril 2002 / April 22, 2002
//
// Description: Code relatif au traitement de l'inuktitut.
//
// -----------------------------------------------------------------------


package utilities;


import java.util.*;

//import javaswing.javax.swing.text.html.*;
import javax.swing.text.html.*;

import documents.NRC_HTMLDocument;

import script.Roman;



public class Inuktitut {

    static Hashtable affixes;

    static Hashtable affixesSyl;

    static Hashtable lexemes;

    static Hashtable lexemesSyl;
    
    public static String fontInuk = null;

    public static class MorceauTexte {
        boolean inuk;

        String police;

        int debut;

        int fin;

        String texte;

        boolean syllabic = true;

        public MorceauTexte(boolean isInuk, int deb, int fn, String texte,
                String pol) {
            inuk = isInuk;
            debut = deb;
            fin = fn;
            setTexte(texte);
            police = pol;
            // lorsque les morceaux de texte sont cr��s, le texte en
            // inuktitut est en syllabique, et le texte non-inuktitut
            // n'est pas en syllabique.
            syllabic = inuk;
        }

        public int getDebut() {
            return debut;
        }

        public int getFin() {
            return fin;
        }

        public String getTexte() {
            return texte;
        }

        public String getPolice() {
            return police;
        }

        public boolean isInuk() {
            return inuk;
        }

        public boolean isSyllabic() {
            return syllabic;
        }

        public void setTexte(String txt) {
            String nouveau = remplacerEspaces(txt);
            texte = nouveau;
        }

        public void setSyllabic(boolean val) {
            syllabic = val;
        }

        // Il semble que la function insertHTML ne consid�re pas le
        // premier caract�re "espace" d'un �l�ment de texte: un
        // �l�ment de texte ne contenant qu'un espace dispara�t.
        // Un �l�ment commen�ant par un espace voit cet espace
        // �limin�. Cette fonction ajoute un deuxi�me espace.

        String remplacerEspaces(String texte) {
            StringBuffer sb = new StringBuffer();
            if (texte.length()>0 && texte.charAt(0) == ' ')
//                sb.append("&nbsp;").append(texte.substring(1));
//                sb.append((char)160).append(texte.substring(1));
            	sb.append("  ").append(texte.substring(1));
            else
                sb.append(texte);
            return sb.toString();
        }

    }

    ////
    // --------------------------------------------------------------------------------------------------------------------

    public static NRC_HTMLDocument.TitreHTML titreEnInuktitut(String text) {
        NRC_HTMLDocument.TitreHTML txtHTML = new NRC_HTMLDocument.TitreHTML(text);
        boolean enInuktitut = false;
        Vector morceaux = new Vector();

        //        try {
        int longu = text.length();
        int i = 0;
        int deb = 0;
        boolean dansInuk = false;
        boolean dansNonInuk = false;
        MorceauTexte morceau;

        // Si le texte n'est qu'un newline, �a n'a pas
        // d'importance; on retourne 'null'
        if (longu == 1 && text.charAt(0) == 10)
            return null;

        // Y a-t-il des caract�res dans ce
        // texte dans la plage du syllabaire aborig�ne canadien?
        //
        // On place alors dans le vecteur offsets des triplets
        // "inuktitut"/"noninuktitut",d�but,fin
        // qui d�crivent la suite de caract�res dans le texte.
        while (i < longu) {
            if (Roman.isInuktitutCharacter(text.charAt(i))) {
                // syllabaire
                enInuktitut = true;
                if (dansInuk) {
                    ;
                } else if (dansNonInuk) {
                    // NOUVEAU MORCEAU DE TEXTE EN INUKTITUT UNICODE SUIVANT
                    // UN MORCEAU DE TEXTE PAS EN INUKTITUT
                    dansNonInuk = false;
                    dansInuk = true;
                    morceau = new MorceauTexte(false, deb, i, text.substring(
                            deb, i), null);
                    morceaux.add(morceau);
                    deb = i;
                } else {
                    // PREMIER MORCEAU DE TEXTE - EN INUKTITUT UNICODE
                    dansInuk = true;
                    deb = i;
                }
            } else {
                if (dansNonInuk) {
                    ;
                } else if (dansInuk) {
                    // NOUVEAU MORCEAU DE TEXTE PAS EN INUKTITUT SUIVANT
                    // UN MORCEAU DE TEXTE EN INUKTITUT
                    dansInuk = false;
                    dansNonInuk = true;
                    morceau = new MorceauTexte(true, deb, i, text.substring(
                            deb, i), null);
                    morceaux.add(morceau);
                    deb = i;
                } else {
                    // PREMIER MORCEAU DE TEXTE - PAS EN INUKTITUT
                    dansNonInuk = true;
                    deb = i;
                }
            }
            i++;
        } /* while */

        // Si du texte en unicode a �t� d�tect�, on ajoute le dernier
        // morceau de texte.
        if (enInuktitut) {
            txtHTML.setCodage(Roman.UNICODE);
            morceau = new MorceauTexte(dansInuk ? true : false, deb, i, text
                    .substring(deb, i), null);
            morceaux.add(morceau);
        }

        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }

        txtHTML.setMorphParts(morceaux);

        return (enInuktitut ? txtHTML : null);

    } // fin de titreEnInuktitut()

    
}