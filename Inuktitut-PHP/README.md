1. **Inuktitut Project**
    1. **_Structure and description of the files_**

This document describes the contents of the Inuktitut Project directory. This directory contains the following subdirectories:

-  •linguisticdata
-  •linguisticobjects
-  •bin
-  •script
-  •lang
-  •lib
-  •fonts
-  •tools
-  •UnitTests

IMPORTANT: Make sure to set the environment variable PHP_INUKTITUT to this directory.

1. **_linguisticdata_**

Linguistic data files. See the document entitled “ILDB-documentation.pdf” for details. Those files are used by some applications like the linguistic database web application which presents information about the morphemes in the linguistic database, and the morphological analyzer. The rewriting of the whole project in PHP from Java has brought a number of slight modifications to those files compared to the files used in the original Java programs.

1. **_linguisticobjects_**

PHP files. All those files have been rewritten in PHP based on the original Java files. The current state reflects what was needed to run under PHP the Inuktitut Database web application which presents the database linguistic information. Some classes may be incomplete with regards to other applications, namely the morphological analyzer which has not been rewritten in PHP yet.

Classes of objects representing the morphemes:

-  •**Morpheme**.php

General class for all morphemes (roots, words, suffixes, and grammatical endings). This class is not complete; it has to be completed by rewriting in PHP stuff from the file Morpheme_from_Java.php.

-  •**Root**.php (class Root extends Morpheme)

General class for roots (noun and verb roots), and for pronouns

-  •**NounRoot**.php (class NounRoot extends Root)

Class of noun roots

-  •**VerbRoot**.php (class VerbRoot extends Root)

Class of verb roots

-  •**Pronoun**.php (class Pronoun extends Root)

Class of pronouns

-  •**Affix**.php (class Affix extends Morpheme)

General class for suffixes and grammatical endings

-  •**Suffix**.php (class Suffixe extends Affix)

Class of suffixes

-  •**NounEnding**.php (class NounEnding extends Affix)

Class of noun grammatical endings

-  •**VerbEnding**.php (class VerbEnding extends Affix)

Class of verb grammatical endings

-  •**Word**.php

-  •**VerbWord**.php

Class for the past participles (see under “lang” below)

-  •**LinguisticObjects**.php

Abstract class for accessing the linguistic database; for example, getting the lists of roots and suffixes; getting an object identified by its unique id; etc. For the time being, the database contain only real roots as objects of class Root; other data, such as composite noun stems, should eventually be added to the “roots”. 

-  •**MeaningOfMorpheme**.php

-  •**_Morpheme_from_Java_**.php

This file is an in-progress rewriting in PHP of the original Java file Morpheme.java.

-  •**Source**.php

Class of objects to represent the sources (book, etc.) where we found information about Inuktitut suffixes, roots, etc.

-  •**Analyzer_MeaningOfMorpheme_Statement**.php

This is a parser for the meaning statements of verb roots. This file is used to analyze the statements for the verb roots found in the linguistic data files in the fields “engMean” and “freMEan” (English meaning and French meaning). An attempt was made for those statements to use a grammar that allows for a single statement that can be used to “generate” the appropriate surface statement depending on the transitivity or intransitivity of the verb,  in the indicative, reflexive, and passive modes.

-  •**CSV_DB_Reader**.php

This is used to read the data from the CSV data files.

Actions (morphophonological) of the morphemes on the stem:

-  •**Action**.php

General class for all actions.

-  •**VowelLengtheningAction**.php

Action of lengthening a vowel.

-  •**AssimilationAction**.php

Action of assimilating the preceding consonant. (In fact, this action should not be. When the project was started and the linguistic database was initiated, an action of assimilation was created based on currently available documents and current knowledge of the language by the author. This action of assimilation was actually confused with the dialectal phenomenon of consonant assimilation.)

-  •**SelfDecapitationAction**.php

Action of self-decapitation of the initial vowel of a suffix.

-  •**UnknownAction.**php

Action unknown. Used when we did not know what the action was for a given morpheme.

-  •**InsertionAction**.php

Action of inserting a vowel, a consonant, or a syllable in front of a suffix.

-  •**ActionFactory**.php

This is an Action factory. It creates an object of the appropriate class.

-  •**NasalizationAction**.php

Action of nasalizing the consonant at the end of the stem.

-  •**NeutralAction**.php

Neutral action. (No action)

-  •**VoicingAction**.php

Action of voicing the consonant at the end of the stem.

-  •**DeletionAction**.php

Action of deleting the consonant at the end of the stem.

-  •**DeletionAndInsertionAction**.php

Action of deleting the consonan at the end of the stem and then inserting a vowel or a syllable.

-  •**SpecificDeletionAction**.php

Action of specifically deleting something at the end of the stem.

-  •**Behaviour**.php

New class representing the behaviour of a suffix in a certain context in terms of actions.

1. **_script_**

-  •**Orthography**.php

This file contains a number of functions related to orthography and mostly needed by the morphological analyzer.

-  •**Roman**.php

This file contains stuff related to the writing of Inukitut with the Roman alphabet. This is an in-progress rewriting of the file from Java to PHP. Some function are no longer necessary. For example, the Java function “transcodeToUnicode” doesn't really need to be rewritten in this file; the file “Syllabics.php” contains the equivalent.

-  •**Syllabics**.php

This file contains all that is needed to process the syllabics.

1. **_lang_**

This directory contains 2 files used to compile, in French and in English, the past participle form of a verb. The past participle is needed in the production of the surface statement of a verb meaning.

-  •**LinguaFRParticiple**.php
-  •**LinguaENParticiple**.php

1. **_fonts_**

This directory contains files related to Inuktitut legacy fonts. So far, 3 files have been rewritten from Java to PHP, for the fonts AiPaiNunavik, Nunacom, and ProSyl. In the original Java version, other fonts were coded: AiNunavik, AiPaiNuna, Aujaq, EMI-Inuktitut,Naamajut, OldSyl, Tariarmiut, Tunngavik. See in the Java directory for those files.

1. **_UnitTests_**

This directory contains unit tests on most of the functions in the PHP version and related files. Look at the file _Unit_tests-documentation_ for detailed information.

1. **_lib_**

This is a library directory. It contains a batch of utility code used by other methods and functions.

1. **_bin_**

This directory contains a number of independent PHP scripts and a PHP initialization file:

-  •**makeIndex**.php

Script for creating the index files for the linguistic data files.

-  •**searchIndex**.php

Script for searching a specific morpheme in the linguistic data base. Displays an array of attribute-value pairs.

-  •**transcode**.php

Script for transcoding or transliterating Inuktitut text.

-  •**getAllTransitiveVerbMeanings**.php

Script for returning the English meaning of all transitive verb roots in the linguistic database.

-  •php.ini

Initialization file for PHP. Among other things, it specifies the location of the log4php package.

-  •english_transitive_verb_meanings.txt

Output of the script “getAllTransitiveVerbMeanings.php”.

-  •Inuktitut-PHP.log

log4php log file

To run those scripts from a DOS console, one needs to run “php” from this directory with the initialization file _php.ini_ : **_php -c . php_file_**

The -c . option tells php to use the initialization file _php.ini_ in the current directory.

1. **_tools_**

This directory contains subdirectories related to different “tools” or applications, such as:

-  •**NunHanSearch**, a tool for searching through the Nunavut Hansard 
-  •**Transcoder**, a tool for transcoding and transliterating Inuktitut text
-  •**MorphologicalAnalyzer**, a tool for decomposing Inuktitut words into their morphemes
