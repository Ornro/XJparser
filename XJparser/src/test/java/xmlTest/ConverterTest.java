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
 *
 */
package xmlTest;

import java.io.FileNotFoundException;

import xml.Converter;
import xml.JDOMConverter;

import org.junit.Test;

import core.Methods;

/**
 * 
 * @author Ben
 */
public class ConverterTest {

	@Test
	public void convertTest() {
		Converter converter = new Converter();
		String s = "";
		try {
			s = converter.convert("../test.xml", false);
			System.out.println(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveTest() {
		Converter converter = new Converter();
		try {
			String output = converter.convert("../test.xml", false);
			Methods.save(output, "../test2.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generalTest() {
		JDOMConverter jdc = new JDOMConverter("../test.xml", "../test3.json");
		jdc.convert(true);
		jdc.save();
		jdc.print();
	}
}
