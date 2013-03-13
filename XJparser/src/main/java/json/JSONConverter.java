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

import org.jdom2.Document;
import org.jdom2.Element;

import com.google.gson.stream.JsonReader;

/**.
 * JSONConverter allow to convert JSON to XML thanks to JSON methods
 * @author Groupe 12
 * @version 1.0
 */
public class JSONConverter {

	JsonReader reader;

	private static Element racine = new Element("quizz");
	private static Document document = new Document(racine);
	private ArrayList<Element> tree = new ArrayList<Element>();
	private int current = 0;

	public JSONConverter(String path) {
		try {
			readJsonStream(new FileInputStream(path));
			tree.add(racine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public final boolean convert() {
		return convertJson();
	}
	
	private Element getCurrentElement(){
		return tree.get(current);
	}
	
	private void nextElement(){
		current ++;
	}
	
	private void previousElement(){
		current --;
	}
	/**.
	 * @param InputStream
	 * @return boolean
	 * @throws IOException
	 */
	private final boolean readJsonStream(InputStream in) {
		try {
			reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
			reader.setLenient(true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**.
	 * @return Boolean
	 */
	private final boolean convertJson() {
		try {
			while (reader.hasNext()) {
				if (filter(reader) == null)
					return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**.
	 * @param reader
	 * @return String
	 * @throws IOException
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

		}

		return "";

	}

}
