package plcBlockModel;

/**
 * The Class FunctionBlock.
 */
public class FunctionBlock extends CodeBlock {

	/**
	 * Instantiates a new function block.
	 *
	 * @param name    the name of the function block
	 * @param nr      the number of the function block
	 * @param file    the XML file of the function block
	 * @param lang    the programming language of the function block
	 * @param intface the interface of the function block
	 */
	public FunctionBlock(String name, int nr, String file, String lang, Interface intface) {
		super(name, nr, file, lang, intface);

	}

	/**
	 * Function block to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String s = "";
		s = "FunctionBlock: FB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + "\r\n";
		s = s + "XMLFileLocation: " + getXmlFile();
		s = s + "\r\n";
		s = s + "ProgrammingLanguage: " + getProgLang();
		return s;
	}

	/**
	 * Function block to string short.
	 *
	 * @return the string
	 */
	public String toStringShort() {
		String s = "";
		s = "FunctionBlock: FB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + " (" + getProgLang() + ")";
		return s;
	}
}
