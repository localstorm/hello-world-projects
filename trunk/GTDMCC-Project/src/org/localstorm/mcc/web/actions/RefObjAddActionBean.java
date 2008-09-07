package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;


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
        return new RedirectResolution( RefObjEditActionBean.class );
    }
    
}
