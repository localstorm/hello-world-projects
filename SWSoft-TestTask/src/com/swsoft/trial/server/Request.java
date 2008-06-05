/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.swsoft.trial.common.ThreadUnsafe;
import com.swsoft.trial.util.ResourceLoader;

/**
 * This class helps to parse client's request. It is to be used in thread pool (One for each thread).
 * 
 * @author Kuznetsov A.
 * @version 1.0
 */
@ThreadUnsafe
public class Request {
	
	/**
	 * Builds new <code>Request</code> instance. <code>Request</code> instances are reusable. 
	 * @throws IOException If I/O exception occured 
	 */
	public Request() throws IOException {
		try {
			
			Schema schema = ResourceLoader.loadSchema(SCHEMA_RESOURCE);
			validator = schema.newValidator();
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			builder = dbf.newDocumentBuilder();
			
		} catch(Exception e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}
	
	/**
	 * Parses client's request
	 * @param is Client's request <code>InputStream</code>
	 * @param maxRequest Maximum request size in bytes
	 * @return <code>Host</code> instance
	 * @throws IOException If I/O error occured
	 */
	public Host getRequestInformation(InputStream is, int maxRequest) throws IOException {
		try {
			
			Document doc = builder.parse(wrap(is, maxRequest));
			validator.validate(new DOMSource(doc));
			
			// XPath again and again
			Host host = new Host();
			
			NodeList addresses = XPathAPI.selectNodeList(doc, ADDRESSES_EXPR);
			for (int i = addresses.getLength()-1; i>=0; i--) {
				Node addr = addresses.item(i);
				String address 	= XPathAPI.selectSingleNode(addr, IP_EXPR).getTextContent();
				String hostName = XPathAPI.selectSingleNode(addr, NAME_EXPR).getTextContent();
				host.addAddress(hostName, address);
			}
			
			NodeList devices = XPathAPI.selectNodeList(doc, DEVICES_EXPR);
			for (int i = devices.getLength()-1; i>=0; i--) {
				Node dev = devices.item(i);
				String fs 	= XPathAPI.selectSingleNode(dev, FS_EXPR).getTextContent();
				String name	= XPathAPI.selectSingleNode(dev, NAME_EXPR).getTextContent();
				host.addDevice(name, fs);
			}
			return host;
			
		} catch(TransformerException e) {
			throw (IOException)(new IOException()).initCause(e);
		} catch(SAXException e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}
	
	// This is to prevent DoS attacks by sending very long XML
	private static InputStream wrap(InputStream is, int maxBytes) throws IOException {
		
		byte[] bytes = new byte[maxBytes];
		int got = 0;
		int justRead;
		
		while ((justRead=is.read(bytes, got, bytes.length-got))>=0) {
			got+=justRead;
			if (got>=bytes.length) {
				break; 
			}
		}
		return new ByteArrayInputStream(bytes, 0, got);
		
	}
	
	private final Validator validator;
	private final DocumentBuilder builder;
	private final static String SCHEMA_RESOURCE = "request.xsd";
	
	private final static String ADDRESSES_EXPR 	= "/request/addresses/address";
	private final static String DEVICES_EXPR 	= "/request/devices/device";

	private final static String IP_EXPR			= "ip";
	private final static String FS_EXPR			= "fs";
	private final static String NAME_EXPR 		= "name";
}
