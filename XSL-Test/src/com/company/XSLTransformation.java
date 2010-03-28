package com.company;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTransformation {
	public static void main(String... args) throws Exception {
		File stylesheet = new File("style.xsd");
		File datafile = new File("artists.xml");

		StreamSource stylesource = new StreamSource(stylesheet);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(stylesource);
		
		Result out = new StreamResult(new File("output.html"));
		transformer.transform(new StreamSource(datafile), out );
	}
}
