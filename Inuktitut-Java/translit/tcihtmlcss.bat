@echo off
java -classpath .;tcihtml.jar;transliteration.jar;log4j-1.2.9.jar;cobra_with_extensions.jar;js.jar;commons-lang-2.1.jar;Tidy.jar translit.TCIHTMLcss "tcihtmlcss.bat" %1 %2 %3