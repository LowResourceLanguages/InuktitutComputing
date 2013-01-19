/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Mar 2, 2006
 * par / by Benoit Farley
 * 
 */
package data.constraints;

import java.util.Vector;

import data.Morpheme;

public class Condition {

    /*
     * Syntaxe:
     * 
     * <condPrec> ::= <condPrec1> | <condPrec2>
     * <condPrec1> ::= <condMorph> ["+" <condPrec1>]?
     * <condPrec2> ::= "!" ["cp" | "cs"] "(" <idMorph> ")" ["," <condPrec2>]?
     * <condMorph> ::= <cond> [" " <condMorph>]?
     * <cond> ::= <C> ["," <cond>]?
     * <cond> ::= "!" "(" <C> ["," <cond>]? ")"
     * <C> ::= "!"? <attr> ":" <val> 
     *      "!" indique que la valeur de l'attribut n'est pas �gale � 'val'.
     * <idMorph> ::= "id" ":" <idVal>
     * <idVal> ::= <nom de morph�me> "/" <id de morph�me>
     * <id de morph�me> ::= <nombre> <lettre> [<lettre> | <chiffre> | <tiret>]+?
     * 
     * <condPrec2> pour les cas o� l'on a A,B,C et D=!A && !B && !C
     * cp pour "condition sur le morph�me pr�c�dent"
     * cs pour "condition sur le morph�me suivant"
     * 
     */
    public Condition cond;
    public boolean truth = true;
    public String parameters;
    
    /*
     * Condition de non-mobilité associée à un infixe.
     */
    static public class NonMobilityOfInfix extends Condition implements Conditions {

        String infixId;
        
        public NonMobilityOfInfix(String infixId) {
            this.infixId = infixId;
        }
        
//        /*
//         *  Pour que cette condition soit respectée, il faut que le morphème ait dans
//         *  sa liste de racines composites dont il est la racine, une racine composite
//         *  dont le dernier élément correspond au dernier morphème trouvé.
//         */
//        public boolean isMetBy(Morpheme m) {
//            Vector vids = m.getIdsOfCompositesWithThisRoot();
//            if (vids != null) {
//                for (int i=0; i<vids.size(); i++) {
//                    String id = (String)vids.elementAt(i);
//                    if (id.endsWith(infixId))
//                        return true;
//                }
//            }
//            return false;
//        }
        
        public boolean isMetBy(Morpheme m) {
        	return true;
        }

        public boolean isMetByFullMorphem(Morpheme m) {
            return true;
        }

        public String toText(String lang) {
            return null;
        }

        public Condition expand() {
            Condition cond = null;
            try {
                cond = (NonMobilityOfInfix)this.clone();
            } catch (CloneNotSupportedException e) {
            }
            return cond;
        }
        
    }
        
    /*
     * Condition sur plus d'un morph�me.
     */
    static public class OverSeveralMorphemes extends Condition implements Conditions, Cloneable {
        
        protected Vector conds;
        
        
        public OverSeveralMorphemes (Vector cs) {
            conds = cs;
        }
        
        private OverSeveralMorphemes() {
        }
        
        public boolean isMetBy(Morpheme m) {
            return true;
        }

        public boolean isMetByFullMorphem(Morpheme morph) {
            return true;
        }
        
        public String toString() {
            String str = ((Condition)conds.elementAt(0)).toString();
            for (int i=1; i<conds.size(); i++)
                str = str+"+"+((Condition)conds.elementAt(i)).toString();
            if (!truth)
                str = "!("+str+")";
            return str;
        }
        
        public String toText(String lang) {
            String text;
            text = lang.equals("e")?
                    "Condition on the preceding morpheme:<br>":
                        "Condition sur le morphème précédent:<br>";
            text += ((Conditions)conds.elementAt(0)).toText(lang);
            for (int i=1; i<conds.size(); i++) {
                text += lang.equals("e")?
                        "Condition on the next preceding morpheme:<br>":
                            "Condition sur le morphème précédent suivant:<br>";
                text += ((Conditions)conds.elementAt(i)).toText(lang);
            }
            return text;            
        }

        public Condition expand() {
            // TODO Auto-generated method stub
            return null;
        }
}
    
    /*
     * Condition compos�e de plusieurs sous-conditions qui doivent toutes �tre
     * respect�es. Par exemple, la condition suivante comprend 3 sous-conditions
     * !type:tn,!(type:n,number:d),!(type:n,number:p)
     */
    static public class And extends Condition implements Conditions,Cloneable {
        
        protected Vector conds;
        
        public And (Vector c2s) {
            conds = c2s;
        }
        
        public And (Condition c1, Condition c2) {
            conds = new Vector();
            conds.add(c1);
            conds.add(c2);
        }
        
        public boolean isMetBy(Morpheme m) {
            boolean res = true;
            Morpheme lm = m.getLastCombiningMorpheme();
            for (int i=0; i<conds.size(); i++)
                if (!((Conditions)conds.elementAt(i)).isMetBy(m)) {
                    res = false;
                    break;
                }
            /*
             * Several roots are described in the database as inchoative roots, that
             * is, roots that are the result of adding a 'q' at the end and doubling
             * the last internal consonant (eg. ikummaq- < ikuma-). In the database,
             * this is done in the column 'combination': the last morpheme is
             * #incho#/1vv. This morpheme, as last morpheme, is NOT to be checked.
             */
            if (!res && lm!=null && !lm.id.equals("#incho#/1vv")) {
                res = true;
                for (int i=0; i<conds.size(); i++)
                    if (!((Conditions)conds.elementAt(i)).isMetBy(lm)) {
                        res = false;
                        break;
                    }
            }

            if (truth)
                return res;
            else
                return !res;
        }

        public boolean isMetByFullMorphem(Morpheme m) {
            boolean res = true;
            for (int i=0; i<conds.size(); i++)
                if (!((Conditions)conds.elementAt(i)).isMetBy(m)) {
                    res = false;
                    break;
                }
            if (truth)
                return res;
            else
                return !res;
        }

        public String toString() {
            String str = ((Condition)conds.elementAt(0)).toString();
            for (int i=1; i<conds.size(); i++)
                str = str+","+((Condition)conds.elementAt(i)).toString();
            if (!truth)
                str = "!("+str+")";
            return str;
        }

        public String toText(String lang) {
            String str = ((Conditions)conds.elementAt(0)).toText(lang);
            for (int i=1; i<conds.size(); i++)
                str = str+(lang.equals("e")?" AND ":" ET ")+((Conditions)conds.elementAt(i)).toText(lang);
            if (!truth)
                str = (lang.equals("e")?"NOT ":"PAS ")+"("+str+")";
            return str;
        }
        
        public Condition expand() {
            And cond = null;
            try {
                cond = (And)this.clone();
                for (int i=0; i<cond.conds.size(); i++) {
                    if (((Condition)cond).truth)
                        ((Condition)cond.conds.elementAt(i)).truth = false;
                    else
                        ((Condition)cond.conds.elementAt(i)).truth = true;
                    conds.setElementAt(((Conditions)cond.conds.elementAt(i)).expand(),i);
                }
                if (!cond.truth) cond.truth=true;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return cond;
        }
}
    
    /*
     * Condition compos�e de plusieurs sous-conditions mutuellement exclusives,
     * dont au moins une doit �tre respect�e.  Par exemple, la condition suivante
     * comprend 3 sous-conditions:   type:n function:vn function:nn
     */
    static public class Or extends Condition implements Conditions {
        
        protected Vector conds;
        
        public Or (Vector c2s) {
            conds = c2s;
        }
        
        public boolean isMetBy(Morpheme m) {
            boolean res = false;
            Morpheme lm = m.getLastCombiningMorpheme();
            /*
             * Several roots are described in the database as inchoative roots, that
             * is, roots that are the result of adding a 'q' at the end and doubling
             * the last internal consonant (eg. ikummaq- < ikuma-). In the database,
             * this is done in the column 'combination': the last morpheme is
             * #incho#/1vv. This morpheme, as last morpheme, is NOT to be checked.
             */
            for (int i=0; i<conds.size(); i++)
                if (((Conditions)conds.elementAt(i)).isMetBy(m) ||
                        (lm!=null && 
                                !lm.id.equals("#incho#/1vv") && 
                                ((Conditions)conds.elementAt(i)).isMetBy(lm)) ) {
                    res = true;
                    break;
                }
            if (truth)
                return res;
            else
                return !res;
        }
        
        public boolean isMetByFullMorphem(Morpheme m) {
            boolean res = false;
           for (int i=0; i<conds.size(); i++)
                if (((Conditions)conds.elementAt(i)).isMetBy(m)) {
                    res = true;
                    break;
                }
            if (truth)
                return res;
            else
                return !res;
        }
        
        public String toString() {
            String str = ((Condition)conds.elementAt(0)).toString();
            for (int i=1; i<conds.size(); i++)
                str = str+" "+((Condition)conds.elementAt(i)).toString();
            if (!truth)
                str = "!("+str+")";
            return str;
        }

        public String toText(String lang) {
            String str = ((Conditions)conds.elementAt(0)).toText(lang);
            for (int i=1; i<conds.size(); i++)
                str = str+(lang.equals("e")?" OR ":" OU ")+((Conditions)conds.elementAt(i)).toText(lang);
            if (!truth)
                str = (lang.equals("e")?"NOT ":"PAS ")+"("+str+")";
            return str;
        }

        public Condition expand() {
            Or cond = null;
            try {
                cond = (Or)this.clone();
                for (int i=0; i<cond.conds.size(); i++) {
                    if (((Condition)cond.conds.elementAt(i)).truth)
                        ((Condition)cond.conds.elementAt(i)).truth = false;
                    else
                        ((Condition)cond.conds.elementAt(i)).truth = true;
                    conds.setElementAt(((Conditions)cond.conds.elementAt(i)).expand(),i);
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return cond;
        }
    }
    
    /*
     * Condition de la forme !cp(id:<morpheme id>)
     * La condition sur le morph�me pr�c�dent rattach�e au morph�me
     * <morpheme id> ne doit pas �tre respect�e.
     * Exemple: !cp(id:it/3nv) rattach�e au morph�me it/2nv "ne pas avoir de",
     * "manquer de".
     * 
     * Ce type de condition est attribu� � un morph�me d'une forme qui est aussi
     * celle d'autres morph�mes de m�me type qui ont, eux, des restrictions
     * particuli�res.  On �vite ainsi des analyses incorrectes.  Par exemple, l'affixe
     * it/2nv et l'affixe it/3nv ont la m�me forme: 'it' et sont tous deux des 'nv'.
     * it/3nv a une contrainte particuli�re qui le diff�rencie de l'autre, et on veut
     * �viter qu'une analyse soit accept�e o� it/2nv est pr�c�d� du type de
     * morph�me attendu par it/3nv.
     */
    static public class Cid extends Condition implements Conditions {
        
        protected String pn;
        protected String morphid;
        
        public Cid (String pn, String morphid) {
            this.pn = pn;
            String s[] = morphid.split(":");
            this.morphid = s[1];
        }
        
        /*
         * The condition to check is actually the condition of the morpheme.
         * 
         */
        public boolean isMetBy(Morpheme m) {
            boolean res;
            Morpheme lm = m.getLastCombiningMorpheme();
            Conditions cond;
            Morpheme morphWithCond = Morpheme.getMorpheme(morphid);
            if (pn.equals("cp"))
                cond = morphWithCond.getPrecCond();
            else
                cond = morphWithCond.getNextCond();
            res = cond.isMetBy(m);
            /*
             * Several roots are described in the database as inchoative roots, that
             * is, roots that are the result of adding a 'q' at the end and doubling
             * the last internal consonant (eg. ikummaq- < ikuma-). In the database,
             * this is done in the column 'combination': the last morpheme is
             * #incho#/1vv. This morpheme, as last morpheme, is NOT to be checked.
             */
            if (!res && lm!=null && !lm.id.equals("#incho#/1vv"))
                res = cond.isMetBy(lm);
            if (truth)
                return res;
            else
                return !res;
        }
        
        public boolean isMetByFullMorphem(Morpheme m) {
            boolean res;
            Conditions cond;
            Morpheme morphWithCond = Morpheme.getMorpheme(morphid);
            if (pn.equals("cp"))
                cond = morphWithCond.getPrecCond();
            else
                cond = morphWithCond.getNextCond();
            res = cond.isMetBy(m);
            if (truth)
                return res;
            else
                return !res;
        }
        
        public String toString() {
            String str = pn+"("+morphid+")";
            if (!truth)
                str = "!"+str;
            return str;
        }

        public String toText(String lang) {
            String str;
            if (pn.equals("cp")) {
                str = lang.equals("en")?"the condition of ":"la condition de ";
                str += morphid;
                str += lang.equals("en")?" on the preceding morpheme":"sur le morphème précédent";
            } else
                str = pn+"("+morphid+")";
            if (!truth)
                str = (lang.equals("en")?"NOT ":"PAS ")+str;
            return str;
        }

        public Condition expand() {
            // TODO Auto-generated method stub
            return null;
        }
    }
    
}
