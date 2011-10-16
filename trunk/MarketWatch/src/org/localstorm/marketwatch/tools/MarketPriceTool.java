package org.localstorm.marketwatch.tools;

import org.localstorm.marketwatch.Asset;
import org.localstorm.marketwatch.Price;
import org.localstorm.marketwatch.pricing.NomosSource;
import org.localstorm.marketwatch.pricing.PricingSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class MarketPriceTool {
    public static void main(String[] args) throws InterruptedException {

        Map<String, Price> prices = new HashMap<String, Price>();

        while (true) {
            try {
                PricingSource ps = new NomosSource("Silver", "Gold");
                while (!ps.updatePrices()) {
                }

                while (ps.updatePrices()) {
                    process(prices, ps);
                    Thread.sleep(60000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private static void updateMarketLogs(List<Asset> assets) throws IOException {

        for (Asset a : assets) {
            File f = new File("data/"+a.getName()+".csv");
            boolean needHeader = !f.exists();

            FileOutputStream fos = new FileOutputStream(f, true);
            PrintStream pst = new PrintStream(fos);
            try {
                if (needHeader) {
                   pst.println(a.getName()+"-Buy,"+a.getName()+"-Sell,Date");
                }
                pst.println(a.getPrice().getBuy() + "," + a.getPrice().getSell()+","+timestamp());
            } finally {
                pst.close();
            }
        }
    }

    private static String timestamp() {
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yy HH:mm:ss");
        return sdf.format(new Date());
    }

    private static void process(Map<String, Price> prices, PricingSource ps) throws IOException{
        List<Asset> pc = ps.getPrices();
        for (Asset a : pc) {
            Price p = prices.get(a.getName());
            if (p == null || !p.equals(a.getPrice())) {
                prices.put(a.getName(), a.getPrice());
                updateMarketLogs(Collections.singletonList(a));
            }
        }
    }

}
