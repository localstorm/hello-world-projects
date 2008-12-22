package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.asset.Operation;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;

/**
 *
 * @author localstorm
 */
@UrlBinding("/actions/RevokeOperation")
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

        AssetManager am = super.getAssetManager();

        Operation op = am.findOperationById(this.getOperationId());
        Asset asset = am.findAssetByValuable(op.getCost().getValuable());

        am.remove(op);

        RedirectResolution rr = new RedirectResolution(OperationsLogActionBean.class);
        {
            rr.addParameter(OperationsLogActionBean.IncomingParameters.ASSET_ID, asset.getId());
        }
        return rr;
    }


}
