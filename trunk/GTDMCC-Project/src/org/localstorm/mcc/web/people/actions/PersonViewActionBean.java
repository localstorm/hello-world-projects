package org.localstorm.mcc.web.people.actions;


import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.Person;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewPerson")
public class PersonViewActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int personId;

    @Validate( required=true )
    private int groupId;

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    
    @DefaultHandler
    public Resolution filling() throws Exception {
        PersonManager pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());
        this.setPerson(p);

        return new ForwardResolution(Views.VIEW_PERSON);
    }
    
    public static interface IncommingParameters {
        public static final String PERSON_ID = "personId";
    }
}
