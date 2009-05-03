package org.localstorm.camel.analyzer;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.camel.util.EndpointUtil;
import org.localstorm.stocks.tracker.AnalyzerInstruction;

/**
 *
 * @author Alexey Kuznetsov
 */
public class InstructorEndpoint extends DefaultEndpoint<DefaultExchange>
{
    public InstructorEndpoint(String endpointUri,
                             InstructorComponent component) {
        super(endpointUri, component);
    }

    public InstructorEndpoint(String endpointUri, Object some) {
        super(endpointUri);
    }

    @Override
    public InstructorComponent getComponent() {
        return (InstructorComponent) super.getComponent();
    }

    public Consumer<DefaultExchange> createConsumer(Processor p) throws Exception
    {
        throw new UnsupportedOperationException("You cannot recieve messages from this endpoint");
    }

    public Producer<DefaultExchange> createProducer() throws Exception
    {
        return new InstructorProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    /*package*/ void processInstruction(AnalyzerInstruction ai)
    {
        AnalyzerEndpoint saEp = EndpointUtil.getEndpoint(this, generateStockAnalyzerUri());

        if (ai.isEnd()) {
            saEp.removeRule(ai);
        } else {
            saEp.appendRule(ai);
        }
    }

    private String generateStockAnalyzerUri()
    {
        // TODO: constant!
        return "a:singleton";
    }
    
}
