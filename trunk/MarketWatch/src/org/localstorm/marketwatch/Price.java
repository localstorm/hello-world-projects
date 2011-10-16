package org.localstorm.marketwatch;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:36 AM
 */
public class Price {
    private double buy; // current buy price
    private double sell; // current sell price

    public Price() {
    }

    public Price(double buy, double sell) {
        this.buy = buy;
        this.sell = sell;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Price{" +
                "buy=" + buy +
                ", sell=" + sell +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (Double.compare(price.buy, buy) != 0) return false;
        if (Double.compare(price.sell, sell) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = buy != +0.0d ? Double.doubleToLongBits(buy) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = sell != +0.0d ? Double.doubleToLongBits(sell) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
