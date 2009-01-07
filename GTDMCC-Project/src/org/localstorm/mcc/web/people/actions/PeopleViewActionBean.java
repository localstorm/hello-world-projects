package org.localstorm.mcc.web.people.actions;

import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.people.Views;

@UrlBinding("/actions/ViewPeople")
public class PeopleViewActionBean extends PeopleBaseActionBean {

    @DefaultHandler
    public Resolution filling() {
        return new ForwardResolution(Views.INDEX);
    }

}