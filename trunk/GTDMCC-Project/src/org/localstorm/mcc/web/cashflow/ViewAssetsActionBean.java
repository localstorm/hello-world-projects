package org.localstorm.mcc.web.cashflow;

import org.localstorm.mcc.web.BaseActionBean;
import java.util.Iterator;
import java.util.LinkedList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.users.User;

/**
 * A very simple calculator action.
 */
@UrlBinding("/actions/ViewAssets")
public class ViewAssetsActionBean extends CashflowBaseActionBean {

    
    @DefaultHandler
    public Resolution filling() {
       return null;
    }

}