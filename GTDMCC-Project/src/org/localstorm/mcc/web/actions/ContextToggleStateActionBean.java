package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.contexts.*;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.annotations.EJBBean;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ToggleStateContext")
public class ContextToggleStateActionBean extends BaseActionBean
{
    @EJBBean(ContextManager.BEAN_NAME)
    private ContextManager cm;

    @Validate( required=true )
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @DefaultHandler
    public Resolution deletingContext() throws Exception {
        
        System.out.println("Deleting/Undeleting context:" +id);
        
        Context ctx = cm.findById(getId());
        ctx.setArchived( !ctx.isArchived() );
        cm.update(ctx);
        
        SessionUtil.clear(getSession(), SessionKeys.CONTEXTS);
        return new RedirectResolution(ContextsEditActionBean.class);
    }
}
