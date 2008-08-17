package org.localstorm.mcc.web.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.lists.ListManager;
import org.localstorm.mcc.web.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewContext")
public class ContextViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int contextId;

    private Context contextResult;
    private Collection<GTDList> contextLists;
    private Collection<GTDList> archivedLists;
    
    
    public int getContextId() {
        return contextId;
    }

    public void setContextId(int id) {
        this.contextId = id;
    }

    public Collection<GTDList> getContextLists() {
        return contextLists;
    }

    public void setContextLists(Collection<GTDList> contextLists) {
        this.contextLists = contextLists;
    }

    public Collection<GTDList> getArchivedLists() {
        return archivedLists;
    }

    public void setArchivedLists(Collection<GTDList> archivedLists) {
        this.archivedLists = archivedLists;
    }

    public Context getContextResult() {
        return contextResult;
    }

    public void setContextResult( Context contextResult ) {
        this.contextResult = contextResult;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {
        
        contextResult   = getContextManager().findById(getContextId());
        ListManager lm  = getListManager();
        
        contextLists    = lm.findByContext(contextResult);
        archivedLists   = lm.findByContextArchived(contextResult);
        
        System.out.println("Viewing context:" +contextId);
        return new ForwardResolution(Views.VIEW_CTX);
    }
    
    public static interface IncommingParameters {
        public static final String CTX_ID = "contextId";
    }
}
