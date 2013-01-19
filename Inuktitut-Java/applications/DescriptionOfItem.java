//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
// -----------------------------------------------------------------------
//           (c) Conseil national de recherches Canada, 2008
//           (c) National Research Council of Canada, 2008
// -----------------------------------------------------------------------

// -----------------------------------------------------------------------
// Document/File:		DefinitionDeItem.java
//
// Type/File type:		code Java / Java code
// 
// Auteur/Author:		Benoit Farley
//
// Organisation/Organization:	Conseil national de recherches du Canada/
//				National Research Council Canada
//
// Date de création/Date of creation:	
//
// Description: La méthode statique 'getDef' retourne dans un format HTML
//              l'information sur un item.
//
// -----------------------------------------------------------------------


package applications;


import data.LinguisticDataAbstract;


public class DescriptionOfItem {

	public static void main(String [] args) {
    	LinguisticDataAbstract.init();
    	if (args.length==0) {
    		System.out.println("args: item");
    		System.exit(1);
    	}
        data.Items.getDef(args,System.out);
    }
    
}
    
