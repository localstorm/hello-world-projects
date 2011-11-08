package org.localstorm.marketwatch;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:51 AM
 */
public class LiquidityPool {
    private double cash;
    private double chunkSize;
    private double onHold;
    private double holdCap;

     public LiquidityPool(double cash, double chunkSize) {
        this(cash, chunkSize, 0, -1);
    }

    public LiquidityPool(double cash, double chunkSize, double onHold, double holdCap) {
        this.cash = cash;
        this.chunkSize = chunkSize;
        this.onHold = onHold;
        this.holdCap = holdCap;
    }

    public boolean getCash(double amount) {
        if (cash >= amount) {
            cash -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void putCash(double value) {
        this.cash += value;
    }

    public void hold(double value) {
        if (value>cash) {
            onHold +=cash;
            cash = 0.0;
        } else {
            onHold += value;
            cash -= value;
        }
    }

    public double getHoldCap() {
        return holdCap;
    }

    public double getOnHold() {
        return onHold;
    }

    public double getChunkSize() {
        return chunkSize;
    }

    public double getCashLeft() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void setChunkSize(double chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void init(File file) throws IOException {
        Properties p = new Properties();
        FileReader reader = new FileReader(file);
        try {
            p.load(reader);
            this.setChunkSize(Double.parseDouble(p.getProperty("chunk")));
            this.setCash(Double.parseDouble(p.getProperty("cash")));
            this.onHold = Double.parseDouble(p.getProperty("onHold"));
            this.holdCap = Double.parseDouble(p.getProperty("holdCap"));
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
}
