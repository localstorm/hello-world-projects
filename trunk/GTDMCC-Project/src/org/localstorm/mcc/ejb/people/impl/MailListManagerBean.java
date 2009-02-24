package org.localstorm.mcc.ejb.people.impl;

import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.PersonToMailList;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class MailListManagerBean extends PeopleStatelessBean implements MailListManagerLocal
{
    public MailListManagerBean() {
        
    }

    @Override
    public PregeneratedMailList generateMailList(Collection<Person> persons)
    {
        PersonManager pm = super.getPersonManager();
        PregeneratedMailList pml = new PregeneratedMailList();

        for (Person p : persons)
        {
            Collection<Attribute> attrs = pm.getEmailAttributes(p);
            
            if (attrs.isEmpty())
            {
                pml.addNoEmailPerson(p);
                continue;
            }

            if (attrs.size()==1)
            {
                pml.addResolvedPerson(p, attrs.iterator().next());
                continue;
            }

            pml.addManyEmailPerson(p, attrs);
        }

        return pml;
    }

    @Override
    public void create(PregeneratedMailList pml, String name, User user)
    {
    }

    @Override
    public Collection<MailList> findByUser(User u)
    {
        // TODO: add to lazy loader
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<PersonToMailList> findMailListContent(MailList ml)
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
