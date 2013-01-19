package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.Morpheme;


public class Item {
	
	static String inuktitutDisplayFont = null;
	
	static String main_title(String text) {
		return "<h3>"+text+"</h3>\n";
	}
	
	public static String process_marked_up(String marked_up, boolean plainText) {
		Pattern pat = Pattern.compile("((\\[\\[(.+?)\\]\\])|(##(.+?)##)|(#(.+?)#))");
		Matcher mpat = pat.matcher(marked_up);

		String res = "";
		int pos = 0;
		while (mpat.find(pos)) {
			res += marked_up.substring(pos, mpat.start());
			if (mpat.group(2)!=null) {
				res += link_to_item(mpat.group(3), plainText);
			} else if (mpat.group(4)!=null) {
				res += link_to_root(mpat.group(5), plainText);
			} else if (mpat.group(6)!=null) {
				res += link_to_suffix(mpat.group(7), plainText);
			}
			pos = mpat.end();
		}
		res += marked_up.substring(pos);
		return res;
	}
	
	static String link_to_item(String item, boolean plainText) {
		String pol;
        if (inuktitutDisplayFont==null)
            pol = "null"; 
       else
           pol = "'"+inuktitutDisplayFont+"'"; 
		StringBuffer res = new StringBuffer();
		if (!plainText) {
			res.append("<a href=\"javascript:appelerDescriptionItem('");
			res.append(item.replaceAll("\\(.+?\\)", ""));
			res.append("'," + pol + ");\">");
		}
		res.append(item.replaceAll("[()]", ""));
		if (!plainText)
			res.append("</a>");
		return res.toString();
	}
	
	static String link_to_root(String id, boolean plainText) {
		String pol;
        if (inuktitutDisplayFont==null)
            pol = "null"; 
       else
           pol = "'"+inuktitutDisplayFont+"'"; 
		StringBuffer res = new StringBuffer();
		Morpheme.Id mid = new Morpheme.Id(id);
		if (!plainText) {
			res.append("<a href=\"javascript:appelerDescriptionRacine('");
			res.append(id);
			res.append("'," + pol + ");\">");
		}
		res.append(mid.morphemeName);
		if (!plainText)
			res.append("</a>");
		if (!plainText)
			res.append("<sub style=\"font-size:75%\">");
		else
			res.append("/");
		res.append(mid.signature);
		if (!plainText)
			res.append("</sub>");
		return res.toString();
	}

	static String link_to_suffix(String id, boolean plainText) {
		String pol;
        if (inuktitutDisplayFont==null)
            pol = "null"; 
       else
           pol = "'"+inuktitutDisplayFont+"'"; 
		StringBuffer res = new StringBuffer();
		if (!plainText) {
			res.append("<a href=\"javascript:appelerDescriptionSuffixe('");
			res.append(id);
			res.append("'," + pol + ");\">");
		}
		Morpheme.Id mid = new Morpheme.Id(id);
		res.append(mid.morphemeName);
		if (!plainText)
			res.append("</a>");
		if (!plainText)
			res.append("<sub style=\"font-size:75%\">");
		res.append(mid.signature);
		if (!plainText)
			res.append("</sub>");
		return res.toString();
	}

}

