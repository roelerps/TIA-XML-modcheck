package factory;

import java.util.List;

import plcBlockModel.PlcBlock;
import plcProgramModel.CallStructure;
import plcProgramModel.PlcProgram;
import plcProgramModel.Variable;

/**
 * A factory for creating PlcProgram objects.
 */
public abstract class PlcProgramFactory {

	/**
	 * Instantiates a new plc program factory.
	 */
	private PlcProgramFactory() {

	}

	/**
	 * Adds the variables of the PLC program to the PLC program.
	 *
	 * @param program the PLC program
	 * @param vars    the variables of the PLC program
	 * @return the PLC program
	 */
	public static PlcProgram addVariables(PlcProgram program, List<Variable> vars) {
		program.setVariables(vars);
		return program;
	}

	/**
	 * Adds the call structure to the PLC program.
	 *
	 * @param program the PLC program
	 * @param cs      the call structure to be added to the PLC program
	 * @return the PLC program
	 */
	public static PlcProgram addCallstructure(PlcProgram program, CallStructure cs) {
		program.setCallStructure(cs);
		return program;
	}

	/**
	 * Creates a new PlcProgram object.
	 *
	 * @param projName the project name of the PLC program
	 * @param blocks   the blocks of the PLC program
	 * @return the PLC program
	 */
	public static PlcProgram createPlcProgram(String projName, List<PlcBlock> blocks) {
		return new PlcProgram(projName, blocks);
	}

}
