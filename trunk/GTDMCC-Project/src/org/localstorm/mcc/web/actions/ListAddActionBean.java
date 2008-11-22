package org.localstorm.mcc.web.actions;

import org.localstorm.mcc.ejb.gtd.contexts.Context;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddList")
public class ListAddActionBean extends ContextViewActionBean
{

    @Validate( required=true )
    private String name;
    
    private Context contextResult;

    // TODO: Type

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            System.out.println("Forced Filling contextlist");
            super.filling();
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }

    @DefaultHandler
    public Resolution addList() throws Exception {
        this.contextResult = getContextManager().findById(super.getContextId());
        
        GTDList list = new GTDList(getName(), contextResult);
        list.setSortOrder(1);
        
        getListManager().create(list);
        
        RedirectResolution fr = new RedirectResolution( ContextViewActionBean.class );
        fr.addParameter( ContextViewActionBean.IncommingParameters.CTX_ID, getContextId() );
        return fr;
    }
    
}
