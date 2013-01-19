/*
 * Conseil national de recherche Canada 2004/ National Research Council Canada
 * 2004
 * 
 * Cr�� le / Created on 27-Aug-2004 par / by Benoit Farley
 *  
 */
package data;

import data.Action;

public class SurfaceForm {

    public String form;

    public String context;

    public Action action1;

    public Action action2;

    public String morphemeIdentity;

    public SurfaceForm(String form, String morphemeIdentity, String context,
            String action1, String action2) {
        this.form = form;
        this.context = context;
        this.action1 = Action.makeAction(action1);
        this.action2 = Action.makeAction(action2);
        this.morphemeIdentity = morphemeIdentity;
    }
}