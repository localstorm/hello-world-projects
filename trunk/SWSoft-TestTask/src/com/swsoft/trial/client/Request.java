/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.client;

import java.io.*;
import java.util.Collection;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import com.swsoft.trial.common.Address;
import com.swsoft.trial.common.Device;
import com.swsoft.trial.common.ThreadSafe;

@ThreadSafe
public class Request {
	
	/**
	* This utility class helps to build client's requests
	*/
	public static void perform(Collection<Device> devices, Collection<Address> addresses, OutputStream os) throws IOException {
		
		if (os==null || devices==null || addresses==null) {
			throw new NullPointerException("Null parameters are not expected!");
		}

		try {
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = buildRequestDocument(db, devices, addresses);
			
			TransformerFactory factory = SAXTransformerFactory.newInstance();
			factory.newTransformer().transform(new DOMSource(doc), new StreamResult(os));
			
		} catch(TransformerException e) {
			throw (IOException)(new IOException()).initCause(e);
		} catch(ParserConfigurationException e) {
			throw (IOException)(new IOException()).initCause(e);
		}
	}
	
	
	private static Document buildRequestDocument(DocumentBuilder builder, Collection<Device> devices, Collection<Address> addresses) {
		
		Document doc = builder.newDocument(); 
		Element root = doc.createElement(REQUEST_ELEMENT);
		{
			Element addr	= doc.createElement(ADDRESSES_ELEMENT);
			Element dev 	= doc.createElement(DEVICES_ELEMENT);

			for (Device d: devices){
				Element devNode = doc.createElement(DEVICE_ELEMENT);
				
				Element name = doc.createElement(NAME_ELEMENT);
				name.setTextContent(d.getName());
				
				Element fs = doc.createElement(FS_ELEMENT);
				fs.setTextContent(d.getFileSystem());
				
				devNode.appendChild(fs);
				devNode.appendChild(name);
				dev.appendChild(devNode);
			}

			for (Address address: addresses){
				Element addrNode = doc.createElement(ADDRESS_ELEMENT);
				
				Element name = doc.createElement(NAME_ELEMENT);
				name.setTextContent(address.getHostName());
				
				Element ip = doc.createElement(IP_ELEMENT);
				ip.setTextContent(address.getHostAddress());
				
				addrNode.appendChild(ip);
				addrNode.appendChild(name);
				addr.appendChild(addrNode);
			}

			root.appendChild(addr);
			root.appendChild(dev);
		}

		doc.appendChild(root);
		return doc;
	}
	
	private final static String REQUEST_ELEMENT 	= "request";
	private final static String ADDRESSES_ELEMENT 	= "addresses";
	private final static String DEVICES_ELEMENT 	= "devices";
	private final static String ADDRESS_ELEMENT 	= "address";
	private final static String DEVICE_ELEMENT 		= "device";
	private final static String NAME_ELEMENT 		= "name";
	private final static String IP_ELEMENT 			= "ip";
	
	private final static String FS_ELEMENT 		= "fs";
}
