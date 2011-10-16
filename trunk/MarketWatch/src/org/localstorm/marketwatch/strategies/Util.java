package org.localstorm.marketwatch.strategies;

import org.localstorm.marketwatch.Price;

public class Util {
    public static double getSpread(Price price) {
        return 2 * (price.getBuy() - price.getSell()) / (price.getBuy() + price.getSell());
    }

    public static double avg(double a, double b) {
        return (a+b)/2;
    }
}
