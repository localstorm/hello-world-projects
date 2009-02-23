package org.localstorm.mcc.web.cashflow.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.assets.Asset;
import org.localstorm.mcc.ejb.cashflow.assets.AssetManager;
import org.localstorm.mcc.ejb.cashflow.operations.Operation;
import org.localstorm.mcc.ejb.cashflow.assets.ValuableObject;
import org.localstorm.mcc.ejb.cashflow.operations.OperationManager;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;

/**
 * @secure-by assetId parameter
 * @author localstorm
 */
@UrlBinding("/actions/ViewOperations")
public class OperationsLogActionBean extends CashflowBaseActionBean {


    @Validate(required=true)
    private Integer assetId;

    private Asset asset;
    
    private Collection<Operation> operations;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {

        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();

        Asset           ass = am.findById(this.getAssetId());
        ValuableObject   vo = ass.getValuable();

        Collection<Operation> ops = om.getOperations(vo);

        this.setOperations(ops);
        this.setAsset(ass);

        return new ForwardResolution(Views.OPS_LOG);
    }

    public static interface IncomingParameters {
        public static final String ASSET_ID = "assetId";
    }

}
