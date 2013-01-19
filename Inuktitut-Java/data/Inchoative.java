package data;

public class Inchoative extends Suffix {
	//
    private static String morph = "#incho#";
    //
    
	//---------------------------------------------------------------------------------------------------------
    public Inchoative() {
        morpheme = morph;
		makeFormsAndActions("V", morpheme, morpheme, "n", "n");
        nb = "1";
	    type = "sv"; 
	    function = "vv";
	    englishMeaning = "to start to; to begin to";
	    frenchMeaning = "commencer à";
	    setAttrs();
    }
    
	//---------------------------------------------------------------------------------------------------------
    static public String getMorph() {
    	return morph;
    }

    //---------------------------------------------------------------------------------------------------------
    void setAttrs() {
    	setAttributes();
    	setId();
    }
    
	//---------------------------------------------------------------------------------------------------------
    public String showData() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Suffix$Inchoative:\n");
        sb.append("morpheme= " + morpheme + "\n");
        sb.append("nb: "+ nb + "\n");
        sb.append("type= " + type + "\n");
        sb.append("function= " + function + "\n");
        sb.append("position= " + position + "\n");
        sb.append("englishMeaning= " + englishMeaning + "\n");
        sb.append("]\n");
        return sb.toString();
    }
    
}

