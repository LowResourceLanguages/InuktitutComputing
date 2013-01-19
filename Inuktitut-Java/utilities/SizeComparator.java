/*
 * Conseil national de recherche Canada 2004/ National Research Council Canada
 * 2004
 * 
 * Créé le / Created on Dec 8, 2004 par / by Benoit Farley
 *  
 */
package utilities;

import java.lang.Object;
import java.lang.reflect.*;
import java.util.Comparator;

public class SizeComparator extends Object implements Comparator {
    private String methodName = "toString"; // default

    private int descAscIndicator = 1;

    public static final int ASCENDING = 1;

    public static final int DESCENDING = -1;

    public SizeComparator(int descAscIndicator) {
        this.descAscIndicator = descAscIndicator;
    }

    public SizeComparator(String methodName, int descAscIndicator) {
        this(descAscIndicator);
        this.methodName = methodName;
    }

    public int compare(Object o1, Object o2) {
        Object comp1 = null;
        Object comp2 = null;

        try {
            Method o1_Method = o1.getClass().getMethod(methodName, null);
            Method o2_Method = o2.getClass().getMethod(methodName, null);
            comp1 = o1_Method.invoke(o1, null);
            comp2 = o2_Method.invoke(o2, null);

        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        Comparable c1 = (Comparable) comp1;
        Comparable c2 = (Comparable) comp2;
        return c1.compareTo(c2) * descAscIndicator;
    }

    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}

