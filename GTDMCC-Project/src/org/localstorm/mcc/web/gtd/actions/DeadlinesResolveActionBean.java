package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.tasks.TaskResolutionAction;
import org.localstorm.mcc.web.gtd.backend.TaskResolutionLogic;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveDeadlines")
public class DeadlinesResolveActionBean extends BaseActionBean
{
    @Validate(required=true)
    private Integer taskId;

    @Validate(required=true, expression="(action eq 'FLIGHT') or " +
                                        "(action eq 'FINISH') or " +
                                        "(action eq 'CANCEL')")
    private String action;
    
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {

        TaskResolutionAction act = TaskResolutionAction.valueOf(action);
        
        switch (act)
        {
            case FLIGHT:
                TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                        TaskResolutionAction.FLIGHT,
                                        null, 
                                        super.getCurrentList(), 
                                        super.getClipboard(),
                                        super.getTaskManager(), 
                                        super.getListManager(), 
                                        super.getFlightPlanManager(),
                                        super.getUser());
                break;
            case FINISH:
                this.finish();
                break;
             case CANCEL:
                this.cancel();
                break;
        }
        
        return new RedirectResolution(DeadlinesReportActionBean.class);
    }
    
    
    private void cancel() throws ObjectNotFoundException
    {
        TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                        TaskResolutionAction.UNDELEGATE,
                                        null, 
                                        super.getCurrentList(), 
                                        super.getClipboard(),
                                        super.getTaskManager(), 
                                        super.getListManager(), 
                                        super.getFlightPlanManager(),
                                        super.getUser());
        TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                        TaskResolutionAction.CANCEL,
                                        null, 
                                        super.getCurrentList(), 
                                        super.getClipboard(),
                                        super.getTaskManager(), 
                                        super.getListManager(), 
                                        super.getFlightPlanManager(),
                                        super.getUser());
    }
    
    private void finish() throws ObjectNotFoundException
    {
        TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                        TaskResolutionAction.UNDELEGATE,
                                        null, 
                                        super.getCurrentList(), 
                                        super.getClipboard(),
                                        super.getTaskManager(), 
                                        super.getListManager(), 
                                        super.getFlightPlanManager(),
                                        super.getUser());
        TaskResolutionLogic.resolveTask(this.getTaskId(), 
                                        TaskResolutionAction.FINISH,
                                        null, 
                                        super.getCurrentList(), 
                                        super.getClipboard(),
                                        super.getTaskManager(), 
                                        super.getListManager(), 
                                        super.getFlightPlanManager(),
                                        super.getUser());
    }
}
