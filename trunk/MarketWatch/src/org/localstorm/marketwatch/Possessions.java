package org.localstorm.marketwatch;

import org.apache.commons.io.IOUtils;
import org.localstorm.marketwatch.pricing.PriceBoard;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:27 AM
 */
public class Possessions {
    private Map<String, Double> q;

    public Possessions() {
        this.q = new HashMap<String, Double>();
    }

    public void add(String assetName, double quantity) {
        Double amount = q.get(assetName);
        if (amount == null) {
            amount = quantity;
        } else {
            amount = amount + quantity;
        }
        q.put(assetName, amount);
    }

    public boolean reduce(String assetName, double quantity) {
        Double amount = q.get(assetName);
        if (amount == null) {
            amount = 0.0;
        }
        if (amount < quantity) {
            return false;
        }

        q.put(assetName, amount - quantity);
        return true;
    }

    public double getQuantity(String assetName) {
        Double amount = this.q.get(assetName);
        return (amount != null) ? amount : 0.0;
    }

    public Collection<String> getAssetNames() {
        return q.keySet();
    }

    public void load(File file) throws IOException {
        Properties p = new Properties();
        FileReader reader = new FileReader(file);
        try {
            p.load(reader);

            this.add(p.getProperty("assetName"), Double.parseDouble(p.getProperty("quantity")));
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

     public double getValue(String assetName, PriceBoard prices) {
        return getQuantity(assetName)*prices.getCurrentPrice(assetName).getSell();
    }

    public double getValue(PriceBoard prices) {
        double total = 0;
        for (Map.Entry<String, Double> e: this.q.entrySet()) {
            String assetName = e.getKey();
            double quantity = e.getValue();
            total += prices.getCurrentPrice(assetName).getSell()*quantity;
        }
        return total;
    }
}
