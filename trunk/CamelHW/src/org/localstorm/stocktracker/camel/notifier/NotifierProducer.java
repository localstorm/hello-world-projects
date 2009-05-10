package org.localstorm.stocktracker.camel.notifier;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alexey Kuznetsov
 */
public class NotifierProducer extends DefaultProducer<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(NotifierProducer.class);

    public NotifierProducer(NotifierEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        NotifierEndpoint    ep = this.getEndpoint();

        //System.out.println("Notifier called: "+exchange.getIn().getBody());
    }

    @Override
    public NotifierEndpoint getEndpoint() {
        return (NotifierEndpoint) super.getEndpoint();
    }

}
