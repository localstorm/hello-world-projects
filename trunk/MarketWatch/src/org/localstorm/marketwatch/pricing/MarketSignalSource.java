package org.localstorm.marketwatch.pricing;


public interface MarketSignalSource {

    public MarketSignal readSignal() throws Exception;

    public void close();
}