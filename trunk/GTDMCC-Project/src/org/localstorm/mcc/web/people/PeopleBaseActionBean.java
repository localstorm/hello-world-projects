package org.localstorm.mcc.web.people;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.web.BaseActionBean;

/**
 *
 * @author localstorm
 */
public class PeopleBaseActionBean extends BaseActionBean {

    public PersonManager getPersonManager() {
        return ContextLookup.lookup(PersonManager.class, PersonManager.BEAN_NAME);
    }

}
