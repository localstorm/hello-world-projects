package org.localstorm.mcc.ejb.people.impl;

import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.Pair;
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
    public MailList find(Integer mailListId)
    {
        return em.find(MailList.class, mailListId);
    }

    @Override
    public void tryAutoResolveBrokenEmails(MailList ml)
    {
        PersonManager pm = super.getPersonManager();

        Collection<PersonToMailList> cont = this.getMailListContent(ml);
        for (PersonToMailList p2ml : cont) {
            if (p2ml.getAttribute()==null) {
                Collection<Attribute> emails = pm.getEmailAttributes(p2ml.getPerson());

                if (emails.size()==1) {
                    p2ml.setAttribute(emails.iterator().next());
                    em.persist(p2ml);
                }
            }
        }
    }

    @Override
    public PregeneratedMailList generateMailList(Collection<Person> persons)
    {
        PersonManager pm = super.getPersonManager();
        PregeneratedMailList pml = new PregeneratedMailList();

        for (Person p : persons) {
            Collection<Attribute> attrs = pm.getEmailAttributes(p);
            
            if (attrs.isEmpty()) {
                pml.addNoEmailPerson(p);
                continue;
            }

            if (attrs.size()==1) {
                pml.addResolvedPerson(p, attrs.iterator().next());
                continue;
            }

            pml.addManyEmailPerson(p, attrs);
        }

        return pml;
    }

    @Override
    public MailList create(PregeneratedMailList pml, String name, User user)
    {
        if (pml.isReady()) {

            MailList ml = new MailList();
            {
                ml.setName(name);
                ml.setOwner(user);
                ml.setArchived(false);
            }
            em.persist(ml);

            for (Pair<Person, Attribute> mlEntry : pml.getResolved()) {
                PersonToMailList p2ml = new PersonToMailList(mlEntry.getFirst(), ml, mlEntry.getSecond());
                em.persist(p2ml);
            }

            return ml;
            
        } else {
            throw new RuntimeException("PregeneratedMailList is not ready!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<MailList> find(User u)
    {
        Query q = em.createNamedQuery(MailList.Queries.FIND_MLS_BY_OWNER);
        q.setParameter(MailList.Properties.OWNER, u);

        return (Collection<MailList>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<MailList> findArchived(User u)
    {
        Query q = em.createNamedQuery(MailList.Queries.FIND_ARCHIVED_MLS_BY_OWNER);
        q.setParameter(MailList.Properties.OWNER, u);

        return (Collection<MailList>) q.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<PersonToMailList> getMailListContent(MailList ml)
    {
        Query q = em.createNamedQuery(PersonToMailList.Queries.FIND_P2ML_BY_ML);
        q.setParameter(PersonToMailList.Properties.MAIL_LIST, ml);

        return (Collection<PersonToMailList>) q.getResultList();
    }

    @Override
    public void joinMailList(MailList ml, Person p, Attribute a)
    {
        Collection<Person> persons = super.getPersonManager().getPersons(ml);
        for (Person person: persons)
        {
            if (person.getId().equals(p.getId())) {
                return;
            }
        }
        
        em.persist(new PersonToMailList(p, ml, a));
    }

    @Override
    public void leaveMailList(MailList ml, Person p)
    {
        Query q = em.createNamedQuery(PersonToMailList.Queries.LEAVE_ML);
        q.setParameter(PersonToMailList.Properties.MAIL_LIST, ml);
        q.setParameter(PersonToMailList.Properties.PERSON, p);
        q.executeUpdate();
    }

    @Override
    public void remove(MailList ml)
    {
        em.remove(em.getReference(MailList.class, ml.getId()));
    }

    @Override
    public void update(MailList ml)
    {
        em.merge(ml);
    }

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
