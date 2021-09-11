package factory;

import java.util.ArrayList;
import java.util.List;

import plcBlockModel.CodeBlock;
import plcBlockModel.OrganizationBlock;
import plcBlockModel.PlcBlock;
import plcCodeUnitModel.Call;
import plcCodeUnitModel.CallOB;
import plcProgramModel.CallStructure;
import plcProgramModel.Node;
import plcProgramModel.PlcProgram;

/**
 * A factory for creating CallStructure objects.
 */
public abstract class CallStructureFactory {

	/**
	 * Instantiates a new call structure factory.
	 */
	private CallStructureFactory() {

	}

	/**
	 * Creates a new CallStructure object.
	 *
	 * @param program the PLC program
	 * @return the call structure of the supplied PLC program
	 */
	public static CallStructure createCallStructure(PlcProgram program) {
		CallStructure cs = new CallStructure();

		List<PlcBlock> obs = program.getTypedBlocks(OrganizationBlock.class);
		int i = 0;
		for (PlcBlock bl : obs) {
			OrganizationBlock ob = (OrganizationBlock) bl;
			Call obcall = new CallOB(i, ob);
			cs.addOB(obcall);
			i++;
		}

		List<Node> obnodes = cs.getRoot().getObs();
		for (Node ob : obnodes) {
			createCallRecursive(program, ob);
		}
		return cs;
	}

	/**
	 * Recursively adds nodes (children) to the supplied node.
	 *
	 * @param program the PLC program
	 * @param node    the parent where recursively nodes are added to
	 */
	private static void createCallRecursive(PlcProgram program, Node node) {
		CodeBlock block = (CodeBlock) program.getBlockByName(node.getBlockname());
		if (block.getProgLang().equals("FBD") || block.getProgLang().equals("LAD")) {
			List<Call> calls = block.getCalls();
			List<Node> children = new ArrayList<>();
			for (Call s : calls) {
				Node newnode = new Node(s);
				children.add(newnode);
				createCallRecursive(program, newnode);
			}
			node.setCalls(children);
		}
	}

}
