package org.localstorm.mcc.web.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UpdateTask")
public class TaskUpdateActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int taskId;
    
    @Validate( required=true )
    private String summary;
    
    private String details;
    
    private String deadline;
    
    private String redline;

    public int getTaskId() {
        return taskId;
    }

    public String getSummary() {
        return summary;
    }

    public String getDetails() {
        return details;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getRedline() {
        return redline;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setRedline(String redline) {
        this.redline = redline;
    }
    
    
    @DefaultHandler
    public Resolution updatingTask() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        
        TaskManager tm = getTaskManager();
        Task t = tm.findById(getTaskId());
        
        t.setSummary(this.getSummary());
        t.setDetails(this.getDetails());

        t.setRedline(this.parse(this.getRedline(), sdf));
        t.setDeadline(this.parse(this.getDeadline(), sdf));
        
        tm.update(t);
        
        System.out.println("Updating task:" +taskId);
        RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
        {
            rr.addParameter("listId", t.getList().getId());
        }
        return rr;
    }
    
    private Date parse(String s, DateFormat df) throws Exception {
        if (s!=null) {
            return df.parse(s);
        }
        return null;
    }
}
