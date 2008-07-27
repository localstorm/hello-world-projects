package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.contexts.*;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditContexts")
public class ContextsEditActionBean extends BaseActionBean {

    private List<Context> result;

    // Filling here
    public List<Context> getOperativeContexts() {
        return result;
    }

    public void setOperativeContexts(List<Context> result) {
        this.result = result;
    }
    
    public List<Context> getArchiveContexts() {
        return result;
    }

    public void setArchiveContexts(List<Context> result) {
        this.result = result;
    }

    @DefaultHandler
    public Resolution filling() {
        System.out.println("Filling contextlist");
        result = new ArrayList<Context>();
        result.add(new Context());
        return new ForwardResolution("/jsp/editContexts.jsp");
    }
    
    
}