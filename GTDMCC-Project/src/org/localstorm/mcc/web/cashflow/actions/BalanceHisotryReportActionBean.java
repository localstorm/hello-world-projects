package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @secure-by session (no security checks)
 * @author localstorm
 */
@UrlBinding("/actions/BalanceHistoryReport")
public class BalanceHisotryReportActionBean extends CashflowBaseActionBean {

    @DefaultHandler
    public Resolution filling() {
        
        return new ForwardResolution(Views.BALANCE_HISTORY);
    }

}