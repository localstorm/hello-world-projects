package org.localstorm.stocktracker.camel.pricetracker;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.localstorm.stocktracker.camel.GenericConsumerableEndpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.camel.util.ProcessUtil;
import org.localstorm.stocktracker.exchange.StockEventType;
import org.localstorm.stocktracker.exchange.StockPrice;
import org.localstorm.stocktracker.exchange.StockPriceRequest;

/**
 * @in TODO!!!  (stock prices)
 * @out TODO!!! (notifications)
 * @author Alexey Kuznetsov
 */
public class PriceTrackerEndpoint extends GenericConsumerableEndpoint<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(PriceTrackerEndpoint.class);
    
    // Key: symbol
    private final ConcurrentMap<String, StockPrice> prices = new ConcurrentHashMap<String, StockPrice>();
    private final StockEventType[] types;
    
    public PriceTrackerEndpoint(String uri, PriceTrackerComponent component) {
        super(uri, component);

        this.types = new StockEventType[]{ StockEventType.RAISE, null, StockEventType.FALL };
    }

    public Producer<DefaultExchange> createProducer() throws Exception {
        return new PriceTrackerProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    // Some business

    /**
     * Appends <code>StockEventType</code> by calling <code>price.setType(..)</code>
     * according price history. Then it resends <code>StockPrice</code> to consumers.
     * If there is no history for given <code>price.getSymbol()</code>
     * this method appends new price to price history.
     *
     * @param price New stock price
     */
    /* package */ void handleNewStockPrice(StockPrice price)
            throws Exception
    {

        StockPrice  oldPrice = prices.put(price.getSymbol(), price);

        if ( oldPrice!=null ) {
            BigDecimal oldValue = oldPrice.getPrice();
            BigDecimal newValue = price.getPrice();

            StockEventType type = this.types[oldValue.compareTo(newValue)+1];
            price.setType(type);

            if ( type!=null ) {
                // We resend this to analyzer if price have changed
                ProcessUtil.process(price, this.getCamelContext(), this.getConsumers());
            }
        }
    }

    /* package */ void handleNewStockPriceRequest(StockPriceRequest priceRequest)
            throws Exception
    {
        for (StockPrice price : priceRequest.getPriceList()) {
            this.handleNewStockPrice(price);
        }
    }

}
