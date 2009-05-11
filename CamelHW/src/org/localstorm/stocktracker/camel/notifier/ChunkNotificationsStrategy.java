package org.localstorm.stocktracker.camel.notifier;

import java.util.Collection;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.localstorm.stocktracker.camel.util.ExchangeUtil;
import org.localstorm.stocktracker.exchange.NotificationFire;
import org.localstorm.stocktracker.exchange.NotificationsChunk;

/**
 * TODO!
 * @author Alexey Kuznetsov
 */
public class ChunkNotificationsStrategy implements AggregationStrategy
{

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        
        NotificationsChunk chunk = ExchangeUtil.inBody(oldExchange);
        NotificationsChunk fired = ExchangeUtil.inBody(newExchange);

        Collection<NotificationFire> fire = fired.getAllNotifications();

        for (NotificationFire newFired : fire) {
            chunk.addNote(newFired);
        }
        
        return oldExchange;
    }

}
