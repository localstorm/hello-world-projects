package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewTask")
public class TaskViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    private Task taskResult;

    public Task getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(Task taskResult) {
        this.taskResult = taskResult;
    }

    
    @DefaultHandler
    public Resolution filling() throws Exception {
        TaskManager tm = getTaskManager();
        setTaskResult(tm.findById(getId()));
        System.out.println("Viewing task:" +id);
        return new ForwardResolution("/jsp/viewTask.jsp");
    }
}
