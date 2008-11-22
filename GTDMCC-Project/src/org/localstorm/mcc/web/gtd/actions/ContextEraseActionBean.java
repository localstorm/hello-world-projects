package org.localstorm.mcc.web.gtd.actions;

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
@UrlBinding("/actions/EraseContext")
public class ContextEraseActionBean extends BaseActionBean
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
    public Resolution deletingContext() throws Exception {
        
        System.out.println("Deleting/Undeleting context:" +contextId);
        
        ContextManager cm = getContextManager();
        Context ctx = cm.findById(getContextId());
        cm.remove(ctx);

        SessionUtil.clear(getSession(), SessionKeys.CONTEXTS);
        SessionUtil.clear(getSession(), SessionKeys.REFERENCE_OBJECTS);
        return new RedirectResolution(ContextsEditActionBean.class);
    }
}
