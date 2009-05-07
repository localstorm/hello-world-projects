package org.localstorm.stocktracker.camel;

import bsh.EvalError;
import bsh.Interpreter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.localstorm.stocktracker.base.ApplicationLogger;
import org.localstorm.stocktracker.base.GenericService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CamelService implements GenericService
{
    private static final CamelService instance = new CamelService();

    private final CamelContext camelContext;

    private CamelService() {
        camelContext = new DefaultCamelContext();
    }

    public static CamelService getInstance() {
        return instance;
    }

    public void start() throws Exception {

        camelContext.addRoutes(new RouteBuilder() {

            public void configure() {

                BufferedReader reader = null;
                try {

                    Interpreter interpreter = new Interpreter();
                    interpreter.set("builder", this);

                    // TODO: remove this:---------
                    from(Endpoints.TRACKING_REQUESTS_INPUT_URI).to(Endpoints.TRACKING_SCHEDULER_URI);
                    from(Endpoints.TRACKING_SCHEDULER_URI).to(Endpoints.INSTRUCTOR_URI);
                    from(Endpoints.STOCK_ANALYZER_URI).to(Endpoints.NOTIFIER_URI);
                    //----------------------------

                    //Evaluating here
                } catch(EvalError e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
        camelContext.start();
    }

    public void stop() throws Exception {
        this.camelContext.stop();
    }

    public CamelContext getCamelContext() {
        return this.camelContext;
    }

    public Runnable getShutdownRunnable()
    {
        return new Runnable() {

            @Override
            public void run() {
                try {

                    ApplicationLogger.getInstance().log("Stopping Apache Camel kernel...");
                    CamelService.this.stop();
                    ApplicationLogger.getInstance().log("Apache Camel kernel is down.");
                    
                } catch(Exception e) {
                    //TODO: log here
                    e.printStackTrace();
                }
            }
            
        };
    }

    
}
