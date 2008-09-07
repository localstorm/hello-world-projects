package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.contexts.*;


/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddRO")
public class RefObjAddActionBean extends BaseActionBean
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

    
    @DefaultHandler
    public Resolution addList() throws Exception {
        Context ctx = this.getContextManager().findById(this.getContextId());
        ReferencedObject ro = new ReferencedObject(name, ctx);
        super.getRefObjectManager().create(ro);
        return new RedirectResolution( RefObjEditActionBean.class );
    }
    
}
