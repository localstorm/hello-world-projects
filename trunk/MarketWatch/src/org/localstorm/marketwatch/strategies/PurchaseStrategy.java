package org.localstorm.marketwatch.strategies;

import org.localstorm.marketwatch.Asset;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:07 AM
 */
public interface PurchaseStrategy {
    public double onPriceChange(Asset changed);
}
