package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/NetWorthHistoryReport")
public class NetWorthHisotryReportActionBean extends CashflowBaseActionBean {

    @DefaultHandler
    @Logged
    public Resolution filling() {
        ReturnPageBean rpb = new ReturnPageBean(Pages.NET_WEALTH_HISTORY.toString());
        super.setReturnPageBean(rpb);
        return new ForwardResolution(Views.NET_WEALTH_HISTORY);
    }

}