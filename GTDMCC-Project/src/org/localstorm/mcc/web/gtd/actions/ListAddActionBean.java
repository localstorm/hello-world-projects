package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.web.util.RedirectUrlBuilderUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/AddList")
public class ListAddActionBean extends ContextViewActionBean
{

    @Validate( required=true )
    private String name;
    
    private Context contextResult;

    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
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
    @Logged
    public Resolution addList() throws Exception {
        this.contextResult = getContextManager().findById(super.getContextId());
        
        GTDList list = new GTDList(getName(), contextResult);
        
        getListManager().create(list);
        
        RedirectResolution fr = RedirectUrlBuilderUtil.redirect(ContextViewActionBean.class);
        fr.addParameter( ContextViewActionBean.IncomingParameters.CTX_ID, getContextId() );
        return fr;
    }
    
}
