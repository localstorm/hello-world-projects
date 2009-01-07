package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.gtd.contexts.ContextManager;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlan;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.TaskWrapper;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/OldTasksReport")
public class OldTasksReportActionBean extends GtdBaseActionBean
{
    private Map<Integer, Collection<TaskWrapper>> tasksResult;
    
    public Map<Integer, Collection<TaskWrapper>> getTasksResult() {
        return tasksResult;
    }

    public void setTasksResult(Map<Integer, Collection<TaskWrapper>> tasksResult) {
        this.tasksResult = tasksResult;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {

        ContextManager    cm  = super.getContextManager();
        TaskManager       tm  = super.getTaskManager();
        FlightPlanManager fpm = super.getFlightPlanManager();

        Integer ctxIdFilter = super.getContextIdFilter();
        Collection<Context> ctxs;

        if ( ctxIdFilter>0 ) {
            ctxs = new ArrayList<Context>(1);
            ctxs.add(cm.findById(ctxIdFilter));
        } else {
            ctxs = cm.findByOwner(super.getUser());
        }

        FlightPlan       fp      = fpm.findByUser(super.getUser());
        Collection<Task> fpTasks = fpm.getTasksFromFlightPlan(fp);

        Map<Integer, Collection<TaskWrapper>> res  = new HashMap<Integer, Collection<TaskWrapper>>();
        Collection<Context> affected = new ArrayList<Context>();
        for (Context ctx : ctxs) {
            Collection<Task> old = tm.findOldestOperative(ctx,
                                                          Constants.MAX_OLDEST_TASKS);
            Collection<TaskWrapper> tasks = WrapUtil.genWrappers(old, fpTasks);
            res.put(ctx.getId(), tasks);

            if (!tasks.isEmpty()) {
                affected.add(ctx);
            }
        }

        if ( ctxIdFilter>0 && affected.isEmpty() ) {
            affected.add(cm.findById(ctxIdFilter));
        }

        this.setTasksResult(res);

        super.setAffectedContexts(affected);
        super.setReturnPageBean(new ReturnPageBean(Pages.OLD_REPORT.toString()));

        return new ForwardResolution(Views.VIEW_OLD);
    }
    
}
