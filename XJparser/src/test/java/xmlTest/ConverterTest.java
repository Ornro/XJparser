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
 * xmlTest package contains test class for convert XML to SON.
 */
package xmlTest;

import java.io.FileNotFoundException;

import xml.ConverterToJSON;
import xml.JDOMConverter;

import org.json.JSONException;
import org.junit.Test;

import core.Methods;

/**.
 * Test class to convert XML to JSON
 * @author Groupe 12
 * @version 1.0
 */
public class ConverterTest {

	/**.
	 * Convert JSON to XML
	 */
	@Test
	public void convertTest() {
		ConverterToJSON converter = new ConverterToJSON();
		String s = "";
		try {
			s = converter.convertToJSON("../test.xml", false);
			System.out.println(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**.
	 * Save the conversion into a file
	 */
	@Test
	public void saveTest() {
		ConverterToJSON converter = new ConverterToJSON();
		try {
			String output = converter.convertToJSON(
					"../test.xml", false);
			Methods.save(output, "../test2.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**.
	 * Test the second way to convert XML to JSON
	 */
	@Test
	public void generalTest() {
		JDOMConverter jdc = new JDOMConverter(
				"../test.xml", "../test3.json");
		jdc.convert();
		try {
			jdc.save(2);
			System.out.println(jdc.toString(2));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
