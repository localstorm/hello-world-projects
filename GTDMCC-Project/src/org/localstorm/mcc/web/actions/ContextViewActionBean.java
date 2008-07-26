package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.contexts.Context;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewContext")
public class ContextViewActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int id;
    
    private Context contextResult;

    public Context getContextResult() {
        return contextResult;
    }

    public void setContextResult( Context contextResult ) {
        this.contextResult = contextResult;
    }
    
    @DefaultHandler
    public Resolution filling() {
        contextResult = new Context();
        System.out.println("Viewing context:" +id);
        return new ForwardResolution("/jsp/viewContext.jsp");
    }
}
