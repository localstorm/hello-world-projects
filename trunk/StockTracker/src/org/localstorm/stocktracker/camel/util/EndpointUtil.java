package org.localstorm.stocktracker.camel.util;

import org.apache.camel.Endpoint;

/**
 * Small utility class to make accessing endpoints easier
 * @author Alexey Kuznetsov
 */
public class EndpointUtil
{
    @SuppressWarnings("unchecked")
    public static <T> T getEndpoint(Endpoint ep, String uri) {
        T t = (T) ep.getCamelContext().getEndpoint(uri);

        if (t==null) {
            throw new RuntimeException("Endpoint not found for given URI: "+uri);
        }

        return t;
    }
}
