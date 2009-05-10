package org.localstorm.stocktracker;

import org.localstorm.stocktracker.config.GlobalConfiguration;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.camel.CamelService;
import org.localstorm.stocktracker.rest.WebConnectorService;
import org.localstorm.stocktracker.util.misc.ThreadUtil;

/**
 * @author Alexey Kuznetsov
 */
public class Main {

    private static Log log = LogFactory.getLog(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {

        if ( !init(args) ) {
            System.err.println("Invalid configuration file. Aborting...");
            return;
        }

        WebConnectorService webContainer = new WebConnectorService();
        CamelService        camelService = CamelService.getInstance();
    
        Main.onShutdown(Main.chainRunnables(
            webContainer.getShutdownRunnable(),
            camelService.getShutdownRunnable()
        ));

        try {

            log.info("Starting Apache Camel kernel...");
            camelService.start();
            log.info("Apache Camel kernel started.");

            log.info("Starting Grizzly Web Container...");
            webContainer.start();
            log.info("Grizzly Web Container started.");
        
            ThreadUtil.waitForInterruption();
        } catch(Exception e) {
            log.error("Can't start application due to error:", e);
        }
    }

   
    private static Runnable chainRunnables(final Runnable... runnables )
    {
        return new Runnable() {

            @Override
            public void run() {
                for (Runnable r: runnables) {
                    try {
                        r.run();
                    } catch(Exception e) {
                        log.error(e);
                    }
                }
            }
        };
        
    }

    private static boolean init(String[] args)
    {
        try {
            if (args.length > 0) {
                GlobalConfiguration.init(args[0]);
            } else {
                GlobalConfiguration.init(null);
            }
            return true;
        } catch(IOException e) {
            log.error(e);
            return false;
        }
    }

    private static void onShutdown(Runnable r) {
        Runtime.getRuntime().addShutdownHook(new Thread(r));
    }
    
}
