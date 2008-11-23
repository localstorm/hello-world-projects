package org.localstorm.mcc.web.cashflow.actions;

import java.util.Collection;
import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/ViewAssets")
public class ViewAssetsActionBean extends CashflowBaseActionBean {

    private Collection<Asset> assets;

    public Collection<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Collection<Asset> assets) {
        this.assets = assets;
    }

    @DefaultHandler
    public Resolution filling() {
        AssetManager am = super.getAssetManager();
       
        this.assets = am.findAssetsByOwner(super.getUser());
        this.assets = WrapUtil.wrapAssets(assets, am);

        return new ForwardResolution(Views.ASSETS);
    }

}