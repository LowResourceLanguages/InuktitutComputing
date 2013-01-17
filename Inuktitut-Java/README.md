1. **Java Inuktitut Project**
    1. **_Structure and description of the files_**

The Java Inuktitut Project was the original project on the Inuktitut language. After several years, most of the Java code was translated to PHP and we took the opportunity to fix a number of bugs, which still remain in the Java version. The Morphological Analyzer and its dependencies were not translated to PHP.

This document describes the contents of the Java Inuktitut Project most important directories:

-  •dataCSV
-  •dataCompiled
-  •data
-  •morph
-  •phonology
-  •script
-  •applications
-  •documents
-  •lib
-  •fonts
-  •html
-  •unitTests

    1. **_dataCSV_**

2. Linguistic data files in CSV format. See the document entitled “ILDB-documentation.pdf” in the PHP Inuktitut Project for details. Those files are used by some applications like the linguistic database web application which presents information about the morphemes in the linguistic database, and the morphological analyzer. The rewriting of the whole project in PHP from Java has brought a number of slight modifications to those files compared to the files used in the original Java programs.
    1. **_dataCompiled_**

5. This directory contains Java files that correspond to the raw CSV data compiled into Java code.
    1. **_data_**

This is pretty much equivalent to the directory linguisticobjects in the PHP Inuktitut Project. The directory contains classes of objects representing the morphemes and all the morphological analyzer needs:

-  •**Morpheme**.java

General class for all morphemes (roots, words, suffixes, and grammatical endings). 

-  •**Base**.java (class Base extends Morpheme)

General class for roots (noun and verb roots), and for pronouns

-  •**Pronoun**.java (class Pronoun extends Base)

Class of pronouns

-  •**Demonstrative**.java (class Demonstrative extends Base)

Class of demonstrative adverbs and pronouns

-  •**Affix**.java (class Affix extends Morpheme)

General class for suffixes and grammatical endings

-  •**Suffix**.java (class Suffix extends Affix)

Class of suffixes

-  •**NounEnding**.java (class NounEnding extends Affix)

Class of noun grammatical endings

-  •**VerbEnding**.java (class VerbEnding extends Affix)

Class of verb grammatical endings

-  •**DemonstrativeEnding**.java (class DemonstrativeEnding extends Affix)

Class of demonstrative grammatical endings

-  •**Words**.java

-  •**VerbWord**.java

Class for the past participles (see under “lang” below)

-  •**LinguisticDataAbstract**.java

Abstract class for accessing the linguistic database; for example, getting the lists of roots and suffixes; getting an object identified by its unique id; etc. For the time being, the database contain only real roots as objects of class Base (roots); other data, such as composite noun stems, should eventually be added to the “roots”. 

-  •**Lexicon**.java

Related to the lexicon, used for lexical retrieval.

Actions (morphophonological) of the morphemes on the stem:

-  •**Action**.java

Contains all classes of actions. (cf. Inuktitut_Project-documentation.rtf of PHP Inuktitut Project).

Some other files are used maintly by the web applications: Examples.java, Gi_verb.java, …

1. **_data/exec_**

This directory contains:

**CompileLinguisticData**.java

This is an executable used to compile the linguistic data of the directory dataCSV into Java code.

**DumpMorphemes**.java

This is an executable used to print the meaning of every root and suffix onto the standard output.

**DumpDescriptionsOfSuffixes**.java

This is an executable used to print the linguistic data of every suffix onto the standard output.

**DumpSurfaceForms**.java

This is an executable used to print all the surface forms of all the affixes onto the standard output.

**InfoMorpheme**.java

This is an executable used to print all the information about a morpheme onto the standard output.

1. **_data/constraints_**

Code that reprensents the conditions and constraints of morphemes about what may be added to a given stem.

1. **_morph_**

This directory contains code related to the morphological analysis of inuktitut words.

-  •**MorphInuk**.java

Morphological analysis.

-  •**Graph**.java

State graph followed by the morphological analysis process.

-  •**Decomposition**.java

Representation of the decomposition of a word.

-  •**PartOfComposition**.java, **RootPartOfComposition**.java, **AffixPartOfComposition**.java

Representation of an constituant element of a word decomposition.

2. **_phonology_**

-  •**Dialect**.java

Phonological rules for all dialects of Inuktitut, used by the morphological analyzer.

2. **_script_**

-  •**Orthography**.java

This file contains a number of functions related to orthography and mostly needed by the morphological analyzer.

-  •**Roman**.java

This file contains stuff related to the writing of Inukitut with the Roman alphabet. This is an in-progress rewriting of the file from Java to PHP. Some function are no longer necessary. For example, the Java function “transcodeToUnicode” doesn't really need to be rewritten in this file; the file “Syllabics.php” contains the equivalent.

-  •**Syllabics**.java

This file contains all that is needed to process the syllabics.

This directory also contains code related to the Inuktitut Transcoder web application.

1. **_applications_**

This directory contains a number of executable, some of which are used by web applications. 

-  •**DecomposeHansard+++Test**.java 
    - This series constitutes a set of tests used to determine whether the morphological analyzer behaves as expected. Those tests compare the output of the tests with a 'gold standard' consisting in the analyses of about a thousand inuktitut words. Those words are the most frequent words in the Nunavut Hansard for the years 1999-2003. The tests write their output in the directory _outputFiles. _The gold standard is in the directory _ressources_.

-  •**Decompose**.java, **DecompositionOfWord**.java

Executable to decompose an Inuktitut word (morphological analysis)

-  •**DescriptionOfItem**.java, D**escriptionOfRoot**.java, **DescriptionOfSuffix**.java

These executable output a description of the database information on items, roots and suffixes.

-  •**Translit+++**.java

Transliteration of web pages and of files from syllabics to roman alphabet, from kalaallisut to inuktitut, etc.

1. **_documents_**

Those Java files are used to extract the contents of HTML, PDF, and DOC files. The directory 'pdffoxAddition' contains additions to the package PDFBOX needed to tell the font of the text elements in a PDF file. This was missing in the original PDFBOX package.

1. **_fonts_**

This directory contains files related to Inuktitut legacy fonts: AiPaiNunavik, Nunacom, and ProSyl, and other fonts: AiNunavik, AiPaiNuna, Aujaq, EMI-Inuktitut,Naamajut, OldSyl, Tariarmiut, Tunngavik.

1. **_unitTests_**

This directory contains unit tests on several functions.

1. **_lib_**

This directory contains a number of jar files used by several applications.

1. **_html_**

This directory contains a nmber of files related to the processing of HTML.

1. **_utilities, utilities1_**

Theses directories contain utility functions.
