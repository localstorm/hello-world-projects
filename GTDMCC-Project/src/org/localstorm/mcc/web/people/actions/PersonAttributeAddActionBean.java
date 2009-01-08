package org.localstorm.mcc.web.people.actions;


import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.Attribute;
import org.localstorm.mcc.ejb.people.AttributeType;
import org.localstorm.mcc.ejb.people.Person;
import org.localstorm.mcc.ejb.people.PersonManager;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AddPersonAttribute")
public class PersonAttributeAddActionBean extends PersonViewActionBean
{
    @Validate(required=true)
    private Integer typeId;

    @Validate(required=true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }


    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    @Override
    public Resolution filling() throws Exception {
        PersonManager pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());

        AttributeType type = pm.findAttributeType(this.getTypeId());

        pm.setAttributeForPerson(p, new Attribute(p, type, this.getValue()));

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
