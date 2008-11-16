package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/SetEasyFilter")
public class SetEasyFilterActionBean extends BaseActionBean {

    @Validate(required=true)
    private Integer contextId;

    public void setContextId(Integer ctxId) {
        this.contextId = ctxId;
    }

    public Integer getContextId() {
        return contextId;
    }
    
    @DefaultHandler
    public Resolution filling() {
        
        super.setContextIdFilter(contextId);
        return new RedirectResolution(EasyTasksReportActionBean.class);
    }

}