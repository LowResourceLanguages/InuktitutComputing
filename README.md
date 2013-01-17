# Inuktitut Computing

## The UQAILAUT Project

The work presented in these project is the result of several years of research as a research officer at the Institute of Information Technology of the National Research Council of Canada (NRC). The source code and the applications that you will find hereinafter are copyrighted to NRC and released under the BSD 3-Clause license.
- Benoît Farley
 
----

Mission: To facilitate the use of Inuktitut in its written form on computers and the web by providing useful tools and links to important resources.

![morningpanorama](https://cloud.githubusercontent.com/assets/196199/9024174/0c057278-388f-11e5-978a-eae0d09bb504.jpg)

Morning at Iqaluit, Nunavut 
Photo: Joel Martin


# Components


[Inuktitut Morphological Analyser](#) 
A common problem for a student of Inuktitut is that words grow to gigantic proportions, often counting over five or six infixes, and need to be broken into units of meaning in order to be understood. This may represent a particular challenge to newcomers to the language, more so when one considers the phonological transformations as those infixes are added up.  In order to provide support for students and as a basis for more complex tools such as spelling correctors and better search tools that are taken for granted in other languages, we have developed a tool that splits these long words into morphemes (a morphological analyser).
The focus is on the dialects of Eastern Canadian Inuktitut.  We are still adding to our linguistic database, but the tool already provides an almost complete set of the "roots" and "infixes" of the language, taking full account for their various forms.

This link leads to applications that use the inuktitut morphological analyzer and the data base. There is an application that returns de decomposition of a word highlighted by the user in a web page. There is also applications that return linguistic information on the roots and the infixes contained in the data base.


[Transcoder](#) 
With this application, one can get Inuktitut text rewritten from some original format (Unicode, Nunacom, ProSyl, roman alphabet, etc.) to another format.

[Web Page Transliteration](#) 
An application that produces an exact copy of an Inuktitut web page with the Inuktitut syllabic text transliterated to the roman alphabet.

[Display and Input of Inuktitut Syllabic Characters](#) 
This page gives access to Unicode and Legacy fonts for displaying Inuktitut syllabic characters, and to keyboard drivers that must be used in order to type those characters at the keyboard.


[Inuktitut Linguistics for Technocrats](#)
Mick Mallon has graciously given us permission to put a copy of this document that provides a brief introduction to the phonology and morphophonology of Inuktitut. This document is written in a style that allows qallunaat seeking an understanding of how the language is put together to absorb the basics of word formation, an essential part of the Inuktitut language.

[Alex Spalding's "Inuktitut - A Multi-dialectal Outline Dictionary (with an Aivilingmiutaq base)"](#)


[Inuktitut-English Parallel Corpus](#)
With the gracious co-operation of the Legislative Assembly of Nunavut we have assembled an aligned parallel corpus, where the Inuktitut text and its English translation is put in parallel, at or near the sentence level. We are in the process of producing an updated version of the Hansard Parallel Corpus but, for now, we will ensure that the versions produced in 2003 are available.


[Searching the Nunavut Hansard](#)
As a demonstration of how the Inuktitut-English parallel corpus can be used as the basis of a useful application for a student or speaker of Inuktitut, we provide a means for searching. At the present time you can search on individual words or parts of words in the Inuktitut or English text and will be shown the complete sentence from the Inuktitut text together with the English sentence aligned to it.

# Usage

This project file contains 2 folders: one with the files of the original  [Java](Inuktitut-Java) project, the other with the more recent [PHP](Inuktitut-PHP) project. The PHP project is a rewriting of the original Java code with some fixes. The morphological analyzer, however, has not been ported to PHP; this is a work in progress and the analyzer will eventually be running on the http://inuktitutcomputing.ca/ site.


The source code and executables of the software developed at the National Research Council of Canada are provided here to any interested person, organization or company, given that the terms of the BSD 3-Clause license are met. Those terms are reproduced at the bottom of this page.

# Rights and License

© National Research Council of Canada 2012

Source code distributed under the BSD 3-Clause license

BSD 3-Clause License

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
Neither the name of the National Research Council of Canada nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The contents of this site was developed by Benoît Farley at the National Research Council of Canada.
