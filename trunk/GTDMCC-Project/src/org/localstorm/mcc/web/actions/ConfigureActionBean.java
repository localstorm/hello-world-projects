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
@UrlBinding("/actions/Configure")
public class ConfigureActionBean extends ContextsEditActionBean {


    @DefaultHandler
    public Resolution configure() {
        return new ForwardResolution( Views.CONFIGURE );
    }
    
}