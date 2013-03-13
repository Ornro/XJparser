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

import org.jdom2.Document;
import org.jdom2.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import core.Methods;

public class JSONConverter {

	private static Element racine = new Element("quizz");
	private static Document document;
	private ArrayList<Element> tree = new ArrayList<Element>();
	private int index = 0;
	private JSONObject jo;
	

	public JSONConverter(String path) {
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
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
