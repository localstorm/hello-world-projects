package org.localstorm.mcc.ejb.flight.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractSingletonManager;
import org.localstorm.mcc.ejb.flight.*;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author Alexey Kuznetsov
 */
@Stateless 
public  class FlightPlanManagerBean extends AbstractSingletonManager<FlightPlan, User> 
                                    implements FlightPlanManagerLocal, FlightPlanManagerRemote
{

    public FlightPlanManagerBean() {
        super(FlightPlan.class);
    }

    @Override
    public void addTaskToFlightPlan(Task t, FlightPlan fp) {
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_BY_TASK_AND_PLAN);
        {
            uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
            uq.setParameter(FlightPlanToTask.Properties.TASK, t);
        }
        
        List<FlightPlanToTask> list = uq.getResultList();
        if (list.isEmpty()) {
            FlightPlanToTask fp2t = new FlightPlanToTask(fp, t);
            em.persist(fp2t);
        }
    }
    
    @Override
    public void removeTaskFromFlightPlan(Task t, FlightPlan fp) {
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_BY_TASK_AND_PLAN);
        {
            uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
            uq.setParameter(FlightPlanToTask.Properties.TASK, t);
        }
        
        List<FlightPlanToTask> list = uq.getResultList();
        for (FlightPlanToTask fp2t: list) {
            em.remove(fp2t);
        }
    }
    
    @Override
    public Collection<Task> getTasksFromFlightPlan(FlightPlan fp) {
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_BY_PLAN);
        {
            uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
        }
        return uq.getResultList();
    }
    
    @Override
    public FlightPlan findCurrent(User u) {
        Query uq = em.createNamedQuery(FlightPlan.Queries.FIND_CURRENT_BY_USER);
        uq.setParameter(FlightPlan.Properties.OWNER, u);
        
        List<FlightPlan> list = uq.getResultList();
        FlightPlan result = null;
        
        switch (list.size())
        {
            case 0: 
                result = this.createNewCurrent(u);
                break;
            default:
            
                Iterator<FlightPlan> it = list.iterator();
                
                while ( it.hasNext() ) {
                    FlightPlan fp = it.next();
                    if ( it.hasNext() ) {
                        fp.setArchived(true);
                        this.update(result);
                    }
                }
                
            /* No break here! :) */
            case 1:
                result = list.get(list.size()-1); 
                break;
        }
        return result;
    }

    @Override
    protected FlightPlan createNewCurrent(User u) {
        
        try 
        {
            FlightPlan result = new FlightPlan(u);
            em.persist(result);
            System.out.println("============DDDDD============");
            return result;
        } catch(EntityExistsException e) 
        {
            throw new RuntimeException(e);
        }
        
    }
}
