package plcCodeUnitModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Wire
 */
public class Wire extends CodeUnit {

	List<Connector> connectors;

	/**
	 * Instantiates a new Wire object
	 * 
	 * @param uid the unique identifier of the wire
	 */
	public Wire(int uid) {
		super(uid);
		this.connectors = new ArrayList<>();
	}

	/**
	 * Gets the connectors of the wire
	 * 
	 * @return the connectors of the wire
	 */
	public List<Connector> getConnectors() {
		return connectors;
	}

	/**
	 * Gets a specific connector from the wire
	 * 
	 * @param nr the specific connector
	 * @return the connector
	 */
	public Connector getConnector(int nr) {
		if (nr >= 0 && nr < connectors.size()) {
			return connectors.get(nr);
		} else {
			return null;
		}
	}

	/**
	 * Gets the number of connectors
	 * 
	 * @return the number of connectors
	 */
	public int getNumberOfConnectors() {
		return connectors.size();
	}

	/**
	 * Adds a connector to the wire
	 * 
	 * @param type the connector type
	 * @param uid  the unique identifier of the connector
	 * @param name the name of the connector
	 */
	public void addConnector(ConnectorType type, int uid, String name) {
		Connector c = new Connector(type, uid, name);
		this.connectors.add(c);
	}

	/**
	 * Adds a connector to the wire
	 * 
	 * @param connector the connector to add
	 */
	public void addConnector(Connector connector) {
		this.connectors.add(connector);
	}

	/**
	 * Checks whether the wire has a connector with the specified uid
	 * 
	 * @param uid the unique identifier of the connector to check
	 * @return true of the wire contains this connector
	 */
	public boolean hasConnectorWithUID(int uid) {
		for (Connector con : connectors) {
			if (con.getUid() == uid) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the first destination of the source uid
	 * 
	 * @param uid the unique identiefier to search for
	 * @return the first destination of the source uid
	 */
	public int getFirstDestinationOfSourceUID(int uid) {
		if (connectors != null && connectors.size() > 0) {
			if (connectors.get(0).getUid() == uid) {
				return connectors.get(1).getUid();
			} else if (connectors.get(1).name.equals("operand") && (connectors.get(1).getUid() == uid)) {
				return connectors.get(0).getUid();
			} else {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Gets the connected parts by using an uid
	 * 
	 * @param uid the unique identifier to search for
	 * @return the connected parts
	 */
	public List<Integer> getConnectedPartsUID(int uid) {
		if (this.hasConnectorWithUID(uid)) {
			List<Integer> uids = new ArrayList<>();
			for (Connector con : connectors) {
				if (con.getUid() != uid) {
					uids.add(con.getUid());
				}
			}
			return uids;
		} else {
			return null;
		}
	}

	/**
	 * Gets all connected parts
	 * 
	 * @return the connected parts
	 */
	public List<Integer> getConnectedPartsUID() {
		List<Integer> uids = new ArrayList<>();
		for (Connector con : connectors) {
			uids.add(con.getUid());
		}
		return uids;
	}

	/**
	 * Converts the wire into a string
	 * 
	 * @return the string
	 */
	public String toString() {
		String outp = "Wire ID: " + this.getId();
		for (Connector con : connectors) {
			outp = outp + "\r\n";
			outp = outp + "   " + con.toString();
		}
		return outp;
	}

	/**
	 * The Class Connector
	 */
	public class Connector {
		ConnectorType type;
		int uid;
		String name;

		/**
		 * Instantiates a new Connector object
		 * 
		 * @param type the type of the connector
		 * @param uid  the unique identifier of the connector
		 * @param name the name of the connector
		 */
		public Connector(ConnectorType type, int uid, String name) {
			this.type = type;
			this.uid = uid;
			this.name = name;
		}

		/**
		 * Converts the connector into a string
		 * 
		 * @return the string
		 */
		public String toString() {
			return "" + this.type.toString() + " " + this.uid + " " + this.name;
		}

		/**
		 * Gets the type of the connector
		 * 
		 * @return the type of the connector
		 */
		public ConnectorType getType() {
			return type;
		}

		/**
		 * Gets the uid of the connector
		 * 
		 * @return the uid of the connector
		 */
		public int getUid() {
			return uid;
		}

		/**
		 * Gets the name of the connector
		 * 
		 * @return the name of the connector
		 */
		public String getName() {
			return name;
		}

	}

	/**
	 * The Enumeration ConnectorType
	 */
	public enum ConnectorType {
		OpenCon, NameCon, IdentCon
	}

	/**
	 * Converts the wire into a string formula
	 *
	 * @return the string formula
	 */
	public String toStringFormula() {
		return "-";
	}

	@Override
	public String toStringFormula(List<String> outp) {
		return null;
	}

}
