package org.localstorm.stocktracker.camel.pricetracker;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.localstorm.stocktracker.camel.GenericConsumerableEndpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocktracker.camel.util.ProcessUtil;
import org.localstorm.stocktracker.exchange.StockEventType;
import org.localstorm.stocktracker.exchange.StockPrice;
import org.localstorm.stocktracker.exchange.StockPriceRequest;

/**
 * @in StockPriceRequest instances
 * @out StockPriceRequest instances
 * @author Alexey Kuznetsov
 */
public class PriceTrackerEndpoint extends GenericConsumerableEndpoint<DefaultExchange>
{
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
     * according price history. If there is no history for given
     * <code>price.getSymbol()</code> this method appends new price to price history.
     *
     * @param price New stock price
     * @return price with specified <code>StockEventType</code> or <code>null</code> if price not changed
     */
    private StockPrice handleNewStockPrice(StockPrice price) throws Exception
    {

        StockPrice  oldPrice = prices.put(price.getSymbol(), price);

        if ( oldPrice!=null ) {
            BigDecimal oldValue = oldPrice.getPrice();
            BigDecimal newValue = price.getPrice();

            StockEventType type = this.types[oldValue.compareTo(newValue)+1];
            price.setType(type);

            // Some of our stock prices suppliers may send unchanged data
            // So, we must check it.
            if (type!=null) {
                return price;
            }
        }

        return null;
    }

    /* package */ void handleNewStockPriceRequest(StockPriceRequest priceRequest)
            throws Exception
    {
        StockPriceRequest spr = new StockPriceRequest();

        for (StockPrice price : priceRequest.getPriceList()) {
            StockPrice sp = this.handleNewStockPrice(price);
            if (sp!=null) {
                spr.addPrice(sp);
            }
        }

        if (spr.getPricesCount()>0) {
            ProcessUtil.process(spr, this.getCamelContext(), this.getConsumers());
        }
    }

}
