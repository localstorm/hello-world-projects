package org.localstorm.stocktracker.rest.parsers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Generic low-level XML API parser
 * @author Alexey Kuznetsov
 */
public interface XmlStreamParser<T>
{
    /**
     * Interprets given stream as <code>&lt;T&gt;</code> object. It doesn't close
     * given <code>XmlStreamReader</code>
     * @param reader XmlStreamReader instance
     * @return <code>&lt;T&gt;</code> instance
     * @throws javax.xml.stream.XMLStreamException On any error
     */
    public T parse(XMLStreamReader reader) throws XMLStreamException;
}
