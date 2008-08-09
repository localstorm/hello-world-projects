package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.tasks.*;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveTask")
public class TaskResolveActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int taskId;
    
    @Validate( required=true )
    private String action;

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
        
        Task t = tm.findById(this.getTaskId());
        
        switch (ACTIONS.valueOf(this.getAction())) {
            case FLIGHT:
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
                t.setAwaited(false);
                t.setDelegated(false);
                break;
            case DELEGATE:
                // Delegating
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
        
        RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
        {
            rr.addParameter("listId", t.getList().getId());
        }
        return rr;
    }
    
    private static enum ACTIONS 
    {
        CANCEL,
        FINISH,
        UNDELEGATE,
        DELEGATE,
        UNRESOLVE,
        FLIGHT
    }
    
}
