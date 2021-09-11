package storageModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import factory.CallStructureFactory;
import factory.InterfaceFactory;
import factory.NetworkFactory;
import factory.PlcBlockFactory;
import factory.PlcProgramFactory;
import factory.VariableFactory;
import plcBlockModel.CodeBlock;
import plcBlockModel.Interface;
import plcBlockModel.PlcBlock;
import plcBlockNetworkModel.Network;
import plcProgramModel.CallStructure;
import plcProgramModel.PlcProgram;
import plcProgramModel.Variable;

/**
 * The Class XmlReader.
 */
public class XmlReader implements PlcProgramReader {

	/** The Constant PCE. */
	protected static final String PCE = "Parser Configuration Exception";

	/** The Constant XML. */
	protected static final String XML = "xml";

	/** The Constant BLOCK_NAME. */
	protected static final String BLOCK_NAME = "Name";

	/** The Constant BLOCK_NR. */
	protected static final String BLOCK_NR = "Number";

	/** The Constant BLOCK_LANG. */
	protected static final String BLOCK_LANG = "ProgrammingLanguage";

	/** The Constant PROGLANG_FBD. */
	protected static final String PROGLANG_FBD = "FBD";

	/** The Constant PROGLANG_LAD. */
	protected static final String PROGLANG_LAD = "LAD";

	/** The Constant NETWORK. */
	protected static final String NETWORK = "SW.Blocks.CompileUnit";

	/** The Constant NETWORK_VARIABLE. */
	protected static final String NETWORK_VARIABLE = "Access";

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
	 * Load program.
	 *
	 * @param foldername the foldername with the XML files of the PLC program
	 * @return the PLC program
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PlcProgram loadProgram(String foldername) throws IOException {
		// First create all blocks from XML files
		List<PlcBlock> blocks = createPlcBlocks(foldername);

		// Create PLC program with blocks and project name
		String projName = Paths.get(foldername).getParent().getFileName().toString();
		PlcProgram tempProgram = PlcProgramFactory.createPlcProgram(projName, blocks);

		// Set the content of the code blocks from the networks in the XML files
		setContentCodeBlocks(tempProgram);

		// Create callstructure and add it to PLC program
		CallStructure cs = CallStructureFactory.createCallStructure(tempProgram);
		tempProgram = PlcProgramFactory.addCallstructure(tempProgram, cs);

		// Create variables and add to PLC program
		List<Variable> variables = createVariables(tempProgram);
		PlcProgram program = PlcProgramFactory.addVariables(tempProgram, variables);

		return program;
	}

	/**
	 * Creates the PLC blocks.
	 *
	 * @param foldername the foldername with the XML files of the PLC program
	 * @return the list with PLC blocks
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private List<PlcBlock> createPlcBlocks(String foldername) throws IOException {
		List<PlcBlock> blocks = new ArrayList<>();
		PlcBlock b = null;

		DocumentBuilder builder;
		Document document = null;

		for (String s : getXmlFiles(foldername)) {

			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				document = builder.parse(new File(s));
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			if (document != null) {
				Element doc = document.getDocumentElement();
				removeEmptyNodes(doc);
				b = createPlcBlock(doc, s);
				if (b != null) {
					blocks.add(b);
				}
			}
		}
		return blocks;
	}

	/**
	 * Creates a PLC block.
	 *
	 * @param doc      the XML file
	 * @param filename the filename of the XML file
	 * @return the PLC block
	 */
	private PlcBlock createPlcBlock(Element doc, String filename) {
		String name = getContent(doc, BLOCK_NAME);
		int nr = Integer.parseInt(getContent(doc, BLOCK_NR));
		String file = filename;
		String lang = getContent(doc, BLOCK_LANG);
		Interface intface = InterfaceFactory.createInterface(doc);

		NodeList nl = doc.getChildNodes();
		String type = nl.item(2).getNodeName();

		return PlcBlockFactory.createPlcBlock(type, name, nr, file, lang, intface);
	}

	/**
	 * Sets the content of code blocks.
	 *
	 * @param program the PLC program to add the content code blocks to
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void setContentCodeBlocks(PlcProgram program) throws IOException {
		DocumentBuilder builder;
		Document document = null;

		List<PlcBlock> blocks = program.getTypedBlocks(CodeBlock.class);
		for (PlcBlock block : blocks) {
			String file = block.getXmlFile();
			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				document = builder.parse(new File(file));
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			if (document != null) {
				Element doc = document.getDocumentElement();
				removeEmptyNodes(doc);
				CodeBlock codeblock = (CodeBlock) block;
				if (codeblock.getProgLang().equals(PROGLANG_FBD)) {
					codeblock.setCode(createNetworks(doc, program));
				}
			}
		}
	}

	/**
	 * Creates the variables.
	 *
	 * @param program the PLC program
	 * @return the list with variables
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private List<Variable> createVariables(PlcProgram program) throws IOException {
		DocumentBuilder builder;
		Document document = null;

		List<Variable> variables = new ArrayList<>();
		List<PlcBlock> blocks = program.getTypedBlocks(CodeBlock.class);
		for (PlcBlock block : blocks) {
			String file = block.getXmlFile();
			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				document = builder.parse(new File(file));
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			if (document != null) {
				Element doc = document.getDocumentElement();
				removeEmptyNodes(doc);
				variables.addAll(createVariablesFromXMLElement(doc));
			}
		}
		return variables;
	}

	/**
	 * Creates the variables from an XML element.
	 *
	 * @param doc the XML file
	 * @return the list with variables
	 */
	private List<Variable> createVariablesFromXMLElement(Element doc) {
		List<Variable> variables = new ArrayList<>();
		NodeList var = doc.getElementsByTagName(NETWORK_VARIABLE);
		for (int i = 0; i < var.getLength(); i++) {
			String name = null;
			String datatype = null;

			Element e = (Element) var.item(i);
			String scope = e.getAttribute(NETWORK_VARIABLE_SCOPE);
			String suid = e.getAttribute(NETWORK_VARIABLE_UID);
			int uid = 1;
			if (!suid.isEmpty()) {
				uid = Integer.parseInt(suid);
			}

			if (scope.equals(VARTYPE_TYPEDCONSTANT)) {
				name = getContent(e, VARNAME_CONSTANT);
				datatype = getContent(e, VARDATATYPE_TYPEDCONSTANT);
			} else if (scope.equals(VARTYPE_LITERALCONSTANT)) {
				name = getContent(e, VARNAME_CONSTANT);
				datatype = getContent(e, VARDATATYPE_LITERALCONSTANT);
			} else if (scope.equals(VARTYPE_LOCAL)) {
				NodeList nl = e.getElementsByTagName(VARNAME_LOCGLOB_ROOT);
				Element d = (Element) nl.item(0);
				name = d.getAttribute(VARNAME_LOCGLOB);
				if (nl.getLength() > 1) {
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

			} else if (scope.equals(VARTYPE_PREDEFINED)) {
				// not applicable
			}
			if (name != null && datatype != null) {
				variables.add(VariableFactory.createVariable(name, datatype, uid));
			}
		}
		return variables;
	}

	/**
	 * Creates the networks.
	 *
	 * @param doc         the XML file
	 * @param tempProgram the PLC program
	 * @return the list with networks
	 */
	private List<Network> createNetworks(Element doc, PlcProgram tempProgram) {
		NodeList nws = doc.getElementsByTagName(NETWORK);
		List<Network> networkList = new ArrayList<>();
		for (int i = 0; i < nws.getLength(); i++) {
			Element network = (Element) nws.item(i);
			List<Variable> variables = createVariablesFromXMLElement(network);
			Network nw = NetworkFactory.createNetwork(tempProgram, network, i + 1, variables);
			networkList.add(nw);
		}
		return networkList;
	}

	/**
	 * Gets the XML files from a folder.
	 *
	 * @param foldername the foldername with XML files
	 * @return the XML files
	 */
	private List<String> getXmlFiles(String foldername) {
		Path initPath = Paths.get(foldername);
		// finding files containing 'xml' in name
		Stream<Path> stream = null;
		List<String> files = new ArrayList<>();
		try {
			stream = Files.find(initPath, 5, (path, basicFileAttributes) -> {
				File file = path.toFile();
				return !file.isDirectory() && file.getName().contains(XML);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Path p : stream.collect(Collectors.toList())) {
			files.add(p.toString());
		}
		return files;
	}

	/**
	 * Removes the empty nodes from XML file.
	 *
	 * @param node the root node of the XML file
	 */
	public void removeEmptyNodes(Node node) {
		NodeList list = node.getChildNodes();
		List<Node> nodesToRecursivelyCall = new LinkedList<>();

		for (int i = 0; i < list.getLength(); i++) {
			nodesToRecursivelyCall.add(list.item(i));
		}
		for (Node tempNode : nodesToRecursivelyCall) {
			removeEmptyNodes(tempNode);
		}

		boolean emptyElement = node.getNodeType() == Node.ELEMENT_NODE && node.getChildNodes().getLength() == 0;
		boolean emptyText = node.getNodeType() == Node.TEXT_NODE && node.getNodeValue().trim().isEmpty();

		if (emptyElement || emptyText) {
			if (!node.hasAttributes()) {
				node.getParentNode().removeChild(node);
			}
		}

	}

	/**
	 * Gets the content of an XML element.
	 *
	 * @param element the element to get content from
	 * @param tagName the tag name to get the content from
	 * @return the content from an XML element
	 */
	private static String getContent(Element element, String tagName) {
		NodeList elems = element.getElementsByTagName(tagName);
		return elems.item(0).getTextContent();
	}

}
