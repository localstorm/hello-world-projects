package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/RenameContext")
public class ContextRenameActionBean extends ContextViewActionBean {

    @Validate( required=true )
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        this.getContext().getRequest().setAttribute("renameForm", Boolean.TRUE);
        
        if ( this.getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    public Resolution filling() throws Exception {
        ContextManager cm = this.getContextManager();
        
        Context ctx   = cm.findById(super.getContextId());
        ctx.setName(this.getName());
        cm.update(ctx);
        
        SessionUtil.clear(getSession(), SessionKeys.CONTEXTS);
        
        RedirectResolution rr = new RedirectResolution(ContextViewActionBean.class);
        {
            rr.addParameter(ContextViewActionBean.IncommingParameters.CTX_ID, super.getContextId());
        }
        return rr;
    }
    
    public static interface IncommingParameters {
        public static final String CTX_ID = "contextId";
    }
}