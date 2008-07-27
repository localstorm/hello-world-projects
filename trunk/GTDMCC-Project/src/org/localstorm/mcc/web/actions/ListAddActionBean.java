package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddList")
public class ListAddActionBean extends BaseActionBean
{

    @Validate( required=true )
    private String name;
    
    @Validate( required=true )
    private int contextId;
    
    // TODO: Type

    /*@After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            System.out.println("Forced Filling contextlist");
            super.filling();
        }
    }*/
    
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }
    
    @DefaultHandler
    public Resolution addList() {
        System.out.println("Adding list:"+getName());

        ForwardResolution fr = new ForwardResolution( ContextViewActionBean.class );
        fr.addParameter( "id", contextId );
        return fr;
    }
    
}
