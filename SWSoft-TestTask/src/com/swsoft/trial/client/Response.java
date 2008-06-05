/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.swsoft.trial.common.ThreadSafe;
import com.swsoft.trial.util.ResourceLoader;

/**
 * This utility class helps client to parse server's response
 * 
 * @author Kuznetsov A.
 * @version 1.0
 */
@ThreadSafe
public class Response {

	/**
	 * Returns illegal device names collection or <code>null</code> if server doesn't know current host 
	 * @param is <code>InputStream</code> to read server's response from
	 * @return illegal device names collection or <code>null</code> if server doesn't know current host
	 * @throws IOException If I/O error occured 
	 */
	public static Collection<String> getIllegalDeviceNames(InputStream is) throws IOException {
		try {
					
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.parse(is);
			
			Schema schema = ResourceLoader.loadSchema(SCHEMA_RESOURCE);
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(doc));
			
			Node abnormal = XPathAPI.selectSingleNode(doc, ABNORMAL_EXPRESSION);
			
			if (Boolean.parseBoolean(abnormal.getTextContent())){
				return null;
			}

			NodeList nl = XPathAPI.selectNodeList(doc, XPATH_EXPRESSION); 
			Collection<String> res = new ArrayList<String>(nl.getLength());
			
			for (int i=nl.getLength()-1; i>=0; i--) {
				res.add(nl.item(i).getTextContent());
			}

			return Collections.unmodifiableCollection(res);

		} catch(SAXException e) {
			throw new IOException(UNEXPECTED_RESPONSE);
		} catch(Exception e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}
	
	private final static String 	SCHEMA_RESOURCE	= "response.xsd";
	private final static String  	XPATH_EXPRESSION 	= "/failures/device";
	private final static String  	ABNORMAL_EXPRESSION = "/failures/@abnormal";
	private final static String  	UNEXPECTED_RESPONSE = "Unexpected response";
}
