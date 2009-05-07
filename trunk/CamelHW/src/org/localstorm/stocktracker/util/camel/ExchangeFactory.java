package org.localstorm.stocktracker.util.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.DefaultExchange;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ExchangeFactory 
{
    public static  DefaultExchange inOnly(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.InOnly);
    }

    public static  DefaultExchange inOnly(Endpoint ep, Object body)
    {
        DefaultExchange de = new DefaultExchange(ep.getCamelContext(), ExchangePattern.InOnly);
        de.getIn().setBody(body);
        return de;
    }

    public static  DefaultExchange inOptionalOut(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.InOptionalOut);
    }

    public static  DefaultExchange inOut(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.InOut);
    }

    public static  DefaultExchange outIn(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.OutIn);
    }

    public static  DefaultExchange outOnly(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.OutOnly);
    }

    public static  DefaultExchange outOptionalIn(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.OutOptionalIn);
    }

    public static  DefaultExchange robustInOnly(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.RobustInOnly);
    }

    public static  DefaultExchange robustOutOnly(Endpoint ep)
    {
        return new DefaultExchange(ep.getCamelContext(), ExchangePattern.RobustOutOnly);
    }

}
