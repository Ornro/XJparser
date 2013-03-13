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
import java.io.IOException;
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
 * . JSONConverter allow to convert JSON to XML thanks to JSON methods
 * 
 * @author Groupe 12
 * @version 1.0
 */
public class JSONConverter {

	private static Document document;
	private JSONObject jo;
	private boolean firstCall = true;
	
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

	/**
	 * . Contains the tree which represent XML File
	 * 
	 * @see ArrayList<Element>
	 */
	private ArrayList<Element> tree = new ArrayList<Element>();
	/**
	 * . index variable
	 * 
	 * @see int
	 */
	private int index = 0;

	

	/**.
	 * Constructor
	 * @param iPath : String
	 */
	public JSONConverter(final String iPath) {
		this.inputPath = iPath;
		this.outputPath = inputPath.split(".json")[0].concat(".xml");
		build();
	}

	/**.
	 * Constructor
	 * @param iPath : String
	 * @param oPath : String
	 */
	public JSONConverter(final String iPath, final String oPath) {
		this.inputPath = iPath;
		this.outputPath = oPath;
		build();
	}

	private final void build(){
		try {
			JSONTokener jstk = new JSONTokener(Methods.getFileAsString(inputPath));
			jo = new JSONObject(jstk);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void convert() {
		try {
			toJavaMap(jo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document = new Document(tree.get(0));
	}

	public void print(int format) {
		System.out.println(this.toString(format));
	}
	
	public final String toString(){
		XMLOutputter sortie = new XMLOutputter();
		return sortie.outputString(document);
	}

	public final String toString(int format){
		XMLOutputter sortie;
		if (format == 2){
			sortie = new XMLOutputter(Format.getPrettyFormat());
		}
		else{
			sortie = new XMLOutputter();
		}
		return sortie.outputString(document);
	}
	
	public void save(){
		
	}

	private void toJavaMap(JSONObject o) throws JSONException {
		Iterator ji = o.keys();
		while (ji.hasNext()) {
			String key = (String) ji.next();
			Object val = o.get(key);
			if (val.getClass() == JSONObject.class) {
				if (!firstCall) {
					index++;
					Element e = new Element(key);
					tree.add(index, e);

					toJavaMap((JSONObject) val);
					index--;
					tree.get(index).addContent(e);
				}else{
					firstCall = false;
					Element e = new Element(key);
					tree.add(index, e);
					toJavaMap((JSONObject) val);
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
						toJavaMap((JSONObject) element);
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