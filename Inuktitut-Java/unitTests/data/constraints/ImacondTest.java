/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Mar 6, 2006
 * par / by Benoit Farley
 * 
 */
package unitTests.data.constraints;

import java.io.ByteArrayInputStream;

import data.LinguisticDataAbstract;

import data.Morpheme;
import data.constraints.Conditions;
import data.constraints.Imacond;
import data.constraints.ParseException;

import junit.framework.TestCase;

public class ImacondTest extends TestCase {

    /*
     * Test method for 'constraints.Imacond.Start()'
     */
    public void testStart() throws ParseException {
        String str;
        ByteArrayInputStream bais;
        
        LinguisticDataAbstract.init("csv");
        
        str = "id:u/1nv";
        bais = new ByteArrayInputStream(str.getBytes());
        Imacond x = new Imacond(bais);
        Conditions cond = (Conditions)x.ParseCondition();
        String className = cond.getClass().getName();
        String expectedClassName = "data.constraints.AttrValCond";
        assertTrue(className+" au lieu de "+expectedClassName,className.equals(expectedClassName));
        Morpheme m = Morpheme.getMorpheme("u/1nv");
        assertTrue(str+" pas observée par "+m.id,cond.isMetBy(m));
        
        str = "type:v,transitivity:i,nature:a";
        bais = new ByteArrayInputStream(str.getBytes());
        x = new Imacond(bais);
        cond = (Conditions)x.ParseCondition();
        className = cond.getClass().getName();
        expectedClassName = "data.constraints.Condition$And";
        assertTrue(className+" au lieu de "+expectedClassName,className.equals(expectedClassName));
        m = Morpheme.getMorpheme("siti/1v");
        assertTrue(str+" pas observée par "+m.id,cond.isMetBy(m));
        
        str = "!cp(id:&aq/1vv)";
        bais = new ByteArrayInputStream(str.getBytes());
        x = new Imacond(bais);
        cond = (Conditions)x.ParseCondition();
        className = cond.getClass().getName();
        expectedClassName = "data.constraints.Condition$Cid";
        assertTrue(className+" au lieu de "+expectedClassName,className.equals(expectedClassName));
        m = Morpheme.getMorpheme("liq/2nv");
        assertTrue(str+" pas observée par "+m.id,cond.isMetBy(m));
        
        
    }

}