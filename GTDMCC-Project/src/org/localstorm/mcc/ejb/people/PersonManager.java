package org.localstorm.mcc.ejb.people;

import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.Person;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.entity.AttributeType;
import java.util.Collection;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public interface PersonManager
{
    public static final String BEAN_NAME="PersonManagerBean";

    public void create(PersonGroup g);

    public void create(Person p, PersonGroup g);

    public Collection<PersonGroup> findArchivedGroupsByOwner(User user);

    public Attribute findAttribute(int attributeId)  throws ObjectNotFoundException;

    public AttributeType findAttributeType(Integer typeId) throws ObjectNotFoundException;

    public PersonGroup findGroup(Integer groupId) throws ObjectNotFoundException;

    public PersonGroup findGroupByPerson(Person p);

    public Person findPerson(Integer personId) throws ObjectNotFoundException;

    public Collection<PersonGroup> findGroupsByOwner(User user);

    public Collection<Person> findPersonsByGroup(PersonGroup g);

    public Collection<AttributeType> getAllAttributeTypes();

    public Collection<Attribute> getAttributes(Person p);

    public Collection<Attribute> getEmailAttributes(Person p);

    public void movePersonToGroup(Person p, PersonGroup findGroup);

    public void remove(Attribute a);

    public void remove(Person p);

    public void remove(PersonGroup g);

    public void setAttributeForPerson(Person p, Attribute attribute);

    public void update(Person p);

    public void update(PersonGroup g);

}
