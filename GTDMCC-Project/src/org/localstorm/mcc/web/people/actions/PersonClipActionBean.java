package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;

/**
 * @secure-by person-id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/group/person/ClipPerson")
public class PersonClipActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        PersonManager  pm = super.getPersonManager();
        Person          p = pm.findPerson(this.getPersonId());
        PersonGroup group = pm.getGroup(p);

        //pm.getAttributes(p, Att)

        PeopleClipboard clip = super.getClipboard();
        clip.copyPerson(p);

        RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncommingParameters.GROUP_ID, group.getId());
        }

        return rr;
    }
    
    public static interface IncommingParameters {
        public static final String PERSON_ID = "personId";
    }
}
