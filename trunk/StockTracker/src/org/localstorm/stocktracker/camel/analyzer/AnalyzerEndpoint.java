package org.localstorm.stocktracker.camel.analyzer;

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
 * @in  StockPriceRequest instances
 * @out NotificationsChuck instances
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

    /*package*/ void analyzeNewPrice(StockPrice sp) {

        if (sp.getType()!=null) {

            NotificationsChunk chunk = core.getFiredNotifications(sp);

            if (!chunk.isEmpty()) {

                CamelContext ctx = this.getCamelContext();
                List<DefaultConsumer> cons = this.getConsumers();

                try {
                    ProcessUtil.process(chunk, ctx, cons);
                } catch(Exception e) {
                    log.error("Unable to deliver notification: ", e);
                }
                
            }
            
        } else {
            log.warn("Analyzer got 'null' stock event type. Skipping...");
        }
    }

    /*package*/ void appendRule(AnalyzerInstruction ai) {

        if (log.isDebugEnabled()) {
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
