package org.localstorm.marketwatch.strategies;

import org.localstorm.marketwatch.BuySell;
import org.localstorm.marketwatch.Price;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:17 AM
 */
public class Signal {
    private BuySell buySell;
    private String assetName;
    private double quantity;

    public Signal(String assetName, BuySell at, Price price) {
        this.assetName = assetName;
        this.buySell = at;
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public void setBuySell(BuySell buySell) {
        this.buySell = buySell;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
}
