package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UtilizeFlightPlan")
public class FlightPlanUtilizeActionBean extends BaseActionBean
{
    @DefaultHandler
    public Resolution utilization() throws Exception {
        this.getFlightPlanManager().utilizeCurrent(this.getUser());
        System.out.println("FUKK");
        return new RedirectResolution(IndexActionBean.class);
    }

}
