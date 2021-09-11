package plcBlockModel;

import java.util.Comparator;

/**
 * The Class PlcBlock.
 */
public abstract class PlcBlock {

	/** Name of the PLC block. */
	private String Name;

	/** Number of the PLC block. */
	private int Number;

	/** XML file of the PLC block. */
	private String XmlFile;

	/** Programming language of the PLC block. */
	private String ProgLang;

	/** Interface of the PLC block. */
	private Interface Interface;

	/**
	 * Instantiates a new PLC block.
	 *
	 * @param name    the name of the PLC block
	 * @param nr      the number of the PLC block
	 * @param file    the XML file of the PLC block
	 * @param lang    the programming language of the PLC block
	 * @param intface the interface of the PLC block
	 */
	public PlcBlock(String name, int nr, String file, String lang, Interface intface) {
		this.Name = name;
		this.Number = nr;
		this.XmlFile = file;
		this.ProgLang = lang;
		this.Interface = intface;
	}

	/**
	 * Gets the XML file of the PLC block.
	 *
	 * @return the XML file of the PLC block
	 */
	public String getXmlFile() {
		return XmlFile;
	}

	/**
	 * Gets the name of the PLC block.
	 *
	 * @return the name of the PLC block
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Gets the number of the PLC block.
	 *
	 * @return the number of the PLC block
	 */
	public int getNumber() {
		return Number;
	}

	/**
	 * Gets the programming language of the PLC block.
	 *
	 * @return the programming language of the PLC block
	 */
	public String getProgLang() {
		return ProgLang;
	}

	/**
	 * Gets the interface of the PLC block.
	 *
	 * @return the interface of the PLC block
	 */
	public Interface getInterface() {
		return Interface;
	}

	/** The Block number comparator. */
	public static Comparator<PlcBlock> BlockNrComp = new Comparator<PlcBlock>() {

		public int compare(PlcBlock b1, PlcBlock b2) {
			int blocknr1 = b1.getNumber();
			int blocknr2 = b2.getNumber();
			return blocknr1 - blocknr2;
		}
	};

	/**
	 * PLC block to string.
	 *
	 * @return the string
	 */
	public abstract String toString();

	/**
	 * PLC block to string short.
	 *
	 * @return the string
	 */
	public abstract String toStringShort();
}
