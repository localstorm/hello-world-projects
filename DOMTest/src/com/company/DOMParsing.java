package com.company;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMParsing {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("input.xml"));
		dumpDOMTree(doc, 0);
	}

	private static void dumpDOMTree(Node node, int level) {
		indent(level);
		System.out.println(node.getNodeName());
		NodeList nl = node.getChildNodes();
		dumpAttributes(node, level);
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			dumpDOMTree(n, level + 1);
		}
	}

	private static void dumpAttributes(Node node, int level) {
		NamedNodeMap attrs = node.getAttributes();
		if (attrs == null) {
			return;
		}
		if (attrs.getLength() > 0) {
			indent(level);
			System.out.println("Attributes:");
		}
		
		for (int i = 0; i < attrs.getLength(); i++) {
			Node n = attrs.item(i);
			indent(level);
			System.out.println(n.getNodeName() + " = " + n.getTextContent());
		}
	}

	private static void indent(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("\t");
		}
	}

}
