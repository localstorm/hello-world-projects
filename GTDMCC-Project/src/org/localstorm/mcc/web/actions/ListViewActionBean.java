package org.localstorm.mcc.web.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewList")
public class ListViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int listId;
    
    private Collection<Task> tasks;

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }
    
    
    public int getListId() {
        return listId;
    }

    public void setListId(int id) {
        this.listId = id;
    }
    
    private GTDList listResult;

    public GTDList getListResult() {
        return listResult;
    }

    public void setListResult(GTDList listResult) {
        this.listResult = listResult;
    }

    
    @DefaultHandler
    public Resolution filling() throws Exception {
        GTDList list = getListManager().findById(getListId());
        this.setListResult( list );
        
        TaskManager tm = getTaskManager();
        this.setTasks(tm.findByList(list));
        
        System.out.println("Viewing list:" +listId);
        return new ForwardResolution("/jsp/viewList.jsp");
    }
}
