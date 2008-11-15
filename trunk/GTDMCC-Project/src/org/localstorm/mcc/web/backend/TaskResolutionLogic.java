package org.localstorm.mcc.web.backend;

import java.util.Collection;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.flight.FlightPlan;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.ejb.tasks.TaskResolutionAction;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Clipboard;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TaskResolutionLogic 
{
    public static GTDList resolveTask( Integer taskId,
                                       TaskResolutionAction action,
                                       String runtimeNote,
                                       GTDList currentList,
                                       Clipboard clip,
                                       TaskManager tm,
                                       ListManager lm,
                                       FlightPlanManager fpm,
                                       User u) throws ObjectNotFoundException
    {
        Task t = tm.findById(taskId);
        
        FlightPlan fp = fpm.findCurrent(u);
        
        boolean update = true;
        
        switch (action) {
            case COPY:
                clip.copyTask(t);
                break;
            case PASTE:
                t = clip.pickTask(taskId);
                if (t!=null)
                {
                    t.setList(currentList);
                } else {
                    t = tm.findById(taskId);
                }
                break;
            case UNFLIGHT:
                fpm.removeTaskFromFlightPlan(t, fp);
                break;
            case FLIGHT:
                fpm.addTaskToFlightPlan(t, fp);
                break;
            case UNRESOLVE:
                t.setFinished(false);
                t.setDelegated(false);
                t.setPaused(false);
                t.setAwaited(false);
                t.setCancelled(false);
                break;
            case UNDELEGATE:
                // Undelegating
                t.setRuntimeNote(null);
                t.setAwaited(false);
                t.setDelegated(false);
                break;
            case DELEGATE:
                // Delegating
                t.setRuntimeNote(runtimeNote);
                t.setAwaited(true);
                t.setDelegated(true);
                break;
            case CANCEL:
                // Cancelling tasks
                t.setCancelled(true);
                t.setFinished(false);
                break;
            case FINISH:
                // Finishing tasks
                t.setFinished(true);
                t.setCancelled(false);
                break;
            case REMOVE:
                update = false;
                break;
            default:
                throw new RuntimeException("Unexpected action:"+action);
        }

        GTDList list = t.getList();

        if (update)
        {
            tm.update(t);
        } else {
            tm.remove(t);
        }

        // List retirement?
        list.setArchived( (!list.isPinned()) && noMoreTasksPending(tm, list) );
        lm.update(list);
        
        if (noMoreFlightTasksPending(fpm, fp))
        {
           fpm.utilizeCurrent(u);
        }
        
        return list;
    }
    
     private static boolean noMoreFlightTasksPending(FlightPlanManager fpm, FlightPlan fp) {
        Collection<Task> tasks = fpm.getTasksFromFlightPlan(fp);
        
        for (Task t: tasks) {
            if (!t.isFinished() && !t.isCancelled()) {
                return false;
            }
        }
        
        return true;
    }

    private static boolean noMoreTasksPending(TaskManager tm, GTDList list) {
        return ( tm.findAwaitedByList(list).isEmpty() 
                 &&
                 tm.findOpeartiveByList(list).isEmpty() );
    }
}
