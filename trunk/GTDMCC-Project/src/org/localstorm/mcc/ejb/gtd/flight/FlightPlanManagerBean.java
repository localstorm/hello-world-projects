package org.localstorm.mcc.ejb.gtd.flight;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.AbstractSingletonManager;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author Alexey Kuznetsov
 */
@Stateless 
public  class FlightPlanManagerBean extends AbstractSingletonManager<FlightPlan, User> 
                                    implements FlightPlanManagerLocal, FlightPlanManagerRemote
{

    @Override
    public void addTaskToFlightPlan(Task t, FlightPlan fp, boolean newTask) {

        if (newTask) {
            FlightPlanToTask fp2t = new FlightPlanToTask(fp, t);
            em.persist(fp2t);
            return;
        }
        
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_CONNECTORS_BY_TASK_AND_PLAN);
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
    public void addTaskToFlightPlan(Task t, FlightPlan fp) {
        this.addTaskToFlightPlan(t, fp, false);
    }
    
    @Override
    public void removeTaskFromFlightPlan(Task t, FlightPlan fp) {
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_CONNECTORS_BY_TASK_AND_PLAN);
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
        Query uq = em.createNamedQuery(FlightPlanToTask.Queries.FIND_TASKS_BY_PLAN);
        {
            uq.setParameter(FlightPlanToTask.Properties.FLIGHT_PLAN, fp);
        }
        return uq.getResultList();
    }

    @Override
    public FlightPlan findByUser(User owner) {
        try {
            return this.findByUser(owner, true);
        } catch(ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FlightPlan findByUser(User u, boolean createIfNone) throws ObjectNotFoundException {
        Query uq = em.createNamedQuery(FlightPlan.Queries.FIND_CURRENT_BY_USER);
        uq.setParameter(FlightPlan.Properties.OWNER, u);
        
        List<FlightPlan> list = uq.getResultList();
        FlightPlan result = null;
        
        switch (list.size())
        {
            case 0:
                if (createIfNone) {
                    result = this.create(u);
                } else {
                    throw new ObjectNotFoundException();
                }
                break;
            case 1:
                result = list.get(0);
                break;
            default:
                throw new RuntimeException("Unexpected case: two or more flight plans found!");
        }

        return result;
    }

    @Override
    protected FlightPlan create(User u) {
        
        try 
        {
            Query uq = em.createNamedQuery(FlightPlan.Queries.FIND_CURRENT_BY_USER);
            uq.setParameter(FlightPlan.Properties.OWNER, u);

            List<FlightPlan> list = uq.getResultList();

            if ( list.size()==1 ) {
                FlightPlan fp = list.iterator().next();
                Collection<Task> tasks = this.getTasksFromFlightPlan(fp);
                for (Task t: tasks) {
                    this.removeTaskFromFlightPlan(t, fp);
                }
                return fp;
            }

            FlightPlan result = new FlightPlan(u);
            em.persist(result);
            return result;
            
        } catch(EntityExistsException e) 
        {
            throw new RuntimeException(e);
        }
    }
}
