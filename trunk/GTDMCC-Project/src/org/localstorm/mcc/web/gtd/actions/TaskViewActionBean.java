package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.web.Constants;
import java.text.SimpleDateFormat;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.DateUtil;
import org.localstorm.mcc.web.gtd.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewTask")
public class TaskViewActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int taskId;

    private Task taskResult;
    private String deadline;
    private String redline;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }
    
    public Task getTaskResult() {
        return taskResult;
    }
    
    public void setTaskResult(Task taskResult) {
        this.taskResult = taskResult;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    
    public String getRedline() {
        return redline;
    }

    public void setRedline(String redline) {
        this.redline = redline;
    }

    
    @DefaultHandler
    public Resolution filling() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        
        TaskManager tm = getTaskManager();
        
        Task task = tm.findById(this.getTaskId());
        
        super.setCurrent(task);
        
        this.setTaskResult(task);
        
        this.setDeadline(DateUtil.format(task.getDeadline(), sdf));
        this.setRedline(DateUtil.format(task.getRedline(), sdf));
        
        return new ForwardResolution(Views.VIEW_TASK);
    }

    
}
