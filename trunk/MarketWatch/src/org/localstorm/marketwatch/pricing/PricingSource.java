package org.localstorm.marketwatch.pricing;

import org.localstorm.marketwatch.Asset;
import org.localstorm.marketwatch.Price;

import java.io.IOException;
import java.util.List;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 10:01 AM
 */
public interface PricingSource {
   public boolean updatePrices() throws IOException;

   public Price getAssetPrice(String assetName);

   public List<Asset> getPrices();

   public boolean isLocalSource();
}
