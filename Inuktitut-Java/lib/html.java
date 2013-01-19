/*
 * Conseil national de recherche Canada 2005/
 * National Research Council Canada 2005
 * 
 * Cr�� le / Created on Aug 22, 2005
 * par / by Benoit Farley
 * 
 */
package lib;

import java.util.Vector;

public class html {
    
    public static String anchorHref = 
        "<a href=";
    public static String callDescriptionOfRoot =
        "\"javascript:appelerDescriptionRacine('";
    public static String callDescriptionOfSuffix =
            "\"javascript:appelerDescriptionSuffixe('";
    public static String ahrefCallDescriptionOfRoot = 
        "<a href=\"javascript:appelerDescriptionRacine('";
    public static String ahrefCallDescriptionOfSuffix =
        "<a href=\"javascript:appelerDescriptionSuffixe('";
    public static String ahrefCallDecompositionOfWord =
        "<a href=\"javascript:appelerDefinitionMot('";
    public static String titleOfCall = 
        "')\" title=\"";
    public static String endOfCall =
        "')\">";
    public static String endOfTitleOfCall =
        "\">";
    public static String endOfRef =
        "</a>";
    public static String space = "&nbsp;";
    
//    public static String scriptDescRacCGI(String langue) {
//        StringBuffer x = new StringBuffer();
//        x.append("<script language=\"javascript\"><!-- \n");
//        x.append("function appelerDescriptionRacine(racine) {\n");
//        x
//                .append("window3=open('','','scrollbars=yes,dependent=yes,resizable=yes,location=no,menubar=no,status=no,toolbar=no,titlebar=no,width=500,height=500');\n");
//        x
//                .append("window3.location.href='../Inuktitut/DataBase/requetes.jsp?c=DefinitionDeRacine");
//        x.append("&l=" + langue);
//        x.append("&caller=jsp");
//        x.append("&m='+encodeURIComponent(racine)");
//        x.append(";\nwindow3.focus();\n}\n");
//        x.append("--></script>");
//        x.append("<noscript><p>Pas de script permis</p></noscript>");
//        return x.toString();
//    }
    
    public static String scriptDescRootJSP(String lang, String font) {
        StringBuffer x = new StringBuffer();
        x.append("<script language=\"javascript\"><!-- \n");
        x.append("function appelerDescriptionRacine(racine) {\n");
        x
                .append("window3=open('','','scrollbars=yes,dependent=yes,resizable=yes,location=no,menubar=no,status=no,toolbar=no,titlebar=no,width=500,height=950');\n");
        x
                .append("window3.location.href='requetes.jsp?c=DefinitionDeRacine");
        x.append("&l=" + lang);
        if (font != null) x.append("&p="+font);
        x.append("&m='+encodeURIComponent(racine)");
        x.append(";\nwindow3.focus();\n}\n");
        x.append("--></script>");
        x.append("<noscript><p>Pas de script permis</p></noscript>");
        return x.toString();
    }
    
//    public static String scriptDescSufCGI(String langue) {
//        StringBuffer x = new StringBuffer();
//        x.append("<script language=\"javascript\"><!-- \n");
//        x.append("function appelerDescriptionSuffixe(suffix,no,type) {\n");
//        x
//                .append("window3=open('','','scrollbars=yes,dependent=yes,resizable=yes,location=no,menubar=no,status=no,toolbar=no,titlebar=no,width=500,height=500');\n");
//        x
//                .append("window3.location.href='../Inuktitut/DataBase/requetes.jsp?c=DefinitionSuffixe");
//        x.append("&l=" + langue);
//        x.append("&caller=jsp");
//        x.append("&m='+encodeURIComponent(suffix)");
//        x.append(";\nwindow3.focus();\n}\n");
//        x.append("--></script>");
//        x.append("<noscript><p>Pas de script permis</p></noscript>");
//        return x.toString();
//    }
    
    public static String scriptDescSufJSP(String lang, String font) {
        StringBuffer x = new StringBuffer();
        x.append("<script language=\"javascript\"><!-- \n");
        x.append("function appelerDescriptionSuffixe(suffix,no,type) {\n");
        x
                .append("window3=open('','','scrollbars=yes,dependent=yes,resizable=yes,location=no,menubar=no,status=no,toolbar=no,titlebar=no,width=500,height=750');\n");
        x
                .append("window3.location.href='requetes.jsp?c=DefinitionDeSuffixe");
        x.append("&l=" + lang);
        if (font != null) x.append("&p="+font);
        x.append("&m='+encodeURIComponent(suffix)");
        x.append(";\nwindow3.focus();\n}\n");
        x.append("--></script>");
        x.append("<noscript><p>Pas de script permis</p></noscript>");
        return x.toString();
    }
    
//    public static String scriptDefMotCGI(String langue) {
//        StringBuffer x = new StringBuffer();
//        x.append("<script language=\"javascript\"><!-- \n");
//        x.append("function appelerDefinitionMot(mot) {\n");
//        x
//                .append("window4=open('','','scrollbars=yes,dependent=yes,resizable=yes,location=no,menubar=no,status=no,toolbar=no,titlebar=no,width=300,height=500');\n");
//        x
//                .append("window4.location.href='../Inuktitut/DataBase/requetes.jsp?c=DefinitionMot");
//        x.append("&l=" + langue);
//        x.append("&caller=jsp");
//        x.append("&m='+encodeURIComponent(mot)");
//        x.append(";\nwindow4.focus();\n}\n");
//        x.append("--></script>");
//        x.append("<noscript><p>Pas de script permis</p></noscript>");
//        return x.toString();
//    }
    
    public static String scriptDecWordJSP(String lang, String font) {
        StringBuffer x = new StringBuffer();
        x.append("<script language=\"javascript\"><!-- \n");
        x.append("function appelerDefinitionMot(mot) {\n");
        x
                .append("window4=open('','','scrollbars=yes,dependent=yes,resizable=yes,location=no,menubar=no,status=no,toolbar=no,titlebar=no,width=500,height=750');\n");
        x
                .append("window4.location.href='requetes.jsp?c=DefinitionDeMot");
        x.append("&l=" + lang);
        if (font != null) x.append("&p="+font);
        x.append("&m='+encodeURIComponent(mot)");
        x.append(";\nwindow4.focus();\n}\n");
        x.append("--></script>");
        x.append("<noscript><p>Pas de script permis</p></noscript>");
        return x.toString();
    }
    
    public static String alphabeticalList(char alpha[], String morphemeType) {
        StringBuffer x = new StringBuffer();
        for (int i=0; i<alpha.length; i++) {
            x.append("<a href=\"#");
            x.append(morphemeType+"_"+alpha[i]);
            x.append("\">");
            x.append(alpha[i]);
            x.append("</a> &nbsp;");
        }
        x.append("<p>");
        return x.toString();
    }
    
    
}
