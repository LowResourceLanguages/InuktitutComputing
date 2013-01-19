/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 9-Dec-2003
 * par Benoit Farley
 * 
 */

package morph;

import java.util.Vector;

import data.Morpheme;
import data.constraints.Conditions;
import data.constraints.Imacond;
import data.constraints.ParseException;


public class Graph {
	static State[] states;
	static public State initialState;
	static public State finalState;
	static public State verbState;

    
	public static class State implements Cloneable {
		public String id;
		Arc[] arcs;

		public State(String id) {
			this.id = id;
		}
        
        private State() {
        }

		public String getId() {
			return id;
		}

		public Arc[] getArcs() {
			return arcs;
		}

		private void setArcs(Arc [] arcs) {
		    this.arcs = arcs;
		    for (int i=0; i<arcs.length; i++)
		        arcs[i].setStartState(this);
		}

		public Vector verify(Morpheme affixe) {
			Vector possibles = new Vector();
			for (int i = 0; i < arcs.length; i++) {
			    if (arcs[i].getCondition()==null) {
			        Vector possibles1 = arcs[i].destState.verify(affixe);
			        possibles.addAll(possibles1);
			    } else if (arcs[i].getCondition().isMetByFullMorphem(affixe))
					 possibles.add(arcs[i]);
			}
			return possibles;
		}

	
//		public State copy()  {
//		    State etat = (State)this.clone();
//		    return etat;
//		}
//        
        public Object clone() {
            State cl = new State();
            cl.id = new String(this.id);
            cl.arcs = (Graph.Arc [])arcs.clone();
            return cl;
        }


}

	public static class Arc implements Cloneable {
        Conditions cond;
		State startState;
		State destState;
		
        public Arc(Conditions cond, State destState) {
            this.cond = cond;
            this.destState = destState;
        }
        
        private Arc() {
        }

		public State getDestinationState() {
			return destState;
		}

        public Conditions getCondition() {
            return cond;
        }

		public String getDestinationStateStr() {
			return destState.id;
		}
		
		public void setStartState(State ss) {
		    startState = ss;
		}
		
	    public Arc copy()  {
	        Arc arc = null;
            try {
                arc = (Arc)this.clone();
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return arc;
	        }

		
	}

//	public static class Condition {
//		String id;
//		String type;
//		String fonction;
//		String element;
//
//		public Condition(String id, String type, String fct, String element) {
//			this.id = id;
//			this.type = type;
//			fonction = fct;
//			this.element = element;
//		}
//
////		public String getId() {
////			return id;
////		}
//
////		public String getType() {
////			return type;
////		}
//
////		public String getFonction() {
////			return fonction;
////		}
//
////		public String getElement() {
////			return element;
////		}
//
//		public String toString() {
//			StringBuffer sb = new StringBuffer();
//			sb.append("[condition: ");
//			sb.append(id);
//			sb.append(",");
//			sb.append(type);
//			sb.append(",");
//			sb.append((fonction == null) ? "null" : fonction);
//			sb.append(",");
//			sb.append((element == null) ? "null" : element);
//			sb.append("]");
//			return sb.toString();
//		}
//
//		// V�rifier si un morph�me respecte la condition.
//		public boolean estRespectee(Morpheme aff) {
//		    String nomClasse = aff.getClass().getName();
//		    if (nomClasse.endsWith("Suffix"))
//				return estRespectee((Suffix) aff);
//			else if (nomClasse.endsWith("TerminaisonNominale"))
//				return estRespectee((TerminaisonNominale) aff);
//			else if (nomClasse.endsWith("TerminaisonVerbale"))
//				return estRespectee((TerminaisonVerbale) aff);
//			else if (nomClasse.endsWith("TerminaisonDemonstrative"))
//				return estRespectee((TerminaisonDemonstrative) aff);
//			else if (nomClasse.endsWith("Base") || nomClasse.endsWith("Demonstrative"))
//				return estRespectee((Base) aff);
//			else
//				return false;
//		}
//
//		public boolean estRespectee(Suffix suff) {
//			if (suff.type.equals(type)
//				&& (fonction == null || suff.function.equals(fonction))
//				&& (element == null || suff.morpheme.equals(element)))
//				return true;
//			else
//				return false;
//		}
//
//		public boolean estRespectee(TerminaisonVerbale tv) {
//			if (tv.type.equals(type))
//				return true;
//			else
//				return false;
//
//		}
//
//		public boolean estRespectee(TerminaisonNominale tn) {
//			if (tn.type.equals(type))
//				return true;
//			else
//				return false;
//		}
//
//		public boolean estRespectee(Base b) {
//			if (b.type.equals(type))
//				return true;
//			else
//				return false;
//		}
//
//		public boolean estRespectee(TerminaisonDemonstrative td) {
//			if (td.type.equals(type)
//				&& (fonction == null || td.number.equals(fonction)))
//				return true;
//			else
//				return false;
//		}
//
//	}

	static {
		State mq, mqi, m, n, v, a, e, c, rn, rnc, rv, ad, pds, pdp, d, pr, pp = null;
		State pp1, pp2, radpp1, radpp2, racpp1, racpp2, zero = null;
		
		mq = new State("mq");	// mot avec queue
		mqi = new State("mqi");	// mot avec queue - interm�diaire
		m = new State("m");		// mot sans queue
		n = new State("n");		// racine nominale
		v = new State("v");		// racine verbale
		verbState = v;
		a = new State("a");		// adverbe
		e = new State("e");		// expression
		c = new State("c");		// conjonction
		rn = new State("rn");	// radical nominal
		rnc = new State("rnc");	// radical nominal de composition
		rv = new State("rv");	// radical verbal
		ad = new State("ad");	// adverbe d�monstratif
		pds = new State("pds");	// pronom d�monstratif singulier
		pdp = new State("pdp");	// pronom d�monstratif pluriel
		d = new State("d");		// d�monstratif
//		pr = new State("pr");		// pronom
		pp = new State("pp");		// pronom personnel
		pp1 = new State("pp1");
		pp2 = new State("pp2");
		radpp1 = new State("radpp1");
		radpp2 = new State("radpp2");
		racpp1 = new State("racpp1");
		racpp2 = new State("racpp2");
		zero = new State("0");	// d�but du mot
		
		mq.setArcs(	new Arc[] {
		        new Arc(makeCond("id:guuq/1q"), mqi),
		        new Arc(makeCond("id:kia/1q"), mqi),
		        new Arc(makeCond("id:ttauq/1q"), mqi),
		        new Arc(makeCond("id:qai/1q"), mqi),
		        new Arc(makeCond("type:q"), m),
		        new Arc(null, m)
		});
	
		mqi.setArcs(new Arc[] { 
		        new Arc(makeCond("id:li/1q"), m), 
		        new Arc(makeCond("id:lu/1q"), m)
		});
		
		m.setArcs(new Arc[] {
		        new Arc(null, n),
		        new Arc(null, v),
		        new Arc(null, e),
		        new Arc(null, a),
		        new Arc(null, c),
//		        new Arc(null, pr),
		        new Arc(null, pp),
		        new Arc(null, d),
		        // addition of the next arc: see comment below
		        new Arc(null, rn),
		});
	
		n.setArcs(new Arc[] {
		        new Arc(makeCond("type:tn"), rn),
		        new Arc(makeCond("type:tn"), rnc),
		        new Arc(makeCond("type:n,number:d"), zero),
		        new Arc(makeCond("type:n,number:p"), zero),
		        /*
                 * The following arc is fine on paper, but in practice, it makes
                 * that there is a duplication: Rv (by NV) to N (by null) to Rn
                 * (by nominal root) to 0; Rv (by NV) to Rn (by nominal root) to
                 * 0.  So, it is commented out and replaced by a null arc from M
                 * to Rn
                 */
//		        new Arc(null, rn)
		        new Arc(makeCond("type:tn,number:s,possPers:null"), a),
		});
		
		v.setArcs(new Arc[] {
		        new Arc(makeCond("type:tv"), rv)
		});
		
		a.setArcs(new Arc[] {
		        new Arc(makeCond("type:a"), zero)
		});
		
		e.setArcs(new Arc[] {
		        new Arc(makeCond("type:e"), zero)
		});
		
		c.setArcs(new Arc[] {
		        new Arc(makeCond("type:c"), zero)
		});
		
		rn.setArcs(new Arc[] {
		        new Arc(makeCond("function:nn"), rn),
		        new Arc(makeCond("function:nn"), rnc),
		        new Arc(makeCond("function:nn"), a),
		        new Arc(makeCond("function:vn"), a),
		        new Arc(makeCond("function:vn"), rv),
		        new Arc(makeCond("type:n,number:s"), zero),
		        new Arc(makeCond("type:p,!nature:per"), zero),
		});
		
		rnc.setArcs(new Arc[] {
		        new Arc(makeCond("type:n,subtype:nc"), zero),
		});
		
		rv.setArcs(new Arc[] {
		        new Arc(makeCond("function:vv"), rv),
		        new Arc(makeCond("function:nv"), n),
		        new Arc(makeCond("function:nv"), rn),
		        new Arc(makeCond("function:nv"), rnc),
		        new Arc(makeCond("function:nv"), a),
		        new Arc(makeCond("function:nv"), d),
		        new Arc(makeCond("type:v"), zero)
		});
		
		// Demonstratives
		d.setArcs(new Arc[] {
		        new Arc(makeCond("type:ad"), zero),
		        new Arc(makeCond("type:pd"), zero),
		        new Arc(makeCond("type:tad"), ad),
		        new Arc(makeCond("type:tpd,number:s"), pds),
		        new Arc(makeCond("type:tpd,number:p"), pdp)
		});
		
		ad.setArcs(new Arc[] { 
		        new Arc(makeCond("type:rad"), zero)
		});
		
		pds.setArcs(new Arc[] { 
		        new Arc(makeCond("type:rpd,number:s"), zero)
		});
		
		pdp.setArcs(new Arc[] { 
		        new Arc(makeCond("type:rpd,number:p"), zero)
		});
		
		// Personal pronouns
		pp.setArcs(new Arc[] { 
		        new Arc(null, pp1),
		        new Arc(null, radpp1),
		        new Arc(null, radpp2),
		        new Arc(makeCond("type:tn,possPers:null"), radpp2),
//		        new Arc(null, pp2),
		        new Arc(makeCond("type:tn,possPers:null"), radpp2),
//		        new Arc(makeCond("type:tn,cas:nom,number:p,possPers:null"), pp2),
//		        new Arc(makeCond("type:tn,cas:gen,number:s,possPers:null"), pp2),
//		        new Arc(makeCond("type:tn,cas:gen,number:p,possPers:null"), pp2),
		});
		
		pp1.setArcs(new Arc[] {
		        new Arc(makeCond("function:nn"), radpp1),
		        new Arc(makeCond("function:nn"), pp1)
		});
		
//		pp2.setArcs(new Arc[] {
//		        new Arc(makeCond("function:nn"), radpp2),
//		        new Arc(makeCond("function:nn"), pp2)
//		});
		
		radpp1.setArcs(new Arc[]{
		        new Arc(makeCond("type:tn,possPers:1,possNumber:number"), racpp1),
		        new Arc(makeCond("type:tn,possPers:2,possNumber:number"), racpp2)
		});
		
//		radpp2.setArcs(new Arc[]{
////		        new Arc(makeCond("type:p,nature:per"), zero),
//		        new Arc(makeCond("type:pr"), zero)
//		});
		
		radpp2.setArcs(new Arc[]{
		        new Arc(makeCond("type:pr"), zero),
		        new Arc(makeCond("function:nn"), radpp2)
		});

		racpp1.setArcs(new Arc[]{
		        new Arc(makeCond("id:uva/1rpr"), zero)
		});
		
		racpp2.setArcs(new Arc[]{
		        new Arc(makeCond("id:ili/1rp"), zero)
		});
		
		
		// Les arcs de l'�tat 'zero' sont null puisque c'est l'�tat final

		initialState = mq;
		finalState = zero;
		
		states =
			new State[] {mq, mqi, m, n, v, a, e, c, rn, rv, ad, pds, pdp, d, pp, 
		        pp1, pp2, radpp1, radpp2, racpp1, racpp2, zero };

	}

	public static State getEtat(String str) {
		for (int i = 0; i < states.length; i++)
			if (states[i].getId().equals(str))
				return states[i];
		return null;
	}
    
    static private Conditions makeCond(String str) {
        try {
            return (Conditions)new Imacond(str).ParseCondition();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
