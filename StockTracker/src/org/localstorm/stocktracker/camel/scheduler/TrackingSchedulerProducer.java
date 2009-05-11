package org.localstorm.stocktracker.camel.scheduler;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.camel.util.ExchangeUtil;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TrackingSchedulerProducer extends DefaultProducer<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(TrackingSchedulerProducer.class);
    
    public TrackingSchedulerProducer(TrackingSchedulerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange){
        try {
            TrackingSchedulerEndpoint ep  = this.getEndpoint();
            StockTrackingRequest      str = ExchangeUtil.inBody(exchange);
            ep.scheduleTracking(str);
        } catch(Exception e) {
            log.error(e);
            exchange.getFault().setBody(e);
        }
    }

    @Override
    public TrackingSchedulerEndpoint getEndpoint() {
        return (TrackingSchedulerEndpoint) super.getEndpoint();
    }

}
