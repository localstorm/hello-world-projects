/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.swsoft.trial.common.ThreadSafe;
import com.swsoft.trial.util.ResourceLoader;

/**
 * This class is used to parse config.xml file using XPath 
 * 
 * @author Kuznetsov A.
 * @version 1.0
 */
@ThreadSafe
public class Configurator {
	
	/**
	 * Verifies and parses given config file
	 * @param file Configuration XML file
	 */
	public Configurator(File file) throws IOException {
		try{
			
			Schema s = ResourceLoader.loadSchema(SCHEMA_RESOURCE);
			Validator validator = s.newValidator(); 
			validator.validate(new StreamSource(file));
			
			// Now it is ok, processing using XPath
			// Here we can use Jakarta Commons Configuration, but in this
			// case we'll need a number of common libs.
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			process(db.parse(file));
			
		} catch(Exception e) {
			throw (IOException)(new IOException().initCause(e));
		}
	}
	
	public boolean isHostRegistered(String address) {
		return (hostMap.get(address)!=null);
	}
	
	public Collection<String> getAllovedFileSystems(String address) {
		Collection<String> res = hostMap.get(address);
		
		if (res==null) {
			res = new ArrayList<String>(0);
		}
		
		return Collections.unmodifiableCollection(res);
	}
	
	public int getMaxRequestSize(){
		return maxRequest;
	}

	public int getPort() {
		return port;
	}
	
	public int getPoolSize() {
		return pool;
	}
	
	public int getQueueSize() {
		return queue;
	}
	
	private void process(Document doc) throws IOException {
		try {

			// XPath here
			Node _prt = XPathAPI.selectSingleNode(doc, PORT_EXPR);
			Node _req = XPathAPI.selectSingleNode(doc, MAX_EXPR);
			Node _que = XPathAPI.selectSingleNode(doc, QUEUE_EXPR);
			Node _tds = XPathAPI.selectSingleNode(doc, POOL_EXPR);
		
			port 		= Integer.parseInt(_prt.getTextContent());
			maxRequest 	= Integer.parseInt(_req.getTextContent());
			pool		= Integer.parseInt(_tds.getTextContent());
			queue		= Integer.parseInt(_que.getTextContent());

			//			Parsing hosts record
			NodeList hosts 	= XPathAPI.selectNodeList(doc, HOSTS_EXPR);
			
			hostMap	= new HashMap<String, Collection<String>>(hosts.getLength());
			for (int i = hosts.getLength()-1; i>=0; i--) {
				Node hostNode = hosts.item(i);;
				
				Node address = XPathAPI.selectSingleNode(hostNode, ADDR_EXPR);
				NodeList allowed = XPathAPI.selectNodeList(hostNode, FS_EXPR);

				Collection<String> fs = new ArrayList<String>(allowed.getLength());
				
				if (hostMap.put(address.getTextContent(), fs)!=null){
					throw new IOException("Duplicated host configuration record with address = "+address);
				}
				
				for (int j=allowed.getLength()-1; j>=0; j--) {
					fs.add(allowed.item(j).getTextContent());
				}
			}
			
		} catch(TransformerException e) {
			throw (IOException)(new IOException().initCause(e));
		}
	}
	
	private int port;
	private int pool;
	private int queue;
	private int maxRequest;
	private Map<String, Collection<String>> hostMap;
	
	private final static String SCHEMA_RESOURCE = "config.xsd";
	
	private final static String PORT_EXPR 	= "/server/conf/port";
	private final static String POOL_EXPR 	= "/server/conf/threads"; 
	private final static String QUEUE_EXPR 	= "/server/conf/queue";
	private final static String MAX_EXPR 	= "/server/conf/maxRequest";
	private final static String HOSTS_EXPR	= "/server/hosts/host";
	private final static String FS_EXPR		= "allowed";
	private final static String ADDR_EXPR	= "@addr";
}

