package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.ReturnPageBean;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/cash/nil/BalanceHistoryReport")
public class BalanceHisotryReportActionBean extends CashflowBaseActionBean {

    @DefaultHandler
    public Resolution filling() {
        ReturnPageBean rpb = new ReturnPageBean(Pages.BALANCE_HISTORY.toString());
        super.setReturnPageBean(rpb);
        
        return new ForwardResolution(Views.BALANCE_HISTORY);
    }

}