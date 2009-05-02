package org.localstorm.camel.th;


import java.util.Collection;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.localstorm.camel.ExchangeFactory;
import org.localstorm.camel.util.ProcessUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TrackingProducer extends DefaultProducer<DefaultExchange>
{

    public TrackingProducer(TrackingHandlerEndpoint endpoint) {
        super(endpoint);
    }

    public void process(Exchange exchange) throws Exception
    {
        Object request = exchange.getIn().getBody();

        System.out.println("TrackingRequestProducer:process("+request+");");
        Object result = this.getEndpoint().parseXmlRequest(request.toString());

        TrackingHandlerEndpoint ep = this.getEndpoint();

        Collection<DefaultConsumer> consumers = ep.getConsumers();

        if (consumers.isEmpty()) {
            System.out.println("No consumers to retransmit...");
            return;
        }
        
        DefaultExchange se = ExchangeFactory.inOnly(ep, result);
        ProcessUtil.process(se, ep.getConsumers());
    }

    @Override
    public TrackingHandlerEndpoint getEndpoint() {
        return (TrackingHandlerEndpoint) super.getEndpoint();
    }

}
