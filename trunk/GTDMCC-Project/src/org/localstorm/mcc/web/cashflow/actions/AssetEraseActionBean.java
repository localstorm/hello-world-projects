package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.util.SessionUtil;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EraseAsset")
public class AssetEraseActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int assetId;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int id) {
        this.assetId = id;
    }
    
    @DefaultHandler
    public Resolution deletingContext() throws Exception {
        
        AssetManager am = super.getAssetManager();
        Asset asset = am.findAssetById(this.getAssetId());
        am.remove(asset);

        SessionUtil.clear(getSession(), SessionKeys.ASSETS);
        return new RedirectResolution(AssetsEditActionBean.class);
    }
}
