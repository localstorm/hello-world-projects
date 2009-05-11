package org.localstorm.stocktracker.camel.pricetracker;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.camel.util.ExchangeUtil;
import org.localstorm.stocktracker.exchange.StockPriceRequest;

/**
 * @author Alexey Kuznetsov
 */
public class PriceTrackerProducer extends DefaultProducer<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(PriceTrackerProducer.class);

    public PriceTrackerProducer(PriceTrackerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        PriceTrackerEndpoint    ep = this.getEndpoint();

        StockPriceRequest priceRequest = ExchangeUtil.inBody(exchange);

        if (log.isDebugEnabled()) {
            log.debug("Price tracker received new stock price request: " + priceRequest);
        }

        ep.handleNewStockPriceRequest(priceRequest);
    }

    @Override
    public PriceTrackerEndpoint getEndpoint() {
        return (PriceTrackerEndpoint) super.getEndpoint();
    }

}
