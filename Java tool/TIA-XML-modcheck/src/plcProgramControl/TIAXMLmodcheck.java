package plcProgramControl;

import java.util.Scanner;

/**
 * The Class TIAXMLmodcheck.
 */
public class TIAXMLmodcheck {

	public static Boolean debug = false;

	/**
	 * The main method of the program.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String dir = "C:/Users/Roel/Documents/GitHub/ou-thesis/tool/parsexml/xmls";

		PlcProgramController progCont = new PlcProgramController();
		progCont.requestLoadProgram(dir);

		Scanner reader = new Scanner(System.in);

		while (true) {
			System.out.println("Choose the desired action:");
			System.out.println("0 = Exit application");
			System.out.println("1 = List all blocks found in export XML files");
			System.out.println("2 = List all blocks with the defined interfaces of the blocks");
			System.out.println("3 = List the call structure of the PLC program");
			System.out.println("4 = List all instances of blocks with connected variables");
			System.out.println("5 = List all instances of blocks with all information of all networks");
			System.out.println("Enter a number: ");
			int n = reader.nextInt();

			if (n > 0 && n < 6) {
				progCont.printPlcProgram(n);
			} else if (n == 0) {
				System.out.println("Application is terminated...");
				break;
			} else {
				System.err.println("Chosen action does not exist!");
			}
		}
		reader.close();
	}

}
