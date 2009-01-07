package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ToggleStatePersonGroup")
public class PersonGroupToggleStateActionBean extends PeopleBaseActionBean
{
    @Validate( required=true )
    private int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int id) {
        this.groupId = id;
    }
    
    @DefaultHandler
    public Resolution toggle() throws Exception {
        
        PersonManager pm = super.getPersonManager();
        
        PersonGroup g = pm.findGroup(this.getGroupId());
        g.setArchived( !g.isArchived() );
        pm.update(g);
        
        SessionUtil.clear(getSession(), SessionKeys.PERSON_GROUPS);
        return new RedirectResolution(PersonGroupsEditActionBean.class);
    }
}
