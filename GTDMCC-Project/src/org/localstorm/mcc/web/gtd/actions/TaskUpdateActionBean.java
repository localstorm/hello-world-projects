package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.Constants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UpdateTask")
public class TaskUpdateActionBean extends TaskViewActionBean
{
    @Validate( required=true )
    private String summary;
    
    private String details;
    
    @Validate( required=true )
    private Integer effort;

    public String getSummary() {
        return summary;
    }

    public String getDetails() {
        return details;
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

    public Integer getEffort() {
        return effort;
    }

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    public Resolution updatingTask() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        
        TaskManager tm = getTaskManager();
        Task t = tm.findById(super.getTaskId());
        
        t.setSummary(this.getSummary());
        t.setDetails(this.getDetails());

        t.setRedline(this.parse(this.getRedline(), sdf));
        t.setDeadline(this.parse(this.getDeadline(), sdf));
        t.setEffort(this.getEffort());
        
        tm.update(t);
        
        RedirectResolution rr;
        
        String returnPage = super.getReturnPage();
        ReturnPages rp = (returnPage==null) ? ReturnPages.LIST_VIEW : ReturnPages.valueOf(returnPage);
        
        switch (rp)
        {
            case FPV:
                rr = new RedirectResolution(FlightPlanViewActionBean.class);
                break;
            case AW_REPORT:
                rr = new RedirectResolution(AwaitingsReportActionBean.class);
                 break;
            case DL_REPORT:
                rr = new RedirectResolution(DeadlineLookupReportActionBean.class);
                break;
            case EASY_REPORT:
                rr = new RedirectResolution(EasyTasksReportActionBean.class);
                break;
            case OLD_REPORT:
                rr = new RedirectResolution(OldTasksReportActionBean.class);
                break;

            default:
            case LIST_VIEW:
                rr = new RedirectResolution(ListViewActionBean.class);
                rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, t.getList().getId());
                break;
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
