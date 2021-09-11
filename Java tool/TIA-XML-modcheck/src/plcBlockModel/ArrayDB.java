package plcBlockModel;

/**
 * The Class ArrayDB.
 */
public class ArrayDB extends DataBlock {

	/**
	 * Instantiates a new array DB.
	 *
	 * @param name    the name of the datablock
	 * @param nr      the number of the datablock
	 * @param file    the XML file of the datablock
	 * @param lang    the programming language of the datablock
	 * @param intface the interface of the datablock
	 */
	public ArrayDB(String name, int nr, String file, String lang, plcBlockModel.Interface intface) {
		super(name, nr, file, lang, intface);

	}

	/**
	 * Datablock to string
	 *
	 * @return the string with number, name and XML file
	 */
	public String toString() {
		String s = "";
		s = "ArrayDB: DB" + getNumber();
		s = s + " (" + getName() + ")";
		s = s + "\r\n";
		s = s + "XMLFileLocation: " + getXmlFile();
		return s;
	}

	/**
	 * Datablock to string short.
	 *
	 * @return the string with number and name
	 */
	public String toStringShort() {
		String s = "";
		s = "ArrayDB: DB" + getNumber();
		s = s + " (" + getName() + ")";
		return s;
	}
}
