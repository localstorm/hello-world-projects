package org.localstorm.mcc.ejb.people.maillists;

import org.localstorm.mcc.ejb.people.persons.*;
import java.util.Collection;
import javax.xml.registry.infomodel.User;

/**
 * @author localstorm
 */
public interface MailListManager
{
    public static final String BEAN_NAME="MailListManagerBean";

    public Collection<MailList> findByUser(User u);

    public MailListContent findMailListContent(MailList ml);

    public void create(PregeneratedMailList pml);

    public void remove(MailList ml);

    public void leaveMailList(MailList ml, Person p);

    public void joinMailList(MailList ml, Person p, Attribute a);

    public PregeneratedMailList generateMailList(Collection<Person> persons);
    
}
