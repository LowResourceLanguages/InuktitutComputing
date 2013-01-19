/*
 * Conseil national de recherche Canada 2003
 * 
 * Cr�� le 23-Dec-2003
 * par Benoit Farley
 * 
 */

/*
 * Selon Louis-Jacques Dorais dans "Inuit uqausiqatigiit", il existe
 * dans le groupe de dialectes Inuktitut de l'est canadien, 6 surfaceFormsOfAffixes
 * dialectales tr�s rapproch�es: 
 * Kivalliq (Keewatin), Aivilik, North Baffin, South Baffin, Arctic Quebec,
 * Labrador.
 * Notes: 
 * Kivalliq - souvent class� comme appartenant � l'Inuktun de l'Ouest
 * Aivilik  - entre North Baffin et Kivalliq
 * 
 * Sur le plan phonologique, les six dialectes peuvent �tre divis�s en
 * trois sous-groupes:
 * 1. Keewatin:        Kivalliq et Aivilik
 * 2. Baffin:          North Baffin et South Baffin
 * 3. Quebec-Labrador: Arctic Quebec et Labrador
 * 
 * 1. Keewatin
 * 	- &
 *  - racine + niq + terminaison grammaticale commen�ant par V:
 * 		niq>nir au lieu de niq>ni
 *  - bilC
 * 1a. Kivalliq
 * 	- apiC
 * 	- s>h en position initiale, intervocalique, et apr�s p, k et q.
 * 	- s>t quelquefois apr�s t.
 * 	- arr�t glottal (')
 * 	- up>p apr�s V
 * 	- it>t apr�s V
 * 1b. Aivilik
 * 	- apiC seulement pour C=api, sinon assimilation de api � C
 * 		ex.: tt,ll,&&,jj,nn
 * 			 nm>mm tk>kk lr>rr djg(lg)>gg
 * 			 nr>rng
 *       >>> pas de api.vel, api.uvu, api.bil
 * 	- ts>tt g�n�ralement
 * 
 * 2. Baffin
 * 	- apiC seulement pour C=api, sinon assimilation de api � C
 * 		api.api ; api.vel>vel.vel ; api.uvu>uvu.uvu ; api.bil>bil.bil
 * 	- bilC seulement pour C=bil, sinon assimilation de bil � C
 * 		bil.bil ; bil.vel>vel.vel ; bil.uvu>uvu.uvu ; bil.api>api.api
 * 	- velC et uvuC
 * 	- velC a tendance vers CC
 * 		velC>CC
 * 	- it>is si i fort (et rarement, ittu>issu si i fort)
 * 2a. North Baffin
 * 	- ps>ss
 * 	- &
 * 	- radical sit->tis- (aussi sat->tas: ex.: saturtuq>tasurtuq)
 * 	- ts>tt
 * 2b. South Baffin
 * 	- ps>ts
 * 	- &>t dans les groupes de consonnes
 * 	- &>l ou &>s entre voyelles
 * 	- t initial>s lorsque la syllabe suivante commence par s
 *  - k&>ss (Spalding)
 * 
 * 3. Arctic Quebec and Labrador
 * 	- velC>CC
 * 	- loi de Schneider
 * 3a. Arctic Quebec
 * 	- uvuC
 * 	- &>s
 * 	- &&>ts
 * 	- qp>rp qt>rt qs>rs
 * 	- souvent q initial>h
 * 	- souvent i faible>a
 * 3b. Labrador
 * 	- uvuC>CC
 * 	- ts
 * 	- qq>kq
 * 	- r>g
 * 	- q final>k
 * 	- toutes les racines doivent terminer par k
 */
package phonology.research;

import java.util.Arrays;
import java.util.Vector;

import script.Roman;


public abstract class DialectalRules {

		public static String[] spellingNorthBaffin(String word) {
			Vector v = new Vector();
			char[] m = word.toCharArray();
			if (word.length() > 3 && word.startsWith("sit"))
				m = word.replaceFirst("sit", "tis").toCharArray();
			apiC2CC(m);
			bilC2CC(m);
			v.add(new String(m));
			char[] mA = t2s_strongI(m);
			if (!Arrays.equals(mA, m))
				v.add(new String(mA));
			char[] m2 = velC2CC(m);
			if (!Arrays.equals(m2, m)) {
				v.add(new String(m2));
				char[] m2A = t2s_strongI(m2);
				if (!Arrays.equals(m2A, m2))
					v.add(new String(m2A));
			}
			return (String[]) v.toArray(new String[] {
			});
		}

		private static char[] apiC2CC(char[] chars) {
			for (int i = 0; i < chars.length - 1; i++) {
				if (Roman.apicalConsonant(chars[i])) {
					if (Roman.isConsonant(chars[i + 1])
						&& !Roman.apicalConsonant(chars[i + 1])) {
						chars[i] = chars[i + 1];
						i = i + 2;
					}
				}
			}
			return chars;
		}

		private static char[] bilC2CC(char[] chars) {
			for (int i = 0; i < chars.length - 1; i++) {
				if (Roman.bilabialConsonant(chars[i])) {
					if (Roman.isConsonant(chars[i + 1])
						&& !Roman.bilabialConsonant(chars[i + 1])) {
						chars[i] = chars[i + 1];
						i = i + 2;
					}
				}
			}
			return chars;
		}

		private static char[] velC2CC(char[] chars) {
			for (int i = 0; i < chars.length - 1; i++) {
				if (Roman.velarConsonant(chars[i])) {
					if (Roman.isConsonant(chars[i + 1])
						&& !Roman.velarConsonant(chars[i + 1])) {
						chars[i] = chars[i + 1];
						i = i + 2;
					}
				}
			}
			return chars;
		}

		public static char[] t2s_strongI(char[] chars) {
			char[] cs = new char[chars.length];
			for (int i = 0; i < chars.length - 1; i++) {
				if (chars[i] == 'i') {
					if (chars[i + 1] == 't') {
						cs[i] = chars[i];
						cs[i + 1] = 's';
						i = i + 1;
					} else {
						cs[i] = chars[i];
					}
				} else {
					cs[i] = chars[i];
				}
			}
			cs[chars.length - 1] = chars[chars.length - 1];
			return cs;
		}

	}
