TCIHTML est un outil Java pour la conversion � Unicode de fichiers HTML Inuktitut 
utilisant des polices archa�ques comme Nunacom, ProSyl, AiPaiNunavik, etc.
(TCI est l'abr�viation de "TransCodageInuktitut")

INSTALLATION :

TCIHTML est livr� dans un fichier ZIP. Il suffit de le d�zipper dans un dossier.

EX�CUTION :

Sur DOS :

tcihtml.bat "<url>" <conversion> <aipaitai> <police Unicode>*

<url> : l'url complet de la page HTML ; doit �tre entour� de doubles guillemets
<conversion> :	la lettre 'l' indique une conversion du syllabique � l'alphabet latin
				la lettre 'u' indique une conversion du syllabique � unicode
<aipaitai> :	le chiffre '0' indique que les caract�res aipaitai ne seront pas utilis�s (a+i)
			le chiffre '1' indique que les caract�res aipaitai seront utilis�s
<police Unicode> :	nom de la police Unicode Inuktitut qui sera utilis�e
					argument optionnel; valeur par d�faut: pigiarniq
					
DISCUSSION :

TCIHTML convertit le texte inuktitut syllabique d'un document HTML cod� avec une police 
archa�que en syllabique Unicode. Les polices archa�ques suivantes sont support�es :
AiNunavik, AiPaiNuna, AiPaiNunavik, Aujaq, Aujaq2, AujaqSyl, EmiInuktitut, Naamajut, Nunacom,
OldSyl, ProSyl, Tariumiut, Tunngavik.

Tous les �l�ments de texte reconnus �tre pr�sent�s avec une police inuktitut archa�que sont
convertis en Unicode et pr�c�d�s d'un �l�ment SPAN. Par exemple :

<SPAN style="font-family:AiPaiNutaaq">le texte converti en Unicode</SPAN>



