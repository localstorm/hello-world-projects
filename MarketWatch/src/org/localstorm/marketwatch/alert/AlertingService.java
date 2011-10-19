package org.localstorm.marketwatch.alert;

import org.localstorm.marketwatch.Asset;


public interface AlertingService {
     public void onPriceChange(Asset a);
}
