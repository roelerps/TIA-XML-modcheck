package plcCodeUnitModel;

import java.util.List;

/**
 * The Class CodeUnit.
 */
public abstract class CodeUnit {

	/** The id of the code unit. */
	private int id;

	/**
	 * Instantiates a new code unit.
	 *
	 * @param id the id of the code unit
	 */
	public CodeUnit(int id) {
		this.id = id;
	}

	/**
	 * Gets the id of the code unit.
	 *
	 * @return the id of the code unit
	 */
	public int getId() {
		return id;
	}

	/**
	 * Converts the code unit into a string
	 * 
	 * @return the string
	 */
	public abstract String toString();

	/**
	 * Converts the code unit into a string formula
	 *
	 * @return the string formula
	 */
	public abstract String toStringFormula();

	/**
	 * Converts the code unit into a string formula
	 *
	 * @param outp the connected variables
	 * @return the string formula
	 */
	public abstract String toStringFormula(List<String> outp);

}
