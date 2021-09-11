package plcCodeUnitModel;

import plcBlockModel.CodeBlock;
import plcBlockModel.Function;
import plcBlockModel.FunctionBlock;
import plcBlockModel.OrganizationBlock;

/**
 * The Class Call.
 */
public abstract class Call extends CodeUnit {

	/** The code block. */
	private CodeBlock block;

	/**
	 * Instantiates a new call.
	 *
	 * @param id    the id of the call
	 * @param block the called block
	 */
	public Call(int id, CodeBlock block) {
		super(id);
		this.block = block;
	}

	/**
	 * Gets the called block.
	 *
	 * @return the called code block
	 */
	public CodeBlock getBlock() {
		return block;
	}

	/**
	 * Sets the called codeblock.
	 *
	 * @param block the called code block
	 */
	public void setBlock(CodeBlock block) {
		this.block = block;
	}

	/**
	 * Gets the callname of the called code block.
	 *
	 * @return the callname of the called code block
	 */
	public String getCallname() {
		return this.block.getName();
	}

	/**
	 * Gets the blocktype of the called code block.
	 *
	 * @return the blocktype of the called code block
	 */
	public String getBlocktype() {
		if (OrganizationBlock.class.isInstance(this.block)) {
			return "OB";
		}
		if (Function.class.isInstance(this.block)) {
			return "FC";
		}
		if (FunctionBlock.class.isInstance(this.block)) {
			return "FB";
		}
		return null;
	}

	/**
	 * Gets the blocknumber of the called code block.
	 *
	 * @return the blocknumber of the called code block
	 */
	public int getBlocknumber() {
		return this.block.getNumber();
	}

	/**
	 * Called code block to string.
	 *
	 * @return the string
	 */
	public String toString() {
		return this.getCallname() + " " + this.getBlocktype() + this.getBlocknumber();
	}

}
