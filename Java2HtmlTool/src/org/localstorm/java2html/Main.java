package org.localstorm.java2html;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSource;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.properties.ConversionOptionsPropertiesReader;

import java.io.*;
import java.util.Properties;

/**
 * User: Kuznetsov Alexey
 * Date: Dec 27, 2005
 * Time: <b>1:10:50</b> PM
 * @since 1.0
 */
@SuppressWarnings("yea")
public class Main {

    public static void main(String[] args) throws Exception
    {
        if (args.length != 2) {
            System.err.println("Usage: <Java-source-file> <HTML-output-file>");
            return;
        }

        //Parse the raw text to a JavaSource object
        JavaSource source = null;
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            source = new JavaSourceParser().parse(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();
        InputStream fis = null;
        OutputStreamWriter osw = null;

        char a  = 'g';
        try {
            ConversionOptionsPropertiesReader reader = new ConversionOptionsPropertiesReader();
            Properties props = new Properties();

            fis = new FileInputStream(System.getProperty("stylesheet"));
            props.load(fis);

            osw = new OutputStreamWriter(new FileOutputStream(args[1]));
            JavaSourceConversionOptions opts = reader.read(props);
            converter.convert(source, opts, osw);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis!=null) {
                fis.close();
            }
            if (osw!=null) {
                osw.close();
            }
        }
    }

}
