package org.localstorm.mcc.web.people.actions;


import java.text.SimpleDateFormat;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.Person;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.Constants;
import org.localstorm.mcc.web.util.DateUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/UpdatePerson")
public class PersonUpdateActionBean extends PersonViewActionBean
{
    @Validate(required=true)
    private String firstName;

    private String lastName;

    private String patronymicName;

    private String birthDate;


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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() ) {
            super.getRequest().setAttribute("updateForm", Boolean.TRUE);
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    public Resolution filling() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

        PersonManager pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());
        {
            p.setPatronymicName(this.getPatronymicName());
            p.setName(this.getFirstName());
            p.setLastName(this.getLastName());
            p.setBirthDate(DateUtil.parse(this.getBirthDate(), sdf));
        }

        pm.update(p);

        RedirectResolution rr = new RedirectResolution(PersonViewActionBean.class);
        {
            rr.addParameter(PersonGroupViewActionBean.IncommingParameters.GROUP_ID, this.getGroupId());
            rr.addParameter(PersonViewActionBean.IncommingParameters.PERSON_ID, this.getPersonId());
        }

        return rr;
    }
    
    public static interface IncommingParameters {
        public static final String PERSON_ID = "personId";
    }


}
