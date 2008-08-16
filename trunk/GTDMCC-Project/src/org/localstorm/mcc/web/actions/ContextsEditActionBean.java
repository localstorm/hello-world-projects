package org.localstorm.mcc.web.actions;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.contexts.*;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SessionKeys;

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
        System.out.println("Filling contextlist");
        result = getContextManager().findByOwnerArchived(super.getUser());
        return new ForwardResolution("/jsp/editContexts.jsp");
    }
    
    
}