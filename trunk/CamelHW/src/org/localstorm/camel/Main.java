package org.localstorm.camel;

import org.localstorm.camel.util.ThreadUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author localstorm
 */
public class Main {

    public static final String TRACKING_REQ_HANDLER_URI = "th:singleton";
    public static final String SCHED_SERVICE_URI        = "ts:singleton";
    public static final String STOCK_ANALYZER_URI       = "sa:singleton";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception /* TODO: remove this*/ {
        ApplicationLogger.getInstance().log("Starting Apache Camel kernel...");

        CamelContext cc = new DefaultCamelContext();

        cc.addRoutes(new RouteBuilder() {

            public void configure() {
                from(TRACKING_REQ_HANDLER_URI).to(SCHED_SERVICE_URI);
                from(SCHED_SERVICE_URI).to(STOCK_ANALYZER_URI);
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

        for (int i=0; i<5; i++){
            System.out.println("-->");
            pt.sendBody(TRACKING_REQ_HANDLER_URI, "<?xml version=\"1.0\" ?><call/>");
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
