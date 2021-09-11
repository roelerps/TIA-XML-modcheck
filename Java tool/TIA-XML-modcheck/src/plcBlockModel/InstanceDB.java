package plcBlockModel;

/**
 * The Class InstanceDB.
 */
public class InstanceDB extends DataBlock {

	/**
	 * Instantiates a new instance DB.
	 *
	 * @param name    the name of the instance db
	 * @param nr      the number of the instance db
	 * @param file    the XML file of the instance db
	 * @param lang    the programming language of the instance db
	 * @param intface the interface of the instance db
	 */
	public InstanceDB(String name, int nr, String file, String lang, plcBlockModel.Interface intface) {
		super(name, nr, file, lang, intface);

	}

	/**
	 * Instance db to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String s = "";
		s = "InstanceDB: iDB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + "\r\n";
		s = s + "XMLFileLocation: " + getXmlFile();
		return s;
	}

	/**
	 * Instance db to string short.
	 *
	 * @return the string
	 */
	public String toStringShort() {
		String s = "";
		s = "InstanceDB: iDB" + getNumber();
		s = s + " (" + getName() + ")";
		return s;
	}
}
