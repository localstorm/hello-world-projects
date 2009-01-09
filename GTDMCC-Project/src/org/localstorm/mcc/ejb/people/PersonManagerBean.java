package org.localstorm.mcc.ejb.people;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.NullResultGuard;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
@Stateless
public class PersonManagerBean implements PersonManagerLocal,
                                          PersonManagerRemote
{
    public PersonManagerBean() {
        
    }

    @Override
    public Collection<AttributeType> getAllAttributeTypes() {
        Query q = em.createNamedQuery(AttributeType.Queries.FIND_ALL);
        return (Collection<AttributeType>) q.getResultList();
    }

    @Override
    public Collection<Attribute> getAttributes(Person p) {
        Query q = em.createNamedQuery(Attribute.Queries.FIND_BY_PERSON);
        q.setParameter(Attribute.Properties.PERSON, p);

        return (Collection<Attribute>) q.getResultList();
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
        return NullResultGuard.checkNotNull(pg);
    }

    @Override
    public Person findPerson(Integer personId) throws ObjectNotFoundException {
        Person p = em.find(Person.class, personId);
        return NullResultGuard.checkNotNull(p);
    }

    @Override
    public Collection<PersonGroup> findArchivedGroupsByOwner(User user) {
        Query q = em.createNamedQuery(PersonGroup.Queries.FIND_ARCHIVED_BY_OWNER);
        q.setParameter(PersonGroup.Properties.OWNER, user);

        return (Collection<PersonGroup>) q.getResultList();
    }

    @Override
    public void remove(PersonGroup g) {
        g = em.getReference(PersonGroup.class, g.getId());

        Query q = em.createNamedQuery(PersonGroup.Queries.DELETE_ORPHAN_PERSONS);
        q.setParameter(PersonGroup.Properties.GROUP, g);
        q.executeUpdate();

        em.remove(g);
    }

    @Override
    public void remove(Attribute a) {
        a = em.getReference(Attribute.class, a.getId());
        em.remove(a);
    }

    @Override
    public void remove(Person a) {
        a = em.getReference(Person.class, a.getId());
        em.remove(a);
    }

    @Override
    public Attribute findAttribute(int attributeId) throws ObjectNotFoundException {
        Attribute a = em.find(Attribute.class, attributeId);
        return NullResultGuard.checkNotNull(a);
    }

    @Override
    public void create(Person p, PersonGroup g) {
        em.persist(p);
        em.persist(new PersonToGroup(p, g));
    }

    @Override
    public void create(PersonGroup g) {
        em.persist(g);
    }

    @Override
    public void update(PersonGroup g) {
        em.merge(g);
    }

    @Override
    public void setAttributeForPerson(Person p, Attribute attribute) {
        attribute.setPerson(p);
        em.persist(attribute);
    }

    @Override
    public AttributeType findAttributeType(Integer typeId) throws ObjectNotFoundException {
        AttributeType t = em.find(AttributeType.class, typeId);
        return NullResultGuard.checkNotNull(t);
    }



    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
