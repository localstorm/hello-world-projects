package org.localstorm.stocktracker.camel.scheduler;

import java.util.Map;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultExchange;

/**
 * This component has uses Quartz to schedule tracking start/end.
 * This component was designed to handle thousands of Triggers and Jobs
 * (Camel-Quartz) is a some kind of a big memory leak in this case.
 *
 * @author Alexey Kuznetsov
 */
public class TrackingSchedulerComponent extends DefaultComponent<DefaultExchange>
{

    @Override
    protected Endpoint<DefaultExchange> createEndpoint(String uri,
                                                       String remaining,
                                                       Map parameters) throws Exception
    {
        return new TrackingSchedulerEndpoint(uri, this);
    }
    
}
