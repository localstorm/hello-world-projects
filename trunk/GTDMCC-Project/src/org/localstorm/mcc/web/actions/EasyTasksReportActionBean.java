package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.tasks.Effort;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EasyTasksReport")
public class EasyTasksReportActionBean extends BaseActionBean
{
    private List<Task> tasks;
    
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<? extends Task> tasks) {
        this.tasks = new ArrayList<Task>(tasks);
    }


    @DefaultHandler
    public Resolution filling() throws Exception {

        Integer ctxId = (Integer) SessionUtil.getValue(super.getSession(), SessionKeys.FILTER_CONTEXT);
        
        
        Context ctx = null;
        if (ctxId!=-1) {
            ctx = super.getContextManager().findById(ctxId);
        }
        
        TaskManager tm = super.getTaskManager();
        
        this.setTasks(tm.findByMaxEffort(ctx, Effort.EASY, super.getUser()));
        return new ForwardResolution(Views.VIEW_EASY);
    }
    
}
