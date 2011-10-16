package org.localstorm.marketwatch;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:28 AM
 */
public class Asset {
    private String name;
    private Price price;
    private double quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public double getValue() {
        return quantity * price.getSell();
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Price newPrice) {
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
