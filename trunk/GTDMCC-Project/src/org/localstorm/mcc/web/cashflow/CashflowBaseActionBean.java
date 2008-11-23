package org.localstorm.mcc.web.cashflow;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.web.BaseActionBean;

/**
 *
 * @author localstorm
 */
public class CashflowBaseActionBean extends BaseActionBean {

    public AssetManager getAssetManager() {
        return ContextLookup.lookup(AssetManager.class, AssetManager.BEAN_NAME);
    }

}
