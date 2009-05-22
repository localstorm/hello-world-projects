package org.localstorm.ldap;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;



/**
 *
 * @author localstorm
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // ActiveDirectory
/*
        String baseDn         = "dc=mss,dc=local";

        String userFilter     = "(&(objectClass=user)(sAMAccountName={0}))";
        String providerURL    = "ldap://sun.mss.local:389";

        String adminDN        = "CN=Алексей А. Кузнецов,CN=Users,DC=mss,DC=local";
        String adminPassword  = "****";

        String groupsFilter   = "(&(objectClass=group)(member={0}))";
        String groupNameAttr  = "cn";

        String mailAttr       = "mail";
        String departmentAttr = "department";
        String displayNameAttr = "displayName";
        String companyAttr    = "company";

        String[] attributes   = {mailAttr, departmentAttr, displayNameAttr};
*/
        // OpenDS

        String baseDn         = "dc=mss,dc=local";

        String userFilter     = "(&(objectClass=inetOrgPerson)(givenName={0}))";
        String providerURL    = "ldap://localhost:1389";

        String adminDN        = "cn=Admin";
        String adminPassword  = "12345";

        String groupsFilter   = "(&(objectClass=groupOfUniqueNames)(uniqueMember={0}))";
        String groupNameAttr  = "cn";

        String firstName      = "givenName";
        String[] attributes   = {firstName};

        String userName = null;
        String password = null;

        if (isConsole()) {
            Console c = System.console();

            c.printf("Enter your domain (mss.local) login:");
            userName  = c.readLine();

            c.printf("Enter your password:");
            password = new String(c.readPassword());
        } else {
            System.out.println("Console not available! Trying old input method (Password not hidden).");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your domain (mss.local) login:");

            userName = br.readLine();

            System.out.print("Enter your password:");
            password = br.readLine();
        }

        
        LdapSecurityProvider auth = new LdapSecurityProvider(baseDn,
                                                             userFilter,
                                                             providerURL,
                                                             groupsFilter,
                                                             groupNameAttr,
                                                             adminDN,
                                                             adminPassword);
        
        AuthenticationResult result = auth.authenticate(userName, password);

        System.out.println(result.getGroups());
        System.out.println(result.getLogin());
        System.out.println(result.getDn());

        LdapDataProvider ldp = new LdapDataProvider(baseDn, userFilter, providerURL, adminDN, adminPassword);
        
        Map<String, Object> vals = ldp.getAttributes(userName, Arrays.asList(attributes));
        System.out.println(vals.toString());
    }

    private static boolean isConsole()
    {
        return System.console()!=null;
    }

}
