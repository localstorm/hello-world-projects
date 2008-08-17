package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.Views;




/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UpdateConfig")
public class ConfigUpdateActionBean extends ContextsEditActionBean {

    private String oldPassword;
    private String password;
    private String password2;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    @DefaultHandler
    public Resolution configure() {
        return new ForwardResolution( Views.CONFIGURE );
    }
    
}