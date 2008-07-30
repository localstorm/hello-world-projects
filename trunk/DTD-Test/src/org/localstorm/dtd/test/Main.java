/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.dtd.test;

import java.io.File;
import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        SAXParserFactory f = SAXParserFactory.newInstance();
        f.setValidating(true); // Default is false
        f.setFeature("http://apache.org/xml/features/validation/schema", true);

        SAXParser p = f.newSAXParser();


        
        p.parse(new File("input.80030.xml"), new DefaultHandler(){

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException
            {
                System.out.println(qName);
            }

            @Override
            public void error(SAXParseException e)
                    throws SAXException
            {
                e.printStackTrace();
            }
            
        });
        
        System.out.println("PARSED!");
    }

}
