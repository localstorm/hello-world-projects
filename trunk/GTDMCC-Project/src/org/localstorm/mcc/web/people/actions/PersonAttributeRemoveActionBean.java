package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.PersonManager;

/**
 * @secure-by person-id, attribute-id
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/RemovePersonAttribute")
public class PersonAttributeRemoveActionBean extends PersonViewActionBean
{
    @Validate( required=true )
    private int attributeId;

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }
    
    @DefaultHandler
    public Resolution rm() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        
        Attribute a = pm.findAttribute(this.getAttributeId());
        
        pm.remove(a);
        
        RedirectResolution rr = new RedirectResolution(PersonViewActionBean.class);
        {
            rr.addParameter(PersonViewActionBean.IncommingParameters.PERSON_ID, this.getPersonId());
        }

        return rr;
    }
}
