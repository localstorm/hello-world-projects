package org.localstorm.stocktracker;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexey Kuznetsov
 */
public class WebConnectorFasade implements ServiceFasade
{
    private SelectorThread selectorThread = null;

    public WebConnectorFasade() {

    }

    public void start() throws Exception  {
        Map<String, String> init = new HashMap<String, String>();

        init.put("com.sun.jersey.config.property.packages",
                 "org.localstorm.camel.rest");

        //That is not a real thread. SelectorThread is an entry point to embedded Grizzly Servlet Container
        this.selectorThread = GrizzlyWebContainerFactory.create("http://localhost:8080/", init);
    }

    public void stop() throws Exception {
        if (this.selectorThread!=null) {
            this.selectorThread.stopEndpoint();
        }
    }

    public Runnable getShutdownRunnable()
    {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    ApplicationLogger.getInstance().log("Stopping web container...");
                    WebConnectorFasade.this.stop();
                    ApplicationLogger.getInstance().log("WebContainer is down.");
                } catch(Exception e) {
                    //TODO: log here
                    e.printStackTrace();
                }
            }
        };
    }

    
}
