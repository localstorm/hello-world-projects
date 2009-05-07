package org.localstorm.stocktracker.scheduler;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.localstorm.stocktracker.util.ExchangeUtil;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TrackingSchedulerProducer extends DefaultProducer<DefaultExchange>
{

    public TrackingSchedulerProducer(TrackingSchedulerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception {
        TrackingSchedulerEndpoint ep     = this.getEndpoint();
        StockTrackingRequest str = ExchangeUtil.inBody(exchange);
        ep.scheduleTracking(str);
    }

    @Override
    public TrackingSchedulerEndpoint getEndpoint() {
        return (TrackingSchedulerEndpoint) super.getEndpoint();
    }

}
