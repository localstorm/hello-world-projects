package org.localstorm.mcc.web.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.flight.*;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.tasks.*;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveFlightTask")
public class FlightTaskResolveActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int taskId;
    
    @Validate( required=true )
    private String action;
    
    private String runtimeNote;

    public String getRuntimeNote() {
        return runtimeNote;
    }

    public void setRuntimeNote(String runtimeNote) {
        this.runtimeNote = runtimeNote;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    @DefaultHandler
    public Resolution resolvingTask() throws Exception {
        
        TaskManager tm = getTaskManager();
        ListManager lm = getListManager();
        FlightPlanManager fpm = this.getFlightPlanManager();
        
        Task t = tm.findById(this.getTaskId());
        
        User u = (User) this.getUser();
        FlightPlan fp = fpm.findCurrent(u);
        
        switch (ACTIONS.valueOf(this.getAction())) {
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
                t.setRuntimeNote(this.getRuntimeNote());
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
            default:
                throw new RuntimeException("Unexpected action:"+this.getAction());
        }
        tm.update(t);
        
        // List retirement?
        GTDList list = t.getList();
        list.setArchived( (!list.isPinned()) && noMoreTasksPending(list) );
        lm.update(list);
        
        if (noMoreFlightTasksPending(fpm, fp))
        {
           fpm.utilizeCurrent(u);
        }
        
        return new RedirectResolution(IndexActionBean.class);
    }
    
    
    private boolean noMoreFlightTasksPending(FlightPlanManager fpm, FlightPlan fp) {
        Collection<Task> tasks = fpm.getTasksFromFlightPlan(fp);
        
        for (Task t: tasks) {
            if (!t.isFinished() && !t.isCancelled()) {
                return false;
            }
        }
        
        return true;
    }

    private boolean noMoreTasksPending(GTDList list) {
        TaskManager tm = getTaskManager();
        
        return ( tm.findAwaitedByList(list).isEmpty() 
                 &&
                 tm.findOpeartiveByList(list).isEmpty() );
    }
    
    private static enum ACTIONS 
    {
        CANCEL,
        FINISH,
        UNDELEGATE,
        DELEGATE,
        UNRESOLVE,
        FLIGHT,
        UNFLIGHT
    }
}
