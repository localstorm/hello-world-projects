package org.localstorm.mcc.web.gtd.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/SetContextFilter")
public class SetContextFilterActionBean extends GtdBaseActionBean {

    @Validate(required=true)
    private Integer contextId;

    @Validate(required=true)
    private String returnPage;

    public void setContextId(Integer ctxId) {
        this.contextId = ctxId;
    }

    public Integer getContextId() {
        return contextId;
    }

    public String getReturnPage() {
        return returnPage;
    }

    public void setReturnPage(String returnPage) {
        this.returnPage = returnPage;
    }

    
    @DefaultHandler
    public Resolution filling() {
       super.setContextIdFilter(contextId);
       return NextDestinationUtil.getRedirectionByReturnPageName(this.getReturnPage());
    }

}