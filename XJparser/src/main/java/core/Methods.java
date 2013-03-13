/**
 * core is the name package of Method class.
 */
package core;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**.
 * Method class for evrybody
 * @author Groupe 12
 * @version 1.0
 */
public class Methods {


/**
 * @param path.
 * @return inputStreamString
 * @throws FileNotFoundException
 */
	public static final String getFileAsString(String path)
			throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(path);
		String inputStreamString = new Scanner(fis, "UTF-8")
				.useDelimiter("\\A").next();

		return inputStreamString;
	}

	/**
	 * @param output
	 * @param path
	 * @return sucess
	 */
	public static final boolean save(final String output, final String path) {
		FileWriter fstream;
		BufferedWriter out;
		boolean sucess = false;
		try {
			fstream = new FileWriter(path);
			out = new BufferedWriter(fstream);
			out.write(output);
			out.close();
			sucess = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sucess;
	}
}
