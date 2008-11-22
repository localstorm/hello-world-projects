package org.localstorm.mcc.web.actions;

import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.web.backend.TaskResolutionLogic;

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
        
        TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                        TaskResolutionAction.valueOf(action), 
                                        this.getRuntimeNote(), 
                                        this.getCurrentList(),
                                        super.getClipboard(),
                                        super.getTaskManager(), 
                                        super.getListManager(), 
                                        super.getFlightPlanManager(), 
                                        super.getUser());
        
        return new RedirectResolution(IndexActionBean.class);
    }
    
    
   
    
   
}
