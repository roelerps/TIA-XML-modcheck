package factory;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import plcBlockModel.CodeBlock;
import plcCodePartModel.Part;
import plcCodePartModel.PartBasic;
import plcCodePartModel.PartExtended;
import plcCodePartModel.PartInstance;
import plcCodeUnitModel.Access;
import plcCodeUnitModel.CallFBMultiple;
import plcCodeUnitModel.CallFBSingle;
import plcCodeUnitModel.CallFC;
import plcCodeUnitModel.CodeUnit;
import plcCodeUnitModel.Wire;
import plcCodeUnitModel.Wire.ConnectorType;
import plcProgramModel.PlcProgram;

/**
 * A factory for creating CodeUnit objects.
 */
public abstract class CodeUnitFactory {

	/** The Constant NETWORK_VARIABLE_SCOPE. */
	protected static final String NETWORK_VARIABLE_SCOPE = "Scope";

	/** The Constant NETWORK_VARIABLE_UID. */
	protected static final String NETWORK_VARIABLE_UID = "UId";

	/** The Constant VARTYPE_TYPEDCONSTANT. */
	protected static final String VARTYPE_TYPEDCONSTANT = "TypedConstant";

	/** The Constant VARTYPE_LITERALCONSTANT. */
	protected static final String VARTYPE_LITERALCONSTANT = "LiteralConstant";

	/** The Constant VARTYPE_LOCAL. */
	protected static final String VARTYPE_LOCAL = "LocalVariable";

	/** The Constant VARTYPE_GLOBAL. */
	protected static final String VARTYPE_GLOBAL = "GlobalVariable";

	/** The Constant VARTYPE_GLOBALCONS. */
	protected static final String VARTYPE_GLOBALCONS = "GlobalConstant";

	/** The Constant VARTYPE_PREDEFINED. */
	protected static final String VARTYPE_PREDEFINED = "PredefinedVariable";

	/** The Constant VARNAME_LOCGLOB_ROOT. */
	protected static final String VARNAME_LOCGLOB_ROOT = "Component";

	/** The Constant VARNAME_CONSTANT. */
	protected static final String VARNAME_CONSTANT = "ConstantValue";

	/** The Constant VARNAME_LOCGLOB. */
	protected static final String VARNAME_LOCGLOB = "Name";

	/** The Constant VARNAME_LOCGLOB_DOT. */
	protected static final String VARNAME_LOCGLOB_DOT = ".";

	/** The Constant VARDATATYPE_TYPEDCONSTANT. */
	protected static final String VARDATATYPE_TYPEDCONSTANT = "StringAttribute";

	/** The Constant VARDATATYPE_LITERALCONSTANT. */
	protected static final String VARDATATYPE_LITERALCONSTANT = "ConstantType";

	/** The Constant VARDATATYPE_LOCAL. */
	protected static final String VARDATATYPE_LOCAL = "Local";

	/** The Constant VARDATATYPE_GLOBAL_ROOT. */
	protected static final String VARDATATYPE_GLOBAL_ROOT = "Address";

	/** The Constant VARDATATYPE_GLOBAL. */
	protected static final String VARDATATYPE_GLOBAL = "Type";

	/**
	 * Instantiates a new code unit factory.
	 */
	private CodeUnitFactory() {

	}

	/**
	 * Creates a new List of CodeUnits for one network.
	 *
	 * @param tempProgram the PLC program (only containing PLC blocks)
	 * @param nw          part of the XML file containing one PLC block network
	 * @return the list of code units for one network
	 */
	static List<CodeUnit> createCodeUnits(PlcProgram tempProgram, Element nw) {
		List<CodeUnit> code = new ArrayList<>();

		NodeList nwcode = nw.getElementsByTagName("NetworkSource");
		for (int i = 0; i < nwcode.getLength(); i++) {
			Element codeunit = (Element) nwcode.item(i);
			// Get calls
			NodeList calls = codeunit.getElementsByTagName("CallInfo");
			if (calls.getLength() > 0) {
				code.addAll(createCalls(tempProgram, codeunit, calls));
			}

			// Get parts
			NodeList parts = codeunit.getElementsByTagName("Part");
			if (parts.getLength() > 0) {
				code.addAll(createParts(parts));
			}

			// Get wires
			NodeList wires = codeunit.getElementsByTagName("Wire");
			if (wires.getLength() > 0) {
				code.addAll(createWires(wires));
			}

			// Get variables
			NodeList variables = codeunit.getElementsByTagName("Access");
			if (variables.getLength() > 0) {
				code.addAll(createVariables(variables));
			}
		}
		return code;
	}

	/**
	 * Creates a new List of Calls for one network.
	 *
	 * @param tempProgram the PLC program (only containing PLC blocks)
	 * @param codeunit    part of the XML file containing one PLC block network
	 * @param calls       the calls for the PLC block network
	 * @return the list of code units for one network
	 */
	private static List<CodeUnit> createCalls(PlcProgram tempProgram, Element codeunit, NodeList calls) {
		List<CodeUnit> codecalls = new ArrayList<>();
		for (int i = 0; i < calls.getLength(); i++) {
			Element c = (Element) calls.item(i);
			String callname = c.getAttribute("Name");
			String blocktype = c.getAttribute("BlockType");
			CodeBlock calledblock = (CodeBlock) tempProgram.getBlockByName(callname);

			Element call = (Element) codeunit.getElementsByTagName("Call").item(i);
			int uid = Integer.parseInt(call.getAttribute("UId"));
			if (blocktype.equals("FB")) {
				CodeUnit un = null;
				Element instance = (Element) c.getElementsByTagName("Instance").item(0);
				String instancelocation = instance.getAttribute("Scope");
				Element comp = (Element) instance.getElementsByTagName("Component").item(0);
				String instancename = comp.getAttribute("Name");
				if (instancelocation.equals("GlobalVariable")) {
					Element singleinst = (Element) instance.getElementsByTagName("Address").item(0);
					String area = singleinst.getAttribute("Area");
					int blocknr = Integer.parseInt(singleinst.getAttribute("BlockNumber"));
					String type = singleinst.getAttribute("Type");
					un = new CallFBSingle(uid, calledblock, instancename, instancelocation, area, blocknr, type);
				} else if (instancelocation.equals("LocalVariable")) {
					un = new CallFBMultiple(uid, calledblock, instancename, instancelocation);
				}
				codecalls.add(un);
			} else if (blocktype.equals("FC")) {
				CodeUnit un = new CallFC(uid, calledblock);
				codecalls.add(un);
			}
		}
		return codecalls;
	}

	/**
	 * Creates a new list with CodeUnit objects only containing Parts.
	 *
	 * @param parts a NodeList with all parts
	 * @return the list with parts
	 */
	private static List<CodeUnit> createParts(NodeList parts) {
		List<CodeUnit> partlist = new ArrayList<>();
		Part part = null;

		for (int i = 0; i < parts.getLength(); i++) {
			Element p = (Element) parts.item(i);
			String parttype = p.getAttribute("Name");
			switch (parttype) {
			case ("Coil"):
			case ("SCoil"):
			case ("RCoil"):
			case ("Contact"):
			case ("ReturnTrue"):
			case ("Sr"):
				part = createPartBasic(p);
				break;

			case ("A"):
			case ("O"):
			case ("Eq"):
			case ("Ne"):
			case ("Lt"):
			case ("Gt"):
			case ("Move"):
			case ("Add"):
				part = createPartExtended(p);
				break;

			case ("TON"):
				part = createPartInstance(p);
				break;
			}
			partlist.add(part);
		}
		return partlist;
	}

	/**
	 * Creates a new PartBasic object.
	 *
	 * @param p the Element containing information about the part
	 * @return the created PartBasic
	 */
	private static Part createPartBasic(Element p) {
		String name = p.getAttribute("Name");
		int uid = Integer.parseInt(p.getAttribute("UId"));
		Boolean negated = false;
		if (p.hasChildNodes()) {
			negated = p.getChildNodes().item(0).getNodeName().equals("Negated");
		}

		return new PartBasic(name, uid, negated);
	}

	/**
	 * Creates a new PartExtended object.
	 *
	 * @param p the Element containing information about the part
	 * @return the created PartExtended
	 */
	private static Part createPartExtended(Element p) {
		Part part = null;
		String name = p.getAttribute("Name");
		int uid = Integer.parseInt(p.getAttribute("UId"));
		List<String> negated = new ArrayList<>();
		String temval1Name, temval1Type, temval1Value, temval2Name, temval2Type, temval2Value;
		temval1Name = temval1Type = temval1Value = temval2Name = temval2Type = temval2Value = "";
		NodeList nl = p.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeName().equals("TemplateValue")) {
				if (temval1Name.equals("")) {
					temval1Name = nl.item(i).getAttributes().getNamedItem("Name").getNodeValue();
					temval1Type = nl.item(i).getAttributes().getNamedItem("Type").getNodeValue();
					temval1Value = nl.item(i).getTextContent();
				} else {
					temval2Name = nl.item(i).getAttributes().getNamedItem("Name").getNodeValue();
					temval2Type = nl.item(i).getAttributes().getNamedItem("Type").getNodeValue();
					temval2Value = nl.item(i).getTextContent();
				}
			} else if (nl.item(i).getNodeName().equals("Negated")) {
				negated.add(nl.item(i).getAttributes().getNamedItem("Name").getNodeValue());
			}
			if (i == nl.getLength() - 1) {
				if (i > 0) {
					part = new PartExtended(name, uid, temval1Name, temval1Type, temval1Value, temval2Name, temval2Type,
							temval2Value, negated);
				} else
					part = new PartExtended(name, uid, temval1Name, temval1Type, temval1Value, negated);
			}
		}
		return part;
	}

	/**
	 * Creates a new PartInstance object.
	 *
	 * @param p the Element containing information about the part
	 * @return the created PartInstance
	 */
	private static Part createPartInstance(Element p) {
		String name = p.getAttribute("Name");
		int uid = Integer.parseInt(p.getAttribute("UId"));
		String version = p.getAttribute("Version");
		Element i = (Element) p.getFirstChild();
		String instName = i.getFirstChild().getAttributes().getNamedItem("Name").getNodeValue();
		String instUId = i.getAttribute("UId");
		String instScope = i.getAttribute("Scope");

		return new PartInstance(name, uid, version, instName, instUId, instScope);
	}

	/**
	 * Creates a new list with all Wires.
	 *
	 * @param wires the NodeList containing all information about the wires
	 * @return the created list with Wires
	 */
	private static List<CodeUnit> createWires(NodeList wires) {
		List<CodeUnit> wirelist = new ArrayList<>();
		for (int i = 0; i < wires.getLength(); i++) {
			int uid = Integer.parseInt(wires.item(i).getAttributes().getNamedItem("UId").getNodeValue());
			Wire wire = new Wire(uid);
			NodeList cons = wires.item(i).getChildNodes();
			String checkENO = "";
			if (cons.item(0).getAttributes().getNamedItem("Name") != null) {
				checkENO = cons.item(0).getAttributes().getNamedItem("Name").getNodeValue();
			}
			if (!checkENO.equals("eno") || true) {
				for (int j = 0; j < cons.getLength(); j++) {
					Node con = cons.item(j);
					ConnectorType conType = null;
					if (con.getNodeName().equals("OpenCon")) {
						conType = ConnectorType.OpenCon;
					} else if (con.getNodeName().equals("NameCon")) {
						conType = ConnectorType.NameCon;
					} else if (con.getNodeName().equals("IdentCon")) {
						conType = ConnectorType.IdentCon;
					}

					int conUId = Integer.parseInt(con.getAttributes().getNamedItem("UId").getNodeValue());
					String conName = "";
					if (con.getAttributes().getNamedItem("Name") != null) {
						conName = con.getAttributes().getNamedItem("Name").getNodeValue();
					}
					wire.addConnector(conType, conUId, conName);

				}
				wirelist.add(wire);
			}
		}
		return wirelist;
	}

	/**
	 * Creates a new list with all Variables.
	 *
	 * @param variables the NodeList containing all information about the variables
	 * @return the created list with Variables
	 */
	private static List<CodeUnit> createVariables(NodeList variables) {
		List<CodeUnit> varlist = new ArrayList<>();
		for (int i = 0; i < variables.getLength(); i++) {
			String name = null;
			String datatype = null;

			Element e = (Element) variables.item(i);
			String scope = e.getAttribute(NETWORK_VARIABLE_SCOPE);
			String suid = e.getAttribute(NETWORK_VARIABLE_UID);
			int uid = 1;
			if (!suid.isEmpty()) {
				uid = Integer.parseInt(suid);
			}
			if (scope.equals(VARTYPE_TYPEDCONSTANT)) {
				name = e.getElementsByTagName(VARNAME_CONSTANT).item(0).getTextContent();
				datatype = e.getElementsByTagName(VARDATATYPE_TYPEDCONSTANT).item(0).getTextContent();
			} else if (scope.equals(VARTYPE_LITERALCONSTANT)) {
				name = e.getElementsByTagName(VARNAME_CONSTANT).item(0).getTextContent();
				datatype = e.getElementsByTagName(VARDATATYPE_LITERALCONSTANT).item(0).getTextContent();
			} else if (scope.equals(VARTYPE_LOCAL)) {
				NodeList nl = e.getElementsByTagName(VARNAME_LOCGLOB_ROOT);
				Element d = (Element) nl.item(0);
				name = d.getAttribute(VARNAME_LOCGLOB);
				if (d.getAttribute("AccessModifier").equals("Array")) {
					if (d.getElementsByTagName("Access").item(0).getAttributes().getNamedItem("Scope").getTextContent()
							.equals("LiteralConstant")) {
						name += "[" + d.getElementsByTagName("ConstantValue").item(0).getTextContent() + "]";
					}
					if (d.getElementsByTagName("Access").item(0).getAttributes().getNamedItem("Scope").getTextContent()
							.equals("LocalVariable")) {
						name += "[" + d.getElementsByTagName("Component").item(0).getAttributes().getNamedItem("Name")
								.getTextContent() + "]";
					}
				} else if (nl.getLength() > 1) {
					Element d2 = (Element) nl.item(1);
					name += VARNAME_LOCGLOB_DOT + d2.getAttribute(VARNAME_LOCGLOB);
				}
				datatype = VARDATATYPE_LOCAL;
			} else if (scope.equals(VARTYPE_GLOBAL)) {
				NodeList nl = e.getElementsByTagName(VARNAME_LOCGLOB_ROOT);
				Element d = (Element) nl.item(0);
				name = d.getAttribute(VARNAME_LOCGLOB);
				if (nl.getLength() > 1) {
					nl = e.getElementsByTagName(VARNAME_LOCGLOB_ROOT);
					d = (Element) nl.item(1);
					name = name + VARNAME_LOCGLOB_DOT + d.getAttribute(VARNAME_LOCGLOB);
				}
				nl = e.getElementsByTagName(VARDATATYPE_GLOBAL_ROOT);
				d = (Element) nl.item(0);
				datatype = d.getAttribute(VARDATATYPE_GLOBAL);

			} else if (scope.equals(VARTYPE_GLOBALCONS) || scope.equals("LocalConstant")) {
				NodeList nl = e.getElementsByTagName("Constant");
				Element d = (Element) nl.item(0);
				name = d.getAttribute("Name");
				datatype = d.getFirstChild().getTextContent();
			} else if (scope.equals(VARTYPE_PREDEFINED)) {
				// not applicable
			}
			if (name != null && datatype != null) {
				Access ac = new Access(uid, name, VariableFactory.checkDatatypeVariable(datatype));
				varlist.add(ac);
			}
		}
		return varlist;
	}
}
