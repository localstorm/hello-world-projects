package org.localstorm.stocktracker.camel.analyzer;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.camel.util.ExchangeUtil;
import org.localstorm.stocktracker.exchange.StockPrice;
import org.localstorm.stocktracker.exchange.StockPriceRequest;

/**
 * @author Alexey Kuznetsov
 */
public class AnalyzerProducer extends DefaultProducer<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(AnalyzerProducer.class);
    
    public AnalyzerProducer(AnalyzerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        AnalyzerEndpoint    ep = this.getEndpoint();

        // Getting StockPriceRequest with changed prices
        StockPriceRequest spr = ExchangeUtil.inBody(exchange);
        
        if (log.isDebugEnabled()) {
            log.debug("Analyzer: "+spr);
        }

        for (StockPrice sp: spr.getPriceList()) {
            ep.analyzeNewPrice(sp);
        }
    }

    @Override
    public AnalyzerEndpoint getEndpoint() {
        return (AnalyzerEndpoint) super.getEndpoint();
    }

}
