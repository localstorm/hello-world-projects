package org.localstorm.stocktracker.camel.notifier;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocktracker.exchange.NotificationsChunk;

/**
 * @in TODO!!!  
 * @out TODO!!! 
 * @author Alexey Kuznetsov
 */
public class NotifierEndpoint extends DefaultEndpoint<DefaultExchange>
{

    public NotifierEndpoint(String uri, NotifierComponent component) {
        super(uri, component);
    }

    public Consumer<DefaultExchange> createConsumer(Processor arg0) throws Exception
    {
        throw new UnsupportedOperationException("You can not consume messages from this endpoint");
    }

    public Producer<DefaultExchange> createProducer() throws Exception {
        return new NotifierProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    @Override
    public NotifierComponent getComponent() {
        return (NotifierComponent) super.getComponent();
    }

    // Some business

    public synchronized void writeNotifications(NotificationsChunk chunk) {
        this.getComponent().writeNotifications(chunk);
    }

}
