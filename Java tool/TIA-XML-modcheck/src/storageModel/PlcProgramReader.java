package storageModel;

import java.io.IOException;

import plcProgramModel.PlcProgram;

/**
 * The Interface PlcProgramReader.
 */
public interface PlcProgramReader {

	/**
	 * Load the PLC program.
	 *
	 * @param foldername the foldername of the PLC program XML files
	 * @return the PLC program
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public abstract PlcProgram loadProgram(String foldername) throws IOException;

}
