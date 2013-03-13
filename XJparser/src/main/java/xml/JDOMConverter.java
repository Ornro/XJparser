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
package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.Methods;

public class JDOMConverter {

	private Document document;
	private Element racine;
	private String result;
	private String inputPath;
	private String outputPath;
	private ArrayList<JSONObject> tree = new ArrayList<JSONObject>();
	private int current = 0;
	private boolean skipp = false;

	public JDOMConverter(String inputPath) {
		this.inputPath = inputPath;
		this.outputPath = inputPath.split(".")[1].concat(".json");
		build();
	}

	public JDOMConverter(String inputPath, String outputPath) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		build();
	}

	public final boolean convert(boolean prettyOutput) {

		try {
			listNodes(racine);
			if (prettyOutput) {
				result = tree.get(0).toString(2);
			}
			else{
				result = tree.get(0).toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public final void print() {
		System.out.println(result);
	}

	public final boolean save() {
		return Methods.save(result, outputPath);
	}

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

	public void listNodes(Object o) throws JSONException {
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
				listNodes(child);
			}
			// Children
			while (itc.hasNext()) {
				Object child = itc.next();
				listNodes(child);
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

	private void handleAttribute(Attribute a) throws JSONException {
		tree.get(current).put(a.getName(), a.getValue());
	}

	private void handleText(Text t) throws JSONException {
		String str = t.getTextNormalize();
		if (!str.isEmpty()) {
			String parent = t.getParent().getName();
			tree.get(current - 1).put(parent, str);
			skipp = true;
		}
	}

	private void handleArray(String elemName) throws JSONException {
		if (!skipp) {
			if (tree.get(current - 1).has(elemName)) {
				JSONArray jsa = new JSONArray();
				jsa.put(tree.get(current - 1).get(elemName));
				jsa.put(tree.get(current));
				tree.remove(tree.get(current - 1).get(elemName));
				tree.get(current - 1).put(elemName, jsa);
			} else {
				tree.get(current - 1).put(elemName, tree.get(current));
			}
		} else {
			skipp = false;
		}

	}
}