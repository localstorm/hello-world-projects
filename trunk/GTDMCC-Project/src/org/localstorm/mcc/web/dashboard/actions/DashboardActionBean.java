package org.localstorm.mcc.web.dashboard.actions;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.Views;

import org.localstorm.mcc.web.gtd.GtdDashboardAgent;
import org.localstorm.mcc.web.people.PeopleDashboardAgent;

@UrlBinding("/actions/Dashboard")
public class DashboardActionBean extends BaseActionBean {

    
    @DefaultHandler
    public Resolution filling() {

        ActionBeanContext ctx = super.getContext();

        GtdDashboardAgent.prepare(ctx);
        PeopleDashboardAgent.prepare(ctx);
        
        return new ForwardResolution(Views.DASH);
    }

}