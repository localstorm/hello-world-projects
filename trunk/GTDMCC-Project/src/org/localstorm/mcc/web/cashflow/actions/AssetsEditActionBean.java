package org.localstorm.mcc.web.cashflow.actions;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.cashflow.assets.Asset;
import org.localstorm.mcc.ejb.cashflow.assets.AssetManager;
import org.localstorm.mcc.ejb.cashflow.operations.OperationManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;

/**
 * @secure-by session (no security check)
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditAssets")
public class AssetsEditActionBean extends CashflowBaseActionBean {

    private Collection<Asset> archiveAssets;

    public Collection<Asset> getArchiveAssets() {
        return archiveAssets;
    }

    public void setArchiveAssets(Collection<Asset> archiveAssets) {
        this.archiveAssets = archiveAssets;
    }

    @DefaultHandler
    public Resolution filling() {
        
        OperationManager om = super.getOperationManager();
        AssetManager     am = super.getAssetManager();
        User user = super.getUser();

        this.setArchiveAssets(WrapUtil.wrapAssets(am.findArchivedAssets(user), om));
        
        return new ForwardResolution(Views.EDIT_ASSETS);
    }
    
    
}