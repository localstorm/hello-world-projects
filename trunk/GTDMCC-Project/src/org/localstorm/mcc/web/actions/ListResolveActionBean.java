package org.localstorm.mcc.web.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.ejb.tasks.*;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ResolveList")
public class ListResolveActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int listId;
    
    @Validate( required=true )
    private String action;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    
    @DefaultHandler
    public Resolution resolvingList() throws Exception {
        
        ListManager lm = getListManager();
        TaskManager tm = getTaskManager();
        
        GTDList list = lm.findById(this.getListId());
        
        
        switch (ACTIONS.valueOf(this.getAction())) {
            case ERASE:
                lm.remove(list);
                break;
            case UNRESOLVE:
                list.setArchived(false);
                lm.update(list);
                break;
            case CANCEL:
                this.cancelList(list, tm);
                lm.update(list);
                break;
            case FINISH:
                this.finishList(list, tm);
                lm.update(list);
                break;
            default:
                throw new RuntimeException("Unexpected action:"+this.getAction());
        }
        
        
        
        RedirectResolution rr = new RedirectResolution(ContextViewActionBean.class);
        {
            rr.addParameter(ContextViewActionBean.IncommingParameters.CTX_ID, list.getContext().getId());
        }
        return rr;
    }
    
    private static enum ACTIONS 
    {
        ERASE,
        CANCEL,
        FINISH,
        UNRESOLVE
    }

    private void cancelList(GTDList list, TaskManager tm) {
        Collection<Task> tasks = tm.findOpeartiveByList(list);
        tasks.addAll(tm.findAwaitedByList(list));
        
        for (Task t: tasks)
        {
            t.setDelegated(false);
            t.setCancelled(true);
            tm.update(t);
        }
        list.setArchived(true);
    }
    
    private void finishList(GTDList list, TaskManager tm) {
        Collection<Task> tasks = tm.findOpeartiveByList(list);
        tasks.addAll(tm.findAwaitedByList(list));
        
        for (Task t: tasks)
        {
            t.setDelegated(false);
            t.setFinished(true);
            tm.update(t);
        }
        list.setArchived(true);
    }
    
}
