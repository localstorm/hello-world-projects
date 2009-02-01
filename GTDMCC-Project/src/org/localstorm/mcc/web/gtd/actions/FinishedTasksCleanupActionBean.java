package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.dashboard.actions.DashboardActionBean;

/**
 * @secure-by session (no security check)
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/CleanupFinishedTasks")
public class FinishedTasksCleanupActionBean extends GtdBaseActionBean {

    @DefaultHandler
    public Resolution cleanup() {
        
        TaskManager tm = super.getTaskManager();
        tm.removeFinishedTasks(this.getUser());

        return new RedirectResolution( DashboardActionBean.class );
    }
    
}