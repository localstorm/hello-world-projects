package org.localstorm.marketwatch.pricing;

import org.localstorm.marketwatch.Asset;
import org.localstorm.marketwatch.Price;

import java.util.HashMap;
import java.util.Map;

/**
 * User: LocalStorm
 * Date: 10/10/11
 * Time: 9:04 PM
 */
public class PriceBoard {

    private Map<String, Price> prices = new HashMap<String, Price>();
    private Map<String, MinMax> sellMinMax = new HashMap<String, MinMax>();
    private Map<String, MinMax> buyMinMax = new HashMap<String, MinMax>();

    public void resetPriceMinMax(Asset asset) {
        String assetName = asset.getName();
        sellMinMax.remove(assetName);
        buyMinMax.remove(assetName);
        prices.remove(assetName);
        updatePrice(asset);
    }

    public void updatePrice(Asset asset) {
        Price newPrice = asset.getPrice();
        String assetName = asset.getName();
        Price old = prices.put(assetName, newPrice);
        if (old != null) {
            MinMax mmb = buyMinMax.get(assetName);
            MinMax mms = sellMinMax.get(assetName);
            sellMinMax.put(assetName, new MinMax(Math.min(mms.getMin(), newPrice.getSell()),
                    Math.max(mms.getMax(), newPrice.getSell())));
            buyMinMax.put(assetName, new MinMax(Math.min(mmb.getMin(), newPrice.getBuy()),
                    Math.max(mmb.getMax(), newPrice.getBuy())));
        } else {
            sellMinMax.put(assetName, new MinMax(newPrice.getSell(), newPrice.getSell()));
            buyMinMax.put(assetName, new MinMax(newPrice.getBuy(), newPrice.getBuy()));
        }
    }

    public MinMax getBuyMinMax(Asset asset) {
        return buyMinMax.get(asset.getName());
    }

    public MinMax getSellMinMax(Asset asset) {
        return sellMinMax.get(asset.getName());
    }

    public Price getCurrentPrice(String assetName) {
        return prices.get(assetName);
    }
}
