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

import core.Methods;

import java.io.FileNotFoundException;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

/**.
 * Converter allow to convert XML to JSON thanks to JSON methods
 * @author Groupe 12
 * @version 1.0
 */
public class ConverterToJSON {
	/**.
	 * Contents of input file
	 * @see String
	 */
	private String input;
	/**.
	 * Contents of output file
	 * @see String
	 */
	private String output;

	/**.
	 * Allow to convert XML to JSON
	 * @param path relative path of the file to convert.
	 * @param typeHints : boolean
	 * @return output
	 * @throws FileNotFoundException
	 * @see Converter#input
	 * @see Converter#output
	 */
	public final String convertToJSON(
			final String path, final boolean typeHints)
			throws FileNotFoundException {
		input = Methods.getFileAsString(path);

		XMLSerializer serializer = new XMLSerializer();
		JSON json = serializer.read(input);
		output = json.toString(2);

		return output;
	}
}
