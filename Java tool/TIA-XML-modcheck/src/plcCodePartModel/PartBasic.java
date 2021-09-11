package plcCodePartModel;

import java.util.List;

/**
 * The Class PartBasic
 */
public class PartBasic extends Part {

	private Boolean negated;

	/**
	 * Instantiates a new PartBasic
	 * 
	 * @param name    the name of the part
	 * @param uId     the unique identifier of the part
	 * @param negated if the part is negated
	 */
	public PartBasic(String name, int uId, Boolean negated) {
		super(name, uId);
		this.negated = negated;
	}

	/**
	 * Gets the value of Negated
	 * 
	 * @return the negated value
	 */
	public Boolean getNegated() {
		return negated;
	}

	/**
	 * Sets the value of negated
	 * 
	 * @param negated if the part is negated
	 */
	public void setNegated(Boolean negated) {
		this.negated = negated;
	}

	/**
	 * Convert the part to a string
	 * 
	 * @return information of the part
	 */
	public String toString() {
		String outp = "Part ID: " + this.getId();
		outp = outp + " " + this.getName();
		if (this.negated) {
			outp = outp + " negated";
		}
		return String.valueOf(this.getId());
	}

	/**
	 * Convert the part to a formula string
	 *
	 * @return the formula of the part
	 */
	public String toStringFormula() {
		String outp = "";
		switch (this.getName()) {
		case ("Coil"):
			outp = "=";
			if (this.getNegated()) {
				outp = "!=";
			}
			break;
		case ("SCoil"):
			outp = "= TRUE";
			break;
		case ("RCoil"):
			outp = "= FALSE";
			break;
		case ("Contact"):
			outp = "&&";
			if (this.getNegated()) {
				outp = "&& !";
			}
			break;
		case ("ReturnTrue"):
			outp = "ReturnTrue";
			break;
		case ("Sr"):
			outp = "SR";
			break;
		}
		return outp;
	}

	/**
	 * Converts the part with connected variables
	 *
	 * @return the formula of the part with connected variables
	 */
	public String toStringFormula(List<String> out) {
		String outp = "";
		int nrOfComp = out.size();
		if (nrOfComp > 0) {
			switch (this.getName()) {
			case ("Coil"):
				if (out.size() == 2) {
					outp = "cu-" + out.get(0) + " " + "=" + " " + "cu-" + out.get(1);
				} else {
					outp = "=" + " " + "cu-" + out.get(0);
				}
				if (this.getNegated()) {
					outp = "!=" + " " + "cu-" + out.get(0);
				}
				break;
			case ("SCoil"):
				outp = "= (if " + "cu-" + out.get(0) + " then TRUE)";
				break;
			case ("RCoil"):
				outp = "= (if " + "cu-" + out.get(0) + " then FALSE)";
				break;
			case ("Contact"):
				outp = "cu-" + out.get(0);
				for (int i = 1; i < out.size(); i++) {
					if (this.negated) {
						outp += " && !" + "cu-" + out.get(i);
					} else {
						outp += " && " + "cu-" + out.get(i);
					}
				}
				break;
			case ("ReturnTrue"):
				outp = "ReturnTrue " + "cu-" + out.get(0);
				for (int i = 1; i < out.size(); i++) {
					if (this.negated) {
						outp += " ? " + "cu-" + out.get(i);
					} else {
						outp += " ? " + "cu-" + out.get(i);
					}
				}
				break;
			case ("Sr"):
				outp = "= ((if " + "cu-" + out.get(0) + " then TRUE)";
				for (int i = 1; i < out.size(); i++) {
					if (this.negated) {
						outp += " (elseif !cu-" + out.get(i) + " then FALSE))";
					} else {
						outp += " (elseif cu-" + out.get(i) + " then FALSE))";
					}
				}
				break;
			}
		}
		return outp;
	}

	/**
	 * Converts the part with connected variables when connected to a call
	 *
	 * @return the formula of the part with connected variables
	 */
	public String toStringFormulaCall(List<String> out) {
		String outp = "";
		int nrOfComp = out.size();
		if (nrOfComp > 0) {
			switch (this.getName()) {
			case ("Coil"):
				if (out.size() == 2) {
					outp = "cu-" + out.get(0) + " " + "=" + " " + "cu-" + out.get(1);
				} else {
					outp = "=" + " " + "cu-" + out.get(0);
				}
				if (this.getNegated()) {
					outp = "!=" + " " + "cu-" + out.get(0);
				}
				break;
			case ("SCoil"):
				outp = "cu-" + out.get(0) + " = TRUE";
				break;
			case ("RCoil"):
				outp = "cu-" + out.get(0) + " = FALSE";
				break;
			case ("Contact"):
				outp = "cu-" + out.get(0);
				for (int i = 1; i < out.size(); i++) {
					if (this.negated) {
						outp += " && !" + "cu-" + out.get(i);
					} else {
						outp += " && " + "cu-" + out.get(i);
					}
				}
				break;
			case ("ReturnTrue"):
				outp = "ReturnTrue " + "cu-" + out.get(0);
				for (int i = 1; i < out.size(); i++) {
					if (this.negated) {
						outp += " ? " + "cu-" + out.get(i);
					} else {
						outp += " ? " + "cu-" + out.get(i);
					}
				}
				break;
			case ("Sr"):
				outp = "( = SET when " + "cu-" + out.get(0) + " ) ";
				for (int i = 1; i < out.size(); i++) {
					if (this.negated) {
						outp += " ( = RESET when !" + "cu-" + out.get(i) + " ) ";
					} else {
						outp += " ( = RESET when " + "cu-" + out.get(i) + " ) ";
					}
				}
				break;
			}
		}
		return outp;
	}

}
