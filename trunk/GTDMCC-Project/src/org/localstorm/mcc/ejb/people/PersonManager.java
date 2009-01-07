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

    public PersonGroup findGroup(Integer groupId) throws ObjectNotFoundException;

    public Collection<PersonGroup> findGroupsByOwner(User user);

    public Collection<Person> findPersonsByGroup(PersonGroup g);

}
