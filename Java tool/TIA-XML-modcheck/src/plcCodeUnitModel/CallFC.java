package plcCodeUnitModel;

import java.util.List;

import plcBlockModel.CodeBlock;

/**
 * The Class CallFC.
 */
public class CallFC extends Call {

	/**
	 * Instantiates a new call FC.
	 *
	 * @param id    the id of the called FC
	 * @param block the block of the function
	 */
	public CallFC(int id, CodeBlock block) {
		super(id, block);

	}

	/**
	 * Converts the code unit into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		String output = "";
		output = "Call: " + super.getBlocktype() + super.getBlocknumber();
		output += " (" + super.getCallname() + ")\r\n";

		return output;
	}

	@Override
	public String toStringFormula(List<String> outp) {
		return null;
	}

}
