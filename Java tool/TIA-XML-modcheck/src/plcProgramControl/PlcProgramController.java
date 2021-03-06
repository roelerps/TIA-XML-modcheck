package plcProgramControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import factory.StorageFactory;
import plcBlockModel.CodeBlock;
import plcBlockModel.PlcBlock;
import plcProgramModel.CallStructure;
import plcProgramModel.Node;
import plcProgramModel.PlcProgram;
import plcProgramModel.Root;
import storageModel.PlcProgramReader;
import storageModel.TextWriter;

/**
 * The Class PlcProgramController.
 */
public class PlcProgramController {

	/** The PLC program. */
	private PlcProgram program = null;

	/**
	 * Create the PlcProgramController.
	 **/
	public PlcProgramController() {

	}

	/**
	 * Request to load the PLC program.
	 *
	 * @param foldername the folder name for the XML files
	 */
	public void requestLoadProgram(String foldername) {
		try {
			PlcProgramReader reader = StorageFactory.createPlcBlockReader();
			program = reader.loadProgram(foldername);
			if (program != null) {

			}
		} catch (IOException exc) {

		}
		if (program == null) {

		}
	}

	/**
	 * Prints the PLC program.
	 *
	 * @param option the print option
	 */
	public void printPlcProgram(int option) {
		System.out.println("\r\nProject name: " + program.getProjectName() + "\r\n");

		List<PlcBlock> bl = new ArrayList<>();
		bl = program.getBlocksOrdered();

		switch (option) {
		case (1):
			printBlockList(bl);
			break;
		case (2):
			printInterfaces(bl);
			break;
		case (3):
			printCallStructure(program);
			break;
		case (4):
			printCallWithInterface(program);
			break;
		case (5):
			printCallWithAllInformation(program);
			break;
		}

	}

	/**
	 * Prints the block list of the PLC Program.
	 *
	 * @param bl the blocks
	 */
	public void printBlockList(List<PlcBlock> bl) {
		for (PlcBlock b : bl) {
			System.out.println(b.toString() + "\r\n");
			printDataToFile("output_option_1.txt", b.toString() + "\r\n");
		}
	}

	/**
	 * Prints the interfaces of all blocks.
	 *
	 * @param bl the PLC blocks
	 */
	public void printInterfaces(List<PlcBlock> bl) {
		for (PlcBlock b : bl) {
			System.out.print(b.toStringShort());
			printDataToFile("output_option_2.txt", b.toStringShort());
			if (b.getInterface() != null) {
				System.out.println(b.getInterface().toString());
				printDataToFile("output_option_2.txt", b.getInterface().toString());
			} else {
				System.out.println("");
			}
		}
	}

	/**
	 * Prints the call structure of the PLC program.
	 *
	 * @param program the PLC program
	 */
	public void printCallStructure(PlcProgram program) {
		CallStructure cs = program.getCallStructure();
		Root r = cs.getRoot();
		List<Node> obs = r.getObs();
		if (obs != null) {
			printNodesRecursive(obs, 0);
		}
		System.out.println("");
	}

	/**
	 * Prints the Call structure nodes recursive.
	 *
	 * @param nodes the call structure nodes
	 * @param depth the depth to search
	 */
	private void printNodesRecursive(List<Node> nodes, int depth) {
		if (nodes != null) {
			for (Node n : nodes) {
				String prefix = "";
				for (int i = 0; i < depth; i++) {
					System.out.print("|--");
					prefix = prefix + "|--";
				}
				System.out.println(n.getCallInfo());
				printDataToFile("output_option_3.txt", prefix + n.getCallInfo() + "\r\n");
				List<Node> ln = n.getCalls();
				printNodesRecursive(ln, depth + 1);
			}
		}
	}

	/**
	 * Prints the call with interface.
	 *
	 * @param program the PLC program
	 */
	private void printCallWithInterface(PlcProgram program) {
		CallStructure cs = program.getCallStructure();
		Root r = cs.getRoot();
		List<Node> obs = r.getObs();
		if (obs != null) {
			printNodesRecursiveInterface(obs, 0);
		}
		System.out.println("");
	}

	/**
	 * Prints the nodes recursive interface.
	 *
	 * @param nodes the nodes with interface
	 * @param depth the depth to search
	 */
	private void printNodesRecursiveInterface(List<Node> nodes, int depth) {
		if (nodes != null) {
			for (Node n : nodes) {
				String prefix = "";
				for (int i = 0; i < depth; i++) {
					prefix += "|--";

				}
				System.out.print(prefix);
				System.out.println(n.getCallInfo());
				printDataToFile("output_option_4.txt", prefix + n.getCallInfo() + "\r\n");
				System.out.print(n.getBlock().getBlock().toStringCode(prefix));
				printDataToFile("output_option_4.txt", n.getBlock().getBlock().toStringCode(prefix));
				List<Node> ln = n.getCalls();
				printNodesRecursiveInterface(ln, depth + 1);
			}
		}
	}

	/**
	 * Prints the PLC program's Logic Model
	 * 
	 * @param program the PLC program
	 */
	private void printCallWithAllInformation(PlcProgram program) {
		List<PlcBlock> blocks = program.getBlocks();
		for (PlcBlock bl : blocks) {
			if (bl instanceof CodeBlock) {
				String outp = ((CodeBlock) bl).toStringCodeAllNetworks();
				printDataToFile("output_option_5.txt", outp + "\r\n");
				System.out.println(outp);
			}
		}

	}

	/**
	 * Prints all data generated by user choice to text file at same time
	 * 
	 * @param filename the filename for the text file
	 * @param outp     the data to be written in the text file
	 */
	private void printDataToFile(String filename, String outp) {
		try {
			TextWriter.writeToTextFile(filename, outp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
