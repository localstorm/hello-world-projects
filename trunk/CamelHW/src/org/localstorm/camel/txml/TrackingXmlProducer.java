package org.localstorm.camel.txml;


import java.util.Collection;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.localstorm.camel.ExchangeFactory;
import org.localstorm.camel.util.ProcessUtil;
import org.localstorm.stocktracker.exchange.StockTrackingRequest;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TrackingXmlProducer extends DefaultProducer<DefaultExchange>
{

    public TrackingXmlProducer(TrackingXmlEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception
    {
        Object request = exchange.getIn().getBody();

        System.out.println("TrackingRequestProducer:process("+request+");");

        StockTrackingRequest result = this.getEndpoint().parseXmlRequest(request.toString());

        TrackingXmlEndpoint ep = this.getEndpoint();

        Collection<DefaultConsumer> consumers = ep.getConsumers();

        if (consumers.isEmpty()) {
            System.out.println("No consumers to retransmit...");
            return;
        }
        
        DefaultExchange se = ExchangeFactory.inOnly(ep, result);
        ProcessUtil.process(se, ep.getConsumers());
    }

    @Override
    public TrackingXmlEndpoint getEndpoint() {
        return (TrackingXmlEndpoint) super.getEndpoint();
    }

}
