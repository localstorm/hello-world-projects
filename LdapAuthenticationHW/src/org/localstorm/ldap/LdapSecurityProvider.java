package org.localstorm.ldap;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
public class LdapSecurityProvider
{
    private final String baseDn;
    private final String userDnByLoginFilter;
    private final String providerUrl;
    private final String adminDn;
    private final String adminPassword;
    private final String groupFilter;
    private final String groupNameAttr;

    public LdapSecurityProvider(String baseDn,
                                String userDnByLoginFilter,
                                String providerUrl,
                                String groupFilter,
                                String groupNameAttr,
                                String adminDn,
                                String adminPassword)
    {
        this.baseDn              = baseDn;
        this.userDnByLoginFilter = userDnByLoginFilter;
        this.providerUrl         = providerUrl;
        this.groupFilter         = groupFilter;
        this.groupNameAttr       = groupNameAttr;
        this.adminDn             = adminDn;
        this.adminPassword       = adminPassword;

        if ( groupFilter!=null && groupNameAttr==null ) {
            throw new IllegalArgumentException("groupNameAttr must be not null!");
        }
    }

    public LdapSecurityProvider(String baseDn,
                        String userByLoginFilter,
                        String providerUrl,
                        String adminDn,
                        String adminPassword)
    {
        this.baseDn            = baseDn;
        this.userDnByLoginFilter = userByLoginFilter;
        this.providerUrl       = providerUrl;
        this.groupFilter       = null;
        this.groupNameAttr     = null;
        this.adminDn           = adminDn;
        this.adminPassword     = adminPassword;
    }

    public AuthenticationResult authenticate(String login, String password) throws Exception
    {
        InitialDirContext context = null;

        try {

            AuthenticationResult ar = new AuthenticationResult();
            context = new InitialDirContext( LdapRoutines.getLdapProperties(providerUrl, adminDn, adminPassword) );

            // Administrative context

            String userDn = LdapRoutines.getUserDnByLogin(context, userDnByLoginFilter, login, baseDn);

            if (userDn==null) {
                return null;
            }

            if (this.groupFilter!=null && this.groupNameAttr!=null)  {
                List<String> groups = this.getUserGroupsByUserDN(context,
                                                                this.groupFilter,
                                                                this.groupNameAttr,
                                                                userDn,
                                                                this.baseDn);
                ar.addGroups(groups);
            }

            context.close();

            // User context
            context = new InitialDirContext( LdapRoutines.getLdapProperties(providerUrl, userDn, password) );

            LdapRoutines.checkBound(context, userDn);
            
            ar.setLogin(login);
            ar.setDn(userDn);
            
            return ar;

        } finally {
            LdapRoutines.closeContextQuietly(context);
        }
    }

    private List<String> getUserGroupsByUserDN(InitialDirContext context,
                                               String groupFilter,
                                               String groupNameAttr,
                                               String userDn,
                                               String baseDn) throws NamingException
    {
        List<String> result = new ArrayList<String>();

        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchGroupsFilter = MessageFormat.format(groupFilter, userDn);
        String[] returnedAtts = {groupNameAttr};

        searchCtls.setReturningAttributes(returnedAtts);

        NamingEnumeration answer = context.search(baseDn, searchGroupsFilter, searchCtls);

        while (answer.hasMoreElements())
        {
            SearchResult sr = (SearchResult) answer.next();
            Attributes attrs = sr.getAttributes();
            Attribute attr = attrs.get(groupNameAttr);
            result.add(attr.get().toString());
        }
        
        answer.close();

        return result;
    }

}
