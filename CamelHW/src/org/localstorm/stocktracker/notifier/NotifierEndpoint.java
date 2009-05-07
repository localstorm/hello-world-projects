package org.localstorm.stocktracker.notifier;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocktracker.exchange.AnalyzerInstruction;

/**
 * @in TODO!!!  
 * @out TODO!!! 
 * @author Alexey Kuznetsov
 */
public class NotifierEndpoint extends DefaultEndpoint<DefaultExchange>
{

    public NotifierEndpoint(String uri, NotifierComponent component)
    {
        super(uri, component);
    }

    public NotifierEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Consumer<DefaultExchange> createConsumer(Processor arg0)
            throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Producer<DefaultExchange> createProducer()
            throws Exception
    {
        return new NotifierProducer(this);
    }

    public boolean isSingleton()
    {
        return true;
    }

}
