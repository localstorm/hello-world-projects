package org.localstorm.mcc.web.people.actions;


import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.people.Attribute;
import org.localstorm.mcc.ejb.people.AttributeType;
import org.localstorm.mcc.ejb.people.Person;
import org.localstorm.mcc.ejb.people.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.people.actions.wrap.WrapUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ViewPerson")
public class PersonViewActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int personId;

    private Person person;

    private Collection<Attribute> attributes;

    private Collection<AttributeType> attributeTypes;

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

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Collection<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public void setAttributeTypes(Collection<AttributeType> attributeTypes) {
        this.attributeTypes = attributeTypes;
    }



    @DefaultHandler
    public Resolution filling() throws Exception {
        PersonManager pm = super.getPersonManager();
        Person p = pm.findPerson(this.getPersonId());
        PersonGroup group = pm.findGroupByPerson(p);
        
        this.setAttributes(pm.getAttributes(p));
        this.setPerson(WrapUtil.genWrapper(p, group));
        this.setAttributeTypes(pm.getAllAttributeTypes());

        return new ForwardResolution(Views.VIEW_PERSON);
    }
    
    public static interface IncommingParameters {
        public static final String PERSON_ID = "personId";
    }
}
