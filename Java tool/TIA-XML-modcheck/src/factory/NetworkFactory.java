package factory;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import plcBlockModel.Interface;
import plcBlockNetworkModel.Connection;
import plcBlockNetworkModel.Network;
import plcCodeUnitModel.Call;
import plcCodeUnitModel.CodeUnit;
import plcProgramControl.TIAXMLmodcheck;
import plcProgramModel.PlcProgram;
import plcProgramModel.UserDefinedDataType;
import plcProgramModel.Variable;

/**
 * A factory for creating Network objects.
 */
public abstract class NetworkFactory {

	/**
	 * Instantiates a new network factory.
	 */
	private NetworkFactory() {

	}

	/**
	 * Creates a new Network object.
	 *
	 * @param tempProgram the PLC program (only containing PLC blocks)
	 * @param nw          part of the XML file containing one PLC block network
	 * @param nr          the network number
	 * @param variables   the available variables of the network
	 * @return the created network
	 */
	public static Network createNetwork(PlcProgram tempProgram, Element nw, int nr, List<Variable> variables) {
		List<CodeUnit> code = CodeUnitFactory.createCodeUnits(tempProgram, nw);
		List<Connection> connections = new ArrayList<>();
		for (CodeUnit u : code) {
			if (u instanceof Call) {
				Call c = (Call) u;
				Interface intface = c.getBlock().getInterface();
				connections.addAll(createConnections(nw, variables, intface));
			}
		}
		return new Network(nr, code, variables, connections);
	}

	/**
	 * Creates a List of Connections used in one network.
	 *
	 * @param nw        part of the XML file containing one PLC block network
	 * @param variables the available variables of the network
	 * @param intface   the interface of the called block
	 * @return the list of connections for one network
	 */
	private static List<Connection> createConnections(Element nw, List<Variable> variables, Interface intface) {
		List<Connection> connections = new ArrayList<>();

		NodeList nl = nw.getElementsByTagName("Wire");
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			Node from = e.getChildNodes().item(0);
			Node to = e.getChildNodes().item(1);

			if (e.getChildNodes().getLength() == 2) {
				String sfrom = from.getNodeName();
				String sto = to.getNodeName();
				if (!sfrom.equals("OpenCon") && !sto.equals("OpenCon")) {
					String fromUid = "";
					String fromName = "";
					String toUid = "";
					String toName = "";
					Variable fromVar = null;
					Variable toVar = null;

					if (sfrom.equals("IdentCon")) {
						fromUid = from.getAttributes().item(0).getNodeValue();
						fromVar = getVariableFromList(nw, variables, fromUid);
					} else if (sfrom.equals("NameCon")) {
						fromUid = from.getAttributes().item(1).getNodeValue();
						fromName = from.getAttributes().item(0).getNodeValue();
						fromVar = intface.getNamedVariable(fromName);
					}
					if (fromVar != null) {
						// do nothing
					} else if (TIAXMLmodcheck.debug) {
						System.err.println("fromVar is null" + sfrom + fromUid + fromName);
					}

					if (sto.equals("IdentCon")) {
						toUid = to.getAttributes().item(0).getNodeValue();
						toVar = getVariableFromList(nw, variables, toUid);
					} else if (sto.equals("NameCon")) {
						toUid = to.getAttributes().item(1).getNodeValue();
						toName = to.getAttributes().item(0).getNodeValue();
						toVar = intface.getNamedVariable(toName);
					}
					if (toVar != null) {
						// do nothing
					} else if (TIAXMLmodcheck.debug) {
						System.err.println("toVar is null" + sto + toUid + toName);
					}

					int fromUidInt = Integer.parseInt(fromUid);
					int toUidInt = Integer.parseInt(toUid);

					if (fromVar != null && toVar != null) {
						connections.add(new Connection(fromUidInt, fromVar, toUidInt, toVar));
					}
				}
			}
		}
		return connections;
	}

	/**
	 * Gets the variable from list.
	 *
	 * @param nw        part of the XML file containing one PLC block network
	 * @param variables the available variables of the network
	 * @param uid       the uid of the Variable
	 * @return the variable from the supplied list
	 */
	private static Variable getVariableFromList(Element nw, List<Variable> vars, String uid) {
		NodeList nlaccess = nw.getElementsByTagName("Access");
		for (int i = 0; i < nlaccess.getLength(); i++) {
			Element e = (Element) nlaccess.item(i);
			if (e.getAttribute("UId").equals(uid)) {
				String name = null;
				String datatype = null;
				String scope = e.getAttribute("Scope");

				if (scope.equals("TypedConstant")) {
					name = getContent(e, "ConstantValue");
					datatype = getContent(e, "StringAttribute");
				} else if (scope.equals("LiteralConstant")) {
					name = getContent(e, "ConstantValue");
					datatype = getContent(e, "ConstantType");
				} else if (scope.equals("LocalVariable")) {
					NodeList nl = e.getElementsByTagName("Component");
					Element d = (Element) nl.item(0);
					name = d.getAttribute("Name");
					if (nl.getLength() > 1) {
						Element d2 = (Element) nl.item(1);
						name += "." + d2.getAttribute("Name");
					}
					datatype = "Localunknown";
				} else if (scope.equals("GlobalVariable")) {
					NodeList nl = e.getElementsByTagName("Component");
					Element d = (Element) nl.item(0);
					name = d.getAttribute("Name");
					if (nl.getLength() > 1) {
						nl = e.getElementsByTagName("Component");
						d = (Element) nl.item(1);
						name = name + "." + d.getAttribute("Name");
					}
					nl = e.getElementsByTagName("Address");
					d = (Element) nl.item(0);
					datatype = d.getAttribute("Type");

				} else if (scope.equals("PredefinedVariable")) {
					// not applicable
				}

				for (Variable var : vars) {
					if (var.getName().equals(name) && var.getDataType().toString().equals(datatype.toUpperCase())) {
						return var;
					} else if (var instanceof UserDefinedDataType) {
						UserDefinedDataType instance = (UserDefinedDataType) var;
						if (instance.getName().equals(name) && instance.getInstanceOf().equals(datatype)) {
							return var;
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Gets the content from an XML item.
	 *
	 * @param element the element where content is taken from
	 * @param tagName the tag name searched in the element
	 * @return the content of the element with the supplied tagname
	 */
	private static String getContent(Element element, String tagName) {
		NodeList elems = element.getElementsByTagName(tagName);
		return elems.item(0).getTextContent();
	}

}
