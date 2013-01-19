UQA.ILA.UT  -  Inuktitut Morphological Analyser

©National Research Council Canada 2009
author: Benoît Farley, Research Officer
affiliation: Institute for Information Technology, NRC
email: Benoit.Farley@nrc-cnrc.gc.ca

The jar file Uqailaut.jar contains the Inuktitut Morphological Analyser 
developed at the Institute for Information Technology of the National 
Research Council of Canada. You are granted to use it free of charge for 
research and education purposes only.

IMPORTANT NOTE:
The Inuktitut Morphological Analyser and the Inuktitut Linguistic Database
that it uses are works in progress. The linguistic database in particular is
always in development and might be missing some morphemes. Please send
a note to the author to indicate any problem with the analyser or missing
morpheme in the database.

The Inuktitut Morphological Analyser is written in Java 1.6.

Its purpose is to decompose an inuktitut word into its constituent parts (or
morphemes). The analyser may return several decompositions for a given
word. In general, the right analysis can be found in the top three ones.
Each possible decomposition is returned in the following format:

{<sequence of characters of the morpheme>:<unique id of the morpheme>}{...}...

(See further down for a description of the decomposition format.)

It can be executed in two different ways:
  1. as an executable Jar file
  2. as a Java method

--------------------------------
1. Executable Jar file
--------------------------------
The jar file Uqailaut.jar is executable. Its main method is given an inuktitut
word on entry, in the latin alphabet (the inuktitut consonant '&' must be
entered as '%26', that is, as its URI-encoded representation), or in UTF-8 
URI-encoded Unicode syllabics (each character is %XX%XX%XX). The output 
is sent to the console (System.out) in the form of a multi-line string, each 
line of which represents a possible decomposition of the input word in the 
latin alphabet. For example, the following command from the command line:

java -jar Uqailaut.jar saqqitaujuq

returns

{saqqi:saqqik/1v}{ta:jaq/1vn}{u:u/1nv}{juq:juq/1vn}
{saqqi:saqqik/1v}{ta:jaq/1vn}{u:u/1nv}{juq:juq/tv-ger-3s}

The command line "java -jar Uqailaut.jar -help" shows how to execute the
analyser.

------------------------
2. Java Method
------------------------
The jar file Uqailaut.jar contains the 2 methods for decomposing an inuktitut 
word: 

decomposeToMultilineString(String word)
decomposeToArrayOfStrings(String word)
             
The method can be called from any Java user application where it will be 
imported from the jar file. The method takes an inuktitut word as its 
argument. The inuktitut word may be passed either in the latin 
alphabet, with the character '&' entered as '%26 (its URI-encoded 
representation), or in UTF-8 URI-encoded Unicode syllabics (each character in 
he form: %XX%XX%XX).

The multiline version returns a string as in 1. above. The other version
returns an array of strings, each containing a decomposition.

-------------------------------------------------------------
Method for meanings in decompositions
-------------------------------------------------------------
The jar file also contain two methods that take the expression of a 
decomposition as first argument, and a language identifier as second
argument ("en" for English; "fr" for French), and return the meanings of
each morpheme in the decomposition. The methods are:

getMeaningsInString(String decstr, String lang, boolean includeSurface, boolean includeId)
getMeaningsInArrayOfStrings(String decstr, String lang, boolean includeSurface, boolean includeId)

For a given expression of a decomposition 'decstr', the methods return for
each part of the decomposition its meaning in the language 'lang'.
If the argument 'includeSurface' is true, the meaning is prefixed with the
sequence of characters of that part in the word; 
if the the argument 'includeId' is true, the meaning is prefixed with the
unique id.

For example, 
getMeaningsInArrayOfStrings("{saqqi:saqqik/1v}{ta:jaq/1vn}{u:u/1nv}{juq:juq/1vn}","en",true,true) will
return the following four strings:

saqqi:saqqik/1v---to make s.t. appear or show to view
ta:jaq/1vn---he/that on whom/which the action is done (passive)
u:u/1nv---existence; is
juq:juq/1vn---one who/something that does the action

--------------------------------------------------------------------------------------
A Java user application can be executed this way by including the Uqailaut
jar file into the classpath along with the path of the directory that contains
the user application:

java -classpath <application's root directory>;Uqailaut.jar <user application> <arguments>

IMPORTANT:
Just make sure to include in your code (see in the example below):
   1. the two 'import' lines
   2. the line that initializes the linguistic database

import applications.Decomposer;
import donnees.DonneesLinguistiquesAbstract;
...
DonneesLinguistiquesAbstract.init(); // initialize the linguistic database
String word; // either latin alphabet or UTF-8 URI-encoded Unicode syllabics
String lang; // either "en" or "fr"
...
String decompositions[] = Decomposer.decomposeToArrayOfStrings(word);
String dec0meaning = Decomposer.getMeaningsInString(decompositions[0],lang,true,true);

--------
TEST
--------
Run the following test from the command line to make sure that the
Inuktitut Morphological Analyser works fine on your computer. You will need
another jar file: junit.jar, also included in the Uqailaut.zip package.

java -classpath Uqailaut.jar;junit.jar applications.DecomposerHansardWithDumpedDataTestForUser

-----------------------------
Tables of meanings
-----------------------------
The Uqailaut.zip file also includes a number of text files containing the English
and French meanings of the morphemes used by the Inuktitut Morphological 
Analyser and designated by their unique ids:

roots.en.txt
roots.fr.txt
suffixes.en.txt
suffixes.fr.txt

---------------------------------------
Format of decomposition
---------------------------------------
Each analysis has the form of a sequence of 1 or more {X:Y} representing
morphemes, where:
X is the surface form of the morpheme in the word
Y is the unique id of the morpheme in the linguistic database

----------------------------------
Format of UNIQUE ID
----------------------------------

<unique id> : <name>/<nb><type>
<nb> : integer
<type>:
v : verb root
n : noun root
a : adverb
c : conjunction
q : tail suffix
nn : noun-to-noun suffix
nv : noun-to-verb suffix
vn : verb-to-noun suffix
vv : verb-to-verb suffix
tv-<mode>-<subject's person & number>[-<object's person & number>-[prespas|fut]] : verb ending
tn-<case>-<number>[-<possessor's person & number>] : noun ending
tad-<case> : demonstrative adverb ending
tpd-<case>-<number> : demonstrative pronoun ending
pd-<location>-<number> : demonstrative pronoun
ad-<location> : demonstrative adverb
rad : demonstrative adverb root
rpd : demonstrative pronoun root

<mode>:	dec : declarative
			ger : gerundive
			int : interrogative
			imp : imperative
			caus : causative
			cond : conditional
			freq : frequentative
			dub : dubitative
			part : participal
<case>:	nom : nominative
		acc : accusative
		gen : genitive
		dat : dative
		abl : ablative
		loc : locative
		sim : similaris
		via : vialis
<number> :	s : singular
			d : dual
			p : plural
<location> :	sc : static or short
				ml : moving or long

