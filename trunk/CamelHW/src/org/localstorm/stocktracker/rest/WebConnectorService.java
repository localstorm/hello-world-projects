package org.localstorm.stocktracker.rest;

import org.localstorm.stocktracker.util.misc.PropertiesUtil;
import org.localstorm.stocktracker.base.GenericService;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.config.Configuration;
import org.localstorm.stocktracker.config.GlobalConfiguration;

/**
 *
 * @author Alexey Kuznetsov
 */
public class WebConnectorService implements GenericService
{
    private static final Log log = LogFactory.getLog(WebConnectorService.class);
    private static final String JERSEY_PROPERTIES = "META-INF/jersey/resources/config";
    private static final String URL_TEMPLATE      = "http://{0}:{1}/";

    private SelectorThread selectorThread = null;

    public WebConnectorService() {

    }

    public void start() throws Exception  {
        

        Properties prop = PropertiesUtil.loadFromResource(JERSEY_PROPERTIES);
        Map<String, String> init = PropertiesUtil.asMap(prop);

        //That is not a real thread. SelectorThread is an entry point to embedded Grizzly Servlet Container
        this.selectorThread = GrizzlyWebContainerFactory.create(getWebBaseUrl(), init);
    }

    private String getWebBaseUrl() {
        Configuration conf = GlobalConfiguration.getConfiguration();

        return MessageFormat.format(URL_TEMPLATE,
                                    conf.getHost(),
                                    Integer.toString(conf.getPort()));
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
                    System.out.println("Stopping web container...");
                    WebConnectorService.this.stop();
                    System.out.println("WebContainer is down.");
                } catch(Exception e) {
                    log.error(e);
                }
            }
        };
    }

    
}
