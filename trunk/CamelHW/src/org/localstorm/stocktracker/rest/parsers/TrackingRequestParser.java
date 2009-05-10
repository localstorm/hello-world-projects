package org.localstorm.stocktracker.rest.parsers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.localstorm.stocktracker.exchange.StockEvent;
import org.localstorm.stocktracker.exchange.StockEventType;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;
import static org.localstorm.stocktracker.util.misc.Guard.*;


/**
 * High performance and low memory footprint tracking XML request reader.
 * That is not very easy to maintain such parser but it is much faster
 * than any DOM-based parsers (JAXB, XPath-processors...)
 *
 * @author Alexey Kuznetsov
 */
public class TrackingRequestParser extends GenericParser<StockTrackingRequest>
{
    public static final String DATETIME_FORMAT        = "yyyy-MM-dd HH:mm:ss";

    // Error messages
    public static final String TYPE_NOT_FOUND         = "/request/event@type attribute not found";
    public static final String PRICE_NOT_FOUND        = "/request/event@price attribute not found";
    public static final String SYMBOL_NOT_FOUND       = "/request/event@symbol attribute not found";
    public static final String END_NOT_FOUND          = "/request/event@end attribute not found";

    public static final String REQUEST_NAME_NOT_FOUND = "Required attribute /request@name not found!";
    public static final String TAG_EVENT_IS_BEFORE_REQUEST_TAG = "Tag 'event' is before 'request' tag";
    
    // Tag names
    public static final String REQUEST_TAG          = "request";
    public static final String EVENT_TAG            = "event";

    public static final String ACCOUNT_ATTR         = "name";
    public static final String EVENT_END_ATTR       = "end";
    public static final String EVENT_START_ATTR     = "start";
    public static final String EVENT_THRESHOLD_ATTR = "price";
    public static final String EVENT_TYPE_ATTR      = "type";
    public static final String EVENT_SYMBOL_ATTR    = "symbol";
    
    // SimpleDateFormat is not ThreadSafe. Can't use static
    private final SimpleDateFormat dateFormat;
    private final int maxEvents;

    public TrackingRequestParser(int maxEvents)
    {
        this.dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        this.maxEvents  = maxEvents;
    }

    public StockTrackingRequest parse(XMLStreamReader reader)
            throws XMLStreamException
    {
        StockTrackingRequest str = new StockTrackingRequest();

        String account = null;

        while (reader.hasNext()) {

            if (XMLStreamReader.START_ELEMENT==reader.next()){
                String tagName = reader.getName().toString();

                if (EVENT_TAG.equals(tagName)) {
                    checkNotNull(account, XMLStreamException.class, TAG_EVENT_IS_BEFORE_REQUEST_TAG);

                    this.addEvent(str, maxEvents, reader);
                    continue;
                }

                if (REQUEST_TAG.equals(tagName)) {
                    account = reader.getAttributeValue(null, ACCOUNT_ATTR);
                    checkNotNull(account, XMLStreamException.class, REQUEST_NAME_NOT_FOUND);

                    str.setAccount(account);
                    continue;
                }

                throw new XMLStreamException("Unknown XML tag found: "+tagName);
            }
        }

        return str;
    }


    private void addEvent(StockTrackingRequest str,
                          int maxEvents,
                          XMLStreamReader reader) throws XMLStreamException
    {
        if (str.eventCount()>=maxEvents) {
            throw new XMLStreamException("Too many tracking events in request! [>"+maxEvents+"]");
        }

        try {

            String type   = reader.getAttributeValue(null, EVENT_TYPE_ATTR);
            String price  = reader.getAttributeValue(null, EVENT_THRESHOLD_ATTR);
            String symbol = reader.getAttributeValue(null, EVENT_SYMBOL_ATTR);
            String start  = reader.getAttributeValue(null, EVENT_START_ATTR);
            String end    = reader.getAttributeValue(null, EVENT_END_ATTR);

            checkNotNull(type,   XMLStreamException.class, TYPE_NOT_FOUND);
            checkNotNull(price,  XMLStreamException.class, PRICE_NOT_FOUND);
            checkNotNull(symbol, XMLStreamException.class, SYMBOL_NOT_FOUND);
            checkNotNull(end,    XMLStreamException.class, END_NOT_FOUND);

            StockEventType   set = StockEventType.valueOf(type.toUpperCase());
            BigDecimal threshold = new BigDecimal(price);
            Date _start          = super.parseDate(dateFormat, start);
            Date _end            = super.parseDate(dateFormat, end);

            StockEvent se = new StockEvent(set, symbol, threshold, _start, _end);
            str.addEvent(se);

        } catch(NumberFormatException e) {
            throw new XMLStreamException(e);
        } catch(IllegalArgumentException e) {
            throw new XMLStreamException(e);
        } catch(ParseException e) {
            throw new XMLStreamException(e);
        }
    }
}
