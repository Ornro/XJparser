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
 * xml is the name package of classes which allow to convert XML to JSON.
 */
package xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import json.JSONConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import core.Methods;

/**.
 * JDOMConverter allow to convert XML to JSON thanks to JDOM methods
 * @author Groupe 12
 * @version 1.0
 */
public class JDOMConverter {
	/**.
	 * Contains the root of the XML file
	 * @see Document
	 */
	private Document document;
	/**.
	 * Contains the root of the XML file
	 * @see Element
	 */
	private Element racine;
	/**.
	 * Contains input file path
	 * @see String
	 */
	private String inputPath;
	/**.
	 * Contains output file path
	 * @see String
	 */
	private String outputPath;
	/**.
	 * Contains the tree of input file
	 * @see ArrayList<JSONObject>
	 */
	private ArrayList<JSONObject> tree = new ArrayList<JSONObject>();
	/**.
	 * index
	 * @see int
	 */
	private int current = 0;
	/**.
	 * Allow to skip
	 * @see boolean
	 */
	private boolean skipp = false;

	/**.
	 * Constructor
	 * @param iPath : String
	 */
	public JDOMConverter(final String iPath) {
		this.inputPath = iPath;
		this.outputPath = inputPath.split(".xml")[0].concat(".json");
		build();
	}

	/**.
	 * Constructor
	 * @param iPath : String
	 * @param oPath : String
	 */
	public JDOMConverter(final String iPath, final String oPath) {
		this.inputPath = iPath;
		this.outputPath = oPath;
		build();
	}

	/**.
	 * Convert the outputprint to JSON segmentation
	 * @return boolean
	 */
	public final boolean convert() {

		try {
			filter(racine);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public final void print(int format){
		try {
			this.toString(format);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**.
	 * Print the result
	 * @return String
	 */
	public final String toString() {
		return tree.get(0).toString();
	}
	
	public final String toString(int format) throws JSONException{
		return tree.get(0).toString(format);
	}

	/**.
	 * Save the result into outputPath
	 * @param format output format (2 = prety)
	 * @return boolean
	 * @throws JSONException 
	 */
	public final boolean save(int format) throws JSONException {
		return Methods.save(this.toString(format), outputPath);
	}

	/**.
	 * Build the tree corresponding to XML file
	 */
	private void build() {
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(inputPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		racine = document.getRootElement();
		tree.add(current, new JSONObject());
	}
	/**.
	 * Allows to list the node
	 * @param o : Object
	 * @thows JSONException
	 * @see JSONConverter#tree
	 * @see JSONConverter#readJsonStream(InputStream)
	 */
	public final void filter(final Object o) throws JSONException {
		if (o instanceof Element) {
			Element element = (Element) o;
			String elemName = element.getName();
			List children = element.getContent();
			List attribute = element.getAttributes();
			Iterator ita = attribute.iterator();
			Iterator itc = children.iterator();

			current++;
			tree.add(current, new JSONObject());

			// Attributes
			while (ita.hasNext()) {
				Object child = ita.next();
				filter(child);
			}
			// Children
			while (itc.hasNext()) {
				Object child = itc.next();
				filter(child);
			}
			handleArray(elemName);
			tree.remove(current);
			current--;
		} else if (o instanceof Attribute) {
			handleAttribute((Attribute) o);
		} else if (o instanceof Text) {
			handleText((Text) o);
		}
	}

	private void handleAttribute(final Attribute a) throws JSONException {
		tree.get(current).put(a.getName(), a.getValue());
	}

	private void handleText(final Text t) throws JSONException {
		String str = t.getTextNormalize();
		if (!str.isEmpty()) {
			String parent = t.getParent().getName();
			tree.get(current - 1).put(parent, str);
			skipp = true;
		}
	}

	private void handleArray(final String elemName) throws JSONException {
		if (!skipp) {
			if (tree.get(current - 1).has(elemName)) {
				JSONArray jsa = new JSONArray();
				jsa.put(tree.get(current - 1).get(elemName));
				jsa.put(tree.get(current));
				tree.remove(tree.get(
						current - 1).get(elemName));
				tree.get(current - 1).put(elemName, jsa);
			} else {
				tree.get(current - 1).put(
						elemName, tree.get(current));
			}
		} else {
			skipp = false;
		}

	}
}