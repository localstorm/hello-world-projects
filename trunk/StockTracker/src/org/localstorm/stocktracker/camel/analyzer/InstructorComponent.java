package org.localstorm.stocktracker.camel.analyzer;

import java.util.Map;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultExchange;

/**
 * Instructor component. Handles all incoming instructions from scheduler
 * @author Alexey Kuznetsov
 */
public class InstructorComponent extends DefaultComponent<DefaultExchange>
{

    @Override
    protected Endpoint<DefaultExchange> createEndpoint(String uri,
                                                       String remaining,
                                                       Map parameters) throws Exception
    {
        return new InstructorEndpoint(uri, this);
    }
    
}
