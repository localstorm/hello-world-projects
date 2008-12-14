package org.localstorm.mcc.web.cashflow;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.stat.HistoricalValuesManager;
import org.localstorm.mcc.web.BaseActionBean;

/**
 *
 * @author localstorm
 */
public class CashflowBaseActionBean extends BaseActionBean {

    public AssetManager getAssetManager() {
        return ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
    }

    public HistoricalValuesManager getHistoricalValuesManager() {
        return ContextLookup.lookup(HistoricalValuesManager.class,
                                    HistoricalValuesManager.BEAN_NAME);
    }

}
