package plcProgramModel;

import java.util.List;

import plcCodeUnitModel.Call;

/**
 * The Class Node.
 */
public class Node {

	/** The called block. */
	private Call block;

	/** The list with block calls. */
	private List<Node> calls;

	/**
	 * Instantiates a new node.
	 *
	 * @param block the called block
	 */
	public Node(Call block) {
		this.block = block;
	}

	/**
	 * Gets the blockname of the called block.
	 *
	 * @return the blockname of the called block
	 */
	public String getBlockname() {
		return block.getCallname();
	}

	/**
	 * Sets the called block.
	 *
	 * @param block the called block
	 */
	public void setBlock(Call block) {
		this.block = block;
	}

	/**
	 * Gets the called block.
	 *
	 * @return the called block
	 */
	public Call getBlock() {
		return this.block;
	}

	/**
	 * Gets the list with calls.
	 *
	 * @return the list with calls
	 */
	public List<Node> getCalls() {
		return calls;
	}

	/**
	 * Sets the list with calls.
	 *
	 * @param calls the list with calls
	 */
	public void setCalls(List<Node> calls) {
		this.calls = calls;
	}

	/**
	 * Adds a call to the list.
	 *
	 * @param call the call to be added to the list
	 */
	public void addCall(Node call) {
		this.calls.add(call);
	}

	/**
	 * Gets the call info of the called block.
	 *
	 * @return the call info of the called block
	 */
	public String getCallInfo() {
		return block.toString();
	}
}