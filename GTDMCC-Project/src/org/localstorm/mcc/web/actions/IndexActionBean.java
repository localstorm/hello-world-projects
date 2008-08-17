package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.flight.FlightPlan;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.*;
import org.localstorm.mcc.web.Views;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/Index")
public class IndexActionBean extends BaseActionBean {

    private FlightPlan flightPlan;
    private Collection<Task> flightPlanTasks;
    private Collection<Task> archiveFlightPlanTasks;
    private Collection<Task> awaitedFlightPlanTasks;
    private List<GTDList> affectedList;

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
    
    public Collection<GTDList> getAffectedLists() {
        return affectedList;
    }
    
    
    
    @DefaultHandler
    public Resolution filling() {
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        
        FlightPlanManager fpm = this.getFlightPlanManager();

        this.flightPlan      = fpm.findCurrent(super.getUser());
                
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
        affectedList           = new LinkedList<GTDList>();
        
        for (Iterator<Task> it = flightPlanTasks.iterator(); it.hasNext(); )
        {
            Task t = it.next();
            GTDList list = t.getList();
            
            if (map.get(list.getId())==null) {
                affectedList.add(list);
                map.put(list.getId(), Boolean.TRUE);
            }
            
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
        
        Collections.sort(affectedList, new Comparator<GTDList>() {
            @Override
            public int compare(GTDList o1, GTDList o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });        
        
        return new ForwardResolution(Views.IDX);
    }

}