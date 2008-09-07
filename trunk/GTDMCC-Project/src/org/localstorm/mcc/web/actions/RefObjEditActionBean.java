package org.localstorm.mcc.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ROEdit")
public class RefObjEditActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution handler() {
        return new ForwardResolution( Views.EDIT_RO );
    }

}
