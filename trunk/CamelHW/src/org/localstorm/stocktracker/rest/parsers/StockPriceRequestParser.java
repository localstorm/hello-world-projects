package org.localstorm.stocktracker.rest.parsers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.localstorm.stocktracker.exchange.StockPriceRequest;

/**
 * TODO!
 * @author Alexey Kuznetsov
 */
public class StockPriceRequestParser implements XmlStreamParser<StockPriceRequest>
{
    private final int maxEvents;

    public StockPriceRequestParser(int maxEvents)
    {
        this.maxEvents = maxEvents;
    }

    public StockPriceRequest parse(XMLStreamReader reader)
            throws XMLStreamException
    {
        //TODO:!
        return null;
    }

}
