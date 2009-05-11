package org.localstorm.stocktracker.camel.util;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultExchange;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ProcessUtil 
{
    public static void process(Exchange ex,
                               Iterable<DefaultConsumer> cons) throws Exception  {

        for (DefaultConsumer con : cons) {
            con.getProcessor().process(ex);
        }
    }

    public static void process(Object o,
                               CamelContext ctx,
                               Iterable<DefaultConsumer> cons) throws Exception  {

        DefaultExchange de = new DefaultExchange(ctx);
        de.getIn().setBody(o);

        for (DefaultConsumer con : cons) {
            con.getProcessor().process(de);
        }
    }
}
