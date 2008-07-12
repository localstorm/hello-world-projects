package org.localstorm.java2html;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSource;

import java.io.StringWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * User: Kuznetsov Alexey
 * Date: Dec 27, 2005
 * Time: 1:10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args)
    {
        //Create a reader of the raw input text

        //Parse the raw text to a JavaSource object
        JavaSource source = null;
        try {
            FileInputStream fis = new FileInputStream("input.java");
            source = new JavaSourceParser().parse(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //Create a converter and write the JavaSource object as Html
        JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter(source);
        StringWriter writer = new StringWriter();
        try {
          converter.convert(new OutputStreamWriter(new FileOutputStream("output.html")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(writer.toString());

    }

}
