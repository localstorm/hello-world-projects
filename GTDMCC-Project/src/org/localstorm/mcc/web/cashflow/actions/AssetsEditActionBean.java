package org.localstorm.mcc.web.cashflow.actions;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditAssets")
public class AssetsEditActionBean extends CashflowBaseActionBean {

    private Collection<Asset> assets;
    private Collection<Asset> archiveAssets;

    public Collection<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Collection<Asset> assets) {
        this.assets = assets;
    }

    public Collection<Asset> getArchiveAssets() {
        return archiveAssets;
    }

    public void setArchiveAssets(Collection<Asset> archiveAssets) {
        this.archiveAssets = archiveAssets;
    }

    @DefaultHandler
    public Resolution filling() {
        
        AssetManager am = super.getAssetManager();
        this.setAssets(am.findAssetsByOwner(super.getUser()));
        this.setArchiveAssets(am.findArchivedAssetsByOwner(super.getUser()));
        
        return new ForwardResolution(Views.EDIT_ASSETS);
    }
    
    
}