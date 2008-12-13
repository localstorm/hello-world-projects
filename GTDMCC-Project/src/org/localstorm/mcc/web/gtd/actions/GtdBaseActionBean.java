/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.gtd.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.gtd.contexts.ContextManager;
import org.localstorm.mcc.ejb.gtd.files.FileManager;
import org.localstorm.mcc.ejb.gtd.files.FileManagerLocal;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.lists.ListManager;
import org.localstorm.mcc.ejb.gtd.notes.NoteManager;
import org.localstorm.mcc.ejb.gtd.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.tasks.Effort;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.Clipboard;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.gtd.RequestAttributes;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author localstorm
 */
public class GtdBaseActionBean extends BaseActionBean {

    public Effort[] getEfforts() {
        return Effort.values();
    }

    protected ContextManager getContextManager() {
        return ContextLookup.lookup(ContextManager.class, ContextManager.BEAN_NAME);
    }

    protected ListManager getListManager() {
        return ContextLookup.lookup(ListManager.class, ListManager.BEAN_NAME);
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

    public void setAffectedContexts(Collection<Context> ctxs) {
        Map<Integer, Boolean> affectedContexts = new HashMap<Integer, Boolean>();

        for (Context ctx: ctxs) {
            affectedContexts.put(ctx.getId(), Boolean.TRUE);
        }

        if (affectedContexts.isEmpty()) {
            affectedContexts.put(-1, Boolean.FALSE);
        }

        HttpServletRequest req = this.getContext().getRequest();
        req.setAttribute(RequestAttributes.AFFECTED_CONTEXTS, affectedContexts);
    }

    public void setAffectedContexts(Collection<Task> ... cols) {
        Integer ctxId = this.getContextIdFilter();
        
        Map<Integer, Boolean> affectedContexts = new HashMap<Integer, Boolean>();
        {
            if (ctxId > 0) {
                affectedContexts.put(ctxId, Boolean.TRUE);
            } else {
                for (Collection<Task> col: cols) {
                    this.appendAffectedCtxs(col, affectedContexts);
                }
                if (affectedContexts.isEmpty()) {
                    affectedContexts.put(-1, Boolean.FALSE);
                }
            }
        }

        HttpServletRequest req = this.getContext().getRequest();
        req.setAttribute(RequestAttributes.AFFECTED_CONTEXTS, affectedContexts);
    }

    private void appendAffectedCtxs(Collection<Task> tasks, Map<Integer, Boolean> affectedContexts) {
        for (Task t : tasks) {
            affectedContexts.put(t.getList().getContext().getId(), Boolean.TRUE);
        }
    }

}
