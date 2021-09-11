package plcProgramModel;

/**
 * The Class UserDefinedDataType.
 */
public class UserDefinedDataType extends Variable {

	/** The Instance of variable. */
	private String InstanceOf;

	/**
	 * Instantiates a new user defined data type.
	 *
	 * @param name     the name of the user defined data type
	 * @param dataType the data type
	 * @param instOf   the instance of the user defined data type
	 * @param uid      the uid of the instance
	 */
	public UserDefinedDataType(String name, VariableDatatype dataType, String instOf, int uid) {
		super(name, dataType, uid);
		this.InstanceOf = instOf;
	}

	/**
	 * Gets the instance of variable.
	 *
	 * @return the instance of variable
	 */
	public String getInstanceOf() {
		return InstanceOf;
	}

	/**
	 * UDT to string.
	 *
	 * @return the string
	 */
	public String toString() {
		return getName() + " (" + getDataType() + " of " + InstanceOf + ")";
	}

}
