package plcBlockModel;

/**
 * The Class OrganizationBlock.
 */
public class OrganizationBlock extends CodeBlock {

	/**
	 * Instantiates a new organization block.
	 *
	 * @param name    the name of the organization block
	 * @param nr      the number of the organization block
	 * @param file    the XML file of the organization block
	 * @param lang    the programming language of the organization block
	 * @param intface the interface of the organization block
	 */
	public OrganizationBlock(String name, int nr, String file, String lang, Interface intface) {
		super(name, nr, file, lang, intface);

	}

	/**
	 * Organization block to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String s = "";
		s = "OrganizationBlock: OB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + "\r\n";
		s = s + "XMLFileLocation: " + getXmlFile();
		s = s + "\r\n";
		s = s + "ProgrammingLanguage: " + getProgLang();
		return s;
	}

	/**
	 * Organization block to string short.
	 *
	 * @return the string
	 */
	public String toStringShort() {
		String s = "";
		s = "OrganizationBlock: OB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + " (" + getProgLang() + ")";
		return s;
	}
}
