package org.localstorm.stocktracker.camel.analyzer;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;

/**
 * @author Alexey Kuznetsov
 */
public class AnalyzerProducer extends DefaultProducer<DefaultExchange>
{

    public AnalyzerProducer(AnalyzerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        AnalyzerEndpoint    ep = this.getEndpoint();
        // Getting Stock events here
    }

    @Override
    public AnalyzerEndpoint getEndpoint() {
        return (AnalyzerEndpoint) super.getEndpoint();
    }

}
