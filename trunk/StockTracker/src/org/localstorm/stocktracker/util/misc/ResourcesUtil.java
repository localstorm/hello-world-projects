package org.localstorm.stocktracker.util.misc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ResourcesUtil 
{
    public static Properties loadPropertiesResource(String resourceName) throws IOException
    {
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);

            if ( is==null ) {
                throw new IOException("Invalid resource name: ["+resourceName+"]");
            }

            Properties p = new Properties();
            p.load(is);

            return p;
        } finally {
            if (is!=null) {
                is.close();
            }
        }
    }

    public static Schema loadSchemaResource(String resourceName) throws IOException
    {
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);

            if ( is==null ) {
                throw new IOException("Invalid resource name: ["+resourceName+"]");
            }

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema    schema = sf.newSchema(new StreamSource(is));


            return schema;
        } catch(SAXException e) {
            throw new IOException(e);
        } finally {
            if (is!=null) {
                is.close();
            }
        }
    }
}
