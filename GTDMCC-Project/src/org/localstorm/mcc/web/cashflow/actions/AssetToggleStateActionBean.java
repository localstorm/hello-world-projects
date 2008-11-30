package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;

import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ToggleStateAsset")
public class AssetToggleStateActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int assetId;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    
    @DefaultHandler
    public Resolution toggle() throws Exception {
        
        AssetManager am = super.getAssetManager();
        Asset asset = am.findAssetById(this.getAssetId());

        asset.setArchived(!asset.isArchived());
        
        am.update(asset);
        
        SessionUtil.clear(getSession(), SessionKeys.ASSETS);
        return new RedirectResolution(AssetsEditActionBean.class);
    }
}
