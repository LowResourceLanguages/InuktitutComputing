/*
 * Conseil national de recherche Canada 2003
 * 
 * Créé le 31-Mar-2004
 * par Benoit Farley
 * 
 */
package utilities;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JEditorPane;

public class MyObjectOutputStream extends ObjectOutputStream {

	JEditorPane p;
	StringBuffer s;
	
	/**
	 * @throws IOException
	 * @throws SecurityException
	 */
	public MyObjectOutputStream(JEditorPane pane) throws IOException, SecurityException {
		super();
		p = pane;
		s = new StringBuffer();
		// TODO Auto-generated constructor stub
	}
	
	
	public void writeObjectOverride(Object o) {
		s.append(o.toString());
	}
	
	public void flush() {
		p.setText(s.toString());
	}
	
}
