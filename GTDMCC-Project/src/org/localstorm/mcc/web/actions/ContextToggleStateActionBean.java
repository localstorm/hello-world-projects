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
@UrlBinding("/actions/ToggleStateContext")
public class ContextToggleStateActionBean extends BaseActionBean
{
    @Validate( required=true )
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @DefaultHandler
    public Resolution deletingContext() {
        
        System.out.println("Deleting/Undeleting context:" +id);
        return new RedirectResolution(ContextsEditActionBean.class);
    
    }
}
