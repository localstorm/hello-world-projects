package org.localstorm.ldap;

import java.text.MessageFormat;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author Alexey Kuznetsov
 */
public class MySimpleLdapAuth 
{
    public AuthenticationResult authenticate(String user, String password) throws Exception
    {
        String userFilter     = "(&(objectClass=inetOrgPerson)(uid={0}))";
        String groupsFilter   = "(&(objectClass=groupOfUniqueNames)(uniqueMember=uid={0},ou=*,dc=*))";
        String baseDn         = "dc=localstorm,dc=org";
        String loginAttr      = "cn";
        String groupAttr      = "cn";
        String entryUUIDAttr  = "entryUUID";
        String providerURL    = "ldap://localhost:1389";
        String principalTemplate = "uid={0},ou=People,dc=localstorm,dc=org";

        // set properties for our connection and provider
        Properties properties = new Properties();
        properties.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
        properties.put( Context.PROVIDER_URL,  providerURL);
        properties.put( Context.REFERRAL,      "ignore" );

        // set properties for authentication
        properties.put( Context.SECURITY_AUTHENTICATION, "simple");
        properties.put( Context.SECURITY_PRINCIPAL,   MessageFormat.format(principalTemplate, user) );
        properties.put( Context.SECURITY_CREDENTIALS, password );

        InitialDirContext context = new InitialDirContext( properties );

        AuthenticationResult ar = new AuthenticationResult();

        this.fetchLdapGroups(ar, groupsFilter, user, groupAttr, context, baseDn);
        this.fetchLdapUser(ar, userFilter, user, loginAttr, entryUUIDAttr, context, baseDn);

        return ar;
    }

    private void fetchLdapGroups(AuthenticationResult ar,
                                String groupsFilter,
                                String user,
                                String groupAttr,
                                InitialDirContext context,
                                String baseDn)
            throws NamingException
    {
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchGroupsFilter = MessageFormat.format(groupsFilter, user);
        String[] returnedAtts = {groupAttr};
        searchCtls.setReturningAttributes(returnedAtts);
        
        NamingEnumeration answer = context.search(baseDn, searchGroupsFilter, searchCtls);
        while (answer.hasMore())
        {
            SearchResult sr = (SearchResult) answer.next();
            Attributes attrs = sr.getAttributes();
            Attribute attr = attrs.get(groupAttr);
            ar.addGroup((String) attr.get());
        }
        answer.close();
    }

    private void fetchLdapUser(AuthenticationResult ar,
                               String userFilter,
                               String user,
                               String loginAttr,
                               String entryUUIDAttr,
                               InitialDirContext context,
                               String baseDn) throws NamingException
    {
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchGroupsFilter = MessageFormat.format(userFilter, user);
        String[] returnedAtts = {loginAttr, entryUUIDAttr};
        searchCtls.setReturningAttributes(returnedAtts);

        NamingEnumeration answer = context.search(baseDn, searchGroupsFilter, searchCtls);
        
        if (answer.hasMore())
        {
            SearchResult sr = (SearchResult) answer.next();
            Attributes attrs = sr.getAttributes();

            Attribute login = attrs.get(loginAttr);
            ar.setLogin((String) login.get());

            Attribute uuid = attrs.get(entryUUIDAttr);
            ar.setUuid((String) uuid.get());
        }

        if (answer.hasMore())
        {
            throw new RuntimeException("Fuck! Too many users found!");
        }

        answer.close();
    }
}
