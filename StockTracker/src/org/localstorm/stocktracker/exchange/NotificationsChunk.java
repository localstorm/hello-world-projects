package org.localstorm.stocktracker.exchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.localstorm.stocktracker.util.io.Printable;

/**
 * Notifications multiplexing collection
 * @author Alexey Kuznetsov
 */
public class NotificationsChunk extends Printable implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // key: account, value: {key: symbol, value=NotificationFire}
    private ConcurrentNavigableMap<String, ConcurrentNavigableMap<String, NotificationFire>> fired;

    public NotificationsChunk() {
        this.fired   = new ConcurrentSkipListMap<String, ConcurrentNavigableMap<String, NotificationFire>>();
    }

    public void addNote(NotificationFire fire) {
        String account = fire.getAccount();
        ConcurrentNavigableMap<String, NotificationFire> newQ = new ConcurrentSkipListMap<String, NotificationFire>();
        ConcurrentNavigableMap<String, NotificationFire> oldQ = this.fired.putIfAbsent(account, newQ);

        if (oldQ==null) {
            oldQ = newQ;
        }

        oldQ.put(fire.getSymbol(), fire);
    }

    public Collection<NotificationFire> getAllNotifications()
    {
        Collection<NotificationFire> all = new ArrayList<NotificationFire>();
        for (ConcurrentNavigableMap<String, NotificationFire> corr : this.fired.values()) {
            all.addAll(corr.values());
        }
        return all;
    }

    public Map<String, ? extends Map<String, NotificationFire>> getCorrelatedFiredNotifications() {
        return this.fired;
    }

    public boolean isEmpty() {
        return this.fired.isEmpty();
    }

}
