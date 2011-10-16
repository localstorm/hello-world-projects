package org.localstorm.marketwatch.strategies;

import org.localstorm.marketwatch.Asset;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyStrategy implements SellStrategy , PurchaseStrategy {
    public double onPriceChange(Asset changed) {
        return 0;
    }
}
