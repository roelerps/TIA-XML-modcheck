package storageModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Class TextWriter
 */
public abstract class TextWriter {

	/**
	 * Writes data into a text file
	 * 
	 * @param filename the text file where the data is written
	 * @param data     the data that is written into the text file
	 * @throws IOException exception for IO errors
	 */
	public static void writeToTextFile(String filename, String data) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
		writer.append(data);
		writer.close();
	}

}
