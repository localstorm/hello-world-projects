package org.localstorm.mcc.ejb.people;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Stateless
public class PersonManagerBean implements PersonManagerLocal,
                                          PersonManagerRemote
{

    public PersonManagerBean() {

    }

    @Override
    public Collection<PersonGroup> findGroupsByOwner(User user) {
        Query q = em.createNamedQuery(PersonGroup.Queries.FIND_BY_OWNER);
        q.setParameter(PersonGroup.Properties.OWNER, user);

        return (Collection<PersonGroup>) q.getResultList();
    }

    @Override
    public Collection<Person> findPersonsByGroup(PersonGroup g) {
        Query q = em.createNamedQuery(Person.Queries.FIND_BY_GROUP);
        q.setParameter(Person.Properties.GROUP, g);

        return (Collection<Person>) q.getResultList();
    }

    @Override
    public PersonGroup findGroup(Integer groupId) throws ObjectNotFoundException {
        
        PersonGroup pg = em.find(PersonGroup.class, groupId);
        if (pg==null) {
            throw new ObjectNotFoundException();
        }

        return pg;
    }



    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
