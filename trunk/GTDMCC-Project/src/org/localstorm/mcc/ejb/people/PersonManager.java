package org.localstorm.mcc.ejb.people;

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

    public PersonGroup findGroup(Integer groupId) throws ObjectNotFoundException;

    public Collection<PersonGroup> findGroupsByOwner(User user);

    public Collection<Person> findPersonsByGroup(PersonGroup g);

    public void remove(PersonGroup g);

    public void update(PersonGroup g);

}
