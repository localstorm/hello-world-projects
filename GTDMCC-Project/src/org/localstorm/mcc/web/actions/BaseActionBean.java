package org.localstorm.mcc.web.actions;


import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.files.FileManager;
import org.localstorm.mcc.ejb.files.FileManagerLocal;
import org.localstorm.mcc.ejb.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.notes.NoteManager;
import org.localstorm.mcc.ejb.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.tasks.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;
import org.localstorm.mcc.web.Clipboard;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

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
    
    protected FileManager getFileManager() {
        return ContextLookup.lookupLocal(FileManagerLocal.class, FileManager.BEAN_NAME);
    }
    
    protected FlightPlanManager getFlightPlanManager() {
        return ContextLookup.lookup(FlightPlanManager.class, FlightPlanManager.BEAN_NAME);
    }
    
    protected RefObjectManager getRefObjectManager() {
        return ContextLookup.lookup(RefObjectManager.class, RefObjectManager.BEAN_NAME);
    }

    protected NoteManager getNoteManager() {
        return ContextLookup.lookup(NoteManager.class, NoteManager.BEAN_NAME);
    }
    
    protected User getUser() 
    {
        User user = (User)this.getSession().getAttribute(SessionKeys.USER);
            
        if (user==null) {
            throw new NullPointerException("USER IS NULL!");
        }
        
        return user;
    }
    
    protected void setCurrent(GTDList list)
    {
        HttpSession sess = this.getSession();
        SessionUtil.fill(sess, SessionKeys.CURR_CTX, list.getContext());
        SessionUtil.fill(sess, SessionKeys.CURR_LIST, list);
        SessionUtil.clear(sess, SessionKeys.CURR_TASK);
    }
    
    protected void setCurrent(Task t)
    {
        HttpSession sess = this.getSession();
        SessionUtil.fill(sess, SessionKeys.CURR_CTX,  t.getList().getContext());
        SessionUtil.fill(sess, SessionKeys.CURR_LIST, t.getList());
        SessionUtil.fill(sess, SessionKeys.CURR_TASK, t);
    }
    
    protected void setCurrent(Context t)
    {
        HttpSession sess = this.getSession();
        SessionUtil.clear(sess, SessionKeys.CURR_TASK);
        SessionUtil.clear(sess, SessionKeys.CURR_LIST);
        SessionUtil.fill(sess, SessionKeys.CURR_CTX,  t);
    }
    
    protected void clearCurrent()
    {
        HttpSession sess = this.getSession();
        SessionUtil.clear(sess, SessionKeys.CURR_TASK);
        SessionUtil.clear(sess, SessionKeys.CURR_LIST);
        SessionUtil.clear(sess, SessionKeys.CURR_CTX);
    }
    
    public Context getCurrentContext()
    {
        return (Context) SessionUtil.getValue(this.getSession(), SessionKeys.CURR_CTX);
    }
    
    public GTDList getCurrentList()
    {
        return (GTDList) SessionUtil.getValue(this.getSession(), SessionKeys.CURR_LIST);
    }
    
    public Task getCurrentTask()
    {
        return (Task) SessionUtil.getValue(this.getSession(), SessionKeys.CURR_TASK);
    }
    
    protected Clipboard getClipboard()
    {
        HttpSession sess = this.getSession();
        Clipboard clip = (Clipboard) SessionUtil.getValue(sess, SessionKeys.CLIPBOARD);
        if (clip==null) {
            clip = new Clipboard();
            SessionUtil.fill(sess, SessionKeys.CLIPBOARD, clip);
        }
        
        return clip;
    }

    protected Integer getContextIdFilter()
    {
        Integer ctx = (Integer) SessionUtil.getValue(this.getSession(), SessionKeys.FILTER_CONTEXT);
        
        if (ctx==null) {
            return -1;
        }
        
        return ctx;
    }
    
    protected void setContextIdFilter(Integer contextId)
    {
        if (contextId>=0) {
            ContextManager cm = this.getContextManager();
            try
            {
                cm.findById(contextId);    
            }catch(ObjectNotFoundException e){
                contextId = -1;
            }
        } else {
            contextId = -1;
        }
        
        SessionUtil.fill(this.getSession(), SessionKeys.FILTER_CONTEXT, contextId);
    }


  
 }
