/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.swsoft.trial.common.Device;
import com.swsoft.trial.common.ThreadUnsafe;

/**
 * This class helps server to build response for client's request
 * @author Kuznetsov A.
 * @version 1.0 
 */
@ThreadUnsafe
public class Response {

	/**
	 * Creates new <code>Response</code> instance. <code>Response</code> instances are reusable!
	 * @throws IOException If I/O error occured
	 */
	public Response() throws IOException {
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			this.db = dbf.newDocumentBuilder();
			
			TransformerFactory factory = SAXTransformerFactory.newInstance();
			this.transformer = factory.newTransformer();

		} catch (ParserConfigurationException e) {
			throw (IOException)(new IOException()).initCause(e);		
		} catch (TransformerConfigurationException e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}
	
	/**
	 * Writes response with a set of odd devices
	 * @param devices <code>Collection</code> of odd devices
	 * @param os Stream to write response to
	 * @throws IOException If I/O error occured
	 */
	public void writeResponse(Collection<Device> devices, OutputStream os) throws IOException{
		try {
			
			Document doc = db.newDocument();
		
			Element root = doc.createElement(FAILURES_NODE_NAME);
			root.setAttribute(ABNORMAL_ATTR_NAME, Boolean.toString(false));
		
			for (Device d: devices) {
				Element devNode = doc.createElement(DEVICE_NODE_NAME);
				devNode.setTextContent(d.getName());
				root.appendChild(devNode);
			}		
		
			doc.appendChild(root);
		
			transformer.transform(new DOMSource(doc), new StreamResult(os));
		
		} catch(TransformerException e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}
	
	/**
	 * Sends "unknown host" to client
	 * @param os OutputStream to write response to
	 * @throws IOException If I/O error occured
	 */
	public void writeUnknownHostResponse(OutputStream os) throws IOException{
		try {
			
			Document doc = db.newDocument();
		
			Element root = doc.createElement(FAILURES_NODE_NAME);
			root.setAttribute(ABNORMAL_ATTR_NAME, Boolean.toString(true));
			doc.appendChild(root);
		
			transformer.transform(new DOMSource(doc), new StreamResult(os));
		
		} catch(TransformerException e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}

	
	private final static String FAILURES_NODE_NAME 	= "failures";
	private final static String DEVICE_NODE_NAME 	= "device";
	private final static String ABNORMAL_ATTR_NAME 	= "abnormal";
	
	private final DocumentBuilder db;
	private final Transformer transformer; 
}
