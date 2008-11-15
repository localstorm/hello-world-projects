package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.flight.FlightPlan;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.tasks.*;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/Index")
public class IndexActionBean extends BaseActionBean {

    private FlightPlan flightPlan;
    private Collection<Task> flightPlanTasks;
    private Collection<Task> archiveFlightPlanTasks;
    private Collection<Task> awaitedFlightPlanTasks;
    
    private boolean redlinesBroken;
    private boolean deadlinesBroken;

    public boolean isDeadlinesBroken() {
        return deadlinesBroken;
    }

    public boolean isRedlinesBroken() {
        return redlinesBroken;
    }

    public void setRedlinesBroken(boolean redlinesBroken) {
        this.redlinesBroken = redlinesBroken;
    }

    public void setDeadlinesBroken(boolean deadlinesBroken) {
        this.deadlinesBroken = deadlinesBroken;
    }

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
        this.flightPlan      = fpm.findCurrent(user);
                
        this.flightPlanTasks = fpm.getTasksFromFlightPlan(flightPlan);
        
        List<Task> l = new ArrayList<Task>(flightPlanTasks.size());
        l.addAll(flightPlanTasks);
        
        Collections.sort(l, new Comparator<Task>() {

            @Override
            public int compare(Task t1, Task t2) {
                return t1.getList().getContext().getName().compareTo(
                            t2.getList().getContext().getName()
                       );
            }
            
        });
        
        flightPlanTasks.clear();
        flightPlanTasks.addAll(l);
        
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
            if (t.isAwaited())
            {
                it.remove();
                awaitedFlightPlanTasks.add(t);
            }
        }
        
        this.applyIndexFilter(flightPlanTasks);
        this.applyIndexFilter(archiveFlightPlanTasks);
        this.applyIndexFilter(awaitedFlightPlanTasks);
        
        TaskManager tm = this.getTaskManager();
        this.setRedlinesBroken(!tm.findRedlinedTasks(user).isEmpty());
        this.setDeadlinesBroken(!tm.findDeadlinedTasks(user).isEmpty());

        return new ForwardResolution(Views.IDX);
    }

    private void applyIndexFilter(Collection<Task> flightPlanTasks) 
    {
        Integer ctx = (Integer) SessionUtil.getValue(super.getSession(), SessionKeys.FP_FILTER_CONTEXT);
        
        if ( ctx<0 ) {
            return;
        }
        
        for (Iterator<Task> it = flightPlanTasks.iterator(); it.hasNext(); ) {
            Task t = it.next();
            if (!ctx.equals(t.getList().getContext().getId())) {
                it.remove();
            }
        }
    }

}