package org.localstorm.stocktracker.exchange;

import java.io.Serializable;
import java.math.BigDecimal;
import org.localstorm.stocktracker.util.io.Printable;

/**
 * Stock price/event item class
 * @author Alexey Kuznetsov
 */
public class StockPrice extends Printable implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String          symbol;
    private final BigDecimal      price;
    private StockEventType  type;

    public StockPrice(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price  = price;
        this.type   = null;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public StockEventType getType() {
        return type;
    }

    public void setType(StockEventType type) {
        this.type = type;
    }

}
