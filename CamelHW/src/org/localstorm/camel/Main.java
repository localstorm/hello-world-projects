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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception /* TODO: remove this*/ {
        ApplicationLogger.getInstance().log("Starting Apache Camel kernel...");

        //JndiContext context = new JndiContext();
        //context.bind("bye", new SayServiceImpl("Good Bye!"));

        CamelContext cc = new DefaultCamelContext(/*context*/);

        cc.addRoutes(new RouteBuilder() {
            public void configure() {
                from("trh:requestHandler").to("ss:scheduler");
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
            pt.sendBody("trh:requestHandler", "<?xml version=\"1.0\" ?><call/>");
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
