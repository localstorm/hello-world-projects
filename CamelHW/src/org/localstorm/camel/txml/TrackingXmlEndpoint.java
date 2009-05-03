package org.localstorm.camel.txml;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocks.tracker.StockEvent;
import org.localstorm.stocks.tracker.StockChangeType;
import org.localstorm.stocks.tracker.StockTrackingRequest;

/**
 * @in  XML (Stock racking Request format)
 * @out StockTrackingRequest instance
 * @author Alexey Kuznetsov
 */
public class TrackingXmlEndpoint extends DefaultEndpoint<DefaultExchange>
{
    private List<DefaultConsumer> consumers = new CopyOnWriteArrayList<DefaultConsumer>();

    public TrackingXmlEndpoint(String endpointUri,
                                   TrackingXmlComponent component) {
        super(endpointUri, component);
    }

    public TrackingXmlEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public boolean isSingleton() {
        return true;
    }

    @Override
    public TrackingXmlComponent getComponent() {
        return (TrackingXmlComponent) super.getComponent();
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
        return new TrackingXmlProducer(this);
    }

    /*package*/ StockTrackingRequest parseXmlRequest(String xml) {
        System.out.println("XML to be parsed:"+xml);

        StockTrackingRequest str = new StockTrackingRequest("localstorm");
        Calendar c = Calendar.getInstance();

        c.add(Calendar.SECOND, 10);
        Date end = c.getTime();

        str.addEvent(new StockEvent(StockChangeType.RAISE, "MSFT", new BigDecimal("10.1"), null, end));
        return str;
    }

    /*package*/ Collection<DefaultConsumer> getConsumers() {
        return Collections.unmodifiableCollection(consumers);
    }

}
