package plcProgramModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import plcBlockModel.ArrayDB;
import plcBlockModel.Function;
import plcBlockModel.FunctionBlock;
import plcBlockModel.GlobalDB;
import plcBlockModel.InstanceDB;
import plcBlockModel.OrganizationBlock;
import plcBlockModel.PlcBlock;

/**
 * The Class PlcProgram.
 */
public class PlcProgram {

	/** The Project name of the PLC program. */
	private String ProjectName;

	/** The Blocks of the PLC program. */
	private List<PlcBlock> Blocks;

	/** The Call structure of the PLC program. */
	private CallStructure CallStructure;

	/** The Variables of the PLC program. */
	private List<Variable> Variables;

	/**
	 * Instantiates a new plc program.
	 *
	 * @param projname the projectname of the PLC program
	 * @param blocks   the blocks of the PLC program
	 */
	public PlcProgram(String projname, List<PlcBlock> blocks) {
		this.ProjectName = projname;
		this.Blocks = blocks;
	}

	/**
	 * Gets the project name of the PLC program.
	 *
	 * @return the project name of the PLC program
	 */
	public String getProjectName() {
		return ProjectName;
	}

	/**
	 * Gets a block by name.
	 *
	 * @param name the name of the block
	 * @return the block by name
	 */
	public PlcBlock getBlockByName(String name) {
		for (PlcBlock b : Blocks) {
			if (b.getName().equals(name)) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Gets the list with blocks.
	 *
	 * @return the list with blocks
	 */
	public List<PlcBlock> getBlocks() {
		return Blocks;
	}

	/**
	 * Gets the typed blocks.
	 *
	 * @param cls the class to match and return
	 * @return the typed blocks
	 */
	public List<PlcBlock> getTypedBlocks(Class<?> cls) {
		List<PlcBlock> TypBlocks = new ArrayList<>();
		for (PlcBlock b : Blocks) {
			if (cls.isInstance(b)) {
				TypBlocks.add(b);
			}
		}
		Collections.sort(TypBlocks, PlcBlock.BlockNrComp);
		return TypBlocks;
	}

	/**
	 * Gets the blocks ordered.
	 *
	 * @return the blocks ordered
	 */
	public List<PlcBlock> getBlocksOrdered() {
		List<PlcBlock> bl = new ArrayList<>();
		bl.addAll(getTypedBlocks(OrganizationBlock.class));
		bl.addAll(getTypedBlocks(FunctionBlock.class));
		bl.addAll(getTypedBlocks(Function.class));
		bl.addAll(getTypedBlocks(GlobalDB.class));
		bl.addAll(getTypedBlocks(InstanceDB.class));
		bl.addAll(getTypedBlocks(ArrayDB.class));
		return bl;
	}

	/**
	 * Gets the call structure of the PLC program.
	 *
	 * @return the call structure of the PLC program
	 */
	public CallStructure getCallStructure() {
		return this.CallStructure;
	}

	/**
	 * Sets the call structure of the PLC program.
	 *
	 * @param callStructure the new call structure of the PLC program
	 */
	public void setCallStructure(CallStructure callStructure) {
		CallStructure = callStructure;
	}

	/**
	 * Gets the variables of the PLC program.
	 *
	 * @return the variables of the PLC program
	 */
	public List<Variable> getVariables() {
		return Variables;
	}

	/**
	 * Sets the variables of the PLC program.
	 *
	 * @param variables the new variables of the PLC program
	 */
	public void setVariables(List<Variable> variables) {
		Variables = variables;
	}

}
