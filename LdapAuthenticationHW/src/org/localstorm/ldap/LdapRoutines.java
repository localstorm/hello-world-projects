package org.localstorm.ldap;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class LdapRoutines 
{
     public static String getUserDnByLogin(InitialDirContext context,
                                           String userByLoginFilter,
                                           String userLogin,
                                           String baseDn)
        throws NamingException
    {
        String            dnValue = null;
        SearchControls searchCtls = new SearchControls();

        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String searchUserFilter = MessageFormat.format(userByLoginFilter, userLogin);

        NamingEnumeration answer = context.search(baseDn, searchUserFilter, searchCtls);

        if (answer.hasMoreElements())
        {
            SearchResult sr = (SearchResult) answer.nextElement();
            dnValue=sr.getNameInNamespace();
        }

        checkNoMore(answer);

        answer.close();
        return dnValue;
    }

    public static Properties getLdapProperties(String providerURL, String dn, String pwd)
    {
        // set properties for our connection and provider
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, providerURL);
        properties.put(Context.PROVIDER_URL, providerURL);
        properties.put(Context.REFERRAL,     "follow" );

        // set properties for authentication
        properties.put(Context.SECURITY_AUTHENTICATION, "simple");
        properties.put(Context.SECURITY_PRINCIPAL,      dn);
        properties.put(Context.SECURITY_CREDENTIALS,    pwd);
        return properties;
    }


    public static void checkNoMore(NamingEnumeration answer) throws RuntimeException
    {
        if (answer.hasMoreElements()) {
            throw new RuntimeException("Too many LDAP records found!");
        }
    }

    public static void closeContextQuietly(InitialDirContext context)
    {
        try {
            if (context != null) {
                context.close();
            }
        } catch(NamingException e) {
            e.printStackTrace();
        }
    }

    public static void checkBound(InitialDirContext context, String userDn) throws NamingException
    {
        context.lookup(userDn);
    }

    public static Map<String, Object> queryForAttributes(InitialDirContext context,
                                                         String userDn,
                                                         List<String> attrNames)
        throws NamingException
    {
        Map<String, Object> result = new HashMap<String, Object>();

        String[] attrIds = new String[attrNames.size()];
        attrNames.toArray(attrIds);

        Attributes attrs = context.getAttributes(userDn, attrIds);
        
        NamingEnumeration<? extends Attribute> enumer = attrs.getAll();

        while (enumer.hasMoreElements()) {
            Attribute attr = enumer.nextElement();
            String key   = attr.getID();
            Object value = attr.get();

            result.put(key, value);
        }

        return result;
    }

}
