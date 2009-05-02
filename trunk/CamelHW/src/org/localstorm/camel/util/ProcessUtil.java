package org.localstorm.camel.util;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultConsumer;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ProcessUtil 
{
    public static void process(Exchange ex, Iterable<DefaultConsumer> cons)
            throws Exception  {

        for (DefaultConsumer con : cons) {
            con.getProcessor().process(ex);
        }
    }
}
