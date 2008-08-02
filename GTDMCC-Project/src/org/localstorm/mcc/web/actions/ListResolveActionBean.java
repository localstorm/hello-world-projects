package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;

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
                // Cancelling tasks
                list.setArchived(true);
                lm.update(list);
                break;
            case FINISH:
                // Finishing tasks
                list.setArchived(true);
                lm.update(list);
                break;
            default:
                throw new RuntimeException("Unexpected action:"+this.getAction());
        }
        
        
        
        RedirectResolution rr = new RedirectResolution(ContextViewActionBean.class);
        {
            rr.addParameter("contextId", list.getContext().getId());
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
    
}
