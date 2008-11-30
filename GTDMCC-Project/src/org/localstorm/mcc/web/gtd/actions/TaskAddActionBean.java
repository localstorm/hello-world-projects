package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlan;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.lists.ListManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddTask")
public class TaskAddActionBean extends ListViewActionBean
{

    @Validate( required=true )
    private String summary;

    @Validate( required=true )
    private Integer effort;

    private boolean flight;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

    public boolean isFlight() {
        return flight;
    }

    public void setFlight(boolean flight) {
        this.flight = flight;
    }
    
    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }
    
    @DefaultHandler
    public Resolution addTask() throws Exception {

        ListManager lm = getListManager();
        GTDList list = lm.findById(super.getListId());
        
        Task t = new Task(this.getSummary(), "", list, null, null);
        t.setEffort(this.getEffort());
        t.setSortOrder(1);
        
        TaskManager tm = getTaskManager();
        tm.create(t);

        if (t.getId()!=null && this.isFlight()) {
            FlightPlanManager fpm = super.getFlightPlanManager();
            FlightPlan fp         = fpm.findCurrent(super.getUser());
            fpm.addTaskToFlightPlan(t, fp, true);
        }
        
        RedirectResolution rr = new RedirectResolution( ListViewActionBean.class );
        rr.addParameter( ListViewActionBean.IncommingParameters.LIST_ID, super.getListId() );
        return rr;
    }
    
}