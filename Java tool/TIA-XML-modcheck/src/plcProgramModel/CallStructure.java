package plcProgramModel;

import plcCodeUnitModel.Call;

/**
 * The Class CallStructure.
 */
public class CallStructure {

	/** The root of the call structure. */
	Root root;

	/**
	 * Instantiates a new call structure.
	 */
	public CallStructure() {
		this.root = new Root();
	}

	/**
	 * Adds the OB to the root.
	 *
	 * @param ob the called ob
	 * @return the node
	 */
	public Node addOB(Call ob) {
		Node nOB = new Node(ob);
		root.addOb(nOB);
		return nOB;
	}

	/**
	 * Adds a call to the list of calls.
	 *
	 * @param currentNode the current node
	 * @param blnm        the block number
	 * @return the node
	 */
	public Node addCall(Node currentNode, Call blnm) {
		Node newNode = new Node(blnm);
		currentNode.addCall(newNode);
		return currentNode;
	}

	/**
	 * Gets the root of the call structure.
	 *
	 * @return the root
	 */
	public Root getRoot() {
		return root;
	}
}
