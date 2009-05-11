package org.localstorm.stocktracker.rest.parsers;

import org.localstorm.stocktracker.exchange.StockPrice;
import java.math.BigDecimal;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.localstorm.stocktracker.exchange.StockPriceRequest;
import static org.localstorm.stocktracker.util.misc.Guard.*;


/**
 * High performance and low memory footprint prices XML request reader.
 * That is not very easy to maintain such parser but it is much faster
 * than any DOM-based parsers (JAXB, XPath-processors...)
 *
 * @author Alexey Kuznetsov
 */
public class StockPriceRequestParser extends GenericParser<StockPriceRequest>
{
    public static final String DATETIME_FORMAT        = "yyyy-MM-dd HH:mm:ss";

    // Error messages
    public static final String PRICE_NOT_FOUND        = "/prices/item@price attribute not found";
    public static final String SYMBOL_NOT_FOUND       = "/prices/item@symbol attribute not found";

    // Tag names
    public static final String PRICES_TAG           = "prices";
    public static final String ITEM_TAG             = "item";

    public static final String ITEM_PRICE_ATTR      = "price";
    public static final String ITEM_SYMBOL_ATTR     = "symbol";
    
    private final int maxIssuers;

    public StockPriceRequestParser(int maxIssuers)
    {
        this.maxIssuers  = maxIssuers;
    }

    public StockPriceRequest parse(XMLStreamReader reader)
            throws XMLStreamException
    {
        StockPriceRequest spr = new StockPriceRequest();

        while (reader.hasNext()) {

            if (XMLStreamReader.START_ELEMENT==reader.next()){
                String tagName = reader.getName().toString();

                if (ITEM_TAG.equals(tagName)) {
                    this.addItem(spr, maxIssuers, reader);
                    continue;
                }

                if (PRICES_TAG.equals(tagName)) {
                    continue;
                }

                throw new XMLStreamException("Unknown XML tag found: "+tagName);
            }
        }

        return spr;
    }


    private void addItem(StockPriceRequest str,
                         int maxIssuers,
                         XMLStreamReader reader) throws XMLStreamException
    {
        if (str.getPricesCount()>=maxIssuers) {
            throw new XMLStreamException("Too many items in request! [>"+maxIssuers+"]");
        }

        try {

            String price  = reader.getAttributeValue(null, ITEM_PRICE_ATTR);
            String symbol = reader.getAttributeValue(null, ITEM_SYMBOL_ATTR);

            checkNotNull(price,  XMLStreamException.class, PRICE_NOT_FOUND);
            checkNotNull(symbol, XMLStreamException.class, SYMBOL_NOT_FOUND);

            BigDecimal _price = new BigDecimal(price);

            StockPrice sp = new StockPrice(symbol, _price);
            str.addPrice(sp);

        } catch(NumberFormatException e) {
            throw new XMLStreamException(e);
        } catch(IllegalArgumentException e) {
            throw new XMLStreamException(e);
        }
    }
}
