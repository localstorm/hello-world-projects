package org.localstorm.camel;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class GenericConsumerableEndpoint<T extends Exchange> extends DefaultEndpoint<T>
{
    private final List<DefaultConsumer> consumers = new CopyOnWriteArrayList<DefaultConsumer>();

    public abstract Producer<T> createProducer() throws Exception;

    public GenericConsumerableEndpoint(String uri, Component component) {
        super(uri, component);
    }

    public GenericConsumerableEndpoint(String uri) {
        super(uri);
    }

    public Consumer<T> createConsumer(Processor p)
            throws Exception
    {
         return new DefaultConsumer<T>(this, p) {

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

    public List<DefaultConsumer> getConsumers()
    {
        return Collections.unmodifiableList(consumers);
    }

}
