package org.localstorm.marketwatch;

import au.com.bytecode.opencsv.CSVReader;
import org.localstorm.marketwatch.pricing.PriceBoard;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TxLog {
    private ArrayList<Tx> txLog = new ArrayList<Tx>();

    public TxLog() {

    }

    public void logOperation(Tx tx) throws IOException {
        txLog.add(tx);
    }


    public Price getLastPrice(Asset asset) {
        for (int i = txLog.size() - 1; i >= 0; i++) {
            Tx t = txLog.get(i);
            if (t.getAssetName().equals(asset.getName())) {
                return t.getPrice();
            }
        }
        return null;
    }


    public double getProfitChangeSinceLastSell(Asset changed, Price price) {
        String assetName = changed.getName();

        int i = txLog.size() - 1;
        for (; i >= 0; i--) {
            Tx t = txLog.get(i);
            if (t.getAssetName().equals(assetName) && t.getOperation() == BuySell.SELL) {
                break;
            }
        }

        return getProfitFrom(changed, price, i + 1);
    }

    private double getProfitFrom(Asset changed, Price curPrice, int startFromTx) {
        double p = 0;
        for (int i = startFromTx; i < txLog.size(); i++) {
            Tx tx = txLog.get(i);
            if (changed.getName().equals(tx.getAssetName())) {
                double q = tx.getQuantity();
                p += (q * curPrice.getSell() - q * tx.getPrice().getBuy());
            }
        }
        return p;
    }


    public int size() {
        int count = 0;
        for (Tx tx : txLog) {
            if (tx.getQuantity() > 0) {
                count++;
            }
        }
        return count;
    }

}
