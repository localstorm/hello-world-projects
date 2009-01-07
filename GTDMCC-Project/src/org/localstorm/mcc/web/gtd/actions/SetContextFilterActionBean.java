package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

@UrlBinding("/actions/SetContextFilter")
public class SetContextFilterActionBean extends GtdBaseActionBean {

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
       return NextDestinationUtil.getRedirection(this.getReturnPageBean());
    }

}