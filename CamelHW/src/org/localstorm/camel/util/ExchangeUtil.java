package org.localstorm.camel.util;

import org.apache.camel.Exchange;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ExchangeUtil 
{
    @SuppressWarnings("unchecked")
    public static <T> T inBody(Exchange e) {
        return (T) e.getIn().getBody();
    }
}
