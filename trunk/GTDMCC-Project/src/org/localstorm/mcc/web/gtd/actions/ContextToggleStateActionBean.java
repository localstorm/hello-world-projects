package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.contexts.*;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ToggleStateContext")
public class ContextToggleStateActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int contextId;

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int id) {
        this.contextId = id;
    }
    
    @DefaultHandler
    public Resolution toggle() throws Exception {
        
        ContextManager cm = getContextManager();
        Context ctx = cm.findById(getContextId());
        ctx.setArchived( !ctx.isArchived() );
        cm.update(ctx);
        
        SessionUtil.clear(getSession(), SessionKeys.CONTEXTS);
        SessionUtil.clear(getSession(), SessionKeys.REFERENCE_OBJECTS);
        return new RedirectResolution(ContextsEditActionBean.class);
    }
}
