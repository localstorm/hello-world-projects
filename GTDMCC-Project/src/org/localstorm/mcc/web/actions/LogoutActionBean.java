package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.gtd.actions.FlightPlanViewActionBean;
import org.localstorm.mcc.web.util.RedirectUrlBuilderUtil;
import org.localstorm.tools.aop.runtime.Logged;


@UrlBinding("/actions/Logout")
public class LogoutActionBean extends BaseActionBean {

    @DefaultHandler
    @Logged
    public Resolution logout() {
        this.getSession().invalidate();
        return RedirectUrlBuilderUtil.redirect(FlightPlanViewActionBean.class);
    }

}