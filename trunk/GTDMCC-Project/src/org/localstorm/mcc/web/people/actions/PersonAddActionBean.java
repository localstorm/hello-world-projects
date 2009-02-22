package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.persons.Person;
import org.localstorm.mcc.ejb.people.persons.PersonGroup;
import org.localstorm.mcc.ejb.people.persons.PersonManager;

@UrlBinding("/actions/AddPerson")
public class PersonAddActionBean extends PersonGroupViewActionBean {

    @Validate(required=true)
    private String firstName;

    private String lastName;

    private String patronymicName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public void setPatronymicName(String pname) {
        this.patronymicName = pname;
    }

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @Override
    @DefaultHandler
    public Resolution filling() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        Person p = new Person();
        {
            p.setName(this.getFirstName());
            p.setLastName(this.getLastName());
            p.setPatronymicName(this.getPatronymicName());
        }

        PersonGroup g = pm.findGroup(this.getGroupId());
        
        pm.create(p, g);

        RedirectResolution rr = new RedirectResolution(PersonGroupViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncommingParameters.GROUP_ID,
                            this.getGroupId());
        }

        return rr;
    }

}