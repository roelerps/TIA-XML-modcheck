package plcCodeUnitModel;

import java.util.List;

import plcBlockModel.CodeBlock;

/**
 * The Class CallOB.
 */
public class CallOB extends Call {

	/**
	 * Instantiates a new call OB.
	 *
	 * @param id    the id of the OB
	 * @param block the OB block
	 */
	public CallOB(int id, CodeBlock block) {
		super(id, block);

	}

	/**
	 * Converts the code unit into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		return this.getCallname();
	}

	@Override
	public String toStringFormula(List<String> outp) {
		return null;
	}
}
