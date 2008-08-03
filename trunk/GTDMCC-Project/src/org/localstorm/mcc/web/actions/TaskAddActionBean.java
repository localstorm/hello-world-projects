package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.tasks.*;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddTask")
public class TaskAddActionBean extends ListViewActionBean
{

    @Validate( required=true )
    private String summary;
    
    /*@Validate( required=true )
    private int listId;

    public int getListId() {
        return listId;
    }*/

    public String getSummary() {
        return summary;
    }

    /*public void setListId(int listId) {
        this.listId = listId;
    }*/

    public void setSummary(String summary) {
        this.summary = summary;
    }

    
    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            System.out.println("Forced Filling!");
            super.filling();
        }
    }
    
    @DefaultHandler
    public Resolution addTask() throws Exception {
        System.out.println("Adding task:"+getSummary());

        ListManager lm = getListManager();
        GTDList list = lm.findById(getListId());
        
        Task t = new Task(getSummary(), "", list, null, null);
        t.setSortOrder(1);
        
        TaskManager tm = getTaskManager();
        tm.create(t);
        
        RedirectResolution rr = new RedirectResolution( ListViewActionBean.class );
        rr.addParameter( "listId", getListId() );
        return rr;
    }
    
}
