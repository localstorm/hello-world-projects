package org.localstorm.mcc.web.actions;

import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.users.*;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddContext")
public class ContextAddActionBean extends ContextsEditActionBean {

    @Validate( required=true )
    private String name;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            System.out.println("Forced Filling contextlist");
            super.filling();
        }
    }
    
    //Adding context
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    @DefaultHandler
    public Resolution addContext() {
        System.out.println("Adding context:"+getName());
        
        try {
            User user = super.getUser();
            
            Context ctx = new Context(this.getName(), user);
            ctx.setSortOrder(1);
            
            getContextManager().create(ctx);
            
            SessionUtil.clear(super.getSession(), SessionKeys.CONTEXTS);
            
        } catch(Exception e) 
        {
            e.printStackTrace();
        }
        
        return new RedirectResolution( ContextsEditActionBean.class );
    }
    
}