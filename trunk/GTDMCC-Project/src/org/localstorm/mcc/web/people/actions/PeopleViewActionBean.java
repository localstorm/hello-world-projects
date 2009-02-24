package org.localstorm.mcc.web.people.actions;

import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.people.Views;

/**
 * @secure-by person id
 * @author localstorm
 */
@UrlBinding("/actions/ViewPeople")
public class PeopleViewActionBean extends PeopleBaseActionBean {

    @DefaultHandler
    public Resolution filling() {
        ReturnPageBean rpb = new ReturnPageBean(Pages.PEOPLE_INDEX.toString());
        
        super.setReturnPageBean(rpb);
        return new ForwardResolution(Views.INDEX);
    }

}