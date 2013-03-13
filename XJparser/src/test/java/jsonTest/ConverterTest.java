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
 * jsonTest package contains test class for convert JSON to XML.
 */
package jsonTest;

import java.io.FileNotFoundException;

import json.ConverterToXML;
import json.JSONConverter;

import org.junit.Test;

import core.Methods;

/**.
 * Test class to convert JSON to XML
 * @author Groupe 12
 * @version 1.0
 */
public class ConverterTest {
	/**.
	 * Convert JSON to XML
	 */
	@Test
	public final void convertTest() {
		ConverterToXML converter = new ConverterToXML();
		String s = "";
		try {
			s = converter.convertToXML("../test3.json",false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(s);
	}

	/**.
	 * Save the conversion into a file
	 */
	@Test
	public final void saveTest() {
		ConverterToXML converter = new ConverterToXML();
		try {
			String output = converter.convertToXML("../test3.json",false);
			Methods.save(output,"../test3.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**.
	 * Test the second way to convert JSON to XML
	 */
	@Test
	public final void jconvertTest() {
		JSONConverter jc = new JSONConverter("../test2.json");
		jc.convert();
		jc.print(2);
	}
}
