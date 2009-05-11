package org.localstorm.stocktracker.rest.parsers;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.util.io.LimitedInputStream;

/**
 * helper class to provide XML-request size constraints
 * @author Alexey Kuznetsov
 */
public class ObjectXmlReader<T>
{
    private static final Log log = LogFactory.getLog(ObjectXmlReader.class);
    
    private final InputStream is;

    public ObjectXmlReader(InputStream is, long maxRequestSize) {
        this.is        = new LimitedInputStream(is, maxRequestSize);
    }

    public T getObject(XmlStreamParser<T> xsp) throws XMLStreamException {
        XMLStreamReader reader = null;
        
        try{
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            reader = inputFactory.createXMLStreamReader(this.is);
        
            return xsp.parse(reader);
        } finally {
            if (reader!=null) {
                reader.close();
            }
        }
    }

    public void close() {
        try {
            this.is.close();
        } catch( IOException e ) {
            log.error(e);
        }
    }

    
}
