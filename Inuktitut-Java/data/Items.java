package data;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import utilities1.Util;

public class Items {

	static boolean syllabiv = true;
	static String lang = "en";
	static String lang_default = "en";
    static String inuktitutDisplayFont = null;


	//-------------------------------------------------------------------
	// INFORMATION SUR UN ITEM
	//-------------------------------------------------------------------


    public static void getDef(String[] args, PrintStream out) {
        try {
            String item = null;
            String l = Util.getArgument(args,"l");
            if (l != null) lang = l;
            if (lang == null) lang = lang_default;
            item = Util.getArgument(args,"m");
            String font = Util.getArgument(args, "p");
            if (font != null)
            	inuktitutDisplayFont = font;
            String output = composeDisplay(item);
            out.print(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static String composeDisplay(String item) {
		String className = item.replaceAll(" ", "_");
		className = className.substring(0,1).toUpperCase()+className.substring(1);
		Class itemClass = null;
		try {
			itemClass = Class.forName("data."+className);
			String aff = (String) itemClass.getMethod("affichage", new Class[]{String.class}).invoke(null, new Object[]{lang});
			return aff;
		} catch (ClassNotFoundException e) {
			return (lang.equals("en") ?
					"There is no information on the item '" : "Il n'y a pas d'information sur l'item '")
					+ item + "'"
					;
		} catch (IllegalArgumentException e) {
			return "error - illegal argument exception to method 'affichage'";
		} catch (SecurityException e) {
			return "error - security exception to method 'affichage'";
		} catch (IllegalAccessException e) {
			return "error - illegal access exception to method 'affichage'";
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return "error - invocation target exception to method 'affichage'";
		} catch (NoSuchMethodException e) {
			return "error - no such method 'affichage'";
		}
	}
	
    

}
