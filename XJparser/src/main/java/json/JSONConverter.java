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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import core.Methods;

/**.
 * JSONConverter allow to convert JSON to XML thanks to JSON methods
 * @author Groupe 12
 * @version 1.0
 */
public class JSONConverter {
<<<<<<< HEAD

	private static Element racine = new Element("quizz");
	private static Document document;
	private ArrayList<Element> tree = new ArrayList<Element>();
	private int index = 0;
	private JSONObject jo;
	
=======
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
>>>>>>> 141c3bcba4867291eed009ff7be34bbc5df2fa52

	/**.
	 * Allows to read a XML File
	 * @param path : String
	 */
	public JSONConverter(final String path) {
		try {
			JSONTokener jstk = new JSONTokener(Methods.getFileAsString(path));
			jo = new JSONObject(jstk);
			tree.add(0, racine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
	public void convert(){
		Map<String, Object> map = new HashMap<String, Object>();
		toJavaMap(jo,map);
		System.out.println(map);
	}

	private void toJavaMap(JSONObject o, Map<String, Object> b) {
		try {
			Iterator ji = o.keys();
			while (ji.hasNext()) {
				String key = (String) ji.next();
				Object val = o.get(key);
				
				if (val.getClass() == JSONObject.class) {
					System.out.println("Object = "+key);
					Map<String, Object> sub = new HashMap<String, Object>();
					index++;
					Element e = new Element(key);
					tree.add(index,e);
					toJavaMap((JSONObject) val, sub);
					index--;
					tree.get(index).addContent(e);
					b.put(key, sub);
					
				} else if (val.getClass() == JSONArray.class) {
					System.out.println("Array = "+key);
					List<Object> l = new ArrayList<Object>();
					JSONArray arr = (JSONArray) val;
					
					for (int a = 0; a < (arr).length(); a++) {
						Map<String, Object> sub = new HashMap<String, Object>();
						Object element;
						element = arr.get(a);
						index++;
						Element e = new Element(key);
						tree.add(index,e);
						if (element instanceof JSONObject) {
							toJavaMap((JSONObject) element, sub);
							l.add(sub);
						} else {
							l.add(element);
						}
						
					}
					b.put(key, l);
				} else {
					System.out.println("Value = "+key);
					Element e = new Element(key);
					tree.get(index).addContent(e);
					
					e.addContent(val.toString());
					b.put(key, val);
=======
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
>>>>>>> 141c3bcba4867291eed009ff7be34bbc5df2fa52
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
<<<<<<< HEAD
=======
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
>>>>>>> 141c3bcba4867291eed009ff7be34bbc5df2fa52
		}
	}

}
