package plcBlockModel;

/**
 * The Class DataBlock.
 */
public abstract class DataBlock extends PlcBlock {

	/**
	 * Instantiates a new data block.
	 *
	 * @param name    the name of the data block
	 * @param nr      the number of the data block
	 * @param file    the XML file of the data block
	 * @param lang    the programming language of the data block
	 * @param intface the interface of the data block
	 */
	public DataBlock(String name, int nr, String file, String lang, plcBlockModel.Interface intface) {
		super(name, nr, file, lang, intface);

	}

	/**
	 * Data block to string.
	 *
	 * @return the string
	 */
	public abstract String toString();

	/**
	 * Data block to string short.
	 *
	 * @return the string
	 */
	public abstract String toStringShort();

}
