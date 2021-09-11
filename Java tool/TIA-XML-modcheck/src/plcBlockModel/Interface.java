package plcBlockModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plcProgramModel.Variable;
import plcProgramModel.VariableDatatype;

/**
 * The Class Interface.
 */
public class Interface {

	/** The input variables */
	private List<Variable> input;

	/** The output variables */
	private List<Variable> output;

	/** The inOut variables */
	private List<Variable> inOut;

	/** The static variables */
	private List<Variable> stat; // static

	/** The temporary variables */
	private List<Variable> temp;

	/** The constant variables */
	private List<Variable> constant;

	/**
	 * Instantiates a new interface.
	 *
	 * @param input    the input variables
	 * @param output   the output variables
	 * @param inOut    the inOut variables
	 * @param stat     the static variables
	 * @param temp     the temporary variables
	 * @param constant the constant variables
	 */
	public Interface(List<Variable> input, List<Variable> output, List<Variable> inOut, List<Variable> stat,
			List<Variable> temp, List<Variable> constant) {
		this.input = input;
		this.output = output;
		this.inOut = inOut;
		this.stat = stat;
		this.temp = temp;
		this.constant = constant;
	}

	/**
	 * Adds the en/eno path.
	 */
	public void addEnPath() {
		this.input.add(new Variable("en", VariableDatatype.BOOL, 1));
		this.output.add(new Variable("eno", VariableDatatype.BOOL, 1));
	}

	/**
	 * Gets a list with all variables.
	 *
	 * @return list with all variables
	 */
	private List<Variable> getAllVariables() {
		List<Variable> allvars = new ArrayList<>();
		allvars.addAll(this.input);
		allvars.addAll(this.output);
		allvars.addAll(this.inOut);
		allvars.addAll(this.stat);
		allvars.addAll(this.temp);
		allvars.addAll(this.constant);
		return allvars;
	}

	/**
	 * Gets the named variable by name.
	 *
	 * @param variableName the variable name
	 * @return the named variable
	 */
	public Variable getNamedVariable(String variableName) {
		List<Variable> allvars = getAllVariables();
		for (Variable var : allvars) {
			if (var.getName().equals(variableName)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * Gets the input variables.
	 *
	 * @return the input variables
	 */
	public List<Variable> getInput() {
		return input;
	}

	/**
	 * Gets the output variables.
	 *
	 * @return the output variables
	 */
	public List<Variable> getOutput() {
		return output;
	}

	/**
	 * Gets the in out variables.
	 *
	 * @return the in out variables
	 */
	public List<Variable> getInOut() {
		return inOut;
	}

	/**
	 * Gets the static variables.
	 *
	 * @return the static variables
	 */
	public List<Variable> getStat() {
		return stat;
	}

	/**
	 * Gets the temporary variables.
	 *
	 * @return the temporary variables
	 */
	public List<Variable> getTemp() {
		return temp;
	}

	/**
	 * Gets the constant variables.
	 *
	 * @return the constant variables
	 */
	public List<Variable> getConstant() {
		return constant;
	}

	/**
	 * Interface to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String s = "";

		if (!this.input.isEmpty()) {
			s = s + "\r\nInputs: ";
			s = s + Arrays.toString(input.toArray());
		}
		if (!this.output.isEmpty()) {
			s = s + "\r\nOutputs: ";
			s = s + Arrays.toString(output.toArray());
		}
		if (!this.inOut.isEmpty()) {
			s = s + "\r\nInOuts: ";
			s = s + Arrays.toString(inOut.toArray());
		}
		if (!this.stat.isEmpty()) {
			s = s + "\r\nStatics: ";
			s = s + Arrays.toString(stat.toArray());
		}
		if (!this.temp.isEmpty()) {
			s = s + "\r\nTemps: ";
			s = s + Arrays.toString(temp.toArray());
		}
		if (!this.constant.isEmpty()) {
			s = s + "\r\nConstants: ";
			s = s + Arrays.toString(constant.toArray());
		}
		s = s + "\r\n";

		return s;
	}

}
