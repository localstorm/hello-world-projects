package org.localstorm.stocktracker.notifier;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;

/**
 * @author Alexey Kuznetsov
 */
public class NotifierProducer extends DefaultProducer<DefaultExchange>
{

    public NotifierProducer(NotifierEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        NotifierEndpoint    ep = this.getEndpoint();
        System.out.println("Notifier called: "+exchange.getIn().getBody());
    }

    @Override
    public NotifierEndpoint getEndpoint() {
        return (NotifierEndpoint) super.getEndpoint();
    }

}
