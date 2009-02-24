package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.assets.Asset;
import org.localstorm.mcc.ejb.cashflow.assets.AssetManager;
import org.localstorm.mcc.ejb.cashflow.operations.OperationManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.actions.wrap.AssetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @secure-by assetId parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/cash/asset/EraseAsset")
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
        
        OperationManager om = super.getOperationManager();
        AssetManager     am = super.getAssetManager();
        Asset         asset = am.findById(this.getAssetId());

        AssetWrapper aw = (AssetWrapper) WrapUtil.wrapAsset(asset, om);
        if (aw.getAmount().compareTo(BigDecimal.ZERO)==1) {
            throw new RuntimeException("Attempt to delete non sold amount!");
        }

        am.remove(asset);

        SessionUtil.clear(getSession(), SessionKeys.ASSETS);
        return new RedirectResolution(AssetsEditActionBean.class);
    }
}
