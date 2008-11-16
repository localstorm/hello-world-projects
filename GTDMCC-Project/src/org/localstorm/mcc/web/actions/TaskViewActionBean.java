package org.localstorm.mcc.web.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.tasks.Effort;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.web.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewTask")
public class TaskViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int id;

    private Task taskResult;
    
    private String deadline;
    private String redline;
    private String returnPage;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Task getTaskResult() {
        return taskResult;
    }
    
    public Effort[] getEfforts()
    {
        return Effort.values();
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

    public String getReturnPage() {
        return returnPage;
    }

    public void setReturnPage(String returnPage) {
        this.returnPage = returnPage;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        
        TaskManager tm = getTaskManager();
        
        Task task = tm.findById(getId());
        
        super.setCurrent(task);
        
        this.setTaskResult(task);
        
        this.setDeadline(this.format(task.getDeadline(), sdf));
        this.setRedline(this.format(task.getRedline(), sdf));
        
        System.out.println("Viewing task:" +id);
        return new ForwardResolution(Views.VIEW_TASK);
    }

    private String format(Date date, DateFormat sdf) throws Exception 
    {
        if (date!=null) {
            return sdf.format(date);
        } else {
            return "";
        }
    }
}
