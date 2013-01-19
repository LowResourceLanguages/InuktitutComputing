/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Feb 16, 2006
 * par / by Benoit Farley
 * 
 */

/*
 * Cette m�thode convertit une cha�ne de texte d'un mode � un autre: les modes
 * sont: unicode, unicode \\uxxxx, nunacom, prosyl, aipainunavik, roman alphabet
 * et lorsqu'on convertit � unicode, on peut sp�cifier si l'on doit utiliser les
 * caract�res syllabiques de la s�rie 'aipaitai'.
 */
package script.exec;


public class TranscodingWebAppMain {

    public static void main(String[] args) {
        String txt = TranscodingWebApp.transcode(args);
        System.out.println(txt);
    }
}
