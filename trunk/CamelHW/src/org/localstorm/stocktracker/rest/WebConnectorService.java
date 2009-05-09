package org.localstorm.stocktracker.rest;

import org.localstorm.stocktracker.util.misc.PropertiesUtil;
import org.localstorm.stocktracker.base.ApplicationLogger;
import org.localstorm.stocktracker.base.GenericService;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Alexey Kuznetsov
 */
public class WebConnectorService implements GenericService
{
    private static final String JERSEY_PROPERTIES = "META-INF/jersey/resources/config";
    private SelectorThread selectorThread = null;

    public WebConnectorService() {

    }

    public void start() throws Exception  {

        Properties prop = PropertiesUtil.loadFromResource(JERSEY_PROPERTIES);
        Map<String, String> init = PropertiesUtil.asMap(prop);

        //That is not a real thread. SelectorThread is an entry point to embedded Grizzly Servlet Container
        // TODO: "http://localhost:8080/" -- to config
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
                    WebConnectorService.this.stop();
                    ApplicationLogger.getInstance().log("WebContainer is down.");
                } catch(Exception e) {
                    //TODO: log here
                    e.printStackTrace();
                }
            }
        };
    }

    
}
