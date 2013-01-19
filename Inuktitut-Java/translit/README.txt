TCIHTML est un outil Java pour la conversion à Unicode de fichiers HTML Inuktitut 
utilisant des polices archaïques comme Nunacom, ProSyl, AiPaiNunavik, etc.
(TCI est l'abréviation de "TransCodageInuktitut")

INSTALLATION :

TCIHTML est livré dans un fichier ZIP. Il suffit de le dézipper dans un dossier.

EXÉCUTION :

Sur DOS :

tcihtml.bat "<url>" <conversion> <aipaitai> <police Unicode>*

<url> : l'url complet de la page HTML ; doit être entouré de doubles guillemets
<conversion> :	la lettre 'l' indique une conversion du syllabique à l'alphabet latin
				la lettre 'u' indique une conversion du syllabique à unicode
<aipaitai> :	le chiffre '0' indique que les caractères aipaitai ne seront pas utilisés (a+i)
			le chiffre '1' indique que les caractères aipaitai seront utilisés
<police Unicode> :	nom de la police Unicode Inuktitut qui sera utilisée
					argument optionnel; valeur par défaut: pigiarniq
					
DISCUSSION :

TCIHTML convertit le texte inuktitut syllabique d'un document HTML codé avec une police 
archaïque en syllabique Unicode. Les polices archaïques suivantes sont supportées :
AiNunavik, AiPaiNuna, AiPaiNunavik, Aujaq, Aujaq2, AujaqSyl, EmiInuktitut, Naamajut, Nunacom,
OldSyl, ProSyl, Tariumiut, Tunngavik.

Tous les éléments de texte reconnus être présentés avec une police inuktitut archaïque sont
convertis en Unicode et précédés d'un élément SPAN. Par exemple :

<SPAN style="font-family:AiPaiNutaaq">le texte converti en Unicode</SPAN>



