package plcBlockModel;

/**
 * The Class GlobalDB.
 */
public class GlobalDB extends DataBlock {

	/**
	 * Instantiates a new global DB.
	 *
	 * @param name    the name of the global db
	 * @param nr      the number of the global db
	 * @param file    the XML file of the global db
	 * @param lang    the programming language of the global db
	 * @param intface the interface of the global db
	 */
	public GlobalDB(String name, int nr, String file, String lang, plcBlockModel.Interface intface) {
		super(name, nr, file, lang, intface);
	}

	/**
	 * Global db to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String s = "";
		s = "GlobalDB: DB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + "\r\n";
		s = s + "XMLFileLocation: " + getXmlFile();
		return s;
	}

	/**
	 * Global db to string short.
	 *
	 * @return the string
	 */
	public String toStringShort() {
		String s = "";
		s = "GlobalDB: DB" + getNumber();
		s = s + " (" + getName() + ")";
		return s;
	}
}
