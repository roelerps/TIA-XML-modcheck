package plcBlockModel;

import java.util.ArrayList;
import java.util.List;

import plcBlockNetworkModel.Network;
import plcCodeUnitModel.Call;

/**
 * The Class CodeBlock.
 */
public abstract class CodeBlock extends PlcBlock {

	/** The code of the code block */
	private List<Network> code;

	/**
	 * Instantiates a new code block.
	 *
	 * @param name    the name of the code block
	 * @param nr      the number of the code block
	 * @param file    the XML file of the code block
	 * @param lang    the programming language of the code block
	 * @param intface the interface of the code block
	 */
	public CodeBlock(String name, int nr, String file, String lang, Interface intface) {
		super(name, nr, file, lang, intface);
	}

	/**
	 * Code block to string.
	 *
	 * @return the string
	 */
	public abstract String toString();

	/**
	 * Code block to string short.
	 *
	 * @return the string
	 */
	public abstract String toStringShort();

	/**
	 * Code of code block to string code.
	 *
	 * @param prefix the prefix to print in output string
	 * @return the string
	 */
	public String toStringCode(String prefix) {
		String outp = "";
		if (code != null) {
			for (Network n : code) {
				outp = outp + n.toString(prefix);
			}
		}
		return outp;
	}

	/**
	 * Code of all networks to string code
	 * 
	 * @return the string
	 */
	public String toStringCodeAllNetworks() {
		String outp = this.toStringShort() + "\r\n";
		if (code != null) {
			for (Network n : code) {
				if (n.getCodeline().size() > 0) {
					outp = outp + "Network " + n.getNumber() + ":\r\n";
				}
				outp = outp + n.formulaToString();
			}
		}
		return outp;
	}

	/**
	 * Gets the calls from the code block.
	 *
	 * @return the calls in the code block
	 */
	public List<Call> getCalls() {
		List<Call> calls = new ArrayList<>();
		for (Network n : code) {
			List<Call> cs = n.getCalls();
			calls.addAll(cs);
		}
		return calls;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code of the code block
	 */
	public List<Network> getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the code of the code block
	 */
	public void setCode(List<Network> code) {
		this.code = code;
	}
}
