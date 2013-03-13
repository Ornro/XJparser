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

/**
 * . Method class for everybody
 * @author Groupe 12
 * @version 1.0
 */
public class Methods {

	/**
	 * . Allows to return the contents of input file from path file
	 * @param path : String
	 * @throws FileNotFoundException
	 * @return the contents of input file"
	 */
	public static final String getFileAsString(final String path)
			throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(path);
		String inputStreamString = new Scanner(fis, "UTF-8")
				.useDelimiter("\\A").next();

		return inputStreamString;
	}

	/**
	 * . Save the conversion into a output file
	 * @param output : String
	 * @param path : String
	 * @return boolean
	 */
	public static final boolean save(final String output,
			final String path) {
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
