package org.localstorm.stocktracker.rest.parsers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.localstorm.stocktracker.exchange.StockEvent;
import org.localstorm.stocktracker.exchange.StockEventType;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;

/**
 * TODO!
 * @author Alexey Kuznetsov
 */
public class StockPriceRequestParser implements XmlStreamParser<StockTrackingRequest>
{
    private final int maxEvents;

    public StockPriceRequestParser(int maxEvents)
    {
        this.maxEvents = maxEvents;
    }

    public StockTrackingRequest parse(XMLStreamReader reader)
            throws XMLStreamException
    {
        //TODO: using XmlStreamReader..

        StockTrackingRequest request = new StockTrackingRequest();
        request.setAccount("localstorm");
        Calendar c = Calendar.getInstance();

        c.add(Calendar.SECOND, 10);
        Date end = c.getTime();

        request.addEvent(new StockEvent(StockEventType.RAISE, "MSFT", new BigDecimal("10.1"), null, end));

        return request;
    }

}
