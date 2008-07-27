package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;


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
        return new ForwardResolution( ContextsEditActionBean.class );
    }
    
}