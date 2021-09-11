package plcCodeUnitModel;

import plcBlockModel.CodeBlock;

/**
 * The Class CallFB.
 */
public abstract class CallFB extends Call {

	/** The instance name of the FB call. */
	private String instancename; // bijv VlvDrn

	/** The instance location of the FB call. */
	private String instancelocation; // bijv LocalVariable

	/**
	 * Instantiates a new call FB.
	 *
	 * @param id               the id of the FB call
	 * @param block            the block of the FB call
	 * @param instancename     the instance name of the FB call
	 * @param instancelocation the instance location of the FB call
	 */
	public CallFB(int id, CodeBlock block, String instancename, String instancelocation) {
		super(id, block);
		this.instancename = instancename;
		this.instancelocation = instancelocation;
	}

	/**
	 * Gets the instance name of the FB call.
	 *
	 * @return the instance name of the FB call
	 */
	public String getInstancename() {
		return instancename;
	}

	/**
	 * Gets the instance location of the FB call.
	 *
	 * @return the instance location of the FB call
	 */
	public String getInstancelocation() {
		return instancelocation;
	}

}
