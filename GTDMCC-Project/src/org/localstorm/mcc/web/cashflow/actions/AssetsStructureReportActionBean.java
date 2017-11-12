package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/AssetsStructureReport")
public class AssetsStructureReportActionBean extends CashflowBaseActionBean {

    @DefaultHandler
    @Logged
    public Resolution filling() {
        
        return new ForwardResolution(Views.ASSETS_STRUCTURE);
    }

}