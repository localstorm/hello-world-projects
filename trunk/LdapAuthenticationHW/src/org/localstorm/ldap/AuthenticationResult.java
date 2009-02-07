package org.localstorm.ldap;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AuthenticationResult
{
    private String login;
    private String uuid;
    private List<String> groups;

    public AuthenticationResult()
    {
        groups = new LinkedList<String>();
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getLogin()
    {
        return login;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void addGroup(String group)
    {
        this.groups.add(group);
    }

    public List<String> getGroups()
    {
        return groups;
    }

}
