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
    public AnalyzerEndpoint(String endpointUri,
                             AnalyzerComponent component) {
        super(endpointUri, component);
    }

    public AnalyzerEndpoint(String endpointUri, Object some) {
        super(endpointUri);
    }

    @Override
    public AnalyzerComponent getComponent() {
        return (AnalyzerComponent) super.getComponent();
    }

    public Consumer<DefaultExchange> createConsumer(Processor p) throws Exception
    {
        throw new UnsupportedOperationException("You cannot recieve messages from this endpoint");
    }

    public Producer<DefaultExchange> createProducer() throws Exception
    {
        //throw new UnsupportedOperationException("You cannot recieve messages from this endpoint");
        return new AnalyzerProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    /*package*/ void processInstruction(AnalyzerInstruction ai)
    {
        System.out.println("Instruction to process: "+ai);
    }
    
}
