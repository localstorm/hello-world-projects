package org.localstorm.camel.ss;

import org.apache.camel.Consumer;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocks.tracker.StockTrackingRequest;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SchedulerEndpoint extends DefaultEndpoint<DefaultExchange>
{
    public SchedulerEndpoint(String endpointUri,
                             SchedulerComponent component) {
        super(endpointUri, component);
    }

    public SchedulerEndpoint(String endpointUri, Object some) {
        super(endpointUri);
    }

    @Override
    public SchedulerComponent getComponent() {
        return (SchedulerComponent) super.getComponent();
    }

    public Consumer<DefaultExchange> createConsumer(Processor p) throws Exception
    {
        throw new UnsupportedOperationException("You cannot recieve messages from this endpoint");
    }

    public Producer<DefaultExchange> createProducer() throws Exception
    {
        //throw new UnsupportedOperationException("You cannot recieve messages from this endpoint");
        return new SchedulerProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    
}
