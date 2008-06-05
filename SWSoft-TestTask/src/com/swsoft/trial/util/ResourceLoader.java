/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 * Used to provide additional functionality for system resource processing.
 * It is adopted to work in Tomcat 5.5.* servlet container.
 * @author Kuznetsov A.
 * @version 1.0
 */
public class ResourceLoader {
	
	/**
	 * Loads system resource with given resource ID.
	 * @param resourceId System resource ID
	 * @return <code>InputStream</code> instance that allows to read resource
	 * @throws IOException If resource not found.
	 */
	public static InputStream loadResource(String resourceId) throws IOException {
		InputStream is;
		
		if ((is=Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceId))==null) {
			throw new IOException("<"+resourceId+"> Not found.");
		}
		
		return is;
	}
	
	/**
	 * Loads XML Schema from resources
	 * @param resourceId Schema resource ID
	 * @return <code>Schema</code> instance
	 * @throws IOException If I/O error occured
	 */
	public static Schema loadSchema(String resourceId) throws IOException {
		
		InputStream is = null;
		try {
			
			is = ResourceLoader.loadResource(resourceId);
			Source schemaSource = new StreamSource(is);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			return factory.newSchema(schemaSource);
			
		} catch(SAXException e) {
			throw (IOException)(new IOException()).initCause(e);
		} finally {
			is.close();
		}
	}
	
	
	private ResourceLoader() {
	}
}
