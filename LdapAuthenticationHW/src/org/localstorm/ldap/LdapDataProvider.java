package org.localstorm.ldap;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author Alexey Kuznetsov
 */
public class LdapDataProvider
{
    private final String baseDn;
    private final String userDnByLoginFilter;
    private final String providerUrl;
    private final String adminDn;
    private final String adminPassword;

    public LdapDataProvider(String baseDn,
                            String userDnByLoginFilter,
                            String providerUrl,
                            String adminDn,
                            String adminPassword)
    {
        this.baseDn              = baseDn;
        this.userDnByLoginFilter = userDnByLoginFilter;
        this.providerUrl         = providerUrl;
        this.adminDn             = adminDn;
        this.adminPassword       = adminPassword;
    }

    public Map<String, Object> getAttributes(String login, List<String> attrNames) throws Exception
    {
        InitialDirContext context = null;

        try {

            context = new InitialDirContext( LdapRoutines.getLdapProperties(providerUrl, adminDn, adminPassword) );

            // Administrative context
            String userDn = LdapRoutines.getUserDnByLogin(context, userDnByLoginFilter, login, baseDn);

            if (userDn==null) {
                return null;
            }

            return LdapRoutines.queryForAttributes(context, userDn, attrNames);

        } catch(Exception e) {
            e.printStackTrace();
            // TODO: logging here
            return null;
        } finally {
            LdapRoutines.closeContextQuietly(context);
        }
    }

   
    
}
