package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.gtd.Views;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/SearchRefObjAttach")
public class RefObjAttachmentSearch extends GtdBaseActionBean
{
    public boolean isFound() {
        return false;
    }

    public String getText() {
        return "";
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        return new ForwardResolution(Views.SEARCH_RO_ATTACH);
    }

    
}
