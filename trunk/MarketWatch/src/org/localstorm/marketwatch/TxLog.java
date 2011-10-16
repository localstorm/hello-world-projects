package org.localstorm.marketwatch;

import au.com.bytecode.opencsv.CSVReader;
import org.localstorm.marketwatch.pricing.PriceBoard;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TxLog {
    private File log;
    private boolean track;
    private ArrayList<Tx> txLog = new ArrayList<Tx>();

    public TxLog(File log, boolean track) {
        this.log = log;
        this.track = track;
    }

    public void logOperation(Tx tx) throws IOException {
        txLog.add(tx);
        if (track) {
            dumpToFile(tx);
        }
    }

    public BuySell getLastOperation(Asset asset) {
        for (int i = txLog.size() - 1; i >= 0; i++) {
            Tx t = txLog.get(i);
            if (t.getAssetName().equals(asset.getName())) {
                return t.getOperation();
            }
        }
        return null;
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

    public double getProfit(Asset asset, Price curPrice) {
        double p = 0;
        for (Tx t : txLog) {
            if (asset.getName().equals(t.getAssetName())) {
                double q = t.getQuantity();
                p += (q * curPrice.getSell() - q * t.getPrice().getBuy());
            }
        }
        return p;
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

    private void dumpToFile(Tx tx) throws IOException {
        boolean needHeader = !log.exists();

        FileOutputStream fos = new FileOutputStream(log, true);
        PrintStream pst = new PrintStream(fos);
        try {
            if (needHeader) {
                pst.println("AssetName,QuantityChange,BuyPrice,SellPrice,Time");
            }
            pst.print(tx.getAssetName() + ",");
            pst.print((tx.getOperation() == BuySell.BUY ? tx.getQuantity() : -tx.getQuantity()) + ",");
            pst.print(tx.getPrice().getBuy() + ",");
            pst.println(tx.getPrice().getSell() + "," + timestamp());
        } finally {
            pst.close();
        }
    }

    private static String timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
        return sdf.format(new Date());
    }

    public Possessions load(LiquidityPool pool) throws IOException {
        Possessions possessions = new Possessions();
        CSVReader reader = new CSVReader(new FileReader(log));

        try {
            String[] op = reader.readNext();

            while (op != null) {
                op = reader.readNext();
                String assetName = op[0];
                double quantity = Double.parseDouble(op[1]);
                double buy = Double.parseDouble(op[2]);
                double sell = Double.parseDouble(op[3]);

                Price price = new Price(buy, sell);
                Tx tx = new Tx(assetName, quantity < 0 ? BuySell.SELL : BuySell.BUY, Math.abs(quantity), price);
                txLog.add(tx);

                if (quantity >= 0) {
                    possessions.add(assetName, quantity);
                    pool.getCash(quantity*price.getBuy());
                } else {
                    possessions.reduce(assetName, quantity);
                    pool.putCash(quantity*price.getSell());
                }
            }
        } finally {
            reader.close();
            return possessions;
        }
    }
}
