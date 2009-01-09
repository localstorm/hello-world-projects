package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.Person;
import org.localstorm.mcc.ejb.people.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.people.Views;

@UrlBinding("/actions/ViewPersonGroup")
public class PersonGroupViewActionBean extends PeopleBaseActionBean {

    private Collection<Person> persons;
    private PersonGroup group;

    @Validate( required=true )
    private Integer groupId;

    public Collection<Person> getPersons() {
        return persons;
    }

    public void setPersons(Collection<Person> persons) {
        this.persons = persons;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public PersonGroup getGroup() {
        return group;
    }

    public void setGroup(PersonGroup group) {
        this.group = group;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        PersonManager pm = super.getPersonManager();
        PersonGroup    g = pm.findGroup(this.getGroupId());

        this.setGroup(g);
        this.setPersons(pm.findPersonsByGroup(g));

        ReturnPageBean rpb = new ReturnPageBean(Pages.GROUP_VIEW.toString());
        {
            rpb.setParam(IncommingParameters.GROUP_ID, this.groupId.toString());
        }
        
        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.PERSON_GROUP);
    }

    public static interface IncommingParameters {
        public static final String GROUP_ID = "groupId";
    }

}