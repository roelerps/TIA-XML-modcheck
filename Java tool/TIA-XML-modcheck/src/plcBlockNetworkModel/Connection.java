package plcBlockNetworkModel;

import plcProgramModel.Variable;

/**
 * The Class Connection.
 */
public class Connection {

	/** The variable source uid. */
	private int sourceUid;

	/** The variable source. */
	private Variable source;

	/** The variable destination uid. */
	private int destinationUid;

	/** The variable destination. */
	private Variable destination;

	/**
	 * Instantiates a new connection.
	 *
	 * @param sourceUid      the source uid of the variable
	 * @param source         the source of the variable
	 * @param destinationUid the destination uid of the variable
	 * @param destination    the destination of the variable
	 */
	public Connection(int sourceUid, Variable source, int destinationUid, Variable destination) {
		this.sourceUid = sourceUid;
		this.source = source;
		this.destinationUid = destinationUid;
		this.destination = destination;
	}

	/**
	 * Gets the source of the variable.
	 *
	 * @return the source of the variable
	 */
	public Variable getSource() {
		return source;
	}

	/**
	 * Gets the destination of the variable.
	 *
	 * @return the destination of the variable
	 */
	public Variable getDestination() {
		return destination;
	}

	/**
	 * Gets the source uid of the variable.
	 *
	 * @return the source uid of the variable
	 */
	public int getSourceUid() {
		return sourceUid;
	}

	/**
	 * Gets the destination uid of the variable.
	 *
	 * @return the destination uid of the variable
	 */
	public int getDestinationUid() {
		return destinationUid;
	}

	/**
	 * Connection to string.
	 *
	 * @return the string
	 */
	public String toString() {
		String outp = "From " + this.source.getName() + "\r\n";
		outp = outp + "To " + this.destination.getName();
		return outp;
	}

}
