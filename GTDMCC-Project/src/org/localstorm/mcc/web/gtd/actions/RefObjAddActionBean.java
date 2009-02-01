package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.ejb.gtd.contexts.Context;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;


/**
 * @secure-by context id parameter
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddRefObj")
public class RefObjAddActionBean extends RefObjEditActionBean
{
    // TODO: Type
    @Validate( required=true )
    private String name;
    
    @Validate( required=true )
    private Integer contextId;

    public Integer getContextId() {
        return contextId;
    }

    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @After( LifecycleStage.BindingAndValidation ) 
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }
    
    @DefaultHandler
    public Resolution addRefObject() throws Exception {
        Context ctx = this.getContextManager().findById(this.getContextId());
        ReferencedObject ro = new ReferencedObject(name, ctx);
        super.getRefObjectManager().create(ro);
        
        SessionUtil.clear(this.getSession(), SessionKeys.REFERENCE_OBJECTS);
        return new RedirectResolution( RefObjEditActionBean.class );
    }
    
}
