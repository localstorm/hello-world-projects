package org.localstorm.camel.analyzer;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.stocks.tracker.AnalyzerInstruction;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AnalyzerEndpoint extends DefaultEndpoint<DefaultExchange>
{

    public AnalyzerEndpoint(String uri, AnalyzerComponent component)
    {
        super(uri, component);
    }

    public AnalyzerEndpoint(String endpointUri) {
        super(endpointUri);
    }

    /*package*/ void appendRule(AnalyzerInstruction ai)
    {
        System.out.println("Appending rule: "+ai);
    }

    /*package*/ void removeRule(AnalyzerInstruction ai)
    {
        System.out.println("Removing rule: "+ai);
    }

    public Consumer<DefaultExchange> createConsumer(Processor arg0)
            throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Producer<DefaultExchange> createProducer()
            throws Exception
    {
        return new AnalyzerProducer(this);
    }

    public boolean isSingleton()
    {
        return true;
    }

}
