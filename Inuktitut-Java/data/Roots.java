package data;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import lib.html;

import org.apache.log4j.Logger;

import utilities.MonURLDecoder;
import utilities1.Util;
import script.TransCoder;

public class Roots {

	private static Logger LOG = Logger.getLogger(Roots.class);
	//static OutputStreamWriter out;
	static boolean syllabic = true;
	static String inuktitutDisplayFont = null;

	static String lang = "en";
	static String lang_default = "en";

	//-------------------------------------------------------------------
	// DÉFINITION D'UNE RACINE
	//-------------------------------------------------------------------

	// Les arguments 'args' sont:
	//   [0]: le morph�me
	//
	//   Note: toutes les racines correspondant au morph�me sont retourn�es.
	//
	// Les arguments optionnels sont:
	//   -l <lang: e | a>
	// Il est possible que cette m�thode soit appel�e avec des arguments 
	// optionnels qui ne lui sont pas destin�s.  Ces arguments optionnels
	// sont sp�cifi�s par une cl� -'X' suivie d'une valeur.

	public static void getDef(String[] args, PrintStream out) {
		try {
			String rootId = null;
			lang = MonURLDecoder.decode(Util.getArgument(args, "l"));
			if (lang == null) {
				lang = lang_default;
			}
			rootId = MonURLDecoder.decode(Util.getArgument(args, "m"));
			String font = Util.getArgument(args, "p");
			if (font != null)
				inuktitutDisplayFont = font;

			StringBuffer output = new StringBuffer();

			//-------------------------------------------------------
			//            StringBuffer sbd = new StringBuffer();
			//            for (int i = 0; i < args.length; i++)
			//                sbd.append("args["+i+"]: " + args[i] + "<br>\n");
			//             out.append(sbd.toString());
			//             out.flush();
			//-------------------------------------------------------

			Base base = null;

			base = (Base) LinguisticDataAbstract.getBase(rootId);
			output.append("<p>");
			output.append(composeRootDisplay(base));
			output.append("<br><br>\n");
			output.append("</p>");
			out.append(output.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String composeRootDisplay(Base base) {
		StringBuffer output = new StringBuffer();
		output.append("<span style=\"font-size:10pt;color:green\">");
		output.append("<span style=\"font-weight:bold;\">");
		if (inuktitutDisplayFont == null)
			output.append(TransCoder.romanToUnicode(base.morpheme));
		else {
			output.append("<span style=\"font-family:"
					+ inuktitutDisplayFont + "\">");
			output.append(TransCoder.romanToLegacy(base.morpheme,
					inuktitutDisplayFont));
			output.append("</span>");
		}
		output.append("&nbsp;&nbsp;");
		output.append(base.morpheme);
		output.append("</span></span>");
		output.append("<span style=\"font-style:italic;font-size:8pt\">");
		output.append(" (");
		output.append(")");
		output.append("</span>");
		output.append("<hr>");
		// Variantes
		if (base.variant != null) {
			output
					.append("\n<p><span style=\"font-weight:bold\">Variante(s)</span><br>"
							+ base.variant);
		}

		// Signification
		output.append("\n<p><span style=\"font-weight:bold\">");
		output.append((lang.equals("en")) ? "Meaning" : "Signification");
		output.append("</span><br>");
		String meaning = ((lang.equals("en")) ? base.englishMeaning : base.frenchMeaning);
		if (meaning != null)
			if (base.isTransitiveVerb()) {
				/*
				 * Un mot commen�ant par / est un verbe � transformer au passif; le s.t.
				 * ou s.o. (ou qqch. ou qqn en fran�ais) repr�sente le compl�ment d'objet
				 * direct du verbe qui, dans le sens au passif, est supprim�, de m�me que
				 * pour les verbes marqu�s 'res'.
				 */
				output.append("<ul>");
				output.append("<li>");
				if (lang.equals("en"))
					output
							.append("<i>when used with transitive verb endings or suffixes, involving 2 actors:</i> ");
				else
					output
							.append("<i>lorsqu'utilisé avec des terminaisons verbales ou des suffixes transitifs, mettant en jeu 2 acteurs:</i>");
				output.append("<br>");
				output.append(composeColorDisplay(base
						.getTransitiveMeaning(lang), "red"));
				output.append("</li>");
				String passive = base.getPassiveMeaning(lang);
				String reflexive = base.getReflexiveMeaning(lang);
				String resultive = base.getResultMeaning(lang);
				output.append("<li>");
				if (lang.equals("en")) {
					output
							.append("<i>when used with intransitive verb endings or suffixes, involving only 1 actor:</i> ");
					output.append("<ul>");
					if (passive != null)
						output.append("<li><i>passive:</i> "
								+ composeColorDisplay(passive, "red")
								+ "</li>");
					if (base.nature != null && base.nature.equals("res")
							&& resultive != null)
						output.append("<li><i>non-passive:</i> "
								+ composeColorDisplay(resultive, "red")
								+ "</li>");
					else if (reflexive != null)
						output.append("<li><i>reflexive:</i> "
								+ composeColorDisplay(reflexive, "red")
								+ "</li>");
					output.append("</ul>");
				} else if (lang.equals("fr")) {
					output
							.append("<i>lorsqu'utilisé avec des terminaisons verbales ou des suffixes intransitifs, mettant en jeu un seul acteur:</i> ");
					output.append("<ul>");
					if (passive != null)
						output.append("<li><i>passif:</i> "
								+ composeColorDisplay(passive, "red")
								+ "</li>");
					if (base.nature != null && base.nature.equals("res")
							&& resultive != null)
						output.append("<li><i>non-passif:</i> "
								+ composeColorDisplay(resultive, "red")
								+ "</li>");
					else if (reflexive != null)
						output.append("<li><i>réfléchi:</i> "
								+ composeColorDisplay(reflexive, "red")
								+ "</li>");
					output.append("</ul>");
				} else {
					output.append("???");
				}
				output.append("</li>");
				output.append("</ul>");
			} else
				output.append(composeColorDisplay(
						((lang.equals("en")) ? base.englishMeaning : base.frenchMeaning),
						"red"));
		else
			output.append("<span style=\"color:red\"><i>"
					+ (lang.equals("en") ? "To be completed"
							: "&Agrave; compl&eacute;ter") + "</i></span>");

		// Type
		output.append("\n<p><span style=\"font-weight:bold\">Type</span><br>");
		if (base.type.equals("v")) {
			// VERBE
			if (base.isTransitiveVerb()) {
				// transitif
				output.append("<ul>");
				output.append("<li>");
				if (lang.equals("en")) {
					output.append("transitive ");
					output.append("verbal ");
					if (base.combinedMorphemes != null)
						output.append("composite lexicalized ");
					output.append("root");
				} else if (lang.equals("fr")) {
					output.append("racine ");
					output.append("verbale ");
					if (base.combinedMorphemes != null)
						output.append("composée et lexicalisée, ");
					output.append("transitive");
				} else {
					output.append("???");
				}
				output.append("</li>");
				if (base.antipassive != null) {
					output.append("<li>");
					if (lang.equals("en")) {
						output.append("requires the suffix(es) ");
					} else if (lang.equals("fr")) {
						output.append("requiert le(s) suffixe(s) ");
					}
					String[] antipassives = base.antipassive.split(" ");
					for (int i = 0; i < antipassives.length; i++) {
						String[] parts = antipassives[i].split("/");
						output.append("<a href=");
						output
								.append("\"javascript:appelerDescriptionSuffixe(");
						if (inuktitutDisplayFont == null)
							output.append("'" + antipassives[i] + "',null");
						else
							output.append("'" + antipassives[i] + "','"
									+ inuktitutDisplayFont + "'");
						output.append(")\">");
						output.append(parts[0]);
						output.append("</a>");
						output.append("<sub style=\"font-size:75%\">"
								+ parts[1] + "</sub>");
						output.append("&nbsp;");
					}
					if (lang.equals("en")) {
						output
								.append(" when used with intransitive verb endings or suffixes in order to keep the transitive sense of the verb and avoid a passive or reflexive interpretation:");
					} else if (lang.equals("fr")) {
						output
								.append(" lorsqu'utilisée avec des terminaisons verbales ou des suffixes intransitifs de façon à conserver le sens transitif du verbe et ne pas être interprétée passivement ou réflexivement&nbsp;:");
					}
					for (int i = 0; i < antipassives.length; i++) {
						String combination = base.id + "+" + antipassives[i];
						if (LOG.isDebugEnabled())
							LOG.debug("combination= '" + combination + "'");
						String combined = Morpheme.combine(combination, true,
								null);
						output.append(composeCombinationDisplay(combined));
					}
					output.append("</li>");
				}
				if (base.nature != null && base.nature.equals("res")) {
					output.append("<li>");
					if (lang.equals("en")) {
						output
								.append("<i>process verb:</i> when used intransitively, that is, with intransitive verb endings or suffixes, ");
						output
								.append("it may have a passive interpretation, or a non-passive interpretation; ");
						output
								.append("but unlike other transitive verbal roots, the non-passive interpretation is not reflexive: ");
						output
								.append("the subject does not act on itself, it is rather going through a process.  ");
						output
								.append("For example, when one say: \" the eggs are cooking\", they are not cooking themselves; ");
						output
								.append("they are rather going through a \"cooking\" process.");
					} else if (lang.equals("fr")) {
						output
								.append("<i>verbe de processus:</i> lorsqu'utilisé intransitivement, i.e. avec des terminaisons verbales ou des suffixes intransitifs, ");
						output
								.append("il peut être interprété passivement ou activement; ");
						output
								.append("mais contrairement à d'autres racines verbales transitives, l'interprétation active n'est pas une action réfléchie: ");
						output
								.append("le sujet n'agit pas sur lui-même, il passe plutôt par un processus.");
					} else {
						output.append("???");
					}
					output.append("</li>");
				}
				output.append("</ul>");
			} else if (base.isIntransitiveVerb()) {
				// intransitif
				output.append("<ul>");
				output.append("<li>");
				if (lang.equals("en")) {
					output.append("intransitive verbal ");
					if (base.combinedMorphemes != null)
						output.append("composite lexicalized ");
					output.append("root");
				} else if (lang.equals("fr")) {
					output.append("racine ");
					output.append("verbale ");
					if (base.combinedMorphemes != null)
						output.append("composée et lexicalisée, ");
					output.append("intransitive");
				} else {
					output.append("???");
				}
				output.append("</li>");
				if (base.nature != null) {
					output.append("<li>");
					if (base.nature.equals("a")) {
						if (lang.equals("en")) {
							output
									.append("adjectival verb: expresses a qualitative state");
						} else if (lang.equals("fr")) {
							output
									.append("verbe adjectif: exprime un état qualitatif");
						} else {
							output.append("???");
						}
					} else if (base.nature.equals("e")) {
						if (lang.equals("en")) {
							output.append("verb of emotion, of feeling");
						} else if (lang.equals("fr")) {
							output.append("verbe d'émotion, de sentiment");
						} else {
							output.append("???");
						}
					} else {
						if (lang.equals("en")) {
							output.append("(nature?)");
						} else if (lang.equals("fr")) {
							output.append("(nature?)");
						} else {
							output.append("???");
						}
					}
					output.append("</li>");
				}

				if (base.intransinfix != null) {
					// SUFFIXE D'INTRANSITIVITÉ
					output.append("<li>");
					String[] intransinfixes = base.intransinfix.split(" ");
					if (lang.equals("en")) {
						output.append("usually followed by ");
						output.append(intransinfixes.length == 1 ? "the suffix"
								: "one of the suffixes");
					} else if (lang.equals("fr")) {
						output.append("habituellement suivie ");
						output.append(intransinfixes.length == 1 ? "du suffixe"
								: "d'un des suffixes");
					}
					for (int i = 0; i < intransinfixes.length; i++) {
						output.append(" ");
						output.append(html.anchorHref);
						output.append(html.callDescriptionOfSuffix);
						output.append(intransinfixes[i]);
						output.append(html.endOfCall);
						output.append(intransinfixes[i].substring(0,
								intransinfixes[i].indexOf('/')));
						output.append(html.endOfRef);
						output.append("<sub style=\"font-size:75%\">"
								+ intransinfixes[i].substring(intransinfixes[i]
										.indexOf('/') + 1) + "</sub>");
					}
					if (lang.equals("en"))
						output
								.append(" when used intransitively, that is, with intransitive verb endings or suffixes:");
					else if (lang.equals("fr")) {
						output
								.append(" lorsqu'utilisée intransitivement, i.e. avec des terminaisons verbales ou des suffixes intransitifs&nbsp:");
					}
					for (int i = 0; i < intransinfixes.length; i++) {
						String combination = base.id + "+" + intransinfixes[i];
						if (LOG.isDebugEnabled())
							LOG.debug("combination= '" + combination + "'");
						String combined = Morpheme.combine(combination, true,
								null);
						output.append(composeCombinationDisplay(combined));
					}
					output.append("</li>");
				}

				if (base.transinfix != null) {
					// SUFFIXE DE TRANSITIVITÉ
					output.append("<li>");
					if (base.transinfix.equals("nil")) {
						// NIL
						if (lang.equals("en")) {
							output
									.append("can be used transitively, that is, with transitive verb endings "
											+ "or suffixes, without being first added a specific suffix to make it transitive");
							//	                            output.append("does not need to be followed by any specific suffix");
							//	                            output
							//	                                    .append(" when used with transitive verb endings or suffixes");
						} else if (lang.equals("fr")) {
							output
									.append("peut être utilisée transitivement, i.e. avec des terminaisons verbales "
											+ "et des suffixes transitifs, sans qu'il faille d'abord lui ajouter un suffixe spécifique pour la "
											+ "rendre transitive");
							//	                            output.append("ne nécessite d'être suivie d'aucun suffixe spécifique");
							//	                            output.append(" lorsqu'utilisée avec des terminaisons verbales ou des suffixes transitifs");
						} else {
							output.append("???");
						}
					} else {
						// AUTRE SUFFIXE DE TRANSITIVITÉ
						String[] transinfixes = base.transinfix.split(" ");
						if (lang.equals("en")) {
							output.append("usually followed by ");
							output
									.append(transinfixes.length == 1 ? "the suffix"
											: "one of the suffixes");
						} else if (lang.equals("fr")) {
							output.append("habituellement suivie ");
							output
									.append(transinfixes.length == 1 ? "du suffixe"
											: "d'un des suffixes");
						}
						for (int i = 0; i < transinfixes.length; i++) {
							output.append(" ");
							output.append(html.anchorHref);
							output.append(html.callDescriptionOfSuffix);
							output.append(transinfixes[i]);
							output.append(html.endOfCall);
							output.append(transinfixes[i].substring(0,
									transinfixes[i].indexOf('/')));
							output.append(html.endOfRef);
							output.append("<sub style=\"font-size:75%\">"
									+ transinfixes[i].substring(transinfixes[i]
											.indexOf('/') + 1) + "</sub>");
						}
						if (lang.equals("en"))
							output
									.append(" when used transitively, that is, with transitive verb endings or suffixes");
						else if (lang.equals("fr")) {
							output
									.append(" lorsqu'utilisée transitivement, i.e. avec des terminaisons verbales ou des suffixes transitifs");
						}
						for (int i = 0; i < transinfixes.length; i++) {
							String combination = base.id + "+"
									+ transinfixes[i];
							if (LOG.isDebugEnabled())
								LOG.debug("combination= '" + combination + "'");
							String combined = Morpheme.combine(combination,
									true, null);
							output.append(composeCombinationDisplay(combined));
						}
					}
					output.append("</li>");
				}
				output.append("</ul>");
			} else {
				// autre?
				output.append("<ul>");
				output.append("<li>");
				if (lang.equals("en")) {
					output.append("verb root");
				} else if (lang.equals("fr")) {
					output.append("racine verbale");
				} else {
					output.append("???");
				}
				output.append("</li>");
				output.append("</ul>");
			}
		} else if (base.type.equals("n")) {
			// NOM
			if (base.nature != null) {
				output.append("<ul>");
				output.append("<li>");
			}
			String nombre = null;
			if (base.number != null)
				nombre = LinguisticDataAbstract.getTextualRendering(base.number,
						lang);
			if (lang.equals("en")) {
				if (base.combinedMorphemes != null)
					output.append("complex ");
				if (nombre != null && !nombre.equals("singular"))
					output.append(nombre + " ");
				output.append("noun root");
			} else if (lang.equals("fr")) {
				output.append("racine nominale");
				if (nombre != null && !nombre.equals("singulier"))
					output.append(" " + nombre + "le");
				if (base.combinedMorphemes != null)
					output.append(" complexe");
			} else {
				output.append("???");
			}
			if (base.nature != null) {
				output.append("</li>");
				output.append("<li>");
				output.append("<i>");
				if (lang.equals("en"))
					output.append("semantic property: ");
				else if (lang.equals("fr"))
					output.append("trait sémantique : ");
				output.append("</i>");
				if (base.nature.equals("ana")) {
					if (lang.equals("en")) {
						output.append("body part");
					} else if (lang.equals("fr")) {
						output.append("partie du corps");
					} else {
						output.append("???");
					}
				} else if (base.nature.equals("bot")) {
					if (lang.equals("en")) {
						output.append("plant, tree, etc.");
					} else if (lang.equals("fr")) {
						output.append("plante, arbre, etc.");
					} else {
						output.append("???");
					}
				} else if (base.nature.equals("zoo")) {
					if (lang.equals("en")) {
						output.append("animal, bird, etc.");
					} else if (lang.equals("fr")) {
						output.append("animal, oiseau, etc.");
					} else {
						output.append("???");
					}
				} else if (base.nature.equals("nb")) {
					if (lang.equals("en")) {
						output.append("number; quantity");
					} else if (lang.equals("fr")) {
						output.append("nombre; quantité");
					} else {
						output.append("???");
					}
				} else if (base.nature.equals("place")) {
					if (lang.equals("en")) {
						output
								.append("name or part of name of place: community, town, etc.");
					} else if (lang.equals("fr")) {
						output
								.append("nom ou partie de nom d'endroit: communauté, ville, etc.");
					} else {
						output.append("???");
					}
				}
				output.append("</li>");
				output.append("</ul>");
			}
		} else if (base.type.equals("pr")) {
			// PRONOM
			if (base.nature == null) {
				if (lang.equals("en")) {
					output.append("pronoun");
				} else if (lang.equals("fr")) {
					output.append("pronom");
				} else {
					output.append("???");
				}
			} else {
				output.append("<ul>");
				if (base.getNature().equals("per")) {
					output.append("<li>");
					if (lang.equals("en")) {
						output.append("personal pronoun");
						output.append("<br>");
						if (((Pronoun) base).isFirstPerson())
							output.append("1<sup>st</sup>");
						else if (((Pronoun) base).isSecondPerson())
							output.append("2<sup>nd</sup>");
						else
							output.append("3<sup>rd</sup>");
						output.append(" person ");
						if (base.isSingular())
							output.append("singular");
						else if (base.isDual())
							output.append("dual");
						else
							output.append("plural");
					} else if (lang.equals("fr")) {
						output.append("pronom personnel");
						output.append("<br>");
						if (((Pronoun) base).isFirstPerson())
							output.append("1<sup>&egrave;re</sup>");
						else if (((Pronoun) base).isSecondPerson())
							output.append("2<sup>&egrave;me</sup>");
						else
							output.append("3<sup>&egrave;me</sup>");
						output.append(" personne du ");
						if (base.isSingular())
							output.append("singulier");
						else if (base.isDual())
							output.append("duel");
						else
							output.append("pluriel");
					} else {
						output.append("???");
					}
					output.append("</li>");
				} else if (((Pronoun) base).getNature().equals("int")) {
					output.append("<li>");
					if (lang.equals("en")) {
						if (base.isSingular())
							output.append("singular");
						else if (base.isDual())
							output.append("dual");
						else
							output.append("plural");
						output.append(" interrogative pronoun");
					} else if (lang.equals("fr")) {
						output.append("pronom interrogatif ");
						if (base.isSingular())
							output.append("<br>singulier");
						else if (base.isDual())
							output.append("<br>duel");
						else
							output.append("<br>pluriel");
					} else {
						output.append("???");
					}
					output.append("</li>");
				} else {
					output.append("<li>");
					if (lang.equals("en")) {
						output.append(((Pronoun) base).getNature() + " pronoun");
					} else if (lang.equals("fr")) {
						output.append("pronom " + ((Pronoun) base).getNature());
					} else {
						output.append("???");
					}
					output.append("</li>");
				}
				output.append("</ul>");
			}
		} else if (base.type.equals("rp")) {
			// RACINE DE PRONOM PERSONNEL
			output.append("<ul>");
			if (((Pronoun) base).getNature().equals("per")) {
				if (lang.equals("en")) {
					output.append("<li>");
					output.append("root of personal pronoun");
					output.append("</li>");
					output.append("<li>");
					if (((Pronoun) base).isFirstPerson())
						output.append("1<sup>st</sup>");
					else if (((Pronoun) base).isSecondPerson())
						output.append("2<sup>nd</sup>");
					else
						output.append("3<sup>rd</sup>");
					output.append(" person");
					output.append("</li>");
				} else if (lang.equals("fr")) {
					output.append("<li>");
					output.append("racine de pronom personnel");
					output.append("</li>");
					output.append("<li>");
					if (((Pronoun) base).isFirstPerson())
						output.append("1<sup>&egrave;re</sup>");
					else if (((Pronoun) base).isSecondPerson())
						output.append("2<sup>&egrave;me</sup>");
					else
						output.append("3<sup>&egrave;me</sup>");
					output.append(" personne");
					output.append("</li>");
				} else {
					output.append("<li>");
					output.append("???");
					output.append("</li>");
				}
			}
			output.append("</ul>");
		} else if (base.type.equals("a")) {
			// ADVERBE
			if (lang.equals("en")) {
				output.append("adverb");
			} else if (lang.equals("fr")) {
				output.append("adverbe");
			} else {
				output.append("???");
			}
			if (base.nature != null) {
				String wordcase = LinguisticDataAbstract.getTextualRendering(
						base.nature, lang);
				if (lang.equals("en")) {
					output.append(" in the ");
					output.append(wordcase);
					output.append(" case");
				} else if (lang.equals("fr")) {
					output.append(" au cas ");
					output.append(wordcase);
				}
			}
		} else if (base.type.equals("c")) {
			// CONJONCTION
			if (lang.equals("en")) {
				output.append("conjunction");
			} else if (lang.equals("fr")) {
				output.append("conjonction");
			} else {
				output.append("???");
			}
		} else if (base.type.equals("e")) {
			// EXCLAMATION
			if (lang.equals("en")) {
				output.append("expression; exclamation");
			} else if (lang.equals("fr")) {
				output.append("expression; exclamation");
			} else {
				output.append("???");
			}
		} else if (base.type.equals("pd")) {
			// PRONOM DÉMONSTRATIF
			if (lang.equals("en")) {
				output.append("demonstrative pronoun");
				output.append("<br>in the ");
				if (((Demonstrative) base).isSingular())
					output.append("singular");
				else if (((Demonstrative) base).isDual())
					output.append("dual");
				else
					output.append("plural");
				output.append(" nominative case");
				output
						.append("<br><i>- in the other cases, the demonstrative pronoun endings ");
				output
						.append("are added to the corresponding demonstrative pronoun root ");
				output.append("<b>" + ((Demonstrative) base).getRoot()
						+ "</b></i>");
			} else if (lang.equals("fr")) {
				output.append("pronom démonstratif");
				output.append("<br>au cas nominatif ");
				if (((Demonstrative) base).isSingular())
					output.append("singulier");
				else if (((Demonstrative) base).isDual())
					output.append("duel");
				else
					output.append("pluriel");
				output
						.append("<br><i>- dans les autres cas, les terminaisons de pronom démonstratif ");
				output
						.append("sont ajoutées à la racine de pronom démonstratif correspondante ");
				output.append("<b>" + ((Demonstrative) base).getRoot()
						+ "</b></i>");
			} else {
				output.append("???");
			}
		} else if (base.type.equals("ad")) {
			// ADVERBE DÉMONSTRATIF
			if (lang.equals("en")) {
				output.append("demonstrative adverb");
				output.append("<br>in the nominative case");
				output
						.append("<br><i>- in the other cases, the demonstrative adverb endings ");
				output
						.append("are added to the corresponding demonstrative adverb root ");
				output.append("<b>" + ((Demonstrative) base).getRoot()
						+ "</b></i>");
			} else if (lang.equals("fr")) {
				output.append("adverbe démonstratif");
				output.append("<br>au cas nominatif ");
				output
						.append("<br><i>- dans les autres cas, les terminaisons d'adverbe démonstratif ");
				output
						.append("sont ajoutées à la racine d'adverbe démonstratif correspondante ");
				output.append("<b>" + ((Demonstrative) base).getRoot()
						+ "</b></i>");
			} else {
				output.append("???");
			}
		} else if (base.type.equals("rpd")) {
			// RACINE DE PRONOM DÉMONSTRATIF
			if (lang.equals("en")) {
				output.append("root of demonstrative pronoun");
				output
						.append("<br>corresponding to the nominative demonstrative pronoun ");
				output.append("<b>" + ((Demonstrative) base).getRoot() + "</b>");
				output.append("<br>in the ");
				if (((Demonstrative) base).isSingular())
					output.append("singular");
				else if (((Demonstrative) base).isDual())
					output.append("dual");
				else
					output.append("plural");
				output
						.append("<br><i>- this root is added demonstrative pronoun endings ");
				output
						.append("to form demonstrative pronouns in cases other than the nominative case</i>");
			} else if (lang.equals("fr")) {
				output.append("racine de pronom démonstratif");
				output
						.append("<br>correspondant au pronom démonstratif nominatif  ");
				output.append("<b>" + ((Demonstrative) base).getRoot() + "</b>");
				output.append("<br>au ");
				if (((Demonstrative) base).isSingular())
					output.append("singulier");
				else if (((Demonstrative) base).isDual())
					output.append("duel");
				else
					output.append("pluriel");
				output
						.append("<br><i>- on ajoute à cette racine les terminaisons de pronom démonstratif ");
				output
						.append("pour former des pronoms démonstratifs dans les cas autres que le cas nominatif</i>");
			} else {
				output.append("???");
			}
		} else if (base.type.equals("rad")) {
			// RACINE D'ADVERBE DÉMONSTRATIF
			if (lang.equals("en")) {
				output.append("root of demonstrative adverb");
				output
						.append("<br>corresponding to the nominative demonstrative adverb ");
				output.append("<b>" + ((Demonstrative) base).getRoot() + "</b>");
				output
						.append("<br><i>- this root is added demonstrative adverb endings ");
				output
						.append("to form demonstrative adverbs in cases other than the nominative case</i>");
			} else if (lang.equals("fr")) {
				output.append("racine d'adverbe démonstratif");
				output
						.append("<br>correspondant à l'adverbe démonstratif nominatif  ");
				output.append("<b>" + ((Demonstrative) base).getRoot() + "</b>");
				output
						.append("<br><i>- on ajoute à cette racine les terminaisons d'adverbe démonstratif ");
				output
						.append("pour former des adverbes démonstratifs dans les cas autres que le cas nominatif</i>");
			} else {
				output.append("???");
			}
		} else {
			output.append(LinguisticDataAbstract.getTextualRendering(base.type,
					lang));
		}

		// Combinaison
		if (base.combinedMorphemes != null) {
			output.append("<p><span style=\"font-weight:bold\">");
			output
					.append((lang.equals("en")) ? "Combination"
							: "Combinaison");
			output.append("</span><br>");
			output
					.append((lang.equals("en")) ? "This complex root is a combination of: "
							: "Cett racine complexe est la combinaison de: ");
			String baseId = base.combinedMorphemes[0];
			Morpheme root = LinguisticDataAbstract.getMorpheme(baseId);
			if (root != null) {
				output.append("<a href=\"javascript:appelerDescriptionRacine(");
				if (inuktitutDisplayFont == null)
					output.append("'" + baseId + "',null");
				else
					output.append("'" + baseId + "','"
							+ inuktitutDisplayFont + "'");
				output.append(")\">");
			}
			output.append(baseId.substring(0, baseId.indexOf('/')));
			if (root != null)
				output.append(html.endOfRef);
			output.append("<sub style=\"font-size:75%\">"
					+ baseId.substring(baseId.indexOf('/') + 1) + "</sub>");
			for (int i = 1; i < base.combinedMorphemes.length; i++) {
				String sufId = base.combinedMorphemes[i];
				Morpheme inf = LinguisticDataAbstract.getMorpheme(sufId);
				output.append("&nbsp;+&nbsp;");
				if (inf != null) {
					output
							.append("<a href=\"javascript:appelerDescriptionSuffixe(");
					if (inuktitutDisplayFont == null)
						output.append("'" + sufId + "',null");
					else
						output.append("'" + sufId + "','"
								+ inuktitutDisplayFont + "'");
					output.append(")\">");
					int posOfSlash = sufId.indexOf('/');
					output.append(sufId.substring(0, posOfSlash));
					output.append("</a>");
					output.append("<sub style=\"font-size:75%\">"
							+ sufId.substring(posOfSlash + 1) + "</sub>");
				} else {
					output.append(sufId);
				}
				output.append("&nbsp;");
			}
			output.append("<p><table border=1 style=\"font-size:8pt;\"><tr>");
			output.append("<td>");
			if (root != null) {
				output.append(root.morpheme);
				output.append("</td><td>");
				output.append(lang.equals("en") ? root.englishMeaning
						: root.frenchMeaning);
				output.append("</td>");
			} else {
				output.append(baseId.substring(0, baseId.indexOf('/')));
				output.append("</td>");
				output.append("<td>&nbsp;</td>");
			}
			output.append("</tr>");
			for (int i = 1; i < base.combinedMorphemes.length; i++) {
				output.append("<tr><td>");
				String sufId = base.combinedMorphemes[i];
				Morpheme inf = LinguisticDataAbstract.getMorpheme(sufId);
				if (inf != null) {
					output.append(inf.morpheme);
					output.append("</td><td>");
					output.append(lang.equals("en") ? inf.englishMeaning
							: inf.frenchMeaning);
					output.append("</td>");
				} else {
					output.append(sufId);
					output.append("</td>");
					output.append("<td>&nbsp;</td>");
				}
				output.append("</tr>");
			}
			output.append("</table></p>");
		}

		// Racines apparentées
		if (base.cfs != null) {
			output.append("\n<p><span style=\"font-weight:bold\">"
					+ (lang.equals("en") ? "Related roots"
							: "Racines apparent&eacute;es") + "</span><br>");
			for (int i = 0; i < base.cfs.length; i++) {
				output.append(html.ahrefCallDescriptionOfRoot + base.cfs[i] + html.endOfCall);
				String parts[] = base.cfs[i].split("/");
				output.append(parts[0] + "</a>");
				output.append("<sub style=\"font-size:75%\">" + parts[1]
						+ "</sub>");
				output.append("&nbsp;&nbsp;");
			}
			output.append("</p>\n");
		}

		// Sources
		String[] sources = base.sources;
		if (sources == null && base.tableName != null && base.tableName.equals("RacinesSpalding"))
			sources = new String[] { "A2" };
		if (sources != null) {
			output
					.append("\n<p><span style=\"font-weight:bold\">Sources</span><br>");
			for (int n = 0; n < sources.length; n++) {
				Source src = (Source) LinguisticDataAbstract
						.getSource(sources[n]);
				output.append(src.authorSurName + ", " + src.authorFirstName);
				if (src.authorMidName != null)
					output.append(" " + src.authorMidName);
				output.append(", \"<i>");
				output.append(src.title);
				if (src.subtitle != null)
					output.append(" " + src.subtitle);
				output.append("\"</i>. ");
				output.append(src.publisher);
				if (src.publisherMisc != null)
					output.append(", " + src.publisherMisc);
				output.append(", " + src.location + ", " + src.year + ".");
				output.append("<br><br>");
			}
			output.append("</p>");
		}

		return output.toString();

	}

	public static String composeCombinationDisplay(String str) {
		StringBuffer output = new StringBuffer();
		output.append("<br>");
		output.append("<span style=\"font-size:10pt;color:green\">");
		output.append("<span style=\"font-weight:bold;\">");
		if (inuktitutDisplayFont == null)
			output.append(TransCoder.romanToUnicode(str));
		else {
			output.append("<span style=\"font-family:"
					+ inuktitutDisplayFont + "\">");
			output.append(TransCoder.romanToLegacy(str,
					inuktitutDisplayFont));
			output.append("</span>");
		}
		output.append("&nbsp;&nbsp;");
		output.append(str);
		output.append("</span></span>");
		return output.toString();
	}

	public static void displayListOfRoots(String args[], PrintStream out) {
		lang = Util.getArgument(args, "l");
		Hashtable roots = LinguisticDataAbstract.getAllRoots();
		displayListOfMorphemes("rac", out, roots, lang);
	}

	protected static void displayListOfMorphemes(String morphemeType,
			PrintStream out, Hashtable morphemes, String lang) {
		try {
			//	            if (morphemeType.equals("rac"))
			//	                out.append(html.scriptDescRootJSP(lang,null));
			//	            else if (morphemeType.equals("suf"))
			//	                out.append(html.scriptDescSufJSP(lang,null));

			Object[] keys = morphemes.keySet().toArray();
			Arrays.sort(keys);
			Vector alpha = new Vector();
			char oldChar = (char) -1;
			for (int i = 0; i < keys.length; i++) {
				char c = ((String) keys[i]).charAt(0);
				if (c != oldChar) {
					alpha.add(new Character(c));
					oldChar = c;
				}
			}
			char alphas[] = new char[alpha.size()];

			// Chaque caractère initial
			for (int i = 0; i < alpha.size(); i++) {
				alphas[i] = ((Character) alpha.elementAt(i)).charValue();
			}

			out.append("<a name='top'></a>");
			out.append(html.alphabeticalList(alphas, morphemeType));

			char alphaChar = ((String) keys[0]).charAt(0);
			out.append("<p><a name=\"");
			out.append(morphemeType + "_" + alphaChar);
			out.append("\">");
			out.append("</a>");
			out.append("<big><b>");
			out.append(alphaChar);
			out.append("</b></big>");
			out
					.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#top'>"
							+ "<small>"
							+ (lang.equals("e") ? "[top]" : "haut")
							+ "</small>" + "</a>");
			out.append("<p>");
			int ncol = 0;
			int ncolLim = 4;
			out.append("<table border=0 style=\"font-size:8pt;\"><tr>");
			// Pour chaque base:
			for (int i = 0; i < keys.length; i++) {
				Morpheme morpheme = (Morpheme) morphemes.get(keys[i]);
				if (((String) keys[i]).charAt(0) != alphaChar) {
					ncol = 0;
					alphaChar = ((String) keys[i]).charAt(0);
					out.append("</tr></table></p><p><a name=\"");
					out.append(morphemeType + "_" + alphaChar);
					out.append("\">");
					out.append("</a>");
					out.append("<big><b>");
					out.append(alphaChar);
					out.append("</b></big>");
					out
							.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
									+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#top'>"
									+ "<small>"
									+ (lang.equals("e") ? "[top]" : "haut")
									+ "</small>" + "</a>");
					out.append("<p>");
					out.append("<table border=0 style=\"font-size:8pt;\"><tr>");
				}
				out.append("<td>");
				out
						.append(morphemeType.equals("rac") ? "<a href=\"javascript:appelerDescriptionRacine("
								: "<a href=\"javascript:appelerDescriptionSuffixe(");
				out.append("'" + (String) keys[i] + "',null)\"");
				if ((lang.equals("e") && morpheme.englishMeaning != null)
						|| (lang.equals("f") && morpheme.frenchMeaning != null)) {
					out.append(" title=\"");
					out.append(lang.equals("e") ? morpheme.englishMeaning
							: morpheme.frenchMeaning);
					out.append("\"");
				}
				out.append(">");
				out.append(morpheme.morpheme);
				out.append("</a>");
				out.append("<sub style=\"font-size:75%\">"
						+ morpheme.idObj.signature + "</sub>");
				out.append(" &nbsp;&nbsp;");
				out.append("</td>");
				ncol++;
				if (ncol == ncolLim) {
					ncol = 0;
					out.append("</tr><tr>");
				}
			}
			out.append("</tr></table>");
		} catch (Exception e) {
		}
	}

	static public String composeColorDisplay(String txt, String color) {
		return "<span style=\"color:" + color + "\">" + txt + "</span>";
	}

}
