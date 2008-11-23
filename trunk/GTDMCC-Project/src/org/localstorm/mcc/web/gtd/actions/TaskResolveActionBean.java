package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.web.gtd.backend.TaskResolutionLogic;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveTask")
public class TaskResolveActionBean extends GtdBaseActionBean
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

        TaskResolutionAction act = TaskResolutionAction.valueOf(this.getAction());
        GTDList list = TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                                       act, 
                                                       this.getRuntimeNote(), 
                                                       this.getCurrentList(), 
                                                       super.getClipboard(), 
                                                       super.getTaskManager(), 
                                                       super.getListManager(), 
                                                       super.getFlightPlanManager(), 
                                                       super.getUser());
        
        return NextDestinationUtil.taskResolveActionResolution(act, list, super.getCurrentList());
    }
    
}
