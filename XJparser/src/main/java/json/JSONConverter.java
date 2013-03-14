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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import core.Methods;

/**
 * . JSONConverter allow to convert JSON to XML thanks to org.json and org.jdom2
 * methods
 * 
 * @author Groupe 12
 * @version 1.0
 */
public class JSONConverter {

	/**
	 * . Document representing the converted document
	 */
	private static Document document;

	/**
	 * . JSONObject containing the initial json object to convert
	 */
	private JSONObject jo;

	/**
	 * . Boolean telling if converter has been initialized. true = not
	 * initialized
	 */
	private boolean firstCall = true;

	/**
	 * . Contains input file path
	 * 
	 * @see String
	 */
	private String inputPath;

	/**
	 * . Contains output file path
	 * 
	 * @see String
	 */
	private String outputPath;

	/**
	 * . Contains the tree which represent XML File
	 * 
	 * @see ArrayList<Element>
	 */
	private ArrayList<Element> tree = new ArrayList<Element>();

	/**
	 * . Current index in the tree
	 */
	private int index = 0;

	/**
	 * . Constructor
	 * 
	 * @param iPath
	 *            : String path of the file to convert
	 */
	public JSONConverter(final String iPath) {
		this.inputPath = iPath;
		this.outputPath = inputPath.split(".json")[0].concat(".xml");
		build();
	}

	/**
	 * . Constructor
	 * 
	 * @param iPath
	 *            : String path of the file to convert
	 * @param oPath
	 *            : String the output where the file will be saved.
	 */
	public JSONConverter(final String iPath, final String oPath) {
		this.inputPath = iPath;
		this.outputPath = oPath;
		build();
	}

	/**
	 * . Builds the initial JSON object.
	 */
	private void build() {
		try {
			JSONTokener jstk = new JSONTokener(
					Methods.getFileAsString(inputPath));
			jo = new JSONObject(jstk);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * . Converts the built JSONObject
	 */
	public void convert() {
		try {
			filter(jo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document = new Document(tree.get(0));
	}

	/**
	 * . Prints the result
	 * 
	 * @param format
	 *            if 2 is specified uses prettyFormat {@link Format}
	 */
	public void print(int format) {
		System.out.println(this.toString(format));
	}

	/*
	 * Non j-doc
	 * 
	 * @see java.lang.String
	 */
	@Override
	public final String toString() {
		XMLOutputter sortie = new XMLOutputter();
		return sortie.outputString(document);
	}

	/**
	 * . Gives a string representation of the object
	 * 
	 * @param format
	 *            uses prettyFormat if format is 2
	 * @return String representation of the object
	 */
	public final String toString(int format) {
		XMLOutputter sortie;
		if (format == 2) {
			sortie = new XMLOutputter(Format.getPrettyFormat());
		} else {
			sortie = new XMLOutputter();
		}
		return sortie.outputString(document);
	}

	/**
	 * . Saves the converted content in the specified file if no file was
	 * specified in the constructor default is applied
	 * 
	 * @param format @see toString(int format)
	 */
	public void save(int format) {
		Methods.save(this.toString(format), outputPath);
	}

	/**
	 * Filters a JSONObject to convert it
	 * @param o the object to convert
	 * @throws JSONException
	 */
	private void filter(JSONObject o) throws JSONException {
		Iterator ji = o.keys();
		while (ji.hasNext()) {
			String key = (String) ji.next();
			Object val = o.get(key);
			if (val.getClass() == JSONObject.class) {
				if (!firstCall) {
					index++;
					Element e = new Element(key);
					tree.add(index, e);

					filter((JSONObject) val);
					index--;
					tree.get(index).addContent(e);
				} else {
					firstCall = false;
					Element e = new Element(key);
					tree.add(index, e);
					filter((JSONObject) val);
				}
			} else if (val.getClass() == JSONArray.class) {
				List<Object> l = new ArrayList<Object>();
				JSONArray arr = (JSONArray) val;

				for (int a = 0; a < (arr).length(); a++) {
					Object element;
					element = arr.get(a);
					index++;
					Element e = new Element(key);
					tree.add(index, e);
					if (element instanceof JSONObject) {
						filter((JSONObject) element);
					} else {
						Element e2 = new Element(key);
						e2.addContent(val.toString());
						tree.get(index).addContent(e2);
						l.add(element);
					}
					index--;
					tree.get(index).addContent(e);
				}
			} else {
				Element e = new Element(key);
				e.addContent(val.toString());
				tree.get(index).addContent(e);
			}
		}
	}
}