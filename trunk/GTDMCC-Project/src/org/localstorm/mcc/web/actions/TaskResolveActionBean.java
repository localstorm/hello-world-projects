package org.localstorm.mcc.web.actions;

import javax.servlet.http.HttpSession;
import javax.transaction.Status;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.web.backend.TaskResolutionLogic;
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

        GTDList list = TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                                       TaskResolutionAction.valueOf(this.getAction()), 
                                                       this.getRuntimeNote(), 
                                                       this.getCurrentList(), 
                                                       super.getClipboard(), 
                                                       super.getTaskManager(), 
                                                       super.getListManager(), 
                                                       super.getFlightPlanManager(), 
                                                       super.getUser());
        

        RedirectResolution rr = null;


        if (list.isArchived()) {
            rr = new RedirectResolution(ContextViewActionBean.class);
            {
                rr.addParameter(ContextViewActionBean.IncommingParameters.CTX_ID, list.getContext().getId());
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
