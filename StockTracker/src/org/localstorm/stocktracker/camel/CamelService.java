package org.localstorm.stocktracker.camel;

import org.localstorm.stocktracker.camel.notifier.ChunkNotificationsStrategy;
import bsh.EvalError;
import bsh.Interpreter;
import org.localstorm.stocktracker.base.GenericService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.config.Configuration;
import org.localstorm.stocktracker.config.GlobalConfiguration;

/**
 * Apache Camel infrastructure service
 * @author Alexey Kuznetsov
 */
public class CamelService implements GenericService
{
    private static final Log log = LogFactory.getLog(CamelService.class);
    private static final CamelService instance = new CamelService();
    private static final String BUILDER_INJECTED = "builder";

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

                Configuration conf = GlobalConfiguration.getConfiguration();

                try {
                    String bsh = conf.getRouteBuilderBeanShellScript();

                    if (bsh!=null && bsh.length()>0) {
                        Interpreter interpreter = new Interpreter();
                        interpreter.set(BUILDER_INJECTED, this);
                        interpreter.eval(bsh);
                    } else {
                        // Default routing
                        from(Endpoints.TRACKING_REQUESTS_INPUT_URI).thread(10).to(Endpoints.TRACKING_SCHEDULER_URI);
                        from(Endpoints.TRACKING_SCHEDULER_URI).thread(5).to(Endpoints.INSTRUCTOR_URI);
                        from(Endpoints.STOCK_PRICES_INPUT_URI).thread(10).to(Endpoints.PRICES_TRACKER_URI);
                        from(Endpoints.PRICES_TRACKER_URI).to(Endpoints.STOCK_ANALYZER_URI);

                        from(Endpoints.STOCK_ANALYZER_URI).
                            aggregator(constant(true), new ChunkNotificationsStrategy()).
                                batchSize(100).batchTimeout(1000). // Aggregator configuration params
                                    to(Endpoints.NOTIFIER_URI);
                    }
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
                    System.out.println("Stopping Apache Camel kernel...");
                    CamelService.this.stop();
                    System.out.println("Apache Camel kernel is down.");
                } catch(Exception e) {
                    log.error(e);
                }
            }
            
        };
    }

    
}
