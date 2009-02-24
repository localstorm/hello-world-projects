package org.localstorm.mcc.ejb.people.impl;

import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.PeopleReporter;
import org.localstorm.mcc.ejb.people.PersonManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class PeopleStatelessBean
{
    public PersonManager getPersonManager()
    {
        return ContextLookup.lookup(PersonManager.class, PersonManager.BEAN_NAME);
    }

    public MailListManager getMailListManager()
    {
        return ContextLookup.lookup(MailListManager.class, MailListManager.BEAN_NAME);
    }

    public PeopleReporter getPeopleReporter()
    {
        return ContextLookup.lookup(PeopleReporter.class, PeopleReporter.BEAN_NAME);
    }
}
