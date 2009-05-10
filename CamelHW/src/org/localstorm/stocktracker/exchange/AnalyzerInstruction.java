package org.localstorm.stocktracker.exchange;

import java.io.Serializable;
import org.localstorm.stocktracker.util.io.Printable;
import java.math.BigDecimal;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AnalyzerInstruction extends Printable implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final boolean end;
    private final String symbol;
    private final String account;
    private final StockEventType type;
    private final BigDecimal threshold;

    public AnalyzerInstruction(boolean end, String symbol, String account, StockEventType type, BigDecimal threshold)
    {
        this.end = end;
        this.symbol = symbol;
        this.account = account;
        this.type = type;
        this.threshold = threshold;
    }

    public String getAccount()
    {
        return account;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public BigDecimal getThreshold()
    {
        return threshold;
    }

    public StockEventType getStockChangeType()
    {
        return type;
    }

    public boolean isEnd()
    {
        return end;
    }

}
