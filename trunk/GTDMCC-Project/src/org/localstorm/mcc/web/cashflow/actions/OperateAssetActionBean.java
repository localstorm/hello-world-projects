package org.localstorm.mcc.web.cashflow.actions;

import java.math.BigDecimal;
import java.math.MathContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.OperationType;
import org.localstorm.mcc.ejb.cashflow.ValuableObject;

/**
 * @author localstorm
 */
@UrlBinding("/actions/OperateAsset")
public class OperateAssetActionBean extends AssetViewActionBean {

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @Validate( required=true )
    private String operationName;

    @Validate( required=true )
    private BigDecimal amount;

    @Validate( required=true )
    private String comment;

   

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @DefaultHandler
    public Resolution operate() throws Exception {

        AssetManager am = super.getAssetManager();
        Asset     asset = am.findAssetById(super.getAssetId());
        ValuableObject vo = asset.getValuable();

        MathContext rounding = new MathContext(5);

        boolean exchange = false;
        switch (OperationType.valueOf(this.getOperationName()))
        {
            case BUY_FX:
                exchange = true;
            case BUY:
                am.buy(vo, RoundUtil.round(this.getAmount(), rounding), this.getComment(), exchange);
                break;
            case SELL_FX:
                exchange = true;
            case SELL:
                am.sell(vo, RoundUtil.round(this.getAmount(), rounding), this.getComment(), exchange);
                break;
            default:
                throw new RuntimeException("Unexpected operation: "+this.getOperationName());
        }

        RedirectResolution rr = new RedirectResolution(AssetViewActionBean.class);
        {
            rr.addParameter(AssetViewActionBean.IncommingParameters.ASSET_ID, asset.getId());
        }
        return rr;
    }
}
