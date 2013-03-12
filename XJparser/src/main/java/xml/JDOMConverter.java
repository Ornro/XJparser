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
import java.util.List;

import org.jdom2.input.SAXBuilder;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.Methods;

public class JDOMConverter {

	private static Document document;
	private static Element racine;
	private String result;
	private static String inputPath;
	private static String outputPath;

	public JDOMConverter(String inputPath) {
		this.inputPath = inputPath;
		this.outputPath = inputPath.split(".")[1].concat(".json");
		build();
	}
	
	public JDOMConverter(String inputPath, String outputPath){
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		build();
	}

	public final boolean convert(boolean prettyOutput) {
		try {
			if (prettyOutput) {
				result = convertElem(racine).toString(2);
			} else {
				result = convertElem(racine).toString(2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public final void print() {
		System.out.println(result);
	}

	public final boolean save() {
		return Methods.save(result,outputPath);
	}

	private void build() {
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(inputPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		racine = document.getRootElement();
	}

	private JSONObject convertElem(Element root) {
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		List<Element> children = root.getChildren();

		if (root.hasAttributes()) {
			convertAttributes(root.getAttributes(), obj);
		}
		if (!children.isEmpty()) {
			for (Element child : children) {
				list.put(convertElem(child));
			}
			try {
				obj.put(children.get(1).getName(), list);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return obj;
	}

	private void convertAttributes(final List<Attribute> attributes,
			JSONObject obj) {
		try {
			for (Attribute attrib : attributes) {
				obj.put(attrib.getName(), attrib.getValue());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
