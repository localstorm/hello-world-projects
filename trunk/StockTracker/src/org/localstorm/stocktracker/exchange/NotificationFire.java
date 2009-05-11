package org.localstorm.stocktracker.exchange;

import java.io.Serializable;
import java.math.BigDecimal;
import org.localstorm.stocktracker.util.io.Printable;

/**
 * Atomic notification 
 * @author Alexey Kuznetsov
 */
public class NotificationFire extends Printable implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String account;
    private String symbol;
    private StockEventType type;
    private BigDecimal price;
    private BigDecimal threshold;

    public NotificationFire(String account, String symbol, StockEventType type, BigDecimal threshold, BigDecimal price) {
        this.account   = account;
        this.symbol    = symbol;
        this.price     = price;
        this.threshold = threshold;
        this.type      = type;

    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public StockEventType getType() {
        return type;
    }

    
}
