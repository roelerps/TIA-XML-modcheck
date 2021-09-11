package factory;

import storageModel.PlcProgramReader;
import storageModel.XmlReader;

/**
 * A factory for creating Storage objects.
 */
public abstract class StorageFactory {

	/**
	 * Instantiates a new storage factory.
	 */
	private StorageFactory() {

	}

	/**
	 * Creates a new PLC block reader object.
	 *
	 * @return the PLC program reader (for XML)
	 */
	public static PlcProgramReader createPlcBlockReader() {
		return new XmlReader();
	}
}
