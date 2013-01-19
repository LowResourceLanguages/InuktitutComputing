package documents.exec;

import documents.NRC_HTMLDocumentByCobra;

public class getFonts {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileUrlName = "http://localsites/"+"CCO/www.choeurclassiqueoutaouais.ca/cco/contact.htm";
		System.out.println("fileUrlName= '"+fileUrlName+"'");
		try {
			NRC_HTMLDocumentByCobra doc = new NRC_HTMLDocumentByCobra(fileUrlName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
