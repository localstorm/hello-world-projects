package org.localstorm.mcc.web.actions;

import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class BaseActionBean implements ActionBean 
{
    private ActionBeanContext context;

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    
    protected HttpSession getSession()
    {
        return this.getContext().getRequest().getSession(true);
    }
    
    protected ContextManager getContextManager() {
        return ContextLookup.lookup(ContextManager.class, ContextManager.BEAN_NAME);
    }
    
    protected ListManager getListManager() {
        return ContextLookup.lookup(ListManager.class, ListManager.BEAN_NAME);
    }
    
    protected UserManager getUserManager() {
        return ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);
    }
    
    protected TaskManager getTaskManager() {
        return ContextLookup.lookup(TaskManager.class, TaskManager.BEAN_NAME);
    }
    
    protected FlightPlanManager getFlightPlanManager() {
        return ContextLookup.lookup(FlightPlanManager.class, FlightPlanManager.BEAN_NAME);
    }
 }
