package org.localstorm.ldap;



/**
 *
 * @author localstorm
 */
public class Main {

    public static void main(String[] args) throws Exception {

        MySimpleLdapAuth auth = new MySimpleLdapAuth();
        AuthenticationResult result = auth.authenticate("localstorm", "12345");

        System.out.println(result.getGroups());
        System.out.println(result.getLogin());
        System.out.println(result.getUuid());
    }

}
