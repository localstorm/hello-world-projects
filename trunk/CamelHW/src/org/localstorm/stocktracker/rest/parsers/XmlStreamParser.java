package org.localstorm.stocktracker.rest.parsers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface XmlStreamParser<T>
{
    public T parse(XMLStreamReader reader) throws XMLStreamException;
}
