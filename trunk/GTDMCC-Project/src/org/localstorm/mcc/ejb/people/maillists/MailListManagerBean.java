package org.localstorm.mcc.ejb.people.maillists;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.registry.infomodel.User;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.people.persons.Attribute;
import org.localstorm.mcc.ejb.people.persons.Person;

/**
 * @author localstorm
 */
@Stateless
public class MailListManagerBean implements MailListManagerLocal,
                                            MailListManagerRemote
{
    public MailListManagerBean() {
        
    }

    @Override
    public PregeneratedMailList generateMailList(Collection<Person> persons)
    {
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
