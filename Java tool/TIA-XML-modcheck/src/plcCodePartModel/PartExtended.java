package plcCodePartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PartExtended
 */
public class PartExtended extends Part {

	private TemplateValue templVal1;
	private TemplateValue templVal2;
	private List<String> negated;

	/**
	 * Instantiates a new part with 1 templatevariable.
	 * 
	 * @param name           the name of the part
	 * @param uId            the unique identifier of the part
	 * @param templval1name  the templatevariable 1 name
	 * @param templval1type  the templatevariable 1 type
	 * @param templval1value the templatevariable 1 value
	 * @param negated        if the part is negated
	 */
	public PartExtended(String name, int uId, String templval1name, String templval1type, String templval1value,
			List<String> negated) {
		super(name, uId);
		this.templVal1 = new TemplateValue();
		this.templVal1.setAll(templval1name, templval1type, templval1value);
		this.negated = new ArrayList<>();
		this.negated.addAll(negated);
	}

	/**
	 * Instantiates a new part with 2 templatevariables.
	 * 
	 * @param name           the name of the part
	 * @param uId            the unique indentifier of the part
	 * @param templval1name  the templatevariable 1 name
	 * @param templval1type  the templatevariable 1 type
	 * @param templval1value the templatevariable 1 value
	 * @param templval2name  the templatevariable 2 name
	 * @param templval2type  the templatevariable 2 type
	 * @param templval2value the templatevariable 2 value
	 * @param negated        if the part is negated
	 */
	public PartExtended(String name, int uId, String templval1name, String templval1type, String templval1value,
			String templval2name, String templval2type, String templval2value, List<String> negated) {
		super(name, uId);
		this.templVal1 = new TemplateValue();
		this.templVal2 = new TemplateValue();
		this.templVal1.setAll(templval1name, templval1type, templval1value);
		this.templVal2.setAll(templval2name, templval2type, templval2value);
		this.negated = new ArrayList<>();
		this.negated.addAll(negated);
	}

	/**
	 * Gets templatevariable 1
	 * 
	 * @return the template variable
	 */
	public TemplateValue getTemplVal1() {
		return templVal1;
	}

	/**
	 * Gets templatevariable 2
	 * 
	 * @return the template variable
	 */
	public TemplateValue getTemplVal2() {
		return templVal2;
	}

	/**
	 * Gets the value of Negated
	 * 
	 * @return the negated value
	 */
	public List<String> getNegated() {
		return negated;
	}

	/**
	 * Sets the value of negated
	 * 
	 * @param neg if the part is negated
	 */
	public void setNegated(List<String> neg) {
		this.negated.clear();
		this.negated.addAll(neg);
	}

	/**
	 * Gets the cardinality of the template variables
	 * 
	 * @return the cardinality of the template variables
	 */
	public int getCardinality() {
		if (this.templVal1 != null && this.templVal1.getType().equals("Cardinality")) {
			return Integer.parseInt(this.templVal1.getValue());
		}
		if (this.templVal2 != null && this.templVal2.getType().equals("Cardinality")) {
			return Integer.parseInt(this.templVal2.getValue());
		}
		return 0;
	}

	/**
	 * Checks if the part is negated
	 * 
	 * @param sComp the component to check if it is negated
	 * @return true if the component of the part is negated
	 */
	public boolean isNegated(String sComp) {
		for (String s : this.negated) {
			if (s.equals(sComp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The Class TemplateValue
	 */
	private class TemplateValue {

		private String name;
		private String type;
		private String value;

		/**
		 * Sets all properties of the templatevalue
		 * 
		 * @param name  the name of the templatevalue
		 * @param type  the type of the templatevalue
		 * @param value the value of the templatevalue
		 */
		public void setAll(String name, String type, String value) {
			this.name = name;
			this.type = type;
			this.value = value;
		}

		/**
		 * Gets the name of the templatevalue
		 * 
		 * @return the name of the templatevalue
		 */
		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		/**
		 * Sets the name of the templatevalue
		 * 
		 * @param name of the template value
		 */
		@SuppressWarnings("unused")
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the type of the templatevalue
		 * 
		 * @return the type of the templatevalue
		 */
		public String getType() {
			return type;
		}

		/**
		 * Sets the type of the templatevalue
		 * 
		 * @param type the type of the templatevalue
		 */
		@SuppressWarnings("unused")
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * Gets the value of the templatevalue
		 * 
		 * @return the value of the templatevalue
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the templatevalue
		 * 
		 * @param value the value of the templatevalue
		 */
		@SuppressWarnings("unused")
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Converts the templatevalue into a string
		 *
		 * @return the string
		 */
		public String toString() {
			return this.name + " " + this.type + " " + this.value;
		}
	}

	/**
	 * Converts the part into a string
	 * 
	 * @return the string
	 */
	public String toString() {
		String outp = "Part ID: " + this.getId();
		outp = outp + " " + this.getName();

		if (templVal1 != null) {
			outp = outp + "\r\n" + "   " + templVal1.toString();
		}
		if (templVal2 != null) {
			outp = outp + "\r\n" + "   " + templVal2.toString();
		}
		if (!negated.contains("false")) {
			outp = outp + "\r\n" + "   " + templVal1.toString();
		}
		return String.valueOf(this.getId());
	}

	/**
	 * Converts the part into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		String outp = "";
		String operator = this.getName();
		switch (operator) {
		case ("A"):
			operator = "&&";
			break;
		case ("O"):
			operator = "||";
			break;
		case ("Eq"):
			operator = "(connector == connector)";
			break;
		case ("Ne"):
			operator = "(connector <> connector)";
			break;
		case ("Lt"):
			operator = "(connector < connector)";
			break;
		case ("Gt"):
			operator = "(connector > connector)";
			break;
		case ("Move"):
			operator = "<== connector when";
			break;
		case ("Add"):
			operator = "+";
			break;
		}

		if (getCardinality() > 1) {
			outp = "(connector";
			for (int i = 1; i < getCardinality(); i++) {
				outp += " " + operator + " connector";
			}
			outp += ")";
		} else if (getCardinality() == 1) {
			if (!operator.equals("<== connector when")) {
				outp = "";
			} else {
				outp = operator;
			}
		} else {
			outp = operator;
		}
		return outp;
	}

	/**
	 * Converts the part into a string formula
	 *
	 * @param out the connected variables
	 * @return the string formula
	 */
	public String toStringFormula(List<String> out) {
		String outp = "";
		String operator = this.getName();
		List<String> vars = new ArrayList<>();
		vars.addAll(out);

		for (String neg : this.negated) {
			if (vars.contains(neg)) {
				int index = vars.indexOf(neg);
				vars.remove(index);
				vars.add(index, "!" + neg);
			}
		}

		switch (operator) {
		case ("A"):
			operator = "&&";
			break;
		case ("O"):
			operator = "||";
			break;
		case ("Eq"):
			operator = "==";
			if (vars.size() == 2) {
				operator = "(" + "cu-" + vars.get(0) + " == " + "cu-" + vars.get(1) + ")";
			}
			break;
		case ("Ne"):
			operator = "<>";
			if (vars.size() == 2) {
				operator = "(" + "cu-" + vars.get(0) + " <> " + "cu-" + vars.get(1) + ")";
			}
			break;
		case ("Lt"):
			operator = "<";
			if (vars.size() == 2) {
				operator = "(" + "cu-" + vars.get(0) + " < " + "cu-" + vars.get(1) + ")";
			}
			break;
		case ("Gt"):
			operator = ">";
			if (vars.size() == 2) {
				operator = "(" + "cu-" + vars.get(0) + " > " + "cu-" + vars.get(1) + ")";
			}
			break;
		case ("Move"):
			operator = "=";
			if (vars.size() == 3) {
				operator = "" + "cu-" + vars.get(2) + " = (if cu-" + vars.get(0) + " then " + "cu-" + vars.get(1) + ")";
			}
			if (vars.size() == 2) {
				operator = "" + "cu-" + vars.get(1) + " = " + "cu-" + vars.get(0) + "";
			}
			break;
		case ("Add"):
			operator = "+";
			if (vars.size() == 3) {
				operator = "(" + "cu-" + vars.get(0) + " = " + "cu-" + vars.get(1) + " + " + "cu-" + vars.get(2) + ")";
			}
			if (vars.size() == 4) {
				operator = "(" + "cu-" + vars.get(3) + " = " + "(if " + "cu-" + vars.get(0) + " then " + "cu-"
						+ vars.get(1) + " + " + "cu-" + vars.get(2) + "))";
			}
			break;
		}

		if (getCardinality() > 1 && vars.size() > 1 && !this.getName().equals("Add")) {
			outp = "(" + "cu-" + vars.get(0);
			for (int i = 1; i < getCardinality(); i++) {
				outp += " " + operator + " " + "cu-" + vars.get(i);
			}
			outp += ")";
		} else if (getCardinality() == 1 && vars.size() == 1) {
			outp = "cu-" + vars.get(0);
		} else if (getCardinality() == 2 && vars.size() == 1) {
			outp = "cu-" + vars.get(0);
		} else {
			outp = operator;
		}
		return outp;
	}

	/**
	 * Converts the part into a string formula
	 * 
	 * @param conn the connected parts
	 * @return the string formula
	 */
	public String toStringFormulaCall(List<String> conn) {
		return toStringFormula(conn);
	}

}
