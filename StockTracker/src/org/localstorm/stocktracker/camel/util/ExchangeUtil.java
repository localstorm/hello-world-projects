package org.localstorm.stocktracker.camel.util;

import org.apache.camel.Exchange;

/**
 * Utility class to operate with Exchange message bodies
 * @author Alexey Kuznetsov
 */
public class ExchangeUtil 
{
    @SuppressWarnings("unchecked")
    public static <T> T inBody(Exchange e) {
        return (T) e.getIn().getBody();
    }
}
