package org.localstorm.stocktracker.rest.parsers;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.localstorm.stocktracker.util.io.LimitedInputStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ObjectReader<T>
{
    private final InputStream is;

    public ObjectReader(InputStream is, long maxRequestSize) {
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
            e.printStackTrace();
            //TODO: Logging
        }
    }
}
