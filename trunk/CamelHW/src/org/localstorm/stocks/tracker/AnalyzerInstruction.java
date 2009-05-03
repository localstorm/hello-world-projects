package org.localstorm.stocks.tracker;

import java.math.BigDecimal;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AnalyzerInstruction extends Printable
{
    private final boolean end;
    private final String symbol;
    private final String account;
    private final StockChangeType type;
    private final BigDecimal threshold;

    public AnalyzerInstruction(boolean end, String symbol, String account, StockChangeType type, BigDecimal threshold)
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

    public StockChangeType getStockChangeType()
    {
        return type;
    }

    public boolean isEnd()
    {
        return end;
    }

}
