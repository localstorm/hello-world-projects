package org.localstorm.marketwatch.alert;

public class AlertingCondition {
    private double buyLowerCap;
    private double sellHigherCap;
    private double spreadCap;

    public AlertingCondition(double buyLowerCap, double sellHigherCap, double spreadCap) {
        this.buyLowerCap = buyLowerCap;
        this.sellHigherCap = sellHigherCap;
        this.spreadCap = spreadCap;
    }

    public double getBuyLowerCap() {
        return buyLowerCap;
    }

    public double getSellHigherCap() {
        return sellHigherCap;
    }

    public double getSpreadCap() {
        return spreadCap;
    }

    public void setSpreadCap(double spreadCap) {
        this.spreadCap = spreadCap;
    }
}
