package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.persons.Person;
import org.localstorm.mcc.ejb.people.persons.PersonGroup;
import org.localstorm.mcc.ejb.people.persons.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;


@UrlBinding("/actions/RemovePerson")
public class PersonRemoveActionBean extends PeopleBaseActionBean {

    @Validate(required=true)
    private Integer personId;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        Person         p = pm.findPerson(this.getPersonId());
        PersonGroup group= pm.findGroupByPerson(p);

        PeopleClipboard clip = super.getClipboard();
        clip.pickPerson(p.getId());

        pm.remove(p);

        RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncommingParameters.GROUP_ID,
                            group.getId());
        }

        return rr;
    }

}