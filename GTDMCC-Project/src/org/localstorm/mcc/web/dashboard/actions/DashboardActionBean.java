package org.localstorm.mcc.web.dashboard.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.Views;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/Dashboard")
public class DashboardActionBean extends BaseActionBean {

    
    @DefaultHandler
    public Resolution filling() {

        return new ForwardResolution(Views.DASH);
    }

}