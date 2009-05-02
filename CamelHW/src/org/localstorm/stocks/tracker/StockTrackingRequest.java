package org.localstorm.stocks.tracker;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexey Kuznetsov
 */
public class StockTrackingRequest extends Printable implements Serializable
{
    private final List<Event> watchList;
    private final String account;

    public StockTrackingRequest(String account) {
        this.watchList  = new LinkedList<Event>();
        this.account    = account;
    }

    public void watchEvent(Event e) {
        this.watchList.add(e);
    }

    public List<Event> getWatchList()
    {
        return watchList;
    }

    public String getAccount()
    {
        return account;
    }

    
    
}
