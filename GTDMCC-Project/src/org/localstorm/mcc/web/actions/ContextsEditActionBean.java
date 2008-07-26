package org.localstorm.mcc.web.actions;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.contexts.*;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditContexts")
public class ContextsEditActionBean extends BaseActionBean {

    private ActionBeanContext context;
    private List<Context> result;

    //@Validate(required=true, on="addContext")
    //private String name;

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    //Adding context
    
    //public String getName() {
    //    return this.name;
    //}
    
    //public void setName( String name ) {
    //    this.name = name;
    //}
    
    /*public Resolution addContext() {
        System.out.println("Creating context: "+getName() );
        return new RedirectResolution( this.getClass() );
    }*/
    
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
        result = new ArrayList<Context>();
        result.add(new Context());
        return new ForwardResolution("/jsp/editContexts.jsp");
    }
    
    
}