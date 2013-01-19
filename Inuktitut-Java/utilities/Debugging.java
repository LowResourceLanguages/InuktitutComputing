//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2002
//           (c) National Research Council of Canada, 2002
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		Debogage.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: Pour le débogage de méthodes de façon systématique,
//              cohérente et globale.
//
// -----------------------------------------------------------------------

package utilities;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.io.*;
import java.util.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Debugging {
    public static boolean actif = false;
	private static Hashtable deb = null;
	private static long lastModified = 0;
	private static CheckBoxHandler cbhandler;
	private static TextFieldHandler cthandler;
	private static ButtonHandler buttonhandler;
	private static Container c;
	private static Box b0, b1, b2, b1a, b1b;
	private static JFrame fr;
	private static JScrollPane sp;
	private static JTextField ctNo;
	private static JTextField ctPoint;
	private static JButton boutonAjouter, boutonEnlever;
	private static int maxSize;
	private static Box boite2;
	private static String fileName = "resources\\debogage.txt";

	// Création d'une fenêtre de dialogue dans laquelle les fonctions
	// débogagles sont affichées avec un bouton radio à cocher pour
	// activer le débogage des fonctions.
	static {
//	public static void init() {
		boolean deb = faireHashDebog();
//		if (deb) {
//			Debogage x = new Debogage();
//			FenetreDebogage fd = x.new FenetreDebogage();
//		}
	}

	private class FenetreDebogage {

		public FenetreDebogage() {
			JTextField ct, ct1;
			cthandler = new TextFieldHandler();
			cbhandler = new CheckBoxHandler();
			buttonhandler = new ButtonHandler();

			// 2 parties: 1) les boutons de la fenêtre (cacher, ...)
			//            2) les champs
			Box boite1 = Box.createHorizontalBox();
			JButton bq = new JButton("Réduire");
			bq.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fr.setExtendedState(Frame.ICONIFIED);
				}
			});
			boite1.add(bq);

			boite2 = Box.createVerticalBox();
			// b1: Déclaration de nouveau point de débogage
			// b2: Liste des points de débogage
			b1 = Box.createVerticalBox();
			b1a = Box.createHorizontalBox();
			b1b = Box.createHorizontalBox();
			b2 = Box.createVerticalBox();
			ct1 = new JTextField("Nouveau point de débogage:");
			ct1.setEditable(false);
			b1.add(ct1);
			ctPoint = new JTextField(20);
			//			ctPoint.addActionListener(cthandler);
			b1a.add(ctPoint);
			ctNo = new JTextField(5);
			//			ctNo.addActionListener(cthandler);
			b1a.add(ctNo);
			b1.add(b1a);
			boutonAjouter = new JButton("Ajouter");
			boutonAjouter.addActionListener(buttonhandler);
			b1b.add(boutonAjouter);
			boutonEnlever = new JButton("Enlever");
			boutonEnlever.addActionListener(buttonhandler);
			b1b.add(boutonEnlever);
			b1.add(b1b);
			boite2.add(b1);
			// Créer les checkbox (dans b2)
			creerCheckBox();
			sp = new JScrollPane(b2);
			boite2.add(sp);

			fr = new JFrame("Débogage");
			fr.setUndecorated(true);
			fr.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
			fr.setSize(300, 600);
			c = fr.getContentPane();
			c.setLayout(new BorderLayout(30, 30));

			//		fr.setSize(250, 500);

			c.add(boite1, BorderLayout.NORTH);
			c.add(boite2, BorderLayout.CENTER);
			fr.show();
			fr.addWindowListener(new WindowAdapter() {
			});
		}
	}

	private void creerCheckBox() {
		JCheckBox cb;
		String[] points = new String[deb.size()];
		int ipoint, npoints;
		npoints = 0;
		for (Enumeration e = deb.keys(); e.hasMoreElements();)
			points[npoints++] = (String) e.nextElement();
		Arrays.sort(points);
		for (ipoint = 0; ipoint < npoints; ipoint++) {
			String pt = points[ipoint];
			Boolean val = (Boolean) deb.get(points[ipoint]);
			cb = new JCheckBox(pt);
			cb.setSelected(val.booleanValue());
			cb.addItemListener(cbhandler);
			b2.add(cb);
		}
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton bouton = (JButton) e.getSource();
			if (bouton == boutonAjouter && !ctPoint.getText().equals("")) {
				boite2.remove(sp);
				String point = ctPoint.getText();
				if (!ctNo.getText().equals(""))
					point = point + " - " + ctNo.getText();
				deb.put(point, new Boolean(false));
				b2 = Box.createVerticalBox();
				creerCheckBox();
				sp = new JScrollPane(b2);
				boite2.add(sp);
				ctPoint.setText("");
				ctNo.setText("");
				fr.show();
			} else if (
				bouton == boutonEnlever && !ctPoint.getText().equals("")) {
				boite2.remove(sp);
				String point = ctPoint.getText();
				if (!ctNo.getText().equals(""))
					point = point + " - " + ctNo.getText();
				deb.remove(point);
				b2 = Box.createVerticalBox();
				creerCheckBox();
				sp = new JScrollPane(b2);
				boite2.add(sp);
				ctPoint.setText("");
				ctNo.setText("");
				fr.show();
			}
		}
	}

	private class CheckBoxHandler implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			JCheckBox src = (JCheckBox) e.getSource();
			deb.put(src.getText(), new Boolean(src.isSelected()));
		}
	}

	private class TextFieldHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}

	//----MESS------------------------------------------------------

	public static void mess(String origine, int no, String s) {
		Object entreeDeb = deb.get(origine + " - " + no);
		if (actif && entreeDeb != null && ((Boolean) entreeDeb).booleanValue()) {
			try {
				System.out.print("\n[" + origine + "] ");
				System.out.println(s);
			} catch (Exception e) {
			}
		}
	}

	public static void mess(
		String origine,
		int no,
		String s,
		OutputStreamWriter out) {
		Object entreeDeb = deb.get(origine + " - " + no);
		if (actif && entreeDeb != null && ((Boolean) entreeDeb).booleanValue()) {
			try {
				out.write("\n[" + origine + "] ");
				out.write(s);
				out.flush();
			} catch (Exception e) {
			}
		}
	}

	public static void mess(
		String origine,
		int no,
		String s,
		ObjectOutputStream out) {
		Object entreeDeb = deb.get(origine + " - " + no);
		if (actif && entreeDeb != null && ((Boolean) entreeDeb).booleanValue()) {
			try {
				System.out.print("\n[" + origine + "] ");
				System.out.println(s);
			} catch (Exception e) {
			}
		}
	}

	//--------------------------------------------------------------

	public static boolean fonction(String nomFonction) {
		Object entreeDeb = deb.get(nomFonction);
		return (entreeDeb != null && ((Boolean) entreeDeb).booleanValue());
	}

	public static boolean faireHashDebog() {
		BufferedReader br = null;
		if (deb == null)
			deb = new Hashtable();
		else
			deb.clear();
		File f = new File(fileName);
		if (!f.exists()) {
//			System.out.println("pas de fichier 'ressources\\debogage.txt'");
			return false;
		}
		//		br = Util.ouvrirFichier("debogage.txt");
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (br != null) {
			boolean fct = false;
			try {
				boolean eof = false;
				while (!eof) {
					String ligne = br.readLine();
					if (ligne == null)
						eof = true;
					else if (ligne.equals(""));
					else {
						StringTokenizer st = new StringTokenizer(ligne);
						String orig = st.nextToken();
						String next = st.nextToken();
						String key;
						if (next.equals("false") || next.equals("true")) {
							key = orig;
							deb.put(key, new Boolean(next));
						} else {
							key = orig + " - " + next;
							String val = st.nextToken();
							deb.put(key, new Boolean(val));
						}
						//						System.out.println("faireHashDebog: key: "+key+" "+deb.get(key));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	static void ecrireDebogage() {
		//		System.out.println("Ecriture de debogage.txt");
		//		System.out.println("deb: number de clés: "+deb.size());
		File f = new File(fileName);
		if (!f.exists())
			return;
		try {
			PrintWriter pw =
				new PrintWriter(new FileWriter(fileName));
			for (Enumeration e = deb.keys(); e.hasMoreElements();) {
				String point = (String) e.nextElement();
				StringTokenizer st = new StringTokenizer(point);
				String orig = st.nextToken();
				if (st.hasMoreTokens()) { // - numéro
					st.nextToken();
					String ligne =
						orig + " " + st.nextToken() + " " + deb.get(point);
					pw.println(ligne);
					//					System.out.println(ligne);
				} else {
					String ligne = orig + " " + deb.get(point);
					pw.println(ligne);
					//					System.out.println(ligne);
				}
			}
			pw.close();
		} catch (Exception e) {
			System.err.println("deb: ERREUR: " + e.toString());
		};
	}

	public static void terminer() {
		ecrireDebogage();
	}

}
