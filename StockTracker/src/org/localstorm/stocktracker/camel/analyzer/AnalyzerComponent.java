package org.localstorm.stocktracker.camel.analyzer;

import java.util.Map;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultExchange;

/**
 * Stock events analyzer component
 * @author Alexey Kuznetsov
 */
public class AnalyzerComponent extends DefaultComponent<DefaultExchange>
{

    @Override
    protected Endpoint<DefaultExchange> createEndpoint(String uri,
                                                       String remaining,
                                                       Map parameters) throws Exception
    {
        return new AnalyzerEndpoint(uri, this);
    }
    
}
