package data;

import data.Base;
import data.Item;

public class Gi_verb extends Item {
	
	static public String affichage(String lang) {
		StringBuffer output = new StringBuffer();
		String title = lang.equals("en") ?
			"GI Verbs" : "Les verbes GI";
		output.append(main_title(title));
		String def = lang.equals("en") ?
				english_definition : french_definition;
		output.append("<p>"+process_marked_up(def,false)+"</p>");
		Base [] gi_verbs = LinguisticDataAbstract.getGiVerbs();
		output.append(process_marked_up(output_of_list(gi_verbs,lang),false));
		return output.toString();
	}
	
	static String english_definition =
		"GI verbs are a class of verbs of thought, feeling or emotion, awareness, or quality (adjective),"
		+" that are directly followed by the suffix #gi/4vv# when used transitively"
		+", i.e. with transitive endings or suffixes. When used intransitively,"
		+", i.e. with intransitive endings or suffixes,"
		+" some of them are followed by one of the suffixes"
		+" #gusuk/1vv#, #suk/1vv#, #ksaq/1vv#, or #liuq/1vv# preceded by the negation #it/1vv#.";
	
	static String french_definition = 
		"Les verbes GI sont une classe de verbes de pens&eacute;e, de sentiment ou d'&eacute;motion, de connaissance,"
		+" ou de qualit&eacute; (adjectif),"
		+" qui sont suivis directement du suffixe #gi/4vv# lorsqu'utilis&eacute;s transitivement"
		+", i.e. avec des terminaisons ou des suffixes transitifs. Lorsqu'ils sont utilis&eacute;s"
		+" intransitivement, i.e. avec des terminaisons ou des suffixes transitifs,"
		+" certains d'entre eux sont suivis de l'un des suffixes"
		+" #gusuk/1vv#, #suk/1vv#, #ksaq/1vv#, ou #liuq/1vv# pr&eacute;c&eacute;d&eacute; de la négation #it/1vv#.";
	
	static String output_of_list (Base [] gi_verbs, String lang) {
		StringBuffer out = new StringBuffer();
		out.append("<p>");
		out.append(lang.equals("en")?
				"This class of verbs contains " : "Cette classe de verbes contient ");
		out.append(gi_verbs.length);
		out.append(lang.equals("en")?
				" roots:" : " racines:");
		out.append("</p><p>");
		int i;
		for (i=0; i<gi_verbs.length-1; i++) {
			out.append("##"+gi_verbs[i].id+"##, ");
		}
		out.append("##"+gi_verbs[i].id+"##");
		out.append("</p>");
		return out.toString();
	}
	
}

