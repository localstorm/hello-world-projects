package org.localstorm.mcc.web.actions;

import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.tasks.*;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.Clipboard;
import org.localstorm.mcc.web.SessionKeys;

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
    
    private String runtimeNote;

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

    public String getRuntimeNote() {
        return runtimeNote;
    }

    public void setRuntimeNote(String runtimeNote) {
        this.runtimeNote = runtimeNote;
    }
    
    @DefaultHandler
    public Resolution resolvingTask() throws Exception {
        
        TaskManager tm = getTaskManager();
        ListManager lm = getListManager();
        
        Task t = tm.findById(this.getTaskId());
        Clipboard clip = super.getClipboard();
        
        boolean update = true;
        
        switch (ACTIONS.valueOf(this.getAction())) {
            case PASTE:
                t = clip.pickTask(this.getTaskId());
                if (t!=null)
                {
                    t.setList(super.getCurrentList());
                } else {
                    t = tm.findById(this.getTaskId());
                }
                break;
            case COPY:
                
                clip.copyTask(t);
                break;
            case FLIGHT:
                HttpSession sess = getSession();
                User u = (User) sess.getAttribute(SessionKeys.USER);
        
                if (u==null) {
                    throw new RuntimeException("USER IS NULL");
                }
                
                FlightPlanManager fpm = this.getFlightPlanManager();
                fpm.addTaskToFlightPlan(t, fpm.findCurrent(u));
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
            case REMOVE:
                update = false;
                break; 
            default:
                throw new RuntimeException("Unexpected action:"+this.getAction());
        }
        
        
        GTDList list = t.getList();
        
        if (update)
        {
            tm.update(t);
        } else {
            tm.remove(t);
        }
        
        RedirectResolution rr = null;
        
        list.setArchived((!list.isPinned()) && noMoreTasksPending(list));
        lm.update(list);
 
        if (list.isArchived()) {
            rr = new RedirectResolution(ContextViewActionBean.class);
            {
                rr.addParameter(ContextViewActionBean.IncommingParameters.CTX_ID, super.getCurrentContext().getId());
            }
        } else {
            rr = new RedirectResolution(ListViewActionBean.class);
            {
                GTDList curList = super.getCurrentList();
                if (curList!=null)
                {
                    Integer id = curList.getId();
                    rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, id);
                } else {
                    rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, list.getId());
                }
            }
        }
        
        return rr;
    }
    
    private static enum ACTIONS 
    {
        PASTE,
        COPY,
        CANCEL,
        FINISH,
        UNDELEGATE,
        DELEGATE,
        UNRESOLVE,
        FLIGHT,
        REMOVE
    }

    private boolean noMoreTasksPending(GTDList list) {
        TaskManager tm = getTaskManager();
        
        return ( tm.findAwaitedByList(list).isEmpty() 
                 &&
                 tm.findOpeartiveByList(list).isEmpty() );
    }
    
}
