package org.localstorm.marketwatch.strategies.signals;

public class SignalFactory {

    public static double roundedFixedCash(double price, double cash) {
        return (long) (cash / price);
    }

}
