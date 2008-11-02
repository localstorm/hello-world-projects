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
import org.localstorm.mcc.web.ReturnPages;

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
    
    private String returnPage;
    
    @Validate( required=true )
    private Integer effort;

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

    public void setEffort(Integer effort) {
        this.effort = effort;
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

    public String getReturnPage() {
        return returnPage;
    }

    public Integer getEffort() {
        return effort;
    }
    
    

    public void setReturnPage(String returnPage) {
        this.returnPage = returnPage;
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
        t.setEffort(this.getEffort());
        
        tm.update(t);
        
        System.out.println("Updated task:" +taskId);
        
        RedirectResolution rr;
        if (ReturnPages.IDX.toString().equals(returnPage)) {
            rr = new RedirectResolution(IndexActionBean.class);
        } else {
            rr = new RedirectResolution(ListViewActionBean.class);
            rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, t.getList().getId());
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
