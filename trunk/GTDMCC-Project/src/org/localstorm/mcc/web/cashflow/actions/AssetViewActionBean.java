package org.localstorm.mcc.web.cashflow.actions;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;


/**
 * @secure-by assetId parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewAsset")
public class AssetViewActionBean extends CashflowBaseActionBean
{
    @Validate( required=true )
    private int assetId;

    private Asset assetResult;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public Asset getAssetResult() {
        return assetResult;
    }

    public void setAssetResult(Asset assetResult) {
        this.assetResult = assetResult;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {
        Asset asset = super.getAssetManager().findAssetById(this.getAssetId());

        this.setAssetResult( WrapUtil.wrapAsset(asset, super.getAssetManager()) );
        
        return new ForwardResolution(Views.VIEW_ASSET);
    }
    
    public static interface IncommingParameters {
        public static final String ASSET_ID = "assetId";
    }
}
