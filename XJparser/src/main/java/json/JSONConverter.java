/*
 * Licensed to the Apache Software Foundation (ASF) under one or
 * more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright
 * ownership. The ASF licenses this file to you under the Apache
 * License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

/**
 * json is the name package of classes which allow to convert JSON to XML.
 */
package json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.jdom2.Element;

import com.google.gson.stream.JsonReader;

/**.
 * JSONConverter allow to convert JSON to XML thanks to JSON methods
 * @author Groupe 12
 * @version 1.0
 */
public class JSONConverter {
	/**
	 *
	 * @see JsonReader
	 */
	private JsonReader reader;
	/**.
	 * Contains the root of the XML file
	 * @see Element
	 */
	private static Element racine = new Element("quizz");
	/**.
	 * Contains the tree which represent XML File
	 * @see ArrayList<Element>
	 */
	private ArrayList<Element> tree = new ArrayList<Element>();
	/**.
	 * index variable
	 * @see int
	 */
	private int current = 0;

	/**.
	 * Allows to read a XML File
	 * @param path : String
	 */
	public JSONConverter(final String path) {
		try {
			readJsonStream(new FileInputStream(path));
			tree.add(racine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**.
	 * True if convert success
	 * @return boolean
	 */
	public final boolean convert() {
		return convertJson();
	}
	/**.
	 * "description de la fonction"
	 * @param path
	 * @return Element
	 * @see JSONConverter#readJsonStream(InputStream)
	 */
	private Element getCurrentElement() {
		return tree.get(current);
	}
	/**.
	 * Allows to find the next Element
	*/
	private void nextElement() {
		current++;
	}
	/**.
	 * Allows to find the previous Element
	 */
	private void previousElement() {
		current--;
	}
	/**.
	 * @param in : InputStream
	 * @return boolean
	 * @throws IOException
	 */
	private boolean readJsonStream(final InputStream in) {
		try {
			reader = new JsonReader(new InputStreamReader(
					in, "UTF-8"));
			reader.setLenient(true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**.
	 * Convert JSON to XML
	 * @return boolean
	 * @see JSONConverter#tree
	 * @see JSONConverter#readJsonStream(InputStream)
	 */
	private boolean convertJson() {
		try {
			while (reader.hasNext()) {
				if (filter(reader) == null) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**.
	 * Return the convert file in String
	 * @param reader
	 * @return String
	 * @throws IOException
	 * @see JSONConverter#tree
	 * @see JSONConverter#readJsonStream(InputStream)
	 */
	private String filter(final JsonReader reader) throws IOException {
		switch (reader.peek()) {
		case STRING:
			System.out.println(" " + reader.nextString());
			break;
		case NUMBER:
			Double d = reader.nextDouble();
			System.out.println(" " + d.toString());
			break;
		case BOOLEAN:
			Boolean b = reader.nextBoolean();
			System.out.println(" " + b.toString());
			break;
		case BEGIN_ARRAY:
			System.out.println("begin array");
			reader.beginArray();
			break;
		case BEGIN_OBJECT:
			System.out.println("begin obj");
			reader.beginObject();
			break;
		case END_ARRAY:
			System.out.println("end array");
			reader.endArray();
			break;
		case END_OBJECT:
			System.out.println("end obj");
			reader.endObject();
			break;
		case NAME:
			System.out.println(reader.nextName());
			break;
		case NULL:
			break;
		case END_DOCUMENT:
			reader.close();
			return null;
		default:
			break;
		}

		return "";

	}

}
