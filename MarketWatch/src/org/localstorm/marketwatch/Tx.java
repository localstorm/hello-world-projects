package org.localstorm.marketwatch;

public class Tx {
    private String assetName;
    private BuySell operation;
    private double quantity;
    private Price price;

    public Tx(String assetName, BuySell operation, double quantity, Price price) {
        this.assetName = assetName;
        this.operation = operation;
        this.quantity = quantity;
        this.price = price;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public BuySell getOperation() {
        return operation;
    }

    public void setOperation(BuySell operation) {
        this.operation = operation;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
