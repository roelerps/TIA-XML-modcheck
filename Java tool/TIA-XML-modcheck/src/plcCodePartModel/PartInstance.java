package plcCodePartModel;

import java.util.List;

/**
 * The Class PartInstance
 */
public class PartInstance extends Part {

	private String version;
	private Instance instance;

	/**
	 * Instantiates a new PartInstance
	 * 
	 * @param name      the name of the part
	 * @param uId       the unique identifier of the part
	 * @param version   the version of the instance
	 * @param instName  the name of the instance
	 * @param instUId   the unique identifier of the instance
	 * @param instScope the scope of the instance
	 */
	public PartInstance(String name, int uId, String version, String instName, String instUId, String instScope) {
		super(name, uId);
		this.version = version;
		this.instance = new Instance();
		this.instance.setAll(instScope, instUId, instName);
	}

	/**
	 * Gets the version of the part
	 * 
	 * @return the version of the part
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Gets the instance of the part
	 * 
	 * @return the instance of the part
	 */
	public Instance getInstance() {
		return this.instance;
	}

	/**
	 * The Class Instance
	 */
	private class Instance {

		private String scope;
		private String uid;
		private String componentname;

		/**
		 * Sets all properties of the instance
		 * 
		 * @param scope         the scope of the instance
		 * @param uid           the unique identifier of the instance
		 * @param componentname the name of the instances component
		 */
		public void setAll(String scope, String uid, String componentname) {
			this.scope = scope;
			this.uid = uid;
			this.componentname = componentname;
		}

		/**
		 * Gets the scope of the instance
		 * 
		 * @return the scope of the instance
		 */
		@SuppressWarnings("unused")
		public String getScope() {
			return scope;
		}

		/**
		 * Sets the scope of the instance
		 * 
		 * @param scope the scope of the instance
		 */
		@SuppressWarnings("unused")
		public void setScope(String scope) {
			this.scope = scope;
		}

		/**
		 * Gets the unique identifier of the instance
		 * 
		 * @return the unique identifier of the instance
		 */
		@SuppressWarnings("unused")
		public String getUid() {
			return uid;
		}

		/**
		 * Sets the unique identifier of the instance
		 * 
		 * @param uid the unique identifier of the instance
		 */
		@SuppressWarnings("unused")
		public void setUid(String uid) {
			this.uid = uid;
		}

		/**
		 * Gets the name of the instances component
		 * 
		 * @return the name of the instances component
		 */
		public String getComponentname() {
			return componentname;
		}

		/**
		 * Sets the name of the instances component
		 * 
		 * @param componentname the name of the instances component
		 */
		@SuppressWarnings("unused")
		public void setComponentname(String componentname) {
			this.componentname = componentname;
		}

		/**
		 * Converts the instance into a string
		 *
		 * @return the string
		 */
		public String toString() {
			return this.scope + " " + this.uid + " " + this.componentname;
		}

	}

	/**
	 * Converts the part into a string
	 * 
	 * @return the string
	 */
	public String toString() {
		String outp = "";
		outp = outp + this.instance.toString();
		outp = outp + this.version;
		return outp;
	}

	/**
	 * Converts the part into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		String outp = "";
		outp = "(" + this.getName() + ": ";
		outp += this.getInstance().getComponentname();
		outp += " PT: connector)";

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
		int nrOfComp = out.size();
		if (nrOfComp == 2) {

			if (this.getName().equals("TON")) {
				outp = "(" + this.getName() + ": ";
				outp += this.getInstance().getComponentname();
				outp += " IN:";
				outp += "cu-" + out.get(0);
				outp += " PT:";
				outp += "cu-" + out.get(1);
				outp += ")";
			}

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
