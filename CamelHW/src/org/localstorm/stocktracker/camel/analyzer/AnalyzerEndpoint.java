package org.localstorm.stocktracker.camel.analyzer;

import org.localstorm.stocktracker.exchange.Notification;
import java.util.Collection;
import java.util.List;
import org.apache.camel.CamelContext;
import org.localstorm.stocktracker.camel.GenericConsumerableEndpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.camel.util.ProcessUtil;
import org.localstorm.stocktracker.exchange.*;

/**
 * @in TODO!!!  (stock prices)
 * @out TODO!!! (notifications)
 * @author Alexey Kuznetsov
 */
public class AnalyzerEndpoint extends GenericConsumerableEndpoint<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(AnalyzerEndpoint.class);
    
    private final AnalyzerCore core = new AnalyzerCore();
    
    public AnalyzerEndpoint(String uri, AnalyzerComponent component) {
        super(uri, component);
    }

    public AnalyzerEndpoint(String endpointUri) {
        super(endpointUri);
    }

    /*package*/ void analyzeNewPrices(StockPriceRequest spr) {
        Collection<Notification> ntfs = core.getFiredNotifications(spr);

        CamelContext ctx = this.getCamelContext();
        List<DefaultConsumer> cons = this.getConsumers();

        for ( Notification n: ntfs ) {
            try {
                ProcessUtil.process(n, ctx, cons);
            } catch(Exception e) {
                log.error("Unable to deliver notification: ", e);
            }
        }
    }

    /*package*/ void appendRule(AnalyzerInstruction ai) {

        if (log.isDebugEnabled()) {
            // That's a heavyweight string-generating method
            log.debug("Appending rule: "+ai);
        }

        this.core.addRule(ai.getSymbol(),
                           ai.getStockChangeType(),
                           ai.getThreshold(),
                           ai.getAccount());
    }

    /*package*/ void removeRule(AnalyzerInstruction ai)
    {
        if (log.isDebugEnabled()) {
            // That's a heavyweight string-generating method
            log.debug("Removing rule: "+ai);
        }
        
        this.core.removeRule(ai.getSymbol(),
                              ai.getStockChangeType(),
                              ai.getThreshold(),
                              ai.getAccount());
    }

    public Producer<DefaultExchange> createProducer()
            throws Exception {
        return new AnalyzerProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

}
