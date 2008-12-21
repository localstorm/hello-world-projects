package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.gtd.backend.TaskResolutionLogic;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveFlightTask")
public class FlightTaskResolveActionBean extends GtdBaseActionBean
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
                                        this.getResolveAction(),
                                        this.getRuntimeNote(),
                                        super.getCurrentList(),
                                        super.getClipboard(),
                                        super.getTaskManager(),
                                        super.getListManager(),
                                        super.getFlightPlanManager(),
                                        super.getUser());

        SessionUtil.clear(super.getSession(), SessionKeys.NEED_CLEANUP);
        return new RedirectResolution(IndexActionBean.class);
    }

    private TaskResolutionAction getResolveAction() {
        return TaskResolutionAction.valueOf(action);
    }
   
}
