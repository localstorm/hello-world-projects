package org.localstorm.stocktracker.exchange;

import org.localstorm.stocktracker.util.io.Printable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Stock traking request class
 * @author Alexey Kuznetsov
 */
public class StockTrackingRequest extends Printable implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final List<StockEvent> watchList;
    private String account;

    public StockTrackingRequest() {
        this.watchList  = new LinkedList<StockEvent>();
        this.account    = null;
    }

    public int eventCount()
    {
        return this.getWatchList().size();
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void addEvent(StockEvent e) {
        this.watchList.add(e);
    }

    public List<StockEvent> getWatchList() {
        return watchList;
    }

    public String getAccount() {
        return account;
    }
    
}
