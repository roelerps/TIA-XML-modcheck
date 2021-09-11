package factory;

import plcBlockModel.ArrayDB;
import plcBlockModel.Function;
import plcBlockModel.FunctionBlock;
import plcBlockModel.GlobalDB;
import plcBlockModel.InstanceDB;
import plcBlockModel.Interface;
import plcBlockModel.OrganizationBlock;
import plcBlockModel.PlcBlock;

/**
 * A factory for creating PlcBlock objects.
 */
public abstract class PlcBlockFactory {

	/**
	 * Instantiates a new plc block factory.
	 */
	private PlcBlockFactory() {

	}

	/**
	 * Creates a new PlcBlock object.
	 *
	 * @param type    the type of PLC block
	 * @param name    the name of the PLC block
	 * @param nr      the number of the PLC block
	 * @param file    the file containing the PLC block
	 * @param lang    the programming language of the PLC block
	 * @param intface the interface of the PLC block
	 * @return the created PLC block
	 */
	public static PlcBlock createPlcBlock(String type, String name, int nr, String file, String lang,
			Interface intface) {
		PlcBlock bl = null;
		switch (type) {
		case ("SW.Blocks.FB"):
			intface.addEnPath();
			bl = new FunctionBlock(name, nr, file, lang, intface);
			break;
		case ("SW.Blocks.FC"):
			intface.addEnPath();
			bl = new Function(name, nr, file, lang, intface);
			break;
		case ("SW.Blocks.OB"):
			bl = new OrganizationBlock(name, nr, file, lang, intface);
			break;
		case ("SW.Blocks.GlobalDB"):
			bl = new GlobalDB(name, nr, file, lang, intface);
			break;
		case ("SW.Blocks.InstanceDB"):
			bl = new InstanceDB(name, nr, file, lang, intface);
			break;
		case ("SW.Blocks.ArrayDB"):
			bl = new ArrayDB(name, nr, file, lang, intface);
			break;
		}
		return bl;
	}

}
