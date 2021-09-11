package plcCodeUnitModel;

import java.util.List;

import plcBlockModel.CodeBlock;

/**
 * The Class CallFBMultiple.
 */
public class CallFBMultiple extends CallFB {

	/**
	 * Instantiates a new call FB multiple.
	 *
	 * @param id               the id of the FB
	 * @param block            the function block
	 * @param instancename     the instance name of the FB
	 * @param instancelocation the instance location of the FB
	 */
	public CallFBMultiple(int id, CodeBlock block, String instancename, String instancelocation) {
		super(id, block, instancename, instancelocation);
	}

	/**
	 * Call FB to string.
	 *
	 * @return the string
	 */
	public String toString() {
		return super.getCallname() + " " + super.getBlocktype() + super.getBlocknumber() + " ("
				+ super.getInstancename() + ")";
	}

	/**
	 * Converts the code unit into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		String output = "";
		output = "Call: " + super.getBlocktype() + super.getBlocknumber();
		output += " (" + super.getCallname() + ")";
		output += ", Instance: " + super.getInstancename();
		output += " (" + super.getInstancelocation() + ")\r\n";

		return output;
	}

	@Override
	public String toStringFormula(List<String> outp) {
		return null;
	}

}
