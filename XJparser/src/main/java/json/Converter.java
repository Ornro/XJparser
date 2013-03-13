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

import core.Methods;

import java.io.FileNotFoundException;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

/**.
 * Converter allow to convert JSON to XML thanks to JSON methods
 * @author Groupe 12
 * @version 1.0
 */
public class Converter {
	/**.
	 * String corresponding to input file 
	 * String corresponding to output file
	 */
	private String input;
	private String output;

	/**.
	 * @param path
	 * @param typeHints
	 *            : relative path of the file to convert.
	 * @return String
	 * @throws FileNotFoundException
	 */
	public final String convert(final String path, final boolean typeHints)
			throws FileNotFoundException {
		input = Methods.getFileAsString(path);

		XMLSerializer serializer = new XMLSerializer();
		serializer.setTypeHintsEnabled(typeHints);
		JSON serializedInput = JSONSerializer.toJSON(input);
		output = serializer.write(serializedInput);

		return output;
	}
}
