package plcCodeUnitModel;

import java.util.List;

import plcProgramModel.VariableDatatype;

/**
 * The Class Access
 */
public class Access extends CodeUnit {

	/** The name of the variable. */
	private String name;

	/** The data type of the variable. */
	private VariableDatatype dataType;

	/**
	 * Instantiates a new Access object
	 * 
	 * @param uid  the unique identifier of the access
	 * @param name the name of the access
	 * @param type the type of access
	 */
	public Access(int uid, String name, VariableDatatype type) {
		super(uid);
		this.name = name;
		this.dataType = type;
	}

	/**
	 * Gets the name of the access
	 * 
	 * @return the name of the access
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the datatype of the access
	 * 
	 * @return the datatype of the access
	 */
	public VariableDatatype getDataType() {
		return dataType;
	}

	/**
	 * Converts the access into a string
	 * 
	 * @return the string
	 */
	public String toString() {
		String outp = "Access variable ID: " + this.getId();
		outp = outp + " " + this.name + " (" + this.dataType + ")";
		return String.valueOf(this.getId());
	}

	/**
	 * Converts the access into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		return this.name;
	}

	@Override
	public String toStringFormula(List<String> outp) {
		return null;
	}
}
