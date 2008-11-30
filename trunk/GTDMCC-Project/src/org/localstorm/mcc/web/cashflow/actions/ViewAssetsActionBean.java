package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.util.Collection;
import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/ViewAssets")
public class ViewAssetsActionBean extends CashflowBaseActionBean {

    private Collection<Asset> assets;
    private BigDecimal        netWealth;
    private BigDecimal        balance;

    public Collection<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Collection<Asset> assets) {
        this.assets = assets;
    }

    public BigDecimal getNetWealth()
    {
        return this.netWealth;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @DefaultHandler
    public Resolution filling() {
        AssetManager am = super.getAssetManager();
       
        this.assets = am.findAssetsByOwner(super.getUser());
        this.assets = WrapUtil.wrapAssets(assets, am);

        this.netWealth = BigDecimal.ZERO;
        this.balance   = BigDecimal.ZERO;

        for (Asset a: assets)
        {
            AssetWrapper aw = (AssetWrapper) a;
            this.netWealth = this.netWealth.add(aw.getNetWealth());
            this.balance   = this.balance.add(aw.getBalance());
        }

        return new ForwardResolution(Views.ASSETS);
    }

}