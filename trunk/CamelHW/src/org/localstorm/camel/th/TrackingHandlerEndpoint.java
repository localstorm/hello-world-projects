package org.localstorm.camel.th;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.camel.Consumer;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocks.tracker.Event;
import org.localstorm.stocks.tracker.StockChangeType;
import org.localstorm.stocks.tracker.StockTrackingRequest;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TrackingHandlerEndpoint extends DefaultEndpoint<DefaultExchange>
{
    private List<DefaultConsumer> consumers = new CopyOnWriteArrayList<DefaultConsumer>();

    public TrackingHandlerEndpoint(String endpointUri,
                                   TrackingHandlerComponent component) {
        super(endpointUri, component);
    }

    public TrackingHandlerEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public boolean isSingleton() {
        return true;
    }

    @Override
    public TrackingHandlerComponent getComponent() {
        return (TrackingHandlerComponent) super.getComponent();
    }

    public Consumer<DefaultExchange> createConsumer(Processor processor) throws Exception {
        return new DefaultConsumer<DefaultExchange>(this, processor) {

            @Override
            public void start() throws Exception {
                consumers.add(this);
                super.start();
            }

            @Override
            public void stop() throws Exception {
                super.stop();
                consumers.remove(this);
            }
        };
    }

    public Producer<DefaultExchange> createProducer() throws Exception {
        return new TrackingProducer(this);
    }

    /*package*/ StockTrackingRequest parseXmlRequest(String xml) {
        System.out.println("XML to be parsed:"+xml);

        StockTrackingRequest str = new StockTrackingRequest("localstorm");
        str.watchEvent(new Event(StockChangeType.RAISE, "MSFT", new BigDecimal("10.1"), null, new Date()));
        return str;
    }

    /*package*/ Collection<DefaultConsumer> getConsumers() {
        return Collections.unmodifiableCollection(consumers);
    }

}
