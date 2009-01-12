package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlan;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.util.FilterUtil;

@UrlBinding("/actions/ViewFlightPlan")
public class FlightPlanViewActionBean extends GtdBaseActionBean {

    private FlightPlan flightPlan;
    private Collection<Task> flightPlanTasks;
    private Collection<Task> archiveFlightPlanTasks;
    private Collection<Task> awaitedFlightPlanTasks;
    
    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public Collection<Task> getFlightPlanTasks() {
        return flightPlanTasks;
    }

    public Collection<Task> getArchiveFlightPlanTasks() {
        return archiveFlightPlanTasks;
    }

    public Collection<Task> getAwaitedFlightPlanTasks() {
        return awaitedFlightPlanTasks;
    }
    
    
    @DefaultHandler
    public Resolution filling() {
        super.clearCurrent();
        
        FlightPlanManager fpm = this.getFlightPlanManager();

        User user = super.getUser();
        this.flightPlan      = fpm.findByUser(user);
        this.flightPlanTasks = fpm.getTasksFromFlightPlan(flightPlan);
        
        archiveFlightPlanTasks = new LinkedList<Task>();
        awaitedFlightPlanTasks = new LinkedList<Task>();
        
        for (Iterator<Task> it = flightPlanTasks.iterator(); it.hasNext(); )
        {
            Task t = it.next();
            if (t.isFinished() || t.isCancelled()){
                it.remove();
                archiveFlightPlanTasks.add(t);
                continue;
            }
            if (t.isAwaited() || t.isDelegated())
            {
                it.remove();
                awaitedFlightPlanTasks.add(t);
            }
        }

        Integer ctxId = super.getContextIdFilter();
        
        FilterUtil.applyContextFilter(flightPlanTasks, ctxId);
        FilterUtil.applyContextFilter(archiveFlightPlanTasks, ctxId);
        FilterUtil.applyContextFilter(awaitedFlightPlanTasks, ctxId);
        
        this.setAffectedContexts(this.flightPlanTasks,
                                 this.awaitedFlightPlanTasks,
                                 this.archiveFlightPlanTasks);

        this.setReturnPageBean(new ReturnPageBean(Pages.FPV.toString()));
        
        return new ForwardResolution(Views.FPV);
    }

}