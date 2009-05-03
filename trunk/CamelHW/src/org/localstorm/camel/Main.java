package org.localstorm.camel;

import javax.activation.DataHandler;
import org.localstorm.camel.util.ThreadUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;

/**
 *
 * @author localstorm
 */
public class Main {

    public static final String TRACKING_XML_HANDLER_URI = "txml:singleton";
    public static final String TRACKING_SCHEDULER_URI   = "ts:singleton";
    public static final String INSTRUCTOR_URI           = "i:singleton";
    public static final String STOCK_ANALYZER_URI       = "a:singleton";
    public static final String NOTIFIER_URI             = "n:singleton";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception /* TODO: remove this*/ {
        ApplicationLogger.getInstance().log("Starting Apache Camel kernel...");

        CamelContext cc = new DefaultCamelContext();

        cc.addRoutes(new RouteBuilder() {

            public void configure() {
                from(TRACKING_XML_HANDLER_URI).to(TRACKING_SCHEDULER_URI);
                from(TRACKING_SCHEDULER_URI).to(INSTRUCTOR_URI);
                from(STOCK_ANALYZER_URI).to(NOTIFIER_URI);
                //from(INSTRUCTOR_URI).to(STOCK_ANALYZER_URI); // Direct calls
            }
        });


        Main.onShutdown(Main.getCamelContextShutdownRunnamble(cc));

        try {
            cc.start();
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        ApplicationLogger.getInstance().log("Apache Camel kernel started.");

        ProducerTemplate pt = cc.createProducerTemplate();
        
        for (int i=0; i<1; i++){
            System.out.println("-->");

            pt.sendBody(TRACKING_XML_HANDLER_URI,
                        ExchangePattern.InOnly,
                        "<?xml version=\"1.0\" ?><call/>");

            System.out.println("<--");
        }

        ThreadUtil.waitForInterruption();

        ApplicationLogger.getInstance().log("Shutdown signal!");
    }

    private static Runnable getCamelContextShutdownRunnamble(final CamelContext cc) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    cc.stop();
                    ApplicationLogger.getInstance().log("Apache Camel kernel is down.");
                } catch(Exception e) {
                    //TODO: log here
                    e.printStackTrace();
                }
            }
        };
    }

    private static void onShutdown(Runnable r) {
        Runtime.getRuntime().addShutdownHook(new Thread(r));
    }



}
