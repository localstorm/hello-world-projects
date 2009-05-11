package org.localstorm.stocktracker.camel.notifier;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.config.Configuration;
import org.localstorm.stocktracker.config.GlobalConfiguration;
import org.localstorm.stocktracker.exchange.NotificationFire;
import org.localstorm.stocktracker.exchange.NotificationsChunk;
import org.localstorm.stocktracker.exchange.StockEventType;

/**
 * Just for simplicity we support only one output file at the moment.
 * No configuration available. Will be fixed in future. :)
 * @author Alexey Kuznetsov
 */
public class NotifierComponent extends DefaultComponent<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(NotifierComponent.class);
    
    private String fileName;
    private PrintStream ps;

    public NotifierComponent() {
        Configuration conf = GlobalConfiguration.getConfiguration();
        this.fileName = conf.getNotificationOutputFilePath();
    }

    @Override
    protected Endpoint<DefaultExchange> createEndpoint(String uri,
                                                       String remaining,
                                                       Map parameters)
        throws Exception
    {
        return new NotifierEndpoint(uri, this);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();

        this.ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    }

    @Override
    protected void doStop() throws Exception {
        this.ps.close();
    }

    /*package*/ synchronized void writeNotifications(NotificationsChunk chunk)
    {
        if (log.isDebugEnabled()) {
            log.debug("Notifier: "+chunk);
        }

        Map<String, ? extends Map<String, NotificationFire>> ns = chunk.getCorrelatedFiredNotifications();
        
        for (Map.Entry<String, ? extends Map<String, NotificationFire>> entry: ns.entrySet()) {
            this.ps.printf("Dear %s,", entry.getKey());
            this.ps.println();
            
            Map<String, NotificationFire> fired =  entry.getValue();
            for (Map.Entry<String, NotificationFire> fireEntry: fired.entrySet()) {
                NotificationFire fire = fireEntry.getValue();
                this.ps.printf("- %s stock %s %s$ and currently is %s$", 
                               fire.getSymbol(),
                               this.getStockChangeMessage(fire.getType()),
                               fire.getThreshold(),
                               fire.getPrice());
                this.ps.println();
            }
        }
        this.ps.flush();
        this.ps.println("");
    }

    private String getStockChangeMessage(StockEventType type) {

        switch(type) {
            case FALL:
                return "fell under";
            case RAISE:
                return "rose over";
            default:
                throw new RuntimeException("Unexpected case: "+type);
        }
    }

    
}
