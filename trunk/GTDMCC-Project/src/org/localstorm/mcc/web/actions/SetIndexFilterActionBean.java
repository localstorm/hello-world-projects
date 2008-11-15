package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/SetIndexFilter")
public class SetIndexFilterActionBean extends BaseActionBean {

    @Validate(required=true)
    private Integer contextId;

    public void setContextId(Integer ctxId) {
        this.contextId = ctxId;
    }

    public Integer getContextId() {
        return contextId;
    }
    
    @DefaultHandler
    public Resolution filling(){
        
        if (this.getContextId()!=-1) {
            ContextManager cm = super.getContextManager();
            try
            {
                cm.findById(this.getContextId());    
            }catch(ObjectNotFoundException e){
                this.setContextId(-1);
            }
        }
        
        SessionUtil.fill(this.getSession(), SessionKeys.FP_FILTER_CONTEXT, this.getContextId());
        return new RedirectResolution(IndexActionBean.class);
    }

}