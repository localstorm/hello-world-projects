package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/CleanupFinishedTasks")
public class FinishedTasksCleanupActionBean extends GtdBaseActionBean {

    @DefaultHandler
    public Resolution cleanup() {
        
        TaskManager tm = super.getTaskManager();
        tm.cleanup(this.getUser());

        SessionUtil.clear(super.getSession(), SessionKeys.NEED_CLEANUP);
        return new RedirectResolution( IndexActionBean.class );
    }
    
}