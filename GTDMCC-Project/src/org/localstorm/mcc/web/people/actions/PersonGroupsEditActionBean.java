package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.localstorm.mcc.ejb.people.persons.PersonGroup;
import org.localstorm.mcc.ejb.people.persons.PersonManager;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/EditPersonGroups")
public class PersonGroupsEditActionBean extends PeopleBaseActionBean {

    private Collection<PersonGroup> result;

    public Collection<PersonGroup> getArchiveGroups() {
        return result;
    }

    public void setArchiveGroups(Collection<PersonGroup> result) {
        this.result = result;
    }

    @DefaultHandler
    public Resolution filling() {
        PersonManager pm = super.getPersonManager();
        result = pm.findArchivedGroupsByOwner(super.getUser());
        return new ForwardResolution(Views.EDIT_GROUPS);
    }
    
    
}