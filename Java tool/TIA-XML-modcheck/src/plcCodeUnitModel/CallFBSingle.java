package plcCodeUnitModel;

import java.util.List;

import plcBlockModel.CodeBlock;

/**
 * The Class CallFBSingle.
 */
public class CallFBSingle extends CallFB {

	/** The area of the instance. */
	private String area;

	/** The blocknumber of the instance. */
	private int blocknr;

	/** The type of the instance. */
	private String type;

	/**
	 * Instantiates a new call FB single.
	 *
	 * @param id               the id of the instance
	 * @param block            the block of the instance
	 * @param instancename     the instance name
	 * @param instancelocation the instance location
	 * @param area             the area of the instance
	 * @param blocknr          the blocknumber of the instance
	 * @param type             the type of the instance
	 */
	public CallFBSingle(int id, CodeBlock block, String instancename, String instancelocation, String area, int blocknr,
			String type) {
		super(id, block, instancename, instancelocation);
		this.area = area;
		this.blocknr = blocknr;
		this.type = type;
	}

	/**
	 * Gets the area of the instance.
	 *
	 * @return the area of the instance
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Gets the blocknumber of the instance.
	 *
	 * @return the blocknumber of the instance
	 */
	public int getBlocknr() {
		return blocknr;
	}

	/**
	 * Gets the type of the instance.
	 *
	 * @return the type of the instance
	 */
	public String getType() {
		return type;
	}

	/**
	 * Call FB to string.
	 *
	 * @return the string
	 */
	public String toString() {
		return super.getCallname() + " " + super.getBlocktype() + super.getBlocknumber() + " ("
				+ super.getInstancename() + " " + area + blocknr + ")";
	}

	/**
	 * Converts the code unit into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		String output = "";
		output = "Call: " + super.getBlocktype() + super.getBlocknumber();
		output += " (" + super.getCallname() + ")";
		output += ", Instance: " + super.getInstancename();
		output += " (" + super.getInstancelocation() + ")\r\n";

		return output;
	}

	@Override
	public String toStringFormula(List<String> outp) {
		return null;
	}
}
