package org.localstorm.mcc.ejb.people.impl;

import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import org.localstorm.mcc.ejb.people.entity.MailListContent;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.registry.infomodel.User;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.PersonManager;

/**
 * @author localstorm
 */
@Stateless
public class MailListManagerBean extends PeopleStatelessBean implements MailListManagerLocal,
                                                                        MailListManagerRemote
{
    public MailListManagerBean() {
        
    }

    @Override
    public PregeneratedMailList generateMailList(Collection<Person> persons)
    {
        PersonManager pm = super.getPersonManager();

        return new PregeneratedMailList();
    }

    @Override
    public void create(PregeneratedMailList pml)
    {
        
    }

    @Override
    public Collection<MailList> findByUser(User u)
    {
        // TODO: add to lazy loader
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MailListContent findMailListContent(MailList ml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void joinMailList(MailList ml, Person p, Attribute a)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void leaveMailList(MailList ml, Person p)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(MailList ml)
    {
        em.remove(em.getReference(MailList.class, ml));
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
