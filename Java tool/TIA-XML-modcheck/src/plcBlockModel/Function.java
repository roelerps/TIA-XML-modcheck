package plcBlockModel;

/**
 * The Class Function.
 */
public class Function extends CodeBlock {

	/**
	 * Instantiates a new function.
	 *
	 * @param name    the name of the function
	 * @param nr      the number of the function
	 * @param file    the XML file of the function
	 * @param lang    the programming language of the function
	 * @param intface the interface of the function
	 */
	public Function(String name, int nr, String file, String lang, Interface intface) {
		super(name, nr, file, lang, intface);

	}

	/**
	 * Function to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String s = "";
		s = "Function: FC" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + "\r\n";
		s = s + "XMLFileLocation: " + getXmlFile();
		s = s + "\r\n";
		s = s + "ProgrammingLanguage: " + getProgLang();
		return s;
	}

	/**
	 * Function to string short.
	 *
	 * @return the string
	 */
	public String toStringShort() {
		String s = "";
		s = "Function: FC" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + " (" + getProgLang() + ")";
		return s;
	}
}
