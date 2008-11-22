package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlan;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Effort;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EasyTasksReport")
public class EasyTasksReportActionBean extends BaseActionBean
{
    private List<Task> tasks;
    
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<? extends Task> tasks) {
        this.tasks = new ArrayList<Task>(tasks);
    }


    @DefaultHandler
    public Resolution filling() throws Exception {

        TaskManager       tm  = super.getTaskManager();
        FlightPlanManager fpm = super.getFlightPlanManager();

        Collection<Task> easy = tm.findByMaxEffort(Effort.EASY, super.getUser());
        FlightPlan       fp   = fpm.findCurrent(super.getUser());
        Collection<Task> fpTasks = fpm.getTasksFromFlightPlan(fp);
        
        Integer ctxId = super.getContextIdFilter();
        FilterUtil.applyContextFilter(easy, ctxId);

        this.setTasks(WrapUtil.genWrappers(easy, fpTasks));
        return new ForwardResolution(Views.VIEW_EASY);
    }
    
}
