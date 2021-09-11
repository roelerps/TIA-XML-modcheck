package factory;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import plcBlockModel.Interface;
import plcProgramModel.Variable;

/**
 * A factory for creating Interface objects.
 */
public abstract class InterfaceFactory {

	/**
	 * Instantiates a new interface factory.
	 */
	private InterfaceFactory() {

	}

	/**
	 * Creates a new Interface object.
	 *
	 * @param doc the read in XML file
	 * @return the created interface
	 */
	public static Interface createInterface(Element doc) {
		List<Variable> input = new ArrayList<Variable>();
		List<Variable> output = new ArrayList<Variable>();
		List<Variable> inOut = new ArrayList<Variable>();
		List<Variable> stat = new ArrayList<Variable>();
		List<Variable> temp = new ArrayList<Variable>();
		List<Variable> constant = new ArrayList<Variable>();

		NodeList elems = doc.getElementsByTagName("Section");

		for (int i = 0; i < elems.getLength(); i++) {
			Node n = elems.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if (e.getParentNode().getParentNode().getNodeName().equals("Interface")) {
					String name = e.getAttribute("Name");
					if (name.equals("Input") || name.equals("Output") || name.equals("InOut") || name.equals("Static")
							|| name.equals("Temp") || name.equals("Constant") || name.equals("Return")) {
						NodeList in = e.getElementsByTagName("Member");
						for (int j = 0; j < in.getLength(); j++) {
							Node m = in.item(j);
							if (m.getNodeType() == Node.ELEMENT_NODE) {
								Element d = (Element) m;
								if (d.getParentNode().getParentNode().getParentNode().getNodeName()
										.equals("Interface")) {
									String varName = d.getAttribute("Name");
									String varType = d.getAttribute("Datatype");
									Variable v = VariableFactory.createVariable(varName, varType, 1);
									if (name.equals("Input")) {
										input.add(v);
									} else if (name.equals("Output") || name.equals("Return")) {
										output.add(v);
									} else if (name.equals("InOut")) {
										inOut.add(v);
									} else if (name.equals("Static")) {
										stat.add(v);
									} else if (name.equals("Temp")) {
										temp.add(v);
									} else if (name.equals("Constant")) {
										constant.add(v);
									}
								}
							}
						}
					}
				}
			}
		}
		return new Interface(input, output, inOut, stat, temp, constant);
	}
}