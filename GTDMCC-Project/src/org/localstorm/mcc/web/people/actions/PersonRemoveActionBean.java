package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.Person;
import org.localstorm.mcc.ejb.people.PersonManager;

@UrlBinding("/actions/RemovePerson")
public class PersonRemoveActionBean extends PersonGroupViewActionBean {

    @Validate(required=true)
    private Integer personId;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    @DefaultHandler
    public Resolution filling() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        Person         p = pm.findPerson(this.getPersonId());

        pm.remove(p);

        RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncommingParameters.GROUP_ID,
                            this.getGroupId());
        }

        return rr;
    }

}