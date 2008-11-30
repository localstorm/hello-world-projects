package org.localstorm.mcc.web.cashflow.actions;

import org.localstorm.mcc.web.cashflow.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/TotalWealthReport")
public class TotalWealthReportActionBean extends CashflowBaseActionBean {

    @DefaultHandler
    public Resolution filling() {
        
        return new ForwardResolution(Views.TOTAL_WEALTH);
    }

}