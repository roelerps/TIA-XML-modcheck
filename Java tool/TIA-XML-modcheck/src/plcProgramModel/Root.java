package plcProgramModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Root.
 */
public class Root {

	/** The OBs. */
	private List<Node> obs;

	/**
	 * Instantiates a new root.
	 */
	public Root() {
		obs = new ArrayList<>();
	}

	/**
	 * Gets the OBs.
	 *
	 * @return the OBs
	 */
	public List<Node> getObs() {
		return obs;
	}

	/**
	 * Sets the OBs.
	 *
	 * @param obs the OBs
	 */
	public void setObs(List<Node> obs) {
		this.obs = obs;
	}

	/**
	 * Adds an OB.
	 *
	 * @param ob the OB
	 */
	public void addOb(Node ob) {
		this.obs.add(ob);
	}
}