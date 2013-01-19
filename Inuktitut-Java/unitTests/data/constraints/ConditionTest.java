/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Nov 3, 2006
 * par / by Benoit Farley
 * 
 */
package unitTests.data.constraints;

import data.constraints.Condition;
import data.constraints.Conditions;
import data.constraints.Imacond;
import data.constraints.ParseException;
import junit.framework.TestCase;

public class ConditionTest extends TestCase {

    public void testToTextString() {
        String condStr;
        condStr = "!type:tn,!(type:n,number:d),!(type:n,number:p)";
        Imacond parser = new Imacond(condStr);
        try {
            Condition cond = parser.ParseCondition();
//            cond = ((Conditions)cond).expand();
            String res = ((Conditions)cond).toText("f");
            System.out.println(condStr);
            System.out.println(res);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
