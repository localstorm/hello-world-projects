package org.localstorm.marketwatch.tools;

import org.localstorm.marketwatch.Asset;
import org.localstorm.marketwatch.alert.OneTimeAlertingService;
import org.localstorm.marketwatch.Price;
import org.localstorm.marketwatch.pricing.EventType;
import org.localstorm.marketwatch.pricing.*;
import org.localstorm.marketwatch.pricing.MarketSignal;
import org.localstorm.marketwatch.pricing.MarketSignalSource;
import org.localstorm.marketwatch.alert.SmsAlerting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class MarketPriceTool {
    public static void main(String[] args) throws Exception {
        OneTimeAlertingService alertingService = new OneTimeAlertingService(new SmsAlerting());
        alertingService.loadAlertingConditions(new File("price_tool/alerts.csv"));

        Map<String, Price> prices = new HashMap<String, Price>();

        while (true) {
            try {
                MarketSignalSource ps = new NomosSource("Silver", "Gold");
                MarketSignal signal;

                while ((signal = ps.readSignal()) == null) {
                }

                do {
                    process(prices, signal, alertingService);
                    signal = ps.readSignal();
                } while (signal != null);
                Thread.sleep(60000);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                continue;
            }
        }
    }

    private static void updateMarketLogs(List<Asset> assets) throws IOException {

        for (Asset a : assets) {
            File f = new File("data/" + a.getName() + ".mkt.csv");
            boolean needHeader = !f.exists();

            FileOutputStream fos = new FileOutputStream(f, true);
            PrintStream pst = new PrintStream(fos);
            try {
                if (needHeader) {
                    pst.println("DateTime,EventType,Asset,BuyPrice,SellPrice,Quantity,Volume,Spread");
                }
                pst.println(timestamp() + "," +
                        EventType.PriceChange.toString() + "," +
                        a.getName() + "," +
                        a.getPrice().getBuy() + "," +
                        a.getPrice().getSell() + "," +
                        "0," +
                        "0," +
                        a.getPrice().getSpread());
            } finally {
                pst.close();
            }
        }
    }

    private static String timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
        return sdf.format(new Date());
    }

    private static void process(Map<String, Price> prices, MarketSignal signal, OneTimeAlertingService alertingService) throws IOException {
        Asset a = new Asset();
        a.setName(signal.getAsset());
        a.setPrice(signal.getPrice());
        a.setQuantity(0.0);

        Price p = prices.get(a.getName());
        if (p == null || !p.equals(a.getPrice())) {
            prices.put(a.getName(), a.getPrice());
            updateMarketLogs(Collections.singletonList(a));
            alertingService.onPriceChange(a);
        }
    }

}
