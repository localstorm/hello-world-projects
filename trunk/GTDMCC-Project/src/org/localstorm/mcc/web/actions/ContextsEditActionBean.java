package org.localstorm.mcc.web.actions;

import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.contexts.*;
import org.localstorm.mcc.web.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditContexts")
public class ContextsEditActionBean extends BaseActionBean {

    private List<Context> result;

    public List<Context> getArchiveContexts() {
        return result;
    }

    public void setArchiveContexts(List<Context> result) {
        this.result = result;
    }

    @DefaultHandler
    public Resolution filling() {
        super.clearCurrent();
        System.out.println("Filling contextlist");
        result = getContextManager().findByOwnerArchived(super.getUser());
        return new ForwardResolution(Views.EDIT_CTXS);
    }
    
    
}