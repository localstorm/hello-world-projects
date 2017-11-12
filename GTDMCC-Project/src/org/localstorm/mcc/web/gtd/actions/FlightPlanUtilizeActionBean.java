package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.web.util.RedirectUrlBuilderUtil;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/UtilizeFlightPlan")
public class FlightPlanUtilizeActionBean extends GtdBaseActionBean
{
    @DefaultHandler
    @Logged
    public Resolution utilization() throws Exception {
        this.getFlightPlanManager().utilizeCurrent(this.getUser());
        return RedirectUrlBuilderUtil.redirect(FlightPlanViewActionBean.class);
    }

}
