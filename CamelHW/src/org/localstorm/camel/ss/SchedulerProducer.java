package org.localstorm.camel.ss;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.localstorm.stocks.tracker.StockTrackingRequest;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SchedulerProducer extends DefaultProducer<DefaultExchange>
{

    public SchedulerProducer(SchedulerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception
    {
        StockTrackingRequest str = (StockTrackingRequest) exchange.getIn().getBody();

        
        System.out.println("SchedulerProducer:process("+str+");");
    }

    @Override
    public SchedulerEndpoint getEndpoint() {
        System.out.println("SchedulerProducer:getEndPoint()");
        return (SchedulerEndpoint) super.getEndpoint();
    }

}
