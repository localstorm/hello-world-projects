package org.localstorm.stocks.tracker;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alexey Kuznetsov
 */
public final class Event extends Printable implements Serializable
{
    private final StockChangeType type;
    private final String          symbol;
    private final BigDecimal      price;
    private final Date            start;
    private final Date            end;

    public Event(StockChangeType type, String symbol, BigDecimal price, Date start, Date end) {
        this.type = type;
        this.symbol = symbol;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockChangeType getType() {
        return type;
    }

}
