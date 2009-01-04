package org.localstorm.mcc.web.gtd.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlan;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Effort;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EasyTasksReport")
public class EasyTasksReportActionBean extends GtdBaseActionBean
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

        super.setAffectedContexts(this.getTasks());

        super.setReturnPageBean(new ReturnPageBean(Pages.EASY_REPORT.toString()));

        return new ForwardResolution(Views.VIEW_EASY);
    }
    
}
