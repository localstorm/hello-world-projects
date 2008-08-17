package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.actions.wrap.TaskWrapper;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewList")
public class ListViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int listId;
    
    private Collection<TaskWrapper> tasks;
    private Collection<TaskWrapper> awaitedTasks;
    private Collection<TaskWrapper> archiveTasks;
    private GTDList listResult;

    public Collection<TaskWrapper> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<TaskWrapper> tasks) {
        this.tasks = tasks;
    }
    
    
    public int getListId() {
        return listId;
    }

    public void setListId(int id) {
        this.listId = id;
    }

    public Collection<TaskWrapper> getArchiveTasks() {
        return archiveTasks;
    }

    public void setArchiveTasks(Collection<TaskWrapper> archiveTasks) {
        this.archiveTasks = archiveTasks;
    }
    
    public GTDList getListResult() {
        return listResult;
    }

    public void setListResult(GTDList listResult) {
        this.listResult = listResult;
    }

    public void setAwaitedTasks(Collection<TaskWrapper> tasks) {
        this.awaitedTasks = tasks;
    }

    public Collection<TaskWrapper> getAwaitedTasks() {
        return this.awaitedTasks;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {
        GTDList list = getListManager().findById(getListId());
        this.setListResult( list );
        
        
        
        TaskManager tm        = this.getTaskManager();
        FlightPlanManager fpm = this.getFlightPlanManager();
        
        Collection<Task> currentFp = fpm.getTasksFromFlightPlan(fpm.findCurrent(this.getUser()));
        
        this.setTasks(genWrappers(tm.findOpeartiveByList(list), currentFp));
        this.setAwaitedTasks(genWrappers(tm.findAwaitedByList(list), currentFp));
        this.setArchiveTasks(genWrappers(tm.findArchiveByList(list), currentFp));
        
        System.out.println("Viewing list:" +listId);
        return new ForwardResolution(Views.VIEW_LIST);
    }

    private Collection<TaskWrapper> genWrappers(Collection<Task> taskList,
                                                Collection<Task> currentFp) 
    {
        Map<Integer, Boolean> mp = new HashMap<Integer, Boolean>();
        for (Task t: currentFp)
        {
            mp.put( t.getId(), Boolean.TRUE );
        }
        
        Collection<TaskWrapper> result = new ArrayList<TaskWrapper>(taskList.size());
        for (Task t: taskList)
        {
            result.add(new TaskWrapper(t, mp.containsKey(t.getId())));
        }
        
        return result;
    }

    public static interface IncommingParameters {
        public static final String LIST_ID = "listId";
    }
    
}
