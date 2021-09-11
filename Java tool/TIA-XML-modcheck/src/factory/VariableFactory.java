package factory;

import java.util.ArrayList;
import java.util.List;

import plcProgramModel.UserDefinedDataType;
import plcProgramModel.Variable;
import plcProgramModel.VariableDatatype;

/**
 * A factory for creating Variable objects.
 */
/**
 * @author Roel
 *
 */
public abstract class VariableFactory {

	/**
	 * Instantiates a new variable factory.
	 */
	private VariableFactory() {

	}

	/**
	 * Creates a new Variable object.
	 *
	 * @param name     the Variable name
	 * @param datatype the Variable datatype
	 * @param uid      the uid of the Variable
	 * @return the created Variable
	 */
	public static Variable createVariable(String name, String datatype, int uid) {
		VariableDatatype dt = null;
		String instance = "";
		if (datatype.equals("Bool")) {
			dt = VariableDatatype.BOOL;
		} else if (datatype.equals("Int")) {
			dt = VariableDatatype.INT;
		} else if (datatype.equals("Real")) {
			dt = VariableDatatype.REAL;
		} else if (datatype.equals("Time")) {
			dt = VariableDatatype.TIME;
		} else if (datatype.contains("\"")) {
			dt = VariableDatatype.UDT;
			instance = datatype.replace("\"", "");
		} else if (datatype.equals("TON_TIME")) {
			dt = VariableDatatype.TIMER;
		} else if (datatype.equals("DInt")) {
			dt = VariableDatatype.DINT;
		} else if (datatype.equals("Local")) {
			dt = VariableDatatype.LOCALUNKNOWN;
		} else if (datatype.equals("Void")) {
			dt = VariableDatatype.VOID;
		} else {
			dt = VariableDatatype.UDT;
			instance = datatype;
		}
		if (dt == VariableDatatype.UDT) {
			return new UserDefinedDataType(name, dt, instance, uid);
		} else {
			return new Variable(name, dt, uid);
		}
	}

	/**
	 * Removes the duplicates in the list of variables.
	 *
	 * @param variables the variables
	 * @return the list with unique variables
	 */
	public static List<Variable> removeDuplicates(List<Variable> variables) {
		List<Variable> uniquevars = new ArrayList<>();
		for (Variable v : variables) {
			boolean found = false;
			String name = v.getName();
			VariableDatatype dt = v.getDataType();
			for (Variable uv : uniquevars) {
				String uname = uv.getName();
				VariableDatatype udt = uv.getDataType();
				if (name != null && name.equals(uname) && dt != null && dt.equals(udt)) {
					found = true;
				}
			}
			if (!found) {
				uniquevars.add(v);
			}
		}
		return uniquevars;
	}

	/**
	 * Checks the datatype of a variable
	 * 
	 * @param datatype the variable which datatype needs to be determined
	 * @return the datatype of the variable
	 */
	public static VariableDatatype checkDatatypeVariable(String datatype) {
		VariableDatatype dt = null;
		if (datatype.equals("Bool")) {
			dt = VariableDatatype.BOOL;
		} else if (datatype.equals("Int")) {
			dt = VariableDatatype.INT;
		} else if (datatype.equals("Real")) {
			dt = VariableDatatype.REAL;
		} else if (datatype.equals("Time")) {
			dt = VariableDatatype.TIME;
		} else if (datatype.contains("\"")) {
			dt = VariableDatatype.UDT;
		} else if (datatype.equals("TON_TIME")) {
			dt = VariableDatatype.TIMER;
		} else if (datatype.equals("DInt")) {
			dt = VariableDatatype.DINT;
		} else if (datatype.equals("Local")) {
			dt = VariableDatatype.LOCALUNKNOWN;
		} else if (datatype.equals("Void")) {
			dt = VariableDatatype.VOID;
		} else {
			dt = VariableDatatype.UDT;
		}

		return dt;
	}

}