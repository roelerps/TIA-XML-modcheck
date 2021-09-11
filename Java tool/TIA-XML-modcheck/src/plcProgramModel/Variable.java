package plcProgramModel;

/**
 * The Class Variable.
 */
public class Variable {

	/** The name of the variable. */
	private String name;

	/** The data type of the variable. */
	private VariableDatatype dataType;

	/** The uid of the variable. */
	private int uid;

	/**
	 * Instantiates a new variable.
	 *
	 * @param name     the name of the variable
	 * @param dataType the data type of the variable
	 * @param uid      the uid of the variable
	 */
	public Variable(String name, VariableDatatype dataType, int uid) {
		this.name = name;
		this.dataType = dataType;
		this.uid = uid;
	}

	/**
	 * Gets the data type of the variable.
	 *
	 * @return the data type of the variable
	 */
	public VariableDatatype getDataType() {
		return dataType;
	}

	/**
	 * Gets the name of the variable.
	 *
	 * @return the name of the variable
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the uid of the variable.
	 *
	 * @return the uid of the variable
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Variable to string. *
	 * 
	 * @return the string
	 */
	public String toString() {
		return name + " (" + dataType + ")";
	}

}
