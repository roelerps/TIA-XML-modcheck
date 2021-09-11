package plcBlockNetworkModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.graph.*;

import plcBlockModel.CodeBlock;
import plcCodePartModel.Part;
import plcCodePartModel.PartBasic;
import plcCodePartModel.PartExtended;
import plcCodeUnitModel.Access;
import plcCodeUnitModel.Call;
import plcCodeUnitModel.CodeUnit;
import plcCodeUnitModel.Wire;
import plcCodeUnitModel.Wire.Connector;
import plcProgramModel.Variable;

/**
 * The Class Network.
 */
public class Network {

	/** The number of the network. */
	private int number;

	/** The codelines of the network. */
	private List<CodeUnit> codeline;

	/** The variables of the network. */
	private List<Variable> variables;

	/** The connections of the network. */
	private List<Connection> connections;

	/** The graph of the PLC code. */
	private DefaultDirectedGraph<String, DefaultEdge> plcCode;

	/**
	 * Instantiates a new network.
	 *
	 * @param nr          the number of the network
	 * @param code        the code of the network
	 * @param variables   the variables of the network
	 * @param connections the connections of the network
	 */
	public Network(int nr, List<CodeUnit> code, List<Variable> variables, List<Connection> connections) {
		this.number = nr;
		this.codeline = code;
		this.variables = variables;
		this.connections = connections;
		this.plcCode = createGraph(code);
	}

	/**
	 * Creates a graph of the network code
	 * 
	 * @param code the code of the network
	 * @return the graph of the network
	 */
	private DefaultDirectedGraph<String, DefaultEdge> createGraph(List<CodeUnit> code) {
		Network.filter(code, Part.class);
		List<Wire> wires = Network.filter(code, Wire.class);
		List<Access> accesses = Network.filter(code, Access.class);

		DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

		for (CodeUnit cu : code) {
			graph.addVertex(String.valueOf(cu.getId()));
		}
		for (Access a : accesses) {
			graph.addVertex(String.valueOf(a.getId()));
		}

		List<Wire> wiresClean = new ArrayList<>();
		List<Wire> wiresToRemove = new ArrayList<>();
		List<Wire> wiresToAdd = new ArrayList<>();

		for (Wire w : wires) {
			int nrOfConn = w.getNumberOfConnectors();

			if (nrOfConn < 2) {
				wiresToRemove.add(w);
			}

			if (nrOfConn >= 2) {
				wiresToRemove.add(w);
				if (!w.getConnector(0).getName().equals(w.getConnector(1).getName()))
					for (int i = 1; i < nrOfConn; i++) {
						Wire wire = new Wire(w.getId());
						List<Connector> connList = w.getConnectors();
						wire.addConnector(connList.get(0));
						wire.addConnector(connList.get(i));
						wiresToAdd.add(wire);
					}
			}
		}

		wiresClean.addAll(wires);
		wiresClean.removeAll(wiresToRemove);
		wiresClean.addAll(wiresToAdd);

		for (Wire w : wiresClean) {
			String v1 = String.valueOf(w.getConnector(0).getUid());
			String v2 = String.valueOf(w.getConnector(1).getUid());
			if (graph.containsVertex(v1) && graph.containsVertex(v2)) {
				graph.addEdge(v1, v2);
			}
		}
		return graph;
	}

	/**
	 * Get the number of the network
	 * 
	 * @return the number of the network
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Gets the variables of the network.
	 *
	 * @return the variables of the network
	 */
	public List<Variable> getVariables() {
		return variables;
	}

	/**
	 * Gets the connections of the network.
	 *
	 * @return the connections of the network
	 */
	public List<Connection> getConnections() {
		return connections;
	}

	/**
	 * Gets the codelines of the network.
	 *
	 * @return the codelines of the network
	 */
	public List<CodeUnit> getCodeline() {
		return codeline;
	}

	/**
	 * Gets the calls of the network.
	 *
	 * @return the calls of the network
	 */
	public List<Call> getCalls() {
		List<Call> calls = new ArrayList<>();
		for (CodeUnit cu : codeline) {
			if (Call.class.isInstance(cu)) {
				calls.add((Call) cu);
			}
		}
		return calls;
	}

	/**
	 * Gets the codeunit by using its ID
	 * 
	 * @param id of the codeunit
	 * @return the codeunit
	 */
	public CodeUnit getCodeUnitByID(int id) {
		for (CodeUnit cu : this.codeline) {
			if (cu.getId() == id) {
				return cu;
			}

		}
		return null;
	}

	/**
	 * Gets all codeunits by using an ID
	 * 
	 * @param id of the codeunit
	 * @return a list with codeunits with that ID
	 */
	public List<CodeUnit> getAllCodeUnitsByID(int id) {
		List<CodeUnit> allCodeUnits = new ArrayList<>();
		for (CodeUnit cu : this.codeline) {
			if (cu.getId() == id) {
				allCodeUnits.add(cu);
			}
		}
		return allCodeUnits;
	}

	/**
	 * Gets all codeunits by using the type of codeunit
	 * 
	 * @param cls is the class of the codeunit to get
	 * @return a list with codeunits of that type
	 */
	public List<CodeUnit> getCodeUnitsByClass(Class<?> cls) {
		List<CodeUnit> TypUnits = new ArrayList<>();
		for (CodeUnit cu : this.codeline) {
			if (cls.isInstance(cu)) {
				TypUnits.add(cu);
			}
		}
		return TypUnits;
	}

	/**
	 * Filter the given list, and create a new list that only contains the elements
	 * that are (subtypes) of the class c
	 * 
	 * @param listA The input list
	 * @param c     The class to filter for
	 * @return The filtered list
	 */
	private static <T> List<T> filter(List<?> listA, Class<T> c) {
		List<T> listB = new ArrayList<T>();
		for (Object a : listA) {
			if (c.isInstance(a)) {
				listB.add(c.cast(a));
			}
		}
		return listB;
	}

	/**
	 * Network to string.
	 *
	 * @param prefix the prefix to be printed
	 * @return the string
	 */
	public String toString(String prefix) {
		String outp = "";
		if (codeline.size() > 0) {
			outp = prefix + "Network number: " + this.number + "\r\n";
		}

		for (CodeUnit cu : codeline) {
			if (cu instanceof Call) {
				Call c = (Call) cu;
				CodeBlock bl = c.getBlock();
				outp += prefix + c.toString() + "\r\n";
				List<Variable> inputs = new ArrayList<>();
				inputs.addAll(bl.getInterface().getInput());
				inputs.addAll(bl.getInterface().getInOut());
				if (!inputs.isEmpty()) {
					outp += prefix + "Inputs: \r\n";
					for (Variable v : inputs) {
						String convar = null;
						for (Connection con : connections) {
							if (con.getDestination() == v && c.getId() == con.getDestinationUid()) {
								convar = con.getSource().getName() + " --> ";
							}
						}
						if (convar == null) {
							convar = "- --> ";
						}
						outp += prefix + "   " + convar + v.toString() + "\r\n";
					}
				}
				List<Variable> outputs = bl.getInterface().getOutput();
				if (!outputs.isEmpty()) {
					outp += prefix + "Outputs: \r\n";
					for (Variable v : outputs) {
						String convar = null;
						for (Connection con : connections) {
							if (con.getSource() == v && c.getId() == con.getSourceUid()) {
								convar = " --> " + con.getDestination().getName();
							}
						}
						if (convar == null) {
							convar = " --> -";
						}
						outp += prefix + "   " + v.toString() + convar + "\r\n";
					}
				}
				outp += "";
			}
		}
		return outp;
	}

	/**
	 * Converts the formula of the network to a string
	 * 
	 * @return the string
	 */
	public String formulaToString() {
		String outp = createNetworkStructure();
		outp = replaceVariables(outp);

		return outp;
	}

	/**
	 * Replace variable IDs in the string to variable names
	 * 
	 * @param outp the string with the PLC code of the network
	 * @return the string with variable names
	 */
	private String replaceVariables(String outp) {
		if (outp != null && !outp.isEmpty()) {
			Pattern p = Pattern.compile("cu-!?\\d+");
			Matcher m = p.matcher(outp);
			String cuNR = "";
			while (m.find()) {
				boolean negatedVar = false;
				if (m.group().charAt(3) == '!') {
					cuNR = m.group().substring(4);
					negatedVar = true;
				} else {
					cuNR = m.group().substring(3);
					negatedVar = false;
				}

				CodeUnit cu = getCodeUnitByID(Integer.parseInt(cuNR));
				if (cu instanceof Access) {
					if (negatedVar) {
						outp = outp.replaceAll(m.group(), ("!" + cu.toStringFormula()));
					} else {
						outp = outp.replaceAll(m.group(), cu.toStringFormula());
					}
				}
			}
		}
		return outp;
	}

	/**
	 * Create the network structure of the network
	 * 
	 * @return the sting with the plc code
	 */
	private String createNetworkStructure() {
		String logicFormula = "";

		// Create Reversed graph
		EdgeReversedGraph<String, DefaultEdge> revGraph = new EdgeReversedGraph<String, DefaultEdge>(this.plcCode);
		prepareStructure(revGraph);

		Set<String> vertices = revGraph.vertexSet();
		// Network without a Call
		if (!containsCall(vertices)) {
			for (String s : vertices) {
				// Find end points in graph
				CodeUnit cu = getCodeUnitByID(Integer.parseInt(s));

				boolean partOnEnd = false;
				if (cu instanceof Part) {
					Part p = (Part) cu;
					if (p.getName().equals("Move")) {
						if (revGraph.inDegreeOf(s) == 1) {
							partOnEnd = true;
						}
					}
					if (p.getName().equals("Add")) {
						partOnEnd = true;
					}
				}

				if (revGraph.inDegreeOf(s) == 0 || partOnEnd) {
					Access endvar = getLastConnectedVar(revGraph, s);
					if ((endvar != null && cu != null) || partOnEnd) {
						if (!partOnEnd) {
							logicFormula += "cu-" + endvar.getId() + " ";
						}

						List<String> outp = getAllConnectedVertices(revGraph, s);
						String idString = String.valueOf(endvar.getId());
						if (outp.contains(idString) && !partOnEnd) {
							outp.remove(idString);
						}
						if (cu instanceof PartExtended && !((PartExtended) cu).getNegated().isEmpty()) {
							PartExtended cuNegated = (PartExtended) cu;
							List<String> negated = cuNegated.getNegated();
							List<String> newNegated = new ArrayList<>();
							for (String s2 : outp) {
								String conName = getConnectorNameTo(Integer.parseInt(s2), Integer.parseInt(s));
								if (negated.contains(conName) || negated.contains(s2)) {
									newNegated.add(s2);
								}
							}
							cuNegated.setNegated(newNegated);
						}
						if (outp.size() > 0) {
							logicFormula += cu.toStringFormula(outp) + " \r\n";
						} else {
							logicFormula += cu.toStringFormula() + " \r\n";
						}
						logicFormula = createNetworkStructureRecursive(revGraph, logicFormula, outp, s);
					}
				}
			}
		}
		// Network with a call
		else {
			logicFormula = "";

			for (String s : vertices) {
				CodeUnit cuCall = getCodeUnitByID(Integer.parseInt(s));
				if (cuCall instanceof Call) {
					getAllConnectedTargetVertices(revGraph, s);
					List<String> outp = getAllConnectedSourceVertices(revGraph, s);
					List<String> conn = getAllConnectedVertices(revGraph, s);
					Call c = (Call) cuCall;
					logicFormula += c.toStringFormula();
					logicFormula += "Inputs:\r\n";
					// All inputs of the called blocked
					Set<DefaultEdge> blockInp = revGraph.outgoingEdgesOf(s);
					for (DefaultEdge e : blockInp) {

						int target = Integer.parseInt(revGraph.getEdgeTarget(e));
						int source = Integer.parseInt(revGraph.getEdgeSource(e));
						CodeUnit cuFrom = getCodeUnitByID(target);
						cuFrom.toStringFormula();
						String to = getConnectorNameTo(target, source);

						if (cuFrom instanceof Access) {
							logicFormula += "cu-" + target;
						} else if (cuFrom instanceof Call) {
							logicFormula += getConnectorNameFrom(target, source);
						} else if (cuFrom instanceof Part) {

							String s2 = String.valueOf(target);
							conn = getAllConnectedVertices(revGraph, s2);

							if (cuFrom instanceof PartExtended && !((PartExtended) cuFrom).getNegated().isEmpty()) {
								PartExtended cuNegated = (PartExtended) cuFrom;
								List<String> negated = cuNegated.getNegated();
								List<String> newNegated = new ArrayList<>();
								for (String s3 : conn) {
									String conName = getConnectorNameTo(Integer.parseInt(s3), Integer.parseInt(s2));
									if (negated.contains(conName) || negated.contains(s3)) {
										newNegated.add(s3);
									}
								}
								cuNegated.setNegated(newNegated);
							}
							String subformula = cuFrom.toStringFormula(conn);
							subformula = createNetworkStructureRecursive(revGraph, subformula, conn, s2);
							logicFormula += subformula;
						}
						logicFormula += " ==> ";
						logicFormula += to + "\r\n";
					}
					// All outputs of the called blocked
					logicFormula += "Outputs:\r\n";
					Set<DefaultEdge> blockOutp = revGraph.incomingEdgesOf(s);
					for (DefaultEdge e : blockOutp) {

						int target = Integer.parseInt(revGraph.getEdgeTarget(e));
						int source = Integer.parseInt(revGraph.getEdgeSource(e));
						CodeUnit cuTo = getCodeUnitByID(source);
						cuTo.toStringFormula();
						String from = getConnectorNameFrom(target, source);
						logicFormula += from;
						logicFormula += " ==> ";

						if (cuTo instanceof Access) {
							logicFormula += "cu-" + source + "\r\n";
						} else if (cuTo instanceof Call) {
							logicFormula += getConnectorNameTo(target, source) + "\r\n";
						} else if (cuTo instanceof Part) {
							String s2 = String.valueOf(source);
							conn = getAllConnectedVertices(revGraph, s2);
							if (conn.contains(s)) {
								conn.remove(s);
							}

							if (cuTo instanceof PartExtended && !((PartExtended) cuTo).getNegated().isEmpty()) {
								PartExtended cuNegated = (PartExtended) cuTo;
								List<String> negated = cuNegated.getNegated();
								List<String> newNegated = new ArrayList<>();

								for (String s3 : outp) {
									String conName = getConnectorNameTo(Integer.parseInt(s3), Integer.parseInt(s));
									if (negated.contains(conName) || negated.contains(s3)) {
										newNegated.add(s3);
									}
								}
								cuNegated.setNegated(newNegated);
							}
							String subformula = ((Part) cuTo).toStringFormulaCall(conn);
							subformula = createNetworkStructureRecursive(revGraph, subformula, conn, s2);
							logicFormula += subformula;
						}
					}
				}
			}
			logicFormula += "\r\n";
			logicFormula = logicFormula.replace("\r\n\r\n", "\r\n");
		}
		return logicFormula;
	}

	/**
	 * Prepares the network structure in the case of some specials
	 * 
	 * @param revGraph graph of the network
	 */
	private void prepareStructure(EdgeReversedGraph<String, DefaultEdge> revGraph) {
		Network.filter(codeline, Wire.class);

		Set<String> vertices = revGraph.vertexSet();
		for (String v : vertices) {
			CodeUnit cu = getCodeUnitByID(Integer.parseInt(v));
			if (cu instanceof Part) {
				Part p = (Part) cu;
				boolean midCoil = p.getName().equals("Coil") && revGraph.incomingEdgesOf(v).size() > 0;
				if (p.getName().equals("Move") || p.getName().equals("Add") || midCoil) {

					boolean negate = false;
					if (p instanceof PartExtended) {
						List<String> negated = ((PartExtended) p).getNegated();
						if (negated.contains("en")) {
							negate = true;
						}
					}
					if (p instanceof PartBasic) {
						if (((PartBasic) p).getNegated()) {
							negate = true;
						}
					}

					List<String> targets = getAllConnectedTargetVertices(revGraph, v);
					String source = "";
					for (String t : targets) {
						String conName = getConnectorNameTo(Integer.parseInt(t), Integer.parseInt(v));
						if (conName != null
								&& ((conName.equals("en") && !midCoil) || (conName.equals("in") && midCoil))) {
							source = t;
						}
					}

					List<String> sources = getAllConnectedSourceVertices(revGraph, v);
					for (String s : sources) {
						String conName = getConnectorNameFrom(Integer.parseInt(v), Integer.parseInt(s));
						if (negate) {
							String negateInput = getConnectorNameTo(Integer.parseInt(v), Integer.parseInt(s));
							CodeUnit negatingCU = getCodeUnitByID(Integer.parseInt(s));
							if (negatingCU instanceof PartExtended) {
								PartExtended negatingPart = (PartExtended) negatingCU;
								List<String> negates = negatingPart.getNegated();
								negates.add(negateInput);
								negatingPart.setNegated(negates);
							}
							if (negatingCU instanceof PartBasic) {
								PartBasic negatingPart = (PartBasic) negatingCU;
								negatingPart.setNegated(true);
							}
						}
						if (conName != null
								&& ((conName.equals("eno") && !midCoil) || (conName.equals("out") && midCoil))) {
							DefaultEdge eToRemove = revGraph.getEdge(s, v);
							revGraph.removeEdge(eToRemove);
							if (source != "") {
								revGraph.addEdge(s, source);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Recursive part to create the network structure
	 * 
	 * @param revGraph      the graph
	 * @param logicFormula  the string with the plc code
	 * @param connVertices  the next connected vertices
	 * @param currentVertex the current vertex
	 * @return the string with the plc code
	 */
	private String createNetworkStructureRecursive(EdgeReversedGraph<String, DefaultEdge> revGraph, String logicFormula,
			List<String> connVertices, String currentVertex) {

		CodeUnit cuCurrent = getCodeUnitByID(Integer.parseInt(currentVertex));

		if (cuCurrent instanceof PartExtended && !((PartExtended) cuCurrent).getNegated().isEmpty()) {
			PartExtended cuNegated = (PartExtended) cuCurrent;
			List<String> negated = cuNegated.getNegated();
			List<String> newNegated = new ArrayList<>();
			for (String s3 : connVertices) {
				String conName = getConnectorNameTo(Integer.parseInt(s3), Integer.parseInt(currentVertex));
				if (negated.contains(conName) || negated.contains(s3)) {
					newNegated.add(s3);
				}
			}
			cuNegated.setNegated(newNegated);
		}

		for (String s2 : connVertices) {
			CodeUnit cuNext = getCodeUnitByID(Integer.parseInt(s2));
			if (cuNext instanceof Part && cuCurrent instanceof Part) {
				List<String> connVertices2 = getAllConnectedVertices(revGraph, s2);
				if (cuNext instanceof PartExtended && !((PartExtended) cuNext).getNegated().isEmpty()) {
					PartExtended cuNegated = (PartExtended) cuNext;
					List<String> negated = cuNegated.getNegated();
					List<String> newNegated = new ArrayList<>();

					for (String s3 : connVertices2) {
						String conName = getConnectorNameTo(Integer.parseInt(s3), Integer.parseInt(s2));
						if (negated.contains(conName) || negated.contains(s3)) {
							newNegated.add(s3);
						}
					}
					cuNegated.setNegated(newNegated);
				}

				if (logicFormula.contains("cu-" + s2) && cuNext.toStringFormula(connVertices2) != null) {
					logicFormula = logicFormula.replace("cu-" + s2, cuNext.toStringFormula(connVertices2));
				}
				logicFormula = createNetworkStructureRecursive(revGraph, logicFormula, connVertices2, s2);
			}
		}
		return logicFormula;
	}

	/**
	 * Get the name of the connected destination vertex by using the wire
	 * 
	 * @param s source vertex id
	 * @param d destination vertex id
	 * @return the name of the connected vertex
	 */
	public String getConnectorNameTo(int s, int d) {
		List<Wire> wires = Network.filter(codeline, Wire.class);

		for (Wire w : wires) {
			Connector connector1 = w.getConnector(0);
			Connector connector2 = w.getConnector(1);
			if (connector1.getUid() == s && connector2.getUid() == d) {
				return connector2.getName();
			}
		}
		return null;
	}

	/**
	 * Get the name of the connected source vertex by using the wire
	 * 
	 * @param s source vertex id
	 * @param d destination vertex id
	 * @return the name of the connected vertex
	 */
	private String getConnectorNameFrom(int s, int d) {
		List<Wire> wires = Network.filter(codeline, Wire.class);

		for (Wire w : wires) {
			List<Connector> connectors = new ArrayList<>();
			int nrCon = w.getConnectors().size();
			for (int i = 0; i < nrCon; i++) {
				connectors.add(w.getConnector(i));
			}

			for (Connector c : connectors) {
				if (c.getUid() == s) {
					for (Connector c2 : connectors) {
						if (c2.getUid() == d) {
							return c.getName();
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get all connected vertices
	 * 
	 * @param graph the graph of the network
	 * @param s     name of the vertex to search from
	 * @return all connected vertices
	 */
	private List<String> getAllConnectedVertices(EdgeReversedGraph<String, DefaultEdge> graph, String s) {
		List<String> connVertices = new ArrayList<>();
		Set<DefaultEdge> listE = null;

		CodeUnit cu = getCodeUnitByID(Integer.parseInt(s));
		if (cu instanceof Part && (((Part) cu).getName().equals("Move") || ((Part) cu).getName().equals("Add"))) {
			listE = graph.outgoingEdgesOf(s);
			for (DefaultEdge e : listE) {
				String target = graph.getEdgeTarget(e);
				CodeUnit cuTarget = getCodeUnitByID(Integer.parseInt(target));
				connVertices.add(String.valueOf(cuTarget.getId()));
			}
			listE = graph.incomingEdgesOf(s);
			for (DefaultEdge e : listE) {
				String source = graph.getEdgeSource(e);
				CodeUnit cuSource = getCodeUnitByID(Integer.parseInt(source));
				if (cuSource instanceof Access) {
					connVertices.add(String.valueOf(cuSource.getId()));
				}
			}
		} else {
			listE = graph.outgoingEdgesOf(s);
			for (DefaultEdge e : listE) {
				String target = graph.getEdgeTarget(e);
				CodeUnit cuTarget = getCodeUnitByID(Integer.parseInt(target));
				connVertices.add(String.valueOf(cuTarget.getId()));
			}
		}
		return connVertices;
	}

	/**
	 * Get all connected source vertices
	 * 
	 * @param graph the graph of the network
	 * @param s     name of the vertex to search from
	 * @return all connected source vertices
	 */
	private List<String> getAllConnectedSourceVertices(EdgeReversedGraph<String, DefaultEdge> graph, String s) {
		List<String> connVertices = new ArrayList<>();
		Set<DefaultEdge> listE = null;

		getCodeUnitByID(Integer.parseInt(s));
		listE = graph.incomingEdgesOf(s);
		for (DefaultEdge e : listE) {
			String source = graph.getEdgeSource(e);
			CodeUnit cuSource = getCodeUnitByID(Integer.parseInt(source));
			connVertices.add(String.valueOf(cuSource.getId()));
		}

		return connVertices;
	}

	/**
	 * Get all connected target vertices
	 * 
	 * @param graph the graph of the network
	 * @param s     name of the vertex to search from
	 * @return all connected target vertices
	 */
	private List<String> getAllConnectedTargetVertices(EdgeReversedGraph<String, DefaultEdge> graph, String s) {
		List<String> connVertices = new ArrayList<>();
		Set<DefaultEdge> listE = null;

		getCodeUnitByID(Integer.parseInt(s));
		listE = graph.outgoingEdgesOf(s);
		for (DefaultEdge e : listE) {
			String target = graph.getEdgeTarget(e);
			CodeUnit cuTarget = getCodeUnitByID(Integer.parseInt(target));
			connVertices.add(String.valueOf(cuTarget.getId()));
		}

		return connVertices;
	}

	/**
	 * Checks if one of the vertices is a call
	 * 
	 * @param vertices to be checked
	 * @return true when one of the vertices contains a call
	 */
	private boolean containsCall(Set<String> vertices) {
		for (String s : vertices) {
			CodeUnit cu = getCodeUnitByID(Integer.parseInt(s));
			if (cu instanceof Call) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the last connected variable using the graph
	 * 
	 * @param graph the graph of the network
	 * @param s     the vertices to get the last connected variable from
	 * @return the last connected variable
	 */
	private Access getLastConnectedVar(EdgeReversedGraph<String, DefaultEdge> graph, String s) {
		CodeUnit cu = getCodeUnitByID(Integer.parseInt(s));
		Access cuReturn = null;
		int id = 0;

		if (cu instanceof Part) {
			for (DefaultEdge e : graph.edgesOf(s)) {
				String target = graph.getEdgeTarget(e);
				CodeUnit cutarget = getCodeUnitByID(Integer.parseInt(target));
				if (cutarget instanceof Access) {
					if (Integer.parseInt(target) > id) {
						cuReturn = (Access) cutarget;
						id = Integer.parseInt(target);
					}
				}
			}
			if (id > 0) {
				return cuReturn;
			}
		}
		return cuReturn;
	}

}
