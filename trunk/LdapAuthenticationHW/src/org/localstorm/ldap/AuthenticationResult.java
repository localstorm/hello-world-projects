package org.localstorm.ldap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AuthenticationResult
{
    private String login;
    private String dn;
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

    public void setDn(String uuid)
    {
        this.dn = uuid;
    }

    public String getDn()
    {
        return dn;
    }

    public void addGroups(Collection<String> groups)
    {
        this.groups.addAll(groups);
    }

    public List<String> getGroups()
    {
        return groups;
    }

}
