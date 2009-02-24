package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.assets.Asset;
import org.localstorm.mcc.ejb.cashflow.assets.AssetManager;
import org.localstorm.mcc.ejb.cashflow.operations.Operation;
import org.localstorm.mcc.ejb.cashflow.operations.OperationManager;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;

/**
 * @secure-by operationId parameter
 * @author localstorm
 */
@UrlBinding("/actions/cash/op/RevokeOperation")
public class OperationRevokeActionBean extends CashflowBaseActionBean {

    @Validate(required=true)
    private Integer operationId;

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }


    @DefaultHandler
    public Resolution filling() throws Exception {

        AssetManager     am = super.getAssetManager();
        OperationManager om = super.getOperationManager();

        Operation op = om.findOperationById(this.getOperationId());
        Asset asset  = am.findAssetByValuable(op.getCost().getValuable());

        om.remove(op);

        RedirectResolution rr = new RedirectResolution(OperationsLogActionBean.class);
        {
            rr.addParameter(OperationsLogActionBean.IncomingParameters.ASSET_ID, asset.getId());
        }
        return rr;
    }


}
