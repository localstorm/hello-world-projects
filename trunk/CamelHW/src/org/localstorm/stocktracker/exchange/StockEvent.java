package org.localstorm.stocktracker.exchange;

import org.localstorm.stocktracker.util.io.Printable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alexey Kuznetsov
 */
public final class StockEvent extends Printable implements Serializable
{
    private final StockEventType type;
    private final String          symbol;
    private final BigDecimal      price;
    private final Date            start;
    private final Date            end;

    public StockEvent(StockEventType type, String symbol, BigDecimal price, Date start, Date end) {
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

    public BigDecimal getThreshold() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockEventType getType() {
        return type;
    }

}
