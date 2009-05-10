package org.localstorm.stocktracker.camel.analyzer;

import org.localstorm.stocktracker.camel.GenericConsumerableEndpoint;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.exchange.*;

/**
 * @in TODO!!!  (stock prices)
 * @out TODO!!! (notifications)
 * @author Alexey Kuznetsov
 */
public class AnalyzerEndpoint extends GenericConsumerableEndpoint<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(AnalyzerEndpoint.class);
    
    private final RulesModel model = new RulesModel();
    
    public AnalyzerEndpoint(String uri, AnalyzerComponent component) {
        super(uri, component);
    }

    public AnalyzerEndpoint(String endpointUri) {
        super(endpointUri);
    }

    /*package*/ void appendRule(AnalyzerInstruction ai)
    {
        if (log.isDebugEnabled()) {
            // That's a heavyweight string-generating method
            log.debug("Appending rule: "+ai);
        }

        this.model.addRule(ai.getSymbol(),
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
        
        this.model.removeRule(ai.getSymbol(),
                              ai.getStockChangeType(),
                              ai.getThreshold(),
                              ai.getAccount());
    }

    public Producer<DefaultExchange> createProducer()
            throws Exception
    {
        return new AnalyzerProducer(this);
    }

    public boolean isSingleton()
    {
        return true;
    }

}
