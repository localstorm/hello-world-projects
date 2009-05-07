package org.localstorm.stocktracker.camel;

import org.apache.camel.Producer;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ProducerUtil 
{

    public static void stopQuietly(Producer<?> p ) {
        try {
            p.stop();
        } catch(Exception e) {
            // TODO: logging?
        }
    }
}
