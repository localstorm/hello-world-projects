package org.localstorm.stocktracker.exchange;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.localstorm.stocktracker.util.io.Printable;

/**
 * Stock prices request class
 * @author Alexey Kuznetsov
 */
public class StockPriceRequest extends Printable implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final List<StockPrice> priceList;

    public StockPriceRequest() {
        this.priceList = new LinkedList<StockPrice>();
    }

    public void addPrice(StockPrice sp)
    {
        this.priceList.add(sp);
    }

    public int getPricesCount()
    {
        return this.priceList.size();
    }

    public List<StockPrice> getPriceList()
    {
        return priceList;
    }

}
