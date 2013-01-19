//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Affix.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de cr�ation/Date of creation:	
//
// Description: Classe Affix
//
// -----------------------------------------------------------------------


package data;

import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.constraints.Condition;
import data.constraints.Conditions;

public abstract class Affix extends Morpheme {
	//
	String function = null;
	String position = null;
	String[] vform = null;
	Action[] vaction1 = null;
	Action[] vaction2 = null;
	String[] tform = null;
	Action[] taction1 = null;
	Action[] taction2 = null;
	String[] kform = null;
	Action[] kaction1 = null;
	Action[] kaction2 = null;
	String[] qform = null;
	Action[] qaction1 = null;
	Action[] qaction2 = null;
	//
	
	//---------------------------------------------------------------------------------------------------------
	public abstract String getTransitivityConstraint(); //
	public abstract void addToHash(String key, Object obj); //
	
	//---------------------------------------------------------------------------------------------------------
	abstract boolean agreeWithTransitivity(String trans); //

	//---------------------------------------------------------------------------------------------------------
	public String getOriginalMorpheme() {
	    return morpheme;
	}
	
	public boolean isNonMobileSuffix() {
		if (getClass() == Suffix.class && ((Suffix) this).mobility != null
				&& ((Suffix) this).mobility.equals("nm"))
			return true;
		else
			return false;
	}
	
    public void addPrecConstraint(Condition cond) {
        if (preCondition==null)
            preCondition = (Conditions)cond;
        else {
            preCondition = new Condition.And((Condition)preCondition,cond);
        }
    }
    
	//---------------------------------------------------------------------------------------------------------
	void setAttributes(HashMap attrs) {
		HashMap affAttrs = new HashMap();
		affAttrs.put("function",function);
		affAttrs.put("position",position);
		affAttrs.put("vform",vform);
		affAttrs.put("vaction1",vaction1);
		affAttrs.put("vaction2",vaction2);
		affAttrs.put("tform",tform);
		affAttrs.put("taction1",taction1);
		affAttrs.put("taction2",taction2);
		affAttrs.put("kform",kform);
		affAttrs.put("kaction1",kaction1);
		affAttrs.put("kaction2",kaction2);
		affAttrs.put("qform",qform);
		affAttrs.put("qaction1",qaction1);
		affAttrs.put("qaction2",qaction2);
		affAttrs.putAll(attrs);
		super.setAttributes(affAttrs);
	}
	
    String[] getForm(char context) {
        if (context=='V' || context=='a' || context=='i' || context=='u')
            return vform;
        else if (context=='t')
            return tform;
        else if (context=='k')
            return kform;
        else if (context=='q')
            return qform;
        else
            return null;
    }
    
    Action[] getAction1(char context) {
        if (context=='V' || context=='a' || context=='i' || context=='u')
            return vaction1;
        else if (context=='t')
            return taction1;
        else if (context=='k')
            return kaction1;
        else if (context=='q')
            return qaction1;
		else
			return null;
    }
    
    Action[] getAction2(char context) {
        if (context=='V' || context=='a' || context=='i' || context=='u')
            return vaction2;
        else if (context=='t')
            return taction2;
        else if (context=='k')
            return kaction2;
        else if (context=='q')
            return qaction2;
		else
			return null;
    }
    
	// Les cha�nes 'alternateForms', 'actions1' et 'action2'
	// contiennent le contenu des champs X-form, X-action1 et X-action2
	// des enregistrements dans la base de donn�es.  Ces champs/cha�nes
	// peuvent identifier plus d'une forme+action. 
	//
	// En fait, les possibilit�s sont les suivantes:
	// nb. actions1 < nb. surfaceFormsOfAffixes >>> (nb. actions1 = 1) l'action est appliqu�e � chaque forme
	// nb. actions1 = nb. surfaceFormsOfAffixes >>> une action par forme
	// nb. actions1 > nb. surfaceFormsOfAffixes >>> (nb. surfaceFormsOfAffixes = 1) chaque action est appliqu�e � la forme
	//
	// nb. surfaceFormsOfAffixes = 0 >>> la forme est la m�me que la forme de 'morpheme'
	//
    // Si form = '-' et action1 = '-', cela signifie qu'il n'y a pas de forme dans ce context.
    //
	// Quant � action2, elle doit soit �tre nulle, soit correspondre en nombre � action1.
	// Dans ce cas,  la valeur '-' signifie une action nulle, i.e. aucune action.
	//
	// Une forme peut prendre les valeurs suivantes:
	//   identificateur
	//   "*" : m�me valeur que la forme du champ 'morpheme'.
    //   "-" : la forme n'existe pas dans ce context
	//
	// Cette fonction construit un objet de classe SurfaceFormOfAffix pour
	// chaque groupe form+action1+action2 identifi�e.

	void makeFormsAndActions(
		String context,
		String morpheme,
		String alternateForms,
		String action1,
		String action2) {
	    
        String[] forms;
        Action[] a1;
        Action[] a2;

	    String allForms = null;
        
        if (action1.equals("-")) {
                // Si le champ 'X-action1' est vide,
                // cela signifie qu'il n'y a pas de forme dans le context X.
                allForms = "";
                action1 = "";
                action2 = "";
        } else if (alternateForms==null)
                // Si le champ 'X-form' est vide,
                // cela signifie que la forme est celle du champ 'morpheme'.
                allForms = new String(morpheme);
        else {
    	    // Transformer les * en la valeur de 'morpheme'
    	    alternateForms = alternateForms.replaceAll("\\x2a",morpheme);
            if (context.equals("V"))
                // Dans la table des suffixes, nous avons adopt� la convention selon
                // laquelle la valeur du champ 'morpheme' est la forme du suffixe dans
                // le context de voyelle, et donc, le champ 'V-form' ne le contient pas:
                // il faut l'ajouter ici dans la liste.
                allForms = morpheme + " " + alternateForms;
            else
                allForms = new String(alternateForms);
        }
        
        StringTokenizer stf = new StringTokenizer(allForms);
        // nb. de surfaceFormsOfAffixes
        int nbf = stf.countTokens();
		
		StringTokenizer st1 = new StringTokenizer(action1);
		// nb. d'actions1
		int nba1 = st1.countTokens();
		
		if (action2==null)
		    action2 = new String("");
		StringTokenizer st2 = new StringTokenizer(action2);
		int nba2 = st2.countTokens();
		
		if (nba1 > nbf && nbf==1) {
            /*
             * S'il y a plus d'action1 que de formes, le nb. de formes devrait �tre �gal � 1
             * et on ajoute dans la liste de formes la valeur initiale autant de fois qu'il le
             * faut pour �galiser le nombre d'actions.
             */
            String initialValue = new String(allForms);
			for (int j = nbf; j < nba1; j++)
				allForms = allForms + " " + initialValue;
			stf = new StringTokenizer(allForms);
			nbf = stf.countTokens();
		} else if (nbf > nba1 && nba1==1) {
            /*
             * S'il y a plus de formes que d'action1, le nb. d'action1 devrait �tre �gal �
             * 1 et on ajoute dans la liste d'action1 la valeur initiale autant de fois qu'
             * il le faut pour �galiser le nombre de formes.
             */
		    String act1 = new String(action1);
		    for (int j = nba1; j < nbf; j++)
		        action1 = action1 + " " + act1;
            st1 = new StringTokenizer(action1);
            nba1 = st1.countTokens();
            /*
             * Si le nombre d'action2 est aussi �gal � 1 (il devrait �tre �gal � 0 ou � 1),
             * on multiplie aussi.
             */
		    if (nba2==1) {
			    String act2 = new String(action2);
			    for (int j = nba2; j < nbf; j++)
			        action2 = action2 + " " + act2;		        
			    st2 = new StringTokenizer(action2);
			    nba2 = st2.countTokens();
		    }
		}
		
		// En principe, ici, allForms, action1 et action2 devraient
		// �tre en phase et contenir un nombre �gal d'�l�ments.

		Vector VForms = new Vector();
		Vector a1V = new Vector();
		Vector a2V = new Vector();

		while (stf.hasMoreTokens()) {
		    String f = stf.nextToken();
			VForms.add(f);
			// La premi�re fois, st1.hasMoreTokens() est vrai, puisque
			// action1 doit absolument avoir une valeur dans la base de
			// donn�es: act1 y trouve sa valeur...
			String act1 = st1.nextToken();
			// ...sinon, act1 conserve la valeur pr�c�dente, obtenue
			// au premier tour.
            Pattern pif1 = Pattern.compile("^if\\((.+),([a-z]+),([a-z]+)\\)$");
            Matcher mif1 = pif1.matcher(act1);
            Pattern pif2 = Pattern.compile("^if\\((.+),([a-z]+)\\)$");
            Matcher mif2 = pif2.matcher(act1);
            if (mif1.matches()) {
                String condition = mif1.group(1).replaceAll("\\x7c"," "); // '|'
                String actPos = mif1.group(2);
                String actNeg = null;
                actNeg = mif1.group(3);
                a1V.add(Action.makeAction(actPos+"si("+condition+")"));
                if (actNeg != null) {
                    VForms.add(f);
                    a1V.add(Action.makeAction(actNeg+"si(!("+condition+"))"));
                }
                if (!action2.equals("")) {
                    String ac2 = st2.nextToken();
                    a2V.add(Action.makeAction(ac2));
                    a2V.add(Action.makeAction(ac2));
                } else {
                    a2V.add(Action.makeAction(null));
                    a2V.add(Action.makeAction(null));
                }
            } else if (mif2.matches()) {
                String condition = mif2.group(1).replaceAll("\\x7c"," "); // '|'
                String actPos = mif2.group(2);
                a1V.add(Action.makeAction(actPos+"si("+condition+")"));
                if (!action2.equals("")) {
                    String ac2 = st2.nextToken();
                    a2V.add(Action.makeAction(ac2));
                    a2V.add(Action.makeAction(ac2));
                } else {
                    a2V.add(Action.makeAction(null));
                    a2V.add(Action.makeAction(null));
                }
            } else {
			    a1V.add(Action.makeAction(act1));
			    if (!action2.equals(""))
			        a2V.add(Action.makeAction(st2.nextToken()));
			    else
			        a2V.add(Action.makeAction(null));
			}
		}
		
		forms = (String[])VForms.toArray(new String []{});
		a1 = (Action[])a1V.toArray(new Action[]{Action.makeAction()});
		a2 = (Action[])a2V.toArray(new Action[]{Action.makeAction()});

		// Enregistrer dans l'object affixe
		if (context.equals("V")) {
			vform = forms;
			vaction1 = a1;
			vaction2 = a2;
		} else if (context.equals("t")) {
			tform = forms;
			taction1 = a1;
			taction2 = a2;
		} else if (context.equals("k")) {
			kform = forms;
			kaction1 = a1;
			kaction2 = a2;
		} else if (context.equals("q")) {
			qform = forms;
			qaction1 = a1;
			qaction2 = a2;
		}
		
		/*
		 * Mis hors-compilation parce pas utile, mais conserv� pour usage futur
		 */
//		for (int j=0; j<forms.length; j++) {
//		    fillFinalRadInitAffHashSet(context,forms[j],a1[j],a2[j]);
//		}
	}
	
//	void fillFinalRadInitAffHashSet(String context, String form, Action a1, Action a2) {
//	    String f = Orthography.simplifiedOrthographyLat(form);
//	    String finalRadInitAff[] = a1.finalRadInitAff(context,f);
//	    for (int i=0; i<finalRadInitAff.length; i++)
//	        Donnees.finalRadInitAffHashSet.add(finalRadInitAff[i]);
//	}

	// ----------------------------
	public String showData() {
		StringBuffer sb = new StringBuffer();
		sb.append("vform= [");
		int i;
		for (i = 0; i < vform.length - 1; i++)
			sb.append(vform[i] + ",");
		sb.append(vform[i] + "]\n");
		sb.append("vaction1= [");
		for (i = 0; i < vaction1.length - 1; i++)
			sb.append(vaction1[i] + ",");
		sb.append(vaction1[i] + "]\n");
		sb.append("vaction2= [");
		for (i = 0; i < vaction2.length - 1; i++)
			sb.append(vaction2[i] + ",");
		sb.append(vaction2[i] + "]\n");
		sb.append("tform= [");
		for (i = 0; i < tform.length - 1; i++)
			sb.append(tform[i] + ",");
		sb.append(tform[i] + "]\n");
		sb.append("taction1= [");
		for (i = 0; i < taction1.length - 1; i++)
			sb.append(taction1[i] + ",");
		sb.append(taction1[i] + "]\n");
		sb.append("taction2= [");
		for (i = 0; i < taction2.length - 1; i++)
			sb.append(taction2[i] + ",");
		sb.append(taction2[i] + "]\n");
		sb.append("kform= [");
		for (i = 0; i < kform.length - 1; i++)
			sb.append(kform[i] + ",");
		sb.append(kform[i] + "]\n");
		sb.append("kaction1= [");
		for (i = 0; i < kaction1.length - 1; i++)
			sb.append(kaction1[i] + ",");
		sb.append(kaction1[i] + "]\n");
		sb.append("kaction2= [");
		for (i = 0; i < kaction2.length - 1; i++)
			sb.append(kaction2[i] + ",");
		sb.append(kaction2[i] + "]\n");
		sb.append("qform= [");
		for (i = 0; i < qform.length - 1; i++)
			sb.append(qform[i] + ",");
		sb.append(qform[i] + "]\n");
		sb.append("qaction1= [");
		for (i = 0; i < qaction1.length - 1; i++)
			sb.append(qaction1[i] + ",");
		sb.append(qaction1[i] + "]\n");
		sb.append("qaction2= [");
		for (i = 0; i < qaction2.length - 1; i++)
			sb.append(qaction2[i] + ",");
		sb.append(qaction2[i] + "]\n");

		return sb.toString();
	}
	

}
