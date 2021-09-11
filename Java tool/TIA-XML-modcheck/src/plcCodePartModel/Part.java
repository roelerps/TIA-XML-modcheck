package plcCodePartModel;

import java.util.List;

import plcCodeUnitModel.CodeUnit;

/**
 * The Class Part
 */
public abstract class Part extends CodeUnit {

	private String name;
	private String formula;

	/**
	 * Instantiates a new part.
	 * 
	 * @param name the name of the part
	 * @param uId  the unique identifier of the part
	 */
	public Part(String name, int uId) {
		super(uId);
		this.name = name;
		this.formula = "";
	}

	/**
	 * Gets the name of the Part.
	 * 
	 * @return the name of the part
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the formula of the part
	 * 
	 * @param formula the formula of the part
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	/**
	 * Gets the formula of the part
	 * 
	 * @return the formula of the part
	 */
	public String getFormula() {
		return this.formula;
	}

	/**
	 * Convert the formula to string
	 * 
	 * @param conn the connected variables
	 * @return the formula of the part
	 */
	public abstract String toStringFormulaCall(List<String> conn);

}
