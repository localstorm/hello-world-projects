package org.localstorm.stocktracker;

import org.localstorm.stocktracker.config.GlobalConfiguration;
import java.io.IOException;
import org.localstorm.stocktracker.base.ApplicationLogger;
import org.localstorm.stocktracker.camel.CamelService;
import org.localstorm.stocktracker.rest.WebConnectorService;
import org.localstorm.stocktracker.util.misc.ThreadUtil;

/**
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {

        if ( !init(args) ) {
            System.err.println("Invalid configuration file. Aborting...");
            return;
        }

        ApplicationLogger.getInstance().log("Starting Apache Camel kernel...");

        WebConnectorService webContainer = new WebConnectorService();
        CamelService         camelFasade = CamelService.getInstance();
    
        Main.onShutdown(Main.chainRunnables(
            webContainer.getShutdownRunnable(),
            camelFasade.getShutdownRunnable()
        ));

        ApplicationLogger.getInstance().log("Apache Camel kernel started.");

        try {
            camelFasade.start();
            webContainer.start();
        
            ThreadUtil.waitForInterruption();
        } catch(Exception e) {
            //TODO: no stack traces!
            e.printStackTrace();
            
            ApplicationLogger.getInstance().log("Can't start application due to error:", e);
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
                        //TODO: log here
                        e.printStackTrace();
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
            //TODO: log!
            e.printStackTrace();
            return false;
        }
    }

    private static void onShutdown(Runnable r) {
        Runtime.getRuntime().addShutdownHook(new Thread(r));
    }

    
}
