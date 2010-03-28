package com.company;

import java.io.FileReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathEvaluation {

	public static void main(String[] args) throws Exception {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		// Getting string note for Tatu
		XPathExpression expr = xpath.compile("//artist[@name='Tatu']/text()");
		FileReader r = new FileReader("artists.xml");
		String tatuNode = (String) expr.evaluate(new InputSource(r),
				XPathConstants.STRING);
		System.out.println(tatuNode);

		// Getting all artists
		expr = xpath.compile("//artist");
		r = new FileReader("artists.xml");
		NodeList artists = (NodeList) expr.evaluate(new InputSource(r),
				XPathConstants.NODESET);
		r.close();

		System.out.println("------------");
		dumpArtists(artists);
	}

	private static void dumpArtists(NodeList artists) throws Exception {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression nameExp = xpath.compile("@name");
		for (int i = 0; i < artists.getLength(); i++) {
			Node artist = artists.item(i);
			System.out.println(nameExp.evaluate(artist)+": "+artist.getTextContent());
		}
	}
}
