package org.localstorm.mcc.ejb.people;

import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import org.localstorm.mcc.ejb.people.entity.Attribute;
import org.localstorm.mcc.ejb.people.entity.Person;
import java.util.Collection;
import org.localstorm.mcc.ejb.people.entity.PersonToMailList;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public interface MailListManager
{
    public static final String BEAN_NAME="MailListManagerBean";

    public Collection<MailList> findByUser(User u);

    public Collection<PersonToMailList> findMailListContent(MailList ml);

    public void create(PregeneratedMailList pml, String name, User u);

    public void remove(MailList ml);

    public void leaveMailList(MailList ml, Person p);

    public void joinMailList(MailList ml, Person p, Attribute a);

    public PregeneratedMailList generateMailList(Collection<Person> persons);
    
}
