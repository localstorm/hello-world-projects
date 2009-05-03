package org.localstorm.camel.util;

import org.apache.camel.Endpoint;

/**
 *
 * @author Alexey Kuznetsov
 */
public class EndpointUtil
{
    @SuppressWarnings("unchecked")
    public static <T> T getEndpoint(Endpoint ep, String uri) {
        return (T) ep.getCamelContext().getEndpoint(uri);
    }
}
