package org.localstorm.mcc.web.gtd.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/DeadlinesReport")
public class DeadlinesReportActionBean extends BaseActionBean
{

    private Collection<? extends Task>  redlinedTasks;
    private Collection<? extends Task> deadlinedTasks;

    public Collection<? extends Task> getRedlinedTasks() {
        return redlinedTasks;
    }

    public void setRedlinedTasks(Collection<? extends Task> redlinedTasks) {
        this.redlinedTasks = redlinedTasks;
    }

    public Collection<? extends Task> getDeadlinedTasks() {
        return deadlinedTasks;
    }

    public void setDeadlinedTasks(Collection<? extends Task> deadlinedTasks) {
        this.deadlinedTasks = deadlinedTasks;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        FlightPlanManager fp = this.getFlightPlanManager();
        Collection<Task> fpt = fp.getTasksFromFlightPlan(fp.findCurrent(this.getUser()));
        
        Collection<Task> rlt = this.getTaskManager().findRedlinedTasks(this.getUser());
        Collection<Task> dlt = this.getTaskManager().findDeadlinedTasks(this.getUser());

        Integer ctxId = super.getContextIdFilter();
        FilterUtil.applyContextFilter(rlt, ctxId);
        FilterUtil.applyContextFilter(dlt, ctxId);

        this.setDeadlinedTasks(WrapUtil.genWrappers(dlt, fpt));
        this.setRedlinedTasks(WrapUtil.genWrappers(rlt, fpt));
        return new ForwardResolution(Views.VIEW_DLR);
    }
    
}
